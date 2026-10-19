package com.mortal_cultivation.ability.skill.unique;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.lang.reflect.Method;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class MischiefSkill extends LegacySkill {
   private static final double SKILL_OBTAIN_COST = 85000.0;
   private static final double LEARNING_COST = 2000.0;
   private static final int MAX_MASTERY = 1000;
   private static final int MIN_LEARNING_ATTEMPTS = 50;
   private static final double WHITE_LIE_MP_COST = 15000.0;
   private static final int MODE_1_COOLDOWN = 2400;
   private static final int MODE_2_COOLDOWN = 600;
   private static final int TELEPORT_COOLDOWN = 60;
   private static final ResourceLocation TWIN_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "mischief_twin_damage");

   public MischiefSkill() {
      super(SkillType.EXTRA);
   }

   private static IExistence getExistence(LivingEntity entity) {
      return TensuraStorages.getExistenceFrom(entity);
   }

   private static double getBaseMagicule(Player player) {
      AttributeInstance magicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
      return magicule != null ? magicule.getBaseValue() : player.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
   }

   private static Holder<MobEffect> presenceConcealmentEffect() {
      return TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT);
   }

   private static boolean isMischiefClone(Entity clone) {
      return clone != null && clone.getType() == HumanEntityTypes.CLONE.get();
   }

   private static boolean consumeMagicule(Player player, double amount, String translationKey) {
      IExistence existence = getExistence(player);
      if (existence.getMagicule() < amount) {
         player.displayClientMessage(Component.translatable(translationKey).withStyle(ChatFormatting.RED), true);
         return false;
      } else {
         existence.setMagicule(existence.getMagicule() - amount);
         existence.markDirty();
         return true;
      }
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/unique/mischief.png");
   }

   @Override
   public double getObtainingEpCost() {
      return 85000.0;
   }

   @Override
   public double learningCost() {
      return 2000.0;
   }

   public boolean addLearnPoint(ManasSkillInstance instance, LivingEntity entity, int mode, double point) {
      double maxIncrement = -this.getAcquirementMastery(entity) / 50.0;
      return super.addLearnPoint(instance, entity, mode, Math.min(point, maxIncrement));
   }

   public int getMaxMastery() {
      return 1000;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return false;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   @Override
   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int mode = this.getLegacyMode(instance);
      if (mode == 2 && tag.getBoolean("WhiteLieActive")) {
         return true;
      }

      if (mode == 1
         && entity instanceof Player player
         && player.isShiftKeyDown()
         && tag.contains("CelestialTwinUUID")
         && entity.level() instanceof ServerLevel serverLevel) {
         UUID twinUUID = tag.getUUID("CelestialTwinUUID");
         Entity existingTwin = ObjectSelectionHelper.getEntityFromUUID(serverLevel, twinUUID, MischiefSkill::isMischiefClone);
         if (existingTwin != null && existingTwin.isAlive()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public int modes() {
      return 2;
   }

   @Override
   public int nextMode(LivingEntity entity, TensuraSkillInstance instance, boolean reverse) {
      int currentMode = this.getLegacyMode(instance);
      int newMode;
      if (reverse) {
         newMode = currentMode <= 1 ? 2 : currentMode - 1;
      } else {
         newMode = currentMode >= 2 ? 1 : currentMode + 1;
      }

      return newMode;
   }

   @Override
   public Component getModeName(int mode) {
      return switch (mode) {
         case 1 -> Component.translatable("mortal_cultivation.skill.mode.mischief.celestial_twin");
         case 2 -> Component.translatable("mortal_cultivation.skill.mode.mischief.white_lie");
         default -> Component.empty();
      };
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      int mode = this.getLegacyMode(instance);
      switch (mode) {
         case 1:
            this.handleCelestialTwin(instance, entity);
            break;
         case 2:
            this.handleWhiteLie(instance, entity);
      }
   }

   @Override
   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta) {
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.contains("CelestialTwinUUID") && entity.level() instanceof ServerLevel level) {
            UUID twinUUID = tag.getUUID("CelestialTwinUUID");
            Entity twin = ObjectSelectionHelper.getEntityFromUUID(level, twinUUID, MischiefSkill::isMischiefClone);
            if (twin == null || !twin.isAlive()) {
               tag.remove("CelestialTwinUUID");
               instance.markDirty();
               if (entity instanceof Player player) {
                  player.displayClientMessage(Component.translatable("mortal_cultivation.skill.mischief.twin_died").withStyle(ChatFormatting.RED), false);
               }
            }
         }

         if (instance.isToggled()) {
            int tickCounter = tag.getInt("MasteryTickCounter");
            if (tickCounter % 200 == 0) {
               this.addMasteryPoint(instance, entity);
            }

            tag.putInt("MasteryTickCounter", tickCounter + 1);
         }
      }
   }

   private void handleCelestialTwin(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         Level level = entity.level();
         if (tag.contains("CelestialTwinUUID") && level instanceof ServerLevel serverLevel) {
            UUID twinUUID = tag.getUUID("CelestialTwinUUID");
            if (ObjectSelectionHelper.getEntityFromUUID(serverLevel, twinUUID, MischiefSkill::isMischiefClone) instanceof LivingEntity clone
               && isMischiefClone(clone)
               && clone.isAlive()) {
               if (player.isShiftKeyDown()) {
                  if (System.currentTimeMillis() < tag.getLong("TeleportCooldownEnd")) {
                     int remaining = (int)((tag.getLong("TeleportCooldownEnd") - System.currentTimeMillis()) / 1000L);
                     player.displayClientMessage(
                        Component.translatable("mortal_cultivation.skill.mischief.teleport_cooldown", new Object[]{remaining + "s"})
                           .withStyle(ChatFormatting.YELLOW),
                        true
                     );
                     return;
                  }

                  player.teleportTo(clone.getX(), clone.getY(), clone.getZ());
                  player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                  tag.putLong("TeleportCooldownEnd", System.currentTimeMillis() + 60000L);
                  instance.markDirty();
               } else {
                  player.displayClientMessage(Component.translatable("mortal_cultivation.skill.mischief.twin_exists").withStyle(ChatFormatting.GOLD), true);
               }

               return;
            }
         }

         this.summonCelestialTwin(instance, player, level);
      }
   }

   private void summonCelestialTwin(ManasSkillInstance instance, Player player, Level level) {
      CompoundTag tag = instance.getOrCreateTag();
      double mpCost = getBaseMagicule(player) * 0.2;
      if (consumeMagicule(player, mpCost, "mortal_cultivation.skill.mischief.insufficient_mp")) {
         BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, player, Fluid.NONE, 5.0);
         Vec3 pos = result.getLocation();
         EntityType<CloneEntity> type = (EntityType<CloneEntity>)HumanEntityTypes.CLONE.get();
         LivingEntity twin = new CloneEntity(type, level);
         this.configureMischiefTwinOwnership(twin, player);
         this.configureMischiefTwinSkill(twin, instance);
         this.copyMischiefTwinStatsAndSkills(twin, player);
         double twinEP = getExistence(player).getEP();
         IExistence twinExistence = getExistence(twin);
         twinExistence.setEP(twinEP);
         this.syncTwinEnergyResources(twin, player);
         twinExistence.markDirty();
         twin.moveTo(pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
         twin.setYBodyRot(player.getYRot());
         twin.setYHeadRot(player.getYHeadRot());

         for (EquipmentSlot slot : EquipmentSlot.values()) {
            twin.setItemSlot(slot, player.getItemBySlot(slot).copy());
         }

         ManasSkillInstance regenInstance = new TensuraSkillInstance((ManasSkill)CommonSkills.SELF_REGENERATION.get());
         regenInstance.setToggled(true);
         SkillAPI.getSkillsFrom(twin).learnSkill(regenInstance);
         AttributeInstance attackDamage = twin.getAttribute(Attributes.ATTACK_DAMAGE);
         if (attackDamage != null) {
            attackDamage.removeModifier(TWIN_DAMAGE_MODIFIER_ID);
            AttributeModifier damageModifier = new AttributeModifier(TWIN_DAMAGE_MODIFIER_ID, 1.0, Operation.ADD_MULTIPLIED_TOTAL);
            attackDamage.addPermanentModifier(damageModifier);
         }

         this.setMischiefTwinBehaviour(twin, 2);
         level.addFreshEntity(twin);
         twin.setHealth(twin.getMaxHealth());
         twin.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4, false, false, true));
         twin.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 2, false, false, true));
         if (level instanceof ServerLevel serverLevel) {
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "clone_" + player.getUUID().toString().substring(0, 8);
            PlayerTeam team = scoreboard.getPlayerTeam(teamName);
            if (team == null) {
               team = scoreboard.addPlayerTeam(teamName);
               team.setAllowFriendlyFire(false);
            }

            scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
            scoreboard.addPlayerToTeam(twin.getStringUUID(), team);
         }

         tag.putUUID("CelestialTwinUUID", twin.getUUID());
         instance.markDirty();
         TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.CLOUD, 2.0);
         TensuraParticleHelper.addServerParticlesAroundSelf(twin, ParticleTypes.CLOUD, 2.0);
         level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_PREPARE_MIRROR, SoundSource.PLAYERS, 1.0F, 1.0F);
         instance.setCoolDown(2400, 0);
         this.addMasteryPoint(instance, player);
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
      if (entity instanceof Player player) {
         boolean avoided = this.handleDeathAvoidance(player, instance);
         if (!avoided) {
            this.forceDiscardMischiefClone(player, instance);
         }

         return !avoided;
      } else {
         return true;
      }
   }

   private void forceDiscardMischiefClone(Player player, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.contains("CelestialTwinUUID")) {
         if (player.level() instanceof ServerLevel level) {
            UUID var7 = tag.getUUID("CelestialTwinUUID");
            Entity twin = ObjectSelectionHelper.getEntityFromUUID(level, var7, MischiefSkill::isMischiefClone);
            if (twin != null) {
               twin.discard();
            }

            tag.remove("CelestialTwinUUID");
            instance.markDirty();
         }
      }
   }

   public boolean handleDeathAvoidance(Player player, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.contains("CelestialTwinUUID")) {
         return false;
      }

      if (player.level() instanceof ServerLevel level) {
         UUID var8 = tag.getUUID("CelestialTwinUUID");
         if (ObjectSelectionHelper.getEntityFromUUID(level, var8, MischiefSkill::isMischiefClone) instanceof LivingEntity clone
            && isMischiefClone(clone)
            && clone.isAlive()) {
            player.teleportTo(clone.getX(), clone.getY(), clone.getZ());
            player.setHealth(clone.getHealth());
            clone.discard();
            tag.remove("CelestialTwinUUID");
            instance.markDirty();
            TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.SMOKE, 3.0);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.displayClientMessage(Component.translatable("mortal_cultivation.skill.mischief.death_avoided").withStyle(ChatFormatting.GOLD), false);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void configureMischiefTwinOwnership(LivingEntity twin, Player player) {
      try {
         Method tame = twin.getClass().getMethod("tame", Player.class);
         tame.invoke(twin, player);
      } catch (ReflectiveOperationException var4) {
      }
   }

   private void configureMischiefTwinSkill(LivingEntity twin, ManasSkillInstance instance) {
      try {
         Method setSkill = twin.getClass().getMethod("setSkill", ManasSkillInstance.class);
         setSkill.invoke(twin, instance);
      } catch (ReflectiveOperationException var4) {
      }
   }

   private void copyMischiefTwinStatsAndSkills(LivingEntity twin, Player player) {
      try {
         Method copyStatsAndSkills = twin.getClass().getMethod("copyStatsAndSkills", Player.class, boolean.class);
         copyStatsAndSkills.invoke(twin, player, true);
      } catch (ReflectiveOperationException var4) {
      }
   }

   private void syncTwinEnergyResources(LivingEntity twin, Player player) {
      IExistence playerExistence = getExistence(player);
      IExistence twinExistence = getExistence(twin);
      double maxMagicule = EnergyHelper.getMaxMagicule(player);
      double maxAura = EnergyHelper.getMaxAura(player);
      EnergyHelper.setMaxMagicule(twin, maxMagicule);
      EnergyHelper.setMaxAura(twin, maxAura);
      twinExistence.setMagicule(Math.min(maxMagicule, Math.max(1.0, playerExistence.getMagicule())));
      twinExistence.setAura(Math.min(maxAura, Math.max(0.0, playerExistence.getAura())));
   }

   private void setMischiefTwinBehaviour(LivingEntity twin, int behaviour) {
      try {
         Method setBehaviour = twin.getClass().getMethod("setBehaviour", int.class);
         setBehaviour.invoke(twin, behaviour);
      } catch (ReflectiveOperationException var4) {
      }
   }

   private void handleWhiteLie(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         boolean isCurrentlyActive = tag.getBoolean("WhiteLieActive");
         if (isCurrentlyActive) {
            tag.putBoolean("WhiteLieActive", false);
            player.removeEffect(MobEffects.INVISIBILITY);
            player.removeEffect(presenceConcealmentEffect());
            instance.markDirty();
            player.refreshDisplayName();
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 0.5F);
         } else if (consumeMagicule(player, 15000.0, "mortal_cultivation.skill.mischief.insufficient_mp")) {
            tag.putBoolean("WhiteLieActive", true);
            instance.setToggled(true);
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 6000, 0, false, false, true));
            player.addEffect(new MobEffectInstance(presenceConcealmentEffect(), 6000, 2, false, false, true));
            player.refreshDisplayName();
            player.displayClientMessage(Component.translatable("mortal_cultivation.skill.mischief.white_lie_on").withStyle(ChatFormatting.AQUA), false);
            instance.setCoolDown(600, 1);
            instance.markDirty();
            this.addMasteryPoint(instance, player);
            TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.ENCHANT, 2.0);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 0.5F);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerRespawn(PlayerRespawnEvent event) {
      Player player = event.getEntity();
      if (player.level() instanceof ServerLevel level) {
         SkillAPI.getSkillsFrom(player).getLearnedSkills().forEach(instance -> {
            if (instance.getSkill() instanceof MischiefSkill) {
               CompoundTag tag = instance.getOrCreateTag();
               if (tag.contains("CelestialTwinUUID")) {
                  UUID twinUUID = tag.getUUID("CelestialTwinUUID");
                  Entity twin = ObjectSelectionHelper.getEntityFromUUID(level, twinUUID, MischiefSkill::isMischiefClone);
                  if (twin != null) {
                     twin.discard();
                  }

                  tag.remove("CelestialTwinUUID");
                  instance.markDirty();
               }
            }
         });
      }
   }
}
