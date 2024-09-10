import java.util.*;

class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;
        
        // Map에 제품과 수량을 저장하고 사려는 제품 종류 수를 변수에 따로 저장
        // discount 반복문 돌면서
        // 해당 제품에 대해서 Map에 있는 수량을 빼고 해당 수량이 0이 되면 종류 수 뺴기
        // 10일전 제품은 반대로 Map에 있는 수량은 더하고 해당 수량이 1이 되면 종류 수량 더하기
        // 전체수량이 0이면 10일안에 모두 살 수 있으므로 가능한날에+1
        
        Map<String, Integer> list = new HashMap<>();
        int total = want.length;
        for(int i = 0;i<want.length;i++){
            String product = want[i];
            int count = number[i];
            list.put(product, count);
        }
        
        for(int i = 0;i<discount.length+1;i++){
            
            if(i<discount.length){
               String product = discount[i];
                if(list.containsKey(product)){
                    list.put(product, list.get(product)-1);
                    if(list.get(product)==0) total--; 
                } 
            }

            if(i>=10){
                String before = discount[i-10];
                if(list.containsKey(before)){
                    list.put(before, list.get(before)+1);
                    if(list.get(before)==1) total++;
                }
            }

            if(total<=0){
                answer++;
            }
            
            
        }
        
        return answer;
    }
}