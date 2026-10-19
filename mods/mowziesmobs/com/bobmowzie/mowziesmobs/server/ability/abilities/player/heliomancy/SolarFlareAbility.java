package com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy;

import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import software.bernie.geckolib.animation.RawAnimation;

public class SolarFlareAbility extends HeliomancyAbilityBase {
   private static final RawAnimation SOLAR_FLARE_ANIM = RawAnimation.begin().thenPlay("solar_flare");

   public SolarFlareAbility(AbilityType<Player, SolarFlareAbility> abilityType, Player user) {
      super(abilityType, user, EntityUmvuthi.SolarFlareAbility.SECTION_TRACK);
   }

   @Override
   public void start() {
      super.start();
      this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_BURST.get(), 1.7F, 1.5F);
      this.playAnimation(SOLAR_FLARE_ANIM);
      if (this.getLevel().isClientSide) {
         this.heldItemMainHandVisualOverride = ItemStack.EMPTY;
         this.heldItemOffHandVisualOverride = ItemStack.EMPTY;
         this.firstPersonOffHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
         this.firstPersonMainHandDisplay = PlayerAbility.HandDisplay.FORCE_RENDER;
      }
   }

   @Override
   public boolean canUse() {
      return this.getUser() != null && this.getUser().getInventory().getSelected().isEmpty()
         ? this.getUser().hasEffect(EffectHandler.SUNS_BLESSING) && super.canUse()
         : false;
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getTicksInUse() < 16) {
         this.getUser().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 2, false, false));
      }

      if (this.getTicksInUse() <= 6 && this.getLevel().isClientSide) {
         int particleCount = 8;

         while (--particleCount != 0) {
            double radius = 2.0;
            double yaw = this.rand.nextFloat() * 2.0F * Math.PI;
            double pitch = this.rand.nextFloat() * 2.0F * Math.PI;
            double ox = radius * Math.sin(yaw) * Math.sin(pitch);
            double oy = radius * Math.cos(pitch);
            double oz = radius * Math.cos(yaw) * Math.sin(pitch);
            this.getLevel()
               .addParticle(
                  ParticleOrb.Data.create(
                     (float)this.getUser().getX(), (float)this.getUser().getY() + this.getUser().getBbHeight() / 2.0F, (float)this.getUser().getZ(), 6.0F
                  ),
                  this.getUser().getX() + ox,
                  this.getUser().getY() + this.getUser().getBbHeight() / 2.0F + oy,
                  this.getUser().getZ() + oz,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      if (this.getTicksInUse() == 10 && this.getLevel().isClientSide) {
         for (int i = 0; i < 30; i++) {
            float velocity = 0.25F;
            float yaw = i * (float) (Math.PI / 15);
            float vy = this.rand.nextFloat() * 0.1F - 0.05F;
            float vx = 0.25F * Mth.cos(yaw);
            float vz = 0.25F * Mth.sin(yaw);
            this.getLevel().addParticle(ParticleTypes.FLAME, this.getUser().getX(), this.getUser().getY() + 1.0, this.getUser().getZ(), vx, vy, vz);
         }
      }
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         Player user = this.getUser();
         float radius = 3.2F;

         for (LivingEntity aHit : this.getEntityLivingBaseNearby(user, radius, radius, radius, radius)) {
            if (aHit != this.getUser()) {
               float damage = 2.0F;
               float knockback = 3.0F;
               damage = (float)(damage * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get());
               if (aHit.hurt(user.damageSources().playerAttack(user), damage) && knockback > 0.0F) {
                  Vec3 vec3 = aHit.position().subtract(user.position()).normalize().scale(knockback * 0.6);
                  if (vec3.lengthSqr() > 0.0) {
                     aHit.push(vec3.x, 0.1, vec3.z);
                  }
               }
            }
         }
      }
   }

   @Override
   public void onLeftClickEmpty(LeftClickEmpty event) {
      super.onLeftClickEmpty(event);
      if (event.getEntity() == this.getUser() && event.getEntity().isShiftKeyDown()) {
         AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SOLAR_FLARE_ABILITY);
      }
   }

   @Override
   public void onLeftClickEntity(AttackEntityEvent event) {
      super.onLeftClickEntity(event);
      if (event.getEntity() == this.getUser() && event.getEntity().isShiftKeyDown()) {
         AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SOLAR_FLARE_ABILITY);
      }
   }
}
