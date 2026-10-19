package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.villager.ActionTradeBase;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.sequence.SequenceManager;
import com.blamejared.crafttweaker.api.util.sequence.SequenceType;
import com.blamejared.crafttweaker.api.villager.CTVillagerTrades;
import com.blamejared.crafttweaker.impl.logging.CraftTweakerLog4jEditor;
import com.blamejared.crafttweaker.impl.script.ScriptReloadListener;
import com.blamejared.crafttweaker.platform.Services;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Pre;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = "crafttweaker")
public class CTCommonEventHandler {
   @SubscribeEvent
   public static void worldTick(Pre e) {
      if (e.getLevel().dimension() == Level.OVERWORLD) {
         SequenceManager.tick(e.getLevel().isClientSide ? SequenceType.CLIENT_THREAD_LEVEL : SequenceType.SERVER_THREAD_LEVEL);
      }
   }

   @SubscribeEvent
   public static void blockInteract(RightClickBlock e) {
      if (Services.EVENT.onBlockInteract(e.getEntity(), e.getHand(), e.getHitVec())) {
         e.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void entityInteract(EntityInteract e) {
      if (Services.EVENT.onEntityInteract(e.getEntity(), e.getHand(), e.getTarget())) {
         e.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void wanderingTradesTweaker(WandererTradesEvent e) {
      CTVillagerTrades.ACTION_WANDERING_TRADES.forEach(IUndoableAction::undo);
      CTVillagerTrades.ACTION_WANDERING_TRADES.forEach(actionTradeBase -> {
         List<ItemListing> trades = switch (actionTradeBase.getLevel()) {
            case 1 -> e.getGenericTrades();
            case 2 -> e.getRareTrades();
            default -> null;
         };
         if (trades != null) {
            actionTradeBase.apply(trades);
         }
      });
      CTVillagerTrades.ACTION_WANDERING_TRADES.clear();
   }

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void villagerTradesTweaker(VillagerTradesEvent e) {
      List<ActionTradeBase> collect = CTVillagerTrades.ACTIONS_VILLAGER_TRADES
         .stream()
         .filter(actionTradeBase -> actionTradeBase.getProfession() == e.getType())
         .toList();
      collect.forEach(IUndoableAction::undo);
      collect.forEach(
         actionTradeBase -> actionTradeBase.apply((List<ItemListing>)e.getTrades().computeIfAbsent(actionTradeBase.getLevel(), value -> new ArrayList()))
      );
      CTVillagerTrades.ACTIONS_VILLAGER_TRADES.removeAll(collect);
   }

   @SubscribeEvent
   public static void burnTimeTweaker(FurnaceFuelBurnTimeEvent e) {
      RecipeType<?> recipeType = e.getRecipeType() != null ? e.getRecipeType() : RecipeType.SMELTING;
      Services.EVENT
         .getBurnTimes()
         .getOrDefault(recipeType, List.of())
         .stream()
         .filter(pair -> ((IIngredient)pair.getFirst()).matches(IItemStack.of(e.getItemStack())))
         .forEach(pair -> e.setBurnTime((Integer)pair.getSecond()));
   }

   @SubscribeEvent
   public static void playerLogin(PlayerLoggedInEvent event) {
      CraftTweakerLog4jEditor.addPlayer(event.getEntity());
   }

   @SubscribeEvent
   public static void playerLogout(PlayerLoggedOutEvent event) {
      CraftTweakerLog4jEditor.removePlayer(event.getEntity());
   }

   @SubscribeEvent
   public static void registerCommands(RegisterCommandsEvent event) {
      CraftTweakerCommon.registerCommands(event.getDispatcher(), event.getCommandSelection());
   }

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void resourceReload(AddReloadListenerEvent event) {
      event.addListener(new ScriptReloadListener(event.getServerResources(), CTCommonEventHandler::giveFeedback));
   }

   private static void giveFeedback(MutableComponent msg) {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server != null) {
         server.getPlayerList().broadcastSystemMessage(msg, false);
      } else {
         CraftTweakerCommon.logger().info(msg.getString());
      }
   }
}
