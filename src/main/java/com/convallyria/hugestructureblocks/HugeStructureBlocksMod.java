package com.convallyria.hugestructureblocks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("hugestructureblocks")
public class HugeStructureBlocksMod {

    public static final String MODID = "hugestructureblocks";
    public static final String NAME = "Huge Structure Blocks";
    public static final String VERSION = "1.0.0";

    public static final Logger LOGGER = LogManager.getLogger();

    public HugeStructureBlocksMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Huge Structure Blocks is now making your structure blocks even bigger!");
    }
}
