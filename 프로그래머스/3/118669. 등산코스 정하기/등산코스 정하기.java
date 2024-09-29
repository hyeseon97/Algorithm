import java.util.*;

class Solution {
    
    // 지점 수
    static int N;
    
    // 산의 지점 간 등산로 정보
    static List<List<int[]>> line;
    
    // 산봉우리 집합
    static Set<Integer> top;
    // 출발점 집합
    static Set<Integer> start;
    
    // 출입구마다 다익스트라로 산봉우리에 도착해서 비교하고 갱신
    static int intensity;
    static int topNum;
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        
        N = n;
        line = new ArrayList<>();
        for(int i = 0;i<=n;i++){
            line.add(new ArrayList<>());
        }
        for(int i = 0;i<paths.length;i++){
            int a = paths[i][0];
            int b = paths[i][1];
            int time = paths[i][2];
            line.get(a).add(new int[]{b, time});
            line.get(b).add(new int[]{a, time});
        }
        top = new HashSet<>();
        for(int i = 0;i<summits.length;i++){
            top.add(summits[i]);
        }
        start = new HashSet<>();
        for(int i = 0;i<gates.length;i++){
            start.add(gates[i]);
        }
        intensity = 10000001;
        topNum = 0;
        
        // bfs 시행
        bfs(gates);
        
        int[] answer = new int[2];
        answer[0] = topNum;
        answer[1] = intensity;
        
        return answer;
    }
    
    static void bfs(int[] gates){
        
        // 가장 작은 intensity인 것
        int inten = 10000001;
        
        int[] dij = new int[N+1];
        boolean[] visited = new boolean[N+1];
        
        PriorityQueue<int[]> q = new PriorityQueue<>(new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2){
                if(o1[1]==o2[1]){
                    return o1[0]-o2[0];
                }
                return o1[1]-o2[1];
            }
        });
        
        // 모든 출입구 넣고 bfs 시작
        for(int i = 0;i<gates.length;i++){
            q.add(new int[]{gates[i], 0});
        }
        
        while(!q.isEmpty()){
            int[] now = q.poll();
            int node = now[0];
            int max = now[1];
            visited[node] = true;
            
            // 산봉우리에 도착했을 때
            // intensity가 작은것부터 꺼내오니까 intensity는 정해졌고
            // 그 이후에 더 작은 봉우리가 나올 수 있으니까 같은 intensity에 대해서는 계속 뽑기
            if(top.contains(node)) {
                if(max < intensity){
                    intensity = max;
                    topNum = node;
                } else if(intensity == max){
                    if(node < topNum){
                        topNum = node;
                    }
                } else{
                    break;
                }
                continue;
            }
            
            if(max > intensity){
                break;
            }
            
            for(int i = 0;i<line.get(node).size();i++){
                int[] temp = line.get(node).get(i);
                int next = temp[0];
                int time = temp[1];
                
                if(visited[next] || start.contains(next)) continue;
                if(dij[next] != 0 && time > dij[next]) continue;
                q.add(new int[]{next, Math.max(max, time)});
                dij[next] = time;
                
            }
        }
    }
}