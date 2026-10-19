package net.p3pp3rf1y.sophisticatedbackpacks.compat.chipped;

import earth.terrarium.chipped.common.compat.jei.WorkbenchCategory;
import earth.terrarium.chipped.common.compat.rei.ChippedReiPlugin;
import earth.terrarium.chipped.common.registry.ModBlocks;
import earth.terrarium.chipped.common.registry.ModRecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.emi.BackpackEmiPlugin;
import net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.jei.BackpackJeiPlugin;
import net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.rei.BackpackReiClientPlugin;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerRegistry;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.compat.ICompat;
import net.p3pp3rf1y.sophisticatedcore.compat.chipped.BlockTransformationUpgradeContainer;
import net.p3pp3rf1y.sophisticatedcore.compat.chipped.BlockTransformationUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.compat.chipped.BlockTransformationUpgradeWrapper;

public class ChippedCompat implements ICompat {
   public static final DeferredHolder<Item, BlockTransformationUpgradeItem> BOTANIST_WORKBENCH_UPGRADE = ModItems.ITEMS
      .register("chipped/botanist_workbench_upgrade", () -> new BlockTransformationUpgradeItem(ModRecipeTypes.WORKBENCH, Config.SERVER.maxUpgradesPerStorage));
   public static final DeferredHolder<Item, BlockTransformationUpgradeItem> GLASSBLOWER_UPGRADE = ModItems.ITEMS
      .register("chipped/glassblower_upgrade", () -> new BlockTransformationUpgradeItem(ModRecipeTypes.WORKBENCH, Config.SERVER.maxUpgradesPerStorage));
   public static final DeferredHolder<Item, BlockTransformationUpgradeItem> CARPENTERS_TABLE_UPGRADE = ModItems.ITEMS
      .register("chipped/carpenters_table_upgrade", () -> new BlockTransformationUpgradeItem(ModRecipeTypes.WORKBENCH, Config.SERVER.maxUpgradesPerStorage));
   public static final DeferredHolder<Item, BlockTransformationUpgradeItem> LOOM_TABLE_UPGRADE = ModItems.ITEMS
      .register("chipped/loom_table_upgrade", () -> new BlockTransformationUpgradeItem(ModRecipeTypes.WORKBENCH, Config.SERVER.maxUpgradesPerStorage));
   public static final DeferredHolder<Item, BlockTransformationUpgradeItem> MASON_TABLE_UPGRADE = ModItems.ITEMS
      .register("chipped/mason_table_upgrade", () -> new BlockTransformationUpgradeItem(ModRecipeTypes.WORKBENCH, Config.SERVER.maxUpgradesPerStorage));
   public static final DeferredHolder<Item, BlockTransformationUpgradeItem> ALCHEMY_BENCH_UPGRADE = ModItems.ITEMS
      .register("chipped/alchemy_bench_upgrade", () -> new BlockTransformationUpgradeItem(ModRecipeTypes.WORKBENCH, Config.SERVER.maxUpgradesPerStorage));
   public static final DeferredHolder<Item, BlockTransformationUpgradeItem> TINKERING_TABLE_UPGRADE = ModItems.ITEMS
      .register("chipped/tinkering_table_upgrade", () -> new BlockTransformationUpgradeItem(ModRecipeTypes.WORKBENCH, Config.SERVER.maxUpgradesPerStorage));

   public void init(IEventBus modBus) {
      modBus.addListener(this::registerContainers);
      if (ModList.get().isLoaded("jei")) {
         (() -> () -> BackpackJeiPlugin.addAdditionalCatalystRegistrar(registration -> {
            registration.addRecipeCatalyst(new ItemStack((ItemLike)BOTANIST_WORKBENCH_UPGRADE.get()), new RecipeType[]{WorkbenchCategory.RECIPE});
            registration.addRecipeCatalyst(new ItemStack((ItemLike)GLASSBLOWER_UPGRADE.get()), new RecipeType[]{WorkbenchCategory.RECIPE});
            registration.addRecipeCatalyst(new ItemStack((ItemLike)CARPENTERS_TABLE_UPGRADE.get()), new RecipeType[]{WorkbenchCategory.RECIPE});
            registration.addRecipeCatalyst(new ItemStack((ItemLike)LOOM_TABLE_UPGRADE.get()), new RecipeType[]{WorkbenchCategory.RECIPE});
            registration.addRecipeCatalyst(new ItemStack((ItemLike)MASON_TABLE_UPGRADE.get()), new RecipeType[]{WorkbenchCategory.RECIPE});
            registration.addRecipeCatalyst(new ItemStack((ItemLike)ALCHEMY_BENCH_UPGRADE.get()), new RecipeType[]{WorkbenchCategory.RECIPE});
            registration.addRecipeCatalyst(new ItemStack((ItemLike)TINKERING_TABLE_UPGRADE.get()), new RecipeType[]{WorkbenchCategory.RECIPE});
         })).get().run();
      }

      if (ModList.get().isLoaded("emi")) {
         (() -> () -> BackpackEmiPlugin.addAdditionalWorkstations(
               registration -> {
                  registration.addWorkstation(
                     SophisticatedBackpacks.getRL("botanist_workbench"), (Block)ModBlocks.BOTANIST_WORKBENCH.get(), (Item)BOTANIST_WORKBENCH_UPGRADE.get()
                  );
                  registration.addWorkstation(SophisticatedBackpacks.getRL("glassblower"), (Block)ModBlocks.GLASSBLOWER.get(), (Item)GLASSBLOWER_UPGRADE.get());
                  registration.addWorkstation(
                     SophisticatedBackpacks.getRL("carpenters_table"), (Block)ModBlocks.CARPENTERS_TABLE.get(), (Item)CARPENTERS_TABLE_UPGRADE.get()
                  );
                  registration.addWorkstation(SophisticatedBackpacks.getRL("loom_table"), (Block)ModBlocks.LOOM_TABLE.get(), (Item)LOOM_TABLE_UPGRADE.get());
                  registration.addWorkstation(SophisticatedBackpacks.getRL("mason_table"), (Block)ModBlocks.MASON_TABLE.get(), (Item)MASON_TABLE_UPGRADE.get());
                  registration.addWorkstation(
                     SophisticatedBackpacks.getRL("alchemy_bench"), (Block)ModBlocks.ALCHEMY_BENCH.get(), (Item)ALCHEMY_BENCH_UPGRADE.get()
                  );
                  registration.addWorkstation(
                     SophisticatedBackpacks.getRL("tinkering_table"), (Block)ModBlocks.TINKERING_TABLE.get(), (Item)TINKERING_TABLE_UPGRADE.get()
                  );
               }
            ))
            .get()
            .run();
      }

      if (ModList.get().isLoaded("roughlyenoughitems") && FMLEnvironment.dist.isClient()) {
         (() -> () -> BackpackReiClientPlugin.addAdditionalWorkstations(registration -> {
            registration.addWorkstations(ChippedReiPlugin.ID, (Item)BOTANIST_WORKBENCH_UPGRADE.get());
            registration.addWorkstations(ChippedReiPlugin.ID, (Item)GLASSBLOWER_UPGRADE.get());
            registration.addWorkstations(ChippedReiPlugin.ID, (Item)CARPENTERS_TABLE_UPGRADE.get());
            registration.addWorkstations(ChippedReiPlugin.ID, (Item)LOOM_TABLE_UPGRADE.get());
            registration.addWorkstations(ChippedReiPlugin.ID, (Item)MASON_TABLE_UPGRADE.get());
            registration.addWorkstations(ChippedReiPlugin.ID, (Item)ALCHEMY_BENCH_UPGRADE.get());
            registration.addWorkstations(ChippedReiPlugin.ID, (Item)TINKERING_TABLE_UPGRADE.get());
         })).get().run();
      }
   }

   public void registerContainers(RegisterEvent event) {
      if (event.getRegistryKey().equals(Registries.MENU)) {
         this.registerUpgradeContainer(BOTANIST_WORKBENCH_UPGRADE);
         this.registerUpgradeContainer(GLASSBLOWER_UPGRADE);
         this.registerUpgradeContainer(CARPENTERS_TABLE_UPGRADE);
         this.registerUpgradeContainer(LOOM_TABLE_UPGRADE);
         this.registerUpgradeContainer(MASON_TABLE_UPGRADE);
         this.registerUpgradeContainer(ALCHEMY_BENCH_UPGRADE);
         this.registerUpgradeContainer(TINKERING_TABLE_UPGRADE);
      }
   }

   private void registerUpgradeContainer(DeferredHolder<Item, BlockTransformationUpgradeItem> item) {
      UpgradeContainerType<BlockTransformationUpgradeWrapper, BlockTransformationUpgradeContainer> containerType = new UpgradeContainerType(
         BlockTransformationUpgradeContainer::new
      );
      UpgradeContainerRegistry.register(item.getId(), containerType);
      if (FMLEnvironment.dist.isClient()) {
         ChippedCompatClient.registerUpgradeTab(item.getId(), containerType);
      }
   }

   public void setup() {
   }
}
