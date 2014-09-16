package com.codepath.instagramviewer;

import java.util.ArrayList;

public class InstagramPhoto {
	// username, caption, image_url, height, likes_count
	public String username;
	public String caption;
	public String image_url;
	public String user_profile_pic;
	public int height;
	public int likesCount;
	public long createdTime;
	public ArrayList<InstagramComment> comments;

	public ArrayList<InstagramComment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<InstagramComment> comments) {
		this.comments = comments;
	}

	public String toString(){
		return image_url;
	}
}
