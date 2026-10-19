package dev.ftb.mods.ftbquests.block.neoforge;

import dev.ftb.mods.ftbquests.block.entity.QuestBarrierBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;

public class NeoForgeQuestBarrierBlockEntity extends QuestBarrierBlockEntity {
   public static final ModelProperty<BlockState> CAMOUFLAGE_STATE = new ModelProperty();

   public NeoForgeQuestBarrierBlockEntity(BlockPos blockPos, BlockState blockState) {
      super(blockPos, blockState);
   }

   @Override
   public void forceAppearanceUpdate() {
      super.forceAppearanceUpdate();
      this.requestModelDataUpdate();
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, Provider lookupProvider) {
      super.onDataPacket(net, pkt, lookupProvider);
      this.requestModelDataUpdate();
      this.level.setBlocksDirty(this.worldPosition, Blocks.AIR.defaultBlockState(), this.getBlockState());
   }

   public void handleUpdateTag(CompoundTag tag, Provider lookupProvider) {
      super.handleUpdateTag(tag, lookupProvider);
      this.requestModelDataUpdate();
      this.level.setBlocksDirty(this.worldPosition, Blocks.AIR.defaultBlockState(), this.getBlockState());
   }

   public ModelData getModelData() {
      return ModelData.builder().with(CAMOUFLAGE_STATE, this.getClientAppearance()).build();
   }
}
