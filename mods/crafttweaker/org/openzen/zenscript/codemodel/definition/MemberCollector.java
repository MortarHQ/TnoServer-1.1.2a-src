package org.openzen.zenscript.codemodel.definition;

import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;

public interface MemberCollector {
   void member(IDefinitionMember var1);

   void enumConstant(EnumConstantMember var1);

   void variantOption(VariantDefinition.Option var1);
}
