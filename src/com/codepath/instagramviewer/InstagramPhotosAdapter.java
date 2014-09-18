package com.codepath.instagramviewer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.text.Html;
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
		TextView viewAll;
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
			viewHolder.viewAll = (TextView) convertView
					.findViewById(R.id.lvViewAll);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate subviews with correct data
		viewHolder.userName.setText(photo.getUsername()); // username
		viewHolder.imagePhoto.getLayoutParams().height = photo.getHeight(); //height
		viewHolder.imagePhoto.setImageResource(0); // image
		viewHolder.likes.setText(photo.getLikesCount() + " "
				+ getContext().getResources().getString(R.string.likes)); // likes
		viewHolder.tvCaption.setText(Html.fromHtml("<b>" + photo.getUsername()
				+ "</b>" + " " + photo.getCaption())); // caption
		viewHolder.viewAll.setText(getContext().getResources().getString(
				R.string.viewAll, photo.getLikesCount())); // view all comments

		ArrayList<InstagramComment> comment = photo.getComments();
		int length = comment.size();

		if (length >= 2) {
			viewHolder.userComment1.setText(comment.get(length - 1)
					.getUsername() + " ");
			viewHolder.comments1.setText(comment.get(length - 1)
					.getCommentText());
			viewHolder.userComment2.setText(comment.get(length - 2)
					.getUsername() + " ");
			viewHolder.comments2.setText(comment.get(length - 2)
					.getCommentText());
		} else {
			viewHolder.userComment1.setVisibility(View.GONE);
			viewHolder.comments1.setVisibility(View.GONE);
			viewHolder.userComment2.setVisibility(View.GONE);
			viewHolder.comments2.setVisibility(View.GONE);
		}

		viewHolder.userProfilePic.setImageResource(0);
		viewHolder.createdTime.setText(getFormatedTime(photo.getCreatedTime()));

		Picasso.with(getContext()).load(photo.getImage_url())
				.into(viewHolder.imagePhoto);
		Picasso.with(getContext()).load(photo.getUser_profile_pic())
				.into(viewHolder.userProfilePic);
		return convertView;
	}

	public String getFormatedTime(long createdTime) {
		String formattedTime = "";
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(createdTime);
		Date creationTime = instance.getTime();

		long diffInMs = (new Date(System.currentTimeMillis()).getTime() / 1000)
				- creationTime.getTime();
		long diffInHours = TimeUnit.SECONDS.toHours(diffInMs);
		long diffInMins = TimeUnit.SECONDS.toMinutes(diffInMs);
		long diffInDays = TimeUnit.SECONDS.toDays(diffInMins);

		if (diffInMins < 60) {
			formattedTime = diffInMins + "m";
		} else if (diffInHours >= 1 && diffInHours < 24) {
			formattedTime = diffInHours + "h";
		} else if (diffInDays >= 1) {
			formattedTime = diffInDays +"d";
		}

		return formattedTime;
	}
}