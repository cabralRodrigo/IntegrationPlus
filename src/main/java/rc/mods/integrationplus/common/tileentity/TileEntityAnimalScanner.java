package rc.mods.integrationplus.common.tileentity;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.core.lua.LuaJLuaMachine;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import rc.mods.integrationplus.common.lib.LibPeripherals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityAnimalScanner extends TileEntity implements IPeripheral {

    private final static String[] METHODS = new String[]{"getAnimalsInRange", "getScannerRadius"};
    private final static String NBT_RADIUS_KEY = "radius";
    private int radiusSize;

    public TileEntityAnimalScanner() {
    }

    public TileEntityAnimalScanner(int radiusSize) {
        this.radiusSize = radiusSize;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(NBT_RADIUS_KEY))
            this.radiusSize = nbtTagCompound.getInteger(NBT_RADIUS_KEY);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(NBT_RADIUS_KEY, this.radiusSize);
    }

    @Override
    public String getType() {
        return LibPeripherals.ANIMAL_SCANNER;
    }

    @Override
    public String[] getMethodNames() {
        return METHODS;
    }

    @Override
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
        synchronized (this) {
            switch (method) {
                case 0:
                    return this.getAnimalsInRange();
                case 1:
                    return this.getScannerRadius();
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
     * Gets information about all animals inside the scanner range
     *
     * @return Information about all animals inside the scanner range
     */
    private Object[] getAnimalsInRange() {
        Map<Integer, Map<String, Object>> animals = new HashMap<Integer, Map<String, Object>>();
        int tableIndex = 0;

        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord - radiusSize, yCoord + 1, zCoord - radiusSize, xCoord + radiusSize + 1, yCoord + 2, zCoord + radiusSize + 1);
        for (EntityAnimal entity : (List<EntityAnimal>) this.worldObj.getEntitiesWithinAABB(EntityAnimal.class, aabb)) {
            HashMap<String, Object> map = new HashMap<String, Object>(4);
            map.put("id", entity.getUniqueID().toString());
            map.put("type", EntityList.getEntityString(entity));
            map.put("health", entity.getHealth());
            map.put("maxHealth", entity.getMaxHealth());
            map.put("isChild", entity.isChild());

            String nameTag = entity.getCustomNameTag();
            if (nameTag != null && !nameTag.isEmpty())
                map.put("nameTag", nameTag);

            if (entity instanceof EntitySheep)
                map.put("color", ItemDye.field_150921_b[~((EntitySheep) entity).getFleeceColor() & 15]);

            if (entity instanceof EntityHorse) {
                map.put("jump", ((EntityHorse) entity).getHorseJumpStrength());
                map.put("speed", entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());

                String horseType;
                switch (((EntityHorse) entity).getHorseType()) {
                    case 0:
                    default:
                        horseType = "horse";
                        break;
                    case 1:
                        horseType = "donkey";
                        break;
                    case 2:
                        horseType = "mule";
                        break;
                    case 3:
                        horseType = "zombie";
                        break;
                    case 4:
                        horseType = "skeleton";
                        break;
                }
                map.put("horseType", horseType.toLowerCase());
            }

            if (entity instanceof EntityTameable) {
                map.put("isTamed", ((EntityTameable) entity).isTamed());

                EntityLivingBase owner = ((EntityTameable) entity).getOwner();
                if (owner != null)
                    map.put("owner", owner.getCommandSenderName().toLowerCase());
            }

            animals.put(tableIndex++, map);

        }

        return new Object[]{animals};
    }

    private Object[] getScannerRadius() {
        return new Object[]{this.radiusSize};
    }
}
