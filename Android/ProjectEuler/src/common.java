/*
	Author: ViLH (zidane)
	Email: huuvi168@gmail.com
*/
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class common {
			
	public static long TinhTongCacChuSo(String str)
	{
		long S = 0;
				
		for (int i=0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			int iValue = Character.getNumericValue(c);
			S = S + iValue;
		}		
		
		return S;		
	}
	
	public static String GiaiThua(int n)
	{
		String str = "";
		BigInteger T = new BigInteger("1");
		
		for (int i=1; i <= n; i++)
		{
			// chuyển integer to BigInteger
			BigInteger k = new BigInteger( Integer.toString(i) );
			T = T.multiply(k);
		}
		
		str = T.toString();								
		return str;
	}
	
	
	public static double TinhGiaiThua(int n)
	{
		double T = 1;
		for (int i=1; i <= n ; i++)
			T = T * i;
		
		return T;
	}
	
	public static long TinhTongChuSo(long  n)
	{
		long S = 0;
		
		while (n != 0)
		{
			S = S + n % 10;
			n = n / 10;
		}
		
		return S;
	}
		
	public static int[]  ConvertStringToArrInt(String str)
	{								
		String[] temp;
		
		int[] intArr = new int[20];
		
		// split string with tab format!
		// temp = str.split("\t");	// chú ý file mình là khoảng trắng không phải là dấu \t ~> Haizzzz.
		
		temp = str.split(" ");
		
		// convert string to int
		for (int i=0; i<temp.length; i++)
			intArr[i] = Integer.parseInt( temp[i] );		
		
		return intArr;
	}
	
	public static List<String> readFile2 (String strPath) throws IOException
	{
		FileReader fs = new FileReader(strPath);		
		BufferedReader input = new BufferedReader(fs);
		
		List<String> lst = new ArrayList<String>();

		
		String str = "";
		
		while (str != null)
		{
			str = input.readLine();
			lst.add(str);
		}		
		
		return lst;		
	}
	
	public static int MaxPos(int a, int b, int c,  int aPos, int bPos, int cPos)
	{
		int Max = (a > b ? aPos : bPos);
		
		if (Max == aPos) // a lớn
			Max = (a > c ? aPos : cPos);
		
		else
			Max = (b > c ? bPos : cPos);
		
		return Max;
	}
	
	
	public static int MaxValue (int a, int b, int c)
	{
		int Max = (a > b ? a : b);
		
		if (Max == a)
			Max = (a > c ? a : c);
		
		else
			Max = (b > c ? b : c);
		
		return Max;
	}
	
	
	public static int MaxPos(int a, int b, int aPos, int bPos)
	{
		return (a > b ? aPos : bPos);
	}
	
	public static int MaxValue (int a, int b)
	{
		return (a > b ? a : b);
	}
	
	public static boolean checkSame(String []sArr, String str, int nPos)
	{
		boolean bFlag = false;				// ko trùng 
		for (int i=0; i < nPos; i++)
		{
			if (sArr[i] == str)
			{
				bFlag = true;
				break;
			}
		}		
		return bFlag;
	}	
	
	// số mũ
	public static String power(BigInteger a, int x)
	{
		String str = "";		
		BigInteger num = new BigInteger(a.toString()).pow(x);		
		str = num.toString();		
		
		return str;
	}	
	
	public static boolean BoiSo(int nNum, int nValue)
	{
		if (nNum % nValue == 0)
			return true;		
		return false;		
	}
	
	/*
	 * Nối chuỗi
	 */
	public static String Concentrace(List<String>lst)
	{
		String str = "";
		for (int i=0; i<lst.size(); i++)
			str += lst.get(i);
		
		return str;
	}
	
	// kiểm tra nNum có phải số nguyên tố không?
	public static boolean IsNguyenTo(int nNum)
	{
		if (nNum == 1)
			return false;
		
		if (nNum % 2 == 0 && nNum != 2)	// chẵn và khác 2!
			return false;
		
		byte bCount = 1;
		
		// cách 2
		for (int i=2; i <= Math.sqrt(nNum); i++)
			if (nNum % i == 0)
				bCount ++;
		
		/* Cách 1
		   for (int i=2; i <= nNum - 1; i++)
			if (nNum % i == 0)
				bCount ++;
		*/
		
		if (bCount == 1)
			return true; // là số nguyên tố (chỉ chia hết cho 1 và chính nó)
		return false;
	}
	
	public static int convertCharToInt(char c)
	{
		return Character.getNumericValue(c);		
	}
	
	public static List<String> readFile(String fileName)
	{
		List<String> records = new ArrayList<String>();
		
		try
		{	
			FileReader fr = new FileReader(fileName); 
			BufferedReader bufReader = new BufferedReader(fr);			
			
			String line;
			
			// use the readline method of the BufferedReader			
			while ( (line = bufReader.readLine()) != null ) 
			{
				records.add(line);
			}
			bufReader.close();			
			return records;
		}
		catch(Exception e)
		{
			System.err.format("Exception occurred trying to red '%s'.", fileName);
			e.printStackTrace();
			return null;
		}		
	}
}
