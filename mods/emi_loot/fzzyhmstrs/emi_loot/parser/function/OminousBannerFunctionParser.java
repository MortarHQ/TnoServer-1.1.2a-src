package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class OminousBannerFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.info("Parsing an ominous banner function");
      }

      Optional<HolderGetter<BannerPattern>> patternLookup = LootTableParser.registryOps.getter(Registries.BANNER_PATTERN);
      return patternLookup.<LootTableParser.LootFunctionResult>map(
            bannerPatternRegistryEntryLookup -> new LootTableParser.LootFunctionResult(
               TextKey.of("emi_loot.function.ominous_banner"), Raid.getLeaderBannerInstance(bannerPatternRegistryEntryLookup), conditionTexts
            )
         )
         .orElseGet(() -> new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.ominous_banner"), ItemStack.EMPTY, conditionTexts));
   }
}
