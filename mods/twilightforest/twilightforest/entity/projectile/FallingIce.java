package twilightforest.entity.projectile;

import java.util.Objects;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.AlphaYeti;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;

public class FallingIce extends Entity {
   private int hangTime = 100;
   private BlockState blockState = Blocks.PACKED_ICE.defaultBlockState();
   public int time;
   protected final int fallDamageMax = 100;
   public final float[] damagePerDifficulty = new float[]{0.0F, 0.5F, 1.0F, 2.0F};
   @Nullable
   public CompoundTag blockData;
   protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(FallingIce.class, EntityDataSerializers.BLOCK_POS);

   public FallingIce(EntityType<? extends FallingIce> type, Level level) {
      super(type, level);
   }

   public FallingIce(Level level, double x, double y, double z, BlockState state, int hangTime) {
      this((EntityType<? extends FallingIce>)TFEntities.FALLING_ICE.get(), level);
      this.hangTime = hangTime;
      this.blockState = state;
      this.blocksBuilding = true;
      this.setPos(x, y, z);
      this.setDeltaMovement(Vec3.ZERO);
      this.xo = x;
      this.yo = y;
      this.zo = z;
      this.setStartPos(this.blockPosition());
   }

   public void setStartPos(BlockPos pos) {
      this.getEntityData().set(DATA_START_POS, pos);
   }

   public BlockPos getStartPos() {
      return (BlockPos)this.getEntityData().get(DATA_START_POS);
   }

   public boolean isAttackable() {
      return false;
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.NONE;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_START_POS, BlockPos.ZERO);
   }

   public boolean isPickable() {
      return !this.isRemoved();
   }

   public void tick() {
      if (this.blockState.isAir()) {
         this.discard();
      } else {
         this.time++;
         this.setNoGravity(this.time < this.hangTime);
         if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
         }

         this.move(MoverType.SELF, this.getDeltaMovement());
         if (this.level().isClientSide()) {
            this.makeWarningTrail();
         } else {
            BlockPos blockpos = this.blockPosition();
            boolean flag = this.blockState.getBlock() instanceof ConcretePowderBlock;
            boolean flag1 = flag && this.blockState.canBeHydrated(this.level(), blockpos, this.level().getFluidState(blockpos), blockpos);
            double d0 = this.getDeltaMovement().lengthSqr();
            if (flag && d0 > 1.0) {
               BlockHitResult blockhitresult = this.level()
                  .clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), Block.COLLIDER, Fluid.SOURCE_ONLY, this));
               if (blockhitresult.getType() != Type.MISS
                  && this.blockState
                     .canBeHydrated(this.level(), blockpos, this.level().getFluidState(blockhitresult.getBlockPos()), blockhitresult.getBlockPos())) {
                  blockpos = blockhitresult.getBlockPos();
                  flag1 = true;
               }
            }

            if (!this.onGround() && !flag1) {
               if (!this.level().isClientSide()
                  && (
                     this.time > 100 && (blockpos.getY() <= this.level().getMinBuildHeight() || blockpos.getY() > this.level().getMaxBuildHeight())
                        || this.time > 1000
                  )) {
                  this.discard();
               }
            } else {
               BlockState blockstate = this.level().getBlockState(blockpos);
               this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
               if (!blockstate.is(Blocks.MOVING_PISTON)) {
                  boolean flag2 = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level(), blockpos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                  boolean flag3 = FallingBlock.isFree(this.level().getBlockState(blockpos.below())) && (!flag || !flag1);
                  boolean flag4 = this.blockState.canSurvive(this.level(), blockpos) && !flag3;
                  if (flag2 && flag4) {
                     if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level().getFluidState(blockpos).getType() == Fluids.WATER) {
                        this.blockState = (BlockState)this.blockState.setValue(BlockStateProperties.WATERLOGGED, true);
                     }

                     if (this.level().setBlock(blockpos, this.blockState, 3)) {
                        ((ServerLevel)this.level())
                           .getChunkSource()
                           .chunkMap
                           .broadcast(this, new ClientboundBlockUpdatePacket(blockpos, this.level().getBlockState(blockpos)));
                        this.discard();
                        if (this.blockData != null && this.blockState.hasBlockEntity()) {
                           BlockEntity blockentity = this.level().getBlockEntity(blockpos);
                           if (blockentity != null) {
                              CompoundTag compoundtag = blockentity.saveWithoutMetadata(this.level().registryAccess());

                              for (String s : this.blockData.getAllKeys()) {
                                 compoundtag.put(s, Objects.requireNonNull(this.blockData.get(s)).copy());
                              }

                              try {
                                 blockentity.loadWithComponents(compoundtag, this.level().registryAccess());
                              } catch (Exception exception) {
                                 TwilightForestMod.LOGGER.error("Failed to load block entity from falling block", exception);
                              }

                              blockentity.setChanged();
                           }
                        }
                     }
                  } else {
                     this.discard();
                  }
               }
            }
         }

         this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
      }
   }

   private void makeWarningTrail() {
      for (int i = 0; i < 10; i++) {
         double dx = this.getX() + 1.5F * (this.random.nextFloat() - this.random.nextFloat());
         double dy = this.getY() - 4.0F * (this.random.nextFloat() - this.random.nextFloat()) - 3.0;
         double dz = this.getZ() + 1.5F * (this.random.nextFloat() - this.random.nextFloat());
         this.level().addAlwaysVisibleParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.blockState), dx, dy, dz, 0.0, -1.0, 0.0);
      }
   }

   public boolean causeFallDamage(float dist, float multiplier, DamageSource source) {
      int realDist = Mth.ceil(dist - 5.0F);
      if (realDist >= 0) {
         float dmg = Math.min(Mth.floor(realDist * this.damagePerDifficulty[this.level().getDifficulty().getId()]), 100);
         this.level().getEntities(this, this.getBoundingBox().inflate(1.0, 0.0, 1.0), EntitySelector.NO_SPECTATORS).forEach(entity -> {
            if (!(entity instanceof AlphaYeti)) {
               entity.hurt(TFDamageTypes.getDamageSource(this.level(), TFDamageTypes.FALLING_ICE), dmg);
            }
         });
      }

      for (int i = 0; i < 200; i++) {
         double dx = this.getX() + 3.0F * (this.random.nextFloat() - this.random.nextFloat());
         double dy = this.getY() + 5.0F * (this.random.nextFloat() - this.random.nextFloat());
         double dz = this.getZ() + 3.0F * (this.random.nextFloat() - this.random.nextFloat());
         this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.blockState), dx, dy, dz, 0.0, 0.0, 0.0);
      }

      this.playSound(Blocks.PACKED_ICE.defaultBlockState().getSoundType().getBreakSound(), 3.0F, 0.5F);
      return false;
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      tag.put("BlockState", NbtUtils.writeBlockState(this.blockState));
      tag.putInt("Time", this.time);
      if (this.blockData != null) {
         tag.put("BlockEntityData", this.blockData);
      }
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      this.blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("BlockState"));
      this.time = tag.getInt("Time");
      if (tag.contains("BlockEntityData", 10)) {
         this.blockData = tag.getCompound("BlockEntityData");
      }

      if (this.blockState.isAir()) {
         this.blockState = Blocks.PACKED_ICE.defaultBlockState();
      }
   }

   public boolean displayFireAnimation() {
      return false;
   }

   public void fillCrashReportCategory(CrashReportCategory category) {
      super.fillCrashReportCategory(category);
      category.setDetail("Imitating BlockState", this.blockState.toString());
   }

   public BlockState getBlockState() {
      return this.blockState;
   }

   public boolean onlyOpCanSetNbt() {
      return true;
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity, net.minecraft.world.level.block.Block.getId(this.getBlockState()));
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      this.blockState = net.minecraft.world.level.block.Block.stateById(packet.getData());
      this.blocksBuilding = true;
      double d0 = packet.getX();
      double d1 = packet.getY();
      double d2 = packet.getZ();
      this.setPos(d0, d1, d2);
      this.setStartPos(this.blockPosition());
   }
}
