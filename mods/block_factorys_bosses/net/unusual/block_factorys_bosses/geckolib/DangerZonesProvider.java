package net.unusual.block_factorys_bosses.geckolib;

import java.util.Collection;
import javax.annotation.Nonnull;
import org.joml.Vector2f;
import org.joml.Vector3f;

public interface DangerZonesProvider {
   Collection<DangerZonesProvider.DangerZone> getDangerZones();

   class DangerZone {
      @Nonnull
      private final Vector3f offset = new Vector3f();
      @Nonnull
      private final Vector2f size = new Vector2f();
      private float rotation = 0.0F;
      private int color = -1;

      @Nonnull
      public Vector3f getOffset() {
         return this.offset;
      }

      public DangerZonesProvider.DangerZone setOffset(Vector3f offset) {
         this.offset.set(offset);
         return this;
      }

      @Nonnull
      public Vector2f getSize() {
         return this.size;
      }

      public DangerZonesProvider.DangerZone setSize(Vector2f size) {
         this.size.set(size);
         return this;
      }

      public DangerZonesProvider.DangerZone setSize(float x, float z) {
         this.size.set(x, z);
         return this;
      }

      public float getRotation() {
         return this.rotation;
      }

      public DangerZonesProvider.DangerZone setRotation(float rotation) {
         this.rotation = rotation;
         return this;
      }

      public int getColor() {
         return this.color;
      }

      public DangerZonesProvider.DangerZone setColor(int color) {
         this.color = color;
         return this;
      }
   }
}
