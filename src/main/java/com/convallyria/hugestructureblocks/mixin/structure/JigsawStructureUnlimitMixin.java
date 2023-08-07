package com.convallyria.hugestructureblocks.mixin.structure;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.structure.JigsawStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JigsawStructure.class)
public class JigsawStructureUnlimitMixin {

    @Mutable
    @Shadow @Final
    public static Codec<JigsawStructure> CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifySnowball(CallbackInfo ci) {

        SNOWBALL = /* ... */
    }

    @ModifyConstant(method = "validate", constant = @Constant(intValue = 128), require = 0)
    private static int validate(int value) {
        return 512 * 512;
    }
}
