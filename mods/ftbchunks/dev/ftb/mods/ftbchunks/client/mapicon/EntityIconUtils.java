package dev.ftb.mods.ftbchunks.client.mapicon;

import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader.EntityIconSettings;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

public class EntityIconUtils {
   public static boolean canTypeRender(EntityType<?> type) {
      return type != EntityType.PLAYER && EntityIconLoader.getSettings(type).isPresent();
   }

   public static boolean shouldEntityRender(Entity entity, Player player) {
      return canTypeRender(entity.getType()) && !entity.isInvisibleTo(player)
         ? BuiltInRegistries.ENTITY_TYPE
            .getResourceKey(entity.getType())
            .map(key -> isIconEnabled((ResourceKey<EntityType<?>>)key, entity.getType()))
            .orElse(false)
         : false;
   }

   private static boolean isIconEnabled(ResourceKey<EntityType<?>> key, EntityType<?> type) {
      if (!((Map)FTBChunksClientConfig.ENTITY_ICON.get()).containsKey(key)) {
         EntityIconSettings settings = EntityIconLoader.getSettings(type).orElse(EntityIconSettings.legacy());
         ((Map)FTBChunksClientConfig.ENTITY_ICON.get()).put(key, settings.defaultEnabled());
         FTBChunksClientConfig.saveConfig();
         return settings.defaultEnabled();
      } else {
         return (Boolean)((Map)FTBChunksClientConfig.ENTITY_ICON.get()).get(key);
      }
   }
}
