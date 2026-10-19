package com.natamus.collective_common_fabric.functions;

import java.util.List;
import net.minecraft.class_1927;
import net.minecraft.class_2338;

public class ExplosionFunctions {
   public static List<class_2338> getAffectedBlockPositions(class_1927 explosion) {
      return explosion.method_8346();
   }

   public static void clearExplosion(class_1927 explosion) {
      explosion.method_8346().clear();
      explosion.method_8351().clear();
   }
}
