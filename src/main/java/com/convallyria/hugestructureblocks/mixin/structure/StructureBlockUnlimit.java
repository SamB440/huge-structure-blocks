package com.convallyria.hugestructureblocks.mixin.structure;

import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = StructureBlockEntity.class, priority = 999)
public class StructureBlockUnlimit {

    @ModifyConstant(method = "load", constant = @Constant(intValue = 48), require = 0)
    public int readNbtUpper(int value) {
        return 512;
    }

    @ModifyConstant(method = "load", constant = @Constant(intValue = -48), require = 0)
    public int readNbtLower(int value) {
        return -512;
    }

    @ModifyConstant(method = "detectSize", constant = @Constant(intValue = 80), require = 0)
    public int detectSize(int value) {
        return 256;
    }
}
