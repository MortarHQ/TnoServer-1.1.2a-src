package org.crypticdev.ancient_artifacts.mixin.world.entity.player;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.crypticdev.ancient_artifacts.AncientArtifacts;
import org.crypticdev.ancient_artifacts.artifact.ArtifactEffects;
import org.crypticdev.ancient_artifacts.artifact.ArtifactRefinement;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class EntityMixin {
   @Unique
   private boolean ancient_artifacts$bonusAppliedThisTick = false;
   @Unique
   private float ancient_artifacts$calculatedBonusDamage = 0.0F;

   @Shadow
   public abstract int getArmorValue();

   @Inject(method = "hurt", at = @At("HEAD"))
   private void preHurtValidation(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
      this.ancient_artifacts$bonusAppliedThisTick = false;
      this.ancient_artifacts$calculatedBonusDamage = 0.0F;
      LivingEntity target = (LivingEntity)this;
      if (!target.level().isClientSide()) {
         if (source.getEntity() instanceof Player player && PlatformEquipment.hasArtifact(player, Artifacts.ShadowbindBracelet)) {
            ItemStack stack = PlatformEquipment.getArtifactStack(player, Artifacts.ShadowbindBracelet);
            Item item = stack.getItem();
            RefinementComponent refinement = ArtifactRefinement.get(stack);
            int refinementSteps = refinement != null ? Math.max(0, refinement.refinement() - 1) : 0;
            int maxRefinementSteps = refinement != null ? RefinementComponent.GRADES.getOrDefault(refinement.grade(), 0) : 0;
            refinementSteps = Math.min(refinementSteps, maxRefinementSteps);
            if (ancient_artifacts$canUseSilentStrike(player, target)
               && ArtifactEffects.EpUnlock(stack, AncientArtifacts.CONFIG.getGradeConfig(1).EffectUnlockEp.get(0))
               && !player.getCooldowns().isOnCooldown(item)) {
               float multi = 0.5F + refinementSteps * 0.05F;
               this.ancient_artifacts$calculatedBonusDamage = amount * multi;
               this.ancient_artifacts$bonusAppliedThisTick = true;
               player.getCooldowns().addCooldown(item, 600);
            }
         }
      }
   }

   @ModifyVariable(method = "hurt", at = @At("HEAD"), argsOnly = true)
   private float applyCachedBonusMath(float amount, DamageSource source) {
      if (this.ancient_artifacts$bonusAppliedThisTick) {
         this.ancient_artifacts$bonusAppliedThisTick = false;
         return amount + this.ancient_artifacts$calculatedBonusDamage;
      } else {
         return amount;
      }
   }

   @Unique
   private static boolean ancient_artifacts$canUseSilentStrike(Player attacker, LivingEntity target) {
      if (target instanceof Mob mob && mob.getTarget() != attacker) {
         return true;
      } else {
         Vec3 targetLook = target.getViewVector(1.0F).normalize();
         Vec3 toAttacker = attacker.position().subtract(target.position()).normalize();
         Vec3 targetLook2D = new Vec3(targetLook.x, 0.0, targetLook.z).normalize();
         Vec3 toAttacker2D = new Vec3(toAttacker.x, 0.0, toAttacker.z).normalize();
         return toAttacker2D.dot(targetLook2D) < -0.5;
      }
   }
}
