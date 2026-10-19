package twilightforest.entity.boss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.TFPart;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFDataSerializers;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.util.WorldUtil;
import twilightforest.util.entities.EntityUtil;

public class Hydra extends BaseTFBoss {
   private static final int TICKS_BEFORE_HEALING = 1000;
   private static final int HEAD_RESPAWN_TICKS = 140;
   private static final int HEAD_MAX_DAMAGE = 120;
   private static final float ARMOR_MULTIPLIER = 8.0F;
   private static final int MAX_HEALTH = 360;
   private static float HEADS_ACTIVITY_FACTOR = 0.3F;
   public static final int MAX_HEADS = 7;
   private static final int SECONDARY_FLAME_CHANCE = 10;
   private static final int SECONDARY_MORTAR_CHANCE = 16;
   private static final EntityDataAccessor<List<String>> HEAD_NAMES = SynchedEntityData.defineId(
      Hydra.class, (EntityDataSerializer)TFDataSerializers.STRING_LIST.get()
   );
   public final HydraHeadContainer[] hc = new HydraHeadContainer[7];
   private final HydraPart[] partArray;
   public final HydraSmallPart body;
   private final HydraSmallPart leftLeg;
   private final HydraSmallPart rightLeg;
   private final HydraSmallPart tail;
   private float randomYawVelocity = 0.0F;
   private int ticksSinceDamaged = 0;
   public boolean renderFakeHeads = true;
   private int numTicksToChaseTarget;

   public Hydra(EntityType<? extends Hydra> type, Level level) {
      super(type, level);
      List<HydraPart> parts = new ArrayList<>();
      parts.add(this.body = new HydraSmallPart(this, 6.0F, 6.0F));
      parts.add(this.leftLeg = new HydraSmallPart(this, 2.0F, 3.0F));
      parts.add(this.rightLeg = new HydraSmallPart(this, 2.0F, 3.0F));
      parts.add(this.tail = new HydraSmallPart(this, 6.0F, 2.0F));

      for (int i = 0; i < 7; i++) {
         this.hc[i] = new HydraHeadContainer(this, i, i < 3);
         this.hc[i].headEntity.setCustomName(Component.literal(this.getHeadNameFor(i)));
         parts.add(this.hc[i].headEntity);
         Collections.addAll(parts, this.hc[i].getNeckArray());
      }

      this.partArray = parts.toArray(new HydraPart[0]);
      this.noCulling = true;
      this.xpReward = 511;
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(HEAD_NAMES, List.of("", "", "", "", "", "", ""));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder registerAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 360.0).add(Attributes.MOVEMENT_SPEED, 0.28);
   }

   @Override
   public void checkDespawn() {
      if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
         for (HydraHeadContainer container : this.hc) {
            container.headEntity.discard();
         }
      }

      super.checkDespawn();
   }

   protected float tickHeadTurn(float yRot, float yTurnDelta) {
      float f = Mth.wrapDegrees(yRot - this.yBodyRot);
      this.yBodyRot += f * 0.3F;
      float f1 = Mth.wrapDegrees(this.getYRot() - this.yBodyRot);
      boolean flag = f1 < -90.0F || f1 >= 90.0F;
      if (f1 < -75.0F) {
         f1 = -75.0F;
      }

      if (f1 >= 75.0F) {
         f1 = 75.0F;
      }

      this.yBodyRot = this.getYRot() - f1;
      if (f1 * f1 > 2500.0F) {
         this.yBodyRot += f1 * 0.2F;
      }

      if (flag) {
         yTurnDelta *= -1.0F;
      }

      return yTurnDelta;
   }

   public boolean isPathFinding() {
      return false;
   }

   protected PathNavigation createNavigation(Level level) {
      return new GroundPathNavigation(this, level) {
         public Path createPath(BlockPos pPos, int pAccuracy) {
            return null;
         }

         protected boolean canUpdatePath() {
            return false;
         }
      };
   }

   public void aiStep() {
      if (this.renderFakeHeads) {
         this.renderFakeHeads = false;
      }

      this.clearFire();
      this.body.tick();
      this.leftLeg.tick();
      this.rightLeg.tick();

      for (int i = 0; i < 7; i++) {
         this.hc[i].tick();
      }

      if (this.hurtTime > 0) {
         for (int i = 0; i < 7; i++) {
            this.hc[i].setHurtTime(this.hurtTime);
         }
      }

      this.ticksSinceDamaged++;
      this.setDifficultyVariables();
      super.aiStep();
      float angle = (this.yBodyRot + 180.0F) * (float) Math.PI / 180.0F;
      double dx = this.getX() - Mth.sin(angle) * 3.0;
      double dy = this.getY() + 0.1;
      double dz = this.getZ() + Mth.cos(angle) * 3.0;
      this.body.setPos(dx, dy, dz);
      dx = this.getX() - Mth.sin(angle) * 10.5;
      dy = this.getY() + 0.1;
      dz = this.getZ() + Mth.cos(angle) * 10.5;
      this.tail.setPos(dx, dy, dz);
      if (this.hurtTime == 0) {
         this.collideWithEntities(this.level().getEntities(this, this.body.getBoundingBox()), this.body);
         this.collideWithEntities(this.level().getEntities(this, this.tail.getBoundingBox()), this.tail);
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      byte headData = 0;

      for (int i = 0; i < 7; i++) {
         if (this.hc[i].isActive()) {
            headData |= (byte)(1 << i);
         }
      }

      compound.putByte("NumHeads", headData);
      ListTag headNames = new ListTag();

      for (int i = 0; i < 7; i++) {
         headNames.add(StringTag.valueOf((String)((List)this.getEntityData().get(HEAD_NAMES)).get(i)));
      }

      compound.put("HeadNames", headNames);
      super.addAdditionalSaveData(compound);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.activateHeadsOnLoad(compound.getByte("NumHeads"));
      if (compound.contains("HeadNames", 9)) {
         List<String> names = new ArrayList<>();
         ListTag list = compound.getList("HeadNames", 8);

         for (int i = 0; i < list.size(); i++) {
            String name = list.getString(i);
            names.add(name);
            this.hc[i].headEntity.setCustomName(Component.literal(name));
         }

         this.getEntityData().set(HEAD_NAMES, names);
      }
   }

   private void activateHeadsOnLoad(byte heads) {
      for (int i = 0; i < 7; i++) {
         if ((heads & 1 << i) != 0) {
            this.hc[i].setNextState(HydraHeadContainer.State.IDLE);
            this.hc[i].endCurrentAction();
         }
      }
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
      this.xxa = 0.0F;
      this.zza = 0.0F;
      float f = 48.0F;
      if (this.ticksSinceDamaged > 1000 && this.ticksSinceDamaged % 5 == 0) {
         this.heal(1.0F);
      }

      for (int i = 0; i < 7; i++) {
         if (!this.hc[i].isDead() && this.hc[i].getDamageTaken() > 120) {
            this.hc[i].setNextState(HydraHeadContainer.State.DYING);
            this.hc[i].endCurrentAction();
            this.hc[i].setRespawnCounter(140);
            int otherHead = this.getRandomDeadHead();
            if (otherHead != -1) {
               this.hc[otherHead].setRespawnCounter(140);
            }
         }
      }

      if (this.getRandom().nextFloat() < 0.7F) {
         Player entityplayer1 = this.level().getNearestPlayer(this, f);
         if (entityplayer1 != null && !entityplayer1.isCreative()) {
            this.setTarget(entityplayer1);
            this.numTicksToChaseTarget = 100 + this.getRandom().nextInt(20);
         } else {
            this.randomYawVelocity = (this.getRandom().nextFloat() - 0.5F) * 20.0F;
         }
      }

      this.destroyBlocksInAABB(this.body.getBoundingBox());
      this.destroyBlocksInAABB(this.tail.getBoundingBox());

      for (int i = 0; i < 7; i++) {
         if (!this.hc[i].isDead()) {
            this.destroyBlocksInAABB(this.hc[i].headEntity.getBoundingBox());
         }
      }

      if (this.tickCount % 20 == 0 && this.isUnsteadySurfaceBeneath()) {
         this.destroyBlocksInAABB(this.getBoundingBox().move(0.0, -1.0, 0.0));
      }

      if (this.getTarget() != null) {
         this.lookAt(this.getTarget(), 10.0F, this.getMaxHeadXRot());

         for (int i = 0; i < 7; i++) {
            if (!this.hc[i].isAttacking() && !this.hc[i].isSecondaryAttacking) {
               this.hc[i].setTargetEntity(this.getTarget());
            }
         }

         if (this.getTarget().isAlive()) {
            float distance = this.getTarget().distanceTo(this);
            if (this.getSensing().hasLineOfSight(this.getTarget())) {
               this.attackEntity(this.getTarget(), distance);
            }
         }

         if (this.numTicksToChaseTarget-- <= 0 || !this.getTarget().isAlive() || this.getTarget().distanceToSqr(this) > f * f) {
            this.setTarget(null);
         }
      } else {
         if (this.getRandom().nextFloat() < 0.05F) {
            this.randomYawVelocity = (this.getRandom().nextFloat() - 0.5F) * 20.0F;
         }

         this.setYRot(this.getYRot() + this.randomYawVelocity);
         this.setXRot(0.0F);

         for (int i = 0; i < 7; i++) {
            if (this.hc[i].isIdle()) {
               this.hc[i].setTargetEntity(null);
            }
         }
      }

      this.secondaryAttacks();
   }

   private void setDifficultyVariables() {
      if (this.level().getDifficulty() != Difficulty.HARD) {
         HEADS_ACTIVITY_FACTOR = 0.3F;
      } else {
         HEADS_ACTIVITY_FACTOR = 0.5F;
      }
   }

   private int getRandomDeadHead() {
      List<Integer> headIDs = new ArrayList<>();

      for (int i = 0; i < 7; i++) {
         if (this.hc[i].canRespawn()) {
            headIDs.add(i);
         }
      }

      return headIDs.isEmpty() ? -1 : headIDs.get(this.random.nextInt(headIDs.size()));
   }

   private void attackEntity(Entity target, float distance) {
      int BITE_CHANCE = 10;
      int FLAME_CHANCE = 100;
      int MORTAR_CHANCE = 160;
      boolean targetAbove = target.getBoundingBox().minY > this.getBoundingBox().maxY;

      for (int i = 0; i < 3; i++) {
         if (this.hc[i].isIdle() && !this.areTooManyHeadsAttacking(i)) {
            if (distance > 4.0F
               && distance < 10.0F
               && this.getRandom().nextInt(BITE_CHANCE) == 0
               && this.countActiveHeads() > 2
               && !this.areOtherHeadsBiting(i)) {
               this.hc[i].setNextState(HydraHeadContainer.State.BITE_BEGINNING);
            } else if (distance > 0.0F && distance < 20.0F && this.getRandom().nextInt(FLAME_CHANCE) == 0) {
               this.hc[i].setNextState(HydraHeadContainer.State.FLAME_BEGINNING);
            } else if (distance > 8.0F && distance < 32.0F && !targetAbove && this.getRandom().nextInt(MORTAR_CHANCE) == 0) {
               this.hc[i].setNextState(HydraHeadContainer.State.MORTAR_BEGINNING);
            }
         }
      }

      for (int i = 3; i < 7; i++) {
         if (this.hc[i].isIdle() && !this.areTooManyHeadsAttacking(i)) {
            if (distance > 0.0F && distance < 20.0F && this.getRandom().nextInt(FLAME_CHANCE) == 0) {
               this.hc[i].setNextState(HydraHeadContainer.State.FLAME_BEGINNING);
            } else if (distance > 8.0F && distance < 32.0F && !targetAbove && this.getRandom().nextInt(MORTAR_CHANCE) == 0) {
               this.hc[i].setNextState(HydraHeadContainer.State.MORTAR_BEGINNING);
            }
         }
      }
   }

   private boolean areTooManyHeadsAttacking(int testHead) {
      int otherAttacks = 0;

      for (int i = 0; i < 7; i++) {
         if (i != testHead && this.hc[i].isAttacking()) {
            otherAttacks++;
            if (this.hc[i].isBiting()) {
               otherAttacks += 2;
            }
         }
      }

      return otherAttacks >= 1.0F + this.countActiveHeads() * HEADS_ACTIVITY_FACTOR;
   }

   private int countActiveHeads() {
      int count = 0;

      for (int i = 0; i < 7; i++) {
         if (!this.hc[i].isDead()) {
            count++;
         }
      }

      return count;
   }

   private boolean areOtherHeadsBiting(int testHead) {
      for (int i = 0; i < 7; i++) {
         if (i != testHead && this.hc[i].isBiting()) {
            return true;
         }
      }

      return false;
   }

   private void secondaryAttacks() {
      LivingEntity secondaryTarget = this.findSecondaryTarget(20.0);
      if (secondaryTarget != null) {
         float distance = secondaryTarget.distanceTo(this);

         for (int i = 1; i < 7; i++) {
            if (!this.hc[i].isDead() && this.hc[i].isIdle() && this.isTargetOnThisSide(i, secondaryTarget)) {
               if (distance > 0.0F && distance < 20.0F && this.getRandom().nextInt(10) == 0) {
                  this.hc[i].setTargetEntity(secondaryTarget);
                  this.hc[i].isSecondaryAttacking = true;
                  this.hc[i].setNextState(HydraHeadContainer.State.FLAME_BEGINNING);
               } else if (distance > 8.0F && distance < 32.0F && this.getRandom().nextInt(16) == 0) {
                  this.hc[i].setTargetEntity(secondaryTarget);
                  this.hc[i].isSecondaryAttacking = true;
                  this.hc[i].setNextState(HydraHeadContainer.State.MORTAR_BEGINNING);
               }
            }
         }
      }
   }

   private boolean isTargetOnThisSide(int headNum, Entity target) {
      double headDist = this.distanceSqXZ(this.hc[headNum].headEntity, target);
      double middleDist = this.distanceSqXZ(this, target);
      return headDist < middleDist;
   }

   private double distanceSqXZ(Entity headEntity, Entity target) {
      double distX = headEntity.getX() - target.getX();
      double distZ = headEntity.getZ() - target.getZ();
      return distX * distX + distZ * distZ;
   }

   @Nullable
   private LivingEntity findSecondaryTarget(double range) {
      return this.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            new AABB(this.getX(), this.getY(), this.getZ(), this.getX() + 1.0, this.getY() + 1.0, this.getZ() + 1.0).inflate(range, range, range)
         )
         .stream()
         .filter(e -> !(e instanceof Hydra))
         .filter(
            e -> e != this.getTarget() && !this.isAnyHeadTargeting(e) && this.getSensing().hasLineOfSight(e) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)
         )
         .min(Comparator.comparingDouble(this::distanceToSqr))
         .orElse(null);
   }

   private boolean isAnyHeadTargeting(Entity targetEntity) {
      for (int i = 0; i < 7; i++) {
         if (this.hc[i].targetEntity != null && this.hc[i].targetEntity.equals(targetEntity)) {
            return true;
         }
      }

      return false;
   }

   private void collideWithEntities(List<Entity> entities, Entity part) {
      double d0 = (part.getBoundingBox().minX + part.getBoundingBox().maxX) / 2.0;
      double d1 = (part.getBoundingBox().minZ + part.getBoundingBox().maxZ) / 2.0;

      for (Entity entity : entities) {
         if (!(entity instanceof Player player && player.isCreative()) && entity instanceof LivingEntity) {
            double d2 = entity.getX() - d0;
            double d3 = entity.getZ() - d1;
            double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);
            entity.push(d2 / d4 * 8.0, 0.2, d3 / d4 * 8.0);
         }
      }
   }

   private boolean isUnsteadySurfaceBeneath() {
      int minX = Mth.floor(this.getBoundingBox().minX);
      int minZ = Mth.floor(this.getBoundingBox().minZ);
      int maxX = Mth.floor(this.getBoundingBox().maxX);
      int maxZ = Mth.floor(this.getBoundingBox().maxZ);
      int minY = Mth.floor(this.getBoundingBox().minY);
      int solid = 0;
      int total = 0;
      int dy = minY - 1;

      for (int dx = minX; dx <= maxX; dx++) {
         for (int dz = minZ; dz <= maxZ; dz++) {
            total++;
            if (this.level().getBlockState(new BlockPos(dx, dy, dz)).isSolid()) {
               solid++;
            }
         }
      }

      return (float)solid / total < 0.6F;
   }

   private void destroyBlocksInAABB(AABB box) {
      if (this.deathTime <= 0 && EventHooks.canEntityGrief(this.level(), this)) {
         for (BlockPos pos : WorldUtil.getAllInBB(box)) {
            if (EntityUtil.canDestroyBlock(this.level(), pos, this)) {
               this.level().destroyBlock(pos, false);
            }
         }
      }
   }

   public int getMaxHeadXRot() {
      return 500;
   }

   public boolean attackEntityFromPart(HydraPart part, DamageSource source, float damage) {
      if (!this.level().isClientSide() && source.is(DamageTypes.IN_WALL)) {
         this.destroyBlocksInAABB(part.getBoundingBox());
      }

      if (source.getEntity() != this && source.getDirectEntity() != this) {
         if (this.getParts() != null) {
            for (PartEntity<?> partEntity : this.getParts()) {
               if (partEntity == source.getEntity() || partEntity == source.getDirectEntity()) {
                  return false;
               }
            }
         }

         HydraHeadContainer headCon = null;

         for (int i = 0; i < 7; i++) {
            if (this.hc[i].headEntity == part) {
               headCon = this.hc[i];
            } else if (part instanceof HydraNeck neck && this.hc[i].headEntity == neck.head && this.hc[i].isDead()) {
               return false;
            }
         }

         double range = this.calculateRange(source);
         if (range > 400 + (source.getDirectEntity() instanceof HydraMortar ? 200 : 0)) {
            return false;
         }

         if (headCon != null && headCon.isDead()) {
            return false;
         }

         boolean tookDamage;
         if (headCon != null && headCon.getCurrentMouthOpen() > 0.5) {
            tookDamage = super.hurt(source, damage);
            headCon.addDamage(damage);
         } else {
            int armoredDamage = Math.round(damage / 8.0F);
            tookDamage = super.hurt(source, armoredDamage);
            if (headCon != null) {
               headCon.addDamage(armoredDamage);
            }
         }

         if (tookDamage) {
            this.ticksSinceDamaged = 0;
         }

         return tookDamage;
      } else {
         return false;
      }
   }

   private double calculateRange(DamageSource damagesource) {
      return damagesource.getEntity() != null ? this.distanceToSqr(damagesource.getEntity()) : -1.0;
   }

   public boolean hurt(DamageSource src, float damage) {
      return src.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && super.hurt(src, damage);
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return !source.is(TFDamageTypes.HYDRA_MORTAR) && super.isInvulnerableTo(source);
   }

   public boolean isMultipartEntity() {
      return true;
   }

   @Nullable
   public PartEntity<?>[] getParts() {
      return this.partArray;
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      TFPart.assignPartIDs(this);
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   protected void doPush(Entity entity) {
   }

   public void knockback(double strength, double xRatio, double zRatio) {
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.HYDRA_GROWL.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.HYDRA_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.HYDRA_DEATH.get();
   }

   protected float getSoundVolume() {
      return 2.0F;
   }

   public boolean isOnFire() {
      return false;
   }

   public String getHeadNameFor(int index) {
      return (String)((List)this.getEntityData().get(HEAD_NAMES)).get(index);
   }

   public void setHeadNameFor(int index, String name) {
      List<String> nameCopy = new ArrayList<>((Collection<? extends String>)this.getEntityData().get(HEAD_NAMES));
      nameCopy.set(index, name);
      this.getEntityData().set(HEAD_NAMES, nameCopy);
   }

   @Override
   public int getHomeRadius() {
      return 20;
   }

   @Override
   public ResourceKey<Structure> getHomeStructure() {
      return TFStructures.HYDRA_LAIR;
   }

   @Override
   public Block getDeathContainer(RandomSource random) {
      return (Block)TFBlocks.MANGROVE_CHEST.get();
   }

   @Override
   public Block getBossSpawner() {
      return (Block)TFBlocks.HYDRA_BOSS_SPAWNER.get();
   }

   @Override
   protected void tickDeath() {
      this.deathTime++;
      if (this.deathTime == 1) {
         for (int i = 0; i < 7; i++) {
            this.hc[i].setRespawnCounter(-1);
            if (!this.hc[i].isDead()) {
               this.hc[i].setNextState(HydraHeadContainer.State.IDLE);
               this.hc[i].endCurrentAction();
               this.hc[i].setHurtTime(200);
            }
         }
      }

      if (this.deathTime <= 140 && this.deathTime % 20 == 0) {
         int headToDie = this.deathTime / 20 - 1;
         if (!this.hc[headToDie].isDead()) {
            this.hc[headToDie].setNextState(HydraHeadContainer.State.DYING);
            this.hc[headToDie].endCurrentAction();
         }
      }

      if (this.deathTime == 200) {
         this.remove(RemovalReason.KILLED);
      }

      if (this.level().isClientSide()) {
         this.tickDeathAnimation();
      }
   }

   @Override
   public void tickDeathAnimation() {
      for (int i = 0; i < 10; i++) {
         double vx = this.getRandom().nextGaussian() * 0.02;
         double vy = this.getRandom().nextGaussian() * 0.02;
         double vz = this.getRandom().nextGaussian() * 0.02;
         this.level()
            .addParticle(
               this.getRandom().nextInt(2) == 0 ? ParticleTypes.EXPLOSION : ParticleTypes.POOF,
               this.getX() + this.getRandom().nextFloat() * this.body.getBbWidth() * 2.0F - this.body.getBbWidth(),
               this.getY() + this.getRandom().nextFloat() * this.body.getBbHeight(),
               this.getZ() + this.getRandom().nextFloat() * this.body.getBbWidth() * 2.0F - this.body.getBbWidth(),
               vx,
               vy,
               vz
            );
      }
   }

   @Override
   public int getBossBarColor() {
      return 388025;
   }
}
