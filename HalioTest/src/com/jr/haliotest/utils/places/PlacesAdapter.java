package com.jr.haliotest.utils.places;

import java.util.List;

import com.jr.haliotest.R;
import com.jr.haliotest.utils.ImageLoader;
import com.jr.haliotest.utils.ImageLoader.ImageLoaderListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacesAdapter extends BaseAdapter{

	private Context mContext;

	private List<Place> placeList;
	
	private ImageLoader imageLoader;
	
	private Bitmap imageThumb;

	public PlacesAdapter(Context context, List<Place> placeList) {
		this.mContext = context;

		this.placeList = placeList;
	}

	public void updateList(List<Place> list) {
		placeList = list;
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return placeList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return placeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.list_item, null);
			holder = new ViewHolder();

			holder.addressTextView = (TextView) convertView.findViewById(R.id.address_text);
			
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.addressTextView.setText(placeList.get(position).getName() + " "
				+ placeList.get(position).getFormattedAddress());
		
		
		
		imageLoader = new ImageLoader(new ImageLoaderListener() {
			
			@Override
			public void onImageLoaded(Bitmap image, String url) {
				holder.icon.setImageBitmap(image);
				
			}
		});

		imageLoader.getThumbImage(placeList.get(position).getIcon());
		return convertView;
	}

	private class ViewHolder{
		private TextView addressTextView;
		private ImageView icon;
	}

}
