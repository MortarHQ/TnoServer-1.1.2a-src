package net.jadenxgamer.elysium_api.impl.core.item;

import java.lang.reflect.Constructor;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ElysiumArrowItem extends ArrowItem {
   private final Class<? extends AbstractArrow> arrowClass;
   private final Pickup pickup;

   public ElysiumArrowItem(Class<? extends AbstractArrow> arrowClass, Pickup pickup, Properties properties) {
      super(properties);
      this.arrowClass = arrowClass;
      this.pickup = pickup;
   }

   public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
      try {
         Constructor<? extends AbstractArrow> constructor = this.arrowClass.getConstructor(Level.class, LivingEntity.class, ItemStack.class, ItemStack.class);
         return constructor.newInstance(level, shooter, ammo.copyWithCount(1), weapon);
      } catch (Exception e) {
         throw new RuntimeException(
            "Failed to create arrow via constructor (Level, LivingEntity, ItemStack, ItemStack) for class: " + this.arrowClass.getName(), e
         );
      }
   }

   public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
      try {
         Constructor<? extends AbstractArrow> constructor = this.arrowClass
            .getConstructor(Level.class, double.class, double.class, double.class, ItemStack.class, ItemStack.class);
         AbstractArrow arrow = constructor.newInstance(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
         arrow.pickup = this.pickup;
         return arrow;
      } catch (Exception e) {
         throw new RuntimeException(
            "Failed to create arrow via constructor (Level, double, double, double, ItemStack, ItemStack) for class: " + this.arrowClass.getName(), e
         );
      }
   }
}
