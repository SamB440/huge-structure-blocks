package com.convallyria.hugestructureblocks.mixin.block_entity;

import com.convallyria.hugestructureblocks.cache.WorldStructureBlockCacheAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {

    @Shadow
    @Nullable
    protected World world;

    @Shadow
    @Final
    protected BlockPos pos;

    @Shadow
    protected boolean removed;

    @Inject(method = "markRemoved", at = @At("HEAD"))
    private void onSetRemoved(CallbackInfo ci) {
        if ((Object) this instanceof StructureBlockBlockEntity) {
            if (world.isClient()) return;
            WorldStructureBlockCacheAccessor cache = (WorldStructureBlockCacheAccessor) this.world;
            cache.huge_structure_blocks$getStructureBlockCache().remove(this.pos);
        }
    }

    @Inject(method = "cancelRemoval", at = @At("HEAD"))
    private void onCancelRemoval(CallbackInfo ci) {
        if ((Object) this instanceof StructureBlockBlockEntity) {
            if (!this.removed || world.isClient()) return;
            WorldStructureBlockCacheAccessor cache = (WorldStructureBlockCacheAccessor) this.world;
            cache.huge_structure_blocks$getStructureBlockCache().add(this.pos);
        }
    }

    @Inject(method = "setWorld", at = @At("TAIL"))
    private void onSetWorld(World world, CallbackInfo ci) {
        if ((Object) this instanceof StructureBlockBlockEntity) {
            if (world.isClient()) return;
            WorldStructureBlockCacheAccessor cache = (WorldStructureBlockCacheAccessor) world;
            cache.huge_structure_blocks$getStructureBlockCache().add(this.pos);
        }
    }
}
