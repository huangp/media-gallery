package com.github.huangp.media;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Patrick Huang <a
 *         href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@ApplicationScoped
public class ResourcesProducer {
    private static final Logger log =
            LoggerFactory.getLogger(ResourcesProducer.class);
    // Expose an entity manager using the resource producer pattern.
    // Must use @PersistenceContext to inject an entityManager so that container
    // will manage the transaction.
    // If inject @PersistenceUnit to inject EntityManagerFactory and then create
    // entityManager, EJB won't get transaction managed by container.
    @PersistenceContext
    @Produces
    private EntityManager entityManager;
    private Node esNode;

    @PostConstruct
    public void init() {
        // Embedded node clients behave just like standalone nodes,
        // which means that they will leave the HTTP port open!
        esNode = NodeBuilder.nodeBuilder()
                .settings(Settings.settingsBuilder()
                        .put("http.enabled", false)
                        // TODO real path to elasticsearch cluster
                        .put("path.home", "/var/lib/elasticsearch/pahuang_elasticsearch/")
                )
                .clusterName("pahuang_elasticsearch")
                .client(true)
                .node();
        esNode.start();
    }

    @PreDestroy
    public void cleanUp() {
        if (!esNode.isClosed()) {
            esNode.close();
        }
    }

    @Produces
    // Frequently starting and stopping one or more node clients creates unnecessary noise across the cluster
    @RequestScoped
    protected Client elasticSearchClient() {
        log.info(">>>>>>>>>>>>>>> getting elastic search client");
        return esNode.client();
    }

    protected void onDisposeElasticSearchClient(@Disposes Client client) {
        log.info("<<<<<<<<<<<<<<< closing elastic search client");
        client.close();
    }

}
