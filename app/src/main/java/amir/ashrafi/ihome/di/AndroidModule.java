package amir.ashrafi.ihome.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import amir.ashrafi.ihome.IhomeAssignmentApplication;

/**
 * Created by Abbas on 22/05/16.
 */
@Module
public class AndroidModule {

    @Provides
    @Singleton
    public static Context provideContext(IhomeAssignmentApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public static Resources provideResources(IhomeAssignmentApplication application) {
        return application.getResources();
    }

    @Provides
    @Singleton
    public static SharedPreferences provideSharedPreferences(IhomeAssignmentApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}
