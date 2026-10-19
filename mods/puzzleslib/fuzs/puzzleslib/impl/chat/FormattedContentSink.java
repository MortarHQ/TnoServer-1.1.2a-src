package fuzs.puzzleslib.impl.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.FormattedText.StyledContentConsumer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.Nullable;

public class FormattedContentSink implements StyledContentConsumer<Unit>, FormattedCharSink {
   private final List<Entry<StringBuilder, Style>> builders = new ArrayList<>(Collections.singletonList(Map.entry(new StringBuilder(), Style.EMPTY)));
   @Nullable
   private Component component;
   @Nullable
   private String string;

   public FormattedContentSink(FormattedText formattedText) {
      formattedText.visit(this, Style.EMPTY);
      if (formattedText instanceof Component) {
         this.component = ((Component)formattedText).copy();
      }
   }

   public FormattedContentSink(FormattedCharSequence formattedCharSequence) {
      formattedCharSequence.accept(this);
   }

   public Optional<Unit> accept(Style style, String string) {
      Entry<StringBuilder, Style> entry = this.builders.get(this.builders.size() - 1);
      if (entry.getValue() != style && (!entry.getValue().isEmpty() || !style.isEmpty())) {
         this.builders.add(Map.entry(new StringBuilder(string), style));
      } else {
         entry.getKey().append(string);
      }

      return Optional.empty();
   }

   public boolean accept(int width, Style style, int codePoint) {
      this.accept(style, String.valueOf(Character.toChars(codePoint)));
      return true;
   }

   public Component getComponent() {
      if (this.component == null) {
         MutableComponent component = null;

         for (Entry<StringBuilder, Style> entry : this.builders) {
            MutableComponent componentFromEntry = Component.literal(entry.getKey().toString()).setStyle(entry.getValue());
            if (component == null) {
               component = componentFromEntry;
            } else {
               component.append(componentFromEntry);
            }
         }

         Objects.requireNonNull(component, "component is null");
         this.component = component;
      }

      return this.component;
   }

   public String getString() {
      if (this.string == null) {
         StringBuilder builder = new StringBuilder();

         for (Entry<StringBuilder, Style> entry : this.builders) {
            builder.append(this.getStringFormatFromStyle(entry.getValue()));
            builder.append(entry.getKey());
            builder.append(ChatFormatting.RESET);
         }

         this.string = builder.toString();
      }

      return this.string;
   }

   public String getStringFormatFromStyle(Style style) {
      return style.isEmpty() ? "" : this.getFormatFromStyle(style).stream().<CharSequence>map(ChatFormatting::toString).collect(Collectors.joining());
   }

   public List<ChatFormatting> getFormatFromStyle(Style style) {
      List<ChatFormatting> list = new ArrayList<>();
      if (style.getColor() != null) {
         ChatFormatting color = ChatFormatting.getByName(style.getColor().serialize());
         if (color != null) {
            list.add(color);
         }
      }

      if (style.isObfuscated()) {
         list.add(ChatFormatting.OBFUSCATED);
      }

      if (style.isBold()) {
         list.add(ChatFormatting.BOLD);
      }

      if (style.isStrikethrough()) {
         list.add(ChatFormatting.STRIKETHROUGH);
      }

      if (style.isUnderlined()) {
         list.add(ChatFormatting.UNDERLINE);
      }

      if (style.isItalic()) {
         list.add(ChatFormatting.ITALIC);
      }

      return list;
   }
}
