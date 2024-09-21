import java.util.*;

class Solution {
    public int solution(int[] money) {
        int N = money.length;
        // 0인덱스를 포함하는 dp
        int[][] dp0 = new int[2][N];
        // 0인덱스를 포함하지 않고 1인덱스부터 포함하는 dp
        int[][] dp1 = new int[2][N];
        
        // 0인덱스는 포함하고 마지막 인덱스는 포함하지 않는 dp 계산
        for(int j = 0;j<N-1;j++){
            
            for(int i = 0;i<2;i++){
                
                if((i==0 && j<2) || (i==1 && j<3) ) {
                    dp0[i][j] = money[j];
                    continue;
                }
            
                dp0[i][j] = Math.max(dp0[0][j-(i+2)], dp0[1][j-(i+2)]) + money[j];
                
            }
            
        }
        
        // 0인덱스는 포함하지 않고 마지막 인덱스는 포함하는 dp 계산
        for(int j = 1;j<N;j++){
            
            for(int i = 0;i<2;i++){
                
                if((i==0 && j<2) || (i==1 && j<3) ) {
                    dp1[i][j] = money[j];
                    continue;
                }
            
                dp1[i][j] = Math.max(dp1[0][j-(i+2)], dp1[1][j-(i+2)]) + money[j];
                
            }
            
        }
        
        List<Integer> list = new ArrayList<>();
        list.add(dp0[0][N-3]);
        list.add(dp0[0][N-2]);
        list.add(dp0[1][N-2]);
        
        list.add(dp1[0][N-2]);
        list.add(dp1[0][N-1]);
        list.add(dp1[1][N-1]);
        
        Collections.sort(list);
        
        return list.get(list.size()-1);
        
    }
}