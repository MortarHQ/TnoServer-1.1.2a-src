package com.bobmowzie.mowziesmobs.server.ability;

import net.minecraft.world.entity.LivingEntity;

public class AbilityType<M extends LivingEntity, T extends Ability<M>> implements Comparable<AbilityType<M, T>> {
   private final AbilityType.IFactory<M, T> factory;
   private final String name;

   public AbilityType(String name, AbilityType.IFactory<M, T> factoryIn) {
      this.factory = factoryIn;
      this.name = name;
   }

   public T makeInstance(LivingEntity user) {
      return this.factory.create(this, (M)user);
   }

   public String getName() {
      return this.name;
   }

   public int compareTo(AbilityType<M, T> o) {
      return this.getName().compareTo(o.getName());
   }

   public interface IFactory<M extends LivingEntity, T extends Ability<M>> {
      T create(AbilityType<M, T> var1, M var2);
   }
}
