package xaero.map.animation;

public class SinAnimation extends Animation {
   public SinAnimation(double from, double to, long time) {
      super(from, to, time);
   }

   @Override
   public double getCurrent() {
      double passed = Math.min(1.0, (double)(System.currentTimeMillis() - this.start) / this.time);
      double angle = (Math.PI / 2) * passed;
      return this.from + this.off * Math.sin(angle);
   }
}
