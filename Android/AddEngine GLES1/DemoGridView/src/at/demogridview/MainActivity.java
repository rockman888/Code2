package at.demogridview;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GridView gv = (GridView)findViewById(R.id.gridview);
		gv.setAdapter(new ImageAdapter(this));
				
		gv.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "pic" + (position + 1) + " selected", Toast.LENGTH_SHORT).show();				
			}			
		});
	}

	public class ImageAdapter extends BaseAdapter
	{
		private Context context;
		public ImageAdapter(Context c)
		{
			context = c;
		}
		
		// -- returns the number of images
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageIDs.length;
		}
		
		// -- return the ID of an item
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		// -- returns the ID of an item
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		// -- return an ImageView view
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			ImageView imageView;
			
			if (convertView == null)
			{
				imageView = new ImageView(context);
				imageView.setLayoutParams(new GridView.LayoutParams(new GridView.LayoutParams(85,85)));
				
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(5, 5, 5, 5);
			}
			else
				imageView = (ImageView)convertView;
			
			
			imageView.setImageResource(imageIDs[position]);
			return imageView;
		}		
	}
}
