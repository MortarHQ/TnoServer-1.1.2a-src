package twilightforest.compat.emi.recipes;

import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.SlotWidget;
import java.util.List;
import java.util.Random;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EmiScepterRepairRecipe extends EmiPatternCraftingRecipe {
   private final int durability;

   public EmiScepterRepairRecipe(List<EmiIngredient> input, EmiStack output, int durability, ResourceLocation id) {
      super(input, output, id);
      this.durability = durability;
   }

   public SlotWidget getInputWidget(int slot, int x, int y) {
      if (slot == 0) {
         return new GeneratedSlotWidget(r -> EmiStack.of(this.damageScepter(r)), this.unique, x, y);
      } else {
         return slot <= this.input.size() ? new SlotWidget((EmiIngredient)this.input.get(slot - 1), x, y) : new SlotWidget(EmiStack.EMPTY, x, y);
      }
   }

   public SlotWidget getOutputWidget(int x, int y) {
      return new GeneratedSlotWidget(r -> EmiStack.of(this.damageNewScepter(r)), this.unique, x, y);
   }

   private ItemStack damageNewScepter(Random random) {
      ItemStack stack = this.output.getItemStack();
      if (stack.getMaxDamage() <= 0) {
         return stack;
      }

      int d = random.nextInt(stack.getMaxDamage());
      int damage = d - this.durability;
      if (damage > 0) {
         stack.setDamageValue(damage);
      }

      return stack;
   }

   private ItemStack damageScepter(Random random) {
      ItemStack stack = this.output.getItemStack();
      if (stack.getMaxDamage() <= 0) {
         return stack;
      }

      int d = random.nextInt(stack.getMaxDamage());
      stack.setDamageValue(d);
      return stack;
   }
}
