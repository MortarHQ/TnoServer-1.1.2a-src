package com.tom.storagemod.jade;

import com.tom.storagemod.block.entity.InventoryConnectorBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.ProgressStyle;

public enum InventoryConnectorProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
   INSTANCE;

   public ResourceLocation getUid() {
      return JadePlugin.INVENTORY_CONNECTOR;
   }

   public void appendServerData(CompoundTag data, BlockAccessor accessor) {
      InventoryConnectorBlockEntity te = (InventoryConnectorBlockEntity)accessor.getBlockEntity();
      InventoryConnectorBlockEntity.UsageInfo usage = te.getUsage();
      data.putInt("free", usage.free());
      data.putInt("all", usage.all());
      data.putInt("blocks", usage.blocks());
   }

   public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
      if (accessor.getServerData().contains("free")) {
         int free = accessor.getServerData().getInt("free");
         int all = accessor.getServerData().getInt("all");
         int blocks = accessor.getServerData().getInt("blocks");
         IElementHelper elements = IElementHelper.get();
         ProgressStyle progressStyle = elements.progressStyle().color(-7444195, -13357274);
         tooltip.add(Component.translatable("tooltip.toms_storage.connector_info.size", new Object[]{free, all}));
         tooltip.add(elements.progress((float)(all - free) / all, null, progressStyle, BoxStyle.getNestedBox(), true));
         tooltip.add(Component.translatable("tooltip.toms_storage.connector_info.inv", new Object[]{blocks}));
      }
   }
}
