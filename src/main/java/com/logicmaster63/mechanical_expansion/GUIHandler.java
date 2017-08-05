package com.logicmaster63.mechanical_expansion;

import com.logicmaster63.mechanical_expansion.containers.CombustionGeneratorContainer;
import com.logicmaster63.mechanical_expansion.containers.ElectricFurnaceContainer;
import com.logicmaster63.mechanical_expansion.guis.CombustionGeneratorGUI;
import com.logicmaster63.mechanical_expansion.guis.ElectricFurnaceGUI;
import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTile;
import com.logicmaster63.mechanical_expansion.tileEntity.ElectricFurnaceTile;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler{

    public static final int GUI_COMBUSTION_GENERATOR = 0;
    public static final int GUI_ELECTRIC_FURNACE = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == 0)
            return new CombustionGeneratorContainer(player.inventory, (MachineTile) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == 1)
            return new ElectricFurnaceContainer(player.inventory, (MachineTile) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new CombustionGeneratorGUI(player.inventory, (MachineTile) world.getTileEntity(new BlockPos(x, y, z)));
        if (ID == 1)
            return new ElectricFurnaceGUI(player.inventory, (MachineTile) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}
