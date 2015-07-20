package rc.mods.integrationplus.common.tileentity.botania;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.tileentity.TileEntity;
import rc.mods.integrationplus.common.integration.Integration;
import rc.mods.integrationplus.common.integration.IntegrationBotania;
import rc.mods.integrationplus.common.lib.LibPeripherals;
import vazkii.botania.api.mana.IManaPool;

public class TileEntityManaPoolProxy extends TileEntity implements IPeripheral {

    private static final String[] methods = new String[]{"hasManaPoolAttached", "getManaAmount", "isFull", "getType"};

    @Override
    public String getType() {
        return LibPeripherals.Botania.MANA_POOL_PROXY;
    }

    @Override
    public String[] getMethodNames() {
        return methods;
    }

    @Override
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
        synchronized (this) {
            switch (method) {
                case 0:
                    return new Object[]{this.hasManaPoolAttached()};
                case 1:
                    return new Object[]{this.getManaAmount()};
                case 2:
                    return new Object[]{this.isManaPoolFull()};
                case 3:
                    return new Object[]{this.getManaPoolTye()};
                default:
                    return null;
            }
        }
    }

    @Override
    public void attach(IComputerAccess computer) {
    }

    @Override
    public void detach(IComputerAccess computer) {
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other == this;
    }

    /**
     * Gets a {@link vazkii.botania.api.mana.IManaPool} above of this peripheral
     *
     * @param throwExceptionIfNotAttached Pass true to throw a {@link dan200.computercraft.api.lua.LuaException} when the pool does not exist
     * @return The {@link vazkii.botania.api.mana.IManaPool} above of this peripheral
     * @throws LuaException When the parameter {@code throwExceptionIfNotAttached} is set to true and the {@link vazkii.botania.api.mana.IManaPool} does not exist
     */
    private IManaPool getManaPool(boolean throwExceptionIfNotAttached) throws LuaException {
        TileEntity tile = this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
        if (tile instanceof IManaPool)
            return (IManaPool) tile;
        else {
            if (throwExceptionIfNotAttached)
                throw new LuaException("A mana pool has to be attached for this method to be called");
            else
                return null;
        }
    }

    private boolean hasManaPoolAttached() {
        try {
            return this.getManaPool(false) != null;
        } catch (Exception ex) {
            return false;
        }
    }

    private int getManaAmount() throws LuaException {
        return this.getManaPool(true).getCurrentMana();
    }

    private boolean isManaPoolFull() throws LuaException {
        return getManaPool(true).isFull();
    }

    private String getManaPoolTye() throws LuaException {
        TileEntity manaPool = (TileEntity) this.getManaPool(true);

        if (manaPool.getBlockType() == Integration.BOTANIA.pool) {
            switch (manaPool.getBlockMetadata()) {
                case IntegrationBotania.ManaPoolType.NORMAL:
                    return "normal";
                case IntegrationBotania.ManaPoolType.CREATIVE:
                    return "creative";
                case IntegrationBotania.ManaPoolType.DILUTED:
                    return "diluted";
                default:
                    return "unknown";
            }
        }

        return "unknown";
    }
}
