package com.tensura_tno.ability.skill;

import com.mojang.datafixers.util.Pair;
import com.tensura_tno.compat.IceAndFireEntityCompat;
import com.tensura_tno.network.SpiritSummonPackets;
import com.tensura_tno.race.fox_spirit.FoxSpiritSummonBonus;
import com.tensura_tno.race.fox_spirit.SpiritSummonEntityHelper;
import com.tensura_tno.race.fox_spirit.SpiritSummonLifecycle;
import com.tensura_tno.registry.TensuraTNOSkills;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.data.existence.EntityExistenceData;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.template.TensuraRideableEntity;
import io.github.manasmods.tensura.entity.template.subclass.ILivingPartEntity;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SpiritSummonSkill extends Skill {
   private static final double ABSORB_MP_COST = 500.0;
   private static final double SUMMON_MP_COST = 500.0;
   private static final double SUMMON_MAGICULE_COST_RATIO = 0.5;
   private static final int DEFAULT_SUMMON_MAGICULE = 50;
   private static final int ABSORB_COOLDOWN_SECONDS = 30;
   private static final int SUMMON_COOLDOWN_SECONDS = 5;
   private static final double ABSORB_RANGE = 5.0;
   private static final TagKey<EntityType<?>> SPIRIT_SUMMON_BLACKLIST = TagKey.create(
      Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("tensura_tno", "spirit_summon_blacklist")
   );
   private static final int CAST_TIME = 20;
   private static final int ANIMATION_TICKS = 40;
   private static final String NBT_ABSORB_TARGET = "SpiritAbsorbTargetUUID";
   private static final String NBT_CIRCLE_X = "circleX";
   private static final String NBT_CIRCLE_Y = "circleY";
   private static final String NBT_CIRCLE_Z = "circleZ";
   private static final String NBT_SUMMON_UUID = "SummonUUID";
   private static final String NBT_CIRCLE_ABSORB = "MagicCircleAbsorbID";
   private static final String NBT_CIRCLE_SUMMON = "MagicCircleSummonID";
   private static final String NBT_SUMMON_MP_COST = "SummonMagiculeCost";
   public static final String NBT_PENDING_SUMMON = "tensura_tno_pending_summon";

   public SpiritSummonSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/skill/spirit_summon.png");
   }

   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withColor(8087790);
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "spirit_summon.absorb";
         case 1 -> "spirit_summon.summon";
         default -> "default";
      };
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return switch (mode) {
         case 0 -> 1;
         case 1 -> 0;
         default -> 0;
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      if (mode == 0) {
         return 500.0;
      }

      if (mode != 1) {
         return 0.0;
      }

      CompoundTag tag = instance.getTag();
      if (tag != null && tag.contains("SummonMagiculeCost")) {
         return tag.getDouble("SummonMagiculeCost");
      }

      if (entity instanceof ServerPlayer player) {
         String pendingId = player.getPersistentData().getString("tensura_tno_pending_summon");
         if (!pendingId.isEmpty()) {
            return getSummonMagiculeCost(player.level(), pendingId);
         }
      }

      return 0.0;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return false;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (entity instanceof ServerPlayer player) {
            switch (mode) {
               case 0:
                  this.startAbsorb(instance, player);
                  break;
               case 1:
                  this.startSummon(instance, player);
            }
         }
      }
   }

   private void startAbsorb(ManasSkillInstance instance, ServerPlayer player) {
      if (!instance.onCoolDown(0)) {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         if (!player.isCreative() && existence.getMagicule() < 500.0) {
            player.displayClientMessage(Component.translatable("tensura.skill.lack_magicule").setStyle(Style.EMPTY.withColor(16733525)), true);
         } else {
            Mob target = this.getTargetMobStrictly(player);
            if (!SpiritSummonLifecycle.isSummoning(target)) {
               if (target != null) {
                  if (!target.getType().is(SPIRIT_SUMMON_BLACKLIST)) {
                     if (IceAndFireEntityCompat.isDragonRemains(target)) {
                        showDragonRemainsRejected(player);
                     } else {
                        if (SpiritSummonLimits.isVanillaOnlyEnforced(player.level())) {
                           ResourceLocation targetTypeId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
                           if (!SpiritSummonLimits.isVanilla(targetTypeId)) {
                              player.displayClientMessage(
                                 Component.translatable("tensura_tno.spirit_summon.target_not_vanilla").setStyle(Style.EMPTY.withColor(16733525)), true
                              );
                              return;
                           }
                        }

                        if (!SpiritSummonLimits.isMaxHealthAllowed(player.level(), target)) {
                           int limit = SpiritSummonLimits.getMaxHealthLimit(player.level());
                           player.displayClientMessage(
                              Component.translatable("tensura_tno.spirit_summon.target_max_hp_too_high", new Object[]{limit})
                                 .setStyle(Style.EMPTY.withColor(16733525)),
                              true
                           );
                        } else if (target.getHealth() > target.getMaxHealth() * 0.5F) {
                           player.displayClientMessage(
                              Component.translatable("tensura_tno.spirit_summon.target_hp_too_high").setStyle(Style.EMPTY.withColor(16733525)), true
                           );
                        } else if (target instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null) {
                           player.displayClientMessage(
                              Component.translatable("tensura_tno.spirit_summon.target_has_owner").setStyle(Style.EMPTY.withColor(16733525)), true
                           );
                        } else {
                           CompoundTag tag = instance.getOrCreateTag();
                           tag.putUUID("SpiritAbsorbTargetUUID", target.getUUID());
                           tag.putDouble("circleX", target.getX());
                           tag.putDouble("circleY", target.getY());
                           tag.putDouble("circleZ", target.getZ());
                           instance.markDirty();
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void startSummon(ManasSkillInstance instance, ServerPlayer player) {
      String pendingId = player.getPersistentData().getString("tensura_tno_pending_summon");
      if (!pendingId.isEmpty()) {
         if (instance.onCoolDown(1)) {
            return;
         }

         if (!SpiritSummonSkill.SpiritSummonPockets.hasAbsorbedEntity(player, pendingId)) {
            player.getPersistentData().remove("tensura_tno_pending_summon");
            this.cleanupSummon(instance, player);
            return;
         }

         double summonCost = getSummonMagiculeCost(player.level(), pendingId);
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         if (!player.isCreative() && existence.getMagicule() < summonCost) {
            player.displayClientMessage(Component.translatable("tensura.skill.lack_magicule").setStyle(Style.EMPTY.withColor(16733525)), true);
            player.getPersistentData().remove("tensura_tno_pending_summon");
            this.cleanupSummon(instance, player);
            return;
         }

         int maxSummons = FoxSpiritSummonBonus.getMaxSummons(player);
         if (maxSummons <= 0) {
            player.getPersistentData().remove("tensura_tno_pending_summon");
            this.cleanupSummon(instance, player);
            return;
         }

         int activeCount = this.countActiveSummons(player);
         if (activeCount >= maxSummons) {
            player.displayClientMessage(
               Component.translatable("tensura_tno.spirit_summon.summon_limit_reached", new Object[]{maxSummons}).setStyle(Style.EMPTY.withColor(16733525)),
               true
            );
            player.getPersistentData().remove("tensura_tno_pending_summon");
            this.cleanupSummon(instance, player);
            return;
         }

         BlockHitResult hitResult = ObjectSelectionHelper.getPlayerPOVHitResult(player.level(), player, Fluid.NONE, 10.0);
         BlockPos hitPos = hitResult.getBlockPos();
         if (!player.level().getBlockState(hitPos.below()).isSolid() || !player.level().getBlockState(hitPos.below(2)).isSolid()) {
            this.cleanupSummon(instance, player);
            return;
         }

         Vec3 pos = hitResult.getLocation();
         CompoundTag tag = instance.getOrCreateTag();
         tag.putDouble("circleX", pos.x);
         tag.putDouble("circleY", pos.y);
         tag.putDouble("circleZ", pos.z);
         tag.putDouble("SummonMagiculeCost", summonCost);
         tag.remove("SummonUUID");
         instance.markDirty();
      } else {
         List<CompoundTag> absorbedEntries = SpiritSummonSkill.SpiritSummonPockets.getAbsorbedEntries(player);
         if (absorbedEntries.isEmpty()) {
            player.displayClientMessage(Component.translatable("tensura_tno.spirit_summon.no_absorbed").setStyle(Style.EMPTY.withColor(16733525)), true);
            return;
         }

         ArrayList<String> parts = new ArrayList<>();
         Registry<EntityExistenceData> registry = player.level().registryAccess().registryOrThrow(TensuraCustomData.ENTITY_EXISTENCE);

         for (CompoundTag entry : absorbedEntries) {
            String eid = entry.getString("id");
            double bep = entry.getDouble("bonus_ep");
            int hp = 20;
            int aura = 50;
            int magicule = 50;
            ResourceLocation entryId = ResourceLocation.tryParse(eid);
            EntityExistenceData data = entryId == null ? null : (EntityExistenceData)registry.get(entryId);
            if (data != null) {
               hp = data.spiritualHP();
               aura = data.minAura();
               magicule = data.minMagicule();
            }

            parts.add(eid + ":" + bep + ":" + hp + ":" + aura + ":" + magicule);
         }

         String csv = String.join(",", parts);
         NetworkManager.sendToPlayer(player, new SpiritSummonPackets.OpenScreenPayload(csv));
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return false;
      }

      if (entity instanceof ServerPlayer player) {
         return switch (mode) {
            case 0 -> this.handleAbsorbHeld(instance, player, heldTicks);
            case 1 -> this.handleSummonHeld(instance, player, heldTicks);
            default -> false;
         };
      } else {
         return false;
      }
   }

   private boolean handleAbsorbHeld(ManasSkillInstance instance, ServerPlayer player, int heldTicks) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.hasUUID("SpiritAbsorbTargetUUID")) {
         return false;
      }

      UUID targetUUID = tag.getUUID("SpiritAbsorbTargetUUID");
      if (((ServerLevel)player.level()).getEntity(targetUUID) instanceof Mob mob && mob.isAlive()) {
         Pair<Double, Double> cost = Pair.of(0.0, 500.0);
         int sinkTick = heldTicks - 20;
         if (heldTicks == 0) {
            mob.setNoAi(true);
            mob.noPhysics = true;
            mob.setInvulnerable(true);
         }

         if (sinkTick >= 0 && sinkTick < 40) {
            mob.setPos(mob.position().subtract(0.0, mob.getBbHeight() * 1.5F / 39.0, 0.0));
         }

         Vec3 mobPos = mob.position();
         MagicCircle.castMagicCircle("MagicCircleAbsorbID", 3.0F, 30, mobPos, MagicCircleVariant.DEMON, false, player, tag, instance, 0, cost);
         player.level().playSound(null, mobPos.x, mobPos.y, mobPos.z, (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), SoundSource.PLAYERS, 0.5F, 1.2F);
         if (sinkTick < 40) {
            return true;
         }

         this.completeAbsorb(instance, player, mob);
         return false;
      } else {
         this.cleanupAbsorb(instance);
         return false;
      }
   }

   private void completeAbsorb(ManasSkillInstance instance, ServerPlayer player, Mob mob) {
      if (IceAndFireEntityCompat.isDragonRemains(mob)) {
         showDragonRemainsRejected(player);
         this.cancelAbsorb(instance, player);
      } else {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         if (!player.isCreative()) {
            existence.setMagicule(existence.getMagicule() - 500.0);
            existence.markDirty();
         }

         ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType());
         if (entityId != null) {
            SpiritSummonSkill.SpiritSummonPockets.addAbsorbedEntity(player, entityId.toString());
         }

         RaceAPI.getRaceFrom(player).getRace().ifPresent(raceInstance -> {
            int count = SpiritSummonSkill.SpiritSummonPockets.getAbsorbedEntities(player).size();
            raceInstance.getOrCreateTag().putInt("absorbedTypesCount", count);
            raceInstance.markDirty();
         });
         mob.discard();
         instance.setCoolDown(30, 0);
         this.cleanupAbsorb(instance);
         instance.markDirty();
      }
   }

   private void cleanupAbsorb(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("SpiritAbsorbTargetUUID");
      tag.remove("circleX");
      tag.remove("circleY");
      tag.remove("circleZ");
      tag.remove("MagicCircleAbsorbID");
      instance.markDirty();
   }

   private boolean handleSummonHeld(ManasSkillInstance instance, ServerPlayer player, int heldTicks) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.contains("circleX")) {
         return false;
      }

      Vec3 circlePos = new Vec3(tag.getDouble("circleX"), tag.getDouble("circleY"), tag.getDouble("circleZ"));
      BlockPos groundPos = ObjectSelectionHelper.getBlockPos(circlePos);
      if (player.level().getBlockState(groundPos.below()).isSolid() && player.level().getBlockState(groundPos.below(2)).isSolid()) {
         double summonCost = tag.contains("SummonMagiculeCost")
            ? tag.getDouble("SummonMagiculeCost")
            : getSummonMagiculeCost(player.level(), player.getPersistentData().getString("tensura_tno_pending_summon"));
         Pair<Double, Double> cost = Pair.of(0.0, summonCost);
         int riseTick = heldTicks - 20;
         MagicCircle.castMagicCircle("MagicCircleSummonID", 3.0F, 30, circlePos, MagicCircleVariant.DEMON, false, player, tag, instance, 1, cost);
         player.level().playSound(null, circlePos.x, circlePos.y, circlePos.z, (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), SoundSource.PLAYERS, 0.5F, 1.5F);
         if (riseTick < 0) {
            return true;
         }

         if (riseTick == 0) {
            this.createSummonEntity(instance, player, circlePos);
         }

         if (!tag.hasUUID("SummonUUID")) {
            this.cleanupSummonAndClearPending(instance, player);
            return false;
         } else {
            UUID summonUUID = tag.getUUID("SummonUUID");
            if (!(((ServerLevel)player.level()).getEntity(summonUUID) instanceof Mob mob && mob.isAlive())) {
               this.cleanupSummonAndClearPending(instance, player);
               return false;
            } else if (riseTick < 40) {
               SpiritSummonLifecycle.refresh(mob);
               mob.setPos(mob.position().add(0.0, mob.getBbHeight() * 1.5F / 39.0, 0.0));
               TensuraParticleHelper.addServerParticlesAroundSelf(mob, ParticleTypes.PORTAL, 2.0);
               return true;
            } else {
               this.completeSummon(instance, player, mob);
               return false;
            }
         }
      } else {
         this.cancelSummon(instance, player);
         return false;
      }
   }

   private void createSummonEntity(ManasSkillInstance instance, ServerPlayer player, Vec3 pos) {
      String pendingId = player.getPersistentData().getString("tensura_tno_pending_summon");
      if (pendingId.isEmpty()) {
         this.cleanupSummonAndClearPending(instance, player);
      } else {
         ResourceLocation rl = ResourceLocation.tryParse(pendingId);
         if (rl != null && BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
            EntityType<?> rawType = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(rl);
            if (rawType.create(player.level()) instanceof Mob mob) {
               mob.setNoAi(true);
               mob.noPhysics = true;
               mob.setPos(pos.add(0.0, -1.5F * mob.getBbHeight(), 0.0));
               mob.finalizeSpawn(
                  (ServerLevelAccessor)player.level(),
                  player.level().getCurrentDifficultyAt(mob.blockPosition()),
                  MobSpawnType.MOB_SUMMONED,
                  (SpawnGroupData)null
               );
               SpiritSummonLifecycle.begin(mob, player);
               if (!player.level().addFreshEntity(mob)) {
                  mob.discard();
                  this.cleanupSummonAndClearPending(instance, player);
               } else {
                  instance.getOrCreateTag().putUUID("SummonUUID", mob.getUUID());
                  instance.markDirty();
               }
            } else {
               this.cleanupSummonAndClearPending(instance, player);
            }
         } else {
            this.cleanupSummonAndClearPending(instance, player);
         }
      }
   }

   private void completeSummon(ManasSkillInstance instance, ServerPlayer player, Mob mob) {
      CompoundTag tag = instance.getOrCreateTag();
      double summonCost = tag.contains("SummonMagiculeCost")
         ? tag.getDouble("SummonMagiculeCost")
         : getSummonMagiculeCost(player.level(), player.getPersistentData().getString("tensura_tno_pending_summon"));
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      if (!player.isCreative() && existence.getMagicule() < summonCost) {
         player.displayClientMessage(Component.translatable("tensura.skill.lack_magicule").setStyle(Style.EMPTY.withColor(16733525)), true);
         mob.discard();
         this.cleanupSummonAndClearPending(instance, player);
      } else {
         if (!player.isCreative()) {
            existence.setMagicule(existence.getMagicule() - summonCost);
            existence.markDirty();
         }

         if (mob instanceof ISubordinate sub) {
            sub.tame(player);
         } else if (mob instanceof TamableAnimal ta) {
            ta.setOwnerUUID(player.getUUID());
            ta.setTame(true, true);
         } else if (mob instanceof AbstractHorse horse) {
            horse.tameWithName(player);
         }

         if (mob instanceof TensuraRideableEntity rideable) {
            rideable.setSaddled(true);
         }

         mob.skipDropExperience();
         mob.addTag("stextras_had_owner");
         IExistence summonEx = TensuraStorages.getExistenceFrom(mob);
         SpiritSummonEntityHelper.markSpiritSummon(mob, player);
         summonEx.setSummonedSecond(FoxSpiritSummonBonus.getSummonDurationSeconds(player));
         ResourceLocation mobId = BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType());
         if (mobId != null) {
            Registry<EntityExistenceData> registry = player.level().registryAccess().registryOrThrow(TensuraCustomData.ENTITY_EXISTENCE);
            EntityExistenceData data = (EntityExistenceData)registry.get(mobId);
            if (data != null) {
               int fixedAura = data.minAura();
               int fixedMagicule = data.minMagicule();
               AttributeInstance auraAttr = mob.getAttribute(TensuraAttributes.MAX_AURA);
               if (auraAttr != null) {
                  auraAttr.setBaseValue(fixedAura);
                  summonEx.setAura(fixedAura);
               }

               AttributeInstance mpAttr = mob.getAttribute(TensuraAttributes.MAX_MAGICULE);
               if (mpAttr != null) {
                  mpAttr.setBaseValue(fixedMagicule);
                  summonEx.setMagicule(fixedMagicule);
               }
            }

            double bonusEP = SpiritSummonSkill.SpiritSummonPockets.getBonusEP(player, mobId.toString());
            if (bonusEP > 0.0) {
               double baseEP = EnergyHelper.getBaseMaxEP(mob);
               double bonusHalf = bonusEP / 2.0;
               EnergyHelper.gainAura(mob, bonusHalf, GainType.MAX);
               EnergyHelper.gainMagicule(mob, bonusHalf, GainType.MAX);
               if (baseEP > 0.0) {
                  AttributeInstance hpAttr = mob.getAttribute(Attributes.MAX_HEALTH);
                  if (hpAttr != null) {
                     double hpBoost = hpAttr.getBaseValue() * (bonusEP / baseEP);
                     hpAttr.setBaseValue(hpAttr.getBaseValue() + hpBoost);
                     mob.setHealth(mob.getMaxHealth());
                  }
               }
            }
         }

         summonEx.markDirty();
         SpiritSummonLifecycle.finish(mob);
         String pendingId = player.getPersistentData().getString("tensura_tno_pending_summon");
         if (!pendingId.isEmpty()) {
            player.getPersistentData().remove("tensura_tno_pending_summon");
         }

         player.level().playSound(null, mob.getX(), mob.getY(), mob.getZ(), (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
         TensuraParticleHelper.addServerParticlesAroundSelf(mob, ParticleTypes.FLASH, 3.0);
         TensuraParticleHelper.addServerParticlesAroundSelf(mob, ParticleTypes.FLASH, 2.0);
         instance.setCoolDown(5, 1);
         this.cleanupSummon(instance, player);
         instance.markDirty();
      }
   }

   private void cleanupSummon(ManasSkillInstance instance, ServerPlayer player) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("SummonUUID");
      tag.remove("circleX");
      tag.remove("circleY");
      tag.remove("circleZ");
      tag.remove("MagicCircleSummonID");
      tag.remove("SummonMagiculeCost");
      instance.markDirty();
   }

   private static void showDragonRemainsRejected(ServerPlayer player) {
      player.displayClientMessage(Component.translatable("tensura_tno.spirit_summon.target_dragon_remains").setStyle(Style.EMPTY.withColor(16733525)), true);
   }

   private void cleanupSummonAndClearPending(ManasSkillInstance instance, ServerPlayer player) {
      player.getPersistentData().remove("tensura_tno_pending_summon");
      this.cleanupSummon(instance, player);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (entity instanceof ServerPlayer player) {
            switch (mode) {
               case 0:
                  this.cancelAbsorb(instance, player);
                  break;
               case 1:
                  this.cancelSummon(instance, player);
            }
         }
      }
   }

   private void cancelAbsorb(ManasSkillInstance instance, ServerPlayer player) {
      CompoundTag tag = instance.getTag();
      if (tag != null && tag.hasUUID("SpiritAbsorbTargetUUID")) {
         UUID targetUUID = tag.getUUID("SpiritAbsorbTargetUUID");
         if (((ServerLevel)player.level()).getEntity(targetUUID) instanceof Mob mob) {
            mob.teleportTo(tag.getDouble("circleX"), tag.getDouble("circleY"), tag.getDouble("circleZ"));
            mob.setNoAi(false);
            mob.noPhysics = false;
            mob.setInvulnerable(false);
         }

         this.cleanupAbsorb(instance);
      }
   }

   private void cancelSummon(ManasSkillInstance instance, ServerPlayer player) {
      CompoundTag tag = instance.getTag();
      if (tag == null) {
         this.cleanupSummon(instance, player);
      } else {
         if (tag.hasUUID("SummonUUID")) {
            UUID summonUUID = tag.getUUID("SummonUUID");
            if (((ServerLevel)player.level()).getEntity(summonUUID) instanceof Mob mob && SpiritSummonLifecycle.isSummoning(mob)) {
               mob.discard();
            }
         }

         this.cleanupSummon(instance, player);
      }
   }

   @Nullable
   private Mob getTargetMobStrictly(ServerPlayer player) {
      Vec3 eyePos = player.getEyePosition();
      Vec3 lookVec = player.getLookAngle();
      Vec3 endPos = eyePos.add(lookVec.scale(5.0));
      AABB searchBox = player.getBoundingBox().expandTowards(lookVec.scale(5.0)).inflate(1.0);
      double closestDist = Double.MAX_VALUE;
      Mob hitMob = null;

      for (Entity entity : player.level().getEntities(player, searchBox)) {
         if (entity instanceof Mob mob) {
            AABB entityBox = entity.getBoundingBox().inflate(0.3);
            Optional<Vec3> hit = entityBox.clip(eyePos, endPos);
            if (hit.isPresent()) {
               double dist = eyePos.distanceTo(hit.get());
               if (dist < closestDist) {
                  closestDist = dist;
                  hitMob = mob;
               }
            }
         }
      }

      if (hitMob instanceof ILivingPartEntity part && part.getHead() instanceof Mob headMob) {
         hitMob = headMob;
      }

      return hitMob;
   }

   public static void summonFromPocket(ServerPlayer player, String entityId) {
      player.getPersistentData().putString("tensura_tno_pending_summon", entityId);
   }

   private static double getSummonMagiculeCost(Level level, String entityId) {
      ResourceLocation id = ResourceLocation.tryParse(entityId);
      if (id == null) {
         return 25.0;
      }

      Registry<EntityExistenceData> registry = level.registryAccess().registryOrThrow(TensuraCustomData.ENTITY_EXISTENCE);
      EntityExistenceData data = (EntityExistenceData)registry.get(id);
      int magicule = data != null ? data.minMagicule() : 50;
      return magicule * 0.5;
   }

   private int countActiveSummons(ServerPlayer player) {
      UUID playerUUID = player.getUUID();
      int count = 0;

      for (Entity entity : ((ServerLevel)player.level()).getAllEntities()) {
         if (entity instanceof Mob mob) {
            try {
               IExistence ex = TensuraStorages.getExistenceFrom(mob);
               if (playerUUID.equals(ex.getSummoner())
                  && ex.getSummonedAbility() != null
                  && ex.getSummonedAbility().getSkill() == TensuraTNOSkills.SPIRIT_SUMMON.get()) {
                  count++;
               }
            } catch (Exception var8) {
            }
         }
      }

      return count;
   }

   public static void removeAllActiveSummons(ServerPlayer player) {
      UUID playerUUID = player.getUUID();
      List<Mob> toRemove = new ArrayList<>();

      for (Entity entity : ((ServerLevel)player.level()).getAllEntities()) {
         if (entity instanceof Mob mob) {
            try {
               IExistence ex = TensuraStorages.getExistenceFrom(mob);
               if (playerUUID.equals(ex.getSummoner())
                  && ex.getSummonedAbility() != null
                  && ex.getSummonedAbility().getSkill() == TensuraTNOSkills.SPIRIT_SUMMON.get()) {
                  toRemove.add(mob);
               }
            } catch (Exception var7) {
            }
         }
      }

      for (Mob mob : toRemove) {
         mob.discard();
      }
   }

   public static class SpiritSummonPockets {
      private static final String TAG_ROOT = "tensura_tno_spirit_summon";
      private static final String TAG_ABSORBED = "absorbed_entities";
      private static final String TAG_ENTITY_ID = "id";
      private static final String TAG_BONUS_EP = "bonus_ep";

      public static List<String> getAbsorbedEntities(ServerPlayer player) {
         CompoundTag root = ensureMigrated(player);
         ArrayList<String> list = new ArrayList<>();

         for (Tag t : root.getList("absorbed_entities", 10)) {
            if (t instanceof CompoundTag ct) {
               list.add(ct.getString("id"));
            }
         }

         return list;
      }

      public static List<CompoundTag> getAbsorbedEntries(ServerPlayer player) {
         CompoundTag root = ensureMigrated(player);
         ArrayList<CompoundTag> list = new ArrayList<>();

         for (Tag t : root.getList("absorbed_entities", 10)) {
            if (t instanceof CompoundTag ct) {
               list.add(ct);
            }
         }

         return list;
      }

      public static double getBonusEP(ServerPlayer player, String entityId) {
         CompoundTag root = ensureMigrated(player);

         for (Tag t : root.getList("absorbed_entities", 10)) {
            if (t instanceof CompoundTag ct && ct.getString("id").equals(entityId)) {
               return ct.getDouble("bonus_ep");
            }
         }

         return 0.0;
      }

      public static boolean hasAbsorbedEntity(ServerPlayer player, String entityId) {
         CompoundTag root = ensureMigrated(player);

         for (Tag t : root.getList("absorbed_entities", 10)) {
            if (t instanceof CompoundTag ct && ct.getString("id").equals(entityId)) {
               return true;
            }
         }

         return false;
      }

      public static void addAbsorbedEntity(ServerPlayer player, String entityId) {
         CompoundTag root = ensureMigrated(player);
         ListTag tagList = root.getList("absorbed_entities", 10);

         for (Tag t : tagList) {
            if (t instanceof CompoundTag ct && ct.getString("id").equals(entityId)) {
               return;
            }
         }

         CompoundTag entry = new CompoundTag();
         entry.putString("id", entityId);
         entry.putDouble("bonus_ep", 0.0);
         tagList.add(entry);
         root.put("absorbed_entities", tagList);
         player.getPersistentData().put("tensura_tno_spirit_summon", root);
      }

      public static void addBonusEP(ServerPlayer player, String entityId, double amount) {
         CompoundTag root = ensureMigrated(player);
         ListTag tagList = root.getList("absorbed_entities", 10);

         for (Tag t : tagList) {
            if (t instanceof CompoundTag ct && ct.getString("id").equals(entityId)) {
               ct.putDouble("bonus_ep", ct.getDouble("bonus_ep") + amount);
               root.put("absorbed_entities", tagList);
               player.getPersistentData().put("tensura_tno_spirit_summon", root);
               return;
            }
         }
      }

      public static void setBonusEP(ServerPlayer player, String entityId, double value) {
         CompoundTag root = ensureMigrated(player);
         ListTag tagList = root.getList("absorbed_entities", 10);

         for (Tag t : tagList) {
            if (t instanceof CompoundTag ct && ct.getString("id").equals(entityId)) {
               ct.putDouble("bonus_ep", value);
               root.put("absorbed_entities", tagList);
               player.getPersistentData().put("tensura_tno_spirit_summon", root);
               return;
            }
         }
      }

      public static void removeAbsorbedEntity(ServerPlayer player, String entityId) {
         CompoundTag root = ensureMigrated(player);
         ListTag tagList = root.getList("absorbed_entities", 10);
         ListTag newList = new ListTag();

         for (Tag t : tagList) {
            if (t instanceof CompoundTag ct && !ct.getString("id").equals(entityId)) {
               newList.add(ct);
            }
         }

         root.put("absorbed_entities", newList);
         player.getPersistentData().put("tensura_tno_spirit_summon", root);
      }

      public static void clearAll(ServerPlayer player) {
         player.getPersistentData().remove("tensura_tno_spirit_summon");
      }

      private static CompoundTag ensureMigrated(ServerPlayer player) {
         CompoundTag root = player.getPersistentData().getCompound("tensura_tno_spirit_summon");
         if (!root.contains("absorbed_entities")) {
            return root;
         }

         ListTag compoundList = root.getList("absorbed_entities", 10);
         if (!compoundList.isEmpty()) {
            return root;
         }

         ListTag stringList = root.getList("absorbed_entities", 8);
         if (stringList.isEmpty()) {
            return root;
         }

         ListTag newList = new ListTag();

         for (Tag t : stringList) {
            CompoundTag entry = new CompoundTag();
            entry.putString("id", t.getAsString());
            entry.putDouble("bonus_ep", 0.0);
            newList.add(entry);
         }

         root.put("absorbed_entities", newList);
         player.getPersistentData().put("tensura_tno_spirit_summon", root);
         return root;
      }
   }
}
