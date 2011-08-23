package net.sandbox.busservice.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RouteInfo {
	private List<BusStop> busStops;
	private List<Position> routePolyLine;
	private double routeDistance;
	
	public RouteInfo(){
		this.busStops = new ArrayList<BusStop>();
		this.routePolyLine = new ArrayList<Position>();
	}
	public RouteInfo(List<BusStop> busStops) {
		this();
		this.busStops.addAll(busStops);
	}
	public List<BusStop> getBusStops() {
		return busStops;
	}
	public void setBusStops(List<BusStop> busStops) {
		this.busStops = busStops;
	}
	
	public List<Position> getRoutePolyLine() {
		return routePolyLine;
	}
	public void setRoutePolyLine(List<Position> routePolyLine) {
		this.routePolyLine = routePolyLine;
	}
	
	public double getRouteDistance() {
		return routeDistance;
	}
	public void setRouteDistance(double routeDistance) {
		this.routeDistance = routeDistance;
	}
	public void addBusStop(BusStop busStop) {
		this.busStops.add(busStop);
	}
	
	public void addRoutePoint(Position position) {
		this.routePolyLine.add(position);
	}
	
}
