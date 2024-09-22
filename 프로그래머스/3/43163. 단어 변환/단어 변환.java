import java.util.*;

class Solution {
    public int solution(String begin, String target, String[] words) {
        int W = words.length;
        
        Map<String, List<String>> dic = new HashMap<>();
        for(int i = 0;i<W;i++){
            String word = words[i];
            for(int j = 0;j<word.length();j++){
                String temp = word.substring(0, j) + "_" + word.substring(j+1, word.length());
                if(!dic.containsKey(temp)){
                    dic.put(temp, new ArrayList<>());
                }
                dic.get(temp).add(word);
                
            }
        }
        
        Queue<Object[]> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        q.add(new Object[]{begin, 0});
        visited.add(begin);
        
        while(!q.isEmpty()){
            Object[] obj = q.poll();
            String now = (String)obj[0];
            int count = (int)obj[1];
            
            if(now.equals(target)) return count;
            
            for(int j = 0;j<now.length();j++){
                String temp = now.substring(0, j) + "_" + now.substring(j+1, now.length());
                if(dic.containsKey(temp)){
                    for(int k = 0;k<dic.get(temp).size();k++){
                        String next = dic.get(temp).get(k);
                        if(visited.contains(next)) continue;
                        q.add(new Object[]{next, count+1});
                        visited.add(next);
                    }
                }
            }
        }
        
        return 0;
    }
}