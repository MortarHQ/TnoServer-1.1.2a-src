package team.creative.creativecore.common.util.filter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import team.creative.creativecore.common.util.CompoundSerializer;
import team.creative.creativecore.common.util.registry.NamedTypeRegistry;
import team.creative.creativecore.common.util.registry.exception.RegistryException;

public class FilterSerializer {
   private final NamedTypeRegistry<Filter> REGISTRY = new NamedTypeRegistry<Filter>().addConstructorPattern(CompoundTag.class);

   public <V extends Filter & CompoundSerializer> FilterSerializer register(String id, Class<V> clazz) {
      this.REGISTRY.register(id, clazz);
      return this;
   }

   public CompoundTag write(Filter filter) throws RegistryException {
      if (filter instanceof Filter.FilterAnd) {
         CompoundTag tag = new CompoundTag();
         ListTag list = new ListTag();

         for (Filter child : ((Filter.FilterAnd)filter).filters()) {
            list.add(this.write(child));
         }

         tag.put("c", list);
         tag.putString("t", "&");
         return tag;
      } else if (!(filter instanceof Filter.FilterOr)) {
         if (filter instanceof Filter.FilterNot) {
            CompoundTag tag = new CompoundTag();
            tag.put("c", this.write(((Filter.FilterNot)filter).filter()));
            tag.putString("t", "!");
            return tag;
         } else if (filter instanceof CompoundSerializer) {
            CompoundTag tag = ((CompoundSerializer)filter).write();
            tag.putString("t", this.REGISTRY.getId(filter));
            return tag;
         } else {
            throw new RegistryException("Type not registered " + filter.getClass());
         }
      } else {
         CompoundTag tag = new CompoundTag();
         ListTag list = new ListTag();

         for (Filter child : ((Filter.FilterOr)filter).filters()) {
            list.add(this.write(child));
         }

         tag.put("c", list);
         tag.putString("t", "+");
         return tag;
      }
   }

   public Filter read(CompoundTag tag) throws RegistryException {
      String type = tag.getString("t");
      switch (type) {
         case "&":
            ListTag list = tag.getList(type, 10);
            Filter[] filters = new Filter[list.size()];

            for (int i = 0; i < list.size(); i++) {
               filters[i] = this.read(list.getCompound(i));
            }

            return new Filter.FilterAnd(filters);
         case "+":
            ListTag list = tag.getList(type, 10);
            Filter[] filters = new Filter[list.size()];

            for (int i = 0; i < list.size(); i++) {
               filters[i] = this.read(list.getCompound(i));
            }

            return new Filter.FilterOr(filters);
         case "!":
            return new Filter.FilterNot(this.read(tag.getCompound("c")));
         default:
            return this.REGISTRY.create(type, tag);
      }
   }
}
