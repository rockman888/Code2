package at.listviewdemo;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TimeTrackerAdapter extends CursorAdapter {
	
	public TimeTrackerAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
	}

	
	
	

	// lấy 2 cột Name, Notes bỏ vào  
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		TextView tvName = (TextView)view.findViewById(R.id.time_view);		
		tvName.setText(cursor.getString(1));
		
		TextView tvNotes = (TextView)view.findViewById(R.id.notes_view);
		tvNotes.setText(cursor.getString(2));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// The LayoutInflater is retrieved and the layout in inflated and returned
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View v = inflater.inflate(R.layout.time_list_item, null);
		return v;
	}
	


// ***************** all below method is extends BaseAdapter ************** //
	
//	private ArrayList<TimeRecord> times = new ArrayList<TimeRecord>();
//	
//	public void addTimeRecord(TimeRecord t)
//	{
//		times.add(t);
//	}
	
	
//	public TimeTrackerAdapter()
//	{
//		String t[] = {"38:23", "49:01", "26:21", "29:42"};
//		String n[] = {"Felling good", "Tired, needed more caffeine", "I'm rocking it", "Lost some time on the hills, but pretty good"};
//		
//		for (int i=0; i<t.length; i++)
//		{
//			TimeRecord tr = new TimeRecord(t[i], n[i]);
//			times.add(tr);
//		}
//	}
	
//	// since there is one TimeRecord for each row
//	// the size of the ListView is just the number of TimeRecord in the ArrayList
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return times.size();
//	}
//
//	
//	// Again the one-to-one mapping keeps everything easy!,
//	// the data for a row at the index is the TimeRecord in 
//	// the ArrayList at that same index
//	@Override
//	public Object getItem(int index) {
//		// TODO Auto-generated method stub
//		return getItem(index);
//	}
//
//	@Override
//	public long getItemId(int index) {
//		// TODO Auto-generated method stub
//		return index;
//	}
//
//	
//	// * There is just one view method you have to implement in BaseAdapter subclasses... 
//	// * the method that returns the view used to display data in the ListView
//	@Override
//	public View getView(int index, View view, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		
//		
//		// check if the view is null.
//		// if it it, retrieve the layout inflate and inflate the view
//		if (view == null)
//		{
//			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//			view = inflater.inflate(R.layout.time_list_item, parent, false);
//		}
//		
//		// the TimeRecord in the ArrayList at the index has everything you need to populate the view 
//		TimeRecord time = times.get(index);
//		
//		
//		// display time on screen
//		TextView timeTextView = (TextView)view.findViewById(R.id.time_view);
//		timeTextView.setText(time.getTime());	
//		
//		
//		// display note on screen
//		TextView notesTextView = (TextView)view.findViewById(R.id.notes_view);
//		notesTextView.setText(time.getNotes());
//		
//		return view;
//	}

}
