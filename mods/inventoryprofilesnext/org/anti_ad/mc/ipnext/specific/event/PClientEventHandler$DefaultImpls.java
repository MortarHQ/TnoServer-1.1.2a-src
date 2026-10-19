package org.anti_ad.mc.ipnext.specific.event;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import org.anti_ad.mc.common.moreinfo.SemVer;
import org.anti_ad.mc.common.vanilla.alias.HoverEventExtKt;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nPClientEventHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PClientEventHandler.kt\norg/anti_ad/mc/ipnext/specific/event/PClientEventHandler$DefaultImpls\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,83:1\n1#2:84\n*E\n"
)
public final class PClientEventHandler$DefaultImpls {
   @NotNull
   public static MutableComponent createChatMessage(@NotNull PClientEventHandler var0, @NotNull SemVer var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      MutableComponent var10000 = Component.literal("");
      MutableComponent var4;
      MutableComponent var2 = var4 = Component.literal("Inventory Profiles Next:");
      MutableComponent var3 = var10000;
      var2.setStyle(Style.EMPTY.withBold(Boolean.TRUE).withColor(ChatFormatting.AQUA));
      var10000 = var3.append((Component)var4);
      MutableComponent var5;
      var2 = var5 = Component.literal(I18n.INSTANCE.translate("inventoryprofiles.update.version", new Object[0]));
      var3 = var10000;
      var2.setStyle(Style.EMPTY);
      var10000 = var3.append((Component)var5);
      MutableComponent var6;
      var2 = var6 = Component.literal("'" + var1 + "'");
      var3 = var10000;
      var2.setStyle(Style.EMPTY.withBold(Boolean.TRUE).withColor(ChatFormatting.DARK_GREEN));
      var10000 = var3.append((Component)var6);
      MutableComponent var7;
      var2 = var7 = Component.literal(I18n.INSTANCE.translate("inventoryprofiles.update.available", new Object[0]));
      var3 = var10000;
      var2.setStyle(Style.EMPTY);
      var10000 = var3.append((Component)var7).append(I18n.INSTANCE.translate("inventoryprofiles.update.get", new Object[0]));
      MutableComponent var8;
      var2 = var8 = Component.literal("\"Modrinth\"");
      var3 = var10000;
      var2.setStyle(
         Style.EMPTY
            .withBold(Boolean.TRUE)
            .withColor(ChatFormatting.DARK_GREEN)
            .withUnderlined(Boolean.TRUE)
            .withClickEvent(new ClickEvent(Action.OPEN_URL, "https://modrinth.com/mod/inventory-profiles-next"))
            .withHoverEvent(HoverEventExtKt.createHoverEventText("https://modrinth.com/mod/inventory-profiles-next"))
      );
      var10000 = var3.append((Component)var8);
      MutableComponent var9;
      var2 = var9 = Component.literal(I18n.INSTANCE.translate("inventoryprofiles.update.or", new Object[0]));
      var3 = var10000;
      var2.setStyle(Style.EMPTY);
      var10000 = var3.append((Component)var9);
      MutableComponent var10;
      var2 = var10 = Component.literal("\"CurseForge\"");
      var3 = var10000;
      var2.setStyle(
         Style.EMPTY
            .withBold(Boolean.TRUE)
            .withColor(ChatFormatting.DARK_RED)
            .withUnderlined(Boolean.TRUE)
            .withClickEvent(new ClickEvent(Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/inventory-profiles-next"))
            .withHoverEvent(HoverEventExtKt.createHoverEventText("https://www.curseforge.com/minecraft/mc-mods/inventory-profiles-next"))
      );
      var10000 = var3.append((Component)var10);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }
}
