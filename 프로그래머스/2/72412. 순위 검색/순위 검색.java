import java.util.*;

class Solution {
    
    // 항목별 모든 경우를 key, 해당 경우를 가지고 있는 지원자 점수를 List를 value
    // key는 항목 사이를 띄어쓰기로 구분하는 문자열로 저장
    static Map<String, List<Integer>> developer;
    
    // 현재 쿼리
    static String[] Q;
    
    // 결과
    static int[] answer;
    
    // 항목별 선택지
    static String[][] list = {{"cpp", "java", "python", "-"},
                              {"backend", "frontend", "-"},
                              {"junior", "senior", "-"},
                              {"chicken", "pizza", "-"}};
    
    public int[] solution(String[] info, String[] query) {
        
        // key에 항목별 모든 경우를 저장하도록 초기화
        developer = new HashMap<>();
        init(0, "");
        
        // 모든 경우에 따라 해당하는 지원자를 저장
        for(int i = 0;i<info.length;i++){
            
            StringTokenizer st = new StringTokenizer(info[i]);
            Q = new String[4];
            int s = 0;
            for(int j = 0;j<5;j++){
                if(j<4){
                    Q[j] = st.nextToken();
                } else{
                    s = Integer.parseInt(st.nextToken());
                }
            }
            
            // 항목별로 선택지 또는 - 로 저장하는 두 가지 경우 모두 저장
            save(0, "", s);
            
        } // 지원자 순회 끝
        
        answer = new int[query.length];
        
        List<String> temp = new ArrayList<>(developer.keySet());
        for(int i = 0;i<temp.size();i++){
            Collections.sort(developer.get(temp.get(i)));
        }
        
        // 쿼리에 해당하는 지원자 명수 계산
        for(int i = 0;i<query.length;i++){
            StringTokenizer st = new StringTokenizer(query[i]);
            String str = "";
            Q = new String[4];
            for(int j = 0;j<4;j++){
                str += st.nextToken()+" ";
                if(j<3) st.nextToken();
            }
            int s = Integer.parseInt(st.nextToken());
            int count = find(str, s);
            answer[i] += count;
            
        }
        
        return answer;
    }
    
    
    // 이분탐색으로 원하는 점수를 찾기
    // 점수순으로 정렬했고 가장 앞에 위치하는 원하는 점수 찾기
    static int find(String str, int s){
        
        if(developer.get(str).isEmpty() || developer.get(str).size()==0){
            return 0;
        }
        
        int min = 0;
        int max = developer.get(str).size()-1;
        int mid = (min+max)/2;
        while(min<=max){
            mid = (min+max)/2;
            if(developer.get(str).get(mid) < s){
                min = mid+1;
            } else{
                max = mid-1;
            }
        }
        
        if(developer.get(str).get(mid) < s){
            return developer.get(str).size()-mid-1;
        }
        return developer.get(str).size()-mid;
        
    }
    
    // 지원자의 정보를 저장하는데 각 항목을 포함 or - 으로 두가지 경우 저장
    static void save(int idx, String str, int s){
        
        if(idx == 4){
            developer.get(str).add(s);
            return;
        }
        
        save(idx+1, str+Q[idx]+" ", s);
        save(idx+1, str+"- ", s);
    }
    
    // 모든 경우에 해당하는 쿼리를 저장하여 초기화
    static void init(int idx, String str){
        
        if(idx == 4){
            developer.put(str, new ArrayList<>());
            return;
        }
        
        for(int j = 0;j<list[idx].length;j++){
            init(idx+1, str+list[idx][j]+" ");
        }
        
    }
}