package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.alias.StructurePoolAliasBinding;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.structure.DimensionPadding;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.List;

@Mixin(value = JigsawStructure.class, priority = 999)
@Debug(export = true)
public abstract class JigsawStructureUnlimit extends Structure {
    @WrapOperation(method = {"method_41662"}, at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;intRange(II)Lcom/mojang/serialization/Codec;"))
    private static Codec<Integer> x(int minInclusive, int maxInclusive, Operation<Codec<Integer>> original) {
        return Codec.INT;
    }

    protected JigsawStructureUnlimit(Config config) {
        super(config);
    }

//    @ModifyConstant(method = "<init>(Lnet/minecraft/world/gen/structure/Structure$Config;Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/world/gen/heightprovider/HeightProvider;Z)V", constant = @Constant(intValue = 80), require = 0)
    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/world/gen/structure/Structure$Config;Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/world/gen/heightprovider/HeightProvider;Z)V", at = @At(value = "CONSTANT", args = "intValue=80"), require = 0)
    private static int init1(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

//    @ModifyConstant(method = "<init>(Lnet/minecraft/world/gen/structure/Structure$Config;Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/world/gen/heightprovider/HeightProvider;ZLnet/minecraft/world/Heightmap$Type;)V", constant = @Constant(intValue = 80), require = 0)
    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/world/gen/structure/Structure$Config;Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/world/gen/heightprovider/HeightProvider;ZLnet/minecraft/world/Heightmap$Type;)V", at = @At(value = "CONSTANT", args = "intValue=80"), require = 0)
    private static int init2(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
//    @ModifyConstant(method = "validate", constant = @Constant(intValue = 128), require = 0)
    @ModifyExpressionValue(method = "validate", at = @At(value = "CONSTANT", args = "intValue=128"), require = 0)
    private static int maxDistanceFromCenter(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
}