package org.cloudbees.crp.github;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Kohsuke Kawaguchi
 */
@Path("/{account}")
public class GitHubAccount {
    @PathParam("account")
    String account;

    @GET
    @Produces(APPLICATION_JSON)
    public List<T> list() {

    }
}
