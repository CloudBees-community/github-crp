package org.cloudbees.crp.github;

import com.cloudbees.cloud_resource.types.CloudResourceError;
import com.cloudbees.cloud_resource.types.CloudResourceTypes;
import com.cloudbees.cloud_resource.types.ReferencedResource;
import org.cloudbees.cloud_resource.jersey.CloudResourceProviderSupport;
import org.cloudbees.cloud_resource.jersey.CloudResourceSupport;
import org.codehaus.jackson.annotate.JsonProperty;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
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
 */
@Path("/{login}")
public class GitHubAccount extends CloudResourceProviderSupport {
    @PathParam("login")
    String login;

    @Context
    UriInfo uriInfo;

    @Inject
    EntityManager manager;

    @JsonProperty
    public List<ReferencedResource> edges() {
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
        Credential cr = manager.find(Credential.class,new CredentialPK());
        if (cr==null) {
            CloudResourceError err = new CloudResourceError("Account %s does not have access to GitHub organization "+login,
                    null, null/*TODO:URL*/);
            throw new WebApplicationException(CloudResourceSupport.asResponse(err));
        }

        return GitHub.connect(cr.account.githubLogin,cr.token).getUser(login);
    }

    @Path("{repo}")
    public GitHubRepository getRepository(@PathParam("repo") String repo) throws IOException {
        return new GitHubRepository(connect().getRepository(repo));
    }
}
