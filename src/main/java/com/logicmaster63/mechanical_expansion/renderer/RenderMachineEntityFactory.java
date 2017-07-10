package com.logicmaster63.mechanical_expansion.renderer;

import com.logicmaster63.mechanical_expansion.entity.MachineEntity;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMachineEntityFactory implements IRenderFactory<MachineEntity> {

    public static final RenderMachineEntityFactory INSTANCE = new RenderMachineEntityFactory();

    @Override
    public Render<? super MachineEntity> createRenderFor(RenderManager manager) {
        return new RenderMachineEntity(manager);
    }
}
