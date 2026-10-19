package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.item.component.LargeItemStackList;
import cn.leolezury.eternalstarlight.common.item.tooltip.GalacticQuiverTooltipComponent;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

public class GalacticQuiverItem extends Item {
   private static final int MAX_ARROWS = 512;

   public GalacticQuiverItem(Properties properties) {
      super(properties);
   }

   public static boolean hasArrows(ItemStack itemStack) {
      return ((List)itemStack.getOrDefault(ESDataComponents.ARROWS.get(), List.of())).stream().anyMatch(stack -> !stack.isEmpty());
   }

   public static boolean addArrowToInventory(Inventory inventory, ItemStack stack) {
      if (stack.is(ItemTags.ARROWS) && !stack.isEmpty()) {
         for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack inventoryItem = inventory.getItem(i);
            if (inventoryItem.is(ESItems.GALACTIC_QUIVER.get())) {
               List<LargeItemStackList.LargeItemStack> arrows = new ArrayList<>(
                  (Collection<? extends LargeItemStackList.LargeItemStack>)inventoryItem.getOrDefault(
                     ESDataComponents.ARROWS.get(), new LargeItemStackList(List.of())
                  )
               );
               AtomicInteger totalCount = new AtomicInteger();
               arrows.forEach(arrow -> totalCount.addAndGet(arrow.getCount()));
               if (totalCount.intValue() < 512) {
                  ItemStack taken = stack.split(512 - totalCount.intValue());
                  boolean newArrow = true;

                  for (LargeItemStackList.LargeItemStack arrowStack : arrows) {
                     if (ItemStack.isSameItemSameComponents(arrowStack.getItem(), taken)) {
                        arrowStack.setCount(arrowStack.getCount() + taken.getCount());
                        newArrow = false;
                        break;
                     }
                  }

                  if (newArrow) {
                     arrows.add(new LargeItemStackList.LargeItemStack(taken));
                  }
               }

               arrows.removeIf(LargeItemStackList.LargeItemStack::isEmpty);
               inventoryItem.set(ESDataComponents.ARROWS.get(), new LargeItemStackList(Collections.unmodifiableList(arrows)));
               if (inventory.player instanceof ServerPlayer serverPlayer) {
                  serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(-2, 0, i, inventoryItem));
               }
            }

            if (stack.isEmpty()) {
               break;
            }
         }

         return stack.isEmpty();
      } else {
         return false;
      }
   }

   public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
      if (stack.getCount() == 1 && action == ClickAction.SECONDARY) {
         List<LargeItemStackList.LargeItemStack> arrows = new ArrayList<>(
            (Collection<? extends LargeItemStackList.LargeItemStack>)stack.getOrDefault(ESDataComponents.ARROWS.get(), List.of())
         );
         ItemStack slotItem = slot.getItem();
         if (slotItem.isEmpty()) {
            if (!arrows.isEmpty()) {
               LargeItemStackList.LargeItemStack removed = arrows.getLast();
               if (removed != null) {
                  ItemStack remain = slot.safeInsert(removed.splitMaxStack());
                  removed.grow(remain.getCount());
               }
            }
         } else if (slotItem.is(ItemTags.ARROWS)) {
            AtomicInteger totalCount = new AtomicInteger();
            arrows.forEach(arrow -> totalCount.addAndGet(arrow.getCount()));
            if (totalCount.intValue() < 512) {
               ItemStack taken = slot.safeTake(slotItem.getCount(), 512 - totalCount.intValue(), player);
               boolean newArrow = true;

               for (LargeItemStackList.LargeItemStack arrowStack : arrows) {
                  if (ItemStack.isSameItemSameComponents(arrowStack.getItem(), taken)) {
                     arrowStack.setCount(arrowStack.getCount() + taken.getCount());
                     newArrow = false;
                     break;
                  }
               }

               if (newArrow) {
                  arrows.add(new LargeItemStackList.LargeItemStack(taken));
               }
            }
         }

         arrows.removeIf(LargeItemStackList.LargeItemStack::isEmpty);
         stack.set(ESDataComponents.ARROWS.get(), new LargeItemStackList(Collections.unmodifiableList(arrows)));
         return true;
      } else {
         return false;
      }
   }

   public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
      if (stack.getCount() != 1) {
         return false;
      }

      if (!other.isEmpty() && !other.is(ItemTags.ARROWS)) {
         return false;
      }

      if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
         List<LargeItemStackList.LargeItemStack> arrows = new ArrayList<>(
            (Collection<? extends LargeItemStackList.LargeItemStack>)stack.getOrDefault(ESDataComponents.ARROWS.get(), List.of())
         );
         if (other.isEmpty()) {
            if (!arrows.isEmpty()) {
               LargeItemStackList.LargeItemStack removed = arrows.getLast();
               if (removed != null) {
                  access.set(removed.splitMaxStack());
               }
            }
         } else if (other.is(ItemTags.ARROWS)) {
            AtomicInteger totalCount = new AtomicInteger();
            arrows.forEach(arrow -> totalCount.addAndGet(arrow.getCount()));
            if (totalCount.intValue() < 512) {
               int capacity = 512 - totalCount.intValue();
               boolean newArrow = true;

               for (LargeItemStackList.LargeItemStack arrowStack : arrows) {
                  if (ItemStack.isSameItemSameComponents(arrowStack.getItem(), other)) {
                     arrowStack.setCount(arrowStack.getCount() + Math.min(other.getCount(), capacity));
                     if (other.getCount() > capacity) {
                        other.shrink(capacity);
                     } else {
                        other.setCount(0);
                     }

                     newArrow = false;
                     break;
                  }
               }

               if (newArrow) {
                  arrows.add(new LargeItemStackList.LargeItemStack(other.split(capacity)));
               }
            }
         }

         arrows.removeIf(LargeItemStackList.LargeItemStack::isEmpty);
         stack.set(ESDataComponents.ARROWS.get(), new LargeItemStackList(Collections.unmodifiableList(arrows)));
         return true;
      } else {
         return false;
      }
   }

   public void onDestroyed(ItemEntity itemEntity) {
      List<LargeItemStackList.LargeItemStack> arrows = (List<LargeItemStackList.LargeItemStack>)itemEntity.getItem().get(ESDataComponents.ARROWS.get());
      if (arrows != null) {
         itemEntity.getItem().set(ESDataComponents.ARROWS.get(), new LargeItemStackList(List.of()));

         for (LargeItemStackList.LargeItemStack arrowStack : arrows) {
            while (!arrowStack.isEmpty()) {
               ItemStack split = arrowStack.splitMaxStack();
               itemEntity.level().addFreshEntity(new ItemEntity(itemEntity.level(), itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), split));
            }
         }
      }
   }

   public boolean isBarVisible(ItemStack stack) {
      List<LargeItemStackList.LargeItemStack> arrows = (List<LargeItemStackList.LargeItemStack>)stack.get(ESDataComponents.ARROWS.get());
      return arrows != null && !arrows.isEmpty();
   }

   public int getBarWidth(ItemStack stack) {
      List<LargeItemStackList.LargeItemStack> arrows = (List<LargeItemStackList.LargeItemStack>)stack.getOrDefault(ESDataComponents.ARROWS.get(), List.of());
      AtomicInteger totalCount = new AtomicInteger();
      arrows.forEach(arrow -> totalCount.addAndGet(arrow.getCount()));
      return Mth.clamp(Math.round(totalCount.intValue() / 512.0F * 13.0F), 0, 13);
   }

   public int getBarColor(ItemStack stack) {
      return 9461416;
   }

   @NotNull
   public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
      if (!stack.has(DataComponents.HIDE_TOOLTIP) && !stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)) {
         LargeItemStackList arrows = (LargeItemStackList)stack.getOrDefault(ESDataComponents.ARROWS.get(), new LargeItemStackList(List.of()));
         return arrows.isEmpty() ? Optional.empty() : Optional.of(new GalacticQuiverTooltipComponent(arrows));
      } else {
         return Optional.empty();
      }
   }
}
