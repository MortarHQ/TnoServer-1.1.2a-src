package twilightforest.entity.monster;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.SpikeBlock;
import twilightforest.entity.TFPart;
import twilightforest.entity.ai.goal.AvoidAnyEntityGoal;
import twilightforest.entity.ai.goal.ThrowSpikeBlockGoal;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFSounds;
import twilightforest.util.entities.EntityUtil;

public class BlockChainGoblin extends Monster {
   private static final float CHAIN_SPEED = 16.0F;
   private static final EntityDataAccessor<Byte> DATA_CHAINLENGTH = SynchedEntityData.defineId(BlockChainGoblin.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Byte> DATA_CHAINPOS = SynchedEntityData.defineId(BlockChainGoblin.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Boolean> IS_THROWING = SynchedEntityData.defineId(BlockChainGoblin.class, EntityDataSerializers.BOOLEAN);
   private int recoilCounter;
   private float chainAngle;
   private float chainMoveLength;
   public final SpikeBlock block = new SpikeBlock(this);
   private final BlockChainGoblin.MultipartGenericsAreDumb[] partsArray = new BlockChainGoblin.MultipartGenericsAreDumb[]{this.block};

   public BlockChainGoblin(EntityType<? extends BlockChainGoblin> type, Level level) {
      super(type, level);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new AvoidAnyEntityGoal(this, PrimedTnt.class, 2.0F, 1.0, 2.0));
      this.goalSelector.addGoal(4, new ThrowSpikeBlockGoal(this, this.block));
      this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_CHAINLENGTH, (byte)0);
      builder.define(DATA_CHAINPOS, (byte)0);
      builder.define(IS_THROWING, false);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28)
         .add(Attributes.ATTACK_DAMAGE, 8.0)
         .add(Attributes.ARMOR, 11.0);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.BLOCKCHAIN_GOBLIN_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.BLOCKCHAIN_GOBLIN_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.BLOCKCHAIN_GOBLIN_DEATH.get();
   }

   public double getChainYOffset() {
      return 1.5 - this.getChainLength() / 4.0;
   }

   public Vec3 getChainPosition() {
      return this.getChainPosition(this.getChainAngle(), this.getChainLength());
   }

   public Vec3 getChainPosition(float angle, float distance) {
      double dx = Math.cos(angle * Math.PI / 180.0) * distance;
      double dz = Math.sin(angle * Math.PI / 180.0) * distance;
      return new Vec3(this.getX() + dx, this.getY() + this.getChainYOffset(), this.getZ() + dz);
   }

   public boolean isSwingingChain() {
      return this.swinging || this.getTarget() != null && this.recoilCounter == 0;
   }

   public boolean doHurtTarget(Entity entity) {
      return EntityUtil.properlyApplyCustomDamageSource(
         this, entity, TFDamageTypes.getIndirectEntityDamageSource(this.level(), TFDamageTypes.SPIKED, this, this.block), null
      );
   }

   public void tick() {
      super.tick();
      this.block.tick();
      if (this.recoilCounter > 0) {
         this.recoilCounter--;
      }

      this.chainAngle += 16.0F;
      this.chainAngle %= 360.0F;
      if (!this.level().isClientSide()) {
         this.getEntityData().set(DATA_CHAINLENGTH, (byte)Math.floor(this.getChainLength() * 127.0F));
         this.getEntityData().set(DATA_CHAINPOS, (byte)Math.floor(this.getChainAngle() / 360.0F * 255.0F));
      } else if (Math.abs(this.chainAngle - this.getChainAngle()) > 32.0F) {
         this.chainAngle = this.getChainAngle();
      }

      if (this.isAlive()) {
         if (!(this.chainMoveLength > 0.0F)) {
            Vec3 blockPos = this.getChainPosition();
            this.block.setPos(blockPos.x(), blockPos.y(), blockPos.z());
         } else {
            Vec3 blockPos = this.getThrowPos();
            double sx2 = this.getX();
            double sy2 = this.getY() + this.getBbHeight() - 0.1;
            double sz2 = this.getZ();
            double ox2 = sx2 - blockPos.x();
            double oy2 = sy2 - blockPos.y() - 0.25;
            double oz2 = sz2 - blockPos.z();
            if (this.chainMoveLength >= 6.0F || !this.isAlive()) {
               this.setThrowing(false);
            }

            this.block.setPos(sx2 - ox2, sy2 - oy2, sz2 - oz2);
         }

         this.block.setYRot(this.getChainAngle());
      }

      if (!this.level().isClientSide() && this.isAlive() && (this.isThrowing() || this.isSwingingChain())) {
         this.applyBlockCollisions(this.block);
      }

      this.chainMove();
   }

   private Vec3 getThrowPos() {
      Vec3 vec3d = this.getViewVector(1.0F);
      return new Vec3(this.getX() + vec3d.x() * this.chainMoveLength, this.getY() + this.getEyeHeight(), this.getZ() + vec3d.z() * this.chainMoveLength);
   }

   private void chainMove() {
      if (this.isThrowing()) {
         this.chainMoveLength = Mth.clamp(this.chainMoveLength + 0.5F, 0.0F, 6.0F);
      } else {
         this.chainMoveLength = Mth.clamp(this.chainMoveLength - 1.5F, 0.0F, 6.0F);
      }
   }

   public float getChainMoveLength() {
      return this.chainMoveLength;
   }

   protected void applyBlockCollisions(Entity collider) {
      for (Entity entity : this.level().getEntities(collider, collider.getBoundingBox().inflate(0.2, 0.0, 0.2))) {
         if (entity.isPushable()) {
            this.applyBlockCollision(collider, entity);
         }
      }

      if (this.isThrowing() && collider.isInWall()) {
         this.setThrowing(false);
         collider.playSound((SoundEvent)TFSounds.BLOCK_AND_CHAIN_COLLIDE.get(), 0.65F, 0.75F);
         this.gameEvent(GameEvent.HIT_GROUND);
      }
   }

   protected void applyBlockCollision(Entity collider, Entity collided) {
      if (collided != this) {
         collided.push(collider);
         if (collided instanceof LivingEntity && super.doHurtTarget(collided)) {
            collided.push(0.0, 0.4, 0.0);
            this.playSound((SoundEvent)TFSounds.BLOCK_AND_CHAIN_HIT.get(), 1.0F, 1.0F);
            this.gameEvent(GameEvent.PROJECTILE_LAND);
            this.recoilCounter = 40;
            if (this.isThrowing()) {
               this.setThrowing(false);
            }
         }
      }
   }

   public boolean isThrowing() {
      return (Boolean)this.getEntityData().get(IS_THROWING);
   }

   public void setThrowing(boolean isThrowing) {
      this.getEntityData().set(IS_THROWING, isThrowing);
   }

   private float getChainAngle() {
      return !this.level().isClientSide() ? this.chainAngle : ((Byte)this.getEntityData().get(DATA_CHAINPOS) & 0xFF) / 255.0F * 360.0F;
   }

   private float getChainLength() {
      if (!this.level().isClientSide()) {
         return this.isSwingingChain() ? 0.9F : 0.3F;
      } else {
         return ((Byte)this.getEntityData().get(DATA_CHAINLENGTH) & 0xFF) / 127.0F;
      }
   }

   public boolean isMultipartEntity() {
      return true;
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      TFPart.assignPartIDs(this);
   }

   public BlockChainGoblin.MultipartGenericsAreDumb[] getParts() {
      return this.partsArray;
   }

   public abstract static class MultipartGenericsAreDumb extends TFPart<Entity> {
      public MultipartGenericsAreDumb(Entity parent) {
         super(parent);
      }
   }
}
