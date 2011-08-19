package net.sandbox.busservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sandbox.busservice.data.BusLine;
import net.sandbox.busservice.data.BusRouteInfo;
import net.sandbox.busservice.data.RouteInfo;

public class BusLineManager {

	
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
	
	public BusRouteInfo getInfoForLine(String line) {
		
		if (routeLoader.getRoutes().containsKey(line)) {
			
			RouteInfo routeInfo = routeLoader.getRoutes().get(line);
			BusLine busLine = busLines.get(line);
			
			return new BusRouteInfo(busLine, routeInfo);
		}
		
		return null;
	}
}
