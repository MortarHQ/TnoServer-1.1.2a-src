package de.keksuccino.konkrete.gui.content;

import de.keksuccino.konkrete.gui.content.handling.AdvancedWidgetsHandler;
import de.keksuccino.konkrete.gui.content.handling.IAdvancedWidgetBase;
import de.keksuccino.konkrete.input.CharData;
import de.keksuccino.konkrete.input.CharacterFilter;
import de.keksuccino.konkrete.input.KeyboardData;
import de.keksuccino.konkrete.mixin.mixins.client.IMixinScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated(forRemoval = true)
public class ExtendedEditBox extends EditBox implements IAdvancedWidgetBase {
   protected boolean handleSelf = false;
   protected CharacterFilter characterFilter;

   public ExtendedEditBox(Font font, int x, int y, int width, int height, Component hint, boolean handleSelf) {
      super(font, x, y, width, height, hint);
      this.handleSelf = handleSelf;
   }

   public ExtendedEditBox(Font font, int x, int y, int width, int height, @Nullable EditBox editBox, Component hint, boolean handleSelf) {
      super(font, x, y, width, height, editBox, hint);
      this.handleSelf = handleSelf;
   }

   public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partial) {
      if (this.handleSelf) {
         AdvancedWidgetsHandler.handleWidget(this);
         Screen s = Minecraft.getInstance().screen;
         if (s != null && !((IMixinScreen)s).getChildrenKonkrete().contains(this)) {
            ((IMixinScreen)s).invokeAddWidgetKonkrete(this);
         }
      }

      super.render(graphics, mouseX, mouseY, partial);
   }

   @Nullable
   public CharacterFilter getCharacterFilter() {
      return this.characterFilter;
   }

   public void setCharacterFilter(@Nullable CharacterFilter characterFilter) {
      this.characterFilter = characterFilter;
   }

   public boolean keyPressed(int p_94132_, int p_94133_, int p_94134_) {
      return !this.handleSelf ? super.keyPressed(p_94132_, p_94133_, p_94134_) : false;
   }

   @Override
   public void onKeyPress(KeyboardData d) {
      if (this.handleSelf) {
         super.keyPressed(d.keycode, d.scancode, d.modfiers);
      }
   }

   public boolean charTyped(char character, int modifiers) {
      return this.handleSelf || this.characterFilter != null && !this.characterFilter.isAllowed(character) ? false : super.charTyped(character, modifiers);
   }

   public void insertText(String textToWrite) {
      if (this.characterFilter != null) {
         textToWrite = this.characterFilter.filterForAllowedChars(textToWrite);
      }

      super.insertText(textToWrite);
   }

   @Override
   public void onCharTyped(CharData d) {
      if (this.handleSelf && (this.characterFilter == null || this.characterFilter.isAllowed(d.typedChar))) {
         super.charTyped(d.typedChar, d.modfiers);
      }
   }
}
