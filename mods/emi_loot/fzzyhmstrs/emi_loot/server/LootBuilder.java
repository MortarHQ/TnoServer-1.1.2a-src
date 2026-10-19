package fzzyhmstrs.emi_loot.server;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public interface LootBuilder {
   void addEntryForPostProcessing(LootTableParser.PostProcessor var1, LootPoolEntryContainer var2);

   Collection<LootPoolEntryContainer> getEntriesToPostProcess(LootTableParser.PostProcessor var1);

   void addItem(LootTableParser.ItemEntryResult var1);

   void build();

   List<LootTableParser.ItemEntryResult> revert();
}
