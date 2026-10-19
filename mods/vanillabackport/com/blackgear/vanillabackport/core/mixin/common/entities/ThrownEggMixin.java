package com.blackgear.vanillabackport.core.mixin.common.entities;

import com.blackgear.vanillabackport.common.api.variant.VariantDataHolder;
import com.blackgear.vanillabackport.common.api.variant.VariantUtils;
import com.blackgear.vanillabackport.common.level.entities.animal.ChickenVariant;
import com.blackgear.vanillabackport.common.registries.ModDataComponents;
import com.blackgear.vanillabackport.core.registries.ModBuiltinRegistries;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.Optional;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEgg.class)
public abstract class ThrownEggMixin extends ThrowableItemProjectile {
   public ThrownEggMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
   }

   @Inject(
      method = "onHit(Lnet/minecraft/world/phys/HitResult;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Chicken;moveTo(DDDFF)V", shift = Shift.AFTER)
   )
   private void setChickenVariant(HitResult result, CallbackInfo ci, @Local Chicken chicken) {
      Optional.ofNullable((ResourceKey)this.getItem().get(ModDataComponents.CHICKEN_VARIANT.get()))
         .map(key -> VariantUtils.getDefault(ModBuiltinRegistries.CHICKEN_VARIANTS, (ResourceKey<? extends ChickenVariant>)key))
         .ifPresent(variant -> VariantDataHolder.getHolder(chicken).setVariantData(variant));
   }
}
