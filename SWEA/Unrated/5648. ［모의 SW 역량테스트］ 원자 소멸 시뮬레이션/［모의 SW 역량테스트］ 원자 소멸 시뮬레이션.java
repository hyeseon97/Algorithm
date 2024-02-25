import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution {

	/** 1. 원자가 다른 원자들과 부딪힐지 알아보기
	 *     이동방향이 상-하 이면서 x좌표가 같거나 좌-우 이면서 y좌표ㄱ 같으면 부딪히는 것
	 *     이동방향이 수직으로 만나면 두 원자의 이동방향의 교점으로부터 각 원자까지의 거리가 같으면 부딪히는것
	 *     교점은 이동방향이 상or하 이면 교점의 x를 가지고 있고, 좌or우 이면 교점의 y를 가지고 있다
	 *  2. 부딪힌다면 우선순위큐에 부딪힐 원자쌍과 시간을 저장하기 (우선순위 큐는 시간으로 재정렬)
	 *  3. 위의 과정을 모든 원자가 거치기
	 *  4. 우선순위큐에서 하나씩 빼면서 소멸시키거나 건너뛰기
	 *     원자쌍 중 하나라도 소멸되었으면 나머지는 소멸될 수 없음
	 */
	
	// 원자 수
	static int N;
	// 원자 정보
	static List<int[]> energy;
	// 부딪히는 쌍 저장
	static PriorityQueue<Object[]> pq;
	// 방출되는 에너지 합
	static int count;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			N = Integer.parseInt(br.readLine());
			energy = new ArrayList<>();
			for(int i = 0;i<N;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				energy.add(new int[] {x, y, d, e});
			} // 입력 완료
			
			// 소멸시간순으로 재정렬
			pq = new PriorityQueue<Object[]>(new Comparator<Object[]>() {
				@Override
				public int compare(Object[] o1, Object[] o2) {
					if((double)o1[2]<(double)o2[2]) {
						return -1;
					} else {
						return 1;
					}
				}
			});
			
			count = 0;

			for(int i = 0;i<N;i++) {
				for(int j = 0;j<N;j++) {
					if(i>=j) continue;
					
					int x1 = energy.get(i)[0];
					int y1 = energy.get(i)[1];
					int d1 = energy.get(i)[2];
					int e1 = energy.get(i)[3];

					int x2 = energy.get(j)[0];
					int y2 = energy.get(j)[1];
					int d2 = energy.get(j)[2];
					int e2 = energy.get(j)[3];
					
					// 같은 방향이면 소멸안됨
					if(d1==d2) continue;
					
					// 원자쌍들의 방향에 따라 조건이 맞으면 소멸
					if(d1==0) {

						if(d2==1 && x1==x2 && y1<y2) {
							double time = Math.abs(y1-y2)/2.0;
							pq.add(new Object[] {i, j, time});
						}
						
						if(d2==2 && x1<x2 && y1<y2 && Math.abs(y1-y2)==Math.abs(x1-x2)) {
							double time = Math.abs(y1-y2);
							pq.add(new Object[] {i, j, time});
						}
						
						if(d2==3 && x1>x2 && y1<y2 && Math.abs(y1-y2)==Math.abs(x1-x2)) {
							double time = Math.abs(y1-y2);
							pq.add(new Object[] {i, j, time});
						}
						
					} else if(d1==1) {
						
						if(d2==0 && x1==x2 && y1>y2) {
							double time = Math.abs(y1-y2)/2.0;
							pq.add(new Object[] {i, j, time});
						}
						
						if(d2==2 && x1<x2 && y1>y2 && Math.abs(y1-y2)==Math.abs(x1-x2)) {
							double time = Math.abs(y1-y2);
							pq.add(new Object[] {i, j, time});
						}
						
						if(d2==3 && x1>x2 && y1>y2 && Math.abs(y1-y2)==Math.abs(x1-x2)) {
							double time = Math.abs(y1-y2);
							pq.add(new Object[] {i, j, time});
						}
						
					} else if(d1==2) {
						
						if(d2==3 && y1==y2 && x1>x2) {
							double time = Math.abs(x1-x2)/2.0;
							pq.add(new Object[] {i, j, time});
						}
						
						if(d2==0 && x1>x2 && y1>y2 && Math.abs(y1-y2)==Math.abs(x1-x2)) {
							double time = Math.abs(y1-y2);
							pq.add(new Object[] {i, j, time});
						}
						
						if(d2==1 && x1>x2 && y1<y2 && Math.abs(y1-y2)==Math.abs(x1-x2)) {
							double time = Math.abs(y1-y2);
							pq.add(new Object[] {i, j, time});
						}
						
					} else {
						
						if(d2==2 && y1==y2 && x1<x2) {
							double time = Math.abs(x1-x2)/2.0;
							pq.add(new Object[] {i, j, time});
						}
						
						if(d2==0 && x1<x2 && y1>y2 && Math.abs(y1-y2)==Math.abs(x1-x2)) {
							double time = Math.abs(y1-y2);
							pq.add(new Object[] {i, j, time});
						}
						
						if(d2==1 && x1<x2 && y1<y2 && Math.abs(y1-y2)==Math.abs(x1-x2)) {
							double time = Math.abs(y1-y2);
							pq.add(new Object[] {i, j, time});
						}

					}
				}
			} // 모든 원소 조사 완료
			
			// 부딪히는 원자쌍들 꺼내서 소멸시키기
			// 같은 시간에 2개이상의 원자쌍들이 한군데에서 부딪혀 소멸될 수 있으므로
			// 소멸될 원자쌍들을 임시리스트에 넣어놓고 소멸되는 원자쌍의 시간이 바뀔 때 소멸시키기
			double time = 0;
			if(!pq.isEmpty()) {
				time = (double)pq.peek()[2];
			}
			List<Integer> atom = new ArrayList<>();
			while(!pq.isEmpty()) {
				
				Object[] couple = pq.poll();
				int i = (int)couple[0];
				int j = (int)couple[1];
				
				// 시간이 달라졌을 때 전에 넣어놓은 임시리스트에 있는 원자들 소멸시키기
				if(time != (double)couple[2]) {
					// 소멸되는 에너지 더하고 0으로 만들기
					for(int a = 0;a<atom.size();a++) {
						count += energy.get(atom.get(a))[3];
						energy.get(atom.get(a))[3] = 0;
					}
					time = (double)couple[2];
					atom = new ArrayList<>();
				}
				
				// 둘 중 하나라도 이미 소멸되었으면 건너뛰기
				if(energy.get(i)[3]==0 || energy.get(j)[3]==0) continue;
				
				// 임시리스트에 넣기
				atom.add(i);
				atom.add(j);
				
			}
			
			// 시간이 달라지지 않고 끝날 수 있으니 남아있는 임시 배열에 있는 것들 소멸시키기
			for(int a = 0;a<atom.size();a++) {
				count += energy.get(atom.get(a))[3];
				energy.get(atom.get(a))[3] = 0;
			}
			
			sb.append("#" + t + " " + count + "\n");
		}
		System.out.println(sb);
	}
}