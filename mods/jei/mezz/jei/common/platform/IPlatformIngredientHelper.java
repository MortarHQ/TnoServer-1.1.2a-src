package mezz.jei.common.platform;

import java.util.List;
import java.util.stream.Stream;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.ComposterBlock;

public interface IPlatformIngredientHelper {
   Ingredient createShulkerDyeIngredient(DyeColor var1);

   List<Ingredient> getPotionContainers(PotionBrewing var1);

   Stream<Ingredient> getPotionIngredients(PotionBrewing var1);

   default float getCompostValue(ItemStack itemStack) {
      return ComposterBlock.COMPOSTABLES.getOrDefault(itemStack.getItem(), 0.0F);
   }
}
