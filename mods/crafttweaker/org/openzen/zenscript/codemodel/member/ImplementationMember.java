package org.openzen.zenscript.codemodel.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.member.ref.ImplementationMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPriority;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class ImplementationMember extends DefinitionMember {
   public final TypeID type;
   public final List<IDefinitionMember> members = new ArrayList<>();
   public final Map<DefinitionMemberRef, IDefinitionMember> definitionBorrowedMembers = new HashMap<>();

   public ImplementationMember(CodePosition position, HighLevelDefinition definition, int modifiers, TypeID type) {
      super(position, definition, modifiers);
      this.type = type;
   }

   public void addMember(IDefinitionMember member) {
      this.members.add(member);
   }

   @Override
   public void registerTo(TypeMembers members, TypeMemberPriority priority, GenericMapper mapper) {
      TypeID instancedType = mapper == null ? this.type : mapper.map(this.type);
      members.addImplementation(new ImplementationMemberRef(this, members.type, instancedType), priority);
      TypeMembers interfaceTypeMembers = members.getMemberCache().get(instancedType);
      interfaceTypeMembers.copyMembersTo(members, TypeMemberPriority.INTERFACE);
   }

   @Override
   public String describe() {
      return "implementation of " + this.type.toString();
   }

   @Override
   public BuiltinID getBuiltin() {
      return null;
   }

   @Override
   public <T> T accept(MemberVisitor<T> visitor) {
      return visitor.visitImplementation(this);
   }

   @Override
   public <C, R> R accept(C context, MemberVisitorWithContext<C, R> visitor) {
      return visitor.visitImplementation(context, this);
   }

   @Override
   public DefinitionMemberRef getOverrides() {
      return null;
   }

   @Override
   public int getEffectiveModifiers() {
      int result = this.modifiers;
      if (this.definition.isInterface()) {
         result |= 1;
      }

      if (!Modifiers.hasAccess(result)) {
         result |= 1;
      }

      return result;
   }

   @Override
   public void normalize(TypeScope scope) {
      Set<IDefinitionMember> implemented = new HashSet<>();

      for (IDefinitionMember member : this.members) {
         member.normalize(scope);
         if (member.getOverrides() != null) {
            implemented.add(member.getOverrides().getTarget());
         }
      }

      TypeMembers interfaceMembers = scope.getTypeMembers(this.type);
      TypeMembers definitionMembers = scope.getTypeMembers(scope.getTypeRegistry().getForMyDefinition(this.definition));
      this.definitionBorrowedMembers.clear();
      this.definitionBorrowedMembers.putAll(interfaceMembers.borrowInterfaceMembersFromDefinition(implemented, definitionMembers));
   }

   @Override
   public boolean isAbstract() {
      return false;
   }

   @Override
   public DefinitionMemberRef ref(TypeID type, GenericMapper mapper) {
      throw new UnsupportedOperationException("Cannot create an implementation reference");
   }

   @Override
   public FunctionHeader getHeader() {
      return null;
   }
}
