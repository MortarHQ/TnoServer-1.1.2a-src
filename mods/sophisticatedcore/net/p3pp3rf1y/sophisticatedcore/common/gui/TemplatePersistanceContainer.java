package net.p3pp3rf1y.sophisticatedcore.common.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.SnbtPrinterTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.network.SyncDatapackSettingsTemplatePayload;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.settings.DatapackSettingsTemplateManager;
import net.p3pp3rf1y.sophisticatedcore.settings.ISettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsHandler;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsTemplateStorage;
import net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay.ItemDisplaySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;
import net.p3pp3rf1y.sophisticatedcore.util.NoopStorageWrapper;

public class TemplatePersistanceContainer {
   private static final Pattern EXPORT_FILE_NAME_PATTERN = Pattern.compile("[A-Za-z0-9_\\-\\s]+");
   private static final int TOTAL_ORDINAL_SAVE_SLOTS = 10;
   private static final String ACTION_TAG = "action";
   private static final String SAVE_SLOT_TAG = "saveSlot";
   private static final String LOAD_SLOT_TAG = "loadSlot";
   static final String TEMPLATE_PERSISTANCE_TAG = "templatePersistance";
   private final SettingsContainerMenu<?> settingsContainer;
   private final RegistryAccess registryAccess;
   private final List<TemplatePersistanceContainer.IPersistanceSlot> saveSlots = new ArrayList<>();
   private int saveSlotIndex = 0;
   private final List<TemplatePersistanceContainer.IPersistanceSlot> loadSlots = new ArrayList<>();
   private int loadSlotIndex = -1;
   @Nullable
   private TemplatePersistanceContainer.TemplateSettingsHandler selectedTemplate;
   private Runnable onSlotsRefreshed = () -> {};

   public TemplatePersistanceContainer(SettingsContainerMenu<?> settingsContainer, RegistryAccess registryAccess) {
      this.settingsContainer = settingsContainer;
      this.registryAccess = registryAccess;
      this.initSlots();
   }

   public void setOnSlotsRefreshed(Runnable onSlotsRefreshed) {
      this.onSlotsRefreshed = onSlotsRefreshed;
   }

   private void initSlots() {
      this.saveSlots.clear();

      int i;
      for (i = 0; i < this.getNumberOfSaves(); i++) {
         this.saveSlots.add(new TemplatePersistanceContainer.OrdinalPersistanceSlot(i + 1));
      }

      if (i < 9) {
         this.saveSlots.add(new TemplatePersistanceContainer.OrdinalPersistanceSlot(i + 1));
      }

      this.getNamedSaves().forEach(name -> this.saveSlots.add(new TemplatePersistanceContainer.NamedPersistanceSlot(name)));
      this.saveSlots.add(new TemplatePersistanceContainer.EditNamePersistanceSlot(""));
      this.loadSlots.clear();

      for (int var2 = 0; var2 < this.getNumberOfSaves(); var2++) {
         this.loadSlots.add(new TemplatePersistanceContainer.OrdinalPersistanceSlot(var2 + 1));
      }

      this.getNamedSaves().forEach(name -> this.loadSlots.add(new TemplatePersistanceContainer.NamedPersistanceSlot(name)));
      DatapackSettingsTemplateManager.getTemplates()
         .forEach(
            (datapackName, templates) -> templates.forEach(
               (templateName, templateNbt) -> this.loadSlots.add(new TemplatePersistanceContainer.DatapackSlot(datapackName, templateName))
            )
         );
      if (this.loadSlotIndex == -1 && !this.loadSlots.isEmpty()) {
         this.loadSlotIndex = 0;
      } else if (this.loadSlotIndex != -1 && this.loadSlots.isEmpty()) {
         this.loadSlotIndex = -1;
      }

      this.updateSelectedTemplate();
      this.onSlotsRefreshed.run();
   }

   public void handlePacket(CompoundTag data) {
      if (data.contains("action")) {
         String action = data.getString("action");
         switch (action) {
            case "saveTemplate":
               this.saveTemplate(data.getString("slotName"));
               break;
            case "loadTemplate":
               this.loadTemplate();
               break;
            case "exportTemplate":
               this.exportTemplate(data.getString("fileName"));
         }
      }

      if (data.contains("saveSlot")) {
         this.scrollSaveSlot(data.getBoolean("saveSlot"));
      } else if (data.contains("loadSlot")) {
         this.scrollLoadSlot(data.getBoolean("loadSlot"));
      }
   }

   private void sendDataToServer(Supplier<CompoundTag> compoundSupplier) {
      this.settingsContainer.sendDataToServer(() -> {
         CompoundTag compound = new CompoundTag();
         compound.put("templatePersistance", (Tag)compoundSupplier.get());
         return compound;
      });
   }

   private Player getPlayer() {
      return this.settingsContainer.getPlayer();
   }

   public void loadTemplate() {
      if (this.selectedTemplate != null) {
         this.settingsContainer
            .getStorageWrapper()
            .getSettingsHandler()
            .getSettingsCategories()
            .values()
            .forEach(
               category -> this.overwriteCategory(
                  category.getClass(), (ISettingsCategory<?>)category, this.selectedTemplate.getTypeCategory((Class<ISettingsCategory<?>>)category.getClass())
               )
            );
         this.sendDataToServer(() -> NBTHelper.putString(new CompoundTag(), "action", "loadTemplate"));
         if (this.getPlayer().level().isClientSide()) {
            this.getPlayer()
               .displayClientMessage(
                  Component.translatable(
                     TranslationHelper.INSTANCE.translSettingsMessage("load_template"), new Object[]{this.loadSlots.get(this.loadSlotIndex).getSlotName()}
                  ),
                  false
               );
         }
      }
   }

   private <T extends ISettingsCategory<T>> void overwriteCategory(
      Class<T> categoryClazz, ISettingsCategory<?> currentCategory, ISettingsCategory<?> otherCategory
   ) {
      ((ISettingsCategory<ISettingsCategory<?>>)currentCategory).overwriteWith(otherCategory);
   }

   public void saveTemplate(String slotName) {
      SettingsTemplateStorage settingsTemplateStorage = SettingsTemplateStorage.get();
      TemplatePersistanceContainer.IPersistanceSlot saveSlot = this.saveSlots.get(this.saveSlotIndex);
      saveSlot.setSlotName(slotName);
      saveSlot.persistTo(this.getPlayer(), settingsTemplateStorage, this.settingsContainer.getStorageWrapper().getSettingsHandler().getNbt().copy());
      this.sendDataToServer(() -> NBTHelper.putString(NBTHelper.putString(new CompoundTag(), "action", "saveTemplate"), "slotName", slotName));
      this.initSlots();
      this.moveSaveSlotIndexTo(saveSlot.getSlotName());
      if (this.getPlayer().level().isClientSide()) {
         this.getPlayer()
            .displayClientMessage(
               Component.translatable(TranslationHelper.INSTANCE.translSettingsMessage("save_template"), new Object[]{saveSlot.getSlotName()}), false
            );
      }
   }

   private void moveSaveSlotIndexTo(String slotName) {
      for (int i = 0; i < this.saveSlots.size(); i++) {
         if (this.saveSlots.get(i).getSlotName().equals(slotName)) {
            this.saveSlotIndex = i;
            break;
         }
      }
   }

   public void scrollSaveSlot(boolean next) {
      this.saveSlotIndex += next ? 1 : -1;
      if (this.saveSlotIndex < 0) {
         this.saveSlotIndex = this.saveSlots.size() - 1;
      }

      if (this.saveSlotIndex >= this.saveSlots.size()) {
         this.saveSlotIndex = 0;
      }

      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "saveSlot", next));
   }

   public void scrollLoadSlot(boolean next) {
      if (this.loadSlots.isEmpty()) {
         this.loadSlotIndex = -1;
      } else {
         this.loadSlotIndex += next ? 1 : -1;
         if (this.loadSlotIndex < 0) {
            this.loadSlotIndex = this.loadSlots.size() - 1;
         }

         if (this.loadSlotIndex >= this.loadSlots.size()) {
            this.loadSlotIndex = 0;
         }

         this.updateSelectedTemplate();
         this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "loadSlot", next));
      }
   }

   private void updateSelectedTemplate() {
      if (this.loadSlotIndex > -1 && this.loadSlotIndex < this.loadSlots.size()) {
         CompoundTag settingsTag = this.loadSlots.get(this.loadSlotIndex).getSettingsNbt(this.getPlayer(), SettingsTemplateStorage.get());
         this.selectedTemplate = new TemplatePersistanceContainer.TemplateSettingsHandler(settingsTag, this.registryAccess) {
            @Override
            protected SettingsHandler getCurrentSettingsHandler() {
               return TemplatePersistanceContainer.this.settingsContainer.getStorageWrapper().getSettingsHandler();
            }
         };
      }
   }

   public MutableComponent getSaveSlotTooltipName() {
      return this.saveSlots.get(this.saveSlotIndex).getSlotTooltipName();
   }

   public MutableComponent getLoadSlotTooltipName() {
      return this.loadSlots.get(this.loadSlotIndex).getSlotTooltipName();
   }

   public int getLoadSlot() {
      return this.loadSlotIndex;
   }

   private int getNumberOfSaves() {
      return SettingsTemplateStorage.get().getPlayerTemplates(this.settingsContainer.getPlayer()).size();
   }

   private List<String> getNamedSaves() {
      return new ArrayList<>(SettingsTemplateStorage.get().getPlayerNamedTemplates(this.settingsContainer.getPlayer()).keySet());
   }

   public Optional<TemplatePersistanceContainer.TemplateSettingsHandler> getSelectedTemplate() {
      return Optional.ofNullable(this.selectedTemplate);
   }

   public boolean showsTextbox() {
      return this.saveSlots.get(this.saveSlotIndex).showsTextbox();
   }

   public void refreshTemplateSlots() {
      this.initSlots();
      this.onSlotsRefreshed.run();
   }

   public void exportTemplate(String fileName) {
      if (fileName.isEmpty()) {
         this.getPlayer()
            .displayClientMessage(
               Component.translatable(TranslationHelper.INSTANCE.translSettingsMessage("export_template.empty_name")).withStyle(ChatFormatting.RED), false
            );
      } else {
         Matcher matcher = EXPORT_FILE_NAME_PATTERN.matcher(fileName);
         if (!matcher.matches()) {
            this.getPlayer()
               .displayClientMessage(
                  Component.translatable(
                        TranslationHelper.INSTANCE.translSettingsMessage("export_template.invalid_characters"),
                        new Object[]{findNonMatchingCharacters(matcher, fileName)}
                     )
                     .withStyle(ChatFormatting.RED),
                  false
               );
         } else {
            fileName = fileName.replace(' ', '_');
            fileName = fileName.toLowerCase(Locale.ROOT);
            String finalFileName = fileName;
            this.sendDataToServer(() -> NBTHelper.putString(NBTHelper.putString(new CompoundTag(), "action", "exportTemplate"), "fileName", finalFileName));
            if (this.getPlayer() instanceof ServerPlayer serverPlayer) {
               ServerLevel serverLevel = serverPlayer.serverLevel();
               Path datapacksDir = serverLevel.getServer().getWorldPath(LevelResource.DATAPACK_DIR);
               String playersFolder = this.getPlayer().getScoreboardName().toLowerCase(Locale.ROOT) + "_soph_templates";
               Path datapackRoot = datapacksDir.resolve(playersFolder);
               Path templatesDir = datapackRoot.resolve("data/" + playersFolder + "/sophisticated_settingstemplates");
               if (!initDatapackStructure(datapackRoot, templatesDir)) {
                  return;
               }

               Path exportPath = templatesDir.resolve(fileName + ".snbt");
               CompoundTag settingsNbt = this.settingsContainer.getStorageWrapper().getSettingsHandler().getNbt().copy();

               try {
                  NbtToSnbt.writeSnbt(CachedOutput.NO_CACHE, exportPath, new SnbtPrinterTagVisitor().visit(settingsNbt));
               } catch (IOException e) {
                  SophisticatedCore.LOGGER.error("Error writing template export", e);
                  return;
               }

               DatapackSettingsTemplateManager.putTemplate(playersFolder, fileName, settingsNbt);
               PacketDistributor.sendToPlayer(
                  serverPlayer, new SyncDatapackSettingsTemplatePayload(playersFolder, fileName, settingsNbt), new CustomPacketPayload[0]
               );
               this.initSlots();
               this.getPlayer()
                  .displayClientMessage(
                     Component.translatable(
                        TranslationHelper.INSTANCE.translSettingsMessage("export_template"),
                        new Object[]{serverLevel.getServer().getWorldPath(LevelResource.ROOT).relativize(exportPath).toString()}
                     ),
                     false
                  );
            }
         }
      }
   }

   public static String findNonMatchingCharacters(Matcher matcher, String input) {
      StringBuilder nonMatchingCharacters = new StringBuilder();

      for (int i = 0; i < input.length(); i++) {
         char c = input.charAt(i);
         if (!matcher.reset(String.valueOf(c)).matches()) {
            nonMatchingCharacters.append(c);
         }
      }

      return nonMatchingCharacters.toString();
   }

   public boolean templateHasTooManySlots() {
      return this.selectedTemplate != null
         && this.selectedTemplate
            .getSettingsCategories()
            .values()
            .stream()
            .anyMatch(category -> category.isLargerThanNumberOfSlots(this.settingsContainer.getStorageWrapper().getInventoryHandler().getSlots()));
   }

   private static boolean initDatapackStructure(Path datapackRoot, Path templatesDir) {
      try {
         Files.createDirectories(templatesDir);
      } catch (IOException e) {
         SophisticatedCore.LOGGER.error("Error creating directory for template export", e);
         return false;
      }

      Path packMcmetaFile = datapackRoot.resolve("pack.mcmeta");
      if (!Files.exists(packMcmetaFile)) {
         try {
            Files.writeString(
               packMcmetaFile,
               "{\n    \"pack\": {\n        \"pack_format\": 15,\n        \"description\": \"Sophisticated Settings Templates data pack\"\n    }\n}\n"
            );
         } catch (IOException e) {
            SophisticatedCore.LOGGER.error("Error creating pack.mcmeta for template export", e);
            return false;
         }
      }

      return true;
   }

   public Optional<String> getLoadSlotSource() {
      return this.loadSlots.get(this.loadSlotIndex).getSlotSource();
   }

   private static class DatapackSlot implements TemplatePersistanceContainer.IPersistanceSlot {
      private final String datapackName;
      private final String templateName;

      public DatapackSlot(String datapackName, String templateName) {
         this.datapackName = datapackName;
         this.templateName = templateName;
      }

      @Override
      public String getName() {
         return "datapack";
      }

      @Override
      public String getSlotName() {
         return this.templateName;
      }

      @Override
      public CompoundTag getSettingsNbt(Player player, SettingsTemplateStorage settingsTemplateStorage) {
         return DatapackSettingsTemplateManager.getTemplateNbt(this.datapackName, this.templateName).orElseGet(CompoundTag::new);
      }

      @Override
      public Optional<String> getSlotSource() {
         return Optional.of(this.datapackName);
      }
   }

   private static class EditNamePersistanceSlot extends TemplatePersistanceContainer.NamedPersistanceSlot {
      public EditNamePersistanceSlot(String slotName) {
         super(slotName);
      }

      @Override
      public boolean showsTextbox() {
         return true;
      }

      @Override
      public void setSlotName(String slotName) {
         this.slotName = slotName;
      }

      @Override
      public MutableComponent getSlotTooltipName() {
         return Component.translatable(TranslationHelper.INSTANCE.translSettingsButton("save_template.custom_name_slot"));
      }
   }

   private interface IPersistanceSlot {
      String getName();

      String getSlotName();

      default void serialize(CompoundTag tag) {
         tag.putString("name", this.getName());
      }

      default void persistTo(Player player, SettingsTemplateStorage settingsTemplateStorage, CompoundTag settingsCopy) {
      }

      default boolean showsTextbox() {
         return false;
      }

      default void setSlotName(String slotName) {
      }

      default MutableComponent getSlotTooltipName() {
         return Component.literal(this.getSlotName());
      }

      CompoundTag getSettingsNbt(Player var1, SettingsTemplateStorage var2);

      default Optional<String> getSlotSource() {
         return Optional.empty();
      }
   }

   private static class NamedPersistanceSlot implements TemplatePersistanceContainer.IPersistanceSlot {
      protected String slotName;

      public NamedPersistanceSlot(String slotName) {
         this.slotName = slotName;
      }

      @Override
      public String getName() {
         return "named";
      }

      @Override
      public String getSlotName() {
         return this.slotName;
      }

      @Override
      public void serialize(CompoundTag tag) {
         TemplatePersistanceContainer.IPersistanceSlot.super.serialize(tag);
         tag.putString("slot", this.slotName);
      }

      @Override
      public void persistTo(Player player, SettingsTemplateStorage settingsTemplateStorage, CompoundTag settingsCopy) {
         settingsTemplateStorage.putPlayerNamedTemplate(player, this.slotName, settingsCopy);
      }

      @Override
      public CompoundTag getSettingsNbt(Player player, SettingsTemplateStorage settingsTemplateStorage) {
         return settingsTemplateStorage.getPlayerNamedTemplates(player).getOrDefault(this.slotName, new CompoundTag());
      }
   }

   private static class OrdinalPersistanceSlot implements TemplatePersistanceContainer.IPersistanceSlot {
      private final int slot;

      public OrdinalPersistanceSlot(int slot) {
         this.slot = slot;
      }

      @Override
      public String getName() {
         return "ordinal";
      }

      @Override
      public String getSlotName() {
         return String.valueOf(this.slot);
      }

      @Override
      public void serialize(CompoundTag tag) {
         TemplatePersistanceContainer.IPersistanceSlot.super.serialize(tag);
         tag.putInt("slot", this.slot);
      }

      @Override
      public void persistTo(Player player, SettingsTemplateStorage settingsTemplateStorage, CompoundTag settingsCopy) {
         settingsTemplateStorage.putPlayerTemplate(player, this.slot, settingsCopy);
      }

      @Override
      public CompoundTag getSettingsNbt(Player player, SettingsTemplateStorage settingsTemplateStorage) {
         return settingsTemplateStorage.getPlayerTemplates(player).getOrDefault(this.slot, new CompoundTag());
      }
   }

   public abstract static class TemplateSettingsHandler extends SettingsHandler {
      protected TemplateSettingsHandler(CompoundTag contentsNbt, RegistryAccess registryAccess) {
         super(contentsNbt, () -> {}, NoopStorageWrapper.INSTANCE::getInventoryHandler, NoopStorageWrapper.INSTANCE::getRenderInfo);
      }

      protected abstract SettingsHandler getCurrentSettingsHandler();

      @Override
      protected CompoundTag getSettingsNbtFromContentsNbt(CompoundTag contentsNbt) {
         return contentsNbt;
      }

      @Override
      protected void addItemDisplayCategory(
         Supplier<InventoryHandler> inventoryHandlerSupplier, Supplier<RenderInfo> renderInfoSupplier, CompoundTag settingsNbt
      ) {
         int itemNumberLimit = this.getCurrentSettingsHandler().getTypeCategory(ItemDisplaySettingsCategory.class).getItemNumberLimit();
         this.addSettingsCategory(
            settingsNbt,
            "item_display",
            this.markContentsDirty,
            (categoryNbt, saveNbt) -> new ItemDisplaySettingsCategory(
               inventoryHandlerSupplier, renderInfoSupplier, categoryNbt, saveNbt, itemNumberLimit, () -> this.getTypeCategory(MemorySettingsCategory.class)
            )
         );
      }

      @Override
      public String getGlobalSettingsCategoryName() {
         return this.getCurrentSettingsHandler().getGlobalSettingsCategoryName();
      }

      @Override
      public ISettingsCategory<?> instantiateGlobalSettingsCategory(CompoundTag categoryNbt, Consumer<CompoundTag> saveNbt) {
         return this.getCurrentSettingsHandler().instantiateGlobalSettingsCategory(categoryNbt, saveNbt);
      }

      @Override
      protected void saveCategoryNbt(CompoundTag settingsNbt, String categoryName, CompoundTag tag) {
      }
   }
}
