package dev.ftb.mods.ftbfiltersystem.registry.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftbfiltersystem.FilterSystemCommands;
import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import dev.ftb.mods.ftbfiltersystem.client.FTBFilterSystemClient;
import dev.ftb.mods.ftbfiltersystem.registry.ModDataComponents;
import dev.ftb.mods.ftbfiltersystem.registry.ModItems;
import dev.ftb.mods.ftbfiltersystem.util.FilterParser;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SmartFilterItem extends Item {
   public SmartFilterItem() {
      super(ModItems.defaultProps().component((DataComponentType)ModDataComponents.FILTER_STRING.get(), ""));
   }

   public static String getFilterString(ItemStack filterStack) {
      return (String)filterStack.getOrDefault((DataComponentType)ModDataComponents.FILTER_STRING.get(), "");
   }

   @NotNull
   public static SmartFilter getFilter(ItemStack filterStack, Provider registryAccess) throws FilterException {
      return FilterParser.parseRaw(getFilterString(filterStack), registryAccess);
   }

   public static void setFilter(ItemStack filterStack, String string) {
      filterStack.set((DataComponentType)ModDataComponents.FILTER_STRING.get(), string);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
      ItemStack stack = player.getItemInHand(interactionHand);
      if (level.isClientSide && !player.isCrouching()) {
         FTBFilterSystemClient.INSTANCE.openFilterScreen(interactionHand);
      } else if (!level.isClientSide && player.isCrouching()) {
         try {
            FilterSystemCommands.tryMatch(player.createCommandSourceStack());
         } catch (CommandSyntaxException | FilterException e) {
            player.displayClientMessage(Component.literal(e.getMessage()).withStyle(ChatFormatting.RED), false);
         }
      }

      return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
      if (context.registries() != null && FTBFilterSystemClient.shouldShowItemTooltip()) {
         list.add(Component.translatable("item.ftbfiltersystem.smart_filter.tooltip.1").withStyle(ChatFormatting.GRAY));
         list.add(Component.translatable("item.ftbfiltersystem.smart_filter.tooltip.2").withStyle(ChatFormatting.GRAY));
      }
   }
}
