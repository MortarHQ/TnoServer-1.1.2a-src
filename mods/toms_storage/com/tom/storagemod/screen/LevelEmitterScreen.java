package com.tom.storagemod.screen;

import com.tom.storagemod.client.ClientUtil;
import com.tom.storagemod.menu.LevelEmitterMenu;
import com.tom.storagemod.network.NetworkHandler;
import com.tom.storagemod.screen.widget.ToggleButton;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LevelEmitterScreen extends AbstractFilteredScreen<LevelEmitterMenu> {
   private static final ResourceLocation gui = ResourceLocation.tryBuild("toms_storage", "textures/gui/level_emitter.png");
   private ToggleButton lessThanBtn;
   private EditBox textF;
   private List<LevelEmitterScreen.AmountBtn> amountBtns = new ArrayList<>();

   public LevelEmitterScreen(LevelEmitterMenu screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
      ((LevelEmitterMenu)this.menu).onPacket = this::receive;
   }

   public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.amountBtns.forEach(LevelEmitterScreen.AmountBtn::update);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
      this.renderTooltip(matrixStack, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics matrixStack, float partialTicks, int x, int y) {
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      matrixStack.blit(gui, i, j, 0, 0, this.imageWidth, this.imageHeight);
   }

   protected void init() {
      this.clearWidgets();
      this.amountBtns.clear();
      super.init();
      this.textF = new EditBox(this.font, this.leftPos + 70, this.topPos + 41, 89, 9, Component.translatable("narrator.toms_storage.level_emitter_amount"));
      this.textF.setMaxLength(100);
      this.textF.setBordered(false);
      this.textF.setVisible(true);
      this.textF.setTextColor(16777215);
      this.textF.setValue(Integer.toString(((LevelEmitterMenu)this.menu).count));
      this.textF.setResponder(t -> {
         try {
            int c = Integer.parseInt(t);
            if (c >= 0) {
               ((LevelEmitterMenu)this.menu).count = c;
               this.send();
            }
         } catch (NumberFormatException var3) {
         }
      });
      this.addRenderableWidget(this.textF);
      this.lessThanBtn = ToggleButton.builder(this.leftPos - 18, this.topPos + 5)
         .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/greater_than"))
         .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/less_than"))
         .build(s -> {
            this.lessThanBtn.setState(s);
            ((LevelEmitterMenu)this.menu).lessThan = s;
            this.send();
         });
      this.lessThanBtn
         .setTooltip(
            Tooltip.create(ClientUtil.multilineTooltip("tooltip.toms_storage.level_emitter.greater_than")),
            Tooltip.create(ClientUtil.multilineTooltip("tooltip.toms_storage.level_emitter.less_than"))
         );
      this.lessThanBtn.setState(((LevelEmitterMenu)this.menu).lessThan);
      this.addRenderableWidget(this.lessThanBtn);
      this.amountBtns.add(new LevelEmitterScreen.AmountBtn(20, 0, 1, 1, 20));
      this.amountBtns.add(new LevelEmitterScreen.AmountBtn(45, 0, 10, 16, 25));
      this.amountBtns.add(new LevelEmitterScreen.AmountBtn(75, 0, 100, 32, 30));
      this.amountBtns.add(new LevelEmitterScreen.AmountBtn(110, 0, 1000, 64, 35));
      this.amountBtns.add(new LevelEmitterScreen.AmountBtn(20, 40, -1, -1, 20));
      this.amountBtns.add(new LevelEmitterScreen.AmountBtn(45, 40, -10, -16, 25));
      this.amountBtns.add(new LevelEmitterScreen.AmountBtn(75, 40, -100, -32, 30));
      this.amountBtns.add(new LevelEmitterScreen.AmountBtn(110, 40, -1000, -64, 35));
   }

   private void receive() {
      this.lessThanBtn.setState(((LevelEmitterMenu)this.menu).lessThan);
      this.textF.setValue(Integer.toString(((LevelEmitterMenu)this.menu).count));
   }

   private void send() {
      CompoundTag mainTag = new CompoundTag();
      mainTag.putInt("count", ((LevelEmitterMenu)this.menu).count);
      mainTag.putBoolean("lessThan", ((LevelEmitterMenu)this.menu).lessThan);
      NetworkHandler.sendDataToServer(mainTag);
   }

   @Override
   public void getExclusionAreas(Consumer<IScreen.Box> consumer) {
      consumer.accept(new IScreen.Box(this.leftPos - 20, this.topPos, 25, 25));
   }

   private class AmountBtn {
      private Button btn;
      private int v;
      private int sv;

      public AmountBtn(int x, int y, int v, int sv, int len) {
         this.btn = Button.builder(Component.literal((v > 0 ? "+" : "") + v), this::evt)
            .bounds(LevelEmitterScreen.this.leftPos + x, LevelEmitterScreen.this.topPos + y + 16, len, 20)
            .build();
         LevelEmitterScreen.this.addRenderableWidget(this.btn);
         this.v = v;
         this.sv = sv;
      }

      private void evt(Button b) {
         LevelEmitterMenu var10000 = (LevelEmitterMenu)LevelEmitterScreen.this.menu;
         var10000.count = var10000.count + (Screen.hasShiftDown() ? this.sv : this.v);
         if (((LevelEmitterMenu)LevelEmitterScreen.this.menu).count < 0) {
            ((LevelEmitterMenu)LevelEmitterScreen.this.menu).count = 0;
         }

         LevelEmitterScreen.this.textF.setValue(Integer.toString(((LevelEmitterMenu)LevelEmitterScreen.this.menu).count));
         LevelEmitterScreen.this.send();
      }

      private void update() {
         if (Screen.hasShiftDown()) {
            this.btn.setMessage(Component.literal((this.sv > 0 ? "+" : "") + this.sv));
         } else {
            this.btn.setMessage(Component.literal((this.v > 0 ? "+" : "") + this.v));
         }
      }
   }
}
