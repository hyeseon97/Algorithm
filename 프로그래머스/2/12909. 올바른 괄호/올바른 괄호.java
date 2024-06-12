import java.util.Stack;

class Solution {
    boolean solution(String s) {
        Stack<Character> stack = new Stack<>();
        
        for(int i = 0;i<s.length();i++){
            if(s.charAt(i)==')'){
                if(!stack.isEmpty() && stack.peek()=='('){
                        stack.pop();
                } else{
                    return false;
                }
            } else{
                stack.push(s.charAt(i));
            }
        }
        
        if(stack.isEmpty()){
            return true;
        } else{
            return false;
        }
    }
}