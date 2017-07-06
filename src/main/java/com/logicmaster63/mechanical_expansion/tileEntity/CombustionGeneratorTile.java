package com.logicmaster63.mechanical_expansion.tileEntity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import com.logicmaster63.mechanical_expansion.MachineSide;
import jline.internal.Log;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class CombustionGeneratorTile extends MachineBaseTile implements IEnergyProvider {

    private static final int NUMBER_OF_SLOTS = 1;
    private static final String NAME = "Combustion Generator";

    protected EnergyStorage storage = new EnergyStorage(32000, 200, 200);
    public String customName;
    public boolean isBurning;
    public int burnTime;
    public int burnInc;

    public CombustionGeneratorTile() {
        super(NAME, NUMBER_OF_SLOTS, 32000, 20, 20, MachineSide.PRODUCER, true);
    }

    @Override
    public void update() {
        BlockPos blockPos = new BlockPos(0, 0, 0);
        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0:
                    blockPos = getPos().add(-1, 0, 0);
                    break;
                case 1:
                    blockPos = getPos().add(1, 0, 0);
                    break;
                case 2:
                    blockPos = getPos().add(0, -1, 0);
                    break;
                case 3:
                    blockPos = getPos().add(0, 1, 0);
                    break;
                case 4:
                    blockPos = getPos().add(0, 0, -1);
                    break;
                case 5:
                    blockPos = getPos().add(0, 0, 1);
                    break;
            }
            if (getWorld().getTileEntity(blockPos) instanceof IEnergyReceiver) {
                IEnergyReceiver ier = (IEnergyReceiver) getWorld().getTileEntity(blockPos);
                if (ier.getEnergyStored(EnumFacing.getFront(i)) < ier.getMaxEnergyStored(EnumFacing.getFront(i))) {
                    int maxExtract = getStorage().getMaxExtract();
                    int extractAmount = Math.min(this.extractEnergy(EnumFacing.getFront(i), maxExtract, true), ier.receiveEnergy(EnumFacing.getFront(i), maxExtract, true));
                    ier.receiveEnergy(EnumFacing.getFront(i), this.extractEnergy(EnumFacing.getFront(i).getOpposite(), extractAmount, false), false);
                }
                markDirty();
            }
        }

        if (isBurning) {
            storage.setEnergyStored(storage.getEnergyStored() + 20);
            if (storage.getEnergyStored() > storage.getMaxEnergyStored())
                storage.setEnergyStored(storage.getMaxEnergyStored());
            burnInc++;
            if (burnInc >= burnTime) {
                isBurning = false;
                burnInc = 0;
            }
            markDirty();
        } else {
            if (TileEntityFurnace.getItemBurnTime(getStackInSlot(0)) != 0 && storage.getEnergyStored() < storage.getMaxEnergyStored()) {
                burnTime = TileEntityFurnace.getItemBurnTime(getStackInSlot(0));
                isBurning = true;
                getStackInSlot(0).splitStack(1);
                if (getStackInSlot(0).getCount() <= 0)
                    this.removeStackFromSlot(0);
                markDirty();
            }
        }
    }

    @Override
    public int extractEnergy(EnumFacing facing, int maxExtract, boolean simulate) {
        if(!sides.get(facing).extractRF)
            return 0;
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }
}
