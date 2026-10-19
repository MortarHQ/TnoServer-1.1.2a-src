package com.chronodawn.entities.mobs;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.items.TimeCompassItem;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModItems;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.Nullable;

public class TimeKeeperEntity extends AbstractVillager {
   private static final EntityDataAccessor<Integer> TRADE_COOLDOWN = SynchedEntityData.defineId(TimeKeeperEntity.class, EntityDataSerializers.INT);

   public TimeKeeperEntity(EntityType<? extends TimeKeeperEntity> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 0;
      this.setInvulnerable(true);
   }

   public boolean requiresCustomPersistence() {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return super.hurt(source, amount);
      } else if (source.getEntity() instanceof Player) {
         this.setInvulnerable(false);
         boolean result = super.hurt(source, amount);
         this.setInvulnerable(true);
         return result;
      } else {
         return false;
      }
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(TRADE_COOLDOWN, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
      this.goalSelector.addGoal(2, new AvoidEntityGoal(this, Mob.class, 8.0F, 0.6, 0.6, entity -> entity instanceof Monster));
      this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.4));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.FOLLOW_RANGE, 16.0);
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (!this.level().isClientSide && this.isAlive() && !this.isTrading()) {
         if (this.getOffers().isEmpty()) {
            this.updateTrades();
         }

         this.setTradingPlayer(player);
         this.openTradingScreen(player, this.getDisplayName(), 1);
         return InteractionResult.SUCCESS;
      } else {
         return super.mobInteract(player, hand);
      }
   }

   protected void updateTrades() {
      MerchantOffers offers = this.getOffers();
      if (offers.isEmpty()) {
         offers.add(new MerchantOffer(new ItemCost((ItemLike)ModItems.CLOCKSTONE.get(), 16), new ItemStack(Items.GLOWSTONE_DUST, 4), 5, 5, 0.05F));
         offers.add(
            new MerchantOffer(new ItemCost((ItemLike)ModItems.CLOCKSTONE.get(), 8), new ItemStack((ItemLike)ModItems.FRUIT_OF_TIME.get(), 3), 5, 3, 0.05F)
         );
         offers.add(new MerchantOffer(new ItemCost((ItemLike)ModItems.FRUIT_OF_TIME.get(), 4), new ItemStack(Items.STRING, 8), 8, 2, 0.05F));
         offers.add(
            new MerchantOffer(
               new ItemCost((ItemLike)ModItems.ENHANCED_CLOCKSTONE.get(), 1),
               Optional.of(new ItemCost((ItemLike)ModItems.CLOCKSTONE.get(), 16)),
               new ItemStack(Items.ENDER_PEARL, 1),
               3,
               10,
               0.05F
            )
         );
         offers.add(
            new MerchantOffer(
               new ItemCost((ItemLike)ModItems.ENHANCED_CLOCKSTONE.get(), 2),
               new ItemStack(((Block)ModBlocks.TIME_WOOD_SAPLING.get()).asItem(), 4),
               3,
               5,
               0.05F
            )
         );
         ItemStack desertTowerCompass = this.createCompassWithPosition("desert_clock_tower");
         offers.add(
            new MerchantOffer(
               new ItemCost((ItemLike)ModItems.CLOCKSTONE.get(), 12),
               Optional.of(new ItemCost((ItemLike)ModItems.TIME_CRYSTAL.get(), 4)),
               desertTowerCompass,
               3,
               10,
               0.05F
            )
         );
         ItemStack phantomCatacombsCompass = this.createCompassWithPosition("phantom_catacombs");
         offers.add(
            new MerchantOffer(
               new ItemCost((ItemLike)ModItems.CLOCKSTONE.get(), 12),
               Optional.of(new ItemCost((ItemLike)ModItems.TIME_CRYSTAL.get(), 6)),
               phantomCatacombsCompass,
               2,
               12,
               0.05F
            )
         );
         ItemStack guardianVaultCompass = this.createCompassWithPosition("guardian_vault");
         offers.add(
            new MerchantOffer(
               new ItemCost((ItemLike)ModItems.CLOCKSTONE.get(), 12),
               Optional.of(new ItemCost((ItemLike)ModItems.TIME_CRYSTAL.get(), 6)),
               guardianVaultCompass,
               2,
               12,
               0.05F
            )
         );
         ItemStack clockworkDepthsCompass = this.createCompassWithPosition("clockwork_depths");
         offers.add(
            new MerchantOffer(
               new ItemCost((ItemLike)ModItems.CLOCKSTONE.get(), 12),
               Optional.of(new ItemCost((ItemLike)ModItems.TIME_CRYSTAL.get(), 6)),
               clockworkDepthsCompass,
               2,
               12,
               0.05F
            )
         );
         ItemStack entropyCryptCompass = this.createCompassWithPosition("entropy_crypt");
         offers.add(
            new MerchantOffer(
               new ItemCost((ItemLike)ModItems.CLOCKSTONE.get(), 12),
               Optional.of(new ItemCost((ItemLike)ModItems.TIME_CRYSTAL.get(), 6)),
               entropyCryptCompass,
               2,
               12,
               0.05F
            )
         );
         ItemStack masterClockCompass = this.createCompassWithPosition("master_clock");
         offers.add(
            new MerchantOffer(
               new ItemCost((ItemLike)ModItems.CLOCKSTONE.get(), 16),
               Optional.of(new ItemCost((ItemLike)ModItems.TIME_CRYSTAL.get(), 8)),
               masterClockCompass,
               1,
               15,
               0.05F
            )
         );
      }
   }

   private ItemStack createCompassWithPosition(String structureType) {
      return TimeCompassItem.createCompass(structureType);
   }

   public void notifyTrade(MerchantOffer offer) {
      super.notifyTrade(offer);
   }

   private void locateAndSetStructurePosition(ServerLevel serverLevel, ItemStack compassStack, String structureType) {
      ChronoDawn.LOGGER.debug("Time Keeper: Locating structure for compass - type: {}", structureType);
      ServerLevel searchLevel;
      ResourceLocation structureId;
      switch (structureType) {
         case "ancient_ruins":
            searchLevel = serverLevel.getServer().getLevel(Level.OVERWORLD);
            structureId = CompatResourceLocation.create("chronodawn", "ancient_ruins");
            break;
         case "desert_clock_tower":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "desert_clock_tower");
            break;
         case "master_clock":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "master_clock");
            break;
         case "phantom_catacombs":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "phantom_catacombs");
            break;
         case "guardian_vault":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "guardian_vault");
            break;
         case "clockwork_depths":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "clockwork_depths");
            break;
         case "entropy_crypt":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "entropy_crypt");
            break;
         default:
            ChronoDawn.LOGGER.warn("Time Keeper: Unknown structure type: {}", structureType);
            return;
      }

      if (searchLevel == null) {
         ChronoDawn.LOGGER.warn("Time Keeper: Search level is null for structure: {}", structureType);
      } else {
         Player tradingPlayer = this.getTradingPlayer();
         BlockPos searchOrigin = tradingPlayer != null ? tradingPlayer.blockPosition() : this.blockPosition();
         ChronoDawn.LOGGER.debug("Time Keeper: Searching from position: {}", searchOrigin);
         Registry<Structure> structureRegistry = searchLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
         Structure structureHolder = (Structure)structureRegistry.get(structureId);
         if (structureHolder != null) {
            ChronoDawn.LOGGER.debug("Time Keeper: Structure found in registry: {}", structureId);
            HolderSet<Structure> structureSet = HolderSet.direct(new Holder[]{structureRegistry.wrapAsHolder(structureHolder)});
            Pair<BlockPos, Holder<Structure>> structurePair = searchLevel.getChunkSource()
               .getGenerator()
               .findNearestMapStructure(searchLevel, structureSet, searchOrigin, 100, false);
            if (structurePair != null) {
               BlockPos structurePos = (BlockPos)structurePair.getFirst();
               GlobalPos globalPos = GlobalPos.of(searchLevel.dimension(), structurePos);
               TimeCompassItem.setTargetPosition(compassStack, globalPos);
               ChronoDawn.LOGGER.debug("Time Keeper: Successfully set compass target to: {}", structurePos);
            } else {
               ChronoDawn.LOGGER.warn("Time Keeper: No structure found within 100 chunks of {}", searchOrigin);
            }
         } else {
            ChronoDawn.LOGGER.warn("Time Keeper: Structure not found in registry: {}", structureId);
         }
      }
   }

   protected void rewardTradeXp(MerchantOffer offer) {
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("TradeCooldown", (Integer)this.entityData.get(TRADE_COOLDOWN));
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.entityData.set(TRADE_COOLDOWN, compound.getInt("TradeCooldown"));
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.WANDERING_TRADER_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.WANDERING_TRADER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.WANDERING_TRADER_DEATH;
   }

   public SoundEvent getNotifyTradeSound() {
      return SoundEvents.WANDERING_TRADER_YES;
   }

   public boolean showProgressBar() {
      return false;
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mate) {
      return null;
   }

   public static boolean checkTimeKeeperSpawnRules(
      EntityType<TimeKeeperEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return Mob.checkMobSpawnRules(entityType, level, spawnType, pos, random) && level.getRawBrightness(pos, 0) >= 9;
   }
}
