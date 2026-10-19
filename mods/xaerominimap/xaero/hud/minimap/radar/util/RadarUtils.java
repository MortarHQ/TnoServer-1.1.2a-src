package xaero.hud.minimap.radar.util;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import xaero.common.minimap.MinimapProcessor;
import xaero.hud.minimap.MinimapLogs;
import xaero.lib.common.reflection.util.ReflectionUtils;

public class RadarUtils {
   private static EntityDataAccessor<Optional<UUID>> FOX_TRUSTED_UUID_SECONDARY;
   private static EntityDataAccessor<Optional<UUID>> FOX_TRUSTED_UUID_MAIN;

   public static double getMaxDistance(MinimapProcessor minimap, boolean circle) {
      int cullingSize = minimap.getMinimapSize() / 2 + 48;
      if (!circle) {
         cullingSize = (int)(cullingSize * Math.sqrt(2.0));
      }

      return cullingSize * cullingSize / (minimap.getMinimapZoom() * minimap.getMinimapZoom());
   }

   public static boolean isHostileException(Entity e) {
      return e instanceof Piglin ? ((Piglin)e).isBaby() : false;
   }

   public static boolean isTamed(Entity e, Player p) {
      if (e instanceof TamableAnimal tameable) {
         return tameable.isTame() && p.getUUID().equals(tameable.getOwnerUUID());
      } else if (e instanceof AbstractHorse horse) {
         return horse.isTamed() && (horse.getOwnerUUID() == null || p.getUUID().equals(horse.getOwnerUUID()));
      } else if (e instanceof Fox fox) {
         return FOX_TRUSTED_UUID_SECONDARY != null && p.getUUID().equals(((Optional)fox.getEntityData().get(FOX_TRUSTED_UUID_SECONDARY)).orElse(null))
            ? true
            : FOX_TRUSTED_UUID_MAIN != null && p.getUUID().equals(((Optional)fox.getEntityData().get(FOX_TRUSTED_UUID_MAIN)).orElse(null));
      } else {
         return false;
      }
   }

   public static boolean isHostile(Entity e) {
      if (Minecraft.getInstance().level.getDifficulty() == Difficulty.PEACEFUL) {
         return false;
      } else {
         return isHostileException(e) ? false : e instanceof Monster || e instanceof Enemy || e.getSoundSource() == SoundSource.HOSTILE;
      }
   }

   public static String getCustomName(Entity e, boolean nullable) {
      Component c = e.getCustomName();
      if (c != null && c.getContents() instanceof PlainTextContents) {
         return ((PlainTextContents)c.getContents()).text();
      } else {
         return nullable ? null : "{non-plain}";
      }
   }

   static {
      Field foxTrustSecondaryField = null;
      Field foxTrustMainField = null;

      try {
         foxTrustSecondaryField = ReflectionUtils.getFieldReflection(Fox.class, "DATA_TRUSTED_ID_0", "field_17951", "Lnet/minecraft/class_2940;", "f_28439_");
      } catch (Exception e) {
         MinimapLogs.LOGGER.error("suppressed exception", e);
      }

      try {
         foxTrustMainField = ReflectionUtils.getFieldReflection(Fox.class, "DATA_TRUSTED_ID_1", "field_17952", "Lnet/minecraft/class_2940;", "f_28440_");
      } catch (Exception e) {
         MinimapLogs.LOGGER.error("suppressed exception", e);
      }

      if (foxTrustSecondaryField != null) {
         FOX_TRUSTED_UUID_SECONDARY = (EntityDataAccessor<Optional<UUID>>)ReflectionUtils.getReflectFieldValue(0, foxTrustSecondaryField);
      }

      if (foxTrustMainField != null) {
         FOX_TRUSTED_UUID_MAIN = (EntityDataAccessor<Optional<UUID>>)ReflectionUtils.getReflectFieldValue(0, foxTrustMainField);
      }
   }
}
