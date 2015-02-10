
public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int x1 = 0, y1 = -1; 
		int x2 = 1, y2 = 0;
		int x3 = 0, y3 = 1;
		int x4 = -1, y4 = 0;
		
		double z1 = Math.atan2(x1, y1);
		double z2 = Math.atan2(x2, y2);
		double z3 = Math.atan2(x3, y3);
		double z4 = Math.atan2(x4, y4);
		
		
		System.out.println("Math.atan2(" + x1 + ", " + y1 + ") = " + z1);
		System.out.println("Math.atan2(" + x2 + ", " + y2 + ") = " + z2);
		System.out.println("Math.atan2(" + x3 + ", " + y3 + ") = " + z3);
		System.out.println("Math.atan2(" + x4 + ", " + y4 + ") = " + z4);		

	}

}
