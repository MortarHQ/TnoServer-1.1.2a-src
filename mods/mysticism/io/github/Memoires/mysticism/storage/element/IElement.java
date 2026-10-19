package io.github.Memoires.mysticism.storage.element;

import io.github.manasmods.tensura.ability.magic.Element;

public interface IElement {
   Element getElement();

   void setElement(Element var1);

   void markDirty();
}
