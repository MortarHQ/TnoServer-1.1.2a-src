package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.AlbisVinaSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AlbisVinaVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.world.entity.ArsWeissCloneEntity;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.TickEvent.Player;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.DimensionTravelEvent;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AlbisVinaHandler {
   private static final Map<UUID, List<ArsWeissCloneEntity>> CLONES = new HashMap<>();
   private static final Set<UUID> PACKAGE_GRANTED = new HashSet<>();
   private static final List<AlbisVinaHandler.QueuedSpear> SPEARS = new ArrayList<>();
   private static final ResourceKey<DamageType> WATER_ELEMENTAL = ResourceKey.create(
      Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("tensura", "water_elemental")
   );

   private AlbisVinaHandler() {
   }

   private static MoreSkillsConfigs.AlbisVinaConfig cfg() {
      return MoreSkillsConfigs.GRAND.albisVina;
   }

   public static void init() {
      SkillEvents.ACTIVATE_SKILL.register((SkillActivationEvent)(skillInstance, owner, keyNumber, mode) -> {
         if (owner instanceof ServerPlayer player) {
            ManasSkillInstance instance = (ManasSkillInstance)skillInstance.get();
            if (instance.getSkill() instanceof Magic magic) {
               startCloneMagicCasts(player, instance, magic, keyNumber, mode);
            }
         }

         return EventResult.pass();
      });
      TickEvent.PLAYER_POST.register((Player)player -> {
         if (player instanceof ServerPlayer serverPlayer) {
            tickPlayer(serverPlayer);
         }
      });
      TensuraEntityEvents.DIMENSION_TRAVEL_EVENT.register((DimensionTravelEvent)(target, teleporter, dimension) -> {
         if (target instanceof ServerPlayer player) {
            removeAllClones(player);
            clearPlayer(player.getUUID());
         }

         return EventResult.pass();
      });
   }

   public static void addClone(UUID owner, ArsWeissCloneEntity clone) {
      CLONES.computeIfAbsent(owner, id -> new ArrayList<>()).add(clone);
   }

   public static void forgetClone(ArsWeissCloneEntity clone) {
      UUID owner = clone.getOwnerUUIDValue();
      if (owner != null) {
         List<ArsWeissCloneEntity> list = CLONES.get(owner);
         if (list != null) {
            list.removeIf(current -> current == clone || !current.isAlive() || current.isRemoved());
            if (list.isEmpty()) {
               CLONES.remove(owner);
            }
         }
      }
   }

   public static List<ArsWeissCloneEntity> getClones(UUID owner) {
      List<ArsWeissCloneEntity> list = CLONES.get(owner);
      if (list == null) {
         return List.of();
      }

      list.removeIf(clone -> clone == null || !clone.isAlive() || clone.isRemoved());
      return List.copyOf(list);
   }

   public static void removeAllClones(net.minecraft.world.entity.player.Player player) {
      removeAllClones(player.getUUID());
   }

   public static void removeAllClones(ServerPlayer player) {
      removeAllClones(player.getUUID());
   }

   public static void removeAllClones(UUID owner) {
      List<ArsWeissCloneEntity> list = CLONES.remove(owner);
      if (list != null) {
         for (ArsWeissCloneEntity clone : list) {
            if (clone != null && clone.isAlive()) {
               clone.shatter(false);
            }
         }
      }
   }

   public static void clearPlayer(UUID uuid) {
      CLONES.remove(uuid);
   }

   public static boolean isFormulaBlacklisted(ResourceLocation id) {
      return ((List)cfg().formulaSuccessionBlacklist.get()).contains(id.toString());
   }

   public static boolean isWaterOrIce(DamageSource source) {
      if (source == null) {
         return false;
      } else if (source.is(TensuraDamageTypes.ICE_ELEMENTAL)) {
         return true;
      } else {
         return source.is(WATER_ELEMENTAL) ? true : source.typeHolder().unwrapKey().map(key -> {
            String path = key.location().getPath();
            return path.contains("ice") || path.contains("water") || path.contains("frost") || path.contains("cold") || path.contains("blizzard");
         }).orElse(false);
      }
   }

   public static boolean isOwnerOrClone(LivingEntity owner, Entity attacker) {
      if (attacker == owner) {
         return true;
      } else {
         return attacker instanceof ArsWeissCloneEntity clone ? owner.getUUID().equals(clone.getOwnerUUIDValue()) : false;
      }
   }

   public static void queueSpear(UUID owner, Vec3 impact, int delay, float damage, boolean main) {
      SPEARS.add(new AlbisVinaHandler.QueuedSpear(owner, impact, delay, damage, main));
   }

   public static void grantAlbisPackage(LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         if ((Boolean)cfg().grantPackageOnAcquire.get()) {
            if (PACKAGE_GRANTED.add(player.getUUID()) || (Boolean)cfg().retryPackageGrantOnToggle.get()) {
               for (String id : (List)cfg().grantedSkillIds.get()) {
                  ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(id));
                  if (skill != null && !SkillUtils.hasSkill(player, skill)) {
                     SkillHelper.learnSkill(player, skill);
                  }
               }
            }
         }
      }
   }

   private static void startCloneMagicCasts(ServerPlayer player, ManasSkillInstance instance, Magic magic, int keyNumber, int mode) {
      ManasSkillInstance albis = SkillUtils.getSkillOrNull(player, (ManasSkill)AllSkills.ALBIS_VINA.get());
      ManasSkillInstance elfaria = SkillUtils.getSkillOrNull(player, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
      boolean albisConnect = albis != null && albis.isToggled();
      boolean elfariaConnect = elfaria != null && elfaria.isToggled();
      if (albisConnect || elfariaConnect) {
         if (instance.is(TensuraSkillTags.MAGIC)) {
            if (isSupportedFormulaMagic(instance, elfariaConnect)) {
               if ((Boolean)cfg().formulaAllowStaffCasting.get() || !Magic.isStaffCasting(instance, player)) {
                  if (!isFormulaBlacklisted(instance.getSkillId())) {
                     if (!(instance.getMastery() < 0.0)) {
                        int castTime = Math.max(1, magic.getCastingTime(instance, player));
                        List<ArsWeissCloneEntity> clones = getClones(player.getUUID());
                        if (!clones.isEmpty()) {
                           Vec3 look = player.getLookAngle();
                           boolean masteredAlbis = albisConnect && albis != null && albis.isMastered(player);
                           boolean masteredElfaria = elfariaConnect && elfaria != null && elfaria.isMastered(player);
                           int albisLimit = masteredAlbis ? 3 : 2;
                           int albisStarted = 0;

                           for (ArsWeissCloneEntity clone : clones) {
                              if (!albisConnect || albisStarted >= albisLimit) {
                                 break;
                              }

                              if (clone.isAlbisFormulaClone() && clone.isAlive() && !clone.isRemoved() && !clone.isContinuingCast()) {
                                 clone.startMirroredMagicCast(
                                    instance.getSkillId(), mode, keyNumber, castTime, look, player.getXRot(), player.getYRot(), instance.toNBT()
                                 );
                                 albisStarted++;
                              }
                           }

                           if (elfariaConnect) {
                              int elfariaLimit = masteredElfaria ? clones.size() : Math.min(clones.size(), 4);
                              int elfariaStarted = 0;

                              for (ArsWeissCloneEntity clone : clones) {
                                 if (elfariaStarted >= elfariaLimit) {
                                    break;
                                 }

                                 if (clone.isElfariaWorkerClone() && clone.isAlive() && !clone.isRemoved() && !clone.isContinuingCast()) {
                                    clone.startMirroredMagicCast(
                                       instance.getSkillId(), mode, keyNumber, castTime, look, player.getXRot(), player.getYRot(), instance.toNBT()
                                    );
                                    elfariaStarted++;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isSupportedFormulaMagic(ManasSkillInstance instance, boolean ultimateConnect) {
      if (ultimateConnect) {
         return instance.is(TensuraSkillTags.MAGIC);
      } else if ((Boolean)cfg().formulaAllowAllMagic.get()) {
         return instance.is(TensuraSkillTags.MAGIC);
      } else if (instance.is(TensuraSkillTags.ASPECTUAL_MAGIC)) {
         return true;
      } else if (instance.is(TensuraSkillTags.SPIRITUAL_MAGIC)) {
         return true;
      } else if (instance.is(TensuraSkillTags.SUMMONING_MAGIC)) {
         return (Boolean)cfg().formulaAllowSummoningMagic.get();
      } else {
         return instance.is(TensuraSkillTags.NECROMANCY_MAGIC) ? (Boolean)cfg().formulaAllowNecromancyMagic.get() : false;
      }
   }

   private static void tickPlayer(ServerPlayer player) {
      tickSpears(player);
      ManasSkillInstance albis = SkillUtils.getSkillOrNull(player, (ManasSkill)AllSkills.ALBIS_VINA.get());
      ManasSkillInstance elfaria = SkillUtils.getSkillOrNull(player, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
      if (albis == null && elfaria == null) {
         removeAllClones(player);
         clearPlayer(player.getUUID());
      } else {
         if (albis != null) {
            grantAlbisPackage(player);
         }

         updateCloneList(player);
      }
   }

   private static void updateCloneList(ServerPlayer player) {
      List<ArsWeissCloneEntity> list = CLONES.get(player.getUUID());
      if (list != null) {
         list.removeIf(clone -> clone == null || clone.isRemoved() || !clone.isAlive() || clone.level().dimension() != player.level().dimension());
         if (list.isEmpty()) {
            CLONES.remove(player.getUUID());
         }
      }
   }

   private static void tickSpears(ServerPlayer player) {
      if (!SPEARS.isEmpty()) {
         Iterator<AlbisVinaHandler.QueuedSpear> iterator = SPEARS.iterator();

         while (iterator.hasNext()) {
            AlbisVinaHandler.QueuedSpear spear = iterator.next();
            if (spear.owner.equals(player.getUUID())) {
               spear.delay--;
               if (spear.delay > 0) {
                  Vec3 top = spear.impact.add(0.0, 5.0, 0.0);
                  player.level().addParticle((ParticleOptions)TensuraParticleTypes.SNOWFLAKE.get(), top.x, top.y, top.z, 0.0, -0.08, 0.0);
               } else {
                  strikeSpear(player, spear);
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void strikeSpear(ServerPlayer owner, AlbisVinaHandler.QueuedSpear spear) {
      Vec3 impact = spear.impact;
      if (owner.level() instanceof ServerLevel level) {
         AlbisVinaVfxPayload.sendFieldImpact(level, impact, spear.main ? 5.2F : 3.8F, spear.main);
      }

      TensuraParticleHelper.spawnServerParticles(
         owner.level(),
         (ParticleOptions)TensuraParticleTypes.SNOWFLAKE_EFFECT.get(),
         impact.x,
         impact.y + 0.8,
         impact.z,
         spear.main ? 42 : 22,
         0.55,
         1.0,
         0.55,
         0.13,
         true
      );
      AABB box = new AABB(impact.x - 1.3, impact.y - 0.8, impact.z - 1.3, impact.x + 1.3, impact.y + 2.8, impact.z + 1.3);
      ManasSkillInstance instance = SkillUtils.getSkillOrNull(owner, (ManasSkill)AllSkills.ALBIS_VINA.get());
      if (instance != null) {
         DamageSource source = ((AlbisVinaSkill)AllSkills.ALBIS_VINA.get()).createSource(instance, owner, TensuraDamageTypes.ICE_ELEMENTAL, 2);

         for (LivingEntity target : owner.level()
            .getEntitiesOfClass(LivingEntity.class, box, entity -> entity.isAlive() && entity != owner && !entity.isAlliedTo(owner))) {
            target.hurt(source, spear.damage);
            AlbisVinaSkill.applyDiamondDust(owner, target, instance.isMastered(owner));
         }
      }
   }

   public static int cloneIndex(UUID owner, ArsWeissCloneEntity target) {
      List<ArsWeissCloneEntity> list = CLONES.get(owner);
      if (list != null && target != null) {
         list.removeIf(clone -> clone == null || !clone.isAlive() || clone.isRemoved());

         for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == target || list.get(i).getUUID().equals(target.getUUID())) {
               return i;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private static final class QueuedSpear {
      private final UUID owner;
      private final Vec3 impact;
      private int delay;
      private final float damage;
      private final boolean main;

      private QueuedSpear(UUID owner, Vec3 impact, int delay, float damage, boolean main) {
         this.owner = owner;
         this.impact = impact;
         this.delay = delay;
         this.damage = damage;
         this.main = main;
      }
   }
}
