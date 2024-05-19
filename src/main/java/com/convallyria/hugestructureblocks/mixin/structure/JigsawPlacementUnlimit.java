package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = JigsawPlacement.class, priority = 999)
public class JigsawPlacementUnlimit {

    @ModifyConstant(method = "generateJigsaw", constant = @Constant(intValue = 128), require = 0, remap = false)
    private static int changeMaxGenDistance(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
}
