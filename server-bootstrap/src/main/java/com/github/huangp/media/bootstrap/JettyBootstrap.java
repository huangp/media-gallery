package com.github.huangp.media.bootstrap;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

public class JettyBootstrap {

    public static void main(String[] args) {
        try {
            URL warLocation =
                    JettyBootstrap.class.getProtectionDomain().getCodeSource()
                            .getLocation();
            if (warLocation == null) {
                throw new IOException("JettyBootstrap not discoverable");
            }

            LiveWarClassLoader clWar = new LiveWarClassLoader(warLocation);
            System.out.println("Using ClassLoader: " + clWar);
            Thread.currentThread().setContextClassLoader(clWar);

            File warFile = new File(warLocation.toURI());
            String warLocationStr = warFile
                    .toPath().toRealPath().toString();

            Class<?> mainClass =
                    Class.forName("com.github.huangp.media.jetty.JettyServerMain", true, clWar);

            Class.forName("org.eclipse.jetty.webapp.WebAppContext", false, clWar);
            Method mainMethod = mainClass.getMethod("main", args.getClass());
            // we put warLocation as first argument and pass on what's passed in here
            String [] extraArgs = new String[args.length + 1];
            extraArgs[0] = warLocationStr;
            System.arraycopy(args, 0, extraArgs, 1, args.length);

            mainMethod.invoke(mainClass, new Object[] { extraArgs });
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(-1);
        }
    }
}
