package com.nhl.bootique.tapestry;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.nhl.bootique.ConfigModule;
import com.nhl.bootique.config.ConfigurationFactory;
import com.nhl.bootique.jetty.JettyModule;
import com.nhl.bootique.jetty.MappedFilter;
import com.nhl.bootique.tapestry.filter.BQTapestryFilterFactory;

public class TapestryModule extends ConfigModule {

    @Override
    public void configure(Binder binder) {
        JettyModule.contributeMappedFilters(binder).addBinding().to(Key.get(MappedFilter.class, TapestryFilter.class));
    }

    @Singleton
    @Provides
    @TapestryFilter
    MappedFilter createTapestryFilter(ConfigurationFactory configurationFactory) {
        return configurationFactory.config(BQTapestryFilterFactory.class, configPrefix).createTapestryFilter();
    }
}
