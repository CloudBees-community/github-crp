package org.cloudbees.crp.github;

import org.cloudbees.cloud_resource.jersey.CloudResourceSupport;
import org.codehaus.jackson.annotate.JsonProperty;
import org.kohsuke.github.GHRepository;

/**
 * JAX-RS Resource that serves a single repository as a cloud resource
 *
 * // TODO: some caching
 *
 * @author Kohsuke Kawaguchi
 */
public class GitHubRepository extends CloudResourceSupport implements GitRepositoryCloudResource {
    private final GHRepository repository;

    public GitHubRepository(GHRepository repository) {
        this.repository = repository;
    }

    @Override
    @JsonProperty
    public String getGitCloneUrl() {
        return repository.getGitTransportUrl();
    }
}
