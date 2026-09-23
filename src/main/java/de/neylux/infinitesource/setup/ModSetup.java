package de.neylux.infinitesource.setup;

import de.neylux.infinitesource.InfiniteSource;
import de.neylux.infinitesource.setup.blocks.InfiniteWaterBlockEntity;
import de.neylux.infinitesource.setup.types.ModBlockEntities;
import de.neylux.infinitesource.setup.types.ModBlocks;
import de.neylux.infinitesource.setup.types.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSetup {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(InfiniteSource.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(InfiniteSource.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, InfiniteSource.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, InfiniteSource.MOD_ID);

    public static void setup(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);

        ModBlocks.setup();
        ModItems.setup();
        ModBlockEntities.setup();

        modEventBus.addListener(ModSetup::registerCapabilities);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlock(Capabilities.Fluid.BLOCK, ((level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof InfiniteWaterBlockEntity) {
                return ((InfiniteWaterBlockEntity) blockEntity).getFluidHandler();
            }

            return null;
        }), ModBlocks.INFINITE_WATER_BLOCK.get());
    }
}
