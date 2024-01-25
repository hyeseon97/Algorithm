import java.util.*;
class Solution {
    public int solution(int[] numbers) {
        int count = 0;
        for(int i = 0;i<numbers.length;i++){
            count += numbers[i];
        }
        return 45-count;
    }
}