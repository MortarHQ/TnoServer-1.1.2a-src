package cc.sighs.oelib.config.ui.scissor;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Rectangle implements Cloneable {
   public int x;
   public int y;
   public int width;
   public int height;

   public Rectangle() {
      this(0, 0, 0, 0);
   }

   public Rectangle(Rectangle r) {
      this(r.x, r.y, r.width, r.height);
   }

   public Rectangle(FloatingRectangle r) {
      this(r.x, r.y, r.width, r.height);
   }

   public Rectangle(int width, int height) {
      this(0, 0, width, height);
   }

   public Rectangle(Point p, Dimension d) {
      this(p.x, p.y, d.width, d.height);
   }

   public Rectangle(Point p, FloatingDimension d) {
      this(p.x, p.y, d.width, d.height);
   }

   public Rectangle(FloatingPoint p, Dimension d) {
      this(p.x, p.y, d.width, d.height);
   }

   public Rectangle(FloatingPoint p, FloatingDimension d) {
      this(p.x, p.y, d.width, d.height);
   }

   public Rectangle(Point p) {
      this(p.x, p.y, 0, 0);
   }

   public Rectangle(FloatingPoint p) {
      this(p.x, p.y, 0.0, 0.0);
   }

   public Rectangle(Dimension d) {
      this(0, 0, d.width, d.height);
   }

   public Rectangle(FloatingDimension d) {
      this(0.0, 0.0, d.width, d.height);
   }

   public Rectangle(double x, double y, double width, double height) {
      this((int)Math.round(x), (int)Math.round(y), (int)Math.round(width), (int)Math.round(height));
   }

   public Rectangle(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public int getX() {
      return this.x;
   }

   public int getMinX() {
      return this.x;
   }

   public int getMaxX() {
      return this.x + this.width;
   }

   public int getCenterX() {
      return this.x + this.width / 2;
   }

   public int getY() {
      return this.y;
   }

   public int getMinY() {
      return this.y;
   }

   public int getMaxY() {
      return this.y + this.height;
   }

   public int getCenterY() {
      return this.y + this.height / 2;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public FloatingRectangle getFloatingBounds() {
      return new FloatingRectangle(this.x, this.y, this.width, this.height);
   }

   public Rectangle getBounds() {
      return new Rectangle(this.x, this.y, this.width, this.height);
   }

   public void setBounds(FloatingRectangle r) {
      this.setBounds(r.x, r.y, r.width, r.height);
   }

   public void setBounds(Rectangle r) {
      this.setBounds(r.x, r.y, r.width, r.height);
   }

   public void setBounds(double x, double y, double width, double height) {
      this.setBounds((int)Math.round(x), (int)Math.round(y), (int)Math.round(width), (int)Math.round(height));
   }

   public void setBounds(int x, int y, int width, int height) {
      this.reshape(x, y, width, height);
   }

   @Deprecated
   public void reshape(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public FloatingPoint getFloatingLocation() {
      return new FloatingPoint(this.x, this.y);
   }

   public Point getLocation() {
      return new Point(this.x, this.y);
   }

   public void setLocation(FloatingPoint p) {
      this.setLocation(p.x, p.y);
   }

   public void setLocation(Point p) {
      this.setLocation(p.x, p.y);
   }

   public void setLocation(double x, double y) {
      this.move((int)Math.round(x), (int)Math.round(y));
   }

   public void setLocation(int x, int y) {
      this.move(x, y);
   }

   @Deprecated
   public void move(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public void translate(int dx, int dy) {
      this.x += dx;
      this.y += dy;
   }

   public Rectangle clone() {
      return this.getBounds();
   }

   public FloatingDimension getFloatingSize() {
      return new FloatingDimension(this.width, this.height);
   }

   public Dimension getSize() {
      return new Dimension(this.width, this.height);
   }

   public void setSize(FloatingDimension d) {
      this.setSize(d.width, d.height);
   }

   public void setSize(Dimension d) {
      this.setSize(d.width, d.height);
   }

   public void setSize(double width, double height) {
      this.resize((int)Math.round(width), (int)Math.round(height));
   }

   public void setSize(int width, int height) {
      this.resize(width, height);
   }

   @Deprecated
   public void resize(int width, int height) {
      this.width = width;
      this.height = height;
   }

   public boolean contains(Point p) {
      return this.contains(p.x, p.y);
   }

   public boolean contains(FloatingPoint p) {
      return this.contains(p.x, p.y);
   }

   public boolean contains(int x, int y) {
      return this.inside(x, y);
   }

   public boolean contains(double x, double y) {
      return this.inside((int)x, (int)y);
   }

   public boolean contains(FloatingRectangle r) {
      return this.contains((int)r.x, (int)r.y, (int)r.width, (int)r.height);
   }

   public boolean contains(Rectangle r) {
      return this.contains(r.x, r.y, r.width, r.height);
   }

   public boolean contains(int X, int Y, int W, int H) {
      return this.contains(X, Y) && this.contains(W, H);
   }

   @Deprecated
   public boolean inside(int x, int y) {
      int thisX = this.x;
      int thisY = this.y;
      return !this.isEmpty() && x >= thisX && x <= thisX + this.width && y >= thisY && y <= thisY + this.height;
   }

   public boolean intersects(Rectangle r) {
      int tw = this.width;
      int th = this.height;
      int rw = r.width;
      int rh = r.height;
      if (rw > 0 && rh > 0 && tw > 0 && th > 0) {
         int tx = this.x;
         int ty = this.y;
         int rx = r.x;
         int ry = r.y;
         rw += rx;
         rh += ry;
         tw += tx;
         th += ty;
         return (rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry);
      } else {
         return false;
      }
   }

   public Rectangle intersection(Rectangle r) {
      int tx1 = this.x;
      int ty1 = this.y;
      int rx1 = r.x;
      int ry1 = r.y;
      long tx2 = tx1;
      tx2 += this.width;
      long ty2 = ty1;
      ty2 += this.height;
      long rx2 = rx1;
      rx2 += r.width;
      long ry2 = ry1;
      ry2 += r.height;
      if (tx1 < rx1) {
         tx1 = rx1;
      }

      if (ty1 < ry1) {
         ty1 = ry1;
      }

      if (tx2 > rx2) {
         tx2 = rx2;
      }

      if (ty2 > ry2) {
         ty2 = ry2;
      }

      tx2 -= tx1;
      ty2 -= ty1;
      if (tx2 < -2147483648L) {
         tx2 = -2147483648L;
      }

      if (ty2 < -2147483648L) {
         ty2 = -2147483648L;
      }

      return new Rectangle(tx1, ty1, (int)tx2, (int)ty2);
   }

   public Rectangle union(Rectangle r) {
      long tx2 = this.width;
      long ty2 = this.height;
      if ((tx2 | ty2) < 0L) {
         return new Rectangle(r);
      }

      long rx2 = r.width;
      long ry2 = r.height;
      if ((rx2 | ry2) < 0L) {
         return new Rectangle(this);
      }

      int tx1 = this.x;
      int ty1 = this.y;
      tx2 += tx1;
      ty2 += ty1;
      int rx1 = r.x;
      int ry1 = r.y;
      rx2 += rx1;
      ry2 += ry1;
      if (tx1 > rx1) {
         tx1 = rx1;
      }

      if (ty1 > ry1) {
         ty1 = ry1;
      }

      if (tx2 < rx2) {
         tx2 = rx2;
      }

      if (ty2 < ry2) {
         ty2 = ry2;
      }

      tx2 -= tx1;
      ty2 -= ty1;
      if (tx2 > 2147483647L) {
         tx2 = 2147483647L;
      }

      if (ty2 > 2147483647L) {
         ty2 = 2147483647L;
      }

      return new Rectangle(tx1, ty1, (int)tx2, (int)ty2);
   }

   public void add(int newx, int newy) {
      if ((this.width | this.height) < 0) {
         this.x = newx;
         this.y = newy;
         this.width = this.height = 0;
      } else {
         int x1 = this.x;
         int y1 = this.y;
         long x2 = this.width;
         long y2 = this.height;
         x2 += x1;
         y2 += y1;
         if (x1 > newx) {
            x1 = newx;
         }

         if (y1 > newy) {
            y1 = newy;
         }

         if (x2 < newx) {
            x2 = newx;
         }

         if (y2 < newy) {
            y2 = newy;
         }

         x2 -= x1;
         y2 -= y1;
         if (x2 > 2147483647L) {
            x2 = 2147483647L;
         }

         if (y2 > 2147483647L) {
            y2 = 2147483647L;
         }

         this.reshape(x1, y1, (int)x2, (int)y2);
      }
   }

   public void add(FloatingPoint pt) {
      this.add((int)pt.x, (int)pt.y);
   }

   public void add(Point pt) {
      this.add(pt.x, pt.y);
   }

   public void add(Rectangle r) {
      long tx2 = this.width;
      long ty2 = this.height;
      if ((tx2 | ty2) < 0L) {
         this.reshape(r.x, r.y, r.width, r.height);
      }

      long rx2 = r.width;
      long ry2 = r.height;
      if ((rx2 | ry2) >= 0L) {
         int tx1 = this.x;
         int ty1 = this.y;
         tx2 += tx1;
         ty2 += ty1;
         int rx1 = r.x;
         int ry1 = r.y;
         rx2 += rx1;
         ry2 += ry1;
         if (tx1 > rx1) {
            tx1 = rx1;
         }

         if (ty1 > ry1) {
            ty1 = ry1;
         }

         if (tx2 < rx2) {
            tx2 = rx2;
         }

         if (ty2 < ry2) {
            ty2 = ry2;
         }

         tx2 -= tx1;
         ty2 -= ty1;
         if (tx2 > 2147483647L) {
            tx2 = 2147483647L;
         }

         if (ty2 > 2147483647L) {
            ty2 = 2147483647L;
         }

         this.reshape(tx1, ty1, (int)tx2, (int)ty2);
      }
   }

   public void grow(int h, int v) {
      long x0 = this.x;
      long y0 = this.y;
      long x1 = this.width;
      long y1 = this.height;
      x1 += x0;
      y1 += y0;
      x0 -= h;
      y0 -= v;
      x1 += h;
      y1 += v;
      if (x1 < x0) {
         x1 -= x0;
         if (x1 < -2147483648L) {
            x1 = -2147483648L;
         }

         if (x0 < -2147483648L) {
            x0 = -2147483648L;
         } else if (x0 > 2147483647L) {
            x0 = 2147483647L;
         }
      } else {
         if (x0 < -2147483648L) {
            x0 = -2147483648L;
         } else if (x0 > 2147483647L) {
            x0 = 2147483647L;
         }

         x1 -= x0;
         if (x1 < -2147483648L) {
            x1 = -2147483648L;
         } else if (x1 > 2147483647L) {
            x1 = 2147483647L;
         }
      }

      if (y1 < y0) {
         y1 -= y0;
         if (y1 < -2147483648L) {
            y1 = -2147483648L;
         }

         if (y0 < -2147483648L) {
            y0 = -2147483648L;
         } else if (y0 > 2147483647L) {
            y0 = 2147483647L;
         }
      } else {
         if (y0 < -2147483648L) {
            y0 = -2147483648L;
         } else if (y0 > 2147483647L) {
            y0 = 2147483647L;
         }

         y1 -= y0;
         if (y1 < -2147483648L) {
            y1 = -2147483648L;
         } else if (y1 > 2147483647L) {
            y1 = 2147483647L;
         }
      }

      this.reshape((int)x0, (int)y0, (int)x1, (int)y1);
   }

   public boolean isEmpty() {
      return this.width <= 0 || this.height <= 0;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return !(obj instanceof Rectangle r) ? super.equals(obj) : this.x == r.x && this.y == r.y && this.width == r.width && this.height == r.height;
      }
   }

   @Override
   public String toString() {
      return ReflectionToStringBuilder.reflectionToString(this);
   }

   @Override
   public int hashCode() {
      int result = 1;
      result = 31 * result + this.x;
      result = 31 * result + this.y;
      result = 31 * result + this.width;
      return 31 * result + this.height;
   }
}
