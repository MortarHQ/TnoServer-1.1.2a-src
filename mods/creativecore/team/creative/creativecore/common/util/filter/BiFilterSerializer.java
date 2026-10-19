package team.creative.creativecore.common.util.filter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import team.creative.creativecore.common.util.CompoundSerializer;
import team.creative.creativecore.common.util.registry.NamedTypeRegistry;
import team.creative.creativecore.common.util.registry.exception.RegistryException;

public class BiFilterSerializer {
   private final NamedTypeRegistry<BiFilter> REGISTRY = new NamedTypeRegistry<BiFilter>().addConstructorPattern(CompoundTag.class);

   public <V extends BiFilter & CompoundSerializer> BiFilterSerializer register(String id, Class<V> clazz) {
      this.REGISTRY.register(id, clazz);
      return this;
   }

   public CompoundTag write(BiFilter filter) throws RegistryException {
      if (filter instanceof BiFilter.BiFilterAnd biFilter) {
         CompoundTag tag = new CompoundTag();
         ListTag list = new ListTag();

         for (BiFilter child : biFilter.filters()) {
            list.add(this.write(child));
         }

         tag.put("c", list);
         tag.putString("t", "&");
         return tag;
      } else if (!(filter instanceof BiFilter.BiFilterOr biFilter)) {
         if (filter instanceof BiFilter.BiFilterNot biFilter) {
            CompoundTag tag = new CompoundTag();
            tag.put("c", this.write(biFilter.filter()));
            tag.putString("t", "!");
            return tag;
         } else if (filter instanceof CompoundSerializer serializer) {
            CompoundTag tag = serializer.write();
            tag.putString("t", this.REGISTRY.getId(filter));
            return tag;
         } else {
            throw new RegistryException("Type not registered " + filter.getClass());
         }
      } else {
         CompoundTag tag = new CompoundTag();
         ListTag list = new ListTag();

         for (BiFilter child : biFilter.filters()) {
            list.add(this.write(child));
         }

         tag.put("c", list);
         tag.putString("t", "+");
         return tag;
      }
   }

   public BiFilter read(CompoundTag tag) throws RegistryException {
      String type = tag.getString("t");
      switch (type) {
         case "&":
            ListTag list = tag.getList(type, 10);
            BiFilter[] filters = new BiFilter[list.size()];

            for (int i = 0; i < list.size(); i++) {
               filters[i] = this.read(list.getCompound(i));
            }

            return new BiFilter.BiFilterAnd(filters);
         case "+":
            ListTag list = tag.getList(type, 10);
            BiFilter[] filters = new BiFilter[list.size()];

            for (int i = 0; i < list.size(); i++) {
               filters[i] = this.read(list.getCompound(i));
            }

            return new BiFilter.BiFilterOr(filters);
         case "!":
            return new BiFilter.BiFilterNot(this.read(tag.getCompound("c")));
         default:
            return this.REGISTRY.create(type, tag);
      }
   }
}
