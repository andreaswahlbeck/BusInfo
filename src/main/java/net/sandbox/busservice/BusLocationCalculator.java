package net.sandbox.busservice;

import java.util.Calendar;
import java.util.Date;

/*
 * Calculations of a simulated bus route that takes 4 minutes.
 * A new route starts every four minutes.
 * The route takes 4 minutes.
 */
public class BusLocationCalculator {

	public static final long ROUTE_TIME = 240;
	private Date currentTime;
	// route distance in meters
	private long totalRouteDistance;
	
	public BusLocationCalculator() {
		this.currentTime = new Date();
	}
	
	public BusLocationCalculator(long routeDistance) {
		this();
		this.totalRouteDistance = routeDistance;
	}
	
	/*
	 * A route starts at X hour and 0 minutes and then there is a new route every 4 minutes 
	 */
	public Calendar getRouteStartTime() {
		Calendar startTimeCalendar = Calendar.getInstance();
		Calendar currentTimeCalendar = Calendar.getInstance();
		currentTimeCalendar.setTime(this.currentTime);
		startTimeCalendar.setTime(currentTimeCalendar.getTime());
		startTimeCalendar.set(Calendar.MINUTE, (currentTimeCalendar.get(Calendar.MINUTE) / 4) * 4);
		startTimeCalendar.set(Calendar.SECOND, 0);
		startTimeCalendar.clear(Calendar.MILLISECOND);
		System.out.println("Returning starttime: " + startTimeCalendar.getTime());
		return startTimeCalendar;
	}
	
	public long getTimeInSecondsSinceRouteStart() {
		System.out.println("Current time: " + this.currentTime + " route start time: " + getRouteStartTime().getTime());
		return (this.currentTime.getTime() / 1000) - (getRouteStartTime().getTimeInMillis() / 1000);
	}
	/*
	 * return the amount of meters since route start
	 */
	public long distanceSinceRouteStart() {
		return getSpeed() * getTimeInSecondsSinceRouteStart();
	}

	private long getSpeed() {
		return this.totalRouteDistance / ROUTE_TIME;
	}
	
	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public long getTotalRouteDistance() {
		return totalRouteDistance;
	}

	public void setTotalRouteDistance(long totalRouteDistance) {
		this.totalRouteDistance = totalRouteDistance;
	}
	
}
