package com.mortal_cultivation.entity.breath;

import com.mortal_cultivation.spiritroot.SpiritRootData;
import com.mortal_cultivation.spiritroot.SpiritRootType;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.TensuraProjectile;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.joml.Vector3f;

public class AlchemistFireBreathEntity extends TensuraProjectile {
   private static final Vector3f DUST_COLOR_RED = new Vector3f(1.0F, 0.15F, 0.05F);
   private static final float BREATH_LENGTH = 10.0F;
   private static final float CONE_ANGLE = (float) (Math.PI / 12);
   private static final double COS_LIMIT = Math.cos((float) (Math.PI / 12));

   public AlchemistFireBreathEntity(EntityType<? extends AlchemistFireBreathEntity> entityType, Level level) {
      super(entityType, level);
      this.setNoGravity(true);
      this.setBurnTicks(60);
      this.setElementalAttack(true);
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.FIRE_ELEMENTAL;
   }

   public boolean shouldDiscardInWater() {
      return false;
   }

   public boolean shouldDiscardInLava() {
      return false;
   }

   public void increaseLife(int life) {
      this.setLife(Math.min(this.getLife() + life, 100));
   }

   public void tick() {
      super.tick();
      Entity owner = this.getOwner();
      if (owner == null) {
         this.discard();
      } else {
         Vec3 eye = owner.getEyePosition(1.0F).subtract(0.0, 0.8, 0.0);
         this.setPos(eye);
         this.setXRot(owner.getXRot());
         this.setYRot(owner.getYRot());
         this.yRotO = this.getYRot();
         this.xRotO = this.getXRot();
         if (!this.level().isClientSide()) {
            ServerLevel serverLevel = (ServerLevel)this.level();
            this.hitEntitiesInCone(owner, serverLevel);
            if (owner instanceof Player player) {
               for (int i = 0; i < 10; i++) {
                  Vec3 lookAngle = owner.getLookAngle()
                     .normalize()
                     .xRot(this.getRandom().nextFloat() * (float) (Math.PI / 12) * 2.0F - (float) (Math.PI / 12))
                     .yRot(this.getRandom().nextFloat() * (float) (Math.PI / 12) * 2.0F - (float) (Math.PI / 12))
                     .zRot(this.getRandom().nextFloat() * (float) (Math.PI / 12) * 2.0F - (float) (Math.PI / 12));
                  this.handleBlockSmelting(player, lookAngle);
               }
            }

            if (this.getAge() % 10 == 0) {
               this.smeltNearbyItemEntities(owner, serverLevel);
            }
         } else {
            this.spawnParticle(owner);
         }
      }
   }

   private void hitEntitiesInCone(Entity owner, ServerLevel level) {
      Vec3 eyePos = owner.getEyePosition();
      Vec3 lookVec = owner.getLookAngle().normalize();
      AABB searchBox = owner.getBoundingBox().inflate(10.0);
      List<LivingEntity> entities = level.getEntitiesOfClass(
         LivingEntity.class, searchBox, e -> e != owner && e.isAlive() && !e.isSpectator() && this.isInCone(eyePos, lookVec, e)
      );
      DamageSource source = TensuraDamageTypes.getEntityDamageSource(level, this.getDamageType(), owner);

      for (LivingEntity target : entities) {
         if (!(target instanceof Player p && p.isCreative())) {
            target.hurt(source, this.getDamage());
            if (this.getBurnTicks() > 0) {
               target.setRemainingFireTicks(Math.max(this.getBurnTicks(), target.getRemainingFireTicks()));
            }
         }
      }
   }

   private boolean isInCone(Vec3 origin, Vec3 lookDir, Entity target) {
      Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      Vec3 toTarget = targetCenter.subtract(origin);
      double distance = toTarget.length();
      return !(distance > 10.0) && !(distance < 0.5) ? lookDir.dot(toTarget.normalize()) >= COS_LIMIT : false;
   }

   private void handleBlockSmelting(Player player, Vec3 lookAngle) {
      ClipContext context = new ClipContext(player.getEyePosition(), player.getEyePosition().add(lookAngle.scale(10.0)), Block.OUTLINE, Fluid.NONE, this);
      BlockHitResult result = this.level().clip(context);
      if (result.getType() == Type.BLOCK) {
         this.trySmeltBlock(result.getBlockPos());
      }
   }

   private void trySmeltBlock(BlockPos pos) {
      if (this.level() instanceof ServerLevel serverLevel) {
         BlockState state = serverLevel.getBlockState(pos);
         if (!state.isAir() && state.getBlock() != Blocks.BEDROCK) {
            ItemStack blockItem = new ItemStack(state.getBlock().asItem());
            if (!blockItem.isEmpty()) {
               Optional<RecipeHolder<SmeltingRecipe>> recipeOpt = serverLevel.getRecipeManager()
                  .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(blockItem), serverLevel);
               if (recipeOpt.isPresent()) {
                  ItemStack smeltedResult = ((SmeltingRecipe)recipeOpt.get().value()).getResultItem(serverLevel.registryAccess()).copy();
                  serverLevel.destroyBlock(pos, false);
                  net.minecraft.world.level.block.Block resultBlock = net.minecraft.world.level.block.Block.byItem(smeltedResult.getItem());
                  if (resultBlock != Blocks.AIR) {
                     serverLevel.setBlockAndUpdate(pos, resultBlock.defaultBlockState());
                  } else {
                     ItemEntity resultEntity = new ItemEntity(serverLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, smeltedResult);
                     serverLevel.addFreshEntity(resultEntity);
                  }

                  serverLevel.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, 0.2, 0.2, 0.2, 0.02);
                  serverLevel.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.2, 0.2, 0.2, 0.02);
               }
            }
         }
      }
   }

   private void smeltNearbyItemEntities(Entity owner, ServerLevel serverLevel) {
      Vec3 eyePos = owner.getEyePosition();
      Vec3 lookVec = owner.getLookAngle().normalize();
      AABB searchBox = owner.getBoundingBox().inflate(10.0);

      for (ItemEntity itemEntity : serverLevel.getEntitiesOfClass(ItemEntity.class, searchBox, e -> this.isInCone(eyePos, lookVec, e))) {
         ItemStack itemStack = itemEntity.getItem();
         Optional<RecipeHolder<SmeltingRecipe>> recipeOpt = serverLevel.getRecipeManager()
            .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(itemStack), serverLevel);
         if (recipeOpt.isPresent()) {
            ItemStack smeltedResult = ((SmeltingRecipe)recipeOpt.get().value()).getResultItem(serverLevel.registryAccess()).copy();
            smeltedResult.setCount(itemStack.getCount());
            itemEntity.setItem(smeltedResult);
            serverLevel.sendParticles(ParticleTypes.SMOKE, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 5, 0.1, 0.1, 0.1, 0.02);
         }
      }
   }

   private void spawnParticle(Entity owner) {
      if (owner instanceof LivingEntity living) {
         boolean heavenRoot = false;
         if (living instanceof Player player) {
            heavenRoot = SpiritRootData.getPrimaryRoot(player).orElse(null) == SpiritRootType.HEAVEN;
         }

         Vec3 rotation = living.getLookAngle().normalize();
         Vec3 pos = living.position().add(rotation.scale(1.6));
         double x = pos.x;
         double y = pos.y + living.getEyeHeight() * 0.9F;
         double z = pos.z;
         double speed = living.getRandom().nextDouble() * 0.35 + 0.55;
         int particleCount = heavenRoot ? 40 : 30;

         for (int i = 0; i < particleCount; i++) {
            double ox = Math.random() * 0.3 - 0.15;
            double oy = Math.random() * 0.3 - 0.15;
            double oz = Math.random() * 0.3 - 0.15;
            Vec3 randomVec = new Vec3(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).normalize();
            Vec3 result = rotation.scale(3.0).add(randomVec).normalize().scale(speed);
            if (heavenRoot) {
               float roll = living.getRandom().nextFloat();
               ParticleOptions particle;
               if (roll < 0.35F) {
                  particle = ParticleTypes.SOUL_FIRE_FLAME;
               } else if (roll < 0.7F) {
                  particle = ParticleTypes.FLAME;
               } else {
                  particle = new DustParticleOptions(DUST_COLOR_RED, 1.5F);
               }

               living.level().addParticle(particle, x + ox, y + oy, z + oz, result.x, result.y, result.z);
            } else {
               living.level().addParticle(ParticleTypes.FLAME, x + ox, y + oy, z + oz, result.x, result.y, result.z);
            }

            if (living.getRandom().nextFloat() < 0.2F) {
               living.level().addParticle(ParticleTypes.SMOKE, x + ox, y + oy, z + oz, result.x * 0.5, result.y * 0.5, result.z * 0.5);
            }
         }
      }
   }

   public static AlchemistFireBreathEntity spawnOrMaintain(
      EntityType<? extends AlchemistFireBreathEntity> entityType, LivingEntity owner, CompoundTag skillTag, float damage, double mpCost
   ) {
      Level level = owner.level();
      int breathId = skillTag.getInt("BreathEntity");
      if (breathId == 0) {
         AlchemistFireBreathEntity breath = (AlchemistFireBreathEntity)entityType.create(level);
         if (breath == null) {
            return null;
         }

         breath.setLife(5);
         breath.setDamage(damage);
         breath.setOwner(owner);
         breath.setPos(owner.position().add(0.0, owner.getBbHeight() / 2.0, 0.0));
         breath.setMpCost(mpCost);
         level.addFreshEntity(breath);
         skillTag.putInt("BreathEntity", breath.getId());
         return breath;
      } else if (level.getEntity(breathId) instanceof AlchemistFireBreathEntity breath) {
         breath.increaseLife(1);
         breath.setDamage(damage);
         return breath;
      } else {
         skillTag.putInt("BreathEntity", 0);
         return null;
      }
   }
}
