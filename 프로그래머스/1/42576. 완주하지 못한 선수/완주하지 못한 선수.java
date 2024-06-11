import java.util.*;
class Solution {
    public String solution(String[] participant, String[] completion) {
        
        Map<String, Integer> temp = new HashMap<>();
        for(int i = 0;i<participant.length;i++){
            if(temp.containsKey(participant[i])){
                temp.put(participant[i], temp.get(participant[i])+1);
            } else{
                temp.put(participant[i], 1);
            }
        }
        
        for(int i = 0;i<completion.length;i++){
            if(temp.containsKey(completion[i])){
                int count = temp.get(completion[i]);
                if(count==1){
                    temp.remove(completion[i]);
                } else{
                    temp.put(completion[i], count-1);
                }
            }
        }
        
        System.out.println(temp.keySet().size());
        
        for(String s : temp.keySet()){
            return s;
        }
        
        return "null";
    }
}