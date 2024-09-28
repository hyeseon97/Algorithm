import java.util.*;

class Solution {
    
    // 이진수
    static String binary;
    
    // 이진수가 들어있는 포화이진트리
    static int[] tree;
    
    // 결과
    static int[] answer;
    
    public int[] solution(long[] numbers) {
        
        // numbers = new long[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        answer = new int[numbers.length];
        Arrays.fill(answer, 1);
        
        N : for(int n = 0;n<numbers.length;n++){
            
            // 현재 숫자
            long number = numbers[n];
            
            // 이진수로 변환
            Stack<Integer> stack = new Stack<>();
            while(number>1){
                int y = (int)(number%2);
                stack.push(y);
                number /= 2;
            
            }
            
            // 이진수
            StringBuilder sb = new StringBuilder();
            
            // 해당 이진수가 들어갈 수 있는 포화이진트리의 높이 구하기
            int len = stack.size()+1;
            int k = 1;
            while(Math.pow(2, k)-1 < len){
                k++;
            }
            
            // 포화이진트리에 맞춰 앞에 0 채워넣기
            for(int i = len;i<Math.pow(2, k)-1;i++){
                sb.append("0");
            }
            
            // stack에서 하나씩 빼서 이진수 저장
            sb.append(number);
            while(!stack.isEmpty()){
                sb.append(stack.pop());
            } 
            
            binary = sb.toString();
            
            // 이진수를 포화이진트리에 넣기
            tree = new int[(int)Math.pow(2, k)];
            index = 0;
            makeTree(1);
            
            
            // 부모노드가 0일때 자식노드에 1이 있으면 불가능
            checkTree(n, 2);
            checkTree(n, 3);
        
        } // numbers 순회 끝
        
        
        return answer;
    }
    
    // 
    static void checkTree(int number, int node){
        
        if(node>=tree.length) return;
        
        if(tree[node/2]==0 && tree[node]==1){
            answer[number] = 0;
            return;
        } else{
            checkTree(number, node*2);
            checkTree(number, node*2+1);
        }
        
    }
    
    // 중위순회로 포화이진트리 채우기
    static int index;
    static void makeTree(int node){
        
        if(node>=tree.length) return;
        
        makeTree(node*2);
        tree[node] = binary.charAt(index++)-'0';
        makeTree(node*2+1);
        
    }
}