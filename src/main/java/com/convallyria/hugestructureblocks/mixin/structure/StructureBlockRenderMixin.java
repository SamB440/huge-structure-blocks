package com.convallyria.hugestructureblocks.mixin.structure;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import net.minecraft.client.renderer.blockentity.StructureBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = StructureBlockRenderer.class, priority = 999)
public class StructureBlockRenderMixin {

    /**
     * @reason Increase the distance that the bounding box can be seen up to 256 blocks
     * @author SamB440/Cotander
     */
    @ModifyConstant(method = "getViewDistance", constant = @Constant(intValue = 96), require = 0, remap = false)
    public int getRenderDistance(int value) {
        return HugeStructureBlocksMod.NEW_STRUCTURE_SIZE / 2;
    }
}