import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int K;
	static char[] arr;
	static int[] number;
	
	// 순열이 생성되는 순서가 0부터 채워지는 순서이기 때문에
	// 가장 먼저 생성되는 순열이 가장 작은숫자
	// 가장 나중에 생성되는 순열이 가장 큰 숫자
	// count가 0일때 min을 갱신하고, max는 계속 갱신
	static int count;
	static String min;
	static String max;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		K = Integer.parseInt(br.readLine());
		arr = new char[K];
		number = new int[K+1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0;i<K;i++) {
			arr[i] = st.nextToken().charAt(0);
		} // 입력완료
		
		count = 0;
		perm(0, 0);
		
		System.out.println(max);
		System.out.println(min);
		
	}
	
	
	static void perm(int k, int visited) {
		
		if(k==K+1) {
			// 숫자 배열을 문자열로
			StringBuilder sb = new StringBuilder();
			for(int i = 0;i<=K;i++) {
				sb.append(number[i]);
			}
			
			// min과 max를 저장
			if(count == 0) {
				min = sb.toString();
			}
			max = sb.toString();
			
			count++;
			return;
		}
		
		for(int i = 0;i<=9;i++) {
			// 기본 순열 만드는 조건
			if((visited&(1<<i))>0)continue;
			
			// 부등호에 따른 백트래킹(?)
			// 첫번째 숫자는 건너뛰고 두번째 숫자부터 바로 전숫자랑 부등호 비교하자
			if(k>0) {
				// 부등호 < 일때 전 숫자보다 현재 숫자가 작으면 건너뛰기
				if(arr[k-1]=='<') {
					if(number[k-1]>i) continue;
				} else {
					// 부등호 > 일때 전 숫자보다 현재 숫자가 크면 건너뛰기
					if(number[k-1]<i) continue;
				}
			}
		
			// 현재 숫자 저장하고 다음으로~
			number[k] = i;
			perm(k+1, visited|(1<<i));
		}
			
	}
	
}