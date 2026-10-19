package twilightforest.block.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import twilightforest.block.CandelabraBlock;
import twilightforest.block.LightableBlock;
import twilightforest.components.item.CandelabraData;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFDataComponents;

public class CandelabraBlockEntity extends BlockEntity {
   private CandelabraData data = CandelabraData.EMPTY;

   public CandelabraBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)TFBlockEntities.CANDELABRA.get(), pos, state);
   }

   public CandelabraData getCandles() {
      return this.data;
   }

   public void setData(CandelabraData data) {
      this.data = data;
   }

   public Block removeCandle(int index) {
      Block block = CandelabraData.getItem(this.data.ordered(), index).orElse(Blocks.AIR);
      this.setCandle(index, Blocks.AIR);
      return block;
   }

   public void setCandle(int index, Block block) {
      List<Block> list = new ArrayList<>(this.data.ordered());
      list.set(index, block);
      this.data = new CandelabraData(list);
      this.updateState(index);
      this.setChanged();
      this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
   }

   public Block getCandle(int index) {
      return CandelabraData.getItem(this.data.ordered(), index).orElse(Blocks.AIR);
   }

   public void saveAdditional(CompoundTag tag, Provider provider) {
      super.saveAdditional(tag, provider);
      this.data.save(tag);
   }

   protected void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      this.data = CandelabraData.load(tag);
   }

   public void updateState(int index) {
      if (index >= 0 && index < 3) {
         BlockState blockstate = this.getBlockState();

         for (int i = 0; i < CandelabraBlock.CANDLES.size(); i++) {
            boolean flag = !this.getCandle(i).defaultBlockState().isAir();
            BooleanProperty booleanproperty = CandelabraBlock.CANDLES.get(i);
            blockstate = (BlockState)blockstate.setValue(booleanproperty, flag);
         }

         if (CandelabraBlock.getCandleCount(blockstate) == 0 && blockstate.getValue(CandelabraBlock.LIGHTING) != LightableBlock.Lighting.NONE) {
            blockstate = (BlockState)blockstate.setValue(CandelabraBlock.LIGHTING, LightableBlock.Lighting.NONE);
         }

         this.getLevel().setBlock(this.getBlockPos(), blockstate, 3);
         this.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), Context.of(blockstate));
      }
   }

   protected void collectImplicitComponents(Builder components) {
      super.collectImplicitComponents(components);
      components.set(TFDataComponents.CANDELABRA_DATA, this.data);
   }

   protected void applyImplicitComponents(DataComponentInput componentInput) {
      super.applyImplicitComponents(componentInput);
      this.data = (CandelabraData)componentInput.getOrDefault(TFDataComponents.CANDELABRA_DATA, CandelabraData.EMPTY);
   }

   public void removeComponentsFromTag(CompoundTag tag) {
      super.removeComponentsFromTag(tag);
      tag.remove("Candles");
   }

   public CompoundTag getUpdateTag(Provider provider) {
      return this.saveCustomOnly(provider);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }
}
