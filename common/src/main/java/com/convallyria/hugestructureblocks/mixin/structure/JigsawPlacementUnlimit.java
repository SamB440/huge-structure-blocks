package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = StructurePoolBasedGenerator.class, priority = 999)
public class JigsawPlacementUnlimit {

    @ModifyConstant(method = "generate(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/util/Identifier;ILnet/minecraft/util/math/BlockPos;Z)Z", constant = @Constant(intValue = 128), require = 0)
    private static int changeMaxGenDistance(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
    }
}