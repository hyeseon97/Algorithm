import java.util.*;

class Solution {
    public int solution(int n, int[][] computers) {
        boolean[] visited = new boolean[n];
        int answer = 0;
        
        for(int i = 0;i<n;i++){
            if(visited[i]) continue;
            answer++;
            
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            visited[i] = true;
            
            while(!q.isEmpty()){
                int com = q.poll();
                for(int j = 0;j<n;j++){
                    if(com==j || visited[j] || computers[com][j]==0) continue;
                    q.add(j);
                    visited[j] = true;
                }
            }
        }
        
        return answer;
    }
}