package net.regions_unexplored.module.version;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class VersionBlockHelper {
   public static Properties postProcessed(Properties properties) {
      return properties.hasPostProcess((state, getter, pos) -> true);
   }
}
