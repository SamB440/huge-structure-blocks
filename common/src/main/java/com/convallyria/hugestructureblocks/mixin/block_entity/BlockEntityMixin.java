package com.convallyria.hugestructureblocks.mixin.block_entity;

import com.convallyria.hugestructureblocks.cache.WorldStructureBlockCacheAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
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
    protected Level level;

    @Shadow
    @Final
    protected BlockPos worldPosition;

    @Shadow
    protected boolean remove;

    @Inject(method = "setRemoved", at = @At("HEAD"))
    private void onSetRemoved(CallbackInfo ci) {
        if ((Object) this instanceof StructureBlockEntity) {
            if (level.isClientSide()) return;
            WorldStructureBlockCacheAccessor cache = (WorldStructureBlockCacheAccessor) this.level;
            cache.huge_structure_blocks$getStructureBlockCache().remove(this.worldPosition);
        }
    }

    @Inject(method = "clearRemoved", at = @At("HEAD"))
    private void onCancelRemoval(CallbackInfo ci) {
        if ((Object) this instanceof StructureBlockEntity) {
            if (!this.remove || level.isClientSide()) return;
            WorldStructureBlockCacheAccessor cache = (WorldStructureBlockCacheAccessor) this.level;
            cache.huge_structure_blocks$getStructureBlockCache().add(this.worldPosition);
        }
    }

    @Inject(method = "setLevel", at = @At("TAIL"))
    private void onSetWorld(Level level, CallbackInfo ci) {
        if ((Object) this instanceof StructureBlockEntity) {
            if (level.isClientSide()) return;
            WorldStructureBlockCacheAccessor cache = (WorldStructureBlockCacheAccessor) level;
            cache.huge_structure_blocks$getStructureBlockCache().add(this.worldPosition);
        }
    }
}
