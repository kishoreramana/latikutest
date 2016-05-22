package com.latiku.sky.video.service;

/* This service interface accepts the customer’s parental control level
 * preference and a movie id. And indicates whether the customer is able to watch the movie 
 * with that parental control level preference.
 * 
 * @author Kishore Ramana
 * @version 1.0
 * @since 2016-05-21
 * */
public interface ParentalControlService {

	/**
	 * This method will accept Parent Control Level preference of the client and
	 * the Movie Id as inputs. And returns a boolean value indicating if a
	 * client can watch a movie with the control level preference.
	 * 
	 * @param parentalControlLevel
	 *            String containing the Parent Control Level Preference.
	 * @param movieId
	 *            String containing Movie Id.
	 * @return boolean True if customer can watch the movie else false.
	 */
	boolean movieWatchControlResponse(String parentalControlLevel, String movieId);
}