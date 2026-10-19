package it.hurts.octostudios.octolib.client.shake;

import java.util.Random;
import org.joml.Vector2f;

public class ShakeData {
   private final Vector2f amplitude;
   private final Vector2f frequency;
   private long durationMillis;
   private long startTimestamp;
   private short seed;
   private boolean isFinished;

   public ShakeData(Vector2f amplitude, Vector2f frequency, double durationInSeconds, short seed) {
      this.amplitude = amplitude;
      this.frequency = frequency;
      this.startTimestamp = System.currentTimeMillis();
      this.durationMillis = (long)(durationInSeconds * 1000.0);
      this.seed = seed;
   }

   public ShakeData(Vector2f amplitude, Vector2f frequency, double durationInSeconds) {
      this(amplitude, frequency, durationInSeconds, (short)new Random().nextInt(-32768, 32767));
   }

   public ShakeData(float amplitude, float frequency, double durationInSeconds) {
      this(new Vector2f(amplitude, amplitude), new Vector2f(frequency * 1.24F, frequency * 0.81F), durationInSeconds);
   }

   public Vector2f update() {
      if (this.isFinished) {
         return new Vector2f(0.0F, 0.0F);
      }

      long now = System.currentTimeMillis();
      long elapsedTime = now - this.startTimestamp;
      float t = (float)Math.min((double)elapsedTime / this.durationMillis, 1.0);
      float easedT = 1.0F - t;
      if (t >= 1.0F) {
         this.isFinished = true;
      }

      float offsetX = this.amplitude.x() * (float)Math.sin(this.seed * Math.PI + (float)elapsedTime / 1000.0F * this.frequency.x() * 2.0F * Math.PI) * easedT;
      float offsetY = this.amplitude.y() * (float)Math.sin(this.seed * Math.PI + (float)elapsedTime / 1000.0F * this.frequency.y() * 2.0F * Math.PI) * easedT;
      return new Vector2f(offsetX, offsetY);
   }

   public boolean isFinished() {
      return this.isFinished;
   }
}
