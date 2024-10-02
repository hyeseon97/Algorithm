import java.util.*;

class Solution {
    
    // 주사위 총 개수
    static int N;
    
    // 주사위
    static int[][] Dice;
    
    // N개중 A의 주사위 N/2개를 뽑기
    static int[] A;
    
    // N개중 B의 주사위 N/2개
    static int[] B;
    
    // 주사위를 뽑을 횟수
    // 모든 경우를 뽑을 필요 없고 반만 뽑으면 나머지도 알 수 있음
    static int pickCount;
    
    // 현재까지 주사위 뽑은 횟수
    static int gameCount;
    
    // A의 N/2개의 주사위를 던져 나올 수 있는 경우
    static List<Integer> scoreA;
    
    // B의 N/2개의 주사위를 던져 나올 수 있는 경우
    static List<Integer> scoreB;
    
    // 최대 승률
    static int max;
    
    // 최대 승률인 주사위 종류
    static int[] maxDice;
    
    public int[] solution(int[][] dice) {
        
        N = dice.length;
        Dice = new int[N][6];
        for(int i = 0;i<N;i++){
            Dice[i] = dice[i];
        }
        A = new int[N/2];
        B = new int[N/2];
        scoreA = new ArrayList<>();
        scoreB = new ArrayList<>();
        max = 0;
        maxDice = new int[N/2];
        
        pickCount = 1;
        for(int i = N;i>N/2;i--){
            pickCount *= i;
        }
        for(int i = N/2;i>=1;i--){
            pickCount /= i;
        }
        pickCount /= 2;
        
        gameCount = 0;
        
        // dice에서 A의 주사위 N/2개 뽑기
        pick(0, 0);
        
        return maxDice;
    }
    
    // N개중에 N/2개의 A주사위 뽑는 메서드 --------------------------------------
    static void pick(int idx, int num){
        
        // 모든 경우 다 계산하지 않고 반만 따져보고 종료
        if(gameCount == pickCount) return;
        
        // A 주사위 다 골랐을 때
        if(idx == N/2){
            // A가 고르지 않은 주사위 B에게 주기           
            B = new int[N/2];
            int aidx = 0; int bidx = 0;
            for(int i = 0;i<N;i++){
                if(aidx<N/2 && A[aidx] == i){
                    aidx++;
                    continue;
                } else{
                    B[bidx++] = i;
                }
            }
            
            // A와 B가 가지고 있는 주사위를 던졌을 때 얻을 수 있는 숫자 합 계산
            scoreA = new ArrayList<>();
            scoreB = new ArrayList<>();
            sum(scoreA, A, 0, 0);
            sum(scoreB, B, 0, 0);
            Collections.sort(scoreA);
            Collections.sort(scoreB);
            
            // A가 이길 경우와 무승부를 할 경우 계산
            // 합계로 나올 수 있는 수
            int M = scoreA.size();
            // 이긴 경우 수와 계산
            int win = 0;
            // 진 경우 수와 계산
            int lose = 0;
            
            // 이분탐색으로 이긴횟수와 진횟수 계산
            for(int a = 0;a<scoreA.size();a++){
                int tempWin = findWin(scoreA.get(a));
                win += tempWin;
                int tempLose = findLose(scoreA.get(a));
                lose += (M - tempLose - 1);
                
            }
            
            // 애초에 모든 주사위를 뽑는 경우가 아닌 반만 하기로 했던 것이
            // A가 뽑은 반대인 경우가 상대방이 가진 경우이기 때문에
            // 이긴 것과 진것을 비교해서 max값 갱신
            if(win > lose){
                if(max < win){
                    max = win;
                    for(int d = 0;d<N/2;d++){
                        maxDice[d] = A[d]+1;
                    }
                }
            } else if(win < lose){
                if(max < lose){
                    max = lose;
                    for(int d = 0;d<N/2;d++){
                        maxDice[d] = B[d]+1;
                    }
                }
            }
            System.out.println(win + " " + lose);
            gameCount++;
            return;
        }
        
        while(num<N){
            A[idx] = num;
            pick(idx+1, num+1);
            num++;
        }

    }
    
    static int findWin(int valueA){
        
        int min = 0;
        int max = scoreB.size()-1;
        int mid = (min+max)/2;
        while(min<=max){
            mid = (min+max)/2;
            if(valueA <= scoreB.get(mid)){
                max = mid-1;
            } else{
                min = mid+1;
            }
        }
        
        if(valueA <= scoreB.get(mid)){
            return mid;
        } else{
            return mid+1;
        }
        
    }
    
    static int findLose(int valueA){
        
        int min = 0;
        int max = scoreB.size()-1;
        int mid = (min+max)/2;
        while(min<=max){
            mid = (min+max)/2;
            if(valueA < scoreB.get(mid)){
                max = mid-1;
            } else{
                min = mid+1;
            }
        }
        
        if(valueA >= scoreB.get(mid)){
            return mid;
        } else{
            return mid-1;
        }
        
    }
    
    // 뽑은 주사위로 얻을 수 있는 모든 합계 계산하는 메서드 ------------------------
    static void sum(List<Integer> score, int[] AB, int idx, int total){
        
        // N/2개의 주사위 모두 각각 숫자 하나씩 골랐을 때
        if(idx == N/2){
            score.add(total);
            return;
        }
        
        for(int i = 0;i<6;i++){
            sum(score, AB, idx+1, total+Dice[AB[idx]][i]);
        }
        
    }
    
    
}