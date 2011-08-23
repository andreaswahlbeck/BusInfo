package net.sandbox.busservice;

import geo.google.datamodel.GeoAltitude;
import geo.google.datamodel.GeoCoordinate;
import geo.google.datamodel.GeoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sandbox.busservice.data.BusLine;
import net.sandbox.busservice.data.BusRouteInfo;
import net.sandbox.busservice.data.Position;
import net.sandbox.busservice.data.RouteInfo;

public class BusLineManager {

	public static double MILE_IN_METERS = 1609.344; 
	
	public static final String TREAN = "Trean";
	public static final String TVAAN = "Tvåan";
	public static final String ETTAN = "Ettan";
	
	private static BusLine one = new BusLine(ETTAN, "1");
	private static BusLine two = new BusLine(TVAAN, "2");
	private static BusLine three = new BusLine(TREAN, "3");

	private static Map<String,BusLine> busLines = new HashMap<String, BusLine>();
	
	{
		busLines.put(one.getLineNumber(), one);
		busLines.put(two.getLineNumber(), two);
		busLines.put(three.getLineNumber(),three);
	}
	
	private RouteLoader routeLoader = new RouteLoader();
	{
		routeLoader.init();
	}
	
	public List<BusLine> getLines() {
		return new ArrayList<BusLine>(busLines.values());
	}
	
	public BusRouteInfo getInfoForLine(String lineNumber) {
		
		if (routeLoader.getRoutes().containsKey(lineNumber)) {
			
			RouteInfo routeInfo = routeLoader.getRoutes().get(lineNumber);
			BusLine busLine = busLines.get(lineNumber);
			
			return new BusRouteInfo(busLine, routeInfo);
		}
		
		return null;
	}

	/*
	 * this one isn't perfect since it's hard faking a real position...
	 */
	public Position getPositionForLine(String lineNumber) {
		
		if (routeLoader.getRoutes().containsKey(lineNumber)) {
			RouteInfo routeInfo = routeLoader.getRoutes().get(lineNumber);
			
			List<Position> routePolyLine = routeInfo.getRoutePolyLine();
			BusLocationCalculator busLocationCalculator = new BusLocationCalculator();
			busLocationCalculator.setTotalRouteDistance((long) routeInfo.getRouteDistance());
			
			double distanceSinceRouteStart = busLocationCalculator.distanceSinceRouteStart();
//			System.out.println("Route distance: " + routeInfo.getRouteDistance());
//			System.out.println("Distance since start: " + distanceSinceRouteStart);
			
			// find point on route
			GeoCoordinate lastCoordinate = null;
			for (Position position : routePolyLine) {
				GeoCoordinate currentCoordinate = new GeoCoordinate(Double.parseDouble(position.getLongitude()), Double.parseDouble(position.getLatitude()), new GeoAltitude(0));
				
				if (lastCoordinate != null) {
					double distanceBetweenPoints = GeoUtils.distanceBetweenInKm(lastCoordinate, currentCoordinate) * 1000;
					//System.out.println("Distance between: (" + lastCoordinate.toString() + " , " + currentCoordinate.toString() + ") in meters " + distanceBetweenPoints);
					if (distanceBetweenPoints < distanceSinceRouteStart) {
						distanceSinceRouteStart -= distanceBetweenPoints;
					} else  {
						// get position from point one on the line between point one and two after distance meters
						// for now return a point from the line...
						return position;
					}
				}
				lastCoordinate = currentCoordinate;
			}
		}
		return null;
	}
}
