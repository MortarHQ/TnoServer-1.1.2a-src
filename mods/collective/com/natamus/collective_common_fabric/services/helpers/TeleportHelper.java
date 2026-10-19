package com.natamus.collective_common_fabric.services.helpers;

import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import net.minecraft.class_5321;

public interface TeleportHelper {
   boolean teleportEntity(class_1297 var1, class_3218 var2, class_243 var3);

   boolean teleportEntity(class_1297 var1, class_3218 var2, class_2338 var3);

   boolean teleportEntity(class_1297 var1, class_5321<class_1937> var2, class_243 var3);

   boolean teleportEntity(class_1297 var1, class_5321<class_1937> var2, class_2338 var3);
}
