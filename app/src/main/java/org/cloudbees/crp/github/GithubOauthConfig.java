package org.cloudbees.crp.github;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.cloudbees.cloud_resource.jersey.guice.OauthConfig;

import javax.servlet.ServletContext;

/**
 * @author Vivek Pandey
 */
@Singleton
public class GithubOauthConfig extends OauthConfig {
    @Inject
    private ServletContext servletContext;

    @Override
    public String getClientId() {
        return servletContext.getInitParameter("client_id");
    }

    @Override
    public String getClientSecret() {
        return servletContext.getInitParameter("client_secret");
    }
}
