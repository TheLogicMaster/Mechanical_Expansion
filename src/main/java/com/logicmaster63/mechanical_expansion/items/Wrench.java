package com.logicmaster63.mechanical_expansion.items;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class Wrench extends Item{
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(Arrays.asList(MechanicalExpansion.instance.MACHINE_LIST).contains(worldIn.getBlockState(pos).getBlock().getUnlocalizedName().substring(5))) {
            worldIn.getBlockState(pos).getBlock().onBlockActivated(worldIn, pos, worldIn.getBlockState(pos), playerIn, side, hitX, hitY, hitZ);
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add("A universal tool");
    }
}
