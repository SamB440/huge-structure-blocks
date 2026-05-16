package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.structure.JigsawStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = JigsawStructure.MaxDistanceFromCenter.class, priority = 999)
public abstract class JigsawStructureMaxDistanceUnlimit {

    // Field init for HORIZONTAL_VALUE_CODEC = Codec.intRange(1, 128) lives in <clinit>;
    // raise the upper bound so MaxDistance.horizontal can scale to NEW_STRUCTURE_SIZE.
    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;intRange(II)Lcom/mojang/serialization/Codec;"))
    private static Codec<Integer> hsb$wrapHorizontalRange(int minInclusive, int maxInclusive, Operation<Codec<Integer>> original) {
        return original.call(minInclusive, HugeStructureBlocksMod.NEW_STRUCTURE_SIZE * 4);
    }
}