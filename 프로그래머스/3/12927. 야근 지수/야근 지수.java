import java.util.*;

class Solution {
    public long solution(int n, int[] works) {
        int W = works.length;
        long answer = 0;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2){
                return o2-o1;
            }
        });
        
        for(int i = 0;i<W;i++){
            pq.add(works[i]);
        }
        
        for(int i = 0;i<n;i++){
            int work = pq.poll();
            if(work == 0) return 0;
            pq.add(work-1);
        }
        
        while(!pq.isEmpty()){
            answer += Math.pow(pq.poll(), 2);
        }
        
        return answer;
        
    }
}