package com.shtern.timeupp;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DestListAdapter extends BaseAdapter {
	private Context mContext;

	ImageSpan is;
	int j;
	Vector<Integer> checkvec = new Vector<>();
	SpannableString text = new SpannableString("");

	private List<DestListItem> mList;
	String popimagelink;

	public DestListAdapter(Context context, List<DestListItem> list) {
		mContext = context;
		mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int pos, View convertView, ViewGroup parent) {
		final DestListItem entry = mList.get(pos);

		// inflating list view layout if null
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.destlistitem, null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(entry.getName());
		name.setTextColor(Color.BLACK);
		TextView dest = (TextView) convertView.findViewById(R.id.destination);
		dest.setText(entry.getDest());
		dest.setTextColor(Color.BLACK);
		TextView time = (TextView) convertView.findViewById(R.id.time);
		time.setText(entry.getTime());
		time.setTextColor(Color.BLACK);
		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.destlistlayout);
		rl.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				final Intent intent = new Intent(mContext,
						CreateActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("destination",entry.getDest());
				intent.putExtra("time", entry.getTime());
				intent.putExtra("name", entry.getName());
				mContext.startActivity(intent);
			}
			
		});

		return convertView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}