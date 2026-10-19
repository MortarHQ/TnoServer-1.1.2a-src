package it.hurts.octostudios.octolib.client.screen.widget;

import it.hurts.octostudios.octolib.mixin.AbstractWidgetAccessor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

public interface Child<T extends LayoutElement> extends LayoutElement, GuiEventListener {
   @Nullable
   T getParent();

   void setParent(@Nullable T var1);

   default int getLocalX() {
      return this instanceof AbstractWidget widget ? ((AbstractWidgetAccessor)widget).getLocalX() : 0;
   }

   default int getLocalY() {
      return this instanceof AbstractWidget widget ? ((AbstractWidgetAccessor)widget).getLocalY() : 0;
   }

   default Vector2i getLocalPosition() {
      return new Vector2i(this.getLocalX(), this.getLocalY());
   }

   default Vector2i getParentPosition() {
      T parent = this.getParent();
      return parent != null ? new Vector2i(parent.getX(), parent.getY()) : new Vector2i(0, 0);
   }

   default Vector2i getPosition() {
      Vector2i parentPos = this.getParentPosition();
      return new Vector2i(this.getLocalX() + parentPos.x, this.getLocalY() + parentPos.y);
   }

   @NotNull
   default ScreenRectangle getRectangle() {
      return super.getRectangle();
   }

   default void detachWidget() {
      Vector2i globalPos = this.getPosition();
      this.setParent(null);
      this.setPosition(globalPos.x, globalPos.y);
   }

   default void attachWidget(@Nullable T parent) {
      Vector2i globalPos = this.getPosition();
      this.setParent(parent);
      Vector2i parentPos = this.getParentPosition();
      this.setPosition(globalPos.x - parentPos.x, globalPos.y - parentPos.y);
   }
}
