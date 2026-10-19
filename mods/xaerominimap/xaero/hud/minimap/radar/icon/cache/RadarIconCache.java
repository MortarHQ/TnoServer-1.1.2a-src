package xaero.hud.minimap.radar.icon.cache;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.entity.EntityType;

public class RadarIconCache {
   private final Map<EntityType<?>, RadarIconEntityCache> iconCacheMap = new HashMap<>();

   public RadarIconEntityCache getEntityCache(EntityType<?> entityType) {
      RadarIconEntityCache result = this.iconCacheMap.get(entityType);
      if (result == null) {
         this.iconCacheMap.put(entityType, result = new RadarIconEntityCache(entityType));
      }

      return result;
   }

   public void clear() {
      this.iconCacheMap.clear();
   }
}
