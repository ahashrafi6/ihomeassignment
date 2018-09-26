package amir.ashrafi.ihome.di;


import javax.inject.Singleton;

import amir.ashrafi.ihome.IhomeAssignmentApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import amir.ashrafi.ihome.di.common.ApiModule;
import amir.ashrafi.ihome.di.common.ClientModule;


@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        AndroidSupportInjectionModule.class,
        FragmentBuilder.class,
        ClientModule.class,
})
public interface ApplicationComponent {

    void inject(IhomeAssignmentApplication __);

    @Component.Builder
    interface Builder {
        ApplicationComponent build();

        @BindsInstance
        Builder application(IhomeAssignmentApplication application);
    }

}

