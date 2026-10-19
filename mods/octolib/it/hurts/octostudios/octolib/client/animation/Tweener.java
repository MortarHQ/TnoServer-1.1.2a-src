package it.hurts.octostudios.octolib.client.animation;

public abstract class Tweener {
   protected Tween tween;
   protected long startTimestampMillis;
   protected boolean finished;

   public void start() {
      this.startTimestampMillis = System.currentTimeMillis();
      this.finished = false;
   }

   public double getElapsedTime() {
      return (System.currentTimeMillis() - this.startTimestampMillis) / 1000.0 * this.tween.speedScale;
   }

   protected void finish() {
      this.finished = true;
   }

   public abstract boolean step();

   public Tween getTween() {
      return this.tween;
   }

   public void setTween(Tween tween) {
      this.tween = tween;
   }

   public boolean isFinished() {
      return this.finished;
   }
}
