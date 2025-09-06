package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import com.convallyria.hugestructureblocks.cache.WorldStructureBlockCacheAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Comparator;
import java.util.stream.Stream;

@Mixin(value = StructureBlockBlockEntity.class, priority = 999)
public class StructureBlockUnlimit {

    @ModifyConstant(method = "readData", constant = @Constant(intValue = 48), require = 0)
    public int readNbtUpper(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    @ModifyConstant(method = "readData", constant = @Constant(intValue = -48), require = 0)
    public int readNbtLower(int value) {
        return -HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    /**
     * @author SamB440
     * @reason Optimise searching of structure blocks by searching using a cache.
     */
    @Overwrite
    private Stream<BlockPos> streamCornerPos(BlockPos min, BlockPos max) {
        StructureBlockBlockEntity origin = (StructureBlockBlockEntity) (Object)this;
        String template = origin.getTemplateName();
        if (template == null) return Stream.empty();

        BlockPos middle = origin.getPos();
        World level = origin.getWorld();
        if (level == null) return Stream.empty();

        WorldStructureBlockCacheAccessor cache = (WorldStructureBlockCacheAccessor) level;
        return cache.huge_structure_blocks$getStructureBlockCache().getAll()
                .filter(pos -> !pos.equals(middle))
                .filter(pos -> pos.isWithinDistance(middle, detectSize(-1) + 1)) // within range
                .map(level::getBlockEntity)
                .filter(be -> be instanceof StructureBlockBlockEntity block
                        && block.getMode() == StructureBlockMode.CORNER
                        && template.equals(block.getTemplateName()))
                .map(BlockEntity::getPos)
                .sorted(Comparator.comparingDouble(pos -> pos.getSquaredDistance(middle)))
                .limit(2);
    }

    @ModifyConstant(method = "detectStructureSize", constant = @Constant(intValue = 80), require = 0)
    public int detectSize(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
}
