import java.util.*;

class Solution {
    
    // 시작공항을 key로 도착공항을 알파벳순으로 정렬해 저장
    static Map<String, List<String>> airplane;
    
    // 방문 체크 여부도 저장
    static Map<String, List<Boolean>> visited;
    
    // 사용해야할 티켓 수
    static int T;
    
    // 여행 경로 저장
    static LinkedList<String> route;
    
    // 결과 저장
    static boolean success;
    static String[] result;
    
    public String[] solution(String[][] tickets) {
        
        airplane = new HashMap<>();
        visited = new HashMap<>();
        T = tickets.length;
        success = false;
        result = new String[T+1];
        
        // airplane에 저장
        for(int i = 0;i<tickets.length;i++){
            String start = tickets[i][0];
            String end = tickets[i][1];
            if(!airplane.containsKey(start)){
                airplane.put(start, new ArrayList<>());
                visited.put(start, new ArrayList<>());
            }
            airplane.get(start).add(end);
            visited.get(start).add(false);
        }
        
        // 각 시작공항에서 도착공항들을 정렬
        Iterator<String> iterator = airplane.keySet().iterator();
        while(iterator.hasNext()){
            String city = iterator.next();
            Collections.sort(airplane.get(city));
        }
        
        // 인천을 시작으로 여행 시작
        route = new LinkedList<>();
        route.addLast("ICN");
        travel(0, "ICN");
                
        return result;
        
    }
    
    static void travel(int t, String now){
        
        // 첫 경로 찾았으면 바로 리턴
        if(success) return;
        
        // 경로 찾았을 때
        if(t==T){
            result = route.toArray(new String[route.size()]);
            success = true;
            return;
        }
        
        // 현재 공항에서 시작하는 티켓이 없으면 리턴
        if(!airplane.containsKey(now)) return;
        
        // 현재 공항에서 시작하는 티켓들 순회
        for(int i = 0;i<airplane.get(now).size();i++){
            // 이미 해당 티켓 사용했으면 건너뛰기
            if(visited.get(now).get(i)) continue;
            
            // 도착 공항으로 dfs 이어나가기
            String next = airplane.get(now).get(i);
            route.addLast(next);
            visited.get(now).set(i, true);
            travel(t+1, next);
            route.pollLast();
            visited.get(now).set(i, false);
        }
        
    }
}