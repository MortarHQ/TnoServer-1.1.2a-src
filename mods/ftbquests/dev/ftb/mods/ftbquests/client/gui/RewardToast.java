package dev.ftb.mods.ftbquests.client.gui;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.misc.SimpleToast;
import java.util.Objects;
import net.minecraft.network.chat.Component;

public class RewardToast extends SimpleToast {
   private final Component title;
   private final Component text;
   private final Icon icon;

   public RewardToast(Component text, Icon icon) {
      this(null, text, icon);
   }

   public RewardToast(Component title, Component text, Icon icon) {
      this.title = title;
      this.text = text;
      this.icon = icon;
   }

   public Component getTitle() {
      return Objects.requireNonNullElse(this.title, Component.translatable("ftbquests.reward.collected"));
   }

   public Component getSubtitle() {
      return this.text;
   }

   public Icon getIcon() {
      return this.icon;
   }
}
