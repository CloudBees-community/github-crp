package org.cloudbees.cloud_resource.jersey;

import com.cloudbees.cloud_resource.types.CloudResource;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * A partial default implementation of Jersey {@link CloudResource}.
 *
 * Subtypes should use Jackson annotations on the fields of this class which makes the state (in the REST sense)
 * of the object.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class CloudResourceSupport implements CloudResource {
    /**
     * Sends a state of this object.
     */
    @GET
    @Produces(CloudResource.CONTENT_TYPE)
    public Response doIndex() {
        return Response.ok(this).build();
    }
}
