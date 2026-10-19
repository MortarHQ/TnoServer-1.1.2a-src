package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.fungus.FungusInfectionProvider;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class FungusInfectionSkill extends Skill {
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "fungus_infection");
   public static final String TAG_ROOT = "tms_fungus_infection";
   private static final String TAG_EFFECTS = "effects";
   private static final String TAG_WORKSHOP = "workshop";
   private static final String TAG_UPGRADES = "upgrades";
   private static final String TAG_SPENT = "spent";
   private static final String TAG_LAST_AURA = "fungus_last_aura";
   private static volatile Map<String, Holder<MobEffect>> EFFECT_BY_DESC = null;

   public FungusInfectionSkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.FungusInfectionConfig cfg() {
      return MoreSkillsConfigs.GRAND.fungusInfection;
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/fungus.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)cfg().obtainCost.get();
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 3 : mode - 1;
      } else {
         return mode >= 3 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "fungus_infection.analyze";
         case 1 -> "fungus_infection.workshop";
         case 2 -> "fungus_infection.configure";
         case 3 -> "fungus_infection.spread";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.isToggled()) {
         if (entity instanceof ServerPlayer player) {
            if (player.level() instanceof ServerLevel level) {
               CompoundTag var14 = instance.getOrCreateTag();
               long now = level.getGameTime();
               long lastAura = var14.getLong("fungus_last_aura");
               if (now - lastAura >= ((Integer)cfg().auraApplyEveryTicks.get()).intValue()) {
                  var14.putLong("fungus_last_aura", now);
                  instance.markDirty();
                  AABB box = player.getBoundingBox().inflate((Double)cfg().auraRadius.get());

                  for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class, box, target -> target != player && target.isAlive())) {
                     victim.addEffect(
                        new MobEffectInstance(
                           TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS),
                           (Integer)cfg().auraParalysisTicks.get(),
                           (Integer)cfg().auraParalysisAmplifier.get(),
                           false,
                           false,
                           true
                        )
                     );
                     TensuraParticleHelper.addServerParticlesAroundPos(
                        player.getRandom(),
                        level,
                        victim.position().add(0.0, victim.getBbHeight() * 0.5, 0.0),
                        TensuraParticleUtils.getPoisonEffect((Integer)cfg().auraPoisonParticlesVictim.get()),
                        0.8
                     );
                     tryPlaceMushroomPatch(
                        level, BlockPos.containing(victim.getX(), victim.getY() - 0.2, victim.getZ()), (Integer)cfg().auraMushroomPatchAttemptsVictim.get()
                     );
                  }

                  TensuraParticleHelper.addServerParticlesAroundPos(
                     player.getRandom(),
                     level,
                     player.position().add(0.0, 1.0, 0.0),
                     TensuraParticleUtils.getPoisonEffect((Integer)cfg().auraPoisonParticlesOwner.get()),
                     1.2
                  );
                  tryPlaceMushroomPatch(level, player.blockPosition(), (Integer)cfg().auraMushroomPatchAttemptsOwner.get());
                  this.addMasteryPoint(instance, entity);
               }

               if (now % ((Integer)cfg().storedAuraVisualIntervalTicks.get()).intValue() == 0L) {
                  int stored = readRoot(player).getCompound("effects").getAllKeys().size();
                  if (stored > 0) {
                     int particles = Math.min(
                        (Integer)cfg().storedAuraVisualMaxParticles.get(),
                        (Integer)cfg().storedAuraVisualBaseParticles.get() + stored * (Integer)cfg().storedAuraVisualParticlesPerEffect.get()
                     );
                     TensuraParticleHelper.addServerParticlesAroundPos(
                        player.getRandom(), level, player.position().add(0.0, 1.0, 0.0), TensuraParticleUtils.getPoisonEffect(particles), 0.9
                     );
                  }
               }
            }
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel level) {
            if (instance.onCoolDown(mode)) {
               player.displayClientMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.cooldown"), true);
            } else if (mode == 0) {
               boolean ok = analyzeEffect(player);
               if (ok) {
                  this.addMasteryPoint(instance, entity);
                  TensuraParticleHelper.addServerParticlesAroundPos(
                     player.getRandom(),
                     level,
                     player.position().add(0.0, 1.0, 0.0),
                     TensuraParticleUtils.getPoisonEffect((Integer)cfg().analyzePoisonParticles.get()),
                     0.8
                  );
               }

               instance.setCoolDown((Integer)cfg().analyzeCooldownTicks.get(), mode);
            } else if (mode == 1) {
               FungusInfectionProvider.open(player);
               TensuraParticleHelper.addServerParticlesAroundPos(
                  player.getRandom(), level, player.position().add(0.0, 1.0, 0.0), TensuraParticleUtils.getPoisonEffect(14), 0.6
               );
               instance.setCoolDown((Integer)cfg().workshopCooldownTicks.get(), mode);
            } else if (mode == 2) {
               boolean ok = player.isShiftKeyDown() ? clearWorkshop(player) : ensureWorkshopConfigured(player, instance.isMastered(player));
               if (ok) {
                  this.addMasteryPoint(instance, entity);
                  TensuraParticleHelper.addServerParticlesAroundPos(
                     player.getRandom(), level, player.position().add(0.0, 1.0, 0.0), TensuraParticleUtils.getPoisonEffect(20), 0.9
                  );
                  tryPlaceMushroomPatch(level, player.blockPosition(), 1);
               }

               instance.setCoolDown((Integer)cfg().configureCooldownTicks.get(), mode);
            } else {
               boolean ok = spreadCurse(player, instance.isMastered(player));
               if (ok) {
                  this.addMasteryPoint(instance, entity);
               }

               instance.setCoolDown((Integer)cfg().spreadCooldownTicks.get(), mode);
            }
         }
      }
   }

   private static boolean analyzeEffect(ServerPlayer player) {
      LivingEntity living;
      if (player.isShiftKeyDown()) {
         living = player;
      } else {
         if (!(getTarget(player) instanceof LivingEntity targetLiving)) {
            player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.analyze_fail"));
            return false;
         }

         living = targetLiving;
      }

      if (living.getActiveEffects().isEmpty()) {
         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.analyze_empty"));
         return false;
      }

      CompoundTag root = getOrCreateRoot(player);
      CompoundTag map = root.getCompound("effects");
      List<MobEffectInstance> newOnes = new ArrayList<>();
      List<MobEffectInstance> oldOnes = new ArrayList<>();

      for (MobEffectInstance effect : living.getActiveEffects()) {
         String key = ((MobEffect)effect.getEffect().value()).getDescriptionId();
         if (map.contains(key)) {
            oldOnes.add(effect);
         } else {
            newOnes.add(effect);
         }
      }

      MobEffectInstance sampled;
      boolean wasNew;
      if (!newOnes.isEmpty()) {
         sampled = newOnes.get(player.getRandom().nextInt(newOnes.size()));
         wasNew = true;
      } else {
         sampled = oldOnes.get(player.getRandom().nextInt(oldOnes.size()));
         wasNew = false;
      }

      String key = ((MobEffect)sampled.getEffect().value()).getDescriptionId();
      int next = map.getInt(key) + 1;
      map.putInt(key, next);
      root.put("effects", map);
      player.getPersistentData().put("tms_fungus_infection", root);
      if (player.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundPos(
            player.getRandom(),
            level,
            living.position().add(0.0, living.getBbHeight() * 0.5, 0.0),
            TensuraParticleUtils.getPoisonEffect((Integer)cfg().analyzePoisonParticles.get()),
            0.75
         );
         tryPlaceMushroomPatch(level, living.blockPosition(), (Integer)cfg().analyzeMushroomPatchAttempts.get());
      }

      if (wasNew) {
         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.analyze_ok", new Object[]{Component.translatable(key)}));
      } else {
         player.sendSystemMessage(
            Component.translatable("tensuramoreskills.skill.fungus_infection.analyze_repeat", new Object[]{Component.translatable(key), next})
         );
      }

      return true;
   }

   private static boolean ensureWorkshopConfigured(ServerPlayer player, boolean mastered) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag workshop = root.getCompound("workshop");
      int size = workshop.getInt("size");
      if (size > 0) {
         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.workshop_ready", new Object[]{size}));
         return true;
      }

      CompoundTag map = root.getCompound("effects");
      if (map.isEmpty()) {
         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.no_stored"));
         return false;
      }

      Set<String> keys = map.getAllKeys();
      List<String> list = new ArrayList<>(keys);
      list.sort(String::compareToIgnoreCase);
      int limit = mastered ? (Integer)cfg().workshopLimitMastered.get() : (Integer)cfg().workshopLimitNormal.get();
      CompoundTag out = new CompoundTag();
      int index = 0;

      for (String key : list) {
         if (index >= limit) {
            break;
         }

         out.putString("e" + index, key);
         index++;
      }

      out.putInt("size", index);
      root.put("workshop", out);
      player.getPersistentData().put("tms_fungus_infection", root);
      player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.workshop_set", new Object[]{index}));
      return true;
   }

   private static boolean clearWorkshop(ServerPlayer player) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag workshop = new CompoundTag();
      workshop.putInt("size", 0);
      root.put("workshop", workshop);
      player.getPersistentData().put("tms_fungus_infection", root);
      player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.workshop_cleared"));
      return true;
   }

   private static boolean spreadCurse(ServerPlayer player, boolean mastered) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag workshop = root.getCompound("workshop");
      int limit = mastered ? (Integer)cfg().workshopLimitMastered.get() : (Integer)cfg().workshopLimitNormal.get();
      List<String> chosenKeys = new ArrayList<>();

      for (int i = 0; i < limit; i++) {
         String key = workshop.getString("e" + i);
         if (!key.isEmpty()) {
            chosenKeys.add(key);
         }
      }

      if (chosenKeys.isEmpty()) {
         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.no_workshop"));
         return false;
      }

      int count = mastered ? (Integer)cfg().spreadCountMastered.get() : (Integer)cfg().spreadCountNormal.get();
      int applied = 0;
      if (!player.isShiftKeyDown()) {
         AABB box = player.getBoundingBox().inflate((Double)cfg().spreadRange.get());
         List<LivingEntity> targets = player.level().getEntitiesOfClass(LivingEntity.class, box, targetx -> targetx != player && targetx.isAlive());
         if (targets.isEmpty()) {
            player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.spread_no_targets"));
            return false;
         }

         for (int i = 0; i < count; i++) {
            String key = chosenKeys.get(player.getRandom().nextInt(chosenKeys.size()));
            Holder<MobEffect> holder = resolveEffectByDescId(key);
            int durationLevel = getUpgradeDur(player, key);
            int amplifierLevel = getUpgradeAmp(player, key);
            int baseDuration = (Integer)cfg().spreadTargetBaseDurationMin.get()
               + player.getRandom().nextInt((Integer)cfg().spreadTargetBaseDurationRandom.get());
            int duration = Math.max(
               (Integer)cfg().spreadTargetMinimumDuration.get(), (int)(baseDuration * (1.0 + (Double)cfg().spreadDurationPerUpgrade.get() * durationLevel))
            );
            int amplifier = Math.max(0, amplifierLevel);
            LivingEntity target = targets.get(player.getRandom().nextInt(targets.size()));
            target.addEffect(new MobEffectInstance(holder, duration, amplifier, false, false, true));
            applied++;
            if (player.level() instanceof ServerLevel serverLevel) {
               TensuraParticleHelper.addServerParticlesAroundPos(
                  player.getRandom(),
                  serverLevel,
                  target.position().add(0.0, target.getBbHeight() * 0.5, 0.0),
                  TensuraParticleUtils.getPoisonEffect((Integer)cfg().spreadTargetPoisonParticles.get()),
                  0.85
               );
               tryPlaceMushroomPatch(serverLevel, target.blockPosition(), (Integer)cfg().spreadMushroomPatchAttempts.get());
            }
         }

         if (player.level() instanceof ServerLevel serverLevel) {
            TensuraParticleHelper.addServerParticlesAroundPos(
               player.getRandom(),
               serverLevel,
               player.position().add(0.0, 1.0, 0.0),
               TensuraParticleUtils.getPoisonEffect((Integer)cfg().spreadOwnerPoisonParticles.get()),
               1.15
            );
            tryPlaceMushroomPatch(serverLevel, player.blockPosition(), (Integer)cfg().spreadMushroomPatchAttempts.get());
         }

         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.spread_applied", new Object[]{applied}));
         return true;
      } else {
         for (int i = 0; i < count; i++) {
            String key = chosenKeys.get(player.getRandom().nextInt(chosenKeys.size()));
            Holder<MobEffect> holder = resolveEffectByDescId(key);
            int durationLevel = getUpgradeDur(player, key);
            int amplifierLevel = getUpgradeAmp(player, key);
            int baseDuration = (Integer)cfg().spreadSelfBaseDurationMin.get() + player.getRandom().nextInt((Integer)cfg().spreadSelfBaseDurationRandom.get());
            int duration = Math.max(
               (Integer)cfg().spreadSelfMinimumDuration.get(), (int)(baseDuration * (1.0 + (Double)cfg().spreadDurationPerUpgrade.get() * durationLevel))
            );
            int amplifier = Math.max(0, amplifierLevel);
            player.addEffect(new MobEffectInstance(holder, duration, amplifier, false, false, true));
            applied++;
         }

         if (player.level() instanceof ServerLevel serverLevel) {
            TensuraParticleHelper.addServerParticlesAroundPos(
               player.getRandom(),
               serverLevel,
               player.position().add(0.0, 1.0, 0.0),
               TensuraParticleUtils.getPoisonEffect((Integer)cfg().spreadSelfPoisonParticles.get()),
               1.25
            );
            tryPlaceMushroomPatch(serverLevel, player.blockPosition(), (Integer)cfg().spreadMushroomPatchAttempts.get());
         }

         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.spread_applied", new Object[]{applied}));
         return true;
      }
   }

   private static Entity getTarget(ServerPlayer player) {
      Entity direct = ObjectSelectionHelper.getTargetingEntity(player, (Double)cfg().analyzeRange.get(), false, true);
      if (direct != null) {
         return direct;
      }

      List<Entity> list = player.level()
         .getEntities(
            player,
            player.getBoundingBox()
               .expandTowards(player.getLookAngle().scale((Double)cfg().analyzeRange.get()))
               .inflate((Double)cfg().analyzeFallbackInflate.get()),
            target -> target instanceof LivingEntity && target.isAlive()
         );
      list.sort(Comparator.comparingDouble(target -> target.distanceToSqr(player)));
      return list.isEmpty() ? null : list.getFirst();
   }

   private static CompoundTag getOrCreateRoot(ServerPlayer player) {
      CompoundTag root = player.getPersistentData().getCompound("tms_fungus_infection");
      if (!root.contains("effects")) {
         root.put("effects", new CompoundTag());
      }

      if (!root.contains("workshop")) {
         root.put("workshop", new CompoundTag());
      }

      if (!root.contains("upgrades")) {
         root.put("upgrades", new CompoundTag());
      }

      if (!root.contains("spent")) {
         root.put("spent", new CompoundTag());
      }

      return root;
   }

   public static CompoundTag readRoot(Player player) {
      return player.getPersistentData().getCompound("tms_fungus_infection");
   }

   public static boolean hasMastered(ServerPlayer player) {
      Skills storage = SkillAPI.getSkillsFrom(player);
      if (storage == null) {
         return false;
      }

      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(ID);
      if (skill == null) {
         return false;
      }

      Optional<ManasSkillInstance> optional = storage.getSkill(skill);
      return optional.<Boolean>map(instance -> instance.isMastered(player)).orElse(false);
   }

   public static boolean hasAbhoth(ServerPlayer player) {
      Skills storage = SkillAPI.getSkillsFrom(player);
      if (storage == null) {
         return false;
      }

      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "abhoth_lord_of_pestilence"));
      if (skill == null) {
         return false;
      }

      Optional<ManasSkillInstance> optional = storage.getSkill(skill);
      return optional.isPresent();
   }

   public static int getWorkshopLimit(ServerPlayer player) {
      return hasAbhoth(player) ? 10 : hasMastered(player) ? (Integer)cfg().workshopLimitMastered.get() : (Integer)cfg().workshopLimitNormal.get();
   }

   public static void writeStorageSnapshot(ServerPlayer player, RegistryFriendlyByteBuf buf) {
      List<String> keys = new ArrayList<>();
      List<Integer> counts = new ArrayList<>();
      List<Integer> duration = new ArrayList<>();
      List<Integer> amplifier = new ArrayList<>();
      List<Integer> spent = new ArrayList<>();
      fillStorageLists(player, keys, counts, duration, amplifier, spent);
      buf.writeVarInt(keys.size());

      for (int i = 0; i < keys.size(); i++) {
         buf.writeUtf(keys.get(i));
         buf.writeVarInt(counts.get(i));
         buf.writeVarInt(duration.get(i));
         buf.writeVarInt(amplifier.get(i));
         buf.writeVarInt(spent.get(i));
      }

      int limit = getWorkshopLimit(player);
      buf.writeVarInt(limit);

      for (int i = 0; i < 10; i++) {
         buf.writeVarInt(indexOfKey(keys, getWorkshopKey(player, i)));
      }
   }

   private static int indexOfKey(List<String> keys, String key) {
      return key != null && !key.isEmpty() ? keys.indexOf(key) : -1;
   }

   public static void fillStorageLists(
      ServerPlayer player, List<String> outKeys, List<Integer> outCounts, List<Integer> outDur, List<Integer> outAmp, List<Integer> outSpent
   ) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag map = root.getCompound("effects");
      List<String> list = new ArrayList<>(map.getAllKeys());
      list.sort(String::compareToIgnoreCase);

      for (String key : list) {
         outKeys.add(key);
         outCounts.add(map.getInt(key));
         outDur.add(getUpgradeDur(player, key));
         outAmp.add(getUpgradeAmp(player, key));
         outSpent.add(getSpent(player, key));
      }
   }

   public static int getAnalyzeCount(ServerPlayer player, String key) {
      CompoundTag root = getOrCreateRoot(player);
      return root.getCompound("effects").getInt(key);
   }

   public static int getUpgradeDur(ServerPlayer player, String key) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag upgrade = root.getCompound("upgrades").getCompound(key);
      return upgrade.getInt("dur");
   }

   public static int getUpgradeAmp(ServerPlayer player, String key) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag upgrade = root.getCompound("upgrades").getCompound(key);
      return upgrade.getInt("amp");
   }

   public static void setUpgradeDur(ServerPlayer player, String key, int level) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag upgrades = root.getCompound("upgrades");
      CompoundTag upgrade = upgrades.getCompound(key);
      upgrade.putInt("dur", level);
      upgrades.put(key, upgrade);
      root.put("upgrades", upgrades);
      player.getPersistentData().put("tms_fungus_infection", root);
   }

   public static void setUpgradeAmp(ServerPlayer player, String key, int level) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag upgrades = root.getCompound("upgrades");
      CompoundTag upgrade = upgrades.getCompound(key);
      upgrade.putInt("amp", level);
      upgrades.put(key, upgrade);
      root.put("upgrades", upgrades);
      player.getPersistentData().put("tms_fungus_infection", root);
   }

   public static int getSpent(ServerPlayer player, String key) {
      CompoundTag root = getOrCreateRoot(player);
      return root.getCompound("spent").getInt(key);
   }

   public static void setSpent(ServerPlayer player, String key, int value) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag spent = root.getCompound("spent");
      spent.putInt(key, value);
      root.put("spent", spent);
      player.getPersistentData().put("tms_fungus_infection", root);
   }

   public static String getWorkshopKey(ServerPlayer player, int slot) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag workshop = root.getCompound("workshop");
      return workshop.getString("e" + slot);
   }

   public static void setWorkshopKey(ServerPlayer player, int slot, String key) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag workshop = root.getCompound("workshop");
      int limit = getWorkshopLimit(player);
      if (slot < limit) {
         workshop.putString("e" + slot, key);
         int size = 0;

         for (int i = 0; i < limit; i++) {
            if (!workshop.getString("e" + i).isEmpty()) {
               size++;
            }
         }

         workshop.putInt("size", size);
         root.put("workshop", workshop);
         player.getPersistentData().put("tms_fungus_infection", root);
      }
   }

   private static Holder<MobEffect> resolveEffectByDescId(String descId) {
      Map<String, Holder<MobEffect>> map = EFFECT_BY_DESC;
      if (map == null) {
         HashMap<String, Holder<MobEffect>> built = new HashMap<>();

         for (MobEffect effect : BuiltInRegistries.MOB_EFFECT) {
            built.put(effect.getDescriptionId(), BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect));
         }

         EFFECT_BY_DESC = built;
         map = built;
      }

      Holder<MobEffect> found = map.get(descId);
      return found != null ? found : TensuraMobEffects.getReference(TensuraMobEffects.INFECTION);
   }

   private static void tryPlaceMushroomPatch(ServerLevel level, BlockPos center, int attempts) {
      int radius = (Integer)cfg().mushroomPatchSearchRadius.get();

      for (int i = 0; i < attempts; i++) {
         BlockPos ground = center.offset(level.random.nextInt(radius * 2 + 1) - radius, -1, level.random.nextInt(radius * 2 + 1) - radius);
         BlockPos place = ground.above();
         if (level.isEmptyBlock(place)) {
            BlockState red = Blocks.RED_MUSHROOM.defaultBlockState();
            BlockState brown = Blocks.BROWN_MUSHROOM.defaultBlockState();
            BlockState chosen = level.random.nextBoolean() ? red : brown;
            if (chosen.canSurvive(level, place)) {
               level.setBlock(place, chosen, (Integer)cfg().mushroomPatchBlockFlags.get());
            }
         }
      }
   }
}
