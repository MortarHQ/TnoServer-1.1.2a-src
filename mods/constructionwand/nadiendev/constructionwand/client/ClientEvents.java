package nadiendev.constructionwand.client;

import com.mojang.blaze3d.platform.InputConstants;
import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.wand.ItemWand;
import nadiendev.constructionwand.network.ModMessages;
import nadiendev.constructionwand.network.PacketQueryUndo;
import nadiendev.constructionwand.network.PacketWandOption;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent.Key;
import net.neoforged.neoforge.client.event.InputEvent.MouseScrollingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;

public class ClientEvents {
   private boolean optPressed = false;

   @SubscribeEvent
   public void KeyEvent(Key event) {
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         if (WandUtil.holdingWand(player) != null) {
            boolean optState = isOptKeyDown();
            if (this.optPressed != optState) {
               this.optPressed = optState;
               ModMessages.sendToServer(new PacketQueryUndo(this.optPressed));
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public void MouseScrollEvent(MouseScrollingEvent event) {
      Player player = Minecraft.getInstance().player;
      double scroll = event.getScrollDeltaY();
      if (player != null && modeKeyCombDown(player) && scroll != 0.0) {
         ItemStack wand = WandUtil.holdingWand(player);
         if (wand != null) {
            WandOptions wandOptions = new WandOptions(wand);
            wandOptions.lock.next(scroll < 0.0);
            ModMessages.sendToServer(new PacketWandOption(wandOptions.lock, true));
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void onLeftClickEmpty(LeftClickEmpty event) {
      Player player = event.getEntity();
      if (player != null && modeKeyCombDown(player)) {
         ItemStack wand = event.getItemStack();
         if (wand.getItem() instanceof ItemWand) {
            WandOptions wandOptions = new WandOptions(wand);
            wandOptions.cores.next();
            ModMessages.sendToServer(new PacketWandOption(wandOptions.cores, true));
         }
      }
   }

   @SubscribeEvent
   public void onRightClickItem(RightClickItem event) {
      if (!event.getSide().isServer()) {
         Player player = event.getEntity();
         if (player != null && guiKeyCombDown(player)) {
            ItemStack wand = event.getItemStack();
            if (wand.getItem() instanceof ItemWand) {
               Minecraft.getInstance().setScreen(new ScreenWand(wand));
               event.setCanceled(true);
            }
         }
      }
   }

   private static boolean isKeyDown(int id) {
      return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), id);
   }

   public static boolean isOptKeyDown() {
      return isKeyDown((Integer)ConfigClient.OPT_KEY.get());
   }

   public static boolean modeKeyCombDown(Player player) {
      return player.isCrouching() && (isOptKeyDown() || !(Boolean)ConfigClient.SHIFTOPT_MODE.get());
   }

   public static boolean guiKeyCombDown(Player player) {
      return player.isCrouching() && (isOptKeyDown() || !(Boolean)ConfigClient.SHIFTOPT_GUI.get());
   }
}
