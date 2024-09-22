import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        int G = gems.length;
        
        Set<String> kind = new HashSet<>();
        Map<String, Integer> count = new HashMap<>();
        for(int i = 0;i<G;i++){
            kind.add(gems[i]);
            if(!count.containsKey(gems[i])){
                count.put(gems[i], 0);
            }
        }
        
        int T = kind.size();
            
        int total = 0;
        
        int left = 0;
        int right = 0;
        
        int min = Integer.MAX_VALUE;
        int leftMin = 0;
        int rightMin = 0;
        
        while(right<G){
            
            // 범위를 오른쪽으로 늘려가면서 모든 종류를 포함하는 곳까지 가기
            String gem = gems[right];
            if(count.get(gem)==0){
                total++;
            }
            count.put(gem, count.get(gem)+1);
            right++;
            
            // 범위를 왼쪽부터 줄여가도 모든 종류를 포함할 수 있으면 줄이기
            while(count.get(gems[left])>1){
                gem = gems[left];
                count.put(gem, count.get(gem)-1);
                left++;
            }
            
            // 현재 구간이 더 짧은 구간이면 갱신하기
            if(total==T && right-left < min){
                min = (right-left);
                leftMin = left;
                rightMin = right-1;
            }
            
            
        }
        
        return new int[]{leftMin+1, rightMin+1};
    }
}