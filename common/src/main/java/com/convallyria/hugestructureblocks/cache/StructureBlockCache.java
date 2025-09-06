package com.convallyria.hugestructureblocks.cache;

import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A per-world cache of structure block positions.
 */
public final class StructureBlockCache {

    private final Set<BlockPos> structureBlocks = new HashSet<>();

    public void add(BlockPos pos) {
        structureBlocks.add(pos.toImmutable());
    }

    public void remove(BlockPos pos) {
        structureBlocks.remove(pos);
    }

    public Stream<BlockPos> getAll() {
        return structureBlocks.stream();
    }
}
