package com.codepath.instagramviewer;

import java.util.ArrayList;

public class InstagramPhoto {
	private String username;
	private String caption;
	private String imageUrl;
	private String userProfilePic;
	private int height;
	private int likesCount;
	private long createdTime;
	private ArrayList<InstagramComment> comments;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImage_url() {
		return imageUrl;
	}

	public void setImage_url(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUser_profile_pic() {
		return userProfilePic;
	}

	public void setUser_profile_pic(String userProfilePic) {
		this.userProfilePic = userProfilePic;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public ArrayList<InstagramComment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<InstagramComment> comments) {
		this.comments = comments;
	}

	public String toString(){
		return imageUrl;
	}
}
