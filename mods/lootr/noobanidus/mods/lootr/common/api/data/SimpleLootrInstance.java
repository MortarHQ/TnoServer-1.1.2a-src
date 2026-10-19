package noobanidus.mods.lootr.common.api.data;

import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import noobanidus.mods.lootr.common.api.LootrAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleLootrInstance {
   protected final NonNullList<ItemStack> emptyItemList;
   protected NonNullList<ItemStack> customInventory = null;
   protected final Set<UUID> clientOpeners = new ObjectOpenHashSet();
   protected UUID infoId = null;
   protected boolean hasBeenOpened = false;
   protected String cachedId;
   protected boolean clientOpened = false;
   protected boolean savingToItem = false;
   protected boolean providesOwnUuid = false;
   protected final Supplier<Set<UUID>> visualOpenersSupplier;

   public SimpleLootrInstance(Supplier<Set<UUID>> visualOpenersSupplier, int size) {
      this.emptyItemList = NonNullList.withSize(size, ItemStack.EMPTY);
      this.visualOpenersSupplier = visualOpenersSupplier;
   }

   @Deprecated
   public NonNullList<ItemStack> getItems() {
      return this.getEmptyItemList();
   }

   public NonNullList<ItemStack> getEmptyItemList() {
      return this.emptyItemList;
   }

   @Nullable
   public NonNullList<ItemStack> getCustomInventory() {
      return this.customInventory;
   }

   public boolean isCustomInventory() {
      return this.customInventory != null && !this.customInventory.isEmpty();
   }

   public void setCustomInventory(NonNullList<ItemStack> customInventory) {
      NonNullList<ItemStack> copy = NonNullList.withSize(customInventory.size(), ItemStack.EMPTY);

      for (int i = 0; i < customInventory.size(); i++) {
         copy.set(i, ((ItemStack)customInventory.get(i)).copy());
      }

      this.customInventory = copy;
   }

   public Set<UUID> getClientOpeners() {
      return this.clientOpeners;
   }

   public boolean isClientOpened() {
      return this.clientOpened;
   }

   public void setClientOpened(boolean opened) {
      this.clientOpened = opened;
   }

   @NotNull
   public UUID getInfoUUID() {
      if (this.providesOwnUuid) {
         throw new IllegalStateException("This instance provides its own UUID but hasn't overriden `getInfoUUID`: " + this);
      }

      if (this.infoId == null) {
         this.infoId = UUID.randomUUID();
      }

      return this.infoId;
   }

   public String getInfoKey() {
      if (this.cachedId == null) {
         this.cachedId = ILootrInfo.generateInfoKey(this.getInfoUUID());
      }

      return this.cachedId;
   }

   public boolean hasBeenOpened() {
      return this.hasBeenOpened;
   }

   public int getInfoContainerSize() {
      return this.emptyItemList.size();
   }

   public void setHasBeenOpened() {
      this.hasBeenOpened = true;
   }

   public boolean isSavingToItem() {
      return this.savingToItem;
   }

   public void setSavingToItem(boolean saving) {
      this.savingToItem = saving;
   }

   public void loadAdditional(CompoundTag compound, Provider provder) {
      if (!this.providesOwnUuid && compound.hasUUID("LootrId")) {
         this.infoId = compound.getUUID("LootrId");
      }

      if (compound.contains("LootrHasBeenOpened", 1)) {
         this.hasBeenOpened = compound.getBoolean("LootrHasBeenOpened");
      }

      if (this.infoId == null && !this.providesOwnUuid) {
         this.getInfoUUID();
      }

      this.clientOpeners.clear();
      if (compound.contains("LootrOpeners")) {
         for (Tag thisTag : compound.getList("LootrOpeners", 11)) {
            this.clientOpeners.add(NbtUtils.loadUUID(thisTag));
         }
      }

      if (compound.contains("customInventory") && compound.contains("customSize")) {
         int size = Math.max(compound.getInt("customSize"), this.getInfoContainerSize());
         this.customInventory = NonNullList.withSize(size, ItemStack.EMPTY);
         ContainerHelper.loadAllItems(compound.getCompound("customInventory"), this.customInventory, provder);
      }
   }

   public void saveAdditional(CompoundTag compound, Provider provider, boolean isClientSide) {
      if (!LootrAPI.shouldDiscard() && !this.isSavingToItem() && !this.providesOwnUuid) {
         compound.putUUID("LootrId", this.getInfoUUID());
      }

      compound.putBoolean("LootrHasBeenOpened", this.hasBeenOpened);
      if (isClientSide && !this.clientOpeners.isEmpty()) {
         ListTag list = new ListTag();

         for (UUID opener : this.clientOpeners) {
            list.add(NbtUtils.createUUID(opener));
         }

         compound.put("LootrOpeners", list);
      }

      if (this.customInventory != null && !this.customInventory.isEmpty()) {
         CompoundTag itemTag = new CompoundTag();
         ContainerHelper.saveAllItems(itemTag, this.customInventory, provider);
         compound.put("customInventory", itemTag);
         compound.putInt("customSize", this.customInventory.size());
      }
   }

   public void fillUpdateTag(CompoundTag result, Provider provider, boolean isClientSide) {
      this.saveAdditional(result, provider, isClientSide);
      if (!isClientSide) {
         Set<UUID> currentOpeners = this.visualOpenersSupplier.get();
         if (currentOpeners != null) {
            ListTag list = new ListTag();
            UnmodifiableIterator var6 = Sets.intersection(currentOpeners, LootrAPI.getPlayerIds()).iterator();

            while (var6.hasNext()) {
               UUID opener = (UUID)var6.next();
               list.add(NbtUtils.createUUID(opener));
            }

            if (!list.isEmpty()) {
               result.put("LootrOpeners", list);
            }
         }
      } else {
         LootrAPI.LOG.error("Tried to fillUpdateTag on the client side for SimpleLootrInstance: {}", this);
      }
   }
}
