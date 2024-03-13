class Solution {
    public String solution(String number, int k) {
        StringBuilder sb = new StringBuilder();
        // 만들 숫자 자리수
        int N = number.length()-k;
        // 만들 숫자의 이전 숫자가 가진 인덱스 +1
        // 현재 만들 숫자를 이 인덱스부터 시작해서 찾기
        int index = 0;
        // 만들 숫자 자리수만큼 for문을 돌려서 한자리씩 숫자 정하기
        for(int i = 0;i<N;i++){
		        // 현재 만들 숫자로 올 수 있는 최대값
            char max = '0';
            // 남은 숫자 고려해서 반복문 종료 조건 정하기
            for(int j = index;j<=i+k;j++){
		            // 더 큰 값 만났을 때 max 와 index 갱신
                if(max<number.charAt(j)){
                    max = number.charAt(j);
                    index = j+1;
                }
            }
            sb.append(max);
        }
        return sb.toString();
    }
}
