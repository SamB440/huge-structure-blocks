package com.convallyria.hugestructureblocks.fabric;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class HSBFabric implements ModInitializer {

    public static final RegistryKey<ItemGroup> STRUCTURE_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(HugeStructureBlocksMod.MOD_ID, "structure_items"));
    public static final ItemGroup STRUCTURE_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.STRUCTURE_BLOCK))
            .displayName(Text.translatable("itemGroup.hugestructureblocks.structure_items"))
            .build();

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        HugeStructureBlocksMod.init();

        // Register the group.
        Registry.register(Registries.ITEM_GROUP, STRUCTURE_ITEM_GROUP_KEY, STRUCTURE_ITEM_GROUP);

        // Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(STRUCTURE_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(Items.STRUCTURE_BLOCK);
            itemGroup.add(Items.JIGSAW);
            itemGroup.add(Items.STRUCTURE_VOID);
        });
    }
}
