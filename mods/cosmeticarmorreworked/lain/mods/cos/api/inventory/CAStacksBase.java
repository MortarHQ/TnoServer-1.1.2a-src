package lain.mods.cos.api.inventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CAStacksBase extends ItemStackHandler {
   protected final Map<String, Set<String>> hidden = new HashMap<>();
   protected boolean[] isSkinArmor = new boolean[this.stacks.size()];

   public CAStacksBase() {
      this(4);
   }

   public CAStacksBase(int size) {
      super(size);
   }

   public void deserializeNBT(Provider provider, CompoundTag nbt) {
      this.setSize(nbt.contains("Size", 3) ? nbt.getInt("Size") : this.stacks.size());
      ListTag tagList = nbt.getList("Items", 10);

      for (int i = 0; i < tagList.size(); i++) {
         CompoundTag itemTags = tagList.getCompound(i);
         int slot = itemTags.getInt("Slot");
         if (slot >= 0 && slot < this.stacks.size()) {
            if (itemTags.contains("id")) {
               ItemStack.parse(provider, itemTags).ifPresent(stack -> this.stacks.set(slot, stack));
            }

            if (itemTags.contains("isSkinArmor")) {
               this.isSkinArmor[slot] = itemTags.getBoolean("isSkinArmor");
            }
         }
      }

      this.hidden.clear();
      Arrays.stream(nbt.getString("Hidden").split("\u0000")).forEach(str -> {
         int ix = str.indexOf(":");
         if (ix != -1) {
            this.hidden.computeIfAbsent(str.substring(0, ix), key -> new HashSet<>()).add(str.substring(ix + 1));
         }
      });
      this.onLoad();
   }

   public void forEachHidden(BiConsumer<String, String> consumer) {
      for (String modid : this.hidden.keySet()) {
         for (String identifier : this.hidden.get(modid)) {
            consumer.accept(modid, identifier);
         }
      }
   }

   public boolean isHidden(String modid, String identifier) {
      return this.hidden.getOrDefault(modid, Collections.emptySet()).contains(identifier);
   }

   public boolean isSkinArmor(int slot) {
      this.validateSlotIndex(slot);
      return this.isSkinArmor[slot];
   }

   public CompoundTag serializeNBT(Provider provider) {
      ListTag nbtTagList = new ListTag();

      for (int i = 0; i < this.stacks.size(); i++) {
         if (!((ItemStack)this.stacks.get(i)).isEmpty() || this.isSkinArmor[i]) {
            CompoundTag itemTag = new CompoundTag();
            itemTag.putInt("Slot", i);
            if (!((ItemStack)this.stacks.get(i)).isEmpty()) {
               itemTag = (CompoundTag)((ItemStack)this.stacks.get(i)).save(provider, itemTag);
            }

            if (this.isSkinArmor[i]) {
               itemTag.putBoolean("isSkinArmor", true);
            }

            nbtTagList.add(itemTag);
         }
      }

      CompoundTag nbt = new CompoundTag();
      nbt.put("Items", nbtTagList);
      nbt.putInt("Size", this.stacks.size());
      nbt.putString(
         "Hidden",
         this.hidden
            .entrySet()
            .stream()
            .map(entry -> entry.getValue().stream().map(value -> entry.getKey() + ":" + value).collect(Collectors.joining("\u0000")))
            .collect(Collectors.joining("\u0000"))
      );
      return nbt;
   }

   public boolean setHidden(String modid, String identifier, boolean set) {
      return set
         ? this.hidden.computeIfAbsent(modid, key -> new HashSet<>()).add(identifier)
         : this.hidden.getOrDefault(modid, Collections.emptySet()).remove(identifier);
   }

   public void setSize(int size) {
      super.setSize(size);
      this.isSkinArmor = new boolean[this.stacks.size()];
   }

   public void setSkinArmor(int slot, boolean enabled) {
      this.validateSlotIndex(slot);
      if (this.isSkinArmor[slot] != enabled) {
         this.isSkinArmor[slot] = enabled;
         this.onContentsChanged(slot);
      }
   }
}
