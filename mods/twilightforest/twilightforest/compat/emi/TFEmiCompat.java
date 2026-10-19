package twilightforest.compat.emi;

import com.mojang.datafixers.util.Pair;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.recipe.EmiAnvilRecipe;
import dev.emi.emi.recipe.EmiGrindstoneRecipe;
import dev.emi.emi.recipe.special.EmiAnvilEnchantRecipe;
import dev.emi.emi.recipe.special.EmiAnvilRepairItemRecipe;
import dev.emi.emi.recipe.special.EmiGrindstoneDisenchantingRecipe;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.block.Block;
import twilightforest.compat.RecipeViewerConstants;
import twilightforest.compat.emi.recipes.EmiCrumbleHornRecipe;
import twilightforest.compat.emi.recipes.EmiDryingRecipe;
import twilightforest.compat.emi.recipes.EmiEmperorsClothRecipe;
import twilightforest.compat.emi.recipes.EmiMoonwormQueenRecipe;
import twilightforest.compat.emi.recipes.EmiNoSmithingTemplateRecipe;
import twilightforest.compat.emi.recipes.EmiOminousFireRecipe;
import twilightforest.compat.emi.recipes.EmiScepterRepairRecipe;
import twilightforest.compat.emi.recipes.EmiTransformationPowderRecipe;
import twilightforest.compat.emi.recipes.EmiTravellersGearGrindstoneRecipe;
import twilightforest.compat.emi.recipes.EmiTravellersGearModifierRecipe;
import twilightforest.compat.emi.recipes.EmiUncraftingRecipe;
import twilightforest.config.TFConfig;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.init.TFRecipes;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.item.recipe.DryingRecipe;
import twilightforest.item.recipe.EmperorsClothRecipe;
import twilightforest.item.recipe.MoonwormQueenRepairRecipe;
import twilightforest.item.recipe.NoTemplateSmithingRecipe;
import twilightforest.item.recipe.ScepterRepairRecipe;
import twilightforest.item.recipe.travellers.TravellersGearModifierRecipe;

@EmiEntrypoint
public class TFEmiCompat implements EmiPlugin {
   private static final Function<List<EmiIngredient>, Boolean> CANT_USE_ENCHANTS = stack -> stack.contains(EmiStack.of(TFItems.MOONWORM_QUEEN))
      || stack.contains(EmiStack.of(TFItems.LAMP_OF_CINDERS))
      || stack.contains(EmiStack.of(TFItems.ORE_MAGNET))
      || stack.contains(EmiStack.of(TFItems.TWILIGHT_SCEPTER))
      || stack.contains(EmiStack.of(TFItems.LIFEDRAIN_SCEPTER))
      || stack.contains(EmiStack.of(TFItems.ZOMBIE_SCEPTER))
      || stack.contains(EmiStack.of(TFItems.FORTIFICATION_SCEPTER))
      || stack.contains(EmiStack.of(TFItems.TRAVELLERS_GOGGLES))
      || stack.contains(EmiStack.of(TFItems.TRAVELLERS_VEST))
      || stack.contains(EmiStack.of(TFItems.TRAVELLERS_GLOVES))
      || stack.contains(EmiStack.of(TFItems.TRAVELLERS_BELT))
      || stack.contains(EmiStack.of(TFItems.TRAVELLERS_WINGS))
      || stack.contains(EmiStack.of(TFItems.TRAVELLERS_BOOTS));
   private static final Function<List<EmiIngredient>, Boolean> NO_REPAIRING = stack -> stack.contains(EmiStack.of(TFItems.LAMP_OF_CINDERS))
      || stack.contains(EmiStack.of(TFItems.GLASS_SWORD))
      || stack.contains(EmiStack.of(TFItems.MAZEBREAKER_PICKAXE));

   public void register(EmiRegistry registry) {
      registry.addCategory(TFEmiCategories.UNCRAFTING);
      registry.addCategory(TFEmiCategories.CRUMBLE_HORN);
      registry.addCategory(TFEmiCategories.TRANSFORMATION);
      registry.addCategory(TFEmiCategories.EXANIMATE);
      registry.addCategory(TFEmiCategories.DRYING);
      if (!TFConfig.disableEntireTable) {
         registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of(TFBlocks.UNCRAFTING_TABLE));
         registry.addWorkstation(TFEmiCategories.UNCRAFTING, EmiStack.of(TFBlocks.UNCRAFTING_TABLE));
      }

      registry.addWorkstation(TFEmiCategories.CRUMBLE_HORN, EmiStack.of(TFItems.CRUMBLE_HORN));
      registry.addWorkstation(TFEmiCategories.TRANSFORMATION, EmiStack.of(TFItems.TRANSFORMATION_POWDER));
      registry.addWorkstation(TFEmiCategories.EXANIMATE, EmiStack.of(TFItems.EXANIMATE_ESSENCE));
      RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
      if (!TFConfig.disableEntireTable) {
         List<RecipeHolder<? extends CraftingRecipe>> recipes = RecipeViewerConstants.getAllUncraftingRecipes(manager);
         recipes.forEach(recipex -> registry.addRecipe(new EmiUncraftingRecipe(recipex)));
      }

      for (RecipeViewerConstants.TransformationPowderInfo info : RecipeViewerConstants.getTransformationPowderRecipes()) {
         registry.addRecipe(new EmiTransformationPowderRecipe(info.input(), info.output(), info.reversible()));
      }

      for (RecipeViewerConstants.OminousFireInfo info : RecipeViewerConstants.getOminousFireRecipes()) {
         registry.addRecipe(new EmiOminousFireRecipe(info.input(), info.output()));
      }

      for (Pair<Block, Block> info : RecipeViewerConstants.getCrumbleHornRecipes()) {
         registry.addRecipe(new EmiCrumbleHornRecipe((Block)info.getFirst(), (Block)info.getSecond()));
      }

      for (RecipeHolder<SmithingRecipe> holder : manager.getAllRecipesFor(RecipeType.SMITHING)
         .stream()
         .filter(holderx -> holderx.value() instanceof NoTemplateSmithingRecipe)
         .toList()) {
         NoTemplateSmithingRecipe recipe = (NoTemplateSmithingRecipe)holder.value();
         registry.addRecipe(
            new EmiNoSmithingTemplateRecipe(
               EmiIngredient.of(recipe.getBase()),
               EmiIngredient.of(recipe.getAddition()),
               EmiStack.of(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())),
               recipe
            )
         );
      }

      for (RecipeHolder<DryingRecipe> holder : manager.getAllRecipesFor((RecipeType)TFRecipes.DRYING_RECIPE.get())) {
         if (!((DryingRecipe)holder.value()).getResult().is(TFItems.STALE_BREAD)) {
            registry.addRecipe(new EmiDryingRecipe(holder));
         }
      }

      EmiTravellersGearGrindstoneRecipe.register(registry);

      for (RecipeHolder<?> holder : manager.getAllRecipesFor(RecipeType.CRAFTING)) {
         EmiRecipe emiRecipe = (EmiRecipe)(switch (holder.value()) {
            case MoonwormQueenRepairRecipe ignored -> new EmiMoonwormQueenRecipe();
            case EmperorsClothRecipe ignored -> new EmiEmperorsClothRecipe();
            case ScepterRepairRecipe recipe -> new EmiScepterRepairRecipe(
               recipe.getIngredients().stream().<EmiIngredient>map(EmiIngredient::of).toList(),
               EmiStack.of(recipe.getScepter()),
               recipe.getRepairDurability(),
               holder.id()
            );
            case TravellersGearModifierRecipe recipe -> TravellersModifiersManager.isModifierEnabled(
                  Minecraft.getInstance().level.registryAccess(), recipe.getTravellersModifierKey()
               )
               ? new EmiTravellersGearModifierRecipe(recipe)
               : null;
            default -> null;
         });
         if (emiRecipe != null) {
            registry.addRecipe(emiRecipe);
         }
      }

      registry.removeRecipes(recipex -> {
         if (recipex instanceof EmiPatternCraftingRecipe || recipex instanceof EmiGrindstoneRecipe) {
            return recipex.getInputs().contains(EmiStack.of(TFItems.MOONWORM_QUEEN)) || NO_REPAIRING.apply(recipex.getInputs());
         } else if (recipex instanceof EmiGrindstoneDisenchantingRecipe || recipex instanceof EmiAnvilEnchantRecipe) {
            return CANT_USE_ENCHANTS.apply(recipex.getInputs());
         } else {
            return !(recipex instanceof EmiAnvilRepairItemRecipe) && !(recipex instanceof EmiAnvilRecipe) ? false : NO_REPAIRING.apply(recipex.getInputs());
         }
      });
      registry.removeEmiStacks(stack -> TFItems.MAGIC_PAINTING.getId().equals(stack.getId()));
   }
}
