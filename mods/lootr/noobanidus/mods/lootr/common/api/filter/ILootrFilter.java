package noobanidus.mods.lootr.common.api.filter;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import noobanidus.mods.lootr.common.api.data.LootFiller;

public interface ILootrFilter {
   int getPriority();

   String getName();

   boolean mutate(ObjectArrayList<ItemStack> var1, LootFiller.LootFillerState var2, LootContext var3, RandomSource var4);

   default boolean mutate(ObjectArrayList<ItemStack> toMutate, LootFiller.LootFillerState state, LootContext context) {
      return this.mutate(toMutate, state, context, context.getRandom());
   }
}
