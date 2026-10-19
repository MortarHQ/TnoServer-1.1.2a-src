package mezz.jei.neoforge.platform;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import mezz.jei.common.platform.IPlatformIngredientHelper;
import mezz.jei.common.util.RegistryUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionBrewing.Mix;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Ingredient.ItemValue;
import net.minecraft.world.item.crafting.Ingredient.TagValue;
import net.minecraft.world.item.crafting.Ingredient.Value;
import net.minecraft.world.level.block.ComposterBlock;

public class IngredientHelper implements IPlatformIngredientHelper {
   @Override
   public Ingredient createShulkerDyeIngredient(DyeColor color) {
      DyeItem dye = DyeItem.byColor(color);
      TagKey<Item> colorTag = color.getTag();
      Value colorList = new TagValue(colorTag);
      Registry<Item> itemRegistry = RegistryUtil.getRegistry(Registries.ITEM);
      Iterable<Holder<Item>> coloredItems = itemRegistry.getTagOrEmpty(colorTag);
      boolean contains = StreamSupport.stream(coloredItems.spliterator(), false).anyMatch(h -> h.value() == dye);
      Stream<Value> colorIngredientStream;
      if (!contains) {
         ItemStack dyeStack = new ItemStack(dye);
         Value dyeList = new ItemValue(dyeStack);
         colorIngredientStream = Stream.of(dyeList, colorList);
      } else {
         colorIngredientStream = Stream.of(colorList);
      }

      return Ingredient.fromValues(colorIngredientStream);
   }

   @Override
   public List<Ingredient> getPotionContainers(PotionBrewing potionBrewing) {
      return potionBrewing.containers;
   }

   @Override
   public Stream<Ingredient> getPotionIngredients(PotionBrewing potionBrewing) {
      return Stream.concat(potionBrewing.containerMixes.stream(), potionBrewing.potionMixes.stream()).map(Mix::ingredient);
   }

   @Override
   public float getCompostValue(ItemStack itemStack) {
      return ComposterBlock.getValue(itemStack);
   }
}
