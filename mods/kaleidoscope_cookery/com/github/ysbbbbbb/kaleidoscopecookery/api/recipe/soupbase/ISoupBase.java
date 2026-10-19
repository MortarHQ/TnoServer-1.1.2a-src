package com.github.ysbbbbbb.kaleidoscopecookery.api.recipe.soupbase;

import com.github.ysbbbbbb.kaleidoscopecookery.api.client.render.ISoupBaseRender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface ISoupBase {
   ResourceLocation getName();

   int getBubbleColor();

   ItemStack getDisplayStack();

   boolean isSoupBase(ItemStack var1);

   ItemStack getReturnContainer(Level var1, LivingEntity var2, ItemStack var3);

   boolean isContainer(ItemStack var1);

   ItemStack getReturnSoupBase(Level var1, LivingEntity var2, ItemStack var3);

   @OnlyIn(Dist.CLIENT)
   ISoupBaseRender getRender();
}
