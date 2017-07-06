package com.logicmaster63.mechanical_expansion;

import net.minecraft.util.EnumFacing;

import java.util.HashMap;
import java.util.Map;

public class MachineSide {

    public static final Map<EnumFacing, MachineSide> PRODUCER;
    public static final Map<EnumFacing, MachineSide> CONSUMER;

    static {
        CONSUMER = new HashMap<>();
        CONSUMER.put(EnumFacing.UP, new MachineSide(true, false, false, false, true, false));
        CONSUMER.put(EnumFacing.DOWN, new MachineSide(true, true, false, false, true, false));
        for(EnumFacing facing: EnumFacing.HORIZONTALS)
            CONSUMER.put(facing, new MachineSide(true, true, false, false, true, false));

        PRODUCER = new HashMap<>();
        for(EnumFacing facing: EnumFacing.VALUES)
            PRODUCER.put(facing, new MachineSide(true, false, false, false, false, true));
        PRODUCER.put(EnumFacing.UP, new MachineSide(true, false, false, false, false, true));
        PRODUCER.put(EnumFacing.DOWN, new MachineSide(true, false, false, false, false, true));
    }

    public boolean insertItem, extractItem, insertLiquid, extractLiquid, insertRF, extractRF;

    public MachineSide(boolean insertItem, boolean extractItem, boolean insertLiquid, boolean extractLiquid, boolean insertRF, boolean extractRF) {
        this.insertItem = insertItem;
        this.extractItem = extractItem;
        this.insertLiquid = insertLiquid;
        this.extractLiquid = extractLiquid;
        this.insertRF = insertRF;
        this.extractRF = extractRF;
    }

    public MachineSide(boolean insertItem, boolean extractItem, boolean insertLiquid, boolean removeLiquid) {
        this.insertItem = insertItem;
        this.extractItem = extractItem;
        this.insertLiquid = insertLiquid;
        this.extractLiquid = removeLiquid;
    }

    public MachineSide(boolean insertItem, boolean extractItem) {
        this.insertItem = insertItem;
        this.extractItem = extractItem;
    }

    public MachineSide() {

    }

    public static Map<EnumFacing, MachineSide> cpy(Map<EnumFacing, MachineSide> sides) {
        Map<EnumFacing, MachineSide> newSides = new HashMap<>();
        newSides.putAll(sides);
        return newSides;
    }
}
