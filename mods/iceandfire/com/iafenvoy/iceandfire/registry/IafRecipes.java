package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.recipe.DragonForgeRecipe;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;

public final class IafRecipes {
   public static final DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister.create("iceandfire", Registries.RECIPE_TYPE);
   public static final RegistrySupplier<RecipeType<DragonForgeRecipe>> DRAGON_FORGE_TYPE = REGISTRY.register(
      "dragonforge", () -> new RecipeType<DragonForgeRecipe>() {
         @Override
         public String toString() {
            return "dragonforge";
         }
      }
   );

   public static void registerDispenser() {
      DispenserBlock.registerProjectileBehavior((ItemLike)IafItems.STYMPHALIAN_ARROW.get());
      DispenserBlock.registerProjectileBehavior((ItemLike)IafItems.AMPHITHERE_ARROW.get());
      DispenserBlock.registerProjectileBehavior((ItemLike)IafItems.SEA_SERPENT_ARROW.get());
      DispenserBlock.registerProjectileBehavior((ItemLike)IafItems.DRAGONBONE_ARROW.get());
      DispenserBlock.registerProjectileBehavior((ItemLike)IafItems.HYDRA_ARROW.get());
      DispenserBlock.registerProjectileBehavior((ItemLike)IafItems.HIPPOGRYPH_EGG.get());
      DispenserBlock.registerProjectileBehavior((ItemLike)IafItems.ROTTEN_EGG.get());
      DispenserBlock.registerProjectileBehavior((ItemLike)IafItems.DEATHWORM_EGG.get());
      DispenserBlock.registerProjectileBehavior((ItemLike)IafItems.DEATHWORM_EGG_GIGANTIC.get());
   }

   public static void init() {
      registerDispenser();
   }
}
