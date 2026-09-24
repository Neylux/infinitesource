package de.neylux.infinitesource.setup.types;

import de.neylux.infinitesource.InfiniteSource;
import de.neylux.infinitesource.setup.ModSetup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModTabs {
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_INFINITE_SOURCE = ModSetup.CREATIVE_TABS.register(
            InfiniteSource.MOD_ID, () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + InfiniteSource.MOD_ID))
                    .icon(() -> ModBlocks.INFINITE_WATER_BLOCK.get().asItem().getDefaultInstance())
                    .displayItems(((_, output) -> ModSetup.ITEMS.getEntries().forEach(e -> output.accept(e.get()))))
                    .build());

    public static void setup() {
    }
}
