package org.openzen.zenscript.parser;

import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.ModuleSpace;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.logger.ParserLogger;

public interface ModuleLoader {
   SemanticModule loadModule(
      ModuleSpace var1, String var2, BracketExpressionParser var3, SemanticModule[] var4, FunctionParameter[] var5, ZSPackage var6, ParserLogger var7
   ) throws ParseException;
}
