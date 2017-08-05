package com.logicmaster63.mechanical_expansion.machines;

import com.logicmaster63.mechanical_expansion.GUIHandler;
import com.logicmaster63.mechanical_expansion.MachineSide;
import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class CombustionGenerator extends Machine {

    @Override
    public void openGui(World world, EntityPlayer player, BlockPos pos) {
        player.openGui(MechanicalExpansion.instance, GUIHandler.GUI_COMBUSTION_GENERATOR, world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public Block getBlock() {
        return Blocks.combustion_generator;
    }

    @Override
    public Map<EnumFacing, MachineSide> getDefaultSides() {
        return new HashMap<>(MachineSide.PRODUCER);
    }
}
