package cc.sighs.oelib.config.ui.entries;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractConfigEntry<T> implements GuiEventListener, NarratableEntry {
   protected final Minecraft mc = Minecraft.getInstance();
   @Nullable
   protected Consumer<T> saveCallback;

   public abstract int getItemHeight();

   public abstract void render(GuiGraphics var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10);

   public void lateRender(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
   }

   public abstract Component getFieldName();

   public abstract T getValue();

   public abstract Optional<T> getDefaultValue();

   public Optional<Component> getConfigError() {
      return Optional.empty();
   }

   public boolean isEdited() {
      return this.getConfigError().isPresent();
   }

   public List<? extends GuiEventListener> children() {
      return Collections.emptyList();
   }

   public List<? extends NarratableEntry> narratables() {
      return Collections.emptyList();
   }

   public void save() {
      if (this.saveCallback != null) {
         this.saveCallback.accept(this.getValue());
      }
   }

   public boolean isFocused() {
      return false;
   }

   public void setFocused(boolean focused) {
   }

   public NarrationPriority narrationPriority() {
      return NarrationPriority.NONE;
   }

   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }
}
