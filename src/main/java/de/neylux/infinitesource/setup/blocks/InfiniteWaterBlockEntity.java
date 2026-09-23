package de.neylux.infinitesource.setup.blocks;

import de.neylux.infinitesource.setup.types.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class InfiniteWaterBlockEntity extends BlockEntity {
    private final InfiniteFluidHandler handler = new InfiniteFluidHandler(() -> Fluids.WATER);

    public InfiniteWaterBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.INFINITE_WATER_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InfiniteWaterBlockEntity blockEntity) {
        if (level.isClientSide()) return;

        // Loop through all 6 structural faces of the block
        for (Direction direction : Direction.values()) {
            BlockPos targetPos = pos.relative(direction);

            // Fetch the fluid handler capability belonging to the adjacent pipe/block
            ResourceHandler<FluidResource> pipeHandler = level.getCapability(
                    Capabilities.Fluid.BLOCK,
                    targetPos,
                    direction.getOpposite() // Target the pipe's internal face
            );

            // If a valid pipe or tank capability exists next to this face
            if (pipeHandler != null) {
                // Open an official root transaction block using try-with-resources
                try (Transaction tx = Transaction.openRoot()) {

                    // Safely move the exact volume the pipe demands per tick
                    int moved = ResourceHandlerUtil.moveStacking(
                            blockEntity.getFluidHandler(), // Source: our infinite block handler
                            pipeHandler,                   // Destination: the adjacent pipe network
                            resource -> resource.getFluid() == Fluids.WATER, // Fluid filter criteria
                            Integer.MAX_VALUE,            // Max limit (moveStacking restricts this to open space automatically)
                            tx                            // Pass the active transaction reference
                    );

                    if (moved > 0) {
                        tx.commit();
                    }
                }
            }
        }
    }

    public ResourceHandler<FluidResource> getFluidHandler() {
        return this.handler;
    }
}
