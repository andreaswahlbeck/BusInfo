package net.sandbox.busservice;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class BusLocationCalculatorTest {
	
	private static final int TEST_ROUTE_DISTANCE = 1500;
	private Calendar testTimeCalendar = Calendar.getInstance();
	private BusLocationCalculator busLocationCalculator;
	
	@Before
	public void setUp() {
		testTimeCalendar.setTime(new Date());
		busLocationCalculator = new BusLocationCalculator();
	}
	
	@Test
	public void test_get_start_time_for_active_route() {
		testTimeCalendar.set(Calendar.HOUR_OF_DAY, 10);
		testTimeCalendar.set(Calendar.MINUTE, 0);
		testTimeCalendar.set(Calendar.SECOND, 0);
		testTimeCalendar.clear(Calendar.MILLISECOND);
	
		setCurrentTimeOfCalculatorToTestTime();
		long startTime = busLocationCalculator.getRouteStartTime().getTimeInMillis();
		
		assertEquals(testTimeCalendar.getTimeInMillis(), startTime);
		testTimeCalendar.add(Calendar.MINUTE, 2);
		setCurrentTimeOfCalculatorToTestTime();
		assertEquals(startTime, busLocationCalculator.getRouteStartTime().getTimeInMillis());
		
		testTimeCalendar.set(Calendar.MINUTE, 13);
		setCurrentTimeOfCalculatorToTestTime();
		assertEquals(12, busLocationCalculator.getRouteStartTime().get(Calendar.MINUTE));
	}
	
	@Test
	public void test_get_elapsed_time_since_route_start() {
		testTimeCalendar.set(Calendar.HOUR_OF_DAY, 10);
		testTimeCalendar.set(Calendar.MINUTE, 0);
		testTimeCalendar.set(Calendar.SECOND, 0);
		
		long startTime = testTimeCalendar.getTimeInMillis() / 1000;
		testTimeCalendar.set(Calendar.MINUTE, 2);
		testTimeCalendar.set(Calendar.SECOND, 20);
		
		setCurrentTimeOfCalculatorToTestTime();
		
		long currentTime = testTimeCalendar.getTimeInMillis() / 1000;
		long secondsSinceStartExpexted = currentTime - startTime;
		
		assertEquals(secondsSinceStartExpexted, busLocationCalculator.getTimeInSecondsSinceRouteStart());
	}
	
	@Test
	public void get_distance_since_route_start() {
		testTimeCalendar.set(Calendar.HOUR_OF_DAY, 10);
		testTimeCalendar.set(Calendar.MINUTE, 2);
		testTimeCalendar.set(Calendar.SECOND, 20);
		
		setCurrentTimeOfCalculatorToTestTime();
		long elapsedTime = 140;
		busLocationCalculator.setTotalRouteDistance(TEST_ROUTE_DISTANCE);
		// s = v * t
		// v = totalDistance / routeTime(240s) 
		long expectedDistance = elapsedTime * (TEST_ROUTE_DISTANCE/BusLocationCalculator.ROUTE_TIME);
		assertEquals(expectedDistance, busLocationCalculator.distanceSinceRouteStart());
		
	}
	
	private void setCurrentTimeOfCalculatorToTestTime() {
		System.out.println("Setting calculator time: " + testTimeCalendar.getTime());
		busLocationCalculator.setCurrentTime(testTimeCalendar.getTime());
	}

}
