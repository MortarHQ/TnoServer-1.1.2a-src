package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.entity.DeathWormEggEntity;
import com.iafenvoy.iceandfire.registry.IafEntities;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class DeathwormEggItem extends Item implements ProjectileItem {
   private final boolean gigantic;

   public DeathwormEggItem(boolean gigantic) {
      super(new Properties().stacksTo(1));
      this.gigantic = gigantic;
   }

   public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.getItemInHand(handIn);
      if (!playerIn.isCreative()) {
         itemstack.shrink(1);
      }

      worldIn.playSound(
         null,
         playerIn.getX(),
         playerIn.getY(),
         playerIn.getZ(),
         SoundEvents.EGG_THROW,
         SoundSource.PLAYERS,
         0.5F,
         0.4F / (worldIn.random.nextFloat() * 0.4F + 0.8F)
      );
      if (!worldIn.isClientSide) {
         DeathWormEggEntity entityegg = new DeathWormEggEntity(
            (EntityType<? extends ThrowableItemProjectile>)IafEntities.DEATH_WORM_EGG.get(), playerIn, worldIn, this.gigantic
         );
         entityegg.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F, 1.0F);
         worldIn.addFreshEntity(entityegg);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
   }

   public Projectile asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
      return new DeathWormEggEntity(
         (EntityType<? extends ThrowableItemProjectile>)IafEntities.DEATH_WORM_EGG.get(), pos.x(), pos.y(), pos.z(), world, this.gigantic
      );
   }
}
