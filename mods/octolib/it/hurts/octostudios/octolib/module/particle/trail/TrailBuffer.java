package it.hurts.octostudios.octolib.module.particle.trail;

import it.hurts.octostudios.octolib.module.particle.RenderBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public interface TrailBuffer extends Iterable<Vec3>, RenderBuffer<TrailProvider, TrailBuffer> {
   void write(Vec3 var1);

   int size();

   void remove();

   default void tick(TrailProvider provider) {
      Vec3 position = provider.getTrailPosition(Minecraft.getInstance().getTimer().getGameTimeDeltaTicks());
      if (provider.isTrailAlive() && provider.isTrailGrowing()) {
         this.write(position);
      } else if (this.size() > 0) {
         this.remove();
      }
   }
}
