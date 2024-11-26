import java.util.*;
import java.io.*;

public class Main {
	
	// 톱니바퀴 클래스
	static class Wheel{
		// 첫상태
		int[] magnet;
		// 12시방향
		int top;
		// 9시방향
		int left;
		// 3시방향
		int right;
	}
	
	// 톱니바퀴 배열
	static Wheel[] wheelList;
	
	// 회전 횟수
	static int N;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		wheelList = new Wheel[4];
		for(int i = 0;i<4;i++) {
			wheelList[i] = new Wheel();
			
			String str = br.readLine();
			int[] temp = new int[8];
			for(int j = 0;j<8;j++) {
				temp[j] = str.charAt(j) - '0';
			}
			wheelList[i].magnet = temp;
			wheelList[i].top = 0;
			wheelList[i].left = 6;
			wheelList[i].right = 2;
		}
		N = Integer.parseInt(br.readLine());
		for(int i = 0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int num = Integer.parseInt(st.nextToken())-1;
			int dir = Integer.parseInt(st.nextToken());
			
			// num의 왼쪽 톱니바퀴들 회전
			leftRotation(num-1, dir*-1);
			// num의 오른쪽 톱니바퀴들 회전
			rightRotation(num+1, dir*-1);
			// num 톱니바퀴 회전
			rotation(num, dir);				
		
		}
		
		int result = 0;
		for(int i = 0;i<4;i++) {
			if(wheelList[i].magnet[wheelList[i].top] == 1) {
				result += (1<<i);
			}
		}
		System.out.println(result);
		
	}
	
	static void rotation(int num, int dir) {
		if(dir == 1) {
			wheelList[num].top = wheelList[num].top-1==-1 ? 7 : wheelList[num].top-1;
			wheelList[num].left = wheelList[num].left-1==-1 ? 7 : wheelList[num].left-1;
			wheelList[num].right = wheelList[num].right-1==-1 ? 7 : wheelList[num].right-1;
		} else {
			wheelList[num].top = (wheelList[num].top+1)%8;
			wheelList[num].left = (wheelList[num].left+1)%8;
			wheelList[num].right = (wheelList[num].right+1)%8;
		}
	}
	
	static void leftRotation(int num, int dir) {
		
		// 가장 왼쪽까지 다 갔거나 오른쪽과 극이 같으면 본인은 회전하지 않으므로 리턴
		if(num == -1 || wheelList[num].magnet[wheelList[num].right] == wheelList[num+1].magnet[wheelList[num+1].left]) {
			return;
		}
		
		// DFS로 왼쪽에 있는 톱니바퀴 먼저 회전하고 그 다음 num 회전
		leftRotation(num-1, dir*-1);
		
		rotation(num, dir);
		
	}

	static void rightRotation(int num, int dir) {
		
		// 가장 오른쪽까지 다 갔거나 왼쪽과 극이 같으면 본인은 회전하지 않으므로 리턴
		if(num == 4 || wheelList[num-1].magnet[wheelList[num-1].right] == wheelList[num].magnet[wheelList[num].left]) {
			return;
		}
		
		// DFS로 왼쪽에 있는 톱니바퀴 먼저 회전하고 그 다음 num 회전
		rightRotation(num+1, dir*-1);
		
		rotation(num, dir);
		
		
	}

}