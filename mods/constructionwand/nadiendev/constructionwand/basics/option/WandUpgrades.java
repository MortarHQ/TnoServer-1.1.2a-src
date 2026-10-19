package nadiendev.constructionwand.basics.option;

import java.util.ArrayList;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandUpgrade;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class WandUpgrades<T extends IWandUpgrade> {
   protected final CompoundTag tag;
   protected final String key;
   protected final ArrayList<T> upgrades;
   protected final T dval;

   public WandUpgrades(CompoundTag tag, String key, T dval) {
      this.tag = tag;
      this.key = key;
      this.dval = dval;
      this.upgrades = new ArrayList<>();
      if (dval != null) {
         this.upgrades.add(0, dval);
      }

      this.deserialize();
   }

   protected void deserialize() {
      ListTag listnbt = this.tag.getList(this.key, 8);
      boolean require_fix = false;

      for (int i = 0; i < listnbt.size(); i++) {
         String str = listnbt.getString(i);
         Item item = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(str));

         try {
            T data = (T)item;
            this.upgrades.add(data);
         } catch (ClassCastException e) {
            ConstructionWand.LOGGER.warn("Invalid wand upgrade: " + str);
            require_fix = true;
         }
      }

      if (require_fix) {
         this.serialize();
      }
   }

   protected void serialize() {
      ListTag listnbt = new ListTag();

      for (T item : this.upgrades) {
         if (item != this.dval) {
            listnbt.add(StringTag.valueOf(item.getRegistryName().toString()));
         }
      }

      this.tag.put(this.key, listnbt);
   }

   public boolean addUpgrade(T upgrade) {
      if (this.hasUpgrade(upgrade)) {
         return false;
      }

      this.upgrades.add(upgrade);
      this.serialize();
      return true;
   }

   public boolean hasUpgrade(T upgrade) {
      return this.upgrades.contains(upgrade);
   }

   public ArrayList<T> getUpgrades() {
      return this.upgrades;
   }
}
