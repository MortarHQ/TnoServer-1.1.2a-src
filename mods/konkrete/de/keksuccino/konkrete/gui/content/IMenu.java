package de.keksuccino.konkrete.gui.content;

@Deprecated(forRemoval = true)
public interface IMenu {
   void setUseable(boolean var1);

   boolean isUseable();

   void closeMenu();

   boolean isOpen();
}
