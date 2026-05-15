package com.convallyria.hugestructureblocks.neoforge;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(HugeStructureBlocksMod.MOD_ID)
public final class HSBForge {

    public static final DeferredRegister<CreativeModeTab> ITEM_GROUPS = DeferredRegister.create(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            HugeStructureBlocksMod.MOD_ID
    );

    public static final Supplier<CreativeModeTab> STRUCTURE_ITEM_GROUP = ITEM_GROUPS.register("structures", () -> CreativeModeTab.builder()
            //Set the title of the tab. Don't forget to add a translation!
            .title(Component.translatable("itemGroup.hugestructureblocks.structure_items"))
            //Set the icon of the tab.
            .icon(() -> new ItemStack(Items.STRUCTURE_BLOCK))
            //Add your items to the tab.
            .displayItems((_, output) -> {
                output.accept(Items.STRUCTURE_BLOCK);
                output.accept(Items.JIGSAW);
                output.accept(Items.STRUCTURE_VOID);
            })
            .build()
    );

    public HSBForge(IEventBus modBus) {
        // Run our common setup.
        HugeStructureBlocksMod.init();

        ITEM_GROUPS.register(modBus);
    }
}
