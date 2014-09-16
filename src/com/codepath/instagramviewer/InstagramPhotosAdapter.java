package com.codepath.instagramviewer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

	private static class ViewHolder {
		TextView tvCaption;
		TextView userName;
		TextView likes;
		CircularImageView userProfilePic;
		ImageView imagePhoto;
		TextView userComment1;
		TextView comments1;
		TextView userComment2;
		TextView comments2;
		TextView createdTime;
	}

	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, R.layout.item_photo, photos);
	}

	// takes a data item at a position and converts it to a row in a list view
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the item
		InstagramPhoto photo = getItem(position);
		ViewHolder viewHolder; // view lookup cache stored in tag
		// Check if we are using a recycled item
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_photo, parent, false);

			// Lookup the subview within the templete
			viewHolder.userProfilePic = (CircularImageView) convertView
					.findViewById(R.id.profilePicture);
			viewHolder.tvCaption = (TextView) convertView
					.findViewById(R.id.lvCaption);
			viewHolder.imagePhoto = (ImageView) convertView
					.findViewById(R.id.imagePhoto);
			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.userName);
			viewHolder.likes = (TextView) convertView.findViewById(R.id.likes);
			viewHolder.userComment1 = (TextView) convertView
					.findViewById(R.id.lvUserId1);
			viewHolder.comments1 = (TextView) convertView
					.findViewById(R.id.lvComment1);
			viewHolder.userComment2 = (TextView) convertView
					.findViewById(R.id.lvUserId2);
			viewHolder.comments2 = (TextView) convertView
					.findViewById(R.id.lvComment2);
			viewHolder.createdTime = (TextView) convertView
					.findViewById(R.id.time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate subviews with correct data
		viewHolder.userName.setText(photo.username); // username
		viewHolder.imagePhoto.getLayoutParams().height = photo.height;
		viewHolder.imagePhoto.setImageResource(0); // image
		viewHolder.likes.setText(photo.likesCount + " likes"); // likes
		viewHolder.tvCaption.setText(photo.caption); // caption

		ArrayList<InstagramComment> comment = photo.getComments();
		int length = comment.size();

		if (comment.get(length - 1).username != null
				&& comment.get(length - 1).commentText != null) {
			viewHolder.userComment1.setText(comment.get(length - 1).username+ " ");
			viewHolder.comments1.setText(comment.get(length - 1).commentText);
		} else {
			viewHolder.userName.setVisibility(View.GONE);
			viewHolder.comments1.setVisibility(View.GONE);
		}
		viewHolder.userComment2.setText(comment.get(length - 2).username + " ");
		viewHolder.comments2.setText(comment.get(length - 2).commentText);
		viewHolder.userProfilePic.setImageResource(0);
		viewHolder.createdTime.setText(DateUtils.getRelativeTimeSpanString(
				photo.createdTime * 1000, System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS));

		// ask for the photo to be added to the imageview based on the photo url
		// send a network request to the url download the image bytes, convert
		// into bitmap, insert bitmap into imageview
		Picasso.with(getContext()).load(photo.image_url)
				.into(viewHolder.imagePhoto);
		Picasso.with(getContext()).load(photo.user_profile_pic)
				.into(viewHolder.userProfilePic);
		return convertView;
	}
}