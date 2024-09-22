import java.util.*;

class Solution {
    
    static int N;
    static int M;
    static int[][] way;
    
    
    static int[] dr = {1, 0};
    static int[] dc = {0, 1};
    
    public int solution(int m, int n, int[][] puddles) {
        
        N = n;
        M = m;
        way = new int[n][m];
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                way[i][j] = -999;
            }
        }
        
        for(int i = 0;i<puddles.length;i++){
            int r = puddles[i][1]-1;
            int c = puddles[i][0]-1;
            way[r][c] = -1;
        }
        
        way[0][0] = 1;
        
        go(n-1, m-1);
        
        return way[n-1][m-1];
        
    }
    
    static int go(int r, int c){
        
        if(r==-1 || c==-1 || way[r][c]==-1) return 0;
        
        if(way[r][c]==-999){
            way[r][c] = (go(r-1, c) % 1000000007 + go(r, c-1) % 1000000007) % 1000000007;
        }
        
        return way[r][c];
        
    }
}