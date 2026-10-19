package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.item.block.entity.JarBlockEntity;
import com.iafenvoy.iceandfire.registry.IafBlockEntities;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.mojang.serialization.MapCodec;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class JarBlock extends BaseEntityBlock {
   private static final MapCodec<? extends BaseEntityBlock> CODEC = simpleCodec(s -> new JarBlock(-1));
   protected static final VoxelShape AABB = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   private final boolean empty;
   private final int pixieType;

   public JarBlock(int pixieType) {
      super(
         pixieType != -1
            ? Properties.of()
               .mapColor(MapColor.NONE)
               .instrument(NoteBlockInstrument.HAT)
               .noOcclusion()
               .dynamicShape()
               .strength(1.0F, 2.0F)
               .sound(SoundType.GLASS)
               .lightLevel(state -> 10)
               .dropsLike((Block)IafBlocks.JAR_EMPTY.get())
            : Properties.of()
               .mapColor(MapColor.NONE)
               .instrument(NoteBlockInstrument.HAT)
               .noOcclusion()
               .dynamicShape()
               .strength(1.0F, 2.0F)
               .sound(SoundType.GLASS)
      );
      this.empty = pixieType == -1;
      this.pixieType = pixieType;
   }

   public static String name(int pixieType) {
      return pixieType == -1 ? "pixie_jar_empty" : String.format(Locale.ROOT, "pixie_jar_%d", pixieType);
   }

   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return AABB;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return AABB;
   }

   public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      this.dropPixie(worldIn, pos);
      super.onRemove(state, worldIn, pos, newState, isMoving);
   }

   public void dropPixie(Level world, BlockPos pos) {
      if (world.getBlockEntity(pos) != null && world.getBlockEntity(pos) instanceof JarBlockEntity jar && jar.hasPixie) {
         jar.releasePixie();
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      if (!this.empty && world.getBlockEntity(pos) != null && world.getBlockEntity(pos) instanceof JarBlockEntity jar && jar.hasPixie && jar.hasProduced) {
         jar.hasProduced = false;
         ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack((ItemLike)IafItems.PIXIE_DUST.get()));
         if (!world.isClientSide) {
            world.addFreshEntity(item);
         }

         world.playLocalSound(
            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (SoundEvent)IafSounds.PIXIE_HURT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, false
         );
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      if (world.getBlockEntity(pos) instanceof JarBlockEntity jar) {
         if (!this.empty) {
            jar.hasPixie = true;
            jar.pixieType = this.pixieType;
         } else {
            jar.hasPixie = false;
         }

         jar.setChanged();
      }
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
      return createTickerHelper(entityType, (BlockEntityType)IafBlockEntities.PIXIE_JAR.get(), JarBlockEntity::tick);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new JarBlockEntity(pos, state, this.empty);
   }

   public int getPixieType() {
      return this.pixieType;
   }

   public boolean isEmpty() {
      return this.empty;
   }
}
