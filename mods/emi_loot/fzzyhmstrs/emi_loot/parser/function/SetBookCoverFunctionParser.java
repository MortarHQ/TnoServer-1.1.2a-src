package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.SetBookCoverLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class SetBookCoverFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      List<MutableComponent> texts = new ArrayList<>();
      Optional<String> author = ((SetBookCoverLootFunctionAccessor)function).getAuthor();
      author.ifPresent(s -> texts.add(LText.literal(s)));
      Optional<Filterable<String>> title = ((SetBookCoverLootFunctionAccessor)function).getTitle();
      title.ifPresent(t -> texts.add(LText.literal((String)t.raw())));
      Optional<Integer> generation = ((SetBookCoverLootFunctionAccessor)function).getGeneration();
      generation.ifPresent(g -> texts.add(LText.literal(g.toString())));
      if (!stack.isEmpty()) {
         stack.update(DataComponents.WRITTEN_BOOK_CONTENT, WrittenBookContent.EMPTY, ((SetBookCoverLootFunctionAccessor)function)::callApply);
      }

      return new LootTableParser.LootFunctionResult(
         TextKey.of("emi_loot.function.set_book_cover", ListProcessors.buildAndList(texts)), ItemStack.EMPTY, conditionTexts
      );
   }
}
