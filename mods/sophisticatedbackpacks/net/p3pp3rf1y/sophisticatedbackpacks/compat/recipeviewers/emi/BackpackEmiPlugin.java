package net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.BackpackScreen;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.BackpackSettingsScreen;
import net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.common.DyeRecipesMaker;
import net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.common.subtypes.SubtypeInterpreters;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsScreen;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi.EmiClientRecipeHelper;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi.EmiGridMenuInfo;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi.EmiSettingsGhostDragDropHandler;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi.EmiStorageGhostDragDropHandler;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi.comparison.EmiSubtypeInterpreter;

@EmiEntrypoint
public class BackpackEmiPlugin implements EmiPlugin {
   private static Consumer<BackpackEmiPlugin.WorkstationRegistration> additionalWorkstations = registrar -> {};

   public static void addAdditionalWorkstations(Consumer<BackpackEmiPlugin.WorkstationRegistration> additionalWorkstations) {
      BackpackEmiPlugin.additionalWorkstations = BackpackEmiPlugin.additionalWorkstations.andThen(additionalWorkstations);
   }

   public void register(EmiRegistry registry) {
      this.registerGuiHandlers(registry);
      this.registerRecipes(registry);
      this.registerDefaultComparisons(registry);
      this.registerRecipeHandlers(registry);
      this.registerWorkstations(registry);
   }

   private void registerDefaultComparisons(EmiRegistry registry) {
      SubtypeInterpreters.getSubtypeInterpreters()
         .forEach((item, subtypeInterpreter) -> registry.setDefaultComparison(EmiStack.of(item), EmiSubtypeInterpreter.of(subtypeInterpreter)));
   }

   private void registerGuiHandlers(EmiRegistry registry) {
      registry.addExclusionArea(BackpackScreen.class, (screen, consumer) -> {
         if (screen != null && screen.getUpgradeSettingsControl() != null) {
            screen.getUpgradeSlotsRectangle().ifPresent(r -> consumer.accept(new Bounds(r.getX(), r.getY(), r.getWidth(), r.getHeight())));
            screen.getUpgradeSettingsControl().getTabRectangles().forEach(r -> consumer.accept(new Bounds(r.getX(), r.getY(), r.getWidth(), r.getHeight())));
            screen.getSortButtonsRectangle().ifPresent(r -> consumer.accept(new Bounds(r.getX(), r.getY(), r.getWidth(), r.getHeight())));
         }
      });
      registry.addExclusionArea(BackpackSettingsScreen.class, (screen, consumer) -> {
         if (screen != null) {
            screen.getExtendedControlsRectangles().forEach(r -> consumer.accept(new Bounds(r.getX(), r.getY(), r.getWidth(), r.getHeight())));
         }
      });
      registry.addDragDropHandler(BackpackScreen.class, new EmiStorageGhostDragDropHandler());
      registry.addDragDropHandler(SettingsScreen.class, new EmiSettingsGhostDragDropHandler());
   }

   private void registerRecipes(EmiRegistry registry) {
      DyeRecipesMaker.getRecipes(EmiClientRecipeHelper::wrapSyntheticShapedRecipe).forEach(registry::addRecipe);
   }

   private void registerRecipeHandlers(EmiRegistry registry) {
      registry.addRecipeHandler(ModItems.BACKPACK_CONTAINER_TYPE.get(), EmiGridMenuInfo.crafting());
      registry.addRecipeHandler(ModItems.BACKPACK_CONTAINER_TYPE.get(), EmiGridMenuInfo.smithing());
   }

   private void registerWorkstations(EmiRegistry registry) {
      registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of((ItemLike)ModItems.CRAFTING_UPGRADE.get()));
      registry.addWorkstation(VanillaEmiRecipeCategories.STONECUTTING, EmiStack.of((ItemLike)ModItems.STONECUTTER_UPGRADE.get()));
      registry.addWorkstation(VanillaEmiRecipeCategories.SMITHING, EmiStack.of((ItemLike)ModItems.SMITHING_UPGRADE.get()));
      additionalWorkstations.accept(new BackpackEmiPlugin.WorkstationRegistration(registry));
   }

   public static class WorkstationRegistration {
      private final EmiRegistry registry;

      private WorkstationRegistration(EmiRegistry registry) {
         this.registry = registry;
      }

      public void addWorkstation(ResourceLocation id, Block icon, Item workstation) {
         this.addWorkstation(new EmiRecipeCategory(id, EmiStack.of(icon)), workstation);
      }

      public void addWorkstation(EmiRecipeCategory category, Item workstation) {
         this.addWorkstation(category, EmiStack.of(workstation));
      }

      public void addWorkstation(EmiRecipeCategory category, EmiStack workstation) {
         this.registry.addWorkstation(category, workstation);
      }
   }
}
