package com.github.huangp.media.api;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.media.model.Media;
import com.github.huangp.media.model.MediaFileType;
import com.github.huangp.media.service.MediaSearchService;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@RequestScoped
@Path("media")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MediaResource {
    private static final Logger log =
            LoggerFactory.getLogger(MediaResource.class);

    @Inject
    private MediaSearchService mediaSearchService;

    @Context
    private SecurityContext securityContext;

    @GET
    public Response getAll() {
        List<Media> result = mediaSearchService.getAllMedia();
//        log.info("=====: {}", result);
        log.info("security context: {}", securityContext.getUserPrincipal());
        return Response.ok(result).build();
    }

    @GET
    @Path("all")
    public Response getAllAsJson() {
        String result = mediaSearchService.getAllMediaAsJSON();
        log.info("=====: {}", result);
        return Response.ok(result).build();
    }

    @GET
    @Path("search")
    public Response search(@QueryParam("from") String fromDate,
            @QueryParam("to") @DefaultValue("now") String toDate,
            @QueryParam("type") @DefaultValue("all") MediaFileType mediaType) {
        // TODO validate from and to date
        String finalToDate = toDate;
        if ("now".equalsIgnoreCase(toDate)) {
           finalToDate = null;
        }
        String result = mediaSearchService.search(fromDate, finalToDate, mediaType);
        log.info("=====: {}", result);
        return Response.ok(result).build();
    }
}
