package com.rs.ms.fb.utils;

/**
 * 
 * @author ManasC
 *
 */
public class DataUrls {

	public static final String me = "first_name,last_name,email,location,hometown,work,education";
	public static final String commentData = "comments{comment_count,message,like_count,from},likes,message,comment_count,like_count,from";
											//comment_count,comments{comments,comment_count,likes,like_count},like_count,likes,from,message
	public static final String mePage = "username,location,about,affiliation,emails,phone,fan_count,global_brand_page_name,has_added_app,hometown,members,products";
	public static final String meEvent = "attending_count,attending{rsvp_status},interested_count,interested{rsvp_status},declined_count,declined{rsvp_status},comments,roles,description,name,place,maybe_count,owner,ticket_uri,noreply_count,maybe,noreply";
	public static final String postComments="comments.limit(1){comments,likes,message,from},likes,message,from";
	//comments{comments,likes},likes,message
}
