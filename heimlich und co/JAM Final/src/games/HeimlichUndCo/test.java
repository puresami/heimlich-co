package games.HeimlichUndCo;

import java.util.Random;

public class test {

	public static void main(String[] args) {
		
		
		
		int[] arr = {1,2,3,4,5,6,7,8,9,10,12};
		
		int schritte = 0;
		
		int prev=2;
		
		int target=5;
		
		
		
		if(prev<target) {
		schritte = target - prev;
			
		} else if (prev>target) {
			
			
			for(int i = prev; i<=12;i++) {
				
				
				schritte++;
				
				
					
					
				} 
				
			
			
			
			
			
		
		for(int i = 1; i< target;i++) {
			
			
			
			schritte++;
			
		}
		
		
		}
		
		
		
		
		
		System.out.println(schritte);
		
		
		
		
	}
}
