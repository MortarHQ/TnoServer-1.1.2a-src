package com.blorb.morerelics;

import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

public class MoreRelicsUtil {
   public static List<ItemStack> actuallyGetEquippedRelics(LivingEntity entity) {
      List<ItemStack> items = new ArrayList<>();
      if (entity instanceof Player player) {
         CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            for (int i = 0; i < handler.getEquippedCurios().getSlots(); i++) {
               ItemStack stack = handler.getEquippedCurios().getStackInSlot(i);
               if (stack.getItem() instanceof IRelicItem) {
                  items.add(stack);
               }
            }
         });
      }

      return items;
   }

   public static List<MoreRelicsUtil.EquippedRelic> actuallyGetEquippedRelicsWithSlots(LivingEntity entity) {
      List<MoreRelicsUtil.EquippedRelic> relics = new ArrayList<>();
      if (entity instanceof Player player) {
         CuriosApi.getCuriosInventory(player).ifPresent(handler -> handler.getCurios().forEach((slotId, curioStacksHandler) -> {
            IItemHandlerModifiable stacks = curioStacksHandler.getStacks();

            for (int i = 0; i < stacks.getSlots(); i++) {
               ItemStack stack = stacks.getStackInSlot(i);
               if (!stack.isEmpty() && stack.getItem() instanceof IRelicItem) {
                  relics.add(new MoreRelicsUtil.EquippedRelic(slotId, i, stack));
               }
            }
         }));
      }

      return relics;
   }

   public record EquippedRelic(String slotId, int slotIndex, ItemStack stack) {
   }
}
