package rc.mods.integrationplus.common.block;

import cpw.mods.fml.common.LoaderException;
import rc.mods.integrationplus.common.block.botania.BlockManaPoolProxy;
import rc.mods.integrationplus.common.misc.LogHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public final class ModBlocks {
    public static BlockManaPoolProxy manaPoolPeripheral;
    public static BlockAnimalScanner animalScanner;

    private static ArrayList<BlockIntegrablePlus> allBlocks = new ArrayList<BlockIntegrablePlus>();

    public static void preInit() {
        instantiateBlocks();

        for (BlockIntegrablePlus block : allBlocks) {
            block.registerBlock();
            block.registerTileEntity();
        }
    }

    public static void init() {
        registerRecipes();
    }

    /**
     * Instantiates the blocks of the mod via reflection
     */
    private static void instantiateBlocks() {
        for (Field field : ModBlocks.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && BlockIntegrablePlus.class.isAssignableFrom(field.getType())) {
                try {
                    field.set(null, field.getType().newInstance());

                    Object instance = field.get(null);
                    if (instance instanceof BlockIntegrablePlus)
                        allBlocks.add((BlockIntegrablePlus) instance);

                } catch (Exception ex) {
                    LogHelper.error("Error trying to instantiate blocks. Report this error to the mod author!");
                    throw new LoaderException(ex);
                }
            }
        }
    }

    /**
     * Registers all blocks, making sure that the block's integration it's properly available
     */
    private static void registerRecipes() {
        for (BlockIntegrablePlus block : allBlocks) {
            if (!block.getIntegration().isIntegrationAvailable())
                continue;

            block.registerRecipes();
        }
    }
}