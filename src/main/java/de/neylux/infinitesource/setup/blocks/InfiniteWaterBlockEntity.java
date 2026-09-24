package de.neylux.infinitesource.setup.blocks;

import de.neylux.infinitesource.setup.types.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public class InfiniteWaterBlockEntity extends BlockEntity {
    private final InfiniteFluidHandler handler = new InfiniteFluidHandler(() -> Fluids.WATER);

    public InfiniteWaterBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.INFINITE_WATER_BLOCK_ENTITY.get(), worldPosition, blockState);
    }
    
    public ResourceHandler<FluidResource> getFluidHandler() {
        return this.handler;
    }
}
