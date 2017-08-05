package com.logicmaster63.mechanical_expansion.tileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class MachineTileTESR extends TileEntitySpecialRenderer<MachineTile> {

    @Override
    public void renderTileEntityAt(MachineTile machineTile, double x, double y, double z, float partialTicks, int blockDamageProgress) {
        if(machineTile.getMachine() == null)
            return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 1/20f, y + 1/20f, z + 1/20f);
        GlStateManager.disableLighting();
        //GlStateManager.disableRescaleNormal();
        GlStateManager.scale(18/20f, 18/20f, 18/20f);
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.world;
        IBakedModel ibakedmodel = mc.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(machineTile.getMachine().getBlock().getDefaultState());
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        //tessellator.getBuffer().setTranslation(x - (double)te.getPos().getX() + 1/20f, y - (double)te.getPos().getY() + 1/20f, z - (double)te.getPos().getZ() + 1/20f);
        mc.getBlockRendererDispatcher().getBlockModelRenderer().renderModel(world, ibakedmodel, machineTile.getMachine().getBlock().getDefaultState(), new BlockPos(0,0,0), tessellator.getBuffer(), true);
        tessellator.draw();
        //tessellator.getBuffer().setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.popMatrix();
    }
}