# Github Cloud Resource Provider (CRP)

This is a sample Cloud Resource Provider (CRP) that exposes a Github repository as Cloud Resource (CR).

## What is Cloud Resource


> A Cloud Resource (CR) is a REST endpoint that represents a resource. We define a number of contracts
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



    bees oauth:app:register --account cloudbees --grant-type client_credentials --url https://localhost/testgithubcrp -n "Github Crp Test App" --callback https://localhost/testgithubcrp/callback

### Create a token with CR read capability

Cloud Resource READ capability is: https://types.cloudbees.com/resource/read. Create an Oauth token with this capability using crs scope mechanism

    bees  oauth:token:create --account CLOUDBEES_ACCOUNT_NAME -scope "crs://HOSTNAME[:PORT]/\!https://types.cloudbees.com/resource/read" -note "Github Crp test app" -note-url "http://localhost/githubcrptest"
