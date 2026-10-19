package io.github.Memoires.mysticism.mixin.handler.client;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.handler.client.OverlayHandler;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OverlayHandler.class)
public abstract class MixinOverlayHandler {
   private static final ThreadLocal<LivingEntity> ANALYSIS_TARGET = new ThreadLocal<>();
   @Shadow
   private static LocalPlayer player;

   @Inject(method = "renderEntityAnalysis(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"))
   private static void captureTarget(LivingEntity target, CallbackInfo ci) {
      ANALYSIS_TARGET.set(target);
   }

   @Inject(method = "renderEntityAnalysis(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("RETURN"))
   private static void clearTarget(LivingEntity target, CallbackInfo ci) {
      ANALYSIS_TARGET.remove();
   }

   @ModifyVariable(method = "renderEntityAnalysis(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("STORE"), ordinal = 0)
   private static String corruptNameText(String original) {
      LivingEntity entity = ANALYSIS_TARGET.get();
      return entity != null && SkillUtils.isSkillToggled(entity, (ManasSkill)UniqueMysticismSkills.FOURTH_WALL.get()) ? "§7§kUnknown" : original;
   }

   @Redirect(
      method = "renderEntityAnalysis(Lnet/minecraft/world/entity/LivingEntity;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getHealth()F")
   )
   private static float corruptHealth(LivingEntity entity) {
      if (SkillUtils.isSkillToggled(entity, (ManasSkill)UniqueMysticismSkills.FOURTH_WALL.get())) {
         float realHealth = entity.getMaxHealth();
         return realHealth <= 1.0F ? 0.0F : entity.level().getRandom().nextFloat() * realHealth;
      } else {
         return entity.getHealth();
      }
   }

   @Redirect(
      method = "renderEntityAnalysis(Lnet/minecraft/world/entity/LivingEntity;)V",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/storage/ep/IExistence;getMagicule()D")
   )
   private static double corruptMagicules(IExistence existence) {
      LivingEntity entity = ANALYSIS_TARGET.get();
      if (entity != null && SkillUtils.isSkillToggled(entity, (ManasSkill)UniqueMysticismSkills.FOURTH_WALL.get())) {
         double realMax = existence.getMagicule();
         return realMax <= 1.0 ? 0.0 : entity.level().getRandom().nextDouble() * realMax;
      } else {
         return existence.getMagicule();
      }
   }

   @Redirect(
      method = "renderEntityAnalysis(Lnet/minecraft/world/entity/LivingEntity;)V",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/storage/ep/IExistence;getAura()D")
   )
   private static double corruptAura(IExistence existence) {
      LivingEntity entity = ANALYSIS_TARGET.get();
      if (entity != null && SkillUtils.isSkillToggled(entity, (ManasSkill)UniqueMysticismSkills.FOURTH_WALL.get())) {
         double realMax = existence.getAura();
         return realMax <= 1.0 ? 0.0 : entity.level().getRandom().nextDouble() * realMax;
      } else {
         return existence.getAura();
      }
   }

   @Redirect(
      method = "renderEntityAnalysis(Lnet/minecraft/world/entity/LivingEntity;)V",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/util/EnergyHelper;getMaxEP(Lnet/minecraft/world/entity/LivingEntity;)D")
   )
   private static double corruptEP(LivingEntity entity) {
      if (SkillUtils.isSkillToggled(entity, (ManasSkill)UniqueMysticismSkills.FOURTH_WALL.get())) {
         double realMax = EnergyHelper.getMaxEP(entity);
         return realMax <= 1.0 ? 0.0 : entity.level().getRandom().nextDouble() * realMax;
      } else {
         return EnergyHelper.getMaxEP(entity);
      }
   }

   @Inject(method = "getLightningBorder()Lnet/minecraft/world/effect/MobEffectInstance;", at = @At("TAIL"), cancellable = true)
   private static void mysticism$getLightningBorder(CallbackInfoReturnable<MobEffectInstance> cir) {
      if (cir.getReturnValue() == null) {
         if (player != null && !player.getActiveEffects().isEmpty()) {
            MobEffectInstance effectInstance = player.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.TAILED_BEAST_CLOAK));
            if (effectInstance != null) {
               cir.setReturnValue(effectInstance);
            }
         }
      }
   }
}
