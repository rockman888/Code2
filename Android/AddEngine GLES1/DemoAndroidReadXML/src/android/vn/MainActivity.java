package android.vn;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Thực hiện hàm phân tích XML
		parseXML();
	}
	//Hàm phân tích XML
	private void parseXML(){
		// Đối tượng quản lý thư mục asset trong một ứng dụng Android
		AssetManager assetManager = getBaseContext().getAssets();
		try {
			//Lấy 1 tập tin làm dữ liệu đầu vào có tên là "order.xml"
			InputStream is = assetManager.open("order.xml");
			//Tạo đối tượng dùng cho việc phân tích cú pháp  tài liệu XML
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			//Đối tượng đọc XML
			XMLReader xr = sp.getXMLReader();

			//Tạo đối tượng xử lý XML theo tuần tự của mình
			OrderXMLHandler myXMLHandler = new OrderXMLHandler();
			//Thiết lập nội dung xử lý
			xr.setContentHandler(myXMLHandler);
			//Nguồn dữ liệu vào
			InputSource inStream = new InputSource(is);
			//Bắt đầu xử lý dữ liệu vào
			xr.parse(inStream);

			// Lấy dữ liệu in thông tin cá nhân khách hàng
			String cartId = myXMLHandler.getCartId();
			String customerId = myXMLHandler.getCustomerId();
			String email = myXMLHandler.getEmail();
			LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout1);
			TextView tv = new TextView(this);
			tv.setText("Mã giỏ hàng: " + cartId);
			ll.addView(tv);
			tv = new TextView(this);
			tv.setText("Mã khách hàng: " + customerId);
			ll.addView(tv);
			tv = new TextView(this);
			tv.setText("Email : " + email);
			ll.addView(tv);
			tv = new TextView(this);
			tv.setText("----- Thông tin mua sắm -----");
			ll.addView(tv);
			
			//In chi tiết sản phẩm ra giao diện ứng dụng
			ArrayList<ProductInfo> cartList = myXMLHandler.getCartList();
			for (ProductInfo productInfo : cartList) {
				tv = new TextView(this);
				tv.setText("Sản phẩm thứ : " + productInfo.getSeqNo());
				ll.addView(tv);
				tv = new TextView(this);
				tv.setText("Mã sản phẩm : " + productInfo.getItemNumber());
				ll.addView(tv);
				tv = new TextView(this);
				tv.setText("Số lượng : " + productInfo.getQuantity());
				ll.addView(tv);
				tv = new TextView(this);
				tv.setText("Giá : " + productInfo.getPrice());
				ll.addView(tv);
				tv = new TextView(this);
				tv.setText("---");
				ll.addView(tv);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}