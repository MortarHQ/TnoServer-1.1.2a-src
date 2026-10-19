package me.decce.gnetum.compat.embeddium;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import me.decce.gnetum.Gnetum;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.embeddedt.embeddium.api.options.OptionIdentifier;
import org.embeddedt.embeddium.api.options.control.ControlValueFormatter;
import org.embeddedt.embeddium.api.options.control.SliderControl;
import org.embeddedt.embeddium.api.options.control.TickBoxControl;
import org.embeddedt.embeddium.api.options.structure.OptionGroup;
import org.embeddedt.embeddium.api.options.structure.OptionImpact;
import org.embeddedt.embeddium.api.options.structure.OptionImpl;
import org.embeddedt.embeddium.api.options.structure.OptionPage;
import org.embeddedt.embeddium.api.options.structure.OptionStorage;
import org.embeddedt.embeddium.api.options.structure.OptionGroup.Builder;

public class GnetumEmbeddiumPage extends OptionPage {
   public static final OptionStorage<?> STORAGE = new OptionStorage<Object>() {
      public Object getData() {
         return new Object();
      }

      public void save() {
         Gnetum.config.save();
      }
   };

   public GnetumEmbeddiumPage() {
      super(OptionIdentifier.create("gnetum", "basic"), Component.literal("Gnetum"), groups());
   }

   private static ImmutableList<OptionGroup> groups() {
      List<OptionGroup> groups = new ArrayList<>();
      Builder general = OptionGroup.createBuilder().setId(ResourceLocation.fromNamespaceAndPath("gnetum", "general"));
      OptionImpl<?, Boolean> enabledOption = OptionImpl.createBuilder(boolean.class, STORAGE)
         .setId(ResourceLocation.fromNamespaceAndPath("gnetum", "enabled"))
         .setName(Component.translatable("gnetum.config.enabled"))
         .setTooltip(Component.translatable("gnetum.config.enabled.tooltip"))
         .setImpact(OptionImpact.HIGH)
         .setControl(TickBoxControl::new)
         .setBinding((opts, value) -> {
            if (Gnetum.config.enabled.get() != value) {
               Gnetum.config.enabled.next();
            }
         }, opts -> Gnetum.config.enabled.get())
         .build();
      general.add(enabledOption);
      general.add(
         OptionImpl.createBuilder(boolean.class, STORAGE)
            .setId(ResourceLocation.fromNamespaceAndPath("gnetum", "show_fps"))
            .setName(Component.translatable("gnetum.config.showFps"))
            .setTooltip(Component.translatable("gnetum.config.showFps.tooltip"))
            .setEnabledPredicate(enabledOption::getValue)
            .setControl(TickBoxControl::new)
            .setBinding((opts, value) -> {
               if (Gnetum.config.showHudFps.get() != value) {
                  Gnetum.config.showHudFps.next();
               }
            }, opts -> Gnetum.config.showHudFps.get())
            .build()
      );
      general.add(
         OptionImpl.createBuilder(boolean.class, STORAGE)
            .setId(ResourceLocation.fromNamespaceAndPath("gnetum", "downscale"))
            .setName(Component.translatable("gnetum.config.downscale"))
            .setTooltip(Component.translatable("gnetum.config.downscale.tooltip"))
            .setEnabledPredicate(enabledOption::getValue)
            .setImpact(OptionImpact.LOW)
            .setControl(TickBoxControl::new)
            .setBinding((opts, value) -> {
               if (Gnetum.config.downscale.get() != value) {
                  Gnetum.config.downscale.next();
               }
            }, opts -> Gnetum.config.downscale.get())
            .build()
      );
      general.add(
         OptionImpl.createBuilder(int.class, STORAGE)
            .setId(ResourceLocation.fromNamespaceAndPath("gnetum", "number_of_passes"))
            .setName(Component.translatable("gnetum.config.numberOfPasses"))
            .setTooltip(Component.translatable("gnetum.config.numberOfPasses.tooltip"))
            .setEnabledPredicate(enabledOption::getValue)
            .setImpact(OptionImpact.MEDIUM)
            .setControl(option -> new SliderControl(option, 2, 10, 1, ControlValueFormatter.number()))
            .setBinding((opts, value) -> Gnetum.config.numberOfPasses = value, opts -> Gnetum.config.numberOfPasses)
            .build()
      );
      general.add(
         OptionImpl.createBuilder(int.class, STORAGE)
            .setId(ResourceLocation.fromNamespaceAndPath("gnetum", "max_fps"))
            .setName(Component.translatable("gnetum.config.maxFps"))
            .setTooltip(Component.translatable("gnetum.config.maxFps.tooltip"))
            .setEnabledPredicate(enabledOption::getValue)
            .setImpact(OptionImpact.MEDIUM)
            .setControl(
               option -> new SliderControl(
                  option,
                  5,
                  125,
                  5,
                  i -> i == 125 ? Component.translatable("options.framerateLimit.max") : Component.translatable("options.framerate", new Object[]{i})
               )
            )
            .setBinding((opts, value) -> Gnetum.config.setMaxFps(value), opts -> Gnetum.config.getRawMaxFps())
            .build()
      );
      general.add(
         OptionImpl.createBuilder(int.class, STORAGE)
            .setId(ResourceLocation.fromNamespaceAndPath("gnetum", "screen_max_fps"))
            .setName(Component.translatable("gnetum.config.screenMaxFps"))
            .setTooltip(Component.translatable("gnetum.config.screenMaxFps.tooltip"))
            .setEnabledPredicate(enabledOption::getValue)
            .setImpact(OptionImpact.MEDIUM)
            .setControl(
               option -> new SliderControl(
                  option,
                  5,
                  65,
                  5,
                  i -> i == 65 ? Component.translatable("options.framerateLimit.max") : Component.translatable("options.framerate", new Object[]{i})
               )
            )
            .setBinding((opts, value) -> Gnetum.config.screenMaxFps = value, opts -> Gnetum.config.screenMaxFps)
            .build()
      );
      groups.add(general.build());
      return ImmutableList.copyOf(groups);
   }
}
