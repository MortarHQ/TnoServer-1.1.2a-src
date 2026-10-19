package twilightforest.block;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import twilightforest.advancements.KillBugTrigger;
import twilightforest.components.item.JarLid;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.init.TFAdvancements;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;

public abstract class CritterBlock extends BaseEntityBlock implements Equipable {
   public static final DirectionProperty FACING = DirectionalBlock.FACING;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private final VoxelShape DOWN_BB = Shapes.create(new AABB(0.2F, 0.85F, 0.2F, 0.8F, 1.0, 0.8F));
   private final VoxelShape UP_BB = Shapes.create(new AABB(0.2F, 0.0, 0.2F, 0.8F, 0.15F, 0.8F));
   private final VoxelShape NORTH_BB = Shapes.create(new AABB(0.2F, 0.2F, 0.85F, 0.8F, 0.8F, 1.0));
   private final VoxelShape SOUTH_BB = Shapes.create(new AABB(0.2F, 0.2F, 0.0, 0.8F, 0.8F, 0.15F));
   private final VoxelShape WEST_BB = Shapes.create(new AABB(0.85F, 0.2F, 0.2F, 1.0, 0.8F, 0.8F));
   private final VoxelShape EAST_BB = Shapes.create(new AABB(0.0, 0.2F, 0.2F, 0.15F, 0.8F, 0.8F));

   protected CritterBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.UP));
   }

   public static boolean canSurvive(LevelReader reader, BlockPos pos, Direction facing) {
      BlockPos restingPos = pos.relative(facing.getOpposite());
      return canSupportCenter(reader, restingPos, facing) || reader.getBlockState(restingPos).getBlock() instanceof LeavesBlock;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case DOWN -> this.DOWN_BB;
         case NORTH -> this.NORTH_BB;
         case SOUTH -> this.SOUTH_BB;
         case WEST -> this.WEST_BB;
         case EAST -> this.EAST_BB;
         default -> this.UP_BB;
      };
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction clicked = context.getClickedFace();
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      BlockState state = (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, clicked))
         .trySetValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
      if (this.canSurvive(state, context.getLevel(), context.getClickedPos())) {
         return state;
      }

      for (Direction dir : context.getNearestLookingDirections()) {
         state = (BlockState)this.defaultBlockState().setValue(FACING, dir.getOpposite());
         if (this.canSurvive(state, context.getLevel(), context.getClickedPos())) {
            return state;
         }
      }

      return null;
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor accessor, BlockPos pos, BlockPos neighborPos) {
      return ((Direction)state.getValue(FACING)).getOpposite() == direction && !state.canSurvive(accessor, pos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, direction, neighborState, accessor, pos, neighborPos);
   }

   public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
      Direction facing = (Direction)state.getValue(DirectionalBlock.FACING);
      return canSurvive(reader, pos, facing);
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result
   ) {
      if (stack.getItem() == TFItems.MASON_JAR.asItem()) {
         ItemContainerContents contents = (ItemContainerContents)stack.getComponents().get(DataComponents.CONTAINER);
         if (contents == null || contents.copyOne().isEmpty()) {
            if (this == TFBlocks.FIREFLY.get()) {
               ItemStack newStack = (ItemStack)Util.make(
                  new ItemStack((ItemLike)TFBlocks.FIREFLY_JAR.get()),
                  jar -> jar.set((DataComponentType)TFDataComponents.JAR_LID.get(), (JarLid)stack.get((DataComponentType)TFDataComponents.JAR_LID.get()))
               );
               stack.consume(1, player);
               player.getInventory().add(newStack);
               level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
               return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }

            if (this == TFBlocks.CICADA.get()) {
               ItemStack newStack = (ItemStack)Util.make(
                  new ItemStack((ItemLike)TFBlocks.CICADA_JAR.get()),
                  jar -> jar.set((DataComponentType)TFDataComponents.JAR_LID.get(), (JarLid)stack.get((DataComponentType)TFDataComponents.JAR_LID.get()))
               );
               stack.consume(1, player);
               player.getInventory().add(newStack);
               if (level.isClientSide()) {
                  Minecraft.getInstance().getSoundManager().stop(((SoundEvent)TFSounds.CICADA.get()).getLocation(), SoundSource.NEUTRAL);
               }

               level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
               return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }
         }
      }

      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (entity instanceof Projectile && !entity.getType().is(EntityTagGenerator.DONT_KILL_BUGS) || entity instanceof FallingBlockEntity) {
         level.setBlockAndUpdate(
            pos, state.hasProperty(WATERLOGGED) && state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState()
         );
         if (level.isClientSide()) {
            Minecraft.getInstance().getSoundManager().stop(((SoundEvent)TFSounds.CICADA.get()).getLocation(), SoundSource.NEUTRAL);
         }

         level.playSound(null, pos, (SoundEvent)TFSounds.BUG_SQUISH.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         if (level instanceof ServerLevel serverLevel && this.getSquishLootTable() != null) {
            LootParams ctx = new net.minecraft.world.level.storage.loot.LootParams.Builder(serverLevel)
               .withParameter(LootContextParams.BLOCK_STATE, state)
               .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
               .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
               .create(LootContextParamSets.BLOCK);
            serverLevel.getServer()
               .reloadableRegistries()
               .getLootTable(this.getSquishLootTable())
               .getRandomItems(ctx)
               .forEach(stack -> popResource(serverLevel, pos, stack));
         }

         for (int i = 0; i < 50; i++) {
            boolean wallBug = state.getValue(FACING) != Direction.UP;
            level.addParticle(
               new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SLIME_BLOCK.defaultBlockState()),
               true,
               pos.getX() + Mth.nextFloat(level.getRandom(), 0.25F, 0.75F),
               pos.getY() + (wallBug ? 0.5F : 0.0F),
               pos.getZ() + Mth.nextFloat(level.getRandom(), 0.25F, 0.75F),
               0.0,
               0.0,
               0.0
            );
         }

         if (entity instanceof Projectile projectile && projectile.getOwner() instanceof ServerPlayer player) {
            player.awardStat((ResourceLocation)TFStats.BUGS_SQUISHED.get());
            ((KillBugTrigger)TFAdvancements.KILL_BUG.get()).trigger(player, state);
         }
      }
   }

   @Nullable
   public abstract ResourceKey<LootTable> getSquishLootTable();

   public abstract BlockEntity newBlockEntity(BlockPos var1, BlockState var2);

   public EquipmentSlot getEquipmentSlot() {
      return EquipmentSlot.HEAD;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }
}
