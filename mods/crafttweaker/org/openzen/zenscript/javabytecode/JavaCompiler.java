package org.openzen.zenscript.javabytecode;

import java.io.File;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zencode.shared.logging.IZSLogger;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.ScriptBlock;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.codemodel.definition.ExpansionDefinition;
import org.openzen.zenscript.codemodel.definition.FunctionDefinition;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.javabytecode.compiler.JavaClassWriter;
import org.openzen.zenscript.javabytecode.compiler.JavaScriptFile;
import org.openzen.zenscript.javabytecode.compiler.JavaStatementVisitor;
import org.openzen.zenscript.javabytecode.compiler.JavaWriter;
import org.openzen.zenscript.javabytecode.compiler.definitions.JavaDefinitionVisitor;
import org.openzen.zenscript.javashared.JavaClass;
import org.openzen.zenscript.javashared.JavaCompileSpace;
import org.openzen.zenscript.javashared.JavaEnumMapper;
import org.openzen.zenscript.javashared.JavaMethod;
import org.openzen.zenscript.javashared.JavaParameterInfo;
import org.openzen.zenscript.javashared.prepare.JavaPrepareDefinitionMemberVisitor;
import org.openzen.zenscript.javashared.prepare.JavaPrepareDefinitionVisitor;

public class JavaCompiler {
   private final IZSLogger logger;
   private int generatedScriptBlockCounter = 0;
   private int expansionCounter = 0;

   public JavaCompiler(IZSLogger logger) {
      this.logger = logger;
   }

   public JavaBytecodeModule compile(String packageName, SemanticModule module, JavaCompileSpace space, JavaEnumMapper enumMapper) {
      Map<String, JavaScriptFile> scriptBlocks = new LinkedHashMap<>();
      module.scripts.sort(Comparator.<ScriptBlock>comparingInt(a -> a.file.getOrder()).reversed());
      module.scripts.forEach(script -> {
         String className = this.getClassName(script.file == null ? null : script.file.getFilename());
         this.getScriptFile(scriptBlocks, script.pkg.fullName + "/" + className);
      });
      Set<JavaScriptFile> scriptFilesThatAreActuallyUsedInScripts = new HashSet<>();
      JavaBytecodeModule target = new JavaBytecodeModule(module.module, module.parameters, this.logger);
      target.getEnumMapper().merge(enumMapper);
      JavaBytecodeContext context = new JavaBytecodeContext(target, space, module.modulePackage, packageName, this.logger);
      context.addModule(module.module, target);

      for (HighLevelDefinition definition : module.definitions.getAll()) {
         String className = this.getClassName(this.getFilename(definition));
         String filename;
         if (definition instanceof FunctionDefinition) {
            filename = className;
         } else {
            filename = className + "_" + (definition.name == null ? "generated" : definition.name) + "_" + this.expansionCounter++;
         }

         JavaPrepareDefinitionVisitor definitionPreparer = new JavaPrepareDefinitionVisitor(context, target, filename, null, filename);
         definition.accept(definitionPreparer);
      }

      for (HighLevelDefinition definition : module.definitions.getAll()) {
         JavaPrepareDefinitionMemberVisitor memberPreparer = new JavaPrepareDefinitionMemberVisitor(context, target);
         definition.accept(memberPreparer);
      }

      for (HighLevelDefinition definition : module.definitions.getAll()) {
         String internalName;
         JavaScriptFile scriptFile;
         if (definition instanceof FunctionDefinition) {
            internalName = this.getClassName(this.getFilename(definition));
            scriptFile = this.getScriptFile(scriptBlocks, module.modulePackage.fullName + "/" + internalName);
            scriptFilesThatAreActuallyUsedInScripts.add(scriptFile);
         } else {
            JavaClass cls = definition instanceof ExpansionDefinition ? context.getJavaExpansionClass(definition) : context.getJavaClass(definition);
            scriptFile = this.getScriptFile(scriptBlocks, cls.fullName);
            internalName = cls.internalName;
         }

         scriptFile.classWriter.visitSource(definition.position.getFilename(), null);
         target.addClass(internalName, definition.accept(new JavaDefinitionVisitor(context, scriptFile.classWriter)));
      }

      FunctionHeader scriptHeader = new FunctionHeader(BasicTypeID.VOID, module.parameters);
      String scriptDescriptor = context.getMethodDescriptor(scriptHeader);
      JavaParameterInfo[] javaScriptParameters = new JavaParameterInfo[module.parameters.length];

      for (int i = 0; i < module.parameters.length; i++) {
         FunctionParameter parameter = module.parameters[i];
         JavaParameterInfo javaParameter = new JavaParameterInfo(i, context.getDescriptor(parameter.type));
         target.setParameterInfo(parameter, javaParameter);
         javaScriptParameters[i] = javaParameter;
      }

      for (ScriptBlock script : module.scripts) {
         SourceFile sourceFile = script.file;
         String className = this.getClassName(sourceFile == null ? null : sourceFile.getFilename());
         JavaScriptFile scriptFile = this.getScriptFile(scriptBlocks, script.pkg.fullName + "/" + className);
         scriptFilesThatAreActuallyUsedInScripts.add(scriptFile);
         if (sourceFile != null) {
            scriptFile.classWriter.visitSource(sourceFile.getFilename(), null);
         }

         String methodName = scriptFile.scriptMethods.isEmpty() ? "run" : "run" + scriptFile.scriptMethods.size();
         JavaClassWriter visitor = scriptFile.classWriter;
         JavaMethod method = JavaMethod.getStatic(
            new JavaClass(context.getPackageName(script.pkg), className, JavaClass.Kind.CLASS), methodName, scriptDescriptor, 9
         );
         scriptFile.scriptMethods.add(new JavaScriptMethod(method, module.parameters, javaScriptParameters));
         JavaStatementVisitor statementVisitor = new JavaStatementVisitor(
            context, context.getJavaModule(script.module), new JavaWriter(this.logger, CodePosition.UNKNOWN, visitor, method, null, null, null)
         );
         statementVisitor.start();

         for (Statement statement : script.statements) {
            statement.accept(statementVisitor);
         }

         statementVisitor.end();
      }

      for (Entry<String, JavaScriptFile> entry : scriptBlocks.entrySet()) {
         for (JavaScriptMethod method : entry.getValue().scriptMethods) {
            target.addScript(method);
         }

         entry.getValue().classWriter.visitEnd();
         if (scriptFilesThatAreActuallyUsedInScripts.contains(entry.getValue())) {
            target.addClass(entry.getKey(), entry.getValue().classWriter.toByteArray());
         }
      }

      return target;
   }

   private String getFilename(HighLevelDefinition definition) {
      SourceFile source = definition.position.file;
      if (source != null) {
         return source.getFilename();
      } else {
         return definition.name == null ? "Expansion" : definition.name;
      }
   }

   private String getClassName(String filename) {
      if (filename == null) {
         return "generatedBlock" + this.generatedScriptBlockCounter++;
      }

      String specialCharRegex = Stream.of('/', '\\', '.', ';')
         .filter(character -> character != File.separatorChar)
         .map(String::valueOf)
         .collect(Collectors.joining("", "[", "]"));
      return filename.substring(0, filename.lastIndexOf(46)).replaceAll(specialCharRegex, "_").replace('[', '_').replace(File.separatorChar, '/');
   }

   private JavaScriptFile getScriptFile(Map<String, JavaScriptFile> scriptBlocks, String className) {
      if (!scriptBlocks.containsKey(className)) {
         JavaClassWriter scriptFileWriter = new JavaClassWriter(2);
         scriptFileWriter.visit(52, 1, className, null, "java/lang/Object", null);
         scriptBlocks.put(className, new JavaScriptFile(scriptFileWriter));
      }

      return scriptBlocks.get(className);
   }
}
