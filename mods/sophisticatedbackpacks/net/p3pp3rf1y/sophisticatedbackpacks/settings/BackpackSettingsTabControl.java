package net.p3pp3rf1y.sophisticatedbackpacks.settings;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsScreen;
import net.p3pp3rf1y.sophisticatedcore.client.gui.Tab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsContainerBase;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsTab;
import net.p3pp3rf1y.sophisticatedcore.settings.StorageSettingsTabControlBase;
import net.p3pp3rf1y.sophisticatedcore.settings.StorageSettingsTabControlBase.ISettingsTabFactory;
import net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay.ItemDisplaySettingsTab;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsTab;
import net.p3pp3rf1y.sophisticatedcore.settings.nosort.NoSortSettingsTab;

public class BackpackSettingsTabControl extends StorageSettingsTabControlBase {
   private static final Map<String, ISettingsTabFactory<?, ?>> SETTINGS_TAB_FACTORIES;

   public BackpackSettingsTabControl(SettingsScreen screen, Position position) {
      super(screen, position);
   }

   protected Tab instantiateReturnBackTab() {
      return new BackToBackpackTab(new Position(this.x, this.getTopY()));
   }

   protected <C extends SettingsContainerBase<?>, T extends SettingsTab<C>> ISettingsTabFactory<C, T> getSettingsTabFactory(String name) {
      return (ISettingsTabFactory<C, T>)SETTINGS_TAB_FACTORIES.get(name);
   }

   protected boolean isSettingsCategoryDisabled(String categoryName) {
      return categoryName.equals("item_display")
         ? Boolean.TRUE.equals(Config.SERVER.itemDisplayDisabled.get())
         : super.isSettingsCategoryDisabled(categoryName);
   }

   static {
      Builder<String, ISettingsTabFactory<?, ?>> builder = new Builder();
      addFactory(builder, "backpackGlobal", BackpackMainSettingsTab::new);
      addFactory(builder, "no_sort", NoSortSettingsTab::new);
      addFactory(builder, "memory", MemorySettingsTab::new);
      addFactory(builder, "item_display", ItemDisplaySettingsTab::new);
      SETTINGS_TAB_FACTORIES = builder.build();
   }
}
