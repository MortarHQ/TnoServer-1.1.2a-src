package net.blay09.mods.waystones.item;

import java.util.List;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.core.InvalidWaystone;
import net.blay09.mods.waystones.menu.WaystoneModifierMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class CrumblingAttunedShardItem extends AbstractAttunedShardItem {
   public CrumblingAttunedShardItem(Properties properties) {
      super(properties.stacksTo(4));
   }

   @Override
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(stack, context, list, flag);
      Waystone attunedWarpPlate = this.getWaystoneAttunedTo(null, null, stack).orElse(InvalidWaystone.INSTANCE);
      if (attunedWarpPlate.isValid()) {
         MutableComponent textComponent = Component.translatable("tooltip.waystones.attuned_shard.attunement_crumbling");
         textComponent.withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC);
         Player player = Balm.getProxy().getClientPlayer();
         if (!(player != null && player.containerMenu instanceof WaystoneModifierMenu wpc)) {
            list.add(textComponent);
         } else if (!attunedWarpPlate.getWaystoneUid().equals(wpc.getWaystone().getWaystoneUid())) {
            list.add(textComponent);
         }
      }
   }
}
