import java.util.*;
class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        
        Map<String, Integer> temp = new HashMap<>();
        for(int i = 0;i<clothes.length;i++){
            if(temp.containsKey(clothes[i][1])){
                temp.put(clothes[i][1], temp.get(clothes[i][1])+1);
            } else{
                temp.put(clothes[i][1], 1);
            }
        }
        
        for(String s : temp.keySet()){
            answer *= (temp.get(s)+1);
        }
        
        return answer-1;
    }
}