import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		// 투포인터 사용
		int start = 1;
		int end = 1;
		// start~end 까지 더한 값 = 1
		int sum = 1;
		// 갯수세기
		int count = 0;
		// end가 N까지 갔다는 것은 더 이상 연속된 합이 N인 것을 찾을 수가 없음
		while(end<=N) {
			// sum이 작을 땐 end값을 늘려 더 큰 수를 더해준다
			if(sum<N) {
				end++;
				sum += end;
			} else if(sum>N) { // sum이 클 땐 start 값을 늘려 작은 값을 빼준다
				sum -= start;
				start++;
			} else { // 같을 땐 카운트 하고 start, end 한칸씩 이동하기
				count++;
				end++;
				sum = sum + end - start;
				start++;
			}
		}
		System.out.println(count);
	}
}
