package team.creative.creativecore.common.util.type.map;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.client.renderer.RenderType;
import team.creative.creativecore.common.util.type.itr.ComputeNextIterator;
import team.creative.creativecore.common.util.type.list.Tuple;

public class ChunkLayerMapList<T> implements Iterable<T> {
   private static final int LAYERS_COUNT = RenderType.chunkBufferLayers().size();
   private static final Object2IntMap<RenderType> LAYERS_INDEX_MAP = new Object2IntArrayMap();
   private final List<T>[] content;

   public ChunkLayerMapList(ChunkLayerMapList<T> map) {
      this.content = Arrays.copyOf(map.content, LAYERS_COUNT);
   }

   public ChunkLayerMapList() {
      this.content = new List[LAYERS_COUNT];
   }

   private int index(RenderType layer) {
      return LAYERS_INDEX_MAP.getInt(layer);
   }

   public List<T> getOrCreate(RenderType layer) {
      List<T> result = this.content[this.index(layer)];
      if (result == null) {
         this.content[this.index(layer)] = result = new ArrayList<>();
      }

      return result;
   }

   public void add(RenderType layer, T element) {
      this.getOrCreate(layer).add(element);
   }

   public List<T> remove(RenderType layer) {
      int index = this.index(layer);
      List<T> result = this.content[index];
      this.content[index] = null;
      return result;
   }

   public void clear() {
      Arrays.fill(this.content, null);
   }

   public void consumeEachLayer(BiConsumer<RenderType, List> consumer) {
      ArrayList<T> tempList = new ArrayList<>();

      for (RenderType layer : RenderType.chunkBufferLayers()) {
         consumer.accept(layer, tempList);
         if (!tempList.isEmpty()) {
            this.content[this.index(layer)] = tempList;
            tempList = new ArrayList<>();
         }
      }
   }

   public Iterable<Tuple<RenderType, List<T>>> tuples() {
      return new ComputeNextIterator<Tuple<RenderType, List<T>>>() {
         private int index;
         private final Tuple<RenderType, List<T>> pair = new Tuple<>(null, null);

         protected Tuple<RenderType, List<T>> computeNext() {
            while (this.index < ChunkLayerMapList.this.content.length && ChunkLayerMapList.this.content[this.index] == null) {
               this.index++;
            }

            if (this.index >= ChunkLayerMapList.this.content.length) {
               return (Tuple<RenderType, List<T>>)this.end();
            }

            this.pair.key = (RenderType)RenderType.chunkBufferLayers().get(this.index);
            this.pair.value = ChunkLayerMapList.this.content[this.index];
            this.index++;
            return this.pair;
         }
      };
   }

   @Override
   public Iterator<T> iterator() {
      return new ComputeNextIterator<T>() {
         private int index;
         private Iterator<T> itr;

         @Override
         protected T computeNext() {
            if (this.itr != null && this.itr.hasNext()) {
               return this.itr.next();
            }

            while (
               this.index < ChunkLayerMapList.this.content.length
                  && (ChunkLayerMapList.this.content[this.index] == null || ChunkLayerMapList.this.content[this.index].isEmpty())
            ) {
               this.index++;
            }

            if (this.index >= ChunkLayerMapList.this.content.length) {
               return (T)this.end();
            }

            this.itr = ChunkLayerMapList.this.content[this.index].iterator();
            this.index++;
            return this.itr.next();
         }
      };
   }

   public boolean containsKey(RenderType layer) {
      return this.content[this.index(layer)] != null;
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
         if (this.content[i] != null && !this.content[i].isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public String toString() {
      return "[" + Arrays.toString(this.content) + "]";
   }

   static {
      int i = 0;

      for (RenderType layer : RenderType.chunkBufferLayers()) {
         LAYERS_INDEX_MAP.put(layer, i);
         i++;
      }
   }
}
