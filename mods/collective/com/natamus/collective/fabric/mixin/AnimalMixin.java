package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveAnimalEvents;
import net.minecraft.class_1296;
import net.minecraft.class_1299;
import net.minecraft.class_1429;
import net.minecraft.class_1937;
import net.minecraft.class_3218;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = class_1429.class, priority = 1001)
public abstract class AnimalMixin extends class_1296 {
   @Shadow
   private int field_6745;

   protected AnimalMixin(class_1299<? extends class_1296> entityType, class_1937 level) {
      super(entityType, level);
   }

   @ModifyVariable(
      method = "spawnChildFromBreeding",
      at = @At(
         value = "INVOKE_ASSIGN",
         target = "Lnet/minecraft/world/entity/animal/Animal;getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/AgeableMob;",
         ordinal = 0
      ),
      ordinal = 0
   )
   private class_1296 Animal_spawnChildFromBreeding(class_1296 ageablemob, class_3218 serverLevel, class_1429 animal) {
      class_1429 parentA = (class_1429)this;
      boolean shouldBreed = ((CollectiveAnimalEvents.On_Baby_Spawn)CollectiveAnimalEvents.PRE_BABY_SPAWN.invoker())
         .onBabySpawn(serverLevel, parentA, animal, ageablemob);
      if (!shouldBreed) {
         ageablemob = null;
         this.method_5614(6000);
         animal.method_5614(6000);
         this.field_6745 = 0;
         animal.method_6476(0);
      }

      return ageablemob;
   }
}
