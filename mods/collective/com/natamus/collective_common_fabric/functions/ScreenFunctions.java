package com.natamus.collective_common_fabric.functions;

import net.minecraft.class_2561;
import net.minecraft.class_2625;
import net.minecraft.class_364;
import net.minecraft.class_3728;
import net.minecraft.class_4068;
import net.minecraft.class_437;
import net.minecraft.class_492;
import net.minecraft.class_6379;
import net.minecraft.class_7743;
import net.minecraft.class_8242;

public class ScreenFunctions {
   public static void setScreenTitle(class_437 screen, class_2561 titleComponent) {
      screen.field_22785 = titleComponent;
   }

   public static void setMerchantScreenTitle(class_492 merchantScreen, class_2561 titleComponent) {
      merchantScreen.field_22785 = titleComponent;
   }

   public static void signSetMessage(class_7743 abstractSignEditScreen, String newMessage, int newLine, boolean setLine) {
      if (setLine) {
         abstractSignEditScreen.field_40428 = newLine;
      }

      abstractSignEditScreen.field_40425[newLine] = newMessage;
      abstractSignEditScreen.field_43362 = abstractSignEditScreen.field_43362.method_49857(newLine, class_2561.method_43470(newMessage));
      abstractSignEditScreen.field_40424.method_49840(abstractSignEditScreen.field_43362, abstractSignEditScreen.field_43363);
   }

   public static class_2625 getSignBlockEntityFromScreen(class_7743 abstractSignEditScreen) {
      return abstractSignEditScreen.field_40424;
   }

   public static class_8242 getSignTextFromScreen(class_7743 abstractSignEditScreen) {
      return abstractSignEditScreen.field_43362;
   }

   public static void setSignTextOnScreen(class_7743 abstractSignEditScreen, String message, int line) {
      abstractSignEditScreen.field_43362.method_49857(line, class_2561.method_43470(message));
   }

   public static void setSignTextOnScreen(class_7743 abstractSignEditScreen, class_2561 message, int line) {
      abstractSignEditScreen.field_43362.method_49857(line, message);
   }

   public static void replaceSignTextOnScreen(class_7743 abstractSignEditScreen, class_8242 signText) {
      abstractSignEditScreen.field_43362 = signText;
   }

   public static String[] getSignMessagesFromScreen(class_7743 abstractSignEditScreen) {
      return abstractSignEditScreen.field_40425;
   }

   public static void setSignMessagesOnScreen(class_7743 abstractSignEditScreen, String[] newMessages) {
      int i = 0;

      for (String message : newMessages) {
         abstractSignEditScreen.field_40425[i] = message;
         i++;
      }
   }

   public static void setSignMessageAtLineOnScreen(class_7743 abstractSignEditScreen, String newMessage, int line) {
      abstractSignEditScreen.field_40425[line] = newMessage;
   }

   public static int getSignLineFromScreen(class_7743 abstractSignEditScreen) {
      return abstractSignEditScreen.field_40428;
   }

   public static void setSignLineOnScreen(class_7743 abstractSignEditScreen, int newLine) {
      abstractSignEditScreen.field_40428 = newLine;
   }

   public static class_3728 getSignFieldFromScreen(class_7743 abstractSignEditScreen) {
      return abstractSignEditScreen.field_40429;
   }

   public static boolean signTextOnScreenisFront(class_7743 abstractSignEditScreen) {
      return abstractSignEditScreen.field_43363;
   }

   public static <T extends class_364 & class_4068 & class_6379> T addRenderableWidget(class_437 screen, T renderableWidget) {
      return (T)screen.method_37063(renderableWidget);
   }
}
