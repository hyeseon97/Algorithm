import java.util.*;
class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0;i<progresses.length;i++){
            int day = (int)Math.ceil((100.0-progresses[i])/speeds[i]);
            queue.add(day);
        }
        
        List<Integer> list = new ArrayList<>();
        int day = queue.poll();
        int count = 1;
        while(!queue.isEmpty()){
            if(day<queue.peek()){
                list.add(count);
                day = queue.poll();
                count = 1;
            } else{
                day = Math.max(day, queue.poll());
                count++;
            }
        }
        list.add(count);
        return list.stream().mapToInt(i->i).toArray();
    }
}