package nadiendev.constructionwand.client;

import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.network.ModMessages;
import nadiendev.constructionwand.network.PacketToggleVoidSackActive;
import nadiendev.constructionwand.network.PacketWandUndo;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent.Key;

public class KeybindHandler {
   public static final KeyMapping KEY_VOID_SACK_TOGGLE = new KeyMapping(getKey("void_sack_toggle"), 77, getCategory("constructionwand"));
   public static final KeyMapping KEY_WAND_UNDO = new KeyMapping(getKey("wand_undo"), 75, getCategory("constructionwand"));

   private static String getKey(String name) {
      return String.join(".", "key", "constructionwand", name);
   }

   private static String getCategory(String modid) {
      return "key.categories." + modid;
   }

   @SubscribeEvent
   public void KeyEvent(Key event) {
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         if (KEY_VOID_SACK_TOGGLE.consumeClick()) {
            for (InteractionHand hand : InteractionHand.values()) {
               ItemStack stack = player.getItemInHand(hand);
               if (stack.getItem() instanceof ItemVoidSack) {
                  PacketToggleVoidSackActive.send(hand);
                  break;
               }
            }
         }

         if (KEY_WAND_UNDO.consumeClick() && WandUtil.holdingWand(player) != null) {
            ModMessages.sendToServer(new PacketWandUndo());
         }
      }
   }
}
