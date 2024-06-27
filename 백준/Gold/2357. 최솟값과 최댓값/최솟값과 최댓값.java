import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    // 1. 세그먼트 트리로 각 구간마다 최소값과 최대값 저장
    // 2. 구간이 정해지면 순회해서 해당하는 범위에 있는 최소값 최대값 찾기

    static int N;
    static int M;

    static int h;
    static int size;

    static int[] arr;
    static int[][] tree;

    static int findMin;
    static int findMax;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        h = (int)Math.ceil(Math.log(N)/Math.log(2));
        size = 1 << (h+1);

        arr = new int[N+1];
        tree = new int[size][2];

        for(int i = 1;i<=N;i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        init(1, 1, N);

        StringBuilder sb = new StringBuilder();

        for(int i = 0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            findMin = Integer.MAX_VALUE;
            findMax = Integer.MIN_VALUE;
            find(1, 1, N, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            sb.append(findMin + " " + findMax + "\n");
        }

        System.out.println(sb.toString());

    }

    static void init(int node, int start, int end) {
        if(start == end){
            tree[node][0] = arr[start];
            tree[node][1] = arr[start];
            return;
        }
        init(node*2, start, (start+end)/2);
        init(node*2+1, (start+end)/2+1, end);

        int min = Math.min(tree[node*2][0], tree[node*2+1][0]);
        int max = Math.max(tree[node*2][1], tree[node*2+1][1]);
        tree[node][0] = min;
        tree[node][1] = max;
    }

    static void find(int node, int start, int end, int left, int right){
        if(right<start || end<left){
            return;
        }
        if(left<=start && end<=right){
            findMin = Math.min(findMin, tree[node][0]);
            findMax = Math.max(findMax, tree[node][1]);
            return;
        }
        find(node*2, start, (start+end)/2, left, right);
        find(node*2+1, (start+end)/2+1, end, left, right);
    }
}