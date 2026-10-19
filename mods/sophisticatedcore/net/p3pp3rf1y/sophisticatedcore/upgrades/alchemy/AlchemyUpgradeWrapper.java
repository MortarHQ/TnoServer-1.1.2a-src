package net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy;

import com.google.common.collect.ImmutableList;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.upgrades.EntityMatch;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.FilterItemStackHandler;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class AlchemyUpgradeWrapper extends UpgradeWrapperBase<AlchemyUpgradeWrapper, AlchemyUpgradeItem> implements ITickableUpgrade {
   private static final int CHECK_INTERVAL = 5;
   private static final int CHECK_RADIUS = 3;
   private long nextCheckTime = 0L;
   private boolean applying = false;
   private LivingEntity applyingToEntity = null;
   private int remainingApplyTime = 0;
   private ItemStack stackBeingAplied = ItemStack.EMPTY;
   private AlchemyUpgradeWrapper.AlchemyItemDefinition defBeingApplied = null;
   @Nullable
   private AlchemyUpgradeWrapper.ObservableFilterItemStackHandler filterHandler = null;
   private static List<AlchemyUpgradeWrapper.AlchemyItemDefinition> itemDefinitions = new ArrayList<>();
   private static final Method ON_HIT = ObfuscationReflectionHelper.findMethod(ThrownPotion.class, "onHit", new Class[]{HitResult.class});

   protected AlchemyUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
   }

   public List<AlchemyFilterAttribute> getFilterAttributes() {
      return (List<AlchemyFilterAttribute>)this.upgrade.getOrDefault(ModCoreDataComponents.ALCHEMY_FILTER_ATTRIBUTES, this.getEmptyAttributes());
   }

   private List<AlchemyFilterAttribute> getEmptyAttributes() {
      List<AlchemyFilterAttribute> emptyAttributes = new ArrayList<>();

      for (int i = 0; i < this.upgradeItem.getFilterSlotCount(); i++) {
         emptyAttributes.add(new AlchemyFilterAttribute(ItemStack.EMPTY, AlchemyCondition.NEVER));
      }

      return emptyAttributes;
   }

   private void setFilter(int slot, ItemStack filter) {
      List<AlchemyFilterAttribute> attributes = new ArrayList<>(this.getFilterAttributes());
      AlchemyFilterAttribute attribute = attributes.get(slot);
      if (attribute.filter().isEmpty()) {
         AlchemyCondition defaultConditionForPotion = itemDefinitions.stream()
            .filter(def -> def.filter.test(filter))
            .findFirst()
            .map(def -> def.getDefaultCondition.apply(filter))
            .orElse(AlchemyCondition.NEVER);
         attribute = attribute.setConditionAndValue(defaultConditionForPotion, defaultConditionForPotion.defaultValue());
      } else if (filter.isEmpty()) {
         attribute = attribute.setConditionAndValue(AlchemyCondition.NEVER, -1.0F);
      }

      attribute = attribute.setFilter(filter);
      attributes.set(slot, attribute);
      this.setUpgradeStackAlchemyAttributes(attributes);
      this.save();
   }

   public AlchemyCondition getCondition(int slot) {
      return this.getFilterAttributes().get(slot).condition();
   }

   public float getValue(int slot) {
      return this.getFilterAttributes().get(slot).value();
   }

   public void setConditionValue(int slot, AlchemyCondition condition, float value) {
      List<AlchemyFilterAttribute> attributes = new ArrayList<>(this.getFilterAttributes());
      attributes.set(slot, attributes.get(slot).setConditionAndValue(condition, value));
      this.setUpgradeStackAlchemyAttributes(attributes);
      this.save();
   }

   private void setUpgradeStackAlchemyAttributes(List<AlchemyFilterAttribute> attributes) {
      this.upgrade.set(ModCoreDataComponents.ALCHEMY_FILTER_ATTRIBUTES, attributes instanceof ImmutableList ? attributes : ImmutableList.copyOf(attributes));
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!level.isClientSide() && this.nextCheckTime <= level.getGameTime()) {
         if (this.remainingApplyTime > 0) {
            this.remainingApplyTime--;
            if (this.remainingApplyTime <= 0) {
               if (this.defBeingApplied.hasItemUseEffects()) {
                  this.triggerItemUseEffects(level);
               }

               this.applying = false;
               ItemStack remainingStack = this.defBeingApplied.finishUsing.apply(this.stackBeingAplied, this.applyingToEntity);
               this.stackBeingAplied = ItemStack.EMPTY;
               this.applyingToEntity = null;
               this.defBeingApplied = null;
               this.storageWrapper.getInventoryForUpgradeProcessing().insertItem(remainingStack, false);
               this.nextCheckTime = level.getGameTime() + 5L;
            } else if (this.shouldTriggerItemUseEffects()) {
               this.triggerItemUseEffects(level);
            }
         } else {
            if (entity instanceof LivingEntity livingEntity) {
               this.applyTo(livingEntity);
            } else {
               for (LivingEntity livingEntity : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(3.0), this::entityMatches)) {
                  this.applyTo(livingEntity);
                  if (this.applying) {
                     break;
                  }
               }
            }

            if (!this.applying) {
               this.nextCheckTime = level.getGameTime() + 5L;
            }
         }
      }
   }

   private boolean entityMatches(LivingEntity livingEntity) {
      if (!livingEntity.isAlive()) {
         return false;
      }

      switch (this.getEntityMatch()) {
         case PLAYERS:
            return livingEntity instanceof Player;
         case ENTITIES:
            return !(livingEntity instanceof Player);
         case PLAYERS_AND_ENTITIES:
            return true;
         default:
            return false;
      }
   }

   public void triggerItemUseEffects(Level level) {
      if (this.stackBeingAplied.getUseAnimation() == UseAnim.DRINK) {
         level.playSound(
            null,
            this.applyingToEntity.getX(),
            this.applyingToEntity.getY(),
            this.applyingToEntity.getZ(),
            this.stackBeingAplied.getDrinkingSound(),
            this.applyingToEntity.getSoundSource(),
            0.5F,
            level.random.nextFloat() * 0.1F + 0.9F
         );
      }

      if (this.stackBeingAplied.getUseAnimation() == UseAnim.EAT) {
         level.playSound(
            null,
            this.applyingToEntity.getX(),
            this.applyingToEntity.getY(),
            this.applyingToEntity.getZ(),
            this.applyingToEntity.getEatingSound(this.stackBeingAplied),
            this.applyingToEntity.getSoundSource(),
            0.5F + 0.5F * level.random.nextInt(2),
            (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.0F
         );
      }
   }

   private boolean shouldTriggerItemUseEffects() {
      if (this.remainingApplyTime < 2) {
         return false;
      }

      int applyTimePassed = this.stackBeingAplied.getUseDuration(this.applyingToEntity) - this.remainingApplyTime;
      int effectDelay = (int)(this.stackBeingAplied.getUseDuration(this.applyingToEntity) * 0.21875F);
      boolean canStartTriggering = applyTimePassed > effectDelay;
      return canStartTriggering && this.remainingApplyTime % 4 == 0;
   }

   private void applyTo(LivingEntity livingEntity) {
      for (AlchemyFilterAttribute filterAttribute : this.getFilterAttributes()) {
         if (!filterAttribute.filter().isEmpty() && filterAttribute.condition().test(livingEntity, filterAttribute.value())) {
            itemDefinitions.stream()
               .filter(def -> def.filter.test(filterAttribute.filter()))
               .findFirst()
               .ifPresent(
                  def -> {
                     if (def.canApply.test(livingEntity, filterAttribute.filter(), this.shouldMatchAllEffects(), this.shouldMatchEffectAmplifier())) {
                        InventoryHelper.iterate(
                           this.storageWrapper.getInventoryForUpgradeProcessing(),
                           (slot, stack) -> {
                              if (def.filter().test(stack)
                                 && def.stackMatches
                                    .test(
                                       stack,
                                       filterAttribute.filter(),
                                       this.shouldMatchAllEffects(),
                                       this.shouldMatchEffectDuration(),
                                       this.shouldMatchEffectAmplifier()
                                    )) {
                                 this.remainingApplyTime = def.startUsing.applyAsInt(stack, livingEntity);
                                 if (this.remainingApplyTime > 0) {
                                    this.applying = true;
                                    this.stackBeingAplied = stack.copyWithCount(1);
                                    this.defBeingApplied = def;
                                    this.applyingToEntity = livingEntity;
                                 }

                                 stack.shrink(1);
                                 this.storageWrapper.getInventoryForUpgradeProcessing().setStackInSlot(slot, stack);
                              }
                           },
                           () -> this.applying
                        );
                     }
                  }
               );
         }
      }
   }

   public static void addItemDefinition(AlchemyUpgradeWrapper.AlchemyItemDefinition itemDefinition) {
      itemDefinitions.add(itemDefinition);
   }

   private static boolean foodStackPotionEffectsMatch(
      ItemStack stack, ItemStack filter, boolean matchAllEffects, boolean matchEffectDuration, boolean matchEffectAmplifier
   ) {
      if (matchAllEffects && matchEffectDuration && matchEffectAmplifier) {
         return ItemStack.isSameItemSameComponents(filter, stack);
      }

      FoodProperties foodProperties = stack.getFoodProperties(null);
      FoodProperties filterFoodProperties = filter.getFoodProperties(null);
      if (foodProperties != null && filterFoodProperties != null) {
         if (!foodProperties.effects().isEmpty() && !filterFoodProperties.effects().isEmpty()) {
            for (PossibleEffect possibleEffect : filterFoodProperties.effects()) {
               if (!matchEffectIn(foodProperties, possibleEffect, matchEffectDuration, matchEffectAmplifier)) {
                  return false;
               }

               if (!matchAllEffects) {
                  return true;
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static void onHit(ThrownPotion thrownPotion, EntityHitResult entityHitResult) {
      try {
         ON_HIT.invoke(thrownPotion, entityHitResult);
      } catch (Exception e) {
         SophisticatedCore.LOGGER.error("Failed to invoke ThrownPotion::onHit method", e);
      }
   }

   private static boolean matchEffectIn(FoodProperties foodProperties, PossibleEffect filterEffect, boolean matchEffectDuration, boolean matchEffectAmplifier) {
      for (PossibleEffect effectInstance : foodProperties.effects()) {
         Holder<MobEffect> effect = effectInstance.effect().getEffect();
         if (effect == filterEffect.effect().getEffect()
            && (!matchEffectDuration || effectInstance.effect().getDuration() == filterEffect.effect().getDuration())
            && (!matchEffectAmplifier || effectInstance.effect().getAmplifier() == filterEffect.effect().getAmplifier())) {
            return true;
         }
      }

      return false;
   }

   public static boolean stackPotionEffectsMatch(
      ItemStack stack, ItemStack filter, boolean matchAllEffects, boolean matchEffectDuration, boolean matchEffectAmplifier
   ) {
      if (matchAllEffects && matchEffectDuration && matchEffectAmplifier) {
         return ItemStack.isSameItemSameComponents(filter, stack);
      }

      PotionContents potioncontents = (PotionContents)stack.get(DataComponents.POTION_CONTENTS);
      PotionContents filterPotionContents = (PotionContents)filter.get(DataComponents.POTION_CONTENTS);
      if (potioncontents != null && filterPotionContents != null) {
         if (potioncontents.hasEffects() && filterPotionContents.hasEffects()) {
            for (MobEffectInstance filterEffectInstance : filterPotionContents.getAllEffects()) {
               if (!matchEffectIn(potioncontents, filterEffectInstance, matchEffectDuration, matchEffectAmplifier)) {
                  return false;
               }

               if (!matchAllEffects) {
                  return true;
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean matchEffectIn(
      PotionContents potionContents, MobEffectInstance filterEffectInstance, boolean matchEffectDuration, boolean matchEffectAmplifier
   ) {
      for (MobEffectInstance effectInstance : potionContents.getAllEffects()) {
         Holder<MobEffect> effect = effectInstance.getEffect();
         if (effect == filterEffectInstance.getEffect()
            && (!matchEffectDuration || effectInstance.getDuration() == filterEffectInstance.getDuration())
            && (!matchEffectAmplifier || effectInstance.getAmplifier() == filterEffectInstance.getAmplifier())) {
            return true;
         }
      }

      return false;
   }

   public static AlchemyCondition getDefaultConditionForPotion(ItemStack potionStack) {
      PotionContents potioncontents = (PotionContents)potionStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
      Iterator<MobEffectInstance> it = potioncontents.getAllEffects().iterator();
      if (!it.hasNext()) {
         return AlchemyCondition.NEVER;
      } else {
         Holder<MobEffect> effect = it.next().getEffect();
         if (effect == MobEffects.WATER_BREATHING) {
            return AlchemyCondition.UNDER_WATER;
         } else if (effect == MobEffects.HEAL || effect == MobEffects.REGENERATION) {
            return AlchemyCondition.HURT;
         } else if (effect == MobEffects.FIRE_RESISTANCE) {
            return AlchemyCondition.ON_FIRE;
         } else if (effect == MobEffects.MOVEMENT_SPEED) {
            return AlchemyCondition.SPRINTING;
         } else if (effect == MobEffects.DIG_SPEED) {
            return AlchemyCondition.MINING;
         } else {
            return effect == MobEffects.SLOW_FALLING ? AlchemyCondition.FALLING : AlchemyCondition.ALWAYS;
         }
      }
   }

   public static boolean shouldApplyPotionEffectsTo(LivingEntity le, ItemStack potionStack, boolean matchAllEffects, boolean matchEffectAmplifier) {
      PotionContents potioncontents = (PotionContents)potionStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
      if (!potioncontents.hasEffects()) {
         return false;
      }

      for (MobEffectInstance effectInstance : potioncontents.getAllEffects()) {
         if (matchAllEffects) {
            if (!effectPresent(le, effectInstance.getEffect(), matchEffectAmplifier, effectInstance.getAmplifier())) {
               return true;
            }
         } else if (effectPresent(le, effectInstance.getEffect(), matchEffectAmplifier, effectInstance.getAmplifier())) {
            return false;
         }
      }

      return !matchAllEffects;
   }

   private static boolean effectPresent(LivingEntity le, Holder<MobEffect> effect, boolean matchEffectAmplifier, int amplifier) {
      MobEffectInstance leEffectInstance = le.getEffect(effect);
      return leEffectInstance != null && (!matchEffectAmplifier || leEffectInstance.getAmplifier() >= amplifier);
   }

   private static boolean shouldApplyFoodEffectsTo(LivingEntity le, ItemStack potionStack, boolean matchAllEffects, boolean matchEffectAmplifier) {
      FoodProperties foodProperties = potionStack.getFoodProperties(le);
      if (foodProperties == null) {
         return false;
      }

      for (PossibleEffect possibleEffect : foodProperties.effects()) {
         MobEffectInstance effectInstance = possibleEffect.effect();
         if (matchAllEffects) {
            if (!effectPresent(le, effectInstance.getEffect(), matchEffectAmplifier, effectInstance.getAmplifier())) {
               return true;
            }
         } else if (effectPresent(le, effectInstance.getEffect(), matchEffectAmplifier, effectInstance.getAmplifier())) {
            return false;
         }
      }

      return !matchAllEffects;
   }

   public boolean isValidAlchemyItem(ItemStack stack) {
      return itemDefinitions.stream().anyMatch(def -> def.filter.test(stack))
         && !InventoryHelper.hasItem(this.getFilterHandler(), s -> ItemStack.isSameItemSameComponents(s, stack));
   }

   public AlchemyUpgradeWrapper.ObservableFilterItemStackHandler getFilterHandler() {
      List<AlchemyFilterAttribute> filterAttributes = this.getFilterAttributes();
      if (this.filterHandler == null) {
         if (filterAttributes.size() < this.upgradeItem.getFilterSlotCount()) {
            filterAttributes = new ArrayList<>(this.getFilterAttributes());

            for (int i = filterAttributes.size(); i < this.upgradeItem.getFilterSlotCount(); i++) {
               filterAttributes.add(new AlchemyFilterAttribute(ItemStack.EMPTY, AlchemyCondition.NEVER));
            }

            this.setUpgradeStackAlchemyAttributes(filterAttributes);
         }

         this.filterHandler = new AlchemyUpgradeWrapper.ObservableFilterItemStackHandler(filterAttributes.size());
         this.filterHandler.initFilters(filterAttributes);
      }

      return this.filterHandler;
   }

   public void setMatchAllEffects(boolean matchAllEffects) {
      this.upgrade.set(ModCoreDataComponents.MATCH_ALL_EFFECTS, matchAllEffects);
      this.save();
   }

   public boolean shouldMatchAllEffects() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.MATCH_ALL_EFFECTS, true);
   }

   public boolean shouldMatchEffectDuration() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.MATCH_EFFECT_DURATION, true);
   }

   public void setMatchEffectDuration(boolean matchEffectDuration) {
      this.upgrade.set(ModCoreDataComponents.MATCH_EFFECT_DURATION, matchEffectDuration);
      this.save();
   }

   public boolean shouldMatchEffectAmplifier() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.MATCH_EFFECT_AMPLIFIER, true);
   }

   public void setMatchEffectAmplifier(boolean matchEffectAmplifier) {
      this.upgrade.set(ModCoreDataComponents.MATCH_EFFECT_AMPLIFIER, matchEffectAmplifier);
      this.save();
   }

   public EntityMatch getEntityMatch() {
      return (EntityMatch)this.upgrade.getOrDefault(ModCoreDataComponents.ENTITY_MATCH, EntityMatch.PLAYERS_AND_ENTITIES);
   }

   public void setEntityMatch(EntityMatch entityMatch) {
      this.upgrade.set(ModCoreDataComponents.ENTITY_MATCH, entityMatch);
      this.save();
   }

   static {
      addItemDefinition(
         new AlchemyUpgradeWrapper.AlchemyItemDefinition(
            stack -> stack.getItem() == Items.OMINOUS_BOTTLE,
            stack -> AlchemyCondition.ALWAYS,
            (le, potionStack, matchAllEffects, matchEffectAmplifier) -> !le.hasEffect(MobEffects.BAD_OMEN),
            (stack, filter, matchAllEffects, matchEffectDuration, matchEffectAmplifier) -> stack.getItem() == Items.OMINOUS_BOTTLE,
            ItemStack::getUseDuration,
            (stack, livingEntity) -> stack.getItem().finishUsingItem(stack, livingEntity.level(), livingEntity)
         )
      );
      addItemDefinition(
         new AlchemyUpgradeWrapper.AlchemyItemDefinition(
            stack -> stack.getItem() == Items.SPLASH_POTION,
            AlchemyUpgradeWrapper::getDefaultConditionForPotion,
            AlchemyUpgradeWrapper::shouldApplyPotionEffectsTo,
            AlchemyUpgradeWrapper::stackPotionEffectsMatch,
            (stack, livingEntity) -> {
               Level level = livingEntity.level();
               level.playSound(
                  null,
                  livingEntity.getX() + livingEntity.getBbWidth() / 2.0F,
                  livingEntity.getY(),
                  livingEntity.getZ() + livingEntity.getBbWidth() / 2.0F,
                  SoundEvents.SPLASH_POTION_THROW,
                  SoundSource.PLAYERS,
                  0.5F,
                  0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
               );
               ThrownPotion thrownPotion = new ThrownPotion(
                  level,
                  livingEntity.getX() + livingEntity.getBbWidth() / 2.0F,
                  livingEntity.getY() + livingEntity.getEyeHeight(),
                  livingEntity.getZ() + livingEntity.getBbWidth() / 2.0F
               );
               thrownPotion.setItem(stack);
               onHit(
                  thrownPotion,
                  new EntityHitResult(livingEntity, new Vec3(livingEntity.getX(), livingEntity.getY() + livingEntity.getEyeHeight(), livingEntity.getZ()))
               );
               return 1;
            },
            (stack, livingEntity) -> ItemStack.EMPTY,
            false
         )
      );
      addItemDefinition(
         new AlchemyUpgradeWrapper.AlchemyItemDefinition(
            stack -> stack.getItem() == Items.POTION,
            AlchemyUpgradeWrapper::getDefaultConditionForPotion,
            AlchemyUpgradeWrapper::shouldApplyPotionEffectsTo,
            AlchemyUpgradeWrapper::stackPotionEffectsMatch,
            ItemStack::getUseDuration,
            (stack, livingEntity) -> {
               ItemStack remainingItem = stack.getItem().finishUsingItem(stack, livingEntity.level(), livingEntity);
               return livingEntity instanceof Player ? remainingItem : new ItemStack(Items.GLASS_BOTTLE);
            }
         )
      );
      addItemDefinition(
         new AlchemyUpgradeWrapper.AlchemyItemDefinition(
            stack -> stack.getItem() == Items.MILK_BUCKET,
            stack -> AlchemyCondition.NEGATIVE_EFFECT,
            (le, potionStack, matchAllEffects, matchEffectAmplifier) -> true,
            (stack, filter, matchAllEffects, matchEffectDuration, matchEffectAmplifier) -> stack.getItem() == Items.MILK_BUCKET,
            ItemStack::getUseDuration,
            (stack, livingEntity) -> stack.getItem().finishUsingItem(stack, livingEntity.level(), livingEntity)
         )
      );
      addItemDefinition(
         new AlchemyUpgradeWrapper.AlchemyItemDefinition(
            stack -> stack.getItem() == Items.GOLDEN_APPLE, stack -> AlchemyCondition.ALWAYS, (le, potionStack, matchAllEffects, matchEffectAmplifier) -> {
               if (!(le instanceof ZombieVillager zombieVillager)) {
                  return le instanceof Player ? shouldApplyFoodEffectsTo(le, potionStack, matchAllEffects, matchEffectAmplifier) : false;
               } else {
                  return !zombieVillager.isConverting() && zombieVillager.hasEffect(MobEffects.WEAKNESS);
               }
            }, AlchemyUpgradeWrapper::foodStackPotionEffectsMatch, ItemStack::getUseDuration, (stack, livingEntity) -> {
               if (livingEntity instanceof ZombieVillager zombieVillager && zombieVillager.hasEffect(MobEffects.WEAKNESS)) {
                  zombieVillager.startConverting(null, livingEntity.level().random.nextInt(2401) + 3600);
                  return ItemStack.EMPTY;
               } else {
                  return stack.getItem().finishUsingItem(stack, livingEntity.level(), livingEntity);
               }
            }
         )
      );
      addItemDefinition(
         new AlchemyUpgradeWrapper.AlchemyItemDefinition(
            stack -> {
               FoodProperties foodProperties = stack.getFoodProperties(null);
               return foodProperties != null && !foodProperties.effects().isEmpty();
            },
            stack -> AlchemyCondition.ALWAYS,
            (le, potionStack, matchAllEffects, matchEffectAmplifier) -> le instanceof Player
               ? shouldApplyFoodEffectsTo(le, potionStack, matchAllEffects, matchEffectAmplifier)
               : false,
            AlchemyUpgradeWrapper::foodStackPotionEffectsMatch,
            ItemStack::getUseDuration,
            (stack, livingEntity) -> stack.getItem().finishUsingItem(stack, livingEntity.level(), livingEntity)
         )
      );
   }

   public record AlchemyItemDefinition(
      Predicate<ItemStack> filter,
      Function<ItemStack, AlchemyCondition> getDefaultCondition,
      AlchemyUpgradeWrapper.AlchemyItemEntityMatcher canApply,
      AlchemyUpgradeWrapper.AlchemyItemStackMatcher stackMatches,
      AlchemyUpgradeWrapper.StartUsing startUsing,
      AlchemyUpgradeWrapper.FinishUsing finishUsing,
      boolean hasItemUseEffects
   ) {
      public AlchemyItemDefinition(
         Predicate<ItemStack> filter,
         Function<ItemStack, AlchemyCondition> getDefaultCondition,
         AlchemyUpgradeWrapper.AlchemyItemEntityMatcher canApply,
         AlchemyUpgradeWrapper.AlchemyItemStackMatcher stackMatches,
         AlchemyUpgradeWrapper.StartUsing startUsing,
         AlchemyUpgradeWrapper.FinishUsing finishUsing
      ) {
         this(filter, getDefaultCondition, canApply, stackMatches, startUsing, finishUsing, true);
      }
   }

   public interface AlchemyItemEntityMatcher {
      boolean test(LivingEntity var1, ItemStack var2, boolean var3, boolean var4);
   }

   public interface AlchemyItemStackMatcher {
      boolean test(ItemStack var1, ItemStack var2, boolean var3, boolean var4, boolean var5);
   }

   public interface FinishUsing {
      ItemStack apply(ItemStack var1, LivingEntity var2);
   }

   public class ObservableFilterItemStackHandler extends FilterItemStackHandler {
      public ObservableFilterItemStackHandler(int filterSlotCount) {
         super(filterSlotCount);
      }

      @Override
      protected void onContentsChanged(int slot) {
         super.onContentsChanged(slot);
         AlchemyUpgradeWrapper.this.setFilter(slot, (ItemStack)this.stacks.get(slot));
         AlchemyUpgradeWrapper.this.save();
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         return stack.isEmpty() || this.doesNotContain(stack) && AlchemyUpgradeWrapper.this.isValidAlchemyItem(stack);
      }

      private boolean doesNotContain(ItemStack stack) {
         return !InventoryHelper.hasItem(this, s -> ItemStack.isSameItemSameComponents(s, stack));
      }

      public void initFilters(List<AlchemyFilterAttribute> filterAttributes) {
         for (int slot = 0; slot < filterAttributes.size(); slot++) {
            this.setStackInSlot(slot, filterAttributes.get(slot).filter().copy());
         }

         this.onLoad();
      }
   }

   public interface StartUsing {
      int applyAsInt(ItemStack var1, LivingEntity var2);
   }
}
