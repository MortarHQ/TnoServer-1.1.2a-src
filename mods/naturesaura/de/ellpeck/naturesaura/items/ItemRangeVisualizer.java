package de.ellpeck.naturesaura.items;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import de.ellpeck.naturesaura.api.render.IVisualizable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;

public class ItemRangeVisualizer extends ItemImpl {
   public static final ListMultimap<ResourceLocation, BlockPos> VISUALIZED_BLOCKS = ArrayListMultimap.create();
   public static final ListMultimap<ResourceLocation, Entity> VISUALIZED_ENTITIES = ArrayListMultimap.create();
   public static final ListMultimap<ResourceLocation, BlockPos> VISUALIZED_RAILS = ArrayListMultimap.create();

   public ItemRangeVisualizer() {
      super("range_visualizer", new Properties().stacksTo(1));
      NeoForge.EVENT_BUS.register(new ItemRangeVisualizer.EventHandler());
   }

   public static void clear() {
      if (!VISUALIZED_BLOCKS.isEmpty()) {
         VISUALIZED_BLOCKS.clear();
      }

      if (!VISUALIZED_ENTITIES.isEmpty()) {
         VISUALIZED_ENTITIES.clear();
      }

      if (!VISUALIZED_RAILS.isEmpty()) {
         VISUALIZED_RAILS.clear();
      }
   }

   public static <T> void visualize(Player player, ListMultimap<ResourceLocation, T> map, ResourceLocation dim, T value) {
      if (map.containsEntry(dim, value)) {
         map.remove(dim, value);
         player.displayClientMessage(Component.translatable("info.naturesaura.range_visualizer.end"), true);
      } else {
         map.put(dim, value);
         player.displayClientMessage(Component.translatable("info.naturesaura.range_visualizer.start"), true);
      }
   }

   public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.getItemInHand(handIn);
      if (playerIn.isShiftKeyDown()) {
         clear();
         playerIn.displayClientMessage(Component.translatable("info.naturesaura.range_visualizer.end_all"), true);
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         return new InteractionResultHolder(InteractionResult.PASS, stack);
      }
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      BlockState state = level.getBlockState(pos);
      Block block = state.getBlock();
      if (block instanceof IVisualizable) {
         if (level.isClientSide) {
            visualize(context.getPlayer(), VISUALIZED_BLOCKS, level.dimension().location(), pos);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public class EventHandler {
      @SubscribeEvent
      public void onInteract(EntityInteractSpecific event) {
         ItemStack stack = event.getItemStack();
         if (!stack.isEmpty() && stack.getItem() == ItemRangeVisualizer.this) {
            Entity entity = event.getTarget();
            if (entity instanceof IVisualizable) {
               if (entity.level().isClientSide) {
                  ResourceLocation dim = entity.level().dimension().location();
                  ItemRangeVisualizer.visualize(event.getEntity(), ItemRangeVisualizer.VISUALIZED_ENTITIES, dim, entity);
               }

               event.getEntity().swing(event.getHand());
               event.setCancellationResult(InteractionResult.SUCCESS);
               event.setCanceled(true);
            }
         }
      }
   }
}
