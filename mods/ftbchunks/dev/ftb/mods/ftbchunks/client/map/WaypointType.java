package dev.ftb.mods.ftbchunks.client.map;

import dev.ftb.mods.ftblibrary.icon.Icon;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WaypointType {
   private static final Map<String, WaypointType> TYPES = new ConcurrentHashMap<>();
   public static final WaypointType DEFAULT = builder().withOutsideIcon("ftbchunks:textures/waypoint_default_outside.png").canChangeColor().build("default");
   public static final WaypointType DEATH = builder().build("death");
   private final String id;
   private final Icon icon;
   private final Icon outsideIcon;
   private final boolean canChangeColor;

   public static WaypointType.Builder builder() {
      return new WaypointType.Builder();
   }

   public static WaypointType forId(String typeName) {
      return TYPES.getOrDefault(typeName, DEFAULT);
   }

   private WaypointType(String id, WaypointType.Builder builder) {
      this.id = id;
      this.icon = Icon.getIcon("ftbchunks:textures/waypoint_" + this.id + ".png");
      this.outsideIcon = builder.outsideIcon == null ? this.icon : builder.outsideIcon;
      this.canChangeColor = builder.canChangeColor;
   }

   public String getId() {
      return this.id;
   }

   public Icon getOutsideIcon() {
      return this.outsideIcon;
   }

   public boolean canChangeColor() {
      return this.canChangeColor;
   }

   public Icon getIcon() {
      return this.icon;
   }

   public static class Builder {
      private Icon outsideIcon = null;
      private boolean canChangeColor = false;

      public WaypointType.Builder withOutsideIcon(String icon) {
         this.outsideIcon = Icon.getIcon(icon);
         return this;
      }

      public WaypointType.Builder withOutsideIcon(Icon icon) {
         this.outsideIcon = icon;
         return this;
      }

      public WaypointType.Builder canChangeColor() {
         this.canChangeColor = true;
         return this;
      }

      public WaypointType build(String id) {
         WaypointType type = new WaypointType(id, this);
         WaypointType.TYPES.put(id, type);
         return type;
      }
   }
}
