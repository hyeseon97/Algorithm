import java.util.*;
import java.io.*;

public class Main {
	
	/* 주인공 정보 */
	// 현재위치와 시작위치
	static int R;
	static int C;
	static int startR;
	static int startC;
	// 레벨
	static int LV;
	// 현재체력, 최대체력
	static int HP;
	static int HPMAX;
	// 주인공공격력, 무기공격력
	static int AP;
	static int W;
	// 주인공방어력, 방어구방어력
	static int DP;
	static int A;
	// 장신구
	static Set<String> O;
	// 현재경험치, 다음레벨이 되는데 필요한 총 경험치
	static int EX;
	static int EXMAX;
	// 이동방향
	static int[] D;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	/* 게임 정보 */
	// 게임턴수
	static int T;
	// 그리드 크기
	static int N;
	static int M;
	// 그리드
	static char[][] grid;
	// 아이템 개수
	static int L;
	// 아이템 정보, key 위치, value 아이템객체
	static Map<String, Item> items;
	// 열린 아이템 상자 위치 저장
	static Set<String> openItem;
	// 아이템 클래스
	static class Item{
		String type;
		int w;
		int a;
		String o;
	}
	
	/* 몬스터 정보 */
	// 몬스터 개수
	static int K;
	// 몬스터 정보, key 위치, value 몬스터객체
	static Map<String, Monster> monsters;
	// 전투에서 승리해 죽은 몬스터 위치 저장
	static Queue<String> deadMonster;
	// 몬스터 클래스
	static class Monster{
		String name;
		int ap;
		int dp;
		int hp;
		int ex;
	}
	
	public static void main(String[] args) throws Exception {
		
		// 전역변수 초기화 및 입력
		init();
		
		// 주인공의 이동경로에 따라 이동
		String resultMessage = "Press any key to continue.";
		for(int turn = 0;turn<D.length;turn++) {
			T++;
			
			// 현재 방향으로 갈 수 있으면 이동
			int nr = R+dr[D[turn]];
			int nc = C+dc[D[turn]];
			if(nr>=1 && nc>=1 && nr<=N && nc<=M && grid[nr][nc]!='#') {
				R = nr;
				C = nc;
			}
			
			// 이동한 위치에 있는 것에 따라서 수행
			char G = grid[R][C];
			if(G == 'B') {
				getItem();
			} else if(G == '^') {
				getDamage();
			} else if(G == '&' || G == 'M') {
				int ex = fightMonter();
				// 경험치를 얻고 전투를 마무리했으면 승리
				if(ex > 0) {
					winHero(ex);
				}
			}
			
			// 레벨업 할 수 있는 경험치가 되었을 때 레벨업
			if(EX >= EXMAX) {
				levelUp();
			}
			
			// 주인공이 죽었을 때
			if(HP <= 0) {
				// 장신구 RE가 있으면 부활, 없으면 바로 종료
				if(O.contains("RE")) {
					revive();
					continue;
				} else {
					HP = 0;
					if(G == '^') {
						resultMessage = "YOU HAVE BEEN KILLED BY SPIKE TRAP..";
					} else {
						resultMessage = "YOU HAVE BEEN KILLED BY " + monsters.get(R + " " + C).name + "..";
					}
					break;
				}
			}
			
			// 보스몬스터를 처치했을 때 바로 종료
			if(G == 'M') {
				resultMessage = "YOU WIN!";
				break;
			}
			
		} // 커맨드 종료
		
		// 게임 결과 출력
		resultPrint(resultMessage);
		
	}
	
	static void resultPrint(String resultMessage) {
		
		// 그리드 상태
		for(int i = 1;i<=N;i++) {
			for(int j = 1;j<=M;j++) {
				if(i == R && j == C && HP > 0) {
					System.out.print("@");
				} else {
					System.out.print(grid[i][j]);
				}
			}
			System.out.println();
		}
		
		// 진행된 턴 수
		System.out.println("Passed Turns : " + T);
		
		// 레벨
		System.out.println("LV : " + LV);
		
		// 남은 체력, 최대 체력
		System.out.println("HP : " + HP + "/" + HPMAX);
		
		// 공격력
		System.out.println("ATT : " + AP + "+" + W);
		
		// 방어력
		System.out.println("DEF : " + DP + "+" + A);
		
		// 현재 경험치, 다음 레벨이 되는데 필요한 경험치
		System.out.println("EXP : " + EX + "/" + EXMAX);
		
		// 게임 결과
		System.out.println(resultMessage);
		
	}
	
	
	static void levelUp() {
		
		// 레벨 증가시키고 경험치는 초기화하고 다음 필요한 경험치를 현재 레벨에 따라 수정
		LV++;
		EX = 0;
		EXMAX = (5 * LV);
		
		// 최대체력, 공격력, 방어력 증가하고 체력회복
		HPMAX += 5;
		AP += 2;
		DP += 2;
		HP = HPMAX;
		
	}
	
	static void getItem() {
		
		String position = R + " " + C;
		// 열려있는 상자이면 바로 종료
		if(openItem.contains(position)) {
			return;
		}

		Item item = items.get(position);
		
		// W, A이면 교체하고 O이면 장신구가 4개 미만이면서 같은 타입이 없을때 소지 가능
		if(item.type.equals("W")) {
			W = item.w;
		} else if(item.type.equals("A")) {
			A = item.a;
		} else if(item.type.equals("O")) {
			if(O.size()<4 && !O.contains(item.o)) {
				O.add(item.o);
			}
		}
		
		// 열린 아이템 상자로 저장하고 그리드에서 삭제
		openItem.add(position);
		grid[R][C] = '.';
		
	}
	
	static void getDamage() {
		
		// DX 장신구를 가지고 있으면 -1데미지, 아니면 -5데미지
		if(O.contains("DX")) {
			HP -= 1;
		} else {
			HP -= 5;
		}
	}
	
	static int fightMonter() {
		
		String position = R + " " + C;
		Monster monster = monsters.get(position);
		
		int mAP = monster.ap;
		int mDP = monster.dp;
		int mHP = monster.hp;
		int mEX = monster.ex;
		
		
		// 주인공 또는 몬스터 둘 중 하나의 체력이 0이 될때까지 반복
		int attack = 1;
		while(true) {
			
			// 주인공 공격
			// 첫 공격에서 장신구 CO가 있을 때 공격력 2배
			int power = 0;
			if(attack == 1 && O.contains("CO")) {
				// 장신구 DX까지 있을 때 공격력 3배
				if(O.contains("DX")) {
					power = Math.max(1, (AP+W)*3-mDP);
				} else {
					power = Math.max(1, (AP+W)*2-mDP);					
				}
			} else {
				power = Math.max(1, (AP+W)-mDP);				
			}
			
			// 몬스터가 데미지를 입고 사망하면 바로 몬스터의 경험치 반환
			mHP -= power;
			if(mHP <= 0) {
				return mEX;
			}
			
			// 몬스터 공격
			// 장신구 HU가 있고 보스몬스터일 때 체력 최대치로 회복하고 첫 공격에서 데미지 0
			power = 0;
			if(O.contains("HU") && grid[R][C] == 'M' && attack == 1) {
				HP = HPMAX;
			} else {
				power = Math.max(1, mAP-(DP+A));
			}
			
			// 주인공이 데미지를 입고 사망하면 바로 0 반환
			HP -= power;
			if(HP <= 0) {
				return 0;
			}
			
			attack++;
		}
		
		
	}
	
	static void winHero(int ex) {
		
		// 죽은 몬스터 위치 저장하고 그리드에서 삭제
		String position = R + " " + C;
		deadMonster.add(position);
		grid[R][C] = '.';
		
		// 경험치 저장
		// 장신구 EX가 있으면 1.2배 저장
		if(O.contains("EX")) {
			EX += (int)(ex * 1.2);
		} else {
			EX += ex;
		}
		
		// 장신구 HR이 있으면 체력 3회복
		if(O.contains("HR")) {
			HP = Math.min(HP+3, HPMAX);
		}
	}
	
	static void revive() {
		
		// 장신구 RE은 소멸
		O.remove("RE");
		
		// 주인공은 시작위치로 가고 체력 회복
		R = startR;
		C = startC;
		HP = HPMAX;
		
		// 죽은 몬스터 부활
		while(!deadMonster.isEmpty()) {
			StringTokenizer position = new StringTokenizer(deadMonster.poll());
			int r = Integer.parseInt(position.nextToken());
			int c = Integer.parseInt(position.nextToken());
			grid[r][c] = '&';
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		W = A = EX = 0;
		LV = 1;
		HP = HPMAX = 20;
		AP = DP = 2;
		O = new HashSet<>();
		EXMAX = 5 * LV;
		
		T = L = 0;
		grid = new char[N+1][M+1];
		items = new HashMap<>();
		openItem = new HashSet<>();
		
		K = 0;
		monsters = new HashMap<>();
		deadMonster = new LinkedList<>();
		
		for(int i = 1;i<=N;i++) {
			String str = br.readLine();
			for(int j = 1;j<=M;j++) {
				char ch = str.charAt(j-1);
				grid[i][j] = ch;
				if(ch == '@') {
					grid[i][j] = '.';
					R = startR = i;
					C = startC = j;
				} else if(ch == 'B') {
					L++;
					items.put(i + " " + j, new Item());
				} else if(ch == '&' || ch == 'M') {
					K++;
					monsters.put(i + " " + j, new Monster());
				}
			}
		}
		
		String route = br.readLine();
		D = new int[route.length()];
		for(int i = 0;i<D.length;i++) {
			char ch = route.charAt(i);
			if(ch == 'U') {
				D[i] = 0;
			} else if(ch == 'D') {
				D[i] = 1;
			} else if(ch == 'L') {
				D[i] = 2;
			} else if(ch == 'R') {
				D[i] = 3;
			}
		}
		
		for(int i = 0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			String position = r + " " + c;
			
			monsters.get(position).name = st.nextToken();
			monsters.get(position).ap = Integer.parseInt(st.nextToken());
			monsters.get(position).dp = Integer.parseInt(st.nextToken());
			monsters.get(position).hp = Integer.parseInt(st.nextToken());
			monsters.get(position).ex = Integer.parseInt(st.nextToken());
		}
		
		for(int i = 0;i<L;i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			String position = r + " " + c;
			
			String type = st.nextToken();
			if(type.equals("W")) {
				items.get(position).type = type;
				items.get(position).w = Integer.parseInt(st.nextToken());
			} else if(type.equals("A")) {
				items.get(position).type = type;
				items.get(position).a = Integer.parseInt(st.nextToken());
			} else if(type.equals("O")) {
				items.get(position).type = type;
				items.get(position).o = st.nextToken();
			}
		}
	}
	
}