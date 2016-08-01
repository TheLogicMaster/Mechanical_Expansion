package com.logicmaster63.mechanical_expansion;

import com.logicmaster63.mechanical_expansion.init.Blocks;
import com.logicmaster63.mechanical_expansion.init.Items;
import com.logicmaster63.mechanical_expansion.init.TileEntities;
import com.logicmaster63.mechanical_expansion.proxy.ClientProxy;
import com.logicmaster63.mechanical_expansion.proxy.CommonProxy;
import com.logicmaster63.mechanical_expansion.tileEntity.CombustionGeneratorTileEntity;
import com.logicmaster63.mechanical_expansion.tileEntity.ElectricFurnaceTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.MODNAME)
public class MechanicalExpansion {

    public final String[] MACHINE_LIST = new String[] {"combustion_generator" , "electric_furnace"};
    ChatComponentText chatComponentText;

    @Mod.Instance("me")
    public static MechanicalExpansion instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.CLIENT_SERVER_CLASS)
    public static CommonProxy proxy;
    public static final MechanicalExpansionTab mechanicalExpansionTab = new MechanicalExpansionTab("mechanicalExpansionTab");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Blocks.init();
        Blocks.register();
        Items.init();
        Items.register();
        TileEntities.register();
        chatComponentText = new ChatComponentText(Reference.MODNAME + " version:" + Reference.VERSION);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.RegisterRenders();
        GameRegistry.registerFuelHandler(new MechanicalExpansionFuelHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new MechanicalExpansionGUIHandler());
        GameRegistry.registerTileEntity(ElectricFurnaceTileEntity.class, "ElectricFurnaceTileEntity");
        GameRegistry.registerTileEntity(CombustionGeneratorTileEntity.class, "CombustionGeneratorTileEntity");
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        //MinecraftServer.getServer().getConfigurationManager().sendChatMsg(chatComponentText);
    }
}
