package twilightforest.item;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import twilightforest.entity.projectile.IceBomb;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class IceBombItem extends Item implements ProjectileItem {
   public IceBombItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      player.playSound((SoundEvent)TFSounds.ICE_BOMB_FIRED.get(), 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
      if (!level.isClientSide()) {
         player.getItemInHand(hand).consume(1, player);
         IceBomb ice = new IceBomb((EntityType<? extends IceBomb>)TFEntities.THROWN_ICE.get(), level, player);
         ice.shootFromRotation(player, player.getXRot(), player.getYRot(), -5.0F, 1.25F, 1.0F);
         level.addFreshEntity(ice);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(hand));
   }

   public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
      return new IceBomb(level, position);
   }
}
