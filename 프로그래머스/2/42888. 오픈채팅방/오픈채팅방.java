import java.util.*;

class Solution {
    public String[] solution(String[] record) {
        
        // 유저아이디로 닉네임 저장
        Map<String, String> user = new HashMap<>();
        // 0인덱스에 유저아이디와 1인덱스에 입장 or 퇴장 정보 저장
        List<String[]> message = new ArrayList<>();
        
        // record 순회하면서 메세지 기록
        for(int i = 0;i<record.length;i++){
            StringTokenizer st = new StringTokenizer(record[i]);
            String info = st.nextToken();
            
            if(info.equals("Enter")){
                String userId = st.nextToken();
                String nickname = st.nextToken();
                user.put(userId, nickname);
                message.add(new String[]{userId, "님이 들어왔습니다."});
            } else if(info.equals("Leave")){
                String userId = st.nextToken();
                message.add(new String[]{userId, "님이 나갔습니다."});
            } else if(info.equals("Change")){
                String userId = st.nextToken();
                String nickname = st.nextToken();
                user.put(userId, nickname);
            }
        } // record 순회 끝
        
        // 기록되어 있는 메세지 출력
        String[] answer = new String[message.size()];
        for(int i = 0;i<message.size();i++){
            answer[i] = user.get(message.get(i)[0]) + message.get(i)[1];
        }
        
        return answer;
    }
}