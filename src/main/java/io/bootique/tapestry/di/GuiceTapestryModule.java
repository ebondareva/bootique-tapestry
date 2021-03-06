package io.bootique.tapestry.di;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import io.bootique.tapestry.annotation.IgnoredPaths;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.LibraryMapping;

import java.util.Set;

/**
 * A Tapestry DI Module that provides integration with Guice DI.
 */
public class GuiceTapestryModule {

    public static void contributeIgnoredPathsFilter(
            @InjectService(InjectorModuleDef.INJECTOR_SERVICE_ID) Injector injector,
            Configuration<String> configuration) {

        TypeLiteral<Set<String>> ignoresType = new TypeLiteral<Set<String>>() {
        };
        Set<String> ignores = injector.getInstance(Key.get(ignoresType, IgnoredPaths.class));
        ignores.forEach(configuration::add);
    }

    public void contributeMasterObjectProvider(
            @InjectService(InjectorModuleDef.INJECTOR_SERVICE_ID) Injector injector,
            OrderedConfiguration configuration) {

        configuration.add("guiceProvider", new GuiceObjectProvider(injector), "after:Service,Alias,Autobuild");
    }

    public void contributeComponentClassResolver(
            @InjectService(InjectorModuleDef.INJECTOR_SERVICE_ID) Injector injector,
            Configuration<LibraryMapping> configuration) {

        // register Tapestry component libraries.


        TypeLiteral<Set<LibraryMapping>> libsType = new TypeLiteral<Set<LibraryMapping>>() {
        };
        injector.getInstance(Key.get(libsType)).forEach(configuration::add);
    }
}
