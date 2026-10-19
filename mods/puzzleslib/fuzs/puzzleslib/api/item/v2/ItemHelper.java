package fuzs.puzzleslib.api.item.v2;

import fuzs.puzzleslib.api.core.v1.CommonAbstractions;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class ItemHelper {
   private ItemHelper() {
   }

   public static void hurtAndBreak(ItemStack itemStack, int amount, LivingEntity entity, InteractionHand interactionHand) {
      hurtAndBreak(itemStack, amount, entity, LivingEntity.getSlotForHand(interactionHand));
   }

   public static void hurtAndBreak(ItemStack itemStack, int amount, LivingEntity entity, EquipmentSlot slot) {
      if (entity.level() instanceof ServerLevel serverLevel) {
         ServerPlayer serverPlayer = entity instanceof ServerPlayer ? (ServerPlayer)entity : null;
         hurtAndBreak(itemStack, amount, serverLevel, serverPlayer, item -> entity.onEquippedItemBroken(item, slot));
      }
   }

   public static void hurtAndBreak(ItemStack itemStack, int amount, ServerLevel level, @Nullable ServerPlayer serverPlayer, Consumer<Item> onBreak) {
      ItemStack originalItemStack = copyItemStackIfNecessary(itemStack, serverPlayer);
      itemStack.hurtAndBreak(amount, level, serverPlayer, item -> {
         onBreak.accept(item);
         if (serverPlayer != null) {
            CommonAbstractions.INSTANCE.onPlayerDestroyItem(serverPlayer, originalItemStack, null);
         }
      });
   }

   private static ItemStack copyItemStackIfNecessary(ItemStack itemStack, @Nullable ServerPlayer serverPlayer) {
      return serverPlayer != null && ModLoaderEnvironment.INSTANCE.getModLoader().isForgeLike() ? itemStack.copy() : itemStack;
   }
}
