package rc.mods.integrationplus.common.integration.botania;

import net.minecraft.block.Block;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import rc.mods.integrationplus.common.block.BlockIntegrablePlus;
import rc.mods.integrationplus.common.lib.LibMod;
import vazkii.botania.api.wiki.IWikiProvider;
import vazkii.botania.api.wiki.WikiHooks;

public class IntegrationPlusWikiProvider implements IWikiProvider {

    public static void registerWikiProvider() {
        WikiHooks.registerModWiki(LibMod.MOD_ID, new IntegrationPlusWikiProvider());
    }

    @Override
    public String getBlockName(World world, MovingObjectPosition pos) {
        BlockIntegrablePlus block = this.getBlock(world, pos);
        return block != null ? block.getWikiBlockName(world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ)) : null;
    }

    protected BlockIntegrablePlus getBlock(World world, MovingObjectPosition pos) {
        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
        if (block instanceof BlockIntegrablePlus)
            return (BlockIntegrablePlus) block;
        else
            return null;
    }

    @Override
    public String getWikiURL(World world, MovingObjectPosition pos) {
        BlockIntegrablePlus block = this.getBlock(world, pos);
        return block != null ? "https://github.com/cabralRodrigo/IntegrationPlus/wiki/" + block.getBlockWikiUrlSuffix(world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ)) : null;
    }

    @Override
    public String getWikiName(World world, MovingObjectPosition pos) {
        return "Integration + Wiki";
    }
}