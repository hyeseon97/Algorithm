import java.util.*;

class Solution {
    static int answer;
    
    static int N;
    static int M;
    static int[][] map;
    static int[][] min;
    
    public int solution(int[][] maps) {
        answer = Integer.MAX_VALUE;
        N = maps.length;
        M = maps[0].length;
        map = new int[N][M];
        min = new int[N][M];
        for(int i = 0;i<N;i++){
            map[i] = maps[i];
            Arrays.fill(min[i], Integer.MAX_VALUE);
        }
        min[0][0] = 1;
        
        다익스트라();
        
        if(min[N-1][M-1]==Integer.MAX_VALUE) return -1;
        return min[N-1][M-1];
    }
    
    static void 다익스트라(){
        
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0, 1});
        while(!q.isEmpty()){
            int[] temp = q.poll();
            int r = temp[0];
            int c = temp[1];
            int count = temp[2];
            
            for(int d = 0;d<4;d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr<0 || nc<0 || nr>=N || nc>=M || map[nr][nc]==0 || count+1>=min[nr][nc]) continue;
                min[nr][nc] = count+1;
                q.add(new int[]{nr, nc, count+1});
            }
        }
    }
    
    class Node {
        int r;
        int c;
        int count;
        String visited;
        
        Node(int r, int c, int count, String visited){
            this.r = r;
            this.c = c;
            this.count = count;
            this.visited = visited;
        }
        
    }
    
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};
}