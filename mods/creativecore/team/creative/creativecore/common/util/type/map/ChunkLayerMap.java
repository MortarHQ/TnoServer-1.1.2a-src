package team.creative.creativecore.common.util.type.map;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import team.creative.creativecore.common.util.type.itr.ComputeNextIterator;
import team.creative.creativecore.common.util.type.itr.FunctionIterator;
import team.creative.creativecore.common.util.type.list.Tuple;

public class ChunkLayerMap<T> implements Iterable<T> {
   private static final int LAYERS_COUNT = RenderType.chunkBufferLayers().size();
   private static final Object2IntMap<RenderType> LAYERS_INDEX_MAP = new Object2IntArrayMap();
   private final T[] content;

   public ChunkLayerMap(ChunkLayerMap<T> map) {
      this.content = (T[])Arrays.copyOf(map.content, LAYERS_COUNT);
   }

   public ChunkLayerMap(Function<RenderType, T> factory) {
      this.content = (T[])(new Object[LAYERS_COUNT]);

      for (int i = 0; i < this.content.length; i++) {
         this.content[i] = factory.apply((RenderType)RenderType.chunkBufferLayers().get(i));
      }
   }

   public ChunkLayerMap() {
      this.content = (T[])(new Object[LAYERS_COUNT]);
   }

   private int index(RenderType layer) {
      return LAYERS_INDEX_MAP.getInt(layer);
   }

   public T get(RenderType layer) {
      return this.content[this.index(layer)];
   }

   public T put(RenderType layer, T element) {
      int index = this.index(layer);
      T result = this.content[index];
      this.content[index] = element;
      return result;
   }

   public T remove(RenderType layer) {
      int index = this.index(layer);
      T result = this.content[index];
      this.content[index] = null;
      return result;
   }

   public void clear() {
      Arrays.fill(this.content, null);
   }

   public Iterable<Tuple<RenderType, T>> tuples() {
      return new ComputeNextIterator<Tuple<RenderType, T>>() {
         private int index;
         private final Tuple<RenderType, T> pair = new Tuple<>(null, null);

         protected Tuple<RenderType, T> computeNext() {
            while (this.index < ChunkLayerMap.this.content.length && ChunkLayerMap.this.content[this.index] == null) {
               this.index++;
            }

            if (this.index >= ChunkLayerMap.this.content.length) {
               return (Tuple<RenderType, T>)this.end();
            }

            this.pair.key = (RenderType)RenderType.chunkBufferLayers().get(this.index);
            this.pair.value = ChunkLayerMap.this.content[this.index];
            this.index++;
            return this.pair;
         }
      };
   }

   public boolean containsKey(RenderType layer) {
      return this.get(layer) != null;
   }

   @Override
   public Iterator<T> iterator() {
      return new ComputeNextIterator<T>() {
         private int index;

         @Override
         protected T computeNext() {
            while (this.index < ChunkLayerMap.this.content.length && ChunkLayerMap.this.content[this.index] == null) {
               this.index++;
            }

            if (this.index >= ChunkLayerMap.this.content.length) {
               return (T)this.end();
            }

            T result = ChunkLayerMap.this.content[this.index];
            this.index++;
            return result;
         }
      };
   }

   public int size() {
      int size = 0;

      for (int i = 0; i < this.content.length; i++) {
         if (this.content[i] != null) {
            size++;
         }
      }

      return size;
   }

   public boolean isEmpty() {
      for (int i = 0; i < this.content.length; i++) {
         if (this.content[i] != null) {
            return false;
         }
      }

      return true;
   }

   @Override
   public String toString() {
      return "[" + String.join(",", () -> new FunctionIterator<>(this, Object::toString)) + "]";
   }

   static {
      int i = 0;

      for (RenderType layer : RenderType.chunkBufferLayers()) {
         LAYERS_INDEX_MAP.put(layer, i);
         i++;
      }
   }
}
