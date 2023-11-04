import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static ArrayList<ArrayList<Integer>> list;
	static long[] count;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        list = new ArrayList<>();
        count = new long[N+1];
        
        for(int i = 0;i<=N;i++) {
        	list.add(new ArrayList<>());
        }
        
        for(int A = 2;A<=N;A++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char ch = st.nextToken().charAt(0);
            long cnt = Long.parseLong(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            // 이동할 방향과 반대로 저장
            // A에서 B로 이동 -> B에서 A로 이어진 것으로 저장
            list.get(B).add(A);
            
            // 양이면 +로, 늑대면 -로 저장
            if(ch=='S') {
            	count[A] = cnt;
            } else {
            	count[A] = -cnt;
            }
        }
        
        DFS(1);
        System.out.println(count[1]);
        
        
    }
    
    // 후위순회로 자식노드들을 먼저 다 계산해주고 난 뒤
    // 늑대인 자식노드는 빼고 양인것만 현재 노드로 이동하고
    // 현재노드가 양인지 늑대인지에 따라 계산해준 뒤 현재 노드에 다시 저장하기
    static void DFS(int node) {
    	
    	// DFS로 자식노드들 먼저 다 방문하기
    	for(int next : list.get(node)) {
    		DFS(next);
    	}
    	
    	// 방문 끝난 뒤 이제 계산해주기
    	// 자식노드가 있을 때
    	if(list.get(node).size()>0) {
    		// 현재노드를 저장하고
    		long sum = count[node];
    		// 자식노드가 양일때 더해주기
    		for(int i = 0;i<list.get(node).size();i++) {
    			if(count[list.get(node).get(i)]>0) {
    				sum += count[list.get(node).get(i)];
    			}
    		}
    		// 계산한 값 현재노드에 저장
    		count[node] = sum;
    	}
    }
    
}