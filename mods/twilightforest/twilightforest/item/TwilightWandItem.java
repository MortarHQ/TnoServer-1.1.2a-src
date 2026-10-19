package twilightforest.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import twilightforest.enchantment.RechargeScepterEffect;
import twilightforest.entity.projectile.TwilightWandBolt;
import twilightforest.init.TFEnchantments;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.util.TFItemStackUtils;

public class TwilightWandItem extends Item {
   public TwilightWandItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (stack.getDamageValue() == stack.getMaxDamage() && !player.getAbilities().instabuild) {
         return InteractionResultHolder.fail(player.getItemInHand(hand));
      }

      player.playSound((SoundEvent)TFSounds.TWILIGHT_SCEPTER_USE.get(), 1.0F, (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
      if (!level.isClientSide()) {
         level.addFreshEntity(new TwilightWandBolt(level, player));
         if (!player.getAbilities().instabuild && (!player.getItemBySlot(EquipmentSlot.HEAD).is(TFItems.MYSTIC_CROWN) || level.getRandom().nextFloat() > 0.05F)
            )
          {
            TFItemStackUtils.hurtButDontBreak(stack, 1, (ServerLevel)level, player);
         }
      }

      return InteractionResultHolder.success(player.getItemInHand(hand));
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      if (entity.tickCount % 20 == 0 && level instanceof ServerLevel serverLevel && stack.has(DataComponents.ENCHANTMENTS) && !isSelected) {
         int renewal = ((ItemEnchantments)stack.get(DataComponents.ENCHANTMENTS)).getLevel(level.holderOrThrow(TFEnchantments.RENEWAL));
         if (renewal > 0) {
            RechargeScepterEffect.applyRecharge(serverLevel, stack, entity);
         }
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(
         Component.translatable("item.twilightforest.scepter.desc", new Object[]{stack.getMaxDamage() - stack.getDamageValue()}).withStyle(ChatFormatting.GRAY)
      );
   }
}
