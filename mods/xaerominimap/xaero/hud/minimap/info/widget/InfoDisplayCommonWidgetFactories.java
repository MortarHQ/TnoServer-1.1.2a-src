package xaero.hud.minimap.info.widget;

import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;

public class InfoDisplayCommonWidgetFactories {
   public static final InfoDisplayCycleWidgetFactory<Boolean> OFF_ON = new InfoDisplayCycleWidgetFactory<>(
      Lists.newArrayList(new Boolean[]{false, true}),
      Lists.newArrayList(new Component[]{Component.translatable("gui.xaero_off"), Component.translatable("gui.xaero_on")})
   );
   public static final InfoDisplayWidgetFactory<Boolean> ALWAYS_ON = (x, y, w, h, entry, onChange, includeNull) -> null;
}
