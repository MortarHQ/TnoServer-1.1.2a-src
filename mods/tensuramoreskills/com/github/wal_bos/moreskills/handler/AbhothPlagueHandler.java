package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.ultimate.AbhothLordOfPestilenceSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class AbhothPlagueHandler {
   private static final MoreSkillsConfigs.AbhothLordOfPestilenceConfig CONFIG = MoreSkillsConfigs.GRAND.abhothLordOfPestilence;
   public static final String MINION_TAG = "tms_abhoth_miscreation";
   public static final String MINION_OWNER = "tms_abhoth_owner";
   public static final String MINION_EXPIRE = "tms_abhoth_expire";
   private static final String ROOT = "tms_abhoth_plague";
   private static final String FILM_OWNER = "owner";
   private static final String FILM_EXPIRE = "expire";
   private static final Map<Integer, Map<BlockPos, CompoundTag>> FILMS = new HashMap<>();

   private AbhothPlagueHandler() {
   }

   public static void init() {
   }

   public static void placeFilm(ServerPlayer owner, BlockPos pos, long expire) {
      if (owner.level() instanceof ServerLevel level) {
         CompoundTag tag = new CompoundTag();
         tag.putUUID("owner", owner.getUUID());
         tag.putLong("expire", expire);
         FILMS.computeIfAbsent(System.identityHashCode(level), key -> new HashMap<>()).put(pos.immutable(), tag);
      }
   }

   public static void infect(ServerPlayer owner, LivingEntity target, int durationTicks, boolean force) {
      if (target != owner && target.isAlive()) {
         if (!target.getPersistentData().getBoolean("tms_abhoth_miscreation")) {
            CompoundTag root = target.getPersistentData().getCompound("tms_abhoth_plague");
            String key = owner.getUUID().toString();
            CompoundTag entry = root.getCompound(key);
            if (force || entry.getLong("until") <= target.level().getGameTime()) {
               entry.putLong("until", target.level().getGameTime() + durationTicks);
               entry.putLong("lastDamage", 0L);
               entry.putLong("lastSpread", 0L);
               root.put(key, entry);
               target.getPersistentData().put("tms_abhoth_plague", root);
               applyTruePlagueEffects(target, (Integer)CONFIG.plagueEffectRefreshDurationTicks.get(), getStackAmplifier(target));
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityTick(Post event) {
      Entity entity = event.getEntity();
      if (entity.level() instanceof ServerLevel level) {
         if (entity.tickCount % (Integer)CONFIG.filmTickIntervalTicks.get() == 0) {
            tickFilms(level);
         }

         if (entity instanceof LivingEntity living) {
            if (living.getPersistentData().getBoolean("tms_abhoth_miscreation")) {
               tickMinion(living, level);
               return;
            }

            tickLiving(living, level);
         }
      }
   }

   private static void tickFilms(ServerLevel level) {
      Map<BlockPos, CompoundTag> map = FILMS.get(System.identityHashCode(level));
      if (map != null && !map.isEmpty()) {
         long now = level.getGameTime();
         Iterator<Entry<BlockPos, CompoundTag>> iterator = map.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<BlockPos, CompoundTag> entry = iterator.next();
            BlockPos pos = entry.getKey();
            CompoundTag tag = entry.getValue();
            if (now > tag.getLong("expire")) {
               iterator.remove();
            } else {
               level.sendParticles(
                  ParticleTypes.ASH,
                  pos.getX() + 0.5,
                  pos.getY() + 0.08,
                  pos.getZ() + 0.5,
                  (Integer)CONFIG.filmAshParticles.get(),
                  (Double)CONFIG.filmAshXZ.get(),
                  (Double)CONFIG.filmAshY.get(),
                  (Double)CONFIG.filmAshXZ.get(),
                  (Double)CONFIG.filmAshSpeed.get()
               );
               level.sendParticles(
                  ParticleTypes.MYCELIUM,
                  pos.getX() + 0.5,
                  pos.getY() + 0.12,
                  pos.getZ() + 0.5,
                  (Integer)CONFIG.filmMyceliumParticles.get(),
                  (Double)CONFIG.filmMyceliumXZ.get(),
                  (Double)CONFIG.filmMyceliumY.get(),
                  (Double)CONFIG.filmMyceliumXZ.get(),
                  (Double)CONFIG.filmMyceliumSpeed.get()
               );
               UUID ownerId = tag.getUUID("owner");
               ServerPlayer owner = level.getServer().getPlayerList().getPlayer(ownerId);
               if (owner != null) {
                  AABB box = new AABB(pos)
                     .inflate(
                        (Double)CONFIG.filmInfectionInflateXZ.get(), (Double)CONFIG.filmInfectionInflateY.get(), (Double)CONFIG.filmInfectionInflateXZ.get()
                     );

                  for (LivingEntity victim : level.getEntitiesOfClass(
                     LivingEntity.class, box, target -> target != owner && target.isAlive() && !target.getPersistentData().getBoolean("tms_abhoth_miscreation")
                  )) {
                     infect(owner, victim, (Integer)CONFIG.filmInfectionDurationTicks.get(), true);
                  }
               }
            }
         }
      }
   }

   private static void tickLiving(LivingEntity target, ServerLevel level) {
      if (!target.level().isClientSide()) {
         CompoundTag root = target.getPersistentData().getCompound("tms_abhoth_plague");
         if (!root.isEmpty()) {
            long now = level.getGameTime();
            boolean dirty = false;

            for (String ownerKey : root.getAllKeys()) {
               CompoundTag entry = root.getCompound(ownerKey);
               if (now > entry.getLong("until")) {
                  root.remove(ownerKey);
                  dirty = true;
               } else {
                  UUID ownerId;
                  try {
                     ownerId = UUID.fromString(ownerKey);
                  } catch (IllegalArgumentException ex) {
                     root.remove(ownerKey);
                     dirty = true;
                     continue;
                  }

                  ServerPlayer owner = level.getServer().getPlayerList().getPlayer(ownerId);
                  if (owner != null) {
                     if (now - entry.getLong("lastDamage") >= ((Integer)CONFIG.plagueDamageIntervalTicks.get()).intValue()) {
                        entry.putLong("lastDamage", now);
                        applyTruePlagueEffects(target, (Integer)CONFIG.plagueEffectRefreshDurationTicks.get(), getStackAmplifier(target));
                        hurtWithResistanceDegradation(
                           owner,
                           target,
                           ((Double)CONFIG.plagueBaseDamage.get()).floatValue()
                              + Math.min(
                                 ((Double)CONFIG.plagueMaxStackBonusDamage.get()).floatValue(),
                                 getStackAmplifier(target) * ((Double)CONFIG.plagueDamagePerStack.get()).floatValue()
                              )
                        );
                        owner.heal(
                           Math.max(
                              ((Double)CONFIG.plagueMinimumHeal.get()).floatValue(), owner.getMaxHealth() * ((Double)CONFIG.plagueHealRatio.get()).floatValue()
                           )
                        );
                        EnergyHelper.gainMagicule(
                           owner,
                           (Double)CONFIG.plagueMagiculeGainBase.get() + getStackAmplifier(target) * (Double)CONFIG.plagueMagiculeGainPerStack.get(),
                           GainType.NORMAL
                        );
                     }

                     if (now - entry.getLong("lastSpread") >= ((Integer)CONFIG.plagueSpreadIntervalTicks.get()).intValue()) {
                        entry.putLong("lastSpread", now);
                        spreadFromTarget(owner, target, level, entry);
                     }

                     root.put(ownerKey, entry);
                     dirty = true;
                  }
               }
            }

            if (dirty) {
               target.getPersistentData().put("tms_abhoth_plague", root);
            }
         }
      }
   }

   private static void spreadFromTarget(ServerPlayer owner, LivingEntity infected, ServerLevel level, CompoundTag entry) {
      AABB box = infected.getBoundingBox().inflate((Double)CONFIG.plagueSpreadRadius.get());
      List<LivingEntity> list = level.getEntitiesOfClass(
         LivingEntity.class,
         box,
         cleanx -> cleanx != infected
            && cleanx != owner
            && cleanx.isAlive()
            && !cleanx.getPersistentData().getBoolean("tms_abhoth_miscreation")
            && !cleanx.isAlliedTo(owner)
      );
      CompoundTag spread = entry.getCompound("spread");

      for (LivingEntity clean : list) {
         String id = clean.getUUID().toString();
         if (!spread.getBoolean(id)) {
            infect(owner, clean, (Integer)CONFIG.plagueSpreadInfectionDurationTicks.get(), false);
            spread.putBoolean(id, true);
            level.sendParticles(
               ParticleTypes.MYCELIUM,
               clean.getX(),
               clean.getY() + clean.getBbHeight() * 0.5,
               clean.getZ(),
               (Integer)CONFIG.plagueSpreadParticles.get(),
               (Double)CONFIG.plagueSpreadParticleXZ.get(),
               (Double)CONFIG.plagueSpreadParticleY.get(),
               (Double)CONFIG.plagueSpreadParticleXZ.get(),
               (Double)CONFIG.plagueSpreadParticleSpeed.get()
            );
         }
      }

      entry.put("spread", spread);
   }

   private static void tickMinion(LivingEntity minion, ServerLevel level) {
      CompoundTag data = minion.getPersistentData();
      if (!data.hasUUID("tms_abhoth_owner")) {
         minion.discard();
      } else {
         ServerPlayer owner = level.getServer().getPlayerList().getPlayer(data.getUUID("tms_abhoth_owner"));
         if (owner != null && owner.isAlive()) {
            purgeAbhothMinionDebuffs(minion);
            long now = level.getGameTime();
            if (now >= data.getLong("tms_abhoth_expire")) {
               owner.heal(owner.getMaxHealth() * ((Double)CONFIG.minionExpireHealRatio.get()).floatValue());
               level.sendParticles(
                  ParticleTypes.ITEM_SLIME,
                  minion.getX(),
                  minion.getY() + 0.35,
                  minion.getZ(),
                  (Integer)CONFIG.minionExpireParticles.get(),
                  (Double)CONFIG.minionExpireParticleXZ.get(),
                  (Double)CONFIG.minionExpireParticleY.get(),
                  (Double)CONFIG.minionExpireParticleXZ.get(),
                  (Double)CONFIG.minionExpireParticleSpeed.get()
               );
               minion.discard();
            } else {
               LivingEntity target = findMinionTarget(owner, minion, level);
               if (target != null) {
                  if (minion instanceof Mob mob) {
                     mob.setTarget(target);
                     mob.getNavigation().moveTo(target, (Double)CONFIG.minionNavigationSpeed.get());
                  } else {
                     Vec3 dir = target.position().subtract(minion.position());
                     if (dir.lengthSqr() > 0.001) {
                        minion.setDeltaMovement(minion.getDeltaMovement().add(dir.normalize().scale((Double)CONFIG.minionFallbackAcceleration.get())));
                     }
                  }

                  if (minion.distanceToSqr(target) <= (Double)CONFIG.minionHitDistanceSqr.get()
                     && now - data.getLong("lastHit") >= ((Integer)CONFIG.minionHitIntervalTicks.get()).intValue()) {
                     data.putLong("lastHit", now);
                     infect(owner, target, (Integer)CONFIG.minionInfectionDurationTicks.get(), true);

                     for (String key : AbhothLordOfPestilenceSkill.getWorkshopEffects(owner)) {
                        AbhothLordOfPestilenceSkill.applyStoredEffect(
                           owner, target, key, (Integer)CONFIG.minionStoredEffectDurationTicks.get(), (Integer)CONFIG.minionStoredEffectBonusAmplifier.get()
                        );
                     }

                     hurtWithResistanceDegradation(owner, target, ((Double)CONFIG.minionDamage.get()).floatValue());
                     level.sendParticles(
                        ParticleTypes.MYCELIUM,
                        target.getX(),
                        target.getY() + target.getBbHeight() * 0.5,
                        target.getZ(),
                        (Integer)CONFIG.minionHitParticles.get(),
                        (Double)CONFIG.minionHitParticleXZ.get(),
                        (Double)CONFIG.minionHitParticleY.get(),
                        (Double)CONFIG.minionHitParticleXZ.get(),
                        (Double)CONFIG.minionHitParticleSpeed.get()
                     );
                  }
               }
            }
         } else {
            minion.discard();
         }
      }
   }

   private static void purgeAbhothMinionDebuffs(LivingEntity minion) {
      minion.getPersistentData().remove("tms_abhoth_plague");
      minion.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.FATAL_POISON));
      minion.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.CORROSION));
      minion.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY));
      minion.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INFECTION));
      if (minion.getHealth() < minion.getMaxHealth()) {
         minion.setHealth(minion.getMaxHealth());
      }
   }

   private static void hurtWithResistanceDegradation(LivingEntity attacker, LivingEntity target, float amount) {
      AttributeInstance attribute = attacker.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
      ResourceLocation id = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "abhoth_true_infection");
      boolean added = false;
      if (attribute != null) {
         attribute.addOrReplacePermanentModifier(new AttributeModifier(id, (Double)CONFIG.temporaryResistanceDegradation.get(), Operation.ADD_VALUE));
         added = true;
      }

      DamageSource source = target.damageSources().mobAttack(attacker);
      target.hurt(source, amount);
      if (added) {
         attribute.removeModifier(id);
      }
   }

   private static LivingEntity findMinionTarget(ServerPlayer owner, LivingEntity minion, ServerLevel level) {
      AABB box = minion.getBoundingBox().inflate((Double)CONFIG.minionTargetRadius.get());
      return level.getEntitiesOfClass(
            LivingEntity.class,
            box,
            target -> target != minion
               && target != owner
               && target.isAlive()
               && !target.getPersistentData().getBoolean("tms_abhoth_miscreation")
               && !target.isAlliedTo(owner)
         )
         .stream()
         .min((a, b) -> Double.compare(a.distanceToSqr(minion), b.distanceToSqr(minion)))
         .orElse(null);
   }

   private static void applyTruePlagueEffects(LivingEntity target, int duration, int amplifier) {
      addStacking(target, TensuraMobEffects.getReference(TensuraMobEffects.FATAL_POISON), duration, amplifier);
      addStacking(target, TensuraMobEffects.getReference(TensuraMobEffects.CORROSION), duration, amplifier);
      addStacking(target, TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY), duration, amplifier);
      addStacking(target, TensuraMobEffects.getReference(TensuraMobEffects.INFECTION), duration, Math.max(0, amplifier - 1));
   }

   private static void addStacking(LivingEntity target, Holder<MobEffect> effect, int duration, int amplifier) {
      MobEffectInstance old = target.getEffect(effect);
      int amp = Math.min((Integer)CONFIG.plagueMaximumAmplifier.get(), amplifier);
      if (old != null) {
         amp = Math.min((Integer)CONFIG.plagueMaximumAmplifier.get(), Math.max(amp, old.getAmplifier() + (Integer)CONFIG.plagueStackGainPerRefresh.get()));
      }

      target.addEffect(new MobEffectInstance(effect, duration, amp, false, false, true));
   }

   private static int getStackAmplifier(LivingEntity target) {
      MobEffectInstance old = target.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.INFECTION));
      return old == null ? 0 : Math.max(0, old.getAmplifier());
   }
}
