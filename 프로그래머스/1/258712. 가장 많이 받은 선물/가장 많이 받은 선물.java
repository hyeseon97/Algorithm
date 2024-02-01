import java.util.*;

class Solution {
    public int solution(String[] friends, String[] gifts) {
        int N = friends.length;
        int[][] arr = new int[N+1][N+1];
        HashMap<String, Integer> map = new HashMap<>();
        for(int i = 0;i<N;i++){
            map.put(friends[i], i);
        }
        
        for(int i = 0;i<gifts.length;i++){
            StringTokenizer st = new StringTokenizer(gifts[i]);
            String A = st.nextToken();
            String B = st.nextToken();
            
            int numA = map.get(A);
            int numB = map.get(B);
            
            arr[numA][numB]++;
        }
        
        for(int i = 0;i<N;i++){
            int total = 0;
            for(int j = 0;j<N;j++){
                total += arr[i][j];
            }
            arr[i][N] = total;
        }
        
        for(int i = 0;i<N;i++){
            int total = 0;
            for(int j = 0;j<N;j++){
                total += arr[j][i];
            }
            arr[N][i] = total;
        }
        
        int[] gift = new int[N];
        
        for(int i = 0;i<N;i++){
            for(int j = 0;j<N;j++){
                if(i<=j) continue;
                
                if(arr[i][j]<arr[j][i]){
                    gift[j]++;
                } else if(arr[i][j]>arr[j][i]){
                    gift[i]++;
                } else{
                    int scoreI = arr[i][N]-arr[N][i];
                    int scoreJ = arr[j][N]-arr[N][j];
                    if(scoreI<scoreJ){
                        gift[j]++;
                    } else if(scoreI>scoreJ){
                        gift[i]++;
                    }
                }
            }
        }
        
        Arrays.sort(gift);
        return gift[N-1];
    }
}