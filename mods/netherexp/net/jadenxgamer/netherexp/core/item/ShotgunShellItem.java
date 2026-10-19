package net.jadenxgamer.netherexp.core.item;

import java.lang.reflect.Constructor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class ShotgunShellItem extends Item {
   private final Class<? extends Projectile> pelletClass;

   public ShotgunShellItem(Class<? extends Projectile> pelletClass, Properties properties) {
      super(properties);
      this.pelletClass = pelletClass;
   }

   public Projectile createPellet(Level level, LivingEntity shooter, double x, double y, double z) {
      try {
         Constructor<? extends Projectile> constructor = this.pelletClass.getConstructor(double.class, double.class, double.class, Level.class, Entity.class);
         return constructor.newInstance(x, y, z, level, shooter);
      } catch (Exception e) {
         throw new RuntimeException(
            "Failed to create pellet via constructor (double, double, double, Level, LivingEntity) for class: " + this.pelletClass.getName(), e
         );
      }
   }
}
