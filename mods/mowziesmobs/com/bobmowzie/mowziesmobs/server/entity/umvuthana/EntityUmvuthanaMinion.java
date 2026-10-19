package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.ai.EntityAIUmvuthanaTrade;
import com.bobmowzie.mowziesmobs.server.ai.EntityAIUmvuthanaTradeLook;
import com.bobmowzie.mowziesmobs.server.ai.UmvuthanaHurtByTargetAI;
import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.TradeStore;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerUmvuthanaTrade;
import com.bobmowzie.mowziesmobs.server.item.UmvuthanaMask;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class EntityUmvuthanaMinion extends EntityUmvuthana implements LeaderSunstrikeImmune, Enemy {
   private static final TradeStore DEFAULT = new TradeStore.Builder()
      .addTrade(Items.GOLD_NUGGET, 4, ((Block)BlockHandler.CLAWED_LOG.get()).asItem(), 1, 9)
      .addTrade(Items.GOLD_NUGGET, 7, ((Block)BlockHandler.CLAWED_LOG.get()).asItem(), 2, 9)
      .addTrade(Items.GOLD_NUGGET, 5, Items.COOKED_CHICKEN, 2, 2)
      .addTrade(Items.GOLD_NUGGET, 4, Items.COOKED_CHICKEN, 1, 2)
      .addTrade(Items.GOLD_NUGGET, 7, Items.COOKED_PORKCHOP, 2, 2)
      .addTrade(Items.GOLD_NUGGET, 4, Items.COOKED_PORKCHOP, 1, 2)
      .addTrade(Items.GOLD_NUGGET, 1, Items.FEATHER, 4, 2)
      .addTrade(Items.GOLD_NUGGET, 1, Items.STICK, 12, 2)
      .addTrade(Items.GOLD_NUGGET, 3, Items.CAMPFIRE, 1, 2)
      .addTrade(Items.MELON_SLICE, 3, Items.GOLD_NUGGET, 5, 2)
      .addTrade(Items.CHICKEN, 1, Items.GOLD_NUGGET, 3, 2)
      .addTrade(Items.CHICKEN, 1, Items.GOLD_NUGGET, 4, 2)
      .addTrade(Items.PORKCHOP, 1, Items.GOLD_NUGGET, 6, 2)
      .addTrade(Items.BEETROOT, 3, Items.GOLD_NUGGET, 6, 2)
      .addTrade(Items.SALMON, 2, Items.GOLD_NUGGET, 8, 1)
      .addTrade(Items.COD, 2, Items.GOLD_NUGGET, 7, 1)
      .addTrade(Items.FLINT, 2, Items.FEATHER, 5, 2)
      .addTrade(Items.FEATHER, 5, Items.FLINT, 2, 2)
      .addTrade(Items.ACACIA_SAPLING, 2, Items.GOLD_NUGGET, 4, 2)
      .addTrade(Items.BONE, 3, Items.GOLD_NUGGET, 4, 1)
      .addTrade(Items.BONE, 2, Items.GOLD_NUGGET, 2, 1)
      .build();
   private static final EntityDataAccessor<Optional<Trade>> TRADE = SynchedEntityData.defineId(
      EntityUmvuthanaMinion.class, (EntityDataSerializer)EntityHandler.OPTIONAL_TRADE.value()
   );
   private static final EntityDataAccessor<Optional<UUID>> MISBEHAVED_PLAYER = SynchedEntityData.defineId(
      EntityUmvuthanaMinion.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private static final EntityDataAccessor<Boolean> IS_TRADING = SynchedEntityData.defineId(EntityUmvuthanaMinion.class, EntityDataSerializers.BOOLEAN);
   private static final int MIN_OFFER_TIME = 6000;
   private static final int MAX_OFFER_TIME = 24000;
   private TradeStore tradeStore = TradeStore.EMPTY;
   private int timeOffering;
   private Player customer;

   public EntityUmvuthanaMinion(EntityType<? extends EntityUmvuthanaMinion> type, Level world) {
      super(type, world);
      this.setWeapon(0);
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new EntityAIUmvuthanaTrade(this));
      this.goalSelector.addGoal(1, new EntityAIUmvuthanaTradeLook(this));
      this.goalSelector.addGoal(7, new MoveTowardsRestrictionGoal(this, 0.4));
   }

   @Override
   protected void registerTargetGoals() {
      this.targetSelector.addGoal(3, new UmvuthanaHurtByTargetAI(this, true));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Player>(this, Player.class, 0, true, true, target -> {
         if (target instanceof Player) {
            if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
               return false;
            }

            ItemStack headArmorStack = (ItemStack)((Player)target).getInventory().armor.get(3);
            return !(headArmorStack.getItem() instanceof UmvuthanaMask) || target == this.getMisbehavedPlayer();
         } else {
            return true;
         }
      }) {
         public void stop() {
            super.stop();
            EntityUmvuthanaMinion.this.setMisbehavedPlayerId(null);
         }
      });
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Zombie.class, 0, true, true, null));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, AbstractSkeleton.class, 0, true, false, null));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, EntityElokosa.class, 0, true, false, null));
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(TRADE, Optional.empty());
      builder.define(MISBEHAVED_PLAYER, Optional.empty());
      builder.define(IS_TRADING, false);
   }

   public void setOfferingTrade(Trade trade) {
      this.getEntityData().set(TRADE, Optional.ofNullable(trade));
   }

   public Trade getOfferingTrade() {
      return (Trade)((Optional)this.getEntityData().get(TRADE)).orElse(null);
   }

   public boolean isOfferingTrade() {
      return this.getEntityData().get(TRADE) instanceof Optional ? ((Optional)this.getEntityData().get(TRADE)).isPresent() : false;
   }

   public void setCustomer(Player customer) {
      this.setTrading(customer != null);
      this.customer = customer;
   }

   public Player getCustomer() {
      return this.customer;
   }

   public void setTrading(boolean trading) {
      this.entityData.set(IS_TRADING, trading);
   }

   public boolean isTrading() {
      return (Boolean)this.entityData.get(IS_TRADING);
   }

   @Override
   protected boolean canHoldVaryingWeapons() {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getTarget() instanceof Player && (((Player)this.getTarget()).isCreative() || this.getTarget().isSpectator())) {
         this.setTarget(null);
      }

      if ((!this.isOfferingTrade() || this.timeOffering <= 0) && this.tradeStore.hasStock()) {
         this.setOfferingTrade(this.tradeStore.get(this.random));
         this.timeOffering = this.random.nextInt(18001) + 6000;
      }
   }

   public void openGUI(Player playerEntity) {
      this.setCustomer(playerEntity);
      MMCommon.PROXY.setReferencedMob(this);
      if (!this.level().isClientSide && this.getTarget() == null && this.isAlive()) {
         playerEntity.openMenu(new MenuProvider() {
            public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
               return new ContainerUmvuthanaTrade(id, EntityUmvuthanaMinion.this, playerInventory);
            }

            public Component getDisplayName() {
               return EntityUmvuthanaMinion.this.getDisplayName();
            }
         });
      }
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (this.canTradeWith(player) && this.getTarget() == null && this.isAlive()) {
         this.openGUI(player);
         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   public boolean canTradeWith(Player player) {
      return this.isTrading() ? false : player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof UmvuthanaMask && this.isOfferingTrade();
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingData) {
      this.tradeStore = DEFAULT;
      if (reason == MobSpawnType.COMMAND) {
         this.restrictTo(this.blockPosition(), 25);
      }

      return super.finalizeSpawn(world, difficulty, reason, livingData);
   }

   public boolean requiresCustomPersistence() {
      return true;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.put("tradeStore", this.tradeStore.serialize(this.registryAccess()));
      if (this.isOfferingTrade()) {
         compound.put("offeringTrade", this.getOfferingTrade().serialize(this.registryAccess()));
      }

      compound.putInt("timeOffering", this.timeOffering);
      compound.putInt("HomePosX", this.getRestrictCenter().getX());
      compound.putInt("HomePosY", this.getRestrictCenter().getY());
      compound.putInt("HomePosZ", this.getRestrictCenter().getZ());
      compound.putInt("HomeDist", (int)this.getRestrictRadius());
      if (this.getMisbehavedPlayerId() != null) {
         compound.putUUID("MisbehavedPlayer", this.getMisbehavedPlayerId());
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.tradeStore = TradeStore.deserialize(this.registryAccess(), compound.getCompound("tradeStore"));
      this.setOfferingTrade(Trade.deserialize(this.registryAccess(), compound.getCompound("offeringTrade")));
      this.timeOffering = compound.getInt("timeOffering");
      int i = compound.getInt("HomePosX");
      int j = compound.getInt("HomePosY");
      int k = compound.getInt("HomePosZ");
      int dist = compound.getInt("HomeDist");
      this.restrictTo(new BlockPos(i, j, k), dist);
      UUID uuid;
      if (compound.hasUUID("MisbehavedPlayer")) {
         uuid = compound.getUUID("MisbehavedPlayer");
      } else {
         String s = compound.getString("MisbehavedPlayer");
         uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
      }

      if (uuid != null) {
         try {
            this.setMisbehavedPlayerId(uuid);
         } catch (Throwable var8) {
         }
      }
   }

   @Nullable
   public UUID getMisbehavedPlayerId() {
      return (UUID)((Optional)this.entityData.get(MISBEHAVED_PLAYER)).orElse((UUID)null);
   }

   public void setMisbehavedPlayerId(@Nullable UUID p_184754_1_) {
      this.entityData.set(MISBEHAVED_PLAYER, Optional.ofNullable(p_184754_1_));
   }

   @Nullable
   public LivingEntity getMisbehavedPlayer() {
      try {
         UUID uuid = this.getMisbehavedPlayerId();
         return uuid == null ? null : this.level().getPlayerByUUID(uuid);
      } catch (IllegalArgumentException illegalargumentexception) {
         return null;
      }
   }
}
