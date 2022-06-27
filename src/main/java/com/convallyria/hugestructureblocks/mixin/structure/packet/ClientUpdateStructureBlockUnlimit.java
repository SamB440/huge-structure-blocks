package com.convallyria.hugestructureblocks.mixin.structure.packet;

import net.minecraft.network.play.client.CUpdateStructureBlockPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = CUpdateStructureBlockPacket.class, priority = 999)
public class ClientUpdateStructureBlockUnlimit {

    @ModifyConstant(method = "read", constant = @Constant(intValue = 48), require = 0)
    public int reinitUpper(int value) {
        return 512;
    }

    @ModifyConstant(method = "read", constant = @Constant(intValue = -48), require = 0)
    public int reinitLower(int value) {
        return -512;
    }
}
