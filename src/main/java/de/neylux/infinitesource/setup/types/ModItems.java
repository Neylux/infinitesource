package de.neylux.infinitesource.setup.types;

import de.neylux.infinitesource.setup.ModSetup;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModItems {
    public static void setup() {}

    static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item) {
        return ModSetup.ITEMS.registerItem(name, item);
    }

    static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item, UnaryOperator<Item.Properties> properties) {
        return ModSetup.ITEMS.registerItem(name, item, properties);
    }
}
