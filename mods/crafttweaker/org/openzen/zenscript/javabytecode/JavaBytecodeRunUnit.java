package org.openzen.zenscript.javabytecode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.objectweb.asm.Type;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.javabytecode.compiler.JavaClassWriter;
import org.openzen.zenscript.javabytecode.compiler.JavaWriter;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaParameterInfo;

public class JavaBytecodeRunUnit {
   private final Map<String, byte[]> classes = new HashMap<>();
   private final List<JavaScriptMethod> scripts = new ArrayList<>();
   private final List<FunctionParameter> scriptParameters = new ArrayList<>();
   private final List<JavaParameterInfo> scriptParameterInfo = new ArrayList<>();
   private final IZSLogger logger;
   private boolean scriptsWritten = false;

   public JavaBytecodeRunUnit(IZSLogger logger) {
      this.logger = logger;
   }

   private static Class<?> loadClass(ClassLoader classLoader, String descriptor) throws ClassNotFoundException {
      switch (descriptor) {
         case "Z":
            return boolean.class;
         case "B":
            return byte.class;
         case "S":
            return short.class;
         case "I":
            return int.class;
         case "J":
            return long.class;
         case "F":
            return float.class;
         case "D":
            return double.class;
         case "C":
            return char.class;
         case "Ljava/lang/Object;":
            return Object.class;
         case "Ljava/lang/String;":
            return String.class;
         case "[Ljava/lang/Object;":
            return Object[].class;
         case "[Ljava/lang/String;":
            return String[].class;
         default:
            return classLoader.loadClass(getClassName(descriptor));
      }
   }

   private static String getClassName(String descriptor) {
      if (descriptor.startsWith("[")) {
         return "[" + getClassName(descriptor.substring(1));
      } else if (descriptor.startsWith("L")) {
         return descriptor.substring(1, descriptor.length() - 1);
      } else {
         throw new IllegalArgumentException("Invalid descriptor: " + descriptor);
      }
   }

   public void add(JavaBytecodeModule module) {
      this.scriptsWritten = false;

      for (Entry<String, byte[]> classEntry : module.getClasses().entrySet()) {
         this.classes.put(classEntry.getKey().replace('/', '.'), classEntry.getValue());
      }

      for (JavaScriptMethod script : module.getScripts()) {
         this.scripts.add(script);

         for (int i = 0; i < script.parameters.length; i++) {
            FunctionParameter parameter = script.parameters[i];
            if (!this.scriptParameters.contains(parameter)) {
               this.scriptParameters.add(parameter);
               this.scriptParameterInfo.add(script.parametersInfo[i]);
            }
         }
      }
   }

   public void run() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      this.run(Collections.emptyMap(), this.getClass().getClassLoader());
   }

   public void run(Map<FunctionParameter, Object> arguments) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      this.run(arguments, this.getClass().getClassLoader());
   }

   public void run(Map<FunctionParameter, Object> arguments, ClassLoader parentClassLoader) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      this.writeScripts();
      JavaBytecodeRunUnit.ScriptClassLoader classLoader = new JavaBytecodeRunUnit.ScriptClassLoader(parentClassLoader);
      Object[] argumentsArray = new Object[this.scriptParameters.size()];

      for (int i = 0; i < this.scriptParameters.size(); i++) {
         FunctionParameter parameter = this.scriptParameters.get(i);
         if (!arguments.containsKey(parameter)) {
            throw new IllegalArgumentException("Missing script argument for parameter " + parameter.name);
         }

         argumentsArray[i] = arguments.get(parameter);
      }

      Class<?>[] classes = new Class[this.scriptParameters.size()];

      for (int i = 0; i < classes.length; i++) {
         classes[i] = loadClass(classLoader, this.scriptParameterInfo.get(i).typeDescriptor);
      }

      classLoader.loadClass("Scripts").getMethod("run", classes).invoke(null, argumentsArray);
   }

   public void dump(File directory) {
      this.writeScripts();
      if (!directory.exists()) {
         directory.mkdirs();
      }

      for (Entry<String, byte[]> classEntry : this.classes.entrySet()) {
         File output = new File(directory, classEntry.getKey().replace('.', File.separatorChar) + ".class");
         if (output.getParentFile().exists() || output.getParentFile().mkdirs()) {
            try (FileOutputStream outputStream = new FileOutputStream(output)) {
               outputStream.write(classEntry.getValue());
            } catch (IOException ex) {
               ex.printStackTrace();
            }
         }
      }
   }

   private int getParameterIndex(FunctionParameter parameter) {
      return this.scriptParameters.indexOf(parameter);
   }

   private void writeScripts() {
      if (!this.scriptsWritten) {
         JavaClassWriter scriptsClassWriter = new JavaClassWriter(2);
         scriptsClassWriter.visit(52, 1, "Scripts", null, "java/lang/Object", null);
         new FunctionHeader(BasicTypeID.VOID, this.scriptParameters.toArray(new FunctionParameter[this.scriptParameters.size()]));
         StringBuilder headerBuilder = new StringBuilder();
         headerBuilder.append('(');

         for (int i = 0; i < this.scriptParameters.size(); i++) {
            headerBuilder.append(this.scriptParameterInfo.get(i).typeDescriptor);
         }

         headerBuilder.append(")V");
         JavaMethod runMethod = JavaMethod.getStatic(new JavaClass("script", "Scripts", JavaClass.Kind.CLASS), "run", headerBuilder.toString(), 9);
         JavaWriter runWriter = new JavaWriter(this.logger, CodePosition.GENERATED, scriptsClassWriter, runMethod, null, null, null);
         runWriter.start();

         for (JavaScriptMethod method : this.scripts) {
            for (int i = 0; i < method.parameters.length; i++) {
               runWriter.load(Type.getType(method.parametersInfo[i].typeDescriptor), this.getParameterIndex(method.parameters[i]));
            }

            runWriter.invokeStatic(method.method);
         }

         runWriter.ret();
         runWriter.end();
         this.classes.put("Scripts", scriptsClassWriter.toByteArray());
         this.scriptsWritten = true;
      }
   }

   public class ScriptClassLoader extends ClassLoader {
      private final Map<String, Class<?>> customClasses = new HashMap<>();

      public ScriptClassLoader(ClassLoader parent) {
         super(parent);
      }

      @Override
      public Class<?> loadClass(String name) throws ClassNotFoundException {
         if (this.customClasses.containsKey(name)) {
            return this.customClasses.get(name);
         } else if (JavaBytecodeRunUnit.this.classes.containsKey(name)) {
            byte[] bytes = JavaBytecodeRunUnit.this.classes.get(name);
            this.customClasses.put(name, this.defineClass(name, bytes, 0, bytes.length, null));
            return this.customClasses.get(name);
         } else {
            return this.getParent().loadClass(name);
         }
      }
   }
}
