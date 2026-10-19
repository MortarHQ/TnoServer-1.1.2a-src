package com.benji.netherman.common.compat;

import com.benji.netherman.init.ModItems;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class CuriosCompat {
   public static ItemStack getTotemFromCurios(Player player) {
      Optional<SlotResult> result = CuriosApi.getCuriosInventory(player)
         .flatMap(iCuriosItemHandler -> iCuriosItemHandler.findFirstCurio(stack -> stack.is((Item)ModItems.CHANCE_TOTEM.get())));
      return result.<ItemStack>map(SlotResult::stack).orElse(null);
   }
}
