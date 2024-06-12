import java.util.*;
class Solution {
    public int solution(int[] priorities, int location) {
        // 각 우선순위에서 같은 우선순위들끼리 마지막에 뽑히는 위치는 어디인지 파악
        // 우선순위가 가장 높은 9부터 파악해서 그 다음 우선순위는 현재 우선순위 마지막의 앞부터 계산
        
        // location의 우선순위
        int P = priorities[location];
        
        // location의 우선순위보다 한단계 높은 우선순위에서 마지막으로 뽑힐 위치
        int index = priorities.length-1;
        
        for(int p = 9;p>P;p--){
            int i = index;
            while(priorities[i]!=p){
                i--;
                if(i==-1) i = priorities.length-1;
                if(i==index) break;
            }
            index = i;
        }
        
        // location의 우선순위보다 더 큰 우선순위들 개수 먼저 더하기
        int count = 0;
        for(int i = 0;i<priorities.length;i++){
            if(priorities[i]>P) count++;
        }
        
        if(count == 0) index = 0;
        
        // index 부터 location까지 P와 같은 개수 더하기
        while(index!=location){
            if(priorities[index++]==P) count++;
            if(index==priorities.length) index = 0;
        }
        
        return count+1;
    }
}