import java.util.*;

class Solution {
    
    class Node{
        int prev;
        int next;
        
        Node(int prev, int next){
            this.prev = prev;
            this.next = next;
        }
    }
    
    public String solution(int n, int k, String[] cmd) {
        
        // 실제로 표 내용이 주어지지 않기 때문에 n개의 행에 대해서
        // 0번부터 n-1번까지 임의의 번호가 이름이라고 생각하고 문제 풀기
        // 삭제와 복구가 빈번히 일어나므로 LinkedList 사용
        // 삭제한 것은 최근 삭제된것부터 복구될 수 있도록 Stack에 저장
        Node[] nodeList = new Node[n];
        List<Integer> table = new ArrayList<>();
        for(int i = 0;i<n;i++){
            table.add(i);
            nodeList[i] = new Node(i-1, i+1);
        }
        
        // 삭제된 행 체크
        boolean[] remove = new boolean[n];
        // 삭제된 행 저장
        Stack<int[]> trash = new Stack<>();
        
        // 명령어 순회
        for(int c = 0;c<cmd.length;c++){
            StringTokenizer st = new StringTokenizer(cmd[c]);
            String command = st.nextToken();
            
            if(command.equals("U")){
                int x = Integer.parseInt(st.nextToken());
                // x만큼 이전 노드로 이동
                for(int i = 0;i<x;i++){
                    k = nodeList[k].prev;
                }
            } else if(command.equals("D")){
                int x = Integer.parseInt(st.nextToken());
                // x만큼 다음 노드로 이동
                for(int i = 0;i<x;i++){
                    k = nodeList[k].next;
                }
            } else if(command.equals("C")){
                // 현재 노드 삭제 체크하고 저장
                remove[k] = true;
                int prev = nodeList[k].prev;
                int next = nodeList[k].next;
                trash.push(new int[]{k, prev, next});
                
                // 이전노드와 다음노드를 연결
                if(prev!=-1) nodeList[prev].next = next;
                if(next!=n) nodeList[next].prev = prev;
                k = next;
                
                // 마지막 노드를 삭제했을 때는 현재 행을 이전노드로
                if(k == n){
                    k = prev;
                }
            } else if(command.equals("Z")){
                // 삭제된 노드 꺼내고 체크 해제
                int[] temp = trash.pop();
                int idx = temp[0];
                int prev = temp[1];
                int next = temp[2];
                remove[idx] = false;
                
                // 이전노드와 다음노드를 현재 노드로 연결
                if(prev!=-1) nodeList[prev].next = idx;
                if(next!=n) nodeList[next].prev = idx;
            }
            
        }
        
        StringBuilder answer = new StringBuilder();
        for(int i = 0;i<n;i++){
            if(remove[i]) answer.append("X");
            else answer.append("O");
        }
        
        return answer.toString();
    }
}