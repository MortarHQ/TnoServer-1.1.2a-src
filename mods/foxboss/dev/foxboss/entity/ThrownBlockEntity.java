package dev.foxboss.entity;

import dev.foxboss.registry.FoxEntities;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.common.Tags.Blocks;

public final class ThrownBlockEntity extends Projectile {
   public static final double MAX_RANGE = 64.0;
   public static final double GRAVITY = 0.06;
   private static final double RADIUS = 0.4;
   private static final EntityDataAccessor<BlockState> BLOCK = SynchedEntityData.defineId(ThrownBlockEntity.class, EntityDataSerializers.BLOCK_STATE);
   private List<ItemStack> oreDrops = List.of();
   private Vec3 origin = Vec3.ZERO;
   private float damage;
   private boolean broken;

   public ThrownBlockEntity(EntityType<? extends ThrownBlockEntity> type, Level level) {
      super(type, level);
      this.noPhysics = true;
   }

   public static ThrownBlockEntity prepare(ServerLevel level, LivingEntity owner, BlockState state, BlockPos source, Vec3 from, Vec3 aim) {
      float hardness = state.getDestroySpeed(level, source);
      if (!state.isAir() && Float.isFinite(hardness) && !(hardness < 0.0F) && Double.isFinite(from.distanceToSqr(aim)) && !(from.distanceToSqr(aim) > 4096.0)) {
         ThrownBlockEntity projectile = (ThrownBlockEntity)((EntityType)FoxEntities.THROWN_BLOCK.get()).create(level);
         if (projectile == null) {
            return null;
         }

         projectile.setOwner(owner);
         projectile.entityData.set(BLOCK, state);
         projectile.launchFrom(from, aim);
         projectile.damage = damageForHardness(hardness);
         ItemStack pickaxe = new ItemStack(Items.NETHERITE_PICKAXE);
         if (state.is(Blocks.ORES) && pickaxe.isCorrectToolForDrops(state)) {
            projectile.oreDrops = Block.getDrops(state, level, source, level.getBlockEntity(source), owner, pickaxe)
               .stream()
               .<ItemStack>map(ItemStack::copy)
               .toList();
         }

         return projectile;
      } else {
         return null;
      }
   }

   public static float damageForHardness(float hardness) {
      return Math.max(10.0F, 40.0F + (hardness - 0.6F) * 14.285714F);
   }

   public static int flightTicks(Vec3 from, Vec3 aim) {
      return Math.max(12, (int)Math.ceil(aim.subtract(from).horizontalDistance() / 1.5));
   }

   public static Vec3 ballisticVelocity(Vec3 from, Vec3 aim) {
      int ticks = flightTicks(from, aim);
      return aim.subtract(from).scale(1.0 / ticks).add(0.0, 0.06 * (ticks - 1) / 2.0, 0.0);
   }

   public BlockState blockState() {
      return (BlockState)this.entityData.get(BLOCK);
   }

   public boolean launchFrom(Vec3 from, Vec3 aim) {
      if (!this.broken && Double.isFinite(from.distanceToSqr(aim)) && !(from.distanceToSqr(aim) > 4096.0)) {
         this.origin = from;
         this.setPos(from.x, from.y - 0.4, from.z);
         this.setDeltaMovement(ballisticVelocity(from, aim));
         this.tickCount = 0;
         return true;
      } else {
         return false;
      }
   }

   public void breakAt(Vec3 center) {
      if (!this.broken) {
         this.setPos(center.x, center.y - 0.4, center.z);
         this.shatter();
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.put("ThrownState", NbtUtils.writeBlockState(this.blockState()));
      tag.putFloat("ThrownDamage", this.damage);
      tag.putDouble("ThrownOriginX", this.origin.x);
      tag.putDouble("ThrownOriginY", this.origin.y);
      tag.putDouble("ThrownOriginZ", this.origin.z);
      tag.putInt("ThrownAge", this.tickCount);
      tag.putBoolean("ThrownBroken", this.broken);
      ListTag drops = new ListTag();

      for (ItemStack drop : this.oreDrops) {
         if (!drop.isEmpty()) {
            drops.add(drop.save(this.registryAccess()));
         }
      }

      tag.put("ThrownOreDrops", drops);
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.entityData.set(BLOCK, NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("ThrownState")));
      this.damage = tag.getFloat("ThrownDamage");
      this.origin = new Vec3(tag.getDouble("ThrownOriginX"), tag.getDouble("ThrownOriginY"), tag.getDouble("ThrownOriginZ"));
      this.tickCount = tag.getInt("ThrownAge");
      this.broken = tag.getBoolean("ThrownBroken");
      ListTag drops = tag.getList("ThrownOreDrops", 10);
      ArrayList<ItemStack> restoredDrops = new ArrayList<>();

      for (int i = 0; i < drops.size(); i++) {
         ItemStack stack = ItemStack.parseOptional(this.registryAccess(), drops.getCompound(i));
         if (!stack.isEmpty()) {
            restoredDrops.add(stack);
         }
      }

      this.oreDrops = List.copyOf(restoredDrops);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(BLOCK, net.minecraft.world.level.block.Blocks.STONE.defaultBlockState());
   }

   public void tick() {
      super.tick();
      if (this.broken) {
         this.discard();
      } else {
         Vec3 start = this.position().add(0.0, 0.4, 0.0);
         Vec3 velocity = this.getDeltaMovement();
         Vec3 end = start.add(velocity);
         if (!this.level().isClientSide) {
            Vec3 impact = end;
            double nearest = Double.POSITIVE_INFINITY;

            for (double x : new double[]{-0.4, 0.0, 0.4}) {
               for (double y : new double[]{-0.4, 0.0, 0.4}) {
                  for (double z : new double[]{-0.4, 0.0, 0.4}) {
                     Vec3 offset = new Vec3(x, y, z);
                     BlockHitResult hit = this.level()
                        .clip(new ClipContext(start.add(offset), end.add(offset), net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, this));
                     if (hit.getType() != Type.MISS) {
                        Vec3 center = hit.getLocation().subtract(offset);
                        double distance = center.distanceToSqr(start);
                        if (distance < nearest) {
                           nearest = distance;
                           impact = center;
                        }
                     }
                  }
               }
            }

            Entity victim = null;
            AABB swept = new AABB(start, end).inflate(0.4);

            for (Entity entity : this.level().getEntities(this, swept, this::canHitEntity)) {
               AABB bounds = entity.getBoundingBox().inflate(0.4);
               Vec3 contact = bounds.contains(start) ? start : (Vec3)bounds.clip(start, end).orElse(null);
               if (contact != null && contact.distanceToSqr(start) < nearest) {
                  nearest = contact.distanceToSqr(start);
                  impact = contact;
                  victim = entity;
               }
            }

            if (Double.isFinite(nearest)) {
               this.setPos(impact.x, impact.y - 0.4, impact.z);
               if (victim != null) {
                  boolean damaged = victim.hurt(this.damageSources().thrown(this, this.getOwner()), this.damage);
                  if (damaged && victim instanceof LivingEntity living && this.getOwner() instanceof MilimEntity milim) {
                     milim.recordSatanaelContact(living);
                  }
               }

               this.shatter();
               return;
            }

            if (this.tickCount >= 100 || end.subtract(this.origin).horizontalDistance() > 64.4 || end.y < this.level().getMinBuildHeight()) {
               this.shatter();
               return;
            }
         }

         this.setPos(end.x, end.y - 0.4, end.z);
         this.setDeltaMovement(velocity.add(0.0, -0.06, 0.0));
      }
   }

   protected boolean canHitEntity(Entity entity) {
      Entity owner = this.getOwner();
      return entity != owner
         && entity.isAlive()
         && entity.isPickable()
         && !entity.isSpectator()
         && !(entity instanceof Player player && player.getAbilities().invulnerable)
         && (owner == null || !owner.isAlliedTo(entity));
   }

   private void shatter() {
      if (!this.broken && this.level() instanceof ServerLevel server) {
         this.broken = true;
         Vec3 var6 = this.position().add(0.0, 0.4, 0.0);
         server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.blockState()), var6.x, var6.y, var6.z, 28, 0.3, 0.3, 0.3, 0.15);
         SoundType sound = this.blockState().getSoundType();
         server.playSound(null, this.getX(), this.getY(), this.getZ(), sound.getBreakSound(), SoundSource.HOSTILE, 1.0F, 0.9F);

         for (ItemStack drop : this.oreDrops) {
            if (!drop.isEmpty()) {
               server.addFreshEntity(new ItemEntity(server, var6.x, var6.y, var6.z, drop.copy()));
            }
         }

         this.oreDrops = List.of();
         this.discard();
      }
   }
}
