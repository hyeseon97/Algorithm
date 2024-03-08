import java.util.*;

class Solution {
    
    static int N;
    static int[] answer;
    static Map<Integer, List<Integer>> line;
    static List<Integer> nodeList;
    public int[] solution(int[][] edges) {
        answer = new int[4];
        line = new HashMap<>();
        nodeList = new ArrayList<>();
        
        for(int i = 0;i<edges.length;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            
            N = Math.max(N, u);
            N = Math.max(N, v);
            
            if(line.containsKey(u)){
                line.get(u).add(v);
            } else{
                nodeList.add(u);
                List<Integer> temp = new ArrayList<>();
                temp.add(v);
                line.put(u, temp);
            }
        }
        
        findStart();
        
        findGraph();
		

		return answer;
    }
    
    static void findGraph(){
        
        Set<Integer> visited = new HashSet<>();
        
        
        for(int i = 1;i<=N;i++){
            int start = i;
            if(start == answer[0]) continue;
            if(visited.contains(start)) continue;
            
            Queue<Integer> q = new LinkedList<>();
            q.add(start);
            Set<Integer> v = new HashSet<>();
            v.add(start);
            visited.add(start);
            
            boolean isStart = false;
            boolean duple = false;
            boolean mid = false;
            
            while(!q.isEmpty()){
                int currentNode = q.poll();
                
                if(line.containsKey(currentNode)){
                    
                    if(line.get(currentNode).size()>=2) mid = true;
                    
                    for(int nextNode : line.get(currentNode)){
                        
                        // 시작지점을 만나면 체크만 하고 큐에 넣지 않음
                        if(nextNode == start){
                            isStart = true;
                        } else{ // 시작지점이 아닐때
                            
                            if(visited.contains(nextNode)) duple = true;
                            
                            // 이미 한 번 간곳일때
                            // 1. 중간지점이면(나아갈 수 있는 노드가 2개이상일 때) -> 큐에 넣고 진행
                            // 2. 중간지점 아니면 또 가지마
                            if(v.contains(nextNode)){
                                if(line.containsKey(nextNode) && line.get(nextNode).size()>=2){
                                    q.add(nextNode);
                                    v.add(nextNode);
                                    visited.add(nextNode);
                                }
                            } else {
                                q.add(nextNode);
                                v.add(nextNode);
                                visited.add(nextNode);    
                            }
                            
                        }
                        
                    }
                }
            }
            
            // 시작지점에 도착했을 때 -> 도넛이거나 8
            if(isStart){
                if(mid){
                    answer[3]++;
                } else{
                    answer[1]++;
                }
            } else{
                if(!duple){
                    answer[2]++;
                }
            }
            
        }
        
    }
    
    static void findStart(){
        
        for(int i = 0;i<nodeList.size();i++){
            int node = nodeList.get(i);
            if(line.get(node).size()>=2){
                int start = node;
                
                Queue<Integer> q = new LinkedList<>();
                q.add(start);
                Set<Integer> visited = new HashSet<>();
                visited.add(start);
                
                boolean isStart = false;
                
                while(!q.isEmpty()){
                    int currentNode = q.poll();
                    
                    // 현재 노드에서 나아갈 노드가 있을 때
                    if(line.containsKey(currentNode)){
                        // 나아갈 수 있는 노드들로 가기
                        for(int nextNode : line.get(currentNode)){
                            // 시작 노드로 갔으면 isStart = true로 하기
                            if(nextNode == start){
                                isStart = true;
                            }
                            // 방문하지 않은 곳만 큐에 넣기
                            if(!visited.contains(nextNode)){
                                q.add(nextNode);
                                visited.add(nextNode);
                            }
                        }
                        
                        // 시작 노드를 만났으면 종료하기
                        if(isStart)
                            break;
                    }
                }
                
                // 시작노드를 만나지 않고 종료했을 때 현재 시작 노드가 추가된 노드
                if(!isStart){
                    answer[0] = start;
                    return;
                }
            }
        }
        
    }
}