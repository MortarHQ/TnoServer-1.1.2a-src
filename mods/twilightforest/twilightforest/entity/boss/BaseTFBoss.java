package twilightforest.entity.boss;

import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction.Plane;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.config.TFConfig;
import twilightforest.entity.EnforcedHomePoint;
import twilightforest.entity.boss.bar.ServerTFBossBar;
import twilightforest.loot.TFLootTables;
import twilightforest.network.UpdateDeathTimePacket;
import twilightforest.util.entities.EntityUtil;
import twilightforest.util.landmarks.LandmarkUtil;

public abstract class BaseTFBoss extends Monster implements IBossLootBuffer, EnforcedHomePoint {
   private static final EntityDataAccessor<Optional<GlobalPos>> HOME_POINT = SynchedEntityData.defineId(
      BaseTFBoss.class, EntityDataSerializers.OPTIONAL_GLOBAL_POS
   );
   private final ServerTFBossBar bossEvent;
   private final NonNullList<ItemStack> dyingInventory = NonNullList.withSize(27, ItemStack.EMPTY);

   protected BaseTFBoss(EntityType<? extends Monster> type, Level level) {
      super(type, level);
      this.bossEvent = this.createBossBar();
   }

   public abstract ResourceKey<Structure> getHomeStructure();

   public abstract Block getDeathContainer(RandomSource var1);

   public abstract Block getBossSpawner();

   protected boolean shouldSpawnLoot() {
      return this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);
   }

   protected boolean shouldCreateSpawner() {
      return true;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(HOME_POINT, Optional.empty());
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.getBossBar().addPlayer(player);
      if (this.deathTime > 0) {
         PacketDistributor.sendToPlayersTrackingEntity(this, new UpdateDeathTimePacket(this.getId(), this.deathTime), new CustomPacketPayload[0]);
      }
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.getBossBar().removePlayer(player);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      this.saveHomePointToNbt(compound);
      this.addDeathItemsSaveData(compound, this.registryAccess());
      super.addAdditionalSaveData(compound);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.readDeathItemsSaveData(compound, this.registryAccess());
      this.loadHomePointFromNbt(compound);
   }

   public void lavaHurt() {
      if (!this.fireImmune()) {
         this.igniteForSeconds(5.0F);
         if (this.hurt(this.damageSources().lava(), 4.0F)) {
            this.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + this.getRandom().nextFloat() * 0.4F);
            EntityUtil.killLavaAround(this);
         }
      }
   }

   public void die(DamageSource cause) {
      super.die(cause);
      if (this.shouldSpawnLoot() && this.level() instanceof ServerLevel server) {
         this.postmortem(server, cause);
      }
   }

   protected void postmortem(ServerLevel serverLevel, DamageSource cause) {
      this.getBossBar().setProgress(0.0F);
      IBossLootBuffer.saveDropsIntoBoss(this, TFLootTables.createLootParams(this, true, cause).create(LootContextParamSets.ENTITY), serverLevel);
      LandmarkUtil.markStructureConquered(serverLevel, this, this.getHomeStructure(), true);
   }

   public void remove(RemovalReason reason) {
      if (this.level() instanceof ServerLevel serverLevel) {
         this.postRemoval(serverLevel, reason);
      }

      super.remove(reason);
   }

   protected void postRemoval(ServerLevel serverLevel, RemovalReason reason) {
      if (reason.equals(RemovalReason.KILLED) && this.shouldSpawnLoot()) {
         IBossLootBuffer.depositDropsIntoChest(
            this,
            (BlockState)this.getDeathContainer(this.getRandom())
               .defaultBlockState()
               .setValue(ChestBlock.FACING, Plane.HORIZONTAL.getRandomDirection(this.level().getRandom())),
            EntityUtil.bossChestLocation(this),
            serverLevel
         );
      }
   }

   public void checkDespawn() {
      if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
         if (this.shouldCreateSpawner()
            && this.isRestrictionPointValid(this.level().dimension())
            && this.level().isLoaded(Objects.requireNonNull(this.getRestrictionPoint()).pos())) {
            this.placeSpawner(this.getRestrictionPoint().pos());
         }

         this.discard();
      } else {
         super.checkDespawn();
      }
   }

   public void placeSpawner(BlockPos pos) {
      this.level().setBlockAndUpdate(pos, this.getBossSpawner().defaultBlockState());
   }

   protected boolean shouldDespawnInPeaceful() {
      return true;
   }

   protected boolean shouldDropLoot() {
      return !TFConfig.bossDropChests;
   }

   public boolean removeWhenFarAway(double distance) {
      return false;
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   public boolean isPushedByFluid(FluidType type) {
      return false;
   }

   protected float getWaterSlowDown() {
      return 1.0F;
   }

   public boolean canUsePortal(boolean force) {
      return false;
   }

   @Nullable
   @Override
   public GlobalPos getRestrictionPoint() {
      return (GlobalPos)((Optional)this.getEntityData().get(HOME_POINT)).orElse(null);
   }

   @Override
   public void setRestrictionPoint(@Nullable GlobalPos pos) {
      this.getEntityData().set(HOME_POINT, Optional.ofNullable(pos));
   }

   @Override
   public NonNullList<ItemStack> getItemStacks() {
      return this.dyingInventory;
   }

   protected void tickDeath() {
      this.deathTime++;
      if (!this.isRemoved()) {
         if (!this.level().isClientSide()) {
            if (this.isDeathAnimationFinished()) {
               this.level().broadcastEntityEvent(this, (byte)60);
               this.remove(RemovalReason.KILLED);
            } else {
               this.tickBossBar();
            }
         } else {
            this.tickDeathAnimation();
         }
      }
   }

   public boolean isDeathAnimationFinished() {
      return this.deathTime >= 20;
   }

   public void tickDeathAnimation() {
   }

   public ServerTFBossBar getBossBar() {
      return this.bossEvent;
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (!this.level().isClientSide()) {
         this.tickBossBar();
      }
   }

   protected void tickBossBar() {
      this.getBossBar().setProgress(this.getHealth() / this.getMaxHealth());
   }

   protected ServerTFBossBar createBossBar() {
      return new ServerTFBossBar(this.getBossBarTitle(), this.getBossBarColor(), this.getBossBarOverlay());
   }

   public Component getBossBarTitle() {
      return this.getDisplayName() != null ? this.getDisplayName() : this.getTypeName();
   }

   public abstract int getBossBarColor();

   public BossBarOverlay getBossBarOverlay() {
      return BossBarOverlay.PROGRESS;
   }

   public void setCustomName(@Nullable Component name) {
      super.setCustomName(name);
      this.bossEvent.setName(this.getBossBarTitle());
   }
}
