package com.almostreliable.lootjs.loot.extension;

import com.almostreliable.lootjs.loot.LootConditionList;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.function.Consumer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

@RemapPrefixForJS("lootjs$")
public interface LootItemFunctionExtension {
   LootItemFunction lootjs$when(Consumer<LootConditionList> var1);
}
