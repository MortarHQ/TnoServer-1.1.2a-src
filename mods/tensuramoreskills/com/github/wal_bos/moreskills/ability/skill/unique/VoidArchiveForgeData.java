package com.github.wal_bos.moreskills.ability.skill.unique;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleType;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class VoidArchiveForgeData {
   public static final String TAG_CREATED_MOVES = "CreatedMoves";
   public static final String TAG_ACTIVE_MOVE = "ActiveCreatedMoveIndex";
   public static final String TAG_EQUIPPED_MOVES = "EquippedCreatedMoves";
   public static final String TAG_RECORDED_ENTITIES = "RecordedEntities";
   public static final String TAG_MELEE_INFUSIONS = "MeleeInfusions";
   public static final String TAG_ACTIVE_MELEE = "ActiveMeleeInfusionIndex";
   private static final ConcurrentMap<ResourceKey<Level>, List<VoidArchiveForgeData.DeployedTrap>> ACTIVE_TRAPS = new ConcurrentHashMap<>();
   private static final ThreadLocal<Set<UUID>> ACTIVE_INFUSION_KEYS = ThreadLocal.withInitial(HashSet::new);
   private static final ThreadLocal<ManasSkillInstance> ACTIVE_GRIEF_INSTANCE = new ThreadLocal<>();
   private static final ThreadLocal<Entity> ACTIVE_GRIEF_OWNER = new ThreadLocal<>();

   private VoidArchiveForgeData() {
   }

   public static List<VoidArchiveForgeData.RecordedEntity> getRecordedEntities(CompoundTag tag) {
      List<VoidArchiveForgeData.RecordedEntity> out = new ArrayList<>();

      for (Tag raw : tag.getList("RecordedEntities", 10)) {
         CompoundTag c = (CompoundTag)raw;
         if (c.contains("EntityId")) {
            out.add(new VoidArchiveForgeData.RecordedEntity(ResourceLocation.parse(c.getString("EntityId")), c.getInt("Kills")));
         }
      }

      return out;
   }

   public static void addRecordedKill(CompoundTag tag, EntityType<?> type) {
      ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
      if (id != null) {
         ListTag list = tag.getList("RecordedEntities", 10);

         for (Tag raw : list) {
            CompoundTag c = (CompoundTag)raw;
            if (id.toString().equals(c.getString("EntityId"))) {
               c.putInt("Kills", Math.min(999, c.getInt("Kills") + 1));
               tag.put("RecordedEntities", list);
               return;
            }
         }

         CompoundTag add = new CompoundTag();
         add.putString("EntityId", id.toString());
         add.putInt("Kills", 1);
         list.add(add);

         while (list.size() > 48) {
            list.remove(0);
         }

         tag.put("RecordedEntities", list);
      }
   }

   public static List<VoidArchiveForgeData.MeleeInfusion> getMeleeInfusions(CompoundTag tag) {
      List<VoidArchiveForgeData.MeleeInfusion> out = new ArrayList<>();

      for (Tag raw : tag.getList("MeleeInfusions", 10)) {
         CompoundTag c = (CompoundTag)raw;
         out.add(
            new VoidArchiveForgeData.MeleeInfusion(
               c.getString("Name"),
               c.getString("DamageId"),
               parseNullableRL(c.getString("EffectId")),
               c.getInt("EffectAmplifier"),
               c.getInt("EffectDuration"),
               c.getFloat("PowerMultiplier"),
               c.getInt("DurationTicks"),
               c.getBoolean("ApplyDamage"),
               c.getBoolean("ApplyEffect"),
               c.getBoolean("LifeSteal")
            )
         );
      }

      return out;
   }

   public static boolean addMeleeInfusion(CompoundTag tag, VoidArchiveForgeData.MeleeInfusion infusion, int max) {
      if (infusion != null && infusion.name() != null && !infusion.name().isBlank()) {
         ListTag list = tag.getList("MeleeInfusions", 10);
         CompoundTag c = new CompoundTag();
         c.putString("Name", infusion.name());
         c.putString("DamageId", safe(infusion.damageId()));
         c.putString("EffectId", infusion.effectId() == null ? "" : infusion.effectId().toString());
         c.putInt("EffectAmplifier", Math.max(0, infusion.effectAmplifier()));
         c.putInt("EffectDuration", Math.max(0, infusion.effectDuration()));
         c.putFloat("PowerMultiplier", clamp(infusion.powerMultiplier(), 0.2F, 8.0F));
         c.putInt("DurationTicks", Math.max(40, Math.min(2400, infusion.durationTicks())));
         c.putBoolean("ApplyDamage", infusion.applyDamage());
         c.putBoolean("ApplyEffect", infusion.applyEffect());
         c.putBoolean("LifeSteal", infusion.lifeSteal());
         list.add(c);

         while (list.size() > max) {
            list.remove(0);
         }

         tag.put("MeleeInfusions", list);
         tag.putInt("ActiveMeleeInfusionIndex", Math.max(0, list.size() - 1));
         return true;
      } else {
         return false;
      }
   }

   public static List<VoidArchiveForgeData.CreatedMove> getCreatedMoves(CompoundTag tag) {
      List<VoidArchiveForgeData.CreatedMove> out = new ArrayList<>();

      for (Tag raw : tag.getList("CreatedMoves", 10)) {
         CompoundTag c = (CompoundTag)raw;
         out.add(
            new VoidArchiveForgeData.CreatedMove(
               c.getString("Name"),
               c.getString("PrimaryDamage"),
               c.getString("SecondaryDamage"),
               parseNullableRL(c.getString("EffectId")),
               c.getInt("EffectAmplifier"),
               c.getInt("EffectDuration"),
               parseShape(c.getString("Shape")),
               parseParticle(c.getString("ParticleStyle")),
               parseNullableRL(c.getString("CustomParticleId")),
               c.getInt("Range"),
               c.getFloat("Radius"),
               c.getFloat("PowerMultiplier"),
               c.getFloat("Speed"),
               Math.max(1, c.getInt("BurstCount")),
               c.getFloat("BarrierBypass"),
               c.getFloat("ResistanceBypass"),
               c.getFloat("MagiculeSteal"),
               c.getInt("SelfBuffDuration"),
               c.getBoolean("MultiHit"),
               c.getBoolean("HealCaster"),
               c.getBoolean("PullTarget"),
               c.getBoolean("LaunchTarget"),
               c.getBoolean("FollowAim"),
               c.getBoolean("GuardBreak"),
               c.getBoolean("Ignite"),
               c.getBoolean("ShockSlow"),
               c.getBoolean("MakeInvulnerable"),
               c.getBoolean("GiveGuarded"),
               c.getBoolean("TrapTargets"),
               parseAction(c.getString("EntityAction")),
               parseNullableRL(c.getString("EntityId")),
               Math.max(1, c.getInt("SummonCount")),
               parseBlockAction(c.getString("BlockAction")),
               parseCastMode(c.getString("CastMode")),
               c.getBoolean("ProjectileCast"),
               c.getBoolean("SelfCast"),
               c.getBoolean("AoeCast")
            )
         );
      }

      return out;
   }

   public static boolean addCreatedMove(CompoundTag tag, VoidArchiveForgeData.CreatedMove move, int max) {
      if (move != null && move.name() != null && !move.name().isBlank()) {
         ListTag list = tag.getList("CreatedMoves", 10);
         CompoundTag c = new CompoundTag();
         c.putString("Name", move.name());
         c.putString("PrimaryDamage", safe(move.primaryDamageId()));
         c.putString("SecondaryDamage", safe(move.secondaryDamageId()));
         c.putString("EffectId", move.effectId() == null ? "" : move.effectId().toString());
         c.putInt("EffectAmplifier", Math.max(0, move.effectAmplifier()));
         c.putInt("EffectDuration", Math.max(0, move.effectDuration()));
         c.putString("Shape", move.shape().name());
         c.putString("ParticleStyle", move.particleStyle().name());
         c.putString("CustomParticleId", move.customParticleId() == null ? "" : move.customParticleId().toString());
         c.putInt("Range", Math.max(1, move.range()));
         c.putFloat("Radius", clamp(move.radius(), 0.5F, 24.0F));
         c.putFloat("PowerMultiplier", clamp(move.powerMultiplier(), 0.2F, 8.0F));
         c.putFloat("Speed", clamp(move.speed(), 0.2F, 5.0F));
         c.putInt("BurstCount", Math.max(1, Math.min(12, move.burstCount())));
         c.putFloat("BarrierBypass", clamp(move.barrierBypass(), 0.0F, 1.0F));
         c.putFloat("ResistanceBypass", clamp(move.resistanceBypass(), 0.0F, 1.0F));
         c.putFloat("MagiculeSteal", clamp(move.magiculeSteal(), 0.0F, 1.0F));
         c.putInt("SelfBuffDuration", Math.max(0, Math.min(1200, move.selfBuffDuration())));
         c.putBoolean("MultiHit", move.multiHit());
         c.putBoolean("HealCaster", move.healCaster());
         c.putBoolean("PullTarget", move.pullTarget());
         c.putBoolean("LaunchTarget", move.launchTarget());
         c.putBoolean("FollowAim", move.followAim());
         c.putBoolean("GuardBreak", move.guardBreak());
         c.putBoolean("Ignite", move.ignite());
         c.putBoolean("ShockSlow", move.shockSlow());
         c.putBoolean("MakeInvulnerable", move.makeInvulnerable());
         c.putBoolean("GiveGuarded", move.giveGuarded());
         c.putBoolean("TrapTargets", move.trapTargets());
         c.putString("EntityAction", move.entityAction().name());
         c.putString("EntityId", move.entityId() == null ? "" : move.entityId().toString());
         c.putInt("SummonCount", Math.max(1, Math.min(16, move.summonCount())));
         c.putString("BlockAction", move.blockAction().name());
         c.putString("CastMode", move.castMode().name());
         c.putBoolean("ProjectileCast", move.projectileCast());
         c.putBoolean("SelfCast", move.selfCast());
         c.putBoolean("AoeCast", move.aoeCast());
         list.add(c);

         while (list.size() > max) {
            list.remove(0);
         }

         tag.put("CreatedMoves", list);
         tag.putInt("ActiveCreatedMoveIndex", Math.max(0, list.size() - 1));
         normalizeEquipped(tag);
         return true;
      } else {
         return false;
      }
   }

   public static void removeCreatedMove(CompoundTag tag, int index) {
      ListTag list = tag.getList("CreatedMoves", 10);
      if (index >= 0 && index < list.size()) {
         list.remove(index);
         tag.put("CreatedMoves", list);
         ListTag equipped = new ListTag();

         for (int equippedIndex : getEquippedMoveIndices(tag)) {
            if (equippedIndex != index) {
               CompoundTag c = new CompoundTag();
               c.putInt("Index", equippedIndex > index ? equippedIndex - 1 : equippedIndex);
               equipped.add(c);
            }
         }

         tag.put("EquippedCreatedMoves", equipped);
         int size = list.size();
         tag.putInt("ActiveCreatedMoveIndex", size <= 0 ? 0 : Math.min(tag.getInt("ActiveCreatedMoveIndex"), size - 1));
      }
   }

   public static int getActiveMoveIndex(CompoundTag tag) {
      return tag.getInt("ActiveCreatedMoveIndex");
   }

   public static void setActiveMove(CompoundTag tag, int index) {
      if (index >= 0 && index < getCreatedMoves(tag).size()) {
         tag.putInt("ActiveCreatedMoveIndex", index);
      }
   }

   public static List<Integer> getEquippedMoveIndices(CompoundTag tag) {
      List<Integer> out = new ArrayList<>();
      int moveCount = getCreatedMoves(tag).size();

      for (Tag raw : tag.getList("EquippedCreatedMoves", 10)) {
         CompoundTag c = (CompoundTag)raw;
         int idx = c.getInt("Index");
         if (idx >= 0 && idx < moveCount && !out.contains(idx)) {
            out.add(idx);
         }
      }

      return out;
   }

   public static List<VoidArchiveForgeData.CreatedMove> getEquippedMoves(CompoundTag tag) {
      List<VoidArchiveForgeData.CreatedMove> all = getCreatedMoves(tag);
      List<VoidArchiveForgeData.CreatedMove> out = new ArrayList<>();

      for (int idx : getEquippedMoveIndices(tag)) {
         if (idx >= 0 && idx < all.size()) {
            out.add(all.get(idx));
         }
      }

      return out;
   }

   public static boolean equipMove(CompoundTag tag, int index, int maxEquipped) {
      List<VoidArchiveForgeData.CreatedMove> moves = getCreatedMoves(tag);
      if (index >= 0 && index < moves.size()) {
         List<Integer> equipped = getEquippedMoveIndices(tag);
         if (equipped.contains(index)) {
            return false;
         }

         equipped.add(index);

         while (equipped.size() > maxEquipped) {
            equipped.remove(0);
         }

         saveEquipped(tag, equipped);
         return true;
      } else {
         return false;
      }
   }

   public static void unequipMove(CompoundTag tag, int index) {
      List<Integer> equipped = getEquippedMoveIndices(tag);
      equipped.removeIf(i -> i == index);
      saveEquipped(tag, equipped);
   }

   public static boolean isMoveEquipped(CompoundTag tag, int index) {
      return getEquippedMoveIndices(tag).contains(index);
   }

   public static VoidArchiveForgeData.MeleeInfusion buildMeleeInfusion(
      String name,
      String damageId,
      @Nullable ResourceLocation effectId,
      int effectAmplifier,
      int effectDuration,
      float powerMultiplier,
      int durationTicks,
      boolean applyDamage,
      boolean applyEffect,
      boolean lifeSteal
   ) {
      return new VoidArchiveForgeData.MeleeInfusion(
         name != null && !name.isBlank() ? name : "Void Coat",
         damageId != null && !damageId.isBlank() ? damageId : "tensura:magic",
         effectId,
         Math.max(0, effectAmplifier),
         Math.max(0, effectDuration),
         clamp(powerMultiplier, 0.2F, 8.0F),
         Math.max(40, Math.min(2400, durationTicks)),
         applyDamage,
         applyEffect,
         lifeSteal
      );
   }

   public static VoidArchiveForgeData.CreatedMove buildMove(
      String name,
      String primaryDamageId,
      String secondaryDamageId,
      @Nullable ResourceLocation effectId,
      int effectAmplifier,
      int effectDuration,
      VoidArchiveForgeData.MoveShape shape,
      VoidArchiveForgeData.MoveParticleStyle particleStyle,
      @Nullable ResourceLocation customParticleId,
      int range,
      float radius,
      float powerMultiplier,
      float speed,
      int burstCount,
      float barrierBypass,
      float resistanceBypass,
      float magiculeSteal,
      int selfBuffDuration,
      boolean multiHit,
      boolean healCaster,
      boolean pullTarget,
      boolean launchTarget,
      boolean followAim,
      boolean guardBreak,
      boolean ignite,
      boolean shockSlow,
      boolean makeInvulnerable,
      boolean giveGuarded,
      boolean trapTargets,
      VoidArchiveForgeData.MoveEntityAction entityAction,
      @Nullable ResourceLocation entityId,
      int summonCount,
      VoidArchiveForgeData.MoveBlockAction blockAction,
      VoidArchiveForgeData.MoveCastMode castMode,
      boolean projectileCast,
      boolean selfCast,
      boolean aoeCast
   ) {
      return new VoidArchiveForgeData.CreatedMove(
         name != null && !name.isBlank() ? name : "Void Move",
         primaryDamageId != null && !primaryDamageId.isBlank() ? primaryDamageId : "tensura:magic",
         secondaryDamageId == null ? "" : secondaryDamageId,
         effectId,
         Math.max(0, effectAmplifier),
         Math.max(0, effectDuration),
         shape == null ? VoidArchiveForgeData.MoveShape.AOE : shape,
         particleStyle == null ? VoidArchiveForgeData.MoveParticleStyle.PORTAL : particleStyle,
         customParticleId,
         Math.max(1, range),
         clamp(radius, 0.5F, 24.0F),
         clamp(powerMultiplier, 0.2F, 8.0F),
         clamp(speed, 0.2F, 5.0F),
         Math.max(1, Math.min(12, burstCount)),
         clamp(barrierBypass, 0.0F, 1.0F),
         clamp(resistanceBypass, 0.0F, 1.0F),
         clamp(magiculeSteal, 0.0F, 1.0F),
         Math.max(0, Math.min(1200, selfBuffDuration)),
         multiHit,
         healCaster,
         pullTarget,
         launchTarget,
         followAim,
         guardBreak,
         ignite,
         shockSlow,
         makeInvulnerable,
         giveGuarded,
         trapTargets,
         entityAction == null ? VoidArchiveForgeData.MoveEntityAction.NONE : entityAction,
         entityId,
         Math.max(1, Math.min(16, summonCount)),
         blockAction == null ? VoidArchiveForgeData.MoveBlockAction.NONE : blockAction,
         castMode == null ? VoidArchiveForgeData.MoveCastMode.DEFAULT : castMode,
         projectileCast,
         selfCast,
         aoeCast
      );
   }

   public static boolean executeMove(
      ServerLevel level, LivingEntity caster, @Nullable ManasSkillInstance instance, VoidArchiveForgeData.CreatedMove move, float baseDamage
   ) {
      if (move == null) {
         return false;
      }

      ManasSkillInstance previousInstance = ACTIVE_GRIEF_INSTANCE.get();
      Entity previousOwner = ACTIVE_GRIEF_OWNER.get();
      ACTIVE_GRIEF_INSTANCE.set(instance);
      ACTIVE_GRIEF_OWNER.set(caster);

      try {
         float scaled = Math.max(1.0F, baseDamage * Math.max(0.2F, move.powerMultiplier()));
         applySelfBuffs(caster, move);
         if (move.trapTargets()) {
            Vec3 trapPos = snapTrapToGround(resolveTrapAnchor(caster, move));
            deployStoredTrap(level, caster, instance, move, trapPos, scaled);
            playCast(level, caster);
            return true;
         }

         if (move.shape() != VoidArchiveForgeData.MoveShape.SUMMON && move.entityAction() == VoidArchiveForgeData.MoveEntityAction.NONE) {
            return resolveSpell(level, caster, move, getCastCenter(caster, move), null, false);
         }

         Vec3 center = getCastCenter(caster, move);
         executeEntityAction(level, caster, move, center);
         applyBlockAction(level, caster, move, center, false, null);
         if (shouldSpawnVisualCircle(move, false, true)) {
            castVisualCircle(level, caster, move, center);
         }

         playCast(level, caster);
         spawnParticles(level, center, move, 20);
         return true;
      } finally {
         if (previousInstance == null) {
            ACTIVE_GRIEF_INSTANCE.remove();
         } else {
            ACTIVE_GRIEF_INSTANCE.set(previousInstance);
         }

         if (previousOwner == null) {
            ACTIVE_GRIEF_OWNER.remove();
         } else {
            ACTIVE_GRIEF_OWNER.set(previousOwner);
         }
      }
   }

   public static void tickDeployedTraps(ServerLevel level) {
      List<VoidArchiveForgeData.DeployedTrap> traps = ACTIVE_TRAPS.get(level.dimension());
      if (traps != null && !traps.isEmpty()) {
         Iterator<VoidArchiveForgeData.DeployedTrap> iterator = traps.iterator();

         while (iterator.hasNext()) {
            VoidArchiveForgeData.DeployedTrap trap = iterator.next();
            trap.lifeTicks--;
            if (trap.armTicks > 0) {
               trap.armTicks--;
            }

            if (level.getEntity(trap.ownerId) instanceof LivingEntity caster && caster.isAlive() && trap.lifeTicks > 0) {
               if (trap.move.followAim() && trap.armTicks > 0) {
                  updateTrapAnchor(level, trap, snapTrapToGround(resolveTrapAnchor(caster, trap.move)));
               }

               if (trap.circleUuid != null) {
                  if (level.getEntity(trap.circleUuid) instanceof MagicCircle circle) {
                     circle.setPos(trap.position);
                  } else {
                     trap.circleUuid = null;
                  }
               }

               spawnTrapIdleParticles(level, trap);
               if (trap.armTicks <= 0) {
                  double radius = Math.max(0.9, trap.triggerRadius);
                  AABB triggerBox = new AABB(
                     trap.position.x - radius,
                     trap.position.y - 0.75,
                     trap.position.z - radius,
                     trap.position.x + radius,
                     trap.position.y + 2.2,
                     trap.position.z + radius
                  );
                  List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, triggerBox, entity -> isValidTrapTarget(entity, trap.ownerId))
                     .stream()
                     .filter(entity -> intersectsTrap(entity, trap.position, radius))
                     .toList();
                  if (!targets.isEmpty()) {
                     triggerTrap(level, caster, trap, targets);
                     iterator.remove();
                  }
               }
            } else {
               discardTrap(level, trap);
               iterator.remove();
            }
         }

         if (traps.isEmpty()) {
            ACTIVE_TRAPS.remove(level.dimension());
         }
      }
   }

   public static void applyMeleeInfusionHit(
      ServerLevel level, LivingEntity attacker, LivingEntity target, VoidArchiveForgeData.MeleeInfusion infusion, float baseDamage
   ) {
      if (infusion != null && attacker != null && target != null && attacker.isAlive() && target.isAlive()) {
         if (beginInfusionProc(attacker, target, infusion)) {
            try {
               float damage = Math.max(1.0F, baseDamage * infusion.powerMultiplier());
               if (infusion.applyDamage()) {
                  DamageSource source = makeDamageSource(level, attacker, infusion.damageId());
                  target.hurt(source, damage);
               }

               if (infusion.applyEffect() && infusion.effectId() != null) {
                  MobEffect effect = (MobEffect)BuiltInRegistries.MOB_EFFECT.get(infusion.effectId());
                  if (effect != null) {
                     Optional<ResourceKey<MobEffect>> keyOptional = BuiltInRegistries.MOB_EFFECT.getResourceKey(effect);
                     if (keyOptional.isPresent()) {
                        Optional<Reference<MobEffect>> holderOptional = BuiltInRegistries.MOB_EFFECT.getHolder(keyOptional.get());
                        holderOptional.ifPresent(
                           reference -> target.addEffect(
                              new MobEffectInstance(
                                 reference, Math.max(40, infusion.effectDuration()), Math.max(0, infusion.effectAmplifier()), true, true, true
                              )
                           )
                        );
                     }
                  }
               }

               applyInfusionSynergy(level, attacker, target, infusion, damage);
               if (infusion.lifeSteal()) {
                  attacker.heal(Math.max(1.0F, damage * 0.15F));
               }

               spawnInfusionParticles(level, target.position().add(0.0, target.getBbHeight() * 0.5, 0.0), infusion);
            } finally {
               endInfusionProc(attacker, target, infusion);
            }
         }
      }
   }

   private static boolean resolveSpell(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, @Nullable LivingEntity focus, boolean triggered
   ) {
      int burst = Math.max(1, move.burstCount());

      boolean didSomething = switch (resolveExecutionShape(move)) {
         case TOUCH -> executeTouch(level, caster, move, center, focus, burst);
         case AOE, NOVA, WALL, RING, DOME, RAIN, SIGIL, PILLAR -> executeAreaPattern(level, caster, move, center, focus, burst, triggered);
         case BEAM -> executeBeam(level, caster, move, center, focus, burst, triggered);
         case ORB -> executeOrb(level, caster, move, center, focus, burst, triggered);
         case AURA -> executeAura(level, caster, move, center, focus, burst);
         default -> executeAreaPattern(level, caster, move, center, focus, burst, triggered);
         case LINE -> executeLine(level, caster, move, center, focus, burst, triggered);
      };
      applyBlockAction(level, caster, move, center, triggered, focus);
      if (shouldSpawnVisualCircle(move, triggered, false)) {
         castVisualCircle(level, caster, move, center);
      }

      spawnParticles(level, center, move, triggered ? 18 : 24);
      playCast(level, caster);
      return didSomething || move.selfCast() || move.aoeCast() || move.blockAction() != VoidArchiveForgeData.MoveBlockAction.NONE;
   }

   private static VoidArchiveForgeData.MoveShape resolveExecutionShape(VoidArchiveForgeData.CreatedMove move) {
      if (!move.projectileCast() && move.castMode() != VoidArchiveForgeData.MoveCastMode.PROJECTILE) {
         if (!move.selfCast() && move.castMode() != VoidArchiveForgeData.MoveCastMode.SELF
            || move.shape() != VoidArchiveForgeData.MoveShape.BEAM
               && move.shape() != VoidArchiveForgeData.MoveShape.LINE
               && move.shape() != VoidArchiveForgeData.MoveShape.ORB) {
            return (move.aoeCast() || move.castMode() == VoidArchiveForgeData.MoveCastMode.TARGET_AREA) && move.shape() == VoidArchiveForgeData.MoveShape.TOUCH
               ? VoidArchiveForgeData.MoveShape.AOE
               : move.shape();
         } else {
            return VoidArchiveForgeData.MoveShape.AURA;
         }
      } else if (move.shape() == VoidArchiveForgeData.MoveShape.AOE
         || move.shape() == VoidArchiveForgeData.MoveShape.NOVA
         || move.shape() == VoidArchiveForgeData.MoveShape.RAIN
         || move.shape() == VoidArchiveForgeData.MoveShape.PILLAR) {
         return VoidArchiveForgeData.MoveShape.ORB;
      } else {
         return move.shape() != VoidArchiveForgeData.MoveShape.RING
               && move.shape() != VoidArchiveForgeData.MoveShape.DOME
               && move.shape() != VoidArchiveForgeData.MoveShape.SIGIL
            ? move.shape()
            : VoidArchiveForgeData.MoveShape.ORB;
      }
   }

   private static Vec3 getCastCenter(LivingEntity caster, VoidArchiveForgeData.CreatedMove move) {
      Vec3 eye = caster.getEyePosition();
      Vec3 look = caster.getLookAngle().normalize();

      return switch (move.castMode()) {
         case SELF -> caster.position().add(0.0, caster.getBbHeight() * 0.5, 0.0);
         case PROJECTILE -> eye.add(look.scale(Math.max(1.0, move.range() * 0.7)));
         case TARGET_AREA -> eye.add(look.scale(Math.max(2.0, move.range())));
         default -> move.selfCast() ? caster.position().add(0.0, caster.getBbHeight() * 0.5, 0.0) : eye.add(look.scale(Math.max(1.5, move.range() * 0.8)));
      };
   }

   private static Vec3 resolveTrapAnchor(LivingEntity caster, VoidArchiveForgeData.CreatedMove move) {
      Vec3 eye = caster.getEyePosition();
      Vec3 look = caster.getLookAngle().normalize();

      double distance = switch (move.castMode()) {
         case SELF -> 0.0;
         case PROJECTILE -> Math.max(1.0, move.range() * 0.8);
         case TARGET_AREA -> Math.max(2.0, move.range());
         default -> move.shape() != VoidArchiveForgeData.MoveShape.TOUCH && !move.selfCast() ? Math.max(1.5, move.range() * 0.8) : 0.0;
      };
      return distance <= 0.0 ? caster.position().add(0.0, 0.05, 0.0) : eye.add(look.scale(distance));
   }

   private static VoidArchiveForgeData.CreatedMove triggerVersion(VoidArchiveForgeData.CreatedMove move) {
      return new VoidArchiveForgeData.CreatedMove(
         move.name(),
         move.primaryDamageId(),
         move.secondaryDamageId(),
         move.effectId(),
         move.effectAmplifier(),
         move.effectDuration(),
         move.shape(),
         move.particleStyle(),
         move.customParticleId(),
         move.range(),
         move.radius(),
         move.powerMultiplier(),
         move.speed(),
         move.burstCount(),
         move.barrierBypass(),
         move.resistanceBypass(),
         move.magiculeSteal(),
         move.selfBuffDuration(),
         move.multiHit(),
         move.healCaster(),
         move.pullTarget(),
         move.launchTarget(),
         false,
         move.guardBreak(),
         move.ignite(),
         move.shockSlow(),
         move.makeInvulnerable(),
         move.giveGuarded(),
         false,
         move.entityAction(),
         move.entityId(),
         move.summonCount(),
         move.blockAction(),
         VoidArchiveForgeData.MoveCastMode.TARGET_AREA,
         false,
         false,
         true
      );
   }

   private static void applySelfBuffs(LivingEntity caster, VoidArchiveForgeData.CreatedMove move) {
      int duration = Math.max(40, move.selfBuffDuration() + Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 40.0F));
      if (move.makeInvulnerable()) {
         caster.invulnerableTime = Math.max(caster.invulnerableTime, 60 + Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 20.0F));
      }

      if (move.giveGuarded()) {
         int amp = move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.LIGHT
               && move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.BLOSSOM
            ? 2
            : 3;
         caster.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.GUARDED), duration, amp, false, false, true));
      }

      if (move.healCaster()) {
         caster.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION),
               duration,
               move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOSSOM ? 2 : 1,
               false,
               false,
               true
            )
         );
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.ENCHANT) {
         caster.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.REINFORCEMENT), duration, move.multiHit() ? 2 : 1, false, false, true)
         );
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.LIGHT && move.makeInvulnerable()) {
         caster.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PROTECTION), duration, 1, false, false, true));
      }

      if ((move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.DARK || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.PORTAL)
         && move.magiculeSteal() > 0.0F) {
         caster.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_AURA), duration, move.magiculeSteal() >= 0.5F ? 2 : 1, false, false, true
            )
         );
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.WIND && move.launchTarget()) {
         caster.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SHADOW_STEP), Math.max(40, duration / 2), 0, false, false, true)
         );
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOCK && move.blockAction() != VoidArchiveForgeData.MoveBlockAction.NONE) {
         caster.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.PROTECTION), Math.max(50, duration / 2), move.guardBreak() ? 1 : 0, false, false, true
            )
         );
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.AURA) {
         caster.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_AURA),
               Math.max(40, duration / 2),
               move.magiculeSteal() > 0.0F ? 1 : 0,
               false,
               false,
               true
            )
         );
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.BEAM) {
         caster.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.REINFORCEMENT), Math.max(40, duration / 2), 0, false, false, true)
         );
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.PILLAR) {
         caster.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PROTECTION), Math.max(40, duration / 2), 0, false, false, true)
         );
      }
   }

   private static boolean executeTouch(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, @Nullable LivingEntity focus, int burst
   ) {
      LivingEntity target = focus != null && focus.isAlive() ? focus : findClosestTarget(level, caster, center, Math.max(1.75, move.radius() + 1.75));
      if (target == null) {
         return false;
      }

      boolean hit = false;

      for (int wave = 0; wave < burst; wave++) {
         float waveDamage = getBaseDamage(move) * (wave == 0 ? 1.1F : getRepeatDamageScale(move) + 0.08F);
         applyMoveDamage(level, caster, target, move, waveDamage);
         applyMoveEffect(target, move);
         applyMotion(caster, target, move, target.position());
         applyExtraFlags(target, move);
         if (move.shape() == VoidArchiveForgeData.MoveShape.TOUCH && move.guardBreak()) {
            target.invulnerableTime = Math.max(0, target.invulnerableTime - 4);
         }

         spawnParticles(level, target.position().add(0.0, target.getBbHeight() * 0.5, 0.0), move, 10 + move.range() / 2);
         if (move.healCaster()) {
            caster.heal(Math.max(1.0F, waveDamage * 0.16F));
         }

         hit = true;
      }

      return hit;
   }

   private static boolean executeBeam(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, @Nullable LivingEntity focus, int burst, boolean triggered
   ) {
      Vec3 start = triggered ? center : caster.getEyePosition().add(caster.getLookAngle().normalize().scale(0.65));
      Vec3 direction = triggered && focus != null ? focus.position().add(0.0, focus.getBbHeight() * 0.5, 0.0).subtract(start) : caster.getLookAngle();
      if (direction.lengthSqr() < 1.0E-4) {
         direction = caster.getLookAngle();
      }

      direction = direction.normalize();
      double range = Math.max(1.0, move.range());
      boolean hit = false;

      for (int wave = 0; wave < burst; wave++) {
         Vec3 waveDir = rotateFlat(direction, (wave - (burst - 1) * 0.5) * 3.5);
         double beamRadius = Math.max(0.55, move.radius() * (0.45 + wave * 0.08));

         for (int step = 1; step <= Math.max(4, move.range() * 3); step++) {
            double progress = range * step / Math.max(4.0, move.range() * 3.0);
            Vec3 point = start.add(waveDir.scale(progress));
            spawnParticles(level, point, move, 3 + move.burstCount());
            AABB box = new AABB(point, point).inflate(beamRadius, Math.max(0.55, beamRadius * 0.6), beamRadius);

            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != caster && entity.isAlive())) {
               float waveDamage = getBaseDamage(move) * (wave == 0 ? 1.0F : getRepeatDamageScale(move));
               applyMoveDamage(level, caster, target, move, waveDamage);
               applyMoveEffect(target, move);
               applyMotion(caster, target, move, point);
               applyExtraFlags(target, move);
               hit = true;
            }
         }
      }

      if (move.blockAction() != VoidArchiveForgeData.MoveBlockAction.NONE || move.aoeCast() || move.castMode() == VoidArchiveForgeData.MoveCastMode.TARGET_AREA
         )
       {
         Vec3 end = start.add(direction.scale(range));
         hit |= applyAreaBurst(level, caster, move, end, getBaseDamage(move) * 0.9F, Math.max(1.1, move.radius()));
      }

      return hit;
   }

   private static boolean executeOrb(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, @Nullable LivingEntity focus, int burst, boolean triggered
   ) {
      boolean hit = false;
      double radius = Math.max(1.0, move.radius());
      if (!triggered) {
         Vec3 eye = caster.getEyePosition();
         Vec3 look = caster.getLookAngle().normalize();
         double range = Math.max(1.0, move.range());
         int travelSteps = Math.max(3, move.range() * 2);

         for (int step = 1; step <= travelSteps; step++) {
            Vec3 point = eye.add(look.scale(range * step / Math.max(3.0, move.range() * 2.0)));
            spawnParticles(level, point, move, 4);
         }
      }

      for (int wave = 0; wave < burst; wave++) {
         Vec3 waveCenter = center;
         if (burst > 1) {
            double angle = (Math.PI * 2) * wave / burst;
            waveCenter = center.add(Math.cos(angle) * radius * 0.45, 0.0, Math.sin(angle) * radius * 0.45);
         }

         hit |= applyAreaBurst(level, caster, move, waveCenter, getBaseDamage(move) * (wave == 0 ? 1.0F : getRepeatDamageScale(move)), radius);
      }

      return hit;
   }

   private static boolean executeLine(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, @Nullable LivingEntity focus, int burst, boolean triggered
   ) {
      Vec3 direction = triggered && focus != null ? focus.position().subtract(center) : caster.getLookAngle();
      if (direction.lengthSqr() < 1.0E-4) {
         direction = caster.getLookAngle();
      }

      direction = direction.normalize();
      Vec3 origin = triggered ? center : caster.getEyePosition().add(direction.scale(0.8));
      Vec3 side = new Vec3(-direction.z, 0.0, direction.x).normalize();
      boolean hit = false;

      for (int wave = 0; wave < burst; wave++) {
         Vec3 offset = side.scale((wave - (burst - 1) * 0.5) * Math.max(0.5, move.radius() * 0.35));

         for (int step = 1; step <= Math.max(5, move.range()); step++) {
            Vec3 point = origin.add(direction.scale(step)).add(offset);
            AABB box = new AABB(point, point).inflate(Math.max(0.7, move.radius() * 0.35), 0.7, Math.max(0.7, move.radius() * 0.35));
            spawnParticles(level, point, move, 4);

            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != caster && entity.isAlive())) {
               float waveDamage = getBaseDamage(move) * (wave == 0 ? 1.0F : getRepeatDamageScale(move));
               applyMoveDamage(level, caster, target, move, waveDamage);
               applyMoveEffect(target, move);
               applyMotion(caster, target, move, point);
               applyExtraFlags(target, move);
               hit = true;
            }

            if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.SPIKE_FIELD) {
               placeSpikeBlock(level, BlockPos.containing(point));
            }

            if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.ICE_FIELD && step % 2 == 0) {
               placeArcaneBlock(level, BlockPos.containing(point).below(), Blocks.BLUE_ICE.defaultBlockState());
            }
         }
      }

      return hit;
   }

   private static boolean executeAura(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, @Nullable LivingEntity focus, int burst
   ) {
      boolean hit = false;
      double radius = Math.max(1.35, move.radius() + move.burstCount() * 0.15);
      AABB box = new AABB(center, center).inflate(radius + move.range() * 0.15, radius, radius + move.range() * 0.15);

      for (int wave = 0; wave < burst; wave++) {
         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != caster && entity.isAlive())) {
            float waveDamage = getBaseDamage(move) * (wave == 0 ? 0.9F : getRepeatDamageScale(move));
            applyMoveDamage(level, caster, target, move, waveDamage);
            applyMoveEffect(target, move);
            applyMotion(caster, target, move, center);
            applyExtraFlags(target, move);
            hit = true;
         }
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.ENCHANT
         || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOSSOM
         || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.LIGHT) {
         applySelfBuffs(caster, move);
      }

      spawnRingParticles(level, center, move, radius, 16 + move.range());
      return hit;
   }

   private static boolean executeAreaPattern(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, @Nullable LivingEntity focus, int burst, boolean triggered
   ) {
      double radius = Math.max(1.0, move.radius());
      boolean hit;
      switch (move.shape()) {
         case AOE:
            spawnNovaParticles(level, center, move, radius, 20 + move.range());
            hit = false;

            for (int wave = 0; wave < burst; wave++) {
               double shrink = Math.max(0.75, 1.0 - wave * 0.1);
               hit |= applyAreaBurst(level, caster, move, center, getBaseDamage(move) * (wave == 0 ? 1.0F : getRepeatDamageScale(move)), radius * shrink);
            }
            break;
         case BEAM:
         case ORB:
         case AURA:
         case SUMMON:
         case LINE:
         default:
            spawnNovaParticles(level, center, move, radius, 18 + move.range());
            hit = applyAreaBurst(level, caster, move, center, getBaseDamage(move), radius);
            break;
         case NOVA:
            spawnNovaParticles(level, center, move, radius, 20 + move.range());
            hit = false;

            for (int wave = 0; wave < burst; wave++) {
               hit |= applyAreaBurst(level, caster, move, center, getBaseDamage(move) * (wave == 0 ? 1.0F : getRepeatDamageScale(move)), radius + wave * 0.35);
            }
            break;
         case WALL:
            Vec3 look = triggered && focus != null ? focus.position().subtract(center).normalize() : caster.getLookAngle().normalize();
            spawnWallParticles(level, center, look, move, radius, 18 + move.range());
            hit = false;

            for (int wave = 0; wave < burst; wave++) {
               Vec3 waveCenter = center.add(look.scale(wave * 0.75));
               hit |= applyWallHit(
                  level, caster, move, waveCenter, getBaseDamage(move) * (wave == 0 ? 1.0F : getRepeatDamageScale(move)), radius + wave * 0.35, 1, look
               );
            }
            break;
         case RING:
            spawnRingParticles(level, center, move, radius, 20 + move.range());
            hit = false;

            for (int wave = 0; wave < burst; wave++) {
               hit |= applyRingDamage(
                  level, caster, move, center, getBaseDamage(move) * (wave == 0 ? 1.0F : getRepeatDamageScale(move)), radius + wave * 0.45, 1
               );
            }
            break;
         case DOME:
            spawnDomeParticles(level, center, move, radius, 18 + move.range());
            hit = false;

            for (int wave = 0; wave < burst; wave++) {
               hit |= applyAreaBurst(
                  level,
                  caster,
                  move,
                  center.add(0.0, wave * 0.2, 0.0),
                  getBaseDamage(move) * (wave == 0 ? 1.0F : getRepeatDamageScale(move)),
                  radius * (1.0 + wave * 0.08)
               );
            }

            applyDomeLift(level, center, radius);
            break;
         case RAIN:
            spawnRainParticles(level, center, move, radius, 18 + move.range());
            hit = applyRainStrikes(level, caster, move, center, getBaseDamage(move), radius, burst);
            break;
         case SIGIL:
            spawnSigilParticles(level, center, move, radius, 20 + move.range());
            hit = false;

            for (int wave = 0; wave < burst; wave++) {
               hit |= applyAreaBurst(level, caster, move, center, getBaseDamage(move) * (wave == 0 ? 0.95F : getRepeatDamageScale(move)), radius);
            }
            break;
         case PILLAR:
            spawnPillarParticles(level, center, move, radius, 18 + move.range());
            hit = false;

            for (int wave = 0; wave < burst; wave++) {
               double angle = burst <= 1 ? 0.0 : (Math.PI * 2) * wave / burst;
               Vec3 point = burst <= 1 ? center : center.add(Math.cos(angle) * radius * 0.55, 0.0, Math.sin(angle) * radius * 0.55);
               hit |= applyPillarBurst(
                  level, caster, move, point, getBaseDamage(move) * (wave == 0 ? 1.0F : getRepeatDamageScale(move)), Math.max(0.9, radius * 0.7), 1
               );
            }
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOCK && move.blockAction() == VoidArchiveForgeData.MoveBlockAction.NONE) {
         placeArcaneBlock(level, BlockPos.containing(center).below(), Blocks.CRYING_OBSIDIAN.defaultBlockState());
      }

      return hit;
   }

   private static boolean applyAreaBurst(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, float damage, double radius
   ) {
      boolean hit = false;
      AABB box = new AABB(center, center).inflate(radius, radius * 0.85 + 0.75, radius);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != caster && entity.isAlive())) {
         Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
         if (!(targetCenter.distanceTo(center) > radius + target.getBbWidth() * 0.6)) {
            applyMoveDamage(level, caster, target, move, damage);
            applyMoveEffect(target, move);
            applyMotion(caster, target, move, center);
            applyExtraFlags(target, move);
            spawnParticles(level, targetCenter, move, 8 + move.range() / 2);
            if (move.shape() == VoidArchiveForgeData.MoveShape.NOVA && move.launchTarget()) {
               target.setDeltaMovement(target.getDeltaMovement().add(0.0, 0.35 + radius * 0.05 + Math.max(0.0, move.powerMultiplier() - 1.0) * 0.08, 0.0));
               target.hurtMarked = true;
            }

            if (move.shape() == VoidArchiveForgeData.MoveShape.AOE
               && move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.PORTAL
               && move.pullTarget()) {
               Vec3 inward = center.subtract(targetCenter);
               if (inward.lengthSqr() > 1.0E-4) {
                  target.setDeltaMovement(target.getDeltaMovement().add(inward.normalize().scale(0.25)));
                  target.hurtMarked = true;
               }
            }

            if (move.shape() == VoidArchiveForgeData.MoveShape.SIGIL) {
               target.addEffect(
                  new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), 60, move.guardBreak() ? 1 : 0, false, false, true)
               );
            }

            if (move.healCaster()) {
               caster.heal(Math.max(1.0F, damage * 0.12F));
            }

            hit = true;
         }
      }

      return hit;
   }

   private static boolean applyRingDamage(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, float damage, double radius, int burst
   ) {
      boolean hit = false;
      AABB box = new AABB(center, center).inflate(radius + 1.5, 2.0, radius + 1.5);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != caster && entity.isAlive())) {
         double dist = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0).distanceTo(center);
         if (!(dist < radius * 0.55) && !(dist > radius * 1.35)) {
            for (int wave = 0; wave < burst; wave++) {
               applyMoveDamage(level, caster, target, move, wave == 0 ? damage : damage * getRepeatDamageScale(move));
               applyMoveEffect(target, move);
               applyMotion(caster, target, move, center);
               applyExtraFlags(target, move);
            }

            hit = true;
         }
      }

      return hit;
   }

   private static boolean applyRainStrikes(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, float damage, double radius, int burst
   ) {
      boolean hit = false;
      int strikes = Math.max(5, move.burstCount() * 3 + Mth.floor(radius));

      for (int i = 0; i < strikes; i++) {
         double angle = (Math.PI * 2) * i / Math.max(1, strikes);
         double dist = radius * (0.25 + 0.75 * (i % 4 / 3.0));
         Vec3 strike = center.add(Math.cos(angle) * dist, 0.0, Math.sin(angle) * dist);
         spawnPillarParticles(level, strike, move, Math.max(0.8, radius * 0.35), 8 + move.range());

         for (int wave = 0; wave < burst; wave++) {
            hit |= applyAreaBurst(
               level,
               caster,
               move,
               strike.add(0.0, wave * 0.1, 0.0),
               wave == 0 ? damage * 0.7F : damage * getRepeatDamageScale(move),
               Math.max(1.0, move.radius() * 0.45)
            );
         }

         if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.WATER || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SNOW) {
            placeArcaneBlock(
               level,
               BlockPos.containing(strike).below(),
               move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SNOW
                  ? Blocks.PACKED_ICE.defaultBlockState()
                  : Blocks.BLUE_ICE.defaultBlockState()
            );
         }
      }

      return hit;
   }

   private static boolean applyPillarBurst(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, float damage, double radius, int burst
   ) {
      boolean hit = false;
      AABB box = new AABB(center, center).inflate(radius * 0.75 + 0.75, radius * 2.0 + 2.0, radius * 0.75 + 0.75);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != caster && entity.isAlive())) {
         for (int wave = 0; wave < burst; wave++) {
            applyMoveDamage(level, caster, target, move, wave == 0 ? damage : damage * getRepeatDamageScale(move));
            applyMoveEffect(target, move);
            applyMotion(caster, target, move, center);
            applyExtraFlags(target, move);
            if (move.launchTarget()) {
               double extra = Math.max(0.0, move.powerMultiplier() - 1.0) * 0.1;
               target.setDeltaMovement(target.getDeltaMovement().add(0.0, 0.5 + radius * 0.08 + extra, 0.0));
               target.hurtMarked = true;
            }
         }

         hit = true;
      }

      return hit;
   }

   private static boolean applyWallHit(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center, float damage, double radius, int burst, Vec3 look
   ) {
      boolean hit = false;
      Vec3 side = new Vec3(-look.z, 0.0, look.x).normalize();

      for (int i = -Mth.ceil((float)(radius * 1.4)); i <= Mth.ceil((float)(radius * 1.4)); i++) {
         Vec3 point = center.add(side.scale(i));
         AABB box = new AABB(point, point).inflate(0.7, radius * 0.8 + 0.7, 0.7);

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != caster && entity.isAlive())) {
            for (int wave = 0; wave < burst; wave++) {
               applyMoveDamage(level, caster, target, move, wave == 0 ? damage : damage * getRepeatDamageScale(move));
               applyMoveEffect(target, move);
               applyMotion(caster, target, move, center);
               applyExtraFlags(target, move);
            }

            hit = true;
         }
      }

      return hit;
   }

   private static LivingEntity findClosestTarget(ServerLevel level, LivingEntity caster, Vec3 center, double radius) {
      LivingEntity best = null;
      double bestDistance = Double.MAX_VALUE;
      AABB box = new AABB(center, center).inflate(radius, radius, radius);

      for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, box, input -> input != caster && input.isAlive())) {
         double distance = entity.position().distanceToSqr(center);
         if (distance < bestDistance) {
            bestDistance = distance;
            best = entity;
         }
      }

      return best;
   }

   private static float getBaseDamage(VoidArchiveForgeData.CreatedMove move) {
      return Math.max(1.0F, 12.0F * Math.max(0.2F, move.powerMultiplier()));
   }

   private static void applyMoveDamage(ServerLevel level, LivingEntity caster, LivingEntity target, VoidArchiveForgeData.CreatedMove move, float damage) {
      float barrierBypass = getScaledBarrierBypass(move);
      float resistanceBypass = getScaledResistanceBypass(move);
      DamageSource primary = setBypass(makeDamageSource(level, caster, move.primaryDamageId()), barrierBypass, resistanceBypass);
      if (move.multiHit() && move.guardBreak()) {
         DamageSource secondaryCombo = setBypass(
            makeDamageSource(
               level, caster, move.secondaryDamageId() != null && !move.secondaryDamageId().isBlank() ? move.secondaryDamageId() : move.primaryDamageId()
            ),
            clamp(barrierBypass + 0.12F, 0.0F, 1.0F),
            clamp(resistanceBypass + 0.1F, 0.0F, 1.0F)
         );
         TensuraDamageHelper.hurtDouble(target, primary, damage, secondaryCombo, Math.max(1.0F, damage * 0.45F));
      } else if (move.multiHit()) {
         DamageSource split = setBypass(
            makeDamageSource(
               level, caster, move.secondaryDamageId() != null && !move.secondaryDamageId().isBlank() ? move.secondaryDamageId() : move.primaryDamageId()
            ),
            Math.max(barrierBypass, barrierBypass * 0.8F),
            Math.max(resistanceBypass, resistanceBypass * 0.8F)
         );
         TensuraDamageHelper.hurtSplit(target, primary, 0.62F, split, damage * 1.35F);
      } else {
         target.hurt(primary, damage);
      }

      if (move.secondaryDamageId() != null && !move.secondaryDamageId().isBlank() && !move.multiHit()) {
         DamageSource secondary = setBypass(
            makeDamageSource(level, caster, move.secondaryDamageId()),
            Math.max(barrierBypass, barrierBypass * 0.8F),
            Math.max(resistanceBypass, resistanceBypass * 0.8F)
         );
         target.hurt(secondary, Math.max(1.0F, damage * (move.guardBreak() ? 0.55F : 0.45F)));
      }

      if (move.magiculeSteal() > 0.0F) {
         float siphonBonus = move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.BLOSSOM
               && move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.LIGHT
            ? 0.0F
            : 0.08F;
         if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SOUL
            || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.DARK
            || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.PORTAL) {
            siphonBonus += 0.07F;
         }

         if (move.shape() == VoidArchiveForgeData.MoveShape.TOUCH
            || move.shape() == VoidArchiveForgeData.MoveShape.AURA
            || move.shape() == VoidArchiveForgeData.MoveShape.SIGIL) {
            siphonBonus += 0.05F;
         }

         caster.heal(Math.max(1.0F, damage * clamp(move.magiculeSteal() + siphonBonus, 0.0F, 1.0F) * 0.25F));
      }
   }

   private static DamageSource setBypass(DamageSource source, float barrier, float resistance) {
      TensuraDamageSource tensura = (TensuraDamageSource)source;
      DamageSource out = tensura.tensura$setBarrierBypassLevel(barrier);
      return ((TensuraDamageSource)out).tensura$setResistanceBypassLevel(resistance);
   }

   private static DamageSource makeDamageSource(ServerLevel level, LivingEntity caster, String id) {
      if (id != null && !id.isBlank()) {
         return switch (id) {
            case "tensura:sever" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.SEVERANCE, caster);
            case "tensura:space" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.DIMENSION_RAY, caster);
            case "tensura:dark" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MIND_CRUSH, caster);
            case "tensura:light" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.WICKED_LIGHT_RAY, caster);
            case "minecraft:fire" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.FLAME_BREATH, caster);
            case "tensura:water" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.WATER_BLADE, caster);
            case "tensura:wind" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.WIND_BREATH, caster);
            case "tensura:earth" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.GRAVITY_PRESS, caster);
            case "tensura:lightning" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.LIGHTNING, caster);
            case "tensura:drain" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.ENERGY_DRAIN, caster);
            case "minecraft:physical" -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.AURA_SLASH, caster);
            default -> TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, caster);
         };
      } else {
         return TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, caster);
      }
   }

   private static void applyMoveEffect(LivingEntity target, VoidArchiveForgeData.CreatedMove move) {
      if (move.effectId() != null) {
         MobEffect effect = (MobEffect)BuiltInRegistries.MOB_EFFECT.get(move.effectId());
         if (effect != null) {
            Optional<ResourceKey<MobEffect>> keyOptional = BuiltInRegistries.MOB_EFFECT.getResourceKey(effect);
            if (keyOptional.isPresent()) {
               Optional<Reference<MobEffect>> holderOptional = BuiltInRegistries.MOB_EFFECT.getHolder(keyOptional.get());
               if (holderOptional.isPresent()) {
                  int ampBonus = switch (move.particleStyle()) {
                     case PORTAL, DARK -> move.pullTarget() ? 1 : 0;
                     case FLAME, BLACK_FIRE -> move.ignite() ? 1 : 0;
                     case SOUL -> move.magiculeSteal() >= 0.4F ? 1 : 0;
                     case LIGHTNING, ARCANE -> 1;
                     case WATER, SNOW -> {
                        if (move.blockAction() != VoidArchiveForgeData.MoveBlockAction.ICE_FIELD
                           && move.blockAction() != VoidArchiveForgeData.MoveBlockAction.GLASS_DOME) {
                           yield 0;
                        }

                        yield 1;
                     }
                     case WIND -> move.launchTarget() ? 1 : 0;
                     case LIGHT, BLOSSOM -> move.giveGuarded() ? 1 : 0;
                     case ENCHANT -> move.multiHit() ? 1 : 0;
                     default -> 0;
                     case BLOCK -> move.blockAction() != VoidArchiveForgeData.MoveBlockAction.NONE ? 1 : 0;
                     case SHOCKWAVE -> !move.launchTarget() && !move.pullTarget() ? 0 : 1;
                  };

                  int durationBonus = switch (move.blockAction()) {
                     case ICE_FIELD, GLASS_DOME -> 40;
                     default -> 0;
                     case SPIKE_FIELD -> 20;
                     case VOID_CRATER -> move.pullTarget() ? 20 : 0;
                  };
                  if (move.multiHit()) {
                     durationBonus += 10;
                  }

                  if (move.guardBreak()) {
                     durationBonus += 15;
                  }

                  if (move.shape() == VoidArchiveForgeData.MoveShape.SIGIL) {
                     durationBonus += 20;
                  }

                  target.addEffect(
                     new MobEffectInstance(
                        (Holder)holderOptional.get(),
                        Math.max(40, move.effectDuration() + durationBonus),
                        Math.max(0, move.effectAmplifier() + ampBonus),
                        true,
                        true,
                        true
                     )
                  );
               }
            }
         }
      }

      applyParticleSynergy(target, move);
   }

   private static void applyMotion(LivingEntity caster, LivingEntity target, VoidArchiveForgeData.CreatedMove move, Vec3 origin) {
      Vec3 delta = target.position().subtract(origin);
      if (delta.lengthSqr() < 1.0E-4) {
         delta = caster.getLookAngle();
      }

      delta = delta.normalize();
      double power = Math.max(0.2, move.powerMultiplier());
      double pull = getScaledPull(move);
      double launch = getScaledLaunch(move);
      if (move.pullTarget()) {
         double vertical = 0.08
            + power * 0.05
            + (
               move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.PORTAL && move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.DARK
                  ? 0.0
                  : 0.05
            );
         if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.WATER) {
            vertical += 0.05;
         }

         target.setDeltaMovement(target.getDeltaMovement().add(delta.scale(-pull)).add(0.0, vertical, 0.0));
         target.hurtMarked = true;
      }

      if (move.launchTarget()) {
         double horizontalBurst = switch (move.particleStyle()) {
            case FLAME -> 0.06;
            default -> 0.0;
            case LIGHTNING, ARCANE -> 0.1;
            case WIND -> 0.18;
            case SHOCKWAVE -> 0.16;
         };
         target.setDeltaMovement(target.getDeltaMovement().add(delta.scale(horizontalBurst)).add(0.0, launch, 0.0));
         target.hurtMarked = true;
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.WALL) {
         target.setDeltaMovement(target.getDeltaMovement().add(delta.scale(0.05)));
         target.hurtMarked = true;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.WATER || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SNOW) {
         target.setDeltaMovement(target.getDeltaMovement().multiply(0.82, 0.95, 0.82));
         target.hurtMarked = true;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SHOCKWAVE) {
         target.setDeltaMovement(target.getDeltaMovement().add(delta.scale(0.12 + Math.max(0.0, move.powerMultiplier() - 1.0) * 0.03)));
         target.hurtMarked = true;
      }
   }

   private static void applyExtraFlags(LivingEntity target, VoidArchiveForgeData.CreatedMove move) {
      if (move.guardBreak()) {
         target.invulnerableTime = Math.max(0, target.invulnerableTime - 12 - Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 4.0F));
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION),
               40 + Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 15.0F),
               move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.DARK && move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.PORTAL
                  ? 0
                  : 1,
               false,
               false,
               true
            )
         );
      }

      if (move.ignite()) {
         int fire = 140 + Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 30.0F);
         if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLACK_FIRE || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.FLAME) {
            fire += 60;
         }

         target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), fire));
      }

      if (move.shockSlow()) {
         double slowXz = move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.LIGHTNING
               && move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.ARCANE
            ? 0.55
            : 0.4;
         target.setDeltaMovement(target.getDeltaMovement().multiply(slowXz, 1.0, slowXz));
         target.hurtMarked = true;
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
               80 + Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 20.0F),
               move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.LIGHTNING ? 2 : 1,
               false,
               false,
               true
            )
         );
      }

      if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.GLASS_DOME) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.WEBBED), 100, 0, false, false, true));
      }

      if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.ICE_FIELD) {
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
               70,
               move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SNOW ? 2 : 1,
               false,
               false,
               true
            )
         );
      }

      if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.SPIKE_FIELD && move.launchTarget()) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY), 60, 0, false, false, true));
      }

      if ((move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLACK_FIRE || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.FLAME)
         && move.blockAction() == VoidArchiveForgeData.MoveBlockAction.VOID_CRATER) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN), 80, 1, false, false, true));
      }

      if ((move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.LIGHT || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOSSOM)
         && move.guardBreak()) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FLASHED_BLINDNESS), 50, 0, false, false, true));
      }

      if ((move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.DARK || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.PORTAL)
         && move.pullTarget()) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), 60, 0, false, false, true));
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SMOKE) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CONFUSION), 60, move.multiHit() ? 1 : 0, false, false, true));
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SOUL && move.magiculeSteal() > 0.0F) {
         target.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN), 60, move.magiculeSteal() >= 0.5F ? 1 : 0, false, false, true)
         );
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.ENCHANT) {
         target.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), 60, move.guardBreak() ? 1 : 0, false, false, true)
         );
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOCK && move.blockAction() != VoidArchiveForgeData.MoveBlockAction.NONE) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), 60, move.guardBreak() ? 1 : 0, false, false, true));
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.SIGIL) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), 50, 0, false, false, true));
      }
   }

   private static void applyTrapDebuffs(LivingEntity target, VoidArchiveForgeData.CreatedMove move) {
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.WEBBED), 80, 0, false, false, true));
      if (move.shockSlow()) {
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
               100 + Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 20.0F),
               1
                  + (
                     move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.LIGHTNING
                           && move.particleStyle() != VoidArchiveForgeData.MoveParticleStyle.ARCANE
                        ? 0
                        : 1
                  ),
               false,
               false,
               true
            )
         );
      }

      if (move.guardBreak()) {
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS),
               50 + Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 10.0F),
               move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.LIGHTNING ? 1 : 0,
               false,
               false,
               true
            )
         );
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLACK_FIRE || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.FLAME) {
         target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), 120 + Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 30.0F)));
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.DARK || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.PORTAL) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), 60, 0, false, false, true));
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), 60, 0, false, false, true));
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SMOKE) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CONFUSION), 70, 0, false, false, true));
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SOUL) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN), 70, 0, false, false, true));
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOCK) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), 70, 0, false, false, true));
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.SIGIL) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), 70, 0, false, false, true));
      }
   }

   private static void executeEntityAction(ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 center) {
      ResourceLocation id = move.entityId();
      if (id != null) {
         EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
         if (type != null) {
            int count = Math.max(1, Math.min(16, move.summonCount()));
            Vec3 look = caster.getLookAngle().normalize();
            Vec3 side = new Vec3(-look.z, 0.0, look.x).normalize();

            for (int i = 0; i < count; i++) {
               Entity entity = type.create(level);
               if (entity != null) {
                  Vec3 pos = switch (move.shape()) {
                     case TOUCH -> center.add(look.scale(1.2 + i * 0.35));
                     case AOE, ORB, NOVA, SIGIL -> {
                        double angle = (Math.PI * 2) * i / Math.max(1, count);
                        double dist = Math.max(0.8, move.radius() * 0.55);
                        yield center.add(Math.cos(angle) * dist, 0.0, Math.sin(angle) * dist);
                     }
                     case BEAM, LINE -> center.add(look.scale(1.0 + i * 1.25));
                     case AURA -> caster.position().add(side.scale((i - (count - 1) * 0.5) * 1.4));
                     case SUMMON -> center.add(look.scale(1.5 + i * 0.4)).add(side.scale((i - (count - 1) * 0.5) * 1.25));
                     case WALL -> center.add(side.scale((i - (count - 1) * 0.5) * 1.6));
                     case RING -> {
                        double angle = (Math.PI * 2) * i / Math.max(1, count);
                        yield center.add(Math.cos(angle) * Math.max(1.25, move.radius()), 0.0, Math.sin(angle) * Math.max(1.25, move.radius()));
                     }
                     case DOME -> {
                        double angle = (Math.PI * 2) * i / Math.max(1, count);
                        double ring = Math.max(1.15, move.radius() * 0.8);
                        yield center.add(Math.cos(angle) * ring, i % 2 == 0 ? 1.1 : 0.35, Math.sin(angle) * ring);
                     }
                     case RAIN -> center.add(side.scale((i - (count - 1) * 0.5) * 0.85)).add(look.scale(i * 0.5)).add(0.0, 3.5 + i % 3, 0.0);
                     case PILLAR -> center.add(0.0, i * 1.2, 0.0);
                  };
                  entity.moveTo(pos.x, pos.y, pos.z, caster.getYRot(), caster.getXRot());
                  if (entity instanceof LivingEntity livingEntity) {
                     IExistence existence = TensuraStorages.getExistenceFrom(livingEntity);
                     existence.setSummoner(caster.getUUID());
                     existence.setSpawnType(MobSpawnType.MOB_SUMMONED);
                     if (caster instanceof Player) {
                        existence.setTemporaryOwner(caster.getUUID());
                     }

                     existence.markDirty();
                     if (livingEntity instanceof Mob mob) {
                        SubordinateHelper.removeTarget(mob);
                        mob.setPersistenceRequired();
                        mob.setTarget(null);
                     }

                     if (livingEntity instanceof ISubordinate subordinate && caster instanceof Player player) {
                        subordinate.tame(player);
                     }
                  }

                  if (entity instanceof Mob mob) {
                     mob.setPersistenceRequired();
                     if (move.entityAction() == VoidArchiveForgeData.MoveEntityAction.REVIVE_RECORDED && caster instanceof Player player) {
                        mob.setCustomName(player.getDisplayName().copy().append("'s Void Shade"));
                     }
                  }

                  level.addFreshEntity(entity);
                  spawnParticles(level, entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0), move, 12);
               }
            }
         }
      }
   }

   private static void applyBlockAction(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 base, boolean triggered, @Nullable LivingEntity focus
   ) {
      if (move.blockAction() != VoidArchiveForgeData.MoveBlockAction.NONE) {
         BlockPos center = BlockPos.containing(base);
         int shapeScale = Math.max(2, Mth.ceil(move.radius()));
         int powerBonus = Math.max(0, Math.round(Math.max(0.0F, move.powerMultiplier() - 1.0F) * 2.0F));
         int size = Math.max(2, shapeScale + powerBonus + (triggered ? 1 : 0));
         Vec3 look = focus != null ? focus.position().subtract(base) : caster.getLookAngle();
         if (look.lengthSqr() < 1.0E-4) {
            look = caster.getLookAngle();
         }

         look = look.normalize();
         Vec3 side = new Vec3(-look.z, 0.0, look.x).normalize();
         switch (move.blockAction()) {
            case OBSIDIAN_WALL:
               int wallHalf = Math.max(2, size + (move.pullTarget() ? 1 : 0));
               int wallHeight = Math.max(3, 2 + Mth.ceil(move.radius() * 0.6F) + powerBonus + (move.guardBreak() ? 1 : 0));
               BlockPos wallCenter = focus != null ? focus.blockPosition() : center;

               for (int i = -wallHalf; i <= wallHalf; i++) {
                  for (int dy = 0; dy <= wallHeight; dy++) {
                     BlockPos pos = wallCenter.offset(Mth.floor(side.x * i), dy, Mth.floor(side.z * i));
                     placeArcaneBlock(level, pos, triggered ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.CRYING_OBSIDIAN.defaultBlockState());
                  }
               }
               break;
            case ICE_FIELD:
               for (int dx = -size; dx <= size; dx++) {
                  for (int dz = -size; dz <= size; dz++) {
                     if (dx * dx + dz * dz <= size * size) {
                        placeArcaneBlock(level, center.offset(dx, -1, dz), Blocks.BLUE_ICE.defaultBlockState());
                        if ((Math.abs(dx) + Math.abs(dz)) % 3 == 0) {
                           placeArcaneBlock(level, center.offset(dx, 0, dz), Blocks.PACKED_ICE.defaultBlockState());
                        }
                     }
                  }
               }
               break;
            case CRYSTAL_RING:
               int ring = Math.max(3, size + 1);

               for (int dx = -ring; dx <= ring; dx++) {
                  for (int dz = -ring; dz <= ring; dz++) {
                     int dist2 = dx * dx + dz * dz;
                     if (dist2 >= (ring - 1) * (ring - 1) && dist2 <= ring * ring + 1) {
                        placeArcaneBlock(level, center.offset(dx, 0, dz), Blocks.AMETHYST_BLOCK.defaultBlockState());
                        if (dist2 % 3 == 0) {
                           placeArcaneBlock(level, center.offset(dx, 1, dz), Blocks.BUDDING_AMETHYST.defaultBlockState());
                        }
                     }
                  }
               }
               break;
            case GLASS_DOME:
               int dome = Math.max(3, size);

               for (int dx = -dome; dx <= dome; dx++) {
                  for (int dz = -dome; dz <= dome; dz++) {
                     for (int dy = 0; dy <= dome; dy++) {
                        int value = dx * dx + dz * dz + dy * dy;
                        if (value >= dome * dome - dome && value <= dome * dome + dome) {
                           placeArcaneBlock(level, center.offset(dx, dy, dz), Blocks.TINTED_GLASS.defaultBlockState());
                        }
                     }
                  }
               }
               break;
            case SPIKE_FIELD:
               for (int dx = -size; dx <= size; dx++) {
                  for (int dz = -size; dz <= size; dz++) {
                     if ((Math.abs(dx) + Math.abs(dz)) % 2 == 0 && dx * dx + dz * dz <= size * size + 2) {
                        placeSpikeBlock(level, center.offset(dx, 0, dz));
                        if (move.launchTarget() && dx * dx + dz * dz <= Math.max(1, size - 1) * Math.max(1, size - 1)) {
                           placeSpikeBlock(level, center.offset(dx, 1, dz));
                        }
                     }
                  }
               }
               break;
            case VOID_CRATER:
               int crater = Math.max(4, size + 1);

               for (int dx = -crater; dx <= crater; dx++) {
                  for (int dz = -crater; dz <= crater; dz++) {
                     int dist2 = dx * dx + dz * dz;
                     if (dist2 <= crater * crater) {
                        placeArcaneBlock(level, center.offset(dx, -1, dz), Blocks.OBSIDIAN.defaultBlockState());
                        if (dist2 >= (crater - 1) * (crater - 1)) {
                           placeArcaneBlock(level, center.offset(dx, 0, dz), Blocks.CRYING_OBSIDIAN.defaultBlockState());
                        }
                     }
                  }
               }
         }
      }
   }

   private static void placeArcaneBlock(ServerLevel level, BlockPos pos, BlockState state) {
      if (canPlaceArcaneBlock(level, pos)) {
         level.setBlock(pos, state, 3);
         postArcaneBlockPlace(level, pos);
         level.sendParticles(
            new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.25, 0.25, 0.25, 0.03
         );
      }
   }

   private static void placeSpikeBlock(ServerLevel level, BlockPos pos) {
      if (canPlaceArcaneBlock(level, pos)) {
         BlockState state = Blocks.POINTED_DRIPSTONE.defaultBlockState();
         level.setBlock(pos, state, 3);
         postArcaneBlockPlace(level, pos);
         level.sendParticles(
            new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.35, pos.getZ() + 0.5, 10, 0.15, 0.15, 0.15, 0.03
         );
      }
   }

   private static boolean canPlaceArcaneBlock(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return false;
      }

      BlockState old = level.getBlockState(pos);
      if (!old.isAir() && !old.canBeReplaced()) {
         return false;
      }

      Entity owner = ACTIVE_GRIEF_OWNER.get();
      ManasSkillInstance instance = ACTIVE_GRIEF_INSTANCE.get();
      return !((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker()).grief(instance, level, owner, pos.getX(), pos.getY(), pos.getZ()).isFalse();
   }

   private static void postArcaneBlockPlace(ServerLevel level, BlockPos pos) {
      Entity owner = ACTIVE_GRIEF_OWNER.get();
      ManasSkillInstance instance = ACTIVE_GRIEF_INSTANCE.get();
      ((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_POS.invoker()).grief(instance, level, owner, pos.getX(), pos.getY(), pos.getZ());
   }

   private static void castVisualCircle(ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 pos) {
      MagicCircle circle = new MagicCircle(level, caster);
      circle.setLife(16 + Math.max(0, move.burstCount() * 3));
      circle.setSize(Math.max(0.9F, Math.min(3.0F, move.radius() * 0.8F)));
      circle.setVariant(chooseVariant(move.primaryDamageId(), move.particleStyle()));
      circle.setFollowPosition(false);
      circle.setLookDistance(0.0F);
      circle.setFollowViewDistance(-1.0F);
      circle.setSpinning(
         move.shape() == VoidArchiveForgeData.MoveShape.RING
            || move.shape() == VoidArchiveForgeData.MoveShape.SIGIL
            || move.shape() == VoidArchiveForgeData.MoveShape.DOME
            || move.shape() == VoidArchiveForgeData.MoveShape.PILLAR
      );
      circle.setPos(pos);
      level.addFreshEntity(circle);
   }

   private static MagicCircleVariant chooseVariant(String damageId, VoidArchiveForgeData.MoveParticleStyle particleStyle) {
      if (particleStyle == VoidArchiveForgeData.MoveParticleStyle.FLAME || particleStyle == VoidArchiveForgeData.MoveParticleStyle.BLACK_FIRE) {
         return MagicCircleVariant.FLAME;
      } else if (particleStyle == VoidArchiveForgeData.MoveParticleStyle.WATER || particleStyle == VoidArchiveForgeData.MoveParticleStyle.SNOW) {
         return MagicCircleVariant.WATER;
      } else if (particleStyle == VoidArchiveForgeData.MoveParticleStyle.WIND) {
         return MagicCircleVariant.WIND;
      } else if (particleStyle == VoidArchiveForgeData.MoveParticleStyle.LIGHTNING || particleStyle == VoidArchiveForgeData.MoveParticleStyle.ARCANE) {
         return MagicCircleVariant.SPACE;
      } else if (particleStyle == VoidArchiveForgeData.MoveParticleStyle.LIGHT || particleStyle == VoidArchiveForgeData.MoveParticleStyle.BLOSSOM) {
         return MagicCircleVariant.HOLY;
      } else if (particleStyle == VoidArchiveForgeData.MoveParticleStyle.DARK || particleStyle == VoidArchiveForgeData.MoveParticleStyle.PORTAL) {
         return MagicCircleVariant.DARK;
      } else if (particleStyle == VoidArchiveForgeData.MoveParticleStyle.BLOCK) {
         return MagicCircleVariant.EARTH;
      } else if (damageId == null) {
         return MagicCircleVariant.MISC;
      } else if (damageId.contains("fire")) {
         return MagicCircleVariant.FLAME;
      } else if (damageId.contains("water")) {
         return MagicCircleVariant.WATER;
      } else if (damageId.contains("wind")) {
         return MagicCircleVariant.WIND;
      } else if (damageId.contains("earth")) {
         return MagicCircleVariant.EARTH;
      } else if (damageId.contains("space")) {
         return MagicCircleVariant.SPACE;
      } else if (damageId.contains("light")) {
         return MagicCircleVariant.HOLY;
      } else {
         return damageId.contains("dark") ? MagicCircleVariant.DARK : MagicCircleVariant.MISC;
      }
   }

   private static void spawnParticles(ServerLevel level, Vec3 pos, VoidArchiveForgeData.CreatedMove move, int intensity) {
      ParticleOptions particle = resolveParticle(move);

      double xSpread = switch (move.shape()) {
         case TOUCH -> 0.22;
         default -> 0.45 + move.radius() * 0.06;
         case BEAM, LINE -> 0.18 + move.radius() * 0.03;
         case WALL -> 0.3 + move.radius() * 0.05;
         case PILLAR -> 0.22 + move.radius() * 0.04;
      };

      double ySpread = switch (move.shape()) {
         case BEAM, LINE -> 0.18;
         case WALL -> 0.55;
         case PILLAR -> 0.65 + move.radius() * 0.06;
         default -> 0.35 + move.radius() * 0.04;
      };
      TensuraParticleHelper.spawnServerParticles(
         level, particle, pos.x, pos.y, pos.z, Math.max(6, intensity), xSpread, ySpread, xSpread, 0.025 + move.speed() * 0.01, true
      );
   }

   private static void spawnRingParticles(ServerLevel level, Vec3 center, VoidArchiveForgeData.CreatedMove move, double radius, int count) {
      ParticleOptions particle = resolveParticle(move);

      for (int i = 0; i < count; i++) {
         double angle = (Math.PI * 2) * i / Math.max(1, count);
         Vec3 pos = center.add(Math.cos(angle) * radius, 0.15, Math.sin(angle) * radius);
         TensuraParticleHelper.spawnServerParticles(level, particle, pos.x, pos.y, pos.z, 2, 0.05, 0.05, 0.05, 0.004, true);
      }
   }

   private static void spawnDomeParticles(ServerLevel level, Vec3 center, VoidArchiveForgeData.CreatedMove move, double radius, int count) {
      ParticleOptions particle = resolveParticle(move);

      for (int y = 0; y <= 4; y++) {
         double layerRadius = radius * (1.0 - y * 0.16);

         for (int i = 0; i < count; i++) {
            double angle = (Math.PI * 2) * i / Math.max(1, count);
            Vec3 pos = center.add(Math.cos(angle) * layerRadius, y * 0.45, Math.sin(angle) * layerRadius);
            TensuraParticleHelper.spawnServerParticles(level, particle, pos.x, pos.y, pos.z, 1, 0.03, 0.03, 0.03, 0.002, true);
         }
      }
   }

   private static void spawnRainParticles(ServerLevel level, Vec3 center, VoidArchiveForgeData.CreatedMove move, double radius, int count) {
      ParticleOptions particle = resolveParticle(move);

      for (int i = 0; i < count; i++) {
         double angle = (Math.PI * 2) * i / Math.max(1, count);
         double dist = radius * (0.4 + 0.6 * (i % 5 / 4.0));
         double x = center.x + Math.cos(angle) * dist;
         double z = center.z + Math.sin(angle) * dist;
         TensuraParticleHelper.spawnServerParticles(level, particle, x, center.y + 3.0 + radius * 0.35, z, 2, 0.03, 0.15, 0.03, 0.008, true);
      }
   }

   private static void spawnPillarParticles(ServerLevel level, Vec3 center, VoidArchiveForgeData.CreatedMove move, double radius, int count) {
      ParticleOptions particle = resolveParticle(move);

      for (int y = 0; y < Math.max(4, Mth.ceil((float)(radius * 2.0))); y++) {
         TensuraParticleHelper.spawnServerParticles(
            level, particle, center.x, center.y + y * 0.35, center.z, Math.max(2, count / 8), radius * 0.16, 0.05, radius * 0.16, 0.006, true
         );
      }
   }

   private static void spawnWallParticles(ServerLevel level, Vec3 center, Vec3 look, VoidArchiveForgeData.CreatedMove move, double radius, int count) {
      ParticleOptions particle = resolveParticle(move);
      Vec3 side = new Vec3(-look.z, 0.0, look.x).normalize();

      for (int i = -Mth.ceil((float)(radius * 1.5)); i <= Mth.ceil((float)(radius * 1.5)); i++) {
         Vec3 pos = center.add(side.scale(i));
         TensuraParticleHelper.spawnServerParticles(level, particle, pos.x, pos.y + 0.8, pos.z, Math.max(2, count / 10), 0.05, 0.55, 0.05, 0.006, true);
      }
   }

   private static void spawnSigilParticles(ServerLevel level, Vec3 center, VoidArchiveForgeData.CreatedMove move, double radius, int count) {
      spawnRingParticles(level, center, move, radius, count);
      spawnRingParticles(level, center, move, Math.max(0.6, radius * 0.55), Math.max(8, count / 2));
   }

   private static void spawnNovaParticles(ServerLevel level, Vec3 center, VoidArchiveForgeData.CreatedMove move, double radius, int count) {
      ParticleOptions particle = resolveParticle(move);
      TensuraParticleHelper.spawnServerParticles(
         level, particle, center.x, center.y, center.z, Math.max(8, count), radius * 0.5, radius * 0.25, radius * 0.5, 0.03 + move.speed() * 0.008, true
      );
   }

   private static void spawnInfusionParticles(ServerLevel level, Vec3 pos, VoidArchiveForgeData.MeleeInfusion infusion) {
      ParticleOptions particle = (ParticleOptions)(infusion.damageId().contains("lightning")
         ? (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get()
         : (
            infusion.damageId().contains("fire")
               ? (ParticleOptions)TensuraParticleTypes.BLACK_FIRE.get()
               : (
                  infusion.damageId().contains("light")
                     ? ParticleTypes.END_ROD
                     : (
                        infusion.damageId().contains("water")
                           ? ParticleTypes.SPLASH
                           : (
                              infusion.damageId().contains("wind")
                                 ? ParticleTypes.CLOUD
                                 : (infusion.damageId().contains("sever") ? (ParticleOptions)TensuraParticleTypes.SOUL.get() : ParticleTypes.PORTAL)
                           )
                     )
               )
         ));
      TensuraParticleHelper.spawnServerParticles(level, particle, pos.x, pos.y, pos.z, 14, 0.3, 0.22, 0.3, 0.02, true);
   }

   private static void applyDomeLift(ServerLevel level, Vec3 center, double radius) {
      AABB box = new AABB(center, center).inflate(radius * 0.9, radius * 0.9, radius * 0.9);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, living -> living.isAlive())) {
         target.setDeltaMovement(target.getDeltaMovement().add(0.0, 0.1, 0.0));
         target.hurtMarked = true;
      }
   }

   private static ParticleOptions resolveParticle(VoidArchiveForgeData.CreatedMove move) {
      if (move.customParticleId() != null) {
         ParticleOptions custom = tryCustomParticle(move.customParticleId());
         if (custom != null) {
            return custom;
         }
      }
      return (ParticleOptions)(switch (move.particleStyle()) {
         case FLAME -> move.shape() == VoidArchiveForgeData.MoveShape.BEAM ? (ParticleOptions)TensuraParticleTypes.BLACK_FIRE.get() : ParticleTypes.FLAME;
         case SOUL -> (TensuraParticleType)TensuraParticleTypes.SOUL.get();
         case LIGHTNING -> (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get();
         case WATER -> ParticleTypes.SPLASH;
         case WIND -> ParticleTypes.CLOUD;
         case DARK -> ParticleTypes.PORTAL;
         case LIGHT -> ParticleTypes.END_ROD;
         case ENCHANT -> ParticleTypes.ENCHANT;
         case SMOKE -> ParticleTypes.SMOKE;
         case BLOCK -> new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CRYING_OBSIDIAN.defaultBlockState());
         case SHOCKWAVE -> ParticleTypes.EXPLOSION;
         case BLOSSOM -> (TensuraParticleType)TensuraParticleTypes.BLOSSOM.get();
         case BLACK_FIRE -> (TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get();
         case SNOW -> (TensuraParticleType)TensuraParticleTypes.SNOWFLAKE.get();
         case ARCANE -> (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get();
         default -> ParticleTypes.PORTAL;
      });
   }

   @Nullable
   private static ParticleOptions tryCustomParticle(ResourceLocation id) {
      if (BuiltInRegistries.PARTICLE_TYPE.get(id) instanceof ParticleOptions options) {
         return options;
      } else if (id.equals(TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.getId())) {
         return (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get();
      } else if (id.equals(TensuraParticleTypes.BLOSSOM.getId())) {
         return (ParticleOptions)TensuraParticleTypes.BLOSSOM.get();
      } else if (id.equals(TensuraParticleTypes.SOUL.getId())) {
         return (ParticleOptions)TensuraParticleTypes.SOUL.get();
      } else if (id.equals(TensuraParticleTypes.BLACK_FIRE.getId())) {
         return (ParticleOptions)TensuraParticleTypes.BLACK_FIRE.get();
      } else {
         return id.equals(TensuraParticleTypes.SNOWFLAKE.getId()) ? (ParticleOptions)TensuraParticleTypes.SNOWFLAKE.get() : null;
      }
   }

   private static void playCast(ServerLevel level, LivingEntity caster) {
      level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.1F, 0.94F);
   }

   private static void deployStoredTrap(
      ServerLevel level, LivingEntity caster, @Nullable ManasSkillInstance instance, VoidArchiveForgeData.CreatedMove move, Vec3 pos, float storedDamage
   ) {
      float triggerRadius = getScaledTriggerRadius(move);
      int lifeTicks = getScaledTrapLifetime(move);
      MagicCircle circle = spawnTrapCircle(level, caster, move, pos, triggerRadius, lifeTicks);
      VoidArchiveForgeData.DeployedTrap trap = new VoidArchiveForgeData.DeployedTrap(
         caster.getUUID(), instance, move, pos, storedDamage, lifeTicks, triggerRadius, circle == null ? null : circle.getUUID(), move.followAim() ? 10 : 5
      );
      ACTIVE_TRAPS.computeIfAbsent(level.dimension(), key -> new ArrayList<>()).add(trap);
   }

   @Nullable
   private static MagicCircle spawnTrapCircle(
      ServerLevel level, LivingEntity caster, VoidArchiveForgeData.CreatedMove move, Vec3 pos, float radius, int lifeTicks
   ) {
      MagicCircle circle = new MagicCircle(level, caster);
      circle.setLife(lifeTicks);
      circle.setSize(Math.max(0.85F, Math.min(2.6F, radius * 0.8F)));
      circle.setVariant(chooseVariant(move.primaryDamageId(), move.particleStyle()));
      circle.setSpinning(true);
      circle.setFollowPosition(false);
      circle.setFollowViewDistance(-1.0F);
      circle.setLookDistance(0.0F);
      circle.setPos(pos);
      level.addFreshEntity(circle);
      return circle;
   }

   private static void spawnTrapIdleParticles(ServerLevel level, VoidArchiveForgeData.DeployedTrap trap) {
      ParticleOptions particle = resolveParticle(trap.move);
      int count = trap.armTicks > 0 ? 2 : 4;
      TensuraParticleHelper.spawnServerParticles(
         level,
         particle,
         trap.position.x,
         trap.position.y + 0.1,
         trap.position.z,
         count,
         trap.triggerRadius * 0.18,
         0.08,
         trap.triggerRadius * 0.18,
         0.003,
         true
      );
   }

   private static void triggerTrap(ServerLevel level, LivingEntity caster, VoidArchiveForgeData.DeployedTrap trap, List<LivingEntity> targets) {
      if (!trap.triggered) {
         trap.triggered = true;
         if (trap.circleUuid != null && level.getEntity(trap.circleUuid) instanceof MagicCircle circle) {
            circle.discard();
         }

         VoidArchiveForgeData.CreatedMove move = triggerVersion(trap.move);
         LivingEntity focus = targets.get(0);
         spawnSigilParticles(level, trap.position, move, Math.max(1.0, trap.triggerRadius), 18 + move.range());
         ManasSkillInstance previousInstance = ACTIVE_GRIEF_INSTANCE.get();
         Entity previousOwner = ACTIVE_GRIEF_OWNER.get();
         ACTIVE_GRIEF_INSTANCE.set(trap.skillInstance);
         ACTIVE_GRIEF_OWNER.set(caster);

         try {
            resolveSpell(level, caster, move, trap.position, focus, true);
         } finally {
            if (previousInstance == null) {
               ACTIVE_GRIEF_INSTANCE.remove();
            } else {
               ACTIVE_GRIEF_INSTANCE.set(previousInstance);
            }

            if (previousOwner == null) {
               ACTIVE_GRIEF_OWNER.remove();
            } else {
               ACTIVE_GRIEF_OWNER.set(previousOwner);
            }
         }

         for (LivingEntity target : targets) {
            if (target.isAlive()) {
               applyTrapDebuffs(target, move);
               if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.OBSIDIAN_WALL && move.pullTarget()) {
                  Vec3 inward = trap.position.subtract(target.position());
                  if (inward.lengthSqr() > 1.0E-4) {
                     target.setDeltaMovement(target.getDeltaMovement().add(inward.normalize().scale(0.9)).add(0.0, 0.1, 0.0));
                     target.hurtMarked = true;
                  }
               }

               if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.VOID_CRATER && move.launchTarget()) {
                  target.setDeltaMovement(target.getDeltaMovement().add(0.0, getScaledLaunch(move) * 0.9, 0.0));
                  target.hurtMarked = true;
               }

               if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLACK_FIRE
                  || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.FLAME) {
                  target.addEffect(
                     new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN), 80, move.ignite() ? 1 : 0, false, false, true)
                  );
               }
            }
         }
      }
   }

   private static void discardTrap(ServerLevel level, VoidArchiveForgeData.DeployedTrap trap) {
      if (trap.circleUuid != null && level.getEntity(trap.circleUuid) instanceof MagicCircle circle) {
         circle.discard();
      }
   }

   public static VoidArchiveForgeData.ComponentData describeMove(VoidArchiveForgeData.CreatedMove move) {
      return new VoidArchiveForgeData.ComponentData(
         move == null ? "No move" : move.name(),
         move == null
            ? ""
            : move.shape().name().toLowerCase(Locale.ROOT)
               + " | "
               + shortDamage(move.primaryDamageId())
               + " | "
               + shortCast(move)
               + " | x"
               + trimFloat(move.powerMultiplier())
               + " | "
               + shortParticle(move)
      );
   }

   public static String shortDamage(String id) {
      if (id != null && !id.isBlank()) {
         int colon = id.indexOf(58);
         String value = colon >= 0 ? id.substring(colon + 1) : id;
         return value.replace('_', ' ');
      } else {
         return "magic";
      }
   }

   public static String shortCast(VoidArchiveForgeData.CreatedMove move) {
      if (move.trapTargets()) {
         return "trap";
      } else if (move.castMode() == VoidArchiveForgeData.MoveCastMode.SELF) {
         return "self";
      } else if (move.castMode() == VoidArchiveForgeData.MoveCastMode.PROJECTILE) {
         return "projectile";
      } else if (move.castMode() == VoidArchiveForgeData.MoveCastMode.TARGET_AREA) {
         return "aoe";
      } else if (move.selfCast()) {
         return "self";
      } else if (move.projectileCast()) {
         return "projectile";
      } else {
         return move.aoeCast() ? "aoe" : "default";
      }
   }

   public static String shortParticle(VoidArchiveForgeData.CreatedMove move) {
      return move.customParticleId() != null
         ? move.customParticleId().getPath().replace('_', ' ')
         : move.particleStyle().name().toLowerCase(Locale.ROOT).replace('_', ' ');
   }

   private static float getRepeatDamageScale(VoidArchiveForgeData.CreatedMove move) {
      float scale = move.multiHit() ? 0.62F : 0.52F;
      if (move.guardBreak()) {
         scale += 0.08F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.LIGHTNING || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.ARCANE) {
         scale += 0.05F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLACK_FIRE) {
         scale += 0.03F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SOUL) {
         scale += 0.03F;
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.BEAM || move.shape() == VoidArchiveForgeData.MoveShape.LINE) {
         scale += 0.02F;
      }

      return clamp(scale, 0.35F, 0.85F);
   }

   private static float getScaledBarrierBypass(VoidArchiveForgeData.CreatedMove move) {
      float bonus = Math.max(0.0F, move.powerMultiplier() - 1.0F) * 0.08F;
      if (move.guardBreak()) {
         bonus += 0.18F;
      }

      if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.VOID_CRATER) {
         bonus += 0.1F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.ARCANE) {
         bonus += 0.05F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOCK || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SHOCKWAVE) {
         bonus += 0.04F;
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.PILLAR) {
         bonus += 0.04F;
      }

      return clamp(move.barrierBypass() + bonus, 0.0F, 1.0F);
   }

   private static float getScaledResistanceBypass(VoidArchiveForgeData.CreatedMove move) {
      float bonus = Math.max(0.0F, move.powerMultiplier() - 1.0F) * 0.06F;
      if (move.guardBreak()) {
         bonus += 0.1F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.PORTAL
         || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.DARK
         || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.ARCANE) {
         bonus += 0.06F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.LIGHTNING && move.shockSlow()) {
         bonus += 0.04F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SOUL) {
         bonus += 0.03F;
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.SIGIL) {
         bonus += 0.03F;
      }

      return clamp(move.resistanceBypass() + bonus, 0.0F, 1.0F);
   }

   private static double getScaledPull(VoidArchiveForgeData.CreatedMove move) {
      double power = Math.max(0.2, move.powerMultiplier());
      double base = 0.45 + power * 0.35 + move.radius() * 0.04;
      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.PORTAL || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.DARK) {
         base += 0.18;
      }

      if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.VOID_CRATER) {
         base += 0.2;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.WATER || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SNOW) {
         base += 0.1;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SHOCKWAVE) {
         base += 0.08;
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.AURA) {
         base += 0.08;
      }

      return Math.min(2.25, base);
   }

   private static double getScaledLaunch(VoidArchiveForgeData.CreatedMove move) {
      double power = Math.max(0.2, move.powerMultiplier());
      double base = 0.35 + power * 0.28 + move.burstCount() * 0.05;
      if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.SPIKE_FIELD || move.blockAction() == VoidArchiveForgeData.MoveBlockAction.VOID_CRATER) {
         base += 0.18;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.LIGHTNING || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.ARCANE) {
         base += 0.08;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.WIND) {
         base += 0.06;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SHOCKWAVE) {
         base += 0.08;
      }

      if (move.shape() == VoidArchiveForgeData.MoveShape.PILLAR || move.shape() == VoidArchiveForgeData.MoveShape.NOVA) {
         base += 0.05;
      }

      return Math.min(2.0, base);
   }

   private static float getScaledTriggerRadius(VoidArchiveForgeData.CreatedMove move) {
      float base = Math.max(1.0F, move.radius() * 0.8F + 0.9F);
      if (move.shape() == VoidArchiveForgeData.MoveShape.RING
         || move.shape() == VoidArchiveForgeData.MoveShape.DOME
         || move.shape() == VoidArchiveForgeData.MoveShape.SIGIL) {
         base += 0.6F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.PORTAL || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.DARK) {
         base += 0.35F;
      }

      if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.GLASS_DOME || move.blockAction() == VoidArchiveForgeData.MoveBlockAction.VOID_CRATER) {
         base += 0.3F;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOCK || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SHOCKWAVE) {
         base += 0.25F;
      }

      return clamp(base, 0.8F, 14.0F);
   }

   private static int getScaledTrapLifetime(VoidArchiveForgeData.CreatedMove move) {
      int base = 80 + move.range() * 4 + Math.round(move.radius() * 12.0F);
      if (move.followAim()) {
         base += 25;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.PORTAL || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.DARK) {
         base += 40;
      }

      if (move.blockAction() != VoidArchiveForgeData.MoveBlockAction.NONE) {
         base += 20;
      }

      if (move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.BLOCK || move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.ENCHANT) {
         base += 15;
      }

      return Math.min(400, base);
   }

   private static boolean beginInfusionProc(LivingEntity attacker, LivingEntity target, VoidArchiveForgeData.MeleeInfusion infusion) {
      Set<UUID> keys = ACTIVE_INFUSION_KEYS.get();
      UUID key = buildInfusionKey(attacker, target, infusion);
      return keys.add(key);
   }

   private static void endInfusionProc(LivingEntity attacker, LivingEntity target, VoidArchiveForgeData.MeleeInfusion infusion) {
      Set<UUID> keys = ACTIVE_INFUSION_KEYS.get();
      keys.remove(buildInfusionKey(attacker, target, infusion));
      if (keys.isEmpty()) {
         ACTIVE_INFUSION_KEYS.remove();
      }
   }

   private static UUID buildInfusionKey(LivingEntity attacker, LivingEntity target, VoidArchiveForgeData.MeleeInfusion infusion) {
      long msb = attacker.getUUID().getMostSignificantBits() ^ target.getUUID().getMostSignificantBits() ^ safe(infusion.damageId()).hashCode();
      long lsb = attacker.getUUID().getLeastSignificantBits() ^ target.getUUID().getLeastSignificantBits() ^ infusion.name().hashCode();
      return new UUID(msb, lsb);
   }

   private static void applyInfusionSynergy(
      ServerLevel level, LivingEntity attacker, LivingEntity target, VoidArchiveForgeData.MeleeInfusion infusion, float damage
   ) {
      String id = safe(infusion.damageId());
      if (id.contains("lightning")) {
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS),
               Math.max(40, infusion.effectDuration() / 2 + 30),
               infusion.effectAmplifier() > 0 ? 1 : 0,
               false,
               false,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), Math.max(60, infusion.effectDuration() / 2 + 40), 1, false, false, true
            )
         );
      }

      if (id.contains("fire")) {
         target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), 100 + Math.round(Math.max(0.0F, infusion.powerMultiplier() - 1.0F) * 25.0F)));
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN), 70, infusion.powerMultiplier() >= 2.0F ? 1 : 0, false, false, true
            )
         );
      }

      if (id.contains("water")) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 70, 1, false, false, true));
         if (infusion.effectId() != null && infusion.effectId().toString().contains("frost")) {
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FROST), 60, 0, false, false, true));
         }
      }

      if (id.contains("wind")) {
         Vec3 look = attacker.getLookAngle().normalize();
         target.setDeltaMovement(target.getDeltaMovement().add(look.scale(0.25)).add(0.0, 0.12, 0.0));
         target.hurtMarked = true;
         if (infusion.effectId() != null && infusion.effectId().toString().contains("fragility")) {
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY), 60, 0, false, false, true));
         }
      }

      if (id.contains("dark") || id.contains("space") || id.contains("drain")) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), 60, 0, false, false, true));
      }

      if (id.contains("space")) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), 60, 0, false, false, true));
      }

      if (id.contains("sever")) {
         target.invulnerableTime = Math.max(0, target.invulnerableTime - 8);
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY), 40, 0, false, false, true));
      }

      if (id.contains("light")) {
         attacker.heal(Math.max(0.5F, damage * 0.08F));
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FLASHED_BLINDNESS), 40, 0, false, false, true));
      }

      if (infusion.lifeSteal() && (id.contains("dark") || id.contains("drain") || id.contains("sever"))) {
         attacker.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_AURA), 60, 1, false, false, true));
      }

      if (infusion.effectId() != null && id.contains("fire") && infusion.effectId().toString().contains("movement_interference")) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN), 80, 1, false, false, true));
      }

      if (infusion.effectId() != null && id.contains("lightning") && infusion.effectId().toString().contains("paralysis")) {
         target.setDeltaMovement(target.getDeltaMovement().multiply(0.45, 0.95, 0.45));
         target.hurtMarked = true;
      }

      spawnInfusionBonusParticles(level, target.position().add(0.0, target.getBbHeight() * 0.55, 0.0), infusion);
   }

   private static void applyParticleSynergy(LivingEntity target, VoidArchiveForgeData.CreatedMove move) {
      switch (move.particleStyle()) {
         case PORTAL:
         case DARK:
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), 50, 0, false, false, true));
         case FLAME:
         case WIND:
         case ENCHANT:
         case BLOSSOM:
         default:
            break;
         case SOUL:
            target.addEffect(
               new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN), 60, move.magiculeSteal() >= 0.5F ? 1 : 0, false, false, true)
            );
            break;
         case LIGHTNING:
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS), 40 + move.burstCount() * 5, move.shockSlow() ? 1 : 0, false, false, true
               )
            );
            break;
         case WATER:
         case SNOW:
            if (move.blockAction() == VoidArchiveForgeData.MoveBlockAction.ICE_FIELD) {
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.FROST),
                     60,
                     move.particleStyle() == VoidArchiveForgeData.MoveParticleStyle.SNOW ? 1 : 0,
                     false,
                     false,
                     true
                  )
               );
            }
            break;
         case LIGHT:
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FLASHED_BLINDNESS), 50, 0, false, false, true));
            break;
         case SMOKE:
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CONFUSION), 60, 0, false, false, true));
            break;
         case BLOCK:
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), 50, 0, false, false, true));
            break;
         case SHOCKWAVE:
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 45, 0, false, false, true));
            break;
         case BLACK_FIRE:
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN), 80, move.ignite() ? 1 : 0, false, false, true));
            break;
         case ARCANE:
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE),
                  50 + move.burstCount() * 5,
                  move.guardBreak() ? 1 : 0,
                  false,
                  false,
                  true
               )
            );
      }
   }

   private static void spawnInfusionBonusParticles(ServerLevel level, Vec3 pos, VoidArchiveForgeData.MeleeInfusion infusion) {
      ParticleOptions particle = (ParticleOptions)(!infusion.damageId().contains("space") && !infusion.damageId().contains("dark")
         ? (
            infusion.damageId().contains("water")
               ? ParticleTypes.SPLASH
               : (
                  infusion.damageId().contains("wind")
                     ? ParticleTypes.CLOUD
                     : (
                        infusion.damageId().contains("sever")
                           ? (ParticleOptions)TensuraParticleTypes.SOUL.get()
                           : (infusion.damageId().contains("light") ? ParticleTypes.END_ROD : null)
                     )
               )
         )
         : ParticleTypes.PORTAL);
      if (particle != null) {
         TensuraParticleHelper.spawnServerParticles(level, particle, pos.x, pos.y, pos.z, 8, 0.2, 0.15, 0.2, 0.01, true);
      }
   }

   private static boolean shouldSpawnVisualCircle(VoidArchiveForgeData.CreatedMove move, boolean triggered, boolean entityCast) {
      if (triggered) {
         return move.shape() == VoidArchiveForgeData.MoveShape.SIGIL
            || move.shape() == VoidArchiveForgeData.MoveShape.RING
            || move.shape() == VoidArchiveForgeData.MoveShape.DOME
            || move.shape() == VoidArchiveForgeData.MoveShape.PILLAR
            || move.blockAction() != VoidArchiveForgeData.MoveBlockAction.NONE;
      }

      if (!entityCast && !move.trapTargets()) {
         if (move.shape() == VoidArchiveForgeData.MoveShape.SIGIL
            || move.shape() == VoidArchiveForgeData.MoveShape.RING
            || move.shape() == VoidArchiveForgeData.MoveShape.DOME
            || move.shape() == VoidArchiveForgeData.MoveShape.SUMMON
            || move.shape() == VoidArchiveForgeData.MoveShape.PILLAR) {
            return true;
         } else {
            return move.blockAction() != VoidArchiveForgeData.MoveBlockAction.NONE
               ? true
               : move.castMode() == VoidArchiveForgeData.MoveCastMode.SELF && (move.giveGuarded() || move.makeInvulnerable());
         }
      } else {
         return true;
      }
   }

   private static boolean isValidTrapTarget(LivingEntity entity, UUID ownerId) {
      return entity.isAlive() && entity.getUUID() != ownerId && !(entity instanceof Player player && player.getAbilities().invulnerable);
   }

   private static boolean intersectsTrap(LivingEntity entity, Vec3 pos, double radius) {
      AABB bb = entity.getBoundingBox();
      double nearestX = Mth.clamp(pos.x, bb.minX, bb.maxX);
      double nearestY = Mth.clamp(pos.y, bb.minY, bb.maxY);
      double nearestZ = Mth.clamp(pos.z, bb.minZ, bb.maxZ);
      double dx = nearestX - pos.x;
      double dy = nearestY - pos.y;
      double dz = nearestZ - pos.z;
      return dx * dx + dy * dy + dz * dz <= radius * radius;
   }

   private static void updateTrapAnchor(ServerLevel level, VoidArchiveForgeData.DeployedTrap trap, Vec3 newPos) {
      trap.position = newPos;
      if (trap.circleUuid != null && level.getEntity(trap.circleUuid) instanceof MagicCircle circle) {
         circle.setPos(newPos);
      }
   }

   private static Vec3 snapTrapToGround(Vec3 pos) {
      return new Vec3(pos.x, Math.floor(pos.y) + 0.05, pos.z);
   }

   private static Vec3 rotateFlat(Vec3 vec, double degrees) {
      double rad = Math.toRadians(degrees);
      double cos = Math.cos(rad);
      double sin = Math.sin(rad);
      return new Vec3(vec.x * cos - vec.z * sin, vec.y, vec.x * sin + vec.z * cos).normalize();
   }

   private static String trimFloat(float value) {
      String raw = String.format(Locale.ROOT, "%.2f", value);

      while (raw.contains(".") && (raw.endsWith("0") || raw.endsWith("."))) {
         raw = raw.substring(0, raw.length() - 1);
      }

      return raw;
   }

   public static String safe(String value) {
      return value == null ? "" : value;
   }

   private static void saveEquipped(CompoundTag tag, List<Integer> equipped) {
      ListTag list = new ListTag();

      for (int idx : equipped) {
         CompoundTag c = new CompoundTag();
         c.putInt("Index", idx);
         list.add(c);
      }

      tag.put("EquippedCreatedMoves", list);
   }

   private static void normalizeEquipped(CompoundTag tag) {
      saveEquipped(tag, getEquippedMoveIndices(tag));
   }

   @Nullable
   private static ResourceLocation parseNullableRL(String value) {
      return value != null && !value.isBlank() ? ResourceLocation.parse(value) : null;
   }

   private static VoidArchiveForgeData.MoveShape parseShape(String value) {
      try {
         return VoidArchiveForgeData.MoveShape.valueOf(value);
      } catch (Exception ignored) {
         return VoidArchiveForgeData.MoveShape.AOE;
      }
   }

   private static VoidArchiveForgeData.MoveParticleStyle parseParticle(String value) {
      try {
         return VoidArchiveForgeData.MoveParticleStyle.valueOf(value);
      } catch (Exception ignored) {
         return VoidArchiveForgeData.MoveParticleStyle.PORTAL;
      }
   }

   private static VoidArchiveForgeData.MoveEntityAction parseAction(String value) {
      try {
         return VoidArchiveForgeData.MoveEntityAction.valueOf(value);
      } catch (Exception ignored) {
         return VoidArchiveForgeData.MoveEntityAction.NONE;
      }
   }

   private static VoidArchiveForgeData.MoveBlockAction parseBlockAction(String value) {
      try {
         return VoidArchiveForgeData.MoveBlockAction.valueOf(value);
      } catch (Exception ignored) {
         return VoidArchiveForgeData.MoveBlockAction.NONE;
      }
   }

   private static VoidArchiveForgeData.MoveCastMode parseCastMode(String value) {
      try {
         return VoidArchiveForgeData.MoveCastMode.valueOf(value);
      } catch (Exception ignored) {
         return VoidArchiveForgeData.MoveCastMode.DEFAULT;
      }
   }

   private static float clamp(float value, float min, float max) {
      return Math.max(min, Math.min(max, value));
   }

   public record ComponentData(String title, String subtitle) {
   }

   public record CreatedMove(
      String name,
      String primaryDamageId,
      String secondaryDamageId,
      @Nullable ResourceLocation effectId,
      int effectAmplifier,
      int effectDuration,
      VoidArchiveForgeData.MoveShape shape,
      VoidArchiveForgeData.MoveParticleStyle particleStyle,
      @Nullable ResourceLocation customParticleId,
      int range,
      float radius,
      float powerMultiplier,
      float speed,
      int burstCount,
      float barrierBypass,
      float resistanceBypass,
      float magiculeSteal,
      int selfBuffDuration,
      boolean multiHit,
      boolean healCaster,
      boolean pullTarget,
      boolean launchTarget,
      boolean followAim,
      boolean guardBreak,
      boolean ignite,
      boolean shockSlow,
      boolean makeInvulnerable,
      boolean giveGuarded,
      boolean trapTargets,
      VoidArchiveForgeData.MoveEntityAction entityAction,
      @Nullable ResourceLocation entityId,
      int summonCount,
      VoidArchiveForgeData.MoveBlockAction blockAction,
      VoidArchiveForgeData.MoveCastMode castMode,
      boolean projectileCast,
      boolean selfCast,
      boolean aoeCast
   ) {
   }

   private static final class DeployedTrap {
      private final UUID ownerId;
      @Nullable
      private final ManasSkillInstance skillInstance;
      private final VoidArchiveForgeData.CreatedMove move;
      private Vec3 position;
      private final float storedDamage;
      private int lifeTicks;
      private final float triggerRadius;
      @Nullable
      private UUID circleUuid;
      private int armTicks;
      private boolean triggered;

      private DeployedTrap(
         UUID ownerId,
         @Nullable ManasSkillInstance skillInstance,
         VoidArchiveForgeData.CreatedMove move,
         Vec3 position,
         float storedDamage,
         int lifeTicks,
         float triggerRadius,
         @Nullable UUID circleUuid,
         int armTicks
      ) {
         this.ownerId = ownerId;
         this.skillInstance = skillInstance;
         this.move = move;
         this.position = position;
         this.storedDamage = storedDamage;
         this.lifeTicks = lifeTicks;
         this.triggerRadius = triggerRadius;
         this.circleUuid = circleUuid;
         this.armTicks = armTicks;
         this.triggered = false;
      }
   }

   public record MeleeInfusion(
      String name,
      String damageId,
      @Nullable ResourceLocation effectId,
      int effectAmplifier,
      int effectDuration,
      float powerMultiplier,
      int durationTicks,
      boolean applyDamage,
      boolean applyEffect,
      boolean lifeSteal
   ) {
   }

   public enum MoveBlockAction {
      NONE,
      OBSIDIAN_WALL,
      ICE_FIELD,
      CRYSTAL_RING,
      GLASS_DOME,
      SPIKE_FIELD,
      VOID_CRATER;
   }

   public enum MoveCastMode {
      DEFAULT,
      SELF,
      PROJECTILE,
      TARGET_AREA;
   }

   public enum MoveEntityAction {
      NONE,
      SUMMON_RECORDED,
      REVIVE_RECORDED;
   }

   public enum MoveParticleStyle {
      PORTAL,
      FLAME,
      SOUL,
      LIGHTNING,
      WATER,
      WIND,
      DARK,
      LIGHT,
      ENCHANT,
      SMOKE,
      BLOCK,
      SHOCKWAVE,
      BLOSSOM,
      BLACK_FIRE,
      SNOW,
      ARCANE;
   }

   public enum MoveShape {
      TOUCH,
      AOE,
      BEAM,
      ORB,
      AURA,
      SUMMON,
      NOVA,
      WALL,
      LINE,
      RING,
      DOME,
      RAIN,
      SIGIL,
      PILLAR;
   }

   public record RecordedEntity(ResourceLocation entityId, int kills) {
   }
}
