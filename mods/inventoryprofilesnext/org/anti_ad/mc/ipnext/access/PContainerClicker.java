package org.anti_ad.mc.ipnext.access;

import java.util.Map;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipn.api.access.IContainerClicker;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.jetbrains.annotations.NotNull;

public final class PContainerClicker implements IContainerClicker {
   @Override
   public final void leftClick(int var1) {
      IPNImpl.Companion.addTickAction(PContainerClicker::leftClick$lambda$0);
   }

   @Override
   public final void rightClick(int var1) {
      IPNImpl.Companion.addTickAction(PContainerClicker::rightClick$lambda$1);
   }

   @Override
   public final void shiftClick(int var1) {
      IPNImpl.Companion.addTickAction(PContainerClicker::shiftClick$lambda$2);
   }

   @Override
   public final void qClick(int var1) {
      IPNImpl.Companion.addTickAction(PContainerClicker::qClick$lambda$3);
   }

   @Override
   public final void click(int var1, int var2) {
      IPNImpl.Companion.addTickAction(PContainerClicker::click$lambda$4);
   }

   @Override
   public final void swap(int var1, int var2) {
      IPNImpl.Companion.addTickAction(PContainerClicker::swap$lambda$5);
   }

   @Override
   public final void executeQClicks(@NotNull Map var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      IPNImpl.Companion.addTickAction(PContainerClicker::executeQClicks$lambda$6);
   }

   @Override
   public final void executeSwapClicks(@NotNull Map var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      IPNImpl.Companion.addTickAction(PContainerClicker::executeSwapClicks$lambda$7);
   }

   @Override
   public final void executeClicks(@NotNull Map var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      IPNImpl.Companion.addTickAction(PContainerClicker::executeClicks$lambda$8);
   }

   private static final Unit leftClick$lambda$0(int var0) {
      ContainerClicker.INSTANCE.leftClick(var0 < 9 ? var0 + 36 : var0);
      return Unit.INSTANCE;
   }

   private static final Unit rightClick$lambda$1(int var0) {
      ContainerClicker.INSTANCE.rightClick(var0 < 9 ? var0 + 36 : var0);
      return Unit.INSTANCE;
   }

   private static final Unit shiftClick$lambda$2(int var0) {
      ContainerClicker.INSTANCE.shiftClick(var0 < 9 ? var0 + 36 : var0);
      return Unit.INSTANCE;
   }

   private static final Unit qClick$lambda$3(int var0) {
      ContainerClicker.INSTANCE.qClick(var0 < 9 ? var0 + 36 : var0);
      return Unit.INSTANCE;
   }

   private static final Unit click$lambda$4(int var0, int var1) {
      ContainerClicker.INSTANCE.click(var0 < 9 ? var0 + 36 : var0, var1);
      return Unit.INSTANCE;
   }

   private static final Unit swap$lambda$5(int var0, int var1) {
      IPNImplKt.access$swapSlots(var0, var1);
      return Unit.INSTANCE;
   }

   private static final Unit executeQClicks$lambda$6(Map var0) {
      ContainerClicker.INSTANCE.executeQClicks(IPNImplKt.access$translateMapValueToSlot(var0), IPNImplKt.access$getInterval());
      return Unit.INSTANCE;
   }

   private static final Unit executeSwapClicks$lambda$7(Map var0) {
      ContainerClicker.INSTANCE.executeSwapClicks(IPNImplKt.access$translateMap(var0), IPNImplKt.access$getInterval());
      return Unit.INSTANCE;
   }

   private static final Unit executeClicks$lambda$8(Map var0) {
      ContainerClicker.INSTANCE.executeClicks(MapsKt.toList(var0), IPNImplKt.access$getInterval());
      return Unit.INSTANCE;
   }
}
