package co.infinum.queenofversions;

import android.support.annotation.VisibleForTesting;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
public interface InAppUpdateData {

    int availableVersionCode();

    @UpdateAvailability
    int updateAvailability();

    @InstallStatus
    int installStatus();

    boolean isUpdateTypeAllowed(@AppUpdateType int updateType);

    int priority();

    int clientStalenessDays();
}
