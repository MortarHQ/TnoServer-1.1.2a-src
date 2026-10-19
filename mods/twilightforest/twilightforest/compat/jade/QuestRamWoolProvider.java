package twilightforest.compat.jade;

import java.util.Map.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;
import twilightforest.TwilightForestMod;
import twilightforest.entity.passive.QuestRam;
import twilightforest.util.ColorUtil;

public enum QuestRamWoolProvider implements IEntityComponentProvider {
   INSTANCE;

   public void appendTooltip(ITooltip tooltip, EntityAccessor entityAccessor, IPluginConfig pluginConfig) {
      if (entityAccessor.getEntity() instanceof QuestRam ram) {
         int getRenderedWools = 0;

         for (Entry<DyeColor, Block> entry : ColorUtil.WOOL_TO_DYE_IN_RAM_ORDER.entrySet()) {
            if (!ram.isColorPresent(entry.getKey())) {
               if (getRenderedWools % 8 == 0) {
                  tooltip.add(IElementHelper.get().smallItem(new ItemStack((ItemLike)entry.getValue())).size(new Vec2(4.0F, 4.0F)));
               } else {
                  tooltip.append(IElementHelper.get().smallItem(new ItemStack((ItemLike)entry.getValue())).size(new Vec2(4.0F, 4.0F)));
               }

               tooltip.append(Component.literal(" "));
               getRenderedWools++;
            }
         }
      }
   }

   public ResourceLocation getUid() {
      return TwilightForestMod.prefix("quest_ram_wool");
   }
}
