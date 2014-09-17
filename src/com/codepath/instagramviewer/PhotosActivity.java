package com.codepath.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class PhotosActivity extends Activity {

	public static final String CLIENT_ID = "7faf4f3abca5446eb037629c1a334994";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;
	private ArrayList<InstagramComment> comments;
	
	PullToRefreshListView lvPhotos;
	AsyncHttpClient client;
	String popularUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);

		fetchPopularPhotos();
	}

	private void fetchPopularPhotos() {
		photos = new ArrayList<InstagramPhoto>();
		aPhotos = new InstagramPhotosAdapter(this, photos);
		
		// ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
		lvPhotos = (PullToRefreshListView) findViewById(R.id.lvPhotos);
		lvPhotos.setAdapter(aPhotos);
		// setup popular url endpoint v bv

		popularUrl = "https://api.instagram.com/v1/media/popular?client_id="
				+ CLIENT_ID;

		// Create the network client
		client = new AsyncHttpClient();
		fetchTimelineAsync(0);
		lvPhotos.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Your code to refresh the list here.
				// Make sure you call listView.onRefreshComplete() when
				// once the network request has completed successfully.
				fetchTimelineAsync(0);
			}
		});
	}

	// Trigger the network request
	public void fetchTimelineAsync(int page) {
		client.get(popularUrl, new JsonHttpResponseHandler() {

			// define success & failure callbacks
			// handle the successful response(popular photos JSON)
			// @Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// Fired once successful response back
				// url, height, username, caption
				// { "data" => [x] => "images" => "standard_resolution" => url}
				// { "data" => [x] => "images" => "standard_resolution" =>
				// height}
				// { "data" => [x] => "user" => "username"}
				// { "data" => [x] => "caption" => "text"}
				// {"data" => [x] => "comments" => "data" => "from" =>
				// "username"}
				// {"data" => [x] => "comments" => "data" => "text"}
				// Log.i("INFO", response.toString());
				JSONArray photosJSON = null;
				//JSONObject commentJSON = new JSONObject();
				try {
					photos.clear();
					photosJSON = response.getJSONArray("data");
					for (int i = 0; i < photosJSON.length(); i++) {
						JSONObject photoJSON = photosJSON.getJSONObject(i);
						InstagramPhoto photo = new InstagramPhoto();
						photo.username = photoJSON.getJSONObject("user")
								.getString("username");
						photo.image_url = photoJSON.getJSONObject("images")
								.getJSONObject("standard_resolution")
								.getString("url");
						photo.height = photoJSON.getJSONObject("images")
								.getJSONObject("standard_resolution")
								.getInt("height");
						photo.createdTime = photoJSON.getInt("created_time");
						Object menuObject = photoJSON.get("caption");

						if (menuObject != JSONObject.NULL) {
							// Skip processing the response Data
							photo.caption = photoJSON.getJSONObject("caption")
									.getString("text");
						}
						photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
						photo.user_profile_pic = photoJSON.getJSONObject("user").getString(
										"profile_picture");

						JSONObject jsonComm = photoJSON.getJSONObject("comments");
						JSONArray jsonCommArr = jsonComm.getJSONArray("data");
						
						comments = new ArrayList<InstagramComment>();
						for (int j = 0; j < jsonCommArr.length(); j++) {
							JSONObject commentsJSON = jsonCommArr
									.getJSONObject(j);
							InstagramComment comm = new InstagramComment();
							
							comm.username = commentsJSON.getJSONObject("from").getString("username");
							comm.commentText = commentsJSON.getString("text");
							comments.add(comm);
						}
						photo.setComments(comments);
						photos.add(photo);
					}
					lvPhotos.onRefreshComplete();
					aPhotos.notifyDataSetChanged();
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}
}
