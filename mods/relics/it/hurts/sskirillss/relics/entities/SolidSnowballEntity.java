package it.hurts.sskirillss.relics.entities;

import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class SolidSnowballEntity extends ThrowableProjectile {
   private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(SolidSnowballEntity.class, EntityDataSerializers.INT);

   public void setSize(int amount) {
      this.getEntityData().set(SIZE, amount);
   }

   public int getSize() {
      return (Integer)this.getEntityData().get(SIZE);
   }

   public SolidSnowballEntity(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public SolidSnowballEntity(Level level) {
      super((EntityType)EntityRegistry.SOLID_SNOWBALL.get(), level);
   }

   public void tick() {
      super.tick();
      if (this.tickCount > 300) {
         this.discard();
      }

      Level level = this.level();
      if (!level.isClientSide()) {
         float scale = this.getSize() * 0.0035F;
         ((ServerLevel)level).sendParticles(ParticleTypes.SNOWFLAKE, this.xo, this.yo + this.getBbHeight() / 2.0F, this.zo, 3, scale, scale, scale, 0.025F);
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      BlockPos pos = result.getBlockPos();
      BlockState state = this.level().getBlockState(pos);
      if (state.blocksMotion()) {
         this.discard();
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      if (!this.level().isClientSide()
         && result.getEntity() instanceof LivingEntity entity
         && (this.getOwner() == null || !entity.getStringUUID().equals(this.getOwner().getStringUUID()))) {
         ItemStack stack = EntityUtils.findEquippedCurio(this.getOwner(), (Item)ItemRegistry.WOOL_MITTEN.get());
         if (stack.getItem() instanceof IRelicItem relic) {
            boolean mayContinue = false;
            float damage = (float)(this.getSize() * relic.getStatValue(stack, "mold", "damage"));
            if (this.getOwner() instanceof Player player) {
               if (EntityUtils.hurt(entity, this.level().damageSources().thrown(this, player), damage)) {
                  mayContinue = true;
               }
            } else if (entity.hurt(this.level().damageSources().magic(), damage)) {
               mayContinue = true;
            }

            if (mayContinue) {
               entity.addEffect(
                  new MobEffectInstance(EffectRegistry.STUN, (int)Math.round(this.getSize() * relic.getStatValue(stack, "mold", "stun")) * 20, 0, true, false)
               );
            }
         }

         this.discard();
      }
   }

   public void onRemovedFromLevel() {
      ParticleUtils.createBall(ParticleTypes.SNOWFLAKE, this.position(), this.level(), 1 + this.getSize() / 10, 0.1F + this.getSize() * 0.005F);
      if (!this.level().isClientSide()) {
         Entity owner = this.getOwner();
         if (owner != null) {
            ItemStack stack = EntityUtils.findEquippedCurio(owner, (Item)ItemRegistry.WOOL_MITTEN.get());
            if (stack.getItem() instanceof IRelicItem relic) {
               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(this.getSize() / 15.0F))) {
                  if (!entity.getStringUUID().equals(owner.getStringUUID())) {
                     entity.setTicksFrozen(
                        (int)(
                           100L
                              + Math.round(
                                 this.getSize()
                                    * relic.getStatValue(EntityUtils.findEquippedCurio(owner, (Item)ItemRegistry.WOOL_MITTEN.get()), "mold", "freeze")
                              )
                        )
                     );
                  }
               }

               if (owner instanceof LivingEntity entity) {
                  relic.spreadRelicExperience(entity, stack, (int)Math.floor(this.getSize() / 5.0F));
               }

               this.level().playSound(null, this.blockPosition(), SoundEvents.SNOW_BREAK, SoundSource.MASTER, 1.0F, 0.5F);
            }
         }
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(SIZE, 0);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setSize(compound.getInt("Size"));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putFloat("Size", this.getSize());
   }

   public boolean isPushedByFluid() {
      return false;
   }
}
