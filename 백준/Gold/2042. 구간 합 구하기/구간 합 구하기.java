import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    // 1. 트리(배열)의 크기 구하기
    // 2. 구간합 초기화하기
    // 3. 쿼리대로 실행하기

    static int N;
    static int M;
    static int K;

    static int h;
    static int size;

    static long[] arr;
    static long[] tree;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr = new long[N+1];
        for(int i = 1;i<=N;i++){
            arr[i] = Long.parseLong(br.readLine());
        }

        h = (int)Math.ceil(Math.log(N)/Math.log(2));
        size = 1 << (h+1);
        tree = new long[size];

        init(1, 1, N);

        StringBuilder sb = new StringBuilder();

        for(int i = 0;i<(M+K);i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if(a==1) {
                change(b, c, 1, 1, N);
            } else{
                sb.append(sum(1, 1, N, b, (int)c)).append("\n");
            }
        }

        System.out.println(sb.toString());

    }

    // 1. 트리 초기화 메서드
    static void init(int node, int start, int end){
        if(start == end){
            tree[node] = arr[start];
            return;
        }
        init(node*2, start, (start+end)/2);
        init(node*2+1,(start+end)/2+1, end);
        tree[node] = tree[node*2]+tree[node*2+1];
    }

    // 2. a가 1일때 수 바꾸기
    static void change(int index, long number, int node, int start, int end){
        if(index<start || index>end){
            return;
        }
        if(start == end){
            tree[node] = number;
            return;
        }
        change(index, number, node*2, start, (start+end)/2);
        change(index, number, node*2+1, (start+end)/2+1, end);
        tree[node] = tree[node*2]+tree[node*2+1];
    }

    // 3. a가 2일때 구간합 구하기
    static long sum(int node, int start, int end, int left, int right){
        if(right<start || end<left){
            return 0;
        }
        if(left<=start && end<=right){
            return tree[node];
        }
        long leftSum = sum(node*2, start, (start+end)/2, left, right);
        long rightSum = sum(node*2+1, (start+end)/2+1, end, left, right);
        return leftSum + rightSum;
    }

}