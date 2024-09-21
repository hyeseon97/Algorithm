class Solution {
    public int solution(int[] food_times, long k) {
        int N = food_times.length;
        
        // 인덱스가 food_times인것 갯수 카운팅
        int[] count = new int[100000001];
        for(int i = 0;i<N;i++){
            count[food_times[i]]++;
        }
        
        // 경과한 시간 -> k를 넘기면 해당 food_times 이상인 것들로 순회해서 찾기
        long time = 0;
        int total = N;
        int foodTime = 0;
        
        for(int i = 1;i<=100000000;i++){
            
            if(time+total >= k+1){
                foodTime = i;
                break;
            }
            
            time += total;
            total -= count[i];
            
            if(total == 0){
                return -1;
            }
        }
        
        for(int i = 0;i<N;i++){
            
            if(food_times[i] >= foodTime){
                time++;
            }
            
            if(time == k+1){
                return i+1;
            }
        }
        
        return -1;
        
    }
}