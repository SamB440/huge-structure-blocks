package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.List;

@Mixin(value = JigsawStructure.class, priority = 999)
public abstract class JigsawStructureUnlimit extends Structure {
    @Accessor
    protected abstract List<StructurePoolAliasBinding> getPoolAliasBindings();
    @Accessor
    protected abstract DimensionPadding getDimensionPadding();
    @Accessor
    protected abstract StructureLiquidSettings getLiquidSettings();
    // I tried so many ways to do this without replacing the codec but nothing worked.
    @Shadow
    @Final
    @Mutable
    public static MapCodec<JigsawStructure> CODEC = RecordCodecBuilder.<JigsawStructure>mapCodec(
            (instance) -> instance.group(
                    configCodecBuilder(instance),
                    StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter((structure) -> structure.startPool),
                    Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((structure) -> structure.startJigsawName),
                    Codec.INT.fieldOf("size").forGetter((structure) -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter((structure) -> structure.startHeight),
                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter((structure) -> structure.useExpansionHack),
                    Heightmap.Type.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((structure) -> structure.projectStartToHeightmap),
                    Codec.INT.fieldOf("max_distance_from_center").forGetter((structure) -> structure.maxDistanceFromCenter),
                    Codec.list(StructurePoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter((structure) -> ((JigsawStructureUnlimit) (Object) structure).getPoolAliasBindings()),
                    DimensionPadding.CODEC.optionalFieldOf("dimension_padding", JigsawStructure.DEFAULT_DIMENSION_PADDING).forGetter((structure) -> ((JigsawStructureUnlimit) (Object) structure).getDimensionPadding()),
                    StructureLiquidSettings.codec.optionalFieldOf("liquid_settings", JigsawStructure.DEFAULT_LIQUID_SETTINGS).forGetter((jigsawStructure) -> ((JigsawStructureUnlimit) (Object) jigsawStructure).getLiquidSettings())
            ).apply(instance, JigsawStructure::new)).validate(JigsawStructure::validate);

    @Shadow @Final private DimensionPadding dimensionPadding;

    protected JigsawStructureUnlimit(Config config) {
        super(config);
    }

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