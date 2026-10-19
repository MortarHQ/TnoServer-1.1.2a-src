package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.event.level.LevelEvent.Load;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class BackpackTemplateStorage extends SavedData {
   private static final String SAVED_DATA_NAME = "sophisticatedbackpacks_backpack_templates";
   private static final BackpackTemplateStorage clientStorageCopy = new BackpackTemplateStorage();
   private final Map<ResourceLocation, CompoundTag> backpackTemplates = new HashMap<>();

   private BackpackTemplateStorage() {
   }

   public static BackpackTemplateStorage get() {
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
         if (server != null) {
            ServerLevel overworld = server.getLevel(Level.OVERWORLD);
            DimensionDataStorage storage = overworld.getDataStorage();
            return (BackpackTemplateStorage)storage.computeIfAbsent(
               new Factory(BackpackTemplateStorage::new, BackpackTemplateStorage::load), "sophisticatedbackpacks_backpack_templates"
            );
         }
      }

      return clientStorageCopy;
   }

   public static BackpackTemplateStorage load(CompoundTag nbt, Provider registries) {
      BackpackTemplateStorage storage = new BackpackTemplateStorage();
      readBackpackTemplates(nbt, storage);
      return storage;
   }

   private static void readBackpackTemplates(CompoundTag nbt, BackpackTemplateStorage storage) {
      if (nbt.contains("backpackTemplates")) {
         ListTag list = nbt.getList("backpackTemplates", 10);

         for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.getCompound(i);
            ResourceLocation templateName = ResourceLocation.parse(tag.getString("templateName"));
            CompoundTag contents = tag.getCompound("contents");
            storage.backpackTemplates.put(templateName, contents);
         }
      }
   }

   public CompoundTag save(CompoundTag compound, Provider registries) {
      CompoundTag ret = new CompoundTag();
      this.writeBackpackTemplates(ret);
      return ret;
   }

   private void writeBackpackTemplates(CompoundTag ret) {
      if (!this.backpackTemplates.isEmpty()) {
         ListTag backpackTemplatesNbt = new ListTag();

         for (Entry<ResourceLocation, CompoundTag> entry : this.backpackTemplates.entrySet()) {
            CompoundTag ret1 = new CompoundTag();
            ret1.putString("templateName", entry.getKey().toString());
            ret1.put("contents", (Tag)entry.getValue());
            backpackTemplatesNbt.add(ret1);
         }

         ret.put("backpackTemplates", backpackTemplatesNbt);
      }
   }

   public Optional<CompoundTag> getBackpackTemplate(ResourceLocation templateName) {
      return Optional.ofNullable(this.backpackTemplates.get(templateName));
   }

   public void setBackpackTemplate(ResourceLocation templateName, CompoundTag contents) {
      this.backpackTemplates.put(templateName, contents);
      this.setDirty();
   }

   public void removeBackpackTemplate(ResourceLocation templateName) {
      this.backpackTemplates.remove(templateName);
      this.setDirty();
   }

   public Map<ResourceLocation, CompoundTag> getBackpackTemplates() {
      return this.backpackTemplates;
   }

   public static void onClientWorldLoad(Load evt) {
      if (evt.getLevel().isClientSide()) {
         clientStorageCopy.backpackTemplates.clear();
      }
   }
}
