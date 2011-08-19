package net.sandbox.busservice;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sandbox.busservice.data.BusStop;
import net.sandbox.busservice.data.Position;
import net.sandbox.busservice.data.RouteInfo;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

public class RouteLoader {

	Map<String,RouteInfo> routes = new HashMap<String,RouteInfo>();
	
	
	public void init() {
		loadRouteForEttan();
		loadRouteForTvaan();
	}

	private void loadRouteForTvaan() {
		InputStream ios = Thread.currentThread().getContextClassLoader().getResourceAsStream("tvaan.kml");
		if ( ios != null) {
			loadRoute("2", ios);
		}
	}

	private void loadRouteForEttan()  {
		InputStream ios = Thread.currentThread().getContextClassLoader().getResourceAsStream("ettan.kml");
		if ( ios != null) {
			loadRoute("1", ios);
		}
	}
	
	private void loadRoute(String lineId, InputStream kmlInputStream) {
		final Kml kml = Kml.unmarshal(kmlInputStream);
		Document document = (Document) kml.getFeature();
		RouteInfo route = new RouteInfo();
		for (Object feature : document.getFeature()) {

			if (feature instanceof Placemark) {
				Placemark placemark = (Placemark) feature;
				Geometry geometry = placemark.getGeometry();
				if (geometry instanceof Point) {
					Point point = (Point) geometry;
					Coordinate coordinate = point.getCoordinates().get(0);
					BusStop busStop = new BusStop(placemark.getName(), new Position(Double.toString(coordinate.getLatitude()), Double.toString(coordinate.getLongitude())));
					route.addBusStop(busStop);
					
				} else if (geometry instanceof LineString) {
				
				}
			}
		}
		routes.put(lineId, route);
	}

	public Map<String, RouteInfo> getRoutes() {
		return routes;
	}
	
}
