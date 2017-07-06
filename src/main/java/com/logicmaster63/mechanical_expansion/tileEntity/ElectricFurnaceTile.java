package com.logicmaster63.mechanical_expansion.tileEntity;

import cofh.api.energy.IEnergyReceiver;
import com.logicmaster63.mechanical_expansion.MachineSide;
import jline.internal.Log;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class ElectricFurnaceTile extends MachineBaseTile implements IEnergyReceiver {

    private static final int NUMBER_OF_SLOTS = 2;
    private static final String NAME = "Electric Furnace";

    public ElectricFurnaceTile() {
        super(NAME, NUMBER_OF_SLOTS, 32000, 20, 20, MachineSide.CONSUMER, true);
    }

    @Override
    public void update() {
        if(world.isRemote)
            storage.setEnergyStored(getField(0));
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
        //return (index == 0 && FurnaceRecipes.instance().getSmeltingResult(stack) != null);
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {  //Make this part of an inherritted consumer class
        if(!sides.get(from).insertRF)
            return 0;
        markDirty();
        return getStorage().receiveEnergy(maxReceive, simulate);
    }
}
