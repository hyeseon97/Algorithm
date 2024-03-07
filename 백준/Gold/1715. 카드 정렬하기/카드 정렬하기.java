import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

// 카드 정렬하기
public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		// 우선순위 큐로 사용
		// 데이터의 삽입, 삭제, 정렬이 자주 일어나기 때문
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for(int i = 0;i<N;i++) {
			// 요소 삽입
			pq.add(Integer.parseInt(br.readLine()));
		}
		// 비교횟수 변수
		int result = 0;
		// 묶음이 1개 남을 때 까지
		while(pq.size()>1) {
			// 가장 작은 값 두개 꺼내서
			int x = pq.poll();
			int y = pq.poll();
			// 더하고 저장
			int sum = x+y;
			// 결과에 더하기
			result += sum;
			// 큐에 다시 넣기
			pq.add(sum);
		}
		System.out.println(result);
	}
}