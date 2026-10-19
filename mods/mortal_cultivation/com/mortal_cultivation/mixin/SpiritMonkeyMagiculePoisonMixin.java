package com.mortal_cultivation.mixin;

import com.mortal_cultivation.registry.WorldgenRegistry;
import com.mortal_cultivation.registry.race.CultivationRaces;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.storage.ep.ExistenceStorage;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExistenceStorage.class)
public abstract class SpiritMonkeyMagiculePoisonMixin {
   @Inject(method = "getMagiculePoisonResistance", at = @At("HEAD"), cancellable = true, remap = false)
   private static void mortalCultivation$protectSpiritMonkey(IExistence existence, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
      if (entity.level().getBiome(entity.getOnPos()).is(WorldgenRegistry.SPIRIT_FORESTS)
         && RaceAPI.getRaceFrom(entity).getRace().filter(instance -> instance.getRace() == CultivationRaces.SPIRIT_MONKEY_RACE.get()).isPresent()) {
         cir.setReturnValue(Integer.MAX_VALUE);
      }
   }
}
