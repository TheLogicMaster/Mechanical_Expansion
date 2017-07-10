package com.logicmaster63.mechanical_expansion.renderer;

import com.logicmaster63.mechanical_expansion.Reference;
import com.logicmaster63.mechanical_expansion.entity.MachineEntity;
import com.logicmaster63.mechanical_expansion.models.ModelMachineEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderMachineEntity extends RenderLiving<MachineEntity> {

    public static float SHADOW_SIZE = 0.7F;

    private ResourceLocation texture;

    public RenderMachineEntity(RenderManager manager) {
        super(manager, new ModelMachineEntity(), SHADOW_SIZE);
        texture = new ResourceLocation(Reference.MODID, "textures/entity/machine_entity.png");
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(MachineEntity entity) {
        return texture;
    }
}
