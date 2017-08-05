package com.logicmaster63.mechanical_expansion.guis;

import com.logicmaster63.mechanical_expansion.containers.CombustionGeneratorContainer;
import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTile;
import com.logicmaster63.mechanical_expansion.tileEntity.MachineTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class CombustionGeneratorGUI extends GuiContainer {

    FontRenderer fontRenderer;
    MachineTile te;

    public CombustionGeneratorGUI (InventoryPlayer inventoryPlayer, MachineTile te) {
        super(new CombustionGeneratorContainer(inventoryPlayer, te));
        this.xSize = 176;
        this.ySize = 166;
        this.te = te;
        this.fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("me:textures/gui/electric_furnace.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(te.getName(), this.xSize / 2 - this.fontRendererObj.getStringWidth(te.getName()) / 2, 6, 4210752);
        fontRenderer.drawString(Integer.toString(te.getStorage().getEnergyStored()), 8, 6, 4210752);
        List<String> list = new ArrayList<String>();
        list.add("A block that used RF");
        list.add("to smealt items");
        list.add("");
        //this.drawHoveringText(list, mouseX, mouseY);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }
}
