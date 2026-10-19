package nadiendev.constructionwand.client;

import javax.annotation.Nonnull;
import nadiendev.constructionwand.basics.option.IOption;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.network.ModMessages;
import nadiendev.constructionwand.network.PacketWandOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ScreenWand extends Screen {
   private final ItemStack wand;
   private final WandOptions wandOptions;
   private static final int BUTTON_WIDTH = 160;
   private static final int BUTTON_HEIGHT = 20;
   private static final int SPACING_WIDTH = 50;
   private static final int SPACING_HEIGHT = 30;
   private static final int N_COLS = 2;
   private static final int N_ROWS = 3;
   private static final int FIELD_WIDTH = 370;
   private static final int FIELD_HEIGHT = 120;

   public ScreenWand(ItemStack wand) {
      super(Component.literal("ScreenWand"));
      this.wand = wand;
      this.wandOptions = new WandOptions(wand);
   }

   protected void init() {
      this.createButton(0, 0, this.wandOptions.cores);
      this.createButton(0, 1, this.wandOptions.lock);
      this.createButton(0, 2, this.wandOptions.direction);
      this.createButton(1, 0, this.wandOptions.replace);
      this.createButton(1, 1, this.wandOptions.match);
      this.createButton(1, 2, this.wandOptions.random);
   }

   public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
      guiGraphics.drawCenteredString(this.font, this.wand.getDisplayName(), this.width / 2, this.height / 2 - 60 - 30, 16777215);
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (Minecraft.getInstance().options.keyInventory.matches(keyCode, scanCode)) {
         this.onClose();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private void createButton(int cx, int cy, IOption<?> option) {
      Button button = Button.builder(this.getButtonLabel(option), bt -> this.clickButton(bt, option))
         .pos(this.getX(cx), this.getY(cy))
         .size(160, 20)
         .tooltip(this.getButtonTooltip(option))
         .build();
      button.active = option.isEnabled();
      this.addRenderableWidget(button);
   }

   private void clickButton(Button button, IOption<?> option) {
      option.next();
      ModMessages.sendToServer(new PacketWandOption(option, false));
      button.setMessage(this.getButtonLabel(option));
      button.setTooltip(this.getButtonTooltip(option));
   }

   private int getX(int n) {
      return this.width / 2 - 185 + n * 210;
   }

   private int getY(int n) {
      return this.height / 2 - 60 + n * 50;
   }

   private Component getButtonLabel(IOption<?> option) {
      return Component.translatable(option.getKeyTranslation()).append(Component.translatable(option.getValueTranslation()));
   }

   private Tooltip getButtonTooltip(IOption<?> option) {
      return Tooltip.create(Component.translatable(option.getDescTranslation()));
   }
}
