package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {

    public static void register() {
        GameRegistry.registerTileEntity(CombustionGeneratorTileEntity.class, "meCombustionGenerator");
    }
}
