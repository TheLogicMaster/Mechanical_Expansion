package com.logicmaster63.mechanical_expansion.guis;

import com.logicmaster63.mechanical_expansion.containers.ElectricFurnaceContainer;
import com.logicmaster63.mechanical_expansion.tileEntity.ElectricFurnaceTile;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineTile;
import jline.internal.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class ElectricFurnaceGUI extends GuiContainer {

    FontRenderer fontRenderer;
    MachineTile te;
    BlockPos pos;

    public ElectricFurnaceGUI(InventoryPlayer inventoryPlayer, MachineTile te) {
        super(new ElectricFurnaceContainer(inventoryPlayer, te));
        this.xSize = 176;
        this.ySize = 166;
        this.te = te;
        this.pos = te.getPos();
        this.fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("me:textures/gui/combustion_engine.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(te.getName(), this.xSize / 2 - this.fontRendererObj.getStringWidth(te.getName()) / 2, 6, 4210752);
        fontRenderer.drawString(Integer.toString(te.getStorage().getEnergyStored()), 8, 6, 4210752);
    }

    @Override
    public void updateScreen() {
        te = (MachineTile) Minecraft.getMinecraft().world.getTileEntity(pos);
        super.updateScreen();
    }
}
