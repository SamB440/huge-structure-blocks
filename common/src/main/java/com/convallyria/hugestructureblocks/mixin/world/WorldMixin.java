package com.convallyria.hugestructureblocks.mixin.world;

import com.convallyria.hugestructureblocks.cache.StructureBlockCache;
import com.convallyria.hugestructureblocks.cache.WorldStructureBlockCacheAccessor;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(World.class)
public class WorldMixin implements WorldStructureBlockCacheAccessor {

    @Unique
    private final StructureBlockCache huge_structure_blocks$structureBlockCache = new StructureBlockCache();

    @Override
    public StructureBlockCache huge_structure_blocks$getStructureBlockCache() {
        return huge_structure_blocks$structureBlockCache;
    }
}
