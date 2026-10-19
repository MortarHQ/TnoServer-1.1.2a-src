package com.blamejared.crafttweaker.mixin.common.access.loot;

import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Builder.class)
public interface AccessLootContextBuilder {
   @Accessor("params")
   LootParams crafttweaker$getParams();
}
