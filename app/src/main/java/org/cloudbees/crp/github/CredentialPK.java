package org.cloudbees.crp.github;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Primary key for {@link Credential}
 *
 * @author Kohsuke Kawaguchi
 */
@Embeddable
public final class CredentialPK {
    @Column(nullable=false,updatable=false)
    public String cloudBeesAccount;

    @Column(nullable=false,updatable=false)
    public String githubLogin;

    public CredentialPK() {
    }

    public CredentialPK(String cloudBeesAccount, String githubLogin) {
        this.cloudBeesAccount = cloudBeesAccount;
        this.githubLogin = githubLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CredentialPK that = (CredentialPK) o;
        return cloudBeesAccount.equals(that.cloudBeesAccount) && githubLogin.equals(that.githubLogin);
    }

    @Override
    public int hashCode() {
        int result = cloudBeesAccount.hashCode();
        result = 31 * result + githubLogin.hashCode();
        return result;
    }
}
