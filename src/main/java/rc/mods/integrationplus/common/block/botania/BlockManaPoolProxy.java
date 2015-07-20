package rc.mods.integrationplus.common.block.botania;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import rc.mods.integrationplus.common.block.BlockIntegrablePlus;
import rc.mods.integrationplus.common.block.ModBlocks;
import rc.mods.integrationplus.common.integration.Integration;
import rc.mods.integrationplus.common.integration.botania.IntegrationBotania;
import rc.mods.integrationplus.common.integration.IntegrationComputerCraft;
import rc.mods.integrationplus.common.lib.LibBlocks;
import rc.mods.integrationplus.common.tileentity.botania.TileEntityManaPoolProxy;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IPoolOverlayProvider;

import java.util.List;

public class BlockManaPoolProxy extends BlockIntegrablePlus<TileEntityManaPoolProxy> implements IPeripheralProvider, ITileEntityProvider, IPoolOverlayProvider {

    @SideOnly(Side.CLIENT)
    private IIcon iconSide, iconTop, iconBottom;

    public BlockManaPoolProxy() {
        super(Material.rock, LibBlocks.MANA_POOL_PROXY, Integration.BOTANIA, TileEntityManaPoolProxy.class);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean advanced) {
        if (this.addShiftForMoreInfo(info)) {
            addStringToTooltip(StatCollector.translateToLocal(this.getUnlocalizedName() + ".info.0"), info);
            addStringToTooltip(StatCollector.translateToLocal(this.getUnlocalizedName() + ".info.1"), info);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        iconSide = iconRegister.registerIcon(this.getTextureName() + "_side");
        iconTop = iconRegister.registerIcon(this.getTextureName() + "_top");
        iconBottom = iconRegister.registerIcon(this.getTextureName() + "_bottom");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        switch (EnumFacing.getFront(side)) {
            case DOWN:
                return iconBottom;
            case UP:
                return iconTop;
            default:
                return iconSide;
        }
    }

    @Override
    public void registerBlock() {
        super.registerBlock();
        ComputerCraftAPI.registerPeripheralProvider(this);
    }

    @Override
    public void registerRecipes() {
        BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModBlocks.manaPoolPeripheral, 1), IntegrationBotania.MAX_MANA / 5, new Object[]{
                new ItemStack(Integration.BOTANIA.pool),
                new ItemStack(Integration.BOTANIA.rune, 1, IntegrationBotania.RuneType.MANA),
                new ItemStack(Integration.BOTANIA.rune, 1, IntegrationBotania.RuneType.MANA),
                new ItemStack(Integration.BOTANIA.manaResource, 1, IntegrationBotania.ManaResourceType.PIXIE_DUST),
                new ItemStack(Integration.BOTANIA.manaResource, 1, IntegrationBotania.ManaResourceType.PIXIE_DUST),
                new ItemStack(Integration.COMPUTER_CRAFT.cable, 1, IntegrationComputerCraft.CableType.WIRED_MODEM),
                new ItemStack(Integration.COMPUTER_CRAFT.computer, 1, IntegrationComputerCraft.ComputerType.ADVANCED)

        });
    }

    @Override
    public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityManaPoolProxy)
            return (IPeripheral) tileEntity;
        else
            return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityManaPoolProxy();
    }

    @Override
    public IIcon getIcon(World world, int x, int y, int z) {
        return this.getIcon(1, 0);
    }
}
