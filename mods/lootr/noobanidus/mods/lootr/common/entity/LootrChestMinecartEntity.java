package noobanidus.mods.lootr.common.entity;

import com.google.auto.service.AutoService;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.entity.vehicle.AbstractMinecart.Type;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.ILootrInfo;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.entity.ILootrEntity;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LootrChestMinecartEntity extends AbstractMinecartContainer implements ILootrEntity {
   private static BlockState cartNormal = null;
   private final Set<UUID> clientOpeners = new ObjectLinkedOpenHashSet();
   private boolean hasBeenOpened = false;
   private boolean opened = false;
   private String cachedId;

   public LootrChestMinecartEntity(EntityType<LootrChestMinecartEntity> type, Level world) {
      super(type, world);
   }

   public LootrChestMinecartEntity(Level worldIn, double x, double y, double z) {
      super(LootrRegistry.getMinecart(), x, y, z, worldIn);
   }

   public ItemStack getPickResult() {
      return new ItemStack(Items.CHEST_MINECART);
   }

   public void unpackChestVehicleLootTable(@Nullable Player p_219950_) {
   }

   @Nullable
   @Override
   public Set<UUID> getClientOpeners() {
      return this.clientOpeners;
   }

   @Override
   public boolean isClientOpened() {
      return this.opened;
   }

   @Override
   public void setClientOpened(boolean opened) {
      this.opened = opened;
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
               if (!player.isShiftKeyDown()) {
                  player.displayClientMessage(Component.translatable("lootr.message.cannot_break_sneak").setStyle(LootrAPI.getChatStyle()), false);
                  return true;
               } else {
                  return false;
               }
            } else {
               player.displayClientMessage(Component.translatable("lootr.message.cannot_break").setStyle(LootrAPI.getChatStyle()), false);
               return true;
            }
         } else if (!source.getEntity().isShiftKeyDown()) {
            ((Player)source.getEntity())
               .displayClientMessage(Component.translatable("lootr.message.cart_should_sneak").setStyle(LootrAPI.getChatStyle()), false);
            ((Player)source.getEntity())
               .displayClientMessage(Component.translatable("lootr.message.cart_should_sneak2").setStyle(LootrAPI.getChatStyle()), false);
            return true;
         } else {
            return !source.getEntity().isShiftKeyDown();
         }
      } else {
         return true;
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

   public int getContainerSize() {
      return 27;
   }

   public Type getMinecartType() {
      return Type.CHEST;
   }

   public BlockState getDefaultDisplayBlockState() {
      if (cartNormal == null) {
         cartNormal = (BlockState)LootrRegistry.getChestBlock().defaultBlockState().setValue(ChestBlock.FACING, Direction.NORTH);
      }

      return cartNormal;
   }

   public int getDefaultDisplayOffset() {
      return 8;
   }

   public AbstractContainerMenu createMenu(int id, Inventory playerInventoryIn) {
      return ChestMenu.threeRows(id, playerInventoryIn, this);
   }

   public void remove(RemovalReason reason) {
      this.setRemoved(reason);
      if (reason == RemovalReason.KILLED) {
         this.gameEvent(GameEvent.ENTITY_DIE);
      }
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      if (!this.level().isClientSide() && !player.isSpectator() && player instanceof ServerPlayer serverPlayer) {
         if (player.isShiftKeyDown()) {
            LootrAPI.handleProviderSneak(this, serverPlayer);
         } else {
            LootrAPI.handleProviderOpen(this, serverPlayer);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.CONSUME;
      }
   }

   public void startOpen(Player player) {
      if (!player.isSpectator()) {
         if (!this.hasBeenOpened) {
            this.hasBeenOpened = true;
            this.markChanged();
         }

         this.performOpen((ServerPlayer)player);
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

   @NotNull
   @Override
   public BlockPos getInfoPos() {
      return this.blockPosition();
   }

   @Override
   public ResourceKey<LootTable> getInfoLootTable() {
      return this.getLootTable();
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
      return this.getContainerSize();
   }

   @Override
   public long getInfoLootSeed() {
      return this.getLootTableSeed();
   }

   @Nullable
   @Override
   public NonNullList<ItemStack> getInfoReferenceInventory() {
      return null;
   }

   @Override
   public boolean isInfoReferenceInventory() {
      return false;
   }

   @Override
   public Level getInfoLevel() {
      return this.level();
   }

   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return LootrBlockType.ENTITY;
   }

   @Override
   public ILootrType getInfoNewType() {
      return BuiltInLootrTypes.MINECART;
   }

   @NotNull
   @Override
   public Vec3 getInfoVec() {
      return this.position();
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.getUUID();
   }

   @Override
   public String getInfoKey() {
      if (this.cachedId == null) {
         this.cachedId = ILootrInfo.generateInfoKey(this.getInfoUUID());
      }

      return this.cachedId;
   }

   @Override
   public boolean hasBeenOpened() {
      return this.hasBeenOpened;
   }

   @Override
   public boolean isPhysicallyOpen() {
      return false;
   }

   @Override
   public void markChanged() {
      this.setChanged();
      this.markDataChanged();
   }

   @Override
   public double getParticleYOffset() {
      return 1.1;
   }

   @Override
   public double[] getParticleXBounds() {
      return new double[]{0.3, 0.7};
   }

   @Override
   public double[] getParticleZBounds() {
      return new double[]{0.3, 0.7};
   }

   @Nullable
   @Override
   public IContainerTrigger getTrigger() {
      return LootrRegistry.getCartTrigger();
   }

   protected void applyNaturalSlowdown() {
      float f = 0.98F;
      if (this.isInWater()) {
         f *= 0.95F;
      }

      this.setDeltaMovement(this.getDeltaMovement().multiply(f, 0.0, f));
   }

   protected Item getDropItem() {
      return Items.CHEST_MINECART;
   }

   @AutoService(ILootrEntityConverter.class)
   public static class DefaultConverter implements ILootrEntityConverter<LootrChestMinecartEntity> {
      public ILootrEntity apply(LootrChestMinecartEntity entity) {
         return entity;
      }

      @Override
      public EntityType<?> getEntityType() {
         return LootrRegistry.getMinecart();
      }
   }
}
