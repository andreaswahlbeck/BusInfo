package net.sandbox.busservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sandbox.busservice.data.BusLine;
import net.sandbox.busservice.data.BusRouteInfo;
import net.sandbox.busservice.data.Position;

@Path("busservice")
@Consumes(MediaType.APPLICATION_JSON)
public class BusService {
	
	private static BusLineManager busLineManager = new BusLineManager();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public List<BusLine> getLines() {
		System.out.println("fetching available lines");
        return busLineManager.getLines();
    }
	
	@GET
	@Path("route/{lineNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public BusRouteInfo getLine(@PathParam("lineNumber") String lineNumber){
		System.out.println("Getting route info for: " + lineNumber);
		return busLineManager.getInfoForLine(lineNumber);
	}
	
	@GET
	@Path("position/{lineNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Position getCurrent(@PathParam("lineNumber") String lineNumber){
		System.out.println("Getting position for: " + lineNumber);
		
		return new Position();
	}
	
}
