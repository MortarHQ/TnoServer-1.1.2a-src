package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.RelicsEquipmentLookup;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "it.hurts.octostudios.reliquified_twilight_forest.item.relic.ScaledCloakItem", remap = false)
public class ReliquifiedScaledCloakDamageMixin {
   private static final ResourceLocation SCALED_CLOAK = ResourceLocation.fromNamespaceAndPath("reliquified_twilight_forest", "scaled_cloak");
   private static final ResourceLocation ROYAL_ENDERMAN = ResourceLocation.fromNamespaceAndPath("royalvariations", "royal_enderman");

   @Inject(
      method = "onDamage(Lnet/neoforged/neoforge/event/entity/living/LivingIncomingDamageEvent;)V",
      at = @At("HEAD"),
      cancellable = true,
      require = 0,
      remap = false
   )
   private static void tensuraTno$skipUnequippedRaycast(LivingIncomingDamageEvent event, CallbackInfo ci) {
      Optional<Boolean> equipped = RelicsEquipmentLookup.isEquipped(event.getEntity(), SCALED_CLOAK);
      if (equipped.isPresent()) {
         if (!equipped.get()) {
            ci.cancel();
         }
      } else {
         ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
         if (ROYAL_ENDERMAN.equals(entityId)) {
            ci.cancel();
         }
      }
   }
}
