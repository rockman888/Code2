
import java.util.Random;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random r = new Random();
		
		int[] arrTemp = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100}; 
		
		for (int i=1; i<=100; i++)
		{
			int a = r.nextInt(100) + 1;
			System.out.print(a + " - ");
			
			for (int j=0; j<arrTemp.length; j++)
				if (arrTemp[j] == i)
				{
					System.out.println();
					break;
				}
		}
	}
	
	// 10 20 30 40 50 60 70 80 90 100 

}
