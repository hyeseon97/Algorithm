import java.util.*;
class Solution {
    public int solution(int[][] land) {
        
        int N = land.length;
        int M = land[0].length;
        int[][] check = new int[N][M];
        List<Integer> count = new ArrayList<>();
        count.add(0);
        
        int num = 1;
        
        boolean[][] visited = new boolean[N][M];
        for(int i = 0;i<N;i++){
            for(int j = 0;j<M;j++){
                if(land[i][j]==0 || visited[i][j]) continue;
                
                Queue<int[]> q = new LinkedList<>();
                q.add(new int[] {i, j});
                visited[i][j] = true;
                int cnt = 0;
                
                while(!q.isEmpty()){
                    int[] temp = q.poll();
                    int r = temp[0];
                    int c = temp[1];
                    check[r][c] = num;
                    cnt++;
                    
                    for(int d = 0;d<4;d++){
                        int nr = r+dr[d];
                        int nc = c+dc[d];
                        if(nr<0 || nc<0 | nr>=N || nc>=M || visited[nr][nc] || land[nr][nc]==0) continue;
                        q.add(new int[]{nr, nc});
                        visited[nr][nc] = true;
                    }
                }
                num++;
                count.add(cnt);
            }
        }
        
        int max = 0;
        for(int j = 0;j<M;j++){
            int sum = 0;
            Set<Integer> set = new HashSet<>();
            for(int i = 0;i<N;i++){
                if(check[i][j]==0) continue;
                if(set.contains(check[i][j])) continue;
                sum += count.get(check[i][j]);
                set.add(check[i][j]);
            }
            max = Math.max(max, sum);
        }
        
        return max;
    }
    
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
}