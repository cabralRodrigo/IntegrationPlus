package rc.mods.integrationplus.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import rc.mods.integrationplus.common.integration.Integration;
import rc.mods.integrationplus.common.integration.IntegrationComputerCraft;
import rc.mods.integrationplus.common.item.block.ItemBlockAnimalScanner;
import rc.mods.integrationplus.common.lib.LibBlocks;
import rc.mods.integrationplus.common.lib.LibMod;
import rc.mods.integrationplus.common.tileentity.TileEntityAnimalScanner;

import java.util.List;

public class BlockAnimalScanner extends BlockIntegrablePlus<TileEntityAnimalScanner> implements ITileEntityProvider, IPeripheralProvider {

    public final static String[] SUB_TYPES = new String[]{"basic", "enhanced", "advanced"};
    private final static int[] SUB_TYPES_RANGE = new int[]{1, 2, 3};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons_side, icons_bottom, icons_top;

    protected BlockAnimalScanner() {
        super(Material.rock, LibBlocks.ANIMAL_SCANNER, Integration.MINECRAFT, TileEntityAnimalScanner.class);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        if (meta >= 0 && meta < SUB_TYPES.length)
            return new TileEntityAnimalScanner(SUB_TYPES_RANGE[meta]);
        else
            return null;
    }

    @Override
    public void registerBlock() {
        GameRegistry.registerBlock(this, ItemBlockAnimalScanner.class, this.getBlockName());
        ComputerCraftAPI.registerPeripheralProvider(this);
    }

    @Override
    public int damageDropped(int damage) {
        return damage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List items) {
        for (int i = 0; i < SUB_TYPES.length; i++)
            items.add(new ItemStack(this, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icons_side = new IIcon[SUB_TYPES.length];
        this.icons_bottom = new IIcon[SUB_TYPES.length];
        this.icons_top = new IIcon[SUB_TYPES.length];
        for (int i = 0; i < SUB_TYPES.length; i++) {
            this.icons_side[i] = iconRegister.registerIcon(LibMod.bindModId(String.format("%s_%s_side", this.getBlockName(), SUB_TYPES[i]), ':'));
            this.icons_bottom[i] = iconRegister.registerIcon(LibMod.bindModId(String.format("%s_%s_bottom", this.getBlockName(), SUB_TYPES[i]), ':'));
            this.icons_top[i] = iconRegister.registerIcon(LibMod.bindModId(String.format("%s_%s_top", this.getBlockName(), SUB_TYPES[i]), ':'));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta >= 0 && meta < SUB_TYPES.length)
            switch (EnumFacing.getFront(side)) {
                case UP:
                    return icons_top[meta];
                case DOWN:
                    return icons_bottom[meta];
                default:
                    return icons_side[meta];
            }
        else
            return null;
    }

    @Override
    public void registerRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), new Object[]{
                "gRg",
                "cCc",
                "sss",
                'g', "glowstone",
                'R', "blockRedstone",
                'c', new ItemStack(Integration.COMPUTER_CRAFT.cable, 1, IntegrationComputerCraft.CableType.WIRED_MODEM),
                'C', new ItemStack(Integration.COMPUTER_CRAFT.computer, 1, IntegrationComputerCraft.ComputerType.ADVANCED),
                's', "stone"
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), new Object[]{
                "ggg",
                "gSg",
                "LLL",
                'g', "ingotGold",
                'S', new ItemStack(this, 1, 0),
                'L', "blockLapis"
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), new Object[]{
                "ddd",
                "dSd",
                "GGG",
                'd', "gemDiamond",
                'S', new ItemStack(this, 1, 1),
                'G', "blockGold"
        }));
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean advanced) {
        int meta = itemStack.getItemDamage();
        if (meta >= 0 && meta < SUB_TYPES.length) {
            if (this.addShiftForMoreInfo(info)) {
                this.addStringToTooltip(StatCollector.translateToLocal(String.format("tile.%s.info.0", LibMod.bindModId(this.getBlockName(), '.'))), info);
                if (SUB_TYPES_RANGE[meta] == 1)
                    this.addStringToTooltip(StatCollector.translateToLocalFormatted(String.format("tile.%s.info.1", LibMod.bindModId(this.getBlockName(), '.')), SUB_TYPES_RANGE[meta]), info);
                else
                    this.addStringToTooltip(StatCollector.translateToLocalFormatted(String.format("tile.%s.info.2", LibMod.bindModId(this.getBlockName(), '.')), SUB_TYPES_RANGE[meta]), info);
            }
        }
    }

    @Override
    public String getWikiBlockName(int meta) {
        return Item.getItemFromBlock(this).getItemStackDisplayName(new ItemStack(this, 1, meta));
    }

    @Override
    public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityAnimalScanner)
            return (IPeripheral) tileEntity;
        else
            return null;
    }
}
