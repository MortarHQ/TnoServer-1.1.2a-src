package twilightforest.entity.boss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;

public class HydraMortar extends ThrowableProjectile {
   private static final int BURN_FACTOR = 5;
   private static final int DIRECT_DAMAGE = 18;
   public int fuse = 80;
   private boolean megaBlast = false;

   public HydraMortar(EntityType<? extends HydraMortar> type, Level world) {
      super(type, world);
   }

   public HydraMortar(EntityType<? extends HydraMortar> type, Level world, HydraHead head) {
      super(type, (LivingEntity)head.getParent(), world);
      Vec3 vector = head.getLookAngle();
      double dist = 3.5;
      double px = head.getX() + vector.x() * dist;
      double py = head.getY() + 1.0 + vector.y() * dist;
      double pz = head.getZ() + vector.z() * dist;
      this.moveTo(px, py, pz, 0.0F, 0.0F);
      head.setDeltaMovement(Vec3.ZERO);
      this.shootFromRotation(head, head.getXRot(), head.getYRot(), -20.0F, 0.5F, 1.0F);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      if (this.onGround()) {
         this.getDeltaMovement().multiply(0.9, 0.9, 0.9);
         if (!this.level().isClientSide() && this.fuse-- <= 0) {
            this.detonate();
         }
      }
   }

   public void setToBlasting() {
      this.megaBlast = true;
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.megaBlast) {
         if (result.getDirection() != Direction.UP) {
            this.detonate();
         }

         this.setDeltaMovement(this.getDeltaMovement().x(), 0.0, this.getDeltaMovement().z());
         this.setOnGround(true);
      } else {
         this.detonate();
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

   protected void onHitEntity(EntityHitResult result) {
      Entity entity = result.getEntity();
      if (!this.level().isClientSide()
         && this.getOwner() != null
         && !(entity instanceof HydraMortar mortar && !mortar.getOwner().is(this.getOwner()))
         && !entity.is(this.getOwner())
         && !this.isPartOfHydra(entity)) {
         this.detonate();
      }
   }

   private boolean isPartOfHydra(Entity entity) {
      return this.getOwner() instanceof Hydra && entity instanceof HydraPart part && ((Hydra)part.getParent()).is(this.getOwner());
   }

   public float getBlockExplosionResistance(Explosion explosion, BlockGetter getter, BlockPos pos, BlockState state, FluidState fluid, float idk) {
      float resistance = super.getBlockExplosionResistance(explosion, getter, pos, state, fluid, idk);
      if (this.megaBlast && !state.is(BlockTagGenerator.COMMON_PROTECTIONS)) {
         resistance = Math.min(0.8F, resistance);
      }

      return resistance;
   }

   private void detonate() {
      float explosionPower = this.megaBlast ? 4.0F : 0.1F;
      boolean flag = EventHooks.canEntityGrief(this.level(), this);
      this.level().explode(this, this.getX(), this.getY(), this.getZ(), explosionPower, flag, ExplosionInteraction.MOB);

      for (Entity nearby : this.level().getEntities(this, this.getBoundingBox().inflate(1.0, 1.0, 1.0))) {
         if ((!nearby.fireImmune() || nearby instanceof Hydra || nearby instanceof HydraPart)
            && nearby.hurt(
               TFDamageTypes.getIndirectEntityDamageSource(this.level(), TFDamageTypes.HYDRA_MORTAR, this, this.getOwner(), (EntityType)TFEntities.HYDRA.get()),
               18.0F
            )) {
            nearby.igniteForSeconds(5.0F);
         }
      }

      this.discard();
   }

   public boolean hurt(DamageSource source, float amount) {
      super.hurt(source, amount);
      if (source.getEntity() != null && !this.level().isClientSide()) {
         Vec3 vec3d = source.getEntity().getLookAngle();
         if (vec3d != null) {
            this.shoot(vec3d.x(), vec3d.y(), vec3d.z(), 1.5F, 0.1F);
            this.setOnGround(false);
            this.fuse += 20;
         }

         if (source.getEntity() instanceof LivingEntity) {
            this.setOwner(source.getEntity());
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isOnFire() {
      return true;
   }

   public boolean isPickable() {
      return true;
   }

   public float getPickRadius() {
      return 1.5F;
   }

   protected double getDefaultGravity() {
      return 0.05;
   }
}
