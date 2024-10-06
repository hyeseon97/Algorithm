import java.io.*;
import java.util.*;


public class Main {

    // 격자판 크기
    static int R;
    static int C;
    // 상어의 수
    static int M;
    // 상어 리스트
    static List<int[]> shark;
    // 상어 이동시키고 각 칸에 우선순위큐로 크기가 큰 상어순으로 정렬해서 저장
    static PriorityQueue<int[]>[][] sea;
    // 다음열에서 잡을 상어 크기
    static int size;
    // 다음열에서 가장 위에 있는 상어의 행
    static int up;
    // 낚시왕이 잡은 상어 크기 합계
    static int total;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        shark = new ArrayList<>();
        sea = new PriorityQueue[R][C];
        total = 0;

        size = 0;
        up = R;
        int num = -1; // 0열에서 잡을 상어 번호 기억했다가 입력 다 받고 삭제하기

        for(int i = 0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1; // 행 위치
            int c = Integer.parseInt(st.nextToken())-1; // 열 위치
            int s = Integer.parseInt(st.nextToken()); // 속력
            int d = Integer.parseInt(st.nextToken())-1; // 이동방향 0부터 방향 가지기 위해 -1
            int z = Integer.parseInt(st.nextToken()); // 크기

            // 상어 이동 방향에 따라 속력 재설정
            // 2*(R-1) 또는 2*(C-1) 의 배수 이동은 원점으로 되돌아오기 때문에 이의 나머지값이 최적의 이동 횟수
            int speed = 0;
            if(d == 0 ||d == 1){
                speed = s % (2*(R-1));
            } else {
                speed = s % (2*(C-1));
            }

            if(c == 0 && r < up){
                up = r;
                size = z;
                num = i;
            }

            shark.add(new int[]{i, r, c, speed, d, z});
        } // 상어 입력 끝

        if(num != -1){
            shark.remove(num);
        }

        // 낚시왕이 한칸씩 오른쪽으로 이동하면서 상어를 잡고 상어 이동시키기
        for(int c = 0;c<C;c++){
            // 이전에 저장해뒀던 잡을 상어 크기 합 더해주기
            total += size;

            // 상어 이동시키기
            setSea();

            // 다음열에서 낚시왕이 잡을 상어 크기 저장하고 살아남은 상어 shark에 넣기
            size = setShark(c+1);
        }

        System.out.println(total);
    }

    static void setSea(){
        sea = new PriorityQueue[R][C];

        for(int i = 0;i<shark.size();i++){
            int num = shark.get(i)[0]; // 상어 번호
            int r = shark.get(i)[1]; // 행 위치
            int c = shark.get(i)[2]; // 열 위치
            int s = shark.get(i)[3]; // 속력
            int d = shark.get(i)[4]; // 이동방향
            int z = shark.get(i)[5]; // 크기

            // 상어 하나씩 이동시켜서 sea에 넣기
            move(i, r, c, s, d, z);
        }
    }

    static void move(int num, int r, int c, int s, int d, int z){

        // 방향대로 s만큼 이동
        int speed = s;
        while(speed > 0){
            // 배열 밖으로 나가면 방향 바꾸기
            if(r+dr[d]<0 || c+dc[d]<0 || r+dr[d]>=R || c+dc[d]>=C){
                if(d == 0 || d == 2) d++;
                else d--;
            }

            r += dr[d];
            c += dc[d];
            speed--;
        }

        // sea의 우선순위큐에 넣기
        if(sea[r][c] == null){
            sea[r][c] = new PriorityQueue<>(new Comparator<int[]>(){
                @Override
                public int compare(int[] o1, int[] o2){
                    return o2[5] - o1[5];
                }
            });
        }

        sea[r][c].add(new int[]{num, r, c, s, d, z});

    }

    static int setShark(int next){

        int nextSize = 0;
        shark = new ArrayList<>();

        for(int j = 0;j<C;j++){
            for(int i = 0;i<R;i++){
                if(sea[i][j] == null) continue;

                // 다음열이면서 잡을 상어가 아직 선택 안되었을 때 잡을 상어 크기 저장
                if(j == next && nextSize == 0){
                    int[] temp = sea[i][j].poll();
                    nextSize = temp[5];
                    continue;
                }

                // pq에서의 첫 상어만 저장
                // 가장 큰 상어로 살아남은 상어
                int[] temp = sea[i][j].poll();
                shark.add(temp);
            }
        }

        return nextSize;
    }
}