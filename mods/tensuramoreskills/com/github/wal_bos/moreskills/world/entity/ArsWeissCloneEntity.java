package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.ability.skill.unique.AlbisVinaSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.AlbisVinaHandler;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ArsWeissCloneEntity extends Mob {
   private static final EntityDataAccessor<String> OWNER_UUID = SynchedEntityData.defineId(ArsWeissCloneEntity.class, EntityDataSerializers.STRING);
   private static final EntityDataAccessor<String> OWNER_NAME = SynchedEntityData.defineId(ArsWeissCloneEntity.class, EntityDataSerializers.STRING);
   private static final EntityDataAccessor<Integer> LIFE = SynchedEntityData.defineId(ArsWeissCloneEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> ANCHOR_BODY = SynchedEntityData.defineId(ArsWeissCloneEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> CLONE_SOURCE = SynchedEntityData.defineId(ArsWeissCloneEntity.class, EntityDataSerializers.INT);
   private static final int SOURCE_ALBIS_FORMULA = 0;
   private static final int SOURCE_ELFARIA_WORKER = 1;
   private boolean continuingCast;
   private boolean preparedCast;
   private boolean releaseAttempted;
   private ResourceLocation magicId;
   private int magicMode;
   private int keyNumber;
   private int heldTicks;
   private int castTime;
   private Vec3 storedLook = Vec3.ZERO;
   private ManasSkillInstance mirroredInstance;
   private ItemStack requestedStack = ItemStack.EMPTY;
   private ItemStack finalRequestedStack = ItemStack.EMPTY;
   private ItemStack cargoStack = ItemStack.EMPTY;
   private final List<ItemStack> workQueue = new ArrayList<>();
   private final List<ItemStack> cargoStacks = new ArrayList<>();
   private BlockPos targetBlock = BlockPos.ZERO;
   private BlockPos sendTarget = BlockPos.ZERO;
   private boolean hasTargetBlock;
   private boolean hasSendTarget;
   private int dropScanCooldown;
   private int containerScanCooldown;
   private int mobScanCooldown;
   private int blockScanCooldown;
   private boolean returning;
   private int minedBlocks;
   private int workDelay;
   private int scanCooldown;
   private String huntTargetName = "";
   private String huntQuery = "";
   private String armyQuery = "";
   private int armyDelay;
   private UUID huntTargetId;
   private final Set<Long> forcedChunks = new HashSet<>();
   private int offlineTicks;
   private int stuckTicks;
   private double lastWorkX;
   private double lastWorkY;
   private double lastWorkZ;
   private long lastGlobalCommandId;
   private int statusReportCooldown;
   private int unresolvedTicks;
   private String lastCommandMode = "";
   private String lastCommandQuery = "";
   private int lastCommandAmount = 1;
   private BlockPos lastSearchCenter = BlockPos.ZERO;
   private int searchRadius;
   private int worldProbeCooldown;
   private int farRoamStep;
   private int waterTravelCooldown;
   private int caveTunnelCooldown;
   private int longTravelCooldown;
   private int oceanTravelCooldown;
   private int oceanTravelStep;
   private boolean commanderLeader;
   private int localScanBudget;
   private int scanBudgetTick = -1;
   private int huntTacticCooldown;
   private int softTravelTicks;
   private int miningModeTicks;
   private int lastMinedBlocksForStuck;
   private int miningNoProgressTicks;
   private int miningDirX = 1;
   private int miningDirZ;
   private int roamGoalTicks;
   private int roamNoProgressTicks;
   private double lastRoamX;
   private double lastRoamZ;
   private String lastSelfNameCommand = "";

   public ArsWeissCloneEntity(EntityType<? extends ArsWeissCloneEntity> entityType, Level level) {
      super(entityType, level);
      this.noCulling = true;
      this.xpReward = 0;
      this.setNoAi(false);
      this.setInvulnerable(false);
      this.setCanPickUpLoot(false);
   }

   private static MoreSkillsConfigs.ElfariaTheHallowedIcemaidenConfig cfg() {
      return MoreSkillsConfigs.GRAND.elfariaTheHallowedIcemaiden;
   }

   private double workerMoveSpeed() {
      return Math.max(0.1, (Double)cfg().parallelBodyWorkerMoveSpeed.get() * 0.98);
   }

   private int scanPhase(int interval) {
      int value = Math.max(1, interval);
      return Math.floorMod(this.getId(), value);
   }

   private boolean canUseScanBudget(ServerPlayer owner, int cost) {
      if (owner == null) {
         return false;
      }

      int now = this.tickCount;
      if (this.scanBudgetTick != now) {
         this.scanBudgetTick = now;
         this.localScanBudget = 2;
      }

      int realCost = Math.max(1, cost);
      if (this.localScanBudget < realCost) {
         return false;
      }

      if (!ArsWeissCloneScanBudget.tryUse(owner.getUUID(), this.level().getGameTime(), realCost)) {
         return false;
      }

      this.localScanBudget -= realCost;
      return true;
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 40.0)
         .add(Attributes.MOVEMENT_SPEED, 0.34)
         .add(Attributes.FOLLOW_RANGE, 64.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.45)
         .add(Attributes.ARMOR, 8.0)
         .add(Attributes.ATTACK_DAMAGE, 6.0)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(TensuraAttributes.CHANT_SPEED, 1.0)
         .add(TensuraAttributes.MAGIC_COST_MULTIPLIER, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(OWNER_UUID, "");
      builder.define(OWNER_NAME, "");
      builder.define(LIFE, 240);
      builder.define(ANCHOR_BODY, false);
      builder.define(CLONE_SOURCE, 0);
   }

   protected void registerGoals() {
   }

   public boolean hasInfiniteMaterials() {
      return true;
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (this.level().isClientSide()) {
         return InteractionResult.SUCCESS;
      } else {
         ServerPlayer owner = this.getOwnerPlayer();
         if (!(player instanceof ServerPlayer serverPlayer && owner != null && owner.getUUID().equals(serverPlayer.getUUID()))) {
            return InteractionResult.PASS;
         } else if (this.isElfariaWorkerClone() && !this.isAnchorBody()) {
            ItemStack held = serverPlayer.getItemInHand(hand);
            if (held.isEmpty()) {
               this.giveCommandTags(serverPlayer);
               serverPlayer.displayClientMessage(
                  Component.literal("Ars Weiss gave command tags. Examples: gather:iron 32, send 120 70 -400, hunt:playerName, guard:owner, status all."), true
               );
               return InteractionResult.SUCCESS;
            }

            if (held.has(DataComponents.CUSTOM_NAME) || held.is(Items.NAME_TAG)) {
               String text = held.getHoverName().getString();
               if (this.acceptTextOrder(owner, text)) {
                  if (!serverPlayer.getAbilities().instabuild) {
                     held.shrink(1);
                  }

                  this.permanentizeOrder();
                  this.signalOrderAccepted();
                  serverPlayer.displayClientMessage(Component.literal("Ars Weiss accepted: " + text), true);
                  return InteractionResult.SUCCESS;
               }
            }

            this.beginGatherOrder(owner, held.copyWithCount(1));
            this.permanentizeOrder();
            this.signalOrderAccepted();
            serverPlayer.displayClientMessage(Component.literal("Ars Weiss gathering: " + held.getHoverName().getString()), true);
            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   private void giveCommandTags(ServerPlayer player) {
      ItemStack tags = new ItemStack(Items.NAME_TAG, 16);
      if (!player.getInventory().add(tags)) {
         Containers.dropItemStack(player.level(), player.getX(), player.getY(), player.getZ(), tags);
      }
   }

   public boolean canBeAffected(MobEffectInstance effectInstance) {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      if (source != null && source.is(DamageTypes.FALL)) {
         this.fallDistance = 0.0F;
         return false;
      } else if (this.isProtectedCloneDamage(source)) {
         return false;
      } else {
         return this.continuingCast && cfg().parallelBodyCloneInvulnerableWhileCasting.get() ? false : super.hurt(source, amount);
      }
   }

   public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
      this.fallDistance = 0.0F;
      return false;
   }

   public void die(DamageSource source) {
      if (!this.level().isClientSide()) {
         this.shatter(false);
      } else {
         super.die(source);
      }
   }

   private boolean isProtectedCloneDamage(DamageSource source) {
      if (source == null) {
         return false;
      }

      Entity attacker = source.getEntity();
      Entity direct = source.getDirectEntity();
      ServerPlayer owner = this.getOwnerPlayer();
      UUID ownerId = this.getOwnerUUIDValue();
      if (attacker != this && direct != this) {
         if (owner != null && (attacker == owner || direct == owner) && !this.isAnchorBody()) {
            return true;
         } else if (attacker instanceof ArsWeissCloneEntity clone && ownerId != null && ownerId.equals(clone.getOwnerUUIDValue())) {
            return true;
         } else if (direct instanceof ArsWeissCloneEntity clone && ownerId != null && ownerId.equals(clone.getOwnerUUIDValue())) {
            return true;
         } else {
            if (direct instanceof Projectile projectile) {
               Entity projectileOwner = projectile.getOwner();
               if (projectileOwner == this) {
                  return true;
               }

               if (owner != null && projectileOwner == owner && !this.isAnchorBody()) {
                  return true;
               }

               if (projectileOwner instanceof ArsWeissCloneEntity clone && ownerId != null && ownerId.equals(clone.getOwnerUUIDValue())) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }

   public boolean isPersistenceRequired() {
      return this.isAnchorBody() || this.hasPermanentOrder() || (Integer)this.entityData.get(LIFE) < 0;
   }

   public boolean requiresCustomPersistence() {
      return this.isPersistenceRequired();
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public boolean wantsToPickUp(ItemStack stack) {
      return false;
   }

   public boolean canPickUpLoot() {
      return false;
   }

   public boolean canBeLeashed(Player player) {
      return false;
   }

   public boolean canChangeDimensions(Level from, Level to) {
      return false;
   }

   public Entity changeDimension(DimensionTransition transition) {
      this.discard();
      return null;
   }

   public boolean canRide(Entity entity) {
      return false;
   }

   protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
   }

   protected void dropAllDeathLoot(DamageSource damageSource) {
   }

   public boolean shouldDropExperience() {
      return false;
   }

   public boolean shouldShowName() {
      return false;
   }

   public void setOwnerData(Player owner, int life) {
      this.entityData.set(OWNER_UUID, owner.getUUID().toString());
      this.entityData.set(OWNER_NAME, owner.getGameProfile().getName());
      this.entityData.set(LIFE, life);
      this.storedLook = owner.getLookAngle();
      this.setHealth(this.getMaxHealth());
      this.setInvulnerable(false);
      this.copyEquipment(owner);
      this.copyCastingAttributes(owner);
   }

   public void setAnchorBody(boolean anchor) {
      this.entityData.set(ANCHOR_BODY, anchor);
      this.setNoAi(anchor);
      this.setNoGravity(anchor);
      this.getNavigation().stop();
      this.returning = false;
      this.hasTargetBlock = false;
      if (anchor) {
         this.setInvulnerable(false);
      }
   }

   public boolean isAnchorBody() {
      return (Boolean)this.entityData.get(ANCHOR_BODY);
   }

   public void setAlbisFormulaClone() {
      this.entityData.set(CLONE_SOURCE, 0);
   }

   public void setElfariaWorkerClone() {
      this.entityData.set(CLONE_SOURCE, 1);
      this.entityData.set(LIFE, -1);
   }

   public boolean isAlbisFormulaClone() {
      return (Integer)this.entityData.get(CLONE_SOURCE) == 0;
   }

   public boolean isElfariaWorkerClone() {
      return (Integer)this.entityData.get(CLONE_SOURCE) == 1;
   }

   public UUID getOwnerUUIDValue() {
      try {
         return UUID.fromString((String)this.entityData.get(OWNER_UUID));
      } catch (Exception ignored) {
         return null;
      }
   }

   public String getOwnerNameValue() {
      return (String)this.entityData.get(OWNER_NAME);
   }

   public ServerPlayer getOwnerPlayer() {
      UUID uuid = this.getOwnerUUIDValue();
      return uuid != null && !this.level().isClientSide() ? this.level().getServer().getPlayerList().getPlayer(uuid) : null;
   }

   public void copyLookFrom(Entity entity) {
      this.setXRot(entity.getXRot());
      this.setYRot(entity.getYRot());
      this.setYHeadRot(entity.getYRot());
      this.storedLook = entity.getLookAngle().normalize();
   }

   public void copyEquipment(LivingEntity owner) {
      this.setItemSlot(EquipmentSlot.HEAD, owner.getItemBySlot(EquipmentSlot.HEAD).copy());
      this.setItemSlot(EquipmentSlot.CHEST, owner.getItemBySlot(EquipmentSlot.CHEST).copy());
      this.setItemSlot(EquipmentSlot.LEGS, owner.getItemBySlot(EquipmentSlot.LEGS).copy());
      this.setItemSlot(EquipmentSlot.FEET, owner.getItemBySlot(EquipmentSlot.FEET).copy());
      this.setItemSlot(EquipmentSlot.MAINHAND, owner.getItemBySlot(EquipmentSlot.MAINHAND).copy());
      this.setItemSlot(EquipmentSlot.OFFHAND, owner.getItemBySlot(EquipmentSlot.OFFHAND).copy());
   }

   private void copyCastingAttributes(LivingEntity owner) {
      AttributeInstance chant = this.getAttribute(TensuraAttributes.CHANT_SPEED);
      if (chant != null) {
         chant.setBaseValue(Math.max(1.0, owner.getAttributeValue(TensuraAttributes.CHANT_SPEED)));
      }

      AttributeInstance cost = this.getAttribute(TensuraAttributes.MAGIC_COST_MULTIPLIER);
      if (cost != null) {
         cost.setBaseValue(Math.max(0.0, owner.getAttributeValue(TensuraAttributes.MAGIC_COST_MULTIPLIER)));
      }

      this.copyCombatAttributes(owner);
   }

   private void copyCombatAttributes(LivingEntity owner) {
      this.copyAttributeBase(Attributes.MAX_HEALTH, Math.max(80.0, owner.getAttributeValue(Attributes.MAX_HEALTH)));
      this.copyAttributeBase(Attributes.ATTACK_DAMAGE, Math.max(20.0, owner.getAttributeValue(Attributes.ATTACK_DAMAGE)));
      this.copyAttributeBase(Attributes.ARMOR, Math.max(20.0, owner.getAttributeValue(Attributes.ARMOR)));
      this.copyAttributeBase(Attributes.MOVEMENT_SPEED, Math.max(0.38, owner.getAttributeValue(Attributes.MOVEMENT_SPEED)));
      this.copyAttributeBase(Attributes.FOLLOW_RANGE, Math.max(128.0, owner.getAttributeValue(Attributes.FOLLOW_RANGE)));
      this.copyAttributeBase(Attributes.KNOCKBACK_RESISTANCE, Math.max(0.9, owner.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
      if (this.getHealth() < this.getMaxHealth() && this.tickCount % 40 == 0) {
         this.heal(Math.max(4.0F, this.getMaxHealth() * 0.08F));
      }
   }

   private void copyAttributeBase(Holder<Attribute> attribute, double value) {
      AttributeInstance instance = this.getAttribute(attribute);
      if (instance != null) {
         instance.setBaseValue(value);
      }
   }

   public void startMirroredMagicCast(
      ResourceLocation magicId, int mode, int keyNumber, int castTime, Vec3 look, float xRot, float yRot, CompoundTag instanceTag
   ) {
      if (!this.continuingCast && !this.isAnchorBody()) {
         this.magicId = magicId;
         this.magicMode = mode;
         this.keyNumber = keyNumber;
         this.heldTicks = 0;
         this.castTime = Math.max(1, castTime);
         this.storedLook = look.normalize();
         this.setXRot(xRot);
         this.setYRot(yRot);
         this.setYHeadRot(yRot);
         this.continuingCast = true;
         this.preparedCast = false;
         this.releaseAttempted = false;
         this.invulnerableTime = 80;
         this.removeAllEffects();
         this.setHealth(this.getMaxHealth());
         this.mirroredInstance = ManasSkillInstance.fromNBT(instanceTag.copy());
         int currentLife = (Integer)this.entityData.get(LIFE);
         if (currentLife >= 0) {
            this.entityData.set(LIFE, Math.max(currentLife, this.castTime + 100));
         }
      }
   }

   public boolean isContinuingCast() {
      return this.continuingCast;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         ServerPlayer owner = this.getOwnerPlayer();
         if (owner != null && owner.isAlive()) {
            this.offlineTicks = 0;
            if (owner.level().dimension() != this.level().dimension()) {
               if (this.isElfariaWorkerClone() && this.hasActiveWorkOrder()) {
                  this.tickOfflineWorker();
               } else {
                  this.discard();
               }
            } else {
               if (this.isElfariaWorkerClone()) {
                  if (!SkillUtils.hasSkill(owner, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get())) {
                     this.shatter(false);
                     return;
                  }
               } else if (!SkillUtils.hasSkill(owner, (ManasSkill)AllSkills.ALBIS_VINA.get())
                  && !SkillUtils.hasSkill(owner, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get())) {
                  this.shatter(false);
                  return;
               }

               this.fallDistance = 0.0F;
               this.tickSelfNameCommand(owner);
               if (this.isAnchorBody()) {
                  this.tickAnchorBody(owner);
               } else {
                  if (this.tickCount % 20 == 0) {
                     this.copyEquipment(owner);
                     this.copyCastingAttributes(owner);
                  }

                  int currentLife = (Integer)this.entityData.get(LIFE);
                  if (currentLife > 0 && !this.hasPermanentOrder()) {
                     int life = currentLife - 1;
                     this.entityData.set(LIFE, life);
                     if (life <= 0) {
                        this.shatter(true);
                        return;
                     }
                  }

                  if (this.continuingCast) {
                     this.tickMirroredCast(owner);
                  } else {
                     this.tickParallelWorker(owner);
                  }
               }
            }
         } else if (this.isElfariaWorkerClone()) {
            this.tickOfflineWorker();
         } else {
            this.shatter(false);
         }
      }
   }

   private void tickSelfNameCommand(ServerPlayer owner) {
      if (owner != null && this.isElfariaWorkerClone() && !this.isAnchorBody()) {
         Component customName = this.getCustomName();
         if (customName == null) {
            this.lastSelfNameCommand = "";
         } else {
            String text = customName.getString() == null ? "" : customName.getString().trim();
            if (text.isBlank()) {
               this.lastSelfNameCommand = "";
            } else if (this.looksLikeNameTagOrder(text)) {
               String normalized = this.normalizeSearchText(text);
               if (!normalized.equals(this.lastSelfNameCommand)) {
                  this.lastSelfNameCommand = normalized;
                  if (this.acceptTextOrder(owner, text)) {
                     this.permanentizeOrder();
                     this.signalOrderAccepted();
                     this.setCustomName(null);
                     this.lastSelfNameCommand = "";
                     owner.displayClientMessage(Component.literal("Ars Weiss accepted: " + text), true);
                  }
               }
            }
         }
      }
   }

   private boolean looksLikeNameTagOrder(String raw) {
      String cleaned = this.normalizeSearchText(raw);
      if (!cleaned.equals("commander") && !cleaned.equals("command leader") && !cleaned.equals("leader")) {
         ArsWeissCloneOrderParser.Order order = ArsWeissCloneOrderParser.parse(raw);
         return !order.mode().isBlank() ? true : this.isTextCommand(raw);
      } else {
         return true;
      }
   }

   private void tickAnchorBody(ServerPlayer owner) {
      this.setNoAi(true);
      this.getNavigation().stop();
      this.setDeltaMovement(Vec3.ZERO);
      this.hurtMarked = true;
      if (this.tickCount % 20 == 0) {
         this.copyEquipment(owner);
      }
   }

   private void tickOfflineWorker() {
      this.offlineTicks++;
      if (!this.isElfariaWorkerClone()) {
         this.shatter(false);
      } else {
         this.entityData.set(LIFE, -1);
         if (this.continuingCast) {
            this.stopMirroredCast();
         }

         if (this.tickCount % 40 == 0) {
            this.removeAllEffects();
            if (this.getHealth() < this.getMaxHealth()) {
               this.heal(Math.max(1.0F, this.getMaxHealth() * 0.02F));
            }
         }

         if (this.hasActiveWorkOrder()) {
            this.releaseForcedChunk();
            this.getNavigation().stop();
            this.setDeltaMovement(Vec3.ZERO);
            this.hurtMarked = true;
         } else {
            this.releaseForcedChunk();
            if (this.offlineTicks > 72000) {
               this.getNavigation().stop();
            }
         }
      }
   }

   private void tickParallelWorker(ServerPlayer owner) {
      if (!this.isElfariaWorkerClone()) {
         this.releaseForcedChunk();
         this.followOwner(owner);
      } else if (!SkillUtils.hasSkill(owner, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get())) {
         this.releaseForcedChunk();
         this.followOwner(owner);
      } else {
         this.tickHiveMemory(owner);
         this.scanOwnerCommandTags(owner);
         this.tickGlobalOwnerCommand(owner);
         this.tickStatusReport(owner);
         if ((this.isInWaterOrBubble() || this.isNoGravity()) && this.tickCount % 20 == this.scanPhase(20)) {
            this.tickMobilitySupport(owner);
         }

         if (this.isNoGravity() && !this.isInWaterOrBubble()) {
            this.setNoGravity(false);
         }

         this.depositCargoToOwner(owner, false);
         if (!this.returning) {
            if (this.hasSendTarget) {
               this.keepTaskChunkLoaded();
               this.tickSendOrder(owner);
            } else if (this.hasArmyOrder()) {
               this.keepTaskChunkLoaded();
               this.tickArmyOrder(owner);
            } else if (this.hasHuntOrder()) {
               this.keepTaskChunkLoaded();
               this.tickHuntOrder(owner);
            } else {
               if (this.workQueue.isEmpty() && this.requestedStack.isEmpty() && this.cargoStacks.isEmpty() && this.cargoStack.isEmpty()) {
                  this.scanForBlueprint(owner);
                  if (this.workQueue.isEmpty() && this.requestedStack.isEmpty()) {
                     this.followOwner(owner);
                     return;
                  }
               }

               this.compactCargo();
               ItemStack target = this.currentWantedStack();
               if (target.isEmpty()) {
                  if (this.isOrderComplete()) {
                     this.keepTaskChunkLoaded();
                     this.returnToOwner(owner);
                  } else {
                     this.keepTaskChunkLoaded();
                     this.roamForWork(owner, this.requestedText());
                  }
               } else {
                  this.requestedStack = target.copyWithCount(1);
                  this.collectUsefulDrops(owner);
                  if (!this.isCurrentChecklistStepComplete() && !this.isCarryFull()) {
                     target = this.currentWantedStack();
                     if (target.isEmpty()) {
                        this.keepTaskChunkLoaded();
                        this.returnToOwner(owner);
                     } else {
                        this.requestedStack = target.copyWithCount(1);
                        this.keepTaskChunkLoaded();
                        if (this.isMineOrder()) {
                           this.collectUsefulDrops(owner);
                           this.mineRequestedBlocks(owner, target);
                        } else if (!this.moveToNearbyUsefulDrop(owner, target)) {
                           if (this.isLootOrder()) {
                              this.tickLootOrder(owner, target);
                           } else if (!this.moveToNearbyUsefulContainer(owner, target)) {
                              if (!ArsWeissCloneWorkerBrain.isLikelyMobDrop(target)) {
                                 this.mineRequestedBlocks(owner, target);
                              } else if (!this.huntMaterialMob(owner, target)) {
                                 this.roamForWork(
                                    owner,
                                    ArsWeissCloneLootIntel.structureHint(this.stackText(target)).isBlank()
                                       ? this.stackText(target)
                                       : ArsWeissCloneLootIntel.structureHint(this.stackText(target))
                                 );
                                 this.recoverIfStuck(owner.blockPosition());
                              }
                           }
                        }
                     }
                  } else {
                     this.keepTaskChunkLoaded();
                     this.returnToOwner(owner);
                  }
               }
            }
         } else {
            this.compactCargo();
            if (this.cargoStacks.isEmpty() && this.cargoStack.isEmpty()) {
               this.followOwner(owner);
               double follow = Math.max(2.0, (Double)cfg().parallelBodyWorkerFollowDistance.get());
               if (this.distanceToSqr(owner) <= follow * follow) {
                  this.returning = false;
                  if (!this.hasActiveWorkOrder()) {
                     this.releaseForcedChunk();
                  }
               }
            } else {
               this.keepTaskChunkLoaded();
               this.returnToOwner(owner);
            }
         }
      }
   }

   private void tickHiveMemory(ServerPlayer owner) {
      if (owner != null && !this.level().isClientSide()) {
         if (this.tickCount % 100 == this.scanPhase(100)) {
            String dimension = this.level().dimension().location().toString();
            long gameTime = this.level().getGameTime();
            ArsWeissCloneHiveMemory.touch(owner.getUUID(), this.getUUID(), dimension, this.blockPosition(), gameTime);
            if (this.tickCount % 1200 == this.scanPhase(1200)) {
               ArsWeissCloneHiveMemory.prune(owner.getUUID(), gameTime);
            }
         }
      }
   }

   private void rememberHive(ServerPlayer owner, String type, String query, BlockPos pos, int score) {
      if (owner != null && pos != null && !this.level().isClientSide()) {
         ArsWeissCloneHiveMemory.remember(owner.getUUID(), type, query, this.level().dimension().location().toString(), pos, score, this.level().getGameTime());
      }
   }

   private void scanForBlueprint(ServerPlayer owner) {
      if (this.scanCooldown > 0) {
         this.scanCooldown--;
      } else {
         this.scanCooldown = Math.max(200, (Integer)cfg().parallelBodyWorkerScanIntervalTicks.get() * 10 + this.scanPhase(80));
         if (this.canUseScanBudget(owner, 3)) {
            ItemEntity item = this.findBestDroppedOrder(owner);
            if (item != null) {
               ItemStack stack = item.getItem();
               if (!this.tryAcceptGlobalCommandDrop(owner, item, stack)) {
                  if (this.tryAcceptHuntBlueprint(owner, stack)) {
                     stack.shrink(1);
                     if (stack.isEmpty()) {
                        item.discard();
                     } else {
                        item.setItem(stack);
                     }

                     this.permanentizeOrder();
                     this.signalOrderAccepted();
                  } else {
                     ItemStack blueprint = stack.copyWithCount(1);
                     stack.shrink(1);
                     if (stack.isEmpty()) {
                        item.discard();
                     } else {
                        item.setItem(stack);
                     }

                     this.beginGatherOrder(owner, blueprint);
                     this.permanentizeOrder();
                     this.signalOrderAccepted();
                  }
               }
            }
         }
      }
   }

   private ItemEntity findBestDroppedOrder(ServerPlayer owner) {
      double range = Math.min(6.0, Math.max(3.0, (Double)cfg().parallelBodyBlueprintRange.get()));
      List<ItemEntity> items = new ArrayList<>();
      AABB ownerBox = owner.getBoundingBox().inflate(range);
      AABB cloneBox = this.getBoundingBox().inflate(range);
      items.addAll(owner.level().getEntitiesOfClass(ItemEntity.class, ownerBox, item -> item.isAlive() && !item.getItem().isEmpty()));
      if (cloneBox.distanceToSqr(owner.position()) > range * range) {
         items.addAll(this.level().getEntitiesOfClass(ItemEntity.class, cloneBox, item -> item.isAlive() && !item.getItem().isEmpty()));
      }

      return items.stream().min(Comparator.comparingDouble(item -> Math.min(item.distanceToSqr(this), item.distanceToSqr(owner)))).orElse(null);
   }

   private boolean tryAcceptGlobalCommandDrop(ServerPlayer owner, ItemEntity item, ItemStack stack) {
      if (!stack.isEmpty() && stack.is(Items.NAME_TAG) && stack.has(DataComponents.CUSTOM_NAME)) {
         String text = stack.getHoverName().getString();
         ArsWeissCloneOrderParser.Order order = ArsWeissCloneOrderParser.parse(text);
         String mode = order.mode();
         String query = this.cleanOrderQuery(order.query());
         String lower = this.normalizeSearchText(text);
         boolean all = lower.contains(" all") || lower.endsWith(" all") || lower.contains(" every") || lower.contains(" workers") || lower.contains(" clones");
         boolean globalMode = mode.equals("dismiss")
            || mode.equals("return")
            || mode.equals("send")
            || mode.equals("status")
            || mode.equals("idle")
            || mode.equals("guard")
            || mode.equals("hunt")
            || mode.equals("army")
            || mode.equals("command")
            || mode.equals("locate")
            || mode.equals("gather")
            || mode.equals("mine")
            || mode.equals("loot")
            || lower.contains("status")
            || lower.contains("report");
         if (globalMode && all) {
            String command = !mode.equals("status") && !lower.contains("status") && !lower.contains("report") ? mode : "status";
            ArsWeissCloneCommandHub.Command published = ArsWeissCloneCommandHub.publishAndGet(owner.getUUID(), command, query, order.amount());
            int affected = this.applyBroadcastToLoadedOwnedClones(owner, published);
            stack.shrink(1);
            if (stack.isEmpty()) {
               item.discard();
            } else {
               item.setItem(stack);
            }

            owner.displayClientMessage(Component.literal("Ars Weiss command broadcast: " + command + " all (" + affected + " loaded clones)"), false);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void beginGatherOrder(ServerPlayer owner, ItemStack blueprint) {
      this.depositCargoToOwner(owner, true);
      int requestedAmount = Math.max(1, blueprint.getCount());
      this.finalRequestedStack = blueprint.copyWithCount(requestedAmount);
      this.requestedStack = blueprint.copyWithCount(Math.min(requestedAmount, Math.max(1, blueprint.getMaxStackSize())));
      this.workQueue.clear();
      this.hasTargetBlock = false;
      this.returning = false;
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
      this.huntTargetName = "";
      this.huntQuery = "";
      this.armyQuery = "";
      this.armyDelay = 0;
      this.huntTargetId = null;
      this.hasSendTarget = false;
      List<ItemStack> planned = this.expandCraftingNeeds(owner, blueprint.copyWithCount(requestedAmount), 0, new HashSet<>());
      if (planned.isEmpty()) {
         this.workQueue.add(blueprint.copyWithCount(requestedAmount));
      } else {
         for (ItemStack stack : planned) {
            this.addNeededStack(stack);
         }
      }

      ArsWeissCloneWorkerBrain.sortPlannedMaterials(this.workQueue);
      this.requestedStack = this.currentWantedStack().isEmpty() ? blueprint.copyWithCount(1) : this.currentWantedStack().copyWithCount(1);
   }

   private void beginMineOrder(ServerPlayer owner, ItemStack blueprint) {
      this.depositCargoToOwner(owner, true);
      int requestedAmount = Math.max(1, blueprint.getCount());
      this.finalRequestedStack = blueprint.copyWithCount(requestedAmount);
      this.requestedStack = blueprint.copyWithCount(1);
      this.workQueue.clear();
      this.workQueue.add(blueprint.copyWithCount(requestedAmount));
      this.hasTargetBlock = false;
      this.returning = false;
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
      this.huntTargetName = "";
      this.huntQuery = "";
      this.armyQuery = "";
      this.armyDelay = 0;
      this.huntTargetId = null;
      this.hasSendTarget = false;
      this.lastCommandMode = "mine";
      this.lastCommandQuery = this.stackText(blueprint);
      this.lastCommandAmount = requestedAmount;
   }

   private List<ItemStack> expandCraftingNeeds(ServerPlayer owner, ItemStack target, int depth, Set<String> seen) {
      List<ItemStack> direct = new ArrayList<>();
      if (target.isEmpty()) {
         return direct;
      }

      String key = this.stackKey(target);
      if (depth < 5 && !seen.contains(key) && !this.shouldKeepAsRawMaterial(target)) {
         seen.add(key);
         Optional<RecipeHolder<CraftingRecipe>> optional = this.findCraftingRecipeFor(owner, target);
         if (!optional.isEmpty()) {
            CraftingRecipe recipe = (CraftingRecipe)optional.get().value();
            ItemStack result = recipe.getResultItem(owner.registryAccess());
            int multiplier = Math.max(1, (int)Math.ceil((double)target.getCount() / Math.max(1, result.getCount())));

            for (Ingredient ingredient : recipe.getIngredients()) {
               if (!ingredient.isEmpty()) {
                  ItemStack[] options = ingredient.getItems();
                  if (options.length != 0) {
                     ItemStack chosen = this.chooseIngredientOption(options);
                     if (!chosen.isEmpty()) {
                        chosen = chosen.copyWithCount(Math.max(1, chosen.getCount()) * multiplier);
                        direct.addAll(this.expandCraftingNeeds(owner, chosen, depth + 1, seen));
                     }
                  }
               }
            }

            seen.remove(key);
            return direct;
         } else {
            List<ItemStack> tensura = ArsWeissCloneTensuraRecipeSupport.expandTensuraRecipe(owner, target);
            if (tensura.isEmpty()) {
               direct.add(target.copy());
               return direct;
            }

            for (ItemStack stack : tensura) {
               direct.addAll(this.expandCraftingNeeds(owner, stack, depth + 1, seen));
            }

            seen.remove(key);
            return direct;
         }
      } else {
         direct.add(target.copy());
         return direct;
      }
   }

   private Optional<RecipeHolder<CraftingRecipe>> findCraftingRecipeFor(ServerPlayer owner, ItemStack target) {
      List<RecipeHolder<CraftingRecipe>> recipes = owner.getServer().getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
      return recipes.stream()
         .filter(holder -> ItemStack.isSameItem(((CraftingRecipe)holder.value()).getResultItem(owner.registryAccess()), target))
         .min(Comparator.comparingInt(holder -> this.recipeComplexity((CraftingRecipe)holder.value())));
   }

   private int recipeComplexity(CraftingRecipe recipe) {
      int count = 0;

      for (Ingredient ingredient : recipe.getIngredients()) {
         if (!ingredient.isEmpty()) {
            count++;
         }
      }

      return count;
   }

   private ItemStack chooseIngredientOption(ItemStack[] options) {
      for (ItemStack option : options) {
         if (!option.isEmpty() && option.getItem() != Items.AIR) {
            return option;
         }
      }

      return ItemStack.EMPTY;
   }

   private boolean shouldKeepAsRawMaterial(ItemStack stack) {
      String text = this.stackText(stack);
      return text.contains("ore")
         || text.contains("raw_")
         || text.contains("ingot")
         || text.contains("gem")
         || text.contains("diamond")
         || text.contains("emerald")
         || text.contains("coal")
         || text.contains("quartz")
         || text.contains("redstone")
         || text.contains("lapis")
         || text.contains("leather")
         || text.contains("string")
         || text.contains("bone")
         || text.contains("feather")
         || text.contains("gunpowder")
         || text.contains("slime")
         || text.contains("blaze_rod")
         || text.contains("ender_pearl")
         || ArsWeissCloneTensuraSupport.isRawTensuraMaterialText(text);
   }

   private void addNeededStack(ItemStack stack) {
      if (!stack.isEmpty()) {
         for (ItemStack needed : this.workQueue) {
            if (ItemStack.isSameItemSameComponents(needed, stack)) {
               needed.grow(stack.getCount());
               return;
            }
         }

         this.workQueue.add(stack.copy());
      }
   }

   private boolean tryAcceptHuntBlueprint(ServerPlayer owner, ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      }

      String rawName = stack.getHoverName().getString().trim();
      if (rawName.isBlank()) {
         return false;
      }

      boolean named = stack.has(DataComponents.CUSTOM_NAME);
      boolean namedTag = stack.is(Items.NAME_TAG) && named;
      boolean explicit = this.isTextCommand(rawName);
      return !namedTag && !explicit ? false : this.acceptTextOrder(owner, rawName);
   }

   private boolean isTextCommand(String raw) {
      String lower = raw.toLowerCase().trim();
      return lower.startsWith("hunt:")
         || lower.startsWith("kill:")
         || lower.startsWith("attack:")
         || lower.startsWith("target:")
         || lower.startsWith("gather:")
         || lower.startsWith("collect:")
         || lower.startsWith("get:")
         || lower.startsWith("mine:")
         || lower.startsWith("farm:")
         || lower.startsWith("loot:")
         || lower.startsWith("find:")
         || lower.startsWith("send:")
         || lower.startsWith("goto:")
         || lower.startsWith("move:")
         || lower.startsWith("command:")
         || lower.startsWith("commander:")
         || lower.startsWith("army:")
         || lower.startsWith("tame:")
         || lower.startsWith("recruit:")
         || lower.startsWith("subordinate:")
         || lower.startsWith("dismiss:")
         || lower.startsWith("despawn:")
         || lower.startsWith("return:")
         || lower.startsWith("recall:")
         || lower.startsWith("guard:")
         || lower.startsWith("defend:")
         || lower.startsWith("protect:")
         || lower.startsWith("escort:")
         || lower.startsWith("idle:")
         || lower.startsWith("wait:")
         || lower.startsWith("locate:")
         || lower.startsWith("structure:");
   }

   private boolean acceptTextOrder(ServerPlayer owner, String raw) {
      String rawClean = this.normalizeSearchText(raw);
      if (!rawClean.equals("commander") && !rawClean.equals("command leader") && !rawClean.equals("leader")) {
         ArsWeissCloneOrderParser.Order order = ArsWeissCloneOrderParser.parse(raw);
         if (order.query().isBlank()
            && !order.mode().equals("dismiss")
            && !order.mode().equals("return")
            && !order.mode().equals("status")
            && !order.mode().equals("command")) {
            return false;
         }

         String mode = order.mode();
         String query = mode.equals("command") ? order.query().trim() : this.cleanOrderQuery(order.query());
         if (!mode.equals("command")) {
            query = ArsWeissCloneLootIntel.itemAlias(query);
         }

         int amount = Math.max(1, order.amount());
         boolean broadcastAll = this.isBroadcastAll(rawClean, query);
         if (broadcastAll && !mode.equals("command")) {
            String broadcastMode = mode.isBlank() ? this.inferOrderMode(query) : mode;
            if (broadcastMode.isBlank()) {
               broadcastMode = "gather";
            }

            ArsWeissCloneCommandHub.Command published = ArsWeissCloneCommandHub.publishAndGet(owner.getUUID(), broadcastMode, query, amount);
            int affected = this.applyBroadcastToLoadedOwnedClones(owner, published);
            owner.displayClientMessage(Component.literal("Ars Weiss command broadcast: " + broadcastMode + " all (" + affected + " loaded clones)"), false);
            return true;
         } else {
            if (mode.isBlank()) {
               mode = this.inferOrderMode(query);
            }

            if ((mode.equals("gather") || mode.isBlank()) && this.looksLikeStructureSearch(query)) {
               mode = "locate";
            }

            if (mode.isBlank()) {
               ServerPlayer playerTarget = owner.getServer().getPlayerList().getPlayerByName(query);
               if (playerTarget != null && !playerTarget.getUUID().equals(owner.getUUID())) {
                  mode = "hunt";
               } else {
                  mode = "gather";
               }
            }

            if (mode.equals("command") && query.isBlank()) {
               this.commanderLeader = true;
               this.rememberCommand("command", "ready", 1);
               this.clearHostileOrdersOnly();
               owner.displayClientMessage(Component.literal("Ars Weiss commander registered. Use tags like: commander gather reset scroll 2"), true);
               return true;
            }

            if (mode.equals("command") && !this.commanderLeader) {
               owner.displayClientMessage(Component.literal("This clone is not the commander. First give it a nametag named: commander"), true);
               return false;
            }

            this.rememberCommand(mode, query, amount);
            if (mode.equals("command")) {
               return this.beginCommanderOrder(owner, query, amount);
            }

            if (mode.equals("dismiss")) {
               this.shatter(false);
               return true;
            }

            if (mode.equals("return")) {
               this.returning = true;
               this.clearHostileOrdersOnly();
               return true;
            }

            if (mode.equals("send")) {
               BlockPos destination = this.parseSendTarget(owner, query);
               if (destination == null) {
                  owner.displayClientMessage(Component.literal("Ars Weiss send needs coordinates: send x y z"), true);
                  return false;
               } else {
                  this.beginSendOrder(owner, destination);
                  return true;
               }
            } else {
               if (mode.equals("idle")) {
                  this.clearOrder();
                  this.followOwner(owner);
                  return true;
               }

               if (mode.equals("guard")) {
                  this.beginGuardOrder(owner, query.isBlank() ? "owner" : query);
                  return true;
               }

               if (mode.equals("status")) {
                  this.sendStatus(owner, "manual");
                  return true;
               }

               if (mode.equals("army")) {
                  this.beginArmyOrder(owner, query.isBlank() ? "all" : query);
                  return true;
               }

               if (mode.equals("hunt")) {
                  this.beginHuntOrder(owner, query);
                  return true;
               }

               if (mode.equals("locate")) {
                  this.beginLocateOrder(owner, query.isBlank() ? "tensura" : query);
                  return true;
               }

               ItemStack wanted = this.findItemStackByQuery(query);
               if (mode.equals("loot")) {
                  this.beginLootNameOrder(owner, query, amount);
                  return true;
               }

               if (!wanted.isEmpty()) {
                  wanted.setCount(amount);
                  if (mode.equals("mine")) {
                     this.beginMineOrder(owner, wanted);
                  } else {
                     this.beginGatherOrder(owner, wanted);
                  }

                  return true;
               } else if (mode.equals("mine")) {
                  owner.displayClientMessage(Component.literal("Ars Weiss mine needs a block or ore name it can match."), true);
                  return false;
               } else if (mode.equals("gather")) {
                  this.beginLootNameOrder(owner, query, amount);
                  return true;
               } else {
                  this.beginHuntOrder(owner, query);
                  return true;
               }
            }
         }
      } else {
         this.commanderLeader = true;
         this.rememberCommand("command", "ready", 1);
         this.clearHostileOrdersOnly();
         owner.displayClientMessage(Component.literal("Ars Weiss commander registered. Use tags like: commander gather reset scroll 2"), true);
         return true;
      }
   }

   private boolean looksLikeStructureSearch(String query) {
      String q = this.normalizeSearchText(query == null ? "" : query);
      return q.contains("structure")
         || q.contains("village")
         || q.contains("tower")
         || q.contains("dungeon")
         || q.contains("temple")
         || q.contains("fortress")
         || q.contains("bastion")
         || q.contains("ruins")
         || q.contains("nest")
         || q.contains("cave")
         || q.contains("library")
         || q.contains("dojo")
         || q.contains("smithy");
   }

   private boolean isBroadcastAll(String raw, String query) {
      String a = this.normalizeSearchText(raw == null ? "" : raw);
      String q = this.normalizeSearchText(query == null ? "" : query);
      return a.equals("all")
         || a.endsWith(" all")
         || a.contains(" all ")
         || a.contains(" every clone")
         || a.contains(" all clones")
         || a.contains(" workers")
         || a.contains(" clones")
         || q.equals("all")
         || q.equals("everyone")
         || q.equals("every clone")
         || q.equals("all clones")
         || q.equals("workers")
         || q.equals("clones");
   }

   private boolean isWordCommand(String word) {
      return !this.canonicalCommand(word).isBlank();
   }

   private String canonicalCommand(String word) {
      String lower = this.normalizeSearchText(word).replace(" ", "");
      if (this.fuzzyWord(lower, "send") || this.fuzzyWord(lower, "goto") || this.fuzzyWord(lower, "move") || this.fuzzyWord(lower, "travel")) {
         return "send";
      } else if (this.fuzzyWord(lower, "dismiss")
         || this.fuzzyWord(lower, "despawn")
         || this.fuzzyWord(lower, "remove")
         || this.fuzzyWord(lower, "delete")
         || this.fuzzyWord(lower, "shatter")) {
         return "dismiss";
      } else if (this.fuzzyWord(lower, "return") || this.fuzzyWord(lower, "recall") || this.fuzzyWord(lower, "come") || this.fuzzyWord(lower, "back")) {
         return "return";
      } else if (this.fuzzyWord(lower, "status") || this.fuzzyWord(lower, "report") || this.fuzzyWord(lower, "progress") || this.fuzzyWord(lower, "info")) {
         return "status";
      } else if (this.fuzzyWord(lower, "idle") || this.fuzzyWord(lower, "wait") || this.fuzzyWord(lower, "stop") || this.fuzzyWord(lower, "cancel")) {
         return "idle";
      } else if (this.fuzzyWord(lower, "guard")
         || this.fuzzyWord(lower, "defend")
         || this.fuzzyWord(lower, "protect")
         || this.fuzzyWord(lower, "escort")
         || this.fuzzyWord(lower, "patrol")) {
         return "guard";
      } else if (this.fuzzyWord(lower, "locate") || this.fuzzyWord(lower, "structure") || this.fuzzyWord(lower, "village") || this.fuzzyWord(lower, "dungeon")) {
         return "locate";
      } else if (this.fuzzyWord(lower, "loot") || this.fuzzyWord(lower, "steal") || this.fuzzyWord(lower, "chest")) {
         return "loot";
      } else if (this.fuzzyWord(lower, "mine") || this.fuzzyWord(lower, "dig") || this.fuzzyWord(lower, "excavate") || this.fuzzyWord(lower, "quarry")) {
         return "mine";
      } else if (this.fuzzyWord(lower, "hunt")
         || this.fuzzyWord(lower, "kill")
         || this.fuzzyWord(lower, "attack")
         || this.fuzzyWord(lower, "target")
         || this.fuzzyWord(lower, "fight")) {
         return "hunt";
      } else if (this.fuzzyWord(lower, "command") || this.fuzzyWord(lower, "commander") || this.fuzzyWord(lower, "delegate") || this.fuzzyWord(lower, "leader")
         )
       {
         return "command";
      } else if (this.fuzzyWord(lower, "army")
         || this.fuzzyWord(lower, "tame")
         || this.fuzzyWord(lower, "recruit")
         || this.fuzzyWord(lower, "subordinate")
         || this.fuzzyWord(lower, "dominate")) {
         return "army";
      } else {
         return !this.fuzzyWord(lower, "gather")
               && !this.fuzzyWord(lower, "collect")
               && !this.fuzzyWord(lower, "get")
               && !this.fuzzyWord(lower, "farm")
               && !this.fuzzyWord(lower, "find")
               && !this.fuzzyWord(lower, "bring")
            ? ""
            : "gather";
      }
   }

   private String inferOrderMode(String query) {
      String cleaned = this.normalizeSearchText(query);

      for (String token : cleaned.split(" ")) {
         String command = this.canonicalCommand(token);
         if (!command.isBlank()) {
            return command;
         }
      }

      return "";
   }

   private String cleanOrderQuery(String query) {
      String cleaned = query == null ? "" : query.trim();
      cleaned = cleaned.replaceAll(
         "(?i)\\b(please|pls|plz|go|and|then|for|me|some|a|an|the|to|from|nearby|around|find|bring|grab|collect|get|gather|mine|farm|loot|hunt|kill|attack|target|guard|defend|protect|escort|patrol)\\b",
         " "
      );
      return cleaned.replaceAll("\\s+", " ").trim();
   }

   private void rememberCommand(String mode, String query, int amount) {
      this.lastCommandMode = mode == null ? "" : mode;
      this.lastCommandQuery = query == null ? "" : query;
      this.lastCommandAmount = Math.max(1, amount);
      this.commanderLeader = mode != null && mode.equals("command");
   }

   private boolean beginCommanderOrder(ServerPlayer owner, String query, int amount) {
      if (owner == null) {
         return false;
      }

      ArsWeissCloneOrderParser.Order nested = ArsWeissCloneOrderParser.parse(query);
      String mode = nested.mode();
      String nestedQuery = this.cleanOrderQuery(nested.query());
      int nestedAmount = Math.max(1, nested.amount() > 1 ? nested.amount() : amount);
      if (mode.isBlank()) {
         mode = this.inferOrderMode(query);
         nestedQuery = this.cleanOrderQuery(query);
      }

      if (mode.isBlank()) {
         mode = "gather";
      }

      if (mode.equals("command")) {
         mode = "gather";
      }

      List<ArsWeissCloneEntity> workers = this.nearbyOwnedWorkers(owner, 96.0, 12);
      if (workers.isEmpty()) {
         workers = List.of(this);
      }

      String cleanNested = ArsWeissCloneLootIntel.itemAlias(nestedQuery);
      if (!mode.equals("status") && !mode.equals("return") && !mode.equals("idle") && !mode.equals("dismiss")) {
         if (!mode.equals("gather") && !mode.equals("mine") && !mode.equals("loot")) {
            int issued = 0;

            for (ArsWeissCloneEntity clone : workers) {
               if (mode.equals("hunt")) {
                  clone.beginHuntOrder(owner, cleanNested);
               } else if (mode.equals("guard")) {
                  clone.beginGuardOrder(owner, cleanNested.isBlank() ? "owner" : cleanNested);
               } else if (mode.equals("army")) {
                  clone.beginArmyOrder(owner, cleanNested.isBlank() ? "all" : cleanNested);
               } else if (mode.equals("locate")) {
                  clone.beginLocateOrder(owner, cleanNested.isBlank() ? "tensura" : cleanNested);
               } else {
                  if (!mode.equals("send")) {
                     continue;
                  }

                  BlockPos destination = this.parseSendTarget(owner, nested.query());
                  if (destination != null) {
                     clone.beginSendOrder(owner, destination);
                  }
               }

               clone.rememberCommand(mode, cleanNested, nestedAmount);
               clone.permanentizeOrder();
               issued++;
            }

            this.sendStatus(owner, "commander delegated " + mode + " to " + issued + " clones");
            return issued > 0;
         } else {
            ItemStack wanted = this.findItemStackByQuery(cleanNested);
            if (!wanted.isEmpty() && mode.equals("mine")) {
               int issued = 0;

               for (ArsWeissCloneEntity clone : workers) {
                  ItemStack split = wanted.copyWithCount(Math.max(1, (int)Math.ceil((double)nestedAmount / Math.max(1, workers.size()))));
                  clone.beginMineOrder(owner, split);
                  clone.rememberCommand("mine", cleanNested, split.getCount());
                  clone.permanentizeOrder();
                  issued++;
               }

               this.sendStatus(owner, "commander delegated mine to " + issued + " clones");
               return true;
            } else if (!wanted.isEmpty() && !mode.equals("loot")) {
               wanted.setCount(nestedAmount);
               List<ItemStack> planned = this.expandCraftingNeeds(owner, wanted.copy(), 0, new HashSet<>());
               if (planned.isEmpty()) {
                  planned.add(wanted.copy());
               }

               ArsWeissCloneWorkerBrain.sortPlannedMaterials(planned);
               this.distributeGatherPlan(owner, workers, planned);
               this.sendStatus(owner, "commander delegated gather to " + workers.size() + " clones");
               return true;
            } else {
               int i = 0;

               for (ArsWeissCloneEntity clone : workers) {
                  if (i++ % 2 == 0) {
                     clone.beginLocateOrder(owner, cleanNested);
                  } else {
                     clone.beginLootNameOrder(owner, cleanNested, nestedAmount);
                  }

                  clone.rememberCommand("loot", cleanNested, nestedAmount);
                  clone.permanentizeOrder();
               }

               this.sendStatus(owner, "commander delegated loot search");
               return true;
            }
         }
      } else {
         ArsWeissCloneCommandHub.Command published = ArsWeissCloneCommandHub.publishAndGet(owner.getUUID(), mode, cleanNested, nestedAmount);
         int affected = this.applyBroadcastToLoadedOwnedClones(owner, published);
         owner.displayClientMessage(Component.literal("Ars Weiss commander broadcast: " + mode + " all (" + affected + " loaded clones)"), false);
         return true;
      }
   }

   private List<ArsWeissCloneEntity> nearbyOwnedWorkers(ServerPlayer owner, double radius, int cap) {
      List<ArsWeissCloneEntity> out = new ArrayList<>();
      if (owner == null) {
         return out;
      }

      UUID ownerId = owner.getUUID();
      AABB box = this.getBoundingBox().inflate(radius);
      List<ArsWeissCloneEntity> clones = this.level()
         .getEntitiesOfClass(
            ArsWeissCloneEntity.class,
            box,
            clonex -> clonex.isAlive() && clonex.isElfariaWorkerClone() && !clonex.isAnchorBody() && ownerId.equals(clonex.getOwnerUUIDValue())
         );
      clones.sort(Comparator.comparingDouble(clonex -> clonex.distanceToSqr(this)));

      for (ArsWeissCloneEntity clone : clones) {
         out.add(clone);
         if (out.size() >= Math.max(1, cap)) {
            break;
         }
      }

      if (!out.contains(this)) {
         out.add(0, this);
      }

      return out;
   }

   private void distributeGatherPlan(ServerPlayer owner, List<ArsWeissCloneEntity> workers, List<ItemStack> planned) {
      if (!workers.isEmpty() && !planned.isEmpty()) {
         List<ItemStack>[] split = new List[workers.size()];

         for (int i = 0; i < split.length; i++) {
            split[i] = new ArrayList<>();
         }

         int cursor = 0;

         for (ItemStack stack : planned) {
            if (!stack.isEmpty()) {
               split[cursor % split.length].add(stack.copy());
               cursor++;
            }
         }

         for (int i = 0; i < workers.size(); i++) {
            ArsWeissCloneEntity clone = workers.get(i);
            if (split[i].isEmpty()) {
               clone.beginGuardOrder(owner, "owner");
               clone.rememberCommand("guard", "owner", 1);
            } else {
               clone.beginDirectGatherOrder(owner, split[i], "commander gather");
               clone.rememberCommand("gather", "commander split", 1);
            }

            clone.permanentizeOrder();
         }
      }
   }

   private void beginDirectGatherOrder(ServerPlayer owner, List<ItemStack> planned, String source) {
      this.depositCargoToOwner(owner, true);
      this.finalRequestedStack = ItemStack.EMPTY;
      this.requestedStack = ItemStack.EMPTY;
      this.workQueue.clear();
      this.hasTargetBlock = false;
      this.returning = false;
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
      this.huntTargetName = "";
      this.huntQuery = "";
      this.armyQuery = "";
      this.armyDelay = 0;
      this.huntTargetId = null;
      this.hasSendTarget = false;

      for (ItemStack stack : planned) {
         this.addNeededStack(stack);
      }

      ArsWeissCloneWorkerBrain.sortPlannedMaterials(this.workQueue);
      this.requestedStack = this.currentWantedStack().isEmpty() ? ItemStack.EMPTY : this.currentWantedStack().copyWithCount(1);
      this.lastCommandMode = "gather";
      this.lastCommandQuery = source == null ? "" : source;
      this.lastCommandAmount = this.totalNeededCount();
   }

   private int totalNeededCount() {
      int total = 0;

      for (ItemStack stack : this.workQueue) {
         if (!stack.isEmpty()) {
            total += stack.getCount();
         }
      }

      return total;
   }

   private void beginSendOrder(ServerPlayer owner, BlockPos destination) {
      this.depositCargoToOwner(owner, true);
      this.sendTarget = destination.immutable();
      this.hasSendTarget = true;
      this.armyQuery = "";
      this.armyDelay = 0;
      this.huntTargetName = "";
      this.huntQuery = "";
      this.huntTargetId = null;
      this.requestedStack = ItemStack.EMPTY;
      this.finalRequestedStack = ItemStack.EMPTY;
      this.workQueue.clear();
      this.targetBlock = BlockPos.ZERO;
      this.hasTargetBlock = false;
      this.returning = false;
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
      this.stuckTicks = 0;
   }

   private BlockPos parseSendTarget(ServerPlayer owner, String query) {
      if (owner != null && query != null) {
         String cleaned = query.replace(',', ' ').replaceAll("\\s+", " ").trim();
         if (cleaned.isBlank()) {
            return null;
         }

         String[] parts = cleaned.split(" ");
         List<String> coords = new ArrayList<>();

         for (String part : parts) {
            if (part.equals("~") || part.matches("~?-?\\d{1,9}(?:\\.\\d+)?")) {
               coords.add(part);
               if (coords.size() == 3) {
                  break;
               }
            }
         }

         if (coords.size() < 3) {
            return null;
         } else {
            Integer x = this.parseCoordinate(coords.get(0), owner.getX());
            Integer y = this.parseCoordinate(coords.get(1), owner.getY());
            Integer z = this.parseCoordinate(coords.get(2), owner.getZ());
            if (x != null && y != null && z != null) {
               int clampedY = Math.max(owner.level().getMinBuildHeight(), Math.min(owner.level().getMaxBuildHeight() - 1, y));
               return new BlockPos(x, clampedY, z);
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private Integer parseCoordinate(String token, double relative) {
      try {
         if (token.equals("~")) {
            return (int)Math.floor(relative);
         } else if (token.startsWith("~")) {
            String offset = token.substring(1);
            double delta = offset.isBlank() ? 0.0 : Double.parseDouble(offset);
            return (int)Math.floor(relative + delta);
         } else {
            return (int)Math.floor(Double.parseDouble(token));
         }
      } catch (Exception ignored) {
         return null;
      }
   }

   private void tickSendOrder(ServerPlayer owner) {
      if (this.level() instanceof ServerLevel level) {
         BlockPos goal = this.sendTarget == null ? BlockPos.ZERO : this.sendTarget;
         this.keepTaskChunkLoaded();
         double arrive = 3.0;
         double dist = this.distanceToSqr(Vec3.atCenterOf(goal));
         if (dist <= arrive * arrive) {
            this.getNavigation().stop();
            this.setDeltaMovement(Vec3.ZERO);
            this.hurtMarked = true;
            this.stuckTicks = 0;
         } else {
            BlockPos stand = ArsWeissClonePathing.findSafeStandNear(level, goal, 8, 8, 16);
            BlockPos moveGoal = stand == null ? goal : stand;
            this.moveToBlockThrottled(moveGoal);
            this.assistExtremeTravel(owner, moveGoal, false);
            this.recoverIfStuck(moveGoal);
         }
      }
   }

   private void moveToBlockThrottled(BlockPos pos) {
      if (pos != null) {
         if (this.getNavigation().isDone() || this.tickCount % 10 == 0) {
            this.getNavigation().moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, this.workerMoveSpeed());
         }
      }
   }

   private void beginHuntOrder(ServerPlayer owner, String query) {
      this.depositCargoToOwner(owner, true);
      query = query == null ? "" : query.trim();
      ServerPlayer target = owner.getServer().getPlayerList().getPlayerByName(query);
      if (target == null) {
         String normalizedQuery = this.normalizeSearchText(query);

         for (ServerPlayer player : owner.getServer().getPlayerList().getPlayers()) {
            if (player != owner && player.isAlive()) {
               String name = this.normalizeSearchText(player.getGameProfile().getName());
               if (!normalizedQuery.isBlank() && (name.equals(normalizedQuery) || name.contains(normalizedQuery) || normalizedQuery.contains(name))) {
                  target = player;
                  break;
               }
            }
         }
      }

      this.huntTargetName = target == null ? "" : target.getGameProfile().getName();
      this.huntTargetId = target != null && !target.getUUID().equals(owner.getUUID()) ? target.getUUID() : null;
      this.huntQuery = this.huntTargetId == null ? this.normalizeSearchText(query) : "";
      this.armyQuery = "";
      this.armyDelay = 0;
      this.requestedStack = ItemStack.EMPTY;
      this.finalRequestedStack = ItemStack.EMPTY;
      this.workQueue.clear();
      this.hasTargetBlock = false;
      this.returning = false;
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
   }

   private ItemStack findItemStackByQuery(String query) {
      String normalized = this.normalizeSearchText(ArsWeissCloneLootIntel.itemAlias(query));
      if (normalized.isBlank()) {
         return ItemStack.EMPTY;
      }

      Item best = null;
      int bestScore = 0;

      for (ResourceLocation id : BuiltInRegistries.ITEM.keySet()) {
         Item item = (Item)BuiltInRegistries.ITEM.get(id);
         if (item != Items.AIR) {
            String candidate = this.normalizeSearchText(id.toString() + " " + id.getPath() + " " + item.getDescriptionId());
            int score = this.textMatchScore(candidate, normalized) + ArsWeissCloneTensuraSupport.itemScoreBoost(id, candidate, normalized);
            if (score > bestScore) {
               bestScore = score;
               best = item;
            }
         }
      }

      return best != null && bestScore >= 20 ? new ItemStack(best) : ItemStack.EMPTY;
   }

   private int textMatchScore(String candidate, String query) {
      candidate = this.normalizeSearchText(candidate);
      query = this.normalizeSearchText(query);
      if (candidate.equals(query)) {
         return 1200;
      }

      if (!candidate.endsWith(" " + query) && !candidate.endsWith(":" + query) && !candidate.contains(" " + query + " ")) {
         String[] parts = query.split(" ");
         String[] words = candidate.split(" ");
         int score = 0;
         int useful = 0;

         for (String part : parts) {
            if (part.length() >= 2) {
               useful++;
               int best = 0;
               if (candidate.contains(part)) {
                  best = part.length() >= 4 ? 90 : 40;
               } else {
                  for (String word : words) {
                     int similarity = this.fuzzySimilarity(part, word);
                     if (similarity > best) {
                        best = similarity;
                     }
                  }
               }

               if (best < 45 && part.length() >= 4) {
                  return 0;
               }

               score += best;
            }
         }

         return useful == 0 ? 0 : score + useful * 10;
      } else {
         return 800;
      }
   }

   private boolean fuzzyWord(String input, String target) {
      if (input.equals(target)) {
         return true;
      } else {
         return input.length() >= 3 && target.contains(input) ? true : this.levenshtein(input, target) <= Math.max(1, target.length() / 3);
      }
   }

   private int fuzzySimilarity(String a, String b) {
      if (a.equals(b)) {
         return 100;
      }

      if (a.length() >= 3 && b.contains(a)) {
         return 80;
      }

      if (b.length() >= 3 && a.contains(b)) {
         return 70;
      }

      int max = Math.max(a.length(), b.length());
      if (max <= 1) {
         return 0;
      }

      int distance = this.levenshtein(a, b);
      int score = (int)Math.round(100.0 * (1.0 - (double)distance / max));
      return Math.max(0, score);
   }

   private int levenshtein(String a, String b) {
      if (a != null && b != null) {
         int[] prev = new int[b.length() + 1];
         int[] curr = new int[b.length() + 1];
         int j = 0;

         while (j <= b.length()) {
            prev[j] = j++;
         }

         for (int i = 1; i <= a.length(); i++) {
            curr[0] = i;

            for (int jx = 1; jx <= b.length(); jx++) {
               int cost = a.charAt(i - 1) == b.charAt(jx - 1) ? 0 : 1;
               curr[jx] = Math.min(Math.min(curr[jx - 1] + 1, prev[jx] + 1), prev[jx - 1] + cost);
            }

            int[] tmp = prev;
            prev = curr;
            curr = tmp;
         }

         return prev[b.length()];
      } else {
         return 999;
      }
   }

   private String normalizeSearchText(String value) {
      return value.toLowerCase().replace("minecraft:", "").replace('_', ' ').replace('-', ' ').replaceAll("[^a-z0-9: ]", " ").replaceAll("\\s+", " ").trim();
   }

   private boolean isOrderComplete() {
      if (!this.cargoStack.isEmpty()) {
         this.addCargoStack(this.cargoStack);
         this.cargoStack = ItemStack.EMPTY;
      }

      for (ItemStack needed : this.workQueue) {
         if (this.countCargo(needed) < needed.getCount()) {
            return false;
         }
      }

      return !this.workQueue.isEmpty() || !this.cargoStacks.isEmpty();
   }

   private boolean isCurrentChecklistStepComplete() {
      if (!this.cargoStack.isEmpty()) {
         this.addCargoStack(this.cargoStack);
         this.cargoStack = ItemStack.EMPTY;
      }

      ItemStack target = this.currentWantedStackRaw();
      return !target.isEmpty() && this.countCargo(target) >= target.getCount();
   }

   private ItemStack currentWantedStackRaw() {
      for (ItemStack needed : this.workQueue) {
         if (!needed.isEmpty()) {
            return needed;
         }
      }

      return ItemStack.EMPTY;
   }

   private ItemStack currentWantedStack() {
      for (ItemStack needed : this.workQueue) {
         if (!needed.isEmpty()) {
            int have = this.countCargo(needed);
            if (have < needed.getCount()) {
               return needed.copyWithCount(needed.getCount() - have);
            }
         }
      }

      return !this.requestedStack.isEmpty() ? this.requestedStack.copyWithCount(1) : ItemStack.EMPTY;
   }

   private int countCargo(ItemStack target) {
      int count = 0;
      if (!this.cargoStack.isEmpty() && ItemStack.isSameItemSameComponents(this.cargoStack, target)) {
         count += this.cargoStack.getCount();
      }

      for (ItemStack stack : this.cargoStacks) {
         if (ItemStack.isSameItemSameComponents(stack, target)) {
            count += stack.getCount();
         }
      }

      return count;
   }

   private int totalCargoCount() {
      int total = this.cargoStack.isEmpty() ? 0 : this.cargoStack.getCount();

      for (ItemStack stack : this.cargoStacks) {
         total += stack.getCount();
      }

      return total;
   }

   private boolean isCarryFull() {
      return this.totalCargoCount() >= Math.max(1, (Integer)cfg().parallelBodyWorkerCarryLimit.get());
   }

   private void compactCargo() {
      if (!this.cargoStack.isEmpty()) {
         int added = this.addCargoStack(this.cargoStack);
         if (added >= this.cargoStack.getCount()) {
            this.cargoStack = ItemStack.EMPTY;
         } else if (added > 0) {
            this.cargoStack.shrink(added);
         }
      }

      this.cargoStacks.removeIf(ItemStack::isEmpty);
   }

   private int addCargoStack(ItemStack stack) {
      if (stack.isEmpty()) {
         return 0;
      }

      int limit = Math.max(1, (Integer)cfg().parallelBodyWorkerCarryLimit.get());
      int room = limit - this.totalCargoCount();
      if (room <= 0) {
         return 0;
      }

      int amount = Math.min(stack.getCount(), room);
      ItemStack adding = stack.copyWithCount(amount);

      for (ItemStack cargo : this.cargoStacks) {
         if (ItemStack.isSameItemSameComponents(cargo, adding)) {
            cargo.grow(amount);
            return amount;
         }
      }

      this.cargoStacks.add(adding);
      return amount;
   }

   private boolean moveToNearbyUsefulDrop(ServerPlayer owner, ItemStack target) {
      if (this.dropScanCooldown > 0) {
         this.dropScanCooldown--;
         return false;
      }

      this.dropScanCooldown = 12 + this.scanPhase(8);
      AABB box = this.getBoundingBox().inflate(Math.max(8.0, (Double)cfg().parallelBodyWorkerPickupRadius.get() * 2.0));
      List<ItemEntity> matches = this.level().getEntitiesOfClass(ItemEntity.class, box, itemx -> itemx.isAlive() && this.isUsefulDrop(itemx.getItem()));
      Optional<ItemEntity> nearest = matches.stream().min(Comparator.comparingDouble(itemx -> itemx.distanceToSqr(this)));
      if (nearest.isEmpty()) {
         return false;
      }

      ItemEntity item = nearest.get();
      this.rememberHive(owner, "drop", ArsWeissCloneHiveMemory.itemQuery(item.getItem()), item.blockPosition(), 80);
      this.getNavigation().moveTo(item, this.workerMoveSpeed());
      this.recoverIfStuck(item.blockPosition());
      if (this.distanceToSqr(item) <= 6.25) {
         this.takeItemEntity(item);
      }

      return true;
   }

   private void collectUsefulDrops(ServerPlayer owner) {
      if (this.tickCount % 5 == this.scanPhase(5)) {
         AABB box = this.getBoundingBox().inflate(Math.max(4.0, (Double)cfg().parallelBodyWorkerPickupRadius.get()));

         for (ItemEntity item : this.level().getEntitiesOfClass(ItemEntity.class, box, e -> e.isAlive() && this.isUsefulDrop(e.getItem()))) {
            this.takeItemEntity(item);
            if (this.isCarryFull()) {
               return;
            }
         }
      }
   }

   private boolean isUsefulDrop(ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      }

      if (this.isCommandNameTagDrop(stack)) {
         return false;
      }

      if (this.isLootOrder()) {
         ItemStack currentLoot = this.currentWantedStack();
         if (!currentLoot.isEmpty() && this.matchesRequestedItem(stack, currentLoot)) {
            return true;
         }

         if (!this.finalRequestedStack.isEmpty() && this.matchesRequestedItem(stack, this.finalRequestedStack)) {
            return true;
         }

         for (ItemStack needed : this.workQueue) {
            if (!needed.isEmpty() && this.matchesRequestedItem(stack, needed)) {
               return true;
            }
         }

         return false;
      } else {
         ItemStack current = this.currentWantedStack();
         if (!current.isEmpty() && this.matchesRequestedItem(stack, current)) {
            return true;
         }

         if (!this.requestedStack.isEmpty() && this.matchesRequestedItem(stack, this.requestedStack)) {
            return true;
         }

         if (!this.finalRequestedStack.isEmpty() && this.matchesRequestedItem(stack, this.finalRequestedStack)) {
            return true;
         }

         for (ItemStack needed : this.workQueue) {
            if (!needed.isEmpty() && this.matchesRequestedItem(stack, needed)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isCommandNameTagDrop(ItemStack stack) {
      return !stack.isEmpty() && stack.is(Items.NAME_TAG) && stack.has(DataComponents.CUSTOM_NAME)
         ? this.isTextCommand(stack.getHoverName().getString()) || ArsWeissCloneOrderParser.parse(stack.getHoverName().getString()).mode().length() > 0
         : false;
   }

   private boolean matchesRequestedItem(ItemStack stack, ItemStack wanted) {
      if (!stack.isEmpty() && !wanted.isEmpty()) {
         if (!ItemStack.isSameItemSameComponents(stack, wanted) && !ItemStack.isSameItem(stack, wanted)) {
            String want = this.stackText(wanted);
            String got = this.stackText(stack);

            for (String part : want.split("[ _:-]+")) {
               if (part.length() >= 3 && got.contains(part)) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private void takeItemEntity(ItemEntity item) {
      ItemStack stack = item.getItem();
      if (stack.isEmpty()) {
         item.discard();
      } else {
         int limit = Math.max(1, (Integer)cfg().parallelBodyWorkerCarryLimit.get());
         int room = limit - this.totalCargoCount();
         if (room > 0) {
            int amount = Math.min(stack.getCount(), room);
            int added = this.addCargoStack(stack.copyWithCount(amount));
            if (added > 0) {
               stack.shrink(added);
               if (stack.isEmpty()) {
                  item.discard();
               } else {
                  item.setItem(stack);
               }

               this.level().playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.35F, 1.4F);
            }
         }
      }
   }

   private boolean moveToNearbyUsefulContainer(ServerPlayer owner, ItemStack target) {
      if (!(this.level() instanceof ServerLevel level)) {
         return false;
      } else {
         if (this.containerScanCooldown > 0) {
            this.containerScanCooldown--;
            return false;
         }

         if (!this.canUseScanBudget(owner, 1)) {
            return false;
         }

         if (!ArsWeissCloneSearchGovernor.allow(
            owner.getUUID(), this.level().dimension().location().toString(), "container", this.level().getGameTime(), 480, 1
         )) {
            return false;
         }

         this.containerScanCooldown = 220 + this.scanPhase(140);
         int radius = this.isLootOrder() ? 4 : 3;
         BlockPos base = this.blockPosition();
         BlockPos best = null;
         int bestSlot = -1;
         double bestDist = Double.MAX_VALUE;

         for (int y = -2; y <= 2; y++) {
            for (int x = -radius; x <= radius; x++) {
               for (int z = -radius; z <= radius; z++) {
                  if (x * x + z * z <= radius * radius) {
                     BlockPos pos = base.offset(x, y, z);
                     if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof Container container) {
                        int slot = this.findContainerSlot(container, target);
                        if (slot < 0 && this.isLootOrder()) {
                           slot = this.findAnyContainerSlot(container);
                        }

                        if (slot >= 0) {
                           BlockState state = level.getBlockState(pos);
                           if (this.canUseContainer(level, owner, pos, state)) {
                              double dist = pos.distSqr(base);
                              if (dist < bestDist) {
                                 bestDist = dist;
                                 best = pos.immutable();
                                 bestSlot = slot;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if (best == null) {
            return false;
         } else {
            this.rememberHive(owner, "chest", ArsWeissCloneHiveMemory.itemQuery(target), best, 90);
            double reach = Math.max(2.5, (Double)cfg().parallelBodyWorkerMineReach.get());
            Vec3 center = Vec3.atCenterOf(best);
            if (this.distanceToSqr(center) > reach * reach) {
               this.getNavigation().moveTo(center.x, center.y, center.z, this.workerMoveSpeed());
               this.breakPathObstruction(owner, best);
               this.recoverIfStuck(best);
               return true;
            } else if (!(level.getBlockEntity(best) instanceof Container container)) {
               return false;
            } else {
               if (bestSlot < 0
                  || bestSlot >= container.getContainerSize()
                  || container.getItem(bestSlot).isEmpty()
                  || !this.isLootOrder() && !this.matchesRequestedItem(container.getItem(bestSlot), target)) {
                  bestSlot = this.findContainerSlot(container, target);
                  if (bestSlot < 0 && this.isLootOrder()) {
                     bestSlot = this.findAnyContainerSlot(container);
                  }
               }

               if (bestSlot < 0) {
                  return false;
               }

               int room = Math.max(0, (Integer)cfg().parallelBodyWorkerCarryLimit.get() - this.totalCargoCount());
               if (room <= 0) {
                  this.returning = true;
                  return true;
               }

               int wantedAmount = this.isLootOrder() ? container.getItem(bestSlot).getCount() : Math.max(1, this.currentWantedStack().getCount());
               ItemStack extracted = container.removeItem(bestSlot, Math.min(room, wantedAmount));
               if (!extracted.isEmpty()) {
                  int added = this.addCargoStack(extracted);
                  if (added < extracted.getCount()) {
                     ItemStack leftover = extracted.copyWithCount(extracted.getCount() - added);
                     ItemStack slotStack = container.getItem(bestSlot);
                     if (slotStack.isEmpty()) {
                        container.setItem(bestSlot, leftover);
                     } else if (ItemStack.isSameItemSameComponents(slotStack, leftover)) {
                        slotStack.grow(leftover.getCount());
                        container.setItem(bestSlot, slotStack);
                     } else {
                        Containers.dropItemStack(level, best.getX() + 0.5, best.getY() + 1.0, best.getZ() + 0.5, leftover);
                     }
                  }

                  if (added > 0) {
                     container.setChanged();
                     level.playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.5F, 1.1F);
                  }
               }

               return true;
            }
         }
      }
   }

   private int findContainerSlot(Container container, ItemStack target) {
      for (int i = 0; i < container.getContainerSize(); i++) {
         ItemStack stack = container.getItem(i);
         if (!stack.isEmpty() && this.matchesRequestedItem(stack, target)) {
            return i;
         }
      }

      return -1;
   }

   private int findAnyContainerSlot(Container container) {
      for (int i = 0; i < container.getContainerSize(); i++) {
         if (!container.getItem(i).isEmpty()) {
            return i;
         }
      }

      return -1;
   }

   private boolean canUseContainer(ServerLevel level, ServerPlayer owner, BlockPos pos, BlockState state) {
      ManasSkillInstance instance = SkillUtils.getSkillOrNull(owner, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
      return !((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker()).grief(instance, level, owner, pos.getX(), pos.getY(), pos.getZ()).isFalse();
   }

   private boolean isLootOrder() {
      return "loot".equals(this.lastCommandMode) || this.lastCommandQuery != null && this.lastCommandQuery.startsWith("loot:");
   }

   private boolean isMineOrder() {
      return "mine".equals(this.lastCommandMode);
   }

   private void tickLootOrder(ServerPlayer owner, ItemStack target) {
      if (this.level() instanceof ServerLevel level) {
         this.keepTaskChunkLoaded();
         if (this.isCarryFull()) {
            this.returning = true;
            this.returnToOwner(owner);
         } else {
            boolean sawStructureSignal = this.scanNearbyLootStructureSignal(level, owner, target);
            if (!this.moveToNearbyUsefulContainer(owner, target)) {
               if (!sawStructureSignal
                  || this.lastSearchCenter == null
                  || this.lastSearchCenter.equals(BlockPos.ZERO)
                  || !(this.distanceToSqr(Vec3.atCenterOf(this.lastSearchCenter)) > 36.0)) {
                  this.roamForLoot(owner, target);
               }
            }
         }
      }
   }

   private void roamForLoot(ServerPlayer owner, ItemStack target) {
      String item = this.stackText(target);
      String hint = ArsWeissCloneLootIntel.structureHint(item);
      String query = hint.isBlank() ? item : item + " " + hint;
      this.roamForWork(owner, query);
   }

   private boolean scanNearbyLootStructureSignal(ServerLevel level, ServerPlayer owner, ItemStack target) {
      if (this.tickCount % 100 != this.scanPhase(100)) {
         return false;
      }

      if (!this.canUseScanBudget(owner, 1)) {
         return false;
      }

      if (!ArsWeissCloneSearchGovernor.allow(
         owner.getUUID(), this.level().dimension().location().toString(), "loot_surface", this.level().getGameTime(), 400, 1
      )) {
         return false;
      }

      BlockPos base = this.blockPosition();
      BlockPos best = null;
      int bestScore = 0;
      double bestDist = Double.MAX_VALUE;
      int radius = 4;

      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            if (x * x + z * z <= radius * radius) {
               BlockPos column = base.offset(x, 0, z);
               if (level.isLoaded(column)) {
                  BlockPos top = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, column);

                  for (int y = -1; y <= 2; y++) {
                     BlockPos pos = top.offset(0, y, 0);
                     if (level.isLoaded(pos)) {
                        BlockState state = level.getBlockState(pos);
                        int score = this.lootStructureBlockScore(state, target);
                        if (score > 0) {
                           double dist = pos.distSqr(base);
                           if (score > bestScore || score == bestScore && dist < bestDist) {
                              bestScore = score;
                              bestDist = dist;
                              best = pos.immutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (best == null) {
         return false;
      }

      this.rememberHive(
         owner, "structure", ArsWeissCloneLootIntel.structureHint(this.stackText(target)) + " " + ArsWeissCloneHiveMemory.itemQuery(target), best, bestScore
      );
      BlockPos stand = ArsWeissClonePathing.findSurfaceStandNear(level, best, 6);
      BlockPos goal = stand == null ? best : stand;
      this.lastSearchCenter = goal.immutable();
      if (this.distanceToSqr(Vec3.atCenterOf(goal)) > 20.25) {
         this.getNavigation().moveTo(goal.getX() + 0.5, goal.getY(), goal.getZ() + 0.5, this.workerMoveSpeed());
         this.assistExtremeTravel(owner, goal, false);
         this.recoverIfStuck(goal);
      }

      return true;
   }

   private int lootStructureBlockScore(BlockState state, ItemStack target) {
      if (state.isAir()) {
         return 0;
      }

      ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
      String block = id == null ? "" : id.toString().toLowerCase();
      String item = this.stackText(target);
      int score = 0;
      if (block.contains("chest") || block.contains("barrel")) {
         score = Math.max(score, 120);
      }

      if (block.contains("bookshelf") || block.contains("lectern") || block.contains("enchant")) {
         score = Math.max(score, this.containsAnyText(item, "book", "paper", "manual", "tome", "scroll", "map") ? 115 : 55);
      }

      if (block.contains("smith") || block.contains("anvil") || block.contains("tool_rack") || block.contains("kiln") || block.contains("mining_station")) {
         score = Math.max(score, this.containsAnyText(item, "ingot", "schematic", "gear", "sword", "spear", "magisteel", "mithril", "orichalcum") ? 110 : 65);
      }

      if (block.contains("magic") || block.contains("spell") || block.contains("crystal") || block.contains("engine")) {
         score = Math.max(score, this.containsAnyText(item, "magic", "tome", "grimoire", "stone", "crystal") ? 110 : 60);
      }

      if (block.contains("tatami") || block.contains("thatch") || block.contains("banner") || block.contains("lantern") || block.contains("candle")) {
         score = Math.max(score, 70);
      }

      if (block.contains("palm") || block.contains("sarasa") || block.contains("warp_pad")) {
         score = Math.max(score, 60);
      }

      if (block.contains("web") || block.contains("spider") || block.contains("moth")) {
         score = Math.max(score, this.containsAnyText(item, "thread", "fang", "carapace", "silk", "spider") ? 115 : 60);
      }

      if (block.contains("mangrove") || block.contains("mud") || block.contains("lily") || block.contains("throne")) {
         score = Math.max(score, this.containsAnyText(item, "lizard", "dragon", "coin", "trident", "fish") ? 100 : 50);
      }

      if (block.contains("tensura")) {
         score += 35;
      }

      return score;
   }

   private boolean containsAnyText(String text, String... values) {
      if (text == null) {
         return false;
      }

      for (String value : values) {
         if (text.contains(value)) {
            return true;
         }
      }

      return false;
   }

   private boolean huntMaterialMob(ServerPlayer owner, ItemStack target) {
      if (!ArsWeissCloneWorkerBrain.isLikelyMobDrop(target)) {
         return false;
      }

      if (this.mobScanCooldown > 0) {
         this.mobScanCooldown--;
         if (this.getTarget() != null && this.getTarget().isAlive()) {
            this.huntLivingTarget(owner, this.getTarget(), false);
            return true;
         } else {
            return false;
         }
      } else {
         this.mobScanCooldown = 8 + this.scanPhase(6);
         double radius = Math.min(48.0, Math.max(24.0, ((Integer)cfg().parallelBodyWorkerSearchRadius.get()).intValue() * 2.0));
         AABB box = this.getBoundingBox().inflate(radius);
         String want = this.stackText(target);
         List<LivingEntity> exact = this.level().getEntitiesOfClass(LivingEntity.class, box, entity -> this.isMaterialMobTarget(owner, entity, want));
         Optional<LivingEntity> nearest = exact.stream().min(Comparator.comparingDouble(entity -> ArsWeissCloneWorkerBrain.mobScore(entity, target, this)));
         if (nearest.isEmpty()) {
            List<LivingEntity> fallback = this.level()
               .getEntitiesOfClass(
                  LivingEntity.class,
                  this.getBoundingBox().inflate(Math.min(24.0, radius)),
                  entity -> ArsWeissCloneTensuraSupport.canBeArmyTarget(owner, entity) && entity instanceof Mob
               );
            nearest = fallback.stream().min(Comparator.comparingDouble(entity -> entity.distanceToSqr(this)));
         }

         if (nearest.isEmpty()) {
            return false;
         }

         LivingEntity targetEntity = nearest.get();
         this.rememberHive(
            owner, "mob", ArsWeissCloneHiveMemory.entityQuery(targetEntity) + " " + ArsWeissCloneHiveMemory.itemQuery(target), targetEntity.blockPosition(), 85
         );
         this.huntLivingTarget(owner, targetEntity, false);
         return true;
      }
   }

   private boolean isLikelyMobDrop(String want) {
      return ArsWeissCloneWorkerBrain.isLikelyMobDropText(want);
   }

   private boolean isMaterialMobTarget(ServerPlayer owner, LivingEntity entity, String want) {
      return ArsWeissCloneWorkerBrain.isMaterialMobTarget(owner, entity, want);
   }

   private void mineRequestedBlocks(ServerPlayer owner, ItemStack target) {
      if (this.level() instanceof ServerLevel level) {
         int var9 = Math.max(16, (Integer)cfg().parallelBodyMaxBlocksPerOrder.get());
         if (this.minedBlocks >= var9) {
            if (!this.isCurrentChecklistStepComplete() && !this.isCarryFull()) {
               this.minedBlocks = 0;
               this.lastMinedBlocksForStuck = 0;
               this.hasTargetBlock = false;
               this.blockScanCooldown = 0;
               if (this.isMiningMaterialTarget(target)) {
                  this.chooseMiningDirection();
                  this.tickMiningMode(owner, target);
               } else {
                  this.roamForWork(owner, this.stackText(target));
               }
            } else {
               this.returnToOwner(owner);
            }
         } else {
            if (!this.hasTargetBlock
               || level.getBlockState(this.targetBlock).isAir()
               || !this.matchesRequestedBlock(level.getBlockState(this.targetBlock), target)) {
               this.hasTargetBlock = this.findImmediateMineBlock(level, owner, target);
               if (!this.hasTargetBlock) {
                  this.hasTargetBlock = this.findRequestedBlock(level, owner, target);
               }

               if (!this.hasTargetBlock && this.isMiningMaterialTarget(target)) {
                  this.hasTargetBlock = this.cheatLocateBlockSource(level, owner, target);
               }

               this.blockScanCooldown = this.hasTargetBlock ? 0 : Math.max(4, 18 + this.scanPhase(12));
               if (!this.hasTargetBlock) {
                  if (this.isMiningMaterialTarget(target)) {
                     this.tickMiningMode(owner, target);
                  } else {
                     this.roamForWork(owner, this.stackText(target));
                  }

                  return;
               }
            }

            double reach = Math.max(1.5, (Double)cfg().parallelBodyWorkerMineReach.get());
            Vec3 center = Vec3.atCenterOf(this.targetBlock);
            if (this.distanceToSqr(center) > reach * reach) {
               this.getNavigation().moveTo(center.x, center.y, center.z, this.workerMoveSpeed());
               this.assistExtremeTravel(owner, this.targetBlock, true);
               this.breakPathObstruction(owner, this.targetBlock);
               this.recoverIfStuck(this.targetBlock);
            } else {
               BlockState state = level.getBlockState(this.targetBlock);
               if (!this.canBreakBlock(level, owner, this.targetBlock, state)) {
                  this.hasTargetBlock = false;
               } else {
                  this.getNavigation().stop();
                  this.workDelay++;
                  if (this.workDelay < Math.max(1, ((Integer)cfg().parallelBodyWorkerMineDelayTicks.get() + 1) / 2)) {
                     if (this.workDelay % 5 == 0) {
                        level.levelEvent(2001, this.targetBlock, Block.getId(state));
                     }
                  } else {
                     this.workDelay = 0;
                     level.destroyBlock(this.targetBlock, true, this);
                     this.minedBlocks++;
                     this.breakNearbyMatchingBlocks(level, owner, this.targetBlock, target);
                     this.hasTargetBlock = false;
                     this.collectUsefulDrops(owner);
                  }
               }
            }
         }
      }
   }

   private boolean findImmediateMineBlock(ServerLevel level, ServerPlayer owner, ItemStack target) {
      if (!this.isMineOrder()) {
         return false;
      }

      BlockPos base = this.blockPosition();
      BlockPos best = null;
      int bestScore = 0;
      double bestDist = Double.MAX_VALUE;

      for (int y = -3; y <= 2; y++) {
         for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
               if (x * x + z * z <= 8) {
                  BlockPos pos = base.offset(x, y, z);
                  if (level.isLoaded(pos)) {
                     BlockState state = level.getBlockState(pos);
                     if (this.canBreakBlock(level, owner, pos, state)) {
                        int score = this.mineBlockMatchScore(state, target);
                        if (score > 0) {
                           if (y == -1 && x == 0 && z == 0) {
                              score += 60;
                           } else if (y < 0) {
                              score += 25;
                           }

                           double dist = pos.distSqr(base);
                           if (score > bestScore || score == bestScore && dist < bestDist) {
                              bestScore = score;
                              bestDist = dist;
                              best = pos.immutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (best == null) {
         return false;
      }

      this.targetBlock = best;
      this.rememberHive(owner, "block", ArsWeissCloneHiveMemory.itemQuery(target), best, bestScore);
      return true;
   }

   private int mineBlockMatchScore(BlockState state, ItemStack target) {
      if (this.isExactBlockRequest(target)) {
         if (this.matchesExactRequestedBlock(state, target)) {
            return 180;
         } else {
            return this.matchesMineAlias(state, target) ? 165 : 0;
         }
      } else {
         int score = ArsWeissCloneWorkerBrain.blockSourceScore(state, target);
         if (score > 0) {
            return score;
         } else {
            return this.matchesMineAlias(state, target) ? 120 : 0;
         }
      }
   }

   private boolean isMiningMaterialTarget(ItemStack target) {
      String text = this.stackText(target);
      if (this.isManufacturedTensuraMetalText(text)) {
         return false;
      } else {
         return !text.contains("magic stone") && !text.contains("magic_stone")
            ? text.contains("magic_ore")
               || text.contains("magic ore")
               || text.contains("_ore")
               || text.contains(" ore")
               || text.contains("raw_")
               || text.contains("coal")
               || text.contains("diamond")
               || text.contains("emerald")
               || text.contains("redstone")
               || text.contains("lapis")
               || text.contains("quartz")
               || text.contains("ancient_debris")
            : false;
      }
   }

   private boolean isManufacturedTensuraMetalText(String text) {
      return text.contains("magisteel")
         || text.contains("mithril")
         || text.contains("orichalcum")
         || text.contains("adamantite")
         || text.contains("hihiirokane");
   }

   private int targetMiningY(ItemStack target) {
      String text = this.stackText(target);
      int min = this.level().getMinBuildHeight() + 4;
      int max = this.level().getMaxBuildHeight() - 8;
      if (text.contains("ancient_debris") || text.contains("netherite")) {
         return Math.max(min, Math.min(max, 15));
      } else if (text.contains("diamond") || text.contains("redstone") || text.contains("lapis") || text.contains("magic_ore") || text.contains("magic ore")) {
         return Math.max(min, Math.min(max, -54));
      } else if (text.contains("silver")) {
         return Math.max(min, Math.min(max, -32));
      } else if (text.contains("gold")) {
         return Math.max(min, Math.min(max, -16));
      } else if (text.contains("iron") || text.contains("copper")) {
         return Math.max(min, Math.min(max, 16));
      } else if (text.contains("coal")) {
         return Math.max(min, Math.min(max, 48));
      } else {
         return text.contains("quartz") ? Math.max(min, Math.min(max, 32)) : Math.max(min, Math.min(max, -48));
      }
   }

   private void tickMiningMode(ServerPlayer owner, ItemStack target) {
      if (this.level() instanceof ServerLevel level) {
         this.keepTaskChunkLoaded();
         this.miningModeTicks++;
         this.returning = false;
         this.getNavigation().stop();
         this.setNoGravity(false);
         int var10 = this.targetMiningY(target);
         BlockPos feet = this.blockPosition();
         if (feet.getY() > var10 + 1) {
            this.digMiningDropShaft(level, owner, feet, target);
            BlockPos below = feet.below();
            BlockState belowState = level.getBlockState(below);
            if (!belowState.isAir() && !belowState.getFluidState().is(FluidTags.WATER)) {
               this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.18, 0.0));
            } else {
               this.getNavigation().stop();
               this.setDeltaMovement(this.getDeltaMovement().scale(0.55).add(0.0, -0.24, 0.0));
            }

            this.hurtMarked = true;
         } else {
            this.clearMiningStance(level, owner, feet, target);
            if (this.miningDirX == 0 && this.miningDirZ == 0) {
               this.chooseMiningDirection();
            }

            BlockPos frontFeet = feet.offset(this.miningDirX, 0, this.miningDirZ);
            BlockPos frontHead = frontFeet.above();
            BlockPos frontFloor = frontFeet.below();
            boolean didMine = false;
            didMine |= this.mineOneMiningBlock(level, owner, frontFeet, target);
            didMine |= this.mineOneMiningBlock(level, owner, frontHead, target);
            if (!this.isSafeMiningFloor(level, frontFloor)) {
               didMine |= this.mineOneMiningBlock(level, owner, frontFloor, target);
            }

            if (!didMine) {
               this.getMoveControl().setWantedPosition(frontFeet.getX() + 0.5, frontFeet.getY(), frontFeet.getZ() + 0.5, this.workerMoveSpeed());
               this.setDeltaMovement(this.getDeltaMovement().add(this.miningDirX * 0.045, 0.0, this.miningDirZ * 0.045));
               if (this.isWaterBlocked(level) || this.isUnderwaterGoal(level, frontFeet)) {
                  this.supportWaterTravel(level, frontFeet, true);
               }

               this.hurtMarked = true;
            }

            if (this.miningModeTicks % 10 == this.scanPhase(10) && this.findRequestedBlock(level, owner, target)) {
               this.miningNoProgressTicks = 0;
            } else {
               if (this.miningModeTicks % 220 == this.scanPhase(220)) {
                  this.chooseMiningDirection();
               }
            }
         }
      }
   }

   private void digMiningDropShaft(ServerLevel level, ServerPlayer owner, BlockPos feet, ItemStack target) {
      int broken = 0;

      for (int y = 1; y <= 2 && broken < 4; y++) {
         for (int x = -1; x <= 1 && broken < 4; x++) {
            for (int z = -1; z <= 1 && broken < 4; z++) {
               if (Math.abs(x) + Math.abs(z) <= 1) {
                  BlockPos pos = feet.offset(x, -y, z);
                  if (this.mineOneMiningBlock(level, owner, pos, target)) {
                     broken++;
                  }
               }
            }
         }
      }
   }

   private void clearMiningStance(ServerLevel level, ServerPlayer owner, BlockPos feet, ItemStack target) {
      int broken = 0;

      for (int x = -1; x <= 1 && broken < 4; x++) {
         for (int z = -1; z <= 1 && broken < 4; z++) {
            if (Math.abs(x) + Math.abs(z) <= 1) {
               BlockPos base = feet.offset(x, 0, z);
               if (this.mineOneMiningBlock(level, owner, base, target)) {
                  broken++;
               } else if (this.mineOneMiningBlock(level, owner, base.above(), target)) {
                  broken++;
               }
            }
         }
      }
   }

   private void chooseMiningDirection() {
      int idx = Math.floorMod(this.getId() + this.miningModeTicks, 4);
      if (idx == 0) {
         this.miningDirX = 1;
         this.miningDirZ = 0;
      } else if (idx == 1) {
         this.miningDirX = -1;
         this.miningDirZ = 0;
      } else if (idx == 2) {
         this.miningDirX = 0;
         this.miningDirZ = 1;
      } else {
         this.miningDirX = 0;
         this.miningDirZ = -1;
      }
   }

   private boolean mineOneMiningBlock(ServerLevel level, ServerPlayer owner, BlockPos pos, ItemStack target) {
      if (pos != null && level.isLoaded(pos)) {
         BlockState state = level.getBlockState(pos);
         if (!state.isAir() && this.canBreakBlock(level, owner, pos, state)) {
            float hardness = state.getDestroySpeed(level, pos);
            if (!(hardness < 0.0F) && !(hardness > Math.max(6.0F, ((Double)cfg().parallelBodyWorkerMineHardnessLimit.get()).floatValue()))) {
               boolean requested = this.matchesRequestedBlock(state, target);
               level.destroyBlock(pos, true, this);
               level.levelEvent(2001, pos, Block.getId(state));
               if (requested) {
                  this.minedBlocks++;
               }

               this.miningNoProgressTicks = 0;
               this.stuckTicks = 0;
               this.collectUsefulDrops(owner);
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isSafeMiningFloor(ServerLevel level, BlockPos pos) {
      if (pos != null && level.isLoaded(pos)) {
         BlockState state = level.getBlockState(pos);
         return !state.isAir() && state.getFluidState().isEmpty() && !state.liquid();
      } else {
         return false;
      }
   }

   private void breakNearbyMatchingBlocks(ServerLevel level, ServerPlayer owner, BlockPos origin, ItemStack target) {
      int extra = this.unresolvedTicks > 1200 ? 3 : 2;

      for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-1, -1, -1), origin.offset(1, 1, 1))) {
         if (extra <= 0 || this.minedBlocks >= (Integer)cfg().parallelBodyMaxBlocksPerOrder.get()) {
            return;
         }

         BlockPos immutable = pos.immutable();
         if (!immutable.equals(origin) && level.isLoaded(immutable)) {
            BlockState state = level.getBlockState(immutable);
            if (this.matchesRequestedBlock(state, target) && this.canBreakBlock(level, owner, immutable, state)) {
               level.destroyBlock(immutable, true, this);
               level.levelEvent(2001, immutable, Block.getId(state));
               this.minedBlocks++;
               extra--;
               this.rememberHive(owner, "block", ArsWeissCloneHiveMemory.itemQuery(target), immutable, 75);
            }
         }
      }
   }

   private boolean findRequestedBlock(ServerLevel level, ServerPlayer owner, ItemStack target) {
      if (!this.canUseScanBudget(owner, 1)) {
         return false;
      }

      if (this.isSurfaceGatherTarget(target) && !this.isMineOrder()) {
         return this.findSurfaceRequestedBlock(level, owner, target);
      }

      int radius = this.isExactBlockRequest(target) ? (this.isMineOrder() ? 10 : 6) : 8;
      boolean exactBlock = this.isExactBlockRequest(target);
      BlockPos base = this.blockPosition();
      BlockPos best = null;
      int bestScore = 0;
      double bestDist = Double.MAX_VALUE;
      int yMin = this.isMiningMaterialTarget(target) ? -7 : (this.isMineOrder() ? -8 : -2);
      int yMax = this.isMiningMaterialTarget(target) ? 7 : (this.isMineOrder() ? 8 : 2);

      for (int y = yMin; y <= yMax; y++) {
         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               if (x * x + z * z <= radius * radius) {
                  BlockPos pos = base.offset(x, y, z);
                  if (level.isLoaded(pos)) {
                     BlockState state = level.getBlockState(pos);
                     if (this.canBreakBlock(level, owner, pos, state)) {
                        int score = exactBlock ? this.mineBlockMatchScore(state, target) : ArsWeissCloneWorkerBrain.blockSourceScore(state, target);
                        if (score > 0) {
                           double dist = pos.distSqr(this.blockPosition());
                           if (score > bestScore || score == bestScore && dist < bestDist) {
                              bestScore = score;
                              bestDist = dist;
                              best = pos.immutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (best == null) {
         return false;
      }

      this.targetBlock = best;
      this.rememberHive(owner, "block", ArsWeissCloneHiveMemory.itemQuery(target), best, bestScore);
      return true;
   }

   private boolean isSurfaceGatherTarget(ItemStack target) {
      if (!target.isEmpty() && !this.isMiningMaterialTarget(target)) {
         String text = this.stackText(target);
         return this.isExactBlockRequest(target)
            || text.contains("sugar_cane")
            || text.contains("sugar cane")
            || text.contains("cactus")
            || text.contains("bamboo")
            || text.contains("kelp")
            || text.contains("flower")
            || text.contains("grass")
            || text.contains("sapling")
            || text.contains("mushroom")
            || text.contains("melon")
            || text.contains("pumpkin")
            || text.contains("wheat")
            || text.contains("carrot")
            || text.contains("potato")
            || text.contains("beetroot");
      } else {
         return false;
      }
   }

   private boolean findSurfaceRequestedBlock(ServerLevel level, ServerPlayer owner, ItemStack target) {
      boolean exactBlock = this.isExactBlockRequest(target);
      BlockPos base = this.blockPosition();
      Vec3 look = this.getLookAngle();
      Vec3 flat = new Vec3(look.x, 0.0, look.z);
      if (flat.lengthSqr() < 0.01) {
         flat = new Vec3(this.miningDirX == 0 && this.miningDirZ == 0 ? 1.0 : this.miningDirX, 0.0, this.miningDirZ);
      }

      flat = flat.normalize();
      Vec3 side = new Vec3(-flat.z, 0.0, flat.x);
      BlockPos best = null;
      int bestScore = 0;
      double bestDist = Double.MAX_VALUE;
      int forward = 5;
      int sideways = 1;

      for (int d = 0; d <= forward; d++) {
         for (int s = -sideways; s <= sideways; s++) {
            BlockPos column = BlockPos.containing(base.getX() + flat.x * d + side.x * s, base.getY(), base.getZ() + flat.z * d + side.z * s);
            if (level.isLoaded(column)) {
               BlockPos top = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, column);

               for (BlockPos pos : new BlockPos[]{top, top.below(), top.above()}) {
                  if (level.isLoaded(pos)) {
                     BlockState state = level.getBlockState(pos);
                     if (this.canBreakBlock(level, owner, pos, state)) {
                        int score = exactBlock ? this.mineBlockMatchScore(state, target) : ArsWeissCloneWorkerBrain.blockSourceScore(state, target);
                        if (score > 0) {
                           double dist = pos.distSqr(base);
                           if (score > bestScore || score == bestScore && dist < bestDist) {
                              bestScore = score;
                              bestDist = dist;
                              best = pos.immutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (best == null) {
         return false;
      }

      this.targetBlock = best;
      this.rememberHive(owner, "block", ArsWeissCloneHiveMemory.itemQuery(target), best, bestScore);
      return true;
   }

   private boolean cheatLocateBlockSource(ServerLevel level, ServerPlayer owner, ItemStack target) {
      String query = ArsWeissCloneHiveMemory.itemQuery(target);
      BlockPos remembered = ArsWeissCloneHiveMemory.nearest(owner.getUUID(), this.level().dimension().location().toString(), query, this.blockPosition(), 192);
      if (remembered != null) {
         if (level.isLoaded(remembered)) {
            BlockState rememberedState = level.getBlockState(remembered);
            if (this.canBreakBlock(level, owner, remembered, rememberedState) && this.matchesRequestedBlock(rememberedState, target)) {
               this.targetBlock = remembered.immutable();
               return true;
            }
         }

         BlockPos stand = level.isLoaded(remembered) ? ArsWeissClonePathing.findSurfaceStandNear(level, remembered, 8) : null;
         if (stand != null) {
            this.lastSearchCenter = stand.immutable();
            this.searchRadius = Math.max(this.searchRadius, Math.min(256, (int)Math.sqrt(Math.max(1.0, stand.distSqr(this.blockPosition())))));
            this.getNavigation().moveTo(stand.getX() + 0.5, stand.getY(), stand.getZ() + 0.5, this.workerMoveSpeed());
            this.assistExtremeTravel(owner, stand, false);
            return false;
         }
      }

      return false;
   }

   private BlockPos scanChunkForRequestedBlock(ServerLevel level, ServerPlayer owner, ChunkPos chunk, ItemStack target) {
      int minY = this.targetMineMinY(target);
      int maxY = this.targetMineMaxY(target);
      int yStep = Math.max(4, maxY - minY > 96 ? 6 : 4);
      int xStep = 4;
      BlockPos best = null;
      int bestScore = 0;
      double bestDist = Double.MAX_VALUE;
      int xStart = chunk.getMinBlockX();
      int zStart = chunk.getMinBlockZ();

      for (int x = xStart; x < xStart + 16; x += xStep) {
         for (int z = zStart; z < zStart + 16; z += xStep) {
            for (int y = maxY; y >= minY; y -= yStep) {
               BlockPos pos = new BlockPos(x, y, z);
               if (level.isLoaded(pos)) {
                  BlockState state = level.getBlockState(pos);
                  if (this.canBreakBlock(level, owner, pos, state)) {
                     int score = this.isExactBlockRequest(target)
                        ? this.mineBlockMatchScore(state, target)
                        : ArsWeissCloneWorkerBrain.blockSourceScore(state, target);
                     if (score > 0) {
                        double dist = pos.distSqr(this.blockPosition());
                        if (score > bestScore || score == bestScore && dist < bestDist) {
                           bestScore = score;
                           bestDist = dist;
                           best = pos.immutable();
                        }
                     }
                  }
               }
            }
         }
      }

      return best;
   }

   private int targetMineMinY(ItemStack target) {
      String text = this.stackText(target);
      int min = this.level().getMinBuildHeight();
      if (text.contains("ancient_debris") || text.contains("netherite")) {
         return Math.max(min, 4);
      } else if (text.contains("diamond") || text.contains("redstone") || text.contains("lapis") || text.contains("magic_ore") || text.contains("magic ore")) {
         return Math.max(min, -64);
      } else if (!text.contains("iron")
         && !text.contains("copper")
         && !text.contains("coal")
         && !text.contains("silver")
         && !text.contains("magisteel")
         && !text.contains("mithril")
         && !text.contains("orichalcum")) {
         return ArsWeissCloneWorkerBrain.materialPriority(target) <= 2 ? Math.max(min, this.blockPosition().getY() - 32) : Math.max(min, -64);
      } else {
         return Math.max(min, -48);
      }
   }

   private int targetMineMaxY(ItemStack target) {
      String text = this.stackText(target);
      int max = this.level().getMaxBuildHeight() - 2;
      if (text.contains("ancient_debris") || text.contains("netherite")) {
         return Math.min(max, 32);
      } else if (text.contains("diamond") || text.contains("redstone") || text.contains("lapis") || text.contains("magic_ore") || text.contains("magic ore")) {
         return Math.min(max, 96);
      } else if (!text.contains("iron") && !text.contains("copper") && !text.contains("coal") && !text.contains("silver")) {
         return ArsWeissCloneWorkerBrain.materialPriority(target) <= 2 ? Math.min(max, this.blockPosition().getY() + 48) : Math.min(max, 128);
      } else {
         return Math.min(max, 128);
      }
   }

   private boolean isExactBlockRequest(ItemStack target) {
      if (target.isEmpty()) {
         return false;
      }

      if (!(target.getItem() instanceof BlockItem)) {
         return false;
      }

      String text = this.stackText(target);
      return !text.contains("ore")
         && !text.contains("raw_")
         && !text.contains("ingot")
         && !text.contains("gem")
         && !text.contains("nugget")
         && !text.contains("magic stone")
         && !text.contains("magic_stone")
         && !text.contains("magisteel")
         && !text.contains("mithril")
         && !text.contains("orichalcum")
         && !text.contains("adamantite")
         && !text.contains("hihiirokane");
   }

   private boolean matchesExactRequestedBlock(BlockState state, ItemStack target) {
      return !(target.getItem() instanceof BlockItem blockItem) ? false : state.is(blockItem.getBlock()) || this.matchesMineAlias(state, target);
   }

   private boolean matchesRequestedBlock(BlockState state, ItemStack target) {
      return this.isExactBlockRequest(target)
         ? this.matchesExactRequestedBlock(state, target)
         : ArsWeissCloneWorkerBrain.blockSourceScore(state, target) > 0 || this.matchesMineAlias(state, target);
   }

   private boolean matchesMineAlias(BlockState state, ItemStack target) {
      if (state != null && target != null && !target.isEmpty()) {
         String want = this.stackText(target).replace('_', ' ');
         String block = this.blockText(state).replace('_', ' ');
         if (want.contains("dirt")
            && this.containsAnyText(
               block,
               "minecraft:dirt",
               "minecraft:coarse dirt",
               "minecraft:rooted dirt",
               "minecraft:grass block",
               "minecraft:podzol",
               "minecraft:mycelium",
               "minecraft:dirt path",
               "minecraft:farmland"
            )) {
            return true;
         } else if (want.contains("grass block") && this.containsAnyText(block, "minecraft:grass block", "minecraft:dirt")) {
            return true;
         } else if (want.contains("sand") && this.containsAnyText(block, "minecraft:sand", "minecraft:red sand", "minecraft:suspicious sand")) {
            return true;
         } else {
            return want.contains("gravel") && this.containsAnyText(block, "minecraft:gravel", "minecraft:suspicious gravel")
               ? true
               : want.contains("clay") && block.contains("minecraft:clay");
         }
      } else {
         return false;
      }
   }

   private String blockText(BlockState state) {
      ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
      return id == null ? "" : id.toString().toLowerCase();
   }

   private boolean matchesOreSource(String blockText, String itemPath, String want) {
      return ArsWeissCloneWorkerBrain.matchesLegacyBlockText(blockText, itemPath, want);
   }

   private void breakPathObstruction(ServerPlayer owner, BlockPos goal) {
      if (this.level() instanceof ServerLevel level) {
         if (this.tickCount % 40 == this.scanPhase(40) && this.canUseScanBudget(owner, 3)) {
            Vec3 from = this.getEyePosition();
            Vec3 to = Vec3.atCenterOf(goal);
            Vec3 dir = to.subtract(from);
            if (!(dir.lengthSqr() < 1.0)) {
               dir = dir.normalize();
               int cleared = 0;

               for (double d = 1.0; d <= 4.5; d += 0.65) {
                  BlockPos pos = BlockPos.containing(from.add(dir.scale(d)));
                  BlockState state = level.getBlockState(pos);
                  if (!state.isAir()) {
                     if (!this.canBreakBlock(level, owner, pos, state)
                        || !(state.getDestroySpeed(level, pos) <= Math.min(4.5F, ((Double)cfg().parallelBodyWorkerMineHardnessLimit.get()).floatValue()))) {
                        return;
                     }

                     level.destroyBlock(pos, true, this);
                     level.levelEvent(2001, pos, Block.getId(state));
                     this.minedBlocks++;
                     cleared++;
                     this.collectUsefulDrops(owner);
                     if (cleared >= 2) {
                        return;
                     }
                  }
               }
            }
         }
      }
   }

   private void roamForWork(ServerPlayer owner, String salt) {
      if (this.level() instanceof ServerLevel level) {
         this.keepTaskChunkLoaded();
         String query = salt != null && !salt.isBlank() ? salt : this.requestedText();
         int cadence = this.unresolvedTicks > 2400 ? 40 : 60;
         if (this.shouldKeepCurrentRoamGoal()) {
            if (this.tickCount % cadence == this.scanPhase(cadence)) {
               this.scanRoamArea(level, owner, query);
            }
         } else {
            this.scanRoamArea(level, owner, query);
            BlockPos remembered = ArsWeissCloneHiveMemory.nearest(
               owner.getUUID(), this.level().dimension().location().toString(), query, this.blockPosition(), 192
            );
            if (remembered != null && this.tickCount % 120 == this.scanPhase(120)) {
               BlockPos goal = remembered;
               if (level.isLoaded(remembered)) {
                  BlockPos stand = ArsWeissClonePathing.findSurfaceStandNear(level, remembered, 8);
                  if (stand != null) {
                     goal = stand;
                  }
               }

               this.beginRoamTo(owner, level, goal, false);
            } else {
               ItemStack roamTarget = this.currentWantedStack();
               BlockPos goal = !roamTarget.isEmpty() && this.isSurfaceGatherTarget(roamTarget)
                  ? this.nextDirectionalSurfaceSearch(level, owner.blockPosition(), query)
                  : this.nextFarSearchSurface(level, owner.blockPosition(), query, false);
               if (goal != null) {
                  this.beginRoamTo(owner, level, goal, false);
               } else {
                  this.recoverIfStuck(this.lastSearchCenter.equals(BlockPos.ZERO) ? this.blockPosition() : this.lastSearchCenter);
               }
            }
         }
      }
   }

   private boolean shouldKeepCurrentRoamGoal() {
      if (this.lastSearchCenter != null && !this.lastSearchCenter.equals(BlockPos.ZERO) && !this.getNavigation().isDone()) {
         this.roamGoalTicks++;
         if (this.distanceToSqr(Vec3.atCenterOf(this.lastSearchCenter)) <= 25.0) {
            return false;
         }

         if (this.tickCount % 20 == this.scanPhase(20)) {
            double dx = this.getX() - this.lastRoamX;
            double dz = this.getZ() - this.lastRoamZ;
            double moved = dx * dx + dz * dz;
            if (moved < 0.2) {
               this.roamNoProgressTicks += 20;
            } else {
               this.roamNoProgressTicks = Math.max(0, this.roamNoProgressTicks - 40);
            }

            this.lastRoamX = this.getX();
            this.lastRoamZ = this.getZ();
         }

         return this.roamGoalTicks < 180 && this.roamNoProgressTicks < 100;
      } else {
         this.roamGoalTicks = 0;
         this.roamNoProgressTicks = 0;
         return false;
      }
   }

   private void scanRoamArea(ServerLevel level, ServerPlayer owner, String query) {
      ItemStack target = this.currentWantedStack();
      if (!target.isEmpty() && !this.isMiningMaterialTarget(target)) {
         int cadence = this.isLootOrder() ? 80 : 32;
         if (this.tickCount % cadence == this.scanPhase(cadence)) {
            if (!this.isLootOrder() || this.isSurfaceGatherTarget(target)) {
               if (this.findRequestedBlock(level, owner, target)) {
                  this.roamGoalTicks = 0;
                  this.roamNoProgressTicks = 0;
               }
            }
         }
      }
   }

   private BlockPos nextDirectionalSurfaceSearch(ServerLevel level, BlockPos anchor, String salt) {
      int step = ++this.farRoamStep;
      long seed = this.getUUID().getMostSignificantBits() ^ (salt == null ? 0 : salt.hashCode());
      int dirIndex = Math.floorMod((int)(seed ^ seed >>> 32), 8);
      int dx = 0;
      int dz = 0;
      byte var15;
      byte var16;
      if (dirIndex == 0) {
         var15 = 1;
         var16 = 0;
      } else if (dirIndex == 1) {
         var15 = -1;
         var16 = 0;
      } else if (dirIndex == 2) {
         var15 = 0;
         var16 = 1;
      } else if (dirIndex == 3) {
         var15 = 0;
         var16 = -1;
      } else if (dirIndex == 4) {
         var15 = 1;
         var16 = 1;
      } else if (dirIndex == 5) {
         var15 = -1;
         var16 = 1;
      } else if (dirIndex == 6) {
         var15 = 1;
         var16 = -1;
      } else {
         var15 = -1;
         var16 = -1;
      }

      int distance = 48 + Math.floorMod(step, 4) * 16;
      this.searchRadius = Math.max(this.searchRadius, Math.min(256, distance));
      int side = Math.floorMod(step + this.getId(), 5) - 2;
      BlockPos base = this.blockPosition();
      BlockPos raw = base.offset(var15 * distance + var16 * side * 8, 0, var16 * distance - var15 * side * 8);
      if (!level.isLoaded(raw)) {
         return new BlockPos(raw.getX(), base.getY(), raw.getZ()).immutable();
      }

      BlockPos surface = ArsWeissClonePathing.findSurfaceStandNear(level, raw, 4);
      return surface == null ? new BlockPos(raw.getX(), base.getY(), raw.getZ()).immutable() : surface.immutable();
   }

   private void beginRoamTo(ServerPlayer owner, ServerLevel level, BlockPos goal, boolean miningGoal) {
      if (goal != null) {
         this.lastSearchCenter = goal.immutable();
         this.searchRadius = Math.max(this.searchRadius, Math.min(256, (int)Math.sqrt(Math.max(1.0, goal.distSqr(this.blockPosition())))));
         this.roamGoalTicks = 0;
         this.roamNoProgressTicks = 0;
         this.lastRoamX = this.getX();
         this.lastRoamZ = this.getZ();
         this.getNavigation().stop();
         this.getNavigation().moveTo(goal.getX() + 0.5, goal.getY(), goal.getZ() + 0.5, this.workerMoveSpeed());
         this.assistExtremeTravel(owner, goal, miningGoal);
         this.recoverIfStuck(goal);
      }
   }

   private BlockPos nextFarSearchSurface(ServerLevel level, BlockPos anchor, String salt, boolean probeOre) {
      int step = ++this.farRoamStep + this.getId() * 13;
      int distance = probeOre ? 96 : 80;
      if (this.unresolvedTicks > 2400) {
         distance += 32;
      }

      this.searchRadius = Math.max(this.searchRadius, Math.min(256, distance));
      long seed = this.getUUID().getLeastSignificantBits() ^ (salt == null ? 0 : salt.hashCode()) ^ step * 341873128712L;
      double angle = (seed & 1048575L) * 5.992112452678286E-6 + step * 2.399963229728653;
      BlockPos center = this.blockPosition();
      BlockPos raw = center.offset((int)Math.round(Math.cos(angle) * distance), 0, (int)Math.round(Math.sin(angle) * distance));
      if (!level.isLoaded(raw)) {
         return new BlockPos(raw.getX(), center.getY(), raw.getZ()).immutable();
      }

      BlockPos surface = ArsWeissClonePathing.findSurfaceStandNear(level, raw, 4);
      return surface != null ? surface.immutable() : new BlockPos(raw.getX(), center.getY(), raw.getZ()).immutable();
   }

   private BlockPos findSafeStandNear(ServerLevel level, BlockPos center) {
      return ArsWeissClonePathing.findSafeStandNear(level, center, 12, 16, 24);
   }

   private void tickMobilitySupport(ServerPlayer owner) {
      if (!this.isAnchorBody()) {
         if (this.level() instanceof ServerLevel level) {
            if (!this.isWaterBlocked(level) && this.isNoGravity()) {
               this.setNoGravity(false);
            }

            BlockPos goal = this.currentMobilityGoal(owner);
            if (goal != null && !goal.equals(BlockPos.ZERO)) {
               this.assistExtremeTravel(owner, goal, this.hasTargetBlock && !this.currentWantedStack().isEmpty());
            }
         }
      }
   }

   private BlockPos currentMobilityGoal(ServerPlayer owner) {
      if (this.returning) {
         return owner.blockPosition();
      } else if (this.hasSendTarget) {
         return this.sendTarget;
      } else if (this.hasTargetBlock) {
         return this.targetBlock;
      } else if (this.getTarget() != null && this.getTarget().isAlive()) {
         return this.getTarget().blockPosition();
      } else {
         return this.lastSearchCenter != null && !this.lastSearchCenter.equals(BlockPos.ZERO) ? this.lastSearchCenter : null;
      }
   }

   private void assistExtremeTravel(ServerPlayer owner, BlockPos goal, boolean miningGoal) {
      if (this.level() instanceof ServerLevel level && goal != null && !goal.equals(BlockPos.ZERO)) {
         if (this.shouldUseOceanTravel(level, goal)) {
            this.supportOceanTravel(level, goal, miningGoal);
         } else if (this.isWaterBlocked(level)) {
            this.supportWaterTravel(level, goal, miningGoal);
         }

         this.supportLongRangeTravel(level, goal, miningGoal);
         if (miningGoal) {
            this.supportCaveMining(owner, level, goal);
         }
      }
   }

   private boolean isWaterBlocked(ServerLevel level) {
      BlockPos feet = this.blockPosition();
      BlockPos chest = feet.above();
      return this.isInWaterOrBubble() || level.getFluidState(feet).is(FluidTags.WATER) || level.getFluidState(chest).is(FluidTags.WATER);
   }

   private boolean shouldUseOceanTravel(ServerLevel level, BlockPos goal) {
      if (goal != null && !goal.equals(BlockPos.ZERO)) {
         double dist = this.distanceToSqr(Vec3.atCenterOf(goal));
         if (!this.isWaterBlocked(level)) {
            if (dist < 4096.0) {
               return false;
            }

            Vec3 toGoal = Vec3.atCenterOf(goal).subtract(this.position());
            Vec3 flat = new Vec3(toGoal.x, 0.0, toGoal.z);
            if (flat.lengthSqr() < 1.0) {
               return false;
            }

            Vec3 dir = flat.normalize();

            for (int d : new int[]{32, 96, 192}) {
               BlockPos check = BlockPos.containing(this.getX() + dir.x * d, this.getY(), this.getZ() + dir.z * d);
               if (this.isOceanWaterColumn(level, check)) {
                  return true;
               }
            }

            return false;
         } else {
            return dist > 256.0 || this.tickCount % 20 == this.scanPhase(20);
         }
      } else {
         return false;
      }
   }

   private void supportOceanTravel(ServerLevel level, BlockPos goal, boolean miningGoal) {
      this.supportWaterTravel(level, goal, miningGoal);
   }

   private BlockPos nextOceanTravelHop(ServerLevel level, BlockPos goal, boolean miningGoal) {
      Vec3 toGoal = Vec3.atCenterOf(goal).subtract(this.position());
      Vec3 flat = new Vec3(toGoal.x, 0.0, toGoal.z);
      if (flat.lengthSqr() < 1.0) {
         return this.waterSurfaceAt(level, goal);
      }

      Vec3 dir = flat.normalize();
      double distance = Math.sqrt(flat.lengthSqr());
      double maxHop = miningGoal ? 4096.0 : 16384.0;
      double hopDistance = Math.min(Math.max(512.0, distance - 48.0), maxHop);
      int side = Math.floorMod(++this.oceanTravelStep + this.getId(), 5) - 2;
      Vec3 lateral = new Vec3(-dir.z, 0.0, dir.x).scale(side * 24.0);
      BlockPos raw = BlockPos.containing(this.getX() + dir.x * hopDistance + lateral.x, this.getY(), this.getZ() + dir.z * hopDistance + lateral.z);
      BlockPos land = ArsWeissClonePathing.findSurfaceStandNear(level, raw, 18);
      if (land != null) {
         return land.immutable();
      }

      BlockPos water = this.waterSurfaceAt(level, raw);
      return water != null ? water.immutable() : raw.immutable();
   }

   private boolean isOceanWaterColumn(ServerLevel level, BlockPos pos) {
      if (!level.hasChunkAt(pos)) {
         return false;
      }

      BlockPos top = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, pos);
      BlockPos water = top.below();
      return level.isLoaded(water) && level.getFluidState(water).is(FluidTags.WATER);
   }

   private BlockPos waterSurfaceAt(ServerLevel level, BlockPos pos) {
      if (!level.hasChunkAt(pos)) {
         return null;
      } else {
         BlockPos top = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, pos);
         BlockPos water = top.below();
         BlockPos feet = top;
         BlockPos head = feet.above();
         if (!level.isLoaded(head)) {
            return null;
         } else {
            return level.getFluidState(water).is(FluidTags.WATER) && level.getBlockState(feet).isAir() && level.getBlockState(head).isAir()
               ? feet.immutable()
               : null;
         }
      }
   }

   private void supportWaterTravel(ServerLevel level, BlockPos goal, boolean miningGoal) {
      if (goal != null) {
         this.setNoGravity(false);
         this.setAirSupply(this.getMaxAirSupply());
         Vec3 target = Vec3.atCenterOf(goal);
         Vec3 flat = new Vec3(target.x - this.getX(), 0.0, target.z - this.getZ());
         Vec3 dir = flat.lengthSqr() < 0.01 ? this.getLookAngle() : flat.normalize();
         boolean underwaterMine = miningGoal && this.isUnderwaterGoal(level, goal);
         double dy;
         if (underwaterMine) {
            double wantedY = target.y - this.getY();
            dy = Math.max(-0.22, Math.min(0.1, wantedY * 0.08));
            if (wantedY < -1.0) {
               dy = Math.min(dy, -0.12);
            }
         } else {
            dy = 0.1;
         }

         double speed = underwaterMine ? 0.18 : 0.22;
         this.setDeltaMovement(this.getDeltaMovement().scale(0.55).add(dir.x * speed, dy, dir.z * speed));
         this.hurtMarked = true;
         if (this.waterTravelCooldown > 0) {
            this.waterTravelCooldown--;
         } else {
            this.waterTravelCooldown = underwaterMine ? 28 + this.scanPhase(28) : 45 + this.scanPhase(45);
            if (!underwaterMine) {
               BlockPos exit = this.findWaterExit(level, goal, dir);
               if (exit != null && this.getNavigation().isDone()) {
                  this.getNavigation().moveTo(exit.getX() + 0.5, exit.getY(), exit.getZ() + 0.5, this.workerMoveSpeed());
               }
            } else if (this.getNavigation().isDone() || this.tickCount % 60 == this.scanPhase(60)) {
               BlockPos stand = ArsWeissClonePathing.findSafeStandNear(level, goal, 8, 12, 18);
               BlockPos move = stand == null ? goal : stand;
               this.getNavigation().moveTo(move.getX() + 0.5, move.getY(), move.getZ() + 0.5, this.workerMoveSpeed());
            }
         }
      }
   }

   private boolean isUnderwaterGoal(ServerLevel level, BlockPos goal) {
      return goal != null && level.isLoaded(goal)
         ? level.getFluidState(goal).is(FluidTags.WATER)
            || level.getFluidState(goal.above()).is(FluidTags.WATER)
            || level.getFluidState(goal.below()).is(FluidTags.WATER)
         : false;
   }

   private BlockPos findWaterExit(ServerLevel level, BlockPos goal, Vec3 dir) {
      BlockPos current = this.blockPosition();
      int[] distances = new int[]{8, 16, 32, 48, 64};

      for (int distance : distances) {
         BlockPos ahead = current.offset((int)Math.round(dir.x * distance), 0, (int)Math.round(dir.z * distance));
         BlockPos stand = ArsWeissClonePathing.findSurfaceStandNear(level, ahead, 10);
         if (stand != null && !level.getFluidState(stand.below()).is(FluidTags.WATER)) {
            return stand.immutable();
         }
      }

      BlockPos nearGoal = ArsWeissClonePathing.findSurfaceStandNear(level, goal, 18);
      return nearGoal != null && !level.getFluidState(nearGoal.below()).is(FluidTags.WATER) && current.distSqr(nearGoal) < 16384.0
         ? nearGoal.immutable()
         : null;
   }

   private void supportLongRangeTravel(ServerLevel level, BlockPos goal, boolean miningGoal) {
      if (goal != null) {
         if (this.getNavigation().isDone() && this.tickCount % 80 == this.scanPhase(80)) {
            this.getNavigation().moveTo(goal.getX() + 0.5, goal.getY(), goal.getZ() + 0.5, this.workerMoveSpeed());
         }
      }
   }

   private BlockPos nextLongTravelHop(ServerLevel level, BlockPos goal, boolean miningGoal) {
      Vec3 toGoal = Vec3.atCenterOf(goal).subtract(this.position());
      Vec3 flat = new Vec3(toGoal.x, 0.0, toGoal.z);
      if (flat.lengthSqr() < 1.0) {
         return ArsWeissClonePathing.findSurfaceStandNear(level, goal, 24);
      }

      Vec3 dir = flat.normalize();
      double distance = Math.sqrt(flat.lengthSqr());
      double hopDistance = Math.min(distance - 32.0, miningGoal ? 768.0 : 1536.0);
      hopDistance = Math.max(128.0, hopDistance);
      BlockPos raw = BlockPos.containing(this.getX() + dir.x * hopDistance, this.getY(), this.getZ() + dir.z * hopDistance);
      BlockPos stand = ArsWeissClonePathing.findSurfaceStandNear(level, raw, 24);
      if (stand != null) {
         return stand.immutable();
      }

      BlockPos water = this.waterSurfaceAt(level, raw);
      return water != null ? water.immutable() : raw.immutable();
   }

   private void supportCaveMining(ServerPlayer owner, ServerLevel level, BlockPos goal) {
      if (this.caveTunnelCooldown > 0) {
         this.caveTunnelCooldown--;
      } else {
         this.caveTunnelCooldown = 6 + this.scanPhase(6);
         double horizontal = this.horizontalDistanceSqr(goal);
         boolean underground = goal.getY() + 6 < this.getY() || horizontal < 144.0;
         if (underground || !(horizontal > 4096.0)) {
            this.digTravelTunnel(owner, level, goal, this.unresolvedTicks > 1200 ? 5 : 3);
         }
      }
   }

   private double horizontalDistanceSqr(BlockPos goal) {
      double dx = goal.getX() + 0.5 - this.getX();
      double dz = goal.getZ() + 0.5 - this.getZ();
      return dx * dx + dz * dz;
   }

   private void digTravelTunnel(ServerPlayer owner, ServerLevel level, BlockPos goal, int maxBreaks) {
      Vec3 from = this.position().add(0.0, 0.35, 0.0);
      Vec3 to = Vec3.atCenterOf(goal);
      Vec3 dir = to.subtract(from);
      if (!(dir.lengthSqr() < 1.0)) {
         dir = dir.normalize();
         int broken = 0;

         for (double d = 1.0; d <= 7.0 && broken < maxBreaks; d += 0.75) {
            BlockPos feet = BlockPos.containing(from.add(dir.scale(d)));
            BlockPos head = feet.above();
            broken += this.breakTunnelBlock(owner, level, feet, maxBreaks - broken);
            if (broken >= maxBreaks) {
               break;
            }

            broken += this.breakTunnelBlock(owner, level, head, maxBreaks - broken);
         }

         if (broken > 0) {
            this.collectUsefulDrops(owner);
            this.stuckTicks = 0;
         }
      }
   }

   private int breakTunnelBlock(ServerPlayer owner, ServerLevel level, BlockPos pos, int remaining) {
      if (remaining > 0 && level.isLoaded(pos)) {
         BlockState state = level.getBlockState(pos);
         if (!state.isAir() && this.canBreakBlock(level, owner, pos, state)) {
            if (state.getDestroySpeed(level, pos) > Math.min(5.0F, ((Double)cfg().parallelBodyWorkerMineHardnessLimit.get()).floatValue())) {
               return 0;
            }

            level.destroyBlock(pos, true, this);
            level.levelEvent(2001, pos, Block.getId(state));
            this.minedBlocks++;
            return 1;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private boolean canBreakBlock(ServerLevel level, ServerPlayer owner, BlockPos pos, BlockState state) {
      if (state.isAir()) {
         return false;
      }

      if (state.getDestroySpeed(level, pos) < 0.0F) {
         return false;
      }

      if (state.getDestroySpeed(level, pos) > (Double)cfg().parallelBodyWorkerMineHardnessLimit.get()) {
         return false;
      }

      if (!(Boolean)cfg().parallelBodyCanBreakBlockEntities.get() && level.getBlockEntity(pos) != null) {
         return false;
      }

      ManasSkillInstance instance = SkillUtils.getSkillOrNull(owner, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
      return !((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker()).grief(instance, level, owner, pos.getX(), pos.getY(), pos.getZ()).isFalse();
   }

   private void returnToOwner(ServerPlayer owner) {
      this.compactCargo();
      double range = Math.max(2.0, (Double)cfg().parallelBodyReturnDistance.get());
      if (this.distanceToSqr(owner) > range * range) {
         if (!this.teleportNearOwnerIfNeeded(owner, 4096.0)) {
            this.getNavigation().moveTo(owner, this.workerMoveSpeed());
            this.recoverIfStuck(owner.blockPosition());
         }
      } else if (this.cargoStacks.isEmpty()) {
         if (this.workQueue.isEmpty() && this.minedBlocks <= 0) {
            this.clearOrder();
         }

         this.followOwner(owner);
      } else {
         this.depositCargoToOwner(owner, true);
         this.workQueue.removeIf(ItemStack::isEmpty);
         if (this.workQueue.isEmpty()) {
            this.clearOrder();
            this.releaseForcedChunk();
         } else {
            this.requestedStack = this.currentWantedStack().copyWithCount(1);
            this.hasTargetBlock = false;
            this.returning = false;
            this.minedBlocks = 0;
            this.workDelay = 0;
         }

         this.level().playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.8F, 1.3F);
      }
   }

   private boolean depositCargoToOwner(ServerPlayer owner, boolean force) {
      if (owner == null) {
         return false;
      }

      this.compactCargo();
      if (this.cargoStacks.isEmpty()) {
         return false;
      }

      if (force || owner.level().dimension() == this.level().dimension() && !(this.distanceToSqr(owner) > 25.0)) {
         List<ItemStack> remainingCargo = new ArrayList<>();
         List<ItemStack> delivered = new ArrayList<>();

         for (ItemStack cargo : this.cargoStacks) {
            if (!cargo.isEmpty()) {
               ItemStack original = cargo.copy();
               ItemStack stack = cargo.copy();
               boolean fullyAdded = owner.getInventory().add(stack);
               int left = stack.isEmpty() ? 0 : stack.getCount();
               int moved = Math.max(0, original.getCount() - left);
               if (moved > 0) {
                  delivered.add(original.copyWithCount(moved));
               }

               if (!fullyAdded && !stack.isEmpty()) {
                  remainingCargo.add(stack.copy());
               }
            }
         }

         this.cargoStacks.clear();
         this.cargoStack = ItemStack.EMPTY;

         for (ItemStack stack : remainingCargo) {
            this.addCargoStack(stack);
         }

         for (ItemStack stack : delivered) {
            this.subtractDeliveredFromWorkQueue(stack);
         }

         this.workQueue.removeIf(ItemStack::isEmpty);
         if (!delivered.isEmpty()) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.7F, 1.25F);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void subtractDeliveredFromWorkQueue(ItemStack delivered) {
      if (!delivered.isEmpty()) {
         int remaining = delivered.getCount();

         for (ItemStack needed : this.workQueue) {
            if (remaining <= 0) {
               break;
            }

            if (ItemStack.isSameItemSameComponents(needed, delivered) || ItemStack.isSameItem(needed, delivered)) {
               int used = Math.min(remaining, needed.getCount());
               needed.shrink(used);
               remaining -= used;
            }
         }
      }
   }

   private void followOwner(ServerPlayer owner) {
      double follow = Math.max(2.0, (Double)cfg().parallelBodyWorkerFollowDistance.get());
      if (this.distanceToSqr(owner) > follow * follow) {
         if (this.teleportNearOwnerIfNeeded(owner, 4096.0)) {
            return;
         }

         this.getNavigation().moveTo(owner, this.workerMoveSpeed());
         this.recoverIfStuck(owner.blockPosition());
      } else if (this.tickCount % 20 == 0) {
         this.getNavigation().stop();
         this.copyLookFrom(owner);
      }
   }

   private boolean teleportNearOwnerIfNeeded(ServerPlayer owner, double hardDistanceSqr) {
      if (this.level() instanceof ServerLevel level && owner.level().dimension() == this.level().dimension()) {
         double dist = this.distanceToSqr(owner);
         if (dist <= hardDistanceSqr && this.stuckTicks < 80) {
            return false;
         }

         if (dist <= 36.0 && this.stuckTicks < 80) {
            return false;
         }

         if (this.tickCount % 20 != 0 && this.stuckTicks < 80) {
            return false;
         }

         BlockPos safe = ArsWeissClonePathing.findSafeStandNear(level, owner.blockPosition(), 12, 12, 16);
         if (safe == null) {
            safe = owner.blockPosition();
         }

         this.teleportTo(safe.getX() + 0.5, safe.getY(), safe.getZ() + 0.5);
         this.getNavigation().stop();
         this.stuckTicks = 0;
         this.hurtMarked = true;
         return true;
      } else {
         return false;
      }
   }

   private void beginGuardOrder(ServerPlayer owner, String query) {
      this.depositCargoToOwner(owner, true);
      this.armyQuery = "guard:" + (query != null && !query.isBlank() ? query.toLowerCase().trim() : "owner");
      this.armyDelay = 0;
      this.huntTargetName = "";
      this.huntQuery = "";
      this.huntTargetId = null;
      this.requestedStack = ItemStack.EMPTY;
      this.finalRequestedStack = ItemStack.EMPTY;
      this.workQueue.clear();
      this.hasTargetBlock = false;
      this.returning = false;
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
   }

   private void tickLocateOrder(ServerPlayer owner, String query) {
      String hint = ArsWeissCloneTensuraIntel.bestLootPlace(query);
      String search = hint.isBlank() ? query : query + " " + hint;
      if (this.level() instanceof ServerLevel level && this.tickCount % 120 == this.scanPhase(120)) {
         BlockPos remembered = ArsWeissCloneHiveMemory.nearest(
            owner.getUUID(), this.level().dimension().location().toString(), search, this.blockPosition(), 192
         );
         if (remembered != null) {
            BlockPos stand = level.isLoaded(remembered) ? ArsWeissClonePathing.findSurfaceStandNear(level, remembered, 8) : null;
            BlockPos goal = stand == null ? remembered : stand;
            this.lastSearchCenter = goal.immutable();
            this.getNavigation().moveTo(goal.getX() + 0.5, goal.getY(), goal.getZ() + 0.5, this.workerMoveSpeed());
            this.assistExtremeTravel(owner, goal, false);
            this.recoverIfStuck(goal);
            return;
         }
      }

      this.roamForWork(owner, search);
      if (!this.lastSearchCenter.equals(BlockPos.ZERO) && this.tickCount % 200 == this.scanPhase(200)) {
         this.rememberHive(owner, "structure", search, this.lastSearchCenter, 45);
      }
   }

   private void tickGuardOrder(ServerPlayer owner, String query) {
      LivingEntity threat = this.findGuardThreat(owner, query);
      if (threat != null) {
         this.huntLivingTarget(owner, threat, false);
      } else {
         this.followOwner(owner);
      }
   }

   private LivingEntity findGuardThreat(ServerPlayer owner, String query) {
      if (!this.canUseScanBudget(owner, 4)) {
         return null;
      }

      if (!ArsWeissCloneSearchGovernor.allow(owner.getUUID(), this.level().dimension().location().toString(), "guard", this.level().getGameTime(), 160, 1)) {
         return null;
      }

      double radius = Math.min(32.0, Math.max(16.0, ((Integer)cfg().parallelBodyWorkerSearchRadius.get()).intValue()));
      AABB box = owner.getBoundingBox().inflate(radius);
      List<LivingEntity> entities = this.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            box,
            entity -> {
               if (entity.isAlive() && entity != this && entity != owner && !(entity instanceof ArsWeissCloneEntity) && !entity.isAlliedTo(owner)) {
                  if (entity instanceof Player player && player.getUUID().equals(owner.getUUID())) {
                     return false;
                  } else if (entity instanceof Mob targetMob && targetMob.getTarget() == owner) {
                     return true;
                  } else {
                     String normalized = query == null ? "" : query.toLowerCase().trim();
                     return !normalized.isBlank() && !normalized.equals("owner") && !normalized.equals("all") && !normalized.equals("nearby")
                        ? this.isNamedHuntTarget(owner, entity, normalized)
                        : entity instanceof Mob;
                  }
               } else {
                  return false;
               }
            }
         );
      return entities.stream().min(Comparator.comparingDouble(entity -> entity.distanceToSqr(owner))).orElse(null);
   }

   private boolean canMineAround(ServerLevel level) {
      BlockPos base = this.blockPosition();

      for (BlockPos pos : BlockPos.betweenClosed(base.offset(-1, -2, -1), base.offset(1, 2, 1))) {
         if (level.isLoaded(pos)) {
            BlockState state = level.getBlockState(pos);
            if (!state.isAir()) {
               float hardness = state.getDestroySpeed(level, pos);
               if (hardness >= 0.0F && hardness <= Math.max(6.0F, ((Double)cfg().parallelBodyWorkerMineHardnessLimit.get()).floatValue())) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private void recoverIfStuck(BlockPos goal) {
      if (this.tickCount % 20 == this.scanPhase(20)) {
         ItemStack miningTarget = this.currentWantedStack();
         boolean activeTask = this.returning
            || this.hasSendTarget
            || this.hasHuntOrder()
            || this.hasArmyOrder()
            || !miningTarget.isEmpty()
            || !this.workQueue.isEmpty()
            || !this.cargoStacks.isEmpty()
            || !this.cargoStack.isEmpty();
         boolean miningTask = !miningTarget.isEmpty() && this.isMiningMaterialTarget(miningTarget);
         if (!activeTask) {
            this.stuckTicks = 0;
            this.miningNoProgressTicks = 0;
            this.lastWorkX = this.getX();
            this.lastWorkY = this.getY();
            this.lastWorkZ = this.getZ();
         } else {
            double dx = this.getX() - this.lastWorkX;
            double dy = this.getY() - this.lastWorkY;
            double dz = this.getZ() - this.lastWorkZ;
            double moveSq = dx * dx + dy * dy + dz * dz;
            if (this.minedBlocks != this.lastMinedBlocksForStuck) {
               this.lastMinedBlocksForStuck = this.minedBlocks;
               this.stuckTicks = 0;
               this.miningNoProgressTicks = 0;
            } else if (moveSq < 0.04) {
               this.stuckTicks += 20;
               this.miningNoProgressTicks += 20;
            } else {
               this.stuckTicks = 0;
               this.miningNoProgressTicks = Math.max(0, this.miningNoProgressTicks - 80);
            }

            this.lastWorkX = this.getX();
            this.lastWorkY = this.getY();
            this.lastWorkZ = this.getZ();
            int recoveryLimit = miningTask ? 2400 : 180;
            if (this.level() instanceof ServerLevel level && this.miningNoProgressTicks >= recoveryLimit) {
               BlockPos safe = this.stuckRecoverySpot(level, goal != null && !goal.equals(BlockPos.ZERO) ? goal : this.blockPosition());
               if (safe == null) {
                  safe = ArsWeissClonePathing.findSurfaceStandNear(level, this.blockPosition(), 32);
               }

               if (safe != null) {
                  this.teleportTo(safe.getX() + 0.5, safe.getY(), safe.getZ() + 0.5);
                  this.getNavigation().stop();
                  this.setNoGravity(false);
                  this.hasTargetBlock = false;
                  this.blockScanCooldown = 0;
                  this.miningNoProgressTicks = 0;
                  this.stuckTicks = 0;
                  this.chooseMiningDirection();
                  this.hurtMarked = true;
               }
            }
         }
      }
   }

   private BlockPos stuckRecoverySpot(ServerLevel level, BlockPos goal) {
      ItemStack wanted = this.currentWantedStack();
      boolean miningTask = !wanted.isEmpty() && this.isMiningMaterialTarget(wanted);
      boolean miningUnderwater = miningTask && (this.isWaterBlocked(level) || goal != null && this.isUnderwaterGoal(level, goal));
      if (miningUnderwater) {
         BlockPos stand = ArsWeissClonePathing.findSafeStandNear(level, this.blockPosition(), 8, 10, 10);
         return stand != null ? stand : this.blockPosition();
      }

      if (this.isWaterBlocked(level) && !miningUnderwater) {
         Vec3 look = goal == null ? this.getLookAngle() : Vec3.atCenterOf(goal).subtract(this.position());
         Vec3 flat = new Vec3(look.x, 0.0, look.z);
         Vec3 dir = flat.lengthSqr() < 0.01 ? this.getLookAngle() : flat.normalize();
         BlockPos exit = this.findWaterExit(level, goal == null ? this.blockPosition() : goal, dir);
         if (exit != null) {
            return exit;
         }

         BlockPos surface = ArsWeissClonePathing.findSurfaceStandNear(level, this.blockPosition(), 18);
         if (surface != null) {
            return surface;
         }
      }

      ItemStack wantedForRecovery = this.currentWantedStack();
      if (!this.shouldRecoverToSearchSurface() || !wantedForRecovery.isEmpty() && this.isMiningMaterialTarget(wantedForRecovery)) {
         BlockPos safe = goal == null ? null : ArsWeissClonePathing.findSafeStandNear(level, goal, 14, 16, 20);
         if (safe == null) {
            safe = ArsWeissClonePathing.findSafeStandNear(level, this.blockPosition(), 12, 12, 16);
         }

         if (safe == null && goal != null) {
            safe = goal;
         }

         return safe;
      } else {
         return this.searchSurfaceRecoverySpot(level, goal);
      }
   }

   private boolean shouldRecoverToSearchSurface() {
      if (this.returning) {
         return false;
      } else if (this.hasSendTarget) {
         return true;
      } else if (!this.workQueue.isEmpty() || !this.requestedStack.isEmpty() || !this.finalRequestedStack.isEmpty()) {
         return true;
      } else {
         return this.hasHuntOrder() ? true : this.hasArmyOrder() && this.armyQuery != null && this.armyQuery.startsWith("locate:");
      }
   }

   private BlockPos searchSurfaceRecoverySpot(ServerLevel level, BlockPos goal) {
      BlockPos anchor = goal != null && !goal.equals(BlockPos.ZERO) ? goal : this.blockPosition();
      String salt = !this.currentWantedStack().isEmpty() ? this.stackText(this.currentWantedStack()) : this.huntQuery + " " + this.armyQuery;
      if (this.hasSendTarget) {
         BlockPos sendStand = ArsWeissClonePathing.findSurfaceStandNear(level, this.sendTarget, 18);
         if (sendStand != null) {
            return sendStand;
         }
      }

      BlockPos nearGoal = ArsWeissClonePathing.findSurfaceStandNear(level, anchor, 18);
      return nearGoal != null && this.distanceToSqr(Vec3.atCenterOf(nearGoal)) > 36.0
         ? nearGoal
         : this.nextFarSearchSurface(level, anchor, salt, !this.currentWantedStack().isEmpty());
   }

   private void clearHostileOrdersOnly() {
      this.huntTargetName = "";
      this.huntQuery = "";
      this.armyQuery = "";
      this.armyDelay = 0;
      this.huntTargetId = null;
      this.hasTargetBlock = false;
      this.hasSendTarget = false;
      this.targetBlock = BlockPos.ZERO;
      this.sendTarget = BlockPos.ZERO;
   }

   private void beginLocateOrder(ServerPlayer owner, String query) {
      this.armyQuery = "";
      this.huntTargetName = "";
      this.huntQuery = "";
      this.huntTargetId = null;
      this.finalRequestedStack = ItemStack.EMPTY;
      this.requestedStack = ItemStack.EMPTY;
      this.workQueue.clear();
      this.hasTargetBlock = false;
      this.returning = false;
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
      this.armyDelay = 0;
      this.armyQuery = "locate:"
         + ArsWeissCloneTensuraSupport.structureQuery(
            ArsWeissCloneLootIntel.structureHint(query).isBlank() ? query : ArsWeissCloneLootIntel.structureHint(query)
         );
   }

   private void beginLootNameOrder(ServerPlayer owner, String query, int amount) {
      this.depositCargoToOwner(owner, true);
      int requestedAmount = Math.max(1, amount);
      ItemStack wanted = this.findItemStackByQuery(query);
      this.finalRequestedStack = wanted.isEmpty() ? ItemStack.EMPTY : wanted.copyWithCount(requestedAmount);
      this.requestedStack = wanted.isEmpty() ? ItemStack.EMPTY : wanted.copyWithCount(1);
      this.workQueue.clear();
      if (!wanted.isEmpty()) {
         this.workQueue.add(wanted.copyWithCount(requestedAmount));
      }

      this.hasTargetBlock = false;
      this.returning = false;
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
      this.huntTargetName = "";
      this.huntQuery = "";
      this.huntTargetId = null;
      this.armyQuery = "";
      this.armyDelay = 0;
      this.hasSendTarget = false;
      this.lastCommandMode = "loot";
      this.lastCommandQuery = query == null ? "" : query;
      this.lastCommandAmount = requestedAmount;
      if (wanted.isEmpty()) {
         this.armyQuery = "locate:"
            + ArsWeissCloneTensuraSupport.structureQuery(
               ArsWeissCloneLootIntel.structureHint(query).isBlank() ? query : ArsWeissCloneLootIntel.structureHint(query)
            );
      }
   }

   private String requestedText() {
      return this.stackText(this.requestedStack);
   }

   private String stackText(ItemStack stack) {
      if (stack.isEmpty()) {
         return "";
      }

      ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
      String registry = id == null ? "" : id.toString();
      String name = stack.getHoverName().getString();
      return (registry + " " + name).toLowerCase();
   }

   private String stackKey(ItemStack stack) {
      ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
      return id == null ? stack.getHoverName().getString() : id.toString();
   }

   private void clearOrder() {
      this.requestedStack = ItemStack.EMPTY;
      this.finalRequestedStack = ItemStack.EMPTY;
      this.workQueue.clear();
      this.huntTargetName = "";
      this.huntQuery = "";
      this.armyQuery = "";
      this.armyDelay = 0;
      this.huntTargetId = null;
      this.targetBlock = BlockPos.ZERO;
      this.hasTargetBlock = false;
      this.hasSendTarget = false;
      this.sendTarget = BlockPos.ZERO;
      this.compactCargo();
      this.returning = !this.cargoStacks.isEmpty();
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
      this.scanCooldown = Math.max(1, (Integer)cfg().parallelBodyWorkerScanIntervalTicks.get());
      this.commanderLeader = false;
      this.softTravelTicks = 0;
      this.miningModeTicks = 0;
      this.releaseForcedChunk();
   }

   private boolean isAllowedWorker(ServerPlayer owner) {
      return this.isElfariaWorkerClone() && SkillUtils.hasSkill(owner, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
   }

   private boolean hasPermanentOrder() {
      return this.isElfariaWorkerClone() || this.hasActiveWorkOrder();
   }

   private boolean hasActiveWorkOrder() {
      return this.hasSendTarget
         || !this.requestedStack.isEmpty()
         || !this.finalRequestedStack.isEmpty()
         || !this.cargoStack.isEmpty()
         || !this.cargoStacks.isEmpty()
         || !this.workQueue.isEmpty()
         || this.hasHuntOrder()
         || this.hasArmyOrder();
   }

   private void permanentizeOrder() {
      this.entityData.set(LIFE, -1);
   }

   private void signalOrderAccepted() {
      this.level().playSound(null, this.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 1.6F);
      TensuraParticleHelper.spawnServerParticles(
         this.level(), (ParticleOptions)TensuraParticleTypes.SNOWFLAKE.get(), this.getX(), this.getY() + 1.0, this.getZ(), 16, 0.25, 0.35, 0.25, 0.03, true
      );
   }

   private int applyBroadcastToLoadedOwnedClones(ServerPlayer owner, ArsWeissCloneCommandHub.Command command) {
      if (owner != null && command != null) {
         int affected = 0;
         UUID ownerId = owner.getUUID();

         for (ServerLevel level : owner.getServer().getAllLevels()) {
            AABB box = new AABB(-3.0E7, level.getMinBuildHeight(), -3.0E7, 3.0E7, level.getMaxBuildHeight(), 3.0E7);

            for (ArsWeissCloneEntity clone : level.getEntitiesOfClass(
               ArsWeissCloneEntity.class,
               box,
               clonex -> clonex.isAlive() && clonex.isElfariaWorkerClone() && !clonex.isAnchorBody() && ownerId.equals(clonex.getOwnerUUIDValue())
            )) {
               clone.lastGlobalCommandId = command.id();
               if (clone.applyBroadcastCommand(owner, command)) {
                  affected++;
               }
            }
         }

         return affected;
      } else {
         return 0;
      }
   }

   private boolean applyBroadcastCommand(ServerPlayer owner, ArsWeissCloneCommandHub.Command command) {
      if (command.command().equals("dismiss")) {
         this.sendStatus(owner, "dismissed by broadcast");
         this.shatter(false);
         return true;
      }

      if (command.command().equals("return")) {
         this.returning = true;
         this.clearHostileOrdersOnly();
         this.sendStatus(owner, "returning by broadcast");
         return true;
      }

      if (command.command().equals("send")) {
         BlockPos destination = this.parseSendTarget(owner, command.query());
         if (destination != null) {
            this.beginSendOrder(owner, destination);
            this.sendStatus(owner, "send broadcast");
            return true;
         } else {
            return false;
         }
      } else {
         if (command.command().equals("status")) {
            this.sendStatus(owner, "broadcast status");
            return true;
         }

         if (command.command().equals("command")) {
            this.beginCommanderOrder(owner, command.query(), command.amount());
            return true;
         }

         if (command.command().equals("idle")) {
            this.clearOrder();
            this.sendStatus(owner, "stopped by broadcast");
            return true;
         }

         if (command.command().equals("guard")) {
            this.beginGuardOrder(owner, command.query().isBlank() ? "owner" : command.query());
            this.sendStatus(owner, "guard broadcast");
            return true;
         }

         if (command.command().equals("hunt")) {
            this.beginHuntOrder(owner, command.query());
            this.sendStatus(owner, "hunt broadcast");
            return true;
         }

         if (command.command().equals("army")) {
            this.beginArmyOrder(owner, command.query().isBlank() ? "all" : command.query());
            this.sendStatus(owner, "army broadcast");
            return true;
         }

         if (command.command().equals("locate")) {
            this.beginLocateOrder(owner, command.query().isBlank() ? "tensura" : command.query());
            this.sendStatus(owner, "locate broadcast");
            return true;
         }

         if (command.command().equals("gather")) {
            ItemStack wanted = this.findItemStackByQuery(command.query());
            if (!wanted.isEmpty()) {
               wanted.setCount(Math.max(1, command.amount()));
               this.beginGatherOrder(owner, wanted);
               this.sendStatus(owner, "gather broadcast");
            } else {
               this.beginLootNameOrder(owner, command.query(), command.amount());
               this.sendStatus(owner, "loot broadcast");
            }

            return true;
         } else if (command.command().equals("mine")) {
            ItemStack wanted = this.findItemStackByQuery(command.query());
            if (!wanted.isEmpty()) {
               wanted.setCount(Math.max(1, command.amount()));
               this.beginMineOrder(owner, wanted);
               this.sendStatus(owner, "mine broadcast");
               return true;
            } else {
               return false;
            }
         } else if (command.command().equals("loot")) {
            this.beginLootNameOrder(owner, command.query(), command.amount());
            this.sendStatus(owner, "loot broadcast");
            return true;
         } else {
            return false;
         }
      }
   }

   private void tickGlobalOwnerCommand(ServerPlayer owner) {
      ArsWeissCloneCommandHub.Command command = ArsWeissCloneCommandHub.latest(this.getOwnerUUIDValue());
      if (command != null && command.id() > this.lastGlobalCommandId) {
         this.lastGlobalCommandId = command.id();
         this.applyBroadcastCommand(owner, command);
      }
   }

   private void scanOwnerCommandTags(ServerPlayer owner) {
      if (this.tickCount % 120 == this.scanPhase(120) && this.canUseScanBudget(owner, 1)) {
         double range = Math.max(8.0, (Double)cfg().parallelBodyBlueprintRange.get());
         AABB box = owner.getBoundingBox().inflate(range);

         for (ItemEntity item : owner.level()
            .getEntitiesOfClass(
               ItemEntity.class,
               box,
               itemx -> itemx.isAlive() && !itemx.getItem().isEmpty() && itemx.getItem().is(Items.NAME_TAG) && itemx.getItem().has(DataComponents.CUSTOM_NAME)
            )) {
            if (this.tryAcceptGlobalCommandDrop(owner, item, item.getItem())) {
               return;
            }
         }
      }
   }

   private void tickStatusReport(ServerPlayer owner) {
      if (this.statusReportCooldown > 0) {
         this.statusReportCooldown--;
      }

      if (!this.hasActiveWorkOrder()) {
         this.unresolvedTicks = 0;
      } else {
         if (this.tickCount % 200 == 0) {
            ItemStack target = this.currentWantedStack();
            if (!target.isEmpty() && this.cargoStacks.isEmpty() && this.cargoStack.isEmpty()) {
               this.unresolvedTicks += 200;
            } else {
               this.unresolvedTicks = Math.max(0, this.unresolvedTicks - 400);
            }
         }

         if (this.unresolvedTicks >= 24000 && this.statusReportCooldown <= 0) {
            this.statusReportCooldown = 24000;
            this.sendStatus(owner, "still searching");
         }
      }
   }

   private void sendStatus(ServerPlayer owner, String reason) {
      if (owner != null) {
         String mode = this.returning
            ? "returning"
            : (
               this.hasSendTarget
                  ? "send"
                  : (
                     this.hasHuntOrder()
                        ? "hunt"
                        : (
                           this.hasArmyOrder()
                              ? (this.armyQuery.startsWith("guard:") ? "guard" : (this.armyQuery.startsWith("locate:") ? "locate" : "army"))
                              : (
                                 !this.currentWantedStack().isEmpty()
                                    ? (this.isLootOrder() ? "loot" : "gather")
                                    : (this.hasActiveWorkOrder() ? (this.isLootOrder() ? "loot" : "working") : "idle")
                              )
                        )
                  )
            );
         ItemStack targetStack = this.currentWantedStack();
         String target = targetStack.isEmpty() ? this.requestedText() : targetStack.getHoverName().getString() + " x" + targetStack.getCount();
         if (target == null || target.isBlank()) {
            target = this.hasSendTarget
               ? this.sendTarget.toShortString()
               : (
                  this.hasHuntOrder()
                     ? (this.huntTargetName != null && !this.huntTargetName.isBlank() ? this.huntTargetName : this.huntQuery)
                     : (this.hasArmyOrder() ? this.armyQuery : "none")
               );
         }

         int neededTotal = 0;
         int remainingTotal = 0;

         for (ItemStack needed : this.workQueue) {
            if (!needed.isEmpty()) {
               neededTotal += needed.getCount();
               remainingTotal += Math.max(0, needed.getCount() - this.countCargo(needed));
            }
         }

         String goal = this.hasTargetBlock ? this.targetBlock.toShortString() : (this.hasSendTarget ? this.sendTarget.toShortString() : "none");
         String nav = this.getNavigation().isDone() && this.hasActiveWorkOrder() ? "searching" : (this.getNavigation().isDone() ? "idle" : "moving");
         String last = this.lastCommandMode
            + (this.lastCommandQuery != null && !this.lastCommandQuery.isBlank() ? ":" + this.lastCommandQuery : "")
            + " x"
            + this.lastCommandAmount;
         owner.displayClientMessage(
            ArsWeissCloneStatusFormatter.line(
               this.getId(),
               reason,
               mode,
               target,
               this.totalCargoCount(),
               remainingTotal,
               neededTotal,
               goal,
               nav,
               this.stuckTicks,
               last,
               ArsWeissCloneHiveMemory.discoveryCount(owner.getUUID()),
               ArsWeissCloneHiveMemory.cloneCount(owner.getUUID()),
               this.lastSearchCenter,
               this.searchRadius,
               this.blockPosition(),
               this.commanderLeader
            ),
            false
         );
      }
   }

   private void beginArmyOrder(ServerPlayer owner, String query) {
      this.depositCargoToOwner(owner, true);
      this.armyQuery = query != null && !query.isBlank() ? query.toLowerCase().trim() : "all";
      this.armyDelay = 0;
      this.huntTargetName = "";
      this.huntQuery = "";
      this.huntTargetId = null;
      this.requestedStack = ItemStack.EMPTY;
      this.finalRequestedStack = ItemStack.EMPTY;
      this.workQueue.clear();
      this.hasTargetBlock = false;
      this.returning = false;
      this.minedBlocks = 0;
      this.miningModeTicks = 0;
      this.miningNoProgressTicks = 0;
      this.lastMinedBlocksForStuck = this.minedBlocks;
      this.roamGoalTicks = 0;
      this.roamNoProgressTicks = 0;
      this.workDelay = 0;
   }

   private boolean hasArmyOrder() {
      return this.armyQuery != null && !this.armyQuery.isBlank();
   }

   private void tickArmyOrder(ServerPlayer owner) {
      if (!this.isElfariaWorkerClone()) {
         this.clearOrder();
         this.releaseForcedChunk();
         this.followOwner(owner);
      } else if (this.armyQuery != null && this.armyQuery.startsWith("locate:")) {
         this.tickLocateOrder(owner, this.armyQuery.substring(7));
      } else if (this.armyQuery != null && this.armyQuery.startsWith("guard:")) {
         this.tickGuardOrder(owner, this.armyQuery.substring(6));
      } else {
         if (this.armyDelay > 0) {
            this.armyDelay--;
         }

         LivingEntity target = this.findArmyTarget(owner);
         if (target == null) {
            this.roamForWork(owner, this.armyQuery);
         } else if (target.level().dimension() != this.level().dimension()) {
            this.roamForWork(owner, this.armyQuery);
         } else {
            this.keepTaskChunkLoaded();
            double dist = this.distanceToSqr(target);
            if (dist > 16.0) {
               this.getNavigation().moveTo(target, this.workerMoveSpeed());
               this.assistExtremeTravel(owner, target.blockPosition(), false);
               this.breakPathObstruction(owner, target.blockPosition());
            } else {
               this.getNavigation().stop();
               this.lookAt(target, 80.0F, 80.0F);
               if (this.armyDelay <= 0) {
                  this.armyDelay = 30;
                  if (ArsWeissCloneTensuraSupport.forceTameForOwner(owner, target)) {
                     this.level().playSound(null, this.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.9F, 1.2F);
                     return;
                  }
               }

               if (target instanceof Mob targetMob && targetMob.getTarget() == owner) {
                  this.setTarget(target);
                  if (this.tickCount % 8 == 0) {
                     this.doHurtTarget(target);
                     this.swing(InteractionHand.MAIN_HAND);
                  }
               }
            }
         }
      }
   }

   private LivingEntity findArmyTarget(ServerPlayer owner) {
      if (!this.canUseScanBudget(owner, 4)) {
         return null;
      }

      if (!ArsWeissCloneSearchGovernor.allow(owner.getUUID(), this.level().dimension().location().toString(), "army", this.level().getGameTime(), 200, 1)) {
         return null;
      }

      double radius = Math.min(40.0, Math.max(20.0, ((Integer)cfg().parallelBodyWorkerSearchRadius.get()).intValue()));
      AABB box = this.getBoundingBox().inflate(radius);
      List<LivingEntity> entities = this.level()
         .getEntitiesOfClass(LivingEntity.class, box, entity -> ArsWeissCloneTensuraSupport.matchesArmyQuery(owner, entity, this.armyQuery));
      return entities.stream().min(Comparator.comparingDouble(entity -> {
         double score = entity.distanceToSqr(this);
         ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
         String type = id == null ? "" : id.toString().toLowerCase();
         if (type.contains("tensura")) {
            score -= 2000.0;
         }

         if (entity instanceof Mob targetMob && targetMob.getTarget() == owner) {
            score -= 1000.0;
         }

         return score;
      })).orElse(null);
   }

   private boolean hasHuntOrder() {
      return this.huntTargetId != null || this.huntTargetName != null && !this.huntTargetName.isBlank() || this.huntQuery != null && !this.huntQuery.isBlank();
   }

   private void tickHuntOrder(ServerPlayer owner) {
      if (!this.isElfariaWorkerClone()) {
         this.clearOrder();
         this.releaseForcedChunk();
         this.followOwner(owner);
      } else {
         ServerPlayer playerTarget = null;
         if (this.huntTargetId != null) {
            playerTarget = owner.getServer().getPlayerList().getPlayer(this.huntTargetId);
         }

         if (playerTarget == null && this.huntTargetName != null && !this.huntTargetName.isBlank()) {
            playerTarget = owner.getServer().getPlayerList().getPlayerByName(this.huntTargetName);
         }

         if (playerTarget != null && playerTarget.isAlive() && !playerTarget.getUUID().equals(owner.getUUID())) {
            this.huntLivingTarget(owner, playerTarget, true);
         } else if (this.huntQuery != null && !this.huntQuery.isBlank()) {
            LivingEntity target = this.findGenericHuntTarget(owner, this.huntQuery);
            if (target == null) {
               this.roamForWork(owner, this.huntQuery);
            } else {
               this.huntLivingTarget(owner, target, false);
            }
         } else {
            this.clearOrder();
            this.followOwner(owner);
         }
      }
   }

   private LivingEntity findGenericHuntTarget(ServerPlayer owner, String query) {
      String normalized = this.normalizeSearchText(query);

      for (ServerPlayer player : owner.getServer().getPlayerList().getPlayers()) {
         if (player != owner && player.isAlive()) {
            String name = this.normalizeSearchText(player.getGameProfile().getName());
            if (name.equals(normalized) || name.contains(normalized) || normalized.contains(name)) {
               this.huntTargetName = player.getGameProfile().getName();
               this.huntTargetId = player.getUUID();
               this.huntQuery = "";
               return player;
            }
         }
      }

      if (!this.canUseScanBudget(owner, 4)) {
         return null;
      }

      if (!ArsWeissCloneSearchGovernor.allow(owner.getUUID(), this.level().dimension().location().toString(), "hunt", this.level().getGameTime(), 160, 1)) {
         return null;
      }

      AABB box = this.getBoundingBox().inflate(Math.min(40.0, Math.max(20.0, ((Integer)cfg().parallelBodyWorkerSearchRadius.get()).intValue())));
      List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, box, entity -> this.isNamedHuntTarget(owner, entity, normalized));
      return entities.stream().min(Comparator.comparingDouble(entity -> ArsWeissCloneCombatTactics.targetScore(this, owner, entity, normalized))).orElse(null);
   }

   private boolean isNamedHuntTarget(ServerPlayer owner, LivingEntity entity, String query) {
      if (entity.isAlive() && entity != this && entity != owner && !entity.isAlliedTo(owner)) {
         if (entity instanceof Player player && player.getUUID().equals(owner.getUUID())) {
            return false;
         } else {
            if (ArsWeissCloneTensuraSupport.matchesTensuraEntityQuery(entity, query)) {
               return true;
            }

            ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            String type = id == null ? "" : id.toString().toLowerCase();
            String name = entity.getName().getString().toLowerCase();

            for (String part : query.split("[ _:-]+")) {
               if (part.length() >= 2 && (type.contains(part) || name.contains(part))) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private void huntLivingTarget(ServerPlayer owner, LivingEntity target, boolean allowTeleport) {
      if (target.level().dimension() != this.level().dimension()) {
         this.roamForWork(owner, target.getName().getString());
      } else {
         this.keepTaskChunkLoaded();
         this.rememberHive(
            owner,
            target instanceof Player ? "player" : "mob",
            ArsWeissCloneHiveMemory.entityQuery(target),
            target.blockPosition(),
            target instanceof Player ? 120 : 70
         );
         this.setTarget(target);
         if (!(this.level() instanceof ServerLevel serverLevel && this.supportUnderwaterCombat(owner, serverLevel, target))) {
            double dist = this.distanceToSqr(target);
            if (dist > 9.0) {
               Vec3 tactic = ArsWeissCloneCombatTactics.approachPoint(this, owner, target);
               this.getNavigation().moveTo(tactic.x, tactic.y, tactic.z, this.workerMoveSpeed());
               ArsWeissCloneCombatTactics.applyCombatMotion(this, owner, target);
               this.assistExtremeTravel(owner, target.blockPosition(), false);
               this.breakPathObstruction(owner, target.blockPosition());
            } else {
               this.getNavigation().stop();
               this.lookAt(target, 80.0F, 80.0F);
               Vec3 away = this.position().subtract(target.position());
               if (away.lengthSqr() > 0.001 && dist < 2.25) {
                  this.setDeltaMovement(this.getDeltaMovement().add(away.normalize().scale(0.12)));
               }

               ArsWeissCloneCombatTactics.applyCombatMotion(this, owner, target);
               if (this.tickCount % 7 == 0) {
                  this.doHurtTarget(target);
                  this.swing(InteractionHand.MAIN_HAND);
               }
            }
         }
      }
   }

   private boolean supportUnderwaterCombat(ServerPlayer owner, ServerLevel level, LivingEntity target) {
      if (target != null && target.isAlive()) {
         boolean targetWater = target.isInWaterOrBubble()
            || level.getFluidState(target.blockPosition()).is(FluidTags.WATER)
            || level.getFluidState(target.blockPosition().above()).is(FluidTags.WATER);
         boolean needsDive = targetWater && target.getY() + 0.75 < this.getY();
         boolean caveBelow = !targetWater && target.getY() + 1.5 < this.getY() && this.horizontalDistanceSqr(target.blockPosition()) < 64.0;
         if (!needsDive && !caveBelow) {
            return false;
         }

         if (caveBelow) {
            this.breakPathObstruction(owner, target.blockPosition());
         }

         if (needsDive) {
            this.setAirSupply(this.getMaxAirSupply());
            this.getNavigation().stop();
            Vec3 to = target.getEyePosition().subtract(this.position());
            Vec3 dir = to.lengthSqr() < 0.01 ? new Vec3(0.0, -1.0, 0.0) : to.normalize();
            double dy = Math.max(-0.28, Math.min(0.12, dir.y * 0.24));
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5).add(dir.x * 0.18, dy, dir.z * 0.18));
            this.hurtMarked = true;
         } else {
            Vec3 pos = target.position();
            this.getNavigation().moveTo(pos.x, pos.y, pos.z, this.workerMoveSpeed());
         }

         double reach = needsDive ? 64.0 : 9.0;
         if (this.distanceToSqr(target) <= reach && this.tickCount % 9 == 0) {
            this.lookAt(target, 80.0F, 80.0F);
            this.doHurtTarget(target);
            this.swing(InteractionHand.MAIN_HAND);
         }

         this.recoverIfStuck(target.blockPosition());
         return true;
      } else {
         return false;
      }
   }

   private void keepTaskChunkLoaded() {
      if (!(this.isElfariaWorkerClone() && (Boolean)cfg().parallelBodyForceChunks.get() && this.level() instanceof ServerLevel level)) {
         this.releaseForcedChunk();
      } else if (!this.hasActiveWorkOrder()) {
         this.releaseForcedChunk();
      } else {
         ArsWeissCloneChunkTickets.prune(level, this.level().getGameTime());
         Set<Long> wanted = new HashSet<>();
         ChunkPos current = new ChunkPos(this.blockPosition());
         this.forceChunk(level, current, wanted);
         BlockPos goal = this.activeChunkLoadGoal();
         if (goal != null && !goal.equals(BlockPos.ZERO)) {
            ChunkPos goalChunk = new ChunkPos(goal);
            ChunkPos next = this.nextNeededChunk(current, goalChunk);
            this.forceChunk(level, next, wanted);
            if (Math.abs(goalChunk.x - current.x) <= 2 && Math.abs(goalChunk.z - current.z) <= 2) {
               this.forceChunk(level, goalChunk, wanted);
            }
         }

         this.releaseChunksExcept(level, wanted);
      }
   }

   private BlockPos activeChunkLoadGoal() {
      ServerPlayer owner = this.getOwnerPlayer();
      if (owner != null) {
         BlockPos goal = this.currentMobilityGoal(owner);
         if (goal != null && !goal.equals(BlockPos.ZERO)) {
            return goal;
         }
      }

      if (this.hasSendTarget) {
         return this.sendTarget;
      } else if (this.hasTargetBlock) {
         return this.targetBlock;
      } else {
         return this.lastSearchCenter != null && !this.lastSearchCenter.equals(BlockPos.ZERO) ? this.lastSearchCenter : this.blockPosition();
      }
   }

   private ChunkPos nextNeededChunk(ChunkPos current, ChunkPos goal) {
      int dx = Integer.compare(goal.x, current.x);
      int dz = Integer.compare(goal.z, current.z);
      return new ChunkPos(current.x + dx, current.z + dz);
   }

   private void forceChunk(ServerLevel level, ChunkPos chunk, Set<Long> wanted) {
      if (chunk != null && wanted.size() < 3) {
         long packed = chunk.toLong();
         wanted.add(packed);
         if (this.forcedChunks.contains(packed)) {
            ArsWeissCloneChunkTickets.touch(level, chunk);
         } else {
            if (ArsWeissCloneChunkTickets.force(level, chunk)) {
               this.forcedChunks.add(packed);
            }
         }
      }
   }

   private void releaseChunksExcept(ServerLevel level, Set<Long> wanted) {
      List<Long> removed = new ArrayList<>();

      for (Long packed : this.forcedChunks) {
         if (!wanted.contains(packed)) {
            ChunkPos chunk = new ChunkPos(packed);
            ArsWeissCloneChunkTickets.release(level, chunk);
            removed.add(packed);
         }
      }

      this.forcedChunks.removeAll(removed);
   }

   private void releaseForcedChunk() {
      if (!this.forcedChunks.isEmpty() && this.level() instanceof ServerLevel level) {
         for (Long packed : new ArrayList<>(this.forcedChunks)) {
            ArsWeissCloneChunkTickets.release(level, new ChunkPos(packed));
         }
      }

      this.forcedChunks.clear();
   }

   private void tickMirroredCast(ServerPlayer owner) {
      ManasSkill skill = this.magicId == null ? null : (ManasSkill)SkillAPI.getSkillRegistry().get(this.magicId);
      if (skill instanceof Magic magic) {
         if (this.mirroredInstance == null) {
            Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(owner).getSkill(skill);
            if (optional.isEmpty()) {
               this.stopMirroredCast();
               return;
            }

            this.mirroredInstance = ManasSkillInstance.fromNBT(optional.get().toNBT());
         }

         this.lookAtVector(this.storedLook);
         if (!this.preparedCast) {
            this.prepareMirroredCast(owner, magic);
         }

         this.safeHeldTick(magic);
         if (this.tickCount % 4 == 0) {
            TensuraParticleHelper.spawnServerParticles(
               this.level(),
               (ParticleOptions)TensuraParticleTypes.SNOWFLAKE_EFFECT.get(),
               this.getX(),
               this.getY() + 1.0,
               this.getZ(),
               5,
               0.2,
               0.35,
               0.2,
               0.02,
               true
            );
         }

         this.heldTicks++;
         if (this.heldTicks >= this.castTime && !this.releaseAttempted) {
            this.releaseAttempted = true;
            this.releaseMirroredMagic(owner, magic);
            this.stopMirroredCast();
         }
      } else {
         this.stopMirroredCast();
      }
   }

   private void prepareMirroredCast(ServerPlayer owner, Magic magic) {
      this.preparedCast = true;
      this.copyEquipment(owner);
      this.copyCastingAttributes(owner);
      this.createCloneMagicCircle(magic, 30);

      try {
         magic.onPressed(this.mirroredInstance, this, this.keyNumber, this.magicMode);
      } catch (Throwable var5) {
      }

      try {
         magic.addHeldAttributeModifiers(this.mirroredInstance, this, this.magicMode);
      } catch (Throwable var4) {
      }

      this.safeHeldTick(magic);
   }

   private void safeHeldTick(Magic magic) {
      try {
         magic.addHeldAttributeModifiers(this.mirroredInstance, this, this.magicMode);
      } catch (Throwable var4) {
      }

      this.createCloneMagicCircle(magic, 30);

      try {
         magic.onHeld(this.mirroredInstance, this, Math.max(0, this.heldTicks), this.magicMode);
      } catch (Throwable var3) {
      }
   }

   private boolean releaseMirroredMagic(ServerPlayer owner, Magic magic) {
      if (this.magicId == null || this.mirroredInstance == null) {
         return false;
      }

      if (AlbisVinaHandler.isFormulaBlacklisted(this.magicId)) {
         return false;
      }

      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(owner).getSkill(magic);
      if (optional.isEmpty()) {
         return false;
      }

      ManasSkillInstance ownerInstance = optional.get();
      int oldCloneCooldown = this.mirroredInstance.getCoolDown(this.magicMode);
      int oldOwnerCooldown = ownerInstance.getCoolDown(this.magicMode);

      try {
         magic.addHeldAttributeModifiers(this.mirroredInstance, this, this.magicMode);
      } catch (Throwable var9) {
      }

      this.createCloneMagicCircle(magic, 30);

      try {
         magic.onRelease(this.mirroredInstance, this, Math.max(this.heldTicks, this.castTime + 5), this.keyNumber, this.magicMode);
      } catch (Throwable ignored) {
         this.cleanupMagic(magic);
         return false;
      }

      int cloneCooldown = this.mirroredInstance.getCoolDown(this.magicMode);
      if (cloneCooldown > oldCloneCooldown && cloneCooldown > oldOwnerCooldown) {
         ownerInstance.setCoolDown(Math.max(ownerInstance.getCoolDown(this.magicMode), cloneCooldown), this.magicMode);
      }

      ownerInstance.addMasteryPoint(owner);
      ownerInstance.markDirty();
      this.cleanupMagic(magic);
      return true;
   }

   private void createCloneMagicCircle(Magic magic, int life) {
      if (this.mirroredInstance != null) {
         try {
            CompoundTag tag = this.mirroredInstance.getOrCreateTag();
            MagicCircle.castMagicCircle(
               "MagicCircleID",
               1.35F,
               life,
               MagicCircleVariant.ICE,
               true,
               this,
               tag,
               0.85F,
               0.0F,
               new Vec3(0.0, 0.25, 0.0),
               this.mirroredInstance,
               this.magicMode,
               Pair.of(0.0, 0.0)
            );
         } catch (Throwable var4) {
         }
      }
   }

   private void cleanupMagic(Magic magic) {
      try {
         magic.removeAttributeModifiers(this.mirroredInstance, this, this.magicMode);
      } catch (Throwable var3) {
      }

      if (this.mirroredInstance != null) {
         CompoundTag tag = this.mirroredInstance.getTag();
         if (tag != null) {
            tag.putInt("MagicCircleID", 0);
            this.mirroredInstance.markDirty();
         }
      }
   }

   private void stopMirroredCast() {
      if (this.magicId != null) {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(this.magicId);
         if (skill instanceof Magic magic && this.mirroredInstance != null) {
            this.cleanupMagic(magic);
         }
      }

      this.continuingCast = false;
      this.preparedCast = false;
      this.releaseAttempted = false;
      this.magicId = null;
      this.magicMode = 0;
      this.keyNumber = 0;
      this.heldTicks = 0;
      this.castTime = 0;
      this.mirroredInstance = null;
   }

   private void lookAtVector(Vec3 look) {
      Vec3 normalized = look.normalize();
      double horizontal = Math.sqrt(normalized.x * normalized.x + normalized.z * normalized.z);
      float xRot = (float)(-(Math.atan2(normalized.y, horizontal) * 180.0F / (float)Math.PI));
      float yRot = (float)(Math.atan2(normalized.z, normalized.x) * 180.0F / (float)Math.PI) - 90.0F;
      this.setXRot(xRot);
      this.setYRot(yRot);
      this.setYHeadRot(yRot);
   }

   private void dropStoredCargo() {
      this.compactCargo();
      if (!this.cargoStack.isEmpty()) {
         Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), this.cargoStack.copy());
         this.cargoStack = ItemStack.EMPTY;
      }

      for (ItemStack stack : this.cargoStacks) {
         if (!stack.isEmpty()) {
            Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), stack.copy());
         }
      }

      this.cargoStacks.clear();
   }

   public void shatter(boolean burst) {
      if (!this.level().isClientSide()) {
         ServerPlayer owner = this.getOwnerPlayer();
         if (burst && owner != null) {
            AABB box = this.getBoundingBox().inflate(2.4);
            boolean mastered = SkillUtils.isSkillMastered(owner, (ManasSkill)AllSkills.ALBIS_VINA.get())
               || SkillUtils.isSkillMastered(owner, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());

            for (LivingEntity target : this.level()
               .getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != owner && e != this && !e.isAlliedTo(owner))) {
               AlbisVinaSkill.applyDiamondDust(owner, target, mastered);
            }
         }

         if (!this.isAnchorBody()) {
            TensuraParticleHelper.spawnServerParticles(
               this.level(),
               (ParticleOptions)TensuraParticleTypes.SNOWFLAKE_EFFECT.get(),
               this.getX(),
               this.getY() + 1.0,
               this.getZ(),
               25,
               0.45,
               0.6,
               0.45,
               0.08,
               true
            );
            this.level().playSound(null, this.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.7F, 1.7F);
         }

         this.dropStoredCargo();
         AlbisVinaHandler.forgetClone(this);
         this.releaseForcedChunk();
      }

      this.discard();
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putString("OwnerUUID", (String)this.entityData.get(OWNER_UUID));
      tag.putString("OwnerName", (String)this.entityData.get(OWNER_NAME));
      tag.putInt("Life", (Integer)this.entityData.get(LIFE));
      tag.putBoolean("AnchorBody", this.isAnchorBody());
      tag.putInt("CloneSource", (Integer)this.entityData.get(CLONE_SOURCE));
      tag.putBoolean("ContinuingCast", this.continuingCast);
      tag.putBoolean("PreparedCast", this.preparedCast);
      tag.putBoolean("ReleaseAttempted", this.releaseAttempted);
      if (this.magicId != null) {
         tag.putString("MagicId", this.magicId.toString());
      }

      tag.putInt("MagicMode", this.magicMode);
      tag.putInt("KeyNumber", this.keyNumber);
      tag.putInt("HeldTicks", this.heldTicks);
      tag.putInt("CastTime", this.castTime);
      tag.putDouble("LookX", this.storedLook.x);
      tag.putDouble("LookY", this.storedLook.y);
      tag.putDouble("LookZ", this.storedLook.z);
      if (this.mirroredInstance != null) {
         tag.put("MirroredInstance", this.mirroredInstance.toNBT());
      }

      if (!this.requestedStack.isEmpty()) {
         tag.put("RequestedStack", this.requestedStack.save(this.registryAccess()));
      }

      if (!this.finalRequestedStack.isEmpty()) {
         tag.put("FinalRequestedStack", this.finalRequestedStack.save(this.registryAccess()));
      }

      if (!this.cargoStack.isEmpty()) {
         tag.put("CargoStack", this.cargoStack.save(this.registryAccess()));
      }

      ListTag workList = new ListTag();

      for (ItemStack stack : this.workQueue) {
         if (!stack.isEmpty()) {
            workList.add(stack.save(this.registryAccess()));
         }
      }

      tag.put("WorkQueue", workList);
      ListTag cargoList = new ListTag();

      for (ItemStack stack : this.cargoStacks) {
         if (!stack.isEmpty()) {
            cargoList.add(stack.save(this.registryAccess()));
         }
      }

      tag.put("CargoStacks", cargoList);
      tag.putInt("TargetX", this.targetBlock.getX());
      tag.putInt("TargetY", this.targetBlock.getY());
      tag.putInt("TargetZ", this.targetBlock.getZ());
      tag.putInt("SendX", this.sendTarget.getX());
      tag.putInt("SendY", this.sendTarget.getY());
      tag.putInt("SendZ", this.sendTarget.getZ());
      tag.putBoolean("HasTargetBlock", this.hasTargetBlock);
      tag.putBoolean("HasSendTarget", this.hasSendTarget);
      tag.putInt("MinedBlocks", this.minedBlocks);
      tag.putInt("WorkDelay", this.workDelay);
      tag.putInt("ScanCooldown", this.scanCooldown);
      tag.putInt("DropScanCooldown", this.dropScanCooldown);
      tag.putInt("ContainerScanCooldown", this.containerScanCooldown);
      tag.putInt("MobScanCooldown", this.mobScanCooldown);
      tag.putInt("BlockScanCooldown", this.blockScanCooldown);
      tag.putString("HuntTargetName", this.huntTargetName == null ? "" : this.huntTargetName);
      tag.putString("HuntQuery", this.huntQuery == null ? "" : this.huntQuery);
      tag.putString("ArmyQuery", this.armyQuery == null ? "" : this.armyQuery);
      tag.putInt("ArmyDelay", this.armyDelay);
      tag.putInt("OfflineTicks", this.offlineTicks);
      tag.putInt("StuckTicks", this.stuckTicks);
      tag.putInt("MiningNoProgressTicks", this.miningNoProgressTicks);
      tag.putInt("LastMinedBlocksForStuck", this.lastMinedBlocksForStuck);
      tag.putDouble("LastWorkX", this.lastWorkX);
      tag.putDouble("LastWorkY", this.lastWorkY);
      tag.putDouble("LastWorkZ", this.lastWorkZ);
      tag.putLong("LastGlobalCommandId", this.lastGlobalCommandId);
      tag.putInt("StatusReportCooldown", this.statusReportCooldown);
      tag.putInt("UnresolvedTicks", this.unresolvedTicks);
      tag.putBoolean("Returning", this.returning);
      tag.putString("LastCommandMode", this.lastCommandMode == null ? "" : this.lastCommandMode);
      tag.putString("LastCommandQuery", this.lastCommandQuery == null ? "" : this.lastCommandQuery);
      tag.putInt("LastCommandAmount", this.lastCommandAmount);
      tag.putInt("LastSearchX", this.lastSearchCenter.getX());
      tag.putInt("LastSearchY", this.lastSearchCenter.getY());
      tag.putInt("LastSearchZ", this.lastSearchCenter.getZ());
      tag.putInt("SearchRadius", this.searchRadius);
      tag.putInt("WorldProbeCooldown", this.worldProbeCooldown);
      tag.putInt("FarRoamStep", this.farRoamStep);
      tag.putInt("WaterTravelCooldown", this.waterTravelCooldown);
      tag.putInt("CaveTunnelCooldown", this.caveTunnelCooldown);
      tag.putInt("LongTravelCooldown", this.longTravelCooldown);
      tag.putInt("OceanTravelCooldown", this.oceanTravelCooldown);
      tag.putInt("OceanTravelStep", this.oceanTravelStep);
      tag.putBoolean("CommanderLeader", this.commanderLeader);
      if (this.huntTargetId != null) {
         tag.putUUID("HuntTargetId", this.huntTargetId);
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.entityData.set(OWNER_UUID, tag.getString("OwnerUUID"));
      this.entityData.set(OWNER_NAME, tag.getString("OwnerName"));
      this.entityData.set(LIFE, tag.getInt("Life"));
      this.entityData.set(ANCHOR_BODY, tag.getBoolean("AnchorBody"));
      this.entityData.set(CLONE_SOURCE, tag.contains("CloneSource") ? tag.getInt("CloneSource") : 0);
      this.continuingCast = tag.getBoolean("ContinuingCast");
      this.preparedCast = tag.getBoolean("PreparedCast");
      this.releaseAttempted = tag.getBoolean("ReleaseAttempted");
      if (tag.contains("MagicId")) {
         this.magicId = ResourceLocation.parse(tag.getString("MagicId"));
      }

      this.magicMode = tag.getInt("MagicMode");
      this.keyNumber = tag.getInt("KeyNumber");
      this.heldTicks = tag.getInt("HeldTicks");
      this.castTime = tag.getInt("CastTime");
      this.storedLook = new Vec3(tag.getDouble("LookX"), tag.getDouble("LookY"), tag.getDouble("LookZ"));
      if (tag.contains("MirroredInstance")) {
         this.mirroredInstance = ManasSkillInstance.fromNBT(tag.getCompound("MirroredInstance"));
      }

      if (tag.contains("RequestedStack")) {
         this.requestedStack = ItemStack.parseOptional(this.registryAccess(), tag.getCompound("RequestedStack"));
      }

      if (tag.contains("FinalRequestedStack")) {
         this.finalRequestedStack = ItemStack.parseOptional(this.registryAccess(), tag.getCompound("FinalRequestedStack"));
      }

      if (tag.contains("CargoStack")) {
         this.cargoStack = ItemStack.parseOptional(this.registryAccess(), tag.getCompound("CargoStack"));
      }

      this.workQueue.clear();
      if (tag.contains("WorkQueue")) {
         ListTag workList = tag.getList("WorkQueue", 10);

         for (int i = 0; i < workList.size(); i++) {
            ItemStack stack = ItemStack.parseOptional(this.registryAccess(), workList.getCompound(i));
            if (!stack.isEmpty()) {
               this.workQueue.add(stack);
            }
         }
      }

      this.cargoStacks.clear();
      if (tag.contains("CargoStacks")) {
         ListTag cargoList = tag.getList("CargoStacks", 10);

         for (int i = 0; i < cargoList.size(); i++) {
            ItemStack stack = ItemStack.parseOptional(this.registryAccess(), cargoList.getCompound(i));
            if (!stack.isEmpty()) {
               this.cargoStacks.add(stack);
            }
         }
      }

      this.targetBlock = new BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"));
      this.sendTarget = new BlockPos(tag.getInt("SendX"), tag.getInt("SendY"), tag.getInt("SendZ"));
      this.hasTargetBlock = tag.getBoolean("HasTargetBlock");
      this.hasSendTarget = tag.getBoolean("HasSendTarget");
      this.minedBlocks = tag.getInt("MinedBlocks");
      this.workDelay = tag.getInt("WorkDelay");
      this.scanCooldown = tag.getInt("ScanCooldown");
      this.dropScanCooldown = tag.getInt("DropScanCooldown");
      this.containerScanCooldown = tag.getInt("ContainerScanCooldown");
      this.mobScanCooldown = tag.getInt("MobScanCooldown");
      this.blockScanCooldown = tag.getInt("BlockScanCooldown");
      this.huntTargetName = tag.getString("HuntTargetName");
      this.huntQuery = tag.getString("HuntQuery");
      this.armyQuery = tag.getString("ArmyQuery");
      this.armyDelay = tag.getInt("ArmyDelay");
      this.offlineTicks = tag.getInt("OfflineTicks");
      this.stuckTicks = tag.getInt("StuckTicks");
      this.miningNoProgressTicks = tag.getInt("MiningNoProgressTicks");
      this.lastMinedBlocksForStuck = tag.getInt("LastMinedBlocksForStuck");
      this.lastWorkX = tag.getDouble("LastWorkX");
      this.lastWorkY = tag.getDouble("LastWorkY");
      this.lastWorkZ = tag.getDouble("LastWorkZ");
      this.lastGlobalCommandId = tag.getLong("LastGlobalCommandId");
      this.statusReportCooldown = tag.getInt("StatusReportCooldown");
      this.unresolvedTicks = tag.getInt("UnresolvedTicks");
      this.returning = tag.getBoolean("Returning");
      this.lastCommandMode = tag.getString("LastCommandMode");
      this.lastCommandQuery = tag.getString("LastCommandQuery");
      this.lastCommandAmount = tag.contains("LastCommandAmount") ? tag.getInt("LastCommandAmount") : 1;
      this.lastSearchCenter = new BlockPos(tag.getInt("LastSearchX"), tag.getInt("LastSearchY"), tag.getInt("LastSearchZ"));
      this.searchRadius = tag.getInt("SearchRadius");
      this.worldProbeCooldown = tag.getInt("WorldProbeCooldown");
      this.farRoamStep = tag.getInt("FarRoamStep");
      this.waterTravelCooldown = tag.getInt("WaterTravelCooldown");
      this.caveTunnelCooldown = tag.getInt("CaveTunnelCooldown");
      this.longTravelCooldown = tag.getInt("LongTravelCooldown");
      this.oceanTravelCooldown = tag.getInt("OceanTravelCooldown");
      this.oceanTravelStep = tag.getInt("OceanTravelStep");
      this.commanderLeader = tag.getBoolean("CommanderLeader");
      this.huntTargetId = tag.hasUUID("HuntTargetId") ? tag.getUUID("HuntTargetId") : null;
   }
}
