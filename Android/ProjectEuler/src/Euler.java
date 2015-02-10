import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/*
	Author: ViLH (zidane)
	Email: huuvi168@gmail.com
*/
	

/*
2.1  Các kiểu dữ liệu cơ bản

Java cung cấp các kiểu dữ liệu cơ bản như sau:
byte:   Dùng để lưu dữ liệu kiểu số nguyên có kích thước một byte (8 bít). Phạm vi biểu diễn giá trị từ -128 đến 127. Giá trị mặc định là 0.
char:   Dùng để lưu dữ liệu kiểu kí tự hoặc số nguyên không âm có kích thước 2 byte (16 bít). Phạm vi biểu diễn giá trị từ 0 đến u\ffff. Giá trị mặc định là 0.
boolean:  Dùng để lưu dữ liệu chỉ có hai trạng thái đúng hoặc sai (độ lớn chỉ có 1 bít). Phạm vi biểu diễn giá trị là {“True”, “False”}. Giá trị mặc định là False.
short:   Dùng để lưu dữ liệu có kiểu số nguyên, kích cỡ 2 byte (16 bít). Phạm vi biểu diễn giá trị từ - 32768 đến 32767. Giá trị mặc định là 0.
int:   Dùng để lưu dữ liệu có kiểu số nguyên, kích cỡ 4 byte (32 bít). Phạm vi biểu diễn giá trị từ -2,147,483,648 đến 2,147,483,647. Giá trị mặc định là 0.
float:   Dùng để lưu dữ liệu có kiểu số thực, kích cỡ 4 byte (32 bít). Giá trị mặc định là 0.0f.
double:  Dùng để lưu dữ liệu có kiểu số thực có kích thước lên đến 8 byte. Giá trị mặc định là 0.00d
long:   Dùng để lưu dữ liệu có kiểu số nguyên có kích thước lên đến 8 byte. Giá trị mặc định là 0l.

2.2  Các kiểu dữ liệu đối tượng

Trong Java, có 3 kiểu dữ liệu đối tượng:
Array:   Một mảng của các dữ liệu cùng kiểu
class:   Dữ liệu kiểu lớp  đối tượng do người dùng  định nghĩa. Chứa tập các thuộc tính và phương thức.
interface:  Dữ liệu kiểu lớp giao tiếp do người dùng định nghĩa. Chứa các phương thức của giao tiếp.
*/

public class Euler {
	
	// problem 20
		// tính tổng các giai thừa
		// n! means n × (n − 1) × ... × 3 × 2 × 1
		// For example, 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800!
		private static void solve_problem20()
		{
			int n = 100000;	
			
			String str = common.GiaiThua(n);
			System.out.println( str );
			
			long S = common.TinhTongCacChuSo(str);
			System.out.println("Kết quả = " + S);
			
		}
		
		
		// problem 18
		// tìm số lớn nhất giữa hai số => vị trí!
		// tìm tiếp trong dòng tip theo!	
		
		/*	
		75
		95 64
		17 47 82
		18 35 87 10
		20 04 82 47 65
		19 01 23 75 03 34
		88 02 77 73 07 63 67
		99 65 04 28 06 16 70 92
		41 41 26 56 83 40 80 70 33
		41 48 72 33 47 32 37 16 94 29
		53 71 44 65 25 43 91 52 97 51 14
		70 11 33 28 77 73 17 78 39 68 17 57
		91 71 52 38 17 14 91 43 58 50 27 29 48
		63 66 04 68 89 53 67 30 73 16 69 87 40 31
		04 62 98 27 23 09 70 98 73 93 38 53 60 04 23	
		*/
		private static void  solve_problem18()
		{		
			

			int S = 0;
			S += 75;
			
			// String strPath = new File (JavaPath.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "../demo.txt").getAbsolutePath();		
			String strPath = "D:\\demo.txt"; 
			
			try
			{
				List<String> lst = common.readFile(strPath);
				int[] intArr = new int[20];
				int iPos = 0;
				int Max = -1;
				
				System.out.println("size = " + (lst.size()));
				
				for (int i=1; i<lst.size()-1; i++)
				{	
					intArr = common.ConvertStringToArrInt( lst.get(i) );				
					
					if (intArr.length == 0)
						System.out.println("cannot convert Array Int!");			
									
					
					if (iPos > 0)
					{
						Max = common.MaxValue(intArr[iPos-1], intArr[iPos], intArr[iPos+1]);				
						iPos = common.MaxPos (intArr[iPos-1],  intArr[iPos], intArr[iPos+1], iPos - 1, iPos, iPos + 1);
					}
					else
					{
						Max = common.MaxValue(intArr[iPos], intArr[iPos+1]);				
						iPos = common.MaxPos ( intArr[iPos], intArr[iPos+1], iPos, iPos + 1);
					}
					
					System.out.println("Dòng thứ i = " + i + " - Max = " + Max + " - Pos = " + iPos);
					S = S + Max;
					
				}				
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage() + " " + e.getCause().getMessage());
			}
			
			System.out.println("Kết Quả: S = " + S);
		}
				
		// problem 16
		/*
		 	2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.
			What is the sum of the digits of the number 2^1000?
		*/    
		
		//public static long solve_problem16()
		public static long solve_problem16()
		{
			// dùng biến double để chứa Math.pow
			// chuyển sang BigInteger;
			BigInteger num = new BigInteger("2").pow(1000);
			
			BigInteger answer = new BigInteger("0");
			
			// đổi ra dạng chuỗi để xừ lý
			String str = num.toString();
			
			long sum = 0;
			
			for (int i=0; i< str.length(); i++)
			{
				char c = str.charAt(i);

				// đổi char sang int
				int iValue = Character.getNumericValue(c);
				sum = sum + iValue;			
			}
			
			
			System.out.println("Result = " + sum);
			return sum;
		}
		

		public static void atan2()		
		{
			int x1 = -1, y1 = 0;
			int x2 = -1, y2 = 1;
			int x3 = 1, y3 = 0;
			int x4 = 0, y4 = -1;
							
			
			double up = Math.atan2(x1, y1);
			double right = Math.atan2(x2, y2);
			double down = Math.atan2(x3, y3);
			double left = Math.atan2(x4, y4);
					
			System.out.println("up: " + up * 180 / 3.14);
			System.out.println("down: " + down * 180 /3.14);
			System.out.println("left: " + left * 180 / 3.14);
			System.out.println("right: " + right * 180 / 3.14);		
		}	
		
	
	// problem29 Distinct powers
	
	/*
	ab for 2 ≤ a ≤ 5 and 2 ≤ b ≤ 5:
	2^2=4, 2^3=8, 2^4=16, 2^5=32
	3^2=9, 3^3=27, 3^4=81, 3^5=243
	4^2=16, 4^3=64, 4^4=256, 4^5=1024
	5^2=25, 5^3=125, 5^4=625, 5^5=3125
	*/
	
	private static void problem29(int x, int y)
	{
		String[] sArr = new String[(y-x+1)*3];
		int k = 0;
		for (int i=x; i <= y; i++)
		{
			// 2, 5
			// 2 ^ 2, 2 ^ 3, 2 ^ 4, 2 ^ 5
			// 3 ^ 2, 3 ^ 3, 3 ^ 4, 3 ^ 5
			
			for (int j=x; j <= y; j++)
			{
				BigInteger bi = BigInteger.valueOf(i);
				sArr[k++] = common.power(bi, j);
			}
		}
		
		for (int i=0; i < sArr.length; i++)
			System.out.println(sArr[i] + " - ");
		
		
		int nCount = 0;
		for (int i=0; i < sArr.length; i++)
			if ( common.checkSame(sArr, sArr[i], i) == false )
				nCount ++;
		
		System.out.print("Result = " + (nCount));
	}
	
	// If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
	// Find the sum of all the multiples of 3 or 5 below 1000.
	// tim tổng bội số của 3 hay 5 nhỏ hơn 1000
	private static void problem1(int nNum)
	{
		int S = 0;
		for (int i=1; i<nNum; i++)
		{
			if (common.BoiSo(i, 3) == true)	// boi so cua 3
			{
				System.out.print(i + " ");
				S += nNum;
			}
			
			else if (common.BoiSo(i, 5) == true)	// boi so cua 3
			{
				System.out.print(i + " ");			
				S += nNum;
			}
		}		
		System.out.print("Tổng các bội số nhỏ hơn: " + nNum + " là: " + S );		
	}
	
	// problem 5
	// 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
	// What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
			
	private static void problem5_SmallestMultiple()
	{
		short sNum1 = 1; // short (2 bytes)
		short sNum2 = 10;
		
		long nValue = 0;
		int nCount = 0;
		
		for (long i=10; i < 2100000; i++)
		{
			nCount = 0;
			for (int k = sNum1; k <= sNum2; k++)
			{
				if (i % k == 0)
				{
					//System.out.println(i / k);
					nCount ++;			
				}
			}
			
			if (nCount == sNum2)
			{
				nValue = i;
				break;
			}
		}		
		System.out.println("Đó là số: " + nValue);							
	}	
	
	private static void problem8_LargestProductInASeries(short nCount)
	{
		// get file đường dẫn tương đối
		// Euler.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "num.txt").getAbsolutePath();
		
		String fileName = new File(Euler.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "num.txt").getAbsolutePath();		
		List<String> lst = common.readFile(fileName);
		
		int nMax = -1;
		int nRow = -1;	 // dòng có kết quả max
		int nPos = -1; 	 // vị trí có kết quả max
		
		String str = common.Concentrace(lst);		
		
		// đi từng dòng (dòng 1) (chừa lại nCount vị trí)			
		for (int row=0; row < str.length() - nCount; row++)
		{
			
			int[] arr = new int[nCount];
							
			for (int j=0; j<nCount; j++)
				arr[j] =  common.convertCharToInt(str.charAt(row+j));
							
			// tính toán => T
			int T = 1;
			for (int k=0; k < arr.length; k++)
				T = T * arr[k];
			
			// so sánh lấy max
			if (T > nMax)
			{				
				nMax = T;
				nPos = row;
			}
		}			
		
		System.out.println(" ------------8	Largest product in a series ----------");
		
		System.out.println("+ Max = " + nMax);
		System.out.println("+ Chỉ số dòng: " + nRow);
		System.out.println("+ Vị trí: " + nPos);
		
		System.out.print("+ Các số đó là: ");
		
		for (int i=0; i<nCount; i++)
			System.out.print(str.charAt(nPos+i) + " ");
		
		System.out.println("");
		System.out.println(" ------------8	Largest product in a series ----------");
	}	
	
	
	/*The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
	Find the sum of all the primes below two million.*/
	
	// tìm tổng các số nguyên tố nhỏ hơn nNum
	public static void problem10_SummationOfPromes(int nNum)
	{
		int iSum = 0;
		for (int i=1; i<=nNum; i++)
			if (common.IsNguyenTo(i) == true)
				iSum += i;
		
		//System.out.print(i +  " ");
		// System.out.println("Tổng các số nguyên tố nhỏ hơn: " + nNum + " là: " + iSum);
	}
	
	public static void test(){
		int x=10,y=10;
		x=x++; //x = x => x = 10
		
		++x; // x = x + 1 => x = 11
		y--; // y = 9
		x=--y; // y = y - 1; x = y => y = 8; x = 8 
		x++;	// x + 1 = 9
		
		x=x++;	// x = x => x = 9
		y=++y;	// y = y + 1; y = y => y = 9
		
		
		
		// In the assignment x = x++ you first extract the old value of x to use in 
		// evaluating the right-hand side expression, in this case 'x'; then, 
		// you increment x by 1. Last, you assign the results of the expression 
		// evaluation (10) to x via the assignment statement.
		
		
		System.out.println("x="+x+"y="+y); 
	}
	
	
	public static void demo()
	{
		  String bigNumber = "73167176531330624919225119674426574742355349194934"
	                + "96983520312774506326239578318016984801869478851843"
	                + "85861560789112949495459501737958331952853208805511"
	                + "12540698747158523863050715693290963295227443043557"
	                + "66896648950445244523161731856403098711121722383113"
	                + "62229893423380308135336276614282806444486645238749"
	                + "30358907296290491560440772390713810515859307960866"
	                + "70172427121883998797908792274921901699720888093776"
	                + "65727333001053367881220235421809751254540594752243"
	                + "52584907711670556013604839586446706324415722155397"
	                + "53697817977846174064955149290862569321978468622482"
	                + "83972241375657056057490261407972968652414535100474"
	                + "82166370484403199890008895243450658541227588666881"
	                + "16427171479924442928230863465674813919123162824586"
	                + "17866458359124566529476545682848912883142607690042"
	                + "24219022671055626321111109370544217506941658960408"
	                + "07198403850962455444362981230987879927244284909188"
	                + "84580156166097919133875499200524063689912560717606"
	                + "05886116467109405077541002256983155200055935729725"
	                + "71636269561882670428252483600823257530420752963450"
	                ;

	                ;
	        ArrayList<Integer> myArray = new ArrayList<Integer>();
	        int counter = 1;
	        int current = 0;
	        int product = 1;
	        int maximumProduct = 0;
	        for(int i = 0; i < bigNumber.length(); i++)  {
	            String b = "" + bigNumber.charAt(i);
	            current = Integer.parseInt(b);
	            myArray.add(current);
	            counter++;
	            if(counter == 5) {
	                for(int x : myArray) {
	                    product = x * product;
	                }
	                if(product > maximumProduct) {
	                    maximumProduct = product;

	                }
	               myArray.remove(0);
	               product = 1;
	            }
	            counter=myArray.size();
	        }
	        System.out.println(maximumProduct);
	 }
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// problem29(2,2);
		test();
	}
}
