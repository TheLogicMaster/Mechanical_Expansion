package com.logicmaster63.mechanical_expansion;

import com.logicmaster63.mechanical_expansion.containers.CombustionGeneratorContainer;
import com.logicmaster63.mechanical_expansion.containers.ElectricFurnaceContainer;
import com.logicmaster63.mechanical_expansion.guis.CombustionGeneratorGUI;
import com.logicmaster63.mechanical_expansion.guis.ElectricFurnaceGUI;
import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTileEntity;
import com.logicmaster63.mechanical_expansion.tileEntity.ElectricFurnaceTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == 0)
            return new CombustionGeneratorContainer(player.inventory, (CombustionGeneratorTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == 1)
            return new ElectricFurnaceContainer(player.inventory, (ElectricFurnaceTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new CombustionGeneratorGUI(player.inventory, (CombustionGeneratorTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        if (ID == 1)
            return new ElectricFurnaceGUI(player.inventory, (ElectricFurnaceTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}
