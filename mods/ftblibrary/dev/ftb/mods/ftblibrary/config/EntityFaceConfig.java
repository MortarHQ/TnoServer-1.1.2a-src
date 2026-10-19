package dev.ftb.mods.ftblibrary.config;

import dev.ftb.mods.ftblibrary.config.ui.resource.EntityFaceResource;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectEntityFaceScreen;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectableResource;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.OptionalLong;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

public class EntityFaceConfig extends ResourceConfigValue<EntityType<?>> {
   public static final EntityType<?> NONE = EntityType.AREA_EFFECT_CLOUD;

   public EntityFaceConfig() {
      this.value = NONE;
   }

   @Override
   public OptionalLong fixedResourceSize() {
      return OptionalLong.of(1L);
   }

   @Override
   public boolean isEmpty() {
      return this.value == null || this.value == NONE;
   }

   @Override
   public SelectableResource<EntityType<?>> getResource() {
      return new EntityFaceResource(this.getValue());
   }

   @Override
   public boolean setResource(SelectableResource<EntityType<?>> selectable) {
      return this.setCurrentValue(selectable.resource());
   }

   @Override
   public void onClicked(Widget clickedWidget, MouseButton button, ConfigCallback callback) {
      new SelectEntityFaceScreen(this, callback).withGridSize(8, 12).openGui();
   }

   @Override
   public void addInfo(TooltipList list) {
      if (this.value != null && !this.value.equals(this.defaultValue)) {
         list.add(
            Component.translatable("config.group.value")
               .append(": ")
               .withStyle(ChatFormatting.AQUA)
               .append(this.getValue().getDescription().copy().withStyle(ChatFormatting.WHITE))
         );
      }

      super.addInfo(list);
   }

   public Component getStringForGUI(@Nullable EntityType<?> v) {
      return (Component)(v != null && v != NONE ? v.getDescription() : Component.translatable("gui.none").withStyle(ChatFormatting.GRAY));
   }
}
