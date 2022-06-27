package com.convallyria.hugestructureblocks.mixin.structure;

import net.minecraft.tileentity.StructureBlockTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = StructureBlockTileEntity.class, priority = 999)
public class StructureBlockRenderMixin {

    /**
     * @reason Increase the distance that the bounding box can be seen up to 256 blocks
     * @author SamB440/Cotander
     */
    @ModifyConstant(method = "getViewDistance", constant = @Constant(intValue = 96), require = 0)
    public int getRenderDistance(int value) {
        return 256;
    }
}