package com.blackgear.platform.common.events;

import com.blackgear.platform.core.util.event.CancellableResult;
import com.blackgear.platform.core.util.event.Event;
import java.util.Arrays;
import java.util.function.Consumer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface EntityEvents {
   Event<EntityEvents.LivingSpawn> ON_SPAWN = Event.cancellable(EntityEvents.LivingSpawn.class);
   Event<EntityEvents.LivingAttack> ON_ATTACK = Event.cancellable(EntityEvents.LivingAttack.class);
   Event<EntityEvents.LivingRemove> ON_REMOVE = Event.cancellable(EntityEvents.LivingRemove.class);
   Event<EntityEvents.LivingDeath> ON_DEATH = Event.create(
      EntityEvents.LivingDeath.class, events -> (entity, source) -> Arrays.stream(events).allMatch(event -> event.onDeath(entity, source))
   );
   Event<EntityEvents.EntityPickUp> ON_PICK = Event.create(EntityEvents.EntityPickUp.class);

   interface EntityPickUp {
      void onPickUp(Entity var1, Consumer<ItemStack> var2);
   }

   interface LivingAttack {
      CancellableResult onAttack(Entity var1, DamageSource var2);
   }

   interface LivingDeath {
      boolean onDeath(Entity var1, DamageSource var2);
   }

   interface LivingRemove {
      CancellableResult onRemove(Entity var1, DamageSource var2);
   }

   interface LivingSpawn {
      CancellableResult onSpawn(Entity var1, Level var2);
   }
}
