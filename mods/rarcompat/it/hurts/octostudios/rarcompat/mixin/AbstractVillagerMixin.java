package it.hurts.octostudios.rarcompat.mixin;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.hat.VillagerHatItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractVillager.class)
abstract class AbstractVillagerMixin {
   @Shadow
   private Player tradingPlayer;
   @Shadow
   protected MerchantOffers offers;

   @Inject(method = "notifyTrade ", at = @At("HEAD"))
   private void notifyTrade(MerchantOffer offer, CallbackInfo ci) {
      ItemStack relicStack = EntityUtils.findEquippedCurio(this.tradingPlayer, (Item)ModItems.VILLAGER_HAT.value());
      if (relicStack.getItem() instanceof VillagerHatItem relic && this.offers != null && relic.isAbilityUnlocked(relicStack, "discount")) {
         int newPrice = (int)Math.round(offer.getItemCostA().count() * relic.getStatValue(relicStack, "discount", "multiplier"));
         if (newPrice > 1) {
            relic.spreadRelicExperience(this.tradingPlayer, relicStack, 1 + this.tradingPlayer.getRandom().nextInt(newPrice) + 1);
         }
      }
   }

   @Inject(method = "getOffers", at = @At("HEAD"))
   private void getOffers(CallbackInfoReturnable<MerchantOffers> cir) {
      ItemStack relicStack = EntityUtils.findEquippedCurio(this.tradingPlayer, (Item)ModItems.VILLAGER_HAT.value());
      if (relicStack.getItem() instanceof VillagerHatItem relic && this.offers != null && relic.isAbilityUnlocked(relicStack, "discount")) {
         for (MerchantOffer offer : this.offers) {
            int newPrice = (int)Math.round(offer.getItemCostA().count() * relic.getStatValue(relicStack, "discount", "multiplier") / 100.0);
            offer.setSpecialPriceDiff(-newPrice);
         }
      }
   }
}
