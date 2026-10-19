package noobanidus.mods.lootr.common.entity;

import com.google.auto.service.AutoService;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.SimpleLootrEntityInstance;
import noobanidus.mods.lootr.common.api.data.entity.ILootrEntity;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.common.mixin.accessor.AccessorMixinItemFrame;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LootrItemFrame extends ItemFrame implements ILootrEntity {
   private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
   private final SimpleLootrEntityInstance instance = new SimpleLootrEntityInstance(this, this::getVisualOpeners, 1);

   public LootrItemFrame(EntityType<? extends ItemFrame> entityType, Level level) {
      super(entityType, level);
   }

   public LootrItemFrame(Level level, BlockPos pos, Direction facingDirection) {
      super(LootrRegistry.getItemFrame(), level, pos, facingDirection);
   }

   public void lootrSetItem(ItemStack stack) {
      stack = stack.copyWithCount(1);
      this.inventory.set(0, stack);
      if (!this.level().isClientSide()) {
         this.setItemInternal(stack);
      }
   }

   public void startSeenByPlayer(ServerPlayer pPlayer) {
      super.startSeenByPlayer(pPlayer);
      if (this.hasVisualOpened(pPlayer)) {
         this.performOpen(pPlayer);
      } else {
         this.performClose(pPlayer);
      }
   }

   public boolean survives() {
      if (LootrAPI.canItemFramesSelfSupport()) {
         return true;
      }

      if (!this.level().noCollision(this)) {
         return false;
      }

      BlockState blockState = this.level().getBlockState(this.pos.relative(this.direction.getOpposite()));
      return (blockState.isSolid() || this.direction.getAxis().isHorizontal() && DiodeBlock.isDiode(blockState))
         && this.level().getEntities(this, this.getBoundingBox(), HANGING_ENTITY).isEmpty();
   }

   public boolean isInvulnerableTo(DamageSource source) {
      if (this.isInvulnerable() && source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return true;
      }

      if (source.getEntity() instanceof Player player) {
         if (LootrAPI.canDestroyOrBreak(player)) {
            return false;
         }

         if (LootrAPI.isBreakDisabled()) {
            if (player.getAbilities().instabuild) {
               return !player.isShiftKeyDown();
            }

            return true;
         }

         if (!source.getEntity().isShiftKeyDown()) {
            return true;
         }

         if (source.getEntity().isShiftKeyDown()) {
            return false;
         }
      }

      return true;
   }

   private void maybeMessagePlayer(DamageSource source) {
      if (source.getEntity() instanceof Player player && !this.level().isClientSide()) {
         if (LootrAPI.canDestroyOrBreak(player)) {
            return;
         }

         if (LootrAPI.isBreakDisabled()) {
            if (player.getAbilities().instabuild) {
               if (!player.isShiftKeyDown()) {
                  player.displayClientMessage(Component.translatable("lootr.message.cannot_break_sneak").setStyle(LootrAPI.getChatStyle()), false);
               }
            } else {
               player.displayClientMessage(Component.translatable("lootr.message.cannot_break").setStyle(LootrAPI.getChatStyle()), false);
            }
         } else if (!source.getEntity().isShiftKeyDown()) {
            ((Player)source.getEntity())
               .displayClientMessage(Component.translatable("lootr.message.cart_should_sneak").setStyle(LootrAPI.getChatStyle()), false);
            ((Player)source.getEntity())
               .displayClientMessage(Component.translatable("lootr.message.cart_should_sneak2").setStyle(LootrAPI.getChatStyle()), false);
         }
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      boolean skipMessage = false;
      ServerPlayer player = null;
      if (source.getEntity() instanceof ServerPlayer player1) {
         player = player1;
      } else if (source.getDirectEntity() instanceof ServerPlayer player2) {
         player = player2;
      }

      if (player != null && this.actuallyDropItem(player)) {
         skipMessage = true;
      }

      if (!skipMessage) {
         this.maybeMessagePlayer(source);
      }

      if (this.isInvulnerableTo(source)) {
         return false;
      }

      if (!this.isRemoved() && !this.level().isClientSide) {
         this.kill();
         this.dropItem(source.getEntity());
         this.markHurt();
      }

      return true;
   }

   public void dropItem(@Nullable Entity entity) {
      if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
         this.spawnAtLocation(this.getFrameItemStack());
      }
   }

   public void setItem(ItemStack stack, boolean updateNeighbours) {
   }

   private boolean actuallyDropItem(ServerPlayer player) {
      if (this.level().isClientSide()) {
         return false;
      }

      ILootrInventory inventory = LootrAPI.getInventory(this, player);
      if (inventory == null) {
         return false;
      }

      if (inventory.getItem(0).isEmpty()) {
         return false;
      }

      if (!this.hasServerOpened(player)) {
         player.awardStat(LootrRegistry.getLootedStat());
         LootrRegistry.getStatTrigger().trigger(player);
      }

      this.playSound(this.getRemoveItemSound(), 1.0F, 1.0F);
      inventory.setItem(0, ItemStack.EMPTY);
      inventory.setChanged();
      ItemStack item = this.getItem().copy();
      this.spawnAtLocation(item);
      this.performTrigger(player);
      if (this.addOpener(player)) {
         this.performOpen(player);
      }

      this.performUpdate(player);
      return true;
   }

   public ItemStack getItem() {
      return (ItemStack)this.getEntityData().get(AccessorMixinItemFrame.lootr$getDataItem());
   }

   public boolean hasFramedMap() {
      return false;
   }

   public SlotAccess getSlot(int slot) {
      return SlotAccess.NULL;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.put("customInventory", ContainerHelper.saveAllItems(new CompoundTag(), this.inventory, this.level().registryAccess()));
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      ContainerHelper.loadAllItems(compound.getCompound("customInventory"), this.inventory, this.level().registryAccess());
      this.setItemInternal((ItemStack)this.inventory.getFirst());
   }

   private void setItemInternal(ItemStack stack) {
      if (stack.is(Items.FILLED_MAP)) {
         LootrAPI.LOG.error("ItemFrames with maps are not supported by Lootr Item Frames due to technical limitations.");
      } else {
         if (!stack.isEmpty()) {
            stack = stack.copyWithCount(1);
         }

         ((AccessorMixinItemFrame)this).lootr$onItemChanged(stack);
         this.getEntityData().set(AccessorMixinItemFrame.lootr$getDataItem(), stack);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         LootrAPI.handleProviderTick(this);
      } else {
         LootrAPI.handleProviderClientTick(this);
      }
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      if (!this.level().isClientSide) {
         if (!player.getItemInHand(hand).isEmpty()) {
            player.displayClientMessage(Component.translatable("lootr.message.cannot_insert"), true);
         } else {
            this.playSound(this.getRotateItemSound(), 1.0F, 1.0F);
            this.setRotation(this.getRotation() + 1);
            this.gameEvent(GameEvent.BLOCK_CHANGE, player);
         }

         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public ItemStack getPickResult() {
      return new ItemStack(Items.ITEM_FRAME);
   }

   protected ItemStack getFrameItemStack() {
      return new ItemStack(Items.ITEM_FRAME);
   }

   @Nullable
   @Override
   public Set<UUID> getClientOpeners() {
      return this.instance.getClientOpeners();
   }

   @Override
   public boolean isClientOpened() {
      return this.instance.isClientOpened();
   }

   @Override
   public void setClientOpened(boolean opened) {
      this.instance.setClientOpened(opened);
   }

   @Override
   public void markChanged() {
      this.markDataChanged();
   }

   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return null;
   }

   @Override
   public ILootrType getInfoNewType() {
      return BuiltInLootrTypes.ITEM_FRAME;
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.getUUID();
   }

   @Override
   public String getInfoKey() {
      return this.instance.getInfoKey();
   }

   @Override
   public boolean hasBeenOpened() {
      return this.instance.hasBeenOpened();
   }

   @Override
   public boolean isPhysicallyOpen() {
      return false;
   }

   @NotNull
   @Override
   public BlockPos getInfoPos() {
      return BlockPos.containing(this.position());
   }

   @Nullable
   @Override
   public Component getInfoDisplayName() {
      return this.getDisplayName();
   }

   @NotNull
   @Override
   public ResourceKey<Level> getInfoDimension() {
      return this.level().dimension();
   }

   @Override
   public int getInfoContainerSize() {
      return 1;
   }

   @Nullable
   @Override
   public NonNullList<ItemStack> getInfoReferenceInventory() {
      return this.inventory;
   }

   @Override
   public boolean isInfoReferenceInventory() {
      return true;
   }

   @Nullable
   @Override
   public ResourceKey<LootTable> getInfoLootTable() {
      return LootrAPI.ITEM_FRAME_EMPTY;
   }

   @Override
   public long getInfoLootSeed() {
      return 0L;
   }

   @Override
   public Level getInfoLevel() {
      return this.level();
   }

   @Override
   public Vec3 getParticleCenter() {
      return this.position();
   }

   @Override
   public double getParticleYOffset() {
      return 0.4F;
   }

   @Override
   public double[] getParticleXBounds() {
      if (this.direction.getAxis() == Axis.Z) {
         return new double[]{-0.35, 0.35};
      } else {
         return this.direction != Direction.SOUTH && this.direction != Direction.EAST ? new double[]{-0.1, -0.05} : new double[]{0.05, 0.1};
      }
   }

   @Override
   public double[] getParticleZBounds() {
      if (this.direction.getAxis() == Axis.X) {
         return new double[]{-0.35, 0.35};
      } else {
         return this.direction != Direction.SOUTH && this.direction != Direction.EAST ? new double[]{-0.1, -0.05} : new double[]{0.05, 0.1};
      }
   }

   @Nullable
   @Override
   public IContainerTrigger getTrigger() {
      return LootrRegistry.getItemFrameTrigger();
   }

   @AutoService(ILootrEntityConverter.class)
   public static class DefaultConverter implements ILootrEntityConverter<LootrItemFrame> {
      public ILootrEntity apply(LootrItemFrame entity) {
         return entity;
      }

      @Override
      public EntityType<?> getEntityType() {
         return LootrRegistry.getItemFrame();
      }
   }
}
