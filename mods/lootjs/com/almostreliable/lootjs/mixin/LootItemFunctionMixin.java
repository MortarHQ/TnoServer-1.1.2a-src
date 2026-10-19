package com.almostreliable.lootjs.mixin;

import com.almostreliable.lootjs.LootJS;
import com.almostreliable.lootjs.loot.LootConditionList;
import com.almostreliable.lootjs.loot.extension.LootItemFunctionExtension;
import java.util.function.Consumer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LootItemFunction.class)
public interface LootItemFunctionMixin extends LootItemFunctionExtension {
   @Override
   default LootItemFunction lootjs$when(Consumer<LootConditionList> consumer) {
      LootJS.DEBUG_ACTION.accept("Non conditional loot functions are not supported yet! Added conditions will be ignored!");
      return (LootItemFunction)this;
   }
}
