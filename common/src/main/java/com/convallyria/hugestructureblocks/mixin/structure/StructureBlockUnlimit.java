package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import com.convallyria.hugestructureblocks.cache.WorldStructureBlockCacheAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private static Optional<BoundingBox> getEnclosingBoundingBox(Level level, BlockPos origin, @Nullable Identifier structureName) {
        if (structureName == null) return Optional.empty();

        final String template = structureName.toString();
        final int size = HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
        WorldStructureBlockCacheAccessor cache = (WorldStructureBlockCacheAccessor) level;
        List<BlockPos> corners = cache.huge_structure_blocks$getStructureBlockCache().getAll()
                .filter(pos -> Math.abs(pos.getX() - origin.getX()) <= size && Math.abs(pos.getZ() - origin.getZ()) <= size) // within range
                .filter(pos -> level.getBlockEntity(pos) instanceof StructureBlockEntity block
                        && block.getMode() == StructureMode.CORNER
                        && template.equals(block.getStructureName()))
                .collect(Collectors.toCollection(ArrayList::new));
        // Like vanilla, a single corner spans from itself to the origin
        if (corners.size() == 1) corners.add(origin);
        return BoundingBox.encapsulatingPositions(corners);
    }
}
