package com.project.application;

import com.project.dao.EmployeeDAO;
import com.project.health.Neo4jHealthCheck;
import com.project.resources.EmployeeResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.neo4j.graphdb.GraphDatabaseService;

public class ServerApplication extends Application<ServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new ServerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ServerConfiguration> bootstrap) {

        bootstrap.addBundle(new SwaggerBundle<ServerConfiguration>() {
            @Override
            public SwaggerBundleConfiguration getSwaggerBundleConfiguration(ServerConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
    }

    @Override
    public void run(ServerConfiguration configuration, Environment environment) throws Exception {
        final GraphDatabaseService graphDb = configuration.getDatabaseService();
        final Neo4jManager neo4jManager = new Neo4jManager(graphDb);

        final Neo4jHealthCheck neo4jHealthCheck = new Neo4jHealthCheck(graphDb);

        final EmployeeDAO userDAO = new EmployeeDAO(graphDb);

        final EmployeeResource msgs = new EmployeeResource(userDAO);
        environment.jersey().register(msgs);
        environment.lifecycle().manage(neo4jManager);
        environment.healthChecks().register("neo4jHealthCheck", neo4jHealthCheck);
    }
}
