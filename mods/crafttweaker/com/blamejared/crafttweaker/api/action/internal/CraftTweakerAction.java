package com.blamejared.crafttweaker.api.action.internal;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.platform.Services;

public abstract class CraftTweakerAction implements IAction {
   private static final String ALLOWED_PACKAGE_NAME = Services.PLATFORM.isDevelopmentEnvironment() ? findPackage() : null;

   public CraftTweakerAction() {
      if (ALLOWED_PACKAGE_NAME != null) {
         verify(this.getClass());
      }
   }

   private static String findPackage() {
      Class<?> thisClass = CraftTweakerAction.class;
      Package thisPackage = thisClass.getPackage();
      String thisPackageName = thisPackage.getName();
      return thisPackageName.replace(".internal", "");
   }

   private static void verify(Class<?> clazz) {
      String packageName = clazz.getPackage().getName();
      if (!packageName.startsWith(ALLOWED_PACKAGE_NAME)) {
         throw new AssertionError("Action " + clazz.getName() + " extends CraftTweakerAction but it is not a CraftTweaker action");
      }
   }

   @Override
   public final String systemName() {
      return "CraftTweaker";
   }
}
