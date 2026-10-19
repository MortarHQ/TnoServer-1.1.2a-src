package com.github.L_Ender.cataclysm.entity.util;

import com.github.L_Ender.cataclysm.Attachment.TidalTentacleAttachment;
import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Tentacle_Entity;
import com.github.L_Ender.cataclysm.init.ModDataAttachments;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;

public class TidalTentacleUtil {
   public static void setLastTentacle(LivingEntity entity, Tidal_Tentacle_Entity tendon) {
      TidalTentacleAttachment portal = (TidalTentacleAttachment)entity.getData(ModDataAttachments.TIDAL_TENTACLE_ATTACHMENT);
      portal.setHasTentacle(tendon != null);
   }

   public static void retractFarTentacles(Level level, LivingEntity livingEntity) {
      Tidal_Tentacle_Entity last = getLastTendon(livingEntity);
      if (last != null) {
         last.remove(RemovalReason.DISCARDED);
         setLastTentacle(livingEntity, null);
      }
   }

   public static boolean canLaunchTentacles(Level level, LivingEntity livingEntity) {
      Tidal_Tentacle_Entity last = getLastTendon(livingEntity);
      return last != null ? last.isRemoved() : true;
   }

   public static Tidal_Tentacle_Entity getLastTendon(LivingEntity livingEntity) {
      TidalTentacleAttachment portal = (TidalTentacleAttachment)livingEntity.getData(ModDataAttachments.TIDAL_TENTACLE_ATTACHMENT);
      UUID uuid = portal.getLastTentacleUUID();
      int id = portal.getLastTentacleID();
      if (!livingEntity.level().isClientSide) {
         if (uuid != null) {
            Entity e = livingEntity.level().getEntity(id);
            return e instanceof Tidal_Tentacle_Entity ? (Tidal_Tentacle_Entity)e : null;
         }
      } else if (id != -1) {
         Entity e = livingEntity.level().getEntity(id);
         return e instanceof Tidal_Tentacle_Entity ? (Tidal_Tentacle_Entity)e : null;
      }

      return null;
   }
}
