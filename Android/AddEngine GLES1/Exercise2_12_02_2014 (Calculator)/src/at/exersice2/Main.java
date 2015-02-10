
/*********************************************
 * Author		: Lý Hữu Vị
 * SkypeID		: huuvi168
 * Tên diễn đàn	: huuvi
 * Khóa học lập trình android miễn phí(Đợt 4) 14/01/2014
*********************************************/

// Notes:

// Bài 1: Tạo 1 giao diện nhập liệu gồm có họ tên, ngày tháng năm sinh, giới tính là (RadioButon), và 1 nút lưu dữ liệu. Phần làm đẹp để cho các bạn thự thể hiện.
// gọi hàm Bai1();

// Bài 2: Tạo 1 giao diện tương tự hình calculator và Xử lý calculator (tính toán đơn giản +,-,x,/)
// gọi hàm Bai2();
// bài toán chỉ xử lý các phép toán cộng trừ nhân chia, 
// cách sử dụng: 
// 1. nhấn vào 1 số, 
// 2. nhấn dấu cộng/trừ/nhân/chia trên màn hình
// 3. nhấn số kế tiếp
// 4. nhấn dấu bằng sẽ xuất hiện kết quả
// 5. nếu đã có kết quả, người dùng nhấn tiếp dấu cộng/trừ/nhân/chia thì máy tính sẽ tính ANS + số_vua_nhập


package at.exersice2;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bai2();
		
	}
	
	private void Bai2()	// calculator
	{
		// nhớ khai báo trong Mainifest
		Intent in = new Intent(Main.this, Calc.class);
		startActivity(in);   
	}
	
	private void Bai1()	// 
	{
		setContentView(R.layout.activity_main_bai1);	// bài 1
		initNamSinh();
	}
	
	private void initNamSinh()
	{
		final Spinner s= (Spinner) findViewById(R.id.spinner_namsinh);		
				
		List<String> list = new ArrayList<String>();
		
		for (int i=1950; i<=2004; i++)
			list.add(String.valueOf(i));
		
		ArrayAdapter<String> data = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		
		data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		s.setAdapter(data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
