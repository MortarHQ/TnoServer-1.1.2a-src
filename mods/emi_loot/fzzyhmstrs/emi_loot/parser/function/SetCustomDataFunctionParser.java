package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.SetCustomDataLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class SetCustomDataFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      CompoundTag compound = ((SetCustomDataLootFunctionAccessor)function).getTag();
      CustomData.update(DataComponents.CUSTOM_DATA, stack, nbt -> nbt.merge(compound));
      return new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.set_nbt"), ItemStack.EMPTY, conditionTexts);
   }
}
