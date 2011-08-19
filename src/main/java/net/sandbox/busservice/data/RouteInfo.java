package net.sandbox.busservice.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RouteInfo {
	private List<BusStop> busStops;
	
	public RouteInfo(){
		this.busStops = new ArrayList<BusStop>();
	}
	public RouteInfo(List<BusStop> busStops) {
		this();
		this.busStops = busStops;
	}
	public List<BusStop> getBusStops() {
		return busStops;
	}
	public void setBusStops(List<BusStop> busStops) {
		this.busStops = busStops;
	}
	
	public void addBusStop(BusStop busStop) {
		this.busStops.add(busStop);
	}
	
}
