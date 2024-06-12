import java.util.*;

public class Solution {
    public int[] solution(int []arr) {
        Stack<Integer> stack = new Stack<>();
        for(int i = 0;i<arr.length;i++){
            if(stack.isEmpty()){
                stack.add(arr[i]);
            } else{
                if(!(stack.peek()==arr[i])){
                    stack.add(arr[i]);
                }
            }
        }
        
        int[] answer = new int[stack.size()];
        int index = stack.size()-1;
        while(!stack.isEmpty()){
            answer[index--] = stack.pop(); 
        }
        
        return answer;
    }
}