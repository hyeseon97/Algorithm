import java.util.Stack;

class Solution {
    boolean solution(String s) {
        boolean answer = true;

        Stack<Character> stack = new Stack<>();
        for(int i = 0;i<s.length();i++){
            char c = s.charAt(i);
            if(!stack.isEmpty()){
                if(c == '('){
                    stack.push(c);
                } else{
                    if(stack.pop() == ')'){
                        answer = false;
                        break;
                    }
                }
            } else{
                if(c == '('){
                    stack.push(c);
                } else{
                    answer = false;
                    break;
                }
            }
        }
        if(!stack.isEmpty()){
            answer = false;
        }
        return answer;
    }
}