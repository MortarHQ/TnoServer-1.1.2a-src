package io.github.manasmods.tensura_iaf.mixin;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import io.github.manasmods.tensura.race.RaceUtils;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RaceUtils.class)
public abstract class MixinRaceUtils {
   @Inject(at = @At("RETURN"), method = "isNonLiving(Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
   private static void isNonLiving(Entity entity, CallbackInfoReturnable<Boolean> cir) {
      if (!(Boolean)cir.getReturnValue()) {
         if (entity instanceof DragonBaseEntity dragon && (dragon.isModelDead() || dragon.getDeathStage() > 0)) {
            cir.setReturnValue(true);
         }
      }
   }
}
