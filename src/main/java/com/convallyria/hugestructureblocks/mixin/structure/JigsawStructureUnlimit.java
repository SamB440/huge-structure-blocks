package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static net.minecraft.world.gen.structure.Structure.configCodecBuilder;

@Mixin(value = JigsawStructure.class, priority = 999)
public class JigsawStructureUnlimit {

    @Shadow
    @Final
    @Mutable
    // Is this even used?
    public static final int MAX_SIZE = Integer.MAX_VALUE;

    // I tried so many ways to do this without replacing the codec but nothing worked.
    @Shadow
    @Final
    @Mutable
    public static final Codec<JigsawStructure> CODEC = Codecs.validate(
        RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    configCodecBuilder(instance),
                    StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.INT.fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
                    Heightmap.Type.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.INT.fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            )
            .apply(instance, JigsawStructure::new)
        ),
        JigsawStructure::validate
    )
    .codec();

    @ModifyConstant(method = "<init>(Lnet/minecraft/world/gen/structure/Structure$Config;Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/world/gen/heightprovider/HeightProvider;Z)V", constant = @Constant(intValue = 80), require = 0)
    private static int init1(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/world/gen/structure/Structure$Config;Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/world/gen/heightprovider/HeightProvider;ZLnet/minecraft/world/Heightmap$Type;)V", constant = @Constant(intValue = 80), require = 0)
    private static int init2(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    @ModifyConstant(method = "validate", constant = @Constant(intValue = 128), require = 0)
    private static int maxDistanceFromCenter(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
}