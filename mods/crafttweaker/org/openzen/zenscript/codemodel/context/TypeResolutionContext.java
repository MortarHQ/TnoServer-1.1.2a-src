package org.openzen.zenscript.codemodel.context;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;

public interface TypeResolutionContext {
   ZSPackage getRootPackage();

   GlobalTypeRegistry getTypeRegistry();

   AnnotationDefinition getAnnotation(String var1);

   TypeID getType(CodePosition var1, List<GenericName> var2);

   TypeID getThisType();
}
