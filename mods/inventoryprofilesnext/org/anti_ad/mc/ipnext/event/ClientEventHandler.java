package org.anti_ad.mc.ipnext.event;

import net.minecraft.network.chat.MutableComponent;
import org.anti_ad.mc.common.input.GlobalInputHandler;
import org.anti_ad.mc.common.moreinfo.SemVer;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.IPNInfoManager;
import org.anti_ad.mc.ipnext.access.IPNImpl;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.config.LockedSlotsSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.config.Tweaks;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;
import org.anti_ad.mc.ipnext.parser.CustomDataFileLoader;
import org.anti_ad.mc.ipnext.specific.event.PClientEventHandler;
import org.anti_ad.mc.ipnext.specific.event.PClientEventHandler$DefaultImpls;
import org.jetbrains.annotations.NotNull;

public final class ClientEventHandler implements PClientEventHandler {
   @NotNull
   public static final ClientEventHandler INSTANCE = new ClientEventHandler();
   private static boolean firstJoin = true;

   private ClientEventHandler() {
   }

   private final boolean getInGame() {
      return VanillaUtil.INSTANCE.inGame();
   }

   public final void onTickPre() {
      ClientInitHandler.INSTANCE.onTickPre();
   }

   public final void onTick() {
      MouseTracer.INSTANCE.onTick();
      if (this.getInGame()) {
         this.onTickInGame();
      }
   }

   private final void onTickInGame() {
      LockedSlotKeeper.INSTANCE.onTickInGame();
      ProfileSwitchHandler.INSTANCE.onTickInGame();
      IPNImpl.Companion.onTickInGame();
      CuttersDispatcher.INSTANCE.onTickInGame();
      AnvilHandler.INSTANCE.onTickInGame();
      if (GuiSettings.INSTANCE.getENABLE_INVENTORY_BUTTONS().getBooleanValue() && GuiSettings.INSTANCE.getSHOW_CONTINUOUS_CRAFTING_CHECKBOX().getBooleanValue()
         )
       {
         ContinuousCraftingHandler.INSTANCE.onTickInGame();
      }

      if (ModSettings.INSTANCE.getENABLE_AUTO_REFILL().getBooleanValue()) {
         AutoRefillHandler.INSTANCE.onTickInGame();
      }

      if (Tweaks.INSTANCE.getCONTAINER_SWIPE_MOVING_ITEMS().getBooleanValue()) {
         MiscHandler.INSTANCE.swipeMoving();
      }

      LockSlotsHandler.INSTANCE.onTickInGame();
      SlotHighlightHandler.INSTANCE.onTickInGame();
   }

   public final void onJoinWorld() {
      CustomDataFileLoader.INSTANCE.load();
      GlobalInputHandler.INSTANCE.getPressedKeys().clear();
      if (ModSettings.INSTANCE.getENABLE_LOCK_SLOTS().getBooleanValue()
         && !LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY().getBooleanValue()) {
         LockedSlotKeeper.INSTANCE.onJoinWorld();
      }

      if (ModSettings.INSTANCE.getENABLE_AUTO_REFILL().getBooleanValue()) {
         AutoRefillHandler.INSTANCE.onJoinWorld();
      }
   }

   public final void onCrafted() {
      if (VanillaUtil.INSTANCE.isOnClientThread()) {
         ContinuousCraftingHandler.INSTANCE.onCrafted();
      }
   }

   public final void onJoinGame() {
      this.onJoinWorld();
      if (firstJoin) {
         firstJoin = false;
         IPNInfoManager.INSTANCE.doCheckVersion();
         IPNInfoManager.INSTANCE.doSessionKeepAlive();
         CustomDataFileLoader.INSTANCE.doSanityCheck();
      }
   }

   @NotNull
   @Override
   public final MutableComponent createChatMessage(@NotNull SemVer var1) {
      return PClientEventHandler$DefaultImpls.createChatMessage(this, var1);
   }
}
