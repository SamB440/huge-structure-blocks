package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static net.minecraft.world.level.levelgen.structure.Structure.settingsCodec;

@Mixin(value = JigsawStructure.class, priority = 999)
public abstract class JigsawStructureUnlimit {

    @Shadow
    @Final
    @Mutable
    // Is this even used?
    public static final int MAX_TOTAL_STRUCTURE_RANGE = Integer.MAX_VALUE;

    // I tried so many ways to do this without replacing the codec but nothing worked.
    @Shadow
    @Final
    @Mutable
    public static final Codec<JigsawStructure> CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec(instance -> {
        return instance.group(settingsCodec(instance), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> {
            return structure.startPool;
        }), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> {
            return structure.startJigsawName;
        }), Codec.INT.fieldOf("size").forGetter(structure -> {
            return structure.maxDepth;
        }), HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> {
            return structure.startHeight;
        }), Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> {
            return structure.useExpansionHack;
        }), Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> {
            return structure.projectStartToHeightmap;
        }), Codec.INT.fieldOf("max_distance_from_center").forGetter(structure -> {
            return structure.maxDistanceFromCenter;
        })).apply(instance, JigsawStructure::new);
    }), JigsawStructure::verifyRange).codec();

    @ModifyConstant(method = "<init>(Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;Lnet/minecraft/core/Holder;ILnet/minecraft/world/level/levelgen/heightproviders/HeightProvider;Z)V", constant = @Constant(intValue = 80), require = 0)
    private static int init1(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;Lnet/minecraft/core/Holder;ILnet/minecraft/world/level/levelgen/heightproviders/HeightProvider;ZLnet/minecraft/world/level/levelgen/Heightmap$Types;)V", constant = @Constant(intValue = 80), require = 0)
    private static int init2(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    @ModifyConstant(method = "verifyRange", constant = @Constant(intValue = 128), require = 0)
    private static int maxDistanceFromCenter(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
}
