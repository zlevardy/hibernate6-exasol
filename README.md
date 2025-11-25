# hibernate6-exasol

A fork based on original hibernate-exasol repo

goal: get HB v6.2+ supporting basic ExaSol access

# using exasol dialect for hibernate 6 in a spring boot 3.5+ project 

You will need a programmatic HibernateConfig object to ensure jdbc drivers "no suppported - gen auto keys" is suppressed. 
Adding hibernate property:

    spring.jpa.properties.hibernate.jdbc.use_get_generated_keys=false

into application.xml does not work. Next HibernateCondig is able to override use_get_generated_keys to False. 

```
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return properties -> properties.put(AvailableSettings.USE_GET_GENERATED_KEYS, Boolean.FALSE.toString());
    }

}
```

# localhost exasol database from docker

Pull image from docker hub:

    docker image pull exasol/docker-db

Starting exasoldb via docker command:

    docker run --name exasoldb -p 127.0.0.1:9563:8563 --detach --privileged --stop-timeout 120  exasol/docker-db:latest

Making persistent container:

    docker run --name exasoldb  -p 127.0.0.1:9563:8563 --detach --privileged --stop-timeout 120 -v exa_volume:/exa exasol/docker-db:latest

Clean shutdown from outside the container:

    docker exec -ti exasoldb dwad_client stop-wait DB1

Optional to use docker-compose.yaml:

```
version: '3'

services:

# Exasol DB
exasol-db:
image: exasol/docker-db:latest
container_name: exasol-db
privileged: true
ports:
- 9563:8563
volumes:
- $PWD/data/test:/test
```

and starting via yaml:

    docker compose up

