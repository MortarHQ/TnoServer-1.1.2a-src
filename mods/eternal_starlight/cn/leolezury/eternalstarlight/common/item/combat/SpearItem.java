package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownSpear;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class SpearItem extends TieredItem implements ProjectileItem {
   public SpearItem(Tier tier, Properties properties) {
      super(tier, properties.component(DataComponents.TOOL, createToolProperties()));
   }

   public static ItemAttributeModifiers createAttributes(Tier tier, float damage, float speed) {
      return ItemAttributeModifiers.builder()
         .add(
            Attributes.ATTACK_DAMAGE,
            new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND
         )
         .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
         .build();
   }

   public static Tool createToolProperties() {
      return new Tool(List.of(), 1.0F, 2);
   }

   public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
      return !player.isCreative();
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.SPEAR;
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 72000;
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
      if (living instanceof Player player) {
         int i = this.getUseDuration(stack, living) - timeLeft;
         if (i >= 10 && !isTooDamagedToUse(stack)) {
            if (!level.isClientSide) {
               stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(living.getUsedItemHand()));
               Vec3 shootPos = player.getEyePosition();
               ThrownSpear spear = this.createSpear(level, player, shootPos.x, shootPos.y, shootPos.z, stack);
               spear.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
               if (player.hasInfiniteMaterials()) {
                  spear.pickup = Pickup.CREATIVE_ONLY;
               }

               level.addFreshEntity(spear);
               if (!player.hasInfiniteMaterials()) {
                  player.getInventory().removeItem(stack);
               }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
         }
      }
   }

   public abstract ThrownSpear createSpear(Level var1, @Nullable LivingEntity var2, double var3, double var5, double var7, ItemStack var9);

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (isTooDamagedToUse(stack)) {
         return InteractionResultHolder.fail(stack);
      }

      player.startUsingItem(hand);
      return InteractionResultHolder.consume(stack);
   }

   private static boolean isTooDamagedToUse(ItemStack stack) {
      return stack.getDamageValue() >= stack.getMaxDamage() - 1;
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      return true;
   }

   public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
   }

   public int getEnchantmentValue() {
      return 1;
   }
}
