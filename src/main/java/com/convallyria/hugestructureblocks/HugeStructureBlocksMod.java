package com.convallyria.hugestructureblocks;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HugeStructureBlocksMod implements ModInitializer {

	public static final String MODID = "hugestructureblocks";
	public static final String NAME = "Huge Structure Blocks";
	public static final String VERSION = "1.0.0";

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {
		LOGGER.info("Huge Structure Blocks is now making your structure blocks even bigger!");
	}
}
