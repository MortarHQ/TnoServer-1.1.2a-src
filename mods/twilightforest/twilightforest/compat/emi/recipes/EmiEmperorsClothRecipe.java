package twilightforest.compat.emi.recipes;

import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.SlotWidget;
import java.util.List;
import java.util.Random;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.init.TFRecipes;

public class EmiEmperorsClothRecipe extends EmiPatternCraftingRecipe {
   public static final List<EmiStack> ARMORS = BuiltInRegistries.ITEM.stream().filter(item -> item instanceof ArmorItem).<EmiStack>map(EmiStack::of).toList();

   public EmiEmperorsClothRecipe() {
      super(List.of(EmiIngredient.of(ARMORS), EmiStack.of(TFItems.EMPERORS_CLOTH)), EmiStack.EMPTY, TFRecipes.EMPERORS_CLOTH_RECIPE.getId());
   }

   public ResourceLocation getId() {
      return super.getId().withPrefix("/");
   }

   public SlotWidget getInputWidget(int slot, int x, int y) {
      if (slot == 0) {
         return new GeneratedSlotWidget(random -> this.getArmor(random, false), this.unique, x, y);
      } else {
         return slot == 1 ? new SlotWidget(EmiStack.of(TFItems.EMPERORS_CLOTH), x, y) : new SlotWidget(EmiStack.EMPTY, x, y);
      }
   }

   public SlotWidget getOutputWidget(int x, int y) {
      return new GeneratedSlotWidget(random -> this.getArmor(random, true), this.unique, x, y);
   }

   public List<EmiStack> getOutputs() {
      return ARMORS;
   }

   private EmiStack getArmor(Random random, boolean addTag) {
      ItemStack stack = ARMORS.get(random.nextInt(ARMORS.size())).getItemStack().copy();
      if (addTag) {
         stack.set(TFDataComponents.EMPERORS_CLOTH, Unit.INSTANCE);
      }

      return EmiStack.of(stack);
   }
}
