package co.infinum.queenofversions;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class AppCallbacks implements Application.ActivityLifecycleCallbacks {

    private final GoogleAppUpdater googleAppUpdater;

    AppCallbacks(GoogleAppUpdater googleAppUpdater) {
        this.googleAppUpdater = googleAppUpdater;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        resumeAppUpdate(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    private void resumeAppUpdate(Activity activity) {
        googleAppUpdater.resumeUpdate(activity);
    }
}