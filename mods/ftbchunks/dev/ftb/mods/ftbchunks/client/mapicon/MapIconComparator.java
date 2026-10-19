package dev.ftb.mods.ftbchunks.client.mapicon;

import dev.ftb.mods.ftbchunks.api.client.icon.MapIcon;
import java.util.Comparator;
import net.minecraft.world.phys.Vec3;

public class MapIconComparator implements Comparator<MapIcon> {
   private final Vec3 pos;
   private final float delta;

   public MapIconComparator(Vec3 pos, float delta) {
      this.pos = pos;
      this.delta = delta;
   }

   public int compare(MapIcon o1, MapIcon o2) {
      int i = Integer.compare(o1.getPriority(), o2.getPriority());
      return i == 0 ? Double.compare(o2.getPos(this.delta).distanceToSqr(this.pos), o1.getPos(this.delta).distanceToSqr(this.pos)) : i;
   }
}
