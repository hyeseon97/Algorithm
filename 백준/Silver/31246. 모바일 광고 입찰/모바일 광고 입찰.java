import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] A = new int[N];
        int[] B = new int[N];
        int[] diff = new int[N];
        for(int i = 0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
            diff[i] = A[i]-B[i];
        }

        Arrays.sort(diff);
        int count = 0;
        int X = 0;
        for(int i = N-1;i>=0;i--){
            if(diff[i]<0) {
                X = Math.abs(diff[i]);
            }
            count++;

            if(count==K){
                System.out.println(X);
                return;
            }
        }

        System.out.println(X);

    }
}