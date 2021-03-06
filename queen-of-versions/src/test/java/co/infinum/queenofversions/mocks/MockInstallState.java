package co.infinum.queenofversions.mocks;

import com.google.android.play.core.install.InstallState;

import org.assertj.core.util.VisibleForTesting;

@VisibleForTesting
public class MockInstallState extends InstallState {

    private int installStatus;
    private int installErrorCode;

    public MockInstallState(int installStatus, int installErrorCode) {
        this.installErrorCode = installErrorCode;
        this.installStatus = installStatus;
    }

    @Override
    public int installStatus() {
        return installStatus;
    }

    @Override
    public long bytesDownloaded() {
        return 0;
    }

    @Override
    public long totalBytesToDownload() {
        return 0;
    }

    @Override
    public int installErrorCode() {
        return installErrorCode;
    }

    @Override
    public String packageName() {
        return null;
    }

}
