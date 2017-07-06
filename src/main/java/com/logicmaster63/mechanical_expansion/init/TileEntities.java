package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTile;
import com.logicmaster63.mechanical_expansion.tileEntity.ElectricFurnaceTile;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {

    public static void register() {
        GameRegistry.registerTileEntity(ElectricFurnaceTile.class, "ElectricFurnaceTileEntity");
        GameRegistry.registerTileEntity(CombustionGeneratorTile.class, "CombustionGeneratorTileEntity");
    }
}
