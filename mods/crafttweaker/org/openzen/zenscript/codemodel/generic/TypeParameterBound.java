package org.openzen.zenscript.codemodel.generic;

import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public interface TypeParameterBound {
   boolean isObjectType();

   <T> T accept(GenericParameterBoundVisitor<T> var1);

   <C, R> R accept(C var1, GenericParameterBoundVisitorWithContext<C, R> var2);

   void registerMembers(LocalMemberCache var1, TypeMembers var2);

   boolean matches(LocalMemberCache var1, TypeID var2);

   TypeParameterBound instance(GenericMapper var1);

   String getCanonical();
}
