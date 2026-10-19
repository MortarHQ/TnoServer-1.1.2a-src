package noobanidus.mods.lootr.common.api.data;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.IMarkChanged;
import noobanidus.mods.lootr.common.api.IOpeners;
import noobanidus.mods.lootr.common.api.IRedirect;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ILootrSavedData extends IRedirect<ILootrInfo>, ILootrInfo, IOpeners, IMarkChanged {
   void update(ILootrInfo var1);

   void refresh();

   default boolean clearInventories(ServerPlayer player) {
      return this.clearInventories(player.getUUID());
   }

   boolean clearInventories(UUID var1);

   default ILootrInventory getInventory(ServerPlayer player) {
      return this.getInventory(player.getUUID());
   }

   default ILootrInventory getOrCreateInventory(ILootrInfoProvider provider, ServerPlayer player, LootFiller filler) {
      if (provider.canPlayerOpen(player)) {
         ILootrInventory result = this.getInventory(player);
         return result != null ? result : this.createInventory(provider, player, filler);
      } else {
         provider.informPlayerCannotOpen(player);
         return null;
      }
   }

   ILootrInventory getInventory(UUID var1);

   ILootrInventory createInventory(ILootrInfoProvider var1, ServerPlayer var2, LootFiller var3);

   ILootrInventory createInventory(ILootrInfoProvider var1, UUID var2, LootFiller var3);

   @Deprecated
   @Nullable
   @Override
   default ILootrInfo.LootrInfoType getInfoType() {
      return this.getRedirect().getInfoType();
   }

   @Deprecated
   @Nullable
   @Override
   default LootrBlockType getInfoBlockType() {
      return this.getRedirect().getInfoBlockType();
   }

   @Nullable
   @Override
   default ILootrType getInfoNewType() {
      return this.getRedirect().getInfoNewType();
   }

   @NotNull
   @Override
   default Vec3 getInfoVec() {
      return this.getRedirect().getInfoVec();
   }

   @NotNull
   @Override
   default UUID getInfoUUID() {
      return this.getRedirect().getInfoUUID();
   }

   @Override
   default String getInfoKey() {
      return this.getRedirect().getInfoKey();
   }

   @NotNull
   @Override
   default BlockPos getInfoPos() {
      return this.getRedirect().getInfoPos();
   }

   @Nullable
   @Override
   default Component getInfoDisplayName() {
      return this.getRedirect().getInfoDisplayName();
   }

   @NotNull
   @Override
   default ResourceKey<Level> getInfoDimension() {
      return this.getRedirect().getInfoDimension();
   }

   @Override
   default int getInfoContainerSize() {
      return this.getRedirect().getInfoContainerSize();
   }

   @Nullable
   @Override
   default Level getInfoLevel() {
      return this.getRedirect().getInfoLevel();
   }

   @Nullable
   @Override
   default Container getInfoContainer() {
      return this.getRedirect().getInfoContainer();
   }

   @Nullable
   @Override
   default NonNullList<ItemStack> getInfoReferenceInventory() {
      return this.getRedirect().getInfoReferenceInventory();
   }

   @Override
   default boolean isInfoReferenceInventory() {
      return this.getRedirect().isInfoReferenceInventory();
   }

   @Override
   default ResourceKey<LootTable> getInfoLootTable() {
      return this.getRedirect().getInfoLootTable();
   }

   @Override
   default long getInfoLootSeed() {
      return this.getRedirect().getInfoLootSeed();
   }
}
