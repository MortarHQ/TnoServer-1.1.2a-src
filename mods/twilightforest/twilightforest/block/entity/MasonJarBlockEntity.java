package twilightforest.block.entity;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerRegistries.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity.WobbleStyle;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.world.AuxiliaryLightManager;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import twilightforest.init.TFBlockEntities;
import twilightforest.network.SetMasonJarItemPacket;

public class MasonJarBlockEntity extends JarBlockEntity {
   public static final String TAG_ITEM = "item";
   public static final String TAG_ANGLE = "rotation";
   protected final MasonJarBlockEntity.MasonJarItemStackHandler item;
   protected int itemRotation = 0;

   public MasonJarBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)TFBlockEntities.MASON_JAR.get(), pos, state);
      this.item = new MasonJarBlockEntity.MasonJarItemStackHandler(this);
   }

   public MasonJarBlockEntity.MasonJarItemStackHandler getItemHandler() {
      return this.item;
   }

   @Override
   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.put("item", this.item.serializeNBT(registries));
      tag.putInt("rotation", this.itemRotation);
   }

   @Override
   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.item.deserializeNBT(registries, tag.getCompound("item"));
      this.itemRotation = tag.getInt("rotation");
   }

   public boolean fillFromLootTable(ResourceKey<LootTable> lootTableKey, long seed, ServerLevel level) {
      MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
      return this.fillFromLootTable(lootTableKey, seed, level, currentServer.reloadableRegistries());
   }

   public boolean fillFromLootTable(ResourceKey<LootTable> lootTableKey, long seed, ServerLevel serverLevel, Holder holder) {
      LootTable lootTable = holder.getLootTable(lootTableKey);
      if (lootTable == LootTable.EMPTY) {
         return false;
      }

      LootParams params = new Builder(serverLevel)
         .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.getBlockPos()))
         .create(LootContextParamSets.CHEST);
      lootTable.getRandomItemsRaw(
         new net.minecraft.world.level.storage.loot.LootContext.Builder(params).withOptionalRandomSeed(seed).create(Optional.of(lootTableKey.location())),
         this::acceptLootTable
      );
      return true;
   }

   private void acceptLootTable(ItemStack stack) {
      MasonJarBlockEntity.MasonJarItemStackHandler jarInv = this.getItemHandler();
      if (jarInv.isEmpty()) {
         jarInv.setItem(stack);
      } else {
         ItemStack contained = jarInv.peekItem();
         if (ItemStack.isSameItemSameComponents(contained, stack)) {
            contained.setCount(Math.min(contained.getCount() + stack.getCount(), contained.getMaxStackSize()));
         }
      }
   }

   public void setFromItem(ItemStack stack) {
      this.applyComponentsFromItemStack(stack);
   }

   @Override
   protected void collectImplicitComponents(net.minecraft.core.component.DataComponentMap.Builder builder) {
      super.collectImplicitComponents(builder);
      builder.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(List.of(this.item.getItem())));
   }

   @Override
   protected void applyImplicitComponents(DataComponentInput input) {
      super.applyImplicitComponents(input);
      this.item.setItem(((ItemContainerContents)input.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)).copyOne());
   }

   @Override
   public void removeComponentsFromTag(CompoundTag tag) {
      super.removeComponentsFromTag(tag);
      tag.remove("item");
   }

   public void setChanged() {
      super.setChanged();
      if (this.level != null) {
         BlockPos pos = this.getBlockPos();
         AuxiliaryLightManager lightManager = this.level.getAuxLightManager(pos);
         if (lightManager != null) {
            lightManager.setLightAt(
               pos, this.item.getItem().getItem() instanceof BlockItem blockItem ? blockItem.getBlock().defaultBlockState().getLightEmission() : 0
            );
         }

         this.level.getLightEngine().checkBlock(pos);
      }

      if (this.level instanceof ServerLevel serverLevel) {
         PacketDistributor.sendToPlayersTrackingChunk(
            serverLevel,
            new ChunkPos(this.getBlockPos()),
            new SetMasonJarItemPacket(this.getBlockPos(), this.item.getItem(), this.itemRotation),
            new CustomPacketPayload[0]
         );
      }
   }

   public int getItemRotation() {
      return this.itemRotation;
   }

   public void setItemRotation(int itemRotation) {
      this.itemRotation = itemRotation;
   }

   public static class MasonJarItemStackHandler extends ItemStackHandler {
      protected final MasonJarBlockEntity jarEntity;

      public MasonJarItemStackHandler(MasonJarBlockEntity jarEntity) {
         super(1);
         this.jarEntity = jarEntity;
      }

      public ItemStack getItem() {
         return ((ItemStack)this.stacks.getFirst()).copy();
      }

      private ItemStack peekItem() {
         return (ItemStack)this.stacks.getFirst();
      }

      public void setItem(ItemStack itemStack) {
         this.stacks.set(0, itemStack);
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         return stack.canFitInsideContainerItems();
      }

      public ItemStack extractItem(int slot, int amount, boolean simulate) {
         if (simulate) {
            return super.extractItem(slot, amount, true);
         }

         ItemStack extractedStack = super.extractItem(slot, amount, false);
         if (!extractedStack.isEmpty()) {
            this.jarEntity.wobble(WobbleStyle.NEGATIVE);
            this.jarEntity.setChanged();
         }

         return extractedStack;
      }

      public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
         if (simulate) {
            return super.insertItem(slot, stack, true);
         }

         ItemStack inserted = stack.copy();
         ItemStack returned = super.insertItem(slot, stack, false);
         if (!ItemStack.isSameItemSameComponents(inserted, returned) || inserted.getCount() != returned.getCount()) {
            this.jarEntity.wobble(WobbleStyle.POSITIVE);
            this.jarEntity.setChanged();
         }

         return returned;
      }

      public boolean isEmpty() {
         return ((ItemStack)this.stacks.getFirst()).isEmpty();
      }
   }
}
