package com.mortal_cultivation.ability.skill.extra;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import com.mortal_cultivation.entity.TenThousandSoulBannerEntity;
import com.mortal_cultivation.registry.CultivationEntities;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Random;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class HundredGhostsSurgeSkill extends LegacySkill {
   private final double skillCastCost = 100000.0;
   private final double learnCost = 5.0;
   private static final Random random = new Random();

   public HundredGhostsSurgeSkill() {
      super(SkillType.EXTRA);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/extra/hundred_ghosts_surge.png");
   }

   @Override
   public boolean meetEPRequirement(Player entity, double curEP) {
      return false;
   }

   @Override
   public double learningCost() {
      return 5.0;
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      int mode = this.getLegacyMode(instance);
      return mode == 2 ? 50000.0 : 100000.0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return false;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   @Override
   public int modes() {
      return 2;
   }

   @Override
   public int nextMode(LivingEntity entity, TensuraSkillInstance instance, boolean reverse) {
      return this.getLegacyMode(instance) == 1 ? 2 : 1;
   }

   @Override
   public Component getModeName(int mode) {
      return switch (mode) {
         case 1 -> Component.translatable("mortal_cultivation.skill.hundred_ghosts_surge.mode.summon");
         case 2 -> Component.translatable("mortal_cultivation.skill.hundred_ghosts_surge.mode.collect");
         default -> Component.empty();
      };
   }

   public static void resetPlayerCooldowns(Player player) {
      SkillAPI.getSkillsFrom(player).getLearnedSkills().forEach(inst -> {
         if (inst.getSkill() instanceof HundredGhostsSurgeSkill) {
            inst.setCoolDown(0, 0);
            inst.setCoolDown(0, 1);
            inst.markDirty();
         }
      });
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      if (!(EnergyHelper.isOutOfMagiculeConsuming(entity, this.magiculeCost(entity, instance)) > 0.0)) {
         if (!entity.level().isClientSide) {
            int mode = this.getLegacyMode(instance);
            if (mode == 1) {
               this.handleSummonMode(entity);
               instance.setCoolDown(2400, 0);
            } else if (mode == 2) {
               boolean success = this.handleCollectMode(entity);
               if (success) {
                  instance.setCoolDown(1200, 1);
               }
            }
         }
      }
   }

   private void handleSummonMode(LivingEntity entity) {
      TenThousandSoulBannerEntity ownedBanner = this.getOwnedBanner(entity);
      if (ownedBanner != null && !ownedBanner.isRemoved()) {
         this.recallBanner(ownedBanner);
      } else {
         this.summonBanner(entity);
      }
   }

   private boolean handleCollectMode(LivingEntity entity) {
      if (!(entity instanceof Player player)) {
         return false;
      } else {
         TenThousandSoulBannerEntity targetBanner = this.getOwnedBanner(player);
         if (targetBanner == null) {
            targetBanner = this.getTargetBanner(player);
         }

         if (targetBanner != null) {
            int souls = targetBanner.getSoulCount();
            if (souls <= 0) {
               player.sendSystemMessage(Component.translatable("mortal_cultivation.skill.hundred_ghosts_surge.no_souls"));
               return false;
            }

            IExistence existence = TensuraStorages.getExistenceFrom(player);
            int currentSoulPoints = existence.getSoulPoints();
            existence.setSoulPoints(currentSoulPoints + souls * 1000);
            existence.markDirty();
            Vec3 bannerPos = targetBanner.position();
            player.level().playSound(null, bannerPos.x, bannerPos.y, bannerPos.z, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            if (player.level() instanceof ServerLevel) {
               ServerLevel serverLevel = (ServerLevel)player.level();

               for (int i = 0; i < 50; i++) {
                  double offsetX = (random.nextDouble() - 0.5) * 2.0;
                  double offsetY = random.nextDouble() * 3.0;
                  double offsetZ = (random.nextDouble() - 0.5) * 2.0;
                  serverLevel.sendParticles(
                     ParticleTypes.LARGE_SMOKE, bannerPos.x + offsetX, bannerPos.y + offsetY, bannerPos.z + offsetZ, 1, 0.0, 0.1, 0.0, 0.02
                  );
               }

               for (int i = 0; i < 30; i++) {
                  double offsetX = (random.nextDouble() - 0.5) * 1.5;
                  double offsetY = random.nextDouble() * 2.0;
                  double offsetZ = (random.nextDouble() - 0.5) * 1.5;
                  serverLevel.sendParticles(ParticleTypes.SOUL, bannerPos.x + offsetX, bannerPos.y + offsetY, bannerPos.z + offsetZ, 1, 0.0, 0.2, 0.0, 0.05);
               }
            }

            targetBanner.discard();
            player.sendSystemMessage(Component.translatable("mortal_cultivation.skill.hundred_ghosts_surge.souls_collected", new Object[]{souls}));
            return true;
         } else {
            player.sendSystemMessage(Component.translatable("mortal_cultivation.skill.hundred_ghosts_surge.no_banner"));
            return false;
         }
      }
   }

   private TenThousandSoulBannerEntity getTargetBanner(Player player) {
      Vec3 eyePos = player.getEyePosition();
      Vec3 lookVec = player.getLookAngle();
      return player.level()
         .getEntitiesOfClass(TenThousandSoulBannerEntity.class, player.getBoundingBox().expandTowards(lookVec.scale(12.0)).inflate(3.0))
         .stream()
         .filter(banner -> {
            Vec3 bannerPos = banner.position();
            double distance = eyePos.distanceTo(bannerPos);
            return distance <= 12.0;
         })
         .min((b1, b2) -> Double.compare(eyePos.distanceTo(b1.position()), eyePos.distanceTo(b2.position())))
         .orElse(null);
   }

   private TenThousandSoulBannerEntity getOwnedBanner(LivingEntity entity) {
      return entity.level()
         .getEntitiesOfClass(TenThousandSoulBannerEntity.class, entity.getBoundingBox().inflate(64.0), banner -> banner.isOwnedBy(entity))
         .stream()
         .min((b1, b2) -> Double.compare(entity.distanceToSqr(b1), entity.distanceToSqr(b2)))
         .orElse(null);
   }

   private void summonBanner(LivingEntity entity) {
      try {
         TenThousandSoulBannerEntity existingBanner = this.getOwnedBanner(entity);
         if (existingBanner != null && !existingBanner.isRemoved()) {
            existingBanner.discard();
         }

         Vec3 playerPos = entity.position();
         Vec3 bannerPos = new Vec3(playerPos.x, playerPos.y, playerPos.z);
         TenThousandSoulBannerEntity banner = new TenThousandSoulBannerEntity((EntityType<?>)CultivationEntities.TEN_THOUSAND_SOUL_BANNER.get(), entity.level());
         banner.setOwnerUuid(entity.getUUID());
         float playerYRot = entity.getYRot();
         float yRot = playerYRot + 90.0F;

         while (playerYRot < 0.0F) {
            playerYRot += 360.0F;
         }

         while (playerYRot >= 360.0F) {
            playerYRot -= 360.0F;
         }

         boolean isNorth = playerYRot >= 315.0F || playerYRot <= 45.0F;
         boolean isSouth = playerYRot >= 135.0F && playerYRot <= 225.0F;
         if (isNorth || isSouth) {
            yRot += 180.0F;
         }

         while (yRot < 0.0F) {
            yRot += 360.0F;
         }

         while (yRot >= 360.0F) {
            yRot -= 360.0F;
         }

         banner.absMoveTo(bannerPos.x, bannerPos.y, bannerPos.z, yRot, 0.0F);
         banner.setYRot(yRot);
         banner.setYBodyRot(yRot);
         banner.setYHeadRot(yRot);
         banner.yRotO = yRot;
         entity.level().addFreshEntity(banner);
         entity.level().playSound(null, bannerPos.x, bannerPos.y, bannerPos.z, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 1.0F, 1.0F);
      } catch (Exception var10) {
      }
   }

   private void recallBanner(TenThousandSoulBannerEntity banner) {
      try {
         if (banner != null && !banner.isRemoved()) {
            Vec3 bannerPos = banner.position();
            banner.level().playSound(null, bannerPos.x, bannerPos.y, bannerPos.z, SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0F, 1.0F);
            banner.discard();
         }
      } catch (Exception var3) {
      }
   }
}
