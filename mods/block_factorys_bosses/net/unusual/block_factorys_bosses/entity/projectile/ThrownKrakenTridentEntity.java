package net.unusual.block_factorys_bosses.entity.projectile;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ThrownKrakenTridentEntity extends ThrownTrident {
   private static final float PULL_DISTANCE = 5.0F;
   private static final float PULL_DISTANCE_DOUBLE = 10.0F;
   private static final float PULL_DISTANCE_SQUARED = 25.0F;

   public ThrownKrakenTridentEntity(EntityType<? extends ThrownKrakenTridentEntity> entityType, Level level) {
      super(entityType, level);
   }

   public ThrownKrakenTridentEntity(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
      super((EntityType)BossesRiseEntities.KRAKEN_TRIDENT.get(), level);
      this.initializeAbstractArrow(shooter, pickupItemStack);
      this.initializeThrownTrident(pickupItemStack);
   }

   public ThrownKrakenTridentEntity(Level level, double x, double y, double z, ItemStack pickupItemStack) {
      super((EntityType)BossesRiseEntities.KRAKEN_TRIDENT.get(), level);
      this.initializeAbstractArrow(x, y, z, pickupItemStack);
      this.initializeThrownTrident(pickupItemStack);
   }

   private void initializeThrownTrident(ItemStack pickupItemStack) {
      this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
      this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());
   }

   private void initializeAbstractArrow(LivingEntity owner, ItemStack pickupItemStack) {
      this.initializeAbstractArrow(owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), pickupItemStack);
      this.setOwner(owner);
   }

   private void initializeAbstractArrow(double x, double y, double z, ItemStack pickupItemStack) {
      this.pickupItemStack = pickupItemStack.copy();
      this.setCustomName((Component)pickupItemStack.get(DataComponents.CUSTOM_NAME));
      Unit unit = (Unit)pickupItemStack.remove(DataComponents.INTANGIBLE_PROJECTILE);
      if (unit != null) {
         this.pickup = Pickup.CREATIVE_ONLY;
      }

      this.setPos(x, y, z);
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)BossesRiseItems.KRAKEN_TRIDENT.get());
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      this.pullEntities();
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      this.pullEntities();
   }

   protected void pullEntities() {
      this.level()
         .getEntities(this, AABB.ofSize(this.position(), 10.0, 10.0, 10.0), candidate -> candidate.isPushable() && candidate.distanceToSqr(this) <= 25.0)
         .forEach(this::pullEntity);
   }

   protected void pullEntity(Entity entity) {
      Entity owner = this.getOwner();
      entity.hurt(this.damageSources().trident(this, (Entity)(owner == null ? this : owner)), 5.0F);
      Vec3 direction = this.position().subtract(entity.position()).normalize().scale(0.4F);
      entity.push(direction.x(), direction.y(), direction.z());
   }
}
