package com.blamejared.controlling;

import com.blamejared.controlling.api.entries.ICategoryEntry;
import com.blamejared.controlling.api.entries.IInputEntry;
import com.blamejared.controlling.api.entries.IKeyEntry;
import com.blamejared.searchables.api.SearchableComponent;
import com.blamejared.searchables.api.SearchableType;
import com.blamejared.searchables.api.SearchableType.Builder;
import java.util.Optional;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ControllingConstants {
   public static final MutableComponent COMPONENT_CONTROLS_RESET = Component.translatable("controls.reset");
   public static final MutableComponent COMPONENT_CONTROLS_RESET_ALL = Component.translatable("controls.resetAll");
   public static final MutableComponent COMPONENT_OPTIONS_CONFIRM_RESET = Component.translatable("options.confirmReset");
   public static final MutableComponent COMPONENT_OPTIONS_SHOW_NONE = Component.translatable("options.showNone");
   public static final MutableComponent COMPONENT_OPTIONS_SHOW_ALL = Component.translatable("options.showAll");
   public static final MutableComponent COMPONENT_OPTIONS_SHOW_CONFLICTS = Component.translatable("options.showConflicts");
   public static final MutableComponent COMPONENT_OPTIONS_SORT = Component.translatable("options.sort");
   public static final MutableComponent COMPONENT_OPTIONS_TOGGLE_FREE = Component.translatable("options.toggleFree");
   public static final MutableComponent COMPONENT_OPTIONS_AVAILABLE_KEYS = Component.translatable("options.availableKeys");
   public static final SearchableType<Entry> SEARCHABLE_KEYBINDINGS = new Builder()
      .component(SearchableComponent.create("category", entry -> {
         if (entry instanceof ICategoryEntry cat) {
            return Optional.of(cat.name().getString());
         } else {
            return entry instanceof IKeyEntry key ? Optional.of(key.categoryName().getString()) : Optional.empty();
         }
      }))
      .component(
         SearchableComponent.create(
            "key", entry -> entry instanceof IKeyEntry key ? Optional.of(key.getKey().getTranslatedKeyMessage().getString()) : Optional.empty()
         )
      )
      .defaultComponent(SearchableComponent.create("name", entry -> {
         if (entry instanceof IKeyEntry key) {
            return Optional.of(key.getKeyDesc().getString());
         } else {
            return entry instanceof IInputEntry input ? Optional.of(input.getInput().getName()) : Optional.empty();
         }
      }))
      .build();
}
