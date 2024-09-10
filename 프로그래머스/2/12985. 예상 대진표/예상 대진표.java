import java.util.*;

class Solution
{
    public int solution(int n, int a, int b)
    {
        
        Queue<Integer> q = new LinkedList<>();
        for(int i = 1;i<=n;i++){
            q.add(i); 
        }
        
        
        int round = 1;
        for(int i = 1;i<=n;i*=2){
            for(int j = 0;j<n/i;j+=2){
                int person1 = q.poll();
                int person2 = q.poll();
                if((person1==a && person2==b) || (person1==b && person2==a)){
                    return round;
                }
                if(person1==a || person2==a) q.add(a);
                else if(person1==b || person2==b) q.add(b);
                else q.add(person1);
            }
            round++;
        }
        
        return round;
    }
}