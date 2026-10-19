package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ItemNagaFangDagger extends DiggerItem {
   public ItemNagaFangDagger(Properties properties) {
      super(Tiers.STONE, BlockTags.MINEABLE_WITH_HOE, properties);
   }

   public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
      return !player.isCreative();
   }

   public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
      return enchantment.is(Enchantments.SWEEPING_EDGE) ? false : super.isPrimaryItemFor(stack, enchantment);
   }

   public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
      return enchantment.is(Enchantments.SWEEPING_EDGE) ? false : super.supportsEnchantment(stack, enchantment);
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (super.hurtEnemy(stack, target, attacker)) {
         target.addEffect(
            new MobEffectInstance(MobEffects.POISON, (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.NAGA_FANG_DAGGER.poisonDuration.get(), 3, false, true)
         );
         return true;
      } else {
         return false;
      }
   }

   public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
      Item item = repair.getItem();
      return item instanceof ItemNagaFang;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
   }
}
