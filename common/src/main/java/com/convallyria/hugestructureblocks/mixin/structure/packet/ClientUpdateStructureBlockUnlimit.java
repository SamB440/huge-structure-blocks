package com.convallyria.hugestructureblocks.mixin.structure.packet;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerboundSetStructureBlockPacket.class, priority = 999)
public class ClientUpdateStructureBlockUnlimit {

    // I don't like how we have to add int parameters on instead of having a way to change from byte -> int
    // If anyone knows a better way to do this please make a PR

    @Shadow
    @Final
    @Mutable
    private BlockPos offset;

    @Shadow
    @Final
    @Mutable
    private Vec3i size;

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"), require = 0)
    public void readInts(FriendlyByteBuf input, CallbackInfo ci) {
        final int newStructureSize = HugeStructureBlocksMod.NEW_STRUCTURE_SIZE;
        this.offset = new BlockPos(
                Mth.clamp(input.readInt(), -newStructureSize, newStructureSize),
                Mth.clamp(input.readInt(), -newStructureSize, newStructureSize),
                Mth.clamp(input.readInt(), -newStructureSize, newStructureSize)
        );

        this.size = new BlockPos(
                Mth.clamp(input.readInt(), 0, newStructureSize),
                Mth.clamp(input.readInt(), 0, newStructureSize),
                Mth.clamp(input.readInt(), 0, newStructureSize)
        );
    }

    @Inject(method = "write", at = @At("RETURN"), require = 0)
    public void writeInts(FriendlyByteBuf output, CallbackInfo ci) {
        output.writeInt(offset.getX());
        output.writeInt(offset.getY());
        output.writeInt(offset.getZ());
        output.writeInt(size.getX());
        output.writeInt(size.getY());
        output.writeInt(size.getZ());
    }
}
