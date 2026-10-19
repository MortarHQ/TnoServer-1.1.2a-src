package com.faboslav.friendsandfoes.neoforge.modcompat;

import com.faboslav.friendsandfoes.common.events.lifecycle.ClientSetupEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import com.faboslav.friendsandfoes.neoforge.modcompat.curios.CuriosTotemRenderer;
import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public final class CuriosCompat implements ModCompat {
   public CuriosCompat() {
      ClientSetupEvent.EVENT.addListener(CuriosCompat::registerRenderers);
   }

   @Override
   public EnumSet<ModCompat.Type> compatTypes() {
      return EnumSet.of(ModCompat.Type.CUSTOM_EQUIPMENT_SLOTS);
   }

   private static void registerRenderers(ClientSetupEvent clientSetupEvent) {
      CuriosRendererRegistry.register(Items.TOTEM_OF_UNDYING, CuriosTotemRenderer::new);
      CuriosRendererRegistry.register((Item)FriendsAndFoesItems.TOTEM_OF_FREEZING.get(), CuriosTotemRenderer::new);
      CuriosRendererRegistry.register((Item)FriendsAndFoesItems.TOTEM_OF_ILLUSION.get(), CuriosTotemRenderer::new);
   }

   @Nullable
   @Override
   public ItemStack getEquippedItemFromCustomSlots(Entity entity, Predicate<ItemStack> itemStackPredicate) {
      return entity instanceof Player player
         ? CuriosApi.getCuriosInventory(player).map(i -> i.findFirstCurio(itemStackPredicate).map(SlotResult::stack).orElse(null)).orElse(null)
         : null;
   }
}
