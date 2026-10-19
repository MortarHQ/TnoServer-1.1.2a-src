package cc.sighs.oelib.config.ui.entries;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CategoryTextEntry extends AbstractConfigEntry<Object> {
   private final Component category;

   public CategoryTextEntry(Component category, Component text) {
      this.category = category;
   }

   @Override
   public int getItemHeight() {
      return 24;
   }

   @Override
   public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
      int centerX = x + entryWidth / 2;
      graphics.fill(x, y + entryHeight - 1, x + entryWidth, y + entryHeight, -2130706433);
      graphics.drawCenteredString(Minecraft.getInstance().font, this.category, centerX, y + 6, -1);
   }

   @Override
   public Component getFieldName() {
      return this.category;
   }

   @Override
   public Object getValue() {
      return null;
   }

   @Override
   public Optional<Object> getDefaultValue() {
      return Optional.empty();
   }
}
