package dev.shared.fallwingas;

import eu.darkbot.api.PluginAPI;
import eu.darkbot.api.extensions.Behavior;
import eu.darkbot.api.extensions.Feature;

@Feature(
        name = "Hourly Profile Switcher",
        description = "Switches profiles according to a schedule."
)
public class HourlyProfileSwitcher implements Behavior {

    public HourlyProfileSwitcher(PluginAPI api) {
        // Aquí se obtienen las APIs necesarias del bot.
    }

    @Override
    public void onTickBehavior() {
        // Aquí irá la lógica adaptada para cambiar el perfil.
    }
}
