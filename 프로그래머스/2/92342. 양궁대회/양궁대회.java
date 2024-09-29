class Solution {
    
    // 화살 개수
    static int N;
    
    // 어피치의 정보와 점수
    static int[] apeach;
    static int A;
    
    // 라이언의 정보와 점수
    static int[] rion;
    static int R;
    
    // 가장 큰 점수 차이
    static int max;
    
    // 가장 큰 점수 차이일 때 라이언 정보
    static int[] maxRion;
    
    public int[] solution(int n, int[] info) {
        
        N = n;
        apeach = new int[11];
        A = 0;
        rion = new int[11];
        R = 0;
        maxRion = new int[11];
        max = 0;
        
        for(int i = 0;i<=10;i++){
            apeach[i] = info[i];
            if(apeach[i]>0){
                A += (10-i);
            }
        }
        
        uuu = 0;
        
        // 라이언이 10점부터 점수를 먹는 경우와 먹지 않는 경우를 따져서
        // 화살 개수를 다 썼을 때 어피치와 점수를 비교
        // 점수 차이가 더 크게 나면 갱신
        // 점수 차이가 같을 때는 가장 낮은 점수를 맞힌 화살 개수 비교해서 갱신
        game(N, 0);
        
        if(max==0){
            return new int[]{-1};
        }
        return maxRion;
    }
    
    // 점수 인덱스 (점수 10-i)
    static void game(int arrow, int i){
        
        // 화살 다 썼거나 점수 0까지 다 고려했을때 비교해서 갱신
        if(arrow==0 || i==11){
            
            // 어피치 점수가 더 크거나 같으면 어피치 승이므로 바로 리턴
            if(A >= R) return;
            
            // 차이가 최대차이보다 클거나 같을때만 업데이트
            int diff = R - (A<0?0:A);
            if(diff >= max){
                uuu++;
                // 화살을 다 쓰지 않았는데 점수를 다 고려했다면 남은 화살은 0점에 꽂기
                if(arrow!=0 && i==11){
                    rion[10] += arrow;
                    // System.out.println("업데이트하는데 화살이 남아서 더함 " + rion[10]);
                }
                update();
                // 원복
                if(arrow!=0 && i==11){
                    rion[10] -= arrow;
                    // System.out.println("업데이트하고 화살 다시 수거 " + rion[10]);
                }
            }
            
            return;
        }
        
        
        // 10-i 점을 먹을 때
        // 어피치가 해당 점수를 쏜 화살 개수보다 +1한 만큼 화살이 남아 있으면 먹을 수 있음
        if(apeach[i]+1 <= arrow){
            // 어피치가 해당 점수를 먹고 있었으면 감점
            if(apeach[i]>0) A -= (10-i);
            R += (10-i);
            rion[i] = apeach[i]+1;
            game(arrow-rion[i], i+1);
            
            // 원복
            rion[i] = 0;
            R -= (10-i);
            // 어피치가 해당 점수를 먹고 있었으면 원복
            if(apeach[i]>0) A += (10-i);
        }
        
        // 10-i 점을 먹지 않을 때
        game(arrow, i+1);
        
    }
    
    static int uuu;
    
    static void update(){
        
        int diff = R - (A<0?0:A);
        
        // 차이가 최대차이보다 클 때
        if(diff > max){
            max = diff;
            for(int i = 0;i<=10;i++){
                maxRion[i] = rion[i];
            }
        } 
        // 차이가 최대차이와 같을 때
        else if(diff == max){
            // 낮은 점수 맞힌 개수 비교
            for(int idx = 10;idx>=0;idx--){
                if(maxRion[idx] == rion[idx]) continue;
                if(maxRion[idx]<rion[idx]){
                    for(int i = 0;i<=10;i++){
                        maxRion[i] = rion[i];
                    }
                } else{
                    break;
                }
            }
        }
        
    }
}