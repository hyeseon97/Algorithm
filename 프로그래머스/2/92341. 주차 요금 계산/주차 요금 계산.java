import java.util.*;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        
        // 누적 주차 시간 저장
        Map<String, Integer> totalTime = new HashMap<>();
        
        // 입차한 차량 기록
        // key는 차량 번호, value는 시간
        Map<String, String> park = new HashMap<>();
        for(int i = 0;i<records.length;i++){
            StringTokenizer st = new StringTokenizer(records[i]);
            String time = st.nextToken();
            String number = st.nextToken();
            String io = st.nextToken();
            
            if(io.equals("IN")){
                park.put(number, time);
            } else{
                // 입차, 출차 시간 따져서 시간 계산
                int minute = howlong(fees, park.get(number), time);
                if(!totalTime.containsKey(number)){
                    totalTime.put(number, 0);
                }
                totalTime.put(number, totalTime.get(number)+minute);
                // 출차했으니 제거
                park.remove(number);
                System.out.println(number + "차량의 시간 " + minute);
            }
        } // 모든 기록 확인 끝
        
        // 출차 내역이 없이 입차한 차량이 남아 있으면 출차를 23:59으로 하여 계산
        Iterator<String> numbers = park.keySet().iterator();
        while(numbers.hasNext()){
            String number = numbers.next();
            // 입차, 출차 시간 따져서 시간 계산
            int minute = howlong(fees, park.get(number), "23:59");
            if(!totalTime.containsKey(number)){
                totalTime.put(number, 0);
            }
            totalTime.put(number, totalTime.get(number)+minute);
        }
        
        // 차량 번호 순으로 정렬해서 결과 반환
        List<String> numList = new ArrayList<>(totalTime.keySet());
        Collections.sort(numList);
        int[] answer = new int[numList.size()];
        for(int i = 0;i<numList.size();i++){
            int minute = totalTime.get(numList.get(i));
            if(minute<=fees[0]){
                answer[i] = fees[1];
            } else{
                answer[i] = fees[1] + (int)Math.ceil((minute-fees[0])/(double)fees[2])*fees[3];
            }
        }
        
        return answer;
    }
    
    // 입차, 출차 시간에 따른 시간 계산
    static int howlong(int[] fees, String in, String out){
        StringTokenizer inTime = new StringTokenizer(in, ":");
        StringTokenizer outTime = new StringTokenizer(out, ":");
        int inHour = Integer.parseInt(inTime.nextToken());
        int inMinute = Integer.parseInt(inTime.nextToken());
        int outHour = Integer.parseInt(outTime.nextToken());
        int outMinute = Integer.parseInt(outTime.nextToken());
        
        if(inMinute <= outMinute){
            return (outHour-inHour)*60 + (outMinute-inMinute);
        } else{
            return (outHour-inHour-1)*60 + (60-inMinute) + outMinute;
        }

    }
}