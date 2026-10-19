package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(VillagerHostilesSensor.class)
public final class VillagerHostilesSensorMixin {
   @Unique
   private static final ImmutableMap<EntityType<?>, Float> FRIENDSANDFOES_SQUARED_DISTANCES_FOR_DANGER = ImmutableMap.builder()
      .put((EntityType)FriendsAndFoesEntityTypes.ILLUSIONER.get(), 12.0F)
      .put((EntityType)FriendsAndFoesEntityTypes.ICEOLOGER.get(), 12.0F)
      .build();

   @WrapMethod(method = "isClose")
   private boolean friendsandfoes$isCloseEnoughForDanger(LivingEntity attacker, LivingEntity target, Operation<Boolean> original) {
      EntityType<?> entityType = target.getType();
      if (FRIENDSANDFOES_SQUARED_DISTANCES_FOR_DANGER.containsKey(entityType)) {
         Float distance = (Float)FRIENDSANDFOES_SQUARED_DISTANCES_FOR_DANGER.get(entityType);
         return distance == null ? false : target.distanceToSqr(attacker) <= distance * distance;
      } else {
         return (Boolean)original.call(new Object[]{attacker, target});
      }
   }

   @WrapMethod(method = "isHostile")
   private boolean friendsandfoes$isHostile(LivingEntity entity, Operation<Boolean> original) {
      return original.call(new Object[]{entity}) ? true : FRIENDSANDFOES_SQUARED_DISTANCES_FOR_DANGER.containsKey(entity.getType());
   }
}
