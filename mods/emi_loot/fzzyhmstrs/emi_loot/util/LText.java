package fzzyhmstrs.emi_loot.util;

import dev.emi.emi.api.render.EmiTooltipComponents;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.Holder;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.enchantment.Enchantment;

public class LText {
   public static HashSet<String> tablePrefixes = new HashSet<>(
      List.of(
         "spawners",
         "spawner",
         "shearing",
         "pots",
         "pot",
         "gameplay",
         "equipment",
         "entities",
         "entity",
         "dispensers",
         "dispenser",
         "chests",
         "chest",
         "blocks",
         "block",
         "archaeology"
      )
   );

   public static List<ClientTooltipComponent> components(Component title, String namespace) {
      ArrayList<ClientTooltipComponent> components = new ArrayList<>();
      components.add(EmiTooltipComponents.of(title));
      EmiTooltipComponents.appendModName(components, namespace);
      return components;
   }

   public static MutableComponent translatable(String key) {
      return Component.translatable(key);
   }

   public static MutableComponent translatable(String key, Object... args) {
      return Component.translatable(key, args);
   }

   public static MutableComponent literal(String msg) {
      return Component.literal(msg);
   }

   public static MutableComponent empty() {
      return Component.empty();
   }

   public static FormattedCharSequence trim(Component text, int width) {
      Font textRenderer = Minecraft.getInstance().font;
      FormattedText stringVisitable = textRenderer.substrByWidth(text, width - textRenderer.width(CommonComponents.ELLIPSIS));
      return Language.getInstance().getVisualOrder(FormattedText.composite(new FormattedText[]{stringVisitable, CommonComponents.ELLIPSIS}));
   }

   public static MutableComponent enchant(Holder<Enchantment> entry) {
      MutableComponent mutableText = ((Enchantment)entry.value()).description().copy();
      if (entry.is(EnchantmentTags.CURSE)) {
         ComponentUtils.mergeStyles(mutableText, Style.EMPTY.withColor(ChatFormatting.RED));
      } else {
         ComponentUtils.mergeStyles(mutableText, Style.EMPTY.withColor(ChatFormatting.GRAY));
      }

      return mutableText;
   }
}
