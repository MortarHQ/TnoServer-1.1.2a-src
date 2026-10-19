package team.lodestar.lodestone.systems.rendering.buffer;

import team.lodestar.lodestone.handlers.LodestoneRenderHandler;

public class LodestoneRenderLayer {
   protected final LodestoneBufferSource target = new LodestoneBufferSource();
   protected final LodestoneBufferSource particleTarget = new LodestoneBufferSource();
   protected final LodestoneBufferSource trailTarget = new LodestoneBufferSource();

   public void endBatches() {
      this.particleTarget.endBatches(false);
      this.target.endBatches(false);
      LodestoneRenderHandler.clearModelViewMatrix();
      this.trailTarget.endBatches(false);
      this.trailTarget.endBatches(true);
      LodestoneRenderHandler.restoreModelViewMatrix();
      this.target.endBatches(true);
      this.particleTarget.endBatches(true);
   }

   public LodestoneBufferSource getTarget() {
      return this.target;
   }

   public LodestoneBufferSource getParticleTarget() {
      return this.particleTarget;
   }

   public LodestoneBufferSource getTrailTarget() {
      return this.trailTarget;
   }
}
