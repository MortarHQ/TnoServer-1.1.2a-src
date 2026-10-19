package fzzyhmstrs.emi_loot.mixins;

import java.util.List;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPool.class)
public interface LootPoolAccessor {
   @Accessor("entries")
   List<LootPoolEntryContainer> getEntries();

   @Accessor("conditions")
   List<LootItemCondition> getConditions();

   @Accessor("functions")
   List<LootItemFunction> getFunctions();

   @Accessor("rolls")
   NumberProvider getRolls();
}
