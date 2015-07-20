package rc.mods.integrationplus.common.handler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import rc.mods.integrationplus.common.integration.Integration;
import rc.mods.integrationplus.common.block.ModBlocks;
import rc.mods.integrationplus.common.lib.LibMod;

import java.io.File;

public class CommonProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath(), LibMod.CONFIG_FILE_NAME));
        ModBlocks.preInit();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        Integration.initIntegrations();
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        ModBlocks.init();
    }
}