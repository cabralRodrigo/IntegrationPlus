package rc.mods.integrationplus.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import rc.mods.integrationplus.common.handler.IProxy;
import rc.mods.integrationplus.common.lib.LibMod;
import rc.mods.integrationplus.common.misc.IntegrationPlusCreativeTab;

@Mod(modid = LibMod.MOD_ID, version = LibMod.VERSION, name = LibMod.NAME, dependencies = LibMod.DEPENDENCIES)
public class IntegrationPlus {

    @Mod.Instance(LibMod.MOD_ID)
    public static IntegrationPlus instance;

    @SidedProxy(clientSide = LibMod.PROXY_CLIENT, serverSide = LibMod.PROXY_SERVER, modId = LibMod.MOD_ID)
    public static IProxy proxy;

    public static CreativeTabs creativeTab = new IntegrationPlusCreativeTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}