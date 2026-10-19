package net.unusual.block_factorys_bosses.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownKrakenTridentEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TridentItem.class)
public class KrakenTridentTridentItemMixin {
   @Redirect(
      method = "releaseUsing",
      at = @At(
         value = "NEW",
         target = "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/ThrownTrident;"
      )
   )
   public ThrownTrident releaseUsing(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
      return pickupItemStack.is((Item)BossesRiseItems.KRAKEN_TRIDENT.get())
         ? new ThrownKrakenTridentEntity(level, shooter, pickupItemStack)
         : new ThrownTrident(level, shooter, pickupItemStack);
   }
}
