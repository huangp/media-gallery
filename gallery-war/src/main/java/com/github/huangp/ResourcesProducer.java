package com.github.huangp;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
