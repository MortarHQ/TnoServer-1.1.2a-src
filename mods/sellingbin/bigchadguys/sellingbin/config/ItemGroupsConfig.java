package bigchadguys.sellingbin.config;

import bigchadguys.sellingbin.data.item.ItemPredicate;
import bigchadguys.sellingbin.data.item.PartialItem;
import bigchadguys.sellingbin.data.item.PartialStack;
import bigchadguys.sellingbin.data.nbt.PartialCompoundNbt;
import com.google.gson.annotations.Expose;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public class ItemGroupsConfig extends FileConfig {
   @Expose
   private Map<ResourceLocation, Set<ItemPredicate>> groups;

   @Override
   public String getPath() {
      return "item_groups";
   }

   public boolean hasGroup(ResourceLocation groupId) {
      return this.groups.containsKey(groupId);
   }

   public Set<ItemPredicate> getGroup(ResourceLocation groupId) {
      return this.groups.getOrDefault(groupId, new HashSet<>());
   }

   public boolean isInGroup(ResourceLocation groupId, PartialItem item, PartialCompoundNbt nbt) {
      for (ItemPredicate predicate : this.groups.getOrDefault(groupId, new HashSet<>())) {
         if (predicate.test(item, nbt)) {
            return true;
         }
      }

      return false;
   }

   public boolean isInGroup(ResourceLocation groupId, PartialStack stack) {
      return this.isInGroup(groupId, stack.getItem(), stack.getNbt());
   }

   @Override
   protected void reset() {
      this.groups = new LinkedHashMap<>();
   }
}
