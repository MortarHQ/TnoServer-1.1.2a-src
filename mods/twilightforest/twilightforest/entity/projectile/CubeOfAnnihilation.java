package twilightforest.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFItems;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.network.ParticlePacket;
import twilightforest.util.WorldUtil;

public class CubeOfAnnihilation extends ThrowableProjectile {
   private boolean hasHitObstacle = false;
   private ItemStack stack;

   public CubeOfAnnihilation(EntityType<? extends CubeOfAnnihilation> type, Level world) {
      super(type, world);
   }

   public CubeOfAnnihilation(EntityType<? extends CubeOfAnnihilation> type, Level world, LivingEntity thrower, ItemStack stack) {
      super(type, thrower, world);
      this.shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0.0F, 1.5F, 1.0F);
      this.stack = stack;
   }

   public boolean canUsePortal(boolean force) {
      return false;
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected double getDefaultGravity() {
      return 0.0;
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (result.getEntity() instanceof LivingEntity && result.getEntity().hurt(this.getDamageSource(), 10.0F)) {
         this.tickCount += 60;
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      if (!this.level().isEmptyBlock(result.getBlockPos())) {
         this.affectBlocksInAABB(this.getBoundingBox().inflate(0.2F, 0.2F, 0.2F));
      }
   }

   protected void onHit(HitResult result) {
      Type hitresult$type = result.getType();
      if (hitresult$type == Type.ENTITY) {
         this.onHitEntity((EntityHitResult)result);
      } else if (hitresult$type == Type.BLOCK) {
         this.onHitBlock((BlockHitResult)result);
      }
   }

   private DamageSource getDamageSource() {
      LivingEntity thrower = (LivingEntity)this.getOwner();
      if (thrower instanceof Player) {
         return this.damageSources().playerAttack((Player)thrower);
      } else {
         return thrower != null ? this.damageSources().mobAttack(thrower) : this.damageSources().thrown(this, null);
      }
   }

   private void affectBlocksInAABB(AABB box) {
      for (BlockPos pos : WorldUtil.getAllInBB(box)) {
         BlockState state = this.level().getBlockState(pos);
         if (!state.isAir() && this.getOwner() instanceof ServerPlayer player) {
            if (!((BreakEvent)NeoForge.EVENT_BUS.post(new BreakEvent(this.level(), pos, state, player))).isCanceled()) {
               if (this.canAnnihilate(pos, state, player.gameMode.getGameModeForPlayer().isBlockPlacingRestricted())) {
                  this.level().removeBlock(pos, false);
                  this.playSound((SoundEvent)TFSounds.BLOCK_ANNIHILATED.get(), 0.125F, this.random.nextFloat() * 0.25F + 0.75F);
                  this.annihilateParticles(this.level(), pos);
                  this.gameEvent(GameEvent.BLOCK_DESTROY);
               } else {
                  this.hasHitObstacle = true;
               }
            } else {
               this.hasHitObstacle = true;
            }
         }
      }
   }

   private boolean canAnnihilate(BlockPos pos, BlockState state, boolean restrictedPlaceMode) {
      Block block = state.getBlock();
      return (state.is(BlockTagGenerator.ANNIHILATION_INCLUSIONS) || block.getExplosionResistance() < 8.0F && state.getDestroySpeed(this.level(), pos) >= 0.0F)
         && (!restrictedPlaceMode || this.stack.canBreakBlockInAdventureMode(new BlockInWorld(this.level(), pos, false)));
   }

   private void annihilateParticles(Level level, BlockPos pos) {
      if (level instanceof ServerLevel server) {
         RandomSource rand = level.getRandom();
         ParticlePacket particlePacket = new ParticlePacket();

         for (int dx = 0; dx < 3; dx++) {
            for (int dy = 0; dy < 3; dy++) {
               for (int dz = 0; dz < 3; dz++) {
                  particlePacket.queueParticle(
                     (ParticleOptions)TFParticleType.ANNIHILATE.get(),
                     false,
                     pos.getX() + (dx + 0.5) / 4.0,
                     pos.getY() + (dy + 0.5) / 4.0,
                     pos.getZ() + (dz + 0.5) / 4.0,
                     rand.nextGaussian() * 0.2,
                     rand.nextGaussian() * 0.2,
                     rand.nextGaussian() * 0.2
                  );
               }
            }
         }

         PacketDistributor.sendToPlayersNear(server, null, pos.getX(), pos.getY(), pos.getZ(), 32.0, particlePacket, new CustomPacketPayload[0]);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.getOwner() == null) {
            this.remove(RemovalReason.KILLED);
            return;
         }

         Vec3 destPoint = new Vec3(this.getOwner().getX(), this.getOwner().getY() + this.getOwner().getEyeHeight(), this.getOwner().getZ());
         double distToPlayer = this.distanceTo(this.getOwner());
         if (this.isReturning()) {
            if (distToPlayer < 2.0) {
               this.remove(RemovalReason.KILLED);
            }
         } else {
            destPoint = destPoint.add(this.getOwner().getLookAngle().scale(16.0));
         }

         Vec3 velocity = new Vec3(this.getX() - destPoint.x(), this.getY() + this.getBbHeight() / 2.0F - destPoint.y(), this.getZ() - destPoint.z());
         this.setDeltaMovement(-velocity.x(), -velocity.y(), -velocity.z());
         float currentSpeed = Mth.sqrt(
            (float)(
               this.getDeltaMovement().x() * this.getDeltaMovement().x()
                  + this.getDeltaMovement().y() * this.getDeltaMovement().y()
                  + this.getDeltaMovement().z() * this.getDeltaMovement().z()
            )
         );
         float maxSpeed = 0.5F;
         if (currentSpeed > maxSpeed) {
            this.setDeltaMovement(
               new Vec3(
                  this.getDeltaMovement().x() / (currentSpeed / maxSpeed),
                  this.getDeltaMovement().y() / (currentSpeed / maxSpeed),
                  this.getDeltaMovement().z() / (currentSpeed / maxSpeed)
               )
            );
         } else {
            float slow = 0.5F;
            this.getDeltaMovement().multiply(slow, slow, slow);
         }

         this.affectBlocksInAABB(this.getBoundingBox().inflate(0.2F));
      }
   }

   public void remove(RemovalReason reason) {
      super.remove(reason);
      LivingEntity thrower = (LivingEntity)this.getOwner();
      if (thrower != null && thrower.getUseItem().is((Item)TFItems.CUBE_OF_ANNIHILATION.get())) {
         thrower.stopUsingItem();
      }
   }

   private boolean isReturning() {
      return !this.hasHitObstacle && this.getOwner() != null && this.getOwner() instanceof Player player ? !player.isUsingItem() : true;
   }

   protected void readAdditionalSaveData(CompoundTag pCompound) {
      super.readAdditionalSaveData(pCompound);
      if (pCompound.contains("CubeOfAnnihilationStack", 10)) {
         this.stack = ItemStack.parseOptional(this.registryAccess(), pCompound.getCompound("CubeOfAnnihilationStack"));
      }
   }

   protected void addAdditionalSaveData(CompoundTag pCompound) {
      super.addAdditionalSaveData(pCompound);
      pCompound.put("CubeOfAnnihilationStack", this.stack.save(this.registryAccess()));
   }
}
