import java.util.*;

class Solution {
    
    // 보드의 크기
    static int N;
    
    // 방문체크
    // i가 작은 것 -> j가 작은 것 순으로 좌표 띄어쓰기해서 저장
    static Set<String> visited = new HashSet<>();
    
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};
    
    public int solution(int[][] board) {
        int answer = 0;
        
        N = board.length;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0, 0, 1, 0});
        visited.add("(0,0) (0,1)");
        while(!q.isEmpty()){
            int[] temp = q.poll();
            int ar = temp[0];
            int ac = temp[1];
            int br = temp[2];
            int bc = temp[3];
            int count = temp[4];
            
            // N-1, N-1에 도착했을 때
            if(br==N-1 && bc==N-1){
                return count;
            }
            
            // 상 하 좌 우 로 이동하기
            for(int d = 0;d<4;d++){
                int nar = ar+dr[d];
                int nac = ac+dc[d];
                int nbr = br+dr[d];
                int nbc = bc+dc[d];
                
                // 배열 밖을 벗어나거나 벽이면 건너뛰기
                if(nar<0 || nac<0 || nbr<0 || nbc<0 || nar>=N || nac>=N || nbr>=N || nbc>=N || board[nar][nac]==1 || board[nbr][nbc]==1) continue;
                
                // 방문한 곳이면 건너뛰기
                String v = "(" + nar + "," + nac + ") (" + nbr + "," + nbc + ")";
                if(visited.contains(v)) continue;
                
                q.add(new int[]{nar, nac, nbr, nbc, count+1});
                visited.add(v);
            } // 상 하 좌 우 이동 끝
            
            // 회전하기
            // 가로일 때 
            if(ar == br){
                // 위로 회전할 수 있을 때
                if(ar-1>=0 && br-1>=0 && board[ar-1][ac]==0 && board[br-1][bc]==0){
                    // 위왼쪽 회전
                    int nar = ar-1;
                    int nac = ac;
                    int nbr = ar;
                    int nbc = ac;
                    // 방문한 곳이 아닐 때 회전 가능
                    String v = "(" + nar + "," + nac + ") (" + nbr + "," + nbc + ")";
                    if(!visited.contains(v)) {
                        q.add(new int[]{nar, nac, nbr, nbc, count+1});
                        visited.add(v);
                    }
                    
                    // 위오른쪽 회전
                    nar = br-1;
                    nac = bc;
                    nbr = br;
                    nbc = bc;
                    // 방문한 곳이 아닐 때 회전 가능
                    v = "(" + nar + "," + nac + ") (" + nbr + "," + nbc + ")";
                    if(!visited.contains(v)) {
                        q.add(new int[]{nar, nac, nbr, nbc, count+1});
                        visited.add(v);
                    }
                }
                // 아래로 회전할 수 있을 때
                if(ar+1<N && br+1<N && board[ar+1][ac]==0 && board[br+1][bc]==0){
                    // 아래왼쪽 회전
                    int nar = ar;
                    int nac = ac;
                    int nbr = ar+1;
                    int nbc = ac;
                    // 방문한 곳이 아닐 때 회전 가능
                    String v = "(" + nar + "," + nac + ") (" + nbr + "," + nbc + ")";
                    if(!visited.contains(v)) {
                        q.add(new int[]{nar, nac, nbr, nbc, count+1});
                        visited.add(v);
                    }
                    
                    // 아래오른쪽 회전
                    nar = br;
                    nac = bc;
                    nbr = br+1;
                    nbc = bc;
                    // 방문한 곳이 아닐 때 회전 가능
                    v = "(" + nar + "," + nac + ") (" + nbr + "," + nbc + ")";
                    if(!visited.contains(v)) {
                        q.add(new int[]{nar, nac, nbr, nbc, count+1});
                        visited.add(v);
                    }
                }
            }
            
            // 세로일 때 
            if(ac == bc){
                // 왼쪽으로 회전할 수 있을 때
                if(ac-1>=0 && bc-1>=0 && board[ar][ac-1]==0 && board[br][bc-1]==0){
                    // 왼쪽위 회전
                    int nar = ar;
                    int nac = ac-1;
                    int nbr = ar;
                    int nbc = ac;
                    // 방문한 곳이 아닐 때 회전 가능
                    String v = "(" + nar + "," + nac + ") (" + nbr + "," + nbc + ")";
                    if(!visited.contains(v)) {
                        q.add(new int[]{nar, nac, nbr, nbc, count+1});
                        visited.add(v);
                    }
                    
                    // 왼쪽아래 회전
                    nar = br;
                    nac = bc-1;
                    nbr = br;
                    nbc = bc;
                    // 방문한 곳이 아닐 때 회전 가능
                    v = "(" + nar + "," + nac + ") (" + nbr + "," + nbc + ")";
                    if(!visited.contains(v)) {
                        q.add(new int[]{nar, nac, nbr, nbc, count+1});
                        visited.add(v);
                    }
                }
                // 오른쪽으로 회전할 수 있을 때
                if(ac+1<N && bc+1<N && board[ar][ac+1]==0 && board[br][bc+1]==0){
                    // 오른쪽위 회전
                    int nar = ar;
                    int nac = ac;
                    int nbr = ar;
                    int nbc = ac+1;
                    // 방문한 곳이 아닐 때 회전 가능
                    String v = "(" + nar + "," + nac + ") (" + nbr + "," + nbc + ")";
                    if(!visited.contains(v)) {
                        q.add(new int[]{nar, nac, nbr, nbc, count+1});
                        visited.add(v);
                    }
                    
                    // 오른쪽아래 회전
                    nar = br;
                    nac = bc;
                    nbr = br;
                    nbc = bc+1;
                    // 방문한 곳이 아닐 때 회전 가능
                    v = "(" + nar + "," + nac + ") (" + nbr + "," + nbc + ")";
                    if(!visited.contains(v)) {
                        q.add(new int[]{nar, nac, nbr, nbc, count+1});
                        visited.add(v);
                    }
                }
            } // 회전 이동 끝
            
        }
        
        return answer;
    }
}