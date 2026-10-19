package com.tno_tensura_extras.util;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.tno_tensura_extras.data.SeducerContractData;
import com.tno_tensura_extras.registry.ExtrasSkills;
import com.tno_tensura_extras.skill.unique.SeducerSkill;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.intrinsic.CharmSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.effect.template.TensuraMobEffectInstance;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.ForceTameEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobDespawnEvent;
import net.neoforged.neoforge.event.entity.living.MobDespawnEvent.Result;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public final class SeducerManager {
   private static final double CONTRACT_RANGE = 12.0;
   private static final double COMMAND_RANGE = 32.0;
   private static final int CONTRACT_COOLDOWN_SECONDS = 5;
   private static final SeducerContractOffers OFFERS = new SeducerContractOffers();
   private static final Map<UUID, Long> LAST_PUNISHMENT = new HashMap<>();
   private static final Set<UUID> PUNISHING = new HashSet<>();

   private SeducerManager() {
   }

   public static void contract(ManasSkillInstance instance, ServerPlayer owner, int mode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(owner, 12.0, false);
      if (target != null && target.isAlive() && owner.hasLineOfSight(target)) {
         SeducerContractData data = SeducerContractData.get(owner.server);
         if (owner.isShiftKeyDown()) {
            if (!data.release(owner.getUUID(), target.getUUID())) {
               feedback(owner, "tno_tensura_extras.seducer.not_contracted");
            } else {
               if (target instanceof Mob mob) {
                  releaseMob(mob, owner.getUUID());
               }

               OFFERS.remove(target.getUUID());
               feedback(owner, "tno_tensura_extras.seducer.released");
               if (target instanceof ServerPlayer player) {
                  feedback(player, "tno_tensura_extras.seducer.released");
               }

               instance.setCoolDown(1, mode);
            }
         } else if (canMakeContract(owner, target, instance, true)) {
            if (target instanceof ServerPlayer player) {
               SeducerContractOffers.Offer offer = OFFERS.create(player.getUUID(), owner.getUUID(), gameTime(owner.server));
               if (offer == null) {
                  feedback(owner, "tno_tensura_extras.seducer.offer_pending");
                  return;
               }

               Component accept = Component.translatable("tno_tensura_extras.seducer.accept")
                  .withStyle(
                     style -> style.withColor(ChatFormatting.GREEN)
                        .withClickEvent(new ClickEvent(Action.RUN_COMMAND, "/tensuraextras contract accept " + offer.token()))
                  );
               Component reject = Component.translatable("tno_tensura_extras.seducer.reject")
                  .withStyle(
                     style -> style.withColor(ChatFormatting.RED)
                        .withClickEvent(new ClickEvent(Action.RUN_COMMAND, "/tensuraextras contract reject " + offer.token()))
                  );
               player.sendSystemMessage(
                  Component.translatable("tno_tensura_extras.seducer.offer", new Object[]{owner.getDisplayName()})
                     .append(" ")
                     .append(accept)
                     .append(" ")
                     .append(reject)
               );
               feedback(owner, "tno_tensura_extras.seducer.offer_sent");
               instance.setCoolDown(5, mode);
            } else if (target instanceof Mob mob) {
               if (!isSurrendered(owner, mob)) {
                  feedback(owner, "tno_tensura_extras.seducer.not_surrendered");
                  return;
               }

               bind(owner, mob, instance);
            }
         }
      } else {
         feedback(owner, "tensura.targeting.not_targeted");
      }
   }

   public static void onRegisterCommands(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)Commands.literal("tensuraextras")
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("contract")
                        .then(
                           Commands.literal("accept")
                              .then(
                                 Commands.argument("offer", UuidArgument.uuid())
                                    .executes(
                                       context -> answer(
                                          ((CommandSourceStack)context.getSource()).getPlayerOrException(), UuidArgument.getUuid(context, "offer"), true
                                       )
                                    )
                              )
                        ))
                     .then(
                        Commands.literal("reject")
                           .then(
                              Commands.argument("offer", UuidArgument.uuid())
                                 .executes(
                                    context -> answer(
                                       ((CommandSourceStack)context.getSource()).getPlayerOrException(), UuidArgument.getUuid(context, "offer"), false
                                    )
                                 )
                           )
                     )
               )
         );
   }

   private static int answer(ServerPlayer subject, UUID token, boolean accept) {
      SeducerContractOffers.Offer offer = OFFERS.consume(subject.getUUID(), token, gameTime(subject.server));
      if (offer == null) {
         feedback(subject, "tno_tensura_extras.seducer.offer_expired");
         return 0;
      }

      if (!accept) {
         feedback(subject, "tno_tensura_extras.seducer.rejected");
         return 1;
      }

      ServerPlayer owner = subject.server.getPlayerList().getPlayer(offer.owner());
      ManasSkillInstance instance = owner == null ? null : activeSkill(owner);
      if (offer.expiresAt() <= gameTime(subject.server)
         || owner == null
         || instance == null
         || !owner.isAlive()
         || !subject.isAlive()
         || owner.level() != subject.level()
         || owner.distanceToSqr(subject) > 144.0
         || !owner.hasLineOfSight(subject)) {
         feedback(subject, "tno_tensura_extras.seducer.offer_expired");
         return 0;
      }

      if (canMakeContract(owner, subject, instance, true) && bind(owner, subject, instance)) {
         return 1;
      }

      feedback(subject, "tensura.ability.activation_failed");
      return 0;
   }

   private static boolean canMakeContract(ServerPlayer owner, LivingEntity target, ManasSkillInstance instance, boolean notify) {
      String failure = null;
      SeducerContractData data = SeducerContractData.get(owner.server);
      if (target == owner
         || !(target instanceof Mob) && !(target instanceof ServerPlayer)
         || target instanceof Player player && (player.isSpectator() || player.getAbilities().invulnerable)
         || target.level() != owner.level()
         || !target.isAlive()
         || hasAntiSkill(target)
         || hasExistingOwner(target)
         || !data.canBind(owner.getUUID(), target.getUUID())) {
         failure = "tno_tensura_extras.seducer.contract_unavailable";
      } else if (data.countOwnedBy(owner.getUUID()) >= (instance.isMastered(owner) ? 16 : 8)) {
         failure = "tno_tensura_extras.seducer.contract_limit";
      } else if (target instanceof Mob
         && (
            !CharmSkill.canMindControl(target, target.level(), false, false)
               || target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.RAMPAGE))
               || SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get())
         )) {
         failure = "tensura.ability.activation_failed";
      }

      if (failure != null && notify) {
         feedback(owner, failure);
      }

      return failure == null;
   }

   private static boolean bind(ServerPlayer owner, LivingEntity subject, ManasSkillInstance instance) {
      if (((ForceTameEvent)TensuraEntityEvents.FORCE_TAME_EVENT.invoker()).tame(subject, owner, false).isFalse()) {
         feedback(owner, "tensura.ability.activation_failed");
         return false;
      }

      if (EnergyHelper.isOutOfEnergy(owner, instance, 0)) {
         return false;
      }

      SeducerContractData data = SeducerContractData.get(owner.server);
      if (!data.bind(owner.getUUID(), subject.getUUID(), subject instanceof Mob mob && mob.isPersistenceRequired())) {
         return false;
      }

      if (subject instanceof Mob mob) {
         IExistence existence = TensuraStorages.getExistenceFrom(mob);
         existence.setPermanentOwner(owner.getUUID());
         existence.markDirty();
         if (mob instanceof ISubordinate subordinate) {
            subordinate.tame(owner);
         }

         if (mob instanceof TamableAnimal tamable) {
            tamable.setOwnerUUID(owner.getUUID());
            tamable.setTame(true, true);
         }

         removeOwnFear(owner, mob);
         SubordinateHelper.removeTarget(mob);
         SubordinateHelper.setFollow(mob);
         SubordinateHelper.setProtect(mob);
         installGoal(mob);
      } else {
         removeOwnFear(owner, subject);
      }

      instance.addMasteryPoint(owner);
      instance.setCoolDown(5, 0);
      owner.swing(InteractionHand.MAIN_HAND, true);
      owner.serverLevel().sendParticles(ParticleTypes.ENCHANT, subject.getX(), subject.getY() + 1.0, subject.getZ(), 12, 0.4, 0.4, 0.4, 0.0);
      owner.serverLevel().playSound(null, subject.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.7F, 0.8F);
      feedback(owner, "tno_tensura_extras.seducer.bound");
      if (subject instanceof ServerPlayer player) {
         feedback(player, "tno_tensura_extras.seducer.bound");
      }

      return true;
   }

   public static void command(ManasSkillInstance instance, ServerPlayer owner, int mode) {
      SeducerContractData data = SeducerContractData.get(owner.server);
      List<Mob> subjects = owner.level()
         .getEntitiesOfClass(
            Mob.class,
            owner.getBoundingBox().inflate(32.0),
            mobx -> mobx.isAlive() && owner.distanceToSqr(mobx) <= 1024.0 && data.isBoundTo(mobx.getUUID(), owner.getUUID())
         );
      if (subjects.isEmpty()) {
         feedback(owner, "tno_tensura_extras.seducer.not_contracted");
      } else {
         if (owner.isShiftKeyDown()) {
            boolean follow = subjects.stream().anyMatch(mobx -> !data.getContract(mobx.getUUID()).following());
            subjects.forEach(mobx -> setFollowing(owner, mobx, follow));
            feedback(owner, follow ? "tno_tensura_extras.seducer.follow" : "tno_tensura_extras.seducer.stay");
         } else {
            LivingEntity target = ObjectSelectionHelper.getTargetingEntity(owner, 32.0, false);
            if (target == null || !owner.hasLineOfSight(target)) {
               feedback(owner, "tensura.targeting.not_targeted");
               return;
            }

            if (target instanceof Mob mob && data.isBoundTo(mob.getUUID(), owner.getUUID())) {
               boolean follow = !data.getContract(mob.getUUID()).following();
               setFollowing(owner, mob, follow);
               feedback(owner, follow ? "tno_tensura_extras.seducer.follow" : "tno_tensura_extras.seducer.stay");
            } else {
               if (!canHarm(owner, target)) {
                  feedback(owner, "tensura.targeting.not_allowed");
                  return;
               }

               boolean accepted = false;

               for (Mob mob : subjects) {
                  setFollowing(owner, mob, true);
                  mob.setTarget(target);
                  if (mob.getTarget() == target) {
                     BrainUtils.setMemory(mob, MemoryModuleType.ATTACK_TARGET, target);
                     accepted = true;
                  }
               }

               if (!accepted) {
                  feedback(owner, "tensura.targeting.not_allowed");
                  return;
               }

               feedback(owner, "tno_tensura_extras.seducer.attack");
            }
         }

         owner.swing(InteractionHand.MAIN_HAND, true);
         instance.setCoolDown(1, mode);
      }
   }

   private static void setFollowing(ServerPlayer owner, Mob mob, boolean following) {
      SeducerContractData.get(owner.server).setFollowing(owner.getUUID(), mob.getUUID(), following);
      SubordinateHelper.removeTarget(mob);
      if (following) {
         SubordinateHelper.setFollow(mob);
      } else {
         SubordinateHelper.setStay(mob);
         mob.getNavigation().stop();
      }
   }

   public static boolean areAllies(LivingEntity owner, LivingEntity target) {
      if (owner != target && !owner.isAlliedTo(target) && !target.isAlliedTo(owner) && !SubordinateHelper.isAlly(owner, target)) {
         MinecraftServer server = owner.level().getServer();
         return server != null && SeducerContractData.get(server).areRelated(owner.getUUID(), target.getUUID());
      } else {
         return true;
      }
   }

   public static boolean canHarm(LivingEntity owner, LivingEntity target) {
      if (!owner.isAlive()
         || !target.isAlive()
         || owner.level() != target.level()
         || areAllies(owner, target)
         || target instanceof Player player && (player.isSpectator() || player.getAbilities().invulnerable)) {
         return false;
      } else {
         return owner instanceof ServerPlayer player && target instanceof Player other ? player.server.isPvpAllowed() && player.canHarmPlayer(other) : true;
      }
   }

   public static boolean isSurrendered(LivingEntity owner, LivingEntity target) {
      if (target.isAlive()
         && !(target.getHealth() > target.getMaxHealth() * 0.25F)
         && !(EnergyHelper.getMaxEP(target) > EnergyHelper.getMaxEP(owner))
         && !target.getType().is(TensuraEntityTags.NO_FEAR)
         && !hasAntiSkill(target)
         && !SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get())) {
         MobEffectInstance fear = target.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
         return fear != null && fear.getAmplifier() >= 4 && isOwnFear(owner, fear);
      } else {
         return false;
      }
   }

   private static boolean isOwnFear(LivingEntity owner, MobEffectInstance fear) {
      TensuraMobEffectInstance source = (TensuraMobEffectInstance)fear;
      AbilitySlot ability = source.tensura$getSourceAbility();
      return owner.getUUID().equals(source.tensura$getSource()) && ability != null && ability.getSkill() == ExtrasSkills.SEDUCER.get();
   }

   private static void removeOwnFear(LivingEntity owner, LivingEntity subject) {
      MobEffectInstance fear = subject.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
      if (fear != null && isOwnFear(owner, fear)) {
         subject.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
      }
   }

   private static boolean hasAntiSkill(LivingEntity target) {
      return SkillUtils.isSkillToggled(target, (ManasSkill)UniqueSkills.ANTI_SKILL.get())
         || TensuraStorages.getAbilityFrom(target).isAbilityInActivePreset((ManasSkill)UniqueSkills.ANTI_SKILL.get());
   }

   private static boolean hasExistingOwner(LivingEntity subject) {
      IExistence existence = TensuraStorages.getExistenceFrom(subject);
      return existence.getPermanentOwner() != null
         || existence.getTemporaryOwner() != null
         || existence.getSummoner() != null
         || SubordinateHelper.getSubordinateOwnerUUID(subject) != null
         || subject instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null
         || subject instanceof TamableAnimal tamable && tamable.isTame();
   }

   @Nullable
   private static ManasSkillInstance activeSkill(ServerPlayer owner) {
      ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)ExtrasSkills.SEDUCER.get()).orElse(null);
      return instance != null
            && instance.getMastery() >= 0.0
            && !owner.isSpectator()
            && ((SeducerSkill)ExtrasSkills.SEDUCER.get()).isInSlot(owner, instance)
            && instance.canInteractSkill(owner)
         ? instance
         : null;
   }

   public static void onAttackEntity(AttackEntityEvent event) {
      if (event.getEntity() instanceof ServerPlayer player && event.getTarget() instanceof LivingEntity victim && blockContractDamage(player, victim)) {
         event.setCanceled(true);
      }
   }

   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      if (!event.getEntity().level().isClientSide() && !(event.getOriginalAmount() <= 0.0F)) {
         LivingEntity attacker = findAttacker(event.getSource().getEntity());
         if (attacker == null) {
            attacker = findAttacker(event.getSource().getDirectEntity());
         }

         if (attacker != null && blockContractDamage(attacker, event.getEntity())) {
            event.setCanceled(true);
         }
      }
   }

   public static EventResult onSpiritualHurt(
      LivingEntity target,
      @Nullable Entity attacker,
      float originalAmount,
      Changeable<Float> resistance,
      Changeable<Float> amount,
      Changeable<DamageSource> source
   ) {
      if (!target.level().isClientSide() && !((Float)amount.get() <= 0.0F)) {
         LivingEntity living = findAttacker(attacker);
         if (living == null && source.get() != null) {
            living = findAttacker(((DamageSource)source.get()).getEntity());
            if (living == null) {
               living = findAttacker(((DamageSource)source.get()).getDirectEntity());
            }
         }

         return living != null && blockContractDamage(living, target) ? EventResult.interruptFalse() : EventResult.pass();
      } else {
         return EventResult.pass();
      }
   }

   private static boolean blockContractDamage(LivingEntity attacker, LivingEntity target) {
      MinecraftServer server = target.level().getServer();
      if (server != null && attacker != target) {
         SeducerContractData data = SeducerContractData.get(server);
         if (PUNISHING.contains(target.getUUID()) && data.isBoundTo(target.getUUID(), attacker.getUUID())) {
            return false;
         }

         LivingEntity responsible = attacker;

         for (int depth = 0; depth < 8 && responsible != null; depth++) {
            if (data.isBoundTo(responsible.getUUID(), target.getUUID())) {
               punish(responsible, target);
               return true;
            }

            if (data.areRelated(responsible.getUUID(), target.getUUID())) {
               return true;
            }

            UUID ownerId = SubordinateHelper.getSubordinateOwnerUUID(responsible);
            if (ownerId == null && responsible instanceof OwnableEntity ownable) {
               ownerId = ownable.getOwnerUUID();
            }

            if (ownerId == null || ownerId.equals(responsible.getUUID())) {
               break;
            }

            LivingEntity nextOwner = findLiving(server, ownerId);
            if (data.isBoundTo(ownerId, target.getUUID())) {
               if (nextOwner != null) {
                  punish(nextOwner, target);
               }

               return true;
            }

            if (data.areRelated(ownerId, target.getUUID())) {
               return true;
            }

            responsible = nextOwner;
         }

         return false;
      } else {
         return false;
      }
   }

   private static void punish(LivingEntity subject, LivingEntity owner) {
      long now = gameTime(Objects.requireNonNull(owner.level().getServer()));
      Long last = LAST_PUNISHMENT.get(subject.getUUID());
      if ((last == null || now - last >= 20L) && PUNISHING.add(subject.getUUID())) {
         LAST_PUNISHMENT.put(subject.getUUID(), now);

         try {
            double health = TensuraStorages.getExistenceFrom(subject).getSpiritualHealth();
            if (Double.isFinite(health) && health > 0.0) {
               DamageSource source = TensuraDamageTypes.getEntityDamageSource(subject.level(), TensuraDamageTypes.SOUL_CONSUMED, owner);
               TensuraDamageHelper.directSpiritualHurt(subject, owner, source, (float)Math.min(Float.MAX_VALUE, Math.max(1.0, health * 0.1)));
            }

            feedback(subject, "tno_tensura_extras.seducer.breach");
         } finally {
            PUNISHING.remove(subject.getUUID());
         }
      }
   }

   @Nullable
   private static LivingEntity findAttacker(@Nullable Entity entity) {
      for (int depth = 0; entity != null && depth < 8; depth++) {
         if (entity instanceof LivingEntity living) {
            return living;
         }

         if (entity instanceof Projectile projectile) {
            entity = projectile.getOwner();
         } else if (entity instanceof PrimedTnt tnt) {
            entity = tnt.getOwner();
         } else {
            if (!(entity instanceof OwnableEntity ownable)) {
               return null;
            }

            entity = ownable.getOwner();
         }
      }

      return null;
   }

   @Nullable
   private static LivingEntity findLiving(MinecraftServer server, UUID id) {
      ServerPlayer player = server.getPlayerList().getPlayer(id);
      if (player != null) {
         return player;
      }

      for (ServerLevel level : server.getAllLevels()) {
         if (level.getEntity(id) instanceof LivingEntity living) {
            return living;
         }
      }

      return null;
   }

   public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
      LivingEntity entity = event.getEntity();
      LivingEntity target = event.getNewAboutToBeSetTarget();
      if (!entity.level().isClientSide()
         && target != null
         && SeducerContractData.get(Objects.requireNonNull(entity.level().getServer())).getContract(entity.getUUID()) != null
         && areAllies(entity, target)) {
         event.setCanceled(true);
      }
   }

   public static EventResult onForceTame(LivingEntity subject, @Nullable Entity owner, boolean temporary) {
      MinecraftServer server = subject.level().getServer();
      if (server != null) {
         SeducerContractData.Contract contract = SeducerContractData.get(server).getContract(subject.getUUID());
         if (contract != null && (owner == null || !contract.owner().equals(owner.getUUID()))) {
            return EventResult.interruptFalse();
         }
      }

      return EventResult.pass();
   }

   public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
      if (event.getLevel() instanceof ServerLevel level
         && event.getEntity() instanceof Mob mob
         && SeducerContractData.get(level.getServer()).getContract(mob.getUUID()) != null) {
         installGoal(mob);
      }
   }

   public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
      if (event.getLevel() instanceof ServerLevel level
         && event.getEntity() instanceof Mob mob
         && (mob.getRemovalReason() == RemovalReason.KILLED || mob.getRemovalReason() == RemovalReason.DISCARDED)) {
         SeducerContractData.get(level.getServer()).remove(mob.getUUID());
      }
   }

   public static void onMobDespawn(MobDespawnEvent event) {
      Mob mob = event.getEntity();
      MinecraftServer server = mob.level().getServer();
      if (server != null && SeducerContractData.get(server).getContract(mob.getUUID()) != null) {
         event.setResult(Result.DENY);
      }
   }

   public static void onEntityTick(Post event) {
      if (event.getEntity() instanceof Mob mob && mob.level() instanceof ServerLevel level) {
         SeducerContractData.Contract contract = SeducerContractData.get(level.getServer()).getContract(mob.getUUID());
         if (contract != null) {
            LivingEntity target = mob.getTarget();
            if (!contract.following()) {
               if (target != null) {
                  SubordinateHelper.removeTarget(mob);
               }

               BrainUtils.clearMemory(mob, MemoryModuleType.WALK_TARGET);
               mob.getNavigation().stop();
            } else if (mob.tickCount % 10 == 0) {
               if (target != null && areAllies(mob, target)) {
                  SubordinateHelper.removeTarget(mob);
               }

               moveSubject(mob, contract);
            }
         }
      }
   }

   private static void moveSubject(Mob mob, SeducerContractData.Contract contract) {
      if (!contract.following()) {
         mob.getNavigation().stop();
      } else if (mob.getTarget() == null || !mob.getTarget().isAlive()) {
         ServerPlayer owner = Objects.requireNonNull(mob.level().getServer()).getPlayerList().getPlayer(contract.owner());
         if (owner != null && owner.isAlive() && owner.level() == mob.level() && !owner.isSpectator()) {
            if (mob.distanceToSqr(owner) > 9.0) {
               mob.getNavigation().moveTo(owner, 1.1);
            } else {
               mob.getNavigation().stop();
            }
         } else {
            mob.getNavigation().stop();
         }
      }
   }

   private static void installGoal(Mob mob) {
      if (mob.goalSelector.getAvailableGoals().stream().noneMatch(wrapped -> wrapped.getGoal() instanceof SeducerManager.ContractGoal)) {
         mob.goalSelector.addGoal(1, new SeducerManager.ContractGoal(mob));
      }
   }

   private static void releaseMob(Mob mob, UUID owner) {
      mob.goalSelector.removeAllGoals(goal -> goal instanceof SeducerManager.ContractGoal);
      SubordinateHelper.removeTarget(mob);
      SubordinateHelper.setFollow(mob);
      IExistence existence = TensuraStorages.getExistenceFrom(mob);
      if (owner.equals(existence.getPermanentOwner())) {
         existence.setPermanentOwner(null);
         existence.markDirty();
      }

      if (mob instanceof ISubordinate subordinate && owner.equals(subordinate.getOwnerUUID())) {
         subordinate.resetOwner(null);
      }

      if (mob instanceof TamableAnimal tamable && owner.equals(tamable.getOwnerUUID())) {
         tamable.setOwnerUUID(null);
         tamable.setTame(false, true);
         tamable.setOrderedToSit(false);
      }
   }

   public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
      OFFERS.cancel(event.getEntity().getUUID());
   }

   public static void onPlayerClone(Clone event) {
      OFFERS.cancel(event.getEntity().getUUID());
   }

   public static void onServerTick(net.neoforged.neoforge.event.tick.ServerTickEvent.Post event) {
      if (event.getServer().getTickCount() % 20 == 0) {
         long now = gameTime(event.getServer());
         OFFERS.expire(now);
         LAST_PUNISHMENT.entrySet().removeIf(entry -> now - entry.getValue() >= 20L);
      }
   }

   public static void onServerStopped(ServerStoppedEvent event) {
      OFFERS.clear();
      LAST_PUNISHMENT.clear();
      PUNISHING.clear();
   }

   private static long gameTime(MinecraftServer server) {
      return server.overworld().getGameTime();
   }

   private static void feedback(LivingEntity entity, String key) {
      if (entity instanceof ServerPlayer player) {
         player.displayClientMessage(Component.translatable(key), true);
      }
   }

   private static final class ContractGoal extends Goal {
      private final Mob mob;

      private ContractGoal(Mob mob) {
         this.mob = mob;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         SeducerContractData.Contract contract = SeducerContractData.get(Objects.requireNonNull(this.mob.level().getServer())).getContract(this.mob.getUUID());
         return contract != null && (!contract.following() || this.mob.getTarget() == null);
      }

      public void tick() {
         SeducerContractData.Contract contract = SeducerContractData.get(Objects.requireNonNull(this.mob.level().getServer())).getContract(this.mob.getUUID());
         if (contract != null) {
            SeducerManager.moveSubject(this.mob, contract);
         }
      }

      public void stop() {
         this.mob.getNavigation().stop();
      }
   }
}
