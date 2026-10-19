package it.hurts.sskirillss.relics.api.events.leveling;

import it.hurts.sskirillss.relics.api.events.base.RelicEvent;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.ICancellableEvent;

public class ExperienceAddEvent extends RelicEvent implements ICancellableEvent {
   private int amount;

   public ExperienceAddEvent(@Nullable LivingEntity entity, ItemStack stack, int amount) {
      super(entity, stack);
      this.amount = amount;
   }

   public int getAmount() {
      return this.amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }
}
