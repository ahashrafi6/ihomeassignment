package amir.ashrafi.ihome;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import amir.ashrafi.ihome.di.ApplicationComponent;
import amir.ashrafi.ihome.di.DaggerApplicationComponent;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by abbas on 10/20/16.
 */

public class IhomeAssignmentApplication extends Application implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent
                .builder()
                .application(this)
                .build();
        component.inject(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("is_m.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}