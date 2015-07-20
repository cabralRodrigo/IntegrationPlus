package rc.mods.integrationplus.common.lib;

public final class LibMod {
    public static final String PROXY_CLIENT = "rc.mods.integrationplus.client.handler.ClientProxy";
    public static final String PROXY_SERVER = "rc.mods.integrationplus.common.handler.CommonProxy";
    public static final String MOD_ID = "integrationplus";
    public static final String CONFIG_FILE_NAME = "IntegrationPlus.cfg";
    public static final String BUILD = "@BUILD@";
    public static final String VERSION = "@VERSION@-" + BUILD;
    public static final String NAME = "Integration +";

    public static final String DEPENDENCIES =
            "required-after:" + LibReference.COMPUTER_CRAFT_ID +
                    ";after:" + LibReference.BOTANIA_ID;

    public static String bindModId(String suffix, char separator) {
        return String.format("%s%s%s", MOD_ID, separator, suffix).toLowerCase();
    }
}
