package twilightforest.enchantment;

import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import twilightforest.init.TFItems;
import twilightforest.item.recipe.ScepterRepairRecipe;

public record RechargeScepterEffect() implements EnchantmentEntityEffect {
   public static final MapCodec<RechargeScepterEffect> CODEC = MapCodec.unit(RechargeScepterEffect::new);

   public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 vec3) {
      applyRecharge(level, item.itemStack(), entity);
   }

   public static void applyRecharge(ServerLevel level, ItemStack item, Entity entity) {
      if (entity instanceof Player player && item.getDamageValue() == item.getMaxDamage()) {
         List<ScepterRepairRecipe> recipes = level.getRecipeManager()
            .getAllRecipesFor(RecipeType.CRAFTING)
            .stream()
            .filter(holder -> holder.value() instanceof ScepterRepairRecipe)
            .<Object>map(RecipeHolder::value)
            .map(ScepterRepairRecipe.class::cast)
            .toList();
         List<Integer> slotsToConsume = new ArrayList<>();

         for (ScepterRepairRecipe recipe : recipes) {
            if (item.is(recipe.getScepter())) {
               ArrayList<Ingredient> ingredientCopy = new ArrayList<>(recipe.getIngredients());

               label58:
               for (int i = 0; i < player.getInventory().items.size(); i++) {
                  ItemStack stack = (ItemStack)player.getInventory().items.get(i);
                  if (!stack.isEmpty()) {
                     if (stack.is(TFItems.EXANIMATE_ESSENCE)) {
                        stack.shrink(1);
                        item.setDamageValue(0);
                        return;
                     }

                     for (Ingredient ingredient : recipe.getIngredients()) {
                        if (ingredientCopy.contains(ingredient) && ingredient.test(stack)) {
                           ingredientCopy.remove(ingredient);
                           slotsToConsume.add(i);
                           if (ingredientCopy.isEmpty()) {
                              break label58;
                           }
                        }
                     }
                  }
               }

               if (slotsToConsume.size() == recipe.getIngredients().size()) {
                  for (int slot : slotsToConsume) {
                     ItemStack stack = (ItemStack)player.getInventory().items.get(slot);
                     stack.shrink(1);
                     if (stack.hasCraftingRemainingItem() && !player.getInventory().add(stack.getCraftingRemainingItem())) {
                        player.drop(stack.getCraftingRemainingItem(), false);
                     }
                  }

                  item.setDamageValue(item.getDamageValue() - recipe.getRepairDurability());
               }
            }
         }
      }
   }

   public MapCodec<? extends EnchantmentEntityEffect> codec() {
      return CODEC;
   }
}
