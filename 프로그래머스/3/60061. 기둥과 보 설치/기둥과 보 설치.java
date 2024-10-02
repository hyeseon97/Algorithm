import java.util.*;

class Solution {
    public int[][] solution(int n, int[][] build_frame) {
        
        // 존재하는 기둥과 보의 좌표저장
        Set<String> wall = new HashSet<>();
        Set<String> top = new HashSet<>();
        
        // 작업 순회
        for(int i = 0;i<build_frame.length;i++){
            int c = build_frame[i][0];
            int r = build_frame[i][1];
            int a = build_frame[i][2];
            int b = build_frame[i][3];
            
            // 설치
            if(b == 1){
                // 기둥 설치 -------------------------------------
                if(a == 0){
                    // r가 n이면 불가능
                    if(r == n) continue;
                    // 바닥 || 밑에 기둥이 존재 || 왼쪽에 보 존재 || 오른쪽에 보 존재 -> 가능
                    if(r==0 || wall.contains(toString(r-1, c)) || top.contains(toString(r, c-1)) || top.contains(toString(r, c))){
                        wall.add(toString(r, c));
                    }
                }
                
                // 보 설치  --------------------------------------
                else{
                    // c가 n이면 불가능
                    if(c == n) continue;
                    // 왼쪽아래 기둥 존재 || 오른쪽아래 기둥 존재 || 양쪽에 보 존재 -> 가능
                    if(wall.contains(toString(r-1, c)) || wall.contains(toString(r-1, c+1)) || (top.contains(toString(r, c-1)) && top.contains(toString(r, c+1)))){
                        top.add(toString(r, c));
                    }
                }
            }
            
            // 삭제
            else {
                // 기둥 삭제 -------------------------------------
                if(a == 0){
                    // 위에 기둥이 존재할 때
                    if(wall.contains(toString(r+1, c))){
                        // 위 양쪽에 보가 하나라도 없으면 불가능
                        if(!(top.contains(toString(r+1, c-1)) || top.contains(toString(r+1, c)))) continue;
                    }
                    // 왼쪽위에 보가 존재할 때
                    if(top.contains(toString(r+1, c-1))){
                        // 왼쪽 기둥 존재, 해당 보의 양쪽에 보가 존재 -> 둘 중 하나도 없으면 불가능
                        if(!(wall.contains(toString(r, c-1)) || (top.contains(toString(r+1, c-2)) && top.contains(toString(r+1, c))))) continue;
                    }
                    // 오른쪽위에 보가 존재할 때
                    if(top.contains(toString(r+1, c))){
                        // 오른쪽 기둥 존재, 해당 보의 양쪽에 보가 존재 -> 둘 중 하나도 없으면 불가능
                        if(!(wall.contains(toString(r, c+1)) || (top.contains(toString(r+1, c-1)) && top.contains(toString(r+1, c+1))))) continue;
                    }
                    // 위 조건에 걸리는게 없으면 삭제 가능
                    wall.remove(toString(r, c));
                }
                
                // 보 삭제  --------------------------------------
                else{
                    // 위에 기둥이 존재할 때
                    if(wall.contains(toString(r, c))){
                        // 아래 기둥 존재 || 왼쪽 보 존재 -> 둘 중 하나도 없으면 불가능
                        if(!(wall.contains(toString(r-1, c)) || top.contains(toString(r, c-1)))) continue;
                    }
                    // 위오른쪽에 기둥이 존재할 떄
                    if(wall.contains(toString(r, c+1))){
                        // 오른쪽 아래 기둥 존재 || 오른쪽 보 존재 -> 둘 중 하나도 없으면 불가능
                        if(!(wall.contains(toString(r-1, c+1)) || top.contains(toString(r, c+1)))) continue;
                    }
                    // 왼쪽에 보가 존재할 때
                    if(top.contains(toString(r, c-1))){
                        // 아래 기둥 존재 || 왼쪽 아래 기둥 존재 -> 둘 중 하나도 없으면 불가능
                        if(!(wall.contains(toString(r-1, c)) || wall.contains(toString(r-1, c-1)))) continue;
                    }
                    // 오른쪽에 보가 존재할 때
                    if(top.contains(toString(r, c+1))){
                        // 오른쪽아래 기둥 존재 || 오른쪽오른쪽 아래 기둥 존재 -> 둘 중 하나도 없으면 불가능
                        if(!(wall.contains(toString(r-1, c+1)) || wall.contains(toString(r-1, c+2)))) continue;
                    }
                    // 위 조건에 걸리는게 없으면 삭제 가능
                    top.remove(toString(r, c));
                }
            }
        } // 작업 순회 끝
        
        // 남은 기둥과 보를 정렬해서 반환
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2){
                if(o1[0] == o2[0]){
                    if(o1[1] == o2[1]){
                        return o1[2] - o2[2];
                    }
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });
        
        
        List<String> temp = new ArrayList<>(wall);
        // System.out.println("남은 기둥 수 " + temp.size());
        for(int i = 0;i<temp.size();i++){
            StringTokenizer st = new StringTokenizer(temp.get(i));
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            pq.add(new int[]{c, r, 0});
        }
        temp = new ArrayList<>(top);
        for(int i = 0;i<temp.size();i++){
            StringTokenizer st = new StringTokenizer(temp.get(i));
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            pq.add(new int[]{c, r, 1});
        }
        
        int[][] answer = new int[pq.size()][3];
        for(int i = 0;i<answer.length;i++){
            int[] tempArr = pq.poll();
            answer[i] = tempArr;
        }
        
        return answer;
    }
    
    static String toString(int r, int c){
        return r + " " + c;
    }
}