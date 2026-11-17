package dev.shared.fallwingas;

import java.time.LocalDateTime;

import dev.shared.do_gamer.utils.ServerTimeHelper;
import eu.darkbot.api.PluginAPI;
import eu.darkbot.api.config.ConfigSetting;
import eu.darkbot.api.extensions.Behavior;
import eu.darkbot.api.extensions.Configurable;
import eu.darkbot.api.extensions.Feature;
import eu.darkbot.api.extensions.Task;
import eu.darkbot.api.managers.BackpageAPI;
import eu.darkbot.api.managers.ConfigAPI;

@Feature(
        name = "Hourly Profile Switcher",
        description = "Switches to a selected profile at a chosen minute every hour."
)
public class HourlyProfileSwitcher
        implements Behavior, Task, Configurable<HourlyProfileSwitcherConfig> {

    private final ConfigAPI configApi;
    private final BackpageAPI backpageApi;

    private HourlyProfileSwitcherConfig config;
    private LocalDateTime lastSwitchHour;

    public HourlyProfileSwitcher(PluginAPI api) {
        this.configApi = api.requireAPI(ConfigAPI.class);
        this.backpageApi = api.requireAPI(BackpageAPI.class);
    }

    @Override
    public void setConfig(ConfigSetting<HourlyProfileSwitcherConfig> setting) {
        this.config = setting.getValue();
    }

    @Override
    public void onBackgroundTick() {
        if (this.config != null
                && this.config.timeSource == HourlyProfileSwitcherConfig.TimeSource.SERVER) {
            ServerTimeHelper.fetchServerOffset(this.backpageApi);
        }
    }

    @Override
    public void onTickBehavior() {
        this.checkSchedule();
    }

    @Override
    public void onStoppedBehavior() {
        this.checkSchedule();
    }

    private void checkSchedule() {
        if (this.config == null || !this.config.enabled
                || this.config.profile == null || this.config.profile.isBlank()) {
            return;
        }

        LocalDateTime now;
        if (this.config.timeSource == HourlyProfileSwitcherConfig.TimeSource.SERVER) {
            if (!ServerTimeHelper.offsetUpdated()) {
                return;
            }
            now = ServerTimeHelper.currentDateTime();
        } else {
            now = LocalDateTime.now();
        }

        if (now.getMinute() != this.config.minute) {
            return;
        }

        LocalDateTime thisHour = now.withMinute(0).withSecond(0).withNano(0);
        if (thisHour.equals(this.lastSwitchHour)) {
            return;
        }

        this.configApi.setConfigProfile(this.config.profile);
        this.lastSwitchHour = thisHour;
    }
}
