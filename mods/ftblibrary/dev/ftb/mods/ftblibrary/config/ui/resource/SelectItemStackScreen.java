package dev.ftb.mods.ftblibrary.config.ui.resource;

import dev.architectury.registry.registries.RegistrarManager;
import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.FTBLibraryClientConfig;
import dev.ftb.mods.ftblibrary.config.ItemStackConfig;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.util.ModUtils;
import dev.ftb.mods.ftblibrary.util.SearchTerms;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;

public class SelectItemStackScreen extends ResourceSelectorScreen<ItemStack> {
   public static final SearchModeIndex<ResourceSearchMode<ItemStack>> KNOWN_MODES = (SearchModeIndex<ResourceSearchMode<ItemStack>>)Util.make(
      new SearchModeIndex(), index -> {
         index.appendMode(ResourceSearchMode.ALL_ITEMS);
         index.appendMode(ResourceSearchMode.INVENTORY);
      }
   );

   public SelectItemStackScreen(ItemStackConfig config, ConfigCallback callback) {
      super(config, callback);
   }

   @Override
   protected SearchModeIndex<ResourceSearchMode<ItemStack>> getSearchModeIndex() {
      return KNOWN_MODES;
   }

   @Override
   protected ResourceSelectorScreen<ItemStack>.ResourceButton makeResourceButton(Panel panel, SelectableResource<ItemStack> resource) {
      return new SelectItemStackScreen.ItemStackButton(panel, Objects.requireNonNullElse(resource, SelectableResource.item(ItemStack.EMPTY)));
   }

   private class ItemStackButton extends ResourceSelectorScreen<ItemStack>.ResourceButton {
      private ItemStackButton(Panel panel, SelectableResource<ItemStack> resource) {
         super(panel, resource);
      }

      @Override
      public boolean shouldAdd(SearchTerms searchTerms) {
         return searchTerms.match(
            RegistrarManager.getId(((ItemStack)this.getResource()).getItem(), Registries.ITEM),
            ((ItemStack)this.getResource()).getHoverName().getString(),
            id -> ((ItemStack)this.getResource()).is(TagKey.create(Registries.ITEM, id))
         );
      }

      @Override
      public void addMouseOverText(TooltipList list) {
         if (!((ItemStack)this.getResource()).isEmpty()) {
            TooltipFlag flag = Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL;
            ((ItemStack)this.getResource())
               .getTooltipLines(TooltipContext.of(Minecraft.getInstance().level), Minecraft.getInstance().player, flag)
               .forEach(list::add);
            if (FTBLibraryClientConfig.ITEM_MODNAME.get()) {
               ModUtils.getModName(((ItemStack)this.getResource()).getItem())
                  .ifPresent(name -> list.add(Component.literal(name).withStyle(new ChatFormatting[]{ChatFormatting.BLUE, ChatFormatting.ITALIC})));
            }
         }
      }
   }
}
