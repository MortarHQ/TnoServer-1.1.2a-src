package team.creative.creativecore.common.gui.control.collection;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.extension.GuiExtensionCreator;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.mc.StackUtils;
import team.creative.creativecore.common.util.text.TextBuilder;
import team.creative.creativecore.common.util.type.map.HashMapList;

public class GuiStackSelector extends GuiLabel {
   public final Player player;
   protected GuiExtensionCreator<GuiStackSelector, GuiStackSelectorExtension> ex = new GuiExtensionCreator<>(this);
   protected GuiStackSelector.StackCollector collector;
   protected HashMapList<String, ItemStack> stacks;
   protected ItemStack selected = ItemStack.EMPTY;
   private boolean searchbar;

   public GuiStackSelector(String name, Player player, GuiStackSelector.StackCollector collector, boolean searchbar) {
      super(name);
      this.player = player;
      this.searchbar = searchbar;
      this.collector = collector;
      this.updateCollectedStacks();
      this.selectFirst();
      this.setAlign(Align.CENTER);
   }

   public GuiStackSelector(String name, Player player, GuiStackSelector.StackCollector collector) {
      this(name, player, collector, true);
   }

   public GuiStackSelector setWidth(int width) {
      this.setDim(width, 14);
      return this;
   }

   public boolean hasSearchbar() {
      return this.searchbar;
   }

   public GuiStackSelector setSearchbar(boolean searchbar) {
      this.searchbar = searchbar;
      return this;
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      this.ex.toggle(this::createBox);
      playSound(SoundEvents.UI_BUTTON_CLICK);
      return true;
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.CLICKABLE;
   }

   public boolean selectFirst() {
      if (this.stacks != null) {
         ItemStack first = this.stacks.getFirst();
         if (first != null) {
            this.setSelected(first);
            return true;
         }
      }

      return false;
   }

   @Override
   public Player getPlayer() {
      return this.player;
   }

   public void updateCollectedStacks() {
      this.stacks = this.collector.collect(this.player);
   }

   public boolean setSelectedForce(ItemStack stack) {
      this.setTitle(new TextBuilder().stack(stack).add(stack.getHoverName()).build());
      this.selected = stack;
      this.raiseEvent(new GuiControlChangedEvent<>(this));
      return true;
   }

   public boolean setSelected(ItemStack stack) {
      if (this.stacks.contains(stack)) {
         this.setTitle(new TextBuilder().stack(stack).add(stack.getHoverName()).build());
         this.selected = stack;
         this.raiseEvent(new GuiControlChangedEvent<>(this));
         return true;
      } else {
         return false;
      }
   }

   public HashMapList<String, ItemStack> getStacks() {
      return this.stacks;
   }

   public ItemStack getSelected() {
      return this.selected;
   }

   protected GuiStackSelectorExtension createBox(GuiExtensionCreator<GuiStackSelector, GuiStackSelectorExtension> creator) {
      return new GuiStackSelectorExtension(this.name + "extension", this.getPlayer(), creator);
   }

   @Override
   public void looseFocus() {
      if (this.ex.checkShouldClose()) {
         this.ex.close();
      }
   }

   public static boolean contains(String search, ItemStack stack) {
      if (search.isEmpty()) {
         return true;
      }

      if (getItemName(stack).toLowerCase().contains(search)) {
         return true;
      }

      for (Component line : stack.getTooltipLines(TooltipContext.EMPTY, null, Default.NORMAL)) {
         if (line.getString().toLowerCase().contains(search)) {
            return true;
         }
      }

      return false;
   }

   public static String getItemName(ItemStack stack) {
      String itemName = "";

      try {
         itemName = stack.getDisplayName().getString();
      } catch (Exception e) {
         itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
      }

      return itemName;
   }

   public static class CreativeCollector extends GuiStackSelector.InventoryCollector {
      public CreativeCollector(GuiStackSelector.StackSelector selector) {
         super(selector);
      }

      @Override
      public HashMapList<String, ItemStack> collect(Player player) {
         HashMapList<String, ItemStack> stacks = super.collect(player);
         List<ItemStack> newStacks = new ArrayList<>();

         for (Item item : BuiltInRegistries.ITEM) {
            ItemStack stack = new ItemStack(item);
            if (this.selector.allow(stack)) {
               newStacks.add(stack);
            }
         }

         stacks.add("collector.all", newStacks);
         return stacks;
      }
   }

   public static class GuiBlockSelector extends GuiStackSelector.SearchSelector {
      @Override
      public boolean allow(ItemStack stack) {
         return super.allow(stack) ? !(Block.byItem(stack.getItem()) instanceof AirBlock) : false;
      }
   }

   public static class InventoryCollector extends GuiStackSelector.StackCollector {
      public InventoryCollector(GuiStackSelector.StackSelector selector) {
         super(selector);
      }

      @Override
      public HashMapList<String, ItemStack> collect(Player player) {
         HashMapList<String, ItemStack> stacks = new HashMapList<>();
         if (player != null) {
            List<ItemStack> tempStacks = new ArrayList<>();

            for (ItemStack stack : player.inventoryMenu.getItems()) {
               if (!stack.isEmpty() && this.selector.allow(stack)) {
                  tempStacks.add(stack.copy());
               } else {
                  StackUtils.collect(stack, this.selector::allow, tempStacks);
               }
            }

            stacks.add("collector.inventory", tempStacks);
         }

         return stacks;
      }
   }

   public static class SearchSelector extends GuiStackSelector.StackSelector {
      public String search = "";

      @Override
      public boolean allow(ItemStack stack) {
         return GuiStackSelector.contains(this.search, stack);
      }
   }

   public abstract static class StackCollector {
      public GuiStackSelector.StackSelector selector;

      public StackCollector(GuiStackSelector.StackSelector selector) {
         this.selector = selector;
      }

      public abstract HashMapList<String, ItemStack> collect(Player var1);
   }

   public abstract static class StackSelector {
      public abstract boolean allow(ItemStack var1);
   }
}
