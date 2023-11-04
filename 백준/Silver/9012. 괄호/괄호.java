import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 0;tc<T;tc++) {
			String str = sc.next();
			
			while(str.contains("()")) {
				str = str.replace("()", "");
			}
			
			if(str.equals("")) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}
}
