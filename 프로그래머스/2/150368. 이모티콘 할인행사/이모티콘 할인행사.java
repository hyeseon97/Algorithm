class Solution {
    public int[] solution(int[][] Users, int[] Emoticons) {
        int[] answer = new int[2];
        maxUser = 0;
        maxAmount = 0;
        
        U = Users.length;
        users = new int[U][2];
        for(int i = 0;i<U;i++){
            users[i] = Users[i];
        }
        E = Emoticons.length;
        emoticons = Emoticons;
        sale = new int[E][2];
        every(0);
        answer[0] = maxUser;
        answer[1] = maxAmount;
        return answer;
    }
    
    // 사용자수와 사용자
    static int U;
    static int[][] users;
    // 이모티콘수 이모티콘
    static int E;
    static int[] emoticons;
    // 할인율과 가격
    static int[][] sale;
    
    // 최대서비스가입자, 최대판매액
    static int maxUser;
    static int maxAmount;
    
    static void cal(){
        
        // 이모티콘 판매액        
        int totalAmount = 0;
        // 이모티콘 서비스 가입자
        int count = 0;
        
        // 모든 사용자들을 반복하면서 어떤 이모티콘을 사고 서비스에 가입할지 계산해보기
        for(int i = 0;i<U;i++){
            int amount = 0;
            for(int j = 0;j<E;j++){
                if(sale[j][0]>=users[i][0]){
                    amount += sale[j][1];
                }
            }
            if(amount>=users[i][1]){
                count++;
            } else{
                totalAmount += amount;
            }
        }
        
        // 최대값 갱신
        if(maxUser<count){
            maxUser = count;
            maxAmount = totalAmount;
        } else if(maxUser == count){
            maxAmount = Math.max(maxAmount, totalAmount);
        }
        
    }
    
    static void every(int idx){
        
        if(idx == E){
            cal();
            return;
        }
        
        // 할인율 10%부터 40%까지 중복 가능 모든 순열 계산
        for(int i = 10;i<=40;i+=10){
            sale[idx][0] = i;
            sale[idx][1] = emoticons[idx] - (emoticons[idx] * i / 100);
            every(idx+1);
        }
    }
}