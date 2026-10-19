package twilightforest.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import twilightforest.enchantment.RechargeScepterEffect;
import twilightforest.entity.monster.LoyalZombie;
import twilightforest.init.TFEnchantments;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.util.TFItemStackUtils;

public class ZombieWandItem extends Item {
   public ZombieWandItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (stack.getDamageValue() == stack.getMaxDamage() && !player.getAbilities().instabuild) {
         return InteractionResultHolder.fail(stack);
      }

      if (!level.isClientSide()) {
         BlockHitResult result = getPlayerPOVHitResult(level, player, Fluid.NONE);
         if (result.getType() != Type.MISS) {
            LoyalZombie zombie = (LoyalZombie)((EntityType)TFEntities.LOYAL_ZOMBIE.get()).create(level);
            zombie.moveTo(result.getLocation());
            if (!level.noCollision(zombie, zombie.getBoundingBox())) {
               return InteractionResultHolder.pass(stack);
            }

            zombie.spawnAnim();
            zombie.setTame(true, false);
            zombie.setOwnerUUID(player.getUUID());
            zombie.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1));
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(TFItems.MYSTIC_CROWN) && level.getRandom().nextFloat() <= 0.1F) {
               zombie.setBaby(true);
            }

            level.addFreshEntity(zombie);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, result.getBlockPos());
            if (!player.getAbilities().instabuild) {
               TFItemStackUtils.hurtButDontBreak(stack, 1, (ServerLevel)level, player);
            }

            zombie.playSound((SoundEvent)TFSounds.ZOMBIE_SCEPTER_USE.get(), 1.0F, 1.0F);
         }
      }

      return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
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
