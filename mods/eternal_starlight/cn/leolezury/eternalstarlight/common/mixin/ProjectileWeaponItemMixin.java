package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.item.component.LargeItemStackList;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin {
   @Inject(
      method = "getHeldProjectile(Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Predicate;)Lnet/minecraft/world/item/ItemStack;",
      at = @At("RETURN"),
      cancellable = true
   )
   private static void getHeldProjectile(LivingEntity livingEntity, Predicate<ItemStack> predicate, CallbackInfoReturnable<ItemStack> cir) {
      if (((ItemStack)cir.getReturnValue()).isEmpty() && livingEntity instanceof Player player) {
         Inventory inventory = player.getInventory();

         for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack inventoryItem = inventory.getItem(i);
            if (inventoryItem.is(ESItems.GALACTIC_QUIVER.get())) {
               for (LargeItemStackList.LargeItemStack arrowStack : (List)inventoryItem.getOrDefault(
                  ESDataComponents.ARROWS.get(), new LargeItemStackList(List.of())
               )) {
                  if (predicate.test(arrowStack.getItem())) {
                     ItemStack ammo = arrowStack.getItem().copy();
                     ammo.set(ESDataComponents.QUIVER_ARROW.get(), Unit.INSTANCE);
                     cir.setReturnValue(ammo);
                     return;
                  }
               }
            }
         }
      }
   }

   @Inject(
      method = "useAmmo(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Z)Lnet/minecraft/world/item/ItemStack;",
      at = @At("RETURN")
   )
   private static void useAmmo(
      ItemStack weapon, ItemStack ammo, LivingEntity shooter, boolean intangable, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 0) int ammoUse
   ) {
      int use = ammoUse;
      ItemStack result = (ItemStack)cir.getReturnValue();
      if (shooter instanceof Player player && result.has(ESDataComponents.QUIVER_ARROW.get()) && use > 0) {
         Inventory inventory = player.getInventory();

         for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack inventoryItem = inventory.getItem(i);
            if (inventoryItem.is(ESItems.GALACTIC_QUIVER.get())) {
               List<LargeItemStackList.LargeItemStack> arrows = new ArrayList<>(
                  (Collection<? extends LargeItemStackList.LargeItemStack>)inventoryItem.getOrDefault(
                     ESDataComponents.ARROWS.get(), new LargeItemStackList(List.of())
                  )
               );

               for (LargeItemStackList.LargeItemStack arrowStack : arrows) {
                  ItemStack item = arrowStack.getItem().copy();
                  item.set(ESDataComponents.QUIVER_ARROW.get(), Unit.INSTANCE);
                  if (ItemStack.isSameItemSameComponents(item, result)) {
                     int shrink = Math.min(use, arrowStack.getCount());
                     arrowStack.shrink(shrink);
                     use -= shrink;
                  }
               }

               arrows.removeIf(LargeItemStackList.LargeItemStack::isEmpty);
               inventoryItem.set(ESDataComponents.ARROWS.get(), new LargeItemStackList(Collections.unmodifiableList(arrows)));
               if (player instanceof ServerPlayer serverPlayer) {
                  serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(-2, 0, i, inventoryItem));
               }
            }
         }
      }
   }
}
