package xaero.hud.minimap.radar;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import xaero.common.minimap.radar.MinimapRadar;
import xaero.hud.minimap.radar.category.EntityRadarCategoryManager;
import xaero.hud.minimap.radar.color.RadarColorHelper;
import xaero.hud.minimap.radar.state.RadarState;
import xaero.hud.minimap.radar.state.RadarStateUpdater;

public class RadarSession {
   private final EntityRadarCategoryManager categoryManager;
   private final RadarState state;
   private final RadarStateUpdater stateUpdater;
   private final RadarColorHelper colorHelper;

   public RadarSession(EntityRadarCategoryManager categoryManager) {
      this.categoryManager = categoryManager;
      this.state = new RadarState();
      this.stateUpdater = new RadarStateUpdater(categoryManager, this.state);
      this.colorHelper = new RadarColorHelper();
   }

   public void update(ClientLevel world, Entity renderEntity, Player player) {
      this.stateUpdater.update(world, renderEntity, player);
   }

   public EntityRadarCategoryManager getCategoryManager() {
      return this.categoryManager;
   }

   public RadarState getState() {
      return this.state;
   }

   public RadarStateUpdater getStateUpdater() {
      return this.stateUpdater;
   }

   public RadarColorHelper getColorHelper() {
      return this.colorHelper;
   }

   public static class Builder {
      private EntityRadarCategoryManager categoryManager;

      private Builder() {
      }

      public RadarSession.Builder setDefault() {
         this.setCategoryManager(null);
         return this;
      }

      public RadarSession.Builder setCategoryManager(EntityRadarCategoryManager categoryManager) {
         this.categoryManager = categoryManager;
         return this;
      }

      public RadarSession build() {
         if (this.categoryManager == null) {
            throw new IllegalStateException();
         } else {
            return new MinimapRadar(this.categoryManager);
         }
      }

      public static RadarSession.Builder begin() {
         return new RadarSession.Builder().setDefault();
      }
   }
}
