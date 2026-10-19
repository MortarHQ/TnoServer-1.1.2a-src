package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.MitamaFaithSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.PathsEffectNetwork;
import com.github.wal_bos.moreskills.net.msg.MitamaAtomicEdictPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class MitamaAtomicEdictHandler {
   private static final Map<Integer, MitamaAtomicEdictHandler.State> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;
   private static final int DURATION = 700;
   private static final int RELEASE_TICK = 430;
   private static final double WATCH_RANGE = 440.0;

   private MitamaAtomicEdictHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MitamaAtomicEdictHandler::serverTick);
      }
   }

   public static void cast(ManasSkillInstance instance, LivingEntity caster) {
      init();
      if (!EnergyHelper.isOutOfEnergy(caster, instance, 4)) {
         if (caster.level() instanceof ServerLevel level) {
            double var13 = MitamaFaithSkill.getWorship(instance);
            double radius = Math.min(
               (Double)MoreSkillsConfigs.GRAND.mitamaFaith.edictRadiusCap.get(),
               (Double)MoreSkillsConfigs.GRAND.mitamaFaith.edictBaseRadius.get()
                  + Math.sqrt(var13) / Math.max(1.0, (Double)MoreSkillsConfigs.GRAND.mitamaFaith.edictRadiusSqrtDivisor.get())
            );
            double damage = (Double)MoreSkillsConfigs.GRAND.mitamaFaith.edictBaseDamage.get() * MitamaFaithSkill.getDamageMultiplier(var13);
            int amplifier = Math.min(
               (Integer)MoreSkillsConfigs.GRAND.mitamaFaith.edictOppressionMaxAmplifier.get(),
               (int)(var13 / Math.max(1.0, (Double)MoreSkillsConfigs.GRAND.mitamaFaith.edictOppressionWorshipDivisor.get()))
            );
            Vec3 origin = caster.position();
            int id = caster.getId() * 131071 + caster.tickCount;
            ACTIVE.put(
               id,
               new MitamaAtomicEdictHandler.State(
                  level,
                  caster.getUUID(),
                  caster.getId(),
                  origin,
                  radius,
                  damage,
                  amplifier,
                  (Integer)MoreSkillsConfigs.GRAND.mitamaFaith.edictOppressionDurationTicks.get()
               )
            );
            MitamaAtomicEdictPayload.send(level, caster, origin, (float)radius, 700, 430);
            instance.addMasteryPoint(caster);
            instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.mitamaFaith.edictCooldownTicks.get(), 4);
            caster.swing(InteractionHand.MAIN_HAND, true);
            level.playSound(
               null, caster.getX(), caster.getY(), caster.getZ(), (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(), SoundSource.PLAYERS, 1.8F, 0.42F
            );
            level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.3F, 0.46F);
            level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 2.0F, 0.3F);
            if (caster instanceof Player player) {
               player.displayClientMessage(Component.literal("I...").withStyle(ChatFormatting.LIGHT_PURPLE), true);
            }
         }
      }
   }

   private static void serverTick(Post event) {
      if (!ACTIVE.isEmpty()) {
         Iterator<Entry<Integer, MitamaAtomicEdictHandler.State>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            MitamaAtomicEdictHandler.State state = iterator.next().getValue();
            state.tick();
            if (state.done()) {
               iterator.remove();
            }
         }
      }
   }

   private static boolean isOwnedBy(LivingEntity owner, LivingEntity target) {
      if (target == owner) {
         return true;
      } else {
         LivingEntity subordinateOwner = SubordinateHelper.getSubordinateOwner(target);
         if (subordinateOwner != null && Objects.equals(subordinateOwner.getUUID(), owner.getUUID())) {
            return true;
         } else if (target instanceof TamableAnimal tamable) {
            return tamable.isOwnedBy(owner);
         } else {
            IExistence existence = TensuraStorages.getExistenceFrom(target);
            return Objects.equals(existence.getPermanentOwner(), owner.getUUID());
         }
      }
   }

   private static void healFollower(LivingEntity user, LivingEntity target, double worship) {
      target.heal(
         (float)Math.min(
            Float.MAX_VALUE,
            (Double)MoreSkillsConfigs.GRAND.mitamaFaith.followerHealBase.get()
               + worship / Math.max(1.0, (Double)MoreSkillsConfigs.GRAND.mitamaFaith.followerHealWorshipDivisor.get())
         )
      );
      IExistence existence = TensuraStorages.getExistenceFrom(target);
      existence.setMagicule(
         Math.min(
            EnergyHelper.getMaxMagicule(target),
            existence.getMagicule()
               + (Double)MoreSkillsConfigs.GRAND.mitamaFaith.followerEnergyBase.get()
               + worship / Math.max(1.0, (Double)MoreSkillsConfigs.GRAND.mitamaFaith.followerEnergyWorshipDivisor.get())
         )
      );
      existence.setAura(
         Math.min(
            EnergyHelper.getMaxAura(target),
            existence.getAura()
               + (Double)MoreSkillsConfigs.GRAND.mitamaFaith.followerEnergyBase.get()
               + worship / Math.max(1.0, (Double)MoreSkillsConfigs.GRAND.mitamaFaith.followerEnergyWorshipDivisor.get())
         )
      );
      existence.markDirty();
   }

   private static void breakEdictBlocks(ServerLevel level, LivingEntity caster, ManasSkillInstance instance, Vec3 origin, double radius) {
      if (TensuraGameRules.canSkillGrief(level)) {
         if (!((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker()).grief(instance, level, caster, origin.x, origin.y, origin.z).isFalse()) {
            float power = Mth.clamp((float)(radius * 0.42), 8.0F, 28.0F);
            level.explode(caster, origin.x, origin.y - 1.0, origin.z, power, false, ExplosionInteraction.TNT);
            ((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_POS.invoker()).grief(instance, level, caster, origin.x, origin.y, origin.z);
         }
      }
   }

   private static final class State {
      private final ServerLevel level;
      private final UUID casterUuid;
      private final int casterId;
      private final Vec3 origin;
      private final double radius;
      private final double damage;
      private final int oppressionAmplifier;
      private final int oppressionDuration;
      private int age;
      private boolean released;

      private State(
         ServerLevel level, UUID casterUuid, int casterId, Vec3 origin, double radius, double damage, int oppressionAmplifier, int oppressionDuration
      ) {
         this.level = level;
         this.casterUuid = casterUuid;
         this.casterId = casterId;
         this.origin = origin;
         this.radius = radius;
         this.damage = damage;
         this.oppressionAmplifier = oppressionAmplifier;
         this.oppressionDuration = oppressionDuration;
      }

      private void tick() {
         this.age++;
         LivingEntity caster = this.resolveCaster();
         if (caster != null && caster.isAlive()) {
            if (this.age == 210) {
               if (caster instanceof Player player) {
                  player.displayClientMessage(Component.literal("Am...").withStyle(ChatFormatting.LIGHT_PURPLE), true);
               }

               this.level.playSound(null, this.origin.x, this.origin.y, this.origin.z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 2.5F, 0.28F);
            }

            if (this.age == 70 || this.age == 145 || this.age == 270 || this.age == 355) {
               this.level
                  .playSound(
                     null, this.origin.x, this.origin.y, this.origin.z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.65F, 0.3F + this.age * 0.0015F
                  );
            }

            if (this.age == 335 || this.age == 395) {
               this.level.playSound(null, this.origin.x, this.origin.y, this.origin.z, SoundEvents.BEACON_AMBIENT, SoundSource.PLAYERS, 2.1F, 0.38F);
            }

            if (!this.released && this.age >= 430) {
               this.released = true;
               this.release(caster);
            }
         } else {
            this.released = true;
            this.age = 740;
         }
      }

      private void release(LivingEntity caster) {
         double worship = 0.0;
         ManasSkillInstance mitama = (ManasSkillInstance)SkillAPI.getSkillsFrom(caster).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get()).orElse(null);
         if (mitama != null) {
            worship = MitamaFaithSkill.getWorship(mitama);
         }

         MitamaAtomicEdictHandler.breakEdictBlocks(this.level, caster, mitama, this.origin, this.radius);
         AABB area = new AABB(
            this.origin.x - this.radius,
            this.origin.y - this.radius * 0.85,
            this.origin.z - this.radius,
            this.origin.x + this.radius,
            this.origin.y + this.radius * 3.6,
            this.origin.z + this.radius
         );

         for (LivingEntity target : this.level
            .getEntitiesOfClass(
               LivingEntity.class, area, targetx -> targetx != caster && targetx.isAlive() && targetx.distanceToSqr(this.origin) <= this.radius * this.radius
            )) {
            if (MitamaAtomicEdictHandler.isOwnedBy(caster, target)) {
               MitamaAtomicEdictHandler.healFollower(caster, target, worship);
            } else {
               target.hurt(
                  TensuraDamageTypes.getEntityDamageSource(this.level, TensuraDamageTypes.HOLY_DAMAGE, caster), (float)Math.min(Float.MAX_VALUE, this.damage)
               );
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), this.oppressionDuration, this.oppressionAmplifier, false, false, true
                  )
               );
            }
         }

         double rangeSqr = 193600.0;

         for (ServerPlayer player : this.level.players()) {
            if (player.distanceToSqr(this.origin.x, this.origin.y, this.origin.z) <= rangeSqr) {
               PathsEffectNetwork.sendShake(player, 260, 10.5F, 18.0F);
            }
         }

         this.level
            .sendParticles(
               ParticleTypes.END_ROD, this.origin.x, this.origin.y + 1.0, this.origin.z, 320, this.radius * 0.42, this.radius * 0.32, this.radius * 0.42, 0.16
            );
         this.level
            .sendParticles(
               ParticleTypes.ELECTRIC_SPARK,
               this.origin.x,
               this.origin.y + 1.0,
               this.origin.z,
               260,
               this.radius * 0.46,
               this.radius * 0.34,
               this.radius * 0.46,
               0.22
            );
         this.level
            .sendParticles(
               ParticleTypes.POOF, this.origin.x, this.origin.y + 0.2, this.origin.z, 120, this.radius * 0.34, this.radius * 0.1, this.radius * 0.34, 0.05
            );
         this.level
            .playSound(null, this.origin.x, this.origin.y, this.origin.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 4.2F, 0.34F);
         this.level.playSound(null, this.origin.x, this.origin.y, this.origin.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 4.4F, 0.28F);
         this.level.playSound(null, this.origin.x, this.origin.y, this.origin.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 4.0F, 0.26F);
         this.level
            .playSound(
               null, this.origin.x, this.origin.y, this.origin.z, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 3.0F, 0.5F
            );
         if (caster instanceof Player player) {
            player.displayClientMessage(Component.literal("Atomic.").withStyle(ChatFormatting.LIGHT_PURPLE), true);
         }
      }

      private LivingEntity resolveCaster() {
         if (this.casterUuid != null && this.level.getEntity(this.casterUuid) instanceof LivingEntity living) {
            return living;
         } else {
            return this.level.getEntity(this.casterId) instanceof LivingEntity living ? living : null;
         }
      }

      private boolean done() {
         return this.age >= 764;
      }
   }
}
