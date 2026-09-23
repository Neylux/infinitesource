package de.neylux.infinitesource.setup.data;

import de.neylux.infinitesource.InfiniteSource;
import de.neylux.infinitesource.setup.ModSetup;
import de.neylux.infinitesource.setup.types.ModBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = InfiniteSource.MOD_ID)
public final class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var lookupProvider = event.getReloadableLookupProvider();

        generator.addProvider(true, new GeneratorModels(packOutput));
        generator.addProvider(true, new GeneratorLanguage(packOutput));
        //generator.addProvider(true, new GeneratorLootTables(lookupProvider));
        /*
        generator.addProvider(true, new LootTableProvider(
                packOutput,
                Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(GeneratorLootTables::new, LootContextParamSets.BLOCK)
                ),
                lookupProvider
        ));

        // Register MultiRegistryBootstrap (Recipes + Advancements)
        generator.addProvider(true, new DatapackBuiltinEntriesProvider(
                packOutput,
                lookupProvider,
                new RegistrySetBuilder().add(GeneratorRecipes.create()),
                Set.of(InfiniteSource.MOD_ID)
        ));
         */
        generator.addProvider(true, new GeneratorBlockTags(packOutput, lookupProvider));
    }

    static class GeneratorModels extends ModelProvider {

        public GeneratorModels(PackOutput output) {
            super(output, InfiniteSource.MOD_ID);
        }

        @Override
        protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
            cubed(blockModels, ModBlocks.INFINITE_WATER_BLOCK.get());
        }

        private void cubed(BlockModelGenerators blockModels, Block block) {
            blockModels.createTrivialCube(block);
        }
    }

    static class GeneratorLanguage extends LanguageProvider {
        public GeneratorLanguage(PackOutput output) {
            super(output, InfiniteSource.MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
            addBlock(ModBlocks.INFINITE_WATER_BLOCK, "Infinite Water Source");
            add("itemGroup.infinitesource", "Infinite Source");
        }
    }

    static class GeneratorLootTables extends BlockLootSubProvider {

        public GeneratorLootTables(LootTableSubProvider.Context context) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), context);
        }

        @Override
        protected void generate() {
            dropSelf(ModBlocks.INFINITE_WATER_BLOCK.get());
        }

        @Override
        public Iterable<Block> getKnownBlocks() {
            return new ArrayList<>(ModSetup.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList());
        }
    }

    static class GeneratorRecipes extends RecipeProvider {

        protected GeneratorRecipes(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
            super(recipeOutput, advancementOutput);
        }

        @Override
        protected void buildRecipes() {
            this.shaped(RecipeCategory.MISC, ModBlocks.INFINITE_WATER_BLOCK.get()).define('i', Tags.Items.GLASS_BLOCKS).define('r', Items.WATER_BUCKET).define('d', Tags.Items.GEMS_DIAMOND).pattern("iii").pattern("rdr").pattern("iii").unlockedBy("has_diamonds", has(Tags.Items.GEMS_DIAMOND)).save(this.output);
        }

        public static MultiRegistryBootstrap create() {
            return new MultiRegistryBootstrap() {
                @Override
                public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                    // Return the registries we are adding entries to.
                    return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
                }

                @Override
                public void run(BootstrapGetter registries) {
                    new GeneratorRecipes(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
                }
            };
        }
    }

    static class GeneratorBlockTags extends BlockTagsProvider {

        public GeneratorBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, InfiniteSource.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.INFINITE_WATER_BLOCK.getKey());
        }
    }
}
