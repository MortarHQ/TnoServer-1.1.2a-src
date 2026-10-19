package com.benji.netherman.common.block.entity;

import com.benji.netherman.common.block.TotemusBlock;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TotemusBlockEntity extends BlockEntity {
   private int totemType = 0;
   private int scanTimer = 0;

   public TotemusBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.TOTEMUS.get(), pos, state);
   }

   public int getTotemType() {
      return this.totemType;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, TotemusBlockEntity entity) {
      if (!level.isClientSide()) {
         entity.scanTimer--;
         if (entity.scanTimer <= 0) {
            entity.scanTimer = 20;
            int newType = 0;

            for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
               BlockState neighbor = level.getBlockState(checkPos);
               if (neighbor.is((Block)ModBlocks.CHISELED_SAMSONIT.get())) {
                  newType = 4;
                  break;
               }

               if (neighbor.is((Block)ModBlocks.BLACKSTONE_COLUMN.get()) && newType < 3) {
                  newType = 3;
               } else if (neighbor.is(Blocks.ANCIENT_DEBRIS) && newType < 2) {
                  newType = 2;
               } else if (neighbor.is(Blocks.GOLD_BLOCK) && newType < 1) {
                  newType = 1;
               }
            }

            if (entity.totemType != newType) {
               entity.totemType = newType;
               entity.setChanged();
            }

            BlockState currentState = level.getBlockState(pos);
            if (currentState.hasProperty(TotemusBlock.TYPE) && (Integer)currentState.getValue(TotemusBlock.TYPE) != entity.totemType) {
               level.setBlock(pos, (BlockState)currentState.setValue(TotemusBlock.TYPE, entity.totemType), 3);
            }

            AABB box = new AABB(pos).inflate(10.0);

            for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, box)) {
               if (entity.totemType == 3) {
                  if (player.hasEffect(ModEffects.FEAR)
                     || player.hasEffect(ModEffects.EXCITEMENT)
                     || player.hasEffect(ModEffects.FAITH)
                     || player.hasEffect(ModEffects.ALERTNESS)) {
                     player.removeEffect(ModEffects.FEAR);
                     player.removeEffect(ModEffects.EXCITEMENT);
                     player.removeEffect(ModEffects.FAITH);
                     player.removeEffect(ModEffects.ALERTNESS);
                     level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.BIG_TEXT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                     Component title = Component.translatable("block.netherman.totemus.arrived")
                        .withStyle(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD});
                     Component subtitle = Component.empty();
                     player.connection.send(new ClientboundSetTitlesAnimationPacket(10, 60, 20));
                     player.connection.send(new ClientboundSetSubtitleTextPacket(subtitle));
                     player.connection.send(new ClientboundSetTitleTextPacket(title));
                  }
               } else {
                  Holder<MobEffect> targetEffect = switch (entity.totemType) {
                     case 1 -> ModEffects.EXCITEMENT;
                     case 2 -> ModEffects.FAITH;
                     default -> ModEffects.FEAR;
                     case 4 -> ModEffects.ALERTNESS;
                  };
                  if (!player.hasEffect(targetEffect)) {
                     player.removeEffect(ModEffects.FEAR);
                     player.removeEffect(ModEffects.EXCITEMENT);
                     player.removeEffect(ModEffects.FAITH);
                     player.removeEffect(ModEffects.ALERTNESS);
                     player.addEffect(new MobEffectInstance(targetEffect, Integer.MAX_VALUE, 0, false, false, true));
                     level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.BIG_TEXT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                     Component title = Component.translatable("block.netherman.totemus.zone").withStyle(ChatFormatting.YELLOW);

                     Component subtitle = switch (entity.totemType) {
                        case 1 -> Component.translatable("block.netherman.totemus.zone.city")
                           .withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD});
                        case 2 -> Component.translatable("block.netherman.totemus.zone.azazel")
                           .withStyle(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD});
                        default -> Component.translatable("block.netherman.totemus.zone.quarries")
                           .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD});
                        case 4 -> Component.translatable("block.netherman.totemus.zone.maze")
                           .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD});
                     };
                     player.connection.send(new ClientboundSetTitlesAnimationPacket(10, 60, 20));
                     player.connection.send(new ClientboundSetSubtitleTextPacket(subtitle));
                     player.connection.send(new ClientboundSetTitleTextPacket(title));
                  }
               }
            }
         }
      }
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.putInt("TotemType", this.totemType);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.totemType = tag.getInt("TotemType");
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag tag = new CompoundTag();
      this.saveAdditional(tag, registries);
      return tag;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }
}
