package beatCode.room_management.competition.code_submission;

import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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

// make this class for every user for every round of competition
// make this class for every user for every round of competition
public class Judge {
	static String apiKey = "11d108ea98msh0b32b603285ab50p1ab7e9jsn64159b60167f";
	static int questionID; // questionID should correspond to database
	static String functionDef; 
	static String questionName; 
	
	// store test case info
	// the output for corresponding inputs will have the same index 
	static Vector<String> allInputs;
	static Vector<String> allExpectedOutputs;
	static int totalTestsPassed = 0;
	static String sourceCode;
	
	// how many more tests the user passed since the last time they clicked run
	static int numAdditionalTestsPassed; 
	

	public Judge(int questionID) { // constructor
		Judge.questionID = questionID;
	}
	
	public int getTotalTestsPassed() {
		return totalTestsPassed;
	}
	
	public int getNumAdditionalTestsPassed() {
		return numAdditionalTestsPassed;
	}
	
	private static void makeDatabaseConn(QuestionRepository qRepo, 
        TestCaseRepository tRepo, 
        TestCaseToQuestionRepository tqRepo) {

		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String jdbcUrl = "jdbc:mysql://localhost:3306/project";
		String username = "root";
		String password = "Velayuthan27";

		
        Question problem = qRepo.findQuestionByQuestionId(questionID);

        String title = problem.getTitle();

        String functiondef = problem.getSkeletonCode(); 


        functionDef = functiondef;

        allInputs = new Vector<>();
        allExpectedOutputs = new Vector<>();
        // get all of the test cases info

        Vector<Integer> testcaseIDs = new Vector<>();

        TestCaseToQuestion[] testcases = tqRepo.findAllTestCaseToQuestionByQuestionId(questionID);

        for (TestCaseToQuestion testcase : testcases)
            testcaseIDs.add(testcase.getTestCaseId());
        
        // get the test case info for each testcase_id
        TestCase testcase;
        for (int i = 0; i < testcaseIDs.size(); i++) {
            // ps = conn.prepareStatement("SELECT * FROM test_cases WHERE test_case_id = " + testcaseIDs.get(i));
            testcase = tRepo.findTestCaseByTestCaseId(testcaseIDs.get(i));

            allInputs.add(testcase.getInput());
            allExpectedOutputs.add(testcase.getOutput());
        }
		
	}
	
	private static void setQuestionName() {
		// will return lengthOfLongestSubstring, twoSum, etc. which is the function name
		String inputString = functionDef;
		
		String regexPattern = "\\b(\\w+)\\(";
	        
        // create a pattern object
        Pattern pattern = Pattern.compile(regexPattern);
        
        // create matcher object
        Matcher matcher = pattern.matcher(inputString);

        if (matcher.find()) {
            // extract the method name
            String methodName = matcher.group(1);
//            System.out.println("Extracted method name: " + methodName);
            questionName = methodName;
        } else {
            System.out.println("Method name not found.");
        }
	}
	
	// call this function to run code
	// returns the total number of test cases passed
    public static int runCode(String sourceCode, int questionID,
        QuestionRepository qRepo, TestCaseRepository tRepo, 
        TestCaseToQuestionRepository tqRepo) {

    	Judge.questionID = questionID;
    	makeDatabaseConn(qRepo, tRepo, tqRepo);
    	setQuestionName();
    	
    	// reset to 0, this is to calculate how many more test cases user has passed since last time they ran the code
    	numAdditionalTestsPassed = 0; 
        totalTestsPassed = 0;
    	
    	String originalCode = sourceCode;
    	
        System.out.println("This is the size: " + allInputs.size());
    	// do this for every single test case
    	for (int i = 0; i < allInputs.size(); i++) {
          try {
        	  
        	// append this to the end of sourceCode for stdout
        	sourceCode = originalCode + "\nsolution = Solution()\n" +
        		    "result = solution." + questionName + "(" + allInputs.get(i) + ")\n" +
        		    "print(result)";
        	
        	System.out.println("This is sourceCode: " + sourceCode);
        	
            URL url = new URL("https://judge0-ce.p.rapidapi.com/submissions/??base64_encoded=true&wait=false");
            
            // open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // setting HTTP Request Method and Headers
            conn.setRequestMethod("POST"); // first is to do POST, this is to get the response token
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-RapidAPI-Key", apiKey);
            conn.setRequestProperty("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");
            conn.setDoOutput(true);
            
            // create json payload with gson
            Gson gson = new Gson();
            JsonObject payload = new JsonObject();
            payload.addProperty("language_id", 71); // Python 3.8
            payload.addProperty("source_code", sourceCode);

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
            
            if (response != null && response.has("token") && !response.get("token").isJsonNull()) {
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
    			        getResultConn.setRequestProperty("X-RapidAPI-Key", apiKey);
    			        getResultConn.setRequestProperty("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");

    			        // read the response
    			        BufferedReader getResultIn = new BufferedReader(new InputStreamReader(getResultConn.getInputStream()));
    			        String getResultInputLine;
    			        StringBuilder getResultContent = new StringBuilder();
    			        while ((getResultInputLine = getResultIn.readLine()) != null) {
    			            getResultContent.append(getResultInputLine);
    			        }
    			        getResultIn.close();

    			        // parse json response using gson into a json object
    			        getResultResponse = gson.fromJson(getResultContent.toString(), JsonObject.class);
                        System.out.println(getResultResponse.toString());
    			        
    			        // wait for response
    			        Thread.sleep(2000); 

    			    } while (getResultResponse.get("status").getAsJsonObject().get("id").getAsInt() == 1); // status ID 1 is "In Queue"
    			    
    			    // Check if response contains 'stdout' -- if it didn't error
    			    if (getResultResponse.has("stdout")) {
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
    			            	numAdditionalTestsPassed++;
    			            }
    			        } catch (IllegalArgumentException e) {
    			            System.out.println("Error in Base64 decoding: " + e.getMessage());
    			        }
    			    }
    			    
    			    // pretty print final response: the json response, optional
//    			    Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
//    			    String prettyGetResultJson = prettyGson.toJson(getResultResponse);
//    			    System.out.println(prettyGetResultJson);
    			    
    			} catch (Exception e) {
    			    e.printStackTrace();
    			}
            }
            
        } catch (Exception e) {
            System.err.println("error in POST request");
        }
	}
    System.out.println("Total number of test cases passed: " + totalTestsPassed);
    return totalTestsPassed;
}
    
    public static void main(String[] args) {
    	// two sum example: (question id is 1)
    	// passes 2 test cases
    	String sourceCode = "from typing import List\n"
    			+ "class Solution:\n"
    			+ "    def twoSum(self, nums: List[int], target: int) -> List[int]:\n"
    			+ "        mylist = [0,1]\n"
    			+ "        return mylist\n";
   
    	
    	// this is a twoSum python solution from leetcode, bruteforce
    	// this works
    	// passes all 3 test cases
    	String sourceCode2 = "from typing import List\n"
    			+ "class Solution:\n"
    			+ "    def twoSum(self, nums: List[int], target: int) -> List[int]:\n"
    			+ "        n = len(nums)\n"
    			+ "        for i in range(n - 1):\n"
    			+ "            for j in range(i + 1, n):\n"
    			+ "                if nums[i] + nums[j] == target:\n"
    			+ "                    return [i, j]\n"
    			+ "        return []\n";
    	
    	// passes 2 test cases
    	String sourceCode3 = "from typing import List\n"
    			+ "class Solution:\n"
    			+ "    def twoSum(self, nums: List[int], target: int) -> List[int]:\n"
    			+ "        mylist = [1,2]\n"
    			+ "        return mylist\n";
    	
    	// length of longest substring example: (question id is 3)
//    	String sourceCode = "class Solution:\n"
//				+ "    def lengthOfLongestSubstring(self, s: str) -> int:\n"
//				+ "        myans = 1\n"
//				+ "        return myans\n";
    	
    	// word ladder example: (question id is 127)
//    	String sourceCode = "from typing import List\n"
//		+ "class Solution:\n"
//				+ "    def ladderLength(self, beginWord: str, endWord: str, wordList: List[str]) -> int:\n"
//				+ "        myans = 5\n"
//				+ "        return 5\n"
//				+ "    \n";
//    	
    	int questionID = 1;
    	// int numTestsPassed = runCode(sourceCode3, questionID);
    }
}
