import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static char[] word; 
	static int seq;
	static char[] tempArr;
	static int count;
	static boolean find;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String input;
		
		while((input=br.readLine())!=null) {
			
			StringTokenizer st = new StringTokenizer(input);
			
			if(!st.hasMoreTokens()) {
				break;
			}
			
			
			String temp = st.nextToken();
			word = temp.toCharArray();
			N = temp.length();
			seq = Integer.parseInt(st.nextToken());
			tempArr = new char[N];
			count = 0;
			find = false;
			// 입력끝
			
			sb.append(temp + " ");
			sb.append(seq + " = ");
			// 출력 저장

			// 순열 전체 개수 
			int total = 1;
			for(int i = N;i>=1;i--) {
				total *= i;
			}
			
			// 전체개수를 넘는 순번의 순열을 구하라고 하면 안된다고 하기
			if(total<seq) {
				sb.append("No permutation\n");
				continue;
			}
			
			perm(0, 0);
			
			for(int i = 0;i<N;i++) {
				sb.append(tempArr[i]);
			}
			sb.append("\n");

			
		}
		
		System.out.println(sb);
	}
	
	static void perm(int n, int visited) {
		
		if(find) return;
		
		if(n==N) {
			count++;
			if(count == seq) {
				find = true;
			}
			return;
		}
		
		
		for(int i = 0;i<N;i++) {
			if((visited&(1<<i))>0) continue;
			tempArr[n] = word[i];
			perm(n+1, visited|(1<<i));
			if(find) break;
		}
		
	}
}