package com.github.huangp.media.api;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import com.github.huangp.media.service.EJBMediaSearchServiceImpl;
import com.github.huangp.media.service.MediaSearchService;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@Path("media")
@Consumes("application/json")
@Produces("application/json")
public class MediaResource {

    @Inject
    private EJBMediaSearchServiceImpl mediaSearchService;

    @GET
    public Response getAll() {
        List<String> result = mediaSearchService.getAllMedia();
        GenericEntity<List<String>> entity = new GenericEntity<List<String>>(result) {};
        return Response.ok(entity).build();
    }
}
