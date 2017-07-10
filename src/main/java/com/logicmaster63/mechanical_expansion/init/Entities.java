package com.logicmaster63.mechanical_expansion.init;

import com.logicmaster63.mechanical_expansion.MechanicalExpansion;
import com.logicmaster63.mechanical_expansion.Reference;
import com.logicmaster63.mechanical_expansion.entity.MachineEntity;
import com.logicmaster63.mechanical_expansion.renderer.RenderMachineEntityFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class Entities {

    public static void init() {

        RenderingRegistry.registerEntityRenderingHandler(MachineEntity.class, RenderMachineEntityFactory.INSTANCE);
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "machine_entity"), MachineEntity.class, "machine_entity", 100, MechanicalExpansion.instance, 128, 1, false, 100, 200);
    }
}
