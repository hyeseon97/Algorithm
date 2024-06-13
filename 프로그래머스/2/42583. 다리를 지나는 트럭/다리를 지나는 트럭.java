import java.util.*;
class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        
        // 현재 시간
        int time = 1;
        // 대기중인 첫번째 차
        int index = 0;
        // 다리 위의 차 무게 합계
        int total = 0;
        // 다리 위의 차들, {다리를 건너기 시작한 시간, 무게}
        Queue<int[]> queue = new LinkedList<>();
        
        while(true){
            
            // 내릴 수 있는 차 내리기
            // 현재시간이 차가들어간시간+다리길이이면 도착
            if(!queue.isEmpty() && queue.peek()[0]+bridge_length<=time){
                int[] temp = queue.poll();
                total -= temp[1]; // 무게합에서 무게 덜어내기
            }
            
            // 다음차가 들어갈 수 있는지 확인
            // 최대개수와 최대무게 제한에 걸리지 않는지 확인
            if(index<truck_weights.length && total+truck_weights[index]<=weight && queue.size()+1<=bridge_length){
                // 가능하면 큐에 넣고, 무게도 더하고, 다음차로 인덱스옮기고, 시간도 1증가
                queue.add(new int[]{time, truck_weights[index]});
                total += truck_weights[index];
                index++;
                time++;
            } else{
                // 가능하지 않으면 현재시간을 다리위의 맨 앞차가 나올 시간으로 순간이동
                if(!queue.isEmpty()){
                    time = queue.peek()[0]+bridge_length;
                }
            }
            
            // 다리에 차가 없으면 모든 차가 다 이동한 것이므로 종료
            if(queue.isEmpty()) break;
            
        }
        
        return time;
        
    }
}