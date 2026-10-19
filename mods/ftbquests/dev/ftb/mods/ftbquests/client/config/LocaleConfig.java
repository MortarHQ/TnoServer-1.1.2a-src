package dev.ftb.mods.ftbquests.client.config;

import com.mojang.datafixers.util.Pair;
import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

public class LocaleConfig extends ConfigValue<String> {
   public static final Color4I COLOR = Color4I.rgb(16755273);

   public void onClicked(Widget clickedWidget, MouseButton button, ConfigCallback callback) {
      LocaleConfig.LocaleSelectorScreen gui = new LocaleConfig.LocaleSelectorScreen(callback);
      gui.setTitle(Component.translatable("ftbquests.xlate.editing_locale"));
      gui.showBottomPanel(false);
      gui.setHasSearchBox(true);
      gui.openGui();
   }

   public Color4I getColor(@Nullable String v) {
      return COLOR;
   }

   public Component getStringForGUI(@Nullable String v) {
      return (Component)(v == null ? NULL_TEXT : Component.literal("\"" + v + "\""));
   }

   private class LocaleSelectorScreen extends AbstractButtonListScreen {
      private final ConfigCallback callback;
      private final List<Pair<String, Component>> entries = new ArrayList<>();
      private final int widest;

      public LocaleSelectorScreen(ConfigCallback callback) {
         this.callback = callback;
         MutableInt widestM = new MutableInt(0);
         this.entries.add(new Pair("", Component.translatable("ftbquests.gui.use_default_lang").withStyle(ChatFormatting.ITALIC)));
         Minecraft.getInstance()
            .getLanguageManager()
            .getLanguages()
            .forEach(
               (lang, info) -> {
                  Component c = Component.literal("[" + lang + "] ")
                     .withStyle(ChatFormatting.YELLOW)
                     .append(info.toComponent().copy().withStyle(ChatFormatting.WHITE));
                  this.entries.add(new Pair(lang, c));
                  widestM.setValue(Math.max(widestM.toInteger(), this.getTheme().getStringWidth(c)));
               }
            );
         this.widest = widestM.intValue();
      }

      public boolean onInit() {
         this.setWidth(this.widest + 25);
         this.setHeight(this.getScreen().getGuiScaledHeight() * 4 / 5);
         return true;
      }

      public void addButtons(Panel panel) {
         this.entries.forEach(entry -> panel.add(new SimpleTextButton(panel, (Component)entry.getSecond(), Color4I.empty()) {
            public void onClicked(MouseButton button) {
               this.playClickSound();
               boolean changed = LocaleConfig.this.setCurrentValue((String)entry.getFirst());
               LocaleSelectorScreen.this.callback.save(changed);
            }
         }));
      }

      protected void doCancel() {
         this.callback.save(false);
      }

      protected void doAccept() {
         this.callback.save(true);
      }
   }
}
