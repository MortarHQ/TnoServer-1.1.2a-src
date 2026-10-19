package xaero.common.minimap.radar.tracker.system.compat;

import java.util.UUID;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.hud.minimap.player.tracker.system.ITrackedPlayerReader;

@Deprecated
public class TrackedPlayerReaderWrapper<P> implements xaero.common.minimap.radar.tracker.system.ITrackedPlayerReader<P> {
   private final ITrackedPlayerReader<P> original;

   public TrackedPlayerReaderWrapper(ITrackedPlayerReader<P> original) {
      this.original = original;
   }

   @Override
   public UUID getId(P player) {
      return this.original.getId(player);
   }

   @Override
   public double getX(P player) {
      return this.original.getX(player);
   }

   @Override
   public double getY(P player) {
      return this.original.getY(player);
   }

   @Override
   public double getZ(P player) {
      return this.original.getZ(player);
   }

   @Override
   public ResourceKey<Level> getDimension(P player) {
      return this.original.getDimension(player);
   }
}
