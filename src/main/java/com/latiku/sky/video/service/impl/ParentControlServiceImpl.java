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
 * @since 2016-05-22
 * */
public class ParentControlServiceImpl implements ParentalControlService {

	private MovieService movieService;

	public ParentControlServiceImpl(MovieService movieService) {
		this.movieService = movieService;
	}

	@Override
	public boolean movieWatchControlResponse(String parentalControlLevel, String movieId) {

		boolean movieWatchControlResponse = true;
		int inputAgeLevel = 0;
		int movieAgeLevel = 0;
		String movieParentalControlLevel = null;

		if (NextGenUtil.isNumeric(parentalControlLevel)) {
			inputAgeLevel = Integer.valueOf(parentalControlLevel);
		} else {
			try {
				inputAgeLevel = AgeLevel.valueOf(parentalControlLevel).getValue();
			} catch (IllegalArgumentException iae) {
				movieWatchControlResponse = false;
				// The below message will be logged for monitoring purpose.
				System.out.println("The Parental Control Level Input : " + parentalControlLevel + ", is not valid!");
				throw iae;
			}
		}

		try {
			movieParentalControlLevel = movieService.getParentalControlLevel(movieId);
		} catch (TechnicalFailureException tfe) {
			movieWatchControlResponse = false;
			// The below message will be logged for monitoring purpose.
			System.out.println("Unfortunately there is a System Error. Sorry you cannot watch the movie!!");
		} catch (TitleNotFoundException tnfe) {
			movieWatchControlResponse = false;
			// The below message will be logged for monitoring purpose.
			System.out.println("The movie service cannot find the given movie " + movieId + "!!");
		}

		// If there are no exceptions in the input argument or the movieservice
		// response then process below logic.
		if (movieWatchControlResponse) {

			if (NextGenUtil.isNumeric(movieParentalControlLevel)) {
				movieAgeLevel = Integer.valueOf(movieParentalControlLevel);
			} else {
				try {
					movieAgeLevel = AgeLevel.valueOf(movieParentalControlLevel).getValue();
				} catch (IllegalArgumentException iae) {
					movieWatchControlResponse = false;
					// The below message will be logged for monitoring purpose.
					System.out.println("Unable to determine Parent Control Level for movie " + movieId + "!!");
					throw iae;
				}
			}
			
			//Determining whether movie age level is less than or equal to input age level.
			movieWatchControlResponse = this.isAbleToWatchMovie(inputAgeLevel, movieAgeLevel);
		}

		return movieWatchControlResponse;
	}

	/**
	 * This method will determine if the movie service age level is less than or
	 * equal to input age level to watch the movie.
	 * 
	 * @param inputAgeLevel
	 *            Age level given as input by user.
	 * @param movieAgeLevel
	 *            Age level for the movie watch given by movie service.
	 * @return
	 */
	private boolean isAbleToWatchMovie(int inputAgeLevel, int movieAgeLevel) {
		return movieAgeLevel <= inputAgeLevel ? true : false;
	}
}