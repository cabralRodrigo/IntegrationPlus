package rc.mods.integrationplus.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import rc.mods.integrationplus.common.block.BlockAnimalScanner;

public class ItemBlockAnimalScanner extends ItemBlockIntegrablePlus {
    public ItemBlockAnimalScanner(Block block) {
        super(block);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        String type = "unknown";

        if (meta >= 0 && meta < BlockAnimalScanner.SUB_TYPES.length)
            type = BlockAnimalScanner.SUB_TYPES[meta];

        return String.format("%s.%s", this.field_150939_a.getUnlocalizedName(), type);
    }
}
