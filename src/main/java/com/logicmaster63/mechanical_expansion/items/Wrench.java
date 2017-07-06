package com.logicmaster63.mechanical_expansion.items;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class Wrench extends ItemBase {
    private static final String NAME = "Wrench";

    public Wrench() {
        super(NAME);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(Arrays.asList(MechanicalExpansion.instance.MACHINE_LIST).contains(worldIn.getBlockState(pos).getBlock().getUnlocalizedName().substring(5))) {
            worldIn.getBlockState(pos).getBlock().onBlockActivated(worldIn, pos, worldIn.getBlockState(pos), player, EnumHand.MAIN_HAND, facing, hitX, hitY, hitZ);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add("A universal tool");
    }
}
