import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s1 = br.readLine();
        String s2 = br.readLine();
        int N = s1.length();
        int M = s2.length();

        int[][] lcs = new int[N+1][M+1];
        int max = 0;

        for(int i = 1;i<=N;i++){
            for(int j = 1;j<=M;j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    lcs[i][j] = lcs[i-1][j-1] + 1;
                    max = Math.max(max, lcs[i][j]);
                } else{
                    lcs[i][j] = Math.max(lcs[i-1][j], lcs[i][j-1]);
                }
            }
        }

        int i = N;
        int j = M;
        int val = lcs[i][j];
        StringBuilder sb = new StringBuilder();

        while(val>0){
            if(lcs[i-1][j]==val) {
                i--;
            } else if(lcs[i][j-1]==val){
                j--;
            } else {
                sb.append(s1.charAt(i-1));
                i--;
                j--;
                val = lcs[i][j];
            }
        }

        System.out.println(max);
        System.out.println(sb.reverse().toString());

    }
}