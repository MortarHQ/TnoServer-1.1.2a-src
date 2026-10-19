package com.almostreliable.lootjs.mixin;

import java.util.List;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.throwables.MixinException;

@Mixin(SetComponentsFunction.class)
public interface SetComponentsFunctionAccessor {
   @Invoker("<init>")
   static SetComponentsFunction lootjs$create(List<LootItemCondition> condition, DataComponentPatch components) {
      throw new MixinException("Invoker not found");
   }
}
