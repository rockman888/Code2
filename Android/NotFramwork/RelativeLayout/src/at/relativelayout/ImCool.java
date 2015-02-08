package at.relativelayout;

import java.io.IOException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.*;
import android.provider.SyncStateContract.Constants;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.ImageView;
import android.widget.TextView;

public class ImCool extends Activity {
	
	// Add a constant for a request type passed to startActivityForResult
	// and verified on return on onActivityResult
	private static final int PICK_CONTACT_REQUEST = 0;
	private Uri contactUri;	// add a variable for the contactUri
	
	
	// button clicked
	public void onImOkButtonClicked(View v)
	{
		
		String phoneNumber;		// the phone number to send the text message to
		String serviceCenterAddress;
		String text;		// the message text
		
		// There are special intents that can be activated like callback, 
		// u won't need to use them for basic text message sending
		PendingIntent sentIntent;
		PendingIntent deliveryIntent;
		
		
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(getMobileNumber(contactUri), null, "I\'m cool babe", null, null);
	}

	// button clicked
	public void OnUpdateContactButtonClicked(View v)
	{
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI); 
		startActivityForResult(intent, PICK_CONTACT_REQUEST);
		
		Log.d("Ouput", "vilh Clicked!");
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == PICK_CONTACT_REQUEST)
			if (resultCode == RESULT_OK)
			{
				
				contactUri = data.getData();	// Cache the contactUri that comes back from the contact selection
				// print the intent to log so u can see what's passed back
				Log.d("Selection", data.toString());
				
				
				// pass the uri ( the data from the intent) on to renderContact
				renderContact(data.getData());
			}		
	}

	// Pass in the uri
	private void renderContact(Uri uri)
	{
		TextView contactNameView = (TextView) findViewById(R.id.contact_name);
		TextView contactPhoneView = (TextView)findViewById(R.id.contact_phone);
		ImageView contactPhotoView = (ImageView) findViewById(R.id.contact_portrait);
		
		// Check for a null URI, if null there must be no contact
		if (uri == null)
		{
			contactNameView.setText("Select a contact");
			contactPhoneView.setText("");
			contactPhotoView.setImageBitmap(null);
			
		}
		else
		{
			contactNameView.setText(getDisplayName(uri));
			contactPhoneView.setText(getMobileNumber(uri));
			contactPhotoView.setImageBitmap(getPhoto(uri));
			
		}
			
	}
	
	// This method will return the display name for the contact
	private String getDisplayName(Uri uri)
	{
		String displayName = null;
		
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		if (cursor.moveToFirst())
		{
			displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		}
		
		cursor.close();
		return displayName;
	}
	
	private String getMobileNumber(Uri uri)
	{
		
		String phoneNumber = null;
		
		// ContactsContract.CommonDataKinds.Phone.CONTENT_URI: Pass in the Content uri constant for phone numbers:
		// ContactsContract.CommonDataKinds.Phone.NUMBER: Set the projection to the phone number
		
		Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
				new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
		
		
		if (cursor.moveToFirst())
		{
			// Store the first phone number
			phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					
		}
		
		// Close the cursor;
		cursor.close();		
		
		
		return phoneNumber;	// return the phone number
	}
	
	// The last method will return the photo for the contact
	private Bitmap getPhoto(Uri uri)
	{
		Bitmap photo = null;
		
		String id = null;
		Cursor cursor = getContentResolver().query(uri, new String[]{ContactsContract.Contacts._ID}, null, null, null );
		
		if (cursor.moveToFirst())
			id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
		
		cursor.close();
		try
		{
			Uri uriTemp = ContactsContract.Contacts.CONTENT_URI;
			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(), ContentUris.withAppendedId(uriTemp, new Long(id).longValue()));
			
			if (input != null)	// Use bitmapFactory to decode the stream into a real, live bitmap
				photo = BitmapFactory.decodeStream(input);
			
			input.close();
			
		}catch(IOException iox)	{}
		
		return photo;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		// pass in a null uri onCreate since no Contact has been selected yet
		renderContact(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
