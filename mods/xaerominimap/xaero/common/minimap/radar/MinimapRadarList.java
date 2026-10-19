package xaero.common.minimap.radar;

import java.util.List;
import net.minecraft.world.entity.Entity;
import xaero.hud.minimap.radar.category.EntityRadarCategory;
import xaero.hud.minimap.radar.state.RadarList;

@Deprecated
public final class MinimapRadarList extends RadarList {
   @Deprecated
   @Override
   public EntityRadarCategory getClientCategory() {
      return super.getClientCategory();
   }

   @Deprecated
   public MinimapRadarList setClientCategory(EntityRadarCategory clientCategory) {
      return (MinimapRadarList)super.setClientCategory(clientCategory);
   }

   @Deprecated
   public List<Entity> getEntities() {
      return (List<Entity>)super.getEntities();
   }

   public static final class Builder {
      private Builder() {
      }

      public MinimapRadarList.Builder setDefault() {
         return this;
      }

      public MinimapRadarList build() {
         return new MinimapRadarList();
      }

      public static MinimapRadarList.Builder getDefault() {
         return new MinimapRadarList.Builder().setDefault();
      }
   }
}
