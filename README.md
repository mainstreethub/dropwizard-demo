# Demo Dropwizard Application

A boilerplate dropwizard application that includes:

* dagger2 dependency injection
* JDBI dao's
* flyway database migration

## Getting Started

    git clone git@github.com:mainstreethub/dropwizard-demo.git
    cd dropwizard-demo
    mvn clean package
    mysql -uroot -e 'create database dwdemo'
    mysql -uroot -e "grant all on dwdemo.* to 'dwdemo'@'localhost' identified by 'foobar'"
    java -jar application/target/application*.jar server config.yaml