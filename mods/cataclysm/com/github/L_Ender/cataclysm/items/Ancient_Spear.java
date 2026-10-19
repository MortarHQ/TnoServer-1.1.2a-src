package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Sandstorm_Projectile;
import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class Ancient_Spear extends Cataclysm_Weapon implements ILeftClick {
   public Ancient_Spear(Properties group) {
      super(group);
   }

   private boolean isCharged(Player player, ItemStack stack) {
      return player.getAttackStrengthScale(0.5F) > 0.9F;
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity player) {
      if (player instanceof Player player1 && this.isCharged(player1, stack)) {
         this.launchTornado(stack, player);
      }

      return true;
   }

   public void postHurtEnemy(ItemStack p_345950_, LivingEntity p_345617_, LivingEntity p_345537_) {
      p_345950_.hurtAndBreak(1, p_345537_, EquipmentSlot.MAINHAND);
   }

   public boolean isValidRepairItem(ItemStack pickaxe, ItemStack stack) {
      return stack.is((Item)ModItems.ANCIENT_METAL_INGOT.get());
   }

   @Override
   public boolean onLeftClick(ItemStack stack, LivingEntity playerIn) {
      return !stack.is((Item)ModItems.ANCIENT_SPEAR.get()) || playerIn instanceof Player && !this.isCharged((Player)playerIn, stack)
         ? false
         : this.launchTornado(stack, playerIn);
   }

   public boolean launchTornado(ItemStack stack, LivingEntity playerIn) {
      Level worldIn = playerIn.level();
      if (!worldIn.isClientSide) {
         stack.hurtAndBreak(1, playerIn, EquipmentSlot.MAINHAND);
         float d7 = playerIn.getYRot();
         float d = playerIn.getXRot();
         float d1 = -Mth.sin(d7 * (float) (Math.PI / 180.0)) * Mth.cos(d * (float) (Math.PI / 180.0));
         float d2 = -Mth.sin(d * (float) (Math.PI / 180.0));
         float d3 = Mth.cos(d7 * (float) (Math.PI / 180.0)) * Mth.cos(d * (float) (Math.PI / 180.0));
         double theta = d7 * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         double x = playerIn.getX() + vecX;
         double Z = playerIn.getZ() + vecZ;
         Sandstorm_Projectile largefireball = new Sandstorm_Projectile(
            playerIn, d1, d2, d3, playerIn.level(), (float)CMCommonConfig.AncientSpear.sandstormdamage
         );
         largefireball.setState(1);
         largefireball.setPos(x, playerIn.getEyeY() - 0.5, Z);
         worldIn.addFreshEntity(largefireball);
         return true;
      } else {
         return false;
      }
   }

   public boolean isEnchantable(ItemStack stack) {
      return true;
   }

   public int getEnchantmentValue() {
      return 16;
   }

   public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
      return !player.isCreative();
   }

   public float getDestroySpeed(ItemStack p_43288_, BlockState p_43289_) {
      if (p_43289_.is(Blocks.COBWEB)) {
         return 15.0F;
      } else {
         return p_43289_.is(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
      }
   }

   public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
      return ItemAbilities.DEFAULT_TRIDENT_ACTIONS.contains(itemAbility);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flags) {
      super.appendHoverText(stack, context, tooltips, flags);
      tooltips.add(Component.translatable("item.cataclysm.ancient_spear.desc").withStyle(ChatFormatting.DARK_GREEN));
   }
}
