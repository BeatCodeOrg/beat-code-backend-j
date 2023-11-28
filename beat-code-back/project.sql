DROP DATABASE IF EXISTS project;
CREATE DATABASE project;
USE project;


CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    pw VARCHAR(255) NOT NULL
);

-- Table structure for table `topics` 
DROP TABLE IF EXISTS `topics`;
CREATE TABLE `topics` (
  `topic_id` int NOT NULL,
  `topic_name` varchar(50) NOT NULL,
  PRIMARY KEY (`topic_id`)
);

-- Table structure for table `difficulty`
DROP TABLE IF EXISTS `difficulty`;
CREATE TABLE `difficulty` (
  `id` int NOT NULL,
  `level` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

-- Table structure for table `variable_type`
DROP TABLE IF EXISTS `variable_type`;
CREATE TABLE `variable_type` (
  `var_type_id` int NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`var_type_id`)
);

-- Table structure for table `questions` 
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `id` int NOT NULL,
  `title` varchar(200) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `title_slug` varchar(75) NOT NULL,
  `num_parameters` int NOT NULL,
  `main_def` varchar(200) NOT NULL,
  `difficulty` int NOT NULL,
  `topic_slugs` varchar(45) NOT NULL,
  `hint` varchar(500) DEFAULT NULL,
  `constraints` varchar(1000) NOT NULL,
  `examples_str_format` varchar(2000) NOT NULL,
  `output_type` int NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`difficulty`) REFERENCES `difficulty` (`id`),
  FOREIGN KEY (`output_type`) REFERENCES `variable_type` (`var_type_id`)
);

-- Table structure for table `question_topics`
DROP TABLE IF EXISTS `question_topics`;
CREATE TABLE `question_topics` (
  `question_id` int NOT NULL,
  `topic_id` int NOT NULL,
  FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  FOREIGN KEY (`topic_id`) REFERENCES `topics` (`topic_id`)
);

-- Table structure for table `test_cases`
DROP TABLE IF EXISTS `test_cases`;
CREATE TABLE `test_cases` (
  `test_case_id` int NOT NULL,
  `input` varchar(500) NOT NULL,
  `param_names_str` varchar(500) NOT NULL,
  `output` varchar(45) NOT NULL,
  `output_type` int NOT NULL,
  PRIMARY KEY (`test_case_id`),
  FOREIGN KEY (`output_type`) REFERENCES `variable_type` (`var_type_id`)
);

-- Table structure for table `question_test_cases`
DROP TABLE IF EXISTS `question_test_cases`;
CREATE TABLE `question_test_cases` (
  `question_id` int NOT NULL,
  `test_case_id` int NOT NULL,
  FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  FOREIGN KEY (`test_case_id`) REFERENCES `test_cases` (`test_case_id`)
);

-- Data insertion for `difficulty`
INSERT INTO difficulty (id, level) VALUES 
(1, 'Easy'),
(2, 'Medium'),
(3, 'Hard');

-- Data insertion for `topics`
INSERT INTO topics (topic_id, topic_name) VALUES 
(1, 'array'),
(2, 'hash-table'),
(3, 'string'),
(4, 'sliding-window'),
(5, 'breadth-first-search');

-- Data insertion for `variable_type`
INSERT INTO variable_type (var_type_id, type) VALUES 
(1, 'List[int]'),
(2, 'str'),
(3, 'int'),
(4, 'List[str]');

-- Data insertion for `questions`
INSERT INTO questions (id, title, description, title_slug, num_parameters, main_def, difficulty, topic_slugs, hint, constraints, examples_str_format, output_type)
VALUES 
(1, 
'Two Sum', 
'Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`', 
'two_sum', 
2, 
'from typing import List
class Solution:
	def twoSum(self, nums: List[int], target: int) -> List[int]:', 
1, 
'array, hash-table', 
'A really brute force way would be to search for all possible pairs of numbers but that would be too slow. Again, it`s best to try out brute force solutions for just for completeness. It is from these brute force solutions that you can come up with optimizations.', 
'**Constraints:**

-   `2 <= nums.length <= 10`^`4`^
-   `-10`^`9`^` <= nums[i] <= 10`^`9`^
-   `-10`^`9`^` <= target <= 10`^`9`^
-   **Only one valid answer exists.**', 
'**Example 1:**

    Input: nums = [2,7,11,15], target = 9
    Output: [0,1]
    Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].

**Example 2:**

    Input: nums = [3,2,4], target = 6
    Output: [1,2]

**Example 3:**

    Input: nums = [3,3], target = 6
    Output: [0,1]', 1),
(3, 
'Longest Substring Without Repeating Characters', 
'Given a string s, find the length of the longest substring without repeating characters.', 
'longest-substring-without-repeating-characters', 
1, 
'from typing import List
class Solution:
	def lengthOfLongestSubstring(self, s: str) -> int:', 
2, 
'hash-table,string,sliding-window', NULL, '**Constraints:**

-   `0 <= s.length <= 5 * 10`^`4`^
-   `s` consists of English letters, digits, symbols and spaces.', 
'**Example 1:**

    Input: s = "abcabcbb"
    Output: 3
    Explanation: The answer is "abc", with the length of 3.

**Example 2:**

    Input: s = "bbbbb"
    Output: 1
    Explanation: The answer is "b", with the length of 1.

**Example 3:**

    Input: s = "pwwkew"
    Output: 3
    Explanation: The answer is "wke", with the length of 3.
    Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.', 3),
(127, 'Word Ladder', 'A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.', 'word-ladder', 
3, 
'from typing import List
class Solution:
	def ladderLength(self, beginWord: str, endWord: str, wordList: List[str]) -> int:', 3, 
'hash-table,string,breadth-first-search', NULL, '-   `1 <= beginWord.length <= 10`
-   `endWord.length == beginWord.length`
-   `1 <= wordList.length <= 5000`
-   `wordList[i].length == beginWord.length`
-   `beginWord`, `endWord`, and `wordList[i]` consist of lowercase
    English letters.
-   `beginWord != endWord`
-   All the words in `wordList` are unique.', '**Example 1:**

    Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
    Output: 5
    Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.

**Example 2:**

    Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
    Output: 0
    Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.', 3);

-- Data insertion for `question_topics`
INSERT INTO question_topics (question_id, topic_id) VALUES 
(1, 1),
(1, 1),
(3, 2),
(3, 3),
(3, 4),
(127, 2),
(127, 3),
(127, 5);

-- Data insertion for `test_cases`
-- put quotations around strings
INSERT INTO test_cases (/* columns */) VALUES 
(1, '[2,7,11,15],9', 'nums,target', '[0, 1]', 1),
(2, '[3,2,4],6', 'nums,target','[1, 2]', 1),
(3, '[3,3],6', 'nums,target','[0, 1]', 1),
(4, '"abcabcbb"', 's', '3', 3),
(5, '"bbbbb"', 's','1', 3),
(6, '"pwwkew"', 's','3', 3),
(7, '"hit","cog",["hot","dot","dog","lot","log","cog"]', 'beginWord,endWord,wordList','5', 3),
(8, '"hit","cog",["hot","dot","dog","lot","log","cog"]', 'beginWord,endWord,wordList', '0', 3);

-- Data insertion for `question_test_cases`
INSERT INTO question_test_cases (question_id, test_case_id) VALUES 
(1, 1),
(1, 2),
(1, 3),
(3, 4),
(3, 5),
(3, 6),
(127, 7),
(127, 8);
