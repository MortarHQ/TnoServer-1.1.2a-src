package noobanidus.mods.lootr.common.data;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.BaseLootrInfo;
import noobanidus.mods.lootr.common.api.data.ILootrInfo;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.ILootrSavedData;
import noobanidus.mods.lootr.common.api.data.LootFiller;
import org.jetbrains.annotations.Nullable;

public class LootrSavedData extends SavedData implements ILootrSavedData {
   private boolean hasBeenOpened;
   private ILootrInfo info;
   private final Map<UUID, LootrInventory> inventories = new HashMap<>();
   private final Set<UUID> openers = new ObjectLinkedOpenHashSet();
   private final Set<UUID> actualOpeners = new ObjectLinkedOpenHashSet();

   protected LootrSavedData(ILootrInfo info) {
      this(info, false);
   }

   protected LootrSavedData(ILootrInfo info, boolean noCopy) {
      if (noCopy) {
         this.info = info;
      } else {
         this.info = BaseLootrInfo.copy(info);
      }
   }

   public static Supplier<LootrSavedData> fromInfo(ILootrInfo info) {
      return () -> new LootrSavedData(info);
   }

   public static LootrSavedData load(CompoundTag compound, Provider provider) {
      ILootrInfo info = ILootrInfo.loadInfoFromTag(compound, provider);
      LootrSavedData data = new LootrSavedData(info, true);
      data.inventories.clear();
      data.openers.clear();
      data.actualOpeners.clear();
      ListTag compounds = compound.getList("inventories", 10);

      for (int i = 0; i < compounds.size(); i++) {
         CompoundTag thisTag = compounds.getCompound(i);
         CompoundTag itemTag = thisTag.getCompound("chest");
         NonNullList<ItemStack> items = info.buildInitialInventory();
         ContainerHelper.loadAllItems(itemTag, items, provider);
         UUID uuid = thisTag.getUUID("uuid");
         data.inventories.put(uuid, new LootrInventory(data, items));
      }

      if (compound.contains("openers")) {
         for (Tag opener : compound.getList("openers", 11)) {
            data.openers.add(NbtUtils.loadUUID(opener));
         }
      }

      if (compound.contains("actualOpeners")) {
         for (Tag opener : compound.getList("actualOpeners", 11)) {
            data.actualOpeners.add(NbtUtils.loadUUID(opener));
         }
      }

      if (compound.contains("hasBeenOpened")) {
         data.hasBeenOpened = compound.getBoolean("hasBeenOpened");
      }

      return data;
   }

   public ILootrInfo getRedirect() {
      return this.info;
   }

   @Override
   public Set<UUID> getVisualOpeners() {
      return this.openers;
   }

   @Override
   public boolean addVisualOpener(UUID uuid) {
      boolean result = ILootrSavedData.super.addVisualOpener(uuid);
      if (result) {
         this.setDirty();
      }

      return result;
   }

   @Override
   public boolean removeVisualOpener(UUID uuid) {
      boolean result = ILootrSavedData.super.removeVisualOpener(uuid);
      if (result) {
         this.setDirty();
      }

      return result;
   }

   @Override
   public boolean addActualOpener(UUID uuid) {
      boolean result = ILootrSavedData.super.addActualOpener(uuid);
      if (result) {
         this.setDirty();
      }

      return result;
   }

   private void removeOpener(UUID uuid) {
      Set<UUID> visualOpeners = this.getVisualOpeners();
      if (visualOpeners != null && visualOpeners.remove(uuid)) {
         this.setDirty();
      }
   }

   @Override
   public Set<UUID> getActualOpeners() {
      return this.actualOpeners;
   }

   @Override
   public void markChanged() {
      this.setDirty();
   }

   @Override
   public void markDataChanged() {
      this.markChanged();
   }

   @Nullable
   public LootrInventory getInventory(UUID id) {
      LootrInventory inventory = this.inventories.get(id);
      if (inventory != null) {
         inventory.setInfo(this);
      }

      return inventory;
   }

   public LootrInventory createInventory(ILootrInfoProvider provider, ServerPlayer player, LootFiller filler) {
      if (provider.canPlayerOpen(player)) {
         LootrInventory result = new LootrInventory(this, provider.buildInitialInventory());
         if (!LootrAPI.isFakePlayer(player)) {
            filler.unpackLootTable(provider, player, result);
         }

         this.inventories.put(player.getUUID(), result);
         this.hasBeenOpened = true;
         this.setDirty();
         return result;
      } else {
         provider.informPlayerCannotOpen(player);
         return null;
      }
   }

   public LootrInventory createInventory(ILootrInfoProvider provider, UUID playerId, LootFiller filler) {
      LootrInventory result = new LootrInventory(this, provider.buildInitialInventory());
      filler.unpackLootTable(provider, LootrAPI.getPlayer(playerId), result);
      this.inventories.put(playerId, result);
      this.hasBeenOpened = true;
      this.setDirty();
      return result;
   }

   public CompoundTag save(CompoundTag compound, Provider provider) {
      this.info.saveInfoToTag(compound, provider);
      ListTag compounds = new ListTag();

      for (Entry<UUID, LootrInventory> entry : this.inventories.entrySet()) {
         CompoundTag thisTag = new CompoundTag();
         thisTag.putUUID("uuid", entry.getKey());
         thisTag.put("chest", entry.getValue().saveToTag(provider));
         compounds.add(thisTag);
      }

      compound.put("inventories", compounds);
      ListTag openers = new ListTag();

      for (UUID opener : this.openers) {
         openers.add(NbtUtils.createUUID(opener));
      }

      compound.put("openers", openers);
      ListTag actualOpeners = new ListTag();

      for (UUID opener : this.actualOpeners) {
         actualOpeners.add(NbtUtils.createUUID(opener));
      }

      compound.put("actualOpeners", actualOpeners);
      compound.putBoolean("hasBeenOpened", this.hasBeenOpened);
      return compound;
   }

   @Override
   public void update(ILootrInfo info) {
      BaseLootrInfo infoCopy = BaseLootrInfo.copy(info);
      if (!infoCopy.equals(this.info)) {
         this.markChanged();
         this.info = info;
      }
   }

   @Override
   public void refresh() {
      this.inventories.clear();
      this.hasBeenOpened = false;
      this.markChanged();
   }

   @Override
   public boolean hasBeenOpened() {
      return this.hasBeenOpened;
   }

   public boolean canBeCulled() {
      return !this.inventories.isEmpty() ? false : !this.hasBeenOpened();
   }

   @Override
   public boolean isPhysicallyOpen() {
      return false;
   }

   @Override
   public boolean clearInventories(UUID id) {
      if (this.inventories.remove(id) != null) {
         this.removeOpener(id);
         this.setDirty();
         return true;
      } else {
         return false;
      }
   }

   public void save(File pFile, Provider provider) {
      if (this.isDirty()) {
         pFile.getParentFile().mkdirs();
      }

      super.save(pFile, provider);
   }
}
