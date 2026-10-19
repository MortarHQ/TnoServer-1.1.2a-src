package com.chronodawn.items.combat;

import com.chronodawn.ChronoDawn;
import com.chronodawn.entities.projectiles.TimeArrowEntity;
import com.chronodawn.registry.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class TimeArrowItem extends ArrowItem {
   public TimeArrowItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties();
   }

   public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter, ItemStack weapon) {
      ChronoDawn.LOGGER.debug("TimeArrowItem.createArrow called - creating TimeArrowEntity (weapon: {})", weapon.getItem());
      TimeArrowEntity arrow = new TimeArrowEntity((EntityType<? extends TimeArrowEntity>)ModEntities.TIME_ARROW.get(), shooter, level, stack.copyWithCount(1));
      ChronoDawn.LOGGER.debug("Created TimeArrowEntity: {}", arrow.getClass().getName());
      return arrow;
   }
}
