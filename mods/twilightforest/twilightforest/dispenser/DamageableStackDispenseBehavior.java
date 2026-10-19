package twilightforest.dispenser;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import twilightforest.util.TFItemStackUtils;

public abstract class DamageableStackDispenseBehavior extends DefaultDispenseItemBehavior {
   private boolean fired = false;

   public ItemStack execute(BlockSource source, ItemStack stack) {
      ServerLevel level = source.level();
      Position pos = DispenserBlock.getDispensePosition(source);
      Direction direction = (Direction)source.state().getValue(DispenserBlock.FACING);
      if (stack.getMaxDamage() >= stack.getDamageValue() + this.getDamageAmount()) {
         Projectile projectileentity = this.getProjectileEntity(level, pos, stack);
         projectileentity.shoot(
            direction.getStepX(), direction.getStepY() + 0.1F, direction.getStepZ(), this.getProjectileVelocity(), this.getProjectileInaccuracy()
         );
         level.addFreshEntity(projectileentity);
         TFItemStackUtils.hurtButDontBreak(stack, 1, level, null);
         this.fired = true;
      }

      return stack;
   }

   protected void playSound(BlockSource source) {
      if (this.fired) {
         source.level().playSound(null, source.center().x(), source.center().y(), source.center().z(), this.getFiredSound(), SoundSource.NEUTRAL, 1.0F, 1.0F);
         this.fired = false;
      } else {
         source.level().levelEvent(1001, source.pos(), 0);
      }
   }

   protected abstract Projectile getProjectileEntity(Level var1, Position var2, ItemStack var3);

   protected abstract int getDamageAmount();

   protected abstract SoundEvent getFiredSound();

   protected float getProjectileInaccuracy() {
      return 18.0F;
   }

   protected float getProjectileVelocity() {
      return 1.1F;
   }
}
