import java.util.*;

class Solution {
    public int solution(int[][] triangle) {
        int N = triangle.length;
        int[][] result = new int[N][N];
        result[0][0] = triangle[0][0];
        
        for(int i = 1;i<N;i++){
            for(int j = 0;j<N;j++){
                if(i<j) continue;
                if(j==0) result[i][j] = result[i-1][j] + triangle[i][j];
                else if(i==j) result[i][j] = result[i-1][j-1] + triangle[i][j];
                else {
                    int num1 = result[i-1][j] + triangle[i][j];
                    int num2 = result[i-1][j-1] + triangle[i][j];
                    result[i][j] = Math.max(num1, num2);
                }
            }
        }
        
        List<Integer> list = new ArrayList<>();
        for(int j = 0;j<N;j++){
            list.add(result[N-1][j]);
        }
        
        Collections.sort(list);
        return list.get(N-1);
        
    }
}