package com.iafenvoy.iceandfire.item.tool;

import com.iafenvoy.iceandfire.data.TrollType;
import com.iafenvoy.iceandfire.registry.IafToolMaterials;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class TrollWeaponItem extends SwordItem {
   public final TrollType.ITrollWeapon weapon;

   public TrollWeaponItem(TrollType.ITrollWeapon weapon) {
      super(
         IafToolMaterials.TROLL_WEAPON_TOOL_MATERIAL,
         new Properties().component(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(IafToolMaterials.TROLL_WEAPON_TOOL_MATERIAL, 15, -3.5F))
      );
      this.weapon = weapon;
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      return !(attacker instanceof Player player)
         ? super.hurtEnemy(stack, target, attacker)
         : player.getAttackStrengthScale(0.0F) < 0.95 || player.attackAnim != 0.0F;
   }

   public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (entity instanceof Player player && selected && player.getAttackStrengthScale(0.0F) < 0.95 && player.attackAnim > 0.0F) {
         player.swingTime--;
      }
   }

   public boolean onEntitySwing(LivingEntity LivingEntity, ItemStack stack) {
      if (LivingEntity instanceof Player player) {
         if (player.getAttackStrengthScale(0.0F) < 1.0F && player.attackAnim > 0.0F) {
            return true;
         }

         player.swingTime = -1;
      }

      return false;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("item.iceandfire.legendary_weapon.desc").withStyle(ChatFormatting.GRAY));
   }
}
