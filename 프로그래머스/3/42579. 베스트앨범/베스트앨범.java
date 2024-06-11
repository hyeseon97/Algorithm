import java.util.*;
class Solution {
    public int[] solution(String[] genres, int[] plays) {
        
        // 장르별로 재생된 횟수 저장 -> 나중에 정렬해서 순서 정하기
        // key:장르, value:장르에 속한 노래의 재생수 합
        Map<String, Integer> playSum = new HashMap<>();
        
        // 우선순위큐를 재정의해서 기준에 맞게 정렬하도록 하자
        // key:장르, int[0]:고유번호, int[1]:재생된수
        Map<String, PriorityQueue<int[]>> sortSong = new HashMap<>();
        
        for(int i = 0;i<genres.length;i++){
            String genre = genres[i];
            int play = plays[i];
            
            // 장르 합계 계산하고
            // 장르에 맞는 map에 저장
            if(playSum.containsKey(genre)){
                playSum.put(genre, playSum.get(genre)+play);
                
                sortSong.get(genre).add(new int[]{i, play});
            } else{
                playSum.put(genre, play);
                
                sortSong.put(genre, new PriorityQueue<>(new Comparator<int[]>(){
                    @Override
                    public int compare(int[] o1, int[] o2){
                        if(o1[1]==o2[1]){
                            return o1[0]-o2[0];
                        }
                        return o2[1]-o1[1];
                    }
                }));
                sortSong.get(genre).add(new int[]{i, play});
            } 
        }
        
        // 장르별 재생수 합계 정렬
        PriorityQueue<Object[]> pq = new PriorityQueue<>(new Comparator<Object[]>(){
            @Override
            public int compare(Object[] o1, Object[] o2){
                return (int)o2[1] - (int)o1[1];
            }
        });
        
        for(String s : playSum.keySet()){
            pq.add(new Object[]{s, playSum.get(s)});
        }
        
        List<Integer> list = new ArrayList<>();
        
        while(!pq.isEmpty()){
            Object[] o = pq.poll();
            String g = (String)o[0];
            
            int size = Math.min(2, sortSong.get(g).size());
            for(int i = 0;i<size;i++){
                int[] s = sortSong.get(g).poll();
                list.add(s[0]);
            }
        }
        
        int[] answer = list.stream().mapToInt(i->i).toArray();
        return answer;
    }
}