package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import com.convallyria.hugestructureblocks.cache.WorldStructureBlockCacheAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Comparator;
import java.util.stream.Stream;

@Mixin(value = StructureBlockEntity.class, priority = 999)
public class StructureBlockUnlimit {

    @ModifyConstant(method = "loadAdditional", constant = @Constant(intValue = 48), require = 0)
    public int readNbtUpper(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    @ModifyConstant(method = "loadAdditional", constant = @Constant(intValue = -48), require = 0)
    public int readNbtLower(int value) {
        return -HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    /**
     * @author SamB440
     * @reason Optimise searching of structure blocks by searching using a cache.
     */
    @Overwrite
    private Stream<BlockPos> getRelatedCorners(BlockPos min, BlockPos max) {
        StructureBlockEntity origin = (StructureBlockEntity) (Object) this;
        String template = origin.getStructureName();
        if (template == null) return Stream.empty();

        BlockPos middle = origin.getBlockPos();
        Level level = origin.getLevel();
        if (level == null) return Stream.empty();

        WorldStructureBlockCacheAccessor cache = (WorldStructureBlockCacheAccessor) level;
        return cache.huge_structure_blocks$getStructureBlockCache().getAll()
                .filter(pos -> !pos.equals(middle))
                .filter(pos -> pos.closerThan(middle, detectSize(-1) + 1)) // within range
                .map(level::getBlockEntity)
                .filter(be -> be instanceof StructureBlockEntity block
                        && block.getMode() == StructureMode.CORNER
                        && template.equals(block.getStructureName()))
                .map(BlockEntity::getBlockPos)
                .sorted(Comparator.comparingDouble(pos -> pos.distSqr(middle)))
                .limit(2);
    }

    @ModifyConstant(method = "detectSize", constant = @Constant(intValue = 80), require = 0)
    public int detectSize(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
}
