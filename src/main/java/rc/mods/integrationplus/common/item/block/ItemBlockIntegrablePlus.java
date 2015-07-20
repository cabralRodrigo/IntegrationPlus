package rc.mods.integrationplus.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import rc.mods.integrationplus.common.block.BlockIntegrablePlus;
import rc.mods.integrationplus.common.integration.IIntegrableResource;
import rc.mods.integrationplus.common.integration.Integration;

import java.util.List;

public class ItemBlockIntegrablePlus extends ItemBlock implements IIntegrableResource {
    public ItemBlockIntegrablePlus(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean advanced) {
        //Call this method on the block to simplify the block's implementation
        if (this.field_150939_a instanceof BlockIntegrablePlus)
            ((BlockIntegrablePlus) this.field_150939_a).addInformation(itemStack, player, info, advanced);
    }

    @Override
    public boolean hasIntegration() {
        if (this.field_150939_a instanceof BlockIntegrablePlus)
            return ((BlockIntegrablePlus) this.field_150939_a).hasIntegration();
        else
            return false;
    }

    @Override
    public Integration getIntegration() {
        if (this.field_150939_a instanceof BlockIntegrablePlus)
            return ((BlockIntegrablePlus) this.field_150939_a).getIntegration();
        else
            return null;
    }
}
