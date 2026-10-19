package com.foxablazeultimate.ability.skill.yogsothoth;

import com.foxablazeultimate.config.YogSothothConfig;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.EntityHitResult;

public final class YogSothothSkill extends Skill implements ISpatialStorage {
   public static final ResourceLocation INFINITY_PRISON = id("tensura", "infinity_prison");
   public static final ResourceLocation ABSOLUTE_SEVERANCE = id("tensura", "absolute_severance");
   public static final ResourceLocation USURPER = id("tensura", "usurper");
   public static final ResourceLocation TIME_TRAVELER = id("tno_tensura_extras", "time_traveler");
   public static final double ACQUISITION_MAGICULE_COST = 5000000.0;
   public static final double TIME_STOP_MAGICULE_COST_PER_SECOND = 100000.0;
   private static final int MODE_INFINITY_IMPRISON = 0;
   private static final int MODE_INFINITY_STORAGE = 1;
   private static final int MODE_SEVERANCE_COAT = 2;
   private static final int MODE_SEVERANCE_PROJECTILE = 3;
   private static final int MODE_USURPER_ROB = 4;
   private static final int MODE_USURPER_COPY = 5;
   private static final int MODE_USURPER_TAKEOVER = 6;
   private static final int MODE_TIME_TRAVEL = 7;
   public static final int MODE_TIME_STOP = 8;
   private static final int MODE_COUNT = 9;
   private static final List<ResourceLocation> PREREQUISITES = List.of(INFINITY_PRISON, ABSOLUTE_SEVERANCE, USURPER, TIME_TRAVELER);
   private static final List<YogSothothSkill.Route> ROUTES = List.of(
      new YogSothothSkill.Route(INFINITY_PRISON, 0),
      new YogSothothSkill.Route(INFINITY_PRISON, 1),
      new YogSothothSkill.Route(ABSOLUTE_SEVERANCE, 0),
      new YogSothothSkill.Route(ABSOLUTE_SEVERANCE, 1),
      new YogSothothSkill.Route(USURPER, 0),
      new YogSothothSkill.Route(USURPER, 1),
      new YogSothothSkill.Route(USURPER, 2),
      new YogSothothSkill.Route(TIME_TRAVELER, 0)
   );

   private static YogSothothConfig.YogSothothSettings settings() {
      return YogSothothConfig.settings();
   }

   public static double acquisitionMagiculeCost() {
      double configured = settings().acquisitionMaximumMagiculeCost;
      return Double.isFinite(configured) ? Math.max(0.0, configured) : 5000000.0;
   }

   public static double timeStopMagiculeCostPerSecond() {
      double configured = settings().timeStopMagiculeCostPerSecond;
      return Double.isFinite(configured) ? Math.max(0.0, configured) : 100000.0;
   }

   public static int timeStopDrainIntervalTicks() {
      return Math.max(1, settings().timeStopDrainIntervalTicks);
   }

   public YogSothothSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return id("foxablazeultimate", "textures/skill/yog_sothoth.png");
   }

   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withStyle(ChatFormatting.RED);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return Double.POSITIVE_INFINITY;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return false;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0 && !instance.isTemporarySkill();
   }

   public int getModes(ManasSkillInstance instance) {
      return 9;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int safe = sanitizeMode(mode);
      if (reverse) {
         return safe == 0 ? 8 : safe - 1;
      } else {
         return safe == 8 ? 0 : safe + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      int safe = sanitizeMode(mode);
      if (safe == 7) {
         return "yog_sothoth.time_travel";
      }

      if (safe == 8) {
         return "yog_sothoth.time_stop";
      }

      YogSothothSkill.Route route = route(safe);
      Skill target = route == null ? null : route.skill();
      return target == null ? super.getModeId(instance, mode) : target.getModeId(YogSothothDelegateHelper.child(instance, target), route.childMode());
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      int safe = sanitizeMode(mode);
      if (safe != 7 && safe != 8) {
         YogSothothSkill.Route route = route(safe);
         Skill target = route == null ? null : route.skill();
         return target == null ? super.getModeName(instance, safe) : target.getModeName(YogSothothDelegateHelper.child(instance, target), route.childMode());
      } else {
         return super.getModeName(instance, safe);
      }
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      YogSothothSkill.Route route = route(sanitizeMode(mode));
      Skill target = route == null ? null : route.skill();
      return target == null ? 0.0 : target.getMagiculeCost(entity, YogSothothDelegateHelper.child(instance, target), route.childMode());
   }

   public double getAuraCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      YogSothothSkill.Route route = route(sanitizeMode(mode));
      Skill target = route == null ? null : route.skill();
      return target == null ? 0.0 : target.getAuraCost(entity, YogSothothDelegateHelper.child(instance, target), route.childMode());
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      YogSothothSkill.Route route = route(sanitizeMode(mode));
      Skill target = route == null ? null : route.skill();
      if (target == null) {
         return false;
      }

      TensuraSkillInstance child = YogSothothDelegateHelper.child(instance, target);
      return target.canInteractSkill(child, entity) && target.canScroll(child, entity, route.childMode());
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      YogSothothSkill.Route route = route(sanitizeMode(mode));
      Skill target = route == null ? null : route.skill();
      return target != null && YogSothothDelegateHelper.canIgnoreCoolDown(instance, target, entity, route.childMode());
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      YogSothothSkill.Route route = route(sanitizeMode(mode));
      Skill target = route == null ? null : route.skill();
      return target != null && YogSothothDelegateHelper.shouldTriggerRelease(instance, target, entity, keyNumber, route.childMode());
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null && YogSothothDelegateHelper.canTick(instance, target, entity)) {
            return true;
         }
      }

      return false;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null) {
            YogSothothDelegateHelper.onTick(instance, target, entity);
         }
      }
   }

   public int getMaxHeldTime(ManasSkillInstance instance, LivingEntity entity) {
      return 72000;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      int safe = sanitizeMode(mode);
      if (safe == 8) {
         if (entity instanceof ServerPlayer player && YogTimeStopManager.toggle(player)) {
            instance.addMasteryPoint(entity);
         }
      } else if (safe == 1) {
         this.openSpatialStorage(entity, instance);
      } else {
         YogSothothSkill.Route route = route(safe);
         Skill target = route == null ? null : route.skill();
         if (target != null) {
            YogSothothDelegateHelper.onPressed(instance, target, entity, keyNumber, safe, route.childMode());
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      int safe = sanitizeMode(mode);
      YogSothothSkill.Route route = route(safe);
      Skill target = route == null ? null : route.skill();
      return target != null && YogSothothDelegateHelper.onHeld(instance, target, entity, heldTicks, safe, route.childMode());
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      int safe = sanitizeMode(mode);
      YogSothothSkill.Route route = route(safe);
      Skill target = route == null ? null : route.skill();
      if (target != null) {
         YogSothothDelegateHelper.onRelease(instance, target, entity, heldTicks, keyNumber, safe, route.childMode());
      }
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta, int mode) {
      int safe = sanitizeMode(mode);
      YogSothothSkill.Route route = route(safe);
      Skill target = route == null ? null : route.skill();
      if (target != null) {
         YogSothothDelegateHelper.onScroll(instance, target, entity, delta, safe, route.childMode());
      }
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null && !YogSothothDelegateHelper.onBeingDamaged(instance, target, entity, source, amount)) {
            return false;
         }
      }

      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null && !YogSothothDelegateHelper.onTakenDamage(instance, target, owner, source, amount)) {
            return false;
         }
      }

      return true;
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill childSkill = skill(id);
         if (childSkill != null && !YogSothothDelegateHelper.onTouchEntity(instance, childSkill, owner, target, source, amount)) {
            return false;
         }
      }

      return true;
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, Entity source, Changeable<MobEffectInstance> effect) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null && !YogSothothDelegateHelper.onEffectAdded(instance, target, entity, source, effect)) {
            return false;
         }
      }

      return true;
   }

   public boolean onBeingTargeted(ManasSkillInstance instance, Changeable<LivingEntity> target, LivingEntity owner) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill childSkill = skill(id);
         if (childSkill != null && !YogSothothDelegateHelper.onBeingTargeted(instance, childSkill, target, owner)) {
            return false;
         }
      }

      return true;
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill childSkill = skill(id);
         if (childSkill != null && !YogSothothDelegateHelper.onDamageEntity(instance, childSkill, owner, target, source, amount)) {
            return false;
         }
      }

      return true;
   }

   public void onProjectileHit(
      ManasSkillInstance instance,
      LivingEntity living,
      EntityHitResult hitResult,
      Projectile projectile,
      Changeable<ProjectileDeflection> deflection,
      Changeable<ProjectileHitResult> result
   ) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null) {
            YogSothothDelegateHelper.onProjectileHit(instance, target, living, hitResult, projectile, deflection, result);
         }
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      if (owner instanceof ServerPlayer player) {
         YogTimeStopManager.stopIfOwner(player);
      }

      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null && !YogSothothDelegateHelper.onDeath(instance, target, owner, source)) {
            return false;
         }
      }

      return true;
   }

   public void onRespawn(ManasSkillInstance instance, ServerPlayer owner, boolean conqueredEnd) {
      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null) {
            YogSothothDelegateHelper.onRespawn(instance, target, owner, conqueredEnd);
         }
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      if (entity instanceof ServerPlayer player && !instance.isTemporarySkill() && !instance.isSubInstance()) {
         this.claimUniqueOwnership(player);
      }

      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null) {
            YogSothothDelegateHelper.onLearnSkill(instance, target, entity);
         }
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         YogTimeStopManager.stopIfOwner(player);
      }

      super.onForgetSkill(instance, entity);

      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null) {
            YogSothothDelegateHelper.onForgetSkill(instance, target, entity);
         }
      }

      if (entity instanceof ServerPlayer player && !instance.isTemporarySkill() && !instance.isSubInstance()) {
         this.releaseUniqueOwnership(player);
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);

      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null) {
            YogSothothDelegateHelper.onToggleOn(instance, target, entity);
         }
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         YogTimeStopManager.stopIfOwner(player);
      }

      for (ResourceLocation id : PREREQUISITES) {
         Skill target = skill(id);
         if (target != null) {
            YogSothothDelegateHelper.onToggleOff(instance, target, entity);
         }
      }

      super.onToggleOff(instance, entity);
   }

   public SpatialStorageContainer getSpatialStorage(ManasSkillInstance instance, Provider provider) {
      CompoundTag tag = instance.getOrCreateTag();
      YogSothothConfig.YogSothothSettings config = settings();
      int pages = Math.max(1, config.spatialStoragePages);
      int size = pages * 27;
      ListTag saved = tag.getList("SpatialStorage", 10);
      int savedMax = 0;

      for (int i = 0; i < saved.size(); i++) {
         int slot = saved.getCompound(i).getByte("Slot") & 255;
         savedMax = Math.max(savedMax, slot + 1);
      }

      if (savedMax > size) {
         size = (savedMax + 26) / 27 * 27;
      }

      int maxStackSize = Math.max(1, config.spatialStorageMaxStackSize);
      SpatialStorageContainer container = new SpatialStorageContainer(size, maxStackSize);
      container.fromTag(saved, provider);
      return container;
   }

   static Skill skill(ResourceLocation id) {
      ManasSkill registered = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
      return registered instanceof Skill tensuraSkill ? tensuraSkill : null;
   }

   private void claimUniqueOwnership(ServerPlayer player) {
      if (FoxAblazeGameRules.YOG_SOTHOTH_TRULY_UNIQUE != null && player.level().getGameRules().getBoolean(FoxAblazeGameRules.YOG_SOTHOTH_TRULY_UNIQUE)) {
         ResourceLocation skillId = this.getRegistryName();
         MinecraftServer server = player.getServer();
         ServerLevel overworld = server == null ? null : server.overworld();
         if (skillId != null && overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            UUID owner = unique.getOwner(skillId);
            if (owner == null || owner.equals(player.getUUID())) {
               unique.addSkill(skillId, player.getUUID());
               unique.markDirty();
            }
         }
      }
   }

   private void releaseUniqueOwnership(ServerPlayer player) {
      if (FoxAblazeGameRules.YOG_SOTHOTH_TRULY_UNIQUE != null) {
         ResourceLocation skillId = this.getRegistryName();
         MinecraftServer server = player.getServer();
         ServerLevel overworld = server == null ? null : server.overworld();
         if (skillId != null && overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            if (player.getUUID().equals(unique.getOwner(skillId))) {
               unique.removeSkill(skillId);
               unique.markDirty();
            }
         }
      }
   }

   private static YogSothothSkill.Route route(int mode) {
      return mode >= 0 && mode < ROUTES.size() ? ROUTES.get(mode) : null;
   }

   private static int sanitizeMode(int mode) {
      return Math.floorMod(mode, 9);
   }

   private static ResourceLocation id(String namespace, String path) {
      return ResourceLocation.fromNamespaceAndPath(namespace, path);
   }

   private record Route(ResourceLocation skillId, int childMode) {
      Skill skill() {
         return YogSothothSkill.skill(this.skillId);
      }
   }
}
