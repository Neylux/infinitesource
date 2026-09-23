package de.neylux.infinitesource.setup.blocks;

import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @see <a href="https://docs.neoforged.net/docs/inventories/transactions/#resource-handlers">NeoForge ResourceHandler</a>
 */
public class InfiniteFluidHandler implements ResourceHandler<FluidResource> {

    private final Supplier<Fluid> fluidSupplier;

    public InfiniteFluidHandler(Supplier<Fluid> fluidSupplier) {
        this.fluidSupplier = fluidSupplier;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public FluidResource getResource(int index) {
        Objects.checkIndex(index, this.size());
        return FluidResource.of(fluidSupplier.get());
    }

    @Override
    public long getAmountAsLong(int index) {
        Objects.checkIndex(index, this.size());
        return Integer.MAX_VALUE;
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        Objects.checkIndex(index, this.size());
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        // Validate arguments.
        Objects.checkIndex(index, this.size());
        TransferPreconditions.checkNonEmpty(resource);

        return resource.getFluid() == fluidSupplier.get();
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        // Validate arguments.
        Objects.checkIndex(index, size());
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);

        return amount;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        // Validate arguments.
        Objects.checkIndex(index, size());
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);

        if (amount <= 0) return 0;

        if (resource.getFluid() == fluidSupplier.get()) {
            return amount;
        }
        return 0;
    }
}
