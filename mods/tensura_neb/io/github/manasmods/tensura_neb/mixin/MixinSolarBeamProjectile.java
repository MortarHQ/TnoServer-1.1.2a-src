package io.github.manasmods.tensura_neb.mixin;

import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.beam.SolarBeamProjectile;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SolarBeamProjectile.class)
public class MixinSolarBeamProjectile {
   @Inject(at = @At("RETURN"), method = "getDamageType", cancellable = true, remap = false)
   private void getDamageType(CallbackInfoReturnable<ResourceKey<DamageType>> cir) {
      SolarBeamProjectile beam = (SolarBeamProjectile)this;
      if (beam.getOwner() != null && beam.getOwner().getType().equals(NebEntityTypes.CARRION.get())) {
         cir.setReturnValue(TensuraDamageTypes.AURA_BULLET);
      }
   }
}
