import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	// 이용권별 가격
	static int[] cost;
	// 이용계획
	static int[] plan;
	// 이용가격 계산
	static int total;
	// 이용가격 최소값
	static int min;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			cost = new int[4];
			plan = new int[13];
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i = 0;i<4;i++) {
				cost[i] = Integer.parseInt(st.nextToken());
			}
			
			st = new StringTokenizer(br.readLine());
			for(int i = 1;i<=12;i++) {
				plan[i] = Integer.parseInt(st.nextToken());
			}
			
			// 초기 min값을 1년이용권으로 정해두고 시작하기
			min = cost[3];
			total = 0;
			cal(1);
			
			sb.append("#" + t + " " + min + "\n");
		}
		System.out.println(sb);
	}
	
	static void cal(int month) {
		
		if(month >= 13) {
			min = Math.min(min, total);
			return;
		}
		
		if(min<total) {
			return;
		}
		
		// 현재 달을 1일이용권으로 하기
		int sum = plan[month]*cost[0];
		total += sum;
		cal(month+1);
		total -= sum;
		
		// 현재 달을 1달이용권으로 하기
		total += cost[1];
		cal(month+1);
		total -= cost[1];
		
		// 현재 달을 3달이용권으로 하기
		total += cost[2];
		cal(month+3); // 3달이용권했으니까 3달이후부터 계산 이어가기
		total -= cost[2];
		
	}
}