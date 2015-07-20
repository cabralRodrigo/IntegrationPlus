package rc.mods.integrationplus.common.handler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import rc.mods.integrationplus.common.integration.Integration;
import rc.mods.integrationplus.common.lib.LibMod;

import java.io.File;

/**
 * @author pahimar
 */
public class ConfigurationHandler {

    public static Configuration configuration;

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile, true);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        Integration.loadIntegrationsSettings(configuration);

        if (configuration.hasChanged())
            configuration.save();
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(LibMod.MOD_ID))
            loadConfiguration();
    }
}
