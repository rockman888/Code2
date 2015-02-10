package at.demogalleryview;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery.LayoutParams;


public class MainActivity extends Activity {

	Integer[] imageIDs = 
	{
		R.drawable.img1,
		R.drawable.img2,
		R.drawable.img3,
		R.drawable.img4,
		R.drawable.img5,
		R.drawable.img6
	};
	
	private ImageSwitcher imageSwitcher;
	
	//** Called when the activity is first created
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// **************************************
		// imageSwitcher = (ImageSwitcher)findViewById(R.id.switcher1);
		// imageSwitcher.setFactory(this);
		//imageSwitcher.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		
		//imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		
		// **************************************
				
		Gallery gallery = (Gallery)findViewById(R.id.gallery1);		
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener()
		{
			
			// tất cả các hàm trong andorid đều chữ đầu tiên là viết thường
			// @Override						
			public void onItemClick(AdapterView<?>parent, View v, int position, long id)
			{		
				//
				Toast.makeText(getBaseContext(), "pic" + (position + 1) + " selected", Toast.LENGTH_SHORT).show();
				
				// display the images selected with ImageView
				 ImageView imageView = (ImageView)findViewById(R.id.image1);
				 imageView.setImageResource(imageIDs[position]);
				
				// display the images selected with imageSwitcher
				//imageSwitcher.setImageResource(imageIDs[position]);
				
				
			}

		});
				
	}
	
	public View makeView()
	{
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(0xFF000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		return imageView;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	// class long class -> pro
	public class ImageAdapter extends BaseAdapter
	{
		private Context context;
		private int itemBackground;
		
		public ImageAdapter(Context c)
		{
			context = c;
			// setting the style
			TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
			itemBackground = a.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 0);
			
			a.recycle();
			
		}

		// return the number of images
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageIDs.length;
		}

		
		// return the ID of an item
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		
		// return the ID of an item
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		// return an ImageView view
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(imageIDs[position]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(150,  120));
			imageView.setBackgroundResource(itemBackground);
			
			return imageView;
		}
	}

	
}


