package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Mixin(value = StructureBlockBlockEntity.class, priority = 999)
public class StructureBlockUnlimit {

    @ModifyConstant(method = "readNbt", constant = @Constant(intValue = 48), require = 0)
    public int readNbtUpper(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    @ModifyConstant(method = "readNbt", constant = @Constant(intValue = -48), require = 0)
    public int readNbtLower(int value) {
        return -HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    /**
     * @author SamB440
     * @reason Optimise searching of structure blocks by searching from the structure block outwards instead of from min corner to max (and reduce streams).
     */
    @Overwrite
    private Stream<BlockPos> streamCornerPos(BlockPos min, BlockPos max) {
        StructureBlockBlockEntity blockEntity = (StructureBlockBlockEntity) (Object) this;
        final World level = blockEntity.getWorld();
        if (level == null) return Stream.empty();
        final BlockPos middle = blockEntity.getPos();
        List<BlockPos> blocks = new ArrayList<>(2);
        final int maxSearch = detectSize(-1) + 1;
        BlockPos.findClosest(middle, maxSearch, Math.min(maxSearch, level.getHeight() + 1), pos -> {
            if (level.getBlockEntity(pos) instanceof StructureBlockBlockEntity block) {
                if (block.getMode() == StructureBlockMode.CORNER && Objects.equals(blockEntity.getTemplateName(), block.getTemplateName())) {
                    blocks.add(block.getPos());
                }
            }
            return blocks.size() == 2;
        });
        return blocks.stream();
    }

    @ModifyConstant(method = "detectStructureSize", constant = @Constant(intValue = 80), require = 0)
    public int detectSize(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
}
