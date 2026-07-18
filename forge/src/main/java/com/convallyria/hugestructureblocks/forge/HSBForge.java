package com.convallyria.hugestructureblocks.forge;

import com.convallyria.hugestructureblocks.HugeStructureBlocksMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(HugeStructureBlocksMod.MOD_ID)
public final class HSBForge {

    public static final DeferredRegister<ItemGroup> ITEM_GROUPS = DeferredRegister.create(
            RegistryKeys.ITEM_GROUP,
            HugeStructureBlocksMod.MOD_ID
    );

    public static final Supplier<ItemGroup> STRUCTURE_ITEM_GROUP = ITEM_GROUPS.register("structure_items", () -> ItemGroup.create(ItemGroup.Row.TOP, 0)
            //Set the title of the tab. Don't forget to add a translation!
            .displayName(Text.translatable("itemGroup.hugestructureblocks.structure_items"))
            //Set the icon of the tab.
            .icon(() -> new ItemStack(Items.STRUCTURE_BLOCK))
            //Add your items to the tab.
            .entries((params, output) -> {
                output.add(Items.STRUCTURE_BLOCK);
                output.add(Items.JIGSAW);
                output.add(Items.STRUCTURE_VOID);
            })
            .build()
    );

    public HSBForge() {
        // Run our common setup.
        HugeStructureBlocksMod.init();

        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEM_GROUPS.register(modBus);
    }
}
