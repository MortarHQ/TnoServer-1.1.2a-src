package artifacts.mixin.item;

import artifacts.Artifacts;
import artifacts.client.ToggleKeyHandlers;
import artifacts.component.ToggleIdentifier;
import artifacts.component.ability.EquipmentAbility;
import artifacts.item.WearableArtifactItem;
import artifacts.registry.ModDataComponents;
import artifacts.util.TooltipHelper;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemLore;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
   @Inject(
      method = "getTooltipLines(Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;",
      locals = LocalCapture.CAPTURE_FAILHARD,
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V"
      )
   )
   private void getTooltipLines(
      TooltipContext context, @Nullable Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, List<Component> tooltip
   ) {
      if (Artifacts.CONFIG.client.showTooltips.get()) {
         ItemStack stack = (ItemStack)this;
         if (stack.getItem() instanceof WearableArtifactItem && TooltipHelper.isCosmetic(stack)) {
            tooltip.add(Component.translatable("%s.tooltip.cosmetic".formatted("artifacts")).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
         }

         ItemLore lore = (ItemLore)stack.get(ModDataComponents.ABILITY_LORE.get());
         if (lore != null) {
            lore.addToTooltip(context, tooltip::add, tooltipFlag);
         }

         for (Supplier<? extends DataComponentType<? extends EquipmentAbility>> type : ModDataComponents.TOOLTIP_ORDER) {
            EquipmentAbility provider = (EquipmentAbility)stack.get(type.get());
            if (provider != null && provider.isNonCosmetic()) {
               provider.addToTooltip(
                  new EquipmentAbility.TooltipWriter((DataComponentType<? extends EquipmentAbility>)type.get(), tooltip::add, context, stack)
               );
            }
         }

         ToggleIdentifier toggleKey = (ToggleIdentifier)stack.get(ModDataComponents.TOGGLE_KEY.get());
         if (toggleKey != null && !TooltipHelper.isCosmetic(stack) && player != null && !player.level().isClientSide()) {
            ToggleKeyHandlers.addTooltip(toggleKey, stack.has(ModDataComponents.DISABLED_BY_TOGGLE.get()), tooltip::add);
         }
      }
   }

   @Inject(
      method = "getTooltipLines(Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;",
      require = 0,
      locals = LocalCapture.CAPTURE_FAILHARD,
      at = @At(
         value = "INVOKE",
         shift = Shift.AFTER,
         target = "Lnet/minecraft/world/item/ItemStack;addAttributeTooltips(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;)V"
      )
   )
   private void addAttributeTooltips(
      TooltipContext context, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, List<Component> tooltip
   ) {
      TooltipHelper.addAttributeTooltips(tooltip::add, (ItemStack)this, context);
   }
}
