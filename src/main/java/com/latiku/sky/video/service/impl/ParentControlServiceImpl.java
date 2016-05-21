package com.latiku.sky.video.service.impl;

import com.latiku.sky.video.service.MovieService;
import com.latiku.sky.video.service.ParentalControlService;
import com.latiku.sky.video.exceptions.TechnicalFailureException;
import com.latiku.sky.video.exceptions.TitleNotFoundException;
import com.latiku.sky.video.util.AgeLevel;
import com.latiku.sky.video.util.NextGenUtil;

/* This service implementation of ParentalControlService Interface which accepts customer’s 
 * parental control level preference and a movie id. And indicates whether the customer is 
 * able to watch the movie with that parental control level preference.
 * 
 * @author Kishore Ramana
 * @version 1.0
 * @since 2015-12-10
 * */
public class ParentControlServiceImpl implements ParentalControlService {

	private MovieService movieService;

	public ParentControlServiceImpl(MovieService movieService) {
		this.movieService = movieService;
	}

	@Override
	public String movieWatchControlResponse(String parentalControlLevel,
			String movieId) {

		String movieWatchControlResponse = null;

		String movieParentalControlLevel = null;

		int inputAgeLevel = 0;
		int movieAgeLevel = 0;

		if (NextGenUtil.isNumeric(parentalControlLevel)) {
			inputAgeLevel = Integer.valueOf(parentalControlLevel);
		} else {
			try {
				inputAgeLevel = AgeLevel.valueOf(parentalControlLevel)
						.getValue();
			} catch (IllegalArgumentException iae) {
				movieWatchControlResponse = parentalControlLevel
						+ " is not valid!";
			}
		}

		try {
			movieParentalControlLevel = movieService
					.getParentalControlLevel(movieId);
		} catch (TechnicalFailureException e) {
			movieWatchControlResponse = "Unfortunately there is a System Error. "
					+ "Sorry you cannot watch the movie!!";
		} catch (TitleNotFoundException e) {
			movieWatchControlResponse = "The movie service cannot find the given movie "
					+ movieId;
		}

		if (NextGenUtil.isNumeric(movieParentalControlLevel)) {
			movieAgeLevel = Integer.valueOf(movieParentalControlLevel);
		} else {
			try {
				movieAgeLevel = AgeLevel.valueOf(movieParentalControlLevel)
						.getValue();
			} catch (IllegalArgumentException iae) {
				movieWatchControlResponse = "Unable to determine Parent Control Level for movie "
						+ movieId;
			}
		}

		if (null == movieWatchControlResponse) {
			if (this.isAbleToWatchMovie(inputAgeLevel, movieAgeLevel)) {
				movieWatchControlResponse = "You can watch the movie "
						+ movieId + "(" + movieParentalControlLevel + " )!!";
			} else {
				movieWatchControlResponse = "Sorry you cannot watch the movie "
						+ movieId + "(" + movieParentalControlLevel + ")!!";
			}
		}

		return movieWatchControlResponse;
	}

	private boolean isAbleToWatchMovie(int inputAgeLevel, int movieAgeLevel) {
		boolean isAbleToWatchMovie = movieAgeLevel <= inputAgeLevel ? true
				: false;
		return isAbleToWatchMovie;
	}
}