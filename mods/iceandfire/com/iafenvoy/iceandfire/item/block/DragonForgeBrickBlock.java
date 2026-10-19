package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.data.DragonType;
import com.iafenvoy.iceandfire.item.block.entity.DragonForgeBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.DragonForgeBrickBlockEntity;
import com.iafenvoy.iceandfire.item.block.util.DragonProof;
import com.iafenvoy.iceandfire.registry.IafBlockEntities;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.util.DragonTypeProvider;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.menu.MenuRegistry;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DragonForgeBrickBlock extends BaseEntityBlock implements DragonProof, DragonTypeProvider {
   private static final Map<DragonType, Block> TYPE_MAP = new HashMap<>();
   public static final BooleanProperty GRILL = BooleanProperty.create("grill");
   private final DragonType dragonType;

   public DragonForgeBrickBlock(DragonType dragonType) {
      super(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).dynamicShape().strength(40.0F, 500.0F).sound(SoundType.METAL));
      this.dragonType = dragonType;
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(GRILL, Boolean.FALSE));
      TYPE_MAP.put(dragonType, this);
   }

   public static String name(DragonType dragonType) {
      return String.format(Locale.ROOT, "dragonforge_%s_brick", dragonType.name());
   }

   public static Block getBlockByType(DragonType type) {
      return TYPE_MAP.getOrDefault(type, (Block)IafBlocks.DRAGONFORGE_FIRE_BRICK.get());
   }

   protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      DragonForgeBlockEntity forge = this.getConnectedBlockEntity(world, pos);
      if (forge != null && forge.getDragonType() == this.dragonType && player instanceof ServerPlayer serverPlayer) {
         MenuRegistry.openExtendedMenu(serverPlayer, forge);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   private DragonForgeBlockEntity getConnectedBlockEntity(Level worldIn, BlockPos pos) {
      for (Direction facing : Direction.values()) {
         if (worldIn.getBlockEntity(pos.relative(facing)) != null
            && worldIn.getBlockEntity(pos.relative(facing)) instanceof DragonForgeBlockEntity forge
            && forge.assembled()) {
            return forge;
         }
      }

      return null;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{GRILL});
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return MapCodec.unit(this);
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new DragonForgeBrickBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
      return world.isClientSide ? null : createTickerHelper(type, (BlockEntityType)IafBlockEntities.DRAGONFORGE_BRICK.get(), DragonForgeBrickBlockEntity::tick);
   }

   @Override
   public DragonType getDragonType() {
      return this.dragonType;
   }
}
