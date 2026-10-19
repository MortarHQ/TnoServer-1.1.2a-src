package net.blay09.mods.waystones.item;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.trait.IAttunementItem;
import net.blay09.mods.waystones.block.WarpPlateBlock;
import net.blay09.mods.waystones.component.ModComponents;
import net.blay09.mods.waystones.core.InvalidWaystone;
import net.blay09.mods.waystones.core.WaystoneProxy;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractAttunedShardItem extends ShardItem implements IAttunementItem {
   public AbstractAttunedShardItem(Properties properties) {
      super(properties);
   }

   public boolean isFoil(ItemStack itemStack) {
      Optional<Waystone> waystoneAttunedTo = this.getWaystoneAttunedTo(null, null, itemStack);
      return waystoneAttunedTo.map(Waystone::isValid).orElse(false);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(stack, context, list, flag);
      Waystone attunedWaystone = this.getWaystoneAttunedTo(null, null, stack).orElse(InvalidWaystone.INSTANCE);
      if (!attunedWaystone.isValid()) {
         MutableComponent textComponent = Component.translatable("tooltip.waystones.attuned_shard.attunement_lost");
         textComponent.withStyle(ChatFormatting.GRAY);
         list.add(textComponent);
      } else {
         if (attunedWaystone.getWaystoneType().equals(WaystoneTypes.WARP_PLATE)) {
            list.add(WarpPlateBlock.getGalacticName(attunedWaystone));
         } else {
            list.add(attunedWaystone.getName().copy().withStyle(ChatFormatting.LIGHT_PURPLE));
         }

         list.add(Component.translatable("tooltip.waystones.attuned_shard.plug_into_warp_plate"));
      }
   }

   @Override
   public Optional<Waystone> getWaystoneAttunedTo(MinecraftServer server, Player player, ItemStack itemStack) {
      return Optional.ofNullable((UUID)itemStack.get((DataComponentType)ModComponents.attunement.get()))
         .map(attunement -> new WaystoneProxy(server, (UUID)attunement));
   }

   @Override
   public void setWaystoneAttunedTo(ItemStack itemStack, @Nullable Waystone waystone) {
      if (waystone != null) {
         itemStack.set((DataComponentType)ModComponents.attunement.get(), waystone.getWaystoneUid());
      } else {
         itemStack.remove((DataComponentType)ModComponents.attunement.get());
      }
   }
}
