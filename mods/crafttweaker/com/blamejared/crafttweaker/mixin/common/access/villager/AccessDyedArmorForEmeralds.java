package com.blamejared.crafttweaker.mixin.common.access.villager;

import net.minecraft.world.entity.npc.VillagerTrades.DyedArmorForEmeralds;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DyedArmorForEmeralds.class)
public interface AccessDyedArmorForEmeralds {
   @Accessor("item")
   Item crafttweaker$getItem();
}
