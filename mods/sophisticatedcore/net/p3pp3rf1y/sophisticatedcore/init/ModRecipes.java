package net.p3pp3rf1y.sophisticatedcore.init;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import net.p3pp3rf1y.sophisticatedcore.crafting.ItemEnabledCondition;
import net.p3pp3rf1y.sophisticatedcore.crafting.UpgradeClearRecipe;
import net.p3pp3rf1y.sophisticatedcore.crafting.UpgradeNextTierRecipe;

public class ModRecipes {
   private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
      BuiltInRegistries.RECIPE_SERIALIZER, "sophisticatedcore"
   );
   private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(Keys.CONDITION_CODECS, "sophisticatedcore");
   public static final Supplier<RecipeSerializer<?>> UPGRADE_NEXT_TIER_SERIALIZER = RECIPE_SERIALIZERS.register(
      "upgrade_next_tier", UpgradeNextTierRecipe.Serializer::new
   );
   public static final Supplier<SimpleCraftingRecipeSerializer<?>> UPGRADE_CLEAR_SERIALIZER = RECIPE_SERIALIZERS.register(
      "upgrade_clear", () -> new SimpleCraftingRecipeSerializer(UpgradeClearRecipe::new)
   );
   public static final Supplier<MapCodec<ItemEnabledCondition>> ITEM_ENABLED_CONDITION = CONDITION_CODECS.register(
      "item_enabled", () -> ItemEnabledCondition.CODEC
   );

   private ModRecipes() {
   }

   public static void registerHandlers(IEventBus modBus) {
      RECIPE_SERIALIZERS.register(modBus);
      CONDITION_CODECS.register(modBus);
   }
}
