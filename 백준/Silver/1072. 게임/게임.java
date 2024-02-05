import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static long X;
	static long Y;
	static long Z;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		X = Long.parseLong(st.nextToken());
		Y = Long.parseLong(st.nextToken());
		Z = Y * 100 / X;

		long min = 1;
		long max = 1000000000;
		long mid = -1;
		long change = 0;

		while (min <= max) {
			mid = (min + max) / 2;
			change = (Y + mid) * 100 / (X + mid);

			if (change == Z) {
				min = mid + 1;
			} else {
				max = mid - 1;
			}

		}

		if (change == Z) {
			if(mid==1000000000) {
				System.out.println(-1);
			} else {
				System.out.println(mid + 1);
			}
		} else {
			System.out.println(mid);
		}

	}
}