package org.anti_ad.mc.ipnext.mixin;

import java.util.Iterator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen.TradeOfferButton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.ipnext.event.villagers.VillagerTradeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MerchantScreen.class)
public class MixinMerchantScreen {
   @Final
   @Shadow
   public TradeOfferButton[] tradeOfferButtons;

   @Inject(
      method = "render",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0),
      locals = LocalCapture.CAPTURE_FAILHARD
   )
   void render(
      GuiGraphics var1,
      int var2,
      int var3,
      float var4,
      CallbackInfo var5,
      MerchantOffers var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      Iterator var12,
      MerchantOffer var13,
      ItemStack var14,
      ItemStack var15,
      ItemStack var16,
      ItemStack var17
   ) {
      MerchantScreen var18 = (MerchantScreen)this;
      VillagerTradeManager.INSTANCE.drawingButton(var18, new NativeContext(var1, null), var2, var3, var13, var7, var8, var9, var10, var11);
   }
}
