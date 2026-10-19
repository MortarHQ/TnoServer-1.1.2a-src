package com.foxablazeultimate.ability.skill.azathoth;

import com.foxablazeultimate.config.AzathothConfig;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.ICloning;
import io.github.manasmods.tensura.ability.subclass.ISpatialMovement;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class AzathothSkill extends Skill implements ISpatialStorage, ISpatialMovement, ICloning {
   public static final int SOUL = 0;
   public static final int VOID = 1;
   public static final int STORAGE = 2;
   public static final int SPATIAL = 3;
   public static final int DRAGONS = 4;
   public static final int CORE = 5;

   public AzathothSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/azathoth.png");
   }

   public MutableComponent getColoredName() {
      return this.getName().withStyle(ChatFormatting.DARK_PURPLE);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   public boolean checkAcquiringRequirement(Player player, double ep) {
      return false;
   }

   public int getModes(ManasSkillInstance instance) {
      return 6;
   }

   public int nextMode(LivingEntity owner, ManasSkillInstance instance, int mode, boolean reverse) {
      return Math.floorMod(mode + (reverse ? -1 : 1), 6);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "azathoth." + switch (mode) {
         case 0 -> "soul_gluttony";
         case 1 -> "void_collapse";
         case 2 -> "imaginary_space";
         case 3 -> "spacetime_domination";
         case 4 -> "dragon_liberation";
         default -> "dragon_nucleation";
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity owner) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity owner, int mode) {
      return owner.isShiftKeyDown() && (mode == 0 || mode == 3 || mode == 4);
   }

   public boolean shouldShowCoolDown(ManasSkillInstance instance, LivingEntity owner, int mode) {
      return instance.onCoolDown(mode);
   }

   public double getMagiculeCost(LivingEntity owner, ManasSkillInstance instance, int mode) {
      AzathothConfig.Settings c = AzathothConfig.settings();

      return Math.max(0.0, switch (mode) {
         case 0 -> c.soulMagiculeCost;
         case 1 -> c.voidMagiculeCost;
         default -> 0.0;
         case 3 -> c.teleportMagiculeCost;
         case 4 -> c.dragonMagiculeCost;
      });
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity owner, int key, int mode) {
      if (!owner.level().isClientSide()) {
         switch (mode) {
            case 0:
               AzathothCombat.onSoulPressed(instance, owner);
               break;
            case 1:
               AzathothCombat.beginCharge(instance, owner);
               break;
            case 2:
               this.openSpatialStorage(owner, instance);
               break;
            case 3:
               AzathothSpatial.onSpatialPressed(instance, owner, key, mode);
               break;
            case 4:
               AzathothDragons.onDragonPressed(instance, owner, key, mode);
               break;
            case 5:
               if (owner instanceof ServerPlayer player) {
                  player.displayClientMessage(Component.translatable("foxablazeultimate.skill.azathoth.coming_soon"), false);
               }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity owner, int ticks, int mode) {
      if (!owner.level().isClientSide()) {
         if (mode == 0) {
            return AzathothCombat.holdSoul(this, instance, owner);
         } else {
            return mode == 1 ? AzathothCombat.holdCharge(instance, owner, ticks) : false;
         }
      } else {
         return mode == 0 || mode == 1;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity owner, int ticks, int key, int mode) {
      if (!owner.level().isClientSide()) {
         if (mode == 1) {
            AzathothCombat.storeCharge(instance, owner);
         }

         if (mode == 0) {
            AzathothCombat.beginSoul(instance);
         }
      }
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity owner, int key, int mode) {
      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> damage) {
      if (!instance.isToggled()) {
         return true;
      }

      AzathothConfig.Settings c = AzathothConfig.settings();
      double reduction = 0.0;
      if (TensuraDamageHelper.isPhysicalAttack(source)) {
         reduction = c.physicalDamageReduction;
      }

      if (TensuraDamageHelper.isNaturalEffects(source) || TensuraDamageHelper.isTensuraMagic(source)) {
         reduction = Math.max(reduction, c.elementalDamageReduction);
      }

      if (source.is(DamageTypeTags.IS_PROJECTILE)) {
         reduction = Math.max(reduction, c.projectileDamageReduction);
      }

      TensuraDamageSource ts = (TensuraDamageSource)source;
      ManasSkillInstance attacking = ts.tensura$getAbilityInstance();
      if (ts.tensura$getSkillType() == SkillType.ULTIMATE || attacking != null && attacking.getSkill() instanceof Skill s && s.getType() == SkillType.ULTIMATE) {
         reduction = Math.max(reduction, c.ultimateDamageReduction);
      }

      damage.set((Float)damage.get() * (float)(1.0 - Math.clamp(reduction, 0.0, 1.0)));
      return true;
   }

   public void onCloneTick(CloneEntity clone, LivingEntity owner) {
      if (!clone.level().isClientSide()) {
         SkillAPI.getSkillsFrom(owner).getSkill(this).ifPresentOrElse(i -> AzathothDragons.onCloneTick(clone, owner, i), clone::discard);
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity owner) {
      super.onLearnSkill(instance, owner);
      if (owner instanceof ServerPlayer player
         && !instance.isSubInstance()
         && instance.getMastery() >= 0.0
         && !instance.getOrCreateTag().getBoolean("AzathothWarpPoints")) {
         ITensuraPlayer data = TensuraStorages.getPlayerDataFrom(player);
         data.setMaxWarpPoints(data.getMaxWarpPoints() + 2);
         data.markDirty();
         instance.getOrCreateTag().putBoolean("AzathothWarpPoints", true);
         instance.markDirty();
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      AzathothCombat.clear(instance, owner);
      AzathothDragons.recallAll(instance, owner);
      return true;
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity owner) {
      AzathothCombat.clear(instance, owner);
      AzathothDragons.recallAll(instance, owner);
      if (owner instanceof ServerPlayer player) {
         player.closeContainer();
         this.dropAllItems(instance, player);
         if (instance.getOrCreateTag().getBoolean("AzathothWarpPoints")) {
            ITensuraPlayer data = TensuraStorages.getPlayerDataFrom(player);
            data.setMaxWarpPoints(Math.max(1, data.getMaxWarpPoints() - 2));
            data.removeWarpPointOverMax(2);
            data.markDirty();
            instance.getOrCreateTag().remove("AzathothWarpPoints");
         }
      }

      super.onForgetSkill(instance, owner);
   }

   public SpatialStorageContainer getSpatialStorage(ManasSkillInstance instance, Provider provider) {
      AzathothConfig.Settings c = AzathothConfig.settings();
      boolean mastered = instance.getMastery() >= instance.getMaxMastery();
      int size = Math.clamp(mastered ? c.storageMasteredPages : c.storagePages, 1, 100) * 27;
      ListTag saved = instance.getOrCreateTag().getList("SpatialStorage", 10);

      for (int i = 0; i < saved.size(); i++) {
         int slot = saved.getCompound(i).getInt("Slot");
         if (slot >= 0 && slot < 2700) {
            size = Math.max(size, (slot / 27 + 1) * 27);
         }
      }

      SpatialStorageContainer container = new SpatialStorageContainer(size, Math.clamp(c.storageMaxStackSize, 1, 999));
      container.fromTag(saved, provider);
      return container;
   }

   public int getSpatialMovementModeIndex() {
      return 3;
   }

   public boolean canWarp(ManasSkillInstance instance, LivingEntity owner) {
      return false;
   }

   public boolean canPortal(ManasSkillInstance instance, LivingEntity owner) {
      return AzathothSpatial.isPortalMode(instance);
   }

   public int getWarpChargeTick(ManasSkillInstance instance, LivingEntity owner) {
      return Math.max(0, AzathothConfig.settings().portalChargeTicks);
   }

   public int getWarpCooldown(ManasSkillInstance instance, LivingEntity owner) {
      return Math.max(0, AzathothConfig.settings().portalCooldownSeconds);
   }

   public double getPortalCost(double distance) {
      return Math.max(0.0, AzathothConfig.settings().portalMagiculeCostPerBlock) * distance;
   }
}
