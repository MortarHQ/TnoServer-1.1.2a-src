package team.lodestar.lodestone.systems.blockentity;

import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

public class LodestoneBlockEntity extends BlockEntity {
   public LodestoneBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public CompoundTag getUpdateTag(Provider pRegistries) {
      return this.saveWithoutMetadata(pRegistries);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public void onBreak(@Nullable Player player) {
   }

   public void onPlace(LivingEntity placer, ItemStack stack) {
   }

   public void onNeighborUpdate(BlockState state, BlockPos pos, BlockPos neighbor) {
   }

   public ItemStack onClone(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
      return ItemStack.EMPTY;
   }

   public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   public InteractionResult onUseWithoutItem(Player pPlayer) {
      return InteractionResult.PASS;
   }

   public ItemInteractionResult onUseWithItem(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   public void onEntityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
   }

   public void update(@Nonnull Level level) {
   }

   public void serverTick(ServerLevel level) {
   }

   public void clientTick(Level level) {
   }

   @Deprecated
   public void tick() {
   }

   public void commonTick(Level level) {
   }

   public void playSound(SoundEvent soundEvent) {
      this.playSound(soundEvent, 1.0F);
   }

   public void playSound(SoundEvent soundEvent, float volume) {
      this.playSound(soundEvent, volume, 1.0F);
   }

   public void playSound(SoundEvent soundEvent, float volume, float pitch) {
      this.level.playSound(null, this.worldPosition, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
   }

   public void setDirty() {
      BlockStateHelper.updateState(this.level, this.worldPosition);
   }

   public void notifyObservers() {
      this.getBlockState().updateNeighbourShapes(this.level, this.worldPosition, 2);
   }
}
