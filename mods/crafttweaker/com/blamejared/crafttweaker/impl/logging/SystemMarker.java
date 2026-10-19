package com.blamejared.crafttweaker.impl.logging;

import org.apache.logging.log4j.Marker;

final class SystemMarker implements Marker {
   private final String name;
   private final Marker marker;

   private SystemMarker(String name, Marker marker) {
      this.name = name;
      this.marker = marker;
   }

   static Marker of(String name, Marker marker) {
      return new SystemMarker(name, marker);
   }

   public Marker addParents(Marker... markers) {
      this.marker.addParents(markers);
      return this;
   }

   public String getName() {
      return this.name;
   }

   public Marker[] getParents() {
      return this.marker.getParents();
   }

   public boolean hasParents() {
      return this.marker.hasParents();
   }

   public boolean isInstanceOf(Marker m) {
      return this.marker.isInstanceOf(m);
   }

   public boolean isInstanceOf(String name) {
      return this.marker.isInstanceOf(name) || this.name.equals(name);
   }

   public boolean remove(Marker marker) {
      return this.marker.remove(marker);
   }

   public Marker setParents(Marker... markers) {
      this.marker.setParents(markers);
      return this;
   }
}
