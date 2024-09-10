class Solution {
    
    // 던전 개수
    static int N;
    // 초기 피로도
    static int K;
    // 던전 정보
    static int[][] dungeon;
    // 던전 탐험할 순서 저장
    static int[] arr;
    // 탐험할 수 있는 최대 던전
    static int max;
    public int solution(int k, int[][] dungeons) {

        N = dungeons.length;
        K = k;
        dungeon = new int[N][2];
        for(int i = 0;i<N;i++){
            dungeon[i] = dungeons[i];
        }
        arr = new int[N];
        max = 0;
        
        // 순열로 탐험할 수 있는 모든 경우의 수 따져보기
        travel(0, 0);
        return max;
    }
    
    // 순서 정해지고 탐험할 수 있는데까지 하기
    static void test(){
        int heart = K;
        int round = 0;
        while(round<N){
            int seq = arr[round];
            if(heart >= dungeon[seq][0]){
                heart -= dungeon[seq][1];
            } else{
                break;
            }
            round++;
        }
        max = Math.max(max, round);
    }
    
    // 순서 정하기
    static void travel(int seq, int visited){
        if(seq == N){
            test();
            return;
        }
        
        for(int i = 0;i<N;i++){
            if((visited & (1<<i))>0) continue;
            arr[seq] = i;
            travel(seq+1, visited|(1<<i));
        }
    }
}