package com.benji.netherman.common.block.entity;

import com.benji.netherman.common.entity.TotemusPuzzleEntity;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TotemusHoleBlockEntity extends BlockEntity {
   public static final int STATE_INACTIVE = 0;
   public static final int STATE_ACTIVE = 1;
   public static final int STATE_SOLVED = 2;
   private int puzzleState = 0;
   private int myColor = -1;
   private boolean isMaster = false;
   private BlockPos masterPos = null;
   private List<BlockPos> linkedHoles = new ArrayList<>();
   private int expectedColor = 0;
   private int cooldown = 0;
   private TotemusPuzzleEntity myEntity = null;

   public TotemusHoleBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.TOTEMUS_HOLE.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, TotemusHoleBlockEntity entity) {
      if (!level.isClientSide() && entity.puzzleState != 2) {
         if (entity.cooldown > 0) {
            entity.cooldown--;
         } else {
            if (entity.puzzleState == 0) {
               AABB detectionBox = new AABB(pos).inflate(10.0);
               if (!level.getEntitiesOfClass(Player.class, detectionBox).isEmpty()) {
                  entity.initiatePuzzleNetwork();
               }
            }
         }
      }
   }

   private void initiatePuzzleNetwork() {
      List<BlockPos> foundHoles = new ArrayList<>();
      int searchRadius = 15;

      for (BlockPos checkPos : BlockPos.betweenClosed(
         this.worldPosition.offset(-searchRadius, -searchRadius, -searchRadius), this.worldPosition.offset(searchRadius, searchRadius, searchRadius)
      )) {
         if (this.level.getBlockEntity(checkPos) instanceof TotemusHoleBlockEntity hole && hole.puzzleState == 0) {
            foundHoles.add(checkPos.immutable());
         }
      }

      if (foundHoles.size() >= 6) {
         this.isMaster = true;
         this.linkedHoles = foundHoles.subList(0, 6);
         this.expectedColor = 0;
         List<Integer> colors = Arrays.asList(0, 1, 2, 3, 4, 5);
         Collections.shuffle(colors);

         for (int i = 0; i < 6; i++) {
            BlockPos targetPos = this.linkedHoles.get(i);
            if (this.level.getBlockEntity(targetPos) instanceof TotemusHoleBlockEntity hole) {
               hole.activate(this.worldPosition, colors.get(i));
            }
         }
      }
   }

   public void activate(BlockPos master, int color) {
      this.masterPos = master;
      this.myColor = color;
      this.puzzleState = 1;
      TotemusPuzzleEntity entity = (TotemusPuzzleEntity)((EntityType)ModEntities.TOTEMUS_PUZZLE.get()).create(this.level);
      if (entity != null) {
         entity.setPos(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.0, this.worldPosition.getZ() + 0.5);
         entity.setColor(color);
         this.level.addFreshEntity(entity);
         this.myEntity = entity;
         this.level.playSound(null, this.worldPosition, SoundEvents.EVOKER_CAST_SPELL, SoundSource.BLOCKS, 1.0F, 0.5F);
      }
   }

   public void reportDeath(int colorKilled, Player player) {
      this.myEntity = null;
      if (this.isMaster) {
         this.handleKillSequence(colorKilled, player);
      } else if (this.masterPos != null && this.level.getBlockEntity(this.masterPos) instanceof TotemusHoleBlockEntity master) {
         master.handleKillSequence(colorKilled, player);
      }
   }

   private void handleKillSequence(int colorKilled, Player player) {
      if (colorKilled == this.expectedColor) {
         this.expectedColor++;
         if (this.expectedColor >= 6) {
            this.solvePuzzle(player);
         }
      } else {
         this.failPuzzle();
      }
   }

   private void failPuzzle() {
      this.level.playSound(null, this.worldPosition, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 0.5F);

      for (BlockPos pos : this.linkedHoles) {
         if (this.level.getBlockEntity(pos) instanceof TotemusHoleBlockEntity hole) {
            if (hole.myEntity != null && hole.myEntity.isAlive()) {
               hole.myEntity.discard();
               hole.myEntity = null;
            }

            hole.puzzleState = 0;
            hole.cooldown = 60;
            hole.isMaster = false;
         }
      }
   }

   private void solvePuzzle(Player player) {
      this.level.playSound(null, this.worldPosition, (SoundEvent)ModSounds.GIANT_BELL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
      if (this.level instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(
            ParticleTypes.TOTEM_OF_UNDYING,
            this.worldPosition.getX() + 0.5,
            this.worldPosition.getY() + 1.5,
            this.worldPosition.getZ() + 0.5,
            100,
            0.5,
            0.5,
            0.5,
            0.2
         );
      }

      ItemEntity key = new ItemEntity(
         this.level,
         this.worldPosition.getX() + 0.5,
         this.worldPosition.getY() + 1.5,
         this.worldPosition.getZ() + 0.5,
         new ItemStack((ItemLike)ModItems.MAZE_KEY.get())
      );
      this.level.addFreshEntity(key);

      for (BlockPos pos : this.linkedHoles) {
         if (this.level.getBlockEntity(pos) instanceof TotemusHoleBlockEntity hole) {
            hole.puzzleState = 2;
         }
      }
   }

   public void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.putInt("PuzzleState", this.puzzleState);
   }

   public void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.puzzleState = tag.getInt("PuzzleState");
   }
}
