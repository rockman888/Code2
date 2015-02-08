package at.exersice2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Calc extends Activity 
{	
	private EditText m_etResult;
	private Long m_FirstNum;
	private Long m_SeconceNum;
	private String m_Sign;	// dấu các phép toán: +,-,x, /
	private boolean m_bStatusCount;	// trạng thái tính toán số hạng thứ hai
	
	
	private void onDisplayString(String szValue)
	{		
		String s = m_etResult.getText().toString();
				
		if (s.equals("0"))
			s = "";
		
		if (m_bStatusCount == true)
		{
			s = "";
			m_bStatusCount = false;
		}
		
		m_etResult.setText(s + szValue); 
		
/*		String customHtml =  "<html>";
		customHtml += "<head>";
		customHtml += "<style type=\"text/css\">";
		customHtml += "p {";
		customHtml += "text-align: right;";
		customHtml += "}";
		customHtml += "</style>";
		customHtml += "</head>";
		customHtml += "<body>";
		//customHtml += "<p><b>0</b></p>";		
		customHtml += "<p><b>" + szValue + "</b></p>";		
		customHtml += "</body>";
		customHtml += "</html>";
		m_webView.loadData(customHtml, "text/html", "UTF-8");
*/		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.activity_main_bai2);	// bài 2
		
		m_etResult = (EditText)findViewById(R.id.et_result);
		onDisplayString("0");
		
		m_Sign = "";
		m_bStatusCount = false;
		
	}
		
	public void onClearButtonClicked(View v)
	{
		m_etResult.setText("");
		onDisplayString("0");
	}
	
	public void on0ButtonClicked(View v)
	{		
		onDisplayString("0");
	}
	
	public void on1ButtonClicked(View v)
	{
		onDisplayString("1");
	}
	
	public void on2ButtonClicked(View v)
	{
		onDisplayString("2");
	}
	
	public void on3ButtonClicked(View v)
	{
		onDisplayString("3");
	}
	
	public void on4ButtonClicked(View v)
	{
		onDisplayString("4");
	}
	
	public void on5ButtonClicked(View v)
	{
		onDisplayString("5");
	}
	
	public void on6ButtonClicked(View v)
	{
		onDisplayString("6");
	}
	
	public void on7ButtonClicked(View v)
	{
		onDisplayString("7");
	}
	
	
	public void on8ButtonClicked(View v)
	{
		onDisplayString("8");
	}
	
	public void on9ButtonClicked(View v)
	{
		onDisplayString("9");
	}
	
	
	// +
	public void onPlusButtonClicked(View v)
	{		
		String szFirstNum = m_etResult.getText().toString();
		m_FirstNum = Long.parseLong(szFirstNum);
		
		m_Sign = "+";
		m_bStatusCount = true;
		
		// onDisplayString("9");
	}
		
	// -	
	public void onMinusButtonClicked(View v)
	{
		String szFirstNum = m_etResult.getText().toString();
		m_FirstNum = Long.parseLong(szFirstNum);
		
		m_Sign = "-";
		m_bStatusCount = true;
	}
	
	// x
	public void onMultButtonClicked(View v)
	{
		String szFirstNum = m_etResult.getText().toString();
		m_FirstNum = Long.parseLong(szFirstNum);
		
		m_Sign = "*";
		m_bStatusCount = true;
	}
	
	// /
	public void onDivButtonClicked(View v)
	{
		String szFirstNum = m_etResult.getText().toString();
		m_FirstNum = Long.parseLong(szFirstNum);
		
		m_Sign = "/";
		m_bStatusCount = true;
	}
	
		
	
	public void onEqualButtonClicked(View v)
	{
		String szSeconceNum = m_etResult.getText().toString();
		m_SeconceNum = Long.parseLong(szSeconceNum);		
		Count();
		
		
	}	
	
	private void Count()
	{
		if (m_Sign.equals("+"))
		{
			Long result = m_FirstNum + m_SeconceNum;
			m_etResult.setText(String.valueOf(result));					
		}
		else if (m_Sign.equals("-"))
		{
			Long result = m_FirstNum - m_SeconceNum;
			m_etResult.setText(String.valueOf(result));
		}
		else if (m_Sign.equals("*"))
		{
			Long result = m_FirstNum * m_SeconceNum;
			m_etResult.setText(String.valueOf(result));	
		}
		else if (m_Sign.equals("/"))
		{
			Long result = m_FirstNum / m_SeconceNum;
			m_etResult.setText(String.valueOf(result));
		}			
		
		m_Sign = "";
		m_bStatusCount = true;
	}
}
