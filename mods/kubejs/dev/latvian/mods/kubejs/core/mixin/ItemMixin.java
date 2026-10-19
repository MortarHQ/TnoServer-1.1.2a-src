package dev.latvian.mods.kubejs.core.mixin;

import dev.latvian.mods.kubejs.core.ItemKJS;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.ItemStackKey;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@RemapPrefixForJS("kjs$")
@Mixin(value = Item.class, priority = 1001)
public abstract class ItemMixin implements ItemKJS {
   @Shadow
   private DataComponentMap components;
   @Shadow
   @Final
   private Reference<Item> builtInRegistryHolder;
   @Unique
   private ItemBuilder kjs$itemBuilder;
   @Unique
   private Map<String, Object> kjs$typeData;
   @Unique
   private Ingredient kjs$asIngredient;
   @Unique
   private ItemStackKey kjs$typeItemStackKey;
   @Unique
   private ResourceKey<Item> kjs$registryKey;
   @Unique
   private String kjs$id;

   @Nullable
   @Override
   public ItemBuilder kjs$getItemBuilder() {
      return this.kjs$itemBuilder;
   }

   public Reference<Item> kjs$asHolder() {
      return this.builtInRegistryHolder;
   }

   @Override
   public ResourceKey<Item> kjs$getKey() {
      if (this.kjs$registryKey == null) {
         this.kjs$registryKey = ItemKJS.super.kjs$getKey();
      }

      return this.kjs$registryKey;
   }

   @Override
   public String kjs$getId() {
      if (this.kjs$id == null) {
         this.kjs$id = ItemKJS.super.kjs$getId();
      }

      return this.kjs$id;
   }

   @Override
   public void kjs$setItemBuilder(ItemBuilder b) {
      this.kjs$itemBuilder = b;
   }

   @Override
   public Map<String, Object> kjs$getTypeData() {
      if (this.kjs$typeData == null) {
         this.kjs$typeData = new HashMap<>();
      }

      return this.kjs$typeData;
   }

   @HideFromJS
   @Override
   public <T> void kjs$overrideComponent(DataComponentType<T> type, @Nullable T value) {
      Builder builder = DataComponentMap.builder().addAll(this.components);
      builder.set(type, value);
      this.components = (DataComponentMap)Properties.COMPONENT_INTERNER.intern(Properties.validateComponents(builder.build()));
   }

   @Accessor("craftingRemainingItem")
   @Mutable
   @Override
   public abstract void kjs$setCraftingRemainder(Item i);

   @Inject(method = "isFoil", at = @At("HEAD"), cancellable = true)
   private void isFoil(ItemStack itemStack, CallbackInfoReturnable<Boolean> ci) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.glow) {
         ci.setReturnValue(true);
      }
   }

   @Inject(method = "appendHoverText", at = @At("RETURN"))
   private void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn, CallbackInfo ci) {
      if (this.kjs$itemBuilder != null && !this.kjs$itemBuilder.tooltip.isEmpty()) {
         tooltip.addAll(this.kjs$itemBuilder.tooltip);
      }
   }

   @Inject(method = "isBarVisible", at = @At("HEAD"), cancellable = true)
   private void isBarVisible(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.barWidth != null && this.kjs$itemBuilder.barWidth.applyAsInt(stack) <= 13) {
         ci.setReturnValue(true);
      }
   }

   @Inject(method = "getBarWidth", at = @At("HEAD"), cancellable = true)
   private void getBarWidth(ItemStack stack, CallbackInfoReturnable<Integer> ci) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.barWidth != null) {
         ci.setReturnValue(this.kjs$itemBuilder.barWidth.applyAsInt(stack));
      }
   }

   @Inject(method = "getBarColor", at = @At("HEAD"), cancellable = true)
   private void getBarColor(ItemStack stack, CallbackInfoReturnable<Integer> ci) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.barColor != null) {
         ci.setReturnValue(this.kjs$itemBuilder.barColor.apply(stack).kjs$getRGB());
      }
   }

   @Inject(method = "getUseDuration", at = @At("HEAD"), cancellable = true)
   private void getUseDuration(ItemStack itemStack, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.useDuration != null) {
         cir.setReturnValue(this.kjs$itemBuilder.useDuration.applyAsInt(itemStack, entity));
      }
   }

   @Inject(method = "getUseAnimation", at = @At("HEAD"), cancellable = true)
   private void getUseAnimation(ItemStack itemStack, CallbackInfoReturnable<UseAnim> ci) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.anim != null) {
         ci.setReturnValue(this.kjs$itemBuilder.anim);
      }
   }

   @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
   private void getName(ItemStack itemStack, CallbackInfoReturnable<Component> ci) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.nameGetter != null) {
         ci.setReturnValue(this.kjs$itemBuilder.nameGetter.apply(itemStack));
      }

      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.displayName != null && this.kjs$itemBuilder.formattedDisplayName) {
         ci.setReturnValue(this.kjs$itemBuilder.displayName);
      }
   }

   @Inject(method = "use", at = @At("HEAD"), cancellable = true)
   private void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> ci) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.use != null) {
         ItemStack itemStack = player.getItemInHand(interactionHand);
         if (this.kjs$itemBuilder.use.use(level, player, interactionHand)) {
            ci.setReturnValue(ItemUtils.startUsingInstantly(level, player, interactionHand));
         } else {
            ci.setReturnValue(InteractionResultHolder.fail(itemStack));
         }
      }
   }

   @Inject(method = "finishUsingItem", at = @At("HEAD"), cancellable = true)
   private void finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> ci) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.finishUsing != null) {
         ci.setReturnValue(this.kjs$itemBuilder.finishUsing.finishUsingItem(itemStack, level, livingEntity));
      }
   }

   @Inject(method = "releaseUsing", at = @At("HEAD"))
   private void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i, CallbackInfo ci) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.releaseUsing != null) {
         this.kjs$itemBuilder.releaseUsing.releaseUsing(itemStack, level, livingEntity, i);
      }
   }

   @Inject(method = "hurtEnemy", at = @At("HEAD"), cancellable = true)
   private void hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2, CallbackInfoReturnable<Boolean> cir) {
      if (this.kjs$itemBuilder != null && this.kjs$itemBuilder.hurtEnemy != null) {
         cir.setReturnValue(this.kjs$itemBuilder.hurtEnemy.test(new ItemBuilder.HurtEnemyContext(itemStack, livingEntity, livingEntity2)));
      }
   }

   @Override
   public Ingredient kjs$asIngredient() {
      if (this.kjs$asIngredient == null) {
         ItemStack is = new ItemStack(this.kjs$self());
         this.kjs$asIngredient = is.isEmpty() ? Ingredient.EMPTY : Ingredient.of(Stream.of(is));
      }

      return this.kjs$asIngredient;
   }

   @Accessor("descriptionId")
   @Mutable
   @Override
   public abstract void kjs$setNameKey(String key);

   @Override
   public ItemStackKey kjs$getTypeItemStackKey() {
      if (this.kjs$typeItemStackKey == null) {
         this.kjs$typeItemStackKey = new ItemStackKey(this.kjs$self(), null);
      }

      return this.kjs$typeItemStackKey;
   }

   @Accessor("canRepair")
   @Mutable
   @Override
   public abstract void kjs$setCanRepair(boolean repairable);
}
