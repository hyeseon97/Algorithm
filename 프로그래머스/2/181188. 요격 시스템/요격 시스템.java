import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;
        
        Arrays.sort(targets, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2){
                if(o1[0] == o2[0]){
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });
        
        int start = targets[0][0];
        int end = targets[0][1];
        
        for(int i = 1;i<targets.length;i++){
            
            int nextS = targets[i][0];
            int nextE = targets[i][1];
            
            start = Math.max(start, nextS);
            end = Math.min(end, nextE);
            
            if(start>=end){
                answer++;
                start = nextS;
                end = nextE;
            }
            
        }
        
        return answer+1;
    }
}