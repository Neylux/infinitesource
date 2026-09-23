package de.neylux.infinitesource;

import de.neylux.infinitesource.setup.ModSetup;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(InfiniteSource.MOD_ID)
public class InfiniteSource {
    public static final String MOD_ID = "infinitesource";

    public InfiniteSource(IEventBus modEventBus, ModContainer modContainer) {
        ModSetup.setup(modEventBus);
    }
}
