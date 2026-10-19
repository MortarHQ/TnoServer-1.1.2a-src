package org.anti_ad.mc.ipnext.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import kotlin.Unit;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import net.neoforged.neoforge.client.event.ContainerScreenEvent.Render.Background;
import net.neoforged.neoforge.client.event.ContainerScreenEvent.Render.Foreground;
import net.neoforged.neoforge.client.event.ScreenEvent.Closing;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.neoforged.neoforge.event.level.LevelEvent.Load;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.config.Tweaks;
import org.anti_ad.mc.ipnext.event.ClientEventHandler;
import org.anti_ad.mc.ipnext.gui.inject.ContainerScreenEventHandler;
import org.anti_ad.mc.ipnext.gui.inject.ScreenEventHandler;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;

public class ForgeEventHandler {
   @SubscribeEvent
   public void clientClick(Pre var1) {
      ClientEventHandler.INSTANCE.onTickPre();
   }

   @SubscribeEvent
   public void clientClick(Post var1) {
      ClientEventHandler.INSTANCE.onTick();
   }

   @SubscribeEvent
   public void onPlayerLogInEvent(LoggingIn var1) {
      if (VanillaUtil.INSTANCE.isOnClientThread()) {
         ClientEventHandler.INSTANCE.onJoinGame();
         ClientEventHandler.INSTANCE.onJoinWorld();
      }
   }

   @SubscribeEvent
   public void joinWorld(Load var1) {
      if (VanillaUtil.INSTANCE.isOnClientThread()) {
         ClientEventHandler.INSTANCE.onJoinGame();
         ClientEventHandler.INSTANCE.onJoinWorld();
      }
   }

   @SubscribeEvent
   public void onCrafted(ItemCraftedEvent var1) {
      ClientEventHandler.INSTANCE.onCrafted();
   }

   @SubscribeEvent
   public void onInitGuiPost(net.neoforged.neoforge.client.event.ScreenEvent.Init.Post var1) {
      ScreenEventHandler.INSTANCE.onScreenInit(var1.getScreen(), var1x -> {
         var1.addListener(var1x);
         return Unit.INSTANCE;
      });
   }

   @SubscribeEvent
   public void preScreenRender(net.neoforged.neoforge.client.event.ScreenEvent.Render.Pre var1) {
      ScreenEventHandler.INSTANCE.preRender(new NativeContext(var1.getGuiGraphics(), null));
   }

   @SubscribeEvent
   public void postScreenRender(net.neoforged.neoforge.client.event.ScreenEvent.Render.Post var1) {
      ScreenEventHandler.INSTANCE.postRender(new NativeContext(var1.getGuiGraphics(), null));
   }

   @SubscribeEvent
   public void onBackgroundRender(Background var1) {
      NativeContext var2 = new NativeContext(var1.getGuiGraphics(), null);
      ContainerScreenEventHandler.INSTANCE.onBackgroundRender(var2, var1.getMouseX(), var1.getMouseY(), 0.0F);
   }

   @SubscribeEvent
   public void onForegroundRender(Foreground var1) {
      NativeContext var2;
      (var2 = new NativeContext(var1.getGuiGraphics(), null)).setOverlay(true);
      AbstractContainerScreen var3 = var1.getContainerScreen();
      var2.getNative().pose().pushPose();
      Point var4 = new Rectangle(var3.getGuiLeft(), var3.getGuiTop(), var3.getXSize(), var3.getYSize()).getTopLeft();
      var2.getNative().pose().translate(-var4.getX(), -var4.getY(), 0.0);
      ContainerScreenEventHandler.INSTANCE.onForegroundRender(var2, var1.getMouseX(), var1.getMouseY(), 0.0F);
      var2.getNative().pose().popPose();
   }

   @SubscribeEvent
   public void onGuiKeyPressedPre(net.neoforged.neoforge.client.event.ScreenEvent.KeyPressed.Pre var1) {
      if (VanillaUtil.INSTANCE.inGame()) {
         Key var2 = InputConstants.getKey(var1.getKeyCode(), var1.getScanCode());
         if (Tweaks.INSTANCE.getPREVENT_CLOSE_GUI_DROP_ITEM().getBooleanValue()
            && (var1.getKeyCode() == 256 || Vanilla.INSTANCE.mc().options.keyInventory.isActiveAndMatches(var2))) {
            GeneralInventoryActions.INSTANCE.handleCloseContainer();
         }
      }
   }

   @SubscribeEvent
   public void onScreenClose(Closing var1) {
      if (VanillaUtil.INSTANCE.inGame()) {
         ScreenEventHandler.INSTANCE.onScreenRemoved(var1.getScreen());
      }
   }
}
