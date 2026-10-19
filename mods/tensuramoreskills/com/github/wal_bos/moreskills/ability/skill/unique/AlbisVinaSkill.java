package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.AlbisVinaHandler;
import com.github.wal_bos.moreskills.net.msg.AlbisVinaVfxPayload;
import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.world.entity.ArsWeissCloneEntity;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class AlbisVinaSkill extends Skill {
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "albis_vina");
   private static final ResourceLocation CHANT_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "albis_vina_chant_speed");
   public static final int MODE_ARS_WEISS = 0;
   public static final int MODE_RIVILLE_CALANTIBIUM = 1;
   public static final int MODE_STELLAS_NATEA = 2;

   public AlbisVinaSkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.AlbisVinaConfig cfg() {
      return MoreSkillsConfigs.GRAND.albisVina;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/albis.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 2 : mode - 1;
      } else {
         return mode >= 2 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "albis_vina.ars_weiss";
         case 1 -> "albis_vina.riville_calantibium";
         case 2 -> "albis_vina.stellas_natea";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> instance.isMastered(entity) ? (Double)cfg().arsWeissCostMastered.get() : (Double)cfg().arsWeissCost.get();
         case 1 -> instance.isMastered(entity) ? (Double)cfg().rivilleCostMastered.get() : (Double)cfg().rivilleCost.get();
         case 2 -> instance.isMastered(entity) ? (Double)cfg().stellasCostMastered.get() : (Double)cfg().stellasCost.get();
         default -> 0.0;
      };
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      this.addChant(entity, instance);
      AlbisVinaHandler.grantAlbisPackage(entity);
      if (entity.level() instanceof ServerLevel level && entity instanceof Player player) {
         level.playSound(null, player.blockPosition(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 0.8F, 1.15F);
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.albis_vina.formula_on"), true);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      this.removeChant(entity);
      if (entity.level() instanceof ServerLevel level && entity instanceof Player player) {
         level.playSound(null, player.blockPosition(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 0.8F, 0.95F);
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.albis_vina.formula_off"), true);
      }
   }

   public boolean onAbilityEquipped(
      Player player, Changeable<ManasSkillInstance> instance, Changeable<Integer> mode, Changeable<Integer> slot, Changeable<Integer> preset
   ) {
      AlbisVinaHandler.grantAlbisPackage(player);
      if (((ManasSkillInstance)instance.get()).isToggled()) {
         this.addChant(player, (ManasSkillInstance)instance.get());
      }

      return true;
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      this.removeChant(entity);
      if (entity instanceof Player player) {
         AlbisVinaHandler.removeAllClones(player);
         AlbisVinaHandler.clearPlayer(player.getUUID());
      }

      instance.setToggled(false);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof Player player) {
         if (mode == 0) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               this.summonClones(instance, player);
               instance.addMasteryPoint(entity);
               instance.setCoolDown(
                  instance.isMastered(entity) ? (Integer)cfg().arsWeissCooldownTicksMastered.get() : (Integer)cfg().arsWeissCooldownTicks.get(), mode
               );
               entity.swing(InteractionHand.MAIN_HAND, true);
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode != 1 && mode != 2) {
         return false;
      }

      int castTime = this.getCastTime(instance, entity, mode);
      if (heldTicks % 5 == 0) {
         TensuraParticleHelper.spawnServerParticles(
            entity.level(),
            (ParticleOptions)TensuraParticleTypes.SNOWFLAKE.get(),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.6,
            entity.getZ(),
            4,
            0.25,
            0.35,
            0.25,
            0.02,
            true
         );
      }

      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.albis_vina.cast", new Object[]{Math.min(heldTicks, castTime), castTime}), true
         );
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 1 || mode == 2) {
         int castTime = this.getCastTime(instance, entity, mode);
         if (heldTicks >= castTime) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               if (mode == 1) {
                  this.releaseRiville(instance, entity);
                  instance.setCoolDown(
                     instance.isMastered(entity) ? (Integer)cfg().rivilleCooldownTicksMastered.get() : (Integer)cfg().rivilleCooldownTicks.get(), mode
                  );
               } else {
                  this.releaseStellas(instance, entity);
                  instance.setCoolDown(
                     instance.isMastered(entity) ? (Integer)cfg().stellasCooldownTicksMastered.get() : (Integer)cfg().stellasCooldownTicks.get(), mode
                  );
               }

               instance.addMasteryPoint(entity);
               entity.swing(InteractionHand.MAIN_HAND, true);
            }
         }
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      MobEffectInstance dust = target.getEffect(ModMobEffects.getReference(ModMobEffects.ALBIS_DIAMOND_DUST));
      if (dust != null) {
         Entity attacker = source.getEntity();
         if (attacker instanceof ArsWeissCloneEntity clone) {
            attacker = clone.getOwnerPlayer();
         }

         if (attacker == entity && AlbisVinaHandler.isWaterOrIce(source)) {
            float multiplier = instance.isMastered(entity)
               ? ((Double)cfg().diamondDustWaterIceDamageMultiplierMastered.get()).floatValue()
               : ((Double)cfg().diamondDustWaterIceDamageMultiplier.get()).floatValue();
            amount.set((Float)amount.get() * multiplier);
         }
      }

      if (instance.isToggled() && AlbisVinaHandler.isOwnerOrClone(entity, source.getEntity()) && AlbisVinaHandler.isWaterOrIce(source)) {
         float multiplier = instance.isMastered(entity)
            ? ((Double)cfg().toggledWaterIceDamageMultiplierMastered.get()).floatValue()
            : ((Double)cfg().toggledWaterIceDamageMultiplier.get()).floatValue();
         amount.set((Float)amount.get() * multiplier);
      }

      return true;
   }

   public static void applyDiamondDust(LivingEntity owner, LivingEntity target, boolean mastered) {
      target.addEffect(
         new MobEffectInstance(
            ModMobEffects.getReference(ModMobEffects.ALBIS_DIAMOND_DUST), (Integer)cfg().diamondDustDurationTicks.get(), mastered ? 1 : 0, true, false, true
         ),
         owner
      );
   }

   private int getCastTime(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 1
         ? instance.isMastered(entity) ? (Integer)cfg().rivilleCastTicksMastered.get() : (Integer)cfg().rivilleCastTicks.get()
         : instance.isMastered(entity) ? (Integer)cfg().stellasCastTicksMastered.get() : (Integer)cfg().stellasCastTicks.get();
   }

   private void summonClones(ManasSkillInstance instance, Player player) {
      if (player.level() instanceof ServerLevel level) {
         AlbisVinaHandler.removeAllClones(player);
         int count = instance.isMastered(player) ? (Integer)cfg().arsWeissCloneCountMastered.get() : (Integer)cfg().arsWeissCloneCount.get();
         int duration = instance.isMastered(player) ? (Integer)cfg().arsWeissDurationTicksMastered.get() : (Integer)cfg().arsWeissDurationTicks.get();

         for (int i = 0; i < count; i++) {
            Vec3 pos = this.findClonePosition(player, i, count);
            ArsWeissCloneEntity clone = new ArsWeissCloneEntity((EntityType<? extends ArsWeissCloneEntity>)ModEntityTypes.ARS_WEISS_CLONE.get(), level);
            clone.setOwnerData(player, duration);
            clone.setAlbisFormulaClone();
            clone.moveTo(pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
            clone.setYHeadRot(player.getYHeadRot());
            clone.setCustomName(player.getDisplayName());
            level.addFreshEntity(clone);
            AlbisVinaHandler.addClone(player.getUUID(), clone);
            TensuraParticleHelper.spawnServerParticles(
               level,
               (ParticleOptions)TensuraParticleTypes.SNOWFLAKE_EFFECT.get(),
               clone.getX(),
               clone.getY() + 1.0,
               clone.getZ(),
               10,
               0.2,
               0.35,
               0.2,
               0.04,
               true
            );
         }

         level.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.65F, 1.6F);
      }
   }

   private Vec3 findClonePosition(Player player, int index, int count) {
      Level level = player.level();
      double base = (Math.PI * 2) / count * index + player.getYRot() * 0.017453292;

      for (int i = 0; i < 8; i++) {
         double angle = base + i * 0.785398163;
         double radius = i < 4 ? 1.8 : 2.4;
         Vec3 pos = player.position().add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
         BlockPos blockPos = BlockPos.containing(pos);
         if (level.getBlockState(blockPos).isAir() && level.getBlockState(blockPos.above()).isAir()) {
            return Vec3.atBottomCenterOf(blockPos);
         }
      }

      Vec3 side = player.getLookAngle().cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 0.001) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return player.position().add(side.normalize().scale(1.4));
   }

   private void releaseRiville(ManasSkillInstance instance, LivingEntity entity) {
      this.fireBeam(instance, entity, entity, 1.0F);
      List<ArsWeissCloneEntity> clones = AlbisVinaHandler.getClones(entity.getUUID());
      int max = instance.isMastered(entity) ? clones.size() : Math.min(1, clones.size());

      for (int i = 0; i < max; i++) {
         ArsWeissCloneEntity clone = clones.get(i);
         clone.copyLookFrom(entity);
         float cloneScale = instance.isMastered(entity)
            ? ((Double)cfg().cloneBeamDamageMultiplierMastered.get()).floatValue()
            : ((Double)cfg().cloneBeamDamageMultiplier.get()).floatValue();
         this.fireBeam(instance, entity, clone, cloneScale);
      }

      entity.level().playSound(null, entity.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_WATER.get(), SoundSource.PLAYERS, 1.0F, 1.25F);
   }

   private void fireBeam(ManasSkillInstance instance, LivingEntity owner, LivingEntity caster, float scale) {
      Vec3 start = caster.getEyePosition();
      Vec3 look = caster.getLookAngle().normalize();
      double range = instance.isMastered(owner) ? (Double)cfg().rivilleRangeMastered.get() : (Double)cfg().rivilleRange.get();
      Vec3 end = start.add(look.scale(range));
      BlockHitResult blockHit = caster.level().clip(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, caster));
      Vec3 visualEnd = blockHit.getLocation();
      AABB box = caster.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0);
      List<LivingEntity> targets = caster.level()
         .getEntitiesOfClass(LivingEntity.class, box, target -> target.isAlive() && target != owner && target != caster && !target.isAlliedTo(owner))
         .stream()
         .sorted(Comparator.comparingDouble(target -> target.distanceToSqr(caster)))
         .toList();
      int pierce = 0;
      int maxPierce = (Integer)cfg().rivillePierceTargets.get();

      for (LivingEntity target : targets) {
         Vec3 close = start.add(look.scale(Math.max(0.0, target.position().subtract(start).dot(look))));
         if (target.getBoundingBox().inflate(0.65).contains(close)) {
            double baseDamage = instance.isMastered(owner) ? (Double)cfg().rivilleDamageMastered.get() : (Double)cfg().rivilleDamage.get();
            float damage = (float)(baseDamage * scale);
            target.hurt(this.createSource(instance, owner, TensuraDamageTypes.ICE_ELEMENTAL, 1), damage);
            applyDiamondDust(owner, target, instance.isMastered(owner));
            SkillHelper.knockBack(target, look, 0.4, 0.25, 0.05);
            if (caster.level() instanceof ServerLevel level) {
               AlbisVinaVfxPayload.sendSpikeImpact(level, close, instance.isMastered(owner) ? 3.0F : 2.3F, scale >= 1.0F);
            }

            if (++pierce >= maxPierce) {
               break;
            }
         }
      }

      if (caster.level() instanceof ServerLevel level) {
         AlbisVinaVfxPayload.sendBeam(level, start, visualEnd, scale >= 1.0F ? 1.0F : 0.75F, scale >= 1.0F);
         AlbisVinaVfxPayload.sendSpikeImpact(level, visualEnd, instance.isMastered(owner) ? 3.2F : 2.5F, scale >= 1.0F);

         for (int i = 0; i < 28; i++) {
            Vec3 pos = start.add(look.scale(i * range / 28.0));
            TensuraParticleHelper.spawnServerParticles(
               level, (ParticleOptions)TensuraParticleTypes.SNOWFLAKE.get(), pos.x, pos.y, pos.z, 1, 0.025, 0.025, 0.025, 0.0, true
            );
         }
      }
   }

   private void releaseStellas(ManasSkillInstance instance, LivingEntity entity) {
      Vec3 target = this.getLookTarget(entity, instance.isMastered(entity) ? (Double)cfg().stellasRangeMastered.get() : (Double)cfg().stellasRange.get());
      int base = instance.isMastered(entity) ? (Integer)cfg().stellasSpearCountMastered.get() : (Integer)cfg().stellasSpearCount.get();

      for (int i = 0; i < base; i++) {
         double angle = i * 2.399963;
         double radius = 0.5 + i * 0.35;
         Vec3 impact = target.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
         float damage = instance.isMastered(entity)
            ? ((Double)cfg().stellasSpearDamageMastered.get()).floatValue()
            : ((Double)cfg().stellasSpearDamage.get()).floatValue();
         AlbisVinaHandler.queueSpear(entity.getUUID(), impact, 14 + i * 4, damage, true);
      }

      List<ArsWeissCloneEntity> clones = AlbisVinaHandler.getClones(entity.getUUID());

      for (int i = 0; i < clones.size(); i++) {
         Vec3 offset = target.add((i - clones.size() * 0.5) * 0.8, 0.0, 0.8);
         float damage = instance.isMastered(entity)
            ? ((Double)cfg().cloneSpearDamageMastered.get()).floatValue()
            : ((Double)cfg().cloneSpearDamage.get()).floatValue();
         AlbisVinaHandler.queueSpear(entity.getUUID(), offset, 20 + i * 3, damage, false);
      }

      entity.level().playSound(null, entity.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_WATER.get(), SoundSource.PLAYERS, 1.0F, 0.85F);
   }

   private Vec3 getLookTarget(LivingEntity entity, double range) {
      Entity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false, true);
      if (target != null) {
         return target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      }

      Vec3 start = entity.getEyePosition();
      Vec3 end = start.add(entity.getLookAngle().scale(range));
      BlockHitResult result = entity.level().clip(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, entity));
      return result.getLocation();
   }

   private void addChant(LivingEntity entity, ManasSkillInstance instance) {
      this.removeChant(entity);
      double amount = instance.isMastered(entity) ? (Double)cfg().toggleChantSpeedMastered.get() : (Double)cfg().toggleChantSpeed.get();
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.CHANT_SPEED, CHANT_ID, amount, Operation.ADD_VALUE);
   }

   private void removeChant(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.CHANT_SPEED, CHANT_ID);
   }
}
