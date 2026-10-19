package artifacts.mixin.attribute.flatulence;

import artifacts.registry.ModAttributes;
import artifacts.registry.ModGameEvents;
import artifacts.registry.ModSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
   @Shadow
   public abstract boolean isShiftKeyDown();

   @Inject(method = "setShiftKeyDown(Z)V", at = @At("HEAD"))
   private void setShiftKeyDown(boolean isDown, CallbackInfo ci) {
      if (isDown && !this.isShiftKeyDown()) {
         Entity chance = (Entity)this;
         if (chance instanceof LivingEntity entity && !entity.level().isClientSide()) {
            double chancex = entity.getAttributeValue(ModAttributes.FLATULENCE);
            if (entity.getRandom().nextFloat() < chancex) {
               entity.gameEvent(ModGameEvents.FART);
               entity.level()
                  .playSound(null, entity, (SoundEvent)ModSoundEvents.FART.value(), SoundSource.PLAYERS, 1.0F, 0.9F + entity.getRandom().nextFloat() * 0.2F);
            }
         }
      }
   }
}
