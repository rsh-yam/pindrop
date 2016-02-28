package pindrop;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
 
@Path("/metrics")
public class Metrics {
	@GET
	@Produces("application/xml")
	public String metrics() {
 		return "<demo>" + "<value>" + "10" + "</value>" + "</demo>";
	}
 
	@Path("{c}")
	@GET
	@Produces("application/xml")
	public String metrics(@PathParam("c") String str) {
		return "<demo>" + "<value>" + str + "</value>" + "</demo>";
	}
}