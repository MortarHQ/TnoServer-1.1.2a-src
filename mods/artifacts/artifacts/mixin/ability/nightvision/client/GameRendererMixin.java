package artifacts.mixin.ability.nightvision.client;

import artifacts.config.value.Value;
import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
   @ModifyReturnValue(method = "getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F", at = @At("RETURN"))
   private static float getNightVisionScale(float original, LivingEntity entity, float f) {
      MobEffectInstance effect = entity.getEffect(MobEffects.NIGHT_VISION);
      if (effect != null && effect.endsWithin(240)) {
         double scale = EquipmentHelper.<Value, Double>reduceComponents(
            ModDataComponents.REDUCED_NIGHT_VISION.get(), entity, 0.0, (prefix, stack, component) -> Math.max((Double)component.get(), prefix)
         );
         return scale == 0.0 ? original : Mth.lerp(Math.max(0.0F, effect.getDuration() - f - 220.0F) / 20.0F, (float)scale, original);
      } else {
         return original;
      }
   }
}
