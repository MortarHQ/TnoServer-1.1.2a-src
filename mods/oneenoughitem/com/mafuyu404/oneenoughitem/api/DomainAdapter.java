package com.mafuyu404.oneenoughitem.api;

import com.mafuyu404.oneenoughitem.client.gui.ReplacementEditorScreen;
import com.mafuyu404.oneenoughitem.client.gui.cache.AbstractGlobalReplacementCache;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface DomainAdapter {
   String modId();

   String id();

   String dataId();

   Component selectObjectLabel();

   Component selectTagLabel();

   @OnlyIn(Dist.CLIENT)
   Screen createObjectSelectionScreen(ReplacementEditorScreen var1, boolean var2);

   @OnlyIn(Dist.CLIENT)
   Screen createTagSelectionScreen(ReplacementEditorScreen var1, boolean var2);

   boolean isAvailable();

   AbstractGlobalReplacementCache globalCache();

   DomainRuntimeCache runtimeCache();

   String dataIdFromItem(Item var1);

   ItemStack iconForDataId(String var1);

   @OnlyIn(Dist.CLIENT)
   void renderDataId(GuiGraphics var1, String var2, int var3, int var4);

   Component displayName(String var1);

   ReplacementUiAdapter uiAdapter();
}
