package org.cloudbees.crp.github;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.Collections;
import java.util.Map;

/**
 * @author Kohsuke Kawaguchi
 */
public class Main extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new JpaPersistModule("github-crp"),
                new JerseyServletModule() {
                    @Override
                    protected void configureServlets() {
                        bind(RestaurantResource.class);
                        bind(UserResource.class);
                        bind(ReviewResource.class);

                        filter("/*").through(PersistFilter.class);
                        serve("/*").with(GuiceContainer.class, POJO_JSON_MAPPING);
                    }
                }
        );
    }

    private static final Map<String,String> POJO_JSON_MAPPING = Collections.singletonMap(
            "com.sun.jersey.api.json.POJOMappingFeature", "true"
    );

}
