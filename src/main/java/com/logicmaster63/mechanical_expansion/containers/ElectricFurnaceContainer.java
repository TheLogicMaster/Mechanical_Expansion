package com.logicmaster63.mechanical_expansion.containers;

import com.logicmaster63.mechanical_expansion.tileEntity.ElectricFurnaceTile;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElectricFurnaceContainer extends Container {

    private MachineTile te;
    private int energy;

    public ElectricFurnaceContainer(InventoryPlayer playerInv, MachineTile te) {
        this.te = te;

        this.addSlotToContainer(new Slot(te, 0, 60, 50));
        this.addSlotToContainer(new Slot(te, 1, 100, 50));

        // Player Inventory, Slot 9-35, Slot IDs 9-35
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs 36-44
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        te.setField(id, data);
    }

    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, te);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < listeners.size(); i++) {
            IContainerListener icontainerlistener = listeners.get(i);
            if(te.getStorage().getEnergyStored() != energy)
                icontainerlistener.sendProgressBarUpdate(this, 0, te.getStorage().getEnergyStored());
        }
        energy = te.getStorage().getEnergyStored();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.te.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index) {
        ItemStack stack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);
        stack = slot.getStack();
        if (index == 0) {
            if (stack != null) {
                if (this.mergeItemStack(stack, 1, 35, false)) {
                    slot.putStack((ItemStack)null);
                } else
                    return null;
            } else {
                return null;
            }
        } else {
            if (stack != null) {
                if (TileEntityFurnace.getItemBurnTime(stack) != 0) {
                    if (this.mergeItemStack(stack, 0, 1, false)) {
                        slot.putStack((ItemStack) null);
                    } else
                        return null;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return stack;
    }
}
