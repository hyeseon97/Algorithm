class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = new int[2];
        for(int i = 1;i<=Math.sqrt(yellow);i++){
            if(yellow%i==0){
                int j = yellow/i;
                if(((i+j)*2+4)==brown){
                    answer[0] = j+2;
                    answer[1] = i+2;
                }
            }
        }
        return answer;
    }
}