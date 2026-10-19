package twilightforest.components.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.custom.ItemDisplays;
import twilightforest.item.travellers_gear.modifiers.display.ItemDisplayType;
import twilightforest.util.TFItemStackUtils;

public class ItemDisplayContents implements TooltipComponent {
   public static final List<DeferredHolder<ItemDisplayType, ItemDisplayType>> LAYOUT = List.of(
      ItemDisplays.MAP, ItemDisplays.MAP, ItemDisplays.MAP, ItemDisplays.COMPASS, ItemDisplays.CLOCK, ItemDisplays.MOON_DIAL
   );
   private static final int FIRST_MAP_SLOT_INDEX = LAYOUT.indexOf(ItemDisplays.MAP);
   public static final ItemDisplayContents EMPTY = new ItemDisplayContents(LAYOUT.size(), FIRST_MAP_SLOT_INDEX);
   public static final Codec<ItemDisplayContents> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ItemDisplayContents.DisplaySlot.CODEC.listOf().fieldOf("slots").forGetter(ItemDisplayContents::asSlots),
            Codec.INT.fieldOf("chosen_map_slot").forGetter(ItemDisplayContents::findActiveMapSlot)
         )
         .apply(instance, ItemDisplayContents::fromSlots)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, ItemDisplayContents> STREAM_CODEC = StreamCodec.composite(
      ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()),
      contents -> new ArrayList(contents.items),
      ByteBufCodecs.VAR_INT,
      contents -> contents.chosenMapSlot,
      ItemDisplayContents::new
   );
   final NonNullList<ItemStack> items;
   public final int chosenMapSlot;

   private ItemDisplayContents(int size, int chosenMapSlot) {
      this.items = NonNullList.withSize(size, ItemStack.EMPTY);
      this.chosenMapSlot = chosenMapSlot;
   }

   private ItemDisplayContents(List<ItemStack> items, int chosenMapSlot) {
      this.items = NonNullList.copyOf(items);
      this.chosenMapSlot = chosenMapSlot;
   }

   private void copyInto(NonNullList<ItemStack> list) {
      for (int i = 0; i < list.size(); i++) {
         ItemStack itemstack = i < this.items.size() ? (ItemStack)this.items.get(i) : ItemStack.EMPTY;
         list.set(i, itemstack.copy());
      }
   }

   private static ItemDisplayContents fromSlots(List<ItemDisplayContents.DisplaySlot> slots, int chosenMapSlot) {
      OptionalInt optionalint = slots.stream().mapToInt(ItemDisplayContents.DisplaySlot::index).max();
      if (optionalint.isEmpty()) {
         return EMPTY;
      }

      ItemDisplayContents contents = new ItemDisplayContents(optionalint.getAsInt() + 1, chosenMapSlot);

      for (ItemDisplayContents.DisplaySlot slot : slots) {
         contents.items.set(slot.index(), slot.item());
      }

      return contents;
   }

   private List<ItemDisplayContents.DisplaySlot> asSlots() {
      List<ItemDisplayContents.DisplaySlot> list = new ArrayList<>();

      for (int i = 0; i < this.items.size(); i++) {
         ItemStack itemstack = (ItemStack)this.items.get(i);
         if (!itemstack.isEmpty()) {
            list.add(new ItemDisplayContents.DisplaySlot(i, itemstack));
         }
      }

      return list;
   }

   public int findActiveMapSlot() {
      return this.chosenMapSlot;
   }

   public NonNullList<ItemStack> items() {
      return this.items;
   }

   public int size() {
      return this.items.size();
   }

   public boolean isEmpty() {
      return this.items.isEmpty();
   }

   @Override
   public boolean equals(Object other) {
      return this == other
         ? true
         : other instanceof ItemDisplayContents contents && this.chosenMapSlot == contents.chosenMapSlot && ItemStack.listMatches(this.items, contents.items);
   }

   @Override
   public int hashCode() {
      return 31 * ItemStack.hashStackList(this.items) + this.chosenMapSlot;
   }

   @Override
   public String toString() {
      return "ItemDisplayContents" + this.items + "chosenMapSlot" + this.chosenMapSlot;
   }

   private record DisplaySlot(int index, ItemStack item) {
      public static final Codec<ItemDisplayContents.DisplaySlot> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.INT.fieldOf("slot").forGetter(ItemDisplayContents.DisplaySlot::index),
               ItemStack.CODEC.fieldOf("item").forGetter(ItemDisplayContents.DisplaySlot::item)
            )
            .apply(instance, ItemDisplayContents.DisplaySlot::new)
      );
   }

   public static class Mutable {
      private final NonNullList<ItemStack> items = NonNullList.withSize(ItemDisplayContents.LAYOUT.size(), ItemStack.EMPTY);
      private int chosenMapSlot;

      public Mutable(ItemDisplayContents contents) {
         this.chosenMapSlot = contents.chosenMapSlot;
         contents.copyInto(this.items);
      }

      public int chosenMapSlot() {
         return this.chosenMapSlot;
      }

      private int findSwapSlot(ItemStack stack) {
         for (int i = 0; i < ItemDisplayContents.LAYOUT.size(); i++) {
            if (((ItemDisplayType)ItemDisplayContents.LAYOUT.get(i).get()).validItems().test(stack)) {
               return i;
            }
         }

         return -1;
      }

      private int findInsertSlot(ItemStack stack) {
         for (int i = 0; i < ItemDisplayContents.LAYOUT.size(); i++) {
            if (((ItemDisplayType)ItemDisplayContents.LAYOUT.get(i).get()).validItems().test(stack) && ((ItemStack)this.items.get(i)).isEmpty()) {
               return i;
            }
         }

         return -1;
      }

      public boolean trySwap(SlotAccess source, Player player) {
         return this.trySwap(source, player, TFItemStackUtils::giveOrDrop);
      }

      public boolean trySwap(SlotAccess source, Player player, BiConsumer<ItemStack, Player> remainder) {
         ItemStack slottedStack = source.get();
         if (!slottedStack.isEmpty() && slottedStack.canFitInsideContainerItems()) {
            int slotForStack = this.findInsertSlot(slottedStack);
            if (slotForStack < 0) {
               slotForStack = this.findSwapSlot(slottedStack);
            }

            if (slotForStack < 0) {
               return false;
            } else {
               ItemStack targetStack = (ItemStack)this.items.get(slotForStack);
               if (!targetStack.isEmpty() && ItemStack.isSameItemSameComponents(slottedStack, targetStack)) {
                  return false;
               } else {
                  ItemStack insert = slottedStack.split(1);
                  ItemStack replaced = (ItemStack)this.items.set(slotForStack, insert);
                  if (replaced.isEmpty()) {
                     this.tryResetChosenMapSlot(slotForStack);
                     return source.set(slottedStack);
                  } else {
                     boolean ret = source.set(replaced);
                     remainder.accept(slottedStack, player);
                     return ret;
                  }
               }
            }
         } else {
            return false;
         }
      }

      @Nullable
      public ItemStack removeFirstFree(@Nullable Slot slot) {
         for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemstack = (ItemStack)this.items.get(i);
            if (!itemstack.isEmpty() && (slot == null || slot.mayPlace(itemstack))) {
               if (i == this.chosenMapSlot) {
                  this.cycleChosenMapSlot();
               }

               return (ItemStack)this.items.set(i, ItemStack.EMPTY);
            }
         }

         return null;
      }

      public ItemDisplayContents toImmutable() {
         return new ItemDisplayContents(this.items, this.chosenMapSlot);
      }

      public int cycleChosenMapSlot() {
         for (int index = this.chosenMapSlot + 1; index < this.items.size(); index++) {
            if (ItemDisplayContents.LAYOUT.get(index) == ItemDisplays.MAP && !((ItemStack)this.items.get(index)).isEmpty()) {
               this.chosenMapSlot = index;
               return this.chosenMapSlot;
            }
         }

         this.chosenMapSlot = -1;
         return this.chosenMapSlot;
      }

      private void tryResetChosenMapSlot(int index) {
         if (index < ItemDisplayContents.LAYOUT.size() && ItemDisplayContents.LAYOUT.get(index) == ItemDisplays.MAP && !this.hasOtherMaps(index)) {
            this.chosenMapSlot = index;
         }
      }

      private boolean hasOtherMaps(int mapIndex) {
         for (int i = 0; i < Math.min(this.items.size(), ItemDisplayContents.LAYOUT.size()); i++) {
            if (!((ItemStack)this.items.get(i)).isEmpty() && ItemDisplayContents.LAYOUT.get(i) == ItemDisplays.MAP && mapIndex != i) {
               return true;
            }
         }

         return false;
      }
   }
}
