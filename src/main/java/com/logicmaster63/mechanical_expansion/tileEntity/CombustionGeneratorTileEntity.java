package com.logicmaster63.mechanical_expansion.tileEntity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.property.IExtendedBlockState;

public class CombustionGeneratorTileEntity extends TileEntity implements IEnergyProvider, ITickable, ISidedInventory{

    protected EnergyStorage storage = new EnergyStorage(32000, 200, 200);
    protected ItemStack inventory;
    public String customName;
    public boolean isBurning;
    public int burnTime;
    public int burnInc;

    public CombustionGeneratorTileEntity() {
    }

    @Override
    public void onLoad() {

    }

    public EnergyStorage getStorage() {
        return storage;
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
                worldObj.markBlockForUpdate(getPos());
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
            worldObj.markBlockForUpdate(getPos());
            markDirty();
        }
        else {
            if (TileEntityFurnace.getItemBurnTime(inventory) != 0 && storage.getEnergyStored() < storage.getMaxEnergyStored()) {
                burnTime = TileEntityFurnace.getItemBurnTime(inventory);
                isBurning = true;
                inventory.splitStack(1);
                if (inventory.stackSize <= 0)
                    inventory = null;
                worldObj.markBlockForUpdate(getPos());
                markDirty();
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);

        NBTTagCompound stackTag = nbt.getCompoundTag("Inventory");
        inventory = ItemStack.loadItemStackFromNBT(stackTag);
        if (nbt.hasKey("CustomName", 8)) {
            this.customName = (nbt.getString("CustomName"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);

        if(inventory != null) {
            NBTTagCompound stackTag = new NBTTagCompound();
            inventory.writeToNBT(stackTag);
            nbt.setTag("Inventory", stackTag);
        }
        if (this.hasCustomName()) {
            nbt.setString("CustomName", this.customName);
        }
    }

    @Override
    public boolean canConnectEnergy(EnumFacing facing) {
        return true;
    }

    @Override
    public int extractEnergy(EnumFacing facing, int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing facing) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing facing) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[]{0};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        if (TileEntityFurnace.getItemBurnTime(itemStackIn) != 0)
        return true;
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if(inventory != null) {
            ItemStack itemstack;
            if(inventory.stackSize <= count) {
                itemstack = inventory;
                inventory = null;
                this.markDirty();
                return itemstack;
            } else {
                itemstack = inventory.splitStack(count);
                if (this.getStackInSlot(index).stackSize <= 0) {
                    inventory = null;
                } else {
                    inventory = getStackInSlot(index);
                }
                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = inventory;
        inventory = null;
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        inventory = null;
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.combustion_generator";
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public IChatComponent getDisplayName() {
        return null;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return new S35PacketUpdateTileEntity(getPos(), 1, syncData);
    }
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }
}
