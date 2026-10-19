package cc.sighs.oelib.config.ui.entries;

import java.util.Optional;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class EmptyEntry extends AbstractConfigEntry<Object> {
   private final int height;

   public EmptyEntry(int height) {
      this.height = height;
   }

   @Override
   public int getItemHeight() {
      return this.height;
   }

   @Override
   public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
   }

   @Override
   public Component getFieldName() {
      return Component.empty();
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
