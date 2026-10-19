package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.NpcDialogueChoiceButton;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.NpcDialogueTextWidget;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.network.CloseGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.network.TriggerEntityEventPacket;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GatekeeperDialogueScreen extends Screen {
   private Component currentText;
   private NpcDialogueTextWidget text;
   private final List<NpcDialogueChoiceButton> choiceButtons = new ArrayList<>();
   private final TheGatekeeper gatekeeper;
   private final boolean challenged;
   private boolean packetSent = false;

   public GatekeeperDialogueScreen(TheGatekeeper gatekeeper, boolean challenged) {
      super(Component.empty());
      this.gatekeeper = gatekeeper;
      this.challenged = challenged;
   }

   protected void init() {
      if (this.currentText == null) {
         this.currentText = Component.translatable("message.eternal_starlight.gatekeeper_intro");
      }

      if (this.choiceButtons.isEmpty()) {
         this.addChoices(new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_challenge"), buttonx -> {
            if (this.challenged) {
               this.currentText = Component.translatable("message.eternal_starlight.gatekeeper_rematch");
            } else {
               this.currentText = Component.translatable("message.eternal_starlight.gatekeeper_challenge_confirm");
            }

            this.addChoices(new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_challenge"), button1 -> {
               this.sendClosePacket(1);
               Minecraft.getInstance().setScreen(null);
            }), new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_do_not"), button1 -> {
               this.sendClosePacket(0);
               Minecraft.getInstance().setScreen(null);
            }));
         }), new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_trade"), buttonx -> {
            if (!this.challenged) {
               this.currentText = Component.translatable("message.eternal_starlight.gatekeeper_deny_trade");
               this.addChoices(new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_confirm"), button1 -> {
                  this.sendClosePacket(0);
                  Minecraft.getInstance().setScreen(null);
               }));
            } else {
               this.sendClosePacket(2);
               Minecraft.getInstance().setScreen(null);
            }
         }), new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_leave"), buttonx -> {
            this.currentText = Component.translatable("message.eternal_starlight.gatekeeper_leave_confirm");
            this.addChoices(new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_leave"), button1 -> {
               this.sendClosePacket(3);
               Minecraft.getInstance().setScreen(null);
            }), new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_do_not"), button1 -> {
               this.sendClosePacket(0);
               Minecraft.getInstance().setScreen(null);
            }));
         }), new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_nothing"), buttonx -> {
            this.currentText = Component.translatable("message.eternal_starlight.gatekeeper_farewell");
            this.addChoices(new NpcDialogueChoiceButton(Component.translatable("message.eternal_starlight.gatekeeper_confirm"), button1 -> {
               this.sendClosePacket(0);
               Minecraft.getInstance().setScreen(null);
            }));
         }));
      } else {
         int y = this.height;
         this.text = (NpcDialogueTextWidget)this.addRenderableWidget(new NpcDialogueTextWidget(this.currentText));

         for (NpcDialogueChoiceButton button : this.choiceButtons) {
            this.addRenderableWidget(button);
            y -= button.getIncrement(this.width);
            button.reposition(0, y, this.width);
         }

         y -= this.text.getIncrement(this.width);
         this.text.reposition(0, y, this.width);
      }
   }

   private void addChoices(NpcDialogueChoiceButton... buttons) {
      this.clearWidgets();
      this.choiceButtons.clear();
      int y = this.height;
      this.text = (NpcDialogueTextWidget)this.addRenderableWidget(new NpcDialogueTextWidget(this.currentText));

      for (int i = buttons.length - 1; i >= 0; i--) {
         this.choiceButtons.add((NpcDialogueChoiceButton)this.addRenderableWidget(buttons[i]));
         y -= buttons[i].getIncrement(this.width);
         buttons[i].reposition(0, y, this.width);
      }

      y -= this.text.getIncrement(this.width);
      this.text.reposition(0, y, this.width);
      ESClientPlatform.INSTANCE.sendToServer(new TriggerEntityEventPacket(this.gatekeeper.getId(), (byte)100));
   }

   public void tick() {
      if (this.text != null) {
         this.text.tick();
      }
   }

   public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
   }

   public boolean isPauseScreen() {
      return false;
   }

   private void sendClosePacket(int id) {
      if (!this.packetSent) {
         ESClientPlatform.INSTANCE.sendToServer(new CloseGatekeeperGuiPacket(this.gatekeeper.getId(), id));
      }

      this.packetSent = true;
   }

   public void removed() {
      super.removed();
      this.sendClosePacket(0);
   }

   public void onClose() {
      super.onClose();
      this.sendClosePacket(0);
   }
}
