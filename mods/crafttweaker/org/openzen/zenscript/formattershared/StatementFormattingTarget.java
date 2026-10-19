package org.openzen.zenscript.formattershared;

import java.util.List;
import org.openzen.zenscript.codemodel.statement.BlockStatement;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.statement.Statement;

public interface StatementFormattingTarget {
   LoopStatement getInnerLoop();

   String getIndent();

   void writeLine(String var1);

   void writeInner(String var1, Statement var2, LoopStatement var3, String var4);

   void writeInner(String var1, String[] var2, Statement var3, LoopStatement var4, String var5);

   void writeInnerMulti(String var1, List<StatementFormattingSubBlock> var2, LoopStatement var3, String var4);

   void writeBlock(String var1, BlockStatement var2, String var3);
}
