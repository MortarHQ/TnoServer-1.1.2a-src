package net.p3pp3rf1y.sophisticatedbackpacks.compat.curios;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedbackpacks.util.PlayerInventoryProvider;
import net.p3pp3rf1y.sophisticatedcore.compat.ICompat;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class CuriosCompat implements ICompat {
   private final Set<String> backpackCurioIdentifiers = new CopyOnWriteArraySet<>();
   private long lastTagsRefresh = -1L;
   private static final int TAGS_REFRESH_COOLDOWN = 100;

   private void addPlayerInventoryHandlers() {
      PlayerInventoryProvider.get()
         .addPlayerInventoryHandler(
            "curios",
            this::getCurioTags,
            (player, identifier) -> getFromCuriosSlotStackHandler(player, identifier, ICurioStacksHandler::getSlots, 0),
            (player, identifier, slot) -> getFromCuriosSlotStackHandler(player, identifier, sh -> sh.getStacks().getStackInSlot(slot), ItemStack.EMPTY),
            false,
            true,
            true,
            true
         );
   }

   private Set<String> getCurioTags(Player player) {
      long gameTime = player.level().getGameTime();
      if (this.lastTagsRefresh + 100L < gameTime) {
         this.lastTagsRefresh = gameTime;
         this.backpackCurioIdentifiers.clear();
         this.backpackCurioIdentifiers
            .addAll(CuriosApi.getItemStackSlots(((BackpackItem)ModItems.BACKPACK.get()).getDefaultInstance(), FMLLoader.getDist() == Dist.CLIENT).keySet());
         this.backpackCurioIdentifiers.add("curio");
      }

      return this.backpackCurioIdentifiers;
   }

   public static <T> T getFromCuriosSlotStackHandler(
      LivingEntity livingEntity, String identifier, Function<ICurioStacksHandler, T> getFromHandler, T defaultValue
   ) {
      return CuriosApi.getCuriosInventory(livingEntity).map(h -> h.getStacksHandler(identifier).map(getFromHandler).orElse(defaultValue)).orElse(defaultValue);
   }

   public void setup() {
      if (FMLEnvironment.dist.isClient()) {
         CuriosCompatClient.registerRenderers();
      }

      this.addPlayerInventoryHandlers();
   }
}
