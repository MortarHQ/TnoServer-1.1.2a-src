package artifacts.component.ability;

import net.minecraft.world.entity.LivingEntity;

public interface TickingCompositeAbility<ENTRY extends TickingAbility> extends CompositeAbility<ENTRY>, TickingAbility {
   @Override
   default void onUnequip(LivingEntity entity) {
      for (ENTRY entry : this.entries()) {
         entry.onUnequip(entity);
      }
   }

   @Override
   default void wornTick(LivingEntity entity, boolean isOnCooldown, boolean isDisabled) {
      for (ENTRY entry : this.entries()) {
         entry.wornTick(entity, isOnCooldown, isDisabled);
      }
   }
}
