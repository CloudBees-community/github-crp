package org.cloudbees.crp.github;

import com.cloudbees.api.oauth.OauthToken;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Mapping between CloudBees account and GitHub OAuth token.
 *
 * {@link OauthToken} is scoped to a CloudBees account.
 * Single CloudBees account should be able to have access to multiple
 * GitHub organizations, and each organization might be accessed using
 * a different OAuth token.
 *
 * And similarly, a single GitHub organization might be accessed by
 * multiple CloudBees accounts. So the GitHub OAuth token needs to be held
 * in (CB account, GH org, GH OAuth token) name.
 *
 * @author Kohsuke Kawaguchi
 */
@Entity
public class Credential {
    @EmbeddedId
    public CredentialPK account;

    /**
     * GitHub OAuth token
     */
    @Column(nullable=false)
    public String token;
}
