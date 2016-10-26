package com.taishi.foursquareapiforandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.taishi.foursquareapiforandroid.Model.Explore.Item_;

import java.util.List;


/**
 * Created by yamasakitaishi on 2016/10/20.
 */

public class ExploreListAdapter extends ArrayAdapter<Item_> {
	private LayoutInflater layoutInflater_;

	public ExploreListAdapter(Context context, int layout, List<Item_> objects) {
		super(context, layout, objects);
		layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 特定の行(position)のデータを得る
		Item_ item_ = getItem(position);

		String name = item_.getVenue().getName();
		double point = item_.getVenue().getRating();
		String genre = item_.getVenue().getCategories().get(0).getName();
		int distance = item_.getVenue().getLocation().getDistance();
		String comment = item_.getTips().get(0).getText();



		// convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		if (null == convertView) {
			convertView = layoutInflater_.inflate(R.layout.item_list, null);
		}

		TextView tvName = (TextView) convertView.findViewById(R.id.tv_item_name);
		TextView tvPoint = (TextView) convertView.findViewById(R.id.tv_list_point);
		TextView tvGenre = (TextView) convertView.findViewById(R.id.tv_item_genre);
		TextView tvDistance = (TextView) convertView.findViewById(R.id.tv_item_distance);
		TextView tvComment = (TextView) convertView.findViewById(R.id.tv_item_comment);



		tvName.setText(name);
		tvPoint.setText(String.valueOf(point));
		tvGenre.setText(genre);
		tvDistance.setText(String.valueOf(distance	));
		tvComment.setText(comment);

		return convertView;
	}
}