package dev.ftb.mods.ftbquests.block;

import com.mojang.serialization.MapCodec;
import dev.architectury.hooks.level.entity.EntityHooks;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbquests.block.entity.BaseBarrierBlockEntity;
import dev.ftb.mods.ftbquests.block.entity.QuestBarrierBlockEntity;
import dev.ftb.mods.ftbquests.block.neoforge.QuestBarrierBlockImpl;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.net.BlockConfigRequestMessage;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import dev.ftb.mods.ftbquests.util.NetUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class QuestBarrierBlock extends BaseEntityBlock {
   private static final MapCodec<QuestBarrierBlock> CODEC = simpleCodec(QuestBarrierBlock::new);
   public static final BooleanProperty OPEN = BooleanProperty.create("open");
   public static final Properties PROPS = Properties.of()
      .mapColor(MapColor.COLOR_LIGHT_BLUE)
      .pushReaction(PushReaction.BLOCK)
      .noOcclusion()
      .isViewBlocking((blockState, blockGetter, blockPos) -> false)
      .isSuffocating((blockState, blockGetter, blockPos) -> false)
      .strength(-1.0F, 6000000.0F)
      .lightLevel(blockState -> 3)
      .emissiveRendering((blockState, blockGetter, blockPos) -> true);

   public QuestBarrierBlock(Properties props) {
      super(props);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(OPEN, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{OPEN});
   }

   protected MapCodec<? extends QuestBarrierBlock> codec() {
      return CODEC;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter bg, BlockPos pos, CollisionContext ctx) {
      return EntityHooks.fromCollision(ctx) instanceof Player player && this.isBarrierOpen(player, state, bg, pos)
         ? Shapes.empty()
         : super.getCollisionShape(state, bg, pos, ctx);
   }

   protected VoxelShape getShape(BlockState blockState, BlockGetter bg, BlockPos pos, CollisionContext ctx) {
      return EntityHooks.fromCollision(ctx) instanceof Player player && this.isBarrierOpen(player, blockState, bg, pos) && !NetUtils.canEdit(player)
         ? Shapes.empty()
         : super.getShape(blockState, bg, pos, ctx);
   }

   private boolean isBarrierOpen(Player player, BlockState state, BlockGetter bg, BlockPos pos) {
      return player.level().isClientSide() ? (Boolean)state.getValue(OPEN) : bg.getBlockEntity(pos) instanceof BaseBarrierBlockEntity be && be.isOpen(player);
   }

   public VoxelShape getVisualShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext ctx) {
      return Shapes.empty();
   }

   @OnlyIn(Dist.CLIENT)
   public float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
      return 1.0F;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter bg, BlockPos pos) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean skipRendering(BlockState state, BlockState state2, Direction dir) {
      return state2.is(this) || super.skipRendering(state, state2, dir);
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
      super.setPlacedBy(level, pos, state, entity, stack);
      if (!level.isClientSide()
         && stack.has(DataComponents.CUSTOM_NAME)
         && !stack.has((DataComponentType)ModDataComponents.BARRIER_SAVED.get())
         && level.getBlockEntity(pos) instanceof BaseBarrierBlockEntity barrier) {
         barrier.updateFromString(stack.getHoverName().getString());
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult
   ) {
      if (player instanceof ServerPlayer sp) {
         if (level.getBlockEntity(blockPos) instanceof BaseBarrierBlockEntity barrier && barrier.hasPermissionToEdit(sp)) {
            NetworkManager.sendToPlayer(sp, new BlockConfigRequestMessage(blockPos, BlockConfigRequestMessage.BlockType.BARRIER));
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
         } else {
            return ItemInteractionResult.FAIL;
         }
      } else {
         return ItemInteractionResult.SUCCESS;
      }
   }

   public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      ItemStack stack = super.getCloneItemStack(levelReader, blockPos, blockState);
      if (levelReader.getBlockEntity(blockPos) instanceof BaseBarrierBlockEntity barrier) {
         stack.set((DataComponentType)ModDataComponents.BARRIER_SAVED.get(), BaseBarrierBlockEntity.BarrierSavedData.fromBlockEntity(barrier));
         if (!barrier.getSkin().isEmpty() && !ClientQuestFile.canClientPlayerEdit()) {
            stack.set(DataComponents.ITEM_NAME, barrier.getSkin().getHoverName());
         }
      }

      return stack;
   }

   protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
      if (entity instanceof ServerPlayer player && player.getServer() != null && level.getBlockEntity(blockPos) instanceof BaseBarrierBlockEntity b) {
         b.optionalTeleportData().ifPresent(teleportData -> {
            if (teleportData.enabled() && b.isOpen(player)) {
               QuestBarrierBlock.TeleportTicker.addPending(player, teleportData.effectiveDest(b.getBlockPos()));
            }
         });
      }
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
      return level != null && level.isClientSide ? BaseBarrierBlockEntity::tick : null;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return questBlockEntityProvider().create(blockPos, blockState);
   }

   @ExpectPlatform
   @Transformed
   public static BlockEntitySupplier<QuestBarrierBlockEntity> questBlockEntityProvider() {
      return QuestBarrierBlockImpl.questBlockEntityProvider();
   }

   public static class TeleportTicker {
      private static final Map<UUID, BaseBarrierBlockEntity.TeleportData> pendingTeleports = new HashMap<>();

      public static void tick(MinecraftServer server) {
         if (!pendingTeleports.isEmpty()) {
            pendingTeleports.forEach((id, teleportData) -> teleportData.teleportPlayer(server.getPlayerList().getPlayer(id)));
            pendingTeleports.clear();
         }
      }

      public static void addPending(ServerPlayer player, BaseBarrierBlockEntity.TeleportData teleportData) {
         pendingTeleports.put(player.getUUID(), teleportData);
      }
   }
}
