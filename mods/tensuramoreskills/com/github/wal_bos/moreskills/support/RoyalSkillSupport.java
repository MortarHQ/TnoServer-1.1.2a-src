package com.github.wal_bos.moreskills.support;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.extra.InfiniteRegenerationSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.ability.InstantRegenerationEffect;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class RoyalSkillSupport {
   public static final String COMBAT_UNTIL = "ArthurAvalonCombatUntil";

   private RoyalSkillSupport() {
   }

   public static boolean isAlly(LivingEntity owner, LivingEntity other) {
      if (owner == other || owner.getUUID().equals(other.getUUID())) {
         return true;
      } else if (owner.isAlliedTo(other) || other.isAlliedTo(owner)) {
         return true;
      } else {
         return SubordinateHelper.isSubordinate(other, owner) ? true : other instanceof OwnableEntity ownable && owner.getUUID().equals(ownable.getOwnerUUID());
      }
   }

   public static boolean validEnemy(LivingEntity owner, LivingEntity target) {
      return target != owner && target.isAlive() && !target.isRemoved() && !isAlly(owner, target);
   }

   public static LivingEntity lookTarget(LivingEntity owner, double range) {
      Entity target = ObjectSelectionHelper.getTargetingEntity(owner, range, false, true);
      return target instanceof LivingEntity living && validEnemy(owner, living) ? living : null;
   }

   public static Vec3 eye(LivingEntity entity) {
      return entity.position().add(0.0, entity.getEyeHeight() * 0.88, 0.0);
   }

   public static Vec3 aimEnd(LivingEntity entity, double range) {
      return eye(entity).add(entity.getLookAngle().normalize().scale(range));
   }

   public static DamageSource source(LivingEntity owner, ManasSkillInstance instance, int mode, ResourceKey<DamageType> type) {
      return instance.getSkill() instanceof Skill tensuraSkill
         ? tensuraSkill.createSource(instance, owner, type, mode)
         : TensuraDamageTypes.getEntityDamageSource(owner.level(), type, owner);
   }

   public static DamageSource authoritySource(LivingEntity owner, ManasSkillInstance instance, int mode, ResourceKey<DamageType> type) {
      return source(owner, instance, mode, type);
   }

   public static DamageSource physicalSource(LivingEntity owner, ManasSkillInstance instance, int mode) {
      return source(owner, instance, mode, owner instanceof Player ? DamageTypes.PLAYER_ATTACK : DamageTypes.MOB_ATTACK);
   }

   public static int damageArea(
      LivingEntity owner, ManasSkillInstance instance, int mode, Vec3 center, double radius, float damage, ResourceKey<DamageType> type
   ) {
      if (owner.level() instanceof ServerLevel level) {
         DamageSource var14 = source(owner, instance, mode, type);
         AABB box = new AABB(center, center).inflate(radius);
         int hits = 0;

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, living -> validEnemy(owner, living))) {
            if (!(target.position().add(0.0, target.getBbHeight() * 0.5, 0.0).distanceToSqr(center) > radius * radius)) {
               target.invulnerableTime = 0;
               if (target.hurt(var14, damage)) {
                  hits++;
                  markCombat(owner, target);
               }
            }
         }

         return hits;
      } else {
         return 0;
      }
   }

   public static int damageLine(
      LivingEntity owner, ManasSkillInstance instance, int mode, Vec3 start, Vec3 end, double radius, float damage, ResourceKey<DamageType> type
   ) {
      if (owner.level() instanceof ServerLevel level) {
         DamageSource var16 = source(owner, instance, mode, type);
         AABB box = new AABB(start, end).inflate(radius + 3.0);
         int hits = 0;

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, living -> validEnemy(owner, living))) {
            Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            if (!(distanceToSegment(center, start, end) > radius + target.getBbWidth() * 0.5)) {
               target.invulnerableTime = 0;
               if (target.hurt(var16, damage)) {
                  hits++;
                  markCombat(owner, target);
               }
            }
         }

         return hits;
      } else {
         return 0;
      }
   }

   public static double distanceToSegment(Vec3 point, Vec3 start, Vec3 end) {
      Vec3 delta = end.subtract(start);
      double length = delta.lengthSqr();
      if (length <= 1.0E-7) {
         return point.distanceTo(start);
      }

      double projection = point.subtract(start).dot(delta) / length;
      projection = Math.max(0.0, Math.min(1.0, projection));
      return point.distanceTo(start.add(delta.scale(projection)));
   }

   public static void applyEffect(LivingEntity target, LivingEntity owner, Holder<MobEffect> effect, int duration, int amplifier) {
      target.addEffect(new MobEffectInstance(effect, duration, amplifier, true, false, true), owner);
   }

   public static void removeHarmfulEffects(LivingEntity entity) {
      for (MobEffectInstance effect : List.copyOf(entity.getActiveEffects())) {
         if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.HARMFUL) {
            entity.removeEffect(effect.getEffect());
         }
      }
   }

   public static void removeEffects(LivingEntity entity, Predicate<Holder<MobEffect>> predicate) {
      for (MobEffectInstance effect : List.copyOf(entity.getActiveEffects())) {
         if (predicate.test(effect.getEffect())) {
            entity.removeEffect(effect.getEffect());
         }
      }
   }

   public static void addAttribute(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id, double amount, Operation operation) {
      AttributeHelper.addPermanentAttribute(entity, attribute, id, amount, operation);
   }

   public static void removeAttribute(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id) {
      AttributeHelper.removeAttribute(entity, attribute, id);
   }

   public static void markCombat(LivingEntity first, LivingEntity second) {
      long until = first.level().getGameTime() + 240L;
      first.getPersistentData().putLong("ArthurAvalonCombatUntil", until);
      second.getPersistentData().putLong("ArthurAvalonCombatUntil", until);
   }

   public static boolean inCombat(LivingEntity entity) {
      return entity.getPersistentData().getLong("ArthurAvalonCombatUntil") >= entity.level().getGameTime();
   }

   public static IExistence existence(LivingEntity entity) {
      return TensuraStorages.getExistenceFrom(entity);
   }

   public static double currentMagicule(LivingEntity entity) {
      return existence(entity).getMagicule();
   }

   public static double currentAura(LivingEntity entity) {
      return existence(entity).getAura();
   }

   public static void restoreEnergyPercent(LivingEntity entity, double magiculePercent, double auraPercent) {
      EnergyHelper.gainMagicule(entity, EnergyHelper.getMaxMagicule(entity) * magiculePercent, GainType.NORMAL);
      EnergyHelper.gainAura(entity, EnergyHelper.getMaxAura(entity) * auraPercent, GainType.NORMAL);
   }

   public static boolean spendMagicule(LivingEntity entity, double amount) {
      return EnergyHelper.isOutOfMagiculeConsuming(entity, amount) <= 0.0;
   }

   public static boolean spendAura(LivingEntity entity, double amount) {
      return EnergyHelper.isOutOfAuraConsuming(entity, amount) <= 0.0;
   }

   public static void infiniteRegeneration(LivingEntity entity, ManasSkillInstance instance, int mode, double costMultiplier) {
      if (entity.isAlive() && !entity.level().isClientSide()) {
         Holder<MobEffect> holder = TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION);
         MobEffectInstance current = entity.getEffect(holder);
         if (current == null || current.getDuration() <= 12) {
            MobEffectInstance regeneration = new MobEffectInstance(holder, 32, 1, false, false, false);
            TensuraMobEffect.addEffect(entity, regeneration, entity, instance.getSkill(), mode);
         }

         if (entity.tickCount % 10 == 0) {
            if (holder.value() instanceof InstantRegenerationEffect regeneration) {
               double var26 = Math.max(0.0, costMultiplier);
               synchronized (InfiniteRegenerationSkill.CONFIG) {
                  double healthCost = InfiniteRegenerationSkill.CONFIG.magiculeCost;
                  double healthCostMastered = InfiniteRegenerationSkill.CONFIG.magiculeCostMastered;
                  double spiritualCost = InfiniteRegenerationSkill.CONFIG.shpMagiculeCost;
                  double spiritualCostMastered = InfiniteRegenerationSkill.CONFIG.shpMagiculeCostMastered;

                  try {
                     InfiniteRegenerationSkill.CONFIG.magiculeCost = healthCost * var26;
                     InfiniteRegenerationSkill.CONFIG.magiculeCostMastered = healthCostMastered * var26;
                     InfiniteRegenerationSkill.CONFIG.shpMagiculeCost = spiritualCost * var26;
                     InfiniteRegenerationSkill.CONFIG.shpMagiculeCostMastered = spiritualCostMastered * var26;
                     regeneration.applyEffectTick(entity, 1);
                  } finally {
                     InfiniteRegenerationSkill.CONFIG.magiculeCost = healthCost;
                     InfiniteRegenerationSkill.CONFIG.magiculeCostMastered = healthCostMastered;
                     InfiniteRegenerationSkill.CONFIG.shpMagiculeCost = spiritualCost;
                     InfiniteRegenerationSkill.CONFIG.shpMagiculeCostMastered = spiritualCostMastered;
                  }
               }
            }
         }
      }
   }

   public static void removeInfiniteRegeneration(LivingEntity entity) {
   }

   public static void unavoidableDamage(LivingEntity target, DamageSource source, float amount) {
      if (target != null && source != null && target.isAlive() && !(amount <= 0.0F) && Float.isFinite(amount)) {
         float healthBefore = target.getHealth() + target.getAbsorptionAmount();
         target.invulnerableTime = 0;
         target.hurt(source, amount);
         float healthAfter = target.getHealth() + target.getAbsorptionAmount();
         float dealt = Math.max(0.0F, healthBefore - healthAfter);
         float remaining = Math.max(0.0F, amount - dealt);
         if (remaining > 0.0F && target.isAlive()) {
            target.setHealth(Math.max(0.0F, target.getHealth() - remaining));
            TensuraDamageHelper.markHurt(target, source.getEntity());
            if (target.getHealth() <= 0.0F) {
               target.die(source);
            }
         }

         target.invulnerableTime = 0;
      }
   }

   public static boolean isSword(ItemStack stack) {
      return stack.getItem() instanceof SwordItem;
   }

   public static boolean isShield(ItemStack stack) {
      return stack.getItem() instanceof ShieldItem;
   }

   public static boolean isPickaxe(ItemStack stack) {
      return stack.getItem() instanceof PickaxeItem;
   }

   public static Collection<ManasSkillInstance> learned(LivingEntity entity) {
      return SkillAPI.getSkillsFrom(entity).getLearnedSkills();
   }

   public static Optional<ManasSkillInstance> skill(LivingEntity entity, ManasSkill skill) {
      return SkillAPI.getSkillsFrom(entity).getSkill(skill);
   }

   public static boolean hasSkill(LivingEntity entity, ManasSkill skill) {
      return skill(entity, skill).isPresent();
   }

   public static boolean isToggled(LivingEntity entity, ManasSkill skill) {
      return skill(entity, skill).<Boolean>map(ManasSkillInstance::isToggled).orElse(false);
   }

   public static boolean mastered(LivingEntity entity, ResourceLocation id) {
      Optional<ManasSkillInstance> instance = SkillAPI.getSkillsFrom(entity).getSkill(id);
      return instance.isPresent() && instance.get().isMastered(entity);
   }

   public static void forceUntoggleAndDelay(LivingEntity target, int cooldownTicks) {
      Skills skills = SkillAPI.getSkillsFrom(target);

      for (ManasSkillInstance learned : List.copyOf(skills.getLearnedSkills())) {
         if (learned != null) {
            if (learned.isToggled()) {
               learned.setToggled(false);
            }

            int modes = Math.max(1, learned.getSkill().getModes(learned));

            for (int mode = 0; mode < modes; mode++) {
               if (learned.getCoolDown(mode) < cooldownTicks) {
                  learned.setCoolDown(cooldownTicks, mode);
               }
            }

            learned.markDirty();
         }
      }

      sync(target);
   }

   public static void forceUntoggleAndExtendCooldowns(LivingEntity target, int extraTicks) {
      Skills skills = SkillAPI.getSkillsFrom(target);

      for (ManasSkillInstance learned : List.copyOf(skills.getLearnedSkills())) {
         if (learned != null) {
            if (learned.isToggled()) {
               learned.setToggled(false);
            }

            int modes = Math.max(1, learned.getSkill().getModes(learned));

            for (int mode = 0; mode < modes; mode++) {
               int cooldown = learned.getCoolDown(mode);
               if (cooldown > 0) {
                  learned.setCoolDown(cooldown + extraTicks, mode);
               }
            }

            learned.markDirty();
         }
      }

      sync(target);
   }

   public static void sync(LivingEntity entity) {
      try {
         Method method = entity.getClass().getMethod("manasCore$sync");
         method.invoke(entity);
      } catch (ReflectiveOperationException var4) {
         if (entity instanceof ServerPlayer player) {
            try {
               Method method = entity.getClass().getMethod("manasCore$sync", ServerPlayer.class);
               method.invoke(entity, player);
            } catch (ReflectiveOperationException var3) {
            }
         }
      }
   }

   public static List<LivingEntity> alliesInRange(LivingEntity owner, double radius) {
      AABB box = owner.getBoundingBox().inflate(radius);
      List<LivingEntity> result = new ArrayList<>();

      for (LivingEntity living : owner.level().getEntitiesOfClass(LivingEntity.class, box)) {
         if (isAlly(owner, living)) {
            result.add(living);
         }
      }

      return result;
   }

   public static List<LivingEntity> enemiesInRange(LivingEntity owner, double radius) {
      return owner.level().getEntitiesOfClass(LivingEntity.class, owner.getBoundingBox().inflate(radius), living -> validEnemy(owner, living));
   }

   public static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", path);
   }
}
