package rc.mods.integrationplus.common.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import rc.mods.integrationplus.common.block.ModBlocks;
import rc.mods.integrationplus.common.integration.IIntegrableResource;
import rc.mods.integrationplus.common.lib.LibMod;

import java.util.Iterator;
import java.util.List;

public class IntegrationPlusCreativeTab extends CreativeTabs {

    public IntegrationPlusCreativeTab() {
        super(LibMod.MOD_ID);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ModBlocks.manaPoolPeripheral);
    }

    @Override
    public void displayAllReleventItems(List items) {
        Iterator iterator = Item.itemRegistry.iterator();

        while (iterator.hasNext()) {
            Item item = (Item) iterator.next();

            if (item == null) {
                continue;
            }

            for (CreativeTabs tab : item.getCreativeTabs()) {
                if (tab == this) {
                    if (item instanceof IIntegrableResource) {
                        IIntegrableResource integrableResource = (IIntegrableResource) item;
                        if (integrableResource.hasIntegration() && !integrableResource.getIntegration().isIntegrationAvailable())
                            continue;
                    }

                    item.getSubItems(item, this, items);
                }
            }
        }
    }
}
