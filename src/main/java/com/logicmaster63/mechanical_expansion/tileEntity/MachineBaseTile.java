package com.logicmaster63.mechanical_expansion.tileEntity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import com.logicmaster63.mechanical_expansion.MachineSide;
import jline.internal.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;

public abstract class MachineBaseTile extends TileEntity implements ITickable, ISidedInventory, IEnergyHandler {

    protected EnergyStorage storage;
    protected ItemStack[] itemStacks;
    protected Map<EnumFacing, MachineSide> sides, defaultSides;
    protected String name;
    protected boolean keepsInv;

    public String customName;

    public MachineBaseTile(String name, int slotNum, int rfCapacity, int rfIn, int rfOut, Map<EnumFacing, MachineSide> defaultSides, boolean keepsInv) { //Add energystorage stuff to constructor
        itemStacks = new ItemStack[slotNum];
        clear();
        this.defaultSides = defaultSides;
        this.keepsInv = keepsInv;
        sides = MachineSide.cpy(defaultSides);
        storage = new EnergyStorage(rfCapacity, rfIn, rfOut);
        this.name = name;
    }

    public EnergyStorage getStorage() {
        return storage;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new SPacketUpdateTileEntity(new BlockPos(pos), 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public int getSizeInventory() {
        return itemStacks.length;
    }

    @Override
    public ItemStack removeStackFromSlot(int slotIndex) {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (!itemStack.isEmpty()) setInventorySlotContents(slotIndex, ItemStack.EMPTY);
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return itemStacks[slotIndex];
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemstack) {
        itemStacks[slotIndex] = itemstack;
        if (itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit()) {
            itemstack.setCount(getInventoryStackLimit());
        }
        markDirty();
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int count) {
        ItemStack itemStackInSlot = getStackInSlot(slotIndex);
        if (itemStackInSlot.isEmpty()) return ItemStack.EMPTY;

        ItemStack itemStackRemoved;
        if (itemStackInSlot.getCount() <= count) {
            itemStackRemoved = itemStackInSlot;
            setInventorySlotContents(slotIndex, ItemStack.EMPTY);
        } else {
            itemStackRemoved = itemStackInSlot.splitStack(count);
            if (itemStackInSlot.getCount() == 0) {
                setInventorySlotContents(slotIndex, ItemStack.EMPTY);
            }
        }
        markDirty();
        return itemStackRemoved;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack items: itemStacks)
            if(!items.isEmpty())
                return false;
        return true;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return sides.get(direction).insertItem;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return sides.get(direction).extractItem;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {    //Finish
        return new int[0];
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public int getField(int id) {
        return storage.getEnergyStored();
    }

    @Override
    public void setField(int id, int value) {
        storage.setEnergyStored(value);
    }

    @Override
    public int getFieldCount() {
        return 1;
    }

    @Override
    public void clear() {
        Arrays.fill(itemStacks, ItemStack.EMPTY);
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : name;
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        final byte NBT_TYPE_COMPOUND = 10;
        NBTTagList dataForAllSlots = nbt.getTagList("Items", NBT_TYPE_COMPOUND);
        Arrays.fill(itemStacks, ItemStack.EMPTY);
        for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
            NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
            int slotIndex = dataForOneSlot.getByte("Slot") & 255;
            if (slotIndex >= 0 && slotIndex < this.itemStacks.length) {
                this.itemStacks[slotIndex] = new ItemStack(dataForOneSlot);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
        NBTTagList dataForAllSlots = new NBTTagList();
        for (int i = 0; i < this.itemStacks.length; ++i) {
            if (!itemStacks[i].isEmpty()){
                NBTTagCompound dataForThisSlot = new NBTTagCompound();
                dataForThisSlot.setByte("Slot", (byte) i);
                this.itemStacks[i].writeToNBT(dataForThisSlot);
                dataForAllSlots.appendTag(dataForThisSlot);
            }
        }
        nbt.setTag("Items", dataForAllSlots);
        return nbt;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.getWorld().getTileEntity(this.pos) == this && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 64;
    }

    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (CapabilityEnergy.ENERGY == capability) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing) {
        if (CapabilityEnergy.ENERGY == capability) {
            return (T) storage;
        }
        return super.getCapability(capability, facing);
    }
}
