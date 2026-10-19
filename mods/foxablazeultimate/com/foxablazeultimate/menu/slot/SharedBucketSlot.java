package com.foxablazeultimate.menu.slot;

import com.foxablazeultimate.menu.BeelzebubStorageMenu;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.item.TensuraConsumableItems;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import java.util.Arrays;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;

public class SharedBucketSlot extends Slot {
   private final BeelzebubStorageMenu menu;
   private static final SharedBucketSlot.FillEntry[] FILL_TABLE = buildTable();

   public SharedBucketSlot(BeelzebubStorageMenu menu, Container container, int slotIndex, int xPosition, int yPosition) {
      super(container, slotIndex, xPosition, yPosition);
      this.menu = menu;
   }

   public int getMaxStackSize() {
      return 1;
   }

   public boolean mayPlace(ItemStack stack) {
      PotionContents potion = (PotionContents)stack.get(DataComponents.POTION_CONTENTS);
      if (potion != null && potion.hasEffects()) {
         return false;
      } else {
         SharedBucketSlot.FillEntry entry = lookup(stack.getItem());
         if (entry == null) {
            return false;
         } else {
            ItemStack currentOutput = this.menu.bucketOutput.getItem(0);
            ItemStack expectedOutput = entry.output().getDefaultInstance();
            if (currentOutput.isEmpty()) {
               return true;
            } else {
               return currentOutput.getCount() >= currentOutput.getMaxStackSize() ? false : ItemStack.isSameItemSameComponents(expectedOutput, currentOutput);
            }
         }
      }
   }

   public void set(ItemStack stack) {
      super.set(stack);
      if (!stack.isEmpty()) {
         SharedBucketSlot.FillEntry entry = lookup(stack.getItem());
         if (entry != null) {
            IAbility ability = TensuraStorages.getAbilityFrom(this.menu.getStorageOwner());
            double maxCapacity = entry.kind() == SharedBucketSlot.FluidKind.WATER
               ? this.menu.getPlayer().getAttributeValue(TensuraAttributes.WATER_CAPACITY)
               : this.menu.getPlayer().getAttributeValue(TensuraAttributes.LAVA_CAPACITY);
            double currentPoint = entry.kind() == SharedBucketSlot.FluidKind.WATER ? ability.getWaterPoint() : ability.getLavaPoint();
            if (!(currentPoint >= maxCapacity)) {
               double newPoint = Math.min(maxCapacity, currentPoint + entry.delta());
               if (entry.kind() == SharedBucketSlot.FluidKind.WATER) {
                  ability.setWaterPoint(newPoint);
               } else {
                  ability.setLavaPoint(newPoint);
               }

               ability.markDirty();
               SoundEvent sound = entry.kind() == SharedBucketSlot.FluidKind.WATER ? SoundEvents.BUCKET_EMPTY : SoundEvents.BUCKET_EMPTY_LAVA;
               this.menu.getPlayer().playSound(sound);
               ItemStack currentOutput = this.menu.bucketOutput.getItem(0);
               if (currentOutput.isEmpty()) {
                  this.menu.bucketOutput.setItem(0, entry.output().getDefaultInstance());
               } else {
                  ItemStack newOutput = currentOutput.copy();
                  newOutput.grow(1);
                  this.menu.bucketOutput.setItem(0, newOutput);
               }

               this.menu.bucketOutput.setChanged();
               this.container.setItem(this.getSlotIndex(), ItemStack.EMPTY);
               this.container.setChanged();
            }
         }
      }
   }

   public static SharedBucketSlot.FillEntry lookup(Item item) {
      return Arrays.stream(FILL_TABLE).filter(e -> e.input().equals(item)).findFirst().orElse(null);
   }

   private static SharedBucketSlot.FillEntry[] buildTable() {
      Item magicWater = orItems(TensuraConsumableItems.WATER_MAGIC_BOTTLE);
      Item magicVacWater = orItems(TensuraConsumableItems.VACUUMED_WATER_MAGIC_BOTTLE);
      Item magicEmpty = orItems(TensuraConsumableItems.MAGIC_BOTTLE);
      return new SharedBucketSlot.FillEntry[]{
         new SharedBucketSlot.FillEntry(Items.WATER_BUCKET, 3.0, Items.BUCKET, SharedBucketSlot.FluidKind.WATER),
         new SharedBucketSlot.FillEntry(Items.WET_SPONGE, 3.0, Items.SPONGE, SharedBucketSlot.FluidKind.WATER),
         new SharedBucketSlot.FillEntry(magicWater, 1.0, magicEmpty, SharedBucketSlot.FluidKind.WATER),
         new SharedBucketSlot.FillEntry(magicVacWater, 1.0, magicEmpty, SharedBucketSlot.FluidKind.WATER),
         new SharedBucketSlot.FillEntry(Items.POTION, 1.0, Items.GLASS_BOTTLE, SharedBucketSlot.FluidKind.WATER),
         new SharedBucketSlot.FillEntry(Items.SPLASH_POTION, 1.0, Items.GLASS_BOTTLE, SharedBucketSlot.FluidKind.WATER),
         new SharedBucketSlot.FillEntry(Items.LINGERING_POTION, 1.0, Items.GLASS_BOTTLE, SharedBucketSlot.FluidKind.WATER),
         new SharedBucketSlot.FillEntry(Items.LAVA_BUCKET, 3.0, Items.BUCKET, SharedBucketSlot.FluidKind.LAVA),
         new SharedBucketSlot.FillEntry(Items.MAGMA_BLOCK, 1.0, Items.COBBLESTONE, SharedBucketSlot.FluidKind.LAVA)
      };
   }

   private static Item orItems(Object supplier) {
      if (supplier instanceof RegistrySupplier<?> rs) {
         return rs.get() instanceof Item it ? it : Items.AIR;
      } else {
         return Items.AIR;
      }
   }

   public record FillEntry(Item input, double delta, Item output, SharedBucketSlot.FluidKind kind) {
   }

   public enum FluidKind {
      WATER,
      LAVA;
   }
}
