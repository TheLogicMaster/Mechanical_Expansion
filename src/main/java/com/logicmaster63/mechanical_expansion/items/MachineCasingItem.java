package com.logicmaster63.mechanical_expansion.items;

import com.logicmaster63.mechanical_expansion.blocks.MachineCasing;
import jline.internal.Log;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class MachineCasingItem extends ItemBlock {

    public MachineCasingItem() {
        super(new MachineCasing());
        setRegistryName("machine_casing");
        setUnlocalizedName("machine_casing");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean show) {
        //super.addInformation(itemStack, player, list, show);
        list.add("A block that burns solid");
        list.add("fuels to generate RF");
        list.add("");
        if (itemStack.getTagCompound() != null)
            list.add("Has stored settings");
    }

    @Override
    public String toString() {
        return "MachineCasing: ";
    }
}
