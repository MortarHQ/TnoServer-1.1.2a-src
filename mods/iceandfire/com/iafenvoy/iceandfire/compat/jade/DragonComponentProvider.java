package com.iafenvoy.iceandfire.compat.jade;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum DragonComponentProvider implements IEntityComponentProvider {
   INSTANCE;

   public ResourceLocation getUid() {
      return ResourceLocation.fromNamespaceAndPath("iceandfire", "dragon");
   }

   public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
      if (entityAccessor.getEntity() instanceof DragonBaseEntity dragon) {
         iTooltip.add(Component.translatable("dragon.stage").withStyle(ChatFormatting.GRAY).append(Component.literal(" " + dragon.getDragonStage())));
         iTooltip.add(Component.literal(dragon.getAgeInDays() + "d"));
         iTooltip.add(Component.literal(dragon.isMale() ? "Male" : "Female"));
      }
   }
}
