import java.util.*;

class Solution {
    
    static int N;
    static int M;
    static int X;
    static int Y;
    static int R;
    static int C;
    static int K;
    
    // 시작->도착 최단거리에 필요한 명령어 별 개수 저장
    static Map<String, Integer> map;
    
    // 실시간 명령어 사용한 갯수
    static int count;
    
    // 명령어 순서 리스트
    static List<String> result;
    
    // 명령어 문자열로
    static StringBuilder sb;
    
    
    // d, l, r, u
    static int[] dr = {1, 0, 0, -1};
    static int[] dc = {0, -1, 1, 0};
    static String[] dir = {"d", "l", "r", "u"};
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        
        // 전역변수로 저장
        N = n; M = m; X = x; Y = y; R = r; C = c; K = k;
        // 최단거리에 필요한 명령어별 개수 저장
        map = new HashMap<>();
        count = find();
        
        // 최단거리 명령어 개수와 k의 값이 서로 같은 짝수이거나 같은 홀수가 아니면 불가능
        // 최단거리 명령어 개수가 k보다 크면 불가능
        if((count%2 != k%2) || count>k) return "impossible";
        
        // 사전순에 따라 d, l 방향으로 갈 수 있는 최대한의 경우 구하기
        // 단 총 명령어 k 이내로
        gogo();
        
        result = new ArrayList<>();
        for(int d = 0;d<4;d++){
            for(int i = 0;i<map.get(dir[d]);i++){
                result.add(dir[d]);
            }
        }
        
        sb = new StringBuilder();
        
        // 그럼 이제 최대한 돌아서 가는데 사전순으로 갈 수 있는 길 찾은 것
        // 그래도 남은 명령어 개수만큼 rl 반복
        rere();
        
        return sb.toString();
        
    }
    
    // 메서드 -----------------------------------------------------------
    
    // 반복 타임
    // 남은 명령어 개수만큼은 rl 반복
    static void rere(){
        
        boolean repeat = false;
        int recount = 0;
 
        if(map.get("r")>0){
            for(int i = 0;i<result.size();i++){
                            
                if(result.get(i).equals("r") && !repeat){
                    for(int j = 0;j<(K-count);j+=2){
                        sb.append("rl");
                    }
                    repeat = true;
                }
                sb.append(result.get(i));
            }
        }
        
        else{
            for(int i = 0;i<result.size();i++){
                sb.append(result.get(i));    
                if(result.get(i).equals("l")){
                    recount++;
                }
                if(recount==map.get("l") && !repeat){
                    for(int j = 0;j<(K-count);j+=2){
                        sb.append("rl");
                    }
                    repeat = true;
                }
            }
        }
        
        
        
    }
    
    // d, l 방향으로 얼마나 더 갈 수 있는지 
    static void gogo(){
        
        // 시작&도착 중 더 밑에 있는것 중에서 d로 얼마나 갈 수 있는가
        int lengthD = N-(Math.max(X, R));
        // K-count/2 남은 명령어를 쓸 수 있는 길이와 lengthD 갈 수 있는 길이 비교해서 최대한 가기
        lengthD = Math.min((K-count)/2, lengthD);
        map.put("d", map.get("d")+lengthD);
        map.put("u", map.get("u")+lengthD);
        count += lengthD*2;
        
        
        // 시작&도착 중 더 왼쪽에 있는 것 중에서 L로 얼마나 갈 수 있는가
        int lengthL = Math.min(Y, C)-1;
        // K-count/2 남은 명령어를 쓸 수 있는 길이와 lengthL 갈 수 있는 길이 비교해서 최대한 가기
        lengthL = Math.min((K-count)/2, lengthL);
        map.put("l", map.get("l")+lengthL);
        map.put("r", map.get("r")+lengthL);
        count += lengthL*2;
        
    }
    
    
    
    // 최단거리 찾는 메서드
    // 리턴값은 최단거리에 필요한 명령어 수
    static int find(){
        map.put("d", 0);
        map.put("l", 0);
        map.put("r", 0);
        map.put("u", 0);
        
        // 시작점좌표 도착점좌표 비교해서 최단거리 경로 찾기
        int ud = R-X;
        int lr = C-Y;
        
        if(ud>0){
            map.put("d", ud);
        } else{
            map.put("u", -ud);
        }
        
        if(lr>0){
            map.put("r", lr);
        } else{
            map.put("l", -lr);
        }
        
        return Math.abs(ud)+Math.abs(lr);
        
    }
    
}