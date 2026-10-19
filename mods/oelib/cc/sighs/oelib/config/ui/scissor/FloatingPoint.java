package cc.sighs.oelib.config.ui.scissor;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class FloatingPoint implements Cloneable {
   public double x;
   public double y;

   public FloatingPoint() {
      this(0.0, 0.0);
   }

   public FloatingPoint(Point p) {
      this(p.x, p.y);
   }

   public FloatingPoint(FloatingPoint p) {
      this(p.x, p.y);
   }

   public FloatingPoint(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public FloatingPoint getFloatingLocation() {
      return new FloatingPoint(this.x, this.y);
   }

   public Point getLocation() {
      return new Point(this.x, this.y);
   }

   public FloatingPoint clone() {
      return this.getFloatingLocation();
   }

   public void setLocation(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public void move(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public void translate(double dx, double dy) {
      this.x += dx;
      this.y += dy;
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof FloatingPoint pt) ? super.equals(obj) : this.x == pt.x && this.y == pt.y;
   }

   @Override
   public int hashCode() {
      int result = 1;
      result = 31 * result + Double.hashCode(this.x);
      return 31 * result + Double.hashCode(this.y);
   }

   @Override
   public String toString() {
      return ReflectionToStringBuilder.reflectionToString(this);
   }
}
