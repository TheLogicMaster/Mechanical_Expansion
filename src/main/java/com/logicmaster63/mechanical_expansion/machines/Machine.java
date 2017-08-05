package com.logicmaster63.mechanical_expansion.machines;

import com.logicmaster63.mechanical_expansion.MachineSide;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public abstract class Machine {      //Make abstract

    public void update() {

    }

    public abstract void openGui(World world, EntityPlayer player, BlockPos pos);

    public void readFromNBT(NBTTagCompound nbt) {

    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return nbt;
    }

    public abstract Block getBlock();

    public abstract Map<EnumFacing, MachineSide> getDefaultSides();
}