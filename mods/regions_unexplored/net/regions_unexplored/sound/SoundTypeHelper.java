package net.regions_unexplored.sound;

import net.minecraft.world.level.block.SoundType;
import net.regions_unexplored.registry.RUSoundEvents;

public class SoundTypeHelper {
   public static SoundType create(String name) {
      return new SoundType(
         1.0F,
         1.0F,
         RUSoundEvents.register(id(name, ".break")),
         RUSoundEvents.register(id(name, ".step")),
         RUSoundEvents.register(id(name, ".place")),
         RUSoundEvents.register(id(name, ".hit")),
         RUSoundEvents.register(id(name, ".fall"))
      );
   }

   private static String id(String baseName, String suffix) {
      return "block." + baseName + suffix;
   }
}
