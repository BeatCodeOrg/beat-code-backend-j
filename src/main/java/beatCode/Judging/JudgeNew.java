package beatCode.Judging;

import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.Base64;
import java.util.Vector;
import java.util.regex.Matcher;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//This class will have one function to call: get_score, which returns a list of
@Component
public class JudgeNew {

    static String judge0Key = "4a05387cb9msh3ef958b5a65aa13p1cf839jsna563b458db4a";
    static String openAIKey = "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";
    //Each instance will need: user_id and question_id
    static String jdbcUrl = "jdbc:mysql://localhost:3306/beatcode_db";
    static String username = "root";
    static String password = "2486@1889Gubei";

    //Creates a hashmap that maps from each coding language to its corresponding id
    static HashMap<String, Integer> languageMap = new HashMap<String, Integer>();
    static {
        languageMap.put("java", 62);
        languageMap.put("python", 71);
        languageMap.put("c++", 54);
    }

    int questionID = -1;
    String sourceCode = "null";
    int languageID = -1;
    String language = "null";
    String error_message = "null";

    Vector<String> allInputs;
    Vector<String> allExpectedOutputs;
    Vector<String> allPrefix;
    Vector<String> allSuffix;

    public JudgeNew() {
        allInputs = new Vector<>();
        allExpectedOutputs = new Vector<>();
        allPrefix = new Vector<>();
        allSuffix = new Vector<>();
    }
    public JudgeNew(String sourceCode, int questionID, String language){
        runCode(sourceCode, questionID, language);
    }

    //This will return -1 if there is an error
    public int runCode(String sourceCode, int questionID, String language){

        if (!languageMap.containsKey(language)){
            System.out.println("Error: language not supported" + language);
            return -1;
        }
        //Completely renews the object with the new instance
        this.questionID = questionID;
        this.sourceCode = sourceCode;
        this.languageID = languageMap.get(language);
        this.language = language;
        allInputs = new Vector<>();
        allExpectedOutputs = new Vector<>();
        allPrefix = new Vector<>();
        allSuffix = new Vector<>();
        error_message = "null";

        makeDatabaseConn();

        if (error_message == "error"){
            System.out.println("error in getting test case code");
            return -1;
        }
        int num_correct = queryJudge0();
        if (error_message == "error") {
            System.out.println("error in judge0");
            return -1;
        }
        return num_correct;


    }

    //Given the testcaseid, prefix and suffix from openAI, it will store this test case for future use
    public void insertTestcaseToDatabase(int test_case_id, String prefix, String suffix){
        try {
            // Establishing the connection
            Connection conn = DriverManager.getConnection(jdbcUrl + "?user=" + username + "&password=" + password);
            if (conn != null) {
                // SQL query to insert data
                String sql = "INSERT INTO test_cases_run (test_case_id, language, prefix_to_code, suffix_to_code) VALUES (?, ?, ?, ?)";

                // Creating a PreparedStatement for executing the query
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    st.setInt(1, test_case_id);
                    st.setInt(2, languageID);
                    st.setString(3, prefix);
                    st.setString(4, suffix);
                    // Executing the query
                    int affectedRows = st.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Data inserted successfully!");
                    } else {
                        System.out.println("Data insertion failed.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("sql exception when trying to add chatgpt response!");
            e.printStackTrace();
        }
    }

    //Returns -1 if there is an error. returns 1 otherwise
    private void makeDatabaseConn() {
        if (questionID == -1) {
            System.out.println("Error: questionID not set");
            error_message = "error";
            return;
        }
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(jdbcUrl + "?user=" + username + "&password=" + password);
            st = conn.createStatement();
            ps = conn.prepareStatement("SELECT * from questions WHERE id = " + questionID);
            rs = ps.executeQuery();

            // get all of the test cases info
            ps = conn.prepareStatement("SELECT * FROM question_test_cases WHERE question_id = " + questionID);
            rs = ps.executeQuery();
            Vector<Integer> testcaseIDs = new Vector<>();
            while(rs.next()) {
                int testcase_id = rs.getInt("test_case_id");
                testcaseIDs.add(testcase_id);
            }
            // get the test case info for each testcase_id
            for (int i = 0; i < testcaseIDs.size(); i++) {
                //This gets the inputs and outputs for the particular test case
                ps = conn.prepareStatement("SELECT * FROM test_cases WHERE test_case_id = " + testcaseIDs.get(i));
                rs = ps.executeQuery();
                while(rs.next()) {
                    String input = rs.getString("input");
                    String output = rs.getString("output");
                    allInputs.add(input);
                    allExpectedOutputs.add(output);
                }
                //This gets the prefix and suffix needed to run each test case
                ps = conn.prepareStatement("SELECT * FROM test_cases_run WHERE test_case_id = " + testcaseIDs.get(i) + " AND language = " + languageID);
                rs = ps.executeQuery();

                boolean test_case_exists = false;

                while(rs.next()){
                    test_case_exists = true;
                    String prefix = rs.getString("prefix_to_code");
                    String suffix = rs.getString("suffix_to_code");
                    allPrefix.add(prefix);
                    allSuffix.add(suffix);
                    //Just take one (in case database is messed up
                    break;
                }

                //If the test case doesn't exist, then we need to create it using chatgpt
                if (!test_case_exists){
                    System.out.println("not found previous case. Creating new case using chatgpt");
                    String chatgptResponse = OpenAIMessenger.convertTestCase(sourceCode, language, allInputs.get(i), openAIKey);

                    if (chatgptResponse.equals("Error")){
                        error_message = "error";
                        return;
                    }
                    //We now need to parse the response
                    String[] responseArr = chatgptResponse.split("<input_code>");
                    System.out.println("successfully added chatgpt!");
                    insertTestcaseToDatabase(testcaseIDs.get(i), responseArr[0], responseArr[1]);
                    //We manually add the prefix and suffix directly
                    allPrefix.add(responseArr[0]);
                    allSuffix.add(responseArr[1]);
                }
            }
        } catch (SQLException sqle) {
            System.out.println ("SQLException: " + sqle.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }

        }
    }

    // call this function to run code
    // returns the total number of test cases passed
    public int queryJudge0() {
        System.out.println("entered!");
        int totalTestsPassed = 0;
        // do this for every single test case
        for (int i = 0; i < allInputs.size(); i++) {
            try {
                System.out.println("entered0!");
                // append this to the end of sourceCode for stdout
                String augmentedSourceCode = allPrefix.get(i) + "\n" + sourceCode + "\n" + allSuffix.get(i);
                System.out.println("entered1!");

//                System.out.println("This is sourceCode: " + sourceCode);

                URL url = new URL("https://judge0-ce.p.rapidapi.com/submissions/??base64_encoded=true&wait=false");

                // open connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // setting HTTP Request Method and Headers
                conn.setRequestMethod("POST"); // first is to do POST, this is to get the response token
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("X-RapidAPI-Key", judge0Key);
                conn.setRequestProperty("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");
                conn.setDoOutput(true);

                // create json payload with gson
                Gson gson = new Gson();
                JsonObject payload = new JsonObject();
                payload.addProperty("language_id", languageID); // Python 3.8
                payload.addProperty("source_code", augmentedSourceCode);

                System.out.println("entered2!");

                // send request
                OutputStream os = conn.getOutputStream();
                // convert jsonobject to json string and send to connection
                os.write(gson.toJson(payload).getBytes("utf-8"));
                os.close();

                // read response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();


                // parse into a json object to access data
                JsonObject response = gson.fromJson(content.toString(), JsonObject.class);
                System.out.println("entered3!");


                if (response != null) {
                    // need to use token to get actual response that's in json format
                    String token = response.get("token").getAsString();

                    // after getting token, need to get detailed response using token
                    try {
                        // GET request url
                        String getResultUrl = "https://judge0-ce.p.rapidapi.com/submissions/" + token + "?base64_encoded=true&fields=*";

                        JsonObject getResultResponse;
                        // keep going until submission processing is complete, api response is not immediate due to differences in executing code
                        do {
                            // make new connection
                            HttpURLConnection getResultConn = (HttpURLConnection) new URL(getResultUrl).openConnection();
                            getResultConn.setRequestMethod("GET");
                            getResultConn.setRequestProperty("X-RapidAPI-Key", judge0Key);
                            getResultConn.setRequestProperty("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");

                            // read the response
                            BufferedReader getResultIn = new BufferedReader(new InputStreamReader(getResultConn.getInputStream()));
                            String getResultInputLine;
                            StringBuilder getResultContent = new StringBuilder();
                            while ((getResultInputLine = getResultIn.readLine()) != null) {
                                getResultContent.append(getResultInputLine);
                            }
                            getResultIn.close();
                            System.out.println("entered4!");

                            // parse json response using gson into a json object
                            getResultResponse = gson.fromJson(getResultContent.toString(), JsonObject.class);

                            // wait for response
                            Thread.sleep(2000);

                        } while ((getResultResponse.get("status").getAsJsonObject().get("id").getAsInt() == 1) || (getResultResponse.get("status").getAsJsonObject().get("id").getAsInt() == 2)); // status ID 1 is "In Queue". Status ID 2 is "processing"


//                        System.out.println("This is the response: " + getResultResponse.toString());
                        // Check if response status is 3 (ok) and contains 'stdout' -- if it didn't error
                        if ((getResultResponse.get("status").getAsJsonObject().get("id").getAsInt() == 3) && (getResultResponse.has("stdout"))) {
                            String encodedStdout = getResultResponse.get("stdout").getAsString();

                            try {
                                encodedStdout = encodedStdout.trim();
                                byte[] decodedBytes = Base64.getDecoder().decode(encodedStdout);

                                // get rid of the extra "\n" at the end of the string
                                String stdoutContent = new String(decodedBytes).replace("\n", "").replace("\r", "");

                                System.out.println("Decoded stdout: " + stdoutContent);

                                // get rid of white space before doing comparisons
                                String expectedOutput = allExpectedOutputs.get(i).trim();
                                System.out.println("This is the expected output: " + expectedOutput);

                                if (stdoutContent.equals(expectedOutput)) {
                                    System.out.println("testcase passed!");
                                    totalTestsPassed++;
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println("Error in Base64 decoding: " + e.getMessage());
                            }
                        }
                        else{
                            System.out.println("Something appears to be wrong in your code! ");
                            error_message = "error";
                        }

                        // pretty print final response: the json response, optional
//                        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
//                        String prettyGetResultJson = prettyGson.toJson(getResultResponse);
//                        System.out.println(prettyGetResultJson);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                System.err.println("error in POST request" + e.getMessage());
            }
        }
        return totalTestsPassed;
    }

}
