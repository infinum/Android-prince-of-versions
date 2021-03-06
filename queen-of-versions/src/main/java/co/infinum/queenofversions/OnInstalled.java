package co.infinum.queenofversions;

/**
 * Called when {@link com.google.android.play.core.install.InstallStateUpdatedListener} produces {@link
 * com.google.android.play.core.install.model.InstallStatus} with value INSTALLED.
 */
public interface OnInstalled {

    /**
     * Called when {@link com.google.android.play.core.install.InstallStateUpdatedListener} produces {@link
     * com.google.android.play.core.install.model.InstallStatus} with value INSTALLED.
     *
     * @param appUpdateInfo information about the update read from Google Play
     */
    void onInstalled(QueenOfVersionsInAppUpdateInfo appUpdateInfo);
}
