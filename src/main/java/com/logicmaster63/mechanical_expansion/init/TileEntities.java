package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTile;
import com.logicmaster63.mechanical_expansion.tileEntity.ElectricFurnaceTile;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineTile;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineTileTESR;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntities {

    public static void init() {
        GameRegistry.registerTileEntity(ElectricFurnaceTile.class, "ElectricFurnaceTileEntity");
        GameRegistry.registerTileEntity(CombustionGeneratorTile.class, "CombustionGeneratorTileEntity");
        GameRegistry.registerTileEntity(MachineTile.class, "MachineTile");

        registerTESR();
    }

    @SideOnly(Side.CLIENT)
    private static void registerTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(MachineTile.class, new MachineTileTESR());
    }
}
