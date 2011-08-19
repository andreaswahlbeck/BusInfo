package net.sandbox.busservice.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BusRouteInfo {

	private BusLine busline;
	private RouteInfo routeInfo;

	public BusRouteInfo(){}

	public BusRouteInfo(BusLine busline, RouteInfo routeInfo) {
		this.busline = busline;
		this.routeInfo = routeInfo;
	}

	public BusLine getBusline() {
		return busline;
	}

	public void setBusline(BusLine busline) {
		this.busline = busline;
	}

	public RouteInfo getRouteInfo() {
		return routeInfo;
	}

	public void setRouteInfo(RouteInfo routeInfo) {
		this.routeInfo = routeInfo;
	}
}
