import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {
	
	// 숫자의 갯수
	static int N;
	// 출력할 순번
	static int K;
	// 비밀번호 길이
	static int len;
	// 큐에 넣고 하나씩 뽑아서 비밀번호를 만들고 다시 넣기
	static Queue<Character> queue;
	// 만든 비밀번호를 저장 (중복X)
	static Set<String> set;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			len = N/4;
			queue = new LinkedList<>();
			set = new HashSet<>();
			
			String str = br.readLine();
			for(int i = 0;i<N;i++) {
				queue.add(str.charAt(i));
			}
			
			// len길이의 비밀번호가 될 수 있는 모든 경우를 저장하기
			// len 번만큼 회전
			for(int i = 0;i<len;i++) {
				StringBuilder temp = new StringBuilder();
				// N 번만큼 뽑고 다시 넣기
				for(int j = 0;j<N;j++) {
					
					char ch = queue.poll();
					temp.append(ch);
					queue.add(ch);

					// 숫자들이 len개 모여서 비밀번호를 이루었으면 set에 저장
					if(j%len==len-1) {
						set.add(temp.toString());
						temp = new StringBuilder();
					}
				}
				
				// N의 숫자들을 모두 뽑아서 비밀번호를 만들고 다시 넣었으면 회전하기
				queue.add(queue.poll());
			}
			
			// set에 있는 것들을 list로 옮기기 (정렬하려고)
			List<String> list = new ArrayList<>(set);
			Collections.sort(list);
			// K번째로 큰 수 뽑기 (오름차순정렬이니까 뒤에서 K번째 뽑기)
			String res = list.get(list.size()-K);
			
			// 16진수인 비밀번호를 10진수로 바꿔 저장할 변수
			int num = 0;
			// 16진수->10진수 계산하기 위한 제곱 변수
			int a = 0;
			for(int i = res.length()-1;i>=0;i--) {
				if(res.charAt(i)>='A' && res.charAt(i)<='F') {
					num += Math.pow(16, a++)*(res.charAt(i)-'A'+10);
				} else {
					num += Math.pow(16, a++)*(res.charAt(i)-'0');
				}
			}
			sb.append("#" + t + " " + num + "\n");
			
		}
		System.out.println(sb);
	}
}