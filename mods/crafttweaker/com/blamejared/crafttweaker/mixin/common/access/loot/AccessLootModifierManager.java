package com.blamejared.crafttweaker.mixin.common.access.loot;

import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifierManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = LootModifierManager.class, remap = false)
public interface AccessLootModifierManager {
   @Accessor(value = "registeredLootModifiers", remap = false)
   Map<ResourceLocation, IGlobalLootModifier> crafttweaker$getModifiers();

   @Accessor(value = "registeredLootModifiers", remap = false)
   void crafttweaker$setModifiers(Map<ResourceLocation, IGlobalLootModifier> var1);
}
