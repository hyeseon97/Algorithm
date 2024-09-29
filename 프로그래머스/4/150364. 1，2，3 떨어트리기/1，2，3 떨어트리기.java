import java.util.*;

class Solution {
    
    // 노드 수
    static int N;
    
    // 노드 인접리스트
    static List<List<Integer>> tree;
    
    // 노드가 가르키고 있는 자식 인덱스
    static int[] way;
    
    // 리프노드에 쌓인 숫자 1, 2, 3 별 개수 저장
    // [i]노드가 가지고 있는 [j]숫자 카운트
    static int[][] count;
    
    // 리프노드에 쌓인 숫자 합계
    static int[] sum;
    
    // target의 총 합 -> 이걸로 다 채워졌는지 계산할 것
    static int T;
    
    // target 전역변수로 만들기
    static int[] tg;
    
    // 숫자 떨어뜨렸을 때 도착하는 리프노드 순서 저장
    static List<Integer> seq;
    
    
    public int[] solution(int[][] edges, int[] target) {
        
        N = target.length;
        tree = new ArrayList<>();
        way = new int[N+1];
        count = new int[N+1][4];
        sum = new int[N+1];
        T = 0;
        tg = new int[N+1];
        seq = new ArrayList<>();
        
        
        // 노드 수 만큼 리스트 생성
        for(int i = 0;i<=N;i++){
            tree.add(new ArrayList<>());
        }
        // 간선에 따라 자식노드 저장
        for(int i = 0;i<edges.length;i++){
            int a = edges[i][0];
            int b = edges[i][1];
            tree.get(a).add(b);
        }
        // 자식노드 정렬
        for(int i = 1;i<=N;i++){
            Collections.sort(tree.get(i));
        }
        // target의 총 합, target 전역변수에 저장
        for(int i = 0;i<N;i++){
            T += target[i];
            tg[i+1] = target[i];
        }
        
        
        // T가 0이 될 때까지 숫자 하나씩 떨어뜨리기
        while(T>0){
            // 불가능할 때 바로 -1만 있는 배열 반환
            if(!drop()){
                return new int[]{-1};
            }
        }
        
        // seq에 따라 해당 노드의 1, 2, 3 사전순으로 결과 저장
        List<Integer> answer = new ArrayList<>();
        for(int i = 0;i<seq.size();i++){
            int node = seq.get(i);
            if(count[node][1]>0){
                answer.add(1);
                count[node][1]--;
            } else if(count[node][2]>0){
                answer.add(2);
                count[node][2]--;
            } else if(count[node][3]>0){
                answer.add(3);
                count[node][3]--;
            }
        }
        
        
        for(int i = 0;i<seq.size();i++){
            System.out.print(seq.get(i) + " ");
        }
        
        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
    
    // 불가능할 때 false 반환
    static boolean drop(){
        
        // 1번노드부터 길을 찾아 내려가기
        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        while(!q.isEmpty()){
            int node = q.poll();
            
            // node가 현재 가르키는 자식 노드
            int index = way[node];
            
            // 자식노드 리스트가 비어있거나 사이즈가 0일 때 리프노드 인 것
            if(tree.get(node).isEmpty() || tree.get(node).size()==0){
                // 도착한 노드를 seq에 저장
                seq.add(node);
                // 해당 노드가 target으로 채워지려면 얼마나 남았는가
                int t = tg[node] - sum[node];
                // 숫자를 최소한으로 해야하기 때문에
                // 3이상이면 3저장, 2이상이면 2저장, 1이상이면 1저장
                // 0일때는 count에 2가 있으면 2를 1로 나눠서 저장, 3이 있으면 2와 1로 나눠서 저장
                // 이것도 다 안되면 불가능한 것
                if(t>=3){
                    count[node][3]++;
                    T -= 3;
                    sum[node] += 3;
                } else if(t>=2){
                    count[node][2]++;
                    T -= 2;
                    sum[node] += 2;
                } else if(t>=1){
                    count[node][1]++;
                    T -= 1;
                    sum[node] += 1;
                } else{
                    if(count[node][2]>0){
                        count[node][2]--;
                        count[node][1]+=2;
                    } else if(count[node][3]>0){
                        count[node][3]--;
                        count[node][2]++;
                        count[node][1]++;
                    } else{
                        return false;
                    }
                }
                
                // 리프노드이므로 순회 종료
                break;
            }
            
            // 리프노드가 아니므로 다음 자식노드로 이동하고
            // node의 길 바꿔주기                
            int child = tree.get(node).get(index);
            q.add(child);
            way[node] = (way[node]+1)%tree.get(node).size();
            
        } // while 끝
        
        // 리프노드까지 가서 순회 끝
        return true;
        
        
    }
}