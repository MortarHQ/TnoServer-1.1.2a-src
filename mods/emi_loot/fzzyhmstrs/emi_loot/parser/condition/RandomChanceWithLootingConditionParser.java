package fzzyhmstrs.emi_loot.parser.condition;

import fzzyhmstrs.emi_loot.parser.EnchantmentLevelBasedValueParser;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;

public class RandomChanceWithLootingConditionParser implements ConditionParser {
   @Override
   public List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      float chance = ((LootItemRandomChanceWithEnchantedBonusCondition)condition).unenchantedChance();
      LevelBasedValue multiplier = ((LootItemRandomChanceWithEnchantedBonusCondition)condition).enchantedChance();
      Holder<Enchantment> enchant = ((LootItemRandomChanceWithEnchantedBonusCondition)condition).enchantment();
      List<Component> args = new ArrayList<>(
         Arrays.stream(new Component[]{EnchantmentLevelBasedValueParser.parseValue(multiplier, "%", 100.0F), LText.enchant(enchant)}).toList()
      );
      return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.chance_looting", args)));
   }
}
