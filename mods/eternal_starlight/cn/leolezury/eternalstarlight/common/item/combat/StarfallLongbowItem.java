package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.util.ESConventionalTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class StarfallLongbowItem extends BowItem {
   public StarfallLongbowItem(Properties properties) {
      super(properties);
   }

   protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
      Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
      ESDataAttachments.ARROW_TYPE.setData(projectile, "eternal_starlight:starfall");
      return projectile;
   }

   public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
      return repairCandidate.is(ESConventionalTags.Items.INGOTS_AETHERSENT) || super.isValidRepairItem(stack, repairCandidate);
   }
}
