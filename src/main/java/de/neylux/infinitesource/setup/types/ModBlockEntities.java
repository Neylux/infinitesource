package de.neylux.infinitesource.setup.types;

import de.neylux.infinitesource.setup.ModSetup;
import de.neylux.infinitesource.setup.blocks.InfiniteWaterBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;

public class ModBlockEntities {
    public static void setup() {}

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InfiniteWaterBlockEntity>> INFINITE_WATER_BLOCK_ENTITY = register(
            "infinite_water_source_entity",
            InfiniteWaterBlockEntity::new,
            ModBlocks.INFINITE_WATER_BLOCK
    );

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(
            String name,
            BlockEntityType.BlockEntitySupplier<T> factory,
            DeferredBlock<?>... blocks
    ) {
        return ModSetup.BLOCK_ENTITIES.register(name, () -> {
            Block[] validBlocks = Arrays.stream(blocks).map(DeferredBlock::get).toArray(Block[]::new);
            return new BlockEntityType<>(factory, validBlocks);
        });
    }
}
