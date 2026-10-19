package twilightforest.item;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import twilightforest.components.entity.PotionFlaskTrackingAttachment;
import twilightforest.components.item.PotionFlaskComponent;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFSounds;

public class BrittleFlaskItem extends Item {
   public static final int DOSES = 3;

   public BrittleFlaskItem(Properties properties) {
      super(properties);
   }

   public ItemStack getDefaultInstance() {
      ItemStack itemstack = super.getDefaultInstance();
      itemstack.set(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY);
      return itemstack;
   }

   public int getMaxStackSize(ItemStack stack) {
      return ((PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY)).potion().potion().isPresent()
         ? 1
         : super.getMaxStackSize(stack);
   }

   public boolean isBarVisible(ItemStack stack) {
      return ((PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY)).potion().potion().isPresent();
   }

   public int getBarColor(ItemStack stack) {
      return ARGB32.opaque(((PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY)).potion().getColor());
   }

   public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
      PotionFlaskComponent flaskContents = (PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY);
      PotionContents potionContents = (PotionContents)other.get(DataComponents.POTION_CONTENTS);
      if (action == ClickAction.SECONDARY
         && potionContents != null
         && (flaskContents.potion().potion().isEmpty() || flaskContents.potion().equals(potionContents))
         && flaskContents.doses() < 3 - flaskContents.breakage()) {
         if (!player.getAbilities().instabuild) {
            other.shrink(1);
            if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
               player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
            }
         }

         this.changeAndConsumeFlask(
            stack, player, flask -> flask.update(TFDataComponents.POTION_FLASK_CONTENTS, flaskContents, component -> component.tryAddDose(potionContents))
         );
         player.playSound((SoundEvent)TFSounds.FLASK_FILL.get(), (flaskContents.doses() + 1) * 0.25F, player.level().getRandom().nextFloat() * 0.1F + 0.9F);
         return true;
      } else {
         return false;
      }
   }

   public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
      PotionFlaskComponent flaskContents = (PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY);
      ItemStack other = slot.getItem();
      PotionContents potionContents = (PotionContents)other.get(DataComponents.POTION_CONTENTS);
      if (action == ClickAction.SECONDARY
         && potionContents != null
         && (flaskContents.potion().potion().isEmpty() || flaskContents.potion().equals(potionContents))
         && flaskContents.doses() < 3 - flaskContents.breakage()) {
         if (!player.getAbilities().instabuild) {
            other.shrink(1);
            if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
               player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
            }
         }

         this.changeAndConsumeFlask(
            stack, player, flask -> flask.update(TFDataComponents.POTION_FLASK_CONTENTS, flaskContents, component -> component.tryAddDose(potionContents))
         );
         player.playSound((SoundEvent)TFSounds.FLASK_FILL.get(), (flaskContents.doses() + 1) * 0.25F, player.level().getRandom().nextFloat() * 0.1F + 0.9F);
         return true;
      } else {
         return false;
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      PotionFlaskComponent flaskContents = (PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY);
      if (flaskContents.potion() == PotionContents.EMPTY) {
         return InteractionResultHolder.fail(player.getItemInHand(hand));
      } else {
         return flaskContents.doses() > 0 ? ItemUtils.startUsingInstantly(level, player, hand) : InteractionResultHolder.fail(player.getItemInHand(hand));
      }
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 32;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.DRINK;
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      PotionFlaskComponent flaskContents = (PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY);
      if (flaskContents.potion() != PotionContents.EMPTY && entity instanceof Player player) {
         if (!level.isClientSide()) {
            for (MobEffectInstance mobeffectinstance : flaskContents.potion().getAllEffects()) {
               if (mobeffectinstance.is(MobEffects.HARM) != entity.isInvertedHealAndHarm() && mobeffectinstance.getAmplifier() > 0) {
                  entity.hurt(entity.damageSources().source(TFDamageTypes.FAILED_CHALLENGE), 6 << mobeffectinstance.getAmplifier());
               } else if (((MobEffect)mobeffectinstance.getEffect().value()).isInstantenous()) {
                  ((MobEffect)mobeffectinstance.getEffect().value()).applyInstantenousEffect(player, player, player, mobeffectinstance.getAmplifier(), 1.0);
               } else {
                  player.addEffect(new MobEffectInstance(mobeffectinstance));
               }
            }

            if (!player.isCreative() && !player.isSpectator() && player instanceof ServerPlayer serverPlayer) {
               flaskContents.potion()
                  .potion()
                  .ifPresent(
                     potion -> ((PotionFlaskTrackingAttachment)player.getData(TFDataAttachments.FLASK_DOSES)).trackDrink((Holder<Potion>)potion, serverPlayer)
                  );
            }
         }

         player.awardStat(Stats.ITEM_USED.get(this));
         if (!player.getAbilities().instabuild) {
            this.changeAndConsumeFlask(stack, player, flask -> flask.update(TFDataComponents.POTION_FLASK_CONTENTS, flaskContents, component -> {
               component = component.removeDose();
               if (component.breakable()) {
                  if (component.breakage() >= 3) {
                     flask.shrink(1);
                     level.playSound(null, player, (SoundEvent)TFSounds.BRITTLE_FLASK_BREAK.get(), player.getSoundSource(), 1.5F, 0.7F);
                  } else {
                     level.playSound(null, player, (SoundEvent)TFSounds.BRITTLE_FLASK_CRACK.get(), player.getSoundSource(), 1.5F, 2.0F);
                  }
               }

               return component;
            }));
         }
      }

      return super.finishUsingItem(stack, level, entity);
   }

   private void changeAndConsumeFlask(ItemStack stack, Player player, Consumer<ItemStack> onDrink) {
      if (stack.getCount() > 1) {
         ItemStack copy = stack.copyWithCount(1);
         stack.shrink(1);
         onDrink.accept(copy);
         if (!player.getInventory().add(copy)) {
            player.drop(copy, false);
         }
      } else {
         onDrink.accept(stack);
      }
   }

   public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
      return Optional.of(
         new BrittleFlaskItem.Tooltip((PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY), 3)
      );
   }

   public int getBarWidth(ItemStack stack) {
      return Math.round(
         13.0F
            - Math.abs(((PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY)).doses() - 3)
               * 13.0F
               / 3.0F
      );
   }

   public record Tooltip(PotionFlaskComponent component, int maxDoses) implements TooltipComponent {
   }
}
