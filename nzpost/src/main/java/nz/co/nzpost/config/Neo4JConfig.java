package nz.co.nzpost.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.api.exceptions.KernelException;
import org.neo4j.kernel.impl.proc.Procedures;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.ogm.driver.Driver;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import apoc.algo.PathFinding;

@EnableNeo4jRepositories
@EnableConfigurationProperties(Neo4jProperties.class)
@EnableTransactionManagement(proxyTargetClass = true)
@org.springframework.context.annotation.Configuration
public class Neo4JConfig {

    @Autowired
    private Neo4jProperties neo4jProperties;

    @Bean
    public Configuration configuration() {
        return neo4jProperties.createConfiguration();
    }

    @Bean
    public SessionFactory sessionFactory(Configuration configuration) {
        SessionFactory sessionFactory = new SessionFactory(configuration, "nz.co.nzpost.domain");
        registerApoc(sessionFactory.getDriver());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) throws Exception {
        return new Neo4jTransactionManager(sessionFactory);
    }

    private void registerApoc(Driver driver) {
        if (driver instanceof EmbeddedDriver) {
            EmbeddedDriver embeddedDriver = (EmbeddedDriver) driver;
			GraphDatabaseService databaseService = embeddedDriver.getGraphDatabaseService();
			Procedures procedures = ((GraphDatabaseAPI) databaseService).getDependencyResolver()
					.resolveDependency(Procedures.class);
			try {
				procedures.registerProcedure(PathFinding.class);
			} catch (KernelException e) {
				e.printStackTrace();
			}
        }
    }
}