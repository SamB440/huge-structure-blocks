package com.convallyria.hugestructureblocks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class HugeStructureBlocksMod {

    public static final String MOD_ID = "hugestructureblocks";

    // New size for structures, TODO: Configurable?
    public static final int NEW_STRUCTURE_SIZE = 512;

    public static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        LOGGER.info("Huge Structure Blocks is now making your structure blocks even bigger!");
        LOGGER.info("New structure size = " + NEW_STRUCTURE_SIZE);
    }
}
