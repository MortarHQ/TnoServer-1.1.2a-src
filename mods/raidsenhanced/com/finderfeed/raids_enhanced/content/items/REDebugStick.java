package com.finderfeed.raids_enhanced.content.items;

import com.finderfeed.fdlib.util.FDTargetFinder;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.RaidBlimp;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class REDebugStick extends Item {
   private static Mob raidBlimp;

   public REDebugStick(Properties p_41383_) {
      super(p_41383_);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      if (!level.isClientSide) {
         for (RaidBlimp bl : FDTargetFinder.getEntitiesInSphere(RaidBlimp.class, player.level(), player.position(), 200.0F)) {
            Vec3 lookPos = player.getLookAngle().scale(200.0);
            Vec3 pos = player.position().add(lookPos);
            bl.getNavigation().moveTo(pos.x, pos.y, pos.z, 1.0);
         }
      }

      return super.use(level, player, hand);
   }
}
