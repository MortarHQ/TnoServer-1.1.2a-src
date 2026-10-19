package com.github.L_Ender.cataclysm.util;

import java.util.Arrays;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class EntityExcludedDamageSource extends DamageSource {
   protected final List<EntityType<?>> entities;

   public EntityExcludedDamageSource(Holder<DamageType> type, EntityType<?>... entities) {
      super(type);
      this.entities = Arrays.stream(entities).toList();
   }

   public Component getLocalizedDeathMessage(LivingEntity living) {
      LivingEntity livingentity = living.getKillCredit();
      String s = "death.attack." + this.type().msgId();
      String s1 = s + ".player";
      if (livingentity != null) {
         for (EntityType<?> entity : this.entities) {
            if (livingentity.getType() == entity) {
               return Component.translatable(s, new Object[]{living.getDisplayName()});
            }
         }
      }

      return livingentity != null
         ? Component.translatable(s1, new Object[]{living.getDisplayName(), livingentity.getDisplayName()})
         : Component.translatable(s, new Object[]{living.getDisplayName()});
   }
}
