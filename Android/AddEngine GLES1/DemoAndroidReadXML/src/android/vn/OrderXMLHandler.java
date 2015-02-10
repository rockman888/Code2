package android.vn;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OrderXMLHandler extends DefaultHandler {

	boolean currentElement = false;
	String currentValue = "";

	String cartId;
	String customerId;
	String email;
	ProductInfo productInfo;
	ArrayList<ProductInfo> cartList;

	public String getCartId() {
		return cartId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getEmail() {
		return email;
	}

	public ArrayList<ProductInfo> getCartList() {
		return cartList;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (qName.equals("PurchaseOrder")) {
			cartList = new ArrayList<ProductInfo>();
		} else if (qName.equals("OrderItemDetail")) {
			productInfo = new ProductInfo();
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		if (qName.equalsIgnoreCase("OrderId"))
			cartId = currentValue.trim();
		else if (qName.equalsIgnoreCase("CustomerId"))
			customerId = currentValue.trim();
		else if (qName.equalsIgnoreCase("Email"))
			email = currentValue.trim();

		else if (qName.equalsIgnoreCase("LineNumber"))
			productInfo.setSeqNo(currentValue.trim());
		else if (qName.equalsIgnoreCase("ProductSku"))
			productInfo.setItemNumber(currentValue.trim());
		else if (qName.equalsIgnoreCase("Quantity"))
			productInfo.setQuantity(currentValue.trim());
		else if (qName.equalsIgnoreCase("Price"))
			productInfo.setPrice(currentValue.trim());
		else if (qName.equalsIgnoreCase("OrderItemDetail"))
			cartList.add(productInfo);

		currentValue = "";
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}
	}
}
