class Solution {
    public int solution(String[][] board, int h, int w) {
        int answer = 0;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for(int d = 0;d<4;d++){
            if(h+dr[d]<0 || w+dc[d]<0 || h+dr[d]>=board.length || w+dc[d]>=board[0].length) continue;
            if(board[h+dr[d]][w+dc[d]].equals(board[h][w])) answer++;
        }
        return answer;
    }
}