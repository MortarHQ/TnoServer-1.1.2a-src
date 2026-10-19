package com.iafenvoy.iceandfire.compat.jade;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.DragonEggEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum DragonEggEntityComponentProvider implements IEntityComponentProvider {
   INSTANCE;

   public ResourceLocation getUid() {
      return ResourceLocation.fromNamespaceAndPath("iceandfire", "dragon_egg_entity");
   }

   public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
      if (entityAccessor.getEntity() instanceof DragonEggEntity egg) {
         iTooltip.add(
            Component.translatable(
               "dragon_egg.hatchTime",
               new Object[]{String.valueOf(((Integer)IafCommonConfig.INSTANCE.dragon.eggBornTime.getValue() - egg.getDragonAge()) / 20)}
            )
         );
      }
   }
}
