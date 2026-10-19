package twilightforest.block.entity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFSounds;

public class KeepsakeCasketBlockEntity extends SkullChestBlockEntity {
   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
      protected void onOpen(Level level, BlockPos pos, BlockState state) {
         level.playSound(null, pos, (SoundEvent)TFSounds.CASKET_OPEN.get(), SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
      }

      protected void onClose(Level level, BlockPos pos, BlockState state) {
         level.playSound(null, pos, (SoundEvent)TFSounds.CASKET_CLOSE.get(), SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
      }

      protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int id, int param) {
         Block block = state.getBlock();
         level.blockEvent(pos, block, 1, param);
      }

      protected boolean isOwnContainer(Player player) {
         if (player.containerMenu instanceof ChestMenu) {
            Container container = ((ChestMenu)player.containerMenu).getContainer();
            return container == KeepsakeCasketBlockEntity.this;
         } else {
            return false;
         }
      }
   };

   public KeepsakeCasketBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)TFBlockEntities.KEEPSAKE_CASKET.get(), pos, state);
   }

   @Override
   public ContainerOpenersCounter getOpenersCounter() {
      return this.openersCounter;
   }

   @Override
   public void displayLockedInfo(Player player) {
      player.playNotifySound((SoundEvent)TFSounds.CASKET_LOCKED.get(), SoundSource.BLOCKS, 0.5F, 0.5F);
      player.displayClientMessage(
         Component.translatable("block.twilightforest.casket.locked", new Object[]{this.owner.gameProfile().getName()}).withStyle(ChatFormatting.RED), true
      );
   }
}
