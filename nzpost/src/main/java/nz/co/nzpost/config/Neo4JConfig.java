package nz.co.nzpost.config;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.api.exceptions.KernelException;
import org.neo4j.kernel.impl.proc.Procedures;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.driver.Driver;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import apoc.algo.PathFinding;

@EnableNeo4jRepositories
@EnableConfigurationProperties(Neo4jProperties.class)
@EnableTransactionManagement(proxyTargetClass = true)
@org.springframework.context.annotation.Configuration
@Profile("standAlone")
public class Neo4jConfig {
	
	private final static Logger log = LoggerFactory.getLogger(Neo4jConfig.class);

    @Autowired
    private Neo4jProperties neo4jProperties;

    @Bean
    public Configuration configuration() {
        return neo4jProperties.createConfiguration();
    }

    @Bean
    public SessionFactory sessionFactory(Configuration configuration) throws KernelException {
        SessionFactory sessionFactory = new SessionFactory(configuration, "nz.co.nzpost.domain");
        registerApoc(sessionFactory.getDriver());
        
        this.loadData(sessionFactory);
        
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) throws Exception {
        return new Neo4jTransactionManager(sessionFactory);
    }

    private void registerApoc(Driver driver) throws KernelException {
        if (driver instanceof EmbeddedDriver) {
            EmbeddedDriver embeddedDriver = (EmbeddedDriver) driver;
			GraphDatabaseService databaseService = embeddedDriver.getGraphDatabaseService();
			Procedures procedures = ((GraphDatabaseAPI) databaseService).getDependencyResolver()
					.resolveDependency(Procedures.class);
			try {
				procedures.registerProcedure(PathFinding.class);
			} catch (KernelException ex) {
				log.error("Could not load the APOC procedures.", ex);
				throw ex;
			}
        }
    }
    
    
    private void loadData( SessionFactory sessionFactory) {
    	Map properties = new HashMap<String, Object>();
    	
    	Session session = sessionFactory.openSession();
        Transaction tran = session.beginTransaction();
        
        // Creating the stations (nodes).
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"A\", `UNIQUE IMPORT ID`:0});", properties);
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"B\", `UNIQUE IMPORT ID`:20});", properties);
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"C\", `UNIQUE IMPORT ID`:40});", properties);
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"D\", `UNIQUE IMPORT ID`:21});", properties);
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"E\", `UNIQUE IMPORT ID`:22});", properties);
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"F\", `UNIQUE IMPORT ID`:23});", properties);
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"G\", `UNIQUE IMPORT ID`:41});", properties);
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"H\", `UNIQUE IMPORT ID`:44});", properties);
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"I\", `UNIQUE IMPORT ID`:42});", properties);
        session.query("CREATE (:`Station`:`UNIQUE IMPORT LABEL` {`name`:\"J\", `UNIQUE IMPORT ID`:43});", properties);
        
        tran.commit();
        tran = session.beginTransaction();
        
        session.query(" CREATE CONSTRAINT ON (node:`UNIQUE IMPORT LABEL`) ASSERT node.`UNIQUE IMPORT ID` IS UNIQUE;", properties);
        
        tran.commit();
        tran = session.beginTransaction();
        
        //Creating the path (relations) between stations.
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:0}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:20}) CREATE (n1)-[r:`DISTANCE` {`value`:12}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:0}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:22}) CREATE (n1)-[r:`DISTANCE` {`value`:20}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:20}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:40}) CREATE (n1)-[r:`DISTANCE` {`value`:5}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:40}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:21}) CREATE (n1)-[r:`DISTANCE` {`value`:5}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:21}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:22}) CREATE (n1)-[r:`DISTANCE` {`value`:7}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:20}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:42}) CREATE (n1)-[r:`DISTANCE` {`value`:15}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:22}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:23}) CREATE (n1)-[r:`DISTANCE` {`value`:5}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:0}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:41}) CREATE (n1)-[r:`DISTANCE` {`value`:16}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:44}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:0}) CREATE (n1)-[r:`DISTANCE` {`value`:4}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:44}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:41}) CREATE (n1)-[r:`DISTANCE` {`value`:6}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:42}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:44}) CREATE (n1)-[r:`DISTANCE` {`value`:21}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:43}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:40}) CREATE (n1)-[r:`DISTANCE` {`value`:15}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:0}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:21}) CREATE (n1)-[r:`DISTANCE` {`value`:19}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:20}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:21}) CREATE (n1)-[r:`DISTANCE` {`value`:13}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:23}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:0}) CREATE (n1)-[r:`DISTANCE` {`value`:5}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:41}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:23}) CREATE (n1)-[r:`DISTANCE` {`value`:11}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:44}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:20}) CREATE (n1)-[r:`DISTANCE` {`value`:19}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:42}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:43}) CREATE (n1)-[r:`DISTANCE` {`value`:10}]->(n2);", properties);
        session.query(" MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:43}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:20}) CREATE (n1)-[r:`DISTANCE` {`value`:7}]->(n2);", properties);
        
        tran.commit();
        tran = session.beginTransaction();
        
        session.query(" MATCH (n:`UNIQUE IMPORT LABEL`)  WITH n LIMIT 20000 REMOVE n:`UNIQUE IMPORT LABEL` REMOVE n.`UNIQUE IMPORT ID`;", properties);
        
        
        tran.commit();
        tran = session.beginTransaction();
        
        session.query(" DROP CONSTRAINT ON (node:`UNIQUE IMPORT LABEL`) ASSERT node.`UNIQUE IMPORT ID` IS UNIQUE;", properties);
        
        tran.commit();
    	
    }
}