package nadiendev.constructionwand.basics.option;

import nadiendev.constructionwand.api.IWandUpgrade;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class WandUpgradesSelectable<T extends IWandUpgrade> extends WandUpgrades<T> implements IOption<T> {
   private final ItemStack stack;
   private final DataComponentType<CompoundTag> componentType;
   private byte selector;

   public WandUpgradesSelectable(ItemStack stack, DataComponentType<CompoundTag> componentType, String key, T dval) {
      super((CompoundTag)stack.getOrDefault(componentType, new CompoundTag()), key, dval);
      this.stack = stack;
      this.componentType = componentType;
   }

   @Override
   public DataComponentType<?> getComponentType() {
      return this.componentType;
   }

   @Override
   public String getKey() {
      return this.key;
   }

   @Override
   public String getValueString() {
      return this.get().getRegistryName().toString();
   }

   @Override
   public void setValueString(String val) {
      for (byte i = 0; i < this.upgrades.size(); i++) {
         if (this.upgrades.get(i).getRegistryName().toString().equals(val)) {
            this.selector = i;
            this.serializeSelector();
            return;
         }
      }
   }

   @Override
   public boolean isEnabled() {
      return this.upgrades.size() > 1;
   }

   public void set(T val) {
      this.selector = (byte)this.upgrades.indexOf(val);
      this.fixSelector();
      this.serializeSelector();
   }

   public T get() {
      this.fixSelector();
      return this.upgrades.get(this.selector);
   }

   public T next(boolean dir) {
      this.selector++;
      this.fixSelector();
      this.serializeSelector();
      return this.get();
   }

   private void fixSelector() {
      if (this.selector < 0 || this.selector >= this.upgrades.size()) {
         this.selector = 0;
      }
   }

   @Override
   protected void deserialize() {
      super.deserialize();
      this.selector = this.tag.getByte(this.key + "_sel");
      this.fixSelector();
   }

   @Override
   protected void serialize() {
      super.serialize();
      this.serializeSelector();
   }

   private void serializeSelector() {
      this.tag.putByte(this.key + "_sel", this.selector);
      this.stack.set(this.componentType, this.tag);
   }
}
