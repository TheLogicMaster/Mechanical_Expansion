package com.logicmaster63.mechanical_expansion.items;

import jline.internal.Log;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class WireCutters extends ItemBase {

    public static String NAME = "wire_cutters";

    public WireCutters() {
        super(NAME);
        setMaxStackSize(1);
        setMaxDamage(10);
        setNoRepair();
    }



    @Override
    public boolean hasContainerItem(ItemStack itemStack) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack returnItem = new ItemStack(itemStack.getItem(), 1, itemStack.getItemDamage() + 1);
        returnItem.setTagCompound(itemStack.getTagCompound());
        return returnItem;
    }
}
