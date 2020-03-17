package hyve.petshow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class ExampleResource {
    static Logger logger = LogManager.getLogger(ExampleResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        logger.debug("debug");
        logger.error("error");
        logger.info("info");
        logger.fatal("fatal");
        logger.warn("warn");
        logger.trace("trace");

        return "hellasao";

    }
}