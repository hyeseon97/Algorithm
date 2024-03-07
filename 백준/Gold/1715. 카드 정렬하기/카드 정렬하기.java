import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		// 가장 작은 카드 묶음 두개를 더해나가는 것이 가장 적게 비교하는 방법
		// 비교해서 합친 카드묶음을 다시 넣고 또 가장 작은 카드 묶음 두개를 꺼내는 것이 반복되므로 우선순위큐 사용
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for(int i = 0;i<N;i++) {
			pq.add(Integer.parseInt(br.readLine()));
		}
		int total = 0;
		while(pq.size()>1) {
			// 두개 뽑아서 더한것을
			// 1. total에 더하고
			// 2. pq에 다시 넣기
			int A = pq.poll();
			int B = pq.poll();
			int sum = A+B;
			total += sum;
			pq.add(sum);
		}
		System.out.println(total);
	}

}