package com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util;

import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.core.BlockPos;

public class NoisySphereUtils {
   public static final class NoisySphereIterable implements Iterable<BlockPos> {
      private final long noiseSeed;
      private final BlockPos origin;
      private final double noiseFrequencyXZ;
      private final double noiseFrequencyY;
      private final int radiusMax;
      private final int radiusMinSq;
      private final int radiusMaxSq;
      private final float noiseRadiusBase;
      private final float noiseRadiusVariation;

      public NoisySphereIterable(BlockPos origin, long noiseSeed, double noiseFrequencyXZ, double noiseFrequencyY, int radiusMin, int radiusMax) {
         this.origin = origin;
         this.noiseSeed = noiseSeed;
         this.noiseFrequencyXZ = noiseFrequencyXZ;
         this.noiseFrequencyY = noiseFrequencyY;
         this.radiusMax = radiusMax;
         this.radiusMinSq = radiusMin * radiusMin;
         this.radiusMaxSq = radiusMax * radiusMax;
         this.noiseRadiusBase = (radiusMax + radiusMin) * 0.5F;
         this.noiseRadiusVariation = (radiusMax - radiusMin) * 0.5F;
      }

      @Override
      public Iterator<BlockPos> iterator() {
         return new NoisySphereUtils.NoisySphereIterable.NoisySphereIterator();
      }

      public class NoisySphereIterator implements Iterator<BlockPos> {
         private int dx = -NoisySphereIterable.this.radiusMax;
         private int dy = -NoisySphereIterable.this.radiusMax;
         private int dz = -NoisySphereIterable.this.radiusMax;
         private BlockPos next;

         public NoisySphereIterator() {
            this.update();
         }

         @Override
         public boolean hasNext() {
            return this.dz <= NoisySphereIterable.this.radiusMax;
         }

         public BlockPos next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            }

            BlockPos here = this.next;
            this.update();
            return here;
         }

         private void update() {
            boolean isInRange;
            boolean loopContinues;
            do {
               int distSq = this.dx * this.dx + this.dy * this.dy + this.dz * this.dz;
               this.next = NoisySphereIterable.this.origin.offset(this.dx, this.dy, this.dz);
               isInRange = distSq < NoisySphereIterable.this.radiusMinSq;
               if (!isInRange && distSq < NoisySphereIterable.this.radiusMaxSq) {
                  float noiseValue = OpenSimplex2S.noise3_ImproveXZ(
                     NoisySphereIterable.this.noiseSeed,
                     this.next.getX() * NoisySphereIterable.this.noiseFrequencyXZ,
                     this.next.getY() * NoisySphereIterable.this.noiseFrequencyY,
                     this.next.getZ() * NoisySphereIterable.this.noiseFrequencyXZ
                  );
                  float noiseRadius = NoisySphereIterable.this.noiseRadiusBase + NoisySphereIterable.this.noiseRadiusVariation * noiseValue;
                  isInRange = distSq < noiseRadius * noiseRadius;
               }

               loopContinues = this.advanceOffsets();
            } while (!isInRange && loopContinues);
         }

         boolean advanceOffsets() {
            this.dy++;
            if (this.dy <= NoisySphereIterable.this.radiusMax) {
               return true;
            }

            this.dy = -NoisySphereIterable.this.radiusMax;
            this.dx++;
            if (this.dx <= NoisySphereIterable.this.radiusMax) {
               return true;
            }

            this.dx = -NoisySphereIterable.this.radiusMax;
            this.dz++;
            return this.dz <= NoisySphereIterable.this.radiusMax;
         }
      }
   }
}
