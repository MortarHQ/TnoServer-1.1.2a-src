package dev.ftbqlang.client;

public final class LanguageAnimation {
   public static final int WORD_COLUMNS = 6;
   public static final int WORD_ROWS = 4;
   public static final int WORD_COUNT = 24;
   public static final double ENTER_SECONDS = 0.28;
   public static final double COLLAPSE_SECONDS = 0.16;
   public static final double LINE_END_SECONDS = 0.22;
   public static final double CLOSE_SECONDS = 0.32;
   private final long openedAt;
   private long closingAt;
   private boolean closing;

   public LanguageAnimation(long openedAt) {
      this.openedAt = openedAt;
   }

   public void beginClose(long now) {
      if (!this.closing) {
         this.closing = true;
         this.closingAt = now;
      }
   }

   public boolean isClosing() {
      return this.closing;
   }

   public boolean isFinished(long now) {
      return this.closing && seconds(now, this.closingAt) >= 0.32;
   }

   public double entrance(long now) {
      return easeOut(seconds(this.closing ? this.closingAt : now, this.openedAt) / 0.28);
   }

   public double panelScaleY(long now) {
      return this.closing ? Math.pow(1.0 - clamp(seconds(now, this.closingAt) / 0.16), 3.0) : 1.0;
   }

   public double backdropOpacity(long now) {
      return this.closing ? this.entrance(now) * (1.0 - easeOut(seconds(now, this.closingAt) / 0.32)) : this.entrance(now);
   }

   public double lineWidth(long now) {
      return this.closing && !(seconds(now, this.closingAt) < 0.16) ? 1.0 - easeOut((seconds(now, this.closingAt) - 0.16) / 0.06) : 0.0;
   }

   public LanguageAnimation.WordPose word(int index, int screenWidth, int screenHeight, double wordWidth, double wordHeight, long now) {
      double age = seconds(this.closing ? this.closingAt : now, this.openedAt);
      double cellWidth = screenWidth / 6.0;
      double cellHeight = screenHeight / 4.0;
      double phase = index * 2.399963;
      double speed = 0.72 + index % 5 * 0.11;
      double ampX = Math.min(22.0, cellWidth * 0.19);
      double ampY = Math.min(18.0, cellHeight * 0.19);
      double centerX = (index % 6 + 0.5 + 0.09 * Math.sin(phase)) * cellWidth;
      double centerY = (index / 6 + 0.5 + 0.1 * Math.cos(phase)) * cellHeight;
      double x = centerX - wordWidth / 2.0 + Math.sin(age * speed + phase) * ampX;
      double y = centerY - wordHeight / 2.0 + Math.cos(age * speed * 0.87 + phase) * ampY;
      x = Math.clamp(x, 4.0, Math.max(4.0, screenWidth - wordWidth - 4.0));
      y = Math.clamp(y, 4.0, Math.max(4.0, screenHeight - wordHeight - 4.0));
      double angle = 4.0 * Math.sin(age * 0.45 + phase);
      double alpha = this.entrance(now) * (0.68 + 0.12 * Math.sin(age * 0.6 + phase));
      if (this.closing) {
         double fallTime = seconds(now, this.closingAt);
         double velocityX = ampX * speed * Math.cos(age * speed + phase) + (index % 3 - 1) * screenWidth * 0.045;
         double velocityY = screenHeight * (1.1 + index % 5 * 0.08);
         double gravity = screenHeight * (20.0 + index % 4);
         x += velocityX * fallTime;
         y += velocityY * fallTime + 0.5 * gravity * fallTime * fallTime;
         angle += (index % 2 == 0 ? 1.0 : -1.0) * (55 + index % 7 * 9) * fallTime;
      }

      return new LanguageAnimation.WordPose(x, y, angle, alpha);
   }

   private static double seconds(long now, long start) {
      return Math.max(0.0, (now - start) / 1.0E9);
   }

   private static double clamp(double value) {
      return Math.clamp(value, 0.0, 1.0);
   }

   private static double easeOut(double progress) {
      return 1.0 - Math.pow(1.0 - clamp(progress), 3.0);
   }

   public record WordPose(double x, double y, double rotation, double alpha) {
   }
}
