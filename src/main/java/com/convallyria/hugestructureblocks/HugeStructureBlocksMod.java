package com.convallyria.hugestructureblocks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("hugestructureblocks")
public class HugeStructureBlocksMod {

    public static final String MODID = "hugestructureblocks";
    public static final String NAME = "Huge Structure Blocks";
    public static final String VERSION = "1.0.0";

    public static final int NEW_STRUCTURE_SIZE = 512;

    public static final Logger LOGGER = LogManager.getLogger();

    public HugeStructureBlocksMod(IEventBus bus) {
        // Register the setup method for modloading
        bus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        bus.register(this);
    }

    @SubscribeEvent
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Huge Structure Blocks is now making your structure blocks even bigger!");
        LOGGER.info("New structure size = " + NEW_STRUCTURE_SIZE);
    }
}
