class Solution {
    public int[] solution(int n, int s) {
        
        int x = s/n;
        int y = s%n;
        
        if(x<1) return new int[]{-1};
        
        int[] answer = new int[n];
        for(int i = n-1;i>=0;i--){
            answer[i] = x;
            if(y>0){
                answer[i]++;
                y--;
            }
        }
        
        return answer;
    }
}