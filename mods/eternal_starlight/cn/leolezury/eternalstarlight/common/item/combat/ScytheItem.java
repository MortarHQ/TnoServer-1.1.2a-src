package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;

public class ScytheItem extends TieredItem {
   protected final boolean canTill;

   public ScytheItem(Tier tier, boolean canTill, Properties properties) {
      super(tier, properties.component(DataComponents.TOOL, tier.createToolProperties(BlockTags.MINEABLE_WITH_HOE)));
      this.canTill = canTill;
   }

   public static ItemAttributeModifiers createAttributes(Tier tier, int damage, float speed, float reach, float sweep) {
      Builder builder = ItemAttributeModifiers.builder()
         .add(
            Attributes.ATTACK_DAMAGE,
            new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND
         )
         .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
         .add(
            Attributes.ENTITY_INTERACTION_RANGE,
            new AttributeModifier(EternalStarlight.id("weapon.entity_reach"), reach, Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND
         )
         .add(
            Attributes.BLOCK_INTERACTION_RANGE,
            new AttributeModifier(EternalStarlight.id("weapon.block_reach"), reach, Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND
         );
      if (sweep > 0.0F) {
         builder.add(
            Attributes.SWEEPING_DAMAGE_RATIO,
            new AttributeModifier(EternalStarlight.id("weapon.sweeping_damage_ratio"), sweep, Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND
         );
      }

      return builder.build();
   }

   public InteractionResult useOn(UseOnContext context) {
      if (!this.canTill) {
         return InteractionResult.PASS;
      }

      Level level = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = ESPlatform.INSTANCE.getToolTillAction(context);
      if (pair == null) {
         return InteractionResult.PASS;
      }

      Predicate<UseOnContext> predicate = (Predicate<UseOnContext>)pair.getFirst();
      Consumer<UseOnContext> consumer = (Consumer<UseOnContext>)pair.getSecond();
      if (predicate.test(context)) {
         Player player = context.getPlayer();
         level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
         if (!level.isClientSide) {
            consumer.accept(context);
            if (player != null) {
               context.getItemInHand().hurtAndBreak(1, player, context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }
         }

         return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   public static Consumer<UseOnContext> changeIntoState(BlockState state) {
      return context -> {
         context.getLevel().setBlock(context.getClickedPos(), state, 11);
         context.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, context.getClickedPos(), Context.of(context.getPlayer(), state));
      };
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
      return true;
   }

   public void postHurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
      stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
   }
}
