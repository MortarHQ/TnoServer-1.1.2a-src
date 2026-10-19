package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.MitamaAtomicEdictHandler;
import com.github.wal_bos.moreskills.handler.MitamaDivineBeamsHandler;
import com.github.wal_bos.moreskills.handler.MitamaFaithHandler;
import com.github.wal_bos.moreskills.handler.UniqueOwnerSkillHandler;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class MitamaFaithSkill extends Skill {
   public static final int MODE_RECRUIT = 0;
   public static final int MODE_TRANSFER = 1;
   public static final int MODE_BEAMS = 2;
   public static final int MODE_CREATION = 3;
   public static final int MODE_EDICT = 4;
   private static final String FOLLOWERS = "MitamaFollowers";
   private static final String WORSHIP = "MitamaWorship";
   private static final String WORSHIP_BONUS = "MitamaWorshipBonus";
   private static final ResourceLocation HP_MODIFIER = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_faith_hp");
   private static final ResourceLocation DAMAGE_MODIFIER = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_faith_damage");
   private static final ResourceLocation MAGICULE_MODIFIER = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_faith_magicule");
   private static final ResourceLocation AURA_MODIFIER = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_faith_aura");
   private static final ResourceLocation NULL_RESIST = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_faith_null_resistance");
   private static final ResourceLocation NULL_LAW = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_faith_null_law");
   private static final ResourceLocation NULL_DODGE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_faith_null_dodge_negate");

   public MitamaFaithSkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.MitamaFaithConfig cfg() {
      return MoreSkillsConfigs.GRAND.mitamaFaith;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/mitama.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)cfg().acquirementMagiculeCost.get();
   }

   public int getMaxMastery() {
      return (Integer)cfg().maxMastery.get();
   }

   public int getModes(ManasSkillInstance instance) {
      return 5;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 4 : mode - 1;
      } else {
         return mode >= 4 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "mitama_faith.recruit";
         case 1 -> "mitama_faith.transfer";
         case 2 -> "mitama_faith.beams";
         case 3 -> "mitama_faith.creation";
         case 4 -> "mitama_faith.edict";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      double worship = getWorship(instance);
      double discount = Math.max((Double)cfg().minimumCostMultiplier.get(), 1.0 - worship / Math.max(1.0, (Double)cfg().worshipCostDiscountDivisor.get()));

      return switch (mode) {
         case 0 -> cfg().recruitCost.get() * discount;
         case 1 -> cfg().transferCost.get() * discount;
         case 2 -> cfg().beamsCost.get() * discount;
         case 3 -> cfg().creationModeCost.get() * discount;
         case 4 -> cfg().edictCost.get() * discount;
         default -> 0.0;
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      UniqueOwnerSkillHandler.learnExclusiveSkill(instance, entity);
      applyFaithAttributes(instance, entity);
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      applyFaithAttributes(instance, entity);
      if (SkillUtils.isSkillToggled(entity, this)) {
         applyNullAttributes(entity);
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      applyNullAttributes(entity);
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.literal("Null: ACTIVE").withStyle(ChatFormatting.GOLD), true);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      removeNullAttributes(entity);
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.literal("Null: INACTIVE").withStyle(ChatFormatting.RED), true);
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return entity.tickCount % 80 == 0;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         this.cleanDeadFollowers(instance, entity);
         applyFaithAttributes(instance, entity);
         if (SkillUtils.isSkillToggled(entity, this)) {
            applyNullAttributes(entity);
         }
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      UniqueOwnerSkillHandler.releaseSkill(instance, entity);
      AttributeHelper.removeAttribute(entity, Attributes.MAX_HEALTH, HP_MODIFIER);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.MAX_MAGICULE, MAGICULE_MODIFIER);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.MAX_AURA, AURA_MODIFIER);
      removeNullAttributes(entity);
      MitamaFaithHandler.clearGod(entity.getUUID());
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         switch (mode) {
            case 0:
               this.recruit(instance, entity);
               break;
            case 1:
               this.transfer(instance, entity);
               break;
            case 2:
               this.castBeams(instance, entity);
               break;
            case 3:
               this.openCreation(instance, entity);
               break;
            case 4:
               this.castEdict(instance, entity);
         }
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      amount.set((float)(((Float)amount.get()).floatValue() * getDamageMultiplier(getWorship(instance))));
      return true;
   }

   public static double getWorship(ManasSkillInstance instance) {
      return Math.max(0.0, instance.getOrCreateTag().getDouble("MitamaWorship"));
   }

   public static double getDamageMultiplier(double worship) {
      return (Double)cfg().damageMultiplierBase.get() + worship / Math.max(1.0, (Double)cfg().damageMultiplierWorshipDivisor.get());
   }

   public static boolean isFollower(ManasSkillInstance instance, UUID uuid) {
      return getFollower(instance, uuid) != null;
   }

   public static boolean addFollower(ManasSkillInstance instance, UUID uuid, String name, boolean player, double points) {
      CompoundTag tag = instance.getOrCreateTag();
      ListTag followers = tag.getList("MitamaFollowers", 10);
      CompoundTag old = getFollowerFromList(followers, uuid);
      if (old != null) {
         old.putDouble("Points", Math.max(old.getDouble("Points"), points));
         old.putString("Name", name);
         old.putBoolean("Player", player);
         recalcWorship(instance);
         instance.markDirty();
         return false;
      } else {
         CompoundTag follower = new CompoundTag();
         follower.putUUID("UUID", uuid);
         follower.putString("Name", name);
         follower.putBoolean("Player", player);
         follower.putDouble("Points", Math.max(player ? 100.0 : 1.0, points));
         followers.add(follower);
         tag.put("MitamaFollowers", followers);
         recalcWorship(instance);
         instance.markDirty();
         return true;
      }
   }

   public static boolean removeFollower(ManasSkillInstance instance, UUID uuid) {
      if (uuid == null) {
         return false;
      }

      CompoundTag tag = instance.getOrCreateTag();
      ListTag followers = tag.getList("MitamaFollowers", 10);
      ListTag kept = new ListTag();
      boolean removed = false;

      for (Tag raw : followers) {
         CompoundTag follower = (CompoundTag)raw;
         if (follower.hasUUID("UUID") && Objects.equals(follower.getUUID("UUID"), uuid)) {
            removed = true;
         } else {
            kept.add(follower);
         }
      }

      if (removed) {
         tag.put("MitamaFollowers", kept);
         recalcWorship(instance);
         instance.markDirty();
      }

      return removed;
   }

   public static void addWorship(ManasSkillInstance instance, double amount) {
      if (!(amount <= 0.0)) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putDouble("MitamaWorshipBonus", tag.getDouble("MitamaWorshipBonus") + amount);
         recalcWorship(instance);
         instance.markDirty();
      }
   }

   public static void subtractWorship(ManasSkillInstance instance, double amount) {
      if (!(amount <= 0.0)) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putDouble("MitamaWorshipBonus", Math.max(0.0, tag.getDouble("MitamaWorshipBonus") - amount));
         recalcWorship(instance);
         instance.markDirty();
      }
   }

   public static List<MitamaFaithSkill.MitamaOfferingEntry> buildSkillEntries(ServerPlayer viewer, MitamaFaithSkill.MitamaMenuKind kind, UUID targetUuid) {
      LivingEntity source = viewer;
      List<MitamaFaithSkill.MitamaOfferingEntry> entries = new ArrayList<>();
      if (kind == MitamaFaithSkill.MitamaMenuKind.CREATE) {
         for (Entry<ResourceKey<ManasSkill>, ManasSkill> entry : SkillAPI.getSkillRegistry().entrySet()) {
            ManasSkill skill = entry.getValue();
            if (skill instanceof Skill tensuraSkill) {
               SkillType type = tensuraSkill.getType();
               if (type == SkillType.COMMON
                  || type == SkillType.EXTRA
                  || type == SkillType.INTRINSIC
                  || type == SkillType.UNIQUE
                  || type == SkillType.RESISTANCE) {
                  ResourceLocation id = skill.getRegistryName();
                  entries.add(new MitamaFaithSkill.MitamaOfferingEntry(id, skill.getName().getString(), type.name(), 0.0, creationCost(type)));
               }
            }
         }
      } else {
         if (kind == MitamaFaithSkill.MitamaMenuKind.DONATE) {
            ServerPlayer onlineGod = viewer.server.getPlayerList().getPlayer(targetUuid);
            if (onlineGod == null) {
               LivingEntity mobOrTarget = findLivingByUuid(viewer, targetUuid);
               if (mobOrTarget != null) {
                  source = mobOrTarget;
               }
            }
         }

         for (ManasSkillInstance skill : SkillAPI.getSkillsFrom(source).getLearnedSkills()) {
            ResourceLocation id = skill.getSkillId();
            if (!id.equals(((MitamaFaithSkill)AllSkills.MITAMA_FAITH.get()).getRegistryName())) {
               String type = "OTHER";
               if (skill.getSkill() instanceof Skill tensuraSkill) {
                  if (!(source instanceof ServerPlayer) && tensuraSkill.getType() == SkillType.RESISTANCE) {
                     continue;
                  }

                  type = tensuraSkill.getType().name();
               }

               entries.add(new MitamaFaithSkill.MitamaOfferingEntry(id, skill.getSkill().getName().getString(), type, skill.getMastery(), skillValue(skill)));
            }
         }
      }

      entries.sort(Comparator.comparing(MitamaFaithSkill.MitamaOfferingEntry::type).thenComparing(entry -> entry.id().toString()));
      return entries;
   }

   public static boolean performMenuAction(
      ServerPlayer actor,
      MitamaFaithSkill.MitamaMenuKind kind,
      UUID targetUuid,
      MitamaFaithSkill.MitamaOfferingStat stat,
      double amount,
      ResourceLocation skillId
   ) {
      if (kind == MitamaFaithSkill.MitamaMenuKind.DONATE) {
         ServerPlayer god = MitamaFaithHandler.findGodForFollower(actor);
         if (god == null || !god.getUUID().equals(targetUuid)) {
            Optional<ManasSkillInstance> optionalActorMitama = SkillAPI.getSkillsFrom(actor).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
            if (optionalActorMitama.isPresent() && isFollower(optionalActorMitama.get(), targetUuid)) {
               LivingEntity donor = findLivingByUuid(actor, targetUuid);
               if (donor == null) {
                  actor.sendSystemMessage(Component.literal("That worshipper is offline or too far away.").withStyle(ChatFormatting.RED));
                  return false;
               } else {
                  return skillId != null ? donateSkillFromLiving(donor, actor, skillId) : donateStatFromLiving(donor, actor, stat, amount);
               }
            } else {
               actor.sendSystemMessage(Component.literal("No valid Mitama offering target found.").withStyle(ChatFormatting.RED));
               return false;
            }
         } else {
            return skillId != null ? donateSkill(actor, god, skillId) : donateStat(actor, god, stat, amount);
         }
      } else if (kind == MitamaFaithSkill.MitamaMenuKind.GIFT) {
         LivingEntity target = findLivingByUuid(actor, targetUuid);
         if (target == null) {
            actor.sendSystemMessage(Component.literal("That supporter is offline or too far away.").withStyle(ChatFormatting.RED));
            return false;
         } else {
            return skillId != null ? giveSkill(actor, target, skillId) : giveStat(actor, target, stat, amount);
         }
      } else {
         return kind == MitamaFaithSkill.MitamaMenuKind.CREATE ? createSkill(actor, skillId) : false;
      }
   }

   public static boolean donateSkill(ServerPlayer donor, ServerPlayer god, ResourceLocation skillId) {
      return donateSkillFromLiving(donor, god, skillId);
   }

   public static boolean donateSkillFromLiving(LivingEntity donor, ServerPlayer god, ResourceLocation skillId) {
      Optional<ManasSkillInstance> optionalGodSkill = SkillAPI.getSkillsFrom(god).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
      if (optionalGodSkill.isEmpty()) {
         return false;
      }

      ManasSkillInstance godSkill = optionalGodSkill.get();
      if (!isFollower(godSkill, donor.getUUID())) {
         if (donor instanceof ServerPlayer donorPlayer) {
            donorPlayer.sendSystemMessage(Component.literal("You are not this god's supporter.").withStyle(ChatFormatting.RED));
         } else {
            god.sendSystemMessage(Component.literal("That mob is not your worshipper.").withStyle(ChatFormatting.RED));
         }

         return false;
      } else if (skillId.equals(((MitamaFaithSkill)AllSkills.MITAMA_FAITH.get()).getRegistryName())) {
         if (donor instanceof ServerPlayer donorPlayer) {
            donorPlayer.sendSystemMessage(Component.literal("You cannot donate Mitama Faith itself.").withStyle(ChatFormatting.RED));
         }

         return false;
      } else {
         Skills donorSkills = SkillAPI.getSkillsFrom(donor);
         Optional<ManasSkillInstance> optionalDonorSkill = donorSkills.getSkill(skillId);
         if (optionalDonorSkill.isEmpty()) {
            if (donor instanceof ServerPlayer donorPlayer) {
               donorPlayer.sendSystemMessage(Component.literal("You do not have that skill.").withStyle(ChatFormatting.RED));
            } else {
               god.sendSystemMessage(Component.literal("That mob does not have that skill.").withStyle(ChatFormatting.RED));
            }

            return false;
         } else {
            ManasSkillInstance donated = optionalDonorSkill.get();
            if (!(donated.getMastery() < 0.0) && !donated.isTemporarySkill()) {
               ManasSkillInstance copy = ManasSkillInstance.fromNBT(donated.toNBT());
               copy.setRemoveTime(-1);
               double points = skillValue(donated);
               boolean learned = SkillHelper.learnSkill(
                  god, copy, -1, Component.literal("Faith offering: ").append(donated.getChatDisplayName(false)).withStyle(ChatFormatting.GOLD)
               );
               if (!learned) {
                  if (donor instanceof ServerPlayer donorPlayer) {
                     donorPlayer.sendSystemMessage(Component.literal("The god could not receive that skill.").withStyle(ChatFormatting.RED));
                  } else {
                     god.sendSystemMessage(Component.literal("You could not absorb that mob skill.").withStyle(ChatFormatting.RED));
                  }

                  return false;
               } else {
                  donorSkills.forgetSkill(donated);
                  donorSkills.markDirty();
                  if (donor instanceof ServerPlayer donorPlayer) {
                     addWorship(godSkill, points);
                     donorPlayer.sendSystemMessage(
                        Component.literal("You offered ")
                           .append(donated.getChatDisplayName(false))
                           .append(Component.literal(" for +" + format(points) + " Worship."))
                           .withStyle(ChatFormatting.GOLD)
                     );
                     god.sendSystemMessage(
                        Component.literal(donor.getName().getString() + " offered ")
                           .append(donated.getChatDisplayName(false))
                           .append(Component.literal(" for +" + format(points) + " Worship."))
                           .withStyle(ChatFormatting.GOLD)
                     );
                  } else {
                     god.sendSystemMessage(
                        Component.literal("You seized ")
                           .append(donated.getChatDisplayName(false))
                           .append(Component.literal(" from " + donor.getName().getString() + "."))
                           .withStyle(ChatFormatting.GOLD)
                     );
                  }

                  return true;
               }
            } else {
               if (donor instanceof ServerPlayer donorPlayer) {
                  donorPlayer.sendSystemMessage(Component.literal("That skill is not permanent enough to offer.").withStyle(ChatFormatting.RED));
               } else {
                  god.sendSystemMessage(Component.literal("That mob skill is not stable enough to seize.").withStyle(ChatFormatting.RED));
               }

               return false;
            }
         }
      }
   }

   public static boolean donateStat(ServerPlayer donor, ServerPlayer god, MitamaFaithSkill.MitamaOfferingStat stat, double amount) {
      return donateStatFromLiving(donor, god, stat, amount);
   }

   public static boolean donateStatFromLiving(LivingEntity donor, ServerPlayer god, MitamaFaithSkill.MitamaOfferingStat stat, double amount) {
      Optional<ManasSkillInstance> optionalGodSkill = SkillAPI.getSkillsFrom(god).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
      if (!optionalGodSkill.isEmpty() && !(amount <= 0.0)) {
         ManasSkillInstance godSkill = optionalGodSkill.get();
         if (!isFollower(godSkill, donor.getUUID())) {
            if (donor instanceof ServerPlayer donorPlayer) {
               donorPlayer.sendSystemMessage(Component.literal("You are not this god's supporter.").withStyle(ChatFormatting.RED));
            } else {
               god.sendSystemMessage(Component.literal("That mob is not your worshipper.").withStyle(ChatFormatting.RED));
            }

            return false;
         } else {
            double paid = transferStat(donor, god, stat, amount);
            if (paid <= 0.0) {
               if (donor instanceof ServerPlayer donorPlayer) {
                  donorPlayer.sendSystemMessage(Component.literal("You do not have enough " + stat.display() + ".").withStyle(ChatFormatting.RED));
               } else {
                  god.sendSystemMessage(Component.literal("That mob does not have enough " + stat.display() + ".").withStyle(ChatFormatting.RED));
               }

               return false;
            } else {
               double points = paid * stat.worshipMultiplier();
               if (donor instanceof ServerPlayer donorPlayer) {
                  addWorship(godSkill, points);
                  donorPlayer.sendSystemMessage(
                     Component.literal("You donated " + format(paid) + " " + stat.display() + " for +" + format(points) + " Worship.")
                        .withStyle(ChatFormatting.GOLD)
                  );
                  god.sendSystemMessage(
                     Component.literal(donor.getName().getString() + " donated " + format(paid) + " " + stat.display() + ".").withStyle(ChatFormatting.GOLD)
                  );
               } else {
                  god.sendSystemMessage(
                     Component.literal("You extracted " + format(paid) + " " + stat.display() + " from " + donor.getName().getString() + ".")
                        .withStyle(ChatFormatting.GOLD)
                  );
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public static boolean giveStat(ServerPlayer god, LivingEntity target, MitamaFaithSkill.MitamaOfferingStat stat, double amount) {
      Optional<ManasSkillInstance> optionalGodSkill = SkillAPI.getSkillsFrom(god).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
      if (!optionalGodSkill.isEmpty() && !(amount <= 0.0)) {
         ManasSkillInstance godSkill = optionalGodSkill.get();
         if (!isFollower(godSkill, target.getUUID())) {
            god.sendSystemMessage(Component.literal("That target is not your supporter.").withStyle(ChatFormatting.RED));
            return false;
         }

         double paid = transferStat(god, target, stat, amount);
         if (paid <= 0.0) {
            god.sendSystemMessage(Component.literal("You do not have enough " + stat.display() + ".").withStyle(ChatFormatting.RED));
            return false;
         }

         god.sendSystemMessage(
            Component.literal("You bestowed " + format(paid) + " " + stat.display() + " on " + target.getName().getString() + ".")
               .withStyle(ChatFormatting.GREEN)
         );
         if (target instanceof ServerPlayer targetPlayer) {
            targetPlayer.sendSystemMessage(
               Component.literal(god.getName().getString() + " bestowed " + format(paid) + " " + stat.display() + " on you.").withStyle(ChatFormatting.GREEN)
            );
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean giveSkill(ServerPlayer god, LivingEntity target, ResourceLocation skillId) {
      Optional<ManasSkillInstance> optionalGodSkill = SkillAPI.getSkillsFrom(god).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
      if (optionalGodSkill.isEmpty()) {
         return false;
      }

      ManasSkillInstance godSkill = optionalGodSkill.get();
      if (!isFollower(godSkill, target.getUUID())) {
         god.sendSystemMessage(Component.literal("That target is not your supporter.").withStyle(ChatFormatting.RED));
         return false;
      }

      if (skillId.equals(((MitamaFaithSkill)AllSkills.MITAMA_FAITH.get()).getRegistryName())) {
         god.sendSystemMessage(Component.literal("You cannot bestow Mitama Faith itself.").withStyle(ChatFormatting.RED));
         return false;
      }

      Optional<ManasSkillInstance> optionalSource = SkillAPI.getSkillsFrom(god).getSkill(skillId);
      if (optionalSource.isEmpty()) {
         god.sendSystemMessage(Component.literal("You do not have that skill.").withStyle(ChatFormatting.RED));
         return false;
      }

      ManasSkillInstance source = optionalSource.get();
      if (source.getMastery() < 0.0) {
         god.sendSystemMessage(Component.literal("That skill is not stable enough to bestow.").withStyle(ChatFormatting.RED));
         return false;
      }

      ManasSkillInstance copy = ManasSkillInstance.fromNBT(source.toNBT());
      copy.setMastery(Math.max(0.0, copy.getMastery() * 0.5));
      copy.setRemoveTime(-1);
      boolean learned = SkillHelper.learnSkill(
         target, copy, -1, Component.literal("Bestowed by faith: ").append(source.getChatDisplayName(false)).withStyle(ChatFormatting.GREEN)
      );
      if (!learned) {
         god.sendSystemMessage(Component.literal("The supporter could not receive that skill.").withStyle(ChatFormatting.RED));
         return false;
      }

      Skills godSkills = SkillAPI.getSkillsFrom(god);
      godSkills.forgetSkill(source);
      godSkills.markDirty();
      god.sendSystemMessage(
         Component.literal("You bestowed ")
            .append(source.getChatDisplayName(false))
            .append(Component.literal(" on " + target.getName().getString() + " and lost it."))
            .withStyle(ChatFormatting.GREEN)
      );
      if (target instanceof ServerPlayer targetPlayer) {
         targetPlayer.sendSystemMessage(
            Component.literal("You received ")
               .append(source.getChatDisplayName(false))
               .append(Component.literal(" from your god."))
               .withStyle(ChatFormatting.GREEN)
         );
      }

      return true;
   }

   public static boolean createSkill(ServerPlayer god, ResourceLocation skillId) {
      Optional<ManasSkillInstance> optionalGodSkill = SkillAPI.getSkillsFrom(god).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
      if (!optionalGodSkill.isEmpty() && skillId != null) {
         ManasSkillInstance mitama = optionalGodSkill.get();
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
         if (skill instanceof Skill tensuraSkill) {
            SkillType type = tensuraSkill.getType();
            if (type != SkillType.COMMON && type != SkillType.EXTRA && type != SkillType.INTRINSIC && type != SkillType.UNIQUE && type != SkillType.RESISTANCE) {
               god.sendSystemMessage(Component.literal("Mitama can only create up to Unique-class skills.").withStyle(ChatFormatting.RED));
               return false;
            } else {
               double cost = creationCost(type);
               if (getWorship(mitama) < cost) {
                  god.sendSystemMessage(Component.literal("Not enough Worship. Need " + format(cost) + ".").withStyle(ChatFormatting.RED));
                  return false;
               } else if (SkillAPI.getSkillsFrom(god).getSkill(skill).isPresent()) {
                  god.sendSystemMessage(Component.literal("You already have that skill.").withStyle(ChatFormatting.RED));
                  return false;
               } else {
                  boolean learned = SkillHelper.learnSkill(god, skill);
                  if (!learned) {
                     god.sendSystemMessage(Component.literal("Skill creation failed.").withStyle(ChatFormatting.RED));
                     return false;
                  } else {
                     subtractWorship(mitama, cost);
                     mitama.addMasteryPoint(god);
                     god.sendSystemMessage(
                        Component.literal("Faith created ")
                           .append(skill.getName())
                           .append(Component.literal(" for " + format(cost) + " Worship."))
                           .withStyle(ChatFormatting.GOLD)
                     );
                     return true;
                  }
               }
            }
         } else {
            god.sendSystemMessage(Component.literal("That skill cannot be created.").withStyle(ChatFormatting.RED));
            return false;
         }
      } else {
         return false;
      }
   }

   public static double skillValue(ManasSkillInstance instance) {
      double base = 5000.0;
      if (instance.getSkill() instanceof Skill skill) {
         base = switch (skill.getType()) {
            case COMMON -> cfg().commonSkillWorship.get();
            case EXTRA -> cfg().extraSkillWorship.get();
            case INTRINSIC -> cfg().intrinsicSkillWorship.get();
            case RESISTANCE -> cfg().resistanceSkillWorship.get();
            case UNIQUE -> cfg().uniqueSkillWorship.get();
            case ULTIMATE -> cfg().ultimateSkillWorship.get();
            default -> 5000.0;
         };
      }

      return base
         + Math.max(0.0, instance.getMastery())
            * Math.max((Double)cfg().skillMasteryMinimumBonus.get(), base / Math.max(1.0, (Double)cfg().skillMasteryDivisor.get()));
   }

   public static double creationCost(SkillType type) {
      return switch (type) {
         case COMMON -> cfg().createCommonCost.get();
         case EXTRA -> cfg().createExtraCost.get();
         case INTRINSIC -> cfg().createIntrinsicCost.get();
         case RESISTANCE -> cfg().createResistanceCost.get();
         case UNIQUE -> cfg().createUniqueCost.get();
         default -> Double.MAX_VALUE;
      };
   }

   public static String format(double value) {
      if (value >= 1.0E9) {
         return String.format("%.2fB", value / 1.0E9);
      } else if (value >= 1000000.0) {
         return String.format("%.2fM", value / 1000000.0);
      } else {
         return value >= 1000.0 ? String.format("%.2fK", value / 1000.0) : String.format("%.0f", value);
      }
   }

   private void recruit(ManasSkillInstance instance, LivingEntity entity) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, 0)) {
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg().recruitRange.get(), false);
         if (target == null || target == entity || !target.isAlive()) {
            this.fail(entity);
         } else if (target instanceof ServerPlayer player) {
            MitamaFaithHandler.sendInvite(entity, player);
            instance.setCoolDown((Integer)cfg().playerInviteCooldownTicks.get(), 0);
            entity.swing(InteractionHand.MAIN_HAND, true);
         } else if (!(target instanceof Mob)) {
            this.fail(entity);
         } else if (isFollower(instance, target.getUUID())) {
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.literal(target.getName().getString() + " is already your worshipper.").withStyle(ChatFormatting.YELLOW), true
               );
            }

            entity.swing(InteractionHand.MAIN_HAND, true);
         } else {
            double targetEp = EnergyHelper.getMaxEP(target);
            double userEp = Math.max(1.0, EnergyHelper.getMaxEP(entity));
            boolean lowHp = target.getHealth() <= target.getMaxHealth() * ((Double)cfg().mobHpThreshold.get()).floatValue();
            boolean crushedEp = targetEp <= userEp * (Double)cfg().mobEpThresholdRatio.get();
            if (!lowHp && !crushedEp) {
               if (entity instanceof Player player) {
                  player.displayClientMessage(Component.literal("The target must be below 20% HP or below 1% of your EP.").withStyle(ChatFormatting.RED), true);
               }

               this.fail(entity);
            } else if (target.getType().getCategory().isFriendly() && targetEp > userEp * (Double)cfg().mobEpThresholdRatio.get() && !lowHp) {
               this.fail(entity);
            } else {
               setOwnedBy(entity, target);
               double points = Math.max(1.0, targetEp / Math.max(1.0, (Double)cfg().mobWorshipEpDivisor.get()));
               addFollower(instance, target.getUUID(), target.getName().getString(), false, points);
               applyFaithAttributes(instance, entity);
               instance.addMasteryPoint(entity);
               instance.setCoolDown((Integer)cfg().recruitSuccessCooldownTicks.get(), 0);
               entity.swing(InteractionHand.MAIN_HAND, true);
               entity.level()
                  .playSound(
                     null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.4F
                  );
               if (entity instanceof Player player) {
                  player.displayClientMessage(Component.literal("Follower gained: +" + format(points) + " Worship").withStyle(ChatFormatting.GOLD), true);
               }

               if (target.level() instanceof ServerLevel serverLevel) {
                  serverLevel.sendParticles(
                     ParticleTypes.HEART, target.getX(), target.getY() + target.getBbHeight() + 0.5, target.getZ(), 12, 0.5, 0.5, 0.5, 0.02
                  );
               }
            }
         }
      }
   }

   private void transfer(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(player, (Double)cfg().transferRange.get(), false);
         if (target == null) {
            MitamaFaithHandler.openOwnCreationOrStatus(player);
         } else if (!isFollower(instance, target.getUUID())) {
            this.fail(player);
         } else if (target instanceof ServerPlayer supporter) {
            if (player.isShiftKeyDown()) {
               MitamaFaithHandler.openOfferingMenu(player, MitamaFaithSkill.MitamaMenuKind.GIFT, supporter.getUUID(), supporter.getName().getString());
            } else {
               MitamaFaithHandler.sendOfferingMenuMessage(player, supporter);
               player.displayClientMessage(Component.literal("Offering menu sent to " + supporter.getName().getString()).withStyle(ChatFormatting.GOLD), true);
            }
         } else {
            if (player.isShiftKeyDown()) {
               MitamaFaithHandler.openOfferingMenu(player, MitamaFaithSkill.MitamaMenuKind.GIFT, target.getUUID(), target.getName().getString());
            } else {
               MitamaFaithHandler.openOfferingMenu(player, MitamaFaithSkill.MitamaMenuKind.DONATE, target.getUUID(), target.getName().getString());
            }
         }
      } else {
         this.fail(entity);
      }
   }

   private void openCreation(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         MitamaFaithHandler.openOfferingMenu(player, MitamaFaithSkill.MitamaMenuKind.CREATE, player.getUUID(), player.getName().getString());
      } else {
         this.fail(entity);
      }
   }

   private void castBeams(ManasSkillInstance instance, LivingEntity entity) {
      MitamaDivineBeamsHandler.cast(instance, entity);
   }

   private void castEdict(ManasSkillInstance instance, LivingEntity entity) {
      MitamaAtomicEdictHandler.cast(instance, entity);
   }

   private static void healFollower(LivingEntity user, LivingEntity target, double worship) {
      target.heal(
         (float)Math.min(Float.MAX_VALUE, (Double)cfg().followerHealBase.get() + worship / Math.max(1.0, (Double)cfg().followerHealWorshipDivisor.get()))
      );
      IExistence existence = TensuraStorages.getExistenceFrom(target);
      existence.setMagicule(
         Math.min(
            EnergyHelper.getMaxMagicule(target),
            existence.getMagicule() + (Double)cfg().followerEnergyBase.get() + worship / Math.max(1.0, (Double)cfg().followerEnergyWorshipDivisor.get())
         )
      );
      existence.setAura(
         Math.min(
            EnergyHelper.getMaxAura(target),
            existence.getAura() + (Double)cfg().followerEnergyBase.get() + worship / Math.max(1.0, (Double)cfg().followerEnergyWorshipDivisor.get())
         )
      );
      existence.markDirty();
   }

   private static void applyFaithAttributes(ManasSkillInstance instance, LivingEntity entity) {
      double worship = getWorship(instance);
      AttributeHelper.addPermanentAttribute(entity, Attributes.MAX_HEALTH, HP_MODIFIER, worship * (Double)cfg().healthPerWorship.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         entity, Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER, worship * (Double)cfg().attackDamagePerWorship.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.MAX_MAGICULE, MAGICULE_MODIFIER, worship * (Double)cfg().maxMagiculePerWorship.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.MAX_AURA, AURA_MODIFIER, worship * (Double)cfg().maxAuraPerWorship.get(), Operation.ADD_VALUE
      );
      if (entity.getHealth() > entity.getMaxHealth()) {
         entity.setHealth(entity.getMaxHealth());
      }
   }

   private static void applyNullAttributes(LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.RESISTANCE_DEGRADATION, NULL_RESIST, (Double)cfg().nullResistanceDegradation.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LAW_DEGRADATION, NULL_LAW, (Double)cfg().nullLawDegradation.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.DODGE_NEGATE_CHANCE, NULL_DODGE, (Double)cfg().nullDodgeNegateChance.get(), Operation.ADD_VALUE
      );
   }

   private static void removeNullAttributes(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, NULL_RESIST);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LAW_DEGRADATION, NULL_LAW);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, NULL_DODGE);
   }

   public static LivingEntity findLivingByUuid(ServerPlayer searcher, UUID uuid) {
      if (uuid == null) {
         return null;
      }

      ServerPlayer online = searcher.server.getPlayerList().getPlayer(uuid);
      if (online != null) {
         return online;
      }

      for (ServerLevel level : searcher.server.getAllLevels()) {
         if (level.getEntity(uuid) instanceof LivingEntity living && living.isAlive()) {
            return living;
         }
      }

      return null;
   }

   private static double transferStat(LivingEntity from, LivingEntity to, MitamaFaithSkill.MitamaOfferingStat stat, double requested) {
      if (Double.isFinite(requested) && !(requested <= 0.0)) {
         double amount = Math.floor(requested);
         if (amount <= 0.0) {
            return 0.0;
         }

         IExistence fromEx = TensuraStorages.getExistenceFrom(from);
         IExistence toEx = TensuraStorages.getExistenceFrom(to);
         if (stat == MitamaFaithSkill.MitamaOfferingStat.MAGICULE) {
            double paid = transferBaseAttribute(from, to, TensuraAttributes.MAX_MAGICULE, amount, 2.0);
            if (paid <= 0.0) {
               return 0.0;
            }

            clampEnergy(from);
            clampEnergy(to);
            toEx.setMagicule(Math.min(EnergyHelper.getMaxMagicule(to), toEx.getMagicule() + paid));
            fromEx.markDirty();
            toEx.markDirty();
            return paid;
         } else if (stat == MitamaFaithSkill.MitamaOfferingStat.AURA) {
            double paid = transferBaseAttribute(from, to, TensuraAttributes.MAX_AURA, amount, 2.0);
            if (paid <= 0.0) {
               return 0.0;
            }

            clampEnergy(from);
            clampEnergy(to);
            toEx.setAura(Math.min(EnergyHelper.getMaxAura(to), toEx.getAura() + paid));
            fromEx.markDirty();
            toEx.markDirty();
            return paid;
         } else if (stat == MitamaFaithSkill.MitamaOfferingStat.EP) {
            double paid = transferBaseEP(from, to, amount, 2.0);
            if (paid <= 0.0) {
               return 0.0;
            }

            clampEnergy(from);
            clampEnergy(to);
            fromEx.markDirty();
            toEx.markDirty();
            return paid;
         } else {
            if (stat != MitamaFaithSkill.MitamaOfferingStat.SOULS) {
               return 0.0;
            }

            if (amount > fromEx.getSoulPoints()) {
               return 0.0;
            }

            int paid = (int)Math.min(2.147483647E9, amount);
            if (paid <= 0) {
               return 0.0;
            }

            fromEx.setSoulPoints(fromEx.getSoulPoints() - paid);
            toEx.setSoulPoints((int)Math.min(2.147483647E9, (double)toEx.getSoulPoints() + paid));
            fromEx.markDirty();
            toEx.markDirty();
            return paid;
         }
      } else {
         return 0.0;
      }
   }

   private static double transferBaseAttribute(LivingEntity from, LivingEntity to, Holder<Attribute> attribute, double requested, double minimumLeft) {
      if (!(requested <= 0.0) && Double.isFinite(requested)) {
         AttributeInstance fromAttribute = from.getAttribute(attribute);
         AttributeInstance toAttribute = to.getAttribute(attribute);
         if (fromAttribute != null && toAttribute != null) {
            double fromBase = getBaseTransferValue(from, attribute);
            double toBase = getBaseTransferValue(to, attribute);
            double available = Math.max(0.0, fromBase - minimumLeft);
            if (requested > available) {
               return 0.0;
            }

            setBaseTransferValue(from, attribute, Math.max(minimumLeft, fromBase - requested));
            setBaseTransferValue(to, attribute, toBase + requested);
            return requested;
         } else {
            return 0.0;
         }
      } else {
         return 0.0;
      }
   }

   private static double transferBaseEP(LivingEntity from, LivingEntity to, double requested, double minimumTotalLeft) {
      if (!(requested <= 0.0) && Double.isFinite(requested)) {
         AttributeInstance fromMagicule = from.getAttribute(TensuraAttributes.MAX_MAGICULE);
         AttributeInstance fromAura = from.getAttribute(TensuraAttributes.MAX_AURA);
         AttributeInstance toMagicule = to.getAttribute(TensuraAttributes.MAX_MAGICULE);
         AttributeInstance toAura = to.getAttribute(TensuraAttributes.MAX_AURA);
         if (fromMagicule != null && fromAura != null && toMagicule != null && toAura != null) {
            double magicBase = EnergyHelper.getBaseMaxMagicule(from);
            double auraBase = EnergyHelper.getBaseMaxAura(from);
            double total = magicBase + auraBase;
            double available = Math.max(0.0, total - minimumTotalLeft);
            if (!(requested > available) && !(total <= minimumTotalLeft)) {
               double magicPaid = requested * (magicBase / total);
               double auraPaid = requested - magicPaid;
               EnergyHelper.setMaxMagicule(from, Math.max(1.0, magicBase - magicPaid));
               EnergyHelper.setMaxAura(from, Math.max(1.0, auraBase - auraPaid));
               EnergyHelper.setMaxMagicule(to, EnergyHelper.getBaseMaxMagicule(to) + requested * 0.5);
               EnergyHelper.setMaxAura(to, EnergyHelper.getBaseMaxAura(to) + requested * 0.5);
               return requested;
            } else {
               return 0.0;
            }
         } else {
            return 0.0;
         }
      } else {
         return 0.0;
      }
   }

   private static double getBaseTransferValue(LivingEntity entity, Holder<Attribute> attribute) {
      if (attribute.equals(TensuraAttributes.MAX_MAGICULE)) {
         return EnergyHelper.getBaseMaxMagicule(entity);
      }

      if (attribute.equals(TensuraAttributes.MAX_AURA)) {
         return EnergyHelper.getBaseMaxAura(entity);
      }

      AttributeInstance instance = entity.getAttribute(attribute);
      return instance == null ? 0.0 : instance.getBaseValue();
   }

   private static void setBaseTransferValue(LivingEntity entity, Holder<Attribute> attribute, double value) {
      if (attribute.equals(TensuraAttributes.MAX_MAGICULE)) {
         EnergyHelper.setMaxMagicule(entity, value);
      } else if (attribute.equals(TensuraAttributes.MAX_AURA)) {
         EnergyHelper.setMaxAura(entity, value);
      } else {
         AttributeInstance instance = entity.getAttribute(attribute);
         if (instance != null) {
            instance.setBaseValue(value);
         }
      }
   }

   private static void clampEnergy(LivingEntity entity) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      existence.setMagicule(Math.min(existence.getMagicule(), EnergyHelper.getMaxMagicule(entity)));
      existence.setAura(Math.min(existence.getAura(), EnergyHelper.getMaxAura(entity)));
      existence.markDirty();
   }

   private void cleanDeadFollowers(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      ListTag followers = tag.getList("MitamaFollowers", 10);
      ListTag kept = new ListTag();
      boolean changed = false;

      for (Tag raw : followers) {
         CompoundTag follower = (CompoundTag)raw;
         UUID uuid = follower.getUUID("UUID");
         boolean player = follower.getBoolean("Player");
         if (!player && findEntity(entity, uuid) == null) {
            changed = true;
         } else {
            kept.add(follower);
         }
      }

      if (changed) {
         tag.put("MitamaFollowers", kept);
         recalcWorship(instance);
         instance.markDirty();
      }
   }

   private static void recalcWorship(ManasSkillInstance instance) {
      double total = instance.getOrCreateTag().getDouble("MitamaWorshipBonus");

      for (Tag raw : instance.getOrCreateTag().getList("MitamaFollowers", 10)) {
         total += Math.max(0.0, ((CompoundTag)raw).getDouble("Points"));
      }

      instance.getOrCreateTag().putDouble("MitamaWorship", total);
   }

   private static CompoundTag getFollower(ManasSkillInstance instance, UUID uuid) {
      return getFollowerFromList(instance.getOrCreateTag().getList("MitamaFollowers", 10), uuid);
   }

   private static CompoundTag getFollowerFromList(ListTag followers, UUID uuid) {
      for (Tag raw : followers) {
         CompoundTag follower = (CompoundTag)raw;
         if (follower.hasUUID("UUID") && Objects.equals(follower.getUUID("UUID"), uuid)) {
            return follower;
         }
      }

      return null;
   }

   private static Entity findEntity(LivingEntity owner, UUID uuid) {
      if (uuid != null && !owner.level().isClientSide()) {
         for (Entity entity : owner.level().getEntities(owner, owner.getBoundingBox().inflate(128.0))) {
            if (Objects.equals(entity.getUUID(), uuid)) {
               return entity;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static void setOwnedBy(LivingEntity owner, LivingEntity target) {
      IExistence existence = TensuraStorages.getExistenceFrom(target);
      existence.setPermanentOwner(owner.getUUID());
      existence.markDirty();
      if (target instanceof TamableAnimal tamable && owner instanceof Player player) {
         tamable.tame(player);
      }

      if (target instanceof Mob mob) {
         mob.setTarget(null);
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

   private void fail(LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensura.ability.activation_failed").withStyle(ChatFormatting.RED), true);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   public enum MitamaMenuKind {
      DONATE,
      GIFT,
      CREATE;
   }

   public record MitamaOfferingEntry(ResourceLocation id, String name, String type, double mastery, double value) {
   }

   public enum MitamaOfferingStat {
      MAGICULE("Max MP", (Double)MitamaFaithSkill.cfg().magiculeWorshipMultiplier.get()),
      AURA("Max Aura", (Double)MitamaFaithSkill.cfg().auraWorshipMultiplier.get()),
      EP("EP", (Double)MitamaFaithSkill.cfg().epWorshipMultiplier.get()),
      SOULS("Souls", (Double)MitamaFaithSkill.cfg().soulWorshipMultiplier.get());

      private final String display;
      private final double worshipMultiplier;

      MitamaOfferingStat(String display, double worshipMultiplier) {
         this.display = display;
         this.worshipMultiplier = worshipMultiplier;
      }

      public String display() {
         return this.display;
      }

      public double worshipMultiplier() {
         return this.worshipMultiplier;
      }
   }
}
