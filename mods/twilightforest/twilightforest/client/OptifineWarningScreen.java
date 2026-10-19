package twilightforest.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import org.jetbrains.annotations.Nullable;

public class OptifineWarningScreen extends Screen {
   private final Screen lastScreen;
   private int ticksUntilEnable = 200;
   private MultiLineLabel message = MultiLineLabel.EMPTY;
   private MultiLineLabel suggestions = MultiLineLabel.EMPTY;
   private static final Component text = Component.translatable("gui.twilightforest.optifine.message");
   private static final MutableComponent url = Component.translatable("gui.twilightforest.optifine.suggestions")
      .withStyle(
         style -> style.withColor(ChatFormatting.GREEN)
            .applyFormat(ChatFormatting.UNDERLINE)
            .withClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/NordicGamerFE/usefulmods"))
      );
   private Button exitButton;

   public OptifineWarningScreen(Screen screen) {
      super(Component.translatable("gui.twilightforest.optifine.title"));
      this.lastScreen = screen;
   }

   public Component getNarrationMessage() {
      return CommonComponents.joinForNarration(new Component[]{super.getNarrationMessage(), text});
   }

   protected void init() {
      super.init();
      this.exitButton = (Button)this.addRenderableWidget(
         Button.builder(CommonComponents.GUI_PROCEED, pressed -> Minecraft.getInstance().setScreen(this.lastScreen))
            .bounds(this.width / 2 - 75, this.height * 3 / 4, 150, 20)
            .build()
      );
      this.exitButton.active = false;
      this.message = MultiLineLabel.create(this.font, text, this.width - 50);
      this.suggestions = MultiLineLabel.create(this.font, url, this.width - 50);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(graphics, mouseX, mouseY, partialTicks);
      graphics.drawCenteredString(this.font, this.title, this.width / 2, 30, 16777215);
      this.message.renderCentered(graphics, this.width / 2, 70);
      this.suggestions.renderCentered(graphics, this.width / 2, 160);
      super.render(graphics, mouseX, mouseY, partialTicks);
      this.exitButton.render(graphics, mouseX, mouseY, partialTicks);
   }

   public void tick() {
      super.tick();
      if (--this.ticksUntilEnable <= 0) {
         this.exitButton.active = true;
      }
   }

   public boolean shouldCloseOnEsc() {
      return this.ticksUntilEnable <= 0;
   }

   public void onClose() {
      Minecraft.getInstance().setScreen(this.lastScreen);
   }

   public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
      if (pMouseY > 160.0 && pMouseY < 170.0) {
         Style style = this.getClickedComponentStyleAt((int)pMouseX);
         if (style != null && style.getClickEvent() != null && style.getClickEvent().getAction() == Action.OPEN_URL) {
            this.handleComponentClicked(style);
            return false;
         }
      }

      return super.mouseClicked(pMouseX, pMouseY, pButton);
   }

   @Nullable
   private Style getClickedComponentStyleAt(int xPos) {
      int wid = Minecraft.getInstance().font.width(url);
      int left = this.width / 2 - wid / 2;
      int right = this.width / 2 + wid / 2;
      return xPos >= left && xPos <= right ? Minecraft.getInstance().font.getSplitter().componentStyleAtWidth(url, xPos - left) : null;
   }
}
