package team.creative.creativecore.common.util.type.set;

import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import team.creative.creativecore.common.util.type.itr.ComputeNextIterator;

public class CubeBitSet implements Iterable<MutableBlockPos> {
   private static final int CHUNK_SIZE = 4;
   private static final int CHUNK_SIZE_SQUARE = 16;
   public static final int CHUNK_BITS = 2;
   private long[][][] chunks;
   private int minChunkX;
   private int minChunkY;
   private int minChunkZ;
   private int count = 0;

   private static int chunkIndex(int coord) {
      return coord < 0 ? (int)Math.floor(coord / 4.0) : coord / 4;
   }

   private static int index(int x, int y, int z) {
      return x * 16 + y * 4 + z;
   }

   public void load(CompoundTag nbt) {
      if (!nbt.contains("info")) {
         this.clearIncludingSize();
      } else {
         int[] info = nbt.getIntArray("info");
         if (info.length != 3) {
            throw new IllegalArgumentException("Data is not valid " + nbt);
         }

         this.count = info[0];
         this.minChunkX = info[1];
         this.minChunkY = info[2];
         this.minChunkZ = info[3];
         ListTag list = nbt.getList("data", 12);
         this.chunks = new long[list.size()][][];

         for (int i = 0; i < list.size(); i++) {
            ListTag nested = list.getList(i);

            for (int j = 0; j < nested.size(); j++) {
               this.chunks[i][j] = nested.getLongArray(j);
            }
         }
      }
   }

   public CompoundTag save() {
      CompoundTag nbt = new CompoundTag();
      if (this.count == 0) {
         return nbt;
      }

      nbt.putIntArray("info", new int[]{this.count, this.minChunkX, this.minChunkY, this.minChunkZ});
      ListTag list = new ListTag();

      for (int i = 0; i < this.chunks.length; i++) {
         ListTag nested = new ListTag();

         for (int j = 0; j < this.chunks[i].length; j++) {
            nested.add(new LongArrayTag(Arrays.copyOf(this.chunks[i][j], this.chunks[i][j].length)));
         }

         list.add(nested);
      }

      nbt.put("data", list);
      return nbt;
   }

   private void init(int x, int y, int z) {
      this.minChunkX = chunkIndex(x);
      this.minChunkY = chunkIndex(y);
      this.minChunkZ = chunkIndex(z);
      if (this.chunks == null || this.chunks.length == 0 || this.chunks[0].length == 0 || this.chunks[0][0].length == 0) {
         this.chunks = new long[1][1][1];
      }

      this.count = 0;
   }

   private void ensureCapacity(int x, int y, int z) {
      if (this.count == 0) {
         this.init(x, y, z);
      } else {
         int chunkX = chunkIndex(x);
         int chunkY = chunkIndex(y);
         int chunkZ = chunkIndex(z);
         if (chunkX >= this.minChunkX) {
            if (chunkX - this.minChunkX >= this.chunks.length) {
               int additional = chunkX - (this.minChunkX + this.chunks.length) + 1;
               int length = this.chunks.length;
               this.chunks = Arrays.copyOf(this.chunks, length + additional);

               for (int i = 0; i < additional; i++) {
                  this.chunks[length + i] = new long[1][1];
               }
            }
         } else {
            int additional = this.minChunkX - chunkX;
            long[][][] newChunks = new long[additional + this.chunks.length][][];

            for (int i = 0; i < additional; i++) {
               newChunks[i] = new long[1][1];
            }

            System.arraycopy(this.chunks, 0, newChunks, additional, this.chunks.length);
            this.chunks = newChunks;
            this.minChunkX = chunkX;
         }

         if (chunkY >= this.minChunkY) {
            int xIndex = chunkX - this.minChunkX;
            if (chunkY - this.minChunkY >= this.chunks[xIndex].length) {
               int additional = chunkY - (this.minChunkY + this.chunks[xIndex].length) + 1;
               int length = this.chunks.length;
               this.chunks[xIndex] = Arrays.copyOf(this.chunks[xIndex], length + additional);

               for (int i = 0; i < additional; i++) {
                  this.chunks[xIndex][length + i] = new long[1];
               }
            }
         } else {
            int additional = this.minChunkY - chunkY;

            for (int xIndex = 0; xIndex < this.chunks.length; xIndex++) {
               long[][] yChunks = this.chunks[xIndex];
               long[][] newChunks = new long[additional + yChunks.length][];

               for (int i = 0; i < additional; i++) {
                  newChunks[i] = new long[1];
               }

               System.arraycopy(yChunks, 0, newChunks, additional, yChunks.length);
               this.chunks[xIndex] = newChunks;
            }

            this.minChunkY = chunkY;
         }

         if (chunkZ < this.minChunkZ) {
            int additional = this.minChunkZ - chunkZ;

            for (int xIndex = 0; xIndex < this.chunks.length; xIndex++) {
               for (int yIndex = 0; yIndex < this.chunks[xIndex].length; yIndex++) {
                  long[] zChunks = this.chunks[xIndex][yIndex];
                  long[] newChunks = new long[additional + zChunks.length];
                  System.arraycopy(zChunks, 0, newChunks, additional, zChunks.length);
                  this.chunks[xIndex][yIndex] = newChunks;
               }
            }

            this.minChunkZ = chunkZ;
         } else {
            int xIndex = chunkX - this.minChunkX;
            int yIndex = chunkY - this.minChunkY;
            if (chunkZ - this.minChunkZ >= this.chunks[xIndex][yIndex].length) {
               int additional = chunkZ - (this.minChunkZ + this.chunks[xIndex][yIndex].length) + 1;
               int length = this.chunks[xIndex][yIndex].length;
               this.chunks[xIndex][yIndex] = Arrays.copyOf(this.chunks[xIndex][yIndex], length + additional);
            }
         }
      }
   }

   public void flip(int x, int y, int z) {
      this.ensureCapacity(x, y, z);
      int chunkX = chunkIndex(x);
      int chunkY = chunkIndex(y);
      int chunkZ = chunkIndex(z);
      int inChunkX = x - (chunkX << 2);
      int inChunkY = y - (chunkY << 2);
      int inChunkZ = z - (chunkZ << 2);
      int xOffset = chunkX - this.minChunkX;
      int yOffset = chunkY - this.minChunkY;
      int zOffset = chunkZ - this.minChunkZ;
      if ((this.chunks[xOffset][yOffset][zOffset] & 1L << index(inChunkX, inChunkY, inChunkZ)) == 0L) {
         this.count++;
      } else {
         this.count--;
      }

      this.chunks[xOffset][yOffset][zOffset] = this.chunks[xOffset][yOffset][zOffset] ^ 1L << index(inChunkX, inChunkY, inChunkZ);
   }

   public void set(int x, int y, int z) {
      this.ensureCapacity(x, y, z);
      int chunkX = chunkIndex(x);
      int chunkY = chunkIndex(y);
      int chunkZ = chunkIndex(z);
      int inChunkX = x - (chunkX << 2);
      int inChunkY = y - (chunkY << 2);
      int inChunkZ = z - (chunkZ << 2);
      int xOffset = chunkX - this.minChunkX;
      int yOffset = chunkY - this.minChunkY;
      int zOffset = chunkZ - this.minChunkZ;
      if ((this.chunks[xOffset][yOffset][zOffset] & 1L << index(inChunkX, inChunkY, inChunkZ)) == 0L) {
         this.count++;
         this.chunks[xOffset][yOffset][zOffset] = this.chunks[xOffset][yOffset][zOffset] | 1L << index(inChunkX, inChunkY, inChunkZ);
      }
   }

   public void set(int x, int y, int z, boolean value) {
      if (value) {
         this.set(x, y, z);
      } else {
         this.clear(x, y, z);
      }
   }

   public void clear(int x, int y, int z) {
      this.ensureCapacity(x, y, z);
      int chunkX = chunkIndex(x);
      int chunkY = chunkIndex(y);
      int chunkZ = chunkIndex(z);
      int inChunkX = x - (chunkX << 2);
      int inChunkY = y - (chunkY << 2);
      int inChunkZ = z - (chunkZ << 2);
      int xOffset = chunkX - this.minChunkX;
      int yOffset = chunkY - this.minChunkY;
      int zOffset = chunkZ - this.minChunkZ;
      if ((this.chunks[xOffset][yOffset][zOffset] & 1L << index(inChunkX, inChunkY, inChunkZ)) != 0L) {
         this.count--;
         this.chunks[xOffset][yOffset][zOffset] = this.chunks[xOffset][yOffset][zOffset] & ~(1L << index(inChunkX, inChunkY, inChunkZ));
      }
   }

   public void clearIncludingSize() {
      this.count = 0;
      this.chunks = null;
      this.minChunkX = 0;
      this.minChunkY = 0;
   }

   public void clear() {
      this.count = 0;
      if (this.chunks != null) {
         for (int i = 0; i < this.chunks.length; i++) {
            for (int j = 0; j < this.chunks[i].length; j++) {
               for (int k = 0; k < this.chunks.length; k++) {
                  this.chunks[i][j][k] = 0L;
               }
            }
         }
      }

      this.minChunkX = 0;
      this.minChunkY = 0;
   }

   public boolean get(int x, int y, int z) {
      if (this.chunks == null) {
         return false;
      }

      int chunkX = chunkIndex(x);
      if (chunkX >= this.minChunkX && chunkX < this.minChunkX + this.chunks.length) {
         int xOffset = chunkX - this.minChunkX;
         int chunkY = chunkIndex(y);
         if (chunkY >= this.minChunkY && chunkY < this.minChunkY + this.chunks[xOffset].length) {
            int yOffset = chunkY - this.minChunkY;
            int chunkZ = chunkIndex(z);
            if (chunkZ >= this.minChunkZ && chunkZ < this.minChunkZ + this.chunks[xOffset][yOffset].length) {
               int inChunkX = x - (chunkX << 2);
               int inChunkY = y - (chunkY << 2);
               int inChunkZ = z - (chunkZ << 2);
               int zOffset = chunkZ - this.minChunkZ;
               return (this.chunks[xOffset][yOffset][zOffset] & 1L << index(inChunkX, inChunkY, inChunkZ)) != 0L;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean isEmpty() {
      return this.count == 0;
   }

   public int count() {
      return this.count;
   }

   @Override
   public Iterator<MutableBlockPos> iterator() {
      return new ComputeNextIterator<MutableBlockPos>() {
         private final MutableBlockPos pos = new MutableBlockPos();
         private int found = 0;
         private int i = 0;
         private int j = 0;
         private int k = 0;
         private int l = 0;

         protected MutableBlockPos computeNext() {
            if (this.found >= CubeBitSet.this.count) {
               return this.end();
            }

            while (this.i < CubeBitSet.this.chunks.length) {
               while (this.j < CubeBitSet.this.chunks[this.i].length) {
                  for (; this.k < CubeBitSet.this.chunks[this.i][this.j].length; this.k++) {
                     long word = CubeBitSet.this.chunks[this.i][this.j][this.k];
                     if (word != 0L) {
                        while (this.l < 64) {
                           long data = word & 1L << this.l;
                           if (data != 0L) {
                              this.pos
                                 .set(
                                    (CubeBitSet.this.minChunkX + this.i) * 4 + this.l / 16,
                                    (CubeBitSet.this.minChunkY + this.j) * 4 + this.l / 4,
                                    (CubeBitSet.this.minChunkZ + this.k) * 4 + this.l % 4
                                 );
                              this.found++;
                              this.l++;
                              return this.pos;
                           }

                           this.l++;
                        }

                        this.l = 0;
                     }
                  }

                  this.k = 0;
                  this.j++;
               }

               this.j = 0;
               this.i++;
            }

            return this.end();
         }
      };
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder("{");
      boolean first = true;
      if (this.chunks != null) {
         for (int i = 0; i < this.chunks.length; i++) {
            for (int j = 0; j < this.chunks[i].length; j++) {
               for (int k = 0; k < this.chunks[i][j].length; k++) {
                  long word = this.chunks[i][j][k];
                  if (word != 0L) {
                     if (first) {
                        first = false;
                     } else {
                        result.append(", ");
                     }

                     for (int l = 0; l < 64; l++) {
                        long data = word & 1L << l;
                        if (data != 0L) {
                           int x = (this.minChunkX + i) * 4 + l / 16;
                           int y = (this.minChunkY + j) * 4 + l / 4;
                           int z = (this.minChunkY + k) * 4 + l % 4;
                           result.append("(").append(x).append(", ").append(y).append(", ").append(z).append(")");
                        }
                     }
                  }
               }
            }
         }
      }

      result.append("}");
      return result.toString();
   }
}
