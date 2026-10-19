package dev.ftb.mods.ftbquests.util;

import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbquests.client.gui.SelectQuestObjectScreen;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class ConfigQuestObject<T extends QuestObjectBase> extends ConfigValue<T> {
   public final Predicate<QuestObjectBase> predicate;
   private final Function<T, Component> formatter;

   public ConfigQuestObject(Predicate<QuestObjectBase> predicate, Function<T, Component> formatter) {
      this.predicate = predicate;
      this.formatter = formatter;
   }

   public ConfigQuestObject(Predicate<QuestObjectBase> predicate) {
      this(predicate, null);
   }

   public static Component formatEntry(QuestObjectBase qo) {
      return qo.getMutableTitle().withStyle(qo.getObjectType().getColor());
   }

   public Component getStringForGUI(@Nullable T value) {
      return (Component)(value == null ? Component.empty() : value.getTitle());
   }

   public void onClicked(Widget clickedWidget, MouseButton button, ConfigCallback callback) {
      if (this.getCanEdit()) {
         new SelectQuestObjectScreen<>(this, callback).withFormatter(this.formatter).openGui();
      }
   }

   public void addInfo(TooltipList list) {
      if (this.value != null) {
         list.add(info("ID", this.value));
      }
   }
}
