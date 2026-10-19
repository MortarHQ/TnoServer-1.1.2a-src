package twilightforest.entity.boss;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAttachments;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.advancements.SimpleAdvancementTrigger;
import twilightforest.entity.ai.control.NoClipMoveControl;
import twilightforest.entity.ai.goal.PhantomAttackStartGoal;
import twilightforest.entity.ai.goal.PhantomThrowWeaponGoal;
import twilightforest.entity.ai.goal.PhantomUpdateFormationAndMoveGoal;
import twilightforest.entity.ai.goal.PhantomWatchAndAttackGoal;
import twilightforest.init.TFAdvancements;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.loot.TFLootTables;
import twilightforest.network.UpdateDeathTimePacket;
import twilightforest.util.entities.EntityUtil;
import twilightforest.util.landmarks.LandmarkUtil;

public class KnightPhantom extends BaseTFBoss {
   private static final Vec3 DYING_ASCENT = new Vec3(0.0, 0.015, 0.0);
   public static final int DYING_TICKS = 18;
   private static final int PARTICLE_TICKS = 70;
   public static final EntityDimensions UNTOUCHABLE = new EntityDimensions(0.0F, 0.0F, 0.0F, EntityAttachments.createDefault(0.0F, 0.0F), true);
   private static final EntityDataAccessor<Boolean> FLAG_CHARGING = SynchedEntityData.defineId(KnightPhantom.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IT_IS_OVER = SynchedEntityData.defineId(KnightPhantom.class, EntityDataSerializers.BOOLEAN);
   private static final AttributeModifier CHARGING_MODIFIER = new AttributeModifier(TwilightForestMod.prefix("charging_attack_boost"), 7.0, Operation.ADD_VALUE);
   private static final AttributeModifier NON_CHARGING_ARMOR_MODIFIER = new AttributeModifier(
      TwilightForestMod.prefix("inactive_armor_boost"), 4.0, Operation.ADD_MULTIPLIED_TOTAL
   );
   private int number;
   private int totalKnownKnights = Integer.MIN_VALUE;
   private int ticksProgress;
   private KnightPhantom.Formation currentFormation;
   private BlockPos chargePos = BlockPos.ZERO;
   private final EntityDimensions invisibleSize = EntityDimensions.fixed(1.25F, 2.5F);
   private final EntityDimensions visibleSize = EntityDimensions.fixed(1.75F, 4.0F);

   public KnightPhantom(EntityType<? extends KnightPhantom> type, Level level) {
      super(type, level);
      this.noPhysics = true;
      this.currentFormation = KnightPhantom.Formation.HOVER;
      this.xpReward = 93;
      this.moveControl = new NoClipMoveControl(this);
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(FLAG_CHARGING, false);
      builder.define(IT_IS_OVER, false);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new PhantomWatchAndAttackGoal(this));
      this.goalSelector.addGoal(1, new PhantomUpdateFormationAndMoveGoal(this));
      this.goalSelector.addGoal(2, new PhantomAttackStartGoal(this));
      this.goalSelector.addGoal(3, new PhantomThrowWeaponGoal(this));
      this.targetSelector.addGoal(0, new NearestAttackableTargetGoal(this, Player.class, false));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder registerAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 35.0).add(Attributes.ATTACK_DAMAGE, 1.0);
   }

   @Override
   public void startSeenByPlayer(ServerPlayer player) {
      if (this.isDeadOrDying()) {
         PacketDistributor.sendToPlayersTrackingEntity(this, new UpdateDeathTimePacket(this.getId(), this.deathTime), new CustomPacketPayload[0]);
      } else if (this.getNumber() == 0) {
         this.getBossBar().addPlayer(player);
      }
   }

   @Override
   public void die(DamageSource cause) {
      super.die(cause);
      if (!this.getNearbyKnights().isEmpty()) {
         this.getBossBar().removeAllPlayers();
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data) {
      data = super.finalizeSpawn(accessor, difficulty, reason, data);
      this.populateDefaultEquipmentSlots(accessor.getRandom(), difficulty);
      this.populateDefaultEquipmentEnchantments(accessor, accessor.getRandom(), difficulty);
      this.getAttribute(Attributes.ARMOR).addTransientModifier(NON_CHARGING_ARMOR_MODIFIER);
      return data;
   }

   protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)TFItems.KNIGHTMETAL_SWORD.get()));
      this.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike)TFItems.PHANTOM_CHESTPLATE.get()));
      this.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike)TFItems.PHANTOM_HELMET.get()));
   }

   public KnightPhantom.Formation getCurrentFormation() {
      return this.currentFormation;
   }

   public BlockPos getChargePos() {
      return this.chargePos;
   }

   public void setChargePos(BlockPos pos) {
      this.chargePos = pos;
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return source.is(DamageTypes.IN_WALL) || super.isInvulnerableTo(source);
   }

   public void aiStep() {
      super.aiStep();
      if (this.level().isClientSide() && this.isChargingAtPlayer() && this.hasYetToDisappear()) {
         for (int i = 0; i < 4; i++) {
            Item particleID = this.getRandom().nextBoolean() ? (Item)TFItems.PHANTOM_HELMET.get() : (Item)TFItems.KNIGHTMETAL_SWORD.get();
            this.level()
               .addParticle(
                  new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(particleID)),
                  this.getX() + (this.getRandom().nextFloat() - 0.5) * this.getBbWidth(),
                  this.getY() + this.getRandom().nextFloat() * (this.getBbHeight() - 0.75) + 0.5,
                  this.getZ() + (this.getRandom().nextFloat() - 0.5) * this.getBbWidth(),
                  0.0,
                  -0.1,
                  0.0
               );
            this.level()
               .addParticle(
                  ParticleTypes.SMOKE,
                  this.getX() + (this.getRandom().nextFloat() - 0.5) * this.getBbWidth(),
                  this.getY() + this.getRandom().nextFloat() * (this.getBbHeight() - 0.75) + 0.5,
                  this.getZ() + (this.getRandom().nextFloat() - 0.5) * this.getBbWidth(),
                  0.0,
                  0.1,
                  0.0
               );
         }
      }
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.totalKnownKnights == Integer.MIN_VALUE) {
         this.updateMyNumber();
      }

      float health = 0.0F;
      float maxHealth = 0.0F;
      int amount = 0;

      for (KnightPhantom nearbyKnight : this.getNearbyKnights()) {
         health += nearbyKnight.getHealth();
         maxHealth += nearbyKnight.getMaxHealth();
         amount++;
      }

      int remaining = this.totalKnownKnights - amount;
      if (remaining > 0) {
         maxHealth += this.getMaxHealth() * remaining;
      }

      this.getBossBar().setProgress(health / maxHealth);
   }

   @Override
   protected void postmortem(ServerLevel serverLevel, DamageSource cause) {
      List<KnightPhantom> knights = this.getNearbyKnights();
      LootParams params = TFLootTables.createLootParams(this, true, cause).create(LootContextParamSets.ENTITY);
      LootTable table = serverLevel.getServer().reloadableRegistries().getLootTable(this.getLootTable());
      if (!knights.isEmpty()) {
         knights.forEach(KnightPhantom::updateMyNumber);
         ObjectArrayList<ItemStack> items = table.getRandomItems(params);
         if (!this.getItemStacks().isEmpty()) {
            items.addAll(this.getItemStacks());
         }

         List<Integer> list = this.getAvailableSlots(this.random);
         table.shuffleAndSplitItems(items, list.size(), this.random);
         giveKnightLoot(knights.getFirst(), items, serverLevel, list, this.position());
      } else {
         this.getBossBar().setProgress(0.0F);
         BlockPos treasurePos = this.getRestrictionPoint() != null
            ? (
               serverLevel.getBlockState(this.getRestrictionPoint().pos().below()).canBeReplaced()
                  ? this.getRestrictionPoint().pos().below()
                  : this.getRestrictionPoint().pos()
            )
            : this.blockPosition();
         ObjectArrayList<ItemStack> items = table.getRandomItems(params);
         net.minecraft.world.level.storage.loot.LootParams.Builder builder = new net.minecraft.world.level.storage.loot.LootParams.Builder(serverLevel)
            .withParameter(LootContextParams.THIS_ENTITY, this)
            .withParameter(LootContextParams.ORIGIN, this.getEyePosition())
            .withParameter(LootContextParams.DAMAGE_SOURCE, cause);
         if (this.lastHurtByPlayer != null) {
            builder = builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
         }

         if (cause.getEntity() != null) {
            builder = builder.withParameter(LootContextParams.ATTACKING_ENTITY, cause.getEntity());
         }

         if (cause.getDirectEntity() != null) {
            builder = builder.withParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, cause.getDirectEntity());
         }

         items.addAll(
            serverLevel.getServer()
               .reloadableRegistries()
               .getLootTable(TFLootTables.KNIGHT_PHANTOM_DEFEATED)
               .getRandomItems(builder.create(LootContextParamSets.ENTITY))
         );
         List<Integer> list = this.getAvailableSlots(this.random);
         table.shuffleAndSplitItems(items, list.size(), this.random);
         giveKnightLoot(this, items, serverLevel, list, this.position());
         if (cause.getEntity() instanceof ServerPlayer player) {
            ((SimpleAdvancementTrigger)TFAdvancements.KILL_ALL_PHANTOMS.get()).trigger(player);

            for (ServerPlayer otherPlayer : this.level().getEntitiesOfClass(ServerPlayer.class, new AABB(treasurePos).inflate(32.0))) {
               ((SimpleAdvancementTrigger)TFAdvancements.KILL_ALL_PHANTOMS.get()).trigger(otherPlayer);
            }
         }

         LandmarkUtil.markStructureConquered(this.level(), this, TFStructures.KNIGHT_STRONGHOLD, true);

         for (KnightPhantom phantom : this.level().getEntitiesOfClass(KnightPhantom.class, this.getBoundingBox().inflate(64.0), LivingEntity::isDeadOrDying)) {
            phantom.deathTime = 1;
            PacketDistributor.sendToPlayersTrackingEntity(phantom, new UpdateDeathTimePacket(phantom.getId(), 1), new CustomPacketPayload[0]);
         }

         this.getEntityData().set(IT_IS_OVER, true);
      }
   }

   protected static void giveKnightLoot(KnightPhantom phantom, ObjectArrayList<ItemStack> items, ServerLevel serverLevel, List<Integer> list, Vec3 dropOff) {
      ObjectListIterator var5 = items.iterator();

      while (var5.hasNext()) {
         ItemStack itemstack = (ItemStack)var5.next();
         if (!list.isEmpty()) {
            while (!list.isEmpty()) {
               int index = list.removeLast();
               if (((ItemStack)phantom.getItemStacks().get(index)).isEmpty()) {
                  ItemStack stack = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack;
                  phantom.getItemStacks().set(index, itemstack);
                  if (!stack.isEmpty() && stack.getCount() > stack.getMaxStackSize()) {
                     stack.setCount(stack.getMaxStackSize());
                  }
                  break;
               }
            }
         } else {
            ItemEntity item = new ItemEntity(serverLevel, dropOff.x(), dropOff.y(), dropOff.z(), itemstack);
            item.setExtendedLifetime();
            item.setNoPickUpDelay();
            serverLevel.addFreshEntity(item);
         }
      }
   }

   @Override
   protected void postRemoval(ServerLevel serverLevel, RemovalReason reason) {
      if (reason.equals(RemovalReason.KILLED) && this.shouldSpawnLoot() && (Boolean)this.entityData.get(IT_IS_OVER)) {
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

   public boolean hurt(DamageSource source, float amount) {
      if (this.isDamageSourceBlocked(source)) {
         this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.level().getRandom().nextFloat() * 0.4F);
         return false;
      } else {
         return super.hurt(source, amount);
      }
   }

   public boolean doHurtTarget(Entity entity) {
      return EntityUtil.properlyApplyCustomDamageSource(this, entity, TFDamageTypes.getEntityDamageSource(this.level(), TFDamageTypes.HAUNT, this), null);
   }

   public void knockback(double damage, double xRatio, double zRatio) {
      this.hasImpulse = true;
      float f = Mth.sqrt((float)(xRatio * xRatio + zRatio * zRatio));
      float distance = 0.2F;
      this.setDeltaMovement(new Vec3(this.getDeltaMovement().x() / 2.0, this.getDeltaMovement().y() / 2.0, this.getDeltaMovement().z() / 2.0));
      this.setDeltaMovement(
         new Vec3(
            this.getDeltaMovement().x() - xRatio / f * distance, this.getDeltaMovement().y() + distance, this.getDeltaMovement().z() - zRatio / f * distance
         )
      );
      if (this.getDeltaMovement().y() > 0.4) {
         this.setDeltaMovement(this.getDeltaMovement().x(), 0.4, this.getDeltaMovement().z());
      }
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public void travel(Vec3 vec3) {
      if (this.isControlledByLocalInstance()) {
         if (this.isInWater()) {
            this.moveRelative(0.02F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
         } else if (this.isInLava()) {
            this.moveRelative(0.02F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
         } else {
            BlockPos ground = this.getBlockPosBelowThatAffectsMyMovement();
            float f = 0.91F;
            if (this.onGround()) {
               f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround()) {
               f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(f));
         }
      }

      this.calculateEntityAnimation(false);
   }

   public boolean onClimbable() {
      return false;
   }

   public List<KnightPhantom> getNearbyKnights() {
      return this.level().getEntitiesOfClass(KnightPhantom.class, this.getBoundingBox().inflate(64.0), LivingEntity::isAlive);
   }

   private void updateMyNumber() {
      List<Integer> nums = Lists.newArrayList();
      List<KnightPhantom> knights = this.getNearbyKnights();

      for (KnightPhantom knight : knights) {
         if (knight != this && knight.isAlive()) {
            nums.add(knight.getNumber());
            if (knight.getNumber() == 0) {
               this.setRestrictionPoint(knight.getRestrictionPoint());
            }
         }
      }

      if (nums.isEmpty()) {
         this.setNumber(0);
      } else {
         int[] n = Ints.toArray(nums);
         Arrays.sort(n);
         int smallest = n[0];
         int largest = knights.size();
         int smallestUnused = largest + 1;
         if (smallest > 0) {
            smallestUnused = 0;
         } else {
            for (int i = 1; i < largest; i++) {
               if (Arrays.binarySearch(n, i) < 0) {
                  smallestUnused = i;
                  break;
               }
            }
         }

         if (this.totalKnownKnights < largest) {
            this.totalKnownKnights = largest;
         }

         if (this.number > smallestUnused || nums.contains(this.number)) {
            this.setNumber(smallestUnused);
         }
      }
   }

   public boolean isChargingAtPlayer() {
      return (Boolean)this.getEntityData().get(FLAG_CHARGING);
   }

   private void setChargingAtPlayer(boolean flag) {
      this.getEntityData().set(FLAG_CHARGING, flag);
      if (!this.level().isClientSide()) {
         if (flag) {
            if (!this.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(CHARGING_MODIFIER.id())) {
               this.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(CHARGING_MODIFIER);
            }

            if (this.getAttribute(Attributes.ARMOR).hasModifier(NON_CHARGING_ARMOR_MODIFIER.id())) {
               this.getAttribute(Attributes.ARMOR).removeModifier(NON_CHARGING_ARMOR_MODIFIER.id());
            }
         } else {
            this.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(CHARGING_MODIFIER.id());
            if (!this.getAttribute(Attributes.ARMOR).hasModifier(NON_CHARGING_ARMOR_MODIFIER.id())) {
               this.getAttribute(Attributes.ARMOR).addTransientModifier(NON_CHARGING_ARMOR_MODIFIER);
            }
         }
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      if (FLAG_CHARGING.equals(accessor)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(accessor);
   }

   public EntityDimensions getDefaultDimensions(Pose pose) {
      return this.isChargingAtPlayer() ? this.visibleSize : this.invisibleSize;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.KNIGHT_PHANTOM_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.KNIGHT_PHANTOM_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.KNIGHT_PHANTOM_DEATH.get();
   }

   private void switchToFormationByNumber(int formationNumber) {
      this.currentFormation = KnightPhantom.Formation.values()[formationNumber];
      this.ticksProgress = 0;
   }

   public void switchToFormation(KnightPhantom.Formation formation) {
      this.currentFormation = formation;
      this.ticksProgress = 0;
      this.updateMyNumber();
      this.setChargingAtPlayer(
         this.currentFormation == KnightPhantom.Formation.ATTACK_PLAYER_START || this.currentFormation == KnightPhantom.Formation.ATTACK_PLAYER_ATTACK
      );
   }

   private int getFormationAsNumber() {
      return this.currentFormation.ordinal();
   }

   public int getTicksProgress() {
      return this.ticksProgress;
   }

   public void setTicksProgress(int ticksProgress) {
      this.ticksProgress = ticksProgress;
   }

   public int getMaxTicksForFormation() {
      return this.currentFormation.duration;
   }

   public boolean isSwordKnight() {
      return this.getMainHandItem().is((Item)TFItems.KNIGHTMETAL_SWORD.get());
   }

   public boolean isAxeKnight() {
      return this.getMainHandItem().is((Item)TFItems.KNIGHTMETAL_AXE.get());
   }

   public boolean isPickKnight() {
      return this.getMainHandItem().is((Item)TFItems.KNIGHTMETAL_PICKAXE.get());
   }

   public int getNumber() {
      return this.number;
   }

   public void setNumber(int number) {
      this.number = number;
      if (number == 0 && !this.isDeadOrDying()) {
         this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(64.0)).forEach(player -> this.getBossBar().addPlayer(player));
      }

      switch (number % 3) {
         case 0:
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)TFItems.KNIGHTMETAL_SWORD.get()));
            break;
         case 1:
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)TFItems.KNIGHTMETAL_AXE.get()));
            break;
         case 2:
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)TFItems.KNIGHTMETAL_PICKAXE.get()));
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("TotalKnownKnights", this.totalKnownKnights);
      compound.putInt("MyNumber", this.getNumber());
      compound.putInt("Formation", this.getFormationAsNumber());
      compound.putInt("TicksProgress", this.getTicksProgress());
      compound.putBoolean("IsItOver", (Boolean)this.getEntityData().get(IT_IS_OVER));
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.totalKnownKnights = compound.getInt("TotalKnownKnights");
      this.setNumber(compound.getInt("MyNumber"));
      this.switchToFormationByNumber(compound.getInt("Formation"));
      this.setTicksProgress(compound.getInt("TicksProgress"));
      this.getEntityData().set(IT_IS_OVER, compound.getBoolean("IsItOver"));
   }

   @Override
   public void setRestrictionPoint(@Nullable GlobalPos pos) {
      super.setRestrictionPoint(pos);
      if (pos != null) {
         this.chargePos = pos.pos();
      }
   }

   @Override
   public int getHomeRadius() {
      return 30;
   }

   @Override
   public ResourceKey<Structure> getHomeStructure() {
      return TFStructures.KNIGHT_STRONGHOLD;
   }

   @Override
   public Block getDeathContainer(RandomSource random) {
      return (Block)TFBlocks.DARK_CHEST.get();
   }

   @Override
   public Block getBossSpawner() {
      return (Block)TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER.get();
   }

   public boolean hasYetToDisappear() {
      return !this.isDeadOrDying() || this.deathTime <= 18 && ((Boolean)this.getEntityData().get(IT_IS_OVER) || !this.getNearbyKnights().isEmpty());
   }

   public Vec3 getDeltaMovement() {
      return this.isDeadOrDying() && this.hasYetToDisappear() ? DYING_ASCENT : super.getDeltaMovement();
   }

   @Override
   public boolean isDeathAnimationFinished() {
      return this.deathTime >= 88 && this.getNearbyKnights().isEmpty();
   }

   @Override
   protected void tickDeath() {
      super.tickDeath();
      if (this.deathTime >= 18 && this.dimensions != UNTOUCHABLE) {
         EntityDimensions oldDimensions = this.dimensions;
         this.dimensions = UNTOUCHABLE;
         this.reapplyPosition();
         boolean flag = UNTOUCHABLE.width() <= 4.0 && UNTOUCHABLE.height() <= 4.0;
         if (!this.level().isClientSide
            && !this.firstTick
            && !this.noPhysics
            && flag
            && (UNTOUCHABLE.width() > oldDimensions.width() || UNTOUCHABLE.height() > oldDimensions.height())) {
            Vec3 vec3 = this.position().add(0.0, oldDimensions.height() / 2.0, 0.0);
            double d0 = Math.max(0.0F, UNTOUCHABLE.width() - oldDimensions.width()) + 1.0E-6;
            double d1 = Math.max(0.0F, UNTOUCHABLE.height() - oldDimensions.height()) + 1.0E-6;
            VoxelShape voxelshape = Shapes.create(AABB.ofSize(vec3, d0, d1, d0));
            this.level()
               .findFreePosition(this, voxelshape, vec3, UNTOUCHABLE.width(), UNTOUCHABLE.height(), UNTOUCHABLE.width())
               .ifPresent(vec31 -> this.setPos(vec31.add(0.0, -UNTOUCHABLE.height() / 2.0, 0.0)));
         }
      }
   }

   @Override
   public void tickDeathAnimation() {
      if (this.level().getEntitiesOfClass(KnightPhantom.class, this.getBoundingBox().inflate(64.0), phantom -> phantom.deathTime < 18).isEmpty()) {
         Vec3 start = this.position().add(0.0, 1.0, 0.0);
         Vec3 end = Vec3.atCenterOf(EntityUtil.bossChestLocation(this));
         Vec3 diff = end.subtract(start);
         double factor = Math.min((this.deathTime - 18 + 1) / 70.0, 1.0);
         double time = this.tickCount + this.getId() * 8;
         Vec3 particlePos = start.add(diff.scale(factor)).add(Math.sin(time * 0.3) * 0.25, Math.sin(time * 0.15) * 0.25, Math.cos(time * 0.35) * 0.25);

         for (int i = 0; i < 3; i++) {
            double x = (this.random.nextDouble() - 0.5) * 0.15 * i;
            double y = (this.random.nextDouble() - 0.5) * 0.15 * i;
            double z = (this.random.nextDouble() - 0.5) * 0.15 * i;
            this.level().addParticle(ParticleTypes.SMOKE, false, particlePos.x() + x, particlePos.y() + y, particlePos.z() + z, 0.0, 0.0, 0.0);
         }
      } else if (!this.getNearbyKnights().isEmpty() || (Boolean)this.getEntityData().get(IT_IS_OVER)) {
         if (this.deathTime == 18) {
            for (int i = 0; i < 20; i++) {
               double d0 = this.getRandom().nextGaussian() * 0.02;
               double d1 = this.getRandom().nextGaussian() * 0.02;
               double d2 = this.getRandom().nextGaussian() * 0.02;
               this.level()
                  .addParticle(
                     this.random.nextBoolean() ? ParticleTypes.SMOKE : ParticleTypes.POOF,
                     this.getRandomX(1.5),
                     this.getRandomY(),
                     this.getRandomZ(1.5),
                     d0,
                     d1,
                     d2
                  );
            }
         } else if (this.deathTime <= 18) {
            for (int i = 0; i < 10; i++) {
               if (this.random.nextInt(4) == 0) {
                  double d0 = this.getRandom().nextGaussian() * 0.02;
                  double d1 = this.getRandom().nextGaussian() * 0.02;
                  double d2 = this.getRandom().nextGaussian() * 0.02;
                  this.level()
                     .addParticle(
                        this.random.nextBoolean() ? ParticleTypes.POOF : ParticleTypes.SMOKE,
                        this.getRandomX(0.75),
                        this.getRandomY(),
                        this.getRandomZ(0.75),
                        d0,
                        d1,
                        d2
                     );
               }

               if (this.random.nextInt(5) == 0) {
                  Item particleID = this.getRandom().nextBoolean() ? (Item)TFItems.PHANTOM_HELMET.get() : (Item)TFItems.KNIGHTMETAL_SWORD.get();
                  this.level()
                     .addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(particleID)),
                        this.getX() + (this.getRandom().nextFloat() - 0.5) * this.getBbWidth(),
                        this.getY() + this.getRandom().nextFloat() * (this.getBbHeight() - 0.75) + 0.5,
                        this.getZ() + (this.getRandom().nextFloat() - 0.5) * this.getBbWidth(),
                        0.0,
                        -0.1,
                        0.0
                     );
               }
            }
         } else {
            double time = this.tickCount + this.getId() * 8;
            Vec3 particlePos = this.position().add(0.0, 1.0, 0.0).add(Math.sin(time * 0.3) * 0.25, Math.sin(time * 0.15) * 0.25, Math.cos(time * 0.35) * 0.25);

            for (int i = 0; i < 3; i++) {
               double x = (this.random.nextDouble() - 0.5) * 0.15 * i;
               double y = (this.random.nextDouble() - 0.5) * 0.15 * i;
               double z = (this.random.nextDouble() - 0.5) * 0.15 * i;
               this.level().addParticle(ParticleTypes.SMOKE, false, particlePos.x() + x, particlePos.y() + y, particlePos.z() + z, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   public void makePoofParticles() {
   }

   @Override
   public Component getBossBarTitle() {
      return Component.translatable("entity.twilightforest.knight_phantom.plural");
   }

   @Override
   public int getBossBarColor() {
      return 8830775;
   }

   public enum Formation {
      HOVER(90),
      LARGE_CLOCKWISE(180),
      SMALL_CLOCKWISE(90),
      LARGE_ANTICLOCKWISE(180),
      SMALL_ANTICLOCKWISE(90),
      CHARGE_PLUSX(180),
      CHARGE_MINUSX(180),
      CHARGE_PLUSZ(180),
      CHARGE_MINUSZ(180),
      WAITING_FOR_LEADER(10),
      ATTACK_PLAYER_START(50),
      ATTACK_PLAYER_ATTACK(50);

      final int duration;

      Formation(int duration) {
         this.duration = duration;
      }
   }
}
