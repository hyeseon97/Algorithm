class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;
        
        int indexD = deliveries.length-1;
        int indexP = pickups.length-1;
        while(indexD>=0 || indexP>=0){
            int C = 0;
            boolean D = false;
            boolean P = false;
            int totalD = 0;
            int totalP = 0;
            int maxIndex = 0;
            
            while(indexD>=0 && C<cap){
                if(!D && deliveries[indexD]>0){
                    maxIndex = Math.max(maxIndex, indexD+1);
                    D = true;
                }
                
                if(C+deliveries[indexD]<=cap){
                    C += deliveries[indexD];
                    totalD += deliveries[indexD];
                    deliveries[indexD] = 0;
                    indexD--;
                } else{
                    deliveries[indexD] -= (cap-C);
                    totalD += (cap-C);
                    C = cap;
                }
            }
            
            C = 0;
            while(indexP>=0 && C<cap){
                if(!P && pickups[indexP]>0){
                    maxIndex = Math.max(maxIndex, indexP+1);
                    P = true;
                }
                
                if(C+pickups[indexP]<=cap){
                    C += pickups[indexP];
                    totalP += pickups[indexP];
                    pickups[indexP] = 0;
                    indexP--;
                } else{
                    pickups[indexP] -= (cap-C);
                    totalP += (cap-C);
                    C = cap;
                }
            }
            
            if(totalD>0 || totalP>0){
                answer += maxIndex*2;
            }
            
        }
        
        
        return answer;
    }

}
