package com.blamejared.crafttweaker.mixin.common.access.villager;

import net.minecraft.world.entity.npc.VillagerTrades.EmeraldForItems;
import net.minecraft.world.item.trading.ItemCost;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EmeraldForItems.class)
public interface AccessEmeraldForItems {
   @Accessor("itemStack")
   ItemCost crafttweaker$getItemStack();
}
