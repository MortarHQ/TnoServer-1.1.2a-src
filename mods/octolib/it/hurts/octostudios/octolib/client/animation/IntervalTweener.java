package it.hurts.octostudios.octolib.client.animation;

public class IntervalTweener extends Tweener {
   private final double duration;

   @Override
   public boolean step() {
      if (this.finished) {
         return false;
      }

      if (this.getElapsedTime() < this.duration) {
         return true;
      }

      this.finish();
      return false;
   }

   public IntervalTweener(double duration) {
      this.duration = duration;
   }
}
