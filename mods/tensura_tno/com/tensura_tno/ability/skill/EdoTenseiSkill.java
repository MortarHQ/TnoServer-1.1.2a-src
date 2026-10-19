package com.tensura_tno.ability.skill;

import com.mojang.datafixers.util.Pair;
import com.tensura_tno.network.EdoTenseiPackets;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.magic.summon.SummoningMagic;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class EdoTenseiSkill extends SummoningMagic<Mob> {
   private static final int CAST_TIME = 60;
   private static final int COOLDOWN_TICKS = 400;
   private static final int SUMMON_DURATION = 0;
   private static final String SELECTED_ENTITY_KEY = "SelectedEntity";
   private static final double NORMAL_COST = 30000.0;
   private static final double BOSS_COST = 150000.0;

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/skill/edo_tensei.png");
   }

   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withColor(9109504);
   }

   public int getMaxMastery() {
      return 100;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      CompoundTag tag = instance.getTag();
      if (tag != null && tag.contains("SelectedEntity")) {
         String id = tag.getString("SelectedEntity");
         if (EdoTenseiPackets.BOSS_ENTITIES.contains(id)) {
            return 150000.0;
         }
      }

      return 30000.0;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 5.0;
   }

   public int getDefaultCastTime() {
      return 60;
   }

   public int getSuccessCooldown(ManasSkillInstance instance, LivingEntity entity) {
      return 400;
   }

   public boolean isInstantCast(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return false;
   }

   public boolean canRemoveSummon(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return false;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.contains("SelectedEntity")) {
         if (entity instanceof ServerPlayer sp) {
            boolean mastered = instance.isMastered(entity);
            NetworkManager.sendToPlayer(
               sp, new EdoTenseiPackets.OpenScreenPayload(String.join(",", EdoTenseiPackets.getUnlockedEntityIds(sp, mastered)), mastered)
            );
         }
      } else {
         super.onPressed(instance, entity, keyNumber, mode);
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      return !instance.getOrCreateTag().contains("SelectedEntity") ? false : super.onHeld(instance, entity, heldTicks, mode);
   }

   public EntityType<? extends Mob> getSummonedType(ManasSkillInstance instance, LivingEntity entity, int mode) {
      CompoundTag tag = instance.getTag();
      if (tag != null && tag.contains("SelectedEntity")) {
         ResourceLocation rl = ResourceLocation.parse(tag.getString("SelectedEntity"));
         if (BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
            return (EntityType<? extends Mob>)BuiltInRegistries.ENTITY_TYPE.get(rl);
         }
      }

      return null;
   }

   public void addAdditionalSummonData(ManasSkillInstance instance, LivingEntity entity, Mob summon, int mode) {
      if (entity instanceof Player player) {
         if (summon instanceof ISubordinate sub) {
            sub.tame(player);
         } else if (summon instanceof TamableAnimal ta) {
            ta.setOwnerUUID(player.getUUID());
            ta.setTame(true, true);
         }
      }

      summon.skipDropExperience();
      ResourceLocation summonId = BuiltInRegistries.ENTITY_TYPE.getKey(summon.getType());
      if (summonId != null && "tensura:shizu".equals(summonId.toString())) {
         try {
            Method setTT = summon.getClass().getMethod("setTransformTick", int.class);
            setTT.invoke(summon, 100);
         } catch (Exception var10) {
         }

         summon.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
         summon.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
         summon.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);

         try {
            Method getSlotId = summon.getClass().getMethod("getSlotId", EquipmentSlot.class);
            Field invField = null;

            for (Class<?> cls = summon.getClass(); cls != null; cls = cls.getSuperclass()) {
               try {
                  invField = cls.getDeclaredField("inventory");
                  break;
               } catch (NoSuchFieldException var11) {
               }
            }

            if (invField != null) {
               invField.setAccessible(true);
               Object inv = invField.get(summon);
               Method setItem = inv.getClass().getMethod("setItem", int.class, ItemStack.class);
               setItem.invoke(inv, (Integer)getSlotId.invoke(summon, EquipmentSlot.HEAD), ItemStack.EMPTY);
               setItem.invoke(inv, (Integer)getSlotId.invoke(summon, EquipmentSlot.MAINHAND), ItemStack.EMPTY);
            }

            Method updateEquip = summon.getClass().getMethod("updateContainerEquipment");
            updateEquip.invoke(summon);
         } catch (Exception var12) {
         }
      }

      IExistence existence = TensuraStorages.getExistenceFrom(summon);
      existence.setSummonedSecond(0);
      existence.setSummoner(entity.getUUID());
      existence.setSummonedAbility(this, mode);
      existence.markDirty();
   }

   public void summonMagicCircle(ManasSkillInstance instance, LivingEntity entity, Vec3 pos, int heldTicks, int mode) {
      Pair<Double, Double> cost = Pair.of(this.getAuraCost(entity, instance, mode), this.getMagiculeCost(entity, instance, mode));
      MagicCircle.castMagicCircle(3.0F, 30, pos, MagicCircleVariant.DEMON, entity, instance.getOrCreateTag(), instance, mode, cost);
   }

   public void onPostSummon(ManasSkillInstance instance, LivingEntity entity, Mob summon, int mode) {
      super.onPostSummon(instance, entity, summon, mode);
      instance.getOrCreateTag().remove("SelectedEntity");
      instance.addMasteryPoint(entity, 25.0);
      instance.markDirty();
   }

   public void onSummonDeath(ManasSkillInstance instance, LivingEntity summon) {
      CompoundTag tag = instance.getTag();
      if (tag != null && tag.hasUUID("SummonUUID") && Objects.equals(summon.getUUID(), tag.getUUID("SummonUUID"))) {
         tag.remove("SummonUUID");
      }
   }

   public ParticleOptions getSummoningParticle(ManasSkillInstance instance, int mode) {
      return TensuraParticleUtils.getWhiteEffect();
   }

   public SoundEvent getSummoningSound(ManasSkillInstance instance, int mode) {
      return (SoundEvent)TensuraSoundEvents.CAST_DARK.get();
   }

   public SoundEvent getFailSound(ManasSkillInstance instance, int mode) {
      return (SoundEvent)TensuraSoundEvents.LEECH_LIZARD_DEATH.get();
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      CompoundTag tag = instance.getTag();
      if (tag != null && tag.contains("SelectedEntity")) {
         String entityId = tag.getString("SelectedEntity");
         ResourceLocation rl = ResourceLocation.parse(entityId);
         if (BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
            return ((EntityType)BuiltInRegistries.ENTITY_TYPE.get(rl)).getDescription();
         }
      }

      return Component.translatable("tensura_tno.edo_tensei.select");
   }
}
