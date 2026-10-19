package de.keksuccino.konkrete.input;

import java.util.function.Consumer;

@Deprecated(forRemoval = true)
public class KeyboardHandler {
   private static boolean ctrlPressed = false;
   private static boolean altPressed = false;
   private static boolean keyPressed = false;
   private static int keycode = 0;
   private static int scancode = 0;
   private static char typedChar = ' ';
   private static int charModifiers = 0;
   private static int modifiers = 0;

   @Deprecated
   public static boolean isKeyPressed() {
      return keyPressed;
   }

   @Deprecated
   public static int getCurrentKeyCode() {
      return keycode;
   }

   @Deprecated
   public static int getCurrentKeyScanCode() {
      return scancode;
   }

   @Deprecated
   public static int getCurrentKeyModifiers() {
      return modifiers;
   }

   @Deprecated
   public static char getCurrentChar() {
      return typedChar;
   }

   @Deprecated
   public static int getCurrentCharModifiers() {
      return charModifiers;
   }

   @Deprecated
   public static int addKeyPressedListener(Consumer<KeyboardData> c) {
      return 0;
   }

   @Deprecated
   public static int addKeyReleasedListener(Consumer<KeyboardData> c) {
      return 0;
   }

   @Deprecated
   public static int addCharTypedListener(Consumer<CharData> c) {
      return 0;
   }

   @Deprecated
   public static void removeKeyPressedListener(int id) {
   }

   @Deprecated
   public static void removeKeyReleasedListener(int id) {
   }

   @Deprecated
   public static void removeCharTypedListener(int id) {
   }

   @Deprecated
   public static boolean isCtrlPressed() {
      return ctrlPressed;
   }

   @Deprecated
   public static boolean isAltPressed() {
      return altPressed;
   }
}
