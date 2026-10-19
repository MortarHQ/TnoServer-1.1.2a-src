package de.ellpeck.naturesaura.items;

import com.google.common.base.Strings;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.blocks.BlockEnderCrate;
import de.ellpeck.naturesaura.gui.ContainerEnderCrate;
import de.ellpeck.naturesaura.gui.ModContainers;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

public class ItemEnderAccess extends ItemImpl {
   public ItemEnderAccess() {
      super("ender_access");
   }

   public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
      if (handIn != InteractionHand.MAIN_HAND) {
         return new InteractionResultHolder(InteractionResult.PASS, playerIn.getItemInHand(handIn));
      }

      ItemStack stack = playerIn.getMainHandItem();
      final String name = BlockEnderCrate.getEnderName(stack);
      if (!Strings.isNullOrEmpty(name)) {
         if (!levelIn.isClientSide && NaturesAuraAPI.instance().extractAuraFromPlayer(playerIn, 10000, false)) {
            playerIn.openMenu(new MenuProvider() {
               public Component getDisplayName() {
                  return Component.translatable("info.naturesaura.ender_access", new Object[]{ChatFormatting.ITALIC + name + ChatFormatting.RESET});
               }

               @Nullable
               public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
                  IItemHandler handler = ILevelData.getOverworldData(inv.player.level()).getEnderStorage(name);
                  return new ContainerEnderCrate(ModContainers.ENDER_ACCESS, windowId, player, handler);
               }
            }, buffer -> buffer.writeUtf(name));
         }

         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         return new InteractionResultHolder(InteractionResult.FAIL, stack);
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      BlockEnderCrate.addEnderNameInfo(stack, tooltipComponents);
   }
}
