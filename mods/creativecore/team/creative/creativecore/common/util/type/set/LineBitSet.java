package team.creative.creativecore.common.util.type.set;

import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.nbt.CompoundTag;
import team.creative.creativecore.common.util.type.itr.ComputeNextIterator;

public class LineBitSet implements Iterable<Integer> {
   private static final int CHUNK_SIZE = 64;
   public static final int CHUNK_BITS = 6;
   private long[] chunks;
   private int minChunk;
   private int count = 0;

   private static int chunkIndex(int coord) {
      return coord < 0 ? (int)Math.floor(coord / 64.0) : coord / 64;
   }

   public void load(CompoundTag nbt) {
      if (!nbt.contains("info")) {
         this.clearIncludingSize();
      } else {
         int[] info = nbt.getIntArray("info");
         if (info.length != 2) {
            throw new IllegalArgumentException("Data is not valid " + nbt);
         }

         this.count = info[0];
         this.minChunk = info[1];
         this.chunks = nbt.getLongArray("data");
      }
   }

   public CompoundTag save() {
      CompoundTag nbt = new CompoundTag();
      if (this.count == 0) {
         return nbt;
      }

      nbt.putIntArray("info", new int[]{this.count, this.minChunk});
      nbt.putLongArray("data", (long[])this.chunks.clone());
      return nbt;
   }

   private void init(int x) {
      this.minChunk = chunkIndex(x);
      if (this.chunks == null || this.chunks.length == 0) {
         this.chunks = new long[1];
      }

      this.count = 0;
   }

   private void ensureCapacity(int x) {
      if (this.count == 0) {
         this.init(x);
      } else {
         int chunk = chunkIndex(x);
         if (chunk < this.minChunk) {
            int additional = this.minChunk - chunk;
            long[] newChunks = new long[additional + this.chunks.length];
            System.arraycopy(this.chunks, 0, newChunks, additional, this.chunks.length);
            this.chunks = newChunks;
            this.minChunk = chunk;
         } else if (chunk - this.minChunk >= this.chunks.length) {
            int additional = chunk - (this.minChunk + this.chunks.length) + 1;
            int length = this.chunks.length;
            this.chunks = Arrays.copyOf(this.chunks, length + additional);
         }
      }
   }

   public void flip(int x) {
      this.ensureCapacity(x);
      int chunk = chunkIndex(x);
      int inChunk = x - (chunk << 6);
      int offset = chunk - this.minChunk;
      if ((this.chunks[offset] & 1L << inChunk) == 0L) {
         this.count++;
      } else {
         this.count--;
      }

      this.chunks[offset] = this.chunks[offset] ^ 1L << inChunk;
   }

   public void set(int x) {
      this.ensureCapacity(x);
      int chunk = chunkIndex(x);
      int inChunk = x - (chunk << 6);
      int offset = chunk - this.minChunk;
      if ((this.chunks[offset] & 1L << inChunk) == 0L) {
         this.count++;
         this.chunks[offset] = this.chunks[offset] | 1L << inChunk;
      }
   }

   public void set(int x, boolean value) {
      if (value) {
         this.set(x);
      } else {
         this.clear(x);
      }
   }

   public void clear(int x) {
      this.ensureCapacity(x);
      int chunk = chunkIndex(x);
      int inChunk = x - (chunk << 6);
      int offset = chunk - this.minChunk;
      if ((this.chunks[offset] & 1L << inChunk) == 0L) {
         this.count++;
         this.chunks[offset] = this.chunks[offset] & ~(1L << inChunk);
      }
   }

   public void clearIncludingSize() {
      this.count = 0;
      this.chunks = null;
      this.minChunk = 0;
   }

   public void clear() {
      this.count = 0;
      if (this.chunks != null) {
         Arrays.fill(this.chunks, 0L);
      }

      this.minChunk = 0;
   }

   public boolean get(int x) {
      if (this.chunks == null) {
         return false;
      } else {
         int chunk = chunkIndex(x);
         if (chunk >= this.minChunk && chunk < this.minChunk + this.chunks.length) {
            int offset = chunk - this.minChunk;
            int inChunk = x - (chunk << 6);
            return (this.chunks[offset] & 1L << inChunk) != 0L;
         } else {
            return false;
         }
      }
   }

   public boolean isEmpty() {
      return this.count == 0;
   }

   public int count() {
      return this.count;
   }

   @Override
   public Iterator<Integer> iterator() {
      return new ComputeNextIterator<Integer>() {
         private int found = 0;
         private int i = 0;
         private int k = 0;

         protected Integer computeNext() {
            if (this.found >= LineBitSet.this.count) {
               return this.end();
            }

            for (; this.i < LineBitSet.this.chunks.length; this.i++) {
               long word = LineBitSet.this.chunks[this.i];
               if (word != 0L) {
                  while (this.k < 64) {
                     long data = word & 1L << this.k;
                     if (data != 0L) {
                        int result = (LineBitSet.this.minChunk + this.i) * 64 + this.k;
                        this.found++;
                        this.k++;
                        return result;
                     }

                     this.k++;
                  }

                  this.k = 0;
               }
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
            long word = this.chunks[i];
            if (word != 0L) {
               for (int k = 0; k < 64; k++) {
                  long data = word & 1L << k;
                  if (data != 0L) {
                     int x = (this.minChunk + i) * 64 + k;
                     if (first) {
                        first = false;
                     } else {
                        result.append(", ");
                     }

                     result.append(x);
                  }
               }
            }
         }
      }

      result.append("}");
      return result.toString();
   }
}
