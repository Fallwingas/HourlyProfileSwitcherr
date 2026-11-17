package dev.shared.fallwingas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.darkbot.api.config.annotations.Configuration;
import eu.darkbot.api.config.annotations.Dropdown;
import eu.darkbot.api.config.annotations.Number;
import eu.darkbot.api.config.annotations.Option;
import eu.darkbot.api.managers.ConfigAPI;
import eu.darkbot.shared.config.ProfileNames;

@Configuration("fallwingas.hourly_profile_switcher")
public class HourlyProfileSwitcherConfig {

    public enum TimeSource {
        PC,
        SERVER
    }

    public static class TimeSourceOptions implements Dropdown.Options<TimeSource> {
        @Override
        public List<TimeSource> options() {
            return List.of(TimeSource.PC, TimeSource.SERVER);
        }

        @Override
        public String getText(TimeSource option) {
            return option == TimeSource.SERVER ? "Server time" : "PC time";
        }
    }

    public static class ProfileOptions extends ProfileNames {
        public ProfileOptions(ConfigAPI api) {
            super(api);
        }

        @Override
        public Collection<String> options() {
            List<String> profiles = new ArrayList<>();
            profiles.add(null);

            Collection<String> availableProfiles = super.options();
            if (availableProfiles != null) {
                profiles.addAll(availableProfiles);
            }

            return profiles;
        }
    }

    @Option("general.enabled")
    public boolean enabled = false;

    @Option("fallwingas.hourly_profile_switcher.first_profile")
    @Dropdown(options = ProfileOptions.class)
    public String firstProfile = null;

    @Option("fallwingas.hourly_profile_switcher.second_profile")
    @Dropdown(options = ProfileOptions.class)
    public String secondProfile = null;

    @Option("fallwingas.hourly_profile_switcher.time_source")
    @Dropdown(options = TimeSourceOptions.class)
    public TimeSource timeSource = TimeSource.PC;

    @Option("fallwingas.hourly_profile_switcher.minute")
    @Number(min = 0, max = 59, step = 1)
    public int minute = 55;
}
