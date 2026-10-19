package it.hurts.sskirillss.relics.api.events.base;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

public class RelicEvent extends Event {
   @Nullable
   LivingEntity entity;
   ItemStack stack;

   public RelicEvent(@Nullable LivingEntity entity, ItemStack stack) {
      this.entity = entity;
      this.stack = stack;
   }

   @Nullable
   public LivingEntity getEntity() {
      return this.entity;
   }

   public ItemStack getStack() {
      return this.stack;
   }
}
