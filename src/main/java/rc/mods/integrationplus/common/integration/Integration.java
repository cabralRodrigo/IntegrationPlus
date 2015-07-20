package rc.mods.integrationplus.common.integration;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;
import rc.mods.integrationplus.common.integration.botania.IntegrationBotania;

import java.util.ArrayList;

public abstract class Integration {
    public static IntegrationMinecraft MINECRAFT;
    public static IntegrationComputerCraft COMPUTER_CRAFT;
    public static IntegrationBotania BOTANIA;

    private static ArrayList<Integration> integrationList;

    static {
        integrationList = new ArrayList<Integration>();
        MINECRAFT = new IntegrationMinecraft();
        COMPUTER_CRAFT = new IntegrationComputerCraft();
        BOTANIA = new IntegrationBotania();
    }

    public static void loadIntegrationsSettings(Configuration config) {
        for (Integration integration : integrationList)
            integration.loadSettings(config);
    }

    public static void initIntegrations() {
        for (Integration integration : integrationList)
            if (integration.isIntegrationAvailable())
                integration.init();
    }

    protected String modId, displayName;
    protected boolean enabled;

    public Integration(String modId, String displayName) {
        this.modId = modId;
        this.displayName = displayName;
        this.enabled = true;

        integrationList.add(this);
    }

    protected void loadSettings(Configuration config) {
        this.enabled = config.getBoolean(this.modId + "-enabled", "Integrations", true, String.format("Set this value to false to disabled the %s integration.", this.displayName));
    }

    protected abstract void init();

    public boolean isIntegrationAvailable() {
        return "minecraft".equals(this.modId.toLowerCase()) ? true : this.enabled && Loader.isModLoaded(this.modId);
    }
}
