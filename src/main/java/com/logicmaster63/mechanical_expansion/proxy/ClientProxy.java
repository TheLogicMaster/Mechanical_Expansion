package com.logicmaster63.mechanical_expansion.proxy;

import com.logicmaster63.mechanical_expansion.init.Blocks;
import com.logicmaster63.mechanical_expansion.init.Items;

public class ClientProxy extends CommonProxy{

    @Override
    public void RegisterRenders() {
        Items.registerRenders();
        Blocks.registerRenders();
    }

}
