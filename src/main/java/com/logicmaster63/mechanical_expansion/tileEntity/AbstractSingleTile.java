package com.logicmaster63.mechanical_expansion.tileEntity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public abstract class AbstractSingleTile extends TileEntity implements IEnergyReceiver, ITickable, ISidedInventory {

    public EnergyStorage storage = new EnergyStorage(32000, 20, 20);
    public ItemStack[] itemStacks = new ItemStack[2];
    public String customName;
    public boolean isSmelting;
    public int sideState[] = new int[]{2, 1, 1, 1, 1, 1};
    public final int[] SLOTS_IN = new int[]{0};
    public final int[] SLOTS_OUT = new int[]{1};
    public final int[] SLOTS_BI = new int[]{0, 1};
    public final int[] SLOTS_NO = new int[]{};

    @Override
    public void update() {
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        int state = sideState[direction.getIndex()];
        System.out.println((state & 1) == 1);
        return  ((state & 1) == 1);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        int state = sideState[direction.getIndex()];
        return (state > 1);
    }
}
