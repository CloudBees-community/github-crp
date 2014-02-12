# Github Cloud Resource Provider (CRP)

This is a sample Cloud Resource Provider (CRP) that exposes a public Github repository as Cloud Resource (CR).

It defines Cloud Resource Type: https://types.cloudbees.com/repository/git

## What is Cloud Resource

A Cloud Resource (CR) is a REST endpoint that represents a resource. We define a number of contracts
each CR can optionally implement that allows us to manipulate (such as bind to, clone, etc.)
CRs in a detail-agnostic manner.

## CR OAuth scope

Every Cloud Resource must define it's capabilities.

Cloud Resource scope is defined as following:

crs://host[:port]/!CAPABILITY


## Steps to RUN GitHub CRP

### Installed CloudBees SDK tool

Instruction to install Bees SDK: http://wiki.cloudbees.com/bin/view/RUN/BeesSDK

#### Install CloudBees OAuth plugin

    $ bees plugin:install org.cloudbees.sdk.plugins:oauth-plugin

### Register a Github Crp app with CloudBees OAuth server

Run the bees SDK command below and note down the client_id and client_secret from the response. Replace
OAUTH_APP_CLIENT_ID and OAUTH_APP_CLIENT_SECRET in the web.xml by the respective values.

    $ bees oauth:app:register --account cloudbees --grant-type client_credentials --url https://localhost/testgithubcrp -n "Github Crp Test App" --callback https://localhost/testgithubcrp/callback

### Start Github Crp

This will start GitHub CRP at http://localhost:9090

    $ mvn jetty:run

## Test GitHub CRP

### List your gitHub public repositories

List GitHub repositories as Cloud Resources

    $ bees cloud:resource:list http://localhost:9090/GITHUB_USERNAME

GITHUB_USERNAME - This is your github username

For example:

    $ bees cloud:resource:get http://localhost:9090/kohsuke

    http://localhost:9090/kohsuke/ForcePad/
    http://localhost:9090/kohsuke/Hudson-GIT-plugin/
    http://localhost:9090/kohsuke/Jenkins-Repository/
    http://localhost:9090/kohsuke/Json-lib/
    http://localhost:9090/kohsuke/access-modifier/
    http://localhost:9090/kohsuke/ajaxterm4j/


### Get information about GitHub repository Cloud Resource

    $ bees cloud:resource:get http://localhost:9090/kohsuke/ForcePad/

    {
      "gitCloneUrl" : "git://github.com/kohsuke/ForcePad.git"
    }

## Implementation notes

Every CRP must support a GET to return collection of resources it represents and also a GET on individual Cloud Resource.
In addition, a GET on each CR must provide details of that particular CR.

In most cases you simply need to extend from abstract class **CloudResourceProviderSupport** and implement resources()
method. **resources()** returns list of Cloud Resources represented by the Cloud Resource Provider.

