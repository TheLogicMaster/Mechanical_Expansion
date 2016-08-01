package com.logicmaster63.mechanical_expansion.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CombustionGeneratorItem extends ItemBlock{
    public CombustionGeneratorItem(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean show) {
        list.add("A block that burns solid");
        list.add("fuels to generate RF");
        list.add("");
        if (stack.getTagCompound() != null) {
            list.add("Has stored settings");
        } else {
            if (player.isSneaking()) {

            } else {

            }
        }
    }
}
