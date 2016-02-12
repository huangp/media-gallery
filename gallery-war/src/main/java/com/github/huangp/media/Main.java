package com.github.huangp.media;


import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.undertow.WARArchive;
import com.github.huangp.media.api.GalleryApplication;
import com.github.huangp.media.api.MediaResource;
import com.github.huangp.media.model.EXIF;
import com.github.huangp.media.model.Media;
import com.github.huangp.media.model.MetaInfo;
import com.github.huangp.media.service.EJBMediaSearchServiceImpl;
import com.github.huangp.media.service.MediaSearchService;

//import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class Main {

    public static void main(String... args) throws Exception {
        Container container = new Container();

        container.fraction(new DatasourcesFraction()
                .jdbcDriver("h2", (d) -> {
                    d.driverClassName("org.h2.Driver");
                    d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
                    d.driverModuleName("com.h2database.h2");
                })
                .dataSource("MyDS", (ds) -> {
                    ds.driverName("h2");
                    ds.connectionUrl(
                            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                    ds.userName("sa");
                    ds.password("sa");
                })
        );

        // Prevent JPA Fraction from installing it's default datasource fraction
        container.fraction(new JPAFraction()
                .inhibitDefaultDatasource()
                .defaultDatasource("jboss/datasources/MyDS")
        );

        container.start();

//        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
//        deployment.addClasses(User.class);
//        deployment.addAsWebInfResource(
//                new ClassLoaderAsset("META-INF/persistence.xml",
//                        Main.class.getClassLoader()),
//                "classes/META-INF/persistence.xml");
//        deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/load.sql",
//                Main.class.getClassLoader()), "classes/META-INF/load.sql");
//        deployment.addAllDependencies();


        WARArchive deployment = ShrinkWrap.create( WARArchive.class );

        // api
        deployment.addClass(GalleryApplication.class);
        deployment.addClass(MediaResource.class);

        // services
        deployment.addClass(EJBMediaSearchServiceImpl.class);
        deployment.addClass(MediaSearchService.class);

        // utilities
        deployment.addClass(ResourcesProducer.class);

        // model
        deployment.addClass(Media.class);
        deployment.addClass(MetaInfo.class);
        deployment.addClass(EXIF.class);

        deployment.addAsWebInfResource(
                new ClassLoaderAsset("META-INF/persistence.xml",
                        Main.class.getClassLoader()),
                "classes/META-INF/persistence.xml");

        deployment.addAsWebResource(
                new ClassLoaderAsset("index.html", Main.class.getClassLoader()), "index.html");

//        deployment.addAsWebInfResource(
//                new ClassLoaderAsset("WEB-INF/web.xml", Main.class.getClassLoader()), "web.xml");
        deployment.addAsWebInfResource(
                new ClassLoaderAsset("WEB-INF/beans.xml", Main.class.getClassLoader()), "beans.xml");

        deployment.addAllDependencies();

        deployment.as(ZipExporter.class).exportTo(new File("/tmp/gallery.war"), true);

        container.deploy(deployment);

    }
}
