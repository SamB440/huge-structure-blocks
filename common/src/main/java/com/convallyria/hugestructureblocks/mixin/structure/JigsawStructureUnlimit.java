package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = JigsawStructure.class, priority = 999)
@Debug(export = true)
public abstract class JigsawStructureUnlimit {

    // Outer mapCodec lambda `(i) -> i.group(...)` in <clinit>. Replaces the
    // `Codec.intRange(0, 20)` for `size` (maxDepth) with an unbounded INT codec.
    @WrapOperation(method = "lambda$static$0", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;intRange(II)Lcom/mojang/serialization/Codec;"))
    private static Codec<Integer> hsb$wrapMaxDepthIntRange(int minInclusive, int maxInclusive, Operation<Codec<Integer>> original) {
        return Codec.INT;
    }

//    @ModifyConstant(method = "<init>(Lnet/minecraft/world/gen/structure/Structure$Config;Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/world/gen/heightprovider/HeightProvider;Z)V", constant = @Constant(intValue = 80), require = 0)
    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;Lnet/minecraft/core/Holder;ILnet/minecraft/world/level/levelgen/heightproviders/HeightProvider;Z)V", at = @At(value = "CONSTANT", args = "intValue=80"), require = 0)
    private static int init1(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE - 12;
    }

//    @ModifyConstant(method = "<init>(Lnet/minecraft/world/gen/structure/Structure$Config;Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/world/gen/heightprovider/HeightProvider;ZLnet/minecraft/world/Heightmap$Type;)V", constant = @Constant(intValue = 80), require = 0)
    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;Lnet/minecraft/core/Holder;ILnet/minecraft/world/level/levelgen/heightproviders/HeightProvider;ZLnet/minecraft/world/level/levelgen/Heightmap$Types;)V", at = @At(value = "CONSTANT", args = "intValue=80"), require = 0)
    private static int init2(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE - 12;
    }

//    @ModifyConstant(method = "validate", constant = @Constant(intValue = 128), require = 0)
    @ModifyExpressionValue(method = "verifyRange", at = @At(value = "CONSTANT", args = "intValue=128"), require = 0)
    private static int maxDistanceFromCenter(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE * 4;
    }
}