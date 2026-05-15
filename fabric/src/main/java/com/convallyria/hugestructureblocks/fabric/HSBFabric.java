package com.convallyria.hugestructureblocks.fabric;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class HSBFabric implements ModInitializer {

    public static final ResourceKey<CreativeModeTab> STRUCTURE_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(),
            Identifier.fromNamespaceAndPath(HugeStructureBlocksMod.MOD_ID, "structure_items"));
    public static final CreativeModeTab STRUCTURE_ITEM_GROUP = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.STRUCTURE_BLOCK))
            .title(Component.translatable("itemGroup.hugestructureblocks.structure_items"))
            .build();

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        HugeStructureBlocksMod.init();

        // Register the group.
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, STRUCTURE_ITEM_GROUP_KEY, STRUCTURE_ITEM_GROUP);

        // Register items to the custom item group.
        CreativeModeTabEvents.modifyOutputEvent(STRUCTURE_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.accept(Items.STRUCTURE_BLOCK);
            itemGroup.accept(Items.JIGSAW);
            itemGroup.accept(Items.STRUCTURE_VOID);
        });
    }
}
