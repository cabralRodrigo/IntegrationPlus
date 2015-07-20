package rc.mods.integrationplus.common.integration.botania;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import rc.mods.integrationplus.common.integration.Integration;
import rc.mods.integrationplus.common.lib.LibReference;

public final class IntegrationBotania extends Integration {

    public static class RuneType {
        public static final int MANA = 8;
    }

    public static class ManaResourceType {
        public static final int PIXIE_DUST = 8;
    }

    public static class ManaPoolType {
        public static final int NORMAL = 0;
        public static final int CREATIVE = 1;
        public static final int DILUTED = 2;
    }

    public static final int MAX_MANA = 100000;

    public Block pool;
    public Item rune, manaResource;

    public IntegrationBotania() {
        super(LibReference.BOTANIA_ID, "Botania");
    }

    @Override
    protected void init() {
        this.pool = GameRegistry.findBlock(this.modId, "pool");
        this.rune = GameRegistry.findItem(this.modId, "rune");
        this.manaResource = GameRegistry.findItem(this.modId, "manaResource");
        IntegrationPlusWikiProvider.registerWikiProvider();
    }
}
