package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public record ConfiguredBookComponent<C extends BookComponentConfig, B extends BookComponent<C>>(B component, C config) {
   public static final Codec<ConfiguredBookComponent<?, ?>> CODEC = BookComponentRegistry.BY_NAME_CODEC
      .dispatch(configured -> configured.component, BookComponent::getConfiguredCodec);

   public boolean isEnabled(Set<ResourceLocation> unlocked) {
      if (this.config().unlockConditions().isEmpty()) {
         return true;
      }

      boolean enabled = false;

      for (HashSet<ResourceLocation> conditions : this.config().unlockConditions()) {
         boolean conditionGroup = true;

         for (ResourceLocation condition : conditions) {
            conditionGroup = conditionGroup && unlocked.contains(condition);
         }

         enabled = enabled || conditionGroup;
      }

      return enabled;
   }

   public int getTotalHeight(BookContext context) {
      return this.component().getTotalHeight(this.config(), context);
   }

   public void render(BookContext context, GuiGraphics graphics, int x, int y) {
      this.component().render(this.config(), context, graphics, x, y);
   }

   public void renderDelayed(BookContext context, GuiGraphics graphics, int x, int y) {
      this.component().renderDelayed(this.config(), context, graphics, x, y);
   }

   public void tick(BookContext context, int x, int y) {
      this.component().tick(this.config(), context, x, y);
   }

   public void onClick(BookContext context, int x, int y) {
      this.component().onClick(this.config(), context, x, y);
   }
}
