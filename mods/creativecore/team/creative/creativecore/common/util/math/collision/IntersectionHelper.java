package team.creative.creativecore.common.util.math.collision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.geo.Ray2d;
import team.creative.creativecore.common.util.math.vec.Vec2d;
import team.creative.creativecore.common.util.math.vec.Vec2f;
import team.creative.creativecore.common.util.math.vec.Vec3f;

public class IntersectionHelper {
   public static List<Vec2d> cutMinMax(double minOne, double minTwo, double maxOne, double maxTwo, Vec2d[] corners) {
      if (corners.length < 3) {
         return Collections.EMPTY_LIST;
      }

      boolean insideOneMin = false;
      boolean insideTwoMin = false;
      boolean insideOneMax = false;
      boolean insideTwoMax = false;
      boolean allInside = true;
      int insideIndex = -1;
      IntersectionHelper.InsideStatus[] status = new IntersectionHelper.InsideStatus[corners.length];

      for (int i = 0; i < corners.length; i++) {
         double valueOne = corners[i].x;
         double valueTwo = corners[i].y;
         status[i] = IntersectionHelper.InsideStatus.get(valueOne, valueTwo, minOne, minTwo, maxOne, maxTwo);
         if (status[i].isInside()) {
            if (insideIndex == -1) {
               insideIndex = i;
            }

            insideTwoMax = true;
            insideTwoMin = true;
            insideOneMax = true;
            insideOneMin = true;
         } else {
            allInside = false;
            if (status[i].isOutsideOne()) {
               if (status[i].outsideDirectionOne()) {
                  insideOneMax = true;
               } else {
                  insideOneMin = true;
               }
            } else {
               insideOneMax = true;
               insideOneMin = true;
            }

            if (status[i].isOutsideTwo()) {
               if (status[i].outsideDirectionTwo()) {
                  insideTwoMax = true;
               } else {
                  insideTwoMin = true;
               }
            } else {
               insideTwoMax = true;
               insideTwoMin = true;
            }
         }
      }

      double originOne = corners[1].x;
      double originTwo = corners[1].y;
      boolean clockwise = (corners[0].x - originOne) * (corners[2].y - originTwo) - (corners[0].y - originTwo) * (corners[2].x - originOne) > 0.0;
      if (allInside) {
         return clockwise
            ? Arrays.asList(new Vec2d(minOne, minTwo), new Vec2d(minOne, maxTwo), new Vec2d(maxOne, maxTwo), new Vec2d(maxOne, minTwo))
            : Arrays.asList(new Vec2d(maxOne, minTwo), new Vec2d(maxOne, maxTwo), new Vec2d(minOne, maxTwo), new Vec2d(minOne, minTwo));
      }

      if (insideOneMin && insideOneMax && insideTwoMin && insideTwoMax) {
         Ray2d ray = new Ray2d(Axis.X, Axis.Y, 0.0, 0.0, 0.0, 0.0);
         List<Vec2d> result = new ArrayList<>();
         boolean hasFoundInside;
         if (!(hasFoundInside = insideIndex != -1)) {
            insideIndex = 0;
         }

         iterateLines(insideIndex, status.length, hasFoundInside, null, ray, minOne, minTwo, maxOne, maxTwo, clockwise, status, corners, result);
         if (result.isEmpty()) {
            boolean aboveOneAboveTwo = false;
            boolean aboveOneBelowTwo = false;
            boolean belowOneAboveTwo = false;
            boolean belowOneBelowTwo = false;

            for (int j = 0; j < corners.length; j++) {
               if (corners[j].x > minOne) {
                  if (corners[j].y > minTwo) {
                     aboveOneAboveTwo = true;
                  } else {
                     aboveOneBelowTwo = true;
                  }
               } else if (corners[j].y > minTwo) {
                  belowOneAboveTwo = true;
               } else {
                  belowOneBelowTwo = true;
               }
            }

            if (aboveOneAboveTwo && aboveOneBelowTwo && belowOneAboveTwo && belowOneBelowTwo) {
               if (clockwise) {
                  return Arrays.asList(new Vec2d(minOne, minTwo), new Vec2d(minOne, maxTwo), new Vec2d(maxOne, maxTwo), new Vec2d(maxOne, minTwo));
               }

               return Arrays.asList(new Vec2d(maxOne, minTwo), new Vec2d(maxOne, maxTwo), new Vec2d(minOne, maxTwo), new Vec2d(minOne, minTwo));
            }
         }

         if (result.size() > 2) {
            return result;
         }
      }

      return Collections.EMPTY_LIST;
   }

   public static List<Vec2f> cutMinMax(Axis one, Axis two, float minOne, float minTwo, float maxOne, float maxTwo, Vec3f[] corners) {
      if (corners.length < 3) {
         return Collections.EMPTY_LIST;
      }

      boolean insideOneMin = false;
      boolean insideTwoMin = false;
      boolean insideOneMax = false;
      boolean insideTwoMax = false;
      boolean allInside = true;
      int insideIndex = -1;
      IntersectionHelper.InsideStatus[] status = new IntersectionHelper.InsideStatus[corners.length];

      for (int i = 0; i < corners.length; i++) {
         float valueOne = corners[i].get(one);
         float valueTwo = corners[i].get(two);
         status[i] = IntersectionHelper.InsideStatus.get(valueOne, valueTwo, minOne, minTwo, maxOne, maxTwo);
         if (status[i].isInside()) {
            if (insideIndex == -1) {
               insideIndex = i;
            }

            insideTwoMax = true;
            insideTwoMin = true;
            insideOneMax = true;
            insideOneMin = true;
         } else {
            allInside = false;
            if (status[i].isOutsideOne()) {
               if (status[i].outsideDirectionOne()) {
                  insideOneMax = true;
               } else {
                  insideOneMin = true;
               }
            } else {
               insideOneMax = true;
               insideOneMin = true;
            }

            if (status[i].isOutsideTwo()) {
               if (status[i].outsideDirectionTwo()) {
                  insideTwoMax = true;
               } else {
                  insideTwoMin = true;
               }
            } else {
               insideTwoMax = true;
               insideTwoMin = true;
            }
         }
      }

      float originOne = corners[1].get(one);
      float originTwo = corners[1].get(two);
      boolean clockwise = (corners[0].get(one) - originOne) * (corners[2].get(two) - originTwo)
            - (corners[0].get(two) - originTwo) * (corners[2].get(one) - originOne)
         > 0.0F;
      if (allInside) {
         return clockwise
            ? Arrays.asList(new Vec2f(minOne, minTwo), new Vec2f(minOne, maxTwo), new Vec2f(maxOne, maxTwo), new Vec2f(maxOne, minTwo))
            : Arrays.asList(new Vec2f(maxOne, minTwo), new Vec2f(maxOne, maxTwo), new Vec2f(minOne, maxTwo), new Vec2f(minOne, minTwo));
      }

      if (insideOneMin && insideOneMax && insideTwoMin && insideTwoMax) {
         Ray2d ray = new Ray2d(one, two, 0.0, 0.0, 0.0, 0.0);
         List<Vec2f> result = new ArrayList<>();
         boolean hasFoundInside;
         if (!(hasFoundInside = insideIndex != -1)) {
            insideIndex = 0;
         }

         iterateLines(insideIndex, status.length, hasFoundInside, null, ray, one, two, minOne, minTwo, maxOne, maxTwo, clockwise, status, corners, result);
         if (result.isEmpty()) {
            boolean aboveOneAboveTwo = false;
            boolean aboveOneBelowTwo = false;
            boolean belowOneAboveTwo = false;
            boolean belowOneBelowTwo = false;

            for (int j = 0; j < corners.length; j++) {
               if (corners[j].get(one) > minOne) {
                  if (corners[j].get(two) > minTwo) {
                     aboveOneAboveTwo = true;
                  } else {
                     aboveOneBelowTwo = true;
                  }
               } else if (corners[j].get(two) > minTwo) {
                  belowOneAboveTwo = true;
               } else {
                  belowOneBelowTwo = true;
               }
            }

            if (aboveOneAboveTwo && aboveOneBelowTwo && belowOneAboveTwo && belowOneBelowTwo) {
               if (clockwise) {
                  return Arrays.asList(new Vec2f(minOne, minTwo), new Vec2f(minOne, maxTwo), new Vec2f(maxOne, maxTwo), new Vec2f(maxOne, minTwo));
               }

               return Arrays.asList(new Vec2f(maxOne, minTwo), new Vec2f(maxOne, maxTwo), new Vec2f(minOne, maxTwo), new Vec2f(minOne, minTwo));
            }
         }

         if (result.size() > 2) {
            return result;
         }
      }

      return Collections.EMPTY_LIST;
   }

   private static IntersectionHelper.Edge iterateLines(
      int offset,
      int count,
      boolean hasFoundInside,
      IntersectionHelper.Edge edge,
      Ray2d ray,
      double minOne,
      double minTwo,
      double maxOne,
      double maxTwo,
      boolean clockwise,
      IntersectionHelper.InsideStatus[] status,
      Vec2d[] corners,
      List<Vec2d> result
   ) {
      double beforeOne = corners[offset].x;
      double beforeTwo = corners[offset].y;
      IntersectionHelper.InsideStatus before = status[offset];
      boolean inside = before.isInside();
      if (inside) {
         add(new Vec2d(beforeOne, beforeTwo), result);
      }

      for (int i = 1; i <= count; i++) {
         int currentIndex = (offset + i) % corners.length;
         Vec2d vec = corners[currentIndex];
         double nowOne = vec.x;
         double nowTwo = vec.y;
         IntersectionHelper.InsideStatus current = status[currentIndex];
         if (inside) {
            if (current.isInside()) {
               add(new Vec2d(nowOne, nowTwo), result);
            } else {
               edge = findIntersection(ray, clockwise, minOne, minTwo, maxOne, maxTwo, current, beforeOne, beforeTwo, nowOne, nowTwo, null, result);
               inside = false;
            }
         } else if (current.isInside()) {
            findIntersection(ray, clockwise, minOne, minTwo, maxOne, maxTwo, before, beforeOne, beforeTwo, nowOne, nowTwo, edge, result);
            add(new Vec2d(nowOne, nowTwo), result);
            edge = null;
            inside = true;
         } else {
            ray.originOne = beforeOne;
            ray.originTwo = beforeTwo;
            ray.directionOne = nowOne - beforeOne;
            ray.directionTwo = nowTwo - beforeTwo;
            IntersectionHelper.Edge inEdge = null;
            float inT = 1.0F;
            IntersectionHelper.Edge outEdge = null;
            float outT = 0.0F;
            if (before.outsideDirectionOne() != current.outsideDirectionOne()) {
               if (BooleanUtils.isFalse(before.outsideDirectionOne()) || BooleanUtils.isFalse(current.outsideDirectionOne())) {
                  float tempT = (float)ray.getT(Axis.X, minOne);
                  float valueTwo = (float)(ray.originTwo + ray.directionTwo * tempT);
                  if (tempT >= 0.0F && tempT <= 1.0F && valueTwo >= minTwo && valueTwo <= maxTwo) {
                     if (tempT < inT) {
                        inEdge = IntersectionHelper.Edge.MIN_ONE;
                        inT = tempT;
                     }

                     if (tempT > outT) {
                        outEdge = IntersectionHelper.Edge.MIN_ONE;
                        outT = tempT;
                     }
                  }
               }

               if (BooleanUtils.isTrue(before.outsideDirectionOne()) || BooleanUtils.isTrue(current.outsideDirectionOne())) {
                  float tempT = (float)ray.getT(Axis.X, maxOne);
                  float valueTwo = (float)(ray.originTwo + ray.directionTwo * tempT);
                  if (tempT >= 0.0F && tempT <= 1.0F && valueTwo >= minTwo && valueTwo <= maxTwo) {
                     if (tempT < inT) {
                        inEdge = IntersectionHelper.Edge.MAX_ONE;
                        inT = tempT;
                     }

                     if (tempT > outT) {
                        outEdge = IntersectionHelper.Edge.MAX_ONE;
                        outT = tempT;
                     }
                  }
               }
            }

            if (before.outsideDirectionTwo() != current.outsideDirectionTwo()) {
               if (BooleanUtils.isFalse(before.outsideDirectionTwo()) || BooleanUtils.isFalse(current.outsideDirectionTwo())) {
                  float tempT = (float)ray.getT(Axis.Y, minTwo);
                  float valueOne = (float)(ray.originOne + ray.directionOne * tempT);
                  if (tempT >= 0.0F && tempT <= 1.0F && valueOne >= minOne && valueOne <= maxOne) {
                     if (tempT < inT) {
                        inEdge = IntersectionHelper.Edge.MIN_TWO;
                        inT = tempT;
                     }

                     if (tempT > outT) {
                        outEdge = IntersectionHelper.Edge.MIN_TWO;
                        outT = tempT;
                     }
                  }
               }

               if (BooleanUtils.isTrue(before.outsideDirectionTwo()) || BooleanUtils.isTrue(current.outsideDirectionTwo())) {
                  float tempT = (float)ray.getT(Axis.Y, maxTwo);
                  float valueOne = (float)(ray.originOne + ray.directionOne * tempT);
                  if (tempT >= 0.0F && tempT <= 1.0F && valueOne >= minOne && valueOne <= maxOne) {
                     if (tempT < inT) {
                        inEdge = IntersectionHelper.Edge.MAX_TWO;
                        inT = tempT;
                     }

                     if (tempT > outT) {
                        outEdge = IntersectionHelper.Edge.MAX_TWO;
                        outT = tempT;
                     }
                  }
               }
            }

            if (inEdge != null && outEdge != null && inEdge != outEdge) {
               if (!hasFoundInside) {
                  Vec2d outVec = ray.get(outT);
                  Vec2d inVec = ray.get(inT);
                  add(outVec, result);
                  edge = iterateLines(currentIndex, status.length - 1, true, outEdge, ray, minOne, minTwo, maxOne, maxTwo, clockwise, status, corners, result);
                  if (edge == null) {
                     result.clear();
                     return null;
                  }

                  addCornersBetween(minOne, minTwo, maxOne, maxTwo, edge, inEdge, clockwise, result);
                  add(inVec, result);
                  return null;
               }

               addCornersBetween(minOne, minTwo, maxOne, maxTwo, edge, inEdge, clockwise, result);
               add(ray.get(inT), result);
               add(ray.get(outT), result);
               edge = outEdge;
            }
         }

         before = current;
         beforeOne = nowOne;
         beforeTwo = nowTwo;
      }

      return edge;
   }

   private static IntersectionHelper.Edge findIntersection(
      Ray2d ray,
      boolean clockwise,
      double minOne,
      double minTwo,
      double maxOne,
      double maxTwo,
      IntersectionHelper.InsideStatus status,
      double beforeOne,
      double beforeTwo,
      double nowOne,
      double nowTwo,
      IntersectionHelper.Edge before,
      List<Vec2d> result
   ) {
      ray.originOne = beforeOne;
      ray.originTwo = beforeTwo;
      ray.directionOne = nowOne - beforeOne;
      ray.directionTwo = nowTwo - beforeTwo;
      if (status.isOutsideOne()) {
         double edgeValue = status.outsideDirectionOne() ? maxOne : minOne;
         double intersection = (float)ray.get(ray.one, edgeValue);
         if (intersection >= minTwo && intersection <= maxTwo) {
            if (before != null) {
               addCornersBetween(minOne, minTwo, maxOne, maxTwo, before, status.oneEdge(), clockwise, result);
            }

            add(new Vec2d(edgeValue, intersection), result);
            return status.oneEdge();
         }
      }

      if (status.isOutsideTwo()) {
         double edgeValue = status.outsideDirectionTwo() ? maxTwo : minTwo;
         double intersection = (float)ray.get(ray.two, edgeValue);
         if (intersection >= minOne && intersection <= maxOne) {
            if (before != null) {
               addCornersBetween(minOne, minTwo, maxOne, maxTwo, before, status.twoEdge(), clockwise, result);
            }

            add(new Vec2d(intersection, edgeValue), result);
            return status.twoEdge();
         }
      }

      throw new RuntimeException("Impossible");
   }

   private static void add(Vec2d vec, List<Vec2d> result) {
      if (result.isEmpty() || !result.get(result.size() - 1).epsilonEquals(vec, 1.0E-4F)) {
         result.add(vec);
      }
   }

   private static void addCornersBetween(
      double minOne,
      double minTwo,
      double maxOne,
      double maxTwo,
      IntersectionHelper.Edge start,
      IntersectionHelper.Edge end,
      boolean clockwise,
      List<Vec2d> result
   ) {
      IntersectionHelper.Edge current = start;

      while (current != end) {
         IntersectionHelper.Edge next = clockwise ? current.clockwise() : current.counterClockwise();
         if (current.one()) {
            add(new Vec2d(current.positive() ? maxOne : minOne, next.positive() ? maxTwo : minTwo), result);
         } else {
            add(new Vec2d(next.positive() ? maxOne : minOne, current.positive() ? maxTwo : minTwo), result);
         }

         current = next;
      }
   }

   private static IntersectionHelper.Edge iterateLines(
      int offset,
      int count,
      boolean hasFoundInside,
      IntersectionHelper.Edge edge,
      Ray2d ray,
      Axis one,
      Axis two,
      float minOne,
      float minTwo,
      float maxOne,
      float maxTwo,
      boolean clockwise,
      IntersectionHelper.InsideStatus[] status,
      Vec3f[] corners,
      List<Vec2f> result
   ) {
      float beforeOne = corners[offset].get(one);
      float beforeTwo = corners[offset].get(two);
      IntersectionHelper.InsideStatus before = status[offset];
      boolean inside = before.isInside();
      if (inside) {
         add(new Vec2f(beforeOne, beforeTwo), result);
      }

      for (int i = 1; i <= count; i++) {
         int currentIndex = (offset + i) % corners.length;
         Vec3f vec = corners[currentIndex];
         float nowOne = vec.get(one);
         float nowTwo = vec.get(two);
         IntersectionHelper.InsideStatus current = status[currentIndex];
         if (inside) {
            if (current.isInside()) {
               add(new Vec2f(nowOne, nowTwo), result);
            } else {
               edge = findIntersection(ray, clockwise, minOne, minTwo, maxOne, maxTwo, current, beforeOne, beforeTwo, nowOne, nowTwo, null, result);
               inside = false;
            }
         } else if (current.isInside()) {
            findIntersection(ray, clockwise, minOne, minTwo, maxOne, maxTwo, before, beforeOne, beforeTwo, nowOne, nowTwo, edge, result);
            add(new Vec2f(nowOne, nowTwo), result);
            edge = null;
            inside = true;
         } else {
            ray.originOne = beforeOne;
            ray.originTwo = beforeTwo;
            ray.directionOne = nowOne - beforeOne;
            ray.directionTwo = nowTwo - beforeTwo;
            IntersectionHelper.Edge inEdge = null;
            float inT = 1.0F;
            IntersectionHelper.Edge outEdge = null;
            float outT = 0.0F;
            if (before.outsideDirectionOne() != current.outsideDirectionOne()) {
               if (BooleanUtils.isFalse(before.outsideDirectionOne()) || BooleanUtils.isFalse(current.outsideDirectionOne())) {
                  float tempT = (float)ray.getT(one, minOne);
                  float valueTwo = (float)(ray.originTwo + ray.directionTwo * tempT);
                  if (tempT >= 0.0F && tempT <= 1.0F && valueTwo >= minTwo && valueTwo <= maxTwo) {
                     if (tempT < inT) {
                        inEdge = IntersectionHelper.Edge.MIN_ONE;
                        inT = tempT;
                     }

                     if (tempT > outT) {
                        outEdge = IntersectionHelper.Edge.MIN_ONE;
                        outT = tempT;
                     }
                  }
               }

               if (BooleanUtils.isTrue(before.outsideDirectionOne()) || BooleanUtils.isTrue(current.outsideDirectionOne())) {
                  float tempT = (float)ray.getT(one, maxOne);
                  float valueTwo = (float)(ray.originTwo + ray.directionTwo * tempT);
                  if (tempT >= 0.0F && tempT <= 1.0F && valueTwo >= minTwo && valueTwo <= maxTwo) {
                     if (tempT < inT) {
                        inEdge = IntersectionHelper.Edge.MAX_ONE;
                        inT = tempT;
                     }

                     if (tempT > outT) {
                        outEdge = IntersectionHelper.Edge.MAX_ONE;
                        outT = tempT;
                     }
                  }
               }
            }

            if (before.outsideDirectionTwo() != current.outsideDirectionTwo()) {
               if (BooleanUtils.isFalse(before.outsideDirectionTwo()) || BooleanUtils.isFalse(current.outsideDirectionTwo())) {
                  float tempT = (float)ray.getT(two, minTwo);
                  float valueOne = (float)(ray.originOne + ray.directionOne * tempT);
                  if (tempT >= 0.0F && tempT <= 1.0F && valueOne >= minOne && valueOne <= maxOne) {
                     if (tempT < inT) {
                        inEdge = IntersectionHelper.Edge.MIN_TWO;
                        inT = tempT;
                     }

                     if (tempT > outT) {
                        outEdge = IntersectionHelper.Edge.MIN_TWO;
                        outT = tempT;
                     }
                  }
               }

               if (BooleanUtils.isTrue(before.outsideDirectionTwo()) || BooleanUtils.isTrue(current.outsideDirectionTwo())) {
                  float tempT = (float)ray.getT(two, maxTwo);
                  float valueOne = (float)(ray.originOne + ray.directionOne * tempT);
                  if (tempT >= 0.0F && tempT <= 1.0F && valueOne >= minOne && valueOne <= maxOne) {
                     if (tempT < inT) {
                        inEdge = IntersectionHelper.Edge.MAX_TWO;
                        inT = tempT;
                     }

                     if (tempT > outT) {
                        outEdge = IntersectionHelper.Edge.MAX_TWO;
                        outT = tempT;
                     }
                  }
               }
            }

            if (inEdge != null && inEdge != outEdge) {
               if (!hasFoundInside) {
                  Vec2f outVec = ray.getFloat(outT);
                  Vec2f inVec = ray.getFloat(inT);
                  add(outVec, result);
                  edge = iterateLines(
                     currentIndex, status.length - 1, true, outEdge, ray, one, two, minOne, minTwo, maxOne, maxTwo, clockwise, status, corners, result
                  );
                  addCornersBetween(minOne, minTwo, maxOne, maxTwo, edge, inEdge, clockwise, result);
                  add(inVec, result);
                  return null;
               }

               if (edge == null) {
                  result.clear();
                  return null;
               }

               addCornersBetween(minOne, minTwo, maxOne, maxTwo, edge, inEdge, clockwise, result);
               add(ray.getFloat(inT), result);
               add(ray.getFloat(outT), result);
               edge = outEdge;
            }
         }

         before = current;
         beforeOne = nowOne;
         beforeTwo = nowTwo;
      }

      return edge;
   }

   private static IntersectionHelper.Edge findIntersection(
      Ray2d ray,
      boolean clockwise,
      float minOne,
      float minTwo,
      float maxOne,
      float maxTwo,
      IntersectionHelper.InsideStatus status,
      float beforeOne,
      float beforeTwo,
      float nowOne,
      float nowTwo,
      IntersectionHelper.Edge before,
      List<Vec2f> result
   ) {
      ray.originOne = beforeOne;
      ray.originTwo = beforeTwo;
      ray.directionOne = nowOne - beforeOne;
      ray.directionTwo = nowTwo - beforeTwo;
      if (status.isOutsideOne()) {
         float edgeValue = status.outsideDirectionOne() ? maxOne : minOne;
         float intersection = (float)ray.get(ray.one, edgeValue);
         if (intersection >= minTwo && intersection <= maxTwo) {
            if (before != null) {
               addCornersBetween(minOne, minTwo, maxOne, maxTwo, before, status.oneEdge(), clockwise, result);
            }

            add(new Vec2f(edgeValue, intersection), result);
            return status.oneEdge();
         }
      }

      if (status.isOutsideTwo()) {
         float edgeValue = status.outsideDirectionTwo() ? maxTwo : minTwo;
         float intersection = (float)ray.get(ray.two, edgeValue);
         if (intersection >= minOne && intersection <= maxOne) {
            if (before != null) {
               addCornersBetween(minOne, minTwo, maxOne, maxTwo, before, status.twoEdge(), clockwise, result);
            }

            add(new Vec2f(intersection, edgeValue), result);
            return status.twoEdge();
         }
      }

      throw new RuntimeException("Impossible");
   }

   private static void add(Vec2f vec, List<Vec2f> result) {
      if (result.isEmpty() || !result.get(result.size() - 1).epsilonEquals(vec, 1.0E-4F) && (result.size() == 1 || !result.get(0).epsilonEquals(vec, 1.0E-4F))) {
         result.add(vec);
      }
   }

   private static void addCornersBetween(
      float minOne, float minTwo, float maxOne, float maxTwo, IntersectionHelper.Edge start, IntersectionHelper.Edge end, boolean clockwise, List<Vec2f> result
   ) {
      IntersectionHelper.Edge current = start;

      while (current != end) {
         IntersectionHelper.Edge next = clockwise ? current.clockwise() : current.counterClockwise();
         if (current.one()) {
            add(new Vec2f(current.positive() ? maxOne : minOne, next.positive() ? maxTwo : minTwo), result);
         } else {
            add(new Vec2f(next.positive() ? maxOne : minOne, current.positive() ? maxTwo : minTwo), result);
         }

         current = next;
      }
   }

   private enum Edge {
      MIN_ONE {
         @Override
         public IntersectionHelper.Edge clockwise() {
            return MAX_TWO;
         }

         @Override
         public IntersectionHelper.Edge counterClockwise() {
            return MIN_TWO;
         }

         @Override
         public boolean one() {
            return true;
         }

         @Override
         public boolean positive() {
            return false;
         }
      },
      MIN_TWO {
         @Override
         public IntersectionHelper.Edge clockwise() {
            return MIN_ONE;
         }

         @Override
         public IntersectionHelper.Edge counterClockwise() {
            return MAX_ONE;
         }

         @Override
         public boolean one() {
            return false;
         }

         @Override
         public boolean positive() {
            return false;
         }
      },
      MAX_ONE {
         @Override
         public IntersectionHelper.Edge clockwise() {
            return MIN_TWO;
         }

         @Override
         public IntersectionHelper.Edge counterClockwise() {
            return MAX_TWO;
         }

         @Override
         public boolean one() {
            return true;
         }

         @Override
         public boolean positive() {
            return true;
         }
      },
      MAX_TWO {
         @Override
         public IntersectionHelper.Edge clockwise() {
            return MAX_ONE;
         }

         @Override
         public IntersectionHelper.Edge counterClockwise() {
            return MIN_ONE;
         }

         @Override
         public boolean one() {
            return false;
         }

         @Override
         public boolean positive() {
            return true;
         }
      };

      public abstract boolean one();

      public abstract boolean positive();

      public abstract IntersectionHelper.Edge clockwise();

      public abstract IntersectionHelper.Edge counterClockwise();
   }

   private enum InsideStatus {
      INSIDE {
         @Override
         public boolean isInside() {
            return true;
         }

         @Override
         public boolean isOutsideOne() {
            return false;
         }

         @Override
         public Boolean outsideDirectionOne() {
            return null;
         }

         @Override
         public boolean isOutsideTwo() {
            return false;
         }

         @Override
         public Boolean outsideDirectionTwo() {
            return null;
         }
      },
      OUTSIDE_MIN_ONE {
         @Override
         public boolean isInside() {
            return false;
         }

         @Override
         public boolean isOutsideOne() {
            return true;
         }

         @Override
         public Boolean outsideDirectionOne() {
            return false;
         }

         @Override
         public boolean isOutsideTwo() {
            return false;
         }

         @Override
         public Boolean outsideDirectionTwo() {
            return null;
         }
      },
      OUTSIDE_MAX_ONE {
         @Override
         public boolean isInside() {
            return false;
         }

         @Override
         public boolean isOutsideOne() {
            return true;
         }

         @Override
         public Boolean outsideDirectionOne() {
            return true;
         }

         @Override
         public boolean isOutsideTwo() {
            return false;
         }

         @Override
         public Boolean outsideDirectionTwo() {
            return null;
         }
      },
      OUTSIDE_MIN_TWO {
         @Override
         public boolean isInside() {
            return false;
         }

         @Override
         public boolean isOutsideOne() {
            return false;
         }

         @Override
         public Boolean outsideDirectionOne() {
            return null;
         }

         @Override
         public boolean isOutsideTwo() {
            return true;
         }

         @Override
         public Boolean outsideDirectionTwo() {
            return false;
         }
      },
      OUTSIDE_MAX_TWO {
         @Override
         public boolean isInside() {
            return false;
         }

         @Override
         public boolean isOutsideOne() {
            return false;
         }

         @Override
         public Boolean outsideDirectionOne() {
            return null;
         }

         @Override
         public boolean isOutsideTwo() {
            return true;
         }

         @Override
         public Boolean outsideDirectionTwo() {
            return true;
         }
      },
      OUTSIDE_MIN_ONE_MIN_TWO {
         @Override
         public boolean isInside() {
            return false;
         }

         @Override
         public boolean isOutsideOne() {
            return true;
         }

         @Override
         public Boolean outsideDirectionOne() {
            return false;
         }

         @Override
         public boolean isOutsideTwo() {
            return true;
         }

         @Override
         public Boolean outsideDirectionTwo() {
            return false;
         }
      },
      OUTSIDE_MIN_ONE_MAX_TWO {
         @Override
         public boolean isInside() {
            return false;
         }

         @Override
         public boolean isOutsideOne() {
            return true;
         }

         @Override
         public Boolean outsideDirectionOne() {
            return false;
         }

         @Override
         public boolean isOutsideTwo() {
            return true;
         }

         @Override
         public Boolean outsideDirectionTwo() {
            return true;
         }
      },
      OUTSIDE_MAX_ONE_MIN_TWO {
         @Override
         public boolean isInside() {
            return false;
         }

         @Override
         public boolean isOutsideOne() {
            return true;
         }

         @Override
         public Boolean outsideDirectionOne() {
            return true;
         }

         @Override
         public boolean isOutsideTwo() {
            return true;
         }

         @Override
         public Boolean outsideDirectionTwo() {
            return false;
         }
      },
      OUTSIDE_MAX_ONE_MAX_TWO {
         @Override
         public boolean isInside() {
            return false;
         }

         @Override
         public boolean isOutsideOne() {
            return true;
         }

         @Override
         public Boolean outsideDirectionOne() {
            return true;
         }

         @Override
         public boolean isOutsideTwo() {
            return true;
         }

         @Override
         public Boolean outsideDirectionTwo() {
            return true;
         }
      };

      public abstract boolean isInside();

      public abstract boolean isOutsideOne();

      public abstract Boolean outsideDirectionOne();

      public IntersectionHelper.Edge oneEdge() {
         return this.outsideDirectionOne() ? IntersectionHelper.Edge.MAX_ONE : IntersectionHelper.Edge.MIN_ONE;
      }

      public abstract boolean isOutsideTwo();

      public abstract Boolean outsideDirectionTwo();

      public IntersectionHelper.Edge twoEdge() {
         return this.outsideDirectionTwo() ? IntersectionHelper.Edge.MAX_TWO : IntersectionHelper.Edge.MIN_TWO;
      }

      public static IntersectionHelper.InsideStatus get(float one, float two, float minOne, float minTwo, float maxOne, float maxTwo) {
         if (one > minOne) {
            if (one < maxOne) {
               if (two > minTwo) {
                  return two < maxTwo ? INSIDE : OUTSIDE_MAX_TWO;
               } else {
                  return OUTSIDE_MIN_TWO;
               }
            } else if (two > minTwo) {
               return two < maxTwo ? OUTSIDE_MAX_ONE : OUTSIDE_MAX_ONE_MAX_TWO;
            } else {
               return OUTSIDE_MAX_ONE_MIN_TWO;
            }
         } else if (two > minTwo) {
            return two < maxTwo ? OUTSIDE_MIN_ONE : OUTSIDE_MIN_ONE_MAX_TWO;
         } else {
            return OUTSIDE_MIN_ONE_MIN_TWO;
         }
      }

      public static IntersectionHelper.InsideStatus get(double one, double two, double minOne, double minTwo, double maxOne, double maxTwo) {
         if (one > minOne) {
            if (one < maxOne) {
               if (two > minTwo) {
                  return two < maxTwo ? INSIDE : OUTSIDE_MAX_TWO;
               } else {
                  return OUTSIDE_MIN_TWO;
               }
            } else if (two >= minTwo) {
               return two <= maxTwo ? OUTSIDE_MAX_ONE : OUTSIDE_MAX_ONE_MAX_TWO;
            } else {
               return OUTSIDE_MAX_ONE_MIN_TWO;
            }
         } else if (two > minTwo) {
            return two < maxTwo ? OUTSIDE_MIN_ONE : OUTSIDE_MIN_ONE_MAX_TWO;
         } else {
            return OUTSIDE_MIN_ONE_MIN_TWO;
         }
      }
   }
}
