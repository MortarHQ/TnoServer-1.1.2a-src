package com.mortal_cultivation.event;

import com.mortal_cultivation.race.special.WastelanderRace;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.RaceEvents;
import io.github.manasmods.manascore.race.api.RaceEvents.SetRaceEvent;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class WastelanderBookHandler {
   private static final String BOOK_GIVEN_TAG = "mortal_cultivation:wastelander_book_given";
   private static final ResourceLocation BOOK_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "cultivation_guide");
   private static final ResourceLocation PATCHOULI_BOOK_ITEM_ID = ResourceLocation.fromNamespaceAndPath("patchouli", "guide_book");
   private static final ResourceLocation PATCHOULI_BOOK_COMPONENT_ID = ResourceLocation.fromNamespaceAndPath("patchouli", "book");
   private static boolean initialized;

   public static void init() {
      if (!initialized) {
         RaceEvents.SET_RACE.register((SetRaceEvent)(oldRace, entity, newRace, evolution, teleport, message) -> {
            if (entity instanceof ServerPlayer serverPlayer) {
               if (serverPlayer.containerMenu instanceof ReincarnationMenu) {
                  return EventResult.pass();
               }

               if (newRace.getRace() instanceof WastelanderRace) {
                  giveBookIfNeeded(serverPlayer);
               }
            }

            return EventResult.pass();
         });
         initialized = true;
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         checkAndGiveBook(serverPlayer);
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide && player.tickCount % 100 == 50 && player instanceof ServerPlayer serverPlayer) {
         if (serverPlayer.containerMenu instanceof ReincarnationMenu) {
            return;
         }

         checkAndGiveBook(serverPlayer);
      }
   }

   private static void checkAndGiveBook(ServerPlayer player) {
      if (isWastelanderRace(player)) {
         giveBookIfNeeded(player);
      }
   }

   private static ItemStack createBookStack() {
      if (!BuiltInRegistries.ITEM.containsKey(PATCHOULI_BOOK_ITEM_ID)) {
         return ItemStack.EMPTY;
      }

      Item bookItem = (Item)BuiltInRegistries.ITEM.get(PATCHOULI_BOOK_ITEM_ID);
      ItemStack stack = new ItemStack(bookItem);
      DataComponentType<ResourceLocation> bookComponent = (DataComponentType<ResourceLocation>)BuiltInRegistries.DATA_COMPONENT_TYPE
         .get(PATCHOULI_BOOK_COMPONENT_ID);
      if (bookComponent != null) {
         stack.set(bookComponent, BOOK_ID);
      }

      return stack;
   }

   private static void giveBookIfNeeded(ServerPlayer player) {
      CompoundTag persistentData = player.getPersistentData();
      if (!persistentData.getBoolean("mortal_cultivation:wastelander_book_given")) {
         if (hasBook(player)) {
            persistentData.putBoolean("mortal_cultivation:wastelander_book_given", true);
         } else {
            ItemStack book = createBookStack();
            if (!book.isEmpty()) {
               boolean added = player.getInventory().add(book);
               if (!added) {
                  player.drop(book, false);
               }

               persistentData.putBoolean("mortal_cultivation:wastelander_book_given", true);
            }
         }
      }
   }

   private static boolean isWastelanderRace(ServerPlayer player) {
      return RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace() instanceof WastelanderRace).orElse(false);
   }

   private static boolean hasBook(Player player) {
      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack stack = player.getInventory().getItem(i);
         if (isOurBook(stack)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isOurBook(ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      }

      Item bookItem = (Item)BuiltInRegistries.ITEM.get(PATCHOULI_BOOK_ITEM_ID);
      if (!stack.is(bookItem)) {
         return false;
      }

      DataComponentType<ResourceLocation> bookComponent = (DataComponentType<ResourceLocation>)BuiltInRegistries.DATA_COMPONENT_TYPE
         .get(PATCHOULI_BOOK_COMPONENT_ID);
      return bookComponent == null ? false : BOOK_ID.equals(stack.get(bookComponent));
   }
}
