package toni.lib.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class VersionUtils {
   public static ResourceLocation resource(String modid, String path) {
      return ResourceLocation.fromNamespaceAndPath(modid, path);
   }

   public static ResourceLocation resource(String path) {
      return ResourceLocation.parse(path);
   }

   public static Component text(String str) {
      return Component.literal(str);
   }

   public static Level level(Entity entity) {
      return entity.level();
   }
}
