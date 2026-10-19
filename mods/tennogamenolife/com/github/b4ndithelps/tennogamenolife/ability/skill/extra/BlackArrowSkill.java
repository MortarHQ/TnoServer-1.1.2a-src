package com.github.b4ndithelps.tennogamenolife.ability.skill.extra;

import com.github.b4ndithelps.tennogamenolife.entity.projectile.BlackRodProjectile;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.IFlugelRace;
import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class BlackArrowSkill extends Skill {
   private static final double UNLOCK_MAGICULE = 10000.0;
   private static final double CAST_COST = 2000.0;
   private static final int RATE_WINDOW_TICKS = 100;
   private static final int MAX_CASTS_PER_WINDOW = 5;
   private static final int OVERUSE_COOLDOWN_SECONDS = 5;
   private static final float PROJECTILE_SPEED = 4.0F;
   private static final String WINDOW_START = "BlackArrowWindowStart";
   private static final String WINDOW_CASTS = "BlackArrowWindowCasts";

   public BlackArrowSkill() {
      super(SkillType.EXTRA);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/extra/black_arrow.png");
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return meetsUnlockRequirement(entity);
   }

   public static boolean meetsUnlockRequirement(Player player) {
      boolean isFlugel = RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace() instanceof IFlugelRace).orElse(false);
      return isFlugel && EnergyHelper.getMaxMagicule(player) >= 10000.0;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 2000.0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel serverLevel) {
         CompoundTag var14 = instance.getOrCreateTag();
         long gameTime = serverLevel.getGameTime();
         long windowStart = var14.getLong("BlackArrowWindowStart");
         int casts = var14.getInt("BlackArrowWindowCasts");
         if (windowStart <= 0L || gameTime - windowStart >= 100L) {
            windowStart = gameTime;
            casts = 0;
         }

         if (casts >= 5) {
            instance.setCoolDown(5, mode);
            var14.putLong("BlackArrowWindowStart", gameTime);
            var14.putInt("BlackArrowWindowCasts", 0);
            instance.markDirty();
         } else if (!this.isOutOfEnergy(entity, instance, mode, 1.0F)) {
            Vec3 look = entity.getLookAngle().normalize();
            BlackRodProjectile projectile = new BlackRodProjectile(
               (EntityType<? extends BlackRodProjectile>)TenreincarnationEntities.BLACK_ROD.get(), serverLevel
            );
            projectile.setOwner(entity);
            projectile.setSkillInstance(instance);
            projectile.setPos(entity.getEyePosition().add(look.scale(2.25)));
            projectile.shoot(look.x, look.y, look.z, 4.0F, 0.0F);
            projectile.alignRotationToMovement();
            serverLevel.addFreshEntity(projectile);
            var14.putLong("BlackArrowWindowStart", windowStart);
            var14.putInt("BlackArrowWindowCasts", casts + 1);
            instance.addMasteryPoint(entity);
            instance.markDirty();
            serverLevel.playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 0.7F);
         }
      }
   }
}
