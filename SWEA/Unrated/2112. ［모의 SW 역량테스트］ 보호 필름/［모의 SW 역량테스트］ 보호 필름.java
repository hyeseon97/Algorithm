import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	// 두께, 가로크기, 합격기준
	static int D;
	static int W;
	static int K;
	
	// 보호필름
	static int[][] arr;
	static int[][] newArr;
	
	static boolean pass;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			D = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			arr = new int[D][W];
			pass = false;
			
			for(int i = 0;i<D;i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0;j<W;j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			for(int i = 0;i<=D;i++) {
				index = new int[i];
				combi(0, 0);
				if(pass) {
					sb.append("#" + t + " " + i + "\n");
					break;
				}
			}
			
			
		}
		
		System.out.println(sb);
	}
	
	// D개중에 (0~D)개 뽑기
	static int[] index;
	static void combi(int idx, int visited) {
		
		if(pass) return;
		
		if(idx == index.length) {
			value = new int[index.length];
			AB(0);
			return;
		}
		
		for(int i = 0;i<D;i++) {
			if((visited&(1<<i))>0) continue;
			if(idx>0 && index[idx-1]>i) continue;
			index[idx] = i;
			combi(idx+1, visited|(1<<i));
		}
	}
	
	// (0~D)개 뽑은것을 A또는 B로 배정하기
	static int[] value;
	static void AB(int idx) {
		
		if(pass) return;
		
		if(idx == value.length) {
//			newArr = new int[D][W];
//			for(int i = 0;i<D;i++) {
//				newArr[i] = arr[i].clone();
//			}
//			for(int i = 0;i<value.length;i++) {
//				for(int j = 0;j<W;j++) {
//					newArr[index[i]][j] = value[i];
//				}
//			}
//			
			if(test()) pass = true;
			
			return;
		}
		
		value[idx] = 0;
		AB(idx+1);
		value[idx] = 1;
		AB(idx+1);
		
	}
	
	static boolean test() {
		for(int i = 0;i<W;i++) {
			int idx = 0;
			int count = 0;
			int num = 0;
			for(int j = 0;j<D;j++) {
				
				int sel = arr[j][i];
				if(index.length>0 && idx<index.length && j==index[idx]) {
					sel = value[idx];
					idx++;
				}
				
				if(j==0) {
					count = 1;
					num = sel;
					continue;
				}
				
				if(sel == num) {
					count++;
				} else {
					num = sel;
					count = 1;
				}
				
				if(count == K) {
					break;
				}
			}
			
			if(count != K) {
				return false;
			}
		}
		
		return true;
	}
	
	
}