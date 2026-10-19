package com.blamejared.crafttweaker.gametest;

import com.blamejared.crafttweaker.api.util.ClassUtil;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.gametest.util.CraftTweakerGameTester;
import com.blamejared.crafttweaker.gametest.util.ICraftTweakerGameTester;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.TestFunction;

public class CraftTweakerGameTests {
   @GameTestGenerator
   public static List<TestFunction> getTests() {
      List<TestFunction> functions = new ArrayList<>();
      ClassUtil.findClassesWithAnnotation(CraftTweakerGameTester.class).forEach(aClass -> {
         if (ICraftTweakerGameTester.class.isAssignableFrom((Class<?>)aClass)) {
            ICraftTweakerGameTester runner = InstantiationUtil.getOrCreateInstance((Class<ICraftTweakerGameTester>)aClass);
            if (runner != null) {
               functions.addAll(runner.collectTests());
            } else {
               throw new IllegalStateException("Error while instantiating ICraftTweakerGameTester: " + aClass);
            }
         } else {
            throw new IllegalStateException("Classes annotation with @CraftTweakerGameTester need to implement ICraftTweakerGameTester!");
         }
      });
      return functions;
   }
}
