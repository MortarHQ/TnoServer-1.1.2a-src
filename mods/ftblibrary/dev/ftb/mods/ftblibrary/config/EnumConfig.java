package dev.ftb.mods.ftblibrary.config;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class EnumConfig<E> extends ConfigWithVariants<E> {
   public final NameMap<E> nameMap;

   public EnumConfig(NameMap<E> nm) {
      this.nameMap = nm;
      this.defaultValue = this.nameMap.defaultValue;
      this.value = this.nameMap.defaultValue;
   }

   @Override
   public Component getStringForGUI(E v) {
      return this.nameMap.getDisplayName(v);
   }

   @Override
   public Color4I getColor(E v) {
      Color4I col = this.nameMap.getColor(v);
      return col.isEmpty() ? Tristate.DEFAULT.color : col;
   }

   @Override
   public void addInfo(TooltipList list) {
      super.addInfo(list);
      if (this.nameMap.size() > 0) {
         list.blankLine();

         for (int i = 0; i < this.nameMap.size(); i++) {
            if (i >= 10) {
               list.add(
                  Component.literal("... " + (this.nameMap.size() - i) + " more ...")
                     .withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC})
               );
               break;
            }

            E v = this.nameMap.get(i);
            boolean e = this.isEqual(v, this.value);
            MutableComponent c = Component.literal(e ? "+ " : "- ");
            c.withStyle(e ? ChatFormatting.AQUA : ChatFormatting.DARK_GRAY);
            c.append(this.nameMap.getDisplayName(v));
            list.add(c);
         }
      }
   }

   @Override
   public void onClicked(Widget clickedWidget, MouseButton button, ConfigCallback callback) {
      if (this.nameMap.values.size() <= 16 && !BaseScreen.isCtrlKeyDown()) {
         super.onClicked(clickedWidget, button, callback);
      } else {
         EnumConfig<E>.EnumSelectScreen screen = new EnumConfig.EnumSelectScreen(clickedWidget.getParent(), callback);
         screen.setHasSearchBox(true);
         screen.showBottomPanel(false);
         screen.showCloseButton(true);
         screen.openGui();
      }
   }

   @Override
   public E getIteration(E currentValue, boolean next) {
      return next ? this.nameMap.getNext(currentValue) : this.nameMap.getPrevious(currentValue);
   }

   @Override
   public Icon getIcon(@Nullable E v) {
      if (v != null) {
         Icon icon = this.nameMap.getIcon(v);
         if (!icon.isEmpty()) {
            return icon;
         }
      }

      return super.getIcon(v);
   }

   private class EnumSelectScreen extends AbstractButtonListScreen {
      private final Panel parent;
      private final ConfigCallback callback;
      private int maxWidth = 176;

      public EnumSelectScreen(Panel parent, ConfigCallback callback) {
         this.parent = parent;
         this.callback = callback;

         for (E v : EnumConfig.this.nameMap) {
            this.maxWidth = Math.max(this.maxWidth, this.getTheme().getStringWidth(EnumConfig.this.nameMap.getDisplayName(v)));
         }
      }

      @Override
      public void addButtons(Panel panel) {
         for (final E v : EnumConfig.this.nameMap) {
            panel.add(new SimpleTextButton(panel, EnumConfig.this.nameMap.getDisplayName(v), EnumConfig.this.nameMap.getIcon(v)) {
               @Override
               public void onClicked(MouseButton button) {
                  this.playClickSound();
                  EnumConfig.this.setCurrentValue(v);
                  EnumSelectScreen.this.doAccept();
               }
            });
         }
      }

      @Override
      public boolean onInit() {
         this.setSize(
            Mth.clamp(this.maxWidth + 35, 176, this.getWindow().getGuiScaledWidth() * 3 / 4),
            Mth.clamp(EnumConfig.this.nameMap.size() * 20 + 50, 166, this.getWindow().getGuiScaledHeight() * 4 / 5)
         );
         return super.onInit();
      }

      @Override
      protected void doCancel() {
         this.parent.run();
         this.callback.save(false);
      }

      @Override
      protected void doAccept() {
         this.parent.run();
         this.callback.save(true);
      }
   }
}
