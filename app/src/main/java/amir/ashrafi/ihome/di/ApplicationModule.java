package amir.ashrafi.ihome.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import amir.ashrafi.ihome.BuildConfig;
import amir.ashrafi.ihome.utils.Constants;
import amir.ashrafi.ihome.utils.SchedulerProvider;
import amir.ashrafi.ihome.utils.SchedulerProviderImpl;

/**
 * Created by abbas on 6/25/16.
 */
@Module
public abstract class ApplicationModule {
    @Provides
    @Singleton
    @Named("isDebug")
    public static boolean provideIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Provides
    @Singleton
    @Named("networkTimeoutInSeconds")
    public static int provideNetworkTimeoutInSeconds() {
        return 30;
    }


    @Provides
    @Singleton
    @Named("BaseUrl")
    public static String provideBaseUrl() {
        return Constants.BASE_URL;
    }


    @Binds
    @Singleton
    public abstract SchedulerProvider provideAppScheduler(SchedulerProviderImpl schedulerProvider);

}
