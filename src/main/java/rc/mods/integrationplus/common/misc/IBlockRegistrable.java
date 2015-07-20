package rc.mods.integrationplus.common.misc;

/**
 * Define a block that can register itself
 */
public interface IBlockRegistrable {
    /**
     * Register the block
     */
    void registerBlock();

    /**
     * Register the block's tile entity (if has any)
     */
    void registerTileEntity();
}