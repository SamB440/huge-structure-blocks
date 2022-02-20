package com.convallyria.hugestructureblocks.mixin.structure.packet;

import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ServerboundSetStructureBlockPacket.class, priority = 999)
public class ClientUpdateStructureBlockUnlimit {

    @ModifyConstant(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", constant = @Constant(intValue = 48), require = 0)
    public int reinitUpper(int value) {
        return 512;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", constant = @Constant(intValue = -48), require = 0)
    public int reinitLower(int value) {
        return -512;
    }
}
