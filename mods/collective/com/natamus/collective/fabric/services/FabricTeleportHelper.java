package com.natamus.collective.fabric.services;

import com.natamus.collective_common_fabric.services.helpers.TeleportHelper;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2709;
import net.minecraft.class_3218;
import net.minecraft.class_5321;

public class FabricTeleportHelper implements TeleportHelper {
   @Override
   public boolean teleportEntity(class_1297 entity, class_3218 serverLevel, class_243 vec3) {
      return entity.method_48105(
         serverLevel, vec3.field_1352, vec3.field_1351, vec3.field_1350, class_2709.field_40710, entity.method_36454(), entity.method_36455()
      );
   }

   @Override
   public boolean teleportEntity(class_1297 entity, class_3218 serverLevel, class_2338 blockPos) {
      return entity.method_48105(
         serverLevel,
         blockPos.method_10263() + 0.5,
         blockPos.method_10264(),
         blockPos.method_10260() + 0.5,
         class_2709.field_40710,
         entity.method_36454(),
         entity.method_36455()
      );
   }

   @Override
   public boolean teleportEntity(class_1297 entity, class_5321<class_1937> targetDimension, class_243 vec3) {
      return entity.method_37908().field_9236 ? false : this.teleportEntity(entity, entity.method_5682().method_3847(targetDimension), vec3);
   }

   @Override
   public boolean teleportEntity(class_1297 entity, class_5321<class_1937> targetDimension, class_2338 blockPos) {
      return entity.method_37908().field_9236 ? false : this.teleportEntity(entity, entity.method_5682().method_3847(targetDimension), blockPos);
   }
}
