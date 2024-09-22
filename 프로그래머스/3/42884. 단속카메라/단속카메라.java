import java.util.*;

class Solution {
    public int solution(int[][] routes) {
        
        int R = routes.length;
        
        Arrays.sort(routes, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2){
                if(o1[0]==o2[0]) return o1[1]-o2[1];
                return o1[0]-o2[0];
            }
        });

        int answer = 0;
        
        int endMin = 30000;
        for(int i = 0;i<R;i++){
            int start = routes[i][0];
            int end = routes[i][1];
            if(endMin < start){
                answer++;
                endMin = end;
            } else{
                endMin = Math.min(endMin, end);
            }
        }
        
        
        return answer+1;
        
    }
}