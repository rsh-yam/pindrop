package pindrop;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import pindrop.core.Details;

@Path("/metrics")
public class Metrics {
	@GET
	@Produces("application/xml")
	public String metrics() {
		String mem = "<mem>" + Details.getMem() + "</mem>";
		String cpu = Details.getCPU();
		String disk = Details.getDisk();
		return "<system>" + mem + cpu + disk + "</system>";
	}

	@Path("{c}")
	@GET
	@Produces("application/xml")
	public String metrics(@PathParam("c") String str) {
		return "<demo>" + "<value>" + str + "</value>" + "</demo>";
	}
}