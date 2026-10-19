package artifacts.mixin.ability.damageimmunity;

import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
   @ModifyReturnValue(method = "isInvulnerableTo(Lnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("RETURN"))
   public boolean isInvulnerableTo(boolean original, DamageSource damageSource) {
      return !original
            && this instanceof LivingEntity entity
            && EquipmentHelper.hasAbilityActive(
               ModDataComponents.DAMAGE_IMMUNITY.get(), entity, true, ability -> ability.condition().test(entity) && damageSource.is(ability.tag())
            )
         ? true
         : original;
   }
}
