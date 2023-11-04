import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		boolean[][] arr = new boolean[100][100];
		
		for(int i = 0;i<4;i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			int xplus = sc.nextInt();
			int yplus = sc.nextInt();
			
			for(int a = x;a<xplus;a++) {
				for(int b = y;b<yplus;b++) {
					arr[a][b] = true;
				}
			}
		}
		int area = 0;
		for(int i = 0;i<100;i++) {
			for(int j = 0;j<100;j++) {
				if(arr[i][j]==true) {
					area++;
				}
			}
		}
		System.out.println(area);
		
	}
}
