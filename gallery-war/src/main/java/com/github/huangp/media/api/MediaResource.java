package com.github.huangp.media.api;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.media.model.Media;
import com.github.huangp.media.service.EJBMediaSearchServiceImpl;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@RequestScoped
@Path("media")
@Consumes("application/json")
@Produces("application/json")
public class MediaResource {
    private static final Logger log =
            LoggerFactory.getLogger(MediaResource.class);

    @Inject
    private EJBMediaSearchServiceImpl mediaSearchService;

    @GET
    public Response getAll() {
        List<Media> result = mediaSearchService.getAllMedia();
        log.info("=====: {}", result);
        return Response.ok(result).build();
    }

    @GET
    @Path("all")
    public Response getAllAsJson() {
        String result = mediaSearchService.getAllMediaAsJSON();
        log.info("=====: {}", result);
        return Response.ok(result).build();
    }
}
