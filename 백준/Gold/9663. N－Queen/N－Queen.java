import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
	
	// 체스판 크디
	static int N;
	// 놓을 수 없는 곳 체크할 Set
	// 현재 위치를 기준으로 아래 방향에 못놓도록 c 저장
	static boolean[] col2;
	// 현재 위치를 기준으로 오른쪽아래 대각선 방향에 못놓도록 r-c 저장
	static boolean[] rd2;
	// 현재 위치를 기준으로 왼쪽아래 대각선 방향에 못놓도록 r+c 저장
	static boolean[] ld2;
	// 놓을 수 있는 경우의 수
	static int total;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		col2 = new boolean[N];
		rd2 = new boolean[N*2];
		ld2 = new boolean[N*2];
		
		total = 0;
		
		queen(0, 0);
		System.out.println(total);
	}
	
	// 매 행에서 놓을 수 있는 자리 찾기
	static void queen (int count, int r) {
		
		if(count<r) {
			return;
		}
		
		// 행이 끝났을 때 종료
		if(r == N) {
			// 지금까지 놓은 퀸의 수가 N과 같을 때 total 증가
			if(count == N) {
				total++;
			}
			return;
		}
		
		// 모든 열에 대해서 놓을 수 있는지 판단하기
		for(int c = 0;c<N;c++) {
			
			if(col2[c]) continue;
			if(rd2[(r-c)+(N-1)]) continue;
			if(ld2[r+c]) continue;
			
			col2[c] = true;
			rd2[(r-c)+(N-1)] = true;
			ld2[r+c] = true;
			queen(count+1, r+1);
			col2[c] = false;
			rd2[(r-c)+(N-1)] = false;
			ld2[r+c] = false;
			
		}
		
	}
}