import java.util.*;
class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i =  0;i<scoville.length;i++){
            pq.add(scoville[i]);
        }
    
        while(pq.size()>=2 && pq.peek()<K){
            int first = pq.poll();
            int second = pq.poll();
            int mix = first + second*2;
            pq.add(mix);
            answer++;
        }
        
        if(pq.peek()<K) return -1;
        return answer;
    }
}