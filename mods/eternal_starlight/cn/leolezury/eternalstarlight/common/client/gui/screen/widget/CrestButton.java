package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.ESGuiUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.phys.Vec3;

public class CrestButton extends Button {
   private static final int CREST_WIDTH = 72;
   private static final int CREST_HEIGHT = 72;
   private final boolean orbit;
   private Crest.Instance crest;
   private int prevHoverProgress;
   private int hoverProgress;
   private float prevX;
   private float prevY;
   private float orbitCenterX;
   private float orbitCenterY;
   private float angle;
   private float prevAngle;

   public CrestButton(int x, int y, int width, int height, Component component, OnPress onPress) {
      this(x, y, width, height, false, component, onPress);
   }

   public CrestButton(int x, int y, int width, int height, boolean orbit, Component component, OnPress onPress) {
      super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
      this.orbit = orbit;
   }

   public void setAngle(float angle) {
      this.angle = angle;
   }

   public void setOrbitCenter(float orbitCenterX, float orbitCenterY) {
      this.orbitCenterX = orbitCenterX;
      this.orbitCenterY = orbitCenterY;
   }

   public CrestButton setCrest(Crest.Instance crest) {
      if (this.crest != crest && Minecraft.getInstance().level != null) {
         if (crest == null) {
            this.setTooltip(Tooltip.create(Component.empty()));
         } else {
            Registry<Crest> registry = Minecraft.getInstance().level.registryAccess().registryOrThrow(ESRegistries.CREST);
            MutableComponent nameComponent = Component.translatable(Util.makeDescriptionId("crest", registry.getKey((Crest)crest.crest().value())));
            MutableComponent levelComponent = Component.translatable("enchantment.level." + crest.level());
            MutableComponent typeComponent = Component.translatable(
                  Util.makeDescriptionId("mana_type", EternalStarlight.id(((Crest)crest.crest().value()).type().getSerializedName()))
               )
               .withColor(((Crest)crest.crest().value()).type().getColor());
            if (((Crest)crest.crest().value()).getSpell().isPresent()) {
               AbstractSpell spell = ((Crest)crest.crest().value()).getSpell().get();
               MutableComponent spellTypeComponent = Component.translatable("tooltip.eternal_starlight.crest_spell_elements").withStyle(ChatFormatting.AQUA);

               for (ManaType type : spell.spellProperties().types()) {
                  spellTypeComponent.append(" ")
                     .append(
                        Component.translatable(Util.makeDescriptionId("mana_type", EternalStarlight.id(type.getSerializedName()))).withColor(type.getColor())
                     );
               }

               typeComponent.append("\n").append(spellTypeComponent);
            }

            MutableComponent descComponent = Component.translatable(Util.makeDescriptionId("crest", registry.getKey((Crest)crest.crest().value())) + ".desc");
            if (((Crest)crest.crest().value()).attributeModifiers().isEmpty()) {
               descComponent.append("\n").append(Component.translatable("tooltip.eternal_starlight.unwearable").withStyle(ChatFormatting.BLUE));
            }

            MutableComponent merged = nameComponent.append(" ").append(levelComponent).append("\n").append(typeComponent).append("\n").append(descComponent);
            if (((Crest)crest.crest().value()).attributeModifiers().isPresent()) {
               for (Crest.LevelBasedAttributeModifier modifier : ((Crest)crest.crest().value()).attributeModifiers().get()) {
                  this.addModifierTooltip(c -> merged.append("\n").append(c), modifier.attribute(), modifier.getModifier(crest.level()));
               }
            }

            this.setTooltip(Tooltip.create(merged));
         }
      }

      this.crest = crest;
      return this;
   }

   private void addModifierTooltip(Consumer<Component> consumer, Holder<Attribute> holder, AttributeModifier attributeModifier) {
      double d = attributeModifier.amount();
      double e;
      if (attributeModifier.operation() == Operation.ADD_MULTIPLIED_BASE || attributeModifier.operation() == Operation.ADD_MULTIPLIED_TOTAL) {
         e = d * 100.0;
      } else if (holder.is(Attributes.KNOCKBACK_RESISTANCE)) {
         e = d * 10.0;
      } else {
         e = d;
      }

      if (d > 0.0) {
         consumer.accept(
            Component.translatable(
                  "attribute.modifier.plus." + attributeModifier.operation().id(),
                  new Object[]{
                     ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(((Attribute)holder.value()).getDescriptionId())
                  }
               )
               .withStyle(((Attribute)holder.value()).getStyle(true))
         );
      } else if (d < 0.0) {
         consumer.accept(
            Component.translatable(
                  "attribute.modifier.take." + attributeModifier.operation().id(),
                  new Object[]{
                     ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(-e), Component.translatable(((Attribute)holder.value()).getDescriptionId())
                  }
               )
               .withStyle(((Attribute)holder.value()).getStyle(false))
         );
      }
   }

   public Crest.Instance getCrest() {
      return this.crest;
   }

   public boolean isEmpty() {
      return this.crest == null;
   }

   public void tick() {
      this.prevHoverProgress = this.hoverProgress;
      this.prevX = this.getX();
      this.prevY = this.getY();
      this.prevAngle = this.angle;
      if (this.isHovered()) {
         if (this.hoverProgress < 5) {
            this.hoverProgress++;
         }
      } else if (this.hoverProgress > 0) {
         this.hoverProgress--;
      }

      this.active = this.crest != null;
   }

   protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
      float partialTicks = Minecraft.getInstance()
         .getTimer()
         .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
      float x;
      float y;
      if (this.orbit) {
         float currentAngle = Mth.lerp(partialTicks, this.prevAngle, this.angle);
         Vec3 centerPos = ESMathUtil.rotationToPosition(new Vec3(this.orbitCenterX, 0.0, this.orbitCenterY), 60.0F, 0.0F, currentAngle);
         x = (float)(centerPos.x - 36.0);
         y = (float)(centerPos.z - 36.0);
         this.setPosition((int)x, (int)y);
      } else {
         x = Mth.lerp(partialTicks, this.prevX, this.getX());
         y = Mth.lerp(partialTicks, this.prevY, this.getY());
      }

      if (this.crest != null) {
         float progress = Mth.lerp(partialTicks, this.prevHoverProgress, this.hoverProgress) / 40.0F + 1.0F;
         float width = 72.0F * progress;
         float height = 72.0F * progress;
         ESGuiUtil.blitFloat(
            guiGraphics,
            ((Crest)this.crest.crest().value()).texture(),
            x - (width - this.getWidth()) / 2.0F,
            y - (height - this.getHeight()) / 2.0F,
            width,
            height,
            width,
            height
         );
      }
   }
}
