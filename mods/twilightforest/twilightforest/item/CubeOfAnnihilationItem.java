package twilightforest.item;

import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.projectile.CubeOfAnnihilation;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFEntities;

public class CubeOfAnnihilationItem extends Item {
   public CubeOfAnnihilationItem(Properties properties) {
      super(properties);
   }

   public void inventoryTick(ItemStack stack, Level level, Entity holder, int slot, boolean isSelected) {
      if (!level.isClientSide() && stack.get(TFDataComponents.THROWN_PROJECTILE) != null && getThrownEntity(level, stack) == null) {
         stack.remove(TFDataComponents.THROWN_PROJECTILE);
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (stack.get(TFDataComponents.THROWN_PROJECTILE) != null) {
         return new InteractionResultHolder(InteractionResult.PASS, stack);
      }

      if (!level.isClientSide()) {
         CubeOfAnnihilation launchedCube = new CubeOfAnnihilation(
            (EntityType<? extends CubeOfAnnihilation>)TFEntities.CUBE_OF_ANNIHILATION.get(), level, player, stack
         );
         level.addFreshEntity(launchedCube);
         stack.set(TFDataComponents.THROWN_PROJECTILE, launchedCube.getUUID());
      }

      player.startUsingItem(hand);
      return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
   }

   @Nullable
   private static CubeOfAnnihilation getThrownEntity(Level level, ItemStack stack) {
      if (level instanceof ServerLevel server) {
         UUID id = (UUID)stack.get(TFDataComponents.THROWN_PROJECTILE);
         if (id != null) {
            Entity e = server.getEntity(id);
            if (e instanceof CubeOfAnnihilation) {
               return (CubeOfAnnihilation)e;
            }
         }
      }

      return null;
   }

   public int getUseDuration(ItemStack stack, LivingEntity user) {
      return 72000;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BLOCK;
   }

   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
   }
}
