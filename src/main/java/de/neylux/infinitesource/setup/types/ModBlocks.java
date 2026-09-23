package de.neylux.infinitesource.setup.types;

import de.neylux.infinitesource.setup.ModSetup;
import de.neylux.infinitesource.setup.blocks.InfiniteWaterSourceBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModBlocks {
    public static void setup() {}

    public static final DeferredBlock<InfiniteWaterSourceBlock> INFINITE_WATER_BLOCK = register(
            "infinite_water_source_block",
            InfiniteWaterSourceBlock::new
    );

    private static <T extends Block> DeferredBlock<T> registerNoItem(
            String name,
            Function<BlockBehaviour.Properties, T> block,
            UnaryOperator<BlockBehaviour.Properties> properties
    ) {
        return ModSetup.BLOCKS.registerBlock(name, block, properties);
    }

    private static <T extends Block> DeferredBlock<T> register(
            String name,
            Function<BlockBehaviour.Properties, T> block
    ) {
        return register(name, block, UnaryOperator.identity(), ModBlocks::defaultItem, Item.Properties::useBlockDescriptionPrefix);
    }

    private static <T extends Block> DeferredBlock<T> register(
            String name,
            Function<BlockBehaviour.Properties, T> block,
            UnaryOperator<BlockBehaviour.Properties> properties,
            Function<DeferredBlock<T>, Function<Item.Properties, ? extends BlockItem>> item,
            UnaryOperator<Item.Properties> itemProperties
    ) {
        DeferredBlock<T> ret = registerNoItem(name, block, properties);
        ModItems.register(name, item.apply(ret), itemProperties);
        return ret;
    }

    private static <T extends Block> Function<Item.Properties, BlockItem> defaultItem(DeferredBlock<T> block) {
        return p -> new BlockItem(block.get(), p);
    }
}
