package org.openzen.zenscript.codemodel.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.Tag;
import org.openzen.zenscript.codemodel.AccessScope;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.MemberAnnotation;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPriority;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public interface IDefinitionMember {
   CodePosition getPosition();

   int getSpecifiedModifiers();

   int getEffectiveModifiers();

   MemberAnnotation[] getAnnotations();

   HighLevelDefinition getDefinition();

   String describe();

   BuiltinID getBuiltin();

   void registerTo(TypeMembers var1, TypeMemberPriority var2, GenericMapper var3);

   <T> T accept(MemberVisitor<T> var1);

   <C, R> R accept(C var1, MemberVisitorWithContext<C, R> var2);

   <T extends Tag> T getTag(Class<T> var1);

   <T extends Tag> void setTag(Class<T> var1, T var2);

   <T extends Tag> boolean hasTag(Class<T> var1);

   DefinitionMemberRef getOverrides();

   void normalize(TypeScope var1);

   boolean isAbstract();

   DefinitionMemberRef ref(TypeID var1, GenericMapper var2);

   FunctionHeader getHeader();

   default AccessScope getAccessScope() {
      return this.getDefinition().getAccessScope();
   }
}
