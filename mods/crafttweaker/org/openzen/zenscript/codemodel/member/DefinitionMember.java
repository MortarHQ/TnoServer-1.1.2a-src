package org.openzen.zenscript.codemodel.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.Taggable;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.annotations.MemberAnnotation;

public abstract class DefinitionMember extends Taggable implements IDefinitionMember {
   public final CodePosition position;
   public final HighLevelDefinition definition;
   protected final int modifiers;
   public MemberAnnotation[] annotations = MemberAnnotation.NONE;

   public DefinitionMember(CodePosition position, HighLevelDefinition definition, int modifiers) {
      this.position = position;
      this.definition = definition;
      this.modifiers = modifiers;
   }

   @Override
   public final CodePosition getPosition() {
      return this.position;
   }

   @Override
   public int getSpecifiedModifiers() {
      return this.modifiers;
   }

   @Override
   public final HighLevelDefinition getDefinition() {
      return this.definition;
   }

   @Override
   public MemberAnnotation[] getAnnotations() {
      return this.annotations;
   }

   @Override
   public String toString() {
      return this.describe();
   }

   public boolean isStatic() {
      return Modifiers.isStatic(this.getEffectiveModifiers());
   }

   public boolean isFinal() {
      return Modifiers.isFinal(this.getEffectiveModifiers());
   }

   public boolean isExtern() {
      return Modifiers.isExtern(this.getEffectiveModifiers());
   }

   public boolean isPrivate() {
      return Modifiers.isPrivate(this.getEffectiveModifiers());
   }

   public boolean isPublic() {
      return Modifiers.isPublic(this.getEffectiveModifiers());
   }

   public boolean isProtected() {
      return Modifiers.isProtected(this.getEffectiveModifiers());
   }
}
