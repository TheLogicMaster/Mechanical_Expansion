package com.logicmaster63.mechanical_expansion.tileEntity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import com.logicmaster63.mechanical_expansion.MachineSide;
import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.machines.Machine;
import jline.internal.Log;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MachineTile extends TileEntity implements ITickable, ISidedInventory, IEnergyReceiver, IEnergyProvider {

    private EnergyStorage storage;
    private ItemStack[] itemStacks;
    private Map<EnumFacing, MachineSide> sides, defaultSides;
    private String name;
    private boolean keepsInv;
    private Machine machine;

    public String customName;

    public MachineTile() {
        itemStacks = new ItemStack[2];
        clear();
        //this.defaultSides = defaultSides;
        this.keepsInv = keepsInv;
        //sides = MachineSide.cpy(defaultSides);
        storage = new EnergyStorage(32000, 20, 20);
        //this.name = name;
    }

    public Machine getMachine() {
        return machine;
    }

    @Override
    public void update() {
        if(world.isRemote)
            storage.setEnergyStored(getField(0));
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_) {
        if(machine == null)
            return false;
        machine.openGui(world, player, pos);
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
        //return (index == 0 && FurnaceRecipes.instance().getSmeltingResult(stack) != null);
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {  //Make this part of an inherritted consumer class
        if(machine == null || !sides.get(from).insertRF)
            return 0;
        markDirty();
        return getStorage().receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        if(machine == null || !sides.get(from).extractRF)
            return 0;
        markDirty();
        return getStorage().extractEnergy(maxExtract, simulate);
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
        MechanicalExpansion.LOGGER.error("Read: " + nbt);
        //Log.error("Read " + (getWorld().isRemote? "client":"server") + ": " + nbt.getString("Machine"));
        try {
            machine = (Machine) Class.forName(nbt.getString("Machine")).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            MechanicalExpansion.LOGGER.error(new Exception("Machine: " + nbt.getString("Machine"), e));
        }
        NBTTagList dataForAllSlots = nbt.getTagList("Items", 10);
        Arrays.fill(itemStacks, ItemStack.EMPTY);
        for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
            NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
            int slotIndex = dataForOneSlot.getByte("Slot") & 255;
            if (slotIndex >= 0 && slotIndex < this.itemStacks.length) {
                this.itemStacks[slotIndex] = new ItemStack(dataForOneSlot);
            }
        }
        if(machine != null) {
            sides = machine.getDefaultSides();
            defaultSides = new HashMap<>(sides);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
        if(machine != null)
            nbt.setString("Machine", machine.getClass().getName());
        //if(machine != null)
            //Log.error("Write: " + machine.getClass().getName());
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
        MechanicalExpansion.LOGGER.error("Write: " + nbt);
        return nbt;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.getWorld().getTileEntity(this.pos) == this && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 64;
    }
}
