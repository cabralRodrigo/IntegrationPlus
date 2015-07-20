package rc.mods.integrationplus.common.integration;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import rc.mods.integrationplus.common.lib.LibReference;

public final class IntegrationComputerCraft extends Integration {

    public static class ComputerType {
        public static final int ADVANCED = 16384; /* Value get from dan200.computercraft.shared.computer.items.ItemComputer */
    }

    public static class CableType {
        public static final int WIRED_MODEM = 1;
    }

    public Block cable, computer;

    public IntegrationComputerCraft() {
        super(LibReference.COMPUTER_CRAFT_ID, "Botania");
    }

    @Override
    protected void init() {
        this.cable = GameRegistry.findBlock(this.modId, "CC-Cable");
        this.computer = GameRegistry.findBlock(this.modId, "CC-Computer");
    }

    @Override
    protected void loadSettings(Configuration config) {
    }
}
