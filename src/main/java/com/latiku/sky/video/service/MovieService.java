package com.latiku.sky.video.service;

import com.latiku.sky.video.exceptions.TechnicalFailureException;
import com.latiku.sky.video.exceptions.TitleNotFoundException;

/* This is a sample showing the service interface exposed by the Movie Meta Data team.
 * The Movie Meta Data team is currently developing the MovieService
 * getParentalControlLevel call that accepts the movie id as an input and
 * returns the parental control level for that movie as a string.
 * 
 * @author Kishore Ramana
 * @version 1.0
 * @since 2015-12-10
 * */
public interface MovieService {

	/**
	 * This is a sample showing the method as part of MovieService interface
	 * exposed by the Movie Meta Data team.
	 * 
	 * @param movieId
	 *            String containing Movie Id.
	 * @return String containing the Parent Control Level.
	 * @throws TitleNotFoundException
	 *             if movieId is not found.
	 * @throws TechnicalFailureException
	 *             if any technical exception or failure occured while
	 *             processing the service request.
	 */
	String getParentalControlLevel(String movieId)
			throws TitleNotFoundException, TechnicalFailureException;
}