import java.util.*;

class Solution {
    public int solution(int[] topping) {
        int answer = 0;
        
        int N = topping.length;
        int[] left = new int[N];
        int[] right = new int[N];
        
        Set<Integer> set = new HashSet<>();
        for(int i = 0;i<N;i++){
            set.add(topping[i]);
            left[i] = set.size();
        }
        
        set = new HashSet<>();
        for(int i = N-1;i>=0;i--){
            set.add(topping[i]);
            right[i] = set.size();
        }
        
        for(int i = 0;i<N-1;i++){
            if(left[i] == right[i+1]){
                answer++;
            }
        }
        
        return answer;
    }
}