package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.List;

import static net.minecraft.world.level.levelgen.structure.Structure.settingsCodec;

@Mixin(value = JigsawStructure.class, priority = 999)
public abstract class JigsawStructureUnlimit {

    // I tried so many ways to do this without replacing the codec but nothing worked.
    @Shadow(remap = false)
    @Final
    @Mutable
    public static final MapCodec<JigsawStructure> CODEC = RecordCodecBuilder.<JigsawStructure>mapCodec(
                    instance -> instance.group(
                                    settingsCodec(instance),
                                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                                    Codec.INT.fieldOf("size").forGetter(structure -> structure.maxDepth),
                                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
                                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                                    Codec.INT.fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                                    Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(JigsawStructure::getPoolAliases)
                            )
                            .apply(instance, JigsawStructure::new)
            )
            .validate(JigsawStructure::verifyRange);

    @ModifyConstant(method = "<init>(Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;Lnet/minecraft/core/Holder;ILnet/minecraft/world/level/levelgen/heightproviders/HeightProvider;Z)V", constant = @Constant(intValue = 80), require = 0, remap = false)
    private static int init1(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;Lnet/minecraft/core/Holder;ILnet/minecraft/world/level/levelgen/heightproviders/HeightProvider;ZLnet/minecraft/world/level/levelgen/Heightmap$Types;)V", constant = @Constant(intValue = 80), require = 0, remap = false)
    private static int init2(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }

    @ModifyConstant(method = "verifyRange", constant = @Constant(intValue = 128), require = 0, remap = false)
    private static int maxDistanceFromCenter(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
}
