package com.github.huangp.media;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

/**
 * @author Patrick Huang <a
 *         href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@ApplicationScoped
public class ResourcesProducer {
    // Expose an entity manager using the resource producer pattern.
    // Must use @PersistenceContext to inject an entityManager so that container
    // will manage the transaction.
    // If inject @PersistenceUnit to inject EntityManagerFactory and then create
    // entityManager, EJB won't get transaction managed by container.
    @PersistenceContext
    @Produces
    private EntityManager entityManager;

    @Produces
    @ApplicationScoped
    Node elasticSearchNode() {
        // TODO check src/main/resource/elasticsearch.yml file for cluster name

        // Embedded node clients behave just like standalone nodes,
        // which means that they will leave the HTTP port open!
        return NodeBuilder.nodeBuilder()
                .settings(ImmutableSettings.settingsBuilder()
                        .put("http.enabled", false))
                .client(true)
                .node();
    }

    protected void onDisposeElasticSearchNode(@Disposes Node node) {
        if (!node.isClosed()) {
            node.close();
        }
    }

    @Produces
    // Frequently starting and stopping one or more node clients creates unnecessary noise across the cluster
    // TODO think of a better scope for client
    protected Client elasticSearchClient(Node node) {
        return node.client();
    }

    protected void onDisposeElasticSearchClient(@Disposes Client client) {
        client.close();
    }

}
