package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Style.Serializer;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class MobInfoBookComponent extends BookComponent<MobInfoBookComponent.Config> {
   private static final Object2IntMap<ResourceLocation> SELECTED_INDEX = new Object2IntArrayMap();
   private static final Map<EntityType<?>, Entity> CACHED_ENTITY_SAMPLES = new HashMap<>();

   public MobInfoBookComponent() {
      super(MobInfoBookComponent.Config.CODEC);
   }

   public int getTotalHeight(MobInfoBookComponent.Config config, BookContext context) {
      return config.height();
   }

   public void render(MobInfoBookComponent.Config config, BookContext context, GuiGraphics graphics, int x, int y) {
      if (!config.entries().isEmpty()) {
         int selectedIndex = Mth.clamp(SELECTED_INDEX.getOrDefault(config.id(), 0), 0, config.entries().size() - 1);
         int centerY = y + config.height() / 2;
         graphics.blit(
            config.leftButton(),
            x + config.centerX() - config.width() / 2,
            centerY - config.buttonHeight() / 2,
            0.0F,
            0.0F,
            config.buttonWidth(),
            config.buttonHeight(),
            config.buttonWidth(),
            config.buttonHeight()
         );
         graphics.blit(
            config.rightButton(),
            x + config.centerX() + config.width() / 2 - config.buttonWidth(),
            centerY - config.buttonHeight() / 2,
            0.0F,
            0.0F,
            config.buttonWidth(),
            config.buttonHeight(),
            config.buttonWidth(),
            config.buttonHeight()
         );
         MobInfoBookComponent.Entry entry = config.entries().get(selectedIndex);
         graphics.blit(
            entry.icon(),
            x + config.centerX() - entry.iconWidth() / 2,
            centerY - entry.iconHeight() / 2,
            0.0F,
            0.0F,
            entry.iconWidth(),
            entry.iconHeight(),
            entry.iconWidth(),
            entry.iconHeight()
         );
      }
   }

   public void renderDelayed(MobInfoBookComponent.Config config, BookContext context, GuiGraphics graphics, int x, int y) {
      if (!config.entries().isEmpty()) {
         int selectedIndex = Mth.clamp(SELECTED_INDEX.getOrDefault(config.id(), 0), 0, config.entries().size() - 1);
         int centerY = y + config.height() / 2;
         MobInfoBookComponent.Entry entry = config.entries().get(selectedIndex);
         if (context.getMouseX() >= x + config.centerX() - entry.iconWidth() / 2
            && context.getMouseX() <= x + config.centerX() + entry.iconWidth() / 2
            && context.getMouseY() >= Math.max(centerY - entry.iconHeight() / 2, context.getContentY())
            && context.getMouseY()
               <= Math.min(
                  centerY + entry.iconHeight() / 2, context.getContentY() + context.getBookDefinition().height() - 2 * context.getBookDefinition().frameWidth()
               )) {
            List<Component> tooltip = new ArrayList<>();
            if (entry.attribute().isPresent()) {
               Optional<EntityType<?>> entityType = BuiltInRegistries.ENTITY_TYPE.getOptional(config.mob());
               Optional<Reference<Attribute>> attribute = BuiltInRegistries.ATTRIBUTE.getHolder(entry.attribute().get());
               if (entityType.isPresent() && attribute.isPresent() && Minecraft.getInstance().level != null) {
                  Entity entity = CACHED_ENTITY_SAMPLES.computeIfAbsent(entityType.get(), type -> type.create(Minecraft.getInstance().level));
                  if (entity instanceof LivingEntity living) {
                     AttributeInstance instance = living.getAttribute((Holder)attribute.get());
                     if (instance != null && entry.attributeTextStyle().isPresent()) {
                        tooltip.add(
                           Component.translatable(((Attribute)attribute.get().value()).getDescriptionId())
                              .append(": " + ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(instance.getValue()))
                              .withStyle(entry.attributeTextStyle().get())
                        );
                        if (!entry.text().toComponent().getString().isEmpty()) {
                           tooltip.add(Component.empty());
                        }
                     }
                  }
               }
            }

            if (!entry.text().toComponent().getString().isEmpty()) {
               List<FormattedCharSequence> list = context.getFont().split(entry.text().toComponent(), config.tooltipWidth());
               list.forEach(s -> tooltip.add(new Component() {
                  public Style getStyle() {
                     return Style.EMPTY;
                  }

                  public ComponentContents getContents() {
                     return PlainTextContents.EMPTY;
                  }

                  public List<Component> getSiblings() {
                     return List.of();
                  }

                  public FormattedCharSequence getVisualOrderText() {
                     return s;
                  }
               }));
            }

            graphics.pose().pushPose();
            graphics.pose().translate(0.0, 0.0, 790.0);
            graphics.renderTooltip(context.getFont(), tooltip, Optional.empty(), context.getMouseX(), context.getMouseY());
            graphics.pose().popPose();
         }
      }
   }

   public void onClick(MobInfoBookComponent.Config config, BookContext context, int x, int y) {
      if (!config.entries().isEmpty()) {
         int selectedIndex = Mth.clamp(SELECTED_INDEX.getOrDefault(config.id(), 0), 0, config.entries().size() - 1);
         int leftButtonX = x + config.centerX() - config.width() / 2;
         int rightButtonX = x + config.centerX() + config.width() / 2 - config.buttonWidth();
         int buttonY = y + config.height() / 2 - config.buttonHeight() / 2;
         if (context.getMouseX() >= leftButtonX
            && context.getMouseX() <= leftButtonX + config.buttonWidth()
            && context.getMouseY() >= buttonY
            && context.getMouseY() <= buttonY + config.buttonHeight()) {
            SELECTED_INDEX.put(config.id(), Mth.clamp(selectedIndex - 1, 0, config.entries().size() - 1));
         }

         if (context.getMouseX() >= rightButtonX
            && context.getMouseX() <= rightButtonX + config.buttonWidth()
            && context.getMouseY() >= buttonY
            && context.getMouseY() < buttonY + config.buttonHeight()) {
            SELECTED_INDEX.put(config.id(), Mth.clamp(selectedIndex + 1, 0, config.entries().size() - 1));
         }
      }
   }

   public record Config(
      ResourceLocation id,
      HashSet<HashSet<ResourceLocation>> unlockConditions,
      ResourceLocation mob,
      List<MobInfoBookComponent.Entry> entries,
      int tooltipWidth,
      int centerX,
      int width,
      int height,
      int buttonWidth,
      int buttonHeight,
      ResourceLocation leftButton,
      ResourceLocation rightButton
   ) implements BookComponentConfig {
      public static final Codec<MobInfoBookComponent.Config> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("id").forGetter(MobInfoBookComponent.Config::id),
               ResourceLocation.CODEC
                  .listOf()
                  .xmap(Sets::newHashSet, Lists::newArrayList)
                  .listOf()
                  .xmap(Sets::newHashSet, Lists::newArrayList)
                  .fieldOf("unlock_conditions")
                  .forGetter(MobInfoBookComponent.Config::unlockConditions),
               ResourceLocation.CODEC.fieldOf("mob").forGetter(MobInfoBookComponent.Config::mob),
               MobInfoBookComponent.Entry.CODEC.listOf().fieldOf("entries").forGetter(MobInfoBookComponent.Config::entries),
               Codec.INT.fieldOf("tooltip_width").forGetter(MobInfoBookComponent.Config::tooltipWidth),
               Codec.INT.fieldOf("center_x").forGetter(MobInfoBookComponent.Config::centerX),
               Codec.INT.fieldOf("width").forGetter(MobInfoBookComponent.Config::width),
               Codec.INT.fieldOf("height").forGetter(MobInfoBookComponent.Config::height),
               Codec.INT.fieldOf("button_width").forGetter(MobInfoBookComponent.Config::buttonWidth),
               Codec.INT.fieldOf("button_height").forGetter(MobInfoBookComponent.Config::buttonHeight),
               ResourceLocation.CODEC.fieldOf("left_button").forGetter(MobInfoBookComponent.Config::leftButton),
               ResourceLocation.CODEC.fieldOf("right_button").forGetter(MobInfoBookComponent.Config::rightButton)
            )
            .apply(instance, MobInfoBookComponent.Config::new)
      );
   }

   public record Entry(
      BookContent text, Optional<ResourceLocation> attribute, Optional<Style> attributeTextStyle, int iconWidth, int iconHeight, ResourceLocation icon
   ) {
      public static final Codec<MobInfoBookComponent.Entry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BookContent.CODEC.fieldOf("text").forGetter(MobInfoBookComponent.Entry::text),
               ResourceLocation.CODEC.optionalFieldOf("attribute").forGetter(MobInfoBookComponent.Entry::attribute),
               Serializer.CODEC.optionalFieldOf("attribute_text_style").forGetter(MobInfoBookComponent.Entry::attributeTextStyle),
               Codec.INT.fieldOf("icon_width").forGetter(MobInfoBookComponent.Entry::iconWidth),
               Codec.INT.fieldOf("icon_height").forGetter(MobInfoBookComponent.Entry::iconHeight),
               ResourceLocation.CODEC.fieldOf("icon").forGetter(MobInfoBookComponent.Entry::icon)
            )
            .apply(instance, MobInfoBookComponent.Entry::new)
      );
   }
}
