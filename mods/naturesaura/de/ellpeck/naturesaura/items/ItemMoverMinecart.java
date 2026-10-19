package de.ellpeck.naturesaura.items;

import de.ellpeck.naturesaura.entities.EntityMoverMinecart;
import de.ellpeck.naturesaura.entities.ModEntities;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;

public class ItemMoverMinecart extends ItemImpl {
   public ItemMoverMinecart() {
      super("mover_cart", new Properties().stacksTo(1));
   }

   @Nonnull
   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      if (BaseRailBlock.isRail(level.getBlockState(pos))) {
         if (!level.isClientSide) {
            AbstractMinecart cart = new EntityMoverMinecart(ModEntities.MOVER_CART, level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            level.addFreshEntity(cart);
         }

         context.getPlayer().getItemInHand(context.getHand()).shrink(1);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
