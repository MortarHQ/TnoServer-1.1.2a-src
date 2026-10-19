package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import software.bernie.geckolib.animation.RawAnimation;

public class GroundSlamAbility extends PlayerAbility {
   private static final RawAnimation GROUND_POUND_LOOP_ANIM = RawAnimation.begin().thenLoop("ground_pound_loop");
   private static final RawAnimation GROUND_POUND_LAND_ANIM = RawAnimation.begin().thenPlay("ground_pound_land");

   public GroundSlamAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 2),
            new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 21)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      this.playAnimation(GROUND_POUND_LOOP_ANIM);
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
      }

      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         this.getUser().setDeltaMovement(0.0, -1.5, 0.0);
         if (this.getUser().onGround()) {
            this.nextSection();

            for (LivingEntity livingentity : this.getUser()
               .level()
               .getEntitiesOfClass(LivingEntity.class, this.getUser().getBoundingBox().inflate(5.2, 2.0, 5.2))) {
               livingentity.hurt(this.getUser().damageSources().mobAttack(this.getUser()), 10.0F);
            }

            EntityCameraShake.cameraShake(this.getUser().level(), this.getUser().position(), 45.0F, 0.09F, 20, 20);
            BlockState blockBeneath = this.getUser().level().getBlockState(this.getUser().blockPosition());
            if (this.getUser().level().isClientSide) {
               this.getUser().playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), 1.5F, 1.0F);

               for (int i = 0; i < 50; i++) {
                  this.getUser()
                     .level()
                     .addParticle(
                        new BlockParticleOption(ParticleTypes.BLOCK, blockBeneath),
                        this.getUser().getRandomX(5.8),
                        this.getUser().getBlockY() + 0.1F,
                        this.getUser().getRandomZ(5.8),
                        0.0,
                        0.38,
                        0.0
                     );
                  this.getUser()
                     .level()
                     .addParticle(ParticleTypes.POOF, this.getUser().getRandomX(5.0), this.getUser().getY(), this.getUser().getRandomZ(5.0), 0.0, 0.08, 0.0);
               }

               AdvancedParticleBase.spawnParticle(
                  this.getUser().level(),
                  ParticleHandler.RING2,
                  (float)this.getUser().getX(),
                  (float)this.getUser().getY() + 0.01F,
                  (float)this.getUser().getZ(),
                  0.0,
                  0.0,
                  0.0,
                  false,
                  0.0,
                  Math.PI / 2,
                  0.0,
                  0.0,
                  3.5,
                  0.83F,
                  1.0,
                  0.39F,
                  1.0,
                  1.0,
                  10.0,
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.8F, 0.0F), false
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(0.0F, 136.0F), false
                     )
                  }
               );
            }
         }
      }

      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
         this.getUser().setDeltaMovement(0.0, 0.0, 0.0);
      }
   }

   @Override
   public boolean canUse() {
      return this.getUser() instanceof Player && !this.getUser().getInventory().getSelected().isEmpty()
         ? false
         : EffectGeomancy.canUse(this.getUser()) && this.getUser().fallDistance > 2.0F && super.canUse();
   }

   @Override
   public void nextSection() {
      super.nextSection();
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
      }

      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
         this.playAnimation(GROUND_POUND_LAND_ANIM);
      }
   }

   @Override
   public void onRightClickEmpty(RightClickEmpty event) {
      super.onRightClickEmpty(event);
      if (!this.getUser().onGround() && this.getUser().isCrouching()) {
         AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.GROUND_SLAM_ABILITY);
      }
   }
}
