package fzzyhmstrs.emi_loot.server;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import java.util.Collection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public abstract class AbstractLootPoolBuilder implements LootBuilder {
   final float rollWeight;
   boolean isSimple = false;
   boolean isEmpty = false;
   ItemStack simpleStack = ItemStack.EMPTY;
   private final Multimap<LootTableParser.PostProcessor, LootPoolEntryContainer> map = ArrayListMultimap.create();

   public AbstractLootPoolBuilder(float rollWeight) {
      this.rollWeight = rollWeight;
   }

   @Override
   public void addEntryForPostProcessing(LootTableParser.PostProcessor process, LootPoolEntryContainer entry) {
      this.map.put(process, entry);
   }

   @Override
   public Collection<LootPoolEntryContainer> getEntriesToPostProcess(LootTableParser.PostProcessor process) {
      return this.map.get(process);
   }
}
