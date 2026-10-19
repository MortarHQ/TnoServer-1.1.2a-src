package fzzyhmstrs.emi_loot.util.stack;

import dev.emi.emi.api.stack.EmiStack;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.EMILootAgnos;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TrimmedTitle;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public abstract class EmiStackWithTitle extends EmiStack {
   protected final ResourceLocation id;
   private final TrimmedTitle name;
   private final MutableComponent rawName;

   public EmiStackWithTitle(ResourceLocation id, String unknownNamespace, String unknownPath, EMILoot.Type type, int width, ChatFormatting... formatting) {
      this.id = id;
      this.rawName = this.getRawTitle(id, unknownNamespace, unknownPath, type);
      this.name = TrimmedTitle.of(this.rawName.copy().withStyle(formatting), width);
   }

   protected MutableComponent getRawTitle(ResourceLocation id, String unknownNamespace, String unknownPath, EMILoot.Type type) {
      String key = String.join(".", "emi_loot", unknownNamespace, id.toString());
      MutableComponent rawTitle;
      if (!I18n.exists(key)) {
         StringBuilder chestName = new StringBuilder();
         String[] chestPathTokens = id.getPath().split("[/_]");

         for (String str : chestPathTokens) {
            if (!LText.tablePrefixes.contains(str)) {
               if (!chestName.isEmpty()) {
                  chestName.append(" ");
               }

               if (str.length() <= 1) {
                  chestName.append(str);
               } else {
                  chestName.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));
               }
            }
         }

         if (EMILootAgnos.isModLoaded(id.getNamespace())) {
            rawTitle = LText.translatable(this.getUnknownModdedKey(unknownNamespace, unknownPath), chestName.toString());
         } else {
            Component unknown = LText.translatable(this.getUnknownKey(unknownNamespace));
            rawTitle = LText.translatable(
               this.getUnknownModdedKey(unknownNamespace, unknownPath), LText.literal(chestName.toString()).append(" ").append(unknown)
            );
         }

         if (EMILoot.config.isLogI18n(type)) {
            EMILoot.LOGGER.warn("Untranslated " + unknownNamespace + " loot table \"" + id + "\" (key: \"" + key + "\")");
         }
      } else {
         rawTitle = LText.translatable(key);
      }

      return rawTitle;
   }

   private String getUnknownModdedKey(String namespace, String path) {
      return String.join(".", "emi_loot", namespace, path);
   }

   private String getUnknownKey(String namespace) {
      return String.join(".", "emi_loot", namespace, "unknown");
   }

   public void render(GuiGraphics draw, int x, int y, float delta, int flags) {
   }

   public boolean isEmpty() {
      return false;
   }

   public DataComponentPatch getComponentChanges() {
      return DataComponentPatch.EMPTY;
   }

   public Object getKey() {
      return this.getId().toString();
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public List<Component> getTooltipText() {
      return List.of();
   }

   public Component getName() {
      return this.rawName.copy();
   }

   public FormattedCharSequence getTrimmedName() {
      return this.name.title();
   }

   public boolean isTrimmed() {
      return this.name.trimmed();
   }
}
