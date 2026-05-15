package com.convallyria.hugestructureblocks.mixin.world;

import com.convallyria.hugestructureblocks.cache.StructureBlockCache;
import com.convallyria.hugestructureblocks.cache.WorldStructureBlockCacheAccessor;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerLevel.class)
public class WorldMixin implements WorldStructureBlockCacheAccessor {

    @Unique
    private final StructureBlockCache huge_structure_blocks$structureBlockCache = new StructureBlockCache();

    @Override
    public StructureBlockCache huge_structure_blocks$getStructureBlockCache() {
        return huge_structure_blocks$structureBlockCache;
    }
}
