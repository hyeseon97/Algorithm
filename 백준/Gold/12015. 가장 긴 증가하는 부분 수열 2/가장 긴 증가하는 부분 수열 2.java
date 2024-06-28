import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        int[] tails = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0;i<N;i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int lastIndex = 0;
        tails[0] = arr[0];

        for(int i = 1;i<N;i++){
            int number = arr[i];

            // 마지막 인덱스의 숫자보다 크면 배열의 가장 뒤에 추가
            if(tails[lastIndex]<number){
                tails[++lastIndex] = number;
            } else{

                // 그렇지 않을 경우 이분탐색으로 들어갈 자리 찾기
                int min = 0;
                int max = lastIndex;
                int mid = (min+max)/2;
                while(min<=max){
                    mid = (min+max)/2;
                    if(tails[mid]<number){
                        min = mid+1;
                    } else {
                        max = mid-1;
                    }
                }

                if(tails[mid]<number){
                    tails[mid+1] = number;
                } else{
                    tails[mid] = number;
                }
            }
        }
        System.out.println(lastIndex+1);
    }
}