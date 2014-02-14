package org.cloudbees.crp.github;

import com.cloudbees.cloud_resource.auth.Secure;
import com.cloudbees.cloud_resource.types.CloudResourceTypes;
import com.cloudbees.cloud_resource.types.ReferencedResource;
import org.cloudbees.cloud_resource.jersey.CloudResourceProviderSupport;
import org.codehaus.jackson.annotate.JsonProperty;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * JAX-RS Resource that reports all the git repositories under a single GitHub user/org.
 *
 * // TODO: some caching
 *
 * @author Kohsuke Kawaguchi
 * @author Vivek Pandey
 */
@Path("/{login}")
@Consumes("application/vnd.cloudbees.resource+json")
@Produces("application/vnd.cloudbees.resource+json")
public class GitHubAccount extends CloudResourceProviderSupport {
    @PathParam("login")
    String login;

    @Context
    UriInfo uriInfo;

    @JsonProperty
    public List<ReferencedResource> resources() {
        List<ReferencedResource> rrl = new ArrayList<>();

        Set<String> types = CloudResourceTypes.of(GitRepositoryCloudResource.class);

        try {
            for (GHRepository r : connect().getRepositories().values()) {
                URI repoUri = uriInfo.getAbsolutePathBuilder().path(r.getName()).build();
                rrl.add(new ReferencedResource(repoUri.toString(),types));
            }
        } catch (IOException e) {
            throw new Error(e);
        }

        return rrl;
    }

    private GHUser connect() throws IOException {
        return GitHub.connectAnonymously().getUser(login);
    }

    @Path("{repo}")
    @Secure(capabilities={"https://types.cloudbees.com/resource/read"})
    public GitHubRepository getRepository(@PathParam("repo") String repo) throws IOException {
        return new GitHubRepository(connect().getRepository(repo));
    }
}
