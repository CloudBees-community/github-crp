package org.cloudbees.crp.github;

import com.cloudbees.cloud_resource.types.CloudResource;
import com.cloudbees.cloud_resource.types.CloudResourceType;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Kohsuke Kawaguchi
 */
@CloudResourceType("https://types.cloudbees.com/repository/git")
public interface GitRepositoryCloudResource extends CloudResource {
    /**
     * Argument for "git-clone" to clone this repository.
     */
    @JsonProperty
    String getGitCloneUrl();
}
