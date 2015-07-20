package rc.mods.integrationplus.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import rc.mods.integrationplus.common.IntegrationPlus;
import rc.mods.integrationplus.common.integration.IIntegrableResource;
import rc.mods.integrationplus.common.integration.Integration;
import rc.mods.integrationplus.common.item.block.ItemBlockIntegrablePlus;
import rc.mods.integrationplus.common.lib.LibMod;
import rc.mods.integrationplus.common.misc.IBlockRegistrable;
import rc.mods.integrationplus.common.integration.botania.IBotaniaWikiable;
import rc.mods.integrationplus.common.misc.IRecipeRegistrable;

import java.util.List;

public abstract class BlockIntegrablePlus<T extends TileEntity> extends Block implements IBlockRegistrable, IRecipeRegistrable, IIntegrableResource, IBotaniaWikiable {
    private Class<T> tileEntityClass;
    private String blockName;
    private Integration integration;

    /**
     * Creates a new instance of the BlockIntegrablePlus class
     *
     * @param material  The material of the block
     * @param blockName The name of the block
     * @see net.minecraft.block.material.Material for all the materials available
     */
    protected BlockIntegrablePlus(Material material, String blockName) {
        super(material);
        this.setDefaultCreativeTab();
        this.setBlockTextureName(LibMod.bindModId(blockName, ':'));
        this.blockName = blockName;
        this.integration = null;
    }

    /**
     * Creates a new instance of the BLockIntegrationPlus class
     *
     * @param material        The material of the block
     * @param blockName       The name of the block
     * @param tileEntityClass The class of the block's tile entity
     * @see net.minecraft.block.material.Material for all the materials available
     */
    protected BlockIntegrablePlus(Material material, String blockName, Class<T> tileEntityClass) {
        this(material, blockName);
        this.tileEntityClass = tileEntityClass;
    }

    /**
     * Creates a new instance of the BlockIntegrablePlus class
     *
     * @param material    The material of the block
     * @param blockName   The name of the block
     * @param integration The integration that this block will be part of
     * @see net.minecraft.block.material.Material for all the materials available
     */
    protected BlockIntegrablePlus(Material material, String blockName, Integration integration) {
        this(material, blockName);
        this.integration = integration;
    }

    /**
     * Creates a new instance of the BlockIntegrablePlus class
     *
     * @param material        The material of the block
     * @param blockName       The name of the block
     * @param integration     The integration that this block will be part of
     * @param tileEntityClass The class of the block's tile entity
     * @see net.minecraft.block.material.Material for all the materials available
     */
    protected BlockIntegrablePlus(Material material, String blockName, Integration integration, Class<T> tileEntityClass) {
        this(material, blockName, integration);
        this.tileEntityClass = tileEntityClass;
    }

    /**
     * Gets the name of the block
     *
     * @return The name of the block
     */
    protected String getBlockName() {
        return this.blockName;
    }

    /**
     * Sets the default creative tab
     */
    protected void setDefaultCreativeTab() {
        this.setCreativeTab(IntegrationPlus.creativeTab);
    }

    /**
     * Adds the 'press shift for more info' message on the tooltip of the item, but only if the player isn't pressing shift
     *
     * @param info The tooltip's list
     * @return True if the user is pressing shift and the message wasn't added, false if was
     */
    protected boolean addShiftForMoreInfo(List info) {
        if (!GuiScreen.isShiftKeyDown()) {
            addStringToTooltip(StatCollector.translateToLocal(String.format("misc.%s.press_shift", LibMod.MOD_ID)), info);
            return false;
        } else
            return true;
    }

    /**
     * Adds the string to the tooltip's list. Changing the character '&' to '§' to format the string
     *
     * @param message The message to be added on the tooltip's list
     * @param info    The tooltip's list
     * @see net.minecraft.util.EnumChatFormatting for more info
     */
    protected void addStringToTooltip(String message, List<String> info) {
        info.add(message.replace("&", "\u00a7"));
    }

    /**
     * Adds information to the tooltip of the block
     * Since tooltip is a property of an item, the class {@link ItemBlockIntegrablePlus}
     * calls this method to simplify the block's implementation
     *
     * @param itemStack The item stack that the tooltip will be rendered on
     * @param player    The play that will see the tooltip
     * @param info      A list with all the lines of the tooltip
     * @param advanced  True if advanced information (F3+H) should be shown in the tooltip, false if not
     */
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean advanced) {
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + LibMod.bindModId(blockName, '.');
    }

    @Override
    public void registerBlock() {
        GameRegistry.registerBlock(this, ItemBlockIntegrablePlus.class, this.getBlockName());
    }

    @Override
    public void registerTileEntity() {
        if (this instanceof ITileEntityProvider)
            GameRegistry.registerTileEntity(this.tileEntityClass, LibMod.bindModId(this.blockName, '.'));
    }

    @Override
    public void registerRecipes() {
    }

    @Override
    public String getWikiBlockName(int meta) {
        return this.getLocalizedName();
    }

    @Override
    public String getBlockWikiUrlSuffix(int meta) {
        return this.blockName.replace('_', '-');
    }

    @Override
    public boolean hasIntegration() {
        return this.getIntegration() != null;
    }

    @Override
    public Integration getIntegration() {
        return this.integration;
    }
}