package team.creative.creativecore.common.util.type.set;

import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import org.joml.Vector2i;
import team.creative.creativecore.common.util.type.itr.ComputeNextIterator;

public class QuadBitSet implements Iterable<Vector2i> {
   private static final int CHUNK_SIZE = 8;
   public static final int CHUNK_BITS = 3;
   private long[][] chunks;
   private int minChunkX;
   private int minChunkY;
   private int count = 0;

   private static int chunkIndex(int coord) {
      return coord < 0 ? (int)Math.floor(coord / 8.0) : coord / 8;
   }

   private static int index(int x, int y) {
      return x * 8 + y;
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
         ListTag list = nbt.getList("data", 12);
         this.chunks = new long[list.size()][];

         for (int i = 0; i < list.size(); i++) {
            this.chunks[i] = list.getLongArray(i);
         }
      }
   }

   public CompoundTag save() {
      CompoundTag nbt = new CompoundTag();
      if (this.count == 0) {
         return nbt;
      }

      nbt.putIntArray("info", new int[]{this.count, this.minChunkX, this.minChunkY});
      ListTag list = new ListTag();

      for (int i = 0; i < this.chunks.length; i++) {
         list.add(new LongArrayTag(Arrays.copyOf(this.chunks[i], this.chunks[i].length)));
      }

      nbt.put("data", list);
      return nbt;
   }

   private void init(int x, int y) {
      this.minChunkX = chunkIndex(x);
      this.minChunkY = chunkIndex(y);
      if (this.chunks == null || this.chunks.length == 0 || this.chunks[0].length == 0) {
         this.chunks = new long[1][1];
      }

      this.count = 0;
   }

   private void ensureCapacity(int x, int y) {
      if (this.count == 0) {
         this.init(x, y);
      } else {
         int chunkX = chunkIndex(x);
         int chunkY = chunkIndex(y);
         if (chunkX >= this.minChunkX) {
            if (chunkX - this.minChunkX >= this.chunks.length) {
               int additional = chunkX - (this.minChunkX + this.chunks.length) + 1;
               int length = this.chunks.length;
               this.chunks = Arrays.copyOf(this.chunks, length + additional);

               for (int i = 0; i < additional; i++) {
                  this.chunks[length + i] = new long[1];
               }
            }
         } else {
            int additional = this.minChunkX - chunkX;
            long[][] newChunks = new long[additional + this.chunks.length][];

            for (int i = 0; i < additional; i++) {
               newChunks[i] = new long[1];
            }

            System.arraycopy(this.chunks, 0, newChunks, additional, this.chunks.length);
            this.chunks = newChunks;
            this.minChunkX = chunkX;
         }

         if (chunkY < this.minChunkY) {
            int additional = this.minChunkY - chunkY;

            for (int xIndex = 0; xIndex < this.chunks.length; xIndex++) {
               long[] yChunks = this.chunks[xIndex];
               long[] newChunks = new long[additional + yChunks.length];
               System.arraycopy(yChunks, 0, newChunks, additional, yChunks.length);
               this.chunks[xIndex] = newChunks;
            }

            this.minChunkY = chunkY;
         } else {
            int xIndex = chunkX - this.minChunkX;
            if (chunkY - this.minChunkY >= this.chunks[xIndex].length) {
               this.chunks[xIndex] = Arrays.copyOf(this.chunks[xIndex], this.chunks[xIndex].length + chunkY - (this.minChunkY + this.chunks[xIndex].length) + 1);
            }
         }
      }
   }

   public void flip(int x, int y) {
      this.ensureCapacity(x, y);
      int chunkX = chunkIndex(x);
      int chunkY = chunkIndex(y);
      int inChunkX = x - (chunkX << 3);
      int inChunkY = y - (chunkY << 3);
      int xOffset = chunkX - this.minChunkX;
      int yOffset = chunkY - this.minChunkY;
      if ((this.chunks[xOffset][yOffset] & 1L << index(inChunkX, inChunkY)) == 0L) {
         this.count++;
      } else {
         this.count--;
      }

      this.chunks[xOffset][yOffset] = this.chunks[xOffset][yOffset] ^ 1L << index(inChunkX, inChunkY);
   }

   public void set(int x, int y) {
      this.ensureCapacity(x, y);
      int chunkX = chunkIndex(x);
      int chunkY = chunkIndex(y);
      int inChunkX = x - (chunkX << 3);
      int inChunkY = y - (chunkY << 3);
      int xOffset = chunkX - this.minChunkX;
      int yOffset = chunkY - this.minChunkY;
      if ((this.chunks[xOffset][yOffset] & 1L << index(inChunkX, inChunkY)) == 0L) {
         this.count++;
         this.chunks[xOffset][yOffset] = this.chunks[xOffset][yOffset] | 1L << index(inChunkX, inChunkY);
      }
   }

   public void set(int x, int y, boolean value) {
      if (value) {
         this.set(x, y);
      } else {
         this.clear(x, y);
      }
   }

   public void clear(int x, int y) {
      this.ensureCapacity(x, y);
      int chunkX = chunkIndex(x);
      int chunkY = chunkIndex(y);
      int inChunkX = x - (chunkX << 3);
      int inChunkY = y - (chunkY << 3);
      int xOffset = chunkX - this.minChunkX;
      int yOffset = chunkY - this.minChunkY;
      if ((this.chunks[xOffset][yOffset] & 1L << index(inChunkX, inChunkY)) != 0L) {
         this.count--;
         this.chunks[xOffset][yOffset] = this.chunks[xOffset][yOffset] & ~(1L << index(inChunkX, inChunkY));
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
            Arrays.fill(this.chunks[i], 0L);
         }
      }

      this.minChunkX = 0;
      this.minChunkY = 0;
   }

   public boolean get(int x, int y) {
      if (this.chunks == null) {
         return false;
      }

      int chunkX = chunkIndex(x);
      if (chunkX >= this.minChunkX && chunkX < this.minChunkX + this.chunks.length) {
         int xOffset = chunkX - this.minChunkX;
         int chunkY = chunkIndex(y);
         if (chunkY >= this.minChunkY && chunkY < this.minChunkY + this.chunks[xOffset].length) {
            int inChunkX = x - (chunkX << 3);
            int inChunkY = y - (chunkY << 3);
            int yOffset = chunkY - this.minChunkY;
            return (this.chunks[xOffset][yOffset] & 1L << index(inChunkX, inChunkY)) != 0L;
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
   public Iterator<Vector2i> iterator() {
      return new ComputeNextIterator<Vector2i>() {
         private final Vector2i vec = new Vector2i();
         private int found = 0;
         private int i = 0;
         private int j = 0;
         private int k = 0;

         protected Vector2i computeNext() {
            if (this.found >= QuadBitSet.this.count) {
               return this.end();
            }

            while (this.i < QuadBitSet.this.chunks.length) {
               for (; this.j < QuadBitSet.this.chunks[this.i].length; this.j++) {
                  long word = QuadBitSet.this.chunks[this.i][this.j];
                  if (word != 0L) {
                     while (this.k < 64) {
                        long data = word & 1L << this.k;
                        if (data != 0L) {
                           this.vec.set((QuadBitSet.this.minChunkX + this.i) * 8 + this.k / 8, (QuadBitSet.this.minChunkY + this.j) * 8 + this.k % 8);
                           this.found++;
                           this.k++;
                           return this.vec;
                        }

                        this.k++;
                     }

                     this.k = 0;
                  }
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
               long word = this.chunks[i][j];
               if (word != 0L) {
                  if (first) {
                     first = false;
                  } else {
                     result.append(", ");
                  }

                  for (int k = 0; k < 64; k++) {
                     long data = word & 1L << k;
                     if (data != 0L) {
                        int x = (this.minChunkX + i) * 8 + k / 8;
                        int y = (this.minChunkY + j) * 8 + k % 8;
                        result.append("(").append(x).append(", ").append(y).append(")");
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
