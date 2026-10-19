package team.creative.creativecore.common.util.math.vec;

public class SmoothValue {
   protected double aimed;
   protected double current;
   protected double before;
   protected long timestamp;
   public final long time;

   public SmoothValue(long time, double initalValue) {
      this.time = time;
      this.setStart(initalValue);
   }

   public SmoothValue(long time) {
      this(time, 0.0);
   }

   public void setStart(double value) {
      this.aimed = value;
      this.current = value;
      this.before = value;
      this.timestamp = 0L;
   }

   public void add(double value) {
      this.set(this.aimed + value);
   }

   public void set(double value) {
      this.timestamp = System.currentTimeMillis();
      this.aimed = value;
      this.before = this.current;
   }

   public void tick() {
      if (this.timestamp != 0L) {
         if (this.timestamp + this.time <= System.currentTimeMillis()) {
            this.current = this.aimed;
            this.before = this.current;
            this.timestamp = 0L;
         } else {
            this.current = this.before + (this.aimed - this.before) * ((double)(System.currentTimeMillis() - this.timestamp) / this.time);
         }
      }
   }

   public double current() {
      return this.current;
   }

   public double aimed() {
      return this.aimed;
   }
}
