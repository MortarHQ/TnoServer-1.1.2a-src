package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.SetPotionLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class SetPotionFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      Holder<Potion> potion = ((SetPotionLootFunctionAccessor)function).getPotion();
      if (!stack.isEmpty()) {
         stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
      }

      Component potionName = LText.translatable(
         Potion.getName(Optional.of(((SetPotionLootFunctionAccessor)function).getPotion()), Items.POTION.getDescriptionId() + ".effect.")
      );
      return new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.potion", potionName), ItemStack.EMPTY, conditionTexts);
   }
}
