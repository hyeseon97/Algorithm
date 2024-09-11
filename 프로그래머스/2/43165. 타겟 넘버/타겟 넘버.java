import java.util.*;

class Solution {
    public int solution(int[] numbers, int target) {
        int answer = 0;
        
        // BFS
        // [0]은 몇번째 숫자를 계산할것인지
        // [1]은 여태까지 계산한 값
        Queue<int[]> q = new LinkedList<>();
        // 첫번째 숫자를 +하거나 -한 두가지 경우 넣고 시작
        q.add(new int[]{1, numbers[0]});
        q.add(new int[]{1, -numbers[0]});
        
        while(!q.isEmpty()){
            int[] temp = q.poll();
            int index = temp[0];
            int value = temp[1];
            
            if(index == numbers.length){
                if(value == target){
                    answer++;
                }
                continue;
            }
            
            q.add(new int[]{index+1, value+numbers[index]});
            q.add(new int[]{index+1, value-numbers[index]});
        }
        
        return answer;
    }
}