package org.crypticdev.stextras.mixin.world.inventory.entity.animal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Animal.class)
public abstract class AnimalMixin {
   @Inject(
      method = "finalizeSpawnChildFromBreeding(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/animal/Animal;Lnet/minecraft/world/entity/AgeableMob;)V",
      at = @At("TAIL")
   )
   private void stextras$afterBreed(ServerLevel level, Animal partner, @Nullable AgeableMob baby, CallbackInfo ci) {
      if (baby != null) {
         Animal self = (Animal)this;
         ServerPlayer sp = self.getLoveCause();
         if (sp == null) {
            sp = partner.getLoveCause();
         }

         if (sp != null) {
            STPlayerEvents.fireBreed(sp, baby, self, partner);
         }
      }
   }
}
