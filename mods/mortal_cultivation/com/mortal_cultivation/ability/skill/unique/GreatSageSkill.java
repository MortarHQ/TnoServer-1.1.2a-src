package com.mortal_cultivation.ability.skill.unique;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import com.mortal_cultivation.registry.item.CultivationItems;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team.Visibility;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class GreatSageSkill extends LegacySkill {
   private static final double SKILL_OBTAIN_COST = 130000.0;
   private static final double LEARNING_COST = 3000.0;
   private static final int MAX_MASTERY = 2500;
   private static final int MODE_1_COOLDOWN = 720;
   private static final int MODE_2_COOLDOWN = 300;
   private static final int MODE_3_COOLDOWN = 50;
   private static final int MODE_4_COOLDOWN = 10000;
   private static final int TELEPORT_COOLDOWN = 10;
   private static final double DEFY_MP_COST = 30000.0;
   private static final double DISGUISE_MP_COST = 15000.0;
   private static final ResourceLocation TWIN_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "great_sage_twin_damage");
   private static final ResourceLocation TWIN_HEALTH_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "great_sage_twin_health");
   private static final ResourceLocation PRIMAL_FURY_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "great_sage_primal_fury_damage");
   private static final ResourceLocation PRIMAL_FURY_HEALTH_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "great_sage_primal_fury_health");
   private static final ResourceLocation PRIMAL_FURY_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "great_sage_primal_fury_speed");
   private static final ResourceLocation CLOUD_WALKER_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "great_sage_cloud_walker_speed");
   private static final int WHITE_LIE_DURATION = 6000;
   private static final int WHITE_LIE_COOLDOWN = 300;
   private static final Set<UUID> hiddenPlayers = new HashSet<>();
   private static final int BERSERK_DURATION = 600;
   private static final int BERSERK_SUCCESS_COOLDOWN = 1200;
   private static final int BERSERK_FAIL_COOLDOWN = 30;
   private static final ResourceLocation BERSERK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "great_sage_berserk_damage");

   public GreatSageSkill() {
      super(SkillType.UNIQUE);
   }

   private static IExistence getExistence(LivingEntity entity) {
      return TensuraStorages.getExistenceFrom(entity);
   }

   private static double getBaseMagicule(Player player) {
      AttributeInstance magicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
      return magicule != null ? magicule.getBaseValue() : player.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
   }

   private static Holder<MobEffect> severanceBladeEffect() {
      return TensuraMobEffects.getReference(TensuraMobEffects.SEVERANCE_BLADE);
   }

   private static Holder<MobEffect> presenceConcealmentEffect() {
      return TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT);
   }

   private static boolean isGreatSageClone(Entity clone) {
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
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/unique/great_sage.png");
   }

   @Override
   public double getObtainingEpCost() {
      return 130000.0;
   }

   @Override
   public double learningCost() {
      return 3000.0;
   }

   public int getMaxMastery() {
      return 2500;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      return instance.isToggled()
         || tag.getBoolean("CloudWalkerActive")
         || tag.getBoolean("PrimalFuryActive")
         || tag.getBoolean("DefyActive")
         || tag.getBoolean("TwinBerserk")
         || tag.getBoolean("IsDisguised")
         || tag.getBoolean("IsHidden")
         || tag.contains("CelestialTwinUUID");
   }

   @Override
   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int mode = this.getLegacyMode(instance);

      return switch (mode) {
         case 1 -> {
            if (entity instanceof Player player
               && player.isShiftKeyDown()
               && tag.contains("CelestialTwinUUID")
               && entity.level() instanceof ServerLevel serverLevel) {
               UUID twinUUID = tag.getUUID("CelestialTwinUUID");
               Entity existingTwin = ObjectSelectionHelper.getEntityFromUUID(serverLevel, twinUUID, GreatSageSkill::isGreatSageClone);
               yield existingTwin != null && existingTwin.isAlive();
            }

            yield false;
         }
         default -> false;
         case 3 -> {
            if (!tag.getBoolean("CloudWalkerActive") && !(entity instanceof Player p && p.isShiftKeyDown())) {
               yield false;
            }

            yield true;
         }
         case 4 -> {
            if (!tag.getBoolean("PrimalFuryActive") && !(entity instanceof Player p && p.isShiftKeyDown())) {
               yield false;
            }

            yield true;
         }
         case 5 -> tag.getBoolean("IsDisguised") || tag.getBoolean("IsHidden");
      };
   }

   @Override
   public int modes() {
      return 5;
   }

   @Override
   public int nextMode(LivingEntity entity, TensuraSkillInstance instance, boolean reverse) {
      int currentMode = this.getLegacyMode(instance);
      int newMode;
      if (reverse) {
         newMode = currentMode <= 1 ? 5 : currentMode - 1;
      } else {
         newMode = currentMode >= 5 ? 1 : currentMode + 1;
      }

      return newMode;
   }

   @Override
   public Component getModeName(int mode) {
      return switch (mode) {
         case 1 -> Component.translatable("mortal_cultivation.skill.mode.great_sage.celestial_twin");
         case 2 -> Component.translatable("mortal_cultivation.skill.mode.great_sage.defy");
         case 3 -> Component.translatable("mortal_cultivation.skill.mode.great_sage.cloud_walker");
         case 4 -> Component.translatable("mortal_cultivation.skill.mode.great_sage.primal_fury");
         case 5 -> Component.translatable("mortal_cultivation.skill.mode.great_sage.white_lie");
         default -> Component.empty();
      };
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.getBoolean("CloudWalkerActive")) {
         this.deactivateCloudWalker(instance, entity);
      }

      if (tag.getBoolean("PrimalFuryActive")) {
         this.deactivatePrimalFury(instance, entity);
      }
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      int mode = this.getLegacyMode(instance);
      CompoundTag tag = instance.getOrCreateTag();
      if (mode == 3 && tag.getBoolean("CloudWalkerActive")) {
         this.deactivateCloudWalker(instance, entity);
      } else if (mode == 4 && tag.getBoolean("PrimalFuryActive")) {
         this.deactivatePrimalFury(instance, entity);
      } else if (mode != 5 || !tag.getBoolean("IsDisguised") && !tag.getBoolean("IsHidden")) {
         if (!(entity instanceof Player player && player.isShiftKeyDown() && (mode == 3 || mode == 4))) {
            switch (mode) {
               case 1:
                  this.handleCelestialTwin(instance, entity);
                  break;
               case 2:
                  this.handleDefy(instance, entity);
                  break;
               case 3:
                  this.handleCloudWalker(instance, entity);
                  break;
               case 4:
                  this.handlePrimalFury(instance, entity);
                  break;
               case 5:
                  this.handleWhiteLie(instance, entity);
            }
         } else {
            this.handleShiftRightClick(instance, player, mode);
         }
      } else {
         this.deactivateWhiteLie(instance, entity);
      }
   }

   @Override
   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta) {
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         if (entity instanceof Player player && !tag.getBoolean("PrimalFuryActive") && entity.tickCount % 20 == 0) {
            this.confiscateJinguBang(player);
         }

         if (tag.contains("CelestialTwinUUID") && entity.level() instanceof ServerLevel level) {
            UUID twinUUID = tag.getUUID("CelestialTwinUUID");
            Entity twin = ObjectSelectionHelper.getEntityFromUUID(level, twinUUID, GreatSageSkill::isGreatSageClone);
            if (twin == null || !twin.isAlive()) {
               tag.remove("CelestialTwinUUID");
               if (!tag.getBoolean("CloudWalkerActive")
                  && !tag.getBoolean("PrimalFuryActive")
                  && !tag.getBoolean("DefyActive")
                  && !tag.getBoolean("TwinBerserk")
                  && !tag.getBoolean("IsDisguised")
                  && !tag.getBoolean("IsHidden")) {
                  instance.setToggled(false);
               }

               instance.markDirty();
               if (entity instanceof Player player) {
                  player.displayClientMessage(Component.translatable("mortal_cultivation.skill.great_sage.twin_died").withStyle(ChatFormatting.RED), false);
               }
            }
         }

         if (tag.getBoolean("DefyActive")) {
            int defyTimer = tag.getInt("DefyTimer");
            if (defyTimer > 0) {
               tag.putInt("DefyTimer", defyTimer - 1);
            } else {
               this.deactivateDefy(instance, entity);
            }
         }

         if (tag.getBoolean("CloudWalkerActive") && entity instanceof Player player) {
            if (player.level() instanceof ServerLevel serverLevel) {
               Vec3 lookVec = player.getLookAngle().normalize();
               Vec3 behindPos = player.position().subtract(lookVec.scale(0.5));
               serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.1, player.getZ(), 2, 0.2, 0.05, 0.2, 0.02);
               serverLevel.sendParticles(ParticleTypes.CLOUD, behindPos.x, behindPos.y + 0.5, behindPos.z, 2, 0.15, 0.1, 0.15, 0.02);
            }

            if (tag.contains("CelestialTwinUUID") && player.level() instanceof ServerLevel level) {
               UUID twinUUID = tag.getUUID("CelestialTwinUUID");
               Entity twin = ObjectSelectionHelper.getEntityFromUUID(level, twinUUID, GreatSageSkill::isGreatSageClone);
               if (twin != null && twin.isAlive()) {
                  Vec3 playerPos = player.position();
                  Vec3 lookVec = player.getLookAngle().normalize();
                  Vec3 twinPos = playerPos.subtract(lookVec.scale(2.0));
                  twin.setPos(twinPos.x, twinPos.y, twinPos.z);
                  twin.setYRot(player.getYRot());
                  twin.setXRot(player.getXRot());
                  Vec3 cloneBehind = twin.position().subtract(lookVec.scale(0.5));
                  level.sendParticles(ParticleTypes.CLOUD, twin.getX(), twin.getY() + 0.1, twin.getZ(), 2, 0.2, 0.05, 0.2, 0.02);
                  level.sendParticles(ParticleTypes.CLOUD, cloneBehind.x, cloneBehind.y + 0.5, cloneBehind.z, 2, 0.15, 0.1, 0.15, 0.02);
               }
            }
         }

         if (tag.getBoolean("PrimalFuryActive") && entity instanceof Player player && entity.tickCount % 20 == 0) {
            this.addMasteryPoint(instance, entity);
         }

         if (tag.getBoolean("TwinBerserk")) {
            int berserkTimer = tag.getInt("TwinBerserkTimer");
            if (berserkTimer > 0) {
               tag.putInt("TwinBerserkTimer", berserkTimer - 1);
               if (entity.tickCount % 20 == 0 && entity instanceof Player player && entity.level() instanceof ServerLevel level) {
                  this.updateBerserkCloneTargets(player, level);
                  instance.markDirty();
               }
            } else {
               this.deactivateTwinBerserk(instance, entity);
            }
         }

         if (tag.getBoolean("IsDisguised") || tag.getBoolean("IsHidden")) {
            int whiteLieTimer = tag.getInt("WhiteLieTimer");
            if (whiteLieTimer > 0) {
               tag.putInt("WhiteLieTimer", whiteLieTimer - 1);
               if (whiteLieTimer % 20 == 0) {
                  instance.markDirty();
               }
            } else {
               this.deactivateWhiteLie(instance, entity);
            }
         }
      }
   }

   private void handleCelestialTwin(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         Level level = entity.level();
         if (tag.contains("CelestialTwinUUID") && level instanceof ServerLevel serverLevel) {
            UUID twinUUID = tag.getUUID("CelestialTwinUUID");
            Entity existingTwin = ObjectSelectionHelper.getEntityFromUUID(serverLevel, twinUUID, GreatSageSkill::isGreatSageClone);
            if (existingTwin != null && existingTwin.isAlive()) {
               if (player.isShiftKeyDown()) {
                  if (System.currentTimeMillis() < tag.getLong("TeleportCooldownEnd")) {
                     int remaining = (int)((tag.getLong("TeleportCooldownEnd") - System.currentTimeMillis()) / 1000L);
                     player.displayClientMessage(
                        Component.translatable("mortal_cultivation.skill.great_sage.teleport_cooldown", new Object[]{remaining + "s"})
                           .withStyle(ChatFormatting.YELLOW),
                        true
                     );
                     return;
                  }

                  player.teleportTo(existingTwin.getX(), existingTwin.getY(), existingTwin.getZ());
                  player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                  player.displayClientMessage(
                     Component.translatable("mortal_cultivation.skill.great_sage.teleport_to_twin").withStyle(ChatFormatting.GREEN), true
                  );
                  tag.putLong("TeleportCooldownEnd", System.currentTimeMillis() + 10000L);
                  instance.markDirty();
               } else {
                  player.displayClientMessage(Component.translatable("mortal_cultivation.skill.great_sage.twin_exists").withStyle(ChatFormatting.GOLD), true);
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
      if (consumeMagicule(player, mpCost, "mortal_cultivation.skill.great_sage.insufficient_mp")) {
         BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, player, Fluid.NONE, 5.0);
         Vec3 pos = result.getLocation();
         EntityType<CloneEntity> type = (EntityType<CloneEntity>)HumanEntityTypes.CLONE.get();
         LivingEntity twin = new CloneEntity(type, level);
         this.configureCelestialTwinOwnership(twin, player);
         this.configureCelestialTwinSkill(twin, instance);
         this.copyCelestialTwinStatsAndSkills(twin, player);
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

         AttributeInstance maxHealth = twin.getAttribute(Attributes.MAX_HEALTH);
         if (maxHealth != null) {
            maxHealth.removeModifier(TWIN_HEALTH_MODIFIER_ID);
            AttributeModifier healthModifier = new AttributeModifier(TWIN_HEALTH_MODIFIER_ID, 1.0, Operation.ADD_MULTIPLIED_TOTAL);
            maxHealth.addPermanentModifier(healthModifier);
         }

         twin.setHealth(twin.getMaxHealth());
         this.setCelestialTwinBehaviour(twin, 2);
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
         instance.setToggled(true);
         instance.markDirty();
         TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.CLOUD, 2.0);
         TensuraParticleHelper.addServerParticlesAroundSelf(twin, ParticleTypes.CLOUD, 2.0);
         level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_PREPARE_MIRROR, SoundSource.PLAYERS, 1.0F, 1.0F);
         instance.setCoolDown(720, 0);
         this.addMasteryPoint(instance, player);
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
      if (entity instanceof Player player) {
         boolean avoided = this.handleDeathAvoidance(player, instance);
         if (!avoided) {
            this.forceDiscardGreatSageClone(player, instance);
         }

         return !avoided;
      } else {
         return true;
      }
   }

   private void forceDiscardGreatSageClone(Player player, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.contains("CelestialTwinUUID")) {
         if (player.level() instanceof ServerLevel level) {
            UUID var7 = tag.getUUID("CelestialTwinUUID");
            Entity twin = ObjectSelectionHelper.getEntityFromUUID(level, var7, GreatSageSkill::isGreatSageClone);
            if (twin != null) {
               twin.discard();
            }

            tag.remove("CelestialTwinUUID");
            instance.markDirty();
         }
      }
   }

   private void configureCelestialTwinOwnership(LivingEntity twin, Player player) {
      try {
         Method tame = twin.getClass().getMethod("tame", Player.class);
         tame.invoke(twin, player);
      } catch (ReflectiveOperationException var6) {
         try {
            Method setTame = twin.getClass().getMethod("setTame", boolean.class, boolean.class);
            setTame.invoke(twin, true, true);
         } catch (ReflectiveOperationException var5) {
         }

         try {
            Method setOwnerUuid = twin.getClass().getMethod("setOwnerUUID", UUID.class);
            setOwnerUuid.invoke(twin, player.getUUID());
         } catch (ReflectiveOperationException var4) {
         }
      }
   }

   private void configureCelestialTwinSkill(LivingEntity twin, ManasSkillInstance instance) {
      try {
         Method setSkill = twin.getClass().getMethod("setSkill", ManasSkillInstance.class);
         setSkill.invoke(twin, instance);
      } catch (ReflectiveOperationException var4) {
      }
   }

   private void copyCelestialTwinStatsAndSkills(LivingEntity twin, Player player) {
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

   private void setCelestialTwinBehaviour(LivingEntity twin, int behaviour) {
      try {
         Method setBehaviour = twin.getClass().getMethod("setBehaviour", int.class);
         setBehaviour.invoke(twin, behaviour);
      } catch (ReflectiveOperationException var4) {
      }
   }

   private UUID getCelestialTwinOwnerUuid(Entity twin) {
      try {
         Method getOwnerUuid = twin.getClass().getMethod("getOwnerUUID");
         if (getOwnerUuid.invoke(twin) instanceof UUID uuid) {
            return uuid;
         }
      } catch (ReflectiveOperationException var5) {
      }

      return null;
   }

   public boolean handleDeathAvoidance(Player player, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.contains("CelestialTwinUUID")) {
         return false;
      }

      if (player.level() instanceof ServerLevel level) {
         UUID var8 = tag.getUUID("CelestialTwinUUID");
         if (ObjectSelectionHelper.getEntityFromUUID(level, var8, GreatSageSkill::isGreatSageClone) instanceof LivingEntity twinLiving
            && isGreatSageClone(twinLiving)
            && twinLiving.isAlive()) {
            player.teleportTo(twinLiving.getX(), twinLiving.getY(), twinLiving.getZ());
            player.setHealth(twinLiving.getHealth());
            twinLiving.discard();
            tag.remove("CelestialTwinUUID");
            instance.markDirty();
            TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.SMOKE, 3.0);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.displayClientMessage(Component.translatable("mortal_cultivation.skill.great_sage.death_avoided").withStyle(ChatFormatting.GOLD), false);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void applyMasteryResistances(Player player, ManasSkillInstance instance) {
      int mastery = (int)instance.getMastery();
      if (mastery >= 500) {
         player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 0, false, false, true));
      }

      if (mastery >= 1000) {
         player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1, false, false, true));
      }

      if (mastery >= 1500) {
         player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0, false, false, true));
      }

      if (mastery >= 2000) {
         player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0, false, false, true));
      }

      if (mastery >= 2500) {
         player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 3, false, false, true));
         player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0, false, false, true));
      }
   }

   private void handleDefy(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         if (consumeMagicule(player, 30000.0, "mortal_cultivation.skill.great_sage.insufficient_mp")) {
            tag.putBoolean("DefyActive", true);
            tag.putInt("DefyTimer", 200);
            tag.putFloat("DefyDamageAccumulated", 0.0F);
            instance.setToggled(true);
            instance.markDirty();
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 255, false, false, true));
            player.setInvulnerable(true);
            player.addEffect(new MobEffectInstance(severanceBladeEffect(), 200, 4, false, false, false));
            this.applyDefyToTwin(player, tag, true);
            TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.FLAME, 2.0);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 0.8F);
            instance.setCoolDown(300, 1);
            this.addMasteryPoint(instance, player);
         }
      }
   }

   private void applyDefyToTwin(Player player, CompoundTag tag, boolean activate) {
      if (player.level() instanceof ServerLevel level) {
         if (tag.contains("CelestialTwinUUID")) {
            UUID twinUUID = tag.getUUID("CelestialTwinUUID");
            if (ObjectSelectionHelper.getEntityFromUUID(level, twinUUID, GreatSageSkill::isGreatSageClone) instanceof LivingEntity clone
               && isGreatSageClone(clone)
               && clone.isAlive()) {
               if (activate) {
                  clone.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 255, false, false, true));
                  clone.setInvulnerable(true);
                  clone.addEffect(new MobEffectInstance(severanceBladeEffect(), 200, 4, false, false, false));
                  TensuraParticleHelper.addServerParticlesAroundSelf(clone, ParticleTypes.FLAME, 2.0);
               } else {
                  clone.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                  clone.removeEffect(severanceBladeEffect());
                  clone.setInvulnerable(false);
               }
            }
         }
      }
   }

   private void applyShieldToTwin(Player player, CompoundTag tag, float shieldAmount) {
      if (player.level() instanceof ServerLevel level) {
         if (tag.contains("CelestialTwinUUID")) {
            UUID twinUUID = tag.getUUID("CelestialTwinUUID");
            if (ObjectSelectionHelper.getEntityFromUUID(level, twinUUID, GreatSageSkill::isGreatSageClone) instanceof LivingEntity clone
               && isGreatSageClone(clone)
               && clone.isAlive()
               && shieldAmount > 0.0F) {
               int absorptionLevel = Math.min((int)(shieldAmount / 4.0F), 255);
               clone.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, absorptionLevel, false, true, true));
               TensuraParticleHelper.addServerParticlesAroundSelf(clone, ParticleTypes.CLOUD, 2.0);
            }
         }
      }
   }

   private void deactivateDefy(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         float accumulatedDamage = tag.getFloat("DefyDamageAccumulated");
         tag.putBoolean("DefyActive", false);
         tag.remove("DefyTimer");
         tag.remove("DefyDamageAccumulated");
         if (!tag.getBoolean("CloudWalkerActive")
            && !tag.getBoolean("PrimalFuryActive")
            && !tag.getBoolean("TwinBerserk")
            && !tag.getBoolean("IsDisguised")
            && !tag.getBoolean("IsHidden")
            && !tag.contains("CelestialTwinUUID")) {
            instance.setToggled(false);
         }

         instance.markDirty();
         player.setInvulnerable(false);
         player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
         player.removeEffect(severanceBladeEffect());
         this.applyDefyToTwin(player, tag, false);
         float shieldAmount = accumulatedDamage * 1.5F;
         if (shieldAmount > 0.0F) {
            int absorptionLevel = Math.min((int)(shieldAmount / 4.0F), 255);
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, absorptionLevel, false, true, true));
            this.applyShieldToTwin(player, tag, shieldAmount);
         }

         TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.CLOUD, 2.0);
      }
   }

   private void handleCloudWalker(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         double initialCost = getBaseMagicule(player) * 0.01;
         if (consumeMagicule(player, initialCost, "mortal_cultivation.skill.great_sage.insufficient_mp")) {
            tag.putBoolean("CloudWalkerActive", true);
            instance.setToggled(true);
            instance.markDirty();
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, Integer.MAX_VALUE, 0, false, false, true));
            player.getAbilities().mayfly = true;
            player.getAbilities().flying = true;
            player.onUpdateAbilities();
            AttributeInstance speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) {
               speedAttr.removeModifier(CLOUD_WALKER_SPEED_ID);
               speedAttr.addTransientModifier(new AttributeModifier(CLOUD_WALKER_SPEED_ID, 4.0, Operation.ADD_MULTIPLIED_TOTAL));
            }

            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 4, false, false, true));
            boolean isPrimalFuryActive = tag.getBoolean("PrimalFuryActive");
            player.setInvulnerable(true);
            if (!isPrimalFuryActive) {
               player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, Integer.MAX_VALUE, 255, false, false, true));
            }

            this.applyCloudWalkerToTwin(player, tag, true, isPrimalFuryActive);
            TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.CLOUD, 3.0);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.addMasteryPoint(instance, player);
         }
      }
   }

   private void applyCloudWalkerToTwin(Player player, CompoundTag tag, boolean activate, boolean isPrimalFuryActive) {
      if (player.level() instanceof ServerLevel level) {
         if (tag.contains("CelestialTwinUUID")) {
            UUID twinUUID = tag.getUUID("CelestialTwinUUID");
            if (ObjectSelectionHelper.getEntityFromUUID(level, twinUUID, GreatSageSkill::isGreatSageClone) instanceof LivingEntity clone
               && isGreatSageClone(clone)
               && clone.isAlive()) {
               if (activate) {
                  clone.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, Integer.MAX_VALUE, 0, false, false, true));
                  clone.setInvulnerable(true);
                  if (!isPrimalFuryActive) {
                     clone.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, Integer.MAX_VALUE, 255, false, false, true));
                  }

                  if (clone instanceof Mob mob) {
                     mob.setNoAi(true);
                  }

                  TensuraParticleHelper.addServerParticlesAroundSelf(clone, ParticleTypes.CLOUD, 2.0);
               } else {
                  clone.removeEffect(MobEffects.INVISIBILITY);
                  clone.removeEffect(MobEffects.WEAKNESS);
                  clone.setInvulnerable(false);
                  if (clone instanceof Mob mob) {
                     mob.setNoAi(false);
                  }

                  TensuraParticleHelper.addServerParticlesAroundSelf(clone, ParticleTypes.CLOUD, 2.0);
               }
            }
         }
      }
   }

   private void deactivateCloudWalker(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putBoolean("CloudWalkerActive", false);
         if (!tag.getBoolean("PrimalFuryActive")
            && !tag.getBoolean("DefyActive")
            && !tag.getBoolean("TwinBerserk")
            && !tag.getBoolean("IsDisguised")
            && !tag.getBoolean("IsHidden")
            && !tag.contains("CelestialTwinUUID")) {
            instance.setToggled(false);
         }

         instance.markDirty();
         player.removeEffect(MobEffects.INVISIBILITY);
         player.removeEffect(MobEffects.WEAKNESS);
         player.removeEffect(MobEffects.MOVEMENT_SPEED);
         AttributeInstance speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
         if (speedAttr != null) {
            speedAttr.removeModifier(CLOUD_WALKER_SPEED_ID);
         }

         if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
         }

         player.setInvulnerable(false);
         this.applyCloudWalkerToTwin(player, tag, false, false);
         instance.setCoolDown(50, 2);
         TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.CLOUD, 2.0);
      }
   }

   private void handlePrimalFury(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         double initialCost = getBaseMagicule(player) * 0.01;
         if (consumeMagicule(player, initialCost, "mortal_cultivation.skill.great_sage.insufficient_mp")) {
            tag.putBoolean("PrimalFuryActive", true);
            instance.setToggled(true);
            instance.markDirty();
            ItemStack originalMainHand = player.getMainHandItem().copy();
            tag.put("OriginalMainHand", originalMainHand.save(player.registryAccess(), new CompoundTag()));
            ItemStack jinguBang = new ItemStack((ItemLike)CultivationItems.RUYI_JINGU_BANG.get());
            player.setItemInHand(InteractionHand.MAIN_HAND, jinguBang);
            this.applyPrimalFuryBuffs(player);
            TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.FLAME, 3.0);
            TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.LAVA, 2.0);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 1.0F, 1.5F);
            instance.setCoolDown(10000, 3);
            this.addMasteryPoint(instance, player);
         }
      }
   }

   private void applyPrimalFuryBuffs(Player player) {
      AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attackDamage != null) {
         attackDamage.removeModifier(PRIMAL_FURY_DAMAGE_ID);
         attackDamage.addTransientModifier(new AttributeModifier(PRIMAL_FURY_DAMAGE_ID, 1.0, Operation.ADD_MULTIPLIED_TOTAL));
      }

      AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
      if (maxHealth != null) {
         maxHealth.removeModifier(PRIMAL_FURY_HEALTH_ID);
         maxHealth.addTransientModifier(new AttributeModifier(PRIMAL_FURY_HEALTH_ID, 1.0, Operation.ADD_MULTIPLIED_TOTAL));
      }

      AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(PRIMAL_FURY_SPEED_ID);
         speed.addTransientModifier(new AttributeModifier(PRIMAL_FURY_SPEED_ID, 1.0, Operation.ADD_MULTIPLIED_TOTAL));
      }

      player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 1, false, true, true));
      player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 1, false, true, true));
   }

   private void removePrimalFuryBuffs(Player player) {
      AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attackDamage != null) {
         attackDamage.removeModifier(PRIMAL_FURY_DAMAGE_ID);
      }

      AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
      if (maxHealth != null) {
         maxHealth.removeModifier(PRIMAL_FURY_HEALTH_ID);
      }

      AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(PRIMAL_FURY_SPEED_ID);
      }

      player.removeEffect(MobEffects.DAMAGE_BOOST);
      player.removeEffect(MobEffects.MOVEMENT_SPEED);
   }

   private void deactivatePrimalFury(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putBoolean("PrimalFuryActive", false);
         tag.remove("PrimalFuryTimer");
         if (!tag.getBoolean("CloudWalkerActive")
            && !tag.getBoolean("DefyActive")
            && !tag.getBoolean("TwinBerserk")
            && !tag.getBoolean("IsDisguised")
            && !tag.getBoolean("IsHidden")
            && !tag.contains("CelestialTwinUUID")) {
            instance.setToggled(false);
         }

         instance.markDirty();
         this.removePrimalFuryBuffs(player);
         this.confiscateJinguBang(player);
         if (tag.contains("OriginalMainHand")) {
            ItemStack originalItem = ItemStack.parseOptional(player.registryAccess(), tag.getCompound("OriginalMainHand"));
            if (player.getMainHandItem().isEmpty()) {
               player.setItemInHand(InteractionHand.MAIN_HAND, originalItem);
            } else if (!player.getInventory().add(originalItem)) {
               player.drop(originalItem, false);
            }

            tag.remove("OriginalMainHand");
         }

         TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.SMOKE, 2.0);
      }
   }

   private void confiscateJinguBang(Player player) {
      if (player.getMainHandItem().getItem() == CultivationItems.RUYI_JINGU_BANG.get()) {
         player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
      }

      if (player.getOffhandItem().getItem() == CultivationItems.RUYI_JINGU_BANG.get()) {
         player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
      }

      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack stack = player.getInventory().getItem(i);
         if (stack.getItem() == CultivationItems.RUYI_JINGU_BANG.get()) {
            player.getInventory().setItem(i, ItemStack.EMPTY);
         }
      }
   }

   public static void resetPlayerCooldowns(Player player) {
      SkillAPI.getSkillsFrom(player).getLearnedSkills().forEach(instance -> {
         if (instance.getSkill() instanceof GreatSageSkill) {
            for (int mode = 0; mode < 5; mode++) {
               instance.setCoolDown(0, mode);
            }

            CompoundTag tag = instance.getOrCreateTag();
            tag.remove("TeleportCooldownEnd");
            tag.remove("BerserkCooldownEnd");
            instance.markDirty();
         }
      });
   }

   public static boolean isCloudWalkerActive(Player player) {
      return SkillAPI.getSkillsFrom(player)
         .getLearnedSkills()
         .stream()
         .filter(instance -> instance.getSkill() instanceof GreatSageSkill)
         .anyMatch(instance -> instance.getOrCreateTag().getBoolean("CloudWalkerActive"));
   }

   @SubscribeEvent
   public static void onPlayerRespawn(PlayerRespawnEvent event) {
      Player player = event.getEntity();
      if (player.level() instanceof ServerLevel level) {
         SkillAPI.getSkillsFrom(player).getLearnedSkills().forEach(instance -> {
            if (instance.getSkill() instanceof GreatSageSkill) {
               CompoundTag tag = instance.getOrCreateTag();
               if (tag.contains("CelestialTwinUUID")) {
                  UUID twinUUID = tag.getUUID("CelestialTwinUUID");
                  Entity twin = ObjectSelectionHelper.getEntityFromUUID(level, twinUUID, GreatSageSkill::isGreatSageClone);
                  if (twin != null) {
                     twin.discard();
                  }

                  tag.remove("CelestialTwinUUID");
               }

               tag.putBoolean("PrimalFuryActive", false);
               tag.putBoolean("CloudWalkerActive", false);
               tag.putBoolean("DefyActive", false);
               tag.putBoolean("TwinBerserk", false);
               tag.putBoolean("IsDisguised", false);
               tag.putBoolean("IsHidden", false);
               instance.setToggled(false);
               instance.markDirty();
            }
         });
      }
   }

   public void handleShiftRightClick(ManasSkillInstance instance, Player player, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      switch (mode) {
         case 3:
            this.handleCloudMerge(instance, player);
            break;
         case 4:
            this.handleTwinBerserk(instance, player);
      }
   }

   private void handleWhiteLie(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("IsDisguised") || tag.getBoolean("IsHidden")) {
            this.deactivateWhiteLie(instance, player);
         } else if (consumeMagicule(player, 15000.0, "mortal_cultivation.skill.great_sage.insufficient_mp")) {
            Vec3 eyePos = player.getEyePosition();
            Vec3 lookVec = player.getLookAngle();
            Vec3 endPos = eyePos.add(lookVec.scale(10.0));
            AABB searchBox = new AABB(eyePos, endPos).inflate(1.0);
            List<Player> nearbyPlayers = player.level().getEntitiesOfClass(Player.class, searchBox, p -> p != player && p.isAlive());
            if (nearbyPlayers.isEmpty()) {
               this.activateHiddenMode(instance, player, tag);
            } else {
               Player targetPlayer = nearbyPlayers.get(0);
               this.activateDisguiseMode(instance, player, targetPlayer, tag);
            }

            instance.setCoolDown(300, 4);
            this.addMasteryPoint(instance, player);
         }
      }
   }

   private void activateHiddenMode(ManasSkillInstance instance, Player player, CompoundTag tag) {
      tag.putBoolean("IsHidden", true);
      tag.putInt("WhiteLieTimer", 6000);
      instance.setToggled(true);
      instance.markDirty();
      hiddenPlayers.add(player.getUUID());
      player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 6000, 0, false, false, true));
      player.addEffect(new MobEffectInstance(presenceConcealmentEffect(), 6000, 2, false, false, true));
      if (player instanceof ServerPlayer serverPlayer) {
         this.hidePlayerFromTabList(serverPlayer);
      }

      TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.SMOKE, 3.0);
      player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 0.5F);
      player.displayClientMessage(Component.translatable("mortal_cultivation.skill.great_sage.white_lie_hidden").withStyle(ChatFormatting.GRAY), false);
   }

   private void activateDisguiseMode(ManasSkillInstance instance, Player player, Player targetPlayer, CompoundTag tag) {
      tag.putString("OriginalName", player.getGameProfile().getName());
      String fakeName = targetPlayer.getGameProfile().getName() + "_Fake";
      tag.putString("DisguisedAs", fakeName);
      tag.putBoolean("IsDisguised", true);
      tag.putInt("WhiteLieTimer", 6000);
      instance.setToggled(true);
      instance.markDirty();
      this.stealSkillFromPlayer(instance, player, targetPlayer, tag);
      player.addEffect(new MobEffectInstance(presenceConcealmentEffect(), 6000, 2, false, false, true));
      if (player instanceof ServerPlayer serverPlayer) {
         this.setPlayerDisplayName(serverPlayer, fakeName);
      }

      player.displayClientMessage(
         Component.translatable("mortal_cultivation.skill.great_sage.disguised_as", new Object[]{fakeName}).withStyle(ChatFormatting.AQUA), false
      );
      TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.ENCHANT, 2.0);
      player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_PREPARE_MIRROR, SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   private void stealSkillFromPlayer(ManasSkillInstance instance, Player player, Player targetPlayer, CompoundTag tag) {
      SkillStorage playerSkills = SkillAPI.getSkillsFrom(player);
      SkillStorage targetSkills = SkillAPI.getSkillsFrom(targetPlayer);
      List<ManasSkillInstance> targetSkillList = new ArrayList<>();
      targetSkills.getLearnedSkills().forEach(targetSkillList::add);
      Set<ResourceLocation> mySkillIds = new HashSet<>();
      playerSkills.getLearnedSkills().forEach(skillInstance -> {
         if (skillInstance.getSkill().getRegistryName() != null) {
            mySkillIds.add(skillInstance.getSkill().getRegistryName());
         }
      });
      ManasSkillInstance stolenSkill = null;

      for (ManasSkillInstance targetSkillInstance : targetSkillList) {
         ResourceLocation skillId = targetSkillInstance.getSkill().getRegistryName();
         if (skillId != null && !mySkillIds.contains(skillId)) {
            stolenSkill = targetSkillInstance;
            break;
         }
      }

      if (stolenSkill != null) {
         ManasSkillInstance copiedSkill = new TensuraSkillInstance(stolenSkill.getSkill());
         copiedSkill.setToggled(false);
         playerSkills.learnSkill(copiedSkill);
         ResourceLocation stolenSkillId = stolenSkill.getSkill().getRegistryName();
         if (stolenSkillId != null) {
            tag.putString("StolenSkillId", stolenSkillId.toString());
         }

         Component skillName = stolenSkill.getSkill().getName();
         if (skillName == null) {
            skillName = Component.literal(stolenSkillId != null ? stolenSkillId.getPath() : "Unknown");
         }

         player.displayClientMessage(
            Component.translatable("mortal_cultivation.skill.great_sage.skill_stolen", new Object[]{skillName}).withStyle(ChatFormatting.LIGHT_PURPLE), false
         );
      } else {
         player.displayClientMessage(Component.translatable("mortal_cultivation.skill.great_sage.no_skill_to_steal").withStyle(ChatFormatting.GRAY), false);
      }
   }

   private void hidePlayerFromTabList(ServerPlayer player) {
      ServerScoreboard scoreboard = player.getServer().getScoreboard();
      String teamName = "hidden_" + player.getUUID().toString().substring(0, 8);
      PlayerTeam team = scoreboard.getPlayerTeam(player.getScoreboardName());
      if (team == null) {
         team = scoreboard.addPlayerTeam(teamName);
      }

      team.setNameTagVisibility(Visibility.NEVER);
      team.setSeeFriendlyInvisibles(false);
      scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
   }

   private void setPlayerDisplayName(ServerPlayer player, String displayName) {
      ServerScoreboard scoreboard = player.getServer().getScoreboard();
      String teamName = "disguise_" + player.getUUID().toString().substring(0, 8);
      PlayerTeam oldTeam = scoreboard.getPlayerTeam(player.getScoreboardName());
      if (oldTeam != null) {
         scoreboard.removePlayerFromTeam(player.getScoreboardName(), oldTeam);
      }

      PlayerTeam team = scoreboard.getPlayerTeam(teamName);
      if (team == null) {
         team = scoreboard.addPlayerTeam(teamName);
      }

      team.setPlayerPrefix(Component.literal(displayName + " "));
      team.setNameTagVisibility(Visibility.ALWAYS);
      scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
   }

   private void restorePlayerInTabList(ServerPlayer player) {
      ServerScoreboard scoreboard = player.getServer().getScoreboard();
      String hiddenTeamName = "hidden_" + player.getUUID().toString().substring(0, 8);
      PlayerTeam hiddenTeam = scoreboard.getPlayerTeam(hiddenTeamName);
      if (hiddenTeam != null) {
         scoreboard.removePlayerFromTeam(player.getScoreboardName(), hiddenTeam);
         scoreboard.removePlayerTeam(hiddenTeam);
      }

      String disguiseTeamName = "disguise_" + player.getUUID().toString().substring(0, 8);
      PlayerTeam disguiseTeam = scoreboard.getPlayerTeam(disguiseTeamName);
      if (disguiseTeam != null) {
         scoreboard.removePlayerFromTeam(player.getScoreboardName(), disguiseTeam);
         scoreboard.removePlayerTeam(disguiseTeam);
      }
   }

   private void deactivateWhiteLie(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.contains("StolenSkillId")) {
            String skillId = tag.getString("StolenSkillId");
            ResourceLocation skillLocation = ResourceLocation.parse(skillId);
            SkillStorage playerSkills = SkillAPI.getSkillsFrom(player);
            List<ManasSkillInstance> toRemove = new ArrayList<>();
            playerSkills.getLearnedSkills().forEach(skillInstance -> {
               if (skillInstance.getSkill().getRegistryName().equals(skillLocation)) {
                  toRemove.add(skillInstance);
               }
            });

            for (ManasSkillInstance skillToRemove : toRemove) {
               playerSkills.forgetSkill(skillToRemove);
            }

            tag.remove("StolenSkillId");
         }

         hiddenPlayers.remove(player.getUUID());
         tag.putBoolean("IsDisguised", false);
         tag.putBoolean("IsHidden", false);
         tag.remove("WhiteLieTimer");
         tag.remove("OriginalName");
         tag.remove("DisguisedAs");
         if (!tag.getBoolean("CloudWalkerActive")
            && !tag.getBoolean("PrimalFuryActive")
            && !tag.getBoolean("DefyActive")
            && !tag.getBoolean("TwinBerserk")
            && !tag.contains("CelestialTwinUUID")) {
            instance.setToggled(false);
         }

         instance.markDirty();
         player.removeEffect(MobEffects.INVISIBILITY);
         player.removeEffect(presenceConcealmentEffect());
         if (player instanceof ServerPlayer serverPlayer) {
            this.restorePlayerInTabList(serverPlayer);
         }

         TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.SMOKE, 2.0);
      }
   }

   public static boolean isPlayerHidden(UUID playerUUID) {
      return hiddenPlayers.contains(playerUUID);
   }

   private void handleCloudMerge(ManasSkillInstance instance, Player player) {
      CompoundTag tag = instance.getOrCreateTag();
      double mpCost = getBaseMagicule(player) * 0.02;
      if (consumeMagicule(player, mpCost, "mortal_cultivation.skill.great_sage.insufficient_mp")) {
         player.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 3, false, false, true));
         player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 600, 0, false, false, true));
         player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0, false, false, true));
         TensuraParticleHelper.addServerParticlesAroundSelf(player, ParticleTypes.CLOUD, 3.0);
         this.addMasteryPoint(instance, player);
      }
   }

   private void handleTwinBerserk(ManasSkillInstance instance, Player player) {
      if (player.level() instanceof ServerLevel level) {
         CompoundTag var13 = instance.getOrCreateTag();
         if (System.currentTimeMillis() < var13.getLong("BerserkCooldownEnd")) {
            int remaining = (int)((var13.getLong("BerserkCooldownEnd") - System.currentTimeMillis()) / 1000L);
            player.displayClientMessage(
               Component.translatable(
                     "mortal_cultivation.skill.great_sage.mode_cooldown",
                     new Object[]{Component.translatable("mortal_cultivation.skill.mode.great_sage.twin_berserk"), remaining + "s"}
                  )
                  .withStyle(ChatFormatting.YELLOW),
               true
            );
         } else {
            UUID playerUUID = player.getUUID();
            AABB searchBox = player.getBoundingBox().inflate(20.0);
            List<LivingEntity> myClones = level.getEntitiesOfClass(
               LivingEntity.class,
               searchBox,
               clonex -> clonex.isAlive() && isGreatSageClone(clonex) && playerUUID.equals(this.getCelestialTwinOwnerUuid(clonex))
            );
            if (myClones.isEmpty()) {
               player.displayClientMessage(
                  Component.translatable("mortal_cultivation.skill.great_sage.no_twin_for_berserk").withStyle(ChatFormatting.RED), true
               );
               instance.getOrCreateTag().putLong("BerserkCooldownEnd", System.currentTimeMillis() + 30000L);
               instance.markDirty();
            } else {
               int berserkCount = 0;

               for (LivingEntity clone : myClones) {
                  AttributeInstance attackDamage = clone.getAttribute(Attributes.ATTACK_DAMAGE);
                  if (attackDamage != null && attackDamage.getModifier(BERSERK_DAMAGE_ID) == null) {
                     attackDamage.addPermanentModifier(new AttributeModifier(BERSERK_DAMAGE_ID, 1.0, Operation.ADD_MULTIPLIED_TOTAL));
                  }

                  LivingEntity target = this.findBerserkTarget(clone, level, playerUUID);
                  if (target != null && clone instanceof Mob mob) {
                     mob.setTarget(target);
                  }

                  TensuraParticleHelper.addServerParticlesAroundSelf(clone, ParticleTypes.FLAME, 3.0);
                  level.playSound(null, clone.getX(), clone.getY(), clone.getZ(), SoundEvents.RAVAGER_ROAR, SoundSource.HOSTILE, 1.0F, 1.0F);
                  berserkCount++;
               }

               CompoundTag tag = instance.getOrCreateTag();
               tag.putBoolean("TwinBerserk", true);
               tag.putInt("TwinBerserkTimer", 600);
               tag.putLong("BerserkCooldownEnd", System.currentTimeMillis() + 1200000L);
               instance.setToggled(true);
               instance.markDirty();
               this.addMasteryPoint(instance, player);
            }
         }
      }
   }

   private LivingEntity findBerserkTarget(LivingEntity clone, ServerLevel level, UUID ownerUUID) {
      AABB targetBox = clone.getBoundingBox().inflate(10.0);
      List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(
         LivingEntity.class,
         targetBox,
         e -> e != clone && !(e instanceof Player) && !isGreatSageClone(e) && !this.isPlayerTamedAnimal(e, ownerUUID) && e.isAlive()
      );
      if (nearbyEntities.isEmpty()) {
         return null;
      }

      LivingEntity closest = null;
      double closestDist = Double.MAX_VALUE;

      for (LivingEntity entity : nearbyEntities) {
         double dist = clone.distanceToSqr(entity);
         if (dist < closestDist) {
            closestDist = dist;
            closest = entity;
         }
      }

      return closest;
   }

   private boolean isPlayerTamedAnimal(LivingEntity entity, UUID ownerUUID) {
      if (!(entity instanceof TamableAnimal tamable)) {
         return false;
      } else {
         UUID tamedOwner = tamable.getOwnerUUID();
         return tamedOwner != null && tamedOwner.equals(ownerUUID);
      }
   }

   private void updateBerserkCloneTargets(Player player, ServerLevel level) {
      UUID playerUUID = player.getUUID();
      AABB searchBox = player.getBoundingBox().inflate(50.0);

      for (LivingEntity clone : level.getEntitiesOfClass(
         LivingEntity.class, searchBox, clonex -> clonex.isAlive() && isGreatSageClone(clonex) && playerUUID.equals(this.getCelestialTwinOwnerUuid(clonex))
      )) {
         AttributeInstance attackDamage = clone.getAttribute(Attributes.ATTACK_DAMAGE);
         if (attackDamage != null && attackDamage.getModifier(BERSERK_DAMAGE_ID) != null) {
            LivingEntity currentTarget = clone instanceof Mob mob ? mob.getTarget() : null;
            if ((currentTarget == null || !currentTarget.isAlive() || clone.distanceToSqr(currentTarget) > 100.0) && clone instanceof Mob mob) {
               LivingEntity newTarget = this.findBerserkTarget(clone, level, playerUUID);
               if (newTarget != null) {
                  mob.setTarget(newTarget);
               }
            }
         }
      }
   }

   private void deactivateTwinBerserk(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         if (player.level() instanceof ServerLevel level) {
            CompoundTag tag = instance.getOrCreateTag();
            tag.putBoolean("TwinBerserk", false);
            tag.remove("TwinBerserkTimer");
            if (!tag.getBoolean("CloudWalkerActive")
               && !tag.getBoolean("PrimalFuryActive")
               && !tag.getBoolean("DefyActive")
               && !tag.getBoolean("IsDisguised")
               && !tag.getBoolean("IsHidden")
               && !tag.contains("CelestialTwinUUID")) {
               instance.setToggled(false);
            }

            instance.markDirty();
            UUID playerUUID = player.getUUID();
            AABB searchBox = player.getBoundingBox().inflate(50.0);

            for (LivingEntity clone : level.getEntitiesOfClass(
               LivingEntity.class,
               searchBox,
               clonex -> clonex.isAlive() && isGreatSageClone(clonex) && playerUUID.equals(this.getCelestialTwinOwnerUuid(clonex))
            )) {
               AttributeInstance attackDamage = clone.getAttribute(Attributes.ATTACK_DAMAGE);
               if (attackDamage != null) {
                  attackDamage.removeModifier(BERSERK_DAMAGE_ID);
               }

               if (clone instanceof Mob mob) {
                  mob.setTarget(null);
               }

               TensuraParticleHelper.addServerParticlesAroundSelf(clone, ParticleTypes.SMOKE, 2.0);
            }

            player.displayClientMessage(Component.translatable("mortal_cultivation.skill.great_sage.twin_berserk_off").withStyle(ChatFormatting.GRAY), false);
         }
      }
   }

   public void spreadDamageInPrimalFury(Player player, float damage, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.getBoolean("PrimalFuryActive") && tag.getBoolean("DefyActive")) {
         if (player.level() instanceof ServerLevel level) {
            AABB var11 = player.getBoundingBox().inflate(10.0);
            List nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, var11, e -> e != player && e.isAlive());
            float spreadDamage = damage * 2.0F;

            for (LivingEntity entity : nearbyEntities) {
               entity.hurt(level.damageSources().magic(), spreadDamage);
            }

            if (!nearbyEntities.isEmpty()) {
               player.displayClientMessage(
                  Component.translatable("mortal_cultivation.skill.great_sage.damage_spread", new Object[]{nearbyEntities.size()})
                     .withStyle(ChatFormatting.RED),
                  true
               );
            }
         }
      }
   }
}
