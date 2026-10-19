package dev.ftb.mods.ftbultimine.api.rightclick;

import dev.ftb.mods.ftbultimine.api.shape.ShapeContext;
import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

public interface RightClickHandler {
   int handleRightClickBlock(ShapeContext var1, InteractionHand var2, Collection<BlockPos> var3);

   @NonExtendable
   default boolean hurtItemAndCheckIfBroken(ServerPlayer player, InteractionHand hand) {
      if (player.isCreative()) {
         return false;
      }

      ItemStack itemStack = player.getItemInHand(hand);
      itemStack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
      return itemStack.isEmpty();
   }
}
