package net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.p3pp3rf1y.sophisticatedbackpacks.settings.BackpackMainSettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.settings.ISettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsHandler;
import net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay.ItemDisplaySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;

public class BackpackSettingsHandler extends SettingsHandler {
   public static final String SETTINGS_TAG = "settings";

   public BackpackSettingsHandler(IStorageWrapper backpackWrapper, CompoundTag backpackContentsNbt, Runnable markBackpackContentsDirty) {
      super(backpackContentsNbt, markBackpackContentsDirty, backpackWrapper::getInventoryHandler, backpackWrapper::getRenderInfo);
   }

   public void copyTo(SettingsHandler settingsHandler) {
      if (this.contentsNbt.contains("settings")) {
         settingsHandler.getNbt().put("settings", this.contentsNbt.get("settings"));
      }
   }

   protected CompoundTag getSettingsNbtFromContentsNbt(CompoundTag contentsNbt) {
      return contentsNbt.getCompound("settings");
   }

   protected void addItemDisplayCategory(Supplier<InventoryHandler> inventoryHandlerSupplier, Supplier<RenderInfo> renderInfoSupplier, CompoundTag settingsNbt) {
      this.addSettingsCategory(
         settingsNbt,
         "item_display",
         this.markContentsDirty,
         (categoryNbt, saveNbt) -> new ItemDisplaySettingsCategory(
            inventoryHandlerSupplier,
            renderInfoSupplier,
            categoryNbt,
            saveNbt,
            1,
            () -> (MemorySettingsCategory)this.getTypeCategory(MemorySettingsCategory.class)
         )
      );
   }

   protected void saveCategoryNbt(CompoundTag settingsNbt, String categoryName, CompoundTag tag) {
      settingsNbt.put(categoryName, tag);
      this.contentsNbt.put("settings", settingsNbt);
   }

   public String getGlobalSettingsCategoryName() {
      return "backpackGlobal";
   }

   public ISettingsCategory<?> instantiateGlobalSettingsCategory(CompoundTag categoryNbt, Consumer<CompoundTag> saveNbt) {
      return new BackpackMainSettingsCategory(categoryNbt, saveNbt);
   }

   public BackpackMainSettingsCategory getGlobalSettingsCategory() {
      return (BackpackMainSettingsCategory)this.getTypeCategory(BackpackMainSettingsCategory.class);
   }
}
