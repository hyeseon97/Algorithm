class Solution {
    public int solution(int[] stones, int k) {
        
        // 이분탐색으로 건널 수 있는 최대 명 수 찾기
        int min = 1;
        int max = 0;
        // 돌 중 가장 숫자가 높은 돌을 max로 초기화
        for(int i = 0;i<stones.length;i++){
            max = Math.max(max, stones[i]);
        }
        
        // 이분탐색
        // mid보다 작은 돌들이 연속으로 몇개 있는지 카운팅하고
        // k보다 크다면 불가능하기 때문에 max를 줄이기
        // 불가능한 경우없이 모든 돌을 순회했으면 최대로 가능한 경우를 찾기 위해 min을 늘리기
        int mid = (min+max)/2;
        W : while(min<=max){
            
            mid = (min+max)/2;
            int count = 0;
            for(int i = 0;i<stones.length;i++){
                if(stones[i] < mid) {
                    count++;
                } else{
                    count = 0;
                }
                
                if(count > (k-1)){
                    max = mid-1;
                    continue W;
                }
            }
            min = mid+1;
        }
        
        
        int count = 0;
        for(int i = 0;i<stones.length;i++){
            if(stones[i] < mid) {
                count++;
            } else{
                count = 0;
            }
                
            if(count > (k-1)){
                max = mid-1;
                return mid-1;
            }
        }
        
        return mid;
    }
}