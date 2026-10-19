package xaero.map.radar.tracker.system;

import java.util.UUID;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public interface ITrackedPlayerReader<P> {
   UUID getId(P var1);

   double getX(P var1);

   double getY(P var1);

   double getZ(P var1);

   ResourceKey<Level> getDimension(P var1);
}
