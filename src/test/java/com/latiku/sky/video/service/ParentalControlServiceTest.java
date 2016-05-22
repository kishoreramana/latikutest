/**
 * 
 */
package com.latiku.sky.video.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.latiku.sky.video.exceptions.TechnicalFailureException;
import com.latiku.sky.video.exceptions.TitleNotFoundException;
import com.latiku.sky.video.service.impl.ParentControlServiceImpl;

/**
 * @author Kishore Ramana
 * @version 1.0
 * @since 2016-05-22
 *
 */
public class ParentalControlServiceTest {

	private static final String PARENTAL_CONTROL_LEVEL_U = "U";
	private static final String PARENTAL_CONTROL_LEVEL_PG = "PG";
	private static final String PARENTAL_CONTROL_LEVEL_12 = "12";
	private static final String PARENTAL_CONTROL_LEVEL_15 = "15";
	private static final String PARENTAL_CONTROL_LEVEL_18 = "18";

	private static final String MOVIE_ID_U = "The Jungle Book";
	private static final String MOVIE_ID_PG = "Lord of the Rings";
	private static final String MOVIE_ID_12 = "Superman Vs Ironman";
	private static final String MOVIE_ID_15 = "XMen Apocalypse";
	private static final String MOVIE_ID_18 = "Hansel Vs Greetel";

	private ParentalControlService parentControlService;

	private MovieService movieService;

	@Before
	public void setUp() {
		movieService = Mockito.mock(MovieService.class);
		parentControlService = new ParentControlServiceImpl(movieService);
	}

	@After
	public void tearDown() {
		movieService = null;
		parentControlService = null;
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level 3 and movie age level 'U'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlService3_NoWatch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_U)).thenReturn(PARENTAL_CONTROL_LEVEL_U);
		assertEquals(false, parentControlService.movieWatchControlResponse("3", MOVIE_ID_U));
		verify(movieService).getParentalControlLevel(MOVIE_ID_U);
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level 'U' and movie age level 'U'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlServiceU_Watch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_U)).thenReturn(PARENTAL_CONTROL_LEVEL_U);
		assertEquals(true, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_U, MOVIE_ID_U));
		verify(movieService).getParentalControlLevel(MOVIE_ID_U);
	}

	/**
	 * This test method will test the parental control service method for
	 * invalid input age level 'asdfasdf' and movie age level 'U' with an
	 * expected result of no watch movie and illegalargumentexception thrown for
	 * invalid input age level.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParentalControlService_NoWatch_InputArgException()
			throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_U)).thenReturn(PARENTAL_CONTROL_LEVEL_U);
		parentControlService.movieWatchControlResponse("asdfasdf", MOVIE_ID_U);
		verify(movieService).getParentalControlLevel(MOVIE_ID_U);
	}

	/**
	 * This test method will test the parental control service method for valid
	 * age level and movie age level 'U' and for an invalid movie id. expected
	 * result of no watch movie and TitleNotFoundException logged for monitoring
	 * purpose.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testParentalControlService_NoWatch_TitleNotFound()
			throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel("Title Not Found Movie")).thenThrow(TitleNotFoundException.class);
		assertEquals(false,
				parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_U, "Title Not Found Movie"));
		verify(movieService).getParentalControlLevel("Title Not Found Movie");
	}

	/**
	 * This test method will test the parental control service method for valid
	 * age level and movie age level 'U' and for a technical failure in movie
	 * service. expected result of no watch movie and TechnicalFailureException
	 * logged for monitoring purpose.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testParentalControlService_NoWatch_TechnicalFailure()
			throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel("Technical Failure Movie"))
				.thenThrow(TechnicalFailureException.class);
		assertEquals(false,
				parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_U, "Technical Failure Movie"));
		verify(movieService).getParentalControlLevel("Technical Failure Movie");
	}

	/**
	 * This test method will test the parental control service method for valid
	 * input age level and invalid movie age level response with an expected
	 * result of no watch movie and illegalargumentexception thrown for invalid
	 * movie age level response.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParentalControlService_NoWatch_MovieServiceRspInvalid()
			throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel("Movie Service Unable to determine Movie"))
				.thenReturn("dafasewatregt");
		assertEquals(false, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_U,
				"Movie Service Unable to determine Movie"));
		verify(movieService).getParentalControlLevel("Movie Service Unable to determine Movie");
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level PG and movie age level 'PG'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlServicePG_Watch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_PG)).thenReturn(PARENTAL_CONTROL_LEVEL_PG);
		assertEquals(true, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_PG, MOVIE_ID_PG));
		verify(movieService).getParentalControlLevel(MOVIE_ID_PG);
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level U and movie age level 'PG'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlServicePG_NoWatch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_PG)).thenReturn(PARENTAL_CONTROL_LEVEL_PG);
		assertEquals(false, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_U, MOVIE_ID_PG));
		verify(movieService).getParentalControlLevel(MOVIE_ID_PG);
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level 12 and movie age level '12'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlService12_Watch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_12)).thenReturn(PARENTAL_CONTROL_LEVEL_12);
		assertEquals(true, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_12, MOVIE_ID_12));
		verify(movieService).getParentalControlLevel(MOVIE_ID_12);
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level PG and movie age level '12'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlService12_NoWatch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_12)).thenReturn(PARENTAL_CONTROL_LEVEL_12);
		assertEquals(false, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_PG, MOVIE_ID_12));
		verify(movieService).getParentalControlLevel(MOVIE_ID_12);
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level 15 and movie age level '15'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlService15_Watch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_15)).thenReturn(PARENTAL_CONTROL_LEVEL_15);
		assertEquals(true, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_15, MOVIE_ID_15));
		verify(movieService).getParentalControlLevel(MOVIE_ID_15);
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level U and movie age level '15'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlService15_NoWatch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_15)).thenReturn(PARENTAL_CONTROL_LEVEL_15);
		assertEquals(false, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_U, MOVIE_ID_15));
		verify(movieService).getParentalControlLevel(MOVIE_ID_15);
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level 18 and movie age level '18'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlService18_Watch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_18)).thenReturn(PARENTAL_CONTROL_LEVEL_18);
		assertEquals(true, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_18, MOVIE_ID_18));
		verify(movieService).getParentalControlLevel(MOVIE_ID_18);
	}

	/**
	 * This test method will test the parental control service method for input
	 * age level 12 and movie age level '18'.
	 * 
	 * @throws TitleNotFoundException
	 *             Thrown by movie service mocking.
	 * @throws TechnicalFailureException
	 *             Thrown by movie service mocking.
	 */
	@Test
	public void testParentalControlService18_NoWatch() throws TitleNotFoundException, TechnicalFailureException {
		when(movieService.getParentalControlLevel(MOVIE_ID_18)).thenReturn(PARENTAL_CONTROL_LEVEL_18);
		assertEquals(false, parentControlService.movieWatchControlResponse(PARENTAL_CONTROL_LEVEL_12, MOVIE_ID_18));
		verify(movieService).getParentalControlLevel(MOVIE_ID_18);
	}
}
