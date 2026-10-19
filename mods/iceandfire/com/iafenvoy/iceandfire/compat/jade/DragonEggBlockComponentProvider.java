package com.iafenvoy.iceandfire.compat.jade;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.item.block.entity.EggInIceBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum DragonEggBlockComponentProvider implements IBlockComponentProvider {
   INSTANCE;

   public ResourceLocation getUid() {
      return ResourceLocation.fromNamespaceAndPath("iceandfire", "dragon_egg_block");
   }

   public void appendTooltip(ITooltip iTooltip, BlockAccessor entityAccessor, IPluginConfig iPluginConfig) {
      if (entityAccessor.getBlockEntity() instanceof EggInIceBlockEntity egg) {
         iTooltip.add(
            Component.translatable(
               "dragon_egg.hatchTime", new Object[]{String.valueOf(((Integer)IafCommonConfig.INSTANCE.dragon.eggBornTime.getValue() - egg.age) / 20)}
            )
         );
      }
   }
}
