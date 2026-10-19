package bigchadguys.sellingbin.data.adapter.util;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.IAdapter;
import bigchadguys.sellingbin.data.adapter.ISimpleAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import bigchadguys.sellingbin.util.WeightedTree;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public abstract class WeightedTreeAdapter<T, W extends WeightedTree<T>> implements ISimpleAdapter<W, ListTag, JsonArray> {
   public abstract W create();

   public abstract String getName(T var1);

   public abstract IAdapter getAdapter(String var1);

   public void writeBits(W value, BitBuffer buffer) {
      if (value == null) {
         buffer.writeBoolean(false);
      } else {
         buffer.writeBoolean(true);
         Adapters.INT_SEGMENTED_7.writeBits(value.getChildren().size(), buffer);
         value.getChildren().forEach((branch, weight) -> {
            buffer.writeBoolean(branch instanceof WeightedTree);
            buffer.writeDouble(weight);
            if (branch instanceof WeightedTree) {
               this.writeBits((W)branch, buffer);
            } else {
               String name = this.getName((T)branch);
               buffer.writeString(name);
               this.getAdapter(name).writeBits(branch, buffer, null);
            }
         });
      }
   }

   @Override
   public Optional<W> readBits(BitBuffer buffer) {
      if (!buffer.readBoolean()) {
         return Optional.empty();
      }

      int groupSize = Adapters.INT_SEGMENTED_7.readBits(buffer).orElseThrow();
      W tree = this.create();

      for (int i = 0; i < groupSize; i++) {
         boolean isTree = buffer.readBoolean();
         double weight = buffer.readDouble();
         if (isTree) {
            this.readBits(buffer).ifPresent(w -> tree.addTree(w, weight));
         } else {
            String name = buffer.readString();
            IAdapter adapter = this.getAdapter(name);
            if (adapter != null) {
               adapter.readBits(buffer, null).ifPresent(o -> tree.addLeaf((T)o, weight));
            }
         }
      }

      return Optional.of(tree);
   }

   public Optional<ListTag> writeNbt(W value) {
      if (value == null) {
         return Optional.empty();
      }

      ListTag list = new ListTag();
      value.getChildren().forEach((branch, weight) -> {
         CompoundTag compound = new CompoundTag();
         compound.putDouble("weight", weight);
         if (branch instanceof WeightedTree) {
            this.writeNbt((W)branch).ifPresent(element -> compound.put("pool", element));
         } else {
            String name = this.getName((T)branch);
            this.getAdapter(name).writeNbt(branch, null).ifPresent(element -> compound.put(name, (Tag)element));
         }

         list.add(compound);
      });
      return Optional.of(list);
   }

   public Optional<W> readNbt(ListTag nbt) {
      if (nbt == null) {
         return Optional.empty();
      }

      W tree = this.create();

      for (int i = 0; i < nbt.size(); i++) {
         CompoundTag element = nbt.getCompound(i);
         double weight = element.getDouble("weight");
         HashSet<String> keys = new HashSet<>(element.getAllKeys());
         keys.remove("weight");
         String name = keys.iterator().next();
         if (name.equals("pool")) {
            this.readNbt(element.getList(name, 10)).ifPresent(w -> tree.addTree(w, weight));
         } else {
            IAdapter adapter = this.getAdapter(name);
            if (adapter != null) {
               adapter.readNbt(element.get(name), null).ifPresent(o -> tree.addLeaf((T)o, weight));
            }
         }
      }

      return Optional.of(tree);
   }

   public Optional<JsonArray> writeJson(W value) {
      if (value == null) {
         return Optional.empty();
      }

      JsonArray array = new JsonArray();
      value.getChildren().forEach((branch, weight) -> {
         if (branch instanceof WeightedTree) {
            object = new JsonObject();
            this.writeJson((W)branch).ifPresent(elementx -> object.add("pool", elementx));
         } else {
            String name = this.getName((T)branch);
            Object element = this.getAdapter(name).writeJson(branch, null).orElse(null);
            if (name == null) {
               if (!(element instanceof JsonObject object)) {
                  throw new UnsupportedOperationException("Cannot write " + element);
               }
            } else {
               object = new JsonObject();
               object.add(name, (JsonElement)element);
            }
         }

         object.addProperty("weight", weight);
         array.add(object);
      });
      return Optional.of(array);
   }

   public Optional<W> readJson(JsonArray json) {
      if (json == null) {
         return Optional.empty();
      }

      W tree = this.create();

      for (int i = 0; i < json.size(); i++) {
         JsonObject element = json.get(i).getAsJsonObject();
         double weight = element.get("weight").getAsDouble();
         String name = null;
         if (element.keySet().size() == 2) {
            HashSet<String> keys = new HashSet<>(element.keySet());
            keys.remove("weight");
            name = keys.iterator().next();
         }

         if (name.equals("pool")) {
            this.readJson(element.get(name).getAsJsonArray()).ifPresent(w -> tree.addTree(w, weight));
         } else {
            IAdapter adapter = this.getAdapter(name);
            if (adapter != null) {
               adapter.readJson((JsonElement)(name == null ? element : element.get(name)), null).ifPresent(o -> tree.addLeaf((T)o, weight));
            }
         }
      }

      return Optional.of(tree);
   }
}
