package net.blay09.mods.trashslot.api;

public interface ISimpleGuiContainerLayout {
   ISimpleGuiContainerLayout addCollisionArea(int var1, int var2, int var3, int var4);

   ISimpleGuiContainerLayout addVerticalSnap(int var1);

   ISimpleGuiContainerLayout addHorizontalSnap(int var1);

   ISimpleGuiContainerLayout addSnappingPoint(int var1, int var2);

   ISimpleGuiContainerLayout enableDefaultCollision();

   ISimpleGuiContainerLayout enableDefaultSnaps();

   ISimpleGuiContainerLayout setEnabledByDefault();
}
