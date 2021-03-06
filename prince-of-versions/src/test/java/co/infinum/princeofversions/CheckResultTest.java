package co.infinum.princeofversions;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CheckResultTest {

    private static final int DEFAULT_REQUIRED_VERSION = 1;

    private static final int DEFAULT_LAST_VERSION_AVAILABLE = 1;

    private static final Map<String, String> DEFAULT_REQUIREMENTS = new HashMap<>();

    private static final int DEFAULT_VERSION = 1;

    private static final Map<String, String> DEFAULT_METADATA = new HashMap<>();

    private static final UpdateInfo updateInfo = new UpdateInfo(DEFAULT_REQUIRED_VERSION, DEFAULT_LAST_VERSION_AVAILABLE,
        DEFAULT_REQUIREMENTS, DEFAULT_VERSION, NotificationType.ALWAYS);

    @Test
    public void checkHasUpdateMandatory() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.hasUpdate()).isTrue();
    }

    @Test
    public void checkHasUpdateOptionalAlways() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.hasUpdate()).isTrue();
    }

    @Test
    public void checkHasUpdateOptionalOnce() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ONCE, DEFAULT_METADATA, updateInfo);

        assertThat(result.hasUpdate()).isTrue();
    }

    @Test
    public void checkHasUpdateMandatoryWithNulls() {
        CheckResult result = CheckResult.mandatoryUpdate(null, null, updateInfo);

        assertThat(result.hasUpdate()).isTrue();
    }

    @Test
    public void checkHasUpdateOptionalAlwaysWithNulls() {
        CheckResult result = CheckResult.optionalUpdate(null, NotificationType.ALWAYS, null, updateInfo);

        assertThat(result.hasUpdate()).isTrue();
    }

    @Test
    public void checkHasUpdateOptionalOnceWithNulls() {
        CheckResult result = CheckResult.optionalUpdate(null, NotificationType.ONCE, null, updateInfo);

        assertThat(result.hasUpdate()).isTrue();
    }

    @Test
    public void checkHasNoUpdate() {
        CheckResult result = CheckResult.noUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.hasUpdate()).isFalse();
    }

    @Test
    public void checkHasNoUpdateWithNulls() {
        CheckResult result = CheckResult.noUpdate(null, null, updateInfo);

        assertThat(result.hasUpdate()).isFalse();
    }

    @Test
    public void checkGetUpdateVersion() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.getUpdateVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    public void checkIsOptionalMandatory() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.isOptional()).isFalse();
    }

    @Test
    public void checkIsOptionalOptionalAlways() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.isOptional()).isTrue();
    }

    @Test
    public void checkIsOptionalOptionalOnce() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ONCE, DEFAULT_METADATA, updateInfo);

        assertThat(result.isOptional()).isTrue();
    }

    @Test
    public void checkIsOptionalMandatoryWithNulls() {
        CheckResult result = CheckResult.mandatoryUpdate(null, null, updateInfo);

        assertThat(result.isOptional()).isFalse();
    }

    @Test
    public void checkIsOptionalOptionalAlwaysWithNulls() {
        CheckResult result = CheckResult.optionalUpdate(null, NotificationType.ALWAYS, null, updateInfo);

        assertThat(result.isOptional()).isTrue();
    }

    @Test
    public void checkIsOptionalOptionalOnceWithNulls() {
        CheckResult result = CheckResult.optionalUpdate(null, NotificationType.ONCE, null, updateInfo);

        assertThat(result.isOptional()).isTrue();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkIsOptionalWhenNoUpdate() {
        CheckResult result = CheckResult.noUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.isOptional()).isFalse();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkIsOptionalWhenNoUpdateWithNulls() {
        CheckResult result = CheckResult.noUpdate(null, null, updateInfo);

        assertThat(result.isOptional()).isFalse();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkNotificationTypeMandatory() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        result.getNotificationType();
    }

    @Test
    public void checkNotificationTypelOptionalAlways() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.getNotificationType()).isEqualTo(NotificationType.ALWAYS);
    }

    @Test
    public void checkNotificationTypeOptionalOnce() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ONCE, DEFAULT_METADATA, updateInfo);

        assertThat(result.getNotificationType()).isEqualTo(NotificationType.ONCE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkNotificationTypeWhenNoUpdate() {
        CheckResult result = CheckResult.noUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        result.getNotificationType();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkNotificationTypeWhenNoUpdateWithNulls() {
        CheckResult result = CheckResult.noUpdate(null, null, updateInfo);

        result.getNotificationType();
    }

    @Test
    public void checkStatusMandatory() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.status()).isEqualTo(UpdateStatus.REQUIRED_UPDATE_NEEDED);
    }

    @Test
    public void checkStatusOptional() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.status()).isEqualTo(UpdateStatus.NEW_UPDATE_AVAILABLE);
    }

    @Test
    public void checkStatusNoUpdate() {
        CheckResult result = CheckResult.noUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.status()).isEqualTo(UpdateStatus.NO_UPDATE_AVAILABLE);
    }

    @Test
    public void checkMetadataMandatory() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.metadata()).isEqualTo(DEFAULT_METADATA);
    }

    @Test
    public void checkMetadataOptional() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.metadata()).isEqualTo(DEFAULT_METADATA);
    }

    @Test
    public void checkMetadataNoUpdate() {
        CheckResult result = CheckResult.noUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.metadata()).isEqualTo(DEFAULT_METADATA);
    }

    @Test
    public void checkUpdateInfoMandatoryUpdate() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo()).isEqualTo(updateInfo);
    }

    @Test
    public void checkUpdateInfoOptionalUpdate() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo()).isEqualTo(updateInfo);
    }

    @Test
    public void checkUpdateInfoRequiredVersionMandatoryUpdate() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo().getRequiredVersion()).isEqualTo(DEFAULT_REQUIRED_VERSION);
    }

    @Test
    public void checkUpdateInfoRequiredVersionOptionalUpdate() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo().getRequiredVersion()).isEqualTo(DEFAULT_REQUIRED_VERSION);
    }

    @Test
    public void checkUpdateInfoLastVersionAvailableMandatoryUpdate() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo().getLastVersionAvailable()).isEqualTo(DEFAULT_LAST_VERSION_AVAILABLE);
    }

    @Test
    public void checkUpdateInfoLastVersionAvailableOptionalUpdate() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo().getLastVersionAvailable()).isEqualTo(DEFAULT_LAST_VERSION_AVAILABLE);
    }

    @Test
    public void checkUpdateInfoInstalledVersionMandatoryUpdate() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo().getInstalledVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    public void checkUpdateInfoInstalledVersionOptionalUpdate() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo().getInstalledVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    public void checkUpdateInfoRequirementsMandatoryUpdate() {
        CheckResult result = CheckResult.mandatoryUpdate(DEFAULT_VERSION, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo().getRequirements()).isEqualTo(DEFAULT_REQUIREMENTS);
    }

    @Test
    public void checkUpdateInfoRequirementsOptionalUpdate() {
        CheckResult result = CheckResult.optionalUpdate(DEFAULT_VERSION, NotificationType.ALWAYS, DEFAULT_METADATA, updateInfo);

        assertThat(result.getInfo().getRequirements()).isEqualTo(DEFAULT_REQUIREMENTS);
    }
}
