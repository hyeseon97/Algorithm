import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		Loop : for(int t = 1;t<=T;t++) {
			String action = br.readLine();
			int N = Integer.parseInt(br.readLine());
			List<Integer> list = new ArrayList<>();
			StringTokenizer st = new StringTokenizer(br.readLine(), "[],");
			for(int i = 0;i<N;i++) {
				list.add(Integer.parseInt(st.nextToken()));
			}
			boolean front = true;
			for(int i = 0;i<action.length();i++) {
				char ch = action.charAt(i);
				
				if(ch=='R') {
					front = !front;
				} else {
					if(list.isEmpty()) {
						sb.append("error\n");
						continue Loop;
					}
					if(front) {
						list.remove(0);
					} else {
						list.remove(list.size()-1);
					}
				}
				
			}
			
			if(list.size()==0) {
				sb.append("[]\n");
			} else {
				sb.append("[");
				if(front) {
					for(int i = 0;i<list.size()-1;i++) {
						sb.append(list.get(i) + ",");
					}
					sb.append(list.get(list.size()-1) + "]\n");
				} else {
					for(int i = list.size()-1;i>=1;i--) {
						sb.append(list.get(i) + ",");
					}
					sb.append(list.get(0) + "]\n");
				}
			}
		}
		
		System.out.println(sb);
	}
	
}