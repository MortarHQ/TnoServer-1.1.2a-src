package com.github.wal_bos.moreskills.handler.ronova;

import com.github.wal_bos.moreskills.ability.skill.shades.ronova.RonovaConfigAccess;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public final class RonovaPlayerHandler {
   public static final ResourceLocation HEALTH_GROWTH = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "ronova_health_growth");
   public static final ResourceLocation DAMAGE_GROWTH = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "ronova_damage_growth");
   public static final String KILLS = "RonovaKills";
   public static final String EXTRA_LIVES = "RonovaExtraLives";
   public static final String SPIRITUAL_FORCE_ACTIVE = "RonovaSpiritualForceActive";
   public static final String SPIRITUAL_FORCE_HIDDEN = "RonovaTabHidden";
   private static Field allowsListingField;

   private RonovaPlayerHandler() {
   }

   public static void onRonovaLearn(ManasSkillInstance instance, LivingEntity entity) {
      MoreSkillsConfigs.RonovaConfig cfg = RonovaConfigAccess.cfg();
      if ((Boolean)cfg.learnShadowMotion.get()) {
         SkillHelper.learnSkill(entity, (ManasSkill)ExtraSkills.SHADOW_MOTION.get(), instance.getRemoveTime());
      }

      if ((Boolean)cfg.learnSpiritualNullification.get()) {
         SkillHelper.learnSkill(entity, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get(), instance.getRemoveTime());
      }

      if ((Boolean)cfg.learnAbnormalNullification.get()) {
         SkillHelper.learnSkill(entity, (ManasSkill)ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.get(), instance.getRemoveTime());
      }

      if ((Boolean)cfg.learnSpatialNullification.get()) {
         SkillHelper.learnSkill(entity, (ManasSkill)ResistanceSkills.SPATIAL_ATTACK_NULLIFICATION.get(), instance.getRemoveTime());
      }

      if ((Boolean)cfg.learnGravityNullification.get()) {
         SkillHelper.learnSkill(entity, (ManasSkill)ResistanceSkills.GRAVITY_ATTACK_NULLIFICATION.get(), instance.getRemoveTime());
      }

      if (SkillUtils.isSkillToggled(instance)) {
         onRonovaToggleOn(instance, entity);
      }
   }

   public static void onRonovaForget(ManasSkillInstance instance, LivingEntity entity) {
      onRonovaToggleOff(instance, entity);
   }

   public static void onRonovaToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide() && (Boolean)RonovaConfigAccess.cfg().silentDeathEnabled.get()) {
         entity.setSilent(true);
      }

      applyGrowth(instance, entity);
   }

   public static void onRonovaToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      spiritualForceOff(instance, entity);
      removeGrowth(entity);
      entity.setSilent(false);
      instance.getOrCreateTag().putBoolean("RonovaSalvationLordActive", false);
      instance.getOrCreateTag().putBoolean("RonovaDeathFieldCompleted", false);
   }

   public static void onRonovaSkillTick(ManasSkillInstance instance, LivingEntity entity) {
      if (SkillUtils.isSkillToggled(instance)) {
         if (!entity.level().isClientSide() && (Boolean)RonovaConfigAccess.cfg().silentDeathEnabled.get()) {
            entity.setSilent(true);
         }

         applyGrowth(instance, entity);
         spiritualForceTick(instance, entity);
      }
   }

   public static void toggleSpiritualForce(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.getOrCreateTag().getBoolean("RonovaSpiritualForceActive")) {
         spiritualForceOff(instance, entity);
      } else {
         spiritualForceOn(instance, entity);
      }
   }

   public static void addKill(ManasSkillInstance instance, LivingEntity entity) {
      int kills = instance.getOrCreateTag().getInt("RonovaKills") + 1;
      instance.getOrCreateTag().putInt("RonovaKills", Math.min(kills, (Integer)RonovaConfigAccess.cfg().killGrowthMaxKills.get()));
      applyGrowth(instance, entity);
   }

   public static boolean hasRonova(LivingEntity entity) {
      return SkillUtils.hasSkill(entity, (ManasSkill)ShadeSkills.RONOVA.get());
   }

   public static void onLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         player.setSilent(false);
         SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)ShadeSkills.RONOVA.get()).ifPresent(instance -> {
            instance.getOrCreateTag().putBoolean("RonovaSpiritualForceActive", false);
            instance.getOrCreateTag().putBoolean("RonovaTabHidden", false);
         });
      }
   }

   public static void onLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)ShadeSkills.RONOVA.get()).ifPresent(instance -> {
            if (instance.getOrCreateTag().getBoolean("RonovaTabHidden")) {
               sendFakeJoinToAll(player);
            }

            instance.getOrCreateTag().putBoolean("RonovaSpiritualForceActive", false);
            instance.getOrCreateTag().putBoolean("RonovaTabHidden", false);
         });
      }
   }

   public static void onTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)ShadeSkills.RONOVA.get()).ifPresent(instance -> {
            if (SkillUtils.isSkillToggled(instance)) {
               onRonovaSkillTick(instance, player);
            }
         });
         if (player.tickCount % 10 == 0 && player.level() instanceof ServerLevel level) {
            RonovaSoulHandler.tick(level);
         }
      }
   }

   private static void applyGrowth(ManasSkillInstance instance, LivingEntity entity) {
      MoreSkillsConfigs.RonovaConfig cfg = RonovaConfigAccess.cfg();
      int kills = Math.min(instance.getOrCreateTag().getInt("RonovaKills"), (Integer)cfg.killGrowthMaxKills.get());
      double hp = kills * (Double)cfg.healthPerKill.get();
      double dmg = kills * (Double)cfg.damagePerKill.get();
      AttributeInstance maxHealth = entity.getAttribute(Attributes.MAX_HEALTH);
      if (maxHealth != null) {
         maxHealth.addOrReplacePermanentModifier(new AttributeModifier(HEALTH_GROWTH, hp, Operation.ADD_VALUE));
      }

      AttributeInstance attack = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attack != null) {
         attack.addOrReplacePermanentModifier(new AttributeModifier(DAMAGE_GROWTH, dmg, Operation.ADD_VALUE));
      }
   }

   private static void removeGrowth(LivingEntity entity) {
      AttributeInstance maxHealth = entity.getAttribute(Attributes.MAX_HEALTH);
      if (maxHealth != null) {
         maxHealth.removeModifier(HEALTH_GROWTH);
      }

      AttributeInstance attack = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attack != null) {
         attack.removeModifier(DAMAGE_GROWTH);
      }
   }

   private static void spiritualForceOn(ManasSkillInstance instance, LivingEntity entity) {
      instance.getOrCreateTag().putBoolean("RonovaSpiritualForceActive", true);
      spiritualForceApply(entity);
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.8F, 0.7F);
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("skill.tensuramoreskills.ronova.spiritual_force_on").withStyle(ChatFormatting.DARK_GRAY), true);
      }

      if (entity instanceof ServerPlayer player) {
         if ((Boolean)RonovaConfigAccess.cfg().spiritualForceFakeLeave.get()) {
            if (!instance.getOrCreateTag().getBoolean("RonovaTabHidden")) {
               sendFakeLeaveToAll(player);
               instance.getOrCreateTag().putBoolean("RonovaTabHidden", true);
            }
         }
      }
   }

   private static void spiritualForceTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.getOrCreateTag().getBoolean("RonovaSpiritualForceActive")) {
         if (entity.tickCount % 5 == 0) {
            spiritualForceApply(entity);
         }
      }
   }

   private static void spiritualForceApply(LivingEntity entity) {
      int duration = Math.max(80, (Integer)RonovaConfigAccess.cfg().spiritualForceEffectTicks.get());
      entity.setInvisible(true);
      entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, duration, 0, false, false, false));
      entity.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT),
            duration,
            (Integer)RonovaConfigAccess.cfg().spiritualForceAmplifier.get(),
            false,
            false,
            true
         )
      );
   }

   private static void spiritualForceOff(ManasSkillInstance instance, LivingEntity entity) {
      instance.getOrCreateTag().putBoolean("RonovaSpiritualForceActive", false);
      if (entity instanceof ServerPlayer player && instance.getOrCreateTag().getBoolean("RonovaTabHidden")) {
         sendFakeJoinToAll(player);
         instance.getOrCreateTag().putBoolean("RonovaTabHidden", false);
      }

      entity.setInvisible(false);
      entity.removeEffect(MobEffects.INVISIBILITY);
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT));
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("skill.tensuramoreskills.ronova.spiritual_force_off").withStyle(ChatFormatting.GRAY), true);
      }
   }

   private static void sendFakeLeaveToAll(ServerPlayer player) {
      Component message = Component.translatable("multiplayer.player.left", new Object[]{Component.literal(player.getGameProfile().getName())})
         .withStyle(ChatFormatting.YELLOW);
      ClientboundPlayerInfoUpdatePacket packet = listedPacket(player, false);

      for (ServerPlayer other : player.server.getPlayerList().getPlayers()) {
         if (other != player) {
            other.connection.send(packet);
            other.sendSystemMessage(message);
         }
      }
   }

   private static void sendFakeJoinToAll(ServerPlayer player) {
      Component message = Component.translatable("multiplayer.player.joined", new Object[]{Component.literal(player.getGameProfile().getName())})
         .withStyle(ChatFormatting.YELLOW);
      ClientboundPlayerInfoUpdatePacket packet = listedPacket(player, true);

      for (ServerPlayer other : player.server.getPlayerList().getPlayers()) {
         if (other != player) {
            other.connection.send(packet);
            other.sendSystemMessage(message);
         }
      }
   }

   private static ClientboundPlayerInfoUpdatePacket listedPacket(ServerPlayer player, boolean listed) {
      boolean old = getAllowsListing(player);
      setAllowsListing(player, listed);
      ClientboundPlayerInfoUpdatePacket packet = new ClientboundPlayerInfoUpdatePacket(EnumSet.of(Action.UPDATE_LISTED), List.of(player));
      setAllowsListing(player, old);
      return packet;
   }

   private static boolean getAllowsListing(ServerPlayer player) {
      try {
         Field field = allowsListingField();
         return field.getBoolean(player);
      } catch (Throwable ignored) {
         return player.allowsListing();
      }
   }

   private static void setAllowsListing(ServerPlayer player, boolean value) {
      try {
         Field field = allowsListingField();
         field.setBoolean(player, value);
      } catch (Throwable var3) {
      }
   }

   private static Field allowsListingField() throws NoSuchFieldException {
      if (allowsListingField != null) {
         return allowsListingField;
      }

      String[] names = new String[]{"allowsListing", "allowServerListing", "field_35566", "f_184127_", "dp"};

      for (String name : names) {
         try {
            Field field = ServerPlayer.class.getDeclaredField(name);
            field.setAccessible(true);
            allowsListingField = field;
            return field;
         } catch (NoSuchFieldException var6) {
         }
      }

      throw new NoSuchFieldException("allowsListing");
   }
}
