package com.chaosthedude.naturescompass.client;

public enum OverlaySide {
   LEFT,
   RIGHT;

   public static OverlaySide fromString(String str) {
      return str.equals("RIGHT") ? RIGHT : LEFT;
   }
}
