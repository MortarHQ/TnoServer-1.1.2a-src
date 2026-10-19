package org.crypticdev.ancient_artifacts.mixin.tensura.ability.battlewill.projectile;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.battlewill.projectile.MagicBulletArt;
import io.github.manasmods.tensura.config.ability.BattlewillConfig.MagicBullet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.ancient_artifacts.registry.ArtifactAttributesRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(MagicBulletArt.class)
public class MagicBulletArtMixin {
   @Shadow
   @Final
   private static MagicBullet CONFIG;

   @ModifyArgs(method = "onHeld", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putInt(Ljava/lang/String;I)V"))
   private void putPowerScale(Args args, ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (args.get(0) == "PowerScale") {
         CompoundTag tag = instance.getOrCreateTag();
         double max = instance.isMastered(entity) ? CONFIG.maxMultiplierMastered : CONFIG.maxMultiplier;
         args.set(1, (int)Math.min(tag.getInt("PowerScale") + 1.0 * entity.getAttributeValue(ArtifactAttributesRegistry.MAGIC_BULLET_CHARGE_SPEED), max * 10.0));
      }
   }
}
