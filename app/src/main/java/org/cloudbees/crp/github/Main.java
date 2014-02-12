package org.cloudbees.crp.github;

import com.cloudbees.cloud_resource.auth.CloudbeesAuthModule;
import com.cloudbees.cloud_resource.auth.jersey.AuthResourceFilter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.cloudbees.cloud_resource.jersey.guice.CloudResourceModule;
import org.cloudbees.cloud_resource.jersey.guice.OauthConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kohsuke Kawaguchi
 */
public class Main extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new JpaPersistModule("github-crp"),
                new CloudResourceModule(),
                new CloudbeesAuthModule(),
                new JerseyServletModule() {
                    @Override
                    protected void configureServlets() {
                        bind(GitHubAccount.class);

                        /** Allows injection of config object in to {@link CloudbeesAuthModule} module **/
                        bind(OauthConfig.class).to(GithubOauthConfig.class);

                        filter("/*").through(PersistFilter.class);

                        Map<String, String> params = new HashMap<String, String>();

                        /**
                         * Enable security via {@link AuthResourceFilter}
                         */
                        params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
                                AuthResourceFilter.class.getName());

                        params.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");

                        serve("/*").with(GuiceContainer.class, params);
                    }
                }
        );
    }
}
