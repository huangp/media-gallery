package com.github.huangp.media;


import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.wildfly.swarm.config.undertow.BufferCache;
import org.wildfly.swarm.config.undertow.HandlerConfiguration;
import org.wildfly.swarm.config.undertow.Server;
import org.wildfly.swarm.config.undertow.ServletContainer;
import org.wildfly.swarm.config.undertow.server.HTTPListener;
import org.wildfly.swarm.config.undertow.server.Host;
import org.wildfly.swarm.config.undertow.server.host.Location;
import org.wildfly.swarm.config.undertow.servlet_container.JSPSetting;
import org.wildfly.swarm.config.undertow.servlet_container.WebsocketsSetting;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.keycloak.Secured;
import org.wildfly.swarm.undertow.UndertowFraction;
import org.wildfly.swarm.undertow.WARArchive;
import com.github.huangp.media.api.GalleryApplication;
import com.github.huangp.media.api.MediaResource;
import com.github.huangp.media.model.AuthenticatedUser;
import com.github.huangp.media.model.EXIF;
import com.github.huangp.media.model.Media;
import com.github.huangp.media.model.MediaFileType;
import com.github.huangp.media.model.MetaInfo;
import com.github.huangp.media.service.EJBMediaSearchServiceImpl;
import com.github.huangp.media.service.MediaSearchService;
import com.github.huangp.media.servlet.AppServlet;
import com.github.huangp.media.servlet.ServletContextListener;
import com.github.huangp.media.util.JSONObjectMapper;
import com.github.huangp.media.util.PathUtil;

//import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class Main {

    public static void main(String... args) throws Exception {
        Container container = new Container();

//        container.fraction(new DatasourcesFraction()
//                .jdbcDriver("h2", (d) -> {
//                    d.driverClassName("org.h2.Driver");
//                    d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
//                    d.driverModuleName("com.h2database.h2");
//                })
//                // TODO use real database
//                .dataSource("MyDS", (ds) -> {
//                    ds.driverName("h2");
//                    ds.connectionUrl(
//                            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
//                    ds.userName("sa");
//                    ds.password("sa");
//                })
//        );

        boolean production = Boolean.parseBoolean(System.getProperty("swarm.production"));

        if (production) {
            container.fraction(new DatasourcesFraction()
                    .jdbcDriver("org.postgresql", (d) -> {
                        d.driverClassName("org.postgresql.Driver");
                        d.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
                        d.driverModuleName("org.postgresql");
                    })
                    .dataSource("MyDS", (ds) -> {
                        ds.driverName("org.postgresql");
                        ds.connectionUrl("jdbc:postgresql://localhost:5432/mediadb");
                        ds.userName("mediauser");
                        ds.password("n3v3rm0r3");
                    })
            );
        } else {
            container.fraction(new DatasourcesFraction()
                    .jdbcDriver("h2", (d) -> {
                        d.driverClassName("org.h2.Driver");
                        d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
                        d.driverModuleName("com.h2database.h2");
                    })
                    .dataSource("MyDS", (ds) -> {
                        ds.driverName("h2");
                        ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                        ds.userName("sa");
                        ds.password("sa");
                    })
            );
        }

        // Prevent JPA Fraction from installing it's default datasource fraction
        container.fraction(new JPAFraction()
                .inhibitDefaultDatasource()
                .defaultDatasource("jboss/datasources/MyDS")
        );

        // add additional file system path to server
        Server server = new Server("default-server")
                .httpListener(new HTTPListener("default").socketBinding("http"))
                .host(new Host("default-host").alias("localhost")
                        .location(new Location("media").handler("mediaHandler"))
                );

        // TODO use real path to media
        String pictures = new File(System.getProperty("user.home"), "Pictures")
                .getAbsolutePath();
        String mediaRootPath = System.getProperty("media.path", pictures);
        org.wildfly.swarm.config.undertow.configuration.File<?> fileConfig =
                new org.wildfly.swarm.config.undertow.configuration.File<>(
                        "mediaHandler").path(mediaRootPath)
                        .directoryListing(true);
        HandlerConfiguration<?> configuration =
                new HandlerConfiguration<>().file(fileConfig);

        container.fraction(new UndertowFraction().server(server)
                .bufferCache(new BufferCache("default"))
                .servletContainer(new ServletContainer("default")
                        .websocketsSetting(new WebsocketsSetting())
                        .jspSetting(new JSPSetting()))
                .handlerConfiguration(configuration));

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

        deployment.addPackages(true, Main.class.getPackage());

        /*// api
        deployment.addClass(GalleryApplication.class);
        deployment.addClass(MediaResource.class);

        // services
        deployment.addClass(EJBMediaSearchServiceImpl.class);
        deployment.addClass(MediaSearchService.class);

        // utilities
        deployment.addClass(ResourcesProducer.class);
        deployment.addClass(JSONObjectMapper.class);

        // model
        deployment.addClass(Media.class);
        deployment.addClass(MetaInfo.class);
        deployment.addClass(EXIF.class);
        deployment.addClass(MediaFileType.class);

        // security
        deployment.addClass(PathUtil.class);
        deployment.addClass(AuthenticatedUser.class);

        // servlet
        deployment.addClass(ServletContextListener.class);
        deployment.addClass(AppServlet.class);*/


        deployment.addAsWebInfResource(
                new ClassLoaderAsset("META-INF/persistence.xml",
                        Main.class.getClassLoader()),
                "classes/META-INF/persistence.xml");

        deployment.addAsWebResource(
                new ClassLoaderAsset("index.html", Main.class.getClassLoader()), "index.html");

        // things under webapp are automatically included as we use WarArchive
//        deployment.addAsWebInfResource(
//                new ClassLoaderAsset("WEB-INF/beans.xml", Main.class.getClassLoader()), "beans.xml");


//        deployment.addAsWebInfResource(
//                new ClassLoaderAsset("WEB-INF/web.xml", Main.class.getClassLoader()), "web.xml");

        deployment.addAllDependencies();

        // keycloak
        deployment.addAsWebInfResource(
                new ClassLoaderAsset("WEB-INF/keycloak.json", Main.class.getClassLoader()), "keycloak.json");
        // this is so that we don't have a class loading issue
        deployment.addModule("org.keycloak.keycloak-adapter-spi", "main");
        deployment.addModule("org.keycloak.keycloak-adapter-core", "main");
        Secured secured = deployment.as(Secured.class);
        secured.protect("/users").withMethod("GET").withRole("user");


        deployment.as(ZipExporter.class).exportTo(new File("/tmp/gallery.war"), true);

        container.deploy(deployment);

    }
}
