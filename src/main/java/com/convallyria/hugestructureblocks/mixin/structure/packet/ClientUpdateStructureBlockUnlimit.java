package com.convallyria.hugestructureblocks.mixin.structure.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.UpdateStructureBlockC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = UpdateStructureBlockC2SPacket.class, priority = 999)
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

    @Inject(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At("RETURN"), require = 0)
    public void readInts(PacketByteBuf buf, CallbackInfo ci) {
        this.offset = new BlockPos(
                MathHelper.clamp(buf.readInt(), -512, 512),
                MathHelper.clamp(buf.readInt(), -512, 512),
                MathHelper.clamp(buf.readInt(), -512, 512)
        );

        this.size = new BlockPos(
                MathHelper.clamp(buf.readInt(), 0, 512),
                MathHelper.clamp(buf.readInt(), 0, 512),
                MathHelper.clamp(buf.readInt(), 0, 512)
        );
    }

    @Inject(method = "write", at = @At("RETURN"), require = 0)
    public void writeInts(PacketByteBuf buf, CallbackInfo ci) {
        buf.writeInt(offset.getX());
        buf.writeInt(offset.getY());
        buf.writeInt(offset.getZ());
        buf.writeInt(size.getX());
        buf.writeInt(size.getY());
        buf.writeInt(size.getZ());
    }
}
