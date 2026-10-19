package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.item.ExMachinasWeaponItem;
import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import com.github.b4ndithelps.tennogamenolife.registry.sound.TenreincarnationSounds;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.beam.SolarBeamProjectile;
import io.github.manasmods.tensura.entity.magic.field.MagicExplosion;
import io.github.manasmods.tensura.storage.effect.EffectStorage;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class ReisenUltimateSkill extends Skill {
   private final double skillCastCost = 100000.0;
   private final double learnCost = 16.0;
   private static final double TARGET_RANGE = 15.0;
   private static final int SKILL_DURATION = 600;
   private static final int COOLDOWN = 5000;
   private static final Random RANDOM = new Random();
   private static final int DRAGON_ROAR_CHARGE_TIME = 600;
   private static final int DRAGON_ROAR_COOLDOWN = 1800;
   private static final float DRAGON_ROAR_EFFECT_RADIUS = 84.5F;
   private static final float DRAGON_ROAR_TERRAIN_RADIUS = 32.5F;
   private static final float DRAGON_ROAR_DAMAGE = 1300.0F;
   private static final float DRAGON_ROAR_VFX_RADIUS = 80.0F;
   private static final double DRAGON_ROAR_COST = 700000.0;
   private static final float SMALL_EXPLOSION_RADIUS = 5.0F;
   private static final float LARGE_EXPLOSION_RADIUS = 256.0F;
   private static final float EXPLOSION_DAMAGE = 500.0F;
   private static final Map<Player, TensuraSkillInstance> COPIED_SKILLS = new HashMap<>();

   public ReisenUltimateSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/ultimate/reisen.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 1 ? 700000.0 : 100000.0;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return mode == 1 ? "reisen.dragon_roar" : "reisen.copy";
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 16.0;
   }

   private LivingEntity getTargetEntity(LivingEntity sourceEntity, double maxDistance) {
      if (sourceEntity.level().isClientSide) {
         return null;
      }

      Vec3 eyePos = sourceEntity.getEyePosition();
      Vec3 lookVec = sourceEntity.getLookAngle();
      Vec3 targetPos = eyePos.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance);
      Predicate<Entity> filter = entity -> entity instanceof LivingEntity && entity != sourceEntity && !entity.isSpectator();
      EntityHitResult entityHit = this.getEntityHitResult(
         sourceEntity,
         eyePos,
         targetPos,
         new AABB(
            eyePos.x - maxDistance, eyePos.y - maxDistance, eyePos.z - maxDistance, eyePos.x + maxDistance, eyePos.y + maxDistance, eyePos.z + maxDistance
         ),
         filter,
         maxDistance * maxDistance
      );
      return entityHit != null && entityHit.getEntity() instanceof LivingEntity ? (LivingEntity)entityHit.getEntity() : null;
   }

   private EntityHitResult getEntityHitResult(Entity source, Vec3 startPos, Vec3 endPos, AABB boundingBox, Predicate<Entity> filter, double distanceSq) {
      double closestDistSq = distanceSq;
      Entity closestEntity = null;
      Vec3 closestHitVec = null;

      for (Entity entity : source.level().getEntities(source, boundingBox, filter)) {
         AABB entityBox = entity.getBoundingBox().inflate(0.3);
         Optional<Vec3> hitVector = entityBox.clip(startPos, endPos);
         if (hitVector.isPresent()) {
            double d = startPos.distanceToSqr(hitVector.get());
            if (d < closestDistSq) {
               closestEntity = entity;
               closestHitVec = hitVector.get();
               closestDistSq = d;
            }
         }
      }

      return closestEntity != null ? new EntityHitResult(closestEntity, closestHitVec) : null;
   }

   private void removePreviousCopiedSkill(Player player) {
      TensuraSkillInstance previousSkill = COPIED_SKILLS.get(player);
      if (previousSkill != null) {
         SkillAPI.getSkillsFrom(player).forgetSkill(previousSkill.getSkill());
         COPIED_SKILLS.remove(player);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!DestructiveSkillSafety.blockIfDisabled(entity, "tennogamenolife.skill.reisen")) {
         if (entity instanceof Player player) {
            if (mode == 1 && !(player.getMainHandItem().getItem() instanceof ExMachinasWeaponItem)) {
               player.sendSystemMessage(
                  Component.translatable("tennogamenolife.skill.reisen.dragon_roar.need_weapon").withStyle(Style.EMPTY.withColor(ChatFormatting.RED))
               );
            } else if (!this.isOutOfEnergy(entity, instance, mode, 1.0F)) {
               if (mode == 1) {
                  this.handleDragonRoarMode(instance, player);
               } else {
                  this.handleCopySkillMode(instance, player);
               }
            }
         }
      }
   }

   private void handleDragonRoarMode(ManasSkillInstance instance, Player player) {
      Vec3 targetPos = this.getTargetPosition(player, 64.0);
      CompoundTag tag = instance.getOrCreateTag();
      tag.putDouble("dragonRoarX", targetPos.x);
      tag.putDouble("dragonRoarY", targetPos.y);
      tag.putDouble("dragonRoarZ", targetPos.z);
      tag.putInt("chargeTime", 0);
      tag.putBoolean("DragonRoarLocked", true);
      instance.markDirty();
   }

   private void handleCopySkillMode(ManasSkillInstance instance, Player player) {
      LivingEntity target = this.getTargetEntity(player, 15.0);
      if (target != null) {
         this.removePreviousCopiedSkill(player);
         if (target instanceof Player targetPlayer) {
            List<ManasSkill> ultimateSkills = new ArrayList<>();
            SkillAPI.getSkillsFrom(targetPlayer).getLearnedSkills().forEach(skillInstance -> {
               ManasSkill skill = skillInstance.getSkill();
               if (skill instanceof Skill && ((Skill)skill).getType() == SkillType.ULTIMATE) {
                  ultimateSkills.add(skill);
               }
            });
            if (!ultimateSkills.isEmpty()) {
               ManasSkill selectedSkill = ultimateSkills.get(RANDOM.nextInt(ultimateSkills.size()));
               TensuraSkillInstance tempSkill = new TensuraSkillInstance(selectedSkill);
               tempSkill.setRemoveTime(600);
               if (SkillHelper.learnSkill(player, tempSkill)) {
                  COPIED_SKILLS.put(player, tempSkill);
                  player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
                  instance.setCoolDown(5000, 0);
                  player.sendSystemMessage(
                     Component.translatable("tennogamenolife.skill.reisen.success_copy", new Object[]{selectedSkill.getName()})
                        .withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))
                  );
                  player.sendSystemMessage(
                     Component.translatable("tennogamenolife.skill.reisen.success_copy_duration", new Object[]{target.getName().getString(), 600})
                        .withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))
                  );
               } else {
                  player.sendSystemMessage(
                     Component.translatable("tennogamenolife.skill.reisen.cannot_copy").withStyle(Style.EMPTY.withColor(ChatFormatting.RED))
                  );
               }
            } else {
               player.sendSystemMessage(
                  Component.translatable("tennogamenolife.skill.reisen.no_ultimate_skill").withStyle(Style.EMPTY.withColor(ChatFormatting.RED))
               );
            }
         } else {
            player.sendSystemMessage(
               Component.translatable("tennogamenolife.skill.reisen.only_player_skill").withStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            );
         }
      } else {
         player.sendSystemMessage(Component.translatable("tennogamenolife.skill.reisen.no_target").withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
      }
   }

   private Vec3 getTargetPosition(Player player, double maxDistance) {
      Vec3 eyePos = player.getEyePosition();
      Vec3 lookVec = player.getLookAngle();
      Vec3 targetPos = eyePos.add(lookVec.scale(maxDistance));
      ClipContext context = new ClipContext(eyePos, targetPos, Block.OUTLINE, Fluid.NONE, player);
      BlockHitResult result = player.level().clip(context);
      return result.getType() != Type.MISS ? result.getLocation() : targetPos;
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode != 1) {
         return false;
      } else if (!(entity instanceof Player player)) {
         return false;
      } else {
         ItemStack mainHand = player.getMainHandItem();
         if (!(mainHand.getItem() instanceof ExMachinasWeaponItem)) {
            CompoundTag tag = instance.getOrCreateTag();
            if (!player.level().isClientSide && player.level().getEntity(tag.getInt("DragonRoarCircleID")) instanceof DragonRoarCircle circle) {
               circle.setLife(10);
            }

            this.resetDragonRoarData(instance);
            return false;
         } else {
            CompoundTag tag = instance.getOrCreateTag();
            Vec3 targetPos = new Vec3(tag.getDouble("dragonRoarX"), tag.getDouble("dragonRoarY"), tag.getDouble("dragonRoarZ"));
            if (!tag.getBoolean("DragonRoarLocked")) {
               return false;
            }

            DragonRoarChargeLock.apply(player, targetPos);
            int chargeTime = tag.getInt("chargeTime");
            tag.putInt("chargeTime", chargeTime + 1);
            instance.markDirty();
            float progress = Math.min(1.0F, chargeTime / 600.0F);
            player.displayClientMessage(
               Component.translatable("tennogamenolife.skill.reisen.dragon_roar.charging", new Object[]{String.format("%.0f%%", progress * 100.0F)})
                  .withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
               true
            );
            if (!player.level().isClientSide) {
               this.createDragonRoarCircle(player, targetPos, instance, mode, chargeTime);
            }

            if (chargeTime % 20 == 0) {
               player.level()
                  .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_AMBIENT, SoundSource.PLAYERS, 0.5F, 1.0F + progress * 0.5F);
            }

            return true;
         }
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 1) {
         if (entity instanceof Player player) {
            CompoundTag tag = instance.getOrCreateTag();
            int chargeTime = tag.getInt("chargeTime");
            if (chargeTime < 600) {
               if (!player.level().isClientSide && player.level().getEntity(tag.getInt("DragonRoarCircleID")) instanceof DragonRoarCircle circle) {
                  circle.setLife(10);
               }

               this.resetDragonRoarData(instance);
            } else {
               Vec3 targetPos = new Vec3(tag.getDouble("dragonRoarX"), tag.getDouble("dragonRoarY"), tag.getDouble("dragonRoarZ"));
               if (!player.level().isClientSide) {
                  if (player.level().getEntity(tag.getInt("DragonRoarCircleID")) instanceof DragonRoarCircle circle) {
                     circle.discard();
                  }

                  player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 3.0F, 1.6F);
                  this.startDragonRoarImpactSequence(player, targetPos, instance, mode);
               }

               instance.setCoolDown(1800, mode);
               this.resetDragonRoarData(instance);
            }
         }
      }
   }

   private void createDragonRoarCircle(Player player, Vec3 targetPos, ManasSkillInstance instance, int mode, int chargeTime) {
      CompoundTag tag = instance.getOrCreateTag();
      Vec3 lookVec = player.getLookAngle();
      Vec3 forwardVec = lookVec.scale(1.5);
      Vec3 circlePos = player.position().add(0.0, 1.6, 0.0).add(forwardVec.x, 0.0, forwardVec.z);
      if (chargeTime == 1) {
         DragonRoarCircle circle = new DragonRoarCircle((EntityType<?>)TenreincarnationEntities.DRAGON_ROAR_CIRCLE.get(), player.level());
         circle.setPos(circlePos);
         circle.setSize(8.0F);
         circle.setLife(620);
         circle.setYRot(player.getYHeadRot());
         player.level().addFreshEntity(circle);
         tag.putInt("DragonRoarCircleID", circle.getId());
         instance.markDirty();
      } else if (player.level().getEntity(tag.getInt("DragonRoarCircleID")) instanceof DragonRoarCircle circle) {
         circle.increaseLife(1);
         circle.setPos(circlePos);
         circle.setYRot(player.getYHeadRot());
      }
   }

   private void startDragonRoarImpactSequence(Player player, Vec3 targetPos, ManasSkillInstance instance, int mode) {
      if (player.level() instanceof ServerLevel serverLevel) {
         DragonRoarStrikeSequence.startImpactSequence(serverLevel, targetPos, () -> this.detonateDragonRoar(serverLevel, player, targetPos, instance, mode));
      }
   }

   void detonateDragonRoar(ServerLevel level, Player caster, Vec3 center, ManasSkillInstance instance, int mode) {
      level.playSound(null, center.x, center.y, center.z, (SoundEvent)TenreincarnationSounds.DRAGON_ROAR_EXPLOSION.get(), SoundSource.PLAYERS, 10.0F, 0.85F);
      SkyStrikeVfx.spawnExplosionExpansion(level, center, 80.0F);

      try {
         MagicExplosion visualExplosion = new MagicExplosion(level, caster);
         visualExplosion.setPos(center);
         visualExplosion.setDamage(0.0F);
         visualExplosion.setSecondaryDamage(0.0F);
         visualExplosion.setSize(80.0F);
         visualExplosion.setVisualOnly(true);
         visualExplosion.setSkill(caster, instance, this, mode, 1.0F);
         level.addFreshEntity(visualExplosion);
      } catch (Exception ignored) {
         level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, center.x, center.y, center.z, 8, 4.0, 2.0, 4.0, 0.0);
      }

      this.damageDragonRoarTargets(level, caster, center, instance, mode);
      if (!DestructiveSkillSafety.shouldPreventTerrainDamage()) {
         float gameRuleLimit = level.getGameRules().getInt(TensuraGameRules.MAXIMUM_MAGIC_EXPLOSION);
         float terrainRadius = Math.min(32.5F, gameRuleLimit * 0.5F);
         TengekiProgressiveDestruction.start(level, center, terrainRadius, 84.5F, caster, instance);
      }
   }

   private void damageDragonRoarTargets(ServerLevel level, Player caster, Vec3 center, ManasSkillInstance instance, int mode) {
      double radiusSquared = 7140.25;
      DamageSource source = this.createSource(instance, caster, TensuraDamageTypes.MAGIC_GENERIC, mode);
      TensuraDamageSource tensuraSource = (TensuraDamageSource)source;
      tensuraSource.tensura$setDodgeBypass();
      tensuraSource.tensura$setBarrierBypassLevel(Float.MAX_VALUE);

      for (LivingEntity target : level.getEntitiesOfClass(
         LivingEntity.class,
         new AABB(center, center).inflate(84.5),
         targetx -> targetx != caster && targetx.isAlive() && targetx.getBoundingBox().distanceToSqr(center) <= radiusSquared
      )) {
         target.invulnerableTime = 0;
         target.hurt(source, 1300.0F);
      }
   }

   private void createDragonRoarBeam(Player player, Vec3 targetPos, ManasSkillInstance instance, int mode) {
      SolarBeamProjectile beam = new SolarBeamProjectile(player.level(), player);
      Vec3 eyePos = player.getEyePosition();
      beam.setPos(eyePos.x, eyePos.y, eyePos.z);
      double distance = eyePos.distanceTo(targetPos);
      beam.setOwner(player);
      beam.setFollowingOwner(false);
      beam.setLife(40);
      beam.setDamage(0.0F);
      beam.setSecondaryDamage(0.0F);
      beam.setSize(6.0F);
      beam.setRange((float)distance);
      beam.setExplosionRadius(0.0F);
      beam.setTargetPos(targetPos.x, targetPos.y, targetPos.z);
      beam.setSkill(player, instance, this, mode, 1.0F);
      beam.setMode(mode);
      beam.updateAngle();
      player.level().addFreshEntity(beam);
      CompoundTag tag = instance.getOrCreateTag();
      tag.putInt("DragonRoarBeamID", beam.getId());
      instance.markDirty();
   }

   private void triggerDragonRoarExplosion(Player player, Vec3 targetPos, ManasSkillInstance instance, int currentMode) {
      boolean preventTerrainDamage = DestructiveSkillSafety.shouldPreventTerrainDamage();
      player.level()
         .getServer()
         .tell(
            new TickTask(
               40,
               () -> {
                  player.level()
                     .explode(player, targetPos.x, targetPos.y, targetPos.z, 5.0F, preventTerrainDamage ? ExplosionInteraction.NONE : ExplosionInteraction.TNT);
                  player.level().playSound(null, targetPos.x, targetPos.y, targetPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 2.0F, 1.0F);
                  CompoundTag tag = instance.getOrCreateTag();
                  Entity beamEntity = player.level().getEntity(tag.getInt("DragonRoarBeamID"));
                  if (beamEntity != null) {
                     beamEntity.discard();
                  }

                  tag.putInt("DragonRoarBeamID", 0);
                  instance.markDirty();
                  this.createLightManipulationBeam(player, targetPos, instance, currentMode);
                  player.level().getServer().tell(new TickTask(100, () -> this.triggerLargeExplosionWithWaves(player, targetPos, instance, currentMode)));
               }
            )
         );
   }

   private void createLightManipulationBeam(Player player, Vec3 targetPos, ManasSkillInstance instance, int mode) {
      Vec3 playerPos = player.position().add(0.0, player.getEyeHeight(), 0.0);
      SolarBeamProjectile lightBeam = new SolarBeamProjectile(player.level(), player);
      lightBeam.setPos(playerPos.x, playerPos.y, playerPos.z);
      double distance = playerPos.distanceTo(targetPos);
      lightBeam.setOwner(player);
      lightBeam.setFollowingOwner(false);
      lightBeam.setLife(100);
      lightBeam.setDamage(0.0F);
      lightBeam.setSecondaryDamage(0.0F);
      lightBeam.setSize(1.0F);
      lightBeam.setRange((float)distance);
      lightBeam.setExplosionRadius(0.0F);
      lightBeam.setTargetPos(targetPos.x, targetPos.y, targetPos.z);
      lightBeam.setSkill(player, instance, this, mode, 1.0F);
      lightBeam.setMode(mode);
      lightBeam.updateAngle();
      player.level().addFreshEntity(lightBeam);
      player.level().playSound(null, playerPos.x, playerPos.y, playerPos.z, SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.0F, 1.5F);
   }

   private void triggerLargeExplosionWithWaves(Player player, Vec3 targetPos, ManasSkillInstance instance, int mode) {
      int waveCount = 4;
      float maxRadius = 256.0F;
      boolean preventTerrainDamage = DestructiveSkillSafety.shouldPreventTerrainDamage();
      if (!preventTerrainDamage) {
         this.spawnFlyingBlocks(player, targetPos, 15);
      }

      player.level().playSound(null, targetPos.x, targetPos.y, targetPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 10.0F, 0.5F);
      MagicExplosion firstWave = new MagicExplosion(player.level(), player);
      firstWave.setSecondaryDamage(500.0F);
      firstWave.setSkill(player, instance, this, mode, 1.0F);
      firstWave.setSize(maxRadius / waveCount);
      firstWave.setPos(targetPos);
      firstWave.setTickEachHit(1);
      firstWave.setLimitedGriefing(true);
      if (preventTerrainDamage) {
         firstWave.setVisualOnly(true);
      }

      player.level().addFreshEntity(firstWave);
      int shakeTime = firstWave.getLife();
      EffectStorage.setCameraShake(player, 512.0, 0.1F, shakeTime);

      for (int i = 1; i < waveCount; i++) {
         int delay = i * 3;
         float waveRadius = maxRadius * (i + 1) / waveCount;
         int waveIndex = i;
         player.level().getServer().tell(new TickTask(delay, () -> {
            MagicExplosion waveExplosion = new MagicExplosion(player.level(), player);
            waveExplosion.setSecondaryDamage(500.0F * (1.0F - (float)waveIndex / waveCount));
            waveExplosion.setSkill(player, instance, this, mode, 1.0F);
            waveExplosion.setSize(waveRadius);
            waveExplosion.setPos(targetPos);
            waveExplosion.setTickEachHit(1);
            waveExplosion.setLimitedGriefing(true);
            if (preventTerrainDamage) {
               waveExplosion.setVisualOnly(true);
            }

            player.level().addFreshEntity(waveExplosion);
         }));
      }

      if (!preventTerrainDamage) {
         player.level().getServer().tell(new TickTask(shakeTime + 20, () -> this.convertTerrainAfterExplosion(player, targetPos, 64.0F)));
      }
   }

   private void spawnFlyingBlocks(Player player, Vec3 center, int maxBlocks) {
      BlockPos centerPos = new BlockPos((int)center.x, (int)center.y, (int)center.z);
      int blocksSpawned = 0;
      int searchRadius = 10;

      for (int attempt = 0; attempt < maxBlocks * 3 && blocksSpawned < maxBlocks; attempt++) {
         int offsetX = (int)(Math.random() * searchRadius * 2.0 - searchRadius);
         int offsetY = (int)(Math.random() * searchRadius - searchRadius / 2);
         int offsetZ = (int)(Math.random() * searchRadius * 2.0 - searchRadius);
         BlockPos pos = centerPos.offset(offsetX, offsetY, offsetZ);
         BlockState state = player.level().getBlockState(pos);
         if (!state.isAir() && state.getBlock().defaultDestroyTime() >= 0.0F && state.getBlock().defaultDestroyTime() < 50.0F) {
            ItemEntity itemEntity = new ItemEntity(player.level(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(state.getBlock()));
            double dx = (pos.getX() - center.x) * 0.3;
            double dy = 1.0 + Math.random() * 0.5;
            double dz = (pos.getZ() - center.z) * 0.3;
            itemEntity.setDeltaMovement(dx, dy, dz);
            itemEntity.setPickUpDelay(40);
            player.level().addFreshEntity(itemEntity);
            player.level().removeBlock(pos, false);
            blocksSpawned++;
         }
      }
   }

   private void triggerLargeExplosion(Player player, Vec3 targetPos, ManasSkillInstance instance, int mode) {
      boolean preventTerrainDamage = DestructiveSkillSafety.shouldPreventTerrainDamage();
      MagicExplosion largeExplosion = new MagicExplosion(player.level(), player);
      largeExplosion.setSecondaryDamage(500.0F);
      largeExplosion.setSkill(player, instance, this, mode, 1.0F);
      largeExplosion.setSize(256.0F);
      largeExplosion.setPos(targetPos);
      largeExplosion.setLimitedGriefing(true);
      if (preventTerrainDamage) {
         largeExplosion.setVisualOnly(true);
      }

      player.level().addFreshEntity(largeExplosion);
      player.level().playSound(null, targetPos.x, targetPos.y, targetPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 10.0F, 0.5F);
      EffectStorage.setCameraShake(largeExplosion, 512.0, 0.1F, 60);
      if (!DestructiveSkillSafety.shouldPreventTerrainDamage()) {
         this.convertTerrainAfterExplosion(player, targetPos, 256.0F);
      }
   }

   private void convertTerrainAfterExplosion(Player player, Vec3 center, float radius) {
      BlockPos centerPos = new BlockPos((int)center.x, (int)center.y, (int)center.z);
      int radiusInt = (int)radius;
      player.level().getServer().tell(new TickTask(40, () -> this.convertTerrainBatch(player, centerPos, radiusInt, 0)));
   }

   private void convertTerrainBatch(Player player, BlockPos center, int radius, int batchIndex) {
      int blocksPerBatch = 2000;
      int startIndex = batchIndex * blocksPerBatch;
      int endIndex = startIndex + blocksPerBatch;
      int currentIndex = 0;

      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            for (int y = -radius; y <= radius; y++) {
               double distance = Math.sqrt(x * x + y * y + z * z);
               if (!(distance > radius)) {
                  if (currentIndex >= startIndex && currentIndex < endIndex) {
                     BlockPos pos = center.offset(x, y, z);
                     BlockState state = player.level().getBlockState(pos);
                     if (this.isNaturalBlock(state)) {
                        double distanceRatio = distance / radius;
                        BlockState newState;
                        if (distanceRatio < 0.3) {
                           newState = Blocks.COARSE_DIRT.defaultBlockState();
                        } else if (distanceRatio < 0.6) {
                           newState = Math.random() < 0.7 ? Blocks.COARSE_DIRT.defaultBlockState() : Blocks.DIRT.defaultBlockState();
                        } else {
                           newState = Math.random() < 0.3 ? Blocks.COARSE_DIRT.defaultBlockState() : Blocks.DIRT.defaultBlockState();
                        }

                        player.level().setBlock(pos, newState, 3);
                     }
                  }

                  currentIndex++;
               }
            }
         }
      }

      if (currentIndex > endIndex) {
         player.level().getServer().tell(new TickTask(5, () -> this.convertTerrainBatch(player, center, radius, batchIndex + 1)));
      }
   }

   private boolean isNaturalBlock(BlockState state) {
      net.minecraft.world.level.block.Block block = state.getBlock();
      return block == Blocks.GRASS_BLOCK
         || block == Blocks.DIRT
         || block == Blocks.COARSE_DIRT
         || block == Blocks.PODZOL
         || block == Blocks.MYCELIUM
         || block == Blocks.DIRT_PATH
         || block == Blocks.FARMLAND
         || block == Blocks.SAND
         || block == Blocks.RED_SAND
         || block == Blocks.GRAVEL
         || block == Blocks.STONE
         || block == Blocks.DEEPSLATE
         || block == Blocks.ANDESITE
         || block == Blocks.DIORITE
         || block == Blocks.GRANITE;
   }

   private void resetDragonRoarData(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putDouble("dragonRoarX", 0.0);
      tag.putDouble("dragonRoarY", 0.0);
      tag.putDouble("dragonRoarZ", 0.0);
      tag.putInt("chargeTime", 0);
      tag.putInt("DragonRoarCircleID", 0);
      tag.putBoolean("DragonRoarLocked", false);
      instance.markDirty();
   }
}
