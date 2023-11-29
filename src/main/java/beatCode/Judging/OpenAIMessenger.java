package beatCode.Judging;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.completion.chat.ChatCompletionRequest.ChatCompletionRequestFunctionCall;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Component;

import java.time.Duration;

import java.util.*;
@Component
public class OpenAIMessenger {
    public static String convertTestCase(String codeStub, String language, String testCase, String openAIKey){
        String reformattedRequest = reformatRequest(codeStub, language, testCase);
        ChatMessage query = new ChatMessage(ChatMessageRole.USER.value(), reformattedRequest);
        List<ChatMessage> context = createFewShot();
        String response = sendRequest(query, context, openAIKey);

        //We next formulate the response into two sections

        //For ease of processing to ensure the array gets split into three
        response = "hi" + response + "bye";

        String[] responseArr = response.split("```");
        if (responseArr.length != 3){
            System.out.println("Error in chatgpt output format. Need to manually add into sql database");
            System.out.println("chatgpt output: " + response);
            return "Error";
        }

        //We take the middle between the two
        String processed_response = responseArr[1];

        System.out.println("processed_response: " + processed_response);

        //We first remove ``` instances
        //Next we remove the first line (which contains the language
        processed_response = processed_response.substring(processed_response.indexOf("\n") + 1);
        //Next we split by the delimitor
        String[] inner_responseArr = processed_response.split("<input_code>");
        if (inner_responseArr.length != 2){
            System.out.println("Error in chatgpt output format. Need to manually add into sql database");
            System.out.println("chatgpt output: " + response);
            return "Error";
        }
        //We now assume the input is correct

        //Note we are passing back the string with the delimitor still inserted
        return processed_response;
    }



    public static String reformatRequest(String codeStub, String language, String testCase){
        String retStr = "language:" + language + "\ncode:\'\'\'" + codeStub +
                "\'\'\'\n" + "Test case: " + testCase;
        return retStr;
    }

    public static List<ChatMessage> createFewShot(){
        List<ChatMessage> messages = new ArrayList<>();

        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You will be provided with a coding language, a starter code to a coding problem, along with a test case. You will add a main function that will run the given test case and output the results to stdout. You should not attempt to complete the coding problem yourself . Your job is solely to construct a pipeline that will run the test case given some solution. Make sure that all imports and namespaces are set accordingly. Indicate where the input should be placed using <input_code> for everything to run properly. ");
        messages.add(systemMessage);

        String userFirstMsg = """
                language: java
                code: '''class Solution {
                    public int reverse(int x) {
                       \s
                    }
                }'''
                Test case: 123""";
        String assistantFirstMsg = """
                ```java
                <input_code>
                public class Main {
                    public static void main(String[] args) {
                        Solution solution = new Solution();
                        int x = 123;
                        int result = solution.reverse(x);
                        System.out.println(result);
                    }
                }
                ```
                """;
        ChatMessage firstShotUser = new ChatMessage(ChatMessageRole.USER.value(), userFirstMsg);
        messages.add(firstShotUser);
        ChatMessage firstShotAssistant = new ChatMessage(ChatMessageRole.ASSISTANT.value(), assistantFirstMsg);
        messages.add(firstShotAssistant);

        String userSecondMsg = """
                language: python
                code: '''class Solution:
                    def longestPalindrome(self, s: str) -> str:
                        '''
                Test case: babad""";
        String assistantSecondMsg = """
                ```python
                <input_code>
                if __name__ == "__main__":
                    solution = Solution()
                    s = "babad"
                    result = solution.longestPalindrome(s)
                    print(result)
                ```
                """;
        ChatMessage secondShotUser = new ChatMessage(ChatMessageRole.USER.value(), userSecondMsg);
        messages.add(secondShotUser);
        ChatMessage secondShotAssistant = new ChatMessage(ChatMessageRole.ASSISTANT.value(), assistantSecondMsg);
        messages.add(secondShotAssistant);

        String userThirdMsg = """
                language: c++
                code: '''class Solution {
                public:
                    vector<int> twoSum(vector<int>& nums, int target) {
                       \s
                    }
                };'''
                Test case: [2,7,11,15], 9""";
        String assistantThirdMsg = """
                ```cpp
                #include <vector>
                #include <iostream>
                using namespace std;
                                
                <input_code>
                                
                int main() {
                    Solution solution;
                    vector<int> nums = {2, 7, 11, 15};
                    int target = 9;
                    vector<int> result = solution.twoSum(nums, target);
                   \s
                    for (int i = 0; i < result.size(); i++) {
                        cout << result[i] << " ";
                    }
                    cout << endl;
                   \s
                    return 0;
                }
                ```
                """;
        ChatMessage thirdShotUser = new ChatMessage(ChatMessageRole.USER.value(), userThirdMsg);
        messages.add(thirdShotUser);
        ChatMessage thirdShotAssistant = new ChatMessage(ChatMessageRole.ASSISTANT.value(), assistantThirdMsg);
        messages.add(thirdShotAssistant);

        return messages;
    }

    public static String sendRequest(ChatMessage query, List<ChatMessage> context, String openAIKey) {
        String token = openAIKey;
//        String token = System.getenv("OPENAI_KEY");
        OpenAiService service = new OpenAiService(token, Duration.ZERO);

        context.add(query);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo-0613")
                .messages(context)
                .n(1)
                .maxTokens(512)
                .logitBias(new HashMap<>())
                .build();
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();

        return responseMessage.getContent();
    }

}
