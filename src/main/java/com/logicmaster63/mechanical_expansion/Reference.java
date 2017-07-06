package com.logicmaster63.mechanical_expansion;

import java.util.ArrayList;

public class Reference {

    public static final String MODID = "me";
    public static final String MODNAME = "Mechanical Expansion";
    public static final String VERSION = "1.0";
    public static final String CLIENT_PROXY_CLASS = "com.logicmaster63.mechanical_expansion.proxy.ClientProxy";
    public static final String CLIENT_SERVER_CLASS = "com.logicmaster63.mechanical_expansion.proxy.CommonProxy";
    public static final String CONFIG_DIRECTORY = "/assets/mechanicalexpansion/config/";
    public static final ArrayList<String> IMPLEMENTABLE_MODIDS = new ArrayList<String>() {{
        add("EnderIO");
        add("ComputerCraft");
        add("JEI");
    }};
}
