package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationDefinition;

public class yetiHandPunch {
   public static final AnimationDefinition HAND_PUNCH;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: fconst_2
      // 0001: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0004: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0007: ldc "CTR_leg_left.002"
      // 0009: new net/minecraft/client/animation/AnimationChannel
      // 000c: dup
      // 000d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0010: bipush 14
      // 0012: anewarray 27
      // 0015: dup
      // 0016: bipush 0
      // 0017: new net/minecraft/client/animation/Keyframe
      // 001a: dup
      // 001b: ldc 0.25
      // 001d: ldc 2.113
      // 001f: fconst_0
      // 0020: ldc -13.058
      // 0022: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0025: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0028: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 002b: aastore
      // 002c: dup
      // 002d: bipush 1
      // 002e: new net/minecraft/client/animation/Keyframe
      // 0031: dup
      // 0032: ldc 0.29
      // 0034: ldc 2.113
      // 0036: fconst_0
      // 0037: ldc -9.401
      // 0039: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 003c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 003f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0042: aastore
      // 0043: dup
      // 0044: bipush 2
      // 0045: new net/minecraft/client/animation/Keyframe
      // 0048: dup
      // 0049: ldc 0.33
      // 004b: ldc 2.113
      // 004d: fconst_0
      // 004e: ldc -2.609
      // 0050: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0053: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0056: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0059: aastore
      // 005a: dup
      // 005b: bipush 3
      // 005c: new net/minecraft/client/animation/Keyframe
      // 005f: dup
      // 0060: ldc 0.38
      // 0062: ldc 2.113
      // 0064: fconst_0
      // 0065: ldc 1.049
      // 0067: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 006a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 006d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0070: aastore
      // 0071: dup
      // 0072: bipush 4
      // 0073: new net/minecraft/client/animation/Keyframe
      // 0076: dup
      // 0077: ldc 1.21
      // 0079: ldc 2.113
      // 007b: fconst_0
      // 007c: ldc 1.049
      // 007e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0081: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0084: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0087: aastore
      // 0088: dup
      // 0089: bipush 5
      // 008a: new net/minecraft/client/animation/Keyframe
      // 008d: dup
      // 008e: ldc 1.25
      // 0090: ldc 2.113
      // 0092: fconst_0
      // 0093: ldc -4.862
      // 0095: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0098: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 009b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 009e: aastore
      // 009f: dup
      // 00a0: bipush 6
      // 00a2: new net/minecraft/client/animation/Keyframe
      // 00a5: dup
      // 00a6: ldc 1.33
      // 00a8: ldc 2.113
      // 00aa: fconst_0
      // 00ab: ldc -30.866
      // 00ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00b6: aastore
      // 00b7: dup
      // 00b8: bipush 7
      // 00ba: new net/minecraft/client/animation/Keyframe
      // 00bd: dup
      // 00be: ldc 1.38
      // 00c0: ldc 2.113
      // 00c2: fconst_0
      // 00c3: ldc -36.776
      // 00c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00ce: aastore
      // 00cf: dup
      // 00d0: bipush 8
      // 00d2: new net/minecraft/client/animation/Keyframe
      // 00d5: dup
      // 00d6: ldc 1.79
      // 00d8: ldc 2.113
      // 00da: fconst_0
      // 00db: ldc -36.776
      // 00dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00e6: aastore
      // 00e7: dup
      // 00e8: bipush 9
      // 00ea: new net/minecraft/client/animation/Keyframe
      // 00ed: dup
      // 00ee: ldc 1.83
      // 00f0: ldc 2.113
      // 00f2: fconst_0
      // 00f3: ldc -34.309
      // 00f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00fe: aastore
      // 00ff: dup
      // 0100: bipush 10
      // 0102: new net/minecraft/client/animation/Keyframe
      // 0105: dup
      // 0106: ldc 1.88
      // 0108: ldc 2.113
      // 010a: fconst_0
      // 010b: ldc -28.427
      // 010d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0110: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0113: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0116: aastore
      // 0117: dup
      // 0118: bipush 11
      // 011a: new net/minecraft/client/animation/Keyframe
      // 011d: dup
      // 011e: ldc 1.92
      // 0120: ldc 2.113
      // 0122: fconst_0
      // 0123: ldc -21.407
      // 0125: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0128: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 012b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 012e: aastore
      // 012f: dup
      // 0130: bipush 12
      // 0132: new net/minecraft/client/animation/Keyframe
      // 0135: dup
      // 0136: ldc 1.96
      // 0138: ldc 2.113
      // 013a: fconst_0
      // 013b: ldc -15.525
      // 013d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0140: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0143: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0146: aastore
      // 0147: dup
      // 0148: bipush 13
      // 014a: new net/minecraft/client/animation/Keyframe
      // 014d: dup
      // 014e: fconst_2
      // 014f: ldc 2.113
      // 0151: fconst_0
      // 0152: ldc -13.058
      // 0154: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0157: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 015a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 015d: aastore
      // 015e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0161: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0164: ldc "CTR_hips"
      // 0166: new net/minecraft/client/animation/AnimationChannel
      // 0169: dup
      // 016a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 016d: bipush 49
      // 016f: anewarray 27
      // 0172: dup
      // 0173: bipush 0
      // 0174: new net/minecraft/client/animation/Keyframe
      // 0177: dup
      // 0178: fconst_0
      // 0179: ldc -0.727
      // 017b: ldc 0.028
      // 017d: ldc -4.943
      // 017f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0182: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0185: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0188: aastore
      // 0189: dup
      // 018a: bipush 1
      // 018b: new net/minecraft/client/animation/Keyframe
      // 018e: dup
      // 018f: ldc 0.04
      // 0191: ldc -0.16
      // 0193: ldc 0.642
      // 0195: ldc -4.655
      // 0197: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 019a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 019d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01a0: aastore
      // 01a1: dup
      // 01a2: bipush 2
      // 01a3: new net/minecraft/client/animation/Keyframe
      // 01a6: dup
      // 01a7: ldc 0.08
      // 01a9: ldc 0.364
      // 01ab: ldc 2.175
      // 01ad: ldc -3.879
      // 01af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01b8: aastore
      // 01b9: dup
      // 01ba: bipush 3
      // 01bb: new net/minecraft/client/animation/Keyframe
      // 01be: dup
      // 01bf: ldc 0.12
      // 01c1: ldc 0.856
      // 01c3: ldc 4.163
      // 01c5: ldc -2.743
      // 01c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01d0: aastore
      // 01d1: dup
      // 01d2: bipush 4
      // 01d3: new net/minecraft/client/animation/Keyframe
      // 01d6: dup
      // 01d7: ldc 0.17
      // 01d9: ldc 1.302
      // 01db: ldc 6.141
      // 01dd: ldc -1.374
      // 01df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01e8: aastore
      // 01e9: dup
      // 01ea: bipush 5
      // 01eb: new net/minecraft/client/animation/Keyframe
      // 01ee: dup
      // 01ef: ldc 0.21
      // 01f1: ldc 1.623
      // 01f3: ldc 7.645
      // 01f5: ldc 0.083
      // 01f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0200: aastore
      // 0201: dup
      // 0202: bipush 6
      // 0204: new net/minecraft/client/animation/Keyframe
      // 0207: dup
      // 0208: ldc 0.25
      // 020a: ldc 1.668
      // 020c: ldc 8.221
      // 020e: ldc 1.463
      // 0210: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0213: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0216: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0219: aastore
      // 021a: dup
      // 021b: bipush 7
      // 021d: new net/minecraft/client/animation/Keyframe
      // 0220: dup
      // 0221: ldc 0.29
      // 0223: ldc 1.328
      // 0225: ldc 8.124
      // 0227: ldc 2.612
      // 0229: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 022c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 022f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0232: aastore
      // 0233: dup
      // 0234: bipush 8
      // 0236: new net/minecraft/client/animation/Keyframe
      // 0239: dup
      // 023a: ldc 0.33
      // 023c: ldc 0.568
      // 023e: ldc 7.929
      // 0240: ldc 3.4
      // 0242: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0245: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0248: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 024b: aastore
      // 024c: dup
      // 024d: bipush 9
      // 024f: new net/minecraft/client/animation/Keyframe
      // 0252: dup
      // 0253: ldc 0.38
      // 0255: ldc -0.695
      // 0257: ldc 7.663
      // 0259: ldc 3.685
      // 025b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 025e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0261: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0264: aastore
      // 0265: dup
      // 0266: bipush 10
      // 0268: new net/minecraft/client/animation/Keyframe
      // 026b: dup
      // 026c: ldc 0.42
      // 026e: ldc -2.573
      // 0270: ldc 7.354
      // 0272: ldc 3.24
      // 0274: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0277: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 027a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 027d: aastore
      // 027e: dup
      // 027f: bipush 11
      // 0281: new net/minecraft/client/animation/Keyframe
      // 0284: dup
      // 0285: ldc 0.46
      // 0287: ldc -5.13
      // 0289: ldc 7.015
      // 028b: ldc 2.052
      // 028d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0290: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0293: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0296: aastore
      // 0297: dup
      // 0298: bipush 12
      // 029a: new net/minecraft/client/animation/Keyframe
      // 029d: dup
      // 029e: ldc 0.5
      // 02a0: ldc -8.047
      // 02a2: ldc 6.645
      // 02a4: ldc 0.29
      // 02a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02af: aastore
      // 02b0: dup
      // 02b1: bipush 13
      // 02b3: new net/minecraft/client/animation/Keyframe
      // 02b6: dup
      // 02b7: ldc 0.54
      // 02b9: ldc -10.86
      // 02bb: ldc 6.248
      // 02bd: ldc -1.883
      // 02bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02c8: aastore
      // 02c9: dup
      // 02ca: bipush 14
      // 02cc: new net/minecraft/client/animation/Keyframe
      // 02cf: dup
      // 02d0: ldc 0.58
      // 02d2: ldc -13.387
      // 02d4: ldc 5.83
      // 02d6: ldc -4.313
      // 02d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02e1: aastore
      // 02e2: dup
      // 02e3: bipush 15
      // 02e5: new net/minecraft/client/animation/Keyframe
      // 02e8: dup
      // 02e9: ldc 0.62
      // 02eb: ldc -15.449
      // 02ed: ldc 5.405
      // 02ef: ldc -6.847
      // 02f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02fa: aastore
      // 02fb: dup
      // 02fc: bipush 16
      // 02fe: new net/minecraft/client/animation/Keyframe
      // 0301: dup
      // 0302: ldc 0.67
      // 0304: ldc -16.876
      // 0306: ldc 4.993
      // 0308: ldc -9.334
      // 030a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 030d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0310: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0313: aastore
      // 0314: dup
      // 0315: bipush 17
      // 0317: new net/minecraft/client/animation/Keyframe
      // 031a: dup
      // 031b: ldc 0.71
      // 031d: ldc -17.506
      // 031f: ldc 4.619
      // 0321: ldc -11.616
      // 0323: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0326: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0329: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 032c: aastore
      // 032d: dup
      // 032e: bipush 18
      // 0330: new net/minecraft/client/animation/Keyframe
      // 0333: dup
      // 0334: ldc 0.75
      // 0336: ldc -17.175
      // 0338: ldc 4.313
      // 033a: ldc -13.525
      // 033c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 033f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0342: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0345: aastore
      // 0346: dup
      // 0347: bipush 19
      // 0349: new net/minecraft/client/animation/Keyframe
      // 034c: dup
      // 034d: ldc 0.79
      // 034f: ldc -15.724
      // 0351: ldc 4.106
      // 0353: ldc -14.881
      // 0355: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0358: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 035b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 035e: aastore
      // 035f: dup
      // 0360: bipush 20
      // 0362: new net/minecraft/client/animation/Keyframe
      // 0365: dup
      // 0366: ldc 0.83
      // 0368: ldc -14.276
      // 036a: ldc 3.995
      // 036c: ldc -15.338
      // 036e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0371: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0374: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0377: aastore
      // 0378: dup
      // 0379: bipush 21
      // 037b: new net/minecraft/client/animation/Keyframe
      // 037e: dup
      // 037f: ldc 0.88
      // 0381: ldc -12.928
      // 0383: ldc 3.765
      // 0385: ldc -15.467
      // 0387: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 038a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 038d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0390: aastore
      // 0391: dup
      // 0392: bipush 22
      // 0394: new net/minecraft/client/animation/Keyframe
      // 0397: dup
      // 0398: ldc 0.92
      // 039a: ldc -11.254
      // 039c: ldc 3.172
      // 039e: ldc -15.408
      // 03a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03a9: aastore
      // 03aa: dup
      // 03ab: bipush 23
      // 03ad: new net/minecraft/client/animation/Keyframe
      // 03b0: dup
      // 03b1: ldc 0.96
      // 03b3: ldc -8.365
      // 03b5: ldc 1.982
      // 03b7: ldc -14.988
      // 03b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03c2: aastore
      // 03c3: dup
      // 03c4: bipush 24
      // 03c6: new net/minecraft/client/animation/Keyframe
      // 03c9: dup
      // 03ca: fconst_1
      // 03cb: ldc 5.301
      // 03cd: ldc -0.04
      // 03cf: ldc -10.857
      // 03d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03da: aastore
      // 03db: dup
      // 03dc: bipush 25
      // 03de: new net/minecraft/client/animation/Keyframe
      // 03e1: dup
      // 03e2: ldc 1.04
      // 03e4: ldc 20.672
      // 03e6: ldc -2.288
      // 03e8: ldc -1.505
      // 03ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03f3: aastore
      // 03f4: dup
      // 03f5: bipush 26
      // 03f7: new net/minecraft/client/animation/Keyframe
      // 03fa: dup
      // 03fb: ldc 1.08
      // 03fd: ldc 28.272
      // 03ff: ldc -3.416
      // 0401: ldc 4.951
      // 0403: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0406: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0409: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 040c: aastore
      // 040d: dup
      // 040e: bipush 27
      // 0410: new net/minecraft/client/animation/Keyframe
      // 0413: dup
      // 0414: ldc 1.12
      // 0416: ldc 28.122
      // 0418: ldc -2.703
      // 041a: ldc 5.148
      // 041c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 041f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0422: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0425: aastore
      // 0426: dup
      // 0427: bipush 28
      // 0429: new net/minecraft/client/animation/Keyframe
      // 042c: dup
      // 042d: ldc 1.17
      // 042f: ldc 27.354
      // 0431: ldc -0.891
      // 0433: ldc 4.189
      // 0435: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0438: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 043b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 043e: aastore
      // 043f: dup
      // 0440: bipush 29
      // 0442: new net/minecraft/client/animation/Keyframe
      // 0445: dup
      // 0446: ldc 1.21
      // 0448: ldc 25.721
      // 044a: ldc 1.479
      // 044c: ldc 2.784
      // 044e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0451: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0454: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0457: aastore
      // 0458: dup
      // 0459: bipush 30
      // 045b: new net/minecraft/client/animation/Keyframe
      // 045e: dup
      // 045f: ldc 1.25
      // 0461: ldc 23.009
      // 0463: ldc 3.858
      // 0465: ldc 1.516
      // 0467: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 046a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 046d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0470: aastore
      // 0471: dup
      // 0472: bipush 31
      // 0474: new net/minecraft/client/animation/Keyframe
      // 0477: dup
      // 0478: ldc 1.29
      // 047a: ldc 19.075
      // 047c: ldc 5.684
      // 047e: ldc 0.969
      // 0480: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0483: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0486: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0489: aastore
      // 048a: dup
      // 048b: bipush 32
      // 048d: new net/minecraft/client/animation/Keyframe
      // 0490: dup
      // 0491: ldc 1.33
      // 0493: ldc 13.789
      // 0495: ldc 6.777
      // 0497: ldc 1.612
      // 0499: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 049c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 049f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04a2: aastore
      // 04a3: dup
      // 04a4: bipush 33
      // 04a6: new net/minecraft/client/animation/Keyframe
      // 04a9: dup
      // 04aa: ldc 1.38
      // 04ac: ldc 6.877
      // 04ae: ldc 7.401
      // 04b0: ldc 3.099
      // 04b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04bb: aastore
      // 04bc: dup
      // 04bd: bipush 34
      // 04bf: new net/minecraft/client/animation/Keyframe
      // 04c2: dup
      // 04c3: ldc 1.42
      // 04c5: ldc -1.377
      // 04c7: ldc 7.673
      // 04c9: ldc 4.866
      // 04cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04d4: aastore
      // 04d5: dup
      // 04d6: bipush 35
      // 04d8: new net/minecraft/client/animation/Keyframe
      // 04db: dup
      // 04dc: ldc 1.46
      // 04de: ldc -8.703
      // 04e0: ldc 7.728
      // 04e2: ldc 6.341
      // 04e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04ed: aastore
      // 04ee: dup
      // 04ef: bipush 36
      // 04f1: new net/minecraft/client/animation/Keyframe
      // 04f4: dup
      // 04f5: ldc 1.5
      // 04f7: ldc -12.115
      // 04f9: ldc 7.713
      // 04fb: ldc 6.957
      // 04fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0500: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0503: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0506: aastore
      // 0507: dup
      // 0508: bipush 37
      // 050a: new net/minecraft/client/animation/Keyframe
      // 050d: dup
      // 050e: ldc 1.54
      // 0510: ldc -11.391
      // 0512: ldc 7.571
      // 0514: ldc 6.714
      // 0516: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0519: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 051c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 051f: aastore
      // 0520: dup
      // 0521: bipush 38
      // 0523: new net/minecraft/client/animation/Keyframe
      // 0526: dup
      // 0527: ldc 1.58
      // 0529: ldc -8.937
      // 052b: ldc 7.177
      // 052d: ldc 6.045
      // 052f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0532: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0535: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0538: aastore
      // 0539: dup
      // 053a: bipush 39
      // 053c: new net/minecraft/client/animation/Keyframe
      // 053f: dup
      // 0540: ldc 1.62
      // 0542: ldc -6.387
      // 0544: ldc 6.574
      // 0546: ldc 5.041
      // 0548: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 054b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 054e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0551: aastore
      // 0552: dup
      // 0553: bipush 40
      // 0555: new net/minecraft/client/animation/Keyframe
      // 0558: dup
      // 0559: ldc 1.67
      // 055b: ldc -3.889
      // 055d: ldc 5.807
      // 055f: ldc 3.79
      // 0561: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0564: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0567: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 056a: aastore
      // 056b: dup
      // 056c: bipush 41
      // 056e: new net/minecraft/client/animation/Keyframe
      // 0571: dup
      // 0572: ldc 1.71
      // 0574: ldc -1.648
      // 0576: ldc 4.924
      // 0578: ldc 2.384
      // 057a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 057d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0580: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0583: aastore
      // 0584: dup
      // 0585: bipush 42
      // 0587: new net/minecraft/client/animation/Keyframe
      // 058a: dup
      // 058b: ldc 1.75
      // 058d: ldc 0.139
      // 058f: ldc 3.973
      // 0591: ldc 0.907
      // 0593: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0596: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0599: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 059c: aastore
      // 059d: dup
      // 059e: bipush 43
      // 05a0: new net/minecraft/client/animation/Keyframe
      // 05a3: dup
      // 05a4: ldc 1.79
      // 05a6: ldc 1.348
      // 05a8: ldc 3.011
      // 05aa: ldc -0.557
      // 05ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05b5: aastore
      // 05b6: dup
      // 05b7: bipush 44
      // 05b9: new net/minecraft/client/animation/Keyframe
      // 05bc: dup
      // 05bd: ldc 1.83
      // 05bf: ldc 1.947
      // 05c1: ldc 2.092
      // 05c3: ldc -1.93
      // 05c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05ce: aastore
      // 05cf: dup
      // 05d0: bipush 45
      // 05d2: new net/minecraft/client/animation/Keyframe
      // 05d5: dup
      // 05d6: ldc 1.88
      // 05d8: ldc 1.97
      // 05da: ldc 1.276
      // 05dc: ldc -3.131
      // 05de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05e7: aastore
      // 05e8: dup
      // 05e9: bipush 46
      // 05eb: new net/minecraft/client/animation/Keyframe
      // 05ee: dup
      // 05ef: ldc 1.92
      // 05f1: ldc 1.484
      // 05f3: ldc 0.621
      // 05f5: ldc_w -4.086
      // 05f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0601: aastore
      // 0602: dup
      // 0603: bipush 47
      // 0605: new net/minecraft/client/animation/Keyframe
      // 0608: dup
      // 0609: ldc 1.96
      // 060b: ldc_w 0.561
      // 060e: ldc_w 0.186
      // 0611: ldc_w -4.715
      // 0614: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0617: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 061a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 061d: aastore
      // 061e: dup
      // 061f: bipush 48
      // 0621: new net/minecraft/client/animation/Keyframe
      // 0624: dup
      // 0625: fconst_2
      // 0626: ldc -0.727
      // 0628: ldc 0.028
      // 062a: ldc -4.943
      // 062c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 062f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0632: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0635: aastore
      // 0636: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0639: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 063c: ldc "CTR_hips"
      // 063e: new net/minecraft/client/animation/AnimationChannel
      // 0641: dup
      // 0642: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0645: bipush 48
      // 0647: anewarray 27
      // 064a: dup
      // 064b: bipush 0
      // 064c: new net/minecraft/client/animation/Keyframe
      // 064f: dup
      // 0650: fconst_0
      // 0651: fconst_0
      // 0652: fconst_0
      // 0653: fconst_0
      // 0654: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0657: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 065a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 065d: aastore
      // 065e: dup
      // 065f: bipush 1
      // 0660: new net/minecraft/client/animation/Keyframe
      // 0663: dup
      // 0664: ldc 0.04
      // 0666: ldc_w -0.466
      // 0669: ldc_w -0.411
      // 066c: ldc_w -1.397
      // 066f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0672: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0675: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0678: aastore
      // 0679: dup
      // 067a: bipush 2
      // 067b: new net/minecraft/client/animation/Keyframe
      // 067e: dup
      // 067f: ldc 0.08
      // 0681: ldc_w -0.84
      // 0684: ldc_w -0.798
      // 0687: ldc_w -2.417
      // 068a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 068d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0690: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0693: aastore
      // 0694: dup
      // 0695: bipush 3
      // 0696: new net/minecraft/client/animation/Keyframe
      // 0699: dup
      // 069a: ldc 0.12
      // 069c: ldc_w -1.094
      // 069f: ldc_w -1.15
      // 06a2: ldc_w -2.972
      // 06a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06ae: aastore
      // 06af: dup
      // 06b0: bipush 4
      // 06b1: new net/minecraft/client/animation/Keyframe
      // 06b4: dup
      // 06b5: ldc 0.17
      // 06b7: ldc_w -1.179
      // 06ba: ldc_w -1.45
      // 06bd: ldc_w -2.937
      // 06c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06c9: aastore
      // 06ca: dup
      // 06cb: bipush 5
      // 06cc: new net/minecraft/client/animation/Keyframe
      // 06cf: dup
      // 06d0: ldc 0.21
      // 06d2: ldc_w -1.017
      // 06d5: ldc_w -1.669
      // 06d8: ldc_w -2.147
      // 06db: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06e4: aastore
      // 06e5: dup
      // 06e6: bipush 6
      // 06e8: new net/minecraft/client/animation/Keyframe
      // 06eb: dup
      // 06ec: ldc 0.25
      // 06ee: ldc_w -0.482
      // 06f1: ldc_w -1.763
      // 06f4: ldc_w -0.395
      // 06f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0700: aastore
      // 0701: dup
      // 0702: bipush 7
      // 0704: new net/minecraft/client/animation/Keyframe
      // 0707: dup
      // 0708: ldc 0.29
      // 070a: ldc_w 0.488
      // 070d: ldc_w -1.659
      // 0710: ldc_w 2.517
      // 0713: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0716: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0719: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 071c: aastore
      // 071d: dup
      // 071e: bipush 8
      // 0720: new net/minecraft/client/animation/Keyframe
      // 0723: dup
      // 0724: ldc 0.33
      // 0726: ldc_w 1.408
      // 0729: ldc_w -1.26
      // 072c: ldc_w 6.539
      // 072f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0732: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0735: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0738: aastore
      // 0739: dup
      // 073a: bipush 9
      // 073c: new net/minecraft/client/animation/Keyframe
      // 073f: dup
      // 0740: ldc 0.38
      // 0742: ldc_w 1.73
      // 0745: ldc_w -0.493
      // 0748: ldc_w 11.005
      // 074b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 074e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0751: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0754: aastore
      // 0755: dup
      // 0756: bipush 10
      // 0758: new net/minecraft/client/animation/Keyframe
      // 075b: dup
      // 075c: ldc 0.42
      // 075e: ldc_w 1.567
      // 0761: ldc_w 0.594
      // 0764: ldc_w 14.829
      // 0767: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 076a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 076d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0770: aastore
      // 0771: dup
      // 0772: bipush 11
      // 0774: new net/minecraft/client/animation/Keyframe
      // 0777: dup
      // 0778: ldc 0.46
      // 077a: ldc_w 1.114
      // 077d: ldc_w 1.853
      // 0780: ldc_w 17.394
      // 0783: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0786: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0789: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 078c: aastore
      // 078d: dup
      // 078e: bipush 12
      // 0790: new net/minecraft/client/animation/Keyframe
      // 0793: dup
      // 0794: ldc 0.5
      // 0796: ldc_w 0.639
      // 0799: ldc_w 3.164
      // 079c: ldc_w 19.219
      // 079f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07a8: aastore
      // 07a9: dup
      // 07aa: bipush 13
      // 07ac: new net/minecraft/client/animation/Keyframe
      // 07af: dup
      // 07b0: ldc 0.54
      // 07b2: ldc_w 0.312
      // 07b5: ldc_w 4.471
      // 07b8: ldc_w 20.839
      // 07bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07c4: aastore
      // 07c5: dup
      // 07c6: bipush 14
      // 07c8: new net/minecraft/client/animation/Keyframe
      // 07cb: dup
      // 07cc: ldc 0.58
      // 07ce: ldc_w 0.116
      // 07d1: ldc_w 5.722
      // 07d4: ldc_w 22.195
      // 07d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07e0: aastore
      // 07e1: dup
      // 07e2: bipush 15
      // 07e4: new net/minecraft/client/animation/Keyframe
      // 07e7: dup
      // 07e8: ldc 0.62
      // 07ea: ldc_w 0.034
      // 07ed: ldc_w 6.869
      // 07f0: ldc_w 23.226
      // 07f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07fc: aastore
      // 07fd: dup
      // 07fe: bipush 16
      // 0800: new net/minecraft/client/animation/Keyframe
      // 0803: dup
      // 0804: ldc 0.67
      // 0806: ldc_w 0.047
      // 0809: ldc_w 7.86
      // 080c: ldc_w 23.86
      // 080f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0812: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0815: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0818: aastore
      // 0819: dup
      // 081a: bipush 17
      // 081c: new net/minecraft/client/animation/Keyframe
      // 081f: dup
      // 0820: ldc 0.71
      // 0822: ldc_w 0.14
      // 0825: ldc_w 8.633
      // 0828: ldc_w 24.008
      // 082b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 082e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0831: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0834: aastore
      // 0835: dup
      // 0836: bipush 18
      // 0838: new net/minecraft/client/animation/Keyframe
      // 083b: dup
      // 083c: ldc 0.75
      // 083e: ldc_w 0.293
      // 0841: ldc_w 9.103
      // 0844: ldc_w 23.554
      // 0847: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 084a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 084d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0850: aastore
      // 0851: dup
      // 0852: bipush 19
      // 0854: new net/minecraft/client/animation/Keyframe
      // 0857: dup
      // 0858: ldc 0.79
      // 085a: ldc_w 0.491
      // 085d: ldc_w 9.143
      // 0860: ldc_w 22.341
      // 0863: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0866: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0869: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 086c: aastore
      // 086d: dup
      // 086e: bipush 20
      // 0870: new net/minecraft/client/animation/Keyframe
      // 0873: dup
      // 0874: ldc 0.83
      // 0876: ldc 0.621
      // 0878: ldc_w 8.862
      // 087b: ldc_w 21.153
      // 087e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0881: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0884: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0887: aastore
      // 0888: dup
      // 0889: bipush 21
      // 088b: new net/minecraft/client/animation/Keyframe
      // 088e: dup
      // 088f: ldc 0.88
      // 0891: ldc_w 0.716
      // 0894: ldc_w 8.48
      // 0897: ldc_w 20.076
      // 089a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 089d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08a3: aastore
      // 08a4: dup
      // 08a5: bipush 22
      // 08a7: new net/minecraft/client/animation/Keyframe
      // 08aa: dup
      // 08ab: ldc 0.92
      // 08ad: ldc_w 0.813
      // 08b0: ldc_w 7.931
      // 08b3: ldc_w 18.796
      // 08b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08bf: aastore
      // 08c0: dup
      // 08c1: bipush 23
      // 08c3: new net/minecraft/client/animation/Keyframe
      // 08c6: dup
      // 08c7: ldc 0.96
      // 08c9: ldc_w 0.95
      // 08cc: ldc_w 6.792
      // 08cf: ldc_w 16.575
      // 08d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08db: aastore
      // 08dc: dup
      // 08dd: bipush 24
      // 08df: new net/minecraft/client/animation/Keyframe
      // 08e2: dup
      // 08e3: fconst_1
      // 08e4: ldc_w 1.28
      // 08e7: ldc_w 3.461
      // 08ea: ldc_w 9.474
      // 08ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08f6: aastore
      // 08f7: dup
      // 08f8: bipush 25
      // 08fa: new net/minecraft/client/animation/Keyframe
      // 08fd: dup
      // 08fe: ldc 1.04
      // 0900: ldc_w 1.768
      // 0903: ldc_w -0.019
      // 0906: ldc_w 0.294
      // 0909: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 090c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 090f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0912: aastore
      // 0913: dup
      // 0914: bipush 26
      // 0916: new net/minecraft/client/animation/Keyframe
      // 0919: dup
      // 091a: ldc 1.08
      // 091c: ldc_w 2.371
      // 091f: ldc_w -1.746
      // 0922: ldc_w -6.943
      // 0925: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0928: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 092b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 092e: aastore
      // 092f: dup
      // 0930: bipush 27
      // 0932: new net/minecraft/client/animation/Keyframe
      // 0935: dup
      // 0936: ldc 1.12
      // 0938: ldc_w 3.043
      // 093b: ldc_w -1.591
      // 093e: ldc_w -10.947
      // 0941: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0944: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0947: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 094a: aastore
      // 094b: dup
      // 094c: bipush 28
      // 094e: new net/minecraft/client/animation/Keyframe
      // 0951: dup
      // 0952: ldc 1.17
      // 0954: ldc_w 3.71
      // 0957: ldc_w -0.854
      // 095a: ldc_w -13.376
      // 095d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0960: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0963: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0966: aastore
      // 0967: dup
      // 0968: bipush 29
      // 096a: new net/minecraft/client/animation/Keyframe
      // 096d: dup
      // 096e: ldc 1.21
      // 0970: ldc_w 4.288
      // 0973: ldc_w -0.052
      // 0976: ldc_w -14.624
      // 0979: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 097c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 097f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0982: aastore
      // 0983: dup
      // 0984: bipush 30
      // 0986: new net/minecraft/client/animation/Keyframe
      // 0989: dup
      // 098a: ldc 1.25
      // 098c: ldc_w 4.696
      // 098f: ldc_w 0.446
      // 0992: ldc_w -15.083
      // 0995: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0998: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 099b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 099e: aastore
      // 099f: dup
      // 09a0: bipush 31
      // 09a2: new net/minecraft/client/animation/Keyframe
      // 09a5: dup
      // 09a6: ldc 1.29
      // 09a8: ldc_w 4.85
      // 09ab: ldc_w 0.599
      // 09ae: ldc_w -15.149
      // 09b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09ba: aastore
      // 09bb: dup
      // 09bc: bipush 32
      // 09be: new net/minecraft/client/animation/Keyframe
      // 09c1: dup
      // 09c2: ldc 1.33
      // 09c4: ldc_w 4.85
      // 09c7: ldc_w 0.374
      // 09ca: ldc_w -15.146
      // 09cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09d6: aastore
      // 09d7: dup
      // 09d8: bipush 33
      // 09da: new net/minecraft/client/animation/Keyframe
      // 09dd: dup
      // 09de: ldc 1.38
      // 09e0: ldc_w 4.85
      // 09e3: ldc_w -0.405
      // 09e6: ldc_w -15.127
      // 09e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09f2: aastore
      // 09f3: dup
      // 09f4: bipush 34
      // 09f6: new net/minecraft/client/animation/Keyframe
      // 09f9: dup
      // 09fa: ldc 1.42
      // 09fc: ldc_w 4.85
      // 09ff: ldc_w -1.95
      // 0a02: ldc_w -15.073
      // 0a05: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a0e: aastore
      // 0a0f: dup
      // 0a10: bipush 35
      // 0a12: new net/minecraft/client/animation/Keyframe
      // 0a15: dup
      // 0a16: ldc 1.46
      // 0a18: ldc_w 4.85
      // 0a1b: ldc_w -4.448
      // 0a1e: ldc_w -14.969
      // 0a21: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a24: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a27: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a2a: aastore
      // 0a2b: dup
      // 0a2c: bipush 36
      // 0a2e: new net/minecraft/client/animation/Keyframe
      // 0a31: dup
      // 0a32: ldc 1.5
      // 0a34: ldc_w 4.85
      // 0a37: ldc_w -6.43
      // 0a3a: ldc_w -14.798
      // 0a3d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a40: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a43: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a46: aastore
      // 0a47: dup
      // 0a48: bipush 37
      // 0a4a: new net/minecraft/client/animation/Keyframe
      // 0a4d: dup
      // 0a4e: ldc 1.54
      // 0a50: ldc_w 4.755
      // 0a53: ldc_w -6.377
      // 0a56: ldc_w -14.33
      // 0a59: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a5c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a5f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a62: aastore
      // 0a63: dup
      // 0a64: bipush 38
      // 0a66: new net/minecraft/client/animation/Keyframe
      // 0a69: dup
      // 0a6a: ldc 1.58
      // 0a6c: ldc_w 4.491
      // 0a6f: ldc_w -6.208
      // 0a72: ldc_w -13.409
      // 0a75: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a78: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a7b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a7e: aastore
      // 0a7f: dup
      // 0a80: bipush 39
      // 0a82: new net/minecraft/client/animation/Keyframe
      // 0a85: dup
      // 0a86: ldc 1.62
      // 0a88: ldc_w 4.092
      // 0a8b: ldc_w -5.907
      // 0a8e: ldc_w -12.131
      // 0a91: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a9a: aastore
      // 0a9b: dup
      // 0a9c: bipush 40
      // 0a9e: new net/minecraft/client/animation/Keyframe
      // 0aa1: dup
      // 0aa2: ldc 1.67
      // 0aa4: ldc_w 3.593
      // 0aa7: ldc_w -5.456
      // 0aaa: ldc_w -10.587
      // 0aad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ab0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ab3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ab6: aastore
      // 0ab7: dup
      // 0ab8: bipush 41
      // 0aba: new net/minecraft/client/animation/Keyframe
      // 0abd: dup
      // 0abe: ldc 1.71
      // 0ac0: ldc_w 3.026
      // 0ac3: ldc_w -4.844
      // 0ac6: ldc_w -8.873
      // 0ac9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0acc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0acf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ad2: aastore
      // 0ad3: dup
      // 0ad4: bipush 42
      // 0ad6: new net/minecraft/client/animation/Keyframe
      // 0ad9: dup
      // 0ada: ldc 1.75
      // 0adc: ldc_w 2.425
      // 0adf: ldc_w -4.069
      // 0ae2: ldc_w -7.083
      // 0ae5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ae8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0aeb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aee: aastore
      // 0aef: dup
      // 0af0: bipush 43
      // 0af2: new net/minecraft/client/animation/Keyframe
      // 0af5: dup
      // 0af6: ldc 1.79
      // 0af8: ldc_w 1.824
      // 0afb: ldc_w -3.159
      // 0afe: ldc_w -5.311
      // 0b01: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b0a: aastore
      // 0b0b: dup
      // 0b0c: bipush 44
      // 0b0e: new net/minecraft/client/animation/Keyframe
      // 0b11: dup
      // 0b12: ldc 1.83
      // 0b14: ldc_w 1.257
      // 0b17: ldc_w -2.189
      // 0b1a: ldc_w -3.649
      // 0b1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b26: aastore
      // 0b27: dup
      // 0b28: bipush 45
      // 0b2a: new net/minecraft/client/animation/Keyframe
      // 0b2d: dup
      // 0b2e: ldc 1.92
      // 0b30: ldc_w 0.359
      // 0b33: ldc_w -0.573
      // 0b36: ldc_w -1.038
      // 0b39: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b42: aastore
      // 0b43: dup
      // 0b44: bipush 46
      // 0b46: new net/minecraft/client/animation/Keyframe
      // 0b49: dup
      // 0b4a: ldc 1.96
      // 0b4c: ldc_w 0.095
      // 0b4f: ldc_w -0.14
      // 0b52: ldc_w -0.275
      // 0b55: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b5e: aastore
      // 0b5f: dup
      // 0b60: bipush 47
      // 0b62: new net/minecraft/client/animation/Keyframe
      // 0b65: dup
      // 0b66: fconst_2
      // 0b67: fconst_0
      // 0b68: fconst_0
      // 0b69: fconst_0
      // 0b6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b73: aastore
      // 0b74: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0b77: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0b7a: ldc_w "CTR_chest"
      // 0b7d: new net/minecraft/client/animation/AnimationChannel
      // 0b80: dup
      // 0b81: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0b84: bipush 48
      // 0b86: anewarray 27
      // 0b89: dup
      // 0b8a: bipush 0
      // 0b8b: new net/minecraft/client/animation/Keyframe
      // 0b8e: dup
      // 0b8f: fconst_0
      // 0b90: ldc_w 0.692
      // 0b93: ldc_w 4.888
      // 0b96: ldc_w 0.106
      // 0b99: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b9c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b9f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ba2: aastore
      // 0ba3: dup
      // 0ba4: bipush 1
      // 0ba5: new net/minecraft/client/animation/Keyframe
      // 0ba8: dup
      // 0ba9: ldc 0.04
      // 0bab: ldc_w -4.323
      // 0bae: ldc_w 5.059
      // 0bb1: ldc_w -0.211
      // 0bb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bbd: aastore
      // 0bbe: dup
      // 0bbf: bipush 2
      // 0bc0: new net/minecraft/client/animation/Keyframe
      // 0bc3: dup
      // 0bc4: ldc 0.08
      // 0bc6: ldc_w -6.742
      // 0bc9: ldc_w 5.522
      // 0bcc: ldc_w -1.075
      // 0bcf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bd2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bd5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bd8: aastore
      // 0bd9: dup
      // 0bda: bipush 3
      // 0bdb: new net/minecraft/client/animation/Keyframe
      // 0bde: dup
      // 0bdf: ldc 0.12
      // 0be1: ldc_w -7.848
      // 0be4: ldc_w 6.202
      // 0be7: ldc_w -2.354
      // 0bea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bf0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bf3: aastore
      // 0bf4: dup
      // 0bf5: bipush 4
      // 0bf6: new net/minecraft/client/animation/Keyframe
      // 0bf9: dup
      // 0bfa: ldc 0.17
      // 0bfc: ldc_w -8.269
      // 0bff: ldc_w 7.023
      // 0c02: ldc_w -3.914
      // 0c05: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c0e: aastore
      // 0c0f: dup
      // 0c10: bipush 5
      // 0c11: new net/minecraft/client/animation/Keyframe
      // 0c14: dup
      // 0c15: ldc 0.25
      // 0c17: ldc_w -8.49
      // 0c1a: ldc_w 8.784
      // 0c1d: ldc_w -7.345
      // 0c20: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c29: aastore
      // 0c2a: dup
      // 0c2b: bipush 6
      // 0c2d: new net/minecraft/client/animation/Keyframe
      // 0c30: dup
      // 0c31: ldc 0.29
      // 0c33: ldc_w -8.804
      // 0c36: ldc_w 9.577
      // 0c39: ldc_w -8.939
      // 0c3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c45: aastore
      // 0c46: dup
      // 0c47: bipush 7
      // 0c49: new net/minecraft/client/animation/Keyframe
      // 0c4c: dup
      // 0c4d: ldc 0.33
      // 0c4f: ldc_w -9.589
      // 0c52: ldc_w 10.221
      // 0c55: ldc_w -10.262
      // 0c58: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c61: aastore
      // 0c62: dup
      // 0c63: bipush 8
      // 0c65: new net/minecraft/client/animation/Keyframe
      // 0c68: dup
      // 0c69: ldc 0.38
      // 0c6b: ldc_w -11.192
      // 0c6e: ldc_w 10.652
      // 0c71: ldc_w -11.165
      // 0c74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c7d: aastore
      // 0c7e: dup
      // 0c7f: bipush 9
      // 0c81: new net/minecraft/client/animation/Keyframe
      // 0c84: dup
      // 0c85: ldc 0.42
      // 0c87: ldc_w -14.221
      // 0c8a: ldc_w 10.81
      // 0c8d: ldc_w -11.499
      // 0c90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c99: aastore
      // 0c9a: dup
      // 0c9b: bipush 10
      // 0c9d: new net/minecraft/client/animation/Keyframe
      // 0ca0: dup
      // 0ca1: ldc 0.46
      // 0ca3: ldc_w -19.673
      // 0ca6: ldc_w 10.263
      // 0ca9: ldc_w -10.005
      // 0cac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0caf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cb5: aastore
      // 0cb6: dup
      // 0cb7: bipush 11
      // 0cb9: new net/minecraft/client/animation/Keyframe
      // 0cbc: dup
      // 0cbd: ldc 0.5
      // 0cbf: ldc_w -25.297
      // 0cc2: ldc_w 8.705
      // 0cc5: ldc_w -6.021
      // 0cc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ccb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cd1: aastore
      // 0cd2: dup
      // 0cd3: bipush 12
      // 0cd5: new net/minecraft/client/animation/Keyframe
      // 0cd8: dup
      // 0cd9: ldc 0.54
      // 0cdb: ldc_w -27.976
      // 0cde: ldc_w 6.261
      // 0ce1: ldc_w -0.307
      // 0ce4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ce7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ced: aastore
      // 0cee: dup
      // 0cef: bipush 13
      // 0cf1: new net/minecraft/client/animation/Keyframe
      // 0cf4: dup
      // 0cf5: ldc 0.58
      // 0cf7: ldc_w -28.76
      // 0cfa: ldc_w 3.157
      // 0cfd: ldc_w 6.42
      // 0d00: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d09: aastore
      // 0d0a: dup
      // 0d0b: bipush 14
      // 0d0d: new net/minecraft/client/animation/Keyframe
      // 0d10: dup
      // 0d11: ldc 0.62
      // 0d13: ldc_w -28.512
      // 0d16: ldc_w -0.263
      // 0d19: ldc_w 13.522
      // 0d1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d25: aastore
      // 0d26: dup
      // 0d27: bipush 15
      // 0d29: new net/minecraft/client/animation/Keyframe
      // 0d2c: dup
      // 0d2d: ldc 0.67
      // 0d2f: ldc_w -27.827
      // 0d32: ldc_w -3.602
      // 0d35: ldc_w 20.428
      // 0d38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d41: aastore
      // 0d42: dup
      // 0d43: bipush 16
      // 0d45: new net/minecraft/client/animation/Keyframe
      // 0d48: dup
      // 0d49: ldc 0.71
      // 0d4b: ldc_w -27.037
      // 0d4e: ldc_w -6.536
      // 0d51: ldc_w 26.562
      // 0d54: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d5d: aastore
      // 0d5e: dup
      // 0d5f: bipush 17
      // 0d61: new net/minecraft/client/animation/Keyframe
      // 0d64: dup
      // 0d65: ldc 0.75
      // 0d67: ldc_w -26.269
      // 0d6a: ldc_w -8.901
      // 0d6d: ldc_w 31.304
      // 0d70: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d79: aastore
      // 0d7a: dup
      // 0d7b: bipush 18
      // 0d7d: new net/minecraft/client/animation/Keyframe
      // 0d80: dup
      // 0d81: ldc 0.79
      // 0d83: ldc_w -25.548
      // 0d86: ldc_w -10.718
      // 0d89: ldc_w 33.982
      // 0d8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d95: aastore
      // 0d96: dup
      // 0d97: bipush 19
      // 0d99: new net/minecraft/client/animation/Keyframe
      // 0d9c: dup
      // 0d9d: ldc 0.83
      // 0d9f: ldc_w -25.742
      // 0da2: ldc_w -12.099
      // 0da5: ldc_w 35.015
      // 0da8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0db1: aastore
      // 0db2: dup
      // 0db3: bipush 20
      // 0db5: new net/minecraft/client/animation/Keyframe
      // 0db8: dup
      // 0db9: ldc 0.88
      // 0dbb: ldc_w -26.286
      // 0dbe: ldc_w -13.314
      // 0dc1: ldc_w 35.482
      // 0dc4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dc7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dcd: aastore
      // 0dce: dup
      // 0dcf: bipush 21
      // 0dd1: new net/minecraft/client/animation/Keyframe
      // 0dd4: dup
      // 0dd5: ldc 0.92
      // 0dd7: ldc_w -27.143
      // 0dda: ldc_w -14.692
      // 0ddd: ldc_w 35.705
      // 0de0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0de3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0de6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0de9: aastore
      // 0dea: dup
      // 0deb: bipush 22
      // 0ded: new net/minecraft/client/animation/Keyframe
      // 0df0: dup
      // 0df1: ldc 0.96
      // 0df3: ldc_w -25.161
      // 0df6: ldc_w -18.952
      // 0df9: ldc_w 18.275
      // 0dfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e05: aastore
      // 0e06: dup
      // 0e07: bipush 23
      // 0e09: new net/minecraft/client/animation/Keyframe
      // 0e0c: dup
      // 0e0d: fconst_1
      // 0e0e: ldc_w -16.224
      // 0e11: ldc_w -23.318
      // 0e14: ldc_w -21.604
      // 0e17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e20: aastore
      // 0e21: dup
      // 0e22: bipush 24
      // 0e24: new net/minecraft/client/animation/Keyframe
      // 0e27: dup
      // 0e28: ldc 1.04
      // 0e2a: ldc_w -8.171
      // 0e2d: ldc_w -19.342
      // 0e30: ldc_w -61.509
      // 0e33: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e3c: aastore
      // 0e3d: dup
      // 0e3e: bipush 25
      // 0e40: new net/minecraft/client/animation/Keyframe
      // 0e43: dup
      // 0e44: ldc 1.08
      // 0e46: ldc_w -5.932
      // 0e49: ldc_w -14.846
      // 0e4c: ldc_w -78.904
      // 0e4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e58: aastore
      // 0e59: dup
      // 0e5a: bipush 26
      // 0e5c: new net/minecraft/client/animation/Keyframe
      // 0e5f: dup
      // 0e60: ldc 1.12
      // 0e62: ldc_w -4.315
      // 0e65: ldc_w -15.759
      // 0e68: ldc_w -78.855
      // 0e6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e74: aastore
      // 0e75: dup
      // 0e76: bipush 27
      // 0e78: new net/minecraft/client/animation/Keyframe
      // 0e7b: dup
      // 0e7c: ldc 1.17
      // 0e7e: ldc_w -3.811
      // 0e81: ldc_w -17.142
      // 0e84: ldc_w -77.09
      // 0e87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e90: aastore
      // 0e91: dup
      // 0e92: bipush 28
      // 0e94: new net/minecraft/client/animation/Keyframe
      // 0e97: dup
      // 0e98: ldc 1.21
      // 0e9a: ldc_w -4.271
      // 0e9d: ldc_w -19.05
      // 0ea0: ldc_w -73.691
      // 0ea3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ea6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ea9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0eac: aastore
      // 0ead: dup
      // 0eae: bipush 29
      // 0eb0: new net/minecraft/client/animation/Keyframe
      // 0eb3: dup
      // 0eb4: ldc 1.25
      // 0eb6: ldc_w -4.756
      // 0eb9: ldc_w -19.909
      // 0ebc: ldc_w -72.025
      // 0ebf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ec2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ec5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ec8: aastore
      // 0ec9: dup
      // 0eca: bipush 30
      // 0ecc: new net/minecraft/client/animation/Keyframe
      // 0ecf: dup
      // 0ed0: ldc 1.29
      // 0ed2: ldc_w -5.191
      // 0ed5: ldc_w -19.621
      // 0ed8: ldc_w -72.233
      // 0edb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ede: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ee1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ee4: aastore
      // 0ee5: dup
      // 0ee6: bipush 31
      // 0ee8: new net/minecraft/client/animation/Keyframe
      // 0eeb: dup
      // 0eec: ldc 1.33
      // 0eee: ldc_w -6.09
      // 0ef1: ldc_w -18.858
      // 0ef4: ldc_w -72.738
      // 0ef7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0efa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0efd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f00: aastore
      // 0f01: dup
      // 0f02: bipush 32
      // 0f04: new net/minecraft/client/animation/Keyframe
      // 0f07: dup
      // 0f08: ldc 1.38
      // 0f0a: ldc_w -7.449
      // 0f0d: ldc_w -17.779
      // 0f10: ldc_w -73.364
      // 0f13: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f1c: aastore
      // 0f1d: dup
      // 0f1e: bipush 33
      // 0f20: new net/minecraft/client/animation/Keyframe
      // 0f23: dup
      // 0f24: ldc 1.42
      // 0f26: ldc_w -9.255
      // 0f29: ldc_w -16.537
      // 0f2c: ldc_w -73.941
      // 0f2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f38: aastore
      // 0f39: dup
      // 0f3a: bipush 34
      // 0f3c: new net/minecraft/client/animation/Keyframe
      // 0f3f: dup
      // 0f40: ldc 1.46
      // 0f42: ldc_w -11.475
      // 0f45: ldc_w -15.256
      // 0f48: ldc_w -74.287
      // 0f4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f54: aastore
      // 0f55: dup
      // 0f56: bipush 35
      // 0f58: new net/minecraft/client/animation/Keyframe
      // 0f5b: dup
      // 0f5c: ldc 1.5
      // 0f5e: ldc_w -14.064
      // 0f61: ldc_w -14.02
      // 0f64: ldc_w -74.207
      // 0f67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f70: aastore
      // 0f71: dup
      // 0f72: bipush 36
      // 0f74: new net/minecraft/client/animation/Keyframe
      // 0f77: dup
      // 0f78: ldc 1.54
      // 0f7a: ldc_w -16.48
      // 0f7d: ldc_w -13.114
      // 0f80: ldc_w -72.457
      // 0f83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f8c: aastore
      // 0f8d: dup
      // 0f8e: bipush 37
      // 0f90: new net/minecraft/client/animation/Keyframe
      // 0f93: dup
      // 0f94: ldc 1.58
      // 0f96: ldc_w -18.188
      // 0f99: ldc_w -12.44
      // 0f9c: ldc_w -68.224
      // 0f9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fa2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fa5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fa8: aastore
      // 0fa9: dup
      // 0faa: bipush 38
      // 0fac: new net/minecraft/client/animation/Keyframe
      // 0faf: dup
      // 0fb0: ldc 1.62
      // 0fb2: ldc_w -19.129
      // 0fb5: ldc_w -11.541
      // 0fb8: ldc_w -61.987
      // 0fbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fc4: aastore
      // 0fc5: dup
      // 0fc6: bipush 39
      // 0fc8: new net/minecraft/client/animation/Keyframe
      // 0fcb: dup
      // 0fcc: ldc 1.67
      // 0fce: ldc_w -19.201
      // 0fd1: ldc_w -10.117
      // 0fd4: ldc_w -54.274
      // 0fd7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fdd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fe0: aastore
      // 0fe1: dup
      // 0fe2: bipush 40
      // 0fe4: new net/minecraft/client/animation/Keyframe
      // 0fe7: dup
      // 0fe8: ldc 1.71
      // 0fea: ldc_w -18.288
      // 0fed: ldc_w -8.078
      // 0ff0: ldc_w -45.63
      // 0ff3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ff6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ff9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ffc: aastore
      // 0ffd: dup
      // 0ffe: bipush 41
      // 1000: new net/minecraft/client/animation/Keyframe
      // 1003: dup
      // 1004: ldc 1.75
      // 1006: ldc_w -16.338
      // 1009: ldc_w -5.549
      // 100c: ldc_w -36.571
      // 100f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1012: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1015: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1018: aastore
      // 1019: dup
      // 101a: bipush 42
      // 101c: new net/minecraft/client/animation/Keyframe
      // 101f: dup
      // 1020: ldc 1.79
      // 1022: ldc_w -13.442
      // 1025: ldc_w -2.81
      // 1028: ldc_w -27.563
      // 102b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 102e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1031: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1034: aastore
      // 1035: dup
      // 1036: bipush 43
      // 1038: new net/minecraft/client/animation/Keyframe
      // 103b: dup
      // 103c: ldc 1.83
      // 103e: ldc_w -9.876
      // 1041: ldc_w -0.196
      // 1044: ldc_w -19.051
      // 1047: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 104a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 104d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1050: aastore
      // 1051: dup
      // 1052: bipush 44
      // 1054: new net/minecraft/client/animation/Keyframe
      // 1057: dup
      // 1058: ldc 1.88
      // 105a: ldc_w -6.095
      // 105d: ldc_w 2.002
      // 1060: ldc_w -11.5
      // 1063: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1066: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1069: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 106c: aastore
      // 106d: dup
      // 106e: bipush 45
      // 1070: new net/minecraft/client/animation/Keyframe
      // 1073: dup
      // 1074: ldc 1.92
      // 1076: ldc_w -2.675
      // 1079: ldc_w 3.609
      // 107c: ldc_w -5.422
      // 107f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1082: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1085: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1088: aastore
      // 1089: dup
      // 108a: bipush 46
      // 108c: new net/minecraft/client/animation/Keyframe
      // 108f: dup
      // 1090: ldc 1.96
      // 1092: ldc_w -0.227
      // 1095: ldc_w 4.568
      // 1098: ldc_w -1.367
      // 109b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 109e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10a4: aastore
      // 10a5: dup
      // 10a6: bipush 47
      // 10a8: new net/minecraft/client/animation/Keyframe
      // 10ab: dup
      // 10ac: fconst_2
      // 10ad: ldc_w 0.692
      // 10b0: ldc_w 4.888
      // 10b3: ldc_w 0.106
      // 10b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10bf: aastore
      // 10c0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 10c3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 10c6: ldc_w "CTR_head"
      // 10c9: new net/minecraft/client/animation/AnimationChannel
      // 10cc: dup
      // 10cd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 10d0: bipush 49
      // 10d2: anewarray 27
      // 10d5: dup
      // 10d6: bipush 0
      // 10d7: new net/minecraft/client/animation/Keyframe
      // 10da: dup
      // 10db: fconst_0
      // 10dc: ldc_w 0.412
      // 10df: ldc_w -4.079
      // 10e2: ldc_w -4.432
      // 10e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10ee: aastore
      // 10ef: dup
      // 10f0: bipush 1
      // 10f1: new net/minecraft/client/animation/Keyframe
      // 10f4: dup
      // 10f5: ldc 0.04
      // 10f7: ldc_w 3.59
      // 10fa: ldc_w -3.597
      // 10fd: ldc_w -4.4
      // 1100: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1103: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1106: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1109: aastore
      // 110a: dup
      // 110b: bipush 2
      // 110c: new net/minecraft/client/animation/Keyframe
      // 110f: dup
      // 1110: ldc 0.08
      // 1112: ldc_w 2.544
      // 1115: ldc_w -2.751
      // 1118: ldc_w -2.818
      // 111b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 111e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1121: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1124: aastore
      // 1125: dup
      // 1126: bipush 3
      // 1127: new net/minecraft/client/animation/Keyframe
      // 112a: dup
      // 112b: ldc 0.12
      // 112d: ldc_w 0.034
      // 1130: ldc_w -2.173
      // 1133: ldc_w -0.109
      // 1136: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1139: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 113c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 113f: aastore
      // 1140: dup
      // 1141: bipush 4
      // 1142: new net/minecraft/client/animation/Keyframe
      // 1145: dup
      // 1146: ldc 0.17
      // 1148: ldc_w -1.806
      // 114b: ldc_w -2.402
      // 114e: ldc_w 3.403
      // 1151: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1154: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1157: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 115a: aastore
      // 115b: dup
      // 115c: bipush 5
      // 115d: new net/minecraft/client/animation/Keyframe
      // 1160: dup
      // 1161: ldc 0.21
      // 1163: ldc_w -0.192
      // 1166: ldc_w -4.434
      // 1169: ldc_w 7.453
      // 116c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 116f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1172: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1175: aastore
      // 1176: dup
      // 1177: bipush 6
      // 1179: new net/minecraft/client/animation/Keyframe
      // 117c: dup
      // 117d: ldc 0.25
      // 117f: ldc_w 5.083
      // 1182: ldc_w -8.417
      // 1185: ldc_w 11.413
      // 1188: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 118b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 118e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1191: aastore
      // 1192: dup
      // 1193: bipush 7
      // 1195: new net/minecraft/client/animation/Keyframe
      // 1198: dup
      // 1199: ldc 0.29
      // 119b: ldc_w 12.022
      // 119e: ldc_w -13.268
      // 11a1: ldc_w 14.933
      // 11a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11ad: aastore
      // 11ae: dup
      // 11af: bipush 8
      // 11b1: new net/minecraft/client/animation/Keyframe
      // 11b4: dup
      // 11b5: ldc 0.33
      // 11b7: ldc_w 18.665
      // 11ba: ldc_w -17.784
      // 11bd: ldc_w 17.722
      // 11c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11c9: aastore
      // 11ca: dup
      // 11cb: bipush 9
      // 11cd: new net/minecraft/client/animation/Keyframe
      // 11d0: dup
      // 11d1: ldc 0.38
      // 11d3: ldc_w 23.336
      // 11d6: ldc_w -21.16
      // 11d9: ldc_w 18.905
      // 11dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11e5: aastore
      // 11e6: dup
      // 11e7: bipush 10
      // 11e9: new net/minecraft/client/animation/Keyframe
      // 11ec: dup
      // 11ed: ldc 0.42
      // 11ef: ldc_w 26.533
      // 11f2: ldc_w -23.541
      // 11f5: ldc_w 17.83
      // 11f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1201: aastore
      // 1202: dup
      // 1203: bipush 11
      // 1205: new net/minecraft/client/animation/Keyframe
      // 1208: dup
      // 1209: ldc 0.46
      // 120b: ldc_w 31.766
      // 120e: ldc_w -25.256
      // 1211: ldc_w 12.728
      // 1214: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1217: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 121a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 121d: aastore
      // 121e: dup
      // 121f: bipush 12
      // 1221: new net/minecraft/client/animation/Keyframe
      // 1224: dup
      // 1225: ldc 0.5
      // 1227: ldc_w 37.307
      // 122a: ldc_w -24.124
      // 122d: ldc_w 4.578
      // 1230: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1233: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1236: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1239: aastore
      // 123a: dup
      // 123b: bipush 13
      // 123d: new net/minecraft/client/animation/Keyframe
      // 1240: dup
      // 1241: ldc 0.54
      // 1243: ldc_w 38.718
      // 1246: ldc_w -19.469
      // 1249: ldc_w -2.977
      // 124c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 124f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1252: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1255: aastore
      // 1256: dup
      // 1257: bipush 14
      // 1259: new net/minecraft/client/animation/Keyframe
      // 125c: dup
      // 125d: ldc 0.58
      // 125f: ldc_w 45.599
      // 1262: ldc_w -12.366
      // 1265: ldc_w -10.309
      // 1268: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 126b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 126e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1271: aastore
      // 1272: dup
      // 1273: bipush 15
      // 1275: new net/minecraft/client/animation/Keyframe
      // 1278: dup
      // 1279: ldc 0.62
      // 127b: ldc_w 49.672
      // 127e: ldc_w -4.536
      // 1281: ldc_w -16.919
      // 1284: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1287: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 128a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 128d: aastore
      // 128e: dup
      // 128f: bipush 16
      // 1291: new net/minecraft/client/animation/Keyframe
      // 1294: dup
      // 1295: ldc 0.67
      // 1297: ldc_w 51.6
      // 129a: ldc_w 2.954
      // 129d: ldc_w -23.16
      // 12a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12a9: aastore
      // 12aa: dup
      // 12ab: bipush 17
      // 12ad: new net/minecraft/client/animation/Keyframe
      // 12b0: dup
      // 12b1: ldc 0.71
      // 12b3: ldc_w 51.936
      // 12b6: ldc_w 9.368
      // 12b9: ldc_w -28.993
      // 12bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12c5: aastore
      // 12c6: dup
      // 12c7: bipush 18
      // 12c9: new net/minecraft/client/animation/Keyframe
      // 12cc: dup
      // 12cd: ldc 0.75
      // 12cf: ldc_w 51.141
      // 12d2: ldc_w 14.305
      // 12d5: ldc_w -33.94
      // 12d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12e1: aastore
      // 12e2: dup
      // 12e3: bipush 19
      // 12e5: new net/minecraft/client/animation/Keyframe
      // 12e8: dup
      // 12e9: ldc 0.79
      // 12eb: ldc_w 49.579
      // 12ee: ldc_w 17.663
      // 12f1: ldc_w -37.19
      // 12f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12fd: aastore
      // 12fe: dup
      // 12ff: bipush 20
      // 1301: new net/minecraft/client/animation/Keyframe
      // 1304: dup
      // 1305: ldc 0.83
      // 1307: ldc_w 49.335
      // 130a: ldc_w 19.823
      // 130d: ldc_w -38.632
      // 1310: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1313: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1316: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1319: aastore
      // 131a: dup
      // 131b: bipush 21
      // 131d: new net/minecraft/client/animation/Keyframe
      // 1320: dup
      // 1321: ldc 0.88
      // 1323: ldc_w 48.619
      // 1326: ldc_w 21.674
      // 1329: ldc_w -39.436
      // 132c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 132f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1332: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1335: aastore
      // 1336: dup
      // 1337: bipush 22
      // 1339: new net/minecraft/client/animation/Keyframe
      // 133c: dup
      // 133d: ldc 0.92
      // 133f: ldc_w 47.67
      // 1342: ldc_w 23.529
      // 1345: ldc_w -40.27
      // 1348: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 134b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 134e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1351: aastore
      // 1352: dup
      // 1353: bipush 23
      // 1355: new net/minecraft/client/animation/Keyframe
      // 1358: dup
      // 1359: ldc 0.96
      // 135b: ldc_w 46.855
      // 135e: ldc_w 23.364
      // 1361: ldc_w -23.697
      // 1364: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1367: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 136a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 136d: aastore
      // 136e: dup
      // 136f: bipush 24
      // 1371: new net/minecraft/client/animation/Keyframe
      // 1374: dup
      // 1375: fconst_1
      // 1376: ldc_w 36.011
      // 1379: ldc_w 17.2
      // 137c: ldc_w 14.359
      // 137f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1382: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1385: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1388: aastore
      // 1389: dup
      // 138a: bipush 25
      // 138c: new net/minecraft/client/animation/Keyframe
      // 138f: dup
      // 1390: ldc 1.04
      // 1392: ldc_w 21.866
      // 1395: ldc_w 4.425
      // 1398: ldc_w 56.907
      // 139b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 139e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13a4: aastore
      // 13a5: dup
      // 13a6: bipush 26
      // 13a8: new net/minecraft/client/animation/Keyframe
      // 13ab: dup
      // 13ac: ldc 1.08
      // 13ae: ldc_w 12.953
      // 13b1: ldc_w -1.327
      // 13b4: ldc_w 79.214
      // 13b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13c0: aastore
      // 13c1: dup
      // 13c2: bipush 27
      // 13c4: new net/minecraft/client/animation/Keyframe
      // 13c7: dup
      // 13c8: ldc 1.12
      // 13ca: ldc_w 13.511
      // 13cd: ldc_w -0.434
      // 13d0: ldc_w 78.516
      // 13d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13dc: aastore
      // 13dd: dup
      // 13de: bipush 28
      // 13e0: new net/minecraft/client/animation/Keyframe
      // 13e3: dup
      // 13e4: ldc 1.17
      // 13e6: ldc_w 15.136
      // 13e9: ldc_w 1.18
      // 13ec: ldc_w 75.682
      // 13ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13f8: aastore
      // 13f9: dup
      // 13fa: bipush 29
      // 13fc: new net/minecraft/client/animation/Keyframe
      // 13ff: dup
      // 1400: ldc 1.21
      // 1402: ldc_w 17.963
      // 1405: ldc_w 2.977
      // 1408: ldc_w 71.148
      // 140b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 140e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1411: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1414: aastore
      // 1415: dup
      // 1416: bipush 30
      // 1418: new net/minecraft/client/animation/Keyframe
      // 141b: dup
      // 141c: ldc 1.25
      // 141e: ldc_w 17.731
      // 1421: ldc_w 4.633
      // 1424: ldc_w 69.311
      // 1427: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 142a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 142d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1430: aastore
      // 1431: dup
      // 1432: bipush 31
      // 1434: new net/minecraft/client/animation/Keyframe
      // 1437: dup
      // 1438: ldc 1.29
      // 143a: ldc_w 17.86
      // 143d: ldc_w 4.557
      // 1440: ldc_w 69.451
      // 1443: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1446: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1449: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 144c: aastore
      // 144d: dup
      // 144e: bipush 32
      // 1450: new net/minecraft/client/animation/Keyframe
      // 1453: dup
      // 1454: ldc 1.33
      // 1456: ldc_w 19.1
      // 1459: ldc_w 2.201
      // 145c: ldc_w 69.875
      // 145f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1462: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1465: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1468: aastore
      // 1469: dup
      // 146a: bipush 33
      // 146c: new net/minecraft/client/animation/Keyframe
      // 146f: dup
      // 1470: ldc 1.38
      // 1472: ldc_w 21.842
      // 1475: ldc_w -1.963
      // 1478: ldc_w 69.977
      // 147b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 147e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1481: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1484: aastore
      // 1485: dup
      // 1486: bipush 34
      // 1488: new net/minecraft/client/animation/Keyframe
      // 148b: dup
      // 148c: ldc 1.42
      // 148e: ldc_w 25.954
      // 1491: ldc_w -7.137
      // 1494: ldc_w 69.045
      // 1497: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 149a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 149d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14a0: aastore
      // 14a1: dup
      // 14a2: bipush 35
      // 14a4: new net/minecraft/client/animation/Keyframe
      // 14a7: dup
      // 14a8: ldc 1.46
      // 14aa: ldc_w 31.669
      // 14ad: ldc_w -9.36
      // 14b0: ldc_w 71.062
      // 14b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14bc: aastore
      // 14bd: dup
      // 14be: bipush 36
      // 14c0: new net/minecraft/client/animation/Keyframe
      // 14c3: dup
      // 14c4: ldc 1.5
      // 14c6: ldc_w 33.264
      // 14c9: ldc_w -10.812
      // 14cc: ldc_w 73.554
      // 14cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14d8: aastore
      // 14d9: dup
      // 14da: bipush 37
      // 14dc: new net/minecraft/client/animation/Keyframe
      // 14df: dup
      // 14e0: ldc 1.54
      // 14e2: ldc_w 31.015
      // 14e5: ldc_w -10.164
      // 14e8: ldc_w 74.979
      // 14eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14f4: aastore
      // 14f5: dup
      // 14f6: bipush 38
      // 14f8: new net/minecraft/client/animation/Keyframe
      // 14fb: dup
      // 14fc: ldc 1.58
      // 14fe: ldc_w 27.757
      // 1501: ldc_w -7.446
      // 1504: ldc_w 73.56
      // 1507: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 150a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 150d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1510: aastore
      // 1511: dup
      // 1512: bipush 39
      // 1514: new net/minecraft/client/animation/Keyframe
      // 1517: dup
      // 1518: ldc 1.62
      // 151a: ldc_w 25.417
      // 151d: ldc_w -6.206
      // 1520: ldc_w 66.639
      // 1523: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1526: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1529: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 152c: aastore
      // 152d: dup
      // 152e: bipush 40
      // 1530: new net/minecraft/client/animation/Keyframe
      // 1533: dup
      // 1534: ldc 1.67
      // 1536: ldc_w 22.609
      // 1539: ldc_w -4.167
      // 153c: ldc_w 57.902
      // 153f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1542: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1545: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1548: aastore
      // 1549: dup
      // 154a: bipush 41
      // 154c: new net/minecraft/client/animation/Keyframe
      // 154f: dup
      // 1550: ldc 1.71
      // 1552: ldc_w 19.096
      // 1555: ldc_w -1.892
      // 1558: ldc_w 47.918
      // 155b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 155e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1561: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1564: aastore
      // 1565: dup
      // 1566: bipush 42
      // 1568: new net/minecraft/client/animation/Keyframe
      // 156b: dup
      // 156c: ldc 1.75
      // 156e: ldc_w 14.94
      // 1571: ldc_w -0.028
      // 1574: ldc_w 37.266
      // 1577: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 157a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 157d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1580: aastore
      // 1581: dup
      // 1582: bipush 43
      // 1584: new net/minecraft/client/animation/Keyframe
      // 1587: dup
      // 1588: ldc 1.79
      // 158a: ldc_w 10.491
      // 158d: ldc_w 0.903
      // 1590: ldc_w 26.59
      // 1593: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1596: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1599: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 159c: aastore
      // 159d: dup
      // 159e: bipush 44
      // 15a0: new net/minecraft/client/animation/Keyframe
      // 15a3: dup
      // 15a4: ldc 1.83
      // 15a6: ldc_w 6.283
      // 15a9: ldc_w 0.699
      // 15ac: ldc_w 16.594
      // 15af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15b8: aastore
      // 15b9: dup
      // 15ba: bipush 45
      // 15bc: new net/minecraft/client/animation/Keyframe
      // 15bf: dup
      // 15c0: ldc 1.88
      // 15c2: ldc_w 2.86
      // 15c5: ldc_w -0.448
      // 15c8: ldc_w 7.962
      // 15cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15d4: aastore
      // 15d5: dup
      // 15d6: bipush 46
      // 15d8: new net/minecraft/client/animation/Keyframe
      // 15db: dup
      // 15dc: ldc 1.92
      // 15de: ldc_w 0.619
      // 15e1: ldc_w -2.034
      // 15e4: ldc_w 1.271
      // 15e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15f0: aastore
      // 15f1: dup
      // 15f2: bipush 47
      // 15f4: new net/minecraft/client/animation/Keyframe
      // 15f7: dup
      // 15f8: ldc 1.96
      // 15fa: ldc_w -0.228
      // 15fd: ldc_w -3.428
      // 1600: ldc_w -3.005
      // 1603: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1606: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1609: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 160c: aastore
      // 160d: dup
      // 160e: bipush 48
      // 1610: new net/minecraft/client/animation/Keyframe
      // 1613: dup
      // 1614: fconst_2
      // 1615: ldc_w 0.412
      // 1618: ldc_w -4.079
      // 161b: ldc_w -4.432
      // 161e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1621: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1624: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1627: aastore
      // 1628: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 162b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 162e: ldc_w "CTR_head"
      // 1631: new net/minecraft/client/animation/AnimationChannel
      // 1634: dup
      // 1635: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1638: bipush 1
      // 1639: anewarray 27
      // 163c: dup
      // 163d: bipush 0
      // 163e: new net/minecraft/client/animation/Keyframe
      // 1641: dup
      // 1642: ldc 0.79
      // 1644: fconst_0
      // 1645: fconst_0
      // 1646: fconst_0
      // 1647: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 164a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 164d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1650: aastore
      // 1651: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1654: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1657: ldc_w "CTR_shoulde1r_left"
      // 165a: new net/minecraft/client/animation/AnimationChannel
      // 165d: dup
      // 165e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1661: bipush 9
      // 1663: anewarray 27
      // 1666: dup
      // 1667: bipush 0
      // 1668: new net/minecraft/client/animation/Keyframe
      // 166b: dup
      // 166c: ldc 0.46
      // 166e: fconst_0
      // 166f: fconst_0
      // 1670: fconst_0
      // 1671: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1674: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1677: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 167a: aastore
      // 167b: dup
      // 167c: bipush 1
      // 167d: new net/minecraft/client/animation/Keyframe
      // 1680: dup
      // 1681: ldc 0.5
      // 1683: ldc_w -0.038
      // 1686: ldc_w 0.129
      // 1689: ldc_w -0.015
      // 168c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 168f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1692: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1695: aastore
      // 1696: dup
      // 1697: bipush 2
      // 1698: new net/minecraft/client/animation/Keyframe
      // 169b: dup
      // 169c: ldc 0.54
      // 169e: ldc_w -0.139
      // 16a1: ldc_w 0.471
      // 16a4: ldc_w -0.053
      // 16a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16b0: aastore
      // 16b1: dup
      // 16b2: bipush 3
      // 16b3: new net/minecraft/client/animation/Keyframe
      // 16b6: dup
      // 16b7: ldc 0.62
      // 16b9: ldc_w -0.446
      // 16bc: ldc_w 1.506
      // 16bf: ldc_w -0.171
      // 16c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16cb: aastore
      // 16cc: dup
      // 16cd: bipush 4
      // 16ce: new net/minecraft/client/animation/Keyframe
      // 16d1: dup
      // 16d2: ldc 0.71
      // 16d4: ldc_w -0.753
      // 16d7: ldc_w 2.542
      // 16da: ldc_w -0.289
      // 16dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16e6: aastore
      // 16e7: dup
      // 16e8: bipush 5
      // 16e9: new net/minecraft/client/animation/Keyframe
      // 16ec: dup
      // 16ed: ldc 0.75
      // 16ef: ldc_w -0.854
      // 16f2: ldc_w 2.884
      // 16f5: ldc_w -0.327
      // 16f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1701: aastore
      // 1702: dup
      // 1703: bipush 6
      // 1705: new net/minecraft/client/animation/Keyframe
      // 1708: dup
      // 1709: ldc 0.79
      // 170b: ldc_w -0.892
      // 170e: ldc_w 3.013
      // 1711: ldc_w -0.342
      // 1714: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1717: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 171a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 171d: aastore
      // 171e: dup
      // 171f: bipush 7
      // 1721: new net/minecraft/client/animation/Keyframe
      // 1724: dup
      // 1725: ldc 0.96
      // 1727: ldc_w -0.892
      // 172a: ldc_w 3.013
      // 172d: ldc_w -0.342
      // 1730: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1733: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1736: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1739: aastore
      // 173a: dup
      // 173b: bipush 8
      // 173d: new net/minecraft/client/animation/Keyframe
      // 1740: dup
      // 1741: ldc 1.04
      // 1743: fconst_0
      // 1744: fconst_0
      // 1745: fconst_0
      // 1746: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1749: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 174c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 174f: aastore
      // 1750: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1753: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1756: ldc_w "CTR_shoulde1r_left"
      // 1759: new net/minecraft/client/animation/AnimationChannel
      // 175c: dup
      // 175d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1760: bipush 49
      // 1762: anewarray 27
      // 1765: dup
      // 1766: bipush 0
      // 1767: new net/minecraft/client/animation/Keyframe
      // 176a: dup
      // 176b: fconst_0
      // 176c: ldc_w -0.112
      // 176f: ldc_w -0.19
      // 1772: ldc_w -0.487
      // 1775: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1778: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 177b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 177e: aastore
      // 177f: dup
      // 1780: bipush 1
      // 1781: new net/minecraft/client/animation/Keyframe
      // 1784: dup
      // 1785: ldc 0.04
      // 1787: ldc_w -0.112
      // 178a: ldc_w -0.54
      // 178d: ldc_w -1.001
      // 1790: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1793: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1796: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1799: aastore
      // 179a: dup
      // 179b: bipush 2
      // 179c: new net/minecraft/client/animation/Keyframe
      // 179f: dup
      // 17a0: ldc 0.08
      // 17a2: ldc_w -0.109
      // 17a5: ldc_w -1.502
      // 17a8: ldc_w -2.411
      // 17ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17b4: aastore
      // 17b5: dup
      // 17b6: bipush 3
      // 17b7: new net/minecraft/client/animation/Keyframe
      // 17ba: dup
      // 17bb: ldc 0.12
      // 17bd: ldc_w -0.102
      // 17c0: ldc_w -2.939
      // 17c3: ldc_w -4.518
      // 17c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17cf: aastore
      // 17d0: dup
      // 17d1: bipush 4
      // 17d2: new net/minecraft/client/animation/Keyframe
      // 17d5: dup
      // 17d6: ldc 0.17
      // 17d8: ldc_w -0.087
      // 17db: ldc_w -4.715
      // 17de: ldc_w -7.122
      // 17e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17ea: aastore
      // 17eb: dup
      // 17ec: bipush 5
      // 17ed: new net/minecraft/client/animation/Keyframe
      // 17f0: dup
      // 17f1: ldc 0.21
      // 17f3: ldc_w -0.064
      // 17f6: ldc_w -6.695
      // 17f9: ldc_w -10.025
      // 17fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1802: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1805: aastore
      // 1806: dup
      // 1807: bipush 6
      // 1809: new net/minecraft/client/animation/Keyframe
      // 180c: dup
      // 180d: ldc 0.25
      // 180f: ldc_w -0.029
      // 1812: ldc_w -8.743
      // 1815: ldc_w -13.027
      // 1818: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 181b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 181e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1821: aastore
      // 1822: dup
      // 1823: bipush 7
      // 1825: new net/minecraft/client/animation/Keyframe
      // 1828: dup
      // 1829: ldc 0.29
      // 182b: ldc_w 0.021
      // 182e: ldc_w -10.722
      // 1831: ldc_w -15.93
      // 1834: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1837: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 183a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 183d: aastore
      // 183e: dup
      // 183f: bipush 8
      // 1841: new net/minecraft/client/animation/Keyframe
      // 1844: dup
      // 1845: ldc 0.33
      // 1847: ldc_w 0.086
      // 184a: ldc_w -12.499
      // 184d: ldc_w -18.534
      // 1850: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1853: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1856: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1859: aastore
      // 185a: dup
      // 185b: bipush 9
      // 185d: new net/minecraft/client/animation/Keyframe
      // 1860: dup
      // 1861: ldc 0.38
      // 1863: ldc 0.17
      // 1865: ldc_w -13.935
      // 1868: ldc_w -20.641
      // 186b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 186e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1871: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1874: aastore
      // 1875: dup
      // 1876: bipush 10
      // 1878: new net/minecraft/client/animation/Keyframe
      // 187b: dup
      // 187c: ldc 0.42
      // 187e: ldc_w 0.275
      // 1881: ldc_w -14.897
      // 1884: ldc_w -22.051
      // 1887: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 188a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 188d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1890: aastore
      // 1891: dup
      // 1892: bipush 11
      // 1894: new net/minecraft/client/animation/Keyframe
      // 1897: dup
      // 1898: ldc 0.46
      // 189a: ldc_w 0.403
      // 189d: ldc_w -15.248
      // 18a0: ldc_w -22.565
      // 18a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18ac: aastore
      // 18ad: dup
      // 18ae: bipush 12
      // 18b0: new net/minecraft/client/animation/Keyframe
      // 18b3: dup
      // 18b4: ldc 0.5
      // 18b6: ldc_w 0.823
      // 18b9: ldc_w -14.973
      // 18bc: ldc_w -22.24
      // 18bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18c8: aastore
      // 18c9: dup
      // 18ca: bipush 13
      // 18cc: new net/minecraft/client/animation/Keyframe
      // 18cf: dup
      // 18d0: ldc 0.54
      // 18d2: ldc_w 1.695
      // 18d5: ldc_w -14.246
      // 18d8: ldc_w -21.385
      // 18db: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18e4: aastore
      // 18e5: dup
      // 18e6: bipush 14
      // 18e8: new net/minecraft/client/animation/Keyframe
      // 18eb: dup
      // 18ec: ldc 0.58
      // 18ee: ldc_w 2.863
      // 18f1: ldc_w -13.214
      // 18f4: ldc_w -20.175
      // 18f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1900: aastore
      // 1901: dup
      // 1902: bipush 15
      // 1904: new net/minecraft/client/animation/Keyframe
      // 1907: dup
      // 1908: ldc 0.62
      // 190a: ldc_w 4.171
      // 190d: ldc_w -12.024
      // 1910: ldc_w -18.788
      // 1913: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1916: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1919: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 191c: aastore
      // 191d: dup
      // 191e: bipush 16
      // 1920: new net/minecraft/client/animation/Keyframe
      // 1923: dup
      // 1924: ldc 0.67
      // 1926: ldc_w 5.461
      // 1929: ldc_w -10.825
      // 192c: ldc_w -17.402
      // 192f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1932: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1935: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1938: aastore
      // 1939: dup
      // 193a: bipush 17
      // 193c: new net/minecraft/client/animation/Keyframe
      // 193f: dup
      // 1940: ldc 0.71
      // 1942: ldc_w 6.576
      // 1945: ldc_w -9.762
      // 1948: ldc_w -16.192
      // 194b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 194e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1951: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1954: aastore
      // 1955: dup
      // 1956: bipush 18
      // 1958: new net/minecraft/client/animation/Keyframe
      // 195b: dup
      // 195c: ldc 0.75
      // 195e: ldc_w 7.361
      // 1961: ldc_w -8.984
      // 1964: ldc_w -15.337
      // 1967: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 196a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 196d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1970: aastore
      // 1971: dup
      // 1972: bipush 19
      // 1974: new net/minecraft/client/animation/Keyframe
      // 1977: dup
      // 1978: ldc 0.79
      // 197a: ldc_w 7.657
      // 197d: ldc_w -8.638
      // 1980: ldc_w -15.012
      // 1983: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1986: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1989: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 198c: aastore
      // 198d: dup
      // 198e: bipush 20
      // 1990: new net/minecraft/client/animation/Keyframe
      // 1993: dup
      // 1994: ldc 0.83
      // 1996: ldc_w 7.284
      // 1999: ldc_w -8.59
      // 199c: ldc_w -15.172
      // 199f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19a8: aastore
      // 19a9: dup
      // 19aa: bipush 21
      // 19ac: new net/minecraft/client/animation/Keyframe
      // 19af: dup
      // 19b0: ldc 0.88
      // 19b2: ldc_w 6.385
      // 19b5: ldc_w -8.584
      // 19b8: ldc_w -15.67
      // 19bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19c4: aastore
      // 19c5: dup
      // 19c6: bipush 22
      // 19c8: new net/minecraft/client/animation/Keyframe
      // 19cb: dup
      // 19cc: ldc 0.92
      // 19ce: ldc_w 5.289
      // 19d1: ldc_w -8.577
      // 19d4: ldc_w -16.529
      // 19d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19e0: aastore
      // 19e1: dup
      // 19e2: bipush 23
      // 19e4: new net/minecraft/client/animation/Keyframe
      // 19e7: dup
      // 19e8: ldc 0.96
      // 19ea: ldc_w 4.328
      // 19ed: ldc_w -8.529
      // 19f0: ldc_w -17.774
      // 19f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19fc: aastore
      // 19fd: dup
      // 19fe: bipush 24
      // 1a00: new net/minecraft/client/animation/Keyframe
      // 1a03: dup
      // 1a04: fconst_1
      // 1a05: ldc_w 3.507
      // 1a08: ldc_w -7.883
      // 1a0b: ldc_w -19.237
      // 1a0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a17: aastore
      // 1a18: dup
      // 1a19: bipush 25
      // 1a1b: new net/minecraft/client/animation/Keyframe
      // 1a1e: dup
      // 1a1f: ldc 1.04
      // 1a21: ldc_w 1.534
      // 1a24: ldc_w -7.188
      // 1a27: ldc_w -19.976
      // 1a2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a33: aastore
      // 1a34: dup
      // 1a35: bipush 26
      // 1a37: new net/minecraft/client/animation/Keyframe
      // 1a3a: dup
      // 1a3b: ldc 1.08
      // 1a3d: ldc_w -0.986
      // 1a40: ldc_w -7.128
      // 1a43: ldc_w -9.286
      // 1a46: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a4f: aastore
      // 1a50: dup
      // 1a51: bipush 27
      // 1a53: new net/minecraft/client/animation/Keyframe
      // 1a56: dup
      // 1a57: ldc 1.12
      // 1a59: ldc_w -0.516
      // 1a5c: ldc_w -6.812
      // 1a5f: ldc_w -9.707
      // 1a62: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a6b: aastore
      // 1a6c: dup
      // 1a6d: bipush 28
      // 1a6f: new net/minecraft/client/animation/Keyframe
      // 1a72: dup
      // 1a73: ldc 1.17
      // 1a75: ldc_w 0.519
      // 1a78: ldc_w -6.187
      // 1a7b: ldc_w -10.633
      // 1a7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a87: aastore
      // 1a88: dup
      // 1a89: bipush 29
      // 1a8b: new net/minecraft/client/animation/Keyframe
      // 1a8e: dup
      // 1a8f: ldc 1.21
      // 1a91: ldc_w 1.554
      // 1a94: ldc_w -5.397
      // 1a97: ldc_w -11.559
      // 1a9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aa0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aa3: aastore
      // 1aa4: dup
      // 1aa5: bipush 30
      // 1aa7: new net/minecraft/client/animation/Keyframe
      // 1aaa: dup
      // 1aab: ldc 1.25
      // 1aad: ldc_w 2.024
      // 1ab0: ldc_w -4.586
      // 1ab3: ldc_w -11.98
      // 1ab6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ab9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1abc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1abf: aastore
      // 1ac0: dup
      // 1ac1: bipush 31
      // 1ac3: new net/minecraft/client/animation/Keyframe
      // 1ac6: dup
      // 1ac7: ldc 1.29
      // 1ac9: fconst_2
      // 1aca: ldc_w -3.872
      // 1acd: ldc_w -11.783
      // 1ad0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ad3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ad6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ad9: aastore
      // 1ada: dup
      // 1adb: bipush 32
      // 1add: new net/minecraft/client/animation/Keyframe
      // 1ae0: dup
      // 1ae1: ldc 1.33
      // 1ae3: ldc_w 1.934
      // 1ae6: ldc_w -3.265
      // 1ae9: ldc -11.254
      // 1aeb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1aee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1af1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1af4: aastore
      // 1af5: dup
      // 1af6: bipush 33
      // 1af8: new net/minecraft/client/animation/Keyframe
      // 1afb: dup
      // 1afc: ldc 1.38
      // 1afe: ldc_w 1.834
      // 1b01: ldc_w -2.752
      // 1b04: ldc_w -10.49
      // 1b07: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b10: aastore
      // 1b11: dup
      // 1b12: bipush 34
      // 1b14: new net/minecraft/client/animation/Keyframe
      // 1b17: dup
      // 1b18: ldc 1.42
      // 1b1a: ldc_w 1.708
      // 1b1d: ldc_w -2.315
      // 1b20: ldc_w -9.583
      // 1b23: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b2c: aastore
      // 1b2d: dup
      // 1b2e: bipush 35
      // 1b30: new net/minecraft/client/animation/Keyframe
      // 1b33: dup
      // 1b34: ldc 1.46
      // 1b36: ldc_w 1.563
      // 1b39: ldc_w -1.939
      // 1b3c: ldc_w -8.63
      // 1b3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b48: aastore
      // 1b49: dup
      // 1b4a: bipush 36
      // 1b4c: new net/minecraft/client/animation/Keyframe
      // 1b4f: dup
      // 1b50: ldc 1.5
      // 1b52: ldc_w 1.407
      // 1b55: ldc_w -1.609
      // 1b58: ldc_w -7.723
      // 1b5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b64: aastore
      // 1b65: dup
      // 1b66: bipush 37
      // 1b68: new net/minecraft/client/animation/Keyframe
      // 1b6b: dup
      // 1b6c: ldc 1.54
      // 1b6e: ldc_w 1.249
      // 1b71: ldc_w -1.31
      // 1b74: ldc_w -6.959
      // 1b77: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b80: aastore
      // 1b81: dup
      // 1b82: bipush 38
      // 1b84: new net/minecraft/client/animation/Keyframe
      // 1b87: dup
      // 1b88: ldc 1.58
      // 1b8a: ldc_w 1.096
      // 1b8d: ldc_w -1.025
      // 1b90: ldc_w -6.43
      // 1b93: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b9c: aastore
      // 1b9d: dup
      // 1b9e: bipush 39
      // 1ba0: new net/minecraft/client/animation/Keyframe
      // 1ba3: dup
      // 1ba4: ldc 1.62
      // 1ba6: ldc_w 0.956
      // 1ba9: ldc_w -0.739
      // 1bac: ldc_w -6.233
      // 1baf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1bb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bb8: aastore
      // 1bb9: dup
      // 1bba: bipush 40
      // 1bbc: new net/minecraft/client/animation/Keyframe
      // 1bbf: dup
      // 1bc0: ldc 1.67
      // 1bc2: ldc_w 0.833
      // 1bc5: ldc_w -0.447
      // 1bc8: ldc_w -7.242
      // 1bcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1bce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bd4: aastore
      // 1bd5: dup
      // 1bd6: bipush 41
      // 1bd8: new net/minecraft/client/animation/Keyframe
      // 1bdb: dup
      // 1bdc: ldc 1.71
      // 1bde: ldc_w 0.72
      // 1be1: ldc_w -0.184
      // 1be4: ldc_w -9.461
      // 1be7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1bea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bf0: aastore
      // 1bf1: dup
      // 1bf2: bipush 42
      // 1bf4: new net/minecraft/client/animation/Keyframe
      // 1bf7: dup
      // 1bf8: ldc 1.75
      // 1bfa: ldc_w 0.604
      // 1bfd: ldc_w 0.007
      // 1c00: ldc_w -11.681
      // 1c03: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c0c: aastore
      // 1c0d: dup
      // 1c0e: bipush 43
      // 1c10: new net/minecraft/client/animation/Keyframe
      // 1c13: dup
      // 1c14: ldc 1.79
      // 1c16: ldc_w 0.473
      // 1c19: ldc 0.08
      // 1c1b: ldc_w -12.689
      // 1c1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c27: aastore
      // 1c28: dup
      // 1c29: bipush 44
      // 1c2b: new net/minecraft/client/animation/Keyframe
      // 1c2e: dup
      // 1c2f: ldc 1.83
      // 1c31: ldc_w 0.321
      // 1c34: ldc_w 0.052
      // 1c37: ldc_w -11.42
      // 1c3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c43: aastore
      // 1c44: dup
      // 1c45: bipush 45
      // 1c47: new net/minecraft/client/animation/Keyframe
      // 1c4a: dup
      // 1c4b: ldc 1.88
      // 1c4d: ldc_w 0.164
      // 1c50: ldc_w -0.015
      // 1c53: ldc_w -8.394
      // 1c56: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c5f: aastore
      // 1c60: dup
      // 1c61: bipush 46
      // 1c63: new net/minecraft/client/animation/Keyframe
      // 1c66: dup
      // 1c67: ldc 1.92
      // 1c69: ldc_w 0.025
      // 1c6c: ldc_w -0.095
      // 1c6f: ldc_w -4.782
      // 1c72: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c7b: aastore
      // 1c7c: dup
      // 1c7d: bipush 47
      // 1c7f: new net/minecraft/client/animation/Keyframe
      // 1c82: dup
      // 1c83: ldc 1.96
      // 1c85: ldc_w -0.074
      // 1c88: ldc_w -0.162
      // 1c8b: ldc_w -1.756
      // 1c8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c97: aastore
      // 1c98: dup
      // 1c99: bipush 48
      // 1c9b: new net/minecraft/client/animation/Keyframe
      // 1c9e: dup
      // 1c9f: fconst_2
      // 1ca0: ldc_w -0.112
      // 1ca3: ldc_w -0.19
      // 1ca6: ldc_w -0.487
      // 1ca9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1caf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cb2: aastore
      // 1cb3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1cb6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1cb9: ldc_w "CTR_upperarm_left"
      // 1cbc: new net/minecraft/client/animation/AnimationChannel
      // 1cbf: dup
      // 1cc0: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1cc3: bipush 49
      // 1cc5: anewarray 27
      // 1cc8: dup
      // 1cc9: bipush 0
      // 1cca: new net/minecraft/client/animation/Keyframe
      // 1ccd: dup
      // 1cce: fconst_0
      // 1ccf: ldc_w -1.331
      // 1cd2: ldc_w -1.925
      // 1cd5: ldc_w -0.448
      // 1cd8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cdb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cde: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ce1: aastore
      // 1ce2: dup
      // 1ce3: bipush 1
      // 1ce4: new net/minecraft/client/animation/Keyframe
      // 1ce7: dup
      // 1ce8: ldc 0.04
      // 1cea: ldc_w 0.593
      // 1ced: ldc_w -4.002
      // 1cf0: ldc_w -1.599
      // 1cf3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cf6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cf9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cfc: aastore
      // 1cfd: dup
      // 1cfe: bipush 2
      // 1cff: new net/minecraft/client/animation/Keyframe
      // 1d02: dup
      // 1d03: ldc 0.08
      // 1d05: ldc_w -1.47
      // 1d08: ldc_w -0.632
      // 1d0b: ldc_w 1.682
      // 1d0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d17: aastore
      // 1d18: dup
      // 1d19: bipush 3
      // 1d1a: new net/minecraft/client/animation/Keyframe
      // 1d1d: dup
      // 1d1e: ldc 0.12
      // 1d20: ldc_w -5.051
      // 1d23: ldc_w 5.802
      // 1d26: ldc_w 6.472
      // 1d29: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d32: aastore
      // 1d33: dup
      // 1d34: bipush 4
      // 1d35: new net/minecraft/client/animation/Keyframe
      // 1d38: dup
      // 1d39: ldc 0.17
      // 1d3b: ldc_w -8.562
      // 1d3e: ldc_w 13.229
      // 1d41: ldc_w 10.321
      // 1d44: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d4d: aastore
      // 1d4e: dup
      // 1d4f: bipush 5
      // 1d50: new net/minecraft/client/animation/Keyframe
      // 1d53: dup
      // 1d54: ldc 0.21
      // 1d56: ldc_w -12.007
      // 1d59: ldc_w 20.2
      // 1d5c: ldc_w 12.438
      // 1d5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d68: aastore
      // 1d69: dup
      // 1d6a: bipush 6
      // 1d6c: new net/minecraft/client/animation/Keyframe
      // 1d6f: dup
      // 1d70: ldc 0.25
      // 1d72: ldc_w -16.038
      // 1d75: ldc_w 25.251
      // 1d78: ldc_w 12.188
      // 1d7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d84: aastore
      // 1d85: dup
      // 1d86: bipush 7
      // 1d88: new net/minecraft/client/animation/Keyframe
      // 1d8b: dup
      // 1d8c: ldc 0.29
      // 1d8e: ldc_w -21.031
      // 1d91: ldc_w 27.085
      // 1d94: ldc_w 9.008
      // 1d97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1da0: aastore
      // 1da1: dup
      // 1da2: bipush 8
      // 1da4: new net/minecraft/client/animation/Keyframe
      // 1da7: dup
      // 1da8: ldc 0.33
      // 1daa: ldc_w -26.849
      // 1dad: ldc_w 24.729
      // 1db0: ldc_w 3.591
      // 1db3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1db6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1db9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dbc: aastore
      // 1dbd: dup
      // 1dbe: bipush 9
      // 1dc0: new net/minecraft/client/animation/Keyframe
      // 1dc3: dup
      // 1dc4: ldc 0.38
      // 1dc6: ldc_w -32.34
      // 1dc9: ldc_w 17.925
      // 1dcc: ldc_w -1.173
      // 1dcf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1dd2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dd5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dd8: aastore
      // 1dd9: dup
      // 1dda: bipush 10
      // 1ddc: new net/minecraft/client/animation/Keyframe
      // 1ddf: dup
      // 1de0: ldc 0.42
      // 1de2: ldc_w -35.239
      // 1de5: ldc_w 8.16
      // 1de8: ldc_w -2.276
      // 1deb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1dee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1df1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1df4: aastore
      // 1df5: dup
      // 1df6: bipush 11
      // 1df8: new net/minecraft/client/animation/Keyframe
      // 1dfb: dup
      // 1dfc: ldc 0.46
      // 1dfe: ldc_w -33.26
      // 1e01: ldc_w -5.493
      // 1e04: ldc_w 0.197
      // 1e07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e10: aastore
      // 1e11: dup
      // 1e12: bipush 12
      // 1e14: new net/minecraft/client/animation/Keyframe
      // 1e17: dup
      // 1e18: ldc 0.5
      // 1e1a: ldc_w -18.743
      // 1e1d: ldc_w -18.635
      // 1e20: ldc_w 0.548
      // 1e23: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e2c: aastore
      // 1e2d: dup
      // 1e2e: bipush 13
      // 1e30: new net/minecraft/client/animation/Keyframe
      // 1e33: dup
      // 1e34: ldc 0.54
      // 1e36: ldc_w 6.302
      // 1e39: ldc_w -22.657
      // 1e3c: ldc_w -13.174
      // 1e3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e48: aastore
      // 1e49: dup
      // 1e4a: bipush 14
      // 1e4c: new net/minecraft/client/animation/Keyframe
      // 1e4f: dup
      // 1e50: ldc 0.58
      // 1e52: ldc_w 17.61
      // 1e55: ldc_w -13.262
      // 1e58: ldc_w -30.896
      // 1e5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e64: aastore
      // 1e65: dup
      // 1e66: bipush 15
      // 1e68: new net/minecraft/client/animation/Keyframe
      // 1e6b: dup
      // 1e6c: ldc 0.62
      // 1e6e: ldc_w 15.867
      // 1e71: ldc_w -3.415
      // 1e74: ldc_w -61.019
      // 1e77: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e80: aastore
      // 1e81: dup
      // 1e82: bipush 16
      // 1e84: new net/minecraft/client/animation/Keyframe
      // 1e87: dup
      // 1e88: ldc 0.67
      // 1e8a: ldc_w 7.519
      // 1e8d: ldc_w -0.778
      // 1e90: ldc_w -94.235
      // 1e93: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e9c: aastore
      // 1e9d: dup
      // 1e9e: bipush 17
      // 1ea0: new net/minecraft/client/animation/Keyframe
      // 1ea3: dup
      // 1ea4: ldc 0.71
      // 1ea6: ldc_w 1.413
      // 1ea9: ldc_w -2.322
      // 1eac: ldc_w -119.326
      // 1eaf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1eb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eb8: aastore
      // 1eb9: dup
      // 1eba: bipush 18
      // 1ebc: new net/minecraft/client/animation/Keyframe
      // 1ebf: dup
      // 1ec0: ldc 0.75
      // 1ec2: ldc_w -3.911
      // 1ec5: ldc_w -3.414
      // 1ec8: ldc_w -137.837
      // 1ecb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ece: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ed1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ed4: aastore
      // 1ed5: dup
      // 1ed6: bipush 19
      // 1ed8: new net/minecraft/client/animation/Keyframe
      // 1edb: dup
      // 1edc: ldc 0.79
      // 1ede: ldc_w -7.871
      // 1ee1: ldc_w -3.828
      // 1ee4: ldc_w -146.333
      // 1ee7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1eea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ef0: aastore
      // 1ef1: dup
      // 1ef2: bipush 20
      // 1ef4: new net/minecraft/client/animation/Keyframe
      // 1ef7: dup
      // 1ef8: ldc 0.83
      // 1efa: ldc_w -10.302
      // 1efd: ldc_w -3.576
      // 1f00: ldc_w -147.461
      // 1f03: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f0c: aastore
      // 1f0d: dup
      // 1f0e: bipush 21
      // 1f10: new net/minecraft/client/animation/Keyframe
      // 1f13: dup
      // 1f14: ldc 0.88
      // 1f16: ldc_w -12.718
      // 1f19: ldc_w -3.106
      // 1f1c: ldc_w -147.548
      // 1f1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f28: aastore
      // 1f29: dup
      // 1f2a: bipush 22
      // 1f2c: new net/minecraft/client/animation/Keyframe
      // 1f2f: dup
      // 1f30: ldc 0.92
      // 1f32: ldc_w -17.052
      // 1f35: ldc_w -2.853
      // 1f38: ldc_w -148.676
      // 1f3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f44: aastore
      // 1f45: dup
      // 1f46: bipush 23
      // 1f48: new net/minecraft/client/animation/Keyframe
      // 1f4b: dup
      // 1f4c: ldc 0.96
      // 1f4e: ldc_w -21.65
      // 1f51: ldc_w -17.196
      // 1f54: ldc_w -167.572
      // 1f57: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f60: aastore
      // 1f61: dup
      // 1f62: bipush 24
      // 1f64: new net/minecraft/client/animation/Keyframe
      // 1f67: dup
      // 1f68: fconst_1
      // 1f69: ldc_w -21.412
      // 1f6c: ldc_w -17.088
      // 1f6f: ldc_w -166.744
      // 1f72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f7b: aastore
      // 1f7c: dup
      // 1f7d: bipush 25
      // 1f7f: new net/minecraft/client/animation/Keyframe
      // 1f82: dup
      // 1f83: ldc 1.04
      // 1f85: ldc_w -79.157
      // 1f88: ldc_w 12.999
      // 1f8b: ldc_w -18.527
      // 1f8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f97: aastore
      // 1f98: dup
      // 1f99: bipush 26
      // 1f9b: new net/minecraft/client/animation/Keyframe
      // 1f9e: dup
      // 1f9f: ldc 1.08
      // 1fa1: ldc_w -75.701
      // 1fa4: ldc_w 57.395
      // 1fa7: ldc_w -16.181
      // 1faa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fb3: aastore
      // 1fb4: dup
      // 1fb5: bipush 27
      // 1fb7: new net/minecraft/client/animation/Keyframe
      // 1fba: dup
      // 1fbb: ldc 1.12
      // 1fbd: ldc_w -65.51
      // 1fc0: ldc_w 63.7
      // 1fc3: ldc_w -11.932
      // 1fc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fcf: aastore
      // 1fd0: dup
      // 1fd1: bipush 28
      // 1fd3: new net/minecraft/client/animation/Keyframe
      // 1fd6: dup
      // 1fd7: ldc 1.17
      // 1fd9: ldc_w -52.378
      // 1fdc: ldc_w 65.373
      // 1fdf: ldc_w -4.844
      // 1fe2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fe5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fe8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1feb: aastore
      // 1fec: dup
      // 1fed: bipush 29
      // 1fef: new net/minecraft/client/animation/Keyframe
      // 1ff2: dup
      // 1ff3: ldc 1.21
      // 1ff5: ldc_w -38.715
      // 1ff8: ldc_w 62.034
      // 1ffb: ldc_w 2.214
      // 1ffe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2001: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2004: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2007: aastore
      // 2008: dup
      // 2009: bipush 30
      // 200b: new net/minecraft/client/animation/Keyframe
      // 200e: dup
      // 200f: ldc 1.25
      // 2011: ldc_w -29.514
      // 2014: ldc_w 59.851
      // 2017: ldc_w 6.292
      // 201a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 201d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2020: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2023: aastore
      // 2024: dup
      // 2025: bipush 31
      // 2027: new net/minecraft/client/animation/Keyframe
      // 202a: dup
      // 202b: ldc 1.29
      // 202d: ldc_w -23.116
      // 2030: ldc_w 58.597
      // 2033: ldc_w 8.859
      // 2036: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2039: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 203c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 203f: aastore
      // 2040: dup
      // 2041: bipush 32
      // 2043: new net/minecraft/client/animation/Keyframe
      // 2046: dup
      // 2047: ldc 1.33
      // 2049: ldc_w -17.705
      // 204c: ldc_w 55.575
      // 204f: ldc_w 10.307
      // 2052: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2055: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2058: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 205b: aastore
      // 205c: dup
      // 205d: bipush 33
      // 205f: new net/minecraft/client/animation/Keyframe
      // 2062: dup
      // 2063: ldc 1.38
      // 2065: ldc_w -13.308
      // 2068: ldc_w 50.753
      // 206b: ldc_w 9.673
      // 206e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2071: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2074: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2077: aastore
      // 2078: dup
      // 2079: bipush 34
      // 207b: new net/minecraft/client/animation/Keyframe
      // 207e: dup
      // 207f: ldc 1.42
      // 2081: ldc_w -10.061
      // 2084: ldc_w 44.363
      // 2087: ldc_w 5.974
      // 208a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 208d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2090: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2093: aastore
      // 2094: dup
      // 2095: bipush 35
      // 2097: new net/minecraft/client/animation/Keyframe
      // 209a: dup
      // 209b: ldc 1.46
      // 209d: ldc_w -8.303
      // 20a0: ldc_w 39.573
      // 20a3: ldc_w 1.636
      // 20a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20af: aastore
      // 20b0: dup
      // 20b1: bipush 36
      // 20b3: new net/minecraft/client/animation/Keyframe
      // 20b6: dup
      // 20b7: ldc 1.5
      // 20b9: ldc_w -7.903
      // 20bc: ldc_w 41.128
      // 20bf: ldc_w 2.785
      // 20c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20cb: aastore
      // 20cc: dup
      // 20cd: bipush 37
      // 20cf: new net/minecraft/client/animation/Keyframe
      // 20d2: dup
      // 20d3: ldc 1.54
      // 20d5: ldc_w -7.551
      // 20d8: ldc_w 46.256
      // 20db: ldc_w 7.788
      // 20de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20e7: aastore
      // 20e8: dup
      // 20e9: bipush 38
      // 20eb: new net/minecraft/client/animation/Keyframe
      // 20ee: dup
      // 20ef: ldc 1.58
      // 20f1: ldc_w -5.979
      // 20f4: ldc_w 50.752
      // 20f7: ldc_w 14.65
      // 20fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2100: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2103: aastore
      // 2104: dup
      // 2105: bipush 39
      // 2107: new net/minecraft/client/animation/Keyframe
      // 210a: dup
      // 210b: ldc 1.62
      // 210d: ldc_w -3.68
      // 2110: ldc_w 52.646
      // 2113: ldc_w 21.067
      // 2116: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2119: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 211c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 211f: aastore
      // 2120: dup
      // 2121: bipush 40
      // 2123: new net/minecraft/client/animation/Keyframe
      // 2126: dup
      // 2127: ldc 1.67
      // 2129: ldc_w -1.497
      // 212c: ldc_w 52.975
      // 212f: ldc_w 27.806
      // 2132: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2135: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2138: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 213b: aastore
      // 213c: dup
      // 213d: bipush 41
      // 213f: new net/minecraft/client/animation/Keyframe
      // 2142: dup
      // 2143: ldc 1.71
      // 2145: ldc_w -0.022
      // 2148: ldc_w 52.095
      // 214b: ldc_w 34.263
      // 214e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2151: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2154: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2157: aastore
      // 2158: dup
      // 2159: bipush 42
      // 215b: new net/minecraft/client/animation/Keyframe
      // 215e: dup
      // 215f: ldc 1.75
      // 2161: ldc_w 0.558
      // 2164: ldc_w 48.385
      // 2167: ldc_w 38.004
      // 216a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 216d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2170: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2173: aastore
      // 2174: dup
      // 2175: bipush 43
      // 2177: new net/minecraft/client/animation/Keyframe
      // 217a: dup
      // 217b: ldc 1.79
      // 217d: ldc_w -1.438
      // 2180: ldc_w 40.669
      // 2183: ldc_w 35.102
      // 2186: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2189: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 218c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 218f: aastore
      // 2190: dup
      // 2191: bipush 44
      // 2193: new net/minecraft/client/animation/Keyframe
      // 2196: dup
      // 2197: ldc 1.83
      // 2199: ldc_w -4.219
      // 219c: ldc_w 29.749
      // 219f: ldc_w 26.505
      // 21a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21ab: aastore
      // 21ac: dup
      // 21ad: bipush 45
      // 21af: new net/minecraft/client/animation/Keyframe
      // 21b2: dup
      // 21b3: ldc 1.88
      // 21b5: ldc_w -5.604
      // 21b8: ldc_w 18.482
      // 21bb: ldc_w 16.839
      // 21be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21c7: aastore
      // 21c8: dup
      // 21c9: bipush 46
      // 21cb: new net/minecraft/client/animation/Keyframe
      // 21ce: dup
      // 21cf: ldc 1.92
      // 21d1: ldc_w -5.111
      // 21d4: ldc_w 8.882
      // 21d7: ldc_w 8.943
      // 21da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21e3: aastore
      // 21e4: dup
      // 21e5: bipush 47
      // 21e7: new net/minecraft/client/animation/Keyframe
      // 21ea: dup
      // 21eb: ldc 1.96
      // 21ed: ldc_w -3.019
      // 21f0: ldc_w 1.527
      // 21f3: ldc_w 2.76
      // 21f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21ff: aastore
      // 2200: dup
      // 2201: bipush 48
      // 2203: new net/minecraft/client/animation/Keyframe
      // 2206: dup
      // 2207: fconst_2
      // 2208: ldc_w -1.331
      // 220b: ldc_w -1.925
      // 220e: ldc_w -0.448
      // 2211: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2214: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2217: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 221a: aastore
      // 221b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 221e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2221: ldc_w "CTR_lowerarm_left"
      // 2224: new net/minecraft/client/animation/AnimationChannel
      // 2227: dup
      // 2228: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 222b: bipush 49
      // 222d: anewarray 27
      // 2230: dup
      // 2231: bipush 0
      // 2232: new net/minecraft/client/animation/Keyframe
      // 2235: dup
      // 2236: fconst_0
      // 2237: ldc_w 0.989
      // 223a: ldc_w -2.325
      // 223d: ldc_w -0.565
      // 2240: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2243: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2246: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2249: aastore
      // 224a: dup
      // 224b: bipush 1
      // 224c: new net/minecraft/client/animation/Keyframe
      // 224f: dup
      // 2250: ldc 0.04
      // 2252: ldc_w -2.937
      // 2255: ldc_w 7.747
      // 2258: ldc_w 1.464
      // 225b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 225e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2261: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2264: aastore
      // 2265: dup
      // 2266: bipush 2
      // 2267: new net/minecraft/client/animation/Keyframe
      // 226a: dup
      // 226b: ldc 0.08
      // 226d: ldc_w -1.637
      // 2270: ldc_w 4.162
      // 2273: ldc_w 0.859
      // 2276: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2279: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 227c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 227f: aastore
      // 2280: dup
      // 2281: bipush 3
      // 2282: new net/minecraft/client/animation/Keyframe
      // 2285: dup
      // 2286: ldc 0.12
      // 2288: ldc_w 2.734
      // 228b: ldc_w -6.099
      // 228e: ldc_w -1.638
      // 2291: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2294: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2297: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 229a: aastore
      // 229b: dup
      // 229c: bipush 4
      // 229d: new net/minecraft/client/animation/Keyframe
      // 22a0: dup
      // 22a1: ldc 0.17
      // 22a3: ldc_w 9.597
      // 22a6: ldc_w -17.443
      // 22a9: ldc_w -6.557
      // 22ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22b5: aastore
      // 22b6: dup
      // 22b7: bipush 5
      // 22b8: new net/minecraft/client/animation/Keyframe
      // 22bb: dup
      // 22bc: ldc 0.21
      // 22be: ldc_w 20.425
      // 22c1: ldc_w -27.864
      // 22c4: ldc_w -15.616
      // 22c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22d0: aastore
      // 22d1: dup
      // 22d2: bipush 6
      // 22d4: new net/minecraft/client/animation/Keyframe
      // 22d7: dup
      // 22d8: ldc 0.25
      // 22da: ldc_w 38.363
      // 22dd: ldc_w -35.948
      // 22e0: ldc_w -32.015
      // 22e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22ec: aastore
      // 22ed: dup
      // 22ee: bipush 7
      // 22f0: new net/minecraft/client/animation/Keyframe
      // 22f3: dup
      // 22f4: ldc 0.29
      // 22f6: ldc_w 61.397
      // 22f9: ldc_w -39.879
      // 22fc: ldc_w -53.914
      // 22ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2302: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2305: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2308: aastore
      // 2309: dup
      // 230a: bipush 8
      // 230c: new net/minecraft/client/animation/Keyframe
      // 230f: dup
      // 2310: ldc 0.33
      // 2312: ldc_w 72.722
      // 2315: ldc_w -40.531
      // 2318: ldc_w -64.798
      // 231b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 231e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2321: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2324: aastore
      // 2325: dup
      // 2326: bipush 9
      // 2328: new net/minecraft/client/animation/Keyframe
      // 232b: dup
      // 232c: ldc 0.38
      // 232e: ldc_w 61.412
      // 2331: ldc_w -39.881
      // 2334: ldc_w -53.929
      // 2337: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 233a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 233d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2340: aastore
      // 2341: dup
      // 2342: bipush 10
      // 2344: new net/minecraft/client/animation/Keyframe
      // 2347: dup
      // 2348: ldc 0.42
      // 234a: ldc_w 35.9
      // 234d: ldc_w -35.212
      // 2350: ldc_w -29.712
      // 2353: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2356: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2359: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 235c: aastore
      // 235d: dup
      // 235e: bipush 11
      // 2360: new net/minecraft/client/animation/Keyframe
      // 2363: dup
      // 2364: ldc 0.46
      // 2366: ldc_w 12.826
      // 2369: ldc_w -21.286
      // 236c: ldc_w -9.139
      // 236f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2372: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2375: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2378: aastore
      // 2379: dup
      // 237a: bipush 12
      // 237c: new net/minecraft/client/animation/Keyframe
      // 237f: dup
      // 2380: ldc 0.5
      // 2382: ldc_w -2.232
      // 2385: ldc_w 2.703
      // 2388: ldc_w 0.868
      // 238b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 238e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2391: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2394: aastore
      // 2395: dup
      // 2396: bipush 13
      // 2398: new net/minecraft/client/animation/Keyframe
      // 239b: dup
      // 239c: ldc 0.54
      // 239e: ldc_w -9.647
      // 23a1: ldc_w 27.409
      // 23a4: ldc_w 3.092
      // 23a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23b0: aastore
      // 23b1: dup
      // 23b2: bipush 14
      // 23b4: new net/minecraft/client/animation/Keyframe
      // 23b7: dup
      // 23b8: ldc 0.58
      // 23ba: ldc_w -9.907
      // 23bd: ldc_w 26.192
      // 23c0: ldc_w 2.96
      // 23c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23cc: aastore
      // 23cd: dup
      // 23ce: bipush 15
      // 23d0: new net/minecraft/client/animation/Keyframe
      // 23d3: dup
      // 23d4: ldc 0.62
      // 23d6: ldc_w -9.922
      // 23d9: ldc_w 21.266
      // 23dc: ldc_w 2.751
      // 23df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23e8: aastore
      // 23e9: dup
      // 23ea: bipush 16
      // 23ec: new net/minecraft/client/animation/Keyframe
      // 23ef: dup
      // 23f0: ldc 0.67
      // 23f2: ldc_w -10.188
      // 23f5: ldc_w 17.42
      // 23f8: ldc_w 2.57
      // 23fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2401: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2404: aastore
      // 2405: dup
      // 2406: bipush 17
      // 2408: new net/minecraft/client/animation/Keyframe
      // 240b: dup
      // 240c: ldc 0.71
      // 240e: ldc_w -10.496
      // 2411: ldc_w 15.295
      // 2414: ldc_w 2.429
      // 2417: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 241a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 241d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2420: aastore
      // 2421: dup
      // 2422: bipush 18
      // 2424: new net/minecraft/client/animation/Keyframe
      // 2427: dup
      // 2428: ldc 0.75
      // 242a: ldc_w -10.707
      // 242d: ldc_w 13.791
      // 2430: ldc_w 2.328
      // 2433: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2436: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2439: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 243c: aastore
      // 243d: dup
      // 243e: bipush 19
      // 2440: new net/minecraft/client/animation/Keyframe
      // 2443: dup
      // 2444: ldc 0.79
      // 2446: ldc_w -10.786
      // 2449: ldc_w 13.221
      // 244c: ldc_w 2.289
      // 244f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2452: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2455: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2458: aastore
      // 2459: dup
      // 245a: bipush 20
      // 245c: new net/minecraft/client/animation/Keyframe
      // 245f: dup
      // 2460: ldc 0.83
      // 2462: ldc_w -10.496
      // 2465: ldc_w 15.295
      // 2468: ldc_w 2.429
      // 246b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 246e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2471: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2474: aastore
      // 2475: dup
      // 2476: bipush 21
      // 2478: new net/minecraft/client/animation/Keyframe
      // 247b: dup
      // 247c: ldc 0.88
      // 247e: ldc_w -9.821
      // 2481: ldc_w 19.857
      // 2484: ldc_w 2.728
      // 2487: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 248a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 248d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2490: aastore
      // 2491: dup
      // 2492: bipush 22
      // 2494: new net/minecraft/client/animation/Keyframe
      // 2497: dup
      // 2498: ldc 0.92
      // 249a: ldc_w -9.098
      // 249d: ldc_w 24.42
      // 24a0: ldc_w 3.017
      // 24a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24ac: aastore
      // 24ad: dup
      // 24ae: bipush 23
      // 24b0: new net/minecraft/client/animation/Keyframe
      // 24b3: dup
      // 24b4: ldc 0.96
      // 24b6: ldc_w -8.754
      // 24b9: ldc_w 26.493
      // 24bc: ldc_w 3.147
      // 24bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24c8: aastore
      // 24c9: dup
      // 24ca: bipush 24
      // 24cc: new net/minecraft/client/animation/Keyframe
      // 24cf: dup
      // 24d0: fconst_1
      // 24d1: ldc_w -8.754
      // 24d4: ldc_w 26.493
      // 24d7: ldc_w 3.147
      // 24da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24e3: aastore
      // 24e4: dup
      // 24e5: bipush 25
      // 24e7: new net/minecraft/client/animation/Keyframe
      // 24ea: dup
      // 24eb: ldc 1.04
      // 24ed: ldc_w 83.574
      // 24f0: ldc_w -40.584
      // 24f3: ldc_w -75.245
      // 24f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24ff: aastore
      // 2500: dup
      // 2501: bipush 26
      // 2503: new net/minecraft/client/animation/Keyframe
      // 2506: dup
      // 2507: ldc 1.08
      // 2509: ldc_w 140.632
      // 250c: ldc_w -25.908
      // 250f: ldc_w -128.77
      // 2512: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2515: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2518: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 251b: aastore
      // 251c: dup
      // 251d: bipush 27
      // 251f: new net/minecraft/client/animation/Keyframe
      // 2522: dup
      // 2523: ldc 1.12
      // 2525: ldc_w 143.936
      // 2528: ldc_w -22.99
      // 252b: ldc_w -131.578
      // 252e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2531: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2534: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2537: aastore
      // 2538: dup
      // 2539: bipush 28
      // 253b: new net/minecraft/client/animation/Keyframe
      // 253e: dup
      // 253f: ldc 1.17
      // 2541: ldc_w 143.877
      // 2544: ldc_w -23.047
      // 2547: ldc_w -131.528
      // 254a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 254d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2550: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2553: aastore
      // 2554: dup
      // 2555: bipush 29
      // 2557: new net/minecraft/client/animation/Keyframe
      // 255a: dup
      // 255b: ldc 1.21
      // 255d: ldc_w 140.446
      // 2560: ldc_w -26.056
      // 2563: ldc_w -128.61
      // 2566: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2569: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 256c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 256f: aastore
      // 2570: dup
      // 2571: bipush 30
      // 2573: new net/minecraft/client/animation/Keyframe
      // 2576: dup
      // 2577: ldc 1.25
      // 2579: ldc_w 132.877
      // 257c: ldc_w -30.96
      // 257f: ldc_w -121.906
      // 2582: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2585: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2588: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 258b: aastore
      // 258c: dup
      // 258d: bipush 31
      // 258f: new net/minecraft/client/animation/Keyframe
      // 2592: dup
      // 2593: ldc 1.29
      // 2595: ldc_w 116.519
      // 2598: ldc_w -36.87
      // 259b: ldc_w -106.746
      // 259e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25a7: aastore
      // 25a8: dup
      // 25a9: bipush 32
      // 25ab: new net/minecraft/client/animation/Keyframe
      // 25ae: dup
      // 25af: ldc 1.33
      // 25b1: ldc_w 80.233
      // 25b4: ldc_w -40.625
      // 25b7: ldc_w -72.029
      // 25ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25c3: aastore
      // 25c4: dup
      // 25c5: bipush 33
      // 25c7: new net/minecraft/client/animation/Keyframe
      // 25ca: dup
      // 25cb: ldc 1.38
      // 25cd: ldc_w 34.18
      // 25d0: ldc_w -34.645
      // 25d3: ldc_w -28.11
      // 25d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25df: aastore
      // 25e0: dup
      // 25e1: bipush 34
      // 25e3: new net/minecraft/client/animation/Keyframe
      // 25e6: dup
      // 25e7: ldc 1.42
      // 25e9: ldc_w 9.89
      // 25ec: ldc_w -17.824
      // 25ef: ldc_w -6.786
      // 25f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25fb: aastore
      // 25fc: dup
      // 25fd: bipush 35
      // 25ff: new net/minecraft/client/animation/Keyframe
      // 2602: dup
      // 2603: ldc 1.46
      // 2605: ldc_w 0.136
      // 2608: ldc_w -0.329
      // 260b: ldc_w -0.076
      // 260e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2611: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2614: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2617: aastore
      // 2618: dup
      // 2619: bipush 36
      // 261b: new net/minecraft/client/animation/Keyframe
      // 261e: dup
      // 261f: ldc 1.5
      // 2621: ldc_w -0.814
      // 2624: ldc_w 2.021
      // 2627: ldc_w 0.44
      // 262a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 262d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2630: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2633: aastore
      // 2634: dup
      // 2635: bipush 37
      // 2637: new net/minecraft/client/animation/Keyframe
      // 263a: dup
      // 263b: ldc 1.54
      // 263d: ldc 3.24
      // 263f: ldc_w -7.118
      // 2642: ldc_w -1.966
      // 2645: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2648: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 264b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 264e: aastore
      // 264f: dup
      // 2650: bipush 38
      // 2652: new net/minecraft/client/animation/Keyframe
      // 2655: dup
      // 2656: ldc 1.58
      // 2658: ldc_w 11.982
      // 265b: ldc_w -20.355
      // 265e: ldc_w -8.453
      // 2661: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2664: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2667: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 266a: aastore
      // 266b: dup
      // 266c: bipush 39
      // 266e: new net/minecraft/client/animation/Keyframe
      // 2671: dup
      // 2672: ldc 1.62
      // 2674: ldc_w 25.029
      // 2677: ldc_w -30.682
      // 267a: ldc_w -19.719
      // 267d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2680: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2683: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2686: aastore
      // 2687: dup
      // 2688: bipush 40
      // 268a: new net/minecraft/client/animation/Keyframe
      // 268d: dup
      // 268e: ldc 1.67
      // 2690: ldc_w 49.128
      // 2693: ldc_w -38.334
      // 2696: ldc_w -42.189
      // 2699: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 269c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 269f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26a2: aastore
      // 26a3: dup
      // 26a4: bipush 41
      // 26a6: new net/minecraft/client/animation/Keyframe
      // 26a9: dup
      // 26aa: ldc 1.71
      // 26ac: ldc_w 81.595
      // 26af: ldc_w -40.615
      // 26b2: ldc_w -73.34
      // 26b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26be: aastore
      // 26bf: dup
      // 26c0: bipush 42
      // 26c2: new net/minecraft/client/animation/Keyframe
      // 26c5: dup
      // 26c6: ldc 1.75
      // 26c8: ldc_w 89.5
      // 26cb: ldc_w -40.384
      // 26ce: ldc_w -80.948
      // 26d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26da: aastore
      // 26db: dup
      // 26dc: bipush 43
      // 26de: new net/minecraft/client/animation/Keyframe
      // 26e1: dup
      // 26e2: ldc 1.79
      // 26e4: ldc_w 63.382
      // 26e7: ldc_w -40.042
      // 26ea: ldc_w -55.82
      // 26ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26f6: aastore
      // 26f7: dup
      // 26f8: bipush 44
      // 26fa: new net/minecraft/client/animation/Keyframe
      // 26fd: dup
      // 26fe: ldc 1.83
      // 2700: ldc_w 28.869
      // 2703: ldc_w -32.565
      // 2706: ldc_w -23.208
      // 2709: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 270c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 270f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2712: aastore
      // 2713: dup
      // 2714: bipush 45
      // 2716: new net/minecraft/client/animation/Keyframe
      // 2719: dup
      // 271a: ldc 1.88
      // 271c: ldc_w 14.801
      // 271f: ldc_w -23.288
      // 2722: ldc_w -10.777
      // 2725: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2728: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 272b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 272e: aastore
      // 272f: dup
      // 2730: bipush 46
      // 2732: new net/minecraft/client/animation/Keyframe
      // 2735: dup
      // 2736: ldc 1.92
      // 2738: ldc_w 9.021
      // 273b: ldc_w -16.671
      // 273e: ldc_w -6.111
      // 2741: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2744: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2747: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 274a: aastore
      // 274b: dup
      // 274c: bipush 47
      // 274e: new net/minecraft/client/animation/Keyframe
      // 2751: dup
      // 2752: ldc 1.96
      // 2754: ldc_w 3.911
      // 2757: ldc_w -8.418
      // 275a: ldc_w -2.41
      // 275d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2760: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2763: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2766: aastore
      // 2767: dup
      // 2768: bipush 48
      // 276a: new net/minecraft/client/animation/Keyframe
      // 276d: dup
      // 276e: fconst_2
      // 276f: ldc_w 0.989
      // 2772: ldc_w -2.325
      // 2775: ldc_w -0.565
      // 2778: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 277b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 277e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2781: aastore
      // 2782: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2785: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2788: ldc_w "CTR_lowerarm_left"
      // 278b: new net/minecraft/client/animation/AnimationChannel
      // 278e: dup
      // 278f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2792: bipush 3
      // 2793: anewarray 27
      // 2796: dup
      // 2797: bipush 0
      // 2798: new net/minecraft/client/animation/Keyframe
      // 279b: dup
      // 279c: fconst_1
      // 279d: dconst_1
      // 279e: dconst_1
      // 279f: dconst_1
      // 27a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 27a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27a9: aastore
      // 27aa: dup
      // 27ab: bipush 1
      // 27ac: new net/minecraft/client/animation/Keyframe
      // 27af: dup
      // 27b0: ldc 1.04
      // 27b2: ldc2_w 0.7929999828338623
      // 27b5: ldc2_w 2.428999900817871
      // 27b8: dconst_1
      // 27b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 27bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27c2: aastore
      // 27c3: dup
      // 27c4: bipush 2
      // 27c5: new net/minecraft/client/animation/Keyframe
      // 27c8: dup
      // 27c9: ldc 1.08
      // 27cb: dconst_1
      // 27cc: dconst_1
      // 27cd: dconst_1
      // 27ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 27d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27d7: aastore
      // 27d8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 27db: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 27de: ldc_w "CTR_upperarm_left.001"
      // 27e1: new net/minecraft/client/animation/AnimationChannel
      // 27e4: dup
      // 27e5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 27e8: bipush 49
      // 27ea: anewarray 27
      // 27ed: dup
      // 27ee: bipush 0
      // 27ef: new net/minecraft/client/animation/Keyframe
      // 27f2: dup
      // 27f3: fconst_0
      // 27f4: ldc_w 2.163
      // 27f7: ldc_w -22.491
      // 27fa: ldc_w -1.837
      // 27fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2800: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2803: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2806: aastore
      // 2807: dup
      // 2808: bipush 1
      // 2809: new net/minecraft/client/animation/Keyframe
      // 280c: dup
      // 280d: ldc 0.04
      // 280f: ldc_w 1.126
      // 2812: ldc_w -16.828
      // 2815: ldc_w -1.479
      // 2818: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 281b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 281e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2821: aastore
      // 2822: dup
      // 2823: bipush 2
      // 2824: new net/minecraft/client/animation/Keyframe
      // 2827: dup
      // 2828: ldc 0.08
      // 282a: ldc_w 1.481
      // 282d: ldc_w -18.622
      // 2830: ldc_w -1.578
      // 2833: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2836: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2839: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 283c: aastore
      // 283d: dup
      // 283e: bipush 3
      // 283f: new net/minecraft/client/animation/Keyframe
      // 2842: dup
      // 2843: ldc 0.12
      // 2845: ldc_w 2.463
      // 2848: ldc_w -23.511
      // 284b: ldc_w -2.026
      // 284e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2851: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2854: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2857: aastore
      // 2858: dup
      // 2859: bipush 4
      // 285a: new net/minecraft/client/animation/Keyframe
      // 285d: dup
      // 285e: ldc 0.17
      // 2860: ldc_w 3.517
      // 2863: ldc_w -27.966
      // 2866: ldc_w -2.713
      // 2869: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 286c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 286f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2872: aastore
      // 2873: dup
      // 2874: bipush 5
      // 2875: new net/minecraft/client/animation/Keyframe
      // 2878: dup
      // 2879: ldc 0.21
      // 287b: ldc_w 4.386
      // 287e: ldc_w -30.179
      // 2881: ldc_w -3.392
      // 2884: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2887: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 288a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 288d: aastore
      // 288e: dup
      // 288f: bipush 6
      // 2891: new net/minecraft/client/animation/Keyframe
      // 2894: dup
      // 2895: ldc 0.25
      // 2897: ldc_w 4.714
      // 289a: ldc_w -28.42
      // 289d: ldc_w -3.731
      // 28a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28a9: aastore
      // 28aa: dup
      // 28ab: bipush 7
      // 28ad: new net/minecraft/client/animation/Keyframe
      // 28b0: dup
      // 28b1: ldc 0.29
      // 28b3: ldc_w 4.001
      // 28b6: ldc_w -21.152
      // 28b9: ldc_w -3.441
      // 28bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28c5: aastore
      // 28c6: dup
      // 28c7: bipush 8
      // 28c9: new net/minecraft/client/animation/Keyframe
      // 28cc: dup
      // 28cd: ldc 0.33
      // 28cf: ldc_w 1.565
      // 28d2: ldc_w -8.727
      // 28d5: ldc_w -3.26
      // 28d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28e1: aastore
      // 28e2: dup
      // 28e3: bipush 9
      // 28e5: new net/minecraft/client/animation/Keyframe
      // 28e8: dup
      // 28e9: ldc 0.38
      // 28eb: ldc_w 7.718
      // 28ee: ldc_w 5.706
      // 28f1: ldc_w 5.801
      // 28f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28fd: aastore
      // 28fe: dup
      // 28ff: bipush 10
      // 2901: new net/minecraft/client/animation/Keyframe
      // 2904: dup
      // 2905: ldc 0.42
      // 2907: ldc_w 2.451
      // 290a: ldc_w 18.356
      // 290d: ldc_w 2.487
      // 2910: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2913: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2916: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2919: aastore
      // 291a: dup
      // 291b: bipush 11
      // 291d: new net/minecraft/client/animation/Keyframe
      // 2920: dup
      // 2921: ldc 0.46
      // 2923: ldc_w 1.432
      // 2926: ldc_w 29.314
      // 2929: ldc_w 2.374
      // 292c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 292f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2932: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2935: aastore
      // 2936: dup
      // 2937: bipush 12
      // 2939: new net/minecraft/client/animation/Keyframe
      // 293c: dup
      // 293d: ldc 0.5
      // 293f: ldc_w 0.031
      // 2942: ldc_w 36.355
      // 2945: ldc_w 3.544
      // 2948: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 294b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 294e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2951: aastore
      // 2952: dup
      // 2953: bipush 13
      // 2955: new net/minecraft/client/animation/Keyframe
      // 2958: dup
      // 2959: ldc 0.54
      // 295b: ldc_w -4.734
      // 295e: ldc_w 41.077
      // 2961: ldc_w 8.632
      // 2964: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2967: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 296a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 296d: aastore
      // 296e: dup
      // 296f: bipush 14
      // 2971: new net/minecraft/client/animation/Keyframe
      // 2974: dup
      // 2975: ldc 0.58
      // 2977: ldc_w -11.822
      // 297a: ldc_w 40.566
      // 297d: ldc_w 16.533
      // 2980: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2983: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2986: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2989: aastore
      // 298a: dup
      // 298b: bipush 15
      // 298d: new net/minecraft/client/animation/Keyframe
      // 2990: dup
      // 2991: ldc 0.62
      // 2993: ldc_w -16.046
      // 2996: ldc_w 39.405
      // 2999: ldc_w 21.314
      // 299c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 299f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29a5: aastore
      // 29a6: dup
      // 29a7: bipush 16
      // 29a9: new net/minecraft/client/animation/Keyframe
      // 29ac: dup
      // 29ad: ldc 0.67
      // 29af: ldc_w -16.298
      // 29b2: ldc_w 40.868
      // 29b5: ldc_w 21.453
      // 29b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29c1: aastore
      // 29c2: dup
      // 29c3: bipush 17
      // 29c5: new net/minecraft/client/animation/Keyframe
      // 29c8: dup
      // 29c9: ldc 0.71
      // 29cb: ldc_w -16.609
      // 29ce: ldc_w 46.008
      // 29d1: ldc_w 21.371
      // 29d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29dd: aastore
      // 29de: dup
      // 29df: bipush 18
      // 29e1: new net/minecraft/client/animation/Keyframe
      // 29e4: dup
      // 29e5: ldc 0.75
      // 29e7: ldc_w -19.055
      // 29ea: ldc_w 51.149
      // 29ed: ldc_w 23.668
      // 29f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29f9: aastore
      // 29fa: dup
      // 29fb: bipush 19
      // 29fd: new net/minecraft/client/animation/Keyframe
      // 2a00: dup
      // 2a01: ldc 0.79
      // 2a03: ldc_w -21.224
      // 2a06: ldc_w 53.049
      // 2a09: ldc_w 25.877
      // 2a0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a15: aastore
      // 2a16: dup
      // 2a17: bipush 20
      // 2a19: new net/minecraft/client/animation/Keyframe
      // 2a1c: dup
      // 2a1d: ldc 0.83
      // 2a1f: ldc_w -22.198
      // 2a22: ldc_w 52.354
      // 2a25: ldc_w 26.964
      // 2a28: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a31: aastore
      // 2a32: dup
      // 2a33: bipush 21
      // 2a35: new net/minecraft/client/animation/Keyframe
      // 2a38: dup
      // 2a39: ldc 0.88
      // 2a3b: ldc_w -23.233
      // 2a3e: ldc_w 50.77
      // 2a41: ldc_w 28.181
      // 2a44: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a4d: aastore
      // 2a4e: dup
      // 2a4f: bipush 22
      // 2a51: new net/minecraft/client/animation/Keyframe
      // 2a54: dup
      // 2a55: ldc 0.92
      // 2a57: ldc_w -25.078
      // 2a5a: ldc_w 48.032
      // 2a5d: ldc_w 30.374
      // 2a60: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a63: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a66: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a69: aastore
      // 2a6a: dup
      // 2a6b: bipush 23
      // 2a6d: new net/minecraft/client/animation/Keyframe
      // 2a70: dup
      // 2a71: ldc 0.96
      // 2a73: ldc_w -38.432
      // 2a76: ldc_w 48.838
      // 2a79: ldc_w 44.541
      // 2a7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a85: aastore
      // 2a86: dup
      // 2a87: bipush 24
      // 2a89: new net/minecraft/client/animation/Keyframe
      // 2a8c: dup
      // 2a8d: fconst_1
      // 2a8e: ldc_w -42.205
      // 2a91: ldc_w 36.863
      // 2a94: ldc_w 50.221
      // 2a97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aa0: aastore
      // 2aa1: dup
      // 2aa2: bipush 25
      // 2aa4: new net/minecraft/client/animation/Keyframe
      // 2aa7: dup
      // 2aa8: ldc 1.04
      // 2aaa: ldc_w -0.905
      // 2aad: ldc_w -6.613
      // 2ab0: ldc_w -2.563
      // 2ab3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ab6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ab9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2abc: aastore
      // 2abd: dup
      // 2abe: bipush 26
      // 2ac0: new net/minecraft/client/animation/Keyframe
      // 2ac3: dup
      // 2ac4: ldc 1.08
      // 2ac6: ldc_w 3.736
      // 2ac9: ldc_w -28.879
      // 2acc: ldc_w -2.869
      // 2acf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ad2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ad5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ad8: aastore
      // 2ad9: dup
      // 2ada: bipush 27
      // 2adc: new net/minecraft/client/animation/Keyframe
      // 2adf: dup
      // 2ae0: ldc 1.12
      // 2ae2: ldc_w 4.63
      // 2ae5: ldc_w -33.684
      // 2ae8: ldc_w -3.511
      // 2aeb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2aee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2af1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2af4: aastore
      // 2af5: dup
      // 2af6: bipush 28
      // 2af8: new net/minecraft/client/animation/Keyframe
      // 2afb: dup
      // 2afc: ldc 1.17
      // 2afe: ldc_w 5.224
      // 2b01: ldc_w -36.59
      // 2b04: ldc_w -3.977
      // 2b07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b10: aastore
      // 2b11: dup
      // 2b12: bipush 29
      // 2b14: new net/minecraft/client/animation/Keyframe
      // 2b17: dup
      // 2b18: ldc 1.21
      // 2b1a: ldc_w 5.266
      // 2b1d: ldc_w -36.663
      // 2b20: ldc_w -4.012
      // 2b23: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b2c: aastore
      // 2b2d: dup
      // 2b2e: bipush 30
      // 2b30: new net/minecraft/client/animation/Keyframe
      // 2b33: dup
      // 2b34: ldc 1.25
      // 2b36: ldc_w 5.209
      // 2b39: ldc_w -36.334
      // 2b3c: ldc_w -3.969
      // 2b3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b48: aastore
      // 2b49: dup
      // 2b4a: bipush 31
      // 2b4c: new net/minecraft/client/animation/Keyframe
      // 2b4f: dup
      // 2b50: ldc 1.29
      // 2b52: ldc_w 4.824
      // 2b55: ldc_w -34.467
      // 2b58: ldc_w -3.665
      // 2b5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b64: aastore
      // 2b65: dup
      // 2b66: bipush 32
      // 2b68: new net/minecraft/client/animation/Keyframe
      // 2b6b: dup
      // 2b6c: ldc 1.33
      // 2b6e: ldc_w 3.839
      // 2b71: ldc_w -29.069
      // 2b74: ldc_w -2.952
      // 2b77: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b80: aastore
      // 2b81: dup
      // 2b82: bipush 33
      // 2b84: new net/minecraft/client/animation/Keyframe
      // 2b87: dup
      // 2b88: ldc 1.38
      // 2b8a: ldc_w 2.31
      // 2b8d: ldc_w -19.786
      // 2b90: ldc_w -2.148
      // 2b93: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b9c: aastore
      // 2b9d: dup
      // 2b9e: bipush 34
      // 2ba0: new net/minecraft/client/animation/Keyframe
      // 2ba3: dup
      // 2ba4: ldc 1.42
      // 2ba6: ldc_w -0.666
      // 2ba9: ldc_w -6.563
      // 2bac: ldc_w -2.726
      // 2baf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bb8: aastore
      // 2bb9: dup
      // 2bba: bipush 35
      // 2bbc: new net/minecraft/client/animation/Keyframe
      // 2bbf: dup
      // 2bc0: ldc 1.46
      // 2bc2: ldc_w 6.775
      // 2bc5: ldc_w 6.172
      // 2bc8: ldc_w 6.195
      // 2bcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bd4: aastore
      // 2bd5: dup
      // 2bd6: bipush 36
      // 2bd8: new net/minecraft/client/animation/Keyframe
      // 2bdb: dup
      // 2bdc: ldc 1.5
      // 2bde: ldc_w 4.233
      // 2be1: ldc_w 9.645
      // 2be4: ldc_w 3.942
      // 2be7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bf0: aastore
      // 2bf1: dup
      // 2bf2: bipush 37
      // 2bf4: new net/minecraft/client/animation/Keyframe
      // 2bf7: dup
      // 2bf8: ldc 1.54
      // 2bfa: ldc_w 9.491
      // 2bfd: ldc_w 4.736
      // 2c00: ldc_w 8.761
      // 2c03: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c0c: aastore
      // 2c0d: dup
      // 2c0e: bipush 38
      // 2c10: new net/minecraft/client/animation/Keyframe
      // 2c13: dup
      // 2c14: ldc 1.58
      // 2c16: ldc_w -2.115
      // 2c19: ldc_w -3.89
      // 2c1c: ldc_w -3.914
      // 2c1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c28: aastore
      // 2c29: dup
      // 2c2a: bipush 39
      // 2c2c: new net/minecraft/client/animation/Keyframe
      // 2c2f: dup
      // 2c30: ldc 1.62
      // 2c32: ldc_w 1.291
      // 2c35: ldc_w -13.119
      // 2c38: ldc_w -2.112
      // 2c3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c44: aastore
      // 2c45: dup
      // 2c46: bipush 40
      // 2c48: new net/minecraft/client/animation/Keyframe
      // 2c4b: dup
      // 2c4c: ldc 1.67
      // 2c4e: ldc_w 3.207
      // 2c51: ldc_w -23.685
      // 2c54: ldc_w -2.641
      // 2c57: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c60: aastore
      // 2c61: dup
      // 2c62: bipush 41
      // 2c64: new net/minecraft/client/animation/Keyframe
      // 2c67: dup
      // 2c68: ldc 1.71
      // 2c6a: ldc_w 5.293
      // 2c6d: ldc_w -35.238
      // 2c70: ldc_w -4.064
      // 2c73: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c7c: aastore
      // 2c7d: dup
      // 2c7e: bipush 42
      // 2c80: new net/minecraft/client/animation/Keyframe
      // 2c83: dup
      // 2c84: ldc 1.75
      // 2c86: ldc_w 7.296
      // 2c89: ldc_w -45.371
      // 2c8c: ldc_w -5.73
      // 2c8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c98: aastore
      // 2c99: dup
      // 2c9a: bipush 43
      // 2c9c: new net/minecraft/client/animation/Keyframe
      // 2c9f: dup
      // 2ca0: ldc 1.79
      // 2ca2: ldc_w 7.818
      // 2ca5: ldc_w -49.181
      // 2ca8: ldc_w -6.178
      // 2cab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cb4: aastore
      // 2cb5: dup
      // 2cb6: bipush 44
      // 2cb8: new net/minecraft/client/animation/Keyframe
      // 2cbb: dup
      // 2cbc: ldc 1.83
      // 2cbe: ldc_w 6.526
      // 2cc1: ldc_w -45.497
      // 2cc4: ldc_w -5.025
      // 2cc7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ccd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cd0: aastore
      // 2cd1: dup
      // 2cd2: bipush 45
      // 2cd4: new net/minecraft/client/animation/Keyframe
      // 2cd7: dup
      // 2cd8: ldc 1.88
      // 2cda: ldc_w 4.99
      // 2cdd: ldc_w -38.668
      // 2ce0: ldc_w -3.729
      // 2ce3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ce6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ce9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cec: aastore
      // 2ced: dup
      // 2cee: bipush 46
      // 2cf0: new net/minecraft/client/animation/Keyframe
      // 2cf3: dup
      // 2cf4: ldc 1.92
      // 2cf6: ldc_w 3.891
      // 2cf9: ldc_w -32.448
      // 2cfc: ldc_w -2.892
      // 2cff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d02: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d05: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d08: aastore
      // 2d09: dup
      // 2d0a: bipush 47
      // 2d0c: new net/minecraft/client/animation/Keyframe
      // 2d0f: dup
      // 2d10: ldc 1.96
      // 2d12: ldc_w 2.854
      // 2d15: ldc_w -26.607
      // 2d18: ldc_w -2.201
      // 2d1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d24: aastore
      // 2d25: dup
      // 2d26: bipush 48
      // 2d28: new net/minecraft/client/animation/Keyframe
      // 2d2b: dup
      // 2d2c: fconst_2
      // 2d2d: ldc_w 2.163
      // 2d30: ldc_w -22.491
      // 2d33: ldc_w -1.837
      // 2d36: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d3f: aastore
      // 2d40: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2d43: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2d46: ldc_w "CTR_upperarm_left.001"
      // 2d49: new net/minecraft/client/animation/AnimationChannel
      // 2d4c: dup
      // 2d4d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2d50: bipush 35
      // 2d52: anewarray 27
      // 2d55: dup
      // 2d56: bipush 0
      // 2d57: new net/minecraft/client/animation/Keyframe
      // 2d5a: dup
      // 2d5b: fconst_0
      // 2d5c: dconst_1
      // 2d5d: dconst_1
      // 2d5e: ldc2_w 1.7860000133514404
      // 2d61: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2d64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d6a: aastore
      // 2d6b: dup
      // 2d6c: bipush 1
      // 2d6d: new net/minecraft/client/animation/Keyframe
      // 2d70: dup
      // 2d71: ldc 0.04
      // 2d73: dconst_1
      // 2d74: dconst_1
      // 2d75: ldc2_w 1.843999981880188
      // 2d78: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2d7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d81: aastore
      // 2d82: dup
      // 2d83: bipush 2
      // 2d84: new net/minecraft/client/animation/Keyframe
      // 2d87: dup
      // 2d88: ldc 0.08
      // 2d8a: dconst_1
      // 2d8b: dconst_1
      // 2d8c: ldc2_w 1.8200000524520874
      // 2d8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2d92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d98: aastore
      // 2d99: dup
      // 2d9a: bipush 3
      // 2d9b: new net/minecraft/client/animation/Keyframe
      // 2d9e: dup
      // 2d9f: ldc 0.17
      // 2da1: dconst_1
      // 2da2: dconst_1
      // 2da3: ldc2_w 1.6670000553131104
      // 2da6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2da9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2daf: aastore
      // 2db0: dup
      // 2db1: bipush 4
      // 2db2: new net/minecraft/client/animation/Keyframe
      // 2db5: dup
      // 2db6: ldc 0.29
      // 2db8: dconst_1
      // 2db9: dconst_1
      // 2dba: ldc2_w 1.378999948501587
      // 2dbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2dc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dc6: aastore
      // 2dc7: dup
      // 2dc8: bipush 5
      // 2dc9: new net/minecraft/client/animation/Keyframe
      // 2dcc: dup
      // 2dcd: ldc 0.33
      // 2dcf: dconst_1
      // 2dd0: dconst_1
      // 2dd1: ldc2_w 1.3200000524520874
      // 2dd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2dd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ddd: aastore
      // 2dde: dup
      // 2ddf: bipush 6
      // 2de1: new net/minecraft/client/animation/Keyframe
      // 2de4: dup
      // 2de5: ldc 0.38
      // 2de7: dconst_1
      // 2de8: dconst_1
      // 2de9: ldc2_w 1.2990000247955322
      // 2dec: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2def: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2df2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2df5: aastore
      // 2df6: dup
      // 2df7: bipush 7
      // 2df9: new net/minecraft/client/animation/Keyframe
      // 2dfc: dup
      // 2dfd: ldc 0.46
      // 2dff: dconst_1
      // 2e00: dconst_1
      // 2e01: ldc2_w 1.2890000343322754
      // 2e04: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2e07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e0d: aastore
      // 2e0e: dup
      // 2e0f: bipush 8
      // 2e11: new net/minecraft/client/animation/Keyframe
      // 2e14: dup
      // 2e15: ldc 0.5
      // 2e17: dconst_1
      // 2e18: dconst_1
      // 2e19: ldc2_w 1.2489999532699585
      // 2e1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2e1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e25: aastore
      // 2e26: dup
      // 2e27: bipush 9
      // 2e29: new net/minecraft/client/animation/Keyframe
      // 2e2c: dup
      // 2e2d: ldc 0.54
      // 2e2f: dconst_1
      // 2e30: dconst_1
      // 2e31: ldc2_w 1.2359999418258667
      // 2e34: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2e37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e3d: aastore
      // 2e3e: dup
      // 2e3f: bipush 10
      // 2e41: new net/minecraft/client/animation/Keyframe
      // 2e44: dup
      // 2e45: ldc 0.58
      // 2e47: dconst_1
      // 2e48: dconst_1
      // 2e49: ldc2_w 1.2070000171661377
      // 2e4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2e4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e55: aastore
      // 2e56: dup
      // 2e57: bipush 11
      // 2e59: new net/minecraft/client/animation/Keyframe
      // 2e5c: dup
      // 2e5d: ldc 0.62
      // 2e5f: dconst_1
      // 2e60: dconst_1
      // 2e61: ldc2_w 1.2599999904632568
      // 2e64: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2e67: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e6a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e6d: aastore
      // 2e6e: dup
      // 2e6f: bipush 12
      // 2e71: new net/minecraft/client/animation/Keyframe
      // 2e74: dup
      // 2e75: ldc 0.67
      // 2e77: dconst_1
      // 2e78: dconst_1
      // 2e79: ldc2_w 1.4279999732971191
      // 2e7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2e7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e85: aastore
      // 2e86: dup
      // 2e87: bipush 13
      // 2e89: new net/minecraft/client/animation/Keyframe
      // 2e8c: dup
      // 2e8d: ldc 0.71
      // 2e8f: dconst_1
      // 2e90: dconst_1
      // 2e91: ldc2_w 1.659999966621399
      // 2e94: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2e97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e9d: aastore
      // 2e9e: dup
      // 2e9f: bipush 14
      // 2ea1: new net/minecraft/client/animation/Keyframe
      // 2ea4: dup
      // 2ea5: ldc 0.75
      // 2ea7: dconst_1
      // 2ea8: dconst_1
      // 2ea9: ldc2_w 1.8559999465942383
      // 2eac: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2eaf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2eb5: aastore
      // 2eb6: dup
      // 2eb7: bipush 15
      // 2eb9: new net/minecraft/client/animation/Keyframe
      // 2ebc: dup
      // 2ebd: ldc 0.79
      // 2ebf: dconst_1
      // 2ec0: dconst_1
      // 2ec1: ldc2_w 1.9739999771118164
      // 2ec4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2ec7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ecd: aastore
      // 2ece: dup
      // 2ecf: bipush 16
      // 2ed1: new net/minecraft/client/animation/Keyframe
      // 2ed4: dup
      // 2ed5: ldc 0.83
      // 2ed7: dconst_1
      // 2ed8: dconst_1
      // 2ed9: ldc2_w 2.0220000743865967
      // 2edc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2edf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ee2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ee5: aastore
      // 2ee6: dup
      // 2ee7: bipush 17
      // 2ee9: new net/minecraft/client/animation/Keyframe
      // 2eec: dup
      // 2eed: ldc 0.88
      // 2eef: dconst_1
      // 2ef0: dconst_1
      // 2ef1: ldc2_w 2.0409998893737793
      // 2ef4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2ef7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2efa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2efd: aastore
      // 2efe: dup
      // 2eff: bipush 18
      // 2f01: new net/minecraft/client/animation/Keyframe
      // 2f04: dup
      // 2f05: ldc 0.92
      // 2f07: dconst_1
      // 2f08: dconst_1
      // 2f09: ldc2_w 2.0360000133514404
      // 2f0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f15: aastore
      // 2f16: dup
      // 2f17: bipush 19
      // 2f19: new net/minecraft/client/animation/Keyframe
      // 2f1c: dup
      // 2f1d: ldc 0.96
      // 2f1f: dconst_1
      // 2f20: dconst_1
      // 2f21: ldc2_w 2.002000093460083
      // 2f24: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f2d: aastore
      // 2f2e: dup
      // 2f2f: bipush 20
      // 2f31: new net/minecraft/client/animation/Keyframe
      // 2f34: dup
      // 2f35: ldc 1.04
      // 2f37: dconst_1
      // 2f38: dconst_1
      // 2f39: ldc2_w 2.009000062942505
      // 2f3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f45: aastore
      // 2f46: dup
      // 2f47: bipush 21
      // 2f49: new net/minecraft/client/animation/Keyframe
      // 2f4c: dup
      // 2f4d: ldc 1.08
      // 2f4f: dconst_1
      // 2f50: dconst_1
      // 2f51: ldc2_w 2.0329999923706055
      // 2f54: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f5d: aastore
      // 2f5e: dup
      // 2f5f: bipush 22
      // 2f61: new net/minecraft/client/animation/Keyframe
      // 2f64: dup
      // 2f65: ldc 1.21
      // 2f67: dconst_1
      // 2f68: dconst_1
      // 2f69: ldc2_w 2.059999942779541
      // 2f6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f75: aastore
      // 2f76: dup
      // 2f77: bipush 23
      // 2f79: new net/minecraft/client/animation/Keyframe
      // 2f7c: dup
      // 2f7d: ldc 1.25
      // 2f7f: dconst_1
      // 2f80: dconst_1
      // 2f81: ldc2_w 2.0859999656677246
      // 2f84: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f8d: aastore
      // 2f8e: dup
      // 2f8f: bipush 24
      // 2f91: new net/minecraft/client/animation/Keyframe
      // 2f94: dup
      // 2f95: ldc 1.38
      // 2f97: dconst_1
      // 2f98: dconst_1
      // 2f99: ldc2_w 2.2260000705718994
      // 2f9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2f9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fa2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fa5: aastore
      // 2fa6: dup
      // 2fa7: bipush 25
      // 2fa9: new net/minecraft/client/animation/Keyframe
      // 2fac: dup
      // 2fad: ldc 1.46
      // 2faf: dconst_1
      // 2fb0: dconst_1
      // 2fb1: ldc2_w 2.365000009536743
      // 2fb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2fb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fbd: aastore
      // 2fbe: dup
      // 2fbf: bipush 26
      // 2fc1: new net/minecraft/client/animation/Keyframe
      // 2fc4: dup
      // 2fc5: ldc 1.5
      // 2fc7: dconst_1
      // 2fc8: dconst_1
      // 2fc9: ldc2_w 2.3499999046325684
      // 2fcc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2fcf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fd5: aastore
      // 2fd6: dup
      // 2fd7: bipush 27
      // 2fd9: new net/minecraft/client/animation/Keyframe
      // 2fdc: dup
      // 2fdd: ldc 1.54
      // 2fdf: dconst_1
      // 2fe0: dconst_1
      // 2fe1: ldc2_w 2.2829999923706055
      // 2fe4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2fe7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fed: aastore
      // 2fee: dup
      // 2fef: bipush 28
      // 2ff1: new net/minecraft/client/animation/Keyframe
      // 2ff4: dup
      // 2ff5: ldc 1.67
      // 2ff7: dconst_1
      // 2ff8: dconst_1
      // 2ff9: ldc2_w 1.9809999465942383
      // 2ffc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 2fff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3002: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3005: aastore
      // 3006: dup
      // 3007: bipush 29
      // 3009: new net/minecraft/client/animation/Keyframe
      // 300c: dup
      // 300d: ldc 1.75
      // 300f: dconst_1
      // 3010: dconst_1
      // 3011: ldc2_w 1.7280000448226929
      // 3014: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3017: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 301a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 301d: aastore
      // 301e: dup
      // 301f: bipush 30
      // 3021: new net/minecraft/client/animation/Keyframe
      // 3024: dup
      // 3025: ldc 1.79
      // 3027: dconst_1
      // 3028: dconst_1
      // 3029: ldc2_w 1.6460000276565552
      // 302c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 302f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3032: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3035: aastore
      // 3036: dup
      // 3037: bipush 31
      // 3039: new net/minecraft/client/animation/Keyframe
      // 303c: dup
      // 303d: ldc 1.83
      // 303f: dconst_1
      // 3040: dconst_1
      // 3041: ldc2_w 1.621999979019165
      // 3044: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3047: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 304a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 304d: aastore
      // 304e: dup
      // 304f: bipush 32
      // 3051: new net/minecraft/client/animation/Keyframe
      // 3054: dup
      // 3055: ldc 1.88
      // 3057: dconst_1
      // 3058: dconst_1
      // 3059: ldc2_w 1.6480000019073486
      // 305c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 305f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3062: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3065: aastore
      // 3066: dup
      // 3067: bipush 33
      // 3069: new net/minecraft/client/animation/Keyframe
      // 306c: dup
      // 306d: ldc 1.96
      // 306f: dconst_1
      // 3070: dconst_1
      // 3071: ldc2_w 1.7519999742507935
      // 3074: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3077: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 307a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 307d: aastore
      // 307e: dup
      // 307f: bipush 34
      // 3081: new net/minecraft/client/animation/Keyframe
      // 3084: dup
      // 3085: fconst_2
      // 3086: dconst_1
      // 3087: dconst_1
      // 3088: ldc2_w 1.7860000133514404
      // 308b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 308e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3091: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3094: aastore
      // 3095: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3098: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 309b: ldc_w "CTR_shoulder_right"
      // 309e: new net/minecraft/client/animation/AnimationChannel
      // 30a1: dup
      // 30a2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 30a5: bipush 30
      // 30a7: anewarray 27
      // 30aa: dup
      // 30ab: bipush 0
      // 30ac: new net/minecraft/client/animation/Keyframe
      // 30af: dup
      // 30b0: fconst_0
      // 30b1: fconst_0
      // 30b2: fconst_0
      // 30b3: fconst_0
      // 30b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30bd: aastore
      // 30be: dup
      // 30bf: bipush 1
      // 30c0: new net/minecraft/client/animation/Keyframe
      // 30c3: dup
      // 30c4: ldc 0.04
      // 30c6: ldc_w -0.454
      // 30c9: ldc_w -0.187
      // 30cc: ldc_w 1.173
      // 30cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30d8: aastore
      // 30d9: dup
      // 30da: bipush 2
      // 30db: new net/minecraft/client/animation/Keyframe
      // 30de: dup
      // 30df: ldc 0.08
      // 30e1: ldc_w -1.699
      // 30e4: ldc_w -0.7
      // 30e7: ldc_w 4.39
      // 30ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30f3: aastore
      // 30f4: dup
      // 30f5: bipush 3
      // 30f6: new net/minecraft/client/animation/Keyframe
      // 30f9: dup
      // 30fa: ldc 0.17
      // 30fc: ldc_w -5.86
      // 30ff: ldc_w -2.412
      // 3102: ldc_w 15.139
      // 3105: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3108: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 310b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 310e: aastore
      // 310f: dup
      // 3110: bipush 4
      // 3111: new net/minecraft/client/animation/Keyframe
      // 3114: dup
      // 3115: ldc 0.21
      // 3117: ldc_w -8.424
      // 311a: ldc_w -3.467
      // 311d: ldc_w 21.763
      // 3120: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3123: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3126: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3129: aastore
      // 312a: dup
      // 312b: bipush 5
      // 312c: new net/minecraft/client/animation/Keyframe
      // 312f: dup
      // 3130: ldc 0.25
      // 3132: ldc_w -11.075
      // 3135: ldc_w -4.559
      // 3138: ldc_w 28.613
      // 313b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 313e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3141: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3144: aastore
      // 3145: dup
      // 3146: bipush 6
      // 3148: new net/minecraft/client/animation/Keyframe
      // 314b: dup
      // 314c: ldc 0.29
      // 314e: ldc_w -13.639
      // 3151: ldc_w -5.614
      // 3154: ldc_w 35.237
      // 3157: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 315a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 315d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3160: aastore
      // 3161: dup
      // 3162: bipush 7
      // 3164: new net/minecraft/client/animation/Keyframe
      // 3167: dup
      // 3168: ldc 0.33
      // 316a: ldc_w -15.939
      // 316d: ldc_w -6.561
      // 3170: ldc_w 41.179
      // 3173: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3176: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3179: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 317c: aastore
      // 317d: dup
      // 317e: bipush 8
      // 3180: new net/minecraft/client/animation/Keyframe
      // 3183: dup
      // 3184: ldc 0.38
      // 3186: ldc_w -17.8
      // 3189: ldc_w -7.327
      // 318c: ldc_w 45.985
      // 318f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3192: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3195: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3198: aastore
      // 3199: dup
      // 319a: bipush 9
      // 319c: new net/minecraft/client/animation/Keyframe
      // 319f: dup
      // 31a0: ldc 0.42
      // 31a2: ldc_w -19.045
      // 31a5: ldc_w -7.839
      // 31a8: ldc_w 49.203
      // 31ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31b4: aastore
      // 31b5: dup
      // 31b6: bipush 10
      // 31b8: new net/minecraft/client/animation/Keyframe
      // 31bb: dup
      // 31bc: ldc 0.46
      // 31be: ldc_w -19.499
      // 31c1: ldc_w -8.026
      // 31c4: ldc_w 50.376
      // 31c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31d0: aastore
      // 31d1: dup
      // 31d2: bipush 11
      // 31d4: new net/minecraft/client/animation/Keyframe
      // 31d7: dup
      // 31d8: ldc 0.96
      // 31da: ldc_w -19.499
      // 31dd: ldc_w -8.026
      // 31e0: ldc_w 50.376
      // 31e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31ec: aastore
      // 31ed: dup
      // 31ee: bipush 12
      // 31f0: new net/minecraft/client/animation/Keyframe
      // 31f3: dup
      // 31f4: fconst_1
      // 31f5: ldc_w -19.371
      // 31f8: ldc_w -7.973
      // 31fb: ldc_w 50.044
      // 31fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3201: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3204: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3207: aastore
      // 3208: dup
      // 3209: bipush 13
      // 320b: new net/minecraft/client/animation/Keyframe
      // 320e: dup
      // 320f: ldc 1.04
      // 3211: ldc_w -19.002
      // 3214: ldc_w -7.822
      // 3217: ldc_w 49.092
      // 321a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 321d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3220: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3223: aastore
      // 3224: dup
      // 3225: bipush 14
      // 3227: new net/minecraft/client/animation/Keyframe
      // 322a: dup
      // 322b: ldc 1.08
      // 322d: ldc_w -18.419
      // 3230: ldc_w -7.582
      // 3233: ldc_w 47.585
      // 3236: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3239: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 323c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 323f: aastore
      // 3240: dup
      // 3241: bipush 15
      // 3243: new net/minecraft/client/animation/Keyframe
      // 3246: dup
      // 3247: ldc 1.17
      // 3249: ldc_w -16.709
      // 324c: ldc_w -6.878
      // 324f: ldc_w 43.168
      // 3252: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3255: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3258: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 325b: aastore
      // 325c: dup
      // 325d: bipush 16
      // 325f: new net/minecraft/client/animation/Keyframe
      // 3262: dup
      // 3263: ldc 1.21
      // 3265: ldc_w -15.634
      // 3268: ldc_w -6.435
      // 326b: ldc_w 40.389
      // 326e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3271: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3274: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3277: aastore
      // 3278: dup
      // 3279: bipush 17
      // 327b: new net/minecraft/client/animation/Keyframe
      // 327e: dup
      // 327f: ldc 1.25
      // 3281: ldc_w -14.444
      // 3284: ldc_w -5.945
      // 3287: ldc_w 37.315
      // 328a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 328d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3290: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3293: aastore
      // 3294: dup
      // 3295: bipush 18
      // 3297: new net/minecraft/client/animation/Keyframe
      // 329a: dup
      // 329b: ldc 1.29
      // 329d: ldc_w -13.166
      // 32a0: ldc_w -5.419
      // 32a3: ldc_w 34.014
      // 32a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32af: aastore
      // 32b0: dup
      // 32b1: bipush 19
      // 32b3: new net/minecraft/client/animation/Keyframe
      // 32b6: dup
      // 32b7: ldc 1.33
      // 32b9: ldc_w -11.825
      // 32bc: ldc_w -4.867
      // 32bf: ldc_w 30.549
      // 32c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32cb: aastore
      // 32cc: dup
      // 32cd: bipush 20
      // 32cf: new net/minecraft/client/animation/Keyframe
      // 32d2: dup
      // 32d3: ldc 1.38
      // 32d5: ldc_w -10.446
      // 32d8: ldc_w -4.3
      // 32db: ldc_w 26.986
      // 32de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32e7: aastore
      // 32e8: dup
      // 32e9: bipush 21
      // 32eb: new net/minecraft/client/animation/Keyframe
      // 32ee: dup
      // 32ef: ldc 1.46
      // 32f1: ldc_w -7.675
      // 32f4: ldc_w -3.159
      // 32f7: ldc_w 19.827
      // 32fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3300: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3303: aastore
      // 3304: dup
      // 3305: bipush 22
      // 3307: new net/minecraft/client/animation/Keyframe
      // 330a: dup
      // 330b: ldc 1.5
      // 330d: ldc_w -6.333
      // 3310: ldc_w -2.607
      // 3313: ldc_w 16.362
      // 3316: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3319: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 331c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 331f: aastore
      // 3320: dup
      // 3321: bipush 23
      // 3323: new net/minecraft/client/animation/Keyframe
      // 3326: dup
      // 3327: ldc 1.54
      // 3329: ldc_w -5.055
      // 332c: ldc_w -2.081
      // 332f: ldc_w 13.06
      // 3332: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3335: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3338: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 333b: aastore
      // 333c: dup
      // 333d: bipush 24
      // 333f: new net/minecraft/client/animation/Keyframe
      // 3342: dup
      // 3343: ldc 1.58
      // 3345: ldc_w -3.866
      // 3348: ldc_w -1.591
      // 334b: ldc_w 9.987
      // 334e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3351: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3354: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3357: aastore
      // 3358: dup
      // 3359: bipush 25
      // 335b: new net/minecraft/client/animation/Keyframe
      // 335e: dup
      // 335f: ldc 1.62
      // 3361: ldc_w -2.79
      // 3364: ldc_w -1.148
      // 3367: ldc_w 7.207
      // 336a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 336d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3370: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3373: aastore
      // 3374: dup
      // 3375: bipush 26
      // 3377: new net/minecraft/client/animation/Keyframe
      // 337a: dup
      // 337b: ldc 1.71
      // 337d: ldc_w -1.08
      // 3380: ldc_w -0.445
      // 3383: ldc_w 2.791
      // 3386: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3389: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 338c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 338f: aastore
      // 3390: dup
      // 3391: bipush 27
      // 3393: new net/minecraft/client/animation/Keyframe
      // 3396: dup
      // 3397: ldc 1.75
      // 3399: ldc_w -0.497
      // 339c: ldc_w -0.205
      // 339f: ldc_w 1.284
      // 33a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33ab: aastore
      // 33ac: dup
      // 33ad: bipush 28
      // 33af: new net/minecraft/client/animation/Keyframe
      // 33b2: dup
      // 33b3: ldc 1.79
      // 33b5: ldc_w -0.128
      // 33b8: ldc_w -0.053
      // 33bb: ldc_w 0.332
      // 33be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33c7: aastore
      // 33c8: dup
      // 33c9: bipush 29
      // 33cb: new net/minecraft/client/animation/Keyframe
      // 33ce: dup
      // 33cf: ldc 1.83
      // 33d1: fconst_0
      // 33d2: fconst_0
      // 33d3: fconst_0
      // 33d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33dd: aastore
      // 33de: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 33e1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 33e4: ldc_w "CTR_shoulder_right"
      // 33e7: new net/minecraft/client/animation/AnimationChannel
      // 33ea: dup
      // 33eb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 33ee: bipush 49
      // 33f0: anewarray 27
      // 33f3: dup
      // 33f4: bipush 0
      // 33f5: new net/minecraft/client/animation/Keyframe
      // 33f8: dup
      // 33f9: fconst_0
      // 33fa: ldc_w 0.016
      // 33fd: ldc_w -1.1
      // 3400: ldc_w -0.172
      // 3403: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3406: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3409: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 340c: aastore
      // 340d: dup
      // 340e: bipush 1
      // 340f: new net/minecraft/client/animation/Keyframe
      // 3412: dup
      // 3413: ldc 0.04
      // 3415: ldc_w 0.017
      // 3418: ldc_w -0.945
      // 341b: ldc_w -0.426
      // 341e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3421: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3424: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3427: aastore
      // 3428: dup
      // 3429: bipush 2
      // 342a: new net/minecraft/client/animation/Keyframe
      // 342d: dup
      // 342e: ldc 0.08
      // 3430: ldc_w 0.022
      // 3433: ldc_w -0.525
      // 3436: ldc_w -1.149
      // 3439: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 343c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 343f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3442: aastore
      // 3443: dup
      // 3444: bipush 3
      // 3445: new net/minecraft/client/animation/Keyframe
      // 3448: dup
      // 3449: ldc 0.12
      // 344b: ldc_w 0.035
      // 344e: ldc_w 0.095
      // 3451: ldc_w -2.278
      // 3454: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3457: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 345a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 345d: aastore
      // 345e: dup
      // 345f: bipush 4
      // 3460: new net/minecraft/client/animation/Keyframe
      // 3463: dup
      // 3464: ldc 0.17
      // 3466: ldc_w 0.061
      // 3469: ldc_w 0.847
      // 346c: ldc_w -3.755
      // 346f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3472: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3475: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3478: aastore
      // 3479: dup
      // 347a: bipush 5
      // 347b: new net/minecraft/client/animation/Keyframe
      // 347e: dup
      // 347f: ldc 0.21
      // 3481: ldc_w 0.103
      // 3484: ldc_w 1.666
      // 3487: ldc_w -5.518
      // 348a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 348d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3490: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3493: aastore
      // 3494: dup
      // 3495: bipush 6
      // 3497: new net/minecraft/client/animation/Keyframe
      // 349a: dup
      // 349b: ldc 0.25
      // 349d: ldc_w 0.167
      // 34a0: ldc_w 2.484
      // 34a3: ldc_w -7.508
      // 34a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 34a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34af: aastore
      // 34b0: dup
      // 34b1: bipush 7
      // 34b3: new net/minecraft/client/animation/Keyframe
      // 34b6: dup
      // 34b7: ldc 0.29
      // 34b9: ldc_w 0.255
      // 34bc: ldc_w 3.237
      // 34bf: ldc_w -9.664
      // 34c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 34c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34cb: aastore
      // 34cc: dup
      // 34cd: bipush 8
      // 34cf: new net/minecraft/client/animation/Keyframe
      // 34d2: dup
      // 34d3: ldc 0.33
      // 34d5: ldc_w 0.373
      // 34d8: ldc_w 3.856
      // 34db: ldc_w -11.925
      // 34de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 34e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34e7: aastore
      // 34e8: dup
      // 34e9: bipush 9
      // 34eb: new net/minecraft/client/animation/Keyframe
      // 34ee: dup
      // 34ef: ldc 0.38
      // 34f1: ldc_w 0.524
      // 34f4: ldc_w 4.277
      // 34f7: ldc_w -14.231
      // 34fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 34fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3500: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3503: aastore
      // 3504: dup
      // 3505: bipush 10
      // 3507: new net/minecraft/client/animation/Keyframe
      // 350a: dup
      // 350b: ldc 0.42
      // 350d: ldc_w 0.712
      // 3510: ldc_w 4.432
      // 3513: ldc_w -16.522
      // 3516: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3519: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 351c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 351f: aastore
      // 3520: dup
      // 3521: bipush 11
      // 3523: new net/minecraft/client/animation/Keyframe
      // 3526: dup
      // 3527: ldc 0.46
      // 3529: ldc_w 1.428
      // 352c: ldc_w 0.617
      // 352f: ldc_w -18.713
      // 3532: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3535: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3538: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 353b: aastore
      // 353c: dup
      // 353d: bipush 12
      // 353f: new net/minecraft/client/animation/Keyframe
      // 3542: dup
      // 3543: ldc 0.5
      // 3545: ldc_w 2.954
      // 3548: ldc_w 1.827
      // 354b: ldc_w -20.53
      // 354e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3551: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3554: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3557: aastore
      // 3558: dup
      // 3559: bipush 13
      // 355b: new net/minecraft/client/animation/Keyframe
      // 355e: dup
      // 355f: ldc 0.54
      // 3561: ldc_w 4.443
      // 3564: ldc_w 3.747
      // 3567: ldc_w -21.304
      // 356a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 356d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3570: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3573: aastore
      // 3574: dup
      // 3575: bipush 14
      // 3577: new net/minecraft/client/animation/Keyframe
      // 357a: dup
      // 357b: ldc 0.58
      // 357d: ldc_w 5.137
      // 3580: ldc_w 4.575
      // 3583: ldc_w -21.213
      // 3586: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3589: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 358c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 358f: aastore
      // 3590: dup
      // 3591: bipush 15
      // 3593: new net/minecraft/client/animation/Keyframe
      // 3596: dup
      // 3597: ldc 0.62
      // 3599: ldc_w 5.263
      // 359c: ldc_w 4.693
      // 359f: ldc_w -20.575
      // 35a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35ab: aastore
      // 35ac: dup
      // 35ad: bipush 16
      // 35af: new net/minecraft/client/animation/Keyframe
      // 35b2: dup
      // 35b3: ldc 0.67
      // 35b5: ldc_w 4.651
      // 35b8: ldc_w 4.002
      // 35bb: ldc_w -18.681
      // 35be: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35c7: aastore
      // 35c8: dup
      // 35c9: bipush 17
      // 35cb: new net/minecraft/client/animation/Keyframe
      // 35ce: dup
      // 35cf: ldc 0.71
      // 35d1: ldc_w 3.163
      // 35d4: ldc_w 2.32
      // 35d7: ldc_w -15.697
      // 35da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35e3: aastore
      // 35e4: dup
      // 35e5: bipush 18
      // 35e7: new net/minecraft/client/animation/Keyframe
      // 35ea: dup
      // 35eb: ldc 0.75
      // 35ed: ldc_w 1.319
      // 35f0: ldc_w 0.237
      // 35f3: ldc_w -12.385
      // 35f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35ff: aastore
      // 3600: dup
      // 3601: bipush 19
      // 3603: new net/minecraft/client/animation/Keyframe
      // 3606: dup
      // 3607: ldc 0.79
      // 3609: ldc_w -0.361
      // 360c: ldc_w -1.66
      // 360f: ldc_w -9.509
      // 3612: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3615: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3618: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 361b: aastore
      // 361c: dup
      // 361d: bipush 20
      // 361f: new net/minecraft/client/animation/Keyframe
      // 3622: dup
      // 3623: ldc 0.83
      // 3625: ldc_w -1.469
      // 3628: ldc_w -2.912
      // 362b: ldc_w -7.654
      // 362e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3631: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3634: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3637: aastore
      // 3638: dup
      // 3639: bipush 21
      // 363b: new net/minecraft/client/animation/Keyframe
      // 363e: dup
      // 363f: ldc 0.88
      // 3641: ldc_w -2.057
      // 3644: ldc_w -3.576
      // 3647: ldc_w -6.699
      // 364a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 364d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3650: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3653: aastore
      // 3654: dup
      // 3655: bipush 22
      // 3657: new net/minecraft/client/animation/Keyframe
      // 365a: dup
      // 365b: ldc 0.92
      // 365d: ldc -2.288
      // 365f: ldc_w -3.838
      // 3662: ldc_w -6.345
      // 3665: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3668: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 366b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 366e: aastore
      // 366f: dup
      // 3670: bipush 23
      // 3672: new net/minecraft/client/animation/Keyframe
      // 3675: dup
      // 3676: ldc 0.96
      // 3678: ldc_w -2.329
      // 367b: ldc_w -3.884
      // 367e: ldc_w -6.293
      // 3681: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3684: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3687: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 368a: aastore
      // 368b: dup
      // 368c: bipush 24
      // 368e: new net/minecraft/client/animation/Keyframe
      // 3691: dup
      // 3692: fconst_1
      // 3693: ldc_w -2.187
      // 3696: ldc_w -3.865
      // 3699: ldc_w -6.336
      // 369c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 369f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36a5: aastore
      // 36a6: dup
      // 36a7: bipush 25
      // 36a9: new net/minecraft/client/animation/Keyframe
      // 36ac: dup
      // 36ad: ldc 1.04
      // 36af: ldc_w -1.799
      // 36b2: ldc_w -3.811
      // 36b5: ldc_w -6.455
      // 36b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36c1: aastore
      // 36c2: dup
      // 36c3: bipush 26
      // 36c5: new net/minecraft/client/animation/Keyframe
      // 36c8: dup
      // 36c9: ldc 1.08
      // 36cb: ldc_w -1.218
      // 36ce: ldc_w -3.732
      // 36d1: ldc_w -6.633
      // 36d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36dd: aastore
      // 36de: dup
      // 36df: bipush 27
      // 36e1: new net/minecraft/client/animation/Keyframe
      // 36e4: dup
      // 36e5: ldc 1.12
      // 36e7: ldc_w -0.5
      // 36ea: ldc_w -3.634
      // 36ed: ldc_w -6.852
      // 36f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36f9: aastore
      // 36fa: dup
      // 36fb: bipush 28
      // 36fd: new net/minecraft/client/animation/Keyframe
      // 3700: dup
      // 3701: ldc 1.17
      // 3703: ldc_w 0.3
      // 3706: ldc_w -3.524
      // 3709: ldc_w -7.096
      // 370c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 370f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3712: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3715: aastore
      // 3716: dup
      // 3717: bipush 29
      // 3719: new net/minecraft/client/animation/Keyframe
      // 371c: dup
      // 371d: ldc 1.21
      // 371f: ldc_w 1.128
      // 3722: ldc_w -3.411
      // 3725: ldc_w -7.349
      // 3728: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 372b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 372e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3731: aastore
      // 3732: dup
      // 3733: bipush 30
      // 3735: new net/minecraft/client/animation/Keyframe
      // 3738: dup
      // 3739: ldc 1.25
      // 373b: ldc_w 1.928
      // 373e: ldc_w -3.301
      // 3741: ldc_w -7.594
      // 3744: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3747: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 374a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 374d: aastore
      // 374e: dup
      // 374f: bipush 31
      // 3751: new net/minecraft/client/animation/Keyframe
      // 3754: dup
      // 3755: ldc 1.29
      // 3757: ldc_w 2.646
      // 375a: ldc_w -3.203
      // 375d: ldc_w -7.813
      // 3760: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3763: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3766: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3769: aastore
      // 376a: dup
      // 376b: bipush 32
      // 376d: new net/minecraft/client/animation/Keyframe
      // 3770: dup
      // 3771: ldc 1.33
      // 3773: ldc_w 3.226
      // 3776: ldc_w -3.123
      // 3779: ldc_w -7.991
      // 377c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 377f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3782: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3785: aastore
      // 3786: dup
      // 3787: bipush 33
      // 3789: new net/minecraft/client/animation/Keyframe
      // 378c: dup
      // 378d: ldc 1.38
      // 378f: ldc_w 3.615
      // 3792: ldc_w -3.07
      // 3795: ldc_w -8.109
      // 3798: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 379b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 379e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37a1: aastore
      // 37a2: dup
      // 37a3: bipush 34
      // 37a5: new net/minecraft/client/animation/Keyframe
      // 37a8: dup
      // 37a9: ldc 1.42
      // 37ab: ldc_w 3.757
      // 37ae: ldc_w -3.051
      // 37b1: ldc_w -8.153
      // 37b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37bd: aastore
      // 37be: dup
      // 37bf: bipush 35
      // 37c1: new net/minecraft/client/animation/Keyframe
      // 37c4: dup
      // 37c5: ldc 1.46
      // 37c7: ldc_w 3.586
      // 37ca: ldc_w -3.074
      // 37cd: ldc_w -8.151
      // 37d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37d9: aastore
      // 37da: dup
      // 37db: bipush 36
      // 37dd: new net/minecraft/client/animation/Keyframe
      // 37e0: dup
      // 37e1: ldc 1.5
      // 37e3: ldc_w 3.124
      // 37e6: ldc_w -3.137
      // 37e9: ldc_w -8.138
      // 37ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37f5: aastore
      // 37f6: dup
      // 37f7: bipush 37
      // 37f9: new net/minecraft/client/animation/Keyframe
      // 37fc: dup
      // 37fd: ldc 1.54
      // 37ff: ldc_w 2.442
      // 3802: ldc_w -3.231
      // 3805: ldc_w -8.103
      // 3808: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 380b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 380e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3811: aastore
      // 3812: dup
      // 3813: bipush 38
      // 3815: new net/minecraft/client/animation/Keyframe
      // 3818: dup
      // 3819: ldc 1.58
      // 381b: ldc_w 1.614
      // 381e: ldc_w -3.344
      // 3821: ldc_w -8.034
      // 3824: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3827: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 382a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 382d: aastore
      // 382e: dup
      // 382f: bipush 39
      // 3831: new net/minecraft/client/animation/Keyframe
      // 3834: dup
      // 3835: ldc 1.62
      // 3837: ldc_w 0.714
      // 383a: ldc_w -3.467
      // 383d: ldc_w -7.92
      // 3840: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3843: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3846: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3849: aastore
      // 384a: dup
      // 384b: bipush 40
      // 384d: new net/minecraft/client/animation/Keyframe
      // 3850: dup
      // 3851: ldc 1.67
      // 3853: ldc_w -0.187
      // 3856: ldc_w -3.591
      // 3859: ldc_w -7.751
      // 385c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 385f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3862: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3865: aastore
      // 3866: dup
      // 3867: bipush 41
      // 3869: new net/minecraft/client/animation/Keyframe
      // 386c: dup
      // 386d: ldc 1.71
      // 386f: ldc_w -1.015
      // 3872: ldc_w -3.704
      // 3875: ldc_w -7.515
      // 3878: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 387b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 387e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3881: aastore
      // 3882: dup
      // 3883: bipush 42
      // 3885: new net/minecraft/client/animation/Keyframe
      // 3888: dup
      // 3889: ldc 1.75
      // 388b: ldc_w -1.696
      // 388e: ldc_w -3.797
      // 3891: ldc_w -7.201
      // 3894: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3897: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 389a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 389d: aastore
      // 389e: dup
      // 389f: bipush 43
      // 38a1: new net/minecraft/client/animation/Keyframe
      // 38a4: dup
      // 38a5: ldc 1.79
      // 38a7: ldc_w -2.159
      // 38aa: ldc_w -3.861
      // 38ad: ldc_w -6.797
      // 38b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38b9: aastore
      // 38ba: dup
      // 38bb: bipush 44
      // 38bd: new net/minecraft/client/animation/Keyframe
      // 38c0: dup
      // 38c1: ldc 1.83
      // 38c3: ldc_w -2.329
      // 38c6: ldc_w -3.884
      // 38c9: ldc_w -6.293
      // 38cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38d5: aastore
      // 38d6: dup
      // 38d7: bipush 45
      // 38d9: new net/minecraft/client/animation/Keyframe
      // 38dc: dup
      // 38dd: ldc 1.88
      // 38df: ldc_w -1.963
      // 38e2: ldc_w -3.449
      // 38e5: ldc_w -5.023
      // 38e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38f1: aastore
      // 38f2: dup
      // 38f3: bipush 46
      // 38f5: new net/minecraft/client/animation/Keyframe
      // 38f8: dup
      // 38f9: ldc 1.92
      // 38fb: ldc_w -1.156
      // 38fe: ldc_w -2.492
      // 3901: ldc_w -2.954
      // 3904: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3907: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 390a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 390d: aastore
      // 390e: dup
      // 390f: bipush 47
      // 3911: new net/minecraft/client/animation/Keyframe
      // 3914: dup
      // 3915: ldc 1.96
      // 3917: ldc_w -0.35
      // 391a: ldc_w -1.535
      // 391d: ldc_w -1.024
      // 3920: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3923: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3926: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3929: aastore
      // 392a: dup
      // 392b: bipush 48
      // 392d: new net/minecraft/client/animation/Keyframe
      // 3930: dup
      // 3931: fconst_2
      // 3932: ldc_w 0.016
      // 3935: ldc_w -1.1
      // 3938: ldc_w -0.172
      // 393b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 393e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3941: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3944: aastore
      // 3945: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3948: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 394b: ldc_w "CTR_upperarm_right"
      // 394e: new net/minecraft/client/animation/AnimationChannel
      // 3951: dup
      // 3952: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3955: bipush 49
      // 3957: anewarray 27
      // 395a: dup
      // 395b: bipush 0
      // 395c: new net/minecraft/client/animation/Keyframe
      // 395f: dup
      // 3960: fconst_0
      // 3961: ldc_w 0.839
      // 3964: ldc_w -2.513
      // 3967: ldc_w 0.452
      // 396a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 396d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3970: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3973: aastore
      // 3974: dup
      // 3975: bipush 1
      // 3976: new net/minecraft/client/animation/Keyframe
      // 3979: dup
      // 397a: ldc 0.04
      // 397c: ldc_w 3.157
      // 397f: ldc_w -2.563
      // 3982: ldc_w 0.257
      // 3985: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3988: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 398b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 398e: aastore
      // 398f: dup
      // 3990: bipush 2
      // 3991: new net/minecraft/client/animation/Keyframe
      // 3994: dup
      // 3995: ldc 0.08
      // 3997: ldc_w 5.706
      // 399a: ldc_w -3.505
      // 399d: ldc_w -1.587
      // 39a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39a9: aastore
      // 39aa: dup
      // 39ab: bipush 3
      // 39ac: new net/minecraft/client/animation/Keyframe
      // 39af: dup
      // 39b0: ldc 0.12
      // 39b2: ldc_w 8.352
      // 39b5: ldc_w -5.1
      // 39b8: ldc_w -4.338
      // 39bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39c4: aastore
      // 39c5: dup
      // 39c6: bipush 4
      // 39c7: new net/minecraft/client/animation/Keyframe
      // 39ca: dup
      // 39cb: ldc 0.17
      // 39cd: ldc_w 11.113
      // 39d0: ldc_w -7.266
      // 39d3: ldc_w -7.315
      // 39d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39df: aastore
      // 39e0: dup
      // 39e1: bipush 5
      // 39e2: new net/minecraft/client/animation/Keyframe
      // 39e5: dup
      // 39e6: ldc 0.21
      // 39e8: ldc_w 13.792
      // 39eb: ldc_w -10.101
      // 39ee: ldc_w -10.132
      // 39f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39fa: aastore
      // 39fb: dup
      // 39fc: bipush 6
      // 39fe: new net/minecraft/client/animation/Keyframe
      // 3a01: dup
      // 3a02: ldc 0.25
      // 3a04: ldc_w 16.023
      // 3a07: ldc_w -13.814
      // 3a0a: ldc_w -12.544
      // 3a0d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a10: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a13: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a16: aastore
      // 3a17: dup
      // 3a18: bipush 7
      // 3a1a: new net/minecraft/client/animation/Keyframe
      // 3a1d: dup
      // 3a1e: ldc 0.29
      // 3a20: ldc_w 17.223
      // 3a23: ldc_w -18.142
      // 3a26: ldc_w -13.876
      // 3a29: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a32: aastore
      // 3a33: dup
      // 3a34: bipush 8
      // 3a36: new net/minecraft/client/animation/Keyframe
      // 3a39: dup
      // 3a3a: ldc 0.33
      // 3a3c: ldc_w 17.126
      // 3a3f: ldc_w -22.521
      // 3a42: ldc_w -13.152
      // 3a45: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a4e: aastore
      // 3a4f: dup
      // 3a50: bipush 9
      // 3a52: new net/minecraft/client/animation/Keyframe
      // 3a55: dup
      // 3a56: ldc 0.38
      // 3a58: ldc_w 16.014
      // 3a5b: ldc_w -26.57
      // 3a5e: ldc_w -10.234
      // 3a61: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a6a: aastore
      // 3a6b: dup
      // 3a6c: bipush 10
      // 3a6e: new net/minecraft/client/animation/Keyframe
      // 3a71: dup
      // 3a72: ldc 0.42
      // 3a74: ldc_w 14.249
      // 3a77: ldc_w -29.806
      // 3a7a: ldc_w -5.759
      // 3a7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a86: aastore
      // 3a87: dup
      // 3a88: bipush 11
      // 3a8a: new net/minecraft/client/animation/Keyframe
      // 3a8d: dup
      // 3a8e: ldc 0.46
      // 3a90: ldc_w 15.392
      // 3a93: ldc_w -29.396
      // 3a96: ldc_w -5.865
      // 3a99: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a9c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a9f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3aa2: aastore
      // 3aa3: dup
      // 3aa4: bipush 12
      // 3aa6: new net/minecraft/client/animation/Keyframe
      // 3aa9: dup
      // 3aaa: ldc 0.5
      // 3aac: ldc_w 28.451
      // 3aaf: ldc_w -24.584
      // 3ab2: ldc_w -21.024
      // 3ab5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ab8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3abb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3abe: aastore
      // 3abf: dup
      // 3ac0: bipush 13
      // 3ac2: new net/minecraft/client/animation/Keyframe
      // 3ac5: dup
      // 3ac6: ldc 0.54
      // 3ac8: ldc_w 30.026
      // 3acb: ldc_w -29.766
      // 3ace: ldc_w -15.172
      // 3ad1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ad4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ad7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ada: aastore
      // 3adb: dup
      // 3adc: bipush 14
      // 3ade: new net/minecraft/client/animation/Keyframe
      // 3ae1: dup
      // 3ae2: ldc 0.58
      // 3ae4: ldc_w 28.942
      // 3ae7: ldc_w -32.559
      // 3aea: ldc_w -24.02
      // 3aed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3af0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3af3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3af6: aastore
      // 3af7: dup
      // 3af8: bipush 15
      // 3afa: new net/minecraft/client/animation/Keyframe
      // 3afd: dup
      // 3afe: ldc 0.62
      // 3b00: ldc_w 27.463
      // 3b03: ldc_w -36.268
      // 3b06: ldc_w -32.623
      // 3b09: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b0c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b0f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b12: aastore
      // 3b13: dup
      // 3b14: bipush 16
      // 3b16: new net/minecraft/client/animation/Keyframe
      // 3b19: dup
      // 3b1a: ldc 0.67
      // 3b1c: ldc_w 24.95
      // 3b1f: ldc_w -42.091
      // 3b22: ldc_w -37.519
      // 3b25: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b28: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b2b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b2e: aastore
      // 3b2f: dup
      // 3b30: bipush 17
      // 3b32: new net/minecraft/client/animation/Keyframe
      // 3b35: dup
      // 3b36: ldc 0.71
      // 3b38: ldc_w 21.223
      // 3b3b: ldc_w -49.783
      // 3b3e: ldc_w -37.716
      // 3b41: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b44: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b47: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b4a: aastore
      // 3b4b: dup
      // 3b4c: bipush 18
      // 3b4e: new net/minecraft/client/animation/Keyframe
      // 3b51: dup
      // 3b52: ldc 0.75
      // 3b54: ldc_w 19.643
      // 3b57: ldc_w -57.012
      // 3b5a: ldc_w -38.629
      // 3b5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b66: aastore
      // 3b67: dup
      // 3b68: bipush 19
      // 3b6a: new net/minecraft/client/animation/Keyframe
      // 3b6d: dup
      // 3b6e: ldc 0.79
      // 3b70: ldc_w 28.062
      // 3b73: ldc_w -60.933
      // 3b76: ldc_w -49.423
      // 3b79: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b7c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b7f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b82: aastore
      // 3b83: dup
      // 3b84: bipush 20
      // 3b86: new net/minecraft/client/animation/Keyframe
      // 3b89: dup
      // 3b8a: ldc 0.83
      // 3b8c: ldc_w 35.72
      // 3b8f: ldc_w -62.078
      // 3b92: ldc_w -57.753
      // 3b95: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b98: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b9b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b9e: aastore
      // 3b9f: dup
      // 3ba0: bipush 21
      // 3ba2: new net/minecraft/client/animation/Keyframe
      // 3ba5: dup
      // 3ba6: ldc 0.88
      // 3ba8: ldc_w 42.974
      // 3bab: ldc_w -61.213
      // 3bae: ldc_w -65.907
      // 3bb1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bb4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bb7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bba: aastore
      // 3bbb: dup
      // 3bbc: bipush 22
      // 3bbe: new net/minecraft/client/animation/Keyframe
      // 3bc1: dup
      // 3bc2: ldc 0.92
      // 3bc4: ldc_w 50.376
      // 3bc7: ldc_w -58.501
      // 3bca: ldc_w -74.967
      // 3bcd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bd0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bd3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bd6: aastore
      // 3bd7: dup
      // 3bd8: bipush 23
      // 3bda: new net/minecraft/client/animation/Keyframe
      // 3bdd: dup
      // 3bde: ldc 0.96
      // 3be0: ldc_w 36.479
      // 3be3: ldc_w -51.936
      // 3be6: ldc_w -51.188
      // 3be9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bf2: aastore
      // 3bf3: dup
      // 3bf4: bipush 24
      // 3bf6: new net/minecraft/client/animation/Keyframe
      // 3bf9: dup
      // 3bfa: fconst_1
      // 3bfb: ldc_w 20.158
      // 3bfe: ldc_w -21.625
      // 3c01: ldc_w -5.969
      // 3c04: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c0d: aastore
      // 3c0e: dup
      // 3c0f: bipush 25
      // 3c11: new net/minecraft/client/animation/Keyframe
      // 3c14: dup
      // 3c15: ldc 1.04
      // 3c17: ldc_w 18.507
      // 3c1a: ldc_w 15.022
      // 3c1d: ldc_w 23.114
      // 3c20: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c29: aastore
      // 3c2a: dup
      // 3c2b: bipush 26
      // 3c2d: new net/minecraft/client/animation/Keyframe
      // 3c30: dup
      // 3c31: ldc 1.08
      // 3c33: ldc_w 7.643
      // 3c36: ldc_w 24.651
      // 3c39: ldc_w 16.211
      // 3c3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c45: aastore
      // 3c46: dup
      // 3c47: bipush 27
      // 3c49: new net/minecraft/client/animation/Keyframe
      // 3c4c: dup
      // 3c4d: ldc 1.12
      // 3c4f: ldc_w -4.053
      // 3c52: ldc_w 25.843
      // 3c55: ldc_w 12.022
      // 3c58: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c61: aastore
      // 3c62: dup
      // 3c63: bipush 28
      // 3c65: new net/minecraft/client/animation/Keyframe
      // 3c68: dup
      // 3c69: ldc 1.17
      // 3c6b: ldc_w -13.313
      // 3c6e: ldc_w 28.517
      // 3c71: ldc_w 11.561
      // 3c74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c7d: aastore
      // 3c7e: dup
      // 3c7f: bipush 29
      // 3c81: new net/minecraft/client/animation/Keyframe
      // 3c84: dup
      // 3c85: ldc 1.21
      // 3c87: ldc_w -16.488
      // 3c8a: ldc_w 30.258
      // 3c8d: ldc_w 12.78
      // 3c90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c99: aastore
      // 3c9a: dup
      // 3c9b: bipush 30
      // 3c9d: new net/minecraft/client/animation/Keyframe
      // 3ca0: dup
      // 3ca1: ldc 1.25
      // 3ca3: ldc_w -15.254
      // 3ca6: ldc_w 30.75
      // 3ca9: ldc_w 14.583
      // 3cac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3caf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cb5: aastore
      // 3cb6: dup
      // 3cb7: bipush 31
      // 3cb9: new net/minecraft/client/animation/Keyframe
      // 3cbc: dup
      // 3cbd: ldc 1.29
      // 3cbf: ldc_w -15.105
      // 3cc2: ldc_w 30.814
      // 3cc5: ldc_w 14.821
      // 3cc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ccb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cd1: aastore
      // 3cd2: dup
      // 3cd3: bipush 32
      // 3cd5: new net/minecraft/client/animation/Keyframe
      // 3cd8: dup
      // 3cd9: ldc 1.33
      // 3cdb: ldc_w -16.488
      // 3cde: ldc_w 30.258
      // 3ce1: ldc_w 12.78
      // 3ce4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ce7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ced: aastore
      // 3cee: dup
      // 3cef: bipush 33
      // 3cf1: new net/minecraft/client/animation/Keyframe
      // 3cf4: dup
      // 3cf5: ldc 1.38
      // 3cf7: ldc_w -16.745
      // 3cfa: ldc_w 26.215
      // 3cfd: ldc_w 8.544
      // 3d00: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d09: aastore
      // 3d0a: dup
      // 3d0b: bipush 34
      // 3d0d: new net/minecraft/client/animation/Keyframe
      // 3d10: dup
      // 3d11: ldc 1.42
      // 3d13: ldc_w -16.618
      // 3d16: ldc_w 21.791
      // 3d19: ldc_w 4.253
      // 3d1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d25: aastore
      // 3d26: dup
      // 3d27: bipush 35
      // 3d29: new net/minecraft/client/animation/Keyframe
      // 3d2c: dup
      // 3d2d: ldc 1.46
      // 3d2f: ldc_w -16.343
      // 3d32: ldc_w 17.441
      // 3d35: ldc_w 0.095
      // 3d38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d41: aastore
      // 3d42: dup
      // 3d43: bipush 36
      // 3d45: new net/minecraft/client/animation/Keyframe
      // 3d48: dup
      // 3d49: ldc 1.5
      // 3d4b: ldc_w -15.945
      // 3d4e: ldc_w 13.232
      // 3d51: ldc_w -3.9
      // 3d54: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d5d: aastore
      // 3d5e: dup
      // 3d5f: bipush 37
      // 3d61: new net/minecraft/client/animation/Keyframe
      // 3d64: dup
      // 3d65: ldc 1.54
      // 3d67: ldc_w -15.43
      // 3d6a: ldc_w 9.202
      // 3d6d: ldc_w -7.708
      // 3d70: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d79: aastore
      // 3d7a: dup
      // 3d7b: bipush 38
      // 3d7d: new net/minecraft/client/animation/Keyframe
      // 3d80: dup
      // 3d81: ldc 1.58
      // 3d83: ldc_w -14.792
      // 3d86: ldc_w 5.387
      // 3d89: ldc_w -11.3
      // 3d8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d95: aastore
      // 3d96: dup
      // 3d97: bipush 39
      // 3d99: new net/minecraft/client/animation/Keyframe
      // 3d9c: dup
      // 3d9d: ldc 1.62
      // 3d9f: ldc_w -14.02
      // 3da2: ldc 1.83
      // 3da4: ldc_w -14.641
      // 3da7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3daa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3db0: aastore
      // 3db1: dup
      // 3db2: bipush 40
      // 3db4: new net/minecraft/client/animation/Keyframe
      // 3db7: dup
      // 3db8: ldc 1.67
      // 3dba: ldc_w -13.094
      // 3dbd: ldc_w -1.414
      // 3dc0: ldc_w -17.682
      // 3dc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dcc: aastore
      // 3dcd: dup
      // 3dce: bipush 41
      // 3dd0: new net/minecraft/client/animation/Keyframe
      // 3dd3: dup
      // 3dd4: ldc 1.71
      // 3dd6: ldc_w -11.983
      // 3dd9: ldc_w -4.27
      // 3ddc: ldc_w -20.354
      // 3ddf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3de2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3de5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3de8: aastore
      // 3de9: dup
      // 3dea: bipush 42
      // 3dec: new net/minecraft/client/animation/Keyframe
      // 3def: dup
      // 3df0: ldc 1.75
      // 3df2: ldc_w -10.642
      // 3df5: ldc_w -6.626
      // 3df8: ldc_w -22.555
      // 3dfb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dfe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e04: aastore
      // 3e05: dup
      // 3e06: bipush 43
      // 3e08: new net/minecraft/client/animation/Keyframe
      // 3e0b: dup
      // 3e0c: ldc 1.79
      // 3e0e: ldc_w -8.993
      // 3e11: ldc_w -8.304
      // 3e14: ldc_w -24.121
      // 3e17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e20: aastore
      // 3e21: dup
      // 3e22: bipush 44
      // 3e24: new net/minecraft/client/animation/Keyframe
      // 3e27: dup
      // 3e28: ldc 1.83
      // 3e2a: ldc_w -6.897
      // 3e2d: ldc_w -8.983
      // 3e30: ldc_w -24.754
      // 3e33: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e3c: aastore
      // 3e3d: dup
      // 3e3e: bipush 45
      // 3e40: new net/minecraft/client/animation/Keyframe
      // 3e43: dup
      // 3e44: ldc 1.88
      // 3e46: ldc_w -3.21
      // 3e49: ldc_w -5.129
      // 3e4c: ldc_w -19.068
      // 3e4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e58: aastore
      // 3e59: dup
      // 3e5a: bipush 46
      // 3e5c: new net/minecraft/client/animation/Keyframe
      // 3e5f: dup
      // 3e60: ldc 1.92
      // 3e62: ldc_w 0.076
      // 3e65: ldc_w -1.718
      // 3e68: ldc_w -9.344
      // 3e6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e74: aastore
      // 3e75: dup
      // 3e76: bipush 47
      // 3e78: new net/minecraft/client/animation/Keyframe
      // 3e7b: dup
      // 3e7c: ldc 1.96
      // 3e7e: ldc_w 0.638
      // 3e81: ldc_w -1.982
      // 3e84: ldc_w -2.735
      // 3e87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e90: aastore
      // 3e91: dup
      // 3e92: bipush 48
      // 3e94: new net/minecraft/client/animation/Keyframe
      // 3e97: dup
      // 3e98: fconst_2
      // 3e99: ldc_w 0.839
      // 3e9c: ldc_w -2.513
      // 3e9f: ldc_w 0.452
      // 3ea2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ea5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ea8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eab: aastore
      // 3eac: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3eaf: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3eb2: ldc_w "CTR_lowerarm_right"
      // 3eb5: new net/minecraft/client/animation/AnimationChannel
      // 3eb8: dup
      // 3eb9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3ebc: bipush 49
      // 3ebe: anewarray 27
      // 3ec1: dup
      // 3ec2: bipush 0
      // 3ec3: new net/minecraft/client/animation/Keyframe
      // 3ec6: dup
      // 3ec7: fconst_0
      // 3ec8: ldc_w -0.021
      // 3ecb: ldc_w -1.724
      // 3ece: ldc_w -0.014
      // 3ed1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ed4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ed7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eda: aastore
      // 3edb: dup
      // 3edc: bipush 1
      // 3edd: new net/minecraft/client/animation/Keyframe
      // 3ee0: dup
      // 3ee1: ldc 0.04
      // 3ee3: ldc_w -1.175
      // 3ee6: ldc_w -9.015
      // 3ee9: ldc_w -0.03
      // 3eec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3eef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ef2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ef5: aastore
      // 3ef6: dup
      // 3ef7: bipush 2
      // 3ef8: new net/minecraft/client/animation/Keyframe
      // 3efb: dup
      // 3efc: ldc 0.08
      // 3efe: ldc_w -2.431
      // 3f01: ldc_w -9.218
      // 3f04: ldc_w 0.505
      // 3f07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f10: aastore
      // 3f11: dup
      // 3f12: bipush 3
      // 3f13: new net/minecraft/client/animation/Keyframe
      // 3f16: dup
      // 3f17: ldc 0.12
      // 3f19: ldc_w -3.179
      // 3f1c: ldc_w -5.439
      // 3f1f: ldc_w 1.509
      // 3f22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f2b: aastore
      // 3f2c: dup
      // 3f2d: bipush 4
      // 3f2e: new net/minecraft/client/animation/Keyframe
      // 3f31: dup
      // 3f32: ldc 0.17
      // 3f34: ldc_w -3.376
      // 3f37: ldc_w 0.088
      // 3f3a: ldc_w 2.911
      // 3f3d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f40: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f43: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f46: aastore
      // 3f47: dup
      // 3f48: bipush 5
      // 3f49: new net/minecraft/client/animation/Keyframe
      // 3f4c: dup
      // 3f4d: ldc 0.21
      // 3f4f: ldc_w -2.881
      // 3f52: ldc_w 5.961
      // 3f55: ldc_w 4.765
      // 3f58: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f61: aastore
      // 3f62: dup
      // 3f63: bipush 6
      // 3f65: new net/minecraft/client/animation/Keyframe
      // 3f68: dup
      // 3f69: ldc 0.25
      // 3f6b: ldc_w -1.574
      // 3f6e: ldc_w 11.063
      // 3f71: ldc_w 7.148
      // 3f74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f7d: aastore
      // 3f7e: dup
      // 3f7f: bipush 7
      // 3f81: new net/minecraft/client/animation/Keyframe
      // 3f84: dup
      // 3f85: ldc 0.29
      // 3f87: ldc_w 0.423
      // 3f8a: ldc_w 14.443
      // 3f8d: ldc_w 9.972
      // 3f90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f99: aastore
      // 3f9a: dup
      // 3f9b: bipush 8
      // 3f9d: new net/minecraft/client/animation/Keyframe
      // 3fa0: dup
      // 3fa1: ldc 0.33
      // 3fa3: ldc_w 2.514
      // 3fa6: ldc_w 15.002
      // 3fa9: ldc_w 12.731
      // 3fac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3faf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fb5: aastore
      // 3fb6: dup
      // 3fb7: bipush 9
      // 3fb9: new net/minecraft/client/animation/Keyframe
      // 3fbc: dup
      // 3fbd: ldc 0.38
      // 3fbf: ldc_w 3.665
      // 3fc2: ldc_w 11.247
      // 3fc5: ldc_w 14.533
      // 3fc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fcb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fd1: aastore
      // 3fd2: dup
      // 3fd3: bipush 10
      // 3fd5: new net/minecraft/client/animation/Keyframe
      // 3fd8: dup
      // 3fd9: ldc 0.42
      // 3fdb: ldc_w 3.235
      // 3fde: ldc_w 1.52
      // 3fe1: ldc_w 14.905
      // 3fe4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fe7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fed: aastore
      // 3fee: dup
      // 3fef: bipush 11
      // 3ff1: new net/minecraft/client/animation/Keyframe
      // 3ff4: dup
      // 3ff5: ldc 0.46
      // 3ff7: ldc_w 3.343
      // 3ffa: ldc_w 0.308
      // 3ffd: ldc_w 15.241
      // 4000: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4003: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4006: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4009: aastore
      // 400a: dup
      // 400b: bipush 12
      // 400d: new net/minecraft/client/animation/Keyframe
      // 4010: dup
      // 4011: ldc 0.5
      // 4013: ldc_w 6.758
      // 4016: ldc_w 18.519
      // 4019: ldc_w 17.824
      // 401c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 401f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4022: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4025: aastore
      // 4026: dup
      // 4027: bipush 13
      // 4029: new net/minecraft/client/animation/Keyframe
      // 402c: dup
      // 402d: ldc 0.54
      // 402f: ldc_w 1.752
      // 4032: ldc_w -13.916
      // 4035: ldc_w 14.39
      // 4038: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 403b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 403e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4041: aastore
      // 4042: dup
      // 4043: bipush 14
      // 4045: new net/minecraft/client/animation/Keyframe
      // 4048: dup
      // 4049: ldc 0.58
      // 404b: ldc_w 1.779
      // 404e: ldc_w -13.639
      // 4051: ldc_w 14.401
      // 4054: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4057: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 405a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 405d: aastore
      // 405e: dup
      // 405f: bipush 15
      // 4061: new net/minecraft/client/animation/Keyframe
      // 4064: dup
      // 4065: ldc 0.62
      // 4067: ldc_w 1.926
      // 406a: ldc_w -12.147
      // 406d: ldc_w 14.465
      // 4070: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4073: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4076: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4079: aastore
      // 407a: dup
      // 407b: bipush 16
      // 407d: new net/minecraft/client/animation/Keyframe
      // 4080: dup
      // 4081: ldc 0.67
      // 4083: ldc_w 1.615
      // 4086: ldc_w -15.354
      // 4089: ldc_w 14.333
      // 408c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 408f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4092: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4095: aastore
      // 4096: dup
      // 4097: bipush 17
      // 4099: new net/minecraft/client/animation/Keyframe
      // 409c: dup
      // 409d: ldc 0.71
      // 409f: ldc_w 0.934
      // 40a2: ldc_w -22.952
      // 40a5: ldc_w 14.103
      // 40a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40b1: aastore
      // 40b2: dup
      // 40b3: bipush 18
      // 40b5: new net/minecraft/client/animation/Keyframe
      // 40b8: dup
      // 40b9: ldc 0.75
      // 40bb: ldc_w 0.638
      // 40be: ldc_w -26.464
      // 40c1: ldc_w 14.03
      // 40c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40cd: aastore
      // 40ce: dup
      // 40cf: bipush 19
      // 40d1: new net/minecraft/client/animation/Keyframe
      // 40d4: dup
      // 40d5: ldc 0.79
      // 40d7: ldc_w 1.494
      // 40da: ldc_w -16.649
      // 40dd: ldc_w 14.286
      // 40e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40e9: aastore
      // 40ea: dup
      // 40eb: bipush 20
      // 40ed: new net/minecraft/client/animation/Keyframe
      // 40f0: dup
      // 40f1: ldc 0.83
      // 40f3: ldc_w 2.217
      // 40f6: ldc_w -9.295
      // 40f9: ldc_w 14.603
      // 40fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4102: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4105: aastore
      // 4106: dup
      // 4107: bipush 21
      // 4109: new net/minecraft/client/animation/Keyframe
      // 410c: dup
      // 410d: ldc 0.88
      // 410f: ldc_w 3.143
      // 4112: ldc_w -1.231
      // 4115: ldc_w 15.117
      // 4118: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 411b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 411e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4121: aastore
      // 4122: dup
      // 4123: bipush 22
      // 4125: new net/minecraft/client/animation/Keyframe
      // 4128: dup
      // 4129: ldc 0.92
      // 412b: ldc_w 4.704
      // 412e: ldc_w 9.212
      // 4131: ldc_w 16.184
      // 4134: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4137: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 413a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 413d: aastore
      // 413e: dup
      // 413f: bipush 23
      // 4141: new net/minecraft/client/animation/Keyframe
      // 4144: dup
      // 4145: ldc 0.96
      // 4147: ldc_w 5.035
      // 414a: ldc_w 11.0
      // 414d: ldc_w 16.434
      // 4150: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4153: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4156: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4159: aastore
      // 415a: dup
      // 415b: bipush 24
      // 415d: new net/minecraft/client/animation/Keyframe
      // 4160: dup
      // 4161: fconst_1
      // 4162: ldc_w 3.05
      // 4165: ldc_w -2.05
      // 4168: ldc_w 15.036
      // 416b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 416e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4171: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4174: aastore
      // 4175: dup
      // 4176: bipush 25
      // 4178: new net/minecraft/client/animation/Keyframe
      // 417b: dup
      // 417c: ldc 1.04
      // 417e: ldc_w 0.22
      // 4181: ldc_w -31.648
      // 4184: ldc_w 13.87
      // 4187: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 418a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 418d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4190: aastore
      // 4191: dup
      // 4192: bipush 26
      // 4194: new net/minecraft/client/animation/Keyframe
      // 4197: dup
      // 4198: ldc 1.08
      // 419a: ldc_w 1.669
      // 419d: ldc_w -15.168
      // 41a0: ldc_w 14.147
      // 41a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41ac: aastore
      // 41ad: dup
      // 41ae: bipush 27
      // 41b0: new net/minecraft/client/animation/Keyframe
      // 41b3: dup
      // 41b4: ldc 1.12
      // 41b6: ldc_w 4.015
      // 41b9: ldc_w 4.251
      // 41bc: ldc_w 15.292
      // 41bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41c8: aastore
      // 41c9: dup
      // 41ca: bipush 28
      // 41cc: new net/minecraft/client/animation/Keyframe
      // 41cf: dup
      // 41d0: ldc 1.17
      // 41d2: ldc_w 8.067
      // 41d5: ldc_w 21.833
      // 41d8: ldc_w 18.298
      // 41db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41e4: aastore
      // 41e5: dup
      // 41e6: bipush 29
      // 41e8: new net/minecraft/client/animation/Keyframe
      // 41eb: dup
      // 41ec: ldc 1.21
      // 41ee: ldc_w 9.889
      // 41f1: ldc_w 26.163
      // 41f4: ldc_w 19.646
      // 41f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4200: aastore
      // 4201: dup
      // 4202: bipush 30
      // 4204: new net/minecraft/client/animation/Keyframe
      // 4207: dup
      // 4208: ldc 1.25
      // 420a: ldc_w 9.541
      // 420d: ldc_w 25.302
      // 4210: ldc_w 19.015
      // 4213: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4216: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4219: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 421c: aastore
      // 421d: dup
      // 421e: bipush 31
      // 4220: new net/minecraft/client/animation/Keyframe
      // 4223: dup
      // 4224: ldc 1.29
      // 4226: ldc_w 9.1
      // 4229: ldc_w 24.242
      // 422c: ldc_w 18.266
      // 422f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4232: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4235: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4238: aastore
      // 4239: dup
      // 423a: bipush 32
      // 423c: new net/minecraft/client/animation/Keyframe
      // 423f: dup
      // 4240: ldc 1.33
      // 4242: ldc_w 8.573
      // 4245: ldc_w 22.961
      // 4248: ldc_w 17.407
      // 424b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 424e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4251: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4254: aastore
      // 4255: dup
      // 4256: bipush 33
      // 4258: new net/minecraft/client/animation/Keyframe
      // 425b: dup
      // 425c: ldc 1.38
      // 425e: ldc_w 7.975
      // 4261: ldc_w 21.441
      // 4264: ldc_w 16.455
      // 4267: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 426a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 426d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4270: aastore
      // 4271: dup
      // 4272: bipush 34
      // 4274: new net/minecraft/client/animation/Keyframe
      // 4277: dup
      // 4278: ldc 1.42
      // 427a: ldc_w 7.321
      // 427d: ldc_w 19.66
      // 4280: ldc_w 15.427
      // 4283: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4286: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4289: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 428c: aastore
      // 428d: dup
      // 428e: bipush 35
      // 4290: new net/minecraft/client/animation/Keyframe
      // 4293: dup
      // 4294: ldc 1.46
      // 4296: ldc_w 6.632
      // 4299: ldc_w 17.598
      // 429c: ldc_w 14.343
      // 429f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42a8: aastore
      // 42a9: dup
      // 42aa: bipush 36
      // 42ac: new net/minecraft/client/animation/Keyframe
      // 42af: dup
      // 42b0: ldc 1.5
      // 42b2: ldc_w 5.93
      // 42b5: ldc_w 15.234
      // 42b8: ldc_w 13.221
      // 42bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42c4: aastore
      // 42c5: dup
      // 42c6: bipush 37
      // 42c8: new net/minecraft/client/animation/Keyframe
      // 42cb: dup
      // 42cc: ldc 1.54
      // 42ce: ldc_w 5.235
      // 42d1: ldc_w 12.547
      // 42d4: ldc_w 12.077
      // 42d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42e0: aastore
      // 42e1: dup
      // 42e2: bipush 38
      // 42e4: new net/minecraft/client/animation/Keyframe
      // 42e7: dup
      // 42e8: ldc 1.58
      // 42ea: ldc_w 4.568
      // 42ed: ldc_w 9.518
      // 42f0: ldc_w 10.924
      // 42f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42fc: aastore
      // 42fd: dup
      // 42fe: bipush 39
      // 4300: new net/minecraft/client/animation/Keyframe
      // 4303: dup
      // 4304: ldc 1.62
      // 4306: ldc_w 3.949
      // 4309: ldc_w 6.126
      // 430c: ldc_w 9.769
      // 430f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4312: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4315: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4318: aastore
      // 4319: dup
      // 431a: bipush 40
      // 431c: new net/minecraft/client/animation/Keyframe
      // 431f: dup
      // 4320: ldc 1.67
      // 4322: ldc_w 3.397
      // 4325: ldc_w 2.353
      // 4328: ldc_w 8.615
      // 432b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 432e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4331: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4334: aastore
      // 4335: dup
      // 4336: bipush 41
      // 4338: new net/minecraft/client/animation/Keyframe
      // 433b: dup
      // 433c: ldc 1.71
      // 433e: ldc_w 2.932
      // 4341: ldc_w -1.819
      // 4344: ldc_w 7.461
      // 4347: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 434a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 434d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4350: aastore
      // 4351: dup
      // 4352: bipush 42
      // 4354: new net/minecraft/client/animation/Keyframe
      // 4357: dup
      // 4358: ldc 1.75
      // 435a: ldc_w 2.574
      // 435d: ldc_w -6.405
      // 4360: ldc_w 6.297
      // 4363: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4366: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4369: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 436c: aastore
      // 436d: dup
      // 436e: bipush 43
      // 4370: new net/minecraft/client/animation/Keyframe
      // 4373: dup
      // 4374: ldc 1.79
      // 4376: ldc_w 2.349
      // 4379: ldc_w -11.422
      // 437c: ldc_w 5.111
      // 437f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4382: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4385: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4388: aastore
      // 4389: dup
      // 438a: bipush 44
      // 438c: new net/minecraft/client/animation/Keyframe
      // 438f: dup
      // 4390: ldc 1.83
      // 4392: ldc_w 2.286
      // 4395: ldc_w -16.88
      // 4398: ldc_w 3.879
      // 439b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 439e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43a4: aastore
      // 43a5: dup
      // 43a6: bipush 45
      // 43a8: new net/minecraft/client/animation/Keyframe
      // 43ab: dup
      // 43ac: ldc 1.88
      // 43ae: ldc_w 2.354
      // 43b1: ldc_w -19.207
      // 43b4: ldc_w 2.706
      // 43b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 43ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43c0: aastore
      // 43c1: dup
      // 43c2: bipush 46
      // 43c4: new net/minecraft/client/animation/Keyframe
      // 43c7: dup
      // 43c8: ldc 1.92
      // 43ca: ldc_w 2.933
      // 43cd: ldc_w -12.609
      // 43d0: ldc_w 1.907
      // 43d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 43d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43dc: aastore
      // 43dd: dup
      // 43de: bipush 47
      // 43e0: new net/minecraft/client/animation/Keyframe
      // 43e3: dup
      // 43e4: ldc 1.96
      // 43e6: ldc_w 2.083
      // 43e9: ldc_w -3.335
      // 43ec: ldc_w 1.029
      // 43ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 43f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43f8: aastore
      // 43f9: dup
      // 43fa: bipush 48
      // 43fc: new net/minecraft/client/animation/Keyframe
      // 43ff: dup
      // 4400: fconst_2
      // 4401: ldc_w -0.021
      // 4404: ldc_w -1.724
      // 4407: ldc_w -0.014
      // 440a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 440d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4410: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4413: aastore
      // 4414: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4417: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 441a: ldc_w "CTR_upperarm_right.001"
      // 441d: new net/minecraft/client/animation/AnimationChannel
      // 4420: dup
      // 4421: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4424: bipush 48
      // 4426: anewarray 27
      // 4429: dup
      // 442a: bipush 0
      // 442b: new net/minecraft/client/animation/Keyframe
      // 442e: dup
      // 442f: fconst_0
      // 4430: ldc_w 0.168
      // 4433: ldc_w 17.864
      // 4436: ldc_w 2.048
      // 4439: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 443c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 443f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4442: aastore
      // 4443: dup
      // 4444: bipush 1
      // 4445: new net/minecraft/client/animation/Keyframe
      // 4448: dup
      // 4449: ldc 0.04
      // 444b: ldc_w -0.293
      // 444e: ldc_w 13.991
      // 4451: ldc_w 1.589
      // 4454: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4457: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 445a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 445d: aastore
      // 445e: dup
      // 445f: bipush 2
      // 4460: new net/minecraft/client/animation/Keyframe
      // 4463: dup
      // 4464: ldc 0.08
      // 4466: ldc_w -0.571
      // 4469: ldc_w 13.804
      // 446c: ldc_w 1.363
      // 446f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4472: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4475: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4478: aastore
      // 4479: dup
      // 447a: bipush 3
      // 447b: new net/minecraft/client/animation/Keyframe
      // 447e: dup
      // 447f: ldc 0.17
      // 4481: ldc_w -0.8
      // 4484: ldc_w 17.423
      // 4487: ldc_w 1.234
      // 448a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 448d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4490: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4493: aastore
      // 4494: dup
      // 4495: bipush 4
      // 4496: new net/minecraft/client/animation/Keyframe
      // 4499: dup
      // 449a: ldc 0.21
      // 449c: ldc_w -0.862
      // 449f: ldc_w 18.923
      // 44a2: ldc_w 1.2
      // 44a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44ae: aastore
      // 44af: dup
      // 44b0: bipush 5
      // 44b1: new net/minecraft/client/animation/Keyframe
      // 44b4: dup
      // 44b5: ldc 0.25
      // 44b7: ldc_w -0.91
      // 44ba: ldc_w 19.127
      // 44bd: ldc_w 1.161
      // 44c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44c9: aastore
      // 44ca: dup
      // 44cb: bipush 6
      // 44cd: new net/minecraft/client/animation/Keyframe
      // 44d0: dup
      // 44d1: ldc 0.29
      // 44d3: ldc_w -0.943
      // 44d6: ldc_w 16.972
      // 44d9: ldc_w 1.11
      // 44dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44e5: aastore
      // 44e6: dup
      // 44e7: bipush 7
      // 44e9: new net/minecraft/client/animation/Keyframe
      // 44ec: dup
      // 44ed: ldc 0.33
      // 44ef: ldc_w -0.936
      // 44f2: ldc_w 11.457
      // 44f5: ldc_w 1.029
      // 44f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4501: aastore
      // 4502: dup
      // 4503: bipush 8
      // 4505: new net/minecraft/client/animation/Keyframe
      // 4508: dup
      // 4509: ldc 0.38
      // 450b: ldc_w -0.685
      // 450e: ldc_w 2.953
      // 4511: ldc_w 0.724
      // 4514: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4517: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 451a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 451d: aastore
      // 451e: dup
      // 451f: bipush 9
      // 4521: new net/minecraft/client/animation/Keyframe
      // 4524: dup
      // 4525: ldc 0.42
      // 4527: ldc_w -1.516
      // 452a: ldc_w -7.218
      // 452d: ldc_w 1.527
      // 4530: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4533: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4536: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4539: aastore
      // 453a: dup
      // 453b: bipush 10
      // 453d: new net/minecraft/client/animation/Keyframe
      // 4540: dup
      // 4541: ldc 0.46
      // 4543: ldc_w -1.311
      // 4546: ldc_w -13.79
      // 4549: ldc_w 1.315
      // 454c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 454f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4552: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4555: aastore
      // 4556: dup
      // 4557: bipush 11
      // 4559: new net/minecraft/client/animation/Keyframe
      // 455c: dup
      // 455d: ldc 0.5
      // 455f: ldc_w -1.384
      // 4562: ldc_w -10.208
      // 4565: ldc_w 1.388
      // 4568: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 456b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 456e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4571: aastore
      // 4572: dup
      // 4573: bipush 12
      // 4575: new net/minecraft/client/animation/Keyframe
      // 4578: dup
      // 4579: ldc 0.54
      // 457b: ldc_w -1.234
      // 457e: ldc_w -23.201
      // 4581: ldc_w 1.241
      // 4584: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4587: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 458a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 458d: aastore
      // 458e: dup
      // 458f: bipush 13
      // 4591: new net/minecraft/client/animation/Keyframe
      // 4594: dup
      // 4595: ldc 0.58
      // 4597: ldc_w -1.233
      // 459a: ldc_w -23.437
      // 459d: ldc_w 1.24
      // 45a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45a9: aastore
      // 45aa: dup
      // 45ab: bipush 14
      // 45ad: new net/minecraft/client/animation/Keyframe
      // 45b0: dup
      // 45b1: ldc 0.62
      // 45b3: ldc_w -1.235
      // 45b6: ldc_w -23.011
      // 45b9: ldc_w 1.242
      // 45bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45c5: aastore
      // 45c6: dup
      // 45c7: bipush 15
      // 45c9: new net/minecraft/client/animation/Keyframe
      // 45cc: dup
      // 45cd: ldc 0.67
      // 45cf: ldc_w -1.232
      // 45d2: ldc_w -23.706
      // 45d5: ldc_w 1.239
      // 45d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45e1: aastore
      // 45e2: dup
      // 45e3: bipush 16
      // 45e5: new net/minecraft/client/animation/Keyframe
      // 45e8: dup
      // 45e9: ldc 0.71
      // 45eb: ldc_w -1.223
      // 45ee: ldc_w -25.878
      // 45f1: ldc_w 1.231
      // 45f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45fd: aastore
      // 45fe: dup
      // 45ff: bipush 17
      // 4601: new net/minecraft/client/animation/Keyframe
      // 4604: dup
      // 4605: ldc 0.75
      // 4607: ldc_w -1.222
      // 460a: ldc_w -26.13
      // 460d: ldc_w 1.23
      // 4610: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4613: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4616: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4619: aastore
      // 461a: dup
      // 461b: bipush 18
      // 461d: new net/minecraft/client/animation/Keyframe
      // 4620: dup
      // 4621: ldc 0.79
      // 4623: ldc_w -1.247
      // 4626: ldc_w -20.768
      // 4629: ldc_w 1.253
      // 462c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 462f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4632: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4635: aastore
      // 4636: dup
      // 4637: bipush 19
      // 4639: new net/minecraft/client/animation/Keyframe
      // 463c: dup
      // 463d: ldc 0.83
      // 463f: ldc_w -1.276
      // 4642: ldc_w -16.766
      // 4645: ldc_w 1.282
      // 4648: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 464b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 464e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4651: aastore
      // 4652: dup
      // 4653: bipush 20
      // 4655: new net/minecraft/client/animation/Keyframe
      // 4658: dup
      // 4659: ldc 0.88
      // 465b: ldc_w -1.328
      // 465e: ldc_w -12.721
      // 4661: ldc_w 1.332
      // 4664: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4667: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 466a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 466d: aastore
      // 466e: dup
      // 466f: bipush 21
      // 4671: new net/minecraft/client/animation/Keyframe
      // 4674: dup
      // 4675: ldc 0.92
      // 4677: ldc -1.505
      // 4679: ldc_w -7.423
      // 467c: ldc_w 1.508
      // 467f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4682: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4685: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4688: aastore
      // 4689: dup
      // 468a: bipush 22
      // 468c: new net/minecraft/client/animation/Keyframe
      // 468f: dup
      // 4690: ldc 0.96
      // 4692: ldc_w -1.303
      // 4695: ldc_w -14.37
      // 4698: ldc_w 1.308
      // 469b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 469e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46a4: aastore
      // 46a5: dup
      // 46a6: bipush 23
      // 46a8: new net/minecraft/client/animation/Keyframe
      // 46ab: dup
      // 46ac: fconst_1
      // 46ad: ldc_w -1.225
      // 46b0: ldc_w -24.266
      // 46b3: ldc_w 1.241
      // 46b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46bf: aastore
      // 46c0: dup
      // 46c1: bipush 24
      // 46c3: new net/minecraft/client/animation/Keyframe
      // 46c6: dup
      // 46c7: ldc 1.04
      // 46c9: ldc_w -1.684
      // 46cc: ldc_w -5.124
      // 46cf: ldc_w 2.001
      // 46d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46db: aastore
      // 46dc: dup
      // 46dd: bipush 25
      // 46df: new net/minecraft/client/animation/Keyframe
      // 46e2: dup
      // 46e3: ldc 1.08
      // 46e5: ldc_w 1.499
      // 46e8: ldc_w 32.363
      // 46eb: ldc_w 3.419
      // 46ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46f7: aastore
      // 46f8: dup
      // 46f9: bipush 26
      // 46fb: new net/minecraft/client/animation/Keyframe
      // 46fe: dup
      // 46ff: ldc 1.12
      // 4701: ldc_w 6.076
      // 4704: ldc_w 35.767
      // 4707: ldc_w 7.629
      // 470a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 470d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4710: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4713: aastore
      // 4714: dup
      // 4715: bipush 27
      // 4717: new net/minecraft/client/animation/Keyframe
      // 471a: dup
      // 471b: ldc 1.17
      // 471d: ldc_w 10.006
      // 4720: ldc_w 31.379
      // 4723: ldc_w 11.074
      // 4726: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4729: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 472c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 472f: aastore
      // 4730: dup
      // 4731: bipush 28
      // 4733: new net/minecraft/client/animation/Keyframe
      // 4736: dup
      // 4737: ldc 1.21
      // 4739: ldc_w 9.261
      // 473c: ldc_w 25.031
      // 473f: ldc_w 10.134
      // 4742: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4745: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4748: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 474b: aastore
      // 474c: dup
      // 474d: bipush 29
      // 474f: new net/minecraft/client/animation/Keyframe
      // 4752: dup
      // 4753: ldc 1.25
      // 4755: ldc_w 5.354
      // 4758: ldc_w 22.434
      // 475b: ldc_w 6.605
      // 475e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4761: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4764: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4767: aastore
      // 4768: dup
      // 4769: bipush 30
      // 476b: new net/minecraft/client/animation/Keyframe
      // 476e: dup
      // 476f: ldc 1.29
      // 4771: ldc_w 1.743
      // 4774: ldc_w 23.816
      // 4777: ldc_w 3.511
      // 477a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 477d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4780: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4783: aastore
      // 4784: dup
      // 4785: bipush 31
      // 4787: new net/minecraft/client/animation/Keyframe
      // 478a: dup
      // 478b: ldc 1.33
      // 478d: ldc_w -1.48
      // 4790: ldc_w 28.288
      // 4793: ldc_w 0.718
      // 4796: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4799: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 479c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 479f: aastore
      // 47a0: dup
      // 47a1: bipush 32
      // 47a3: new net/minecraft/client/animation/Keyframe
      // 47a6: dup
      // 47a7: ldc 1.38
      // 47a9: ldc_w -8.308
      // 47ac: ldc_w 36.914
      // 47af: ldc_w -5.51
      // 47b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47bb: aastore
      // 47bc: dup
      // 47bd: bipush 33
      // 47bf: new net/minecraft/client/animation/Keyframe
      // 47c2: dup
      // 47c3: ldc 1.42
      // 47c5: ldc_w -17.692
      // 47c8: ldc_w 44.484
      // 47cb: ldc_w -14.331
      // 47ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47d7: aastore
      // 47d8: dup
      // 47d9: bipush 34
      // 47db: new net/minecraft/client/animation/Keyframe
      // 47de: dup
      // 47df: ldc 1.46
      // 47e1: ldc_w -29.061
      // 47e4: ldc_w 49.401
      // 47e7: ldc_w -25.139
      // 47ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47f3: aastore
      // 47f4: dup
      // 47f5: bipush 35
      // 47f7: new net/minecraft/client/animation/Keyframe
      // 47fa: dup
      // 47fb: ldc 1.5
      // 47fd: ldc_w -38.924
      // 4800: ldc_w 53.871
      // 4803: ldc_w -34.662
      // 4806: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4809: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 480c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 480f: aastore
      // 4810: dup
      // 4811: bipush 36
      // 4813: new net/minecraft/client/animation/Keyframe
      // 4816: dup
      // 4817: ldc 1.54
      // 4819: ldc_w -44.191
      // 481c: ldc_w 59.15
      // 481f: ldc_w -39.913
      // 4822: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4825: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4828: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 482b: aastore
      // 482c: dup
      // 482d: bipush 37
      // 482f: new net/minecraft/client/animation/Keyframe
      // 4832: dup
      // 4833: ldc 1.58
      // 4835: ldc_w -46.634
      // 4838: ldc_w 59.902
      // 483b: ldc_w -42.291
      // 483e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4841: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4844: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4847: aastore
      // 4848: dup
      // 4849: bipush 38
      // 484b: new net/minecraft/client/animation/Keyframe
      // 484e: dup
      // 484f: ldc 1.62
      // 4851: ldc_w -46.595
      // 4854: ldc_w 57.255
      // 4857: ldc_w -42.144
      // 485a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 485d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4860: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4863: aastore
      // 4864: dup
      // 4865: bipush 39
      // 4867: new net/minecraft/client/animation/Keyframe
      // 486a: dup
      // 486b: ldc 1.67
      // 486d: ldc_w -43.416
      // 4870: ldc_w 54.44
      // 4873: ldc_w -38.974
      // 4876: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4879: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 487c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 487f: aastore
      // 4880: dup
      // 4881: bipush 40
      // 4883: new net/minecraft/client/animation/Keyframe
      // 4886: dup
      // 4887: ldc 1.71
      // 4889: ldc_w -36.974
      // 488c: ldc_w 53.405
      // 488f: ldc_w -32.786
      // 4892: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4895: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4898: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 489b: aastore
      // 489c: dup
      // 489d: bipush 41
      // 489f: new net/minecraft/client/animation/Keyframe
      // 48a2: dup
      // 48a3: ldc 1.75
      // 48a5: ldc_w -28.119
      // 48a8: ldc_w 53.827
      // 48ab: ldc_w -24.393
      // 48ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48b7: aastore
      // 48b8: dup
      // 48b9: bipush 42
      // 48bb: new net/minecraft/client/animation/Keyframe
      // 48be: dup
      // 48bf: ldc 1.79
      // 48c1: ldc_w -18.717
      // 48c4: ldc_w 54.06
      // 48c7: ldc_w -15.502
      // 48ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48d3: aastore
      // 48d4: dup
      // 48d5: bipush 43
      // 48d7: new net/minecraft/client/animation/Keyframe
      // 48da: dup
      // 48db: ldc 1.83
      // 48dd: ldc_w -10.638
      // 48e0: ldc_w 52.819
      // 48e3: ldc_w -7.863
      // 48e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48ef: aastore
      // 48f0: dup
      // 48f1: bipush 44
      // 48f3: new net/minecraft/client/animation/Keyframe
      // 48f6: dup
      // 48f7: ldc 1.88
      // 48f9: ldc_w -4.815
      // 48fc: ldc_w 41.772
      // 48ff: ldc_w -2.339
      // 4902: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4905: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4908: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 490b: aastore
      // 490c: dup
      // 490d: bipush 45
      // 490f: new net/minecraft/client/animation/Keyframe
      // 4912: dup
      // 4913: ldc 1.92
      // 4915: ldc_w -1.426
      // 4918: ldc_w 28.897
      // 491b: ldc_w 0.767
      // 491e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4921: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4924: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4927: aastore
      // 4928: dup
      // 4929: bipush 46
      // 492b: new net/minecraft/client/animation/Keyframe
      // 492e: dup
      // 492f: ldc 1.96
      // 4931: ldc_w 0.081
      // 4934: ldc_w 21.716
      // 4937: ldc_w 2.036
      // 493a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 493d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4940: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4943: aastore
      // 4944: dup
      // 4945: bipush 47
      // 4947: new net/minecraft/client/animation/Keyframe
      // 494a: dup
      // 494b: fconst_2
      // 494c: ldc_w 0.168
      // 494f: ldc_w 17.864
      // 4952: ldc_w 2.048
      // 4955: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4958: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 495b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 495e: aastore
      // 495f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4962: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4965: ldc_w "CTR_upperarm_right.001"
      // 4968: new net/minecraft/client/animation/AnimationChannel
      // 496b: dup
      // 496c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 496f: bipush 39
      // 4971: anewarray 27
      // 4974: dup
      // 4975: bipush 0
      // 4976: new net/minecraft/client/animation/Keyframe
      // 4979: dup
      // 497a: fconst_0
      // 497b: dconst_1
      // 497c: dconst_1
      // 497d: ldc2_w 1.6740000247955322
      // 4980: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4983: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4986: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4989: aastore
      // 498a: dup
      // 498b: bipush 1
      // 498c: new net/minecraft/client/animation/Keyframe
      // 498f: dup
      // 4990: ldc 0.04
      // 4992: dconst_1
      // 4993: dconst_1
      // 4994: ldc2_w 1.7120000123977661
      // 4997: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 499a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 499d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49a0: aastore
      // 49a1: dup
      // 49a2: bipush 2
      // 49a3: new net/minecraft/client/animation/Keyframe
      // 49a6: dup
      // 49a7: ldc 0.08
      // 49a9: dconst_1
      // 49aa: dconst_1
      // 49ab: ldc2_w 1.7079999446868896
      // 49ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 49b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49b7: aastore
      // 49b8: dup
      // 49b9: bipush 3
      // 49ba: new net/minecraft/client/animation/Keyframe
      // 49bd: dup
      // 49be: ldc 0.17
      // 49c0: dconst_1
      // 49c1: dconst_1
      // 49c2: ldc2_w 1.6360000371932983
      // 49c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 49c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49ce: aastore
      // 49cf: dup
      // 49d0: bipush 4
      // 49d1: new net/minecraft/client/animation/Keyframe
      // 49d4: dup
      // 49d5: ldc 0.33
      // 49d7: dconst_1
      // 49d8: dconst_1
      // 49d9: ldc2_w 1.4279999732971191
      // 49dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 49df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49e5: aastore
      // 49e6: dup
      // 49e7: bipush 5
      // 49e8: new net/minecraft/client/animation/Keyframe
      // 49eb: dup
      // 49ec: ldc 0.38
      // 49ee: dconst_1
      // 49ef: dconst_1
      // 49f0: ldc2_w 1.3910000324249268
      // 49f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 49f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49fc: aastore
      // 49fd: dup
      // 49fe: bipush 6
      // 4a00: new net/minecraft/client/animation/Keyframe
      // 4a03: dup
      // 4a04: ldc 0.42
      // 4a06: dconst_1
      // 4a07: dconst_1
      // 4a08: ldc2_w 1.3880000114440918
      // 4a0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a14: aastore
      // 4a15: dup
      // 4a16: bipush 7
      // 4a18: new net/minecraft/client/animation/Keyframe
      // 4a1b: dup
      // 4a1c: ldc 0.46
      // 4a1e: dconst_1
      // 4a1f: dconst_1
      // 4a20: ldc2_w 1.2940000295639038
      // 4a23: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a2c: aastore
      // 4a2d: dup
      // 4a2e: bipush 8
      // 4a30: new net/minecraft/client/animation/Keyframe
      // 4a33: dup
      // 4a34: ldc 0.5
      // 4a36: dconst_1
      // 4a37: dconst_1
      // 4a38: ldc2_w 1.1679999828338623
      // 4a3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a44: aastore
      // 4a45: dup
      // 4a46: bipush 9
      // 4a48: new net/minecraft/client/animation/Keyframe
      // 4a4b: dup
      // 4a4c: ldc 0.54
      // 4a4e: dconst_1
      // 4a4f: dconst_1
      // 4a50: ldc2_w 1.3660000562667847
      // 4a53: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a5c: aastore
      // 4a5d: dup
      // 4a5e: bipush 10
      // 4a60: new net/minecraft/client/animation/Keyframe
      // 4a63: dup
      // 4a64: ldc 0.62
      // 4a66: dconst_1
      // 4a67: dconst_1
      // 4a68: ldc2_w 1.3550000190734863
      // 4a6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a74: aastore
      // 4a75: dup
      // 4a76: bipush 11
      // 4a78: new net/minecraft/client/animation/Keyframe
      // 4a7b: dup
      // 4a7c: ldc 0.67
      // 4a7e: dconst_1
      // 4a7f: dconst_1
      // 4a80: ldc2_w 1.371999979019165
      // 4a83: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a8c: aastore
      // 4a8d: dup
      // 4a8e: bipush 12
      // 4a90: new net/minecraft/client/animation/Keyframe
      // 4a93: dup
      // 4a94: ldc 0.71
      // 4a96: dconst_1
      // 4a97: dconst_1
      // 4a98: ldc2_w 1.4140000343322754
      // 4a9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4aa1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4aa4: aastore
      // 4aa5: dup
      // 4aa6: bipush 13
      // 4aa8: new net/minecraft/client/animation/Keyframe
      // 4aab: dup
      // 4aac: ldc 0.75
      // 4aae: dconst_1
      // 4aaf: dconst_1
      // 4ab0: ldc2_w 1.4420000314712524
      // 4ab3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4ab6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ab9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4abc: aastore
      // 4abd: dup
      // 4abe: bipush 14
      // 4ac0: new net/minecraft/client/animation/Keyframe
      // 4ac3: dup
      // 4ac4: ldc 0.79
      // 4ac6: dconst_1
      // 4ac7: dconst_1
      // 4ac8: ldc2_w 1.4040000438690186
      // 4acb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4ace: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ad1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ad4: aastore
      // 4ad5: dup
      // 4ad6: bipush 15
      // 4ad8: new net/minecraft/client/animation/Keyframe
      // 4adb: dup
      // 4adc: ldc 0.88
      // 4ade: dconst_1
      // 4adf: dconst_1
      // 4ae0: ldc2_w 1.350000023841858
      // 4ae3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4ae6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ae9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4aec: aastore
      // 4aed: dup
      // 4aee: bipush 16
      // 4af0: new net/minecraft/client/animation/Keyframe
      // 4af3: dup
      // 4af4: ldc 0.92
      // 4af6: dconst_1
      // 4af7: dconst_1
      // 4af8: ldc2_w 1.3179999589920044
      // 4afb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4afe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b04: aastore
      // 4b05: dup
      // 4b06: bipush 17
      // 4b08: new net/minecraft/client/animation/Keyframe
      // 4b0b: dup
      // 4b0c: ldc 0.96
      // 4b0e: dconst_1
      // 4b0f: dconst_1
      // 4b10: ldc2_w 1.2740000486373901
      // 4b13: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4b16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b1c: aastore
      // 4b1d: dup
      // 4b1e: bipush 18
      // 4b20: new net/minecraft/client/animation/Keyframe
      // 4b23: dup
      // 4b24: fconst_1
      // 4b25: dconst_1
      // 4b26: dconst_1
      // 4b27: ldc2_w 1.2890000343322754
      // 4b2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4b2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b33: aastore
      // 4b34: dup
      // 4b35: bipush 19
      // 4b37: new net/minecraft/client/animation/Keyframe
      // 4b3a: dup
      // 4b3b: ldc 1.04
      // 4b3d: dconst_1
      // 4b3e: dconst_1
      // 4b3f: ldc2_w 1.3860000371932983
      // 4b42: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4b45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b4b: aastore
      // 4b4c: dup
      // 4b4d: bipush 20
      // 4b4f: new net/minecraft/client/animation/Keyframe
      // 4b52: dup
      // 4b53: ldc 1.12
      // 4b55: dconst_1
      // 4b56: dconst_1
      // 4b57: ldc2_w 1.4520000219345093
      // 4b5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4b5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b63: aastore
      // 4b64: dup
      // 4b65: bipush 21
      // 4b67: new net/minecraft/client/animation/Keyframe
      // 4b6a: dup
      // 4b6b: ldc 1.17
      // 4b6d: dconst_1
      // 4b6e: dconst_1
      // 4b6f: ldc2_w 1.4850000143051147
      // 4b72: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4b75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b7b: aastore
      // 4b7c: dup
      // 4b7d: bipush 22
      // 4b7f: new net/minecraft/client/animation/Keyframe
      // 4b82: dup
      // 4b83: ldc 1.21
      // 4b85: dconst_1
      // 4b86: dconst_1
      // 4b87: ldc2_w 1.4780000448226929
      // 4b8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4b8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b93: aastore
      // 4b94: dup
      // 4b95: bipush 23
      // 4b97: new net/minecraft/client/animation/Keyframe
      // 4b9a: dup
      // 4b9b: ldc 1.25
      // 4b9d: dconst_1
      // 4b9e: dconst_1
      // 4b9f: ldc2_w 1.4249999523162842
      // 4ba2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4ba5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ba8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bab: aastore
      // 4bac: dup
      // 4bad: bipush 24
      // 4baf: new net/minecraft/client/animation/Keyframe
      // 4bb2: dup
      // 4bb3: ldc 1.29
      // 4bb5: dconst_1
      // 4bb6: dconst_1
      // 4bb7: ldc2_w 1.343000054359436
      // 4bba: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4bbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bc3: aastore
      // 4bc4: dup
      // 4bc5: bipush 25
      // 4bc7: new net/minecraft/client/animation/Keyframe
      // 4bca: dup
      // 4bcb: ldc 1.38
      // 4bcd: dconst_1
      // 4bce: dconst_1
      // 4bcf: ldc2_w 1.11899995803833
      // 4bd2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4bd5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bd8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bdb: aastore
      // 4bdc: dup
      // 4bdd: bipush 26
      // 4bdf: new net/minecraft/client/animation/Keyframe
      // 4be2: dup
      // 4be3: ldc 1.42
      // 4be5: dconst_1
      // 4be6: dconst_1
      // 4be7: ldc2_w 1.0119999647140503
      // 4bea: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4bed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bf0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bf3: aastore
      // 4bf4: dup
      // 4bf5: bipush 27
      // 4bf7: new net/minecraft/client/animation/Keyframe
      // 4bfa: dup
      // 4bfb: ldc 1.46
      // 4bfd: dconst_1
      // 4bfe: dconst_1
      // 4bff: ldc2_w 0.9509999752044678
      // 4c02: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4c05: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c08: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c0b: aastore
      // 4c0c: dup
      // 4c0d: bipush 28
      // 4c0f: new net/minecraft/client/animation/Keyframe
      // 4c12: dup
      // 4c13: ldc 1.5
      // 4c15: dconst_1
      // 4c16: dconst_1
      // 4c17: ldc2_w 0.9490000009536743
      // 4c1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4c1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c23: aastore
      // 4c24: dup
      // 4c25: bipush 29
      // 4c27: new net/minecraft/client/animation/Keyframe
      // 4c2a: dup
      // 4c2b: ldc 1.54
      // 4c2d: dconst_1
      // 4c2e: dconst_1
      // 4c2f: ldc2_w 0.9629999995231628
      // 4c32: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4c35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c3b: aastore
      // 4c3c: dup
      // 4c3d: bipush 30
      // 4c3f: new net/minecraft/client/animation/Keyframe
      // 4c42: dup
      // 4c43: ldc 1.58
      // 4c45: dconst_1
      // 4c46: dconst_1
      // 4c47: ldc2_w 0.9539999961853027
      // 4c4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4c4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c53: aastore
      // 4c54: dup
      // 4c55: bipush 31
      // 4c57: new net/minecraft/client/animation/Keyframe
      // 4c5a: dup
      // 4c5b: ldc 1.71
      // 4c5d: dconst_1
      // 4c5e: dconst_1
      // 4c5f: ldc2_w 0.8320000171661377
      // 4c62: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4c65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c6b: aastore
      // 4c6c: dup
      // 4c6d: bipush 32
      // 4c6f: new net/minecraft/client/animation/Keyframe
      // 4c72: dup
      // 4c73: ldc 1.75
      // 4c75: dconst_1
      // 4c76: dconst_1
      // 4c77: ldc2_w 0.8370000123977661
      // 4c7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4c7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c83: aastore
      // 4c84: dup
      // 4c85: bipush 33
      // 4c87: new net/minecraft/client/animation/Keyframe
      // 4c8a: dup
      // 4c8b: ldc 1.79
      // 4c8d: dconst_1
      // 4c8e: dconst_1
      // 4c8f: ldc2_w 0.8939999938011169
      // 4c92: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4c95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c9b: aastore
      // 4c9c: dup
      // 4c9d: bipush 34
      // 4c9f: new net/minecraft/client/animation/Keyframe
      // 4ca2: dup
      // 4ca3: ldc 1.83
      // 4ca5: dconst_1
      // 4ca6: dconst_1
      // 4ca7: ldc2_w 0.9950000047683716
      // 4caa: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4cad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cb3: aastore
      // 4cb4: dup
      // 4cb5: bipush 35
      // 4cb7: new net/minecraft/client/animation/Keyframe
      // 4cba: dup
      // 4cbb: ldc 1.88
      // 4cbd: dconst_1
      // 4cbe: dconst_1
      // 4cbf: ldc2_w 1.187000036239624
      // 4cc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4cc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ccb: aastore
      // 4ccc: dup
      // 4ccd: bipush 36
      // 4ccf: new net/minecraft/client/animation/Keyframe
      // 4cd2: dup
      // 4cd3: ldc 1.92
      // 4cd5: dconst_1
      // 4cd6: dconst_1
      // 4cd7: ldc2_w 1.4290000200271606
      // 4cda: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4cdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ce0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ce3: aastore
      // 4ce4: dup
      // 4ce5: bipush 37
      // 4ce7: new net/minecraft/client/animation/Keyframe
      // 4cea: dup
      // 4ceb: ldc 1.96
      // 4ced: dconst_1
      // 4cee: dconst_1
      // 4cef: ldc2_w 1.6019999980926514
      // 4cf2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4cf5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cf8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cfb: aastore
      // 4cfc: dup
      // 4cfd: bipush 38
      // 4cff: new net/minecraft/client/animation/Keyframe
      // 4d02: dup
      // 4d03: fconst_2
      // 4d04: dconst_1
      // 4d05: dconst_1
      // 4d06: ldc2_w 1.6740000247955322
      // 4d09: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4d0c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d0f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d12: aastore
      // 4d13: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4d16: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4d19: ldc_w "CTR_leg_left"
      // 4d1c: new net/minecraft/client/animation/AnimationChannel
      // 4d1f: dup
      // 4d20: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4d23: bipush 47
      // 4d25: anewarray 27
      // 4d28: dup
      // 4d29: bipush 0
      // 4d2a: new net/minecraft/client/animation/Keyframe
      // 4d2d: dup
      // 4d2e: fconst_0
      // 4d2f: ldc_w -177.066
      // 4d32: ldc_w 52.262
      // 4d35: ldc_w 182.934
      // 4d38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d41: aastore
      // 4d42: dup
      // 4d43: bipush 1
      // 4d44: new net/minecraft/client/animation/Keyframe
      // 4d47: dup
      // 4d48: ldc 0.04
      // 4d4a: ldc_w -177.572
      // 4d4d: ldc_w 48.748
      // 4d50: ldc_w 182.428
      // 4d53: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d5c: aastore
      // 4d5d: dup
      // 4d5e: bipush 2
      // 4d5f: new net/minecraft/client/animation/Keyframe
      // 4d62: dup
      // 4d63: ldc 0.08
      // 4d65: ldc_w -178.273
      // 4d68: ldc_w 45.168
      // 4d6b: ldc_w 181.727
      // 4d6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d77: aastore
      // 4d78: dup
      // 4d79: bipush 3
      // 4d7a: new net/minecraft/client/animation/Keyframe
      // 4d7d: dup
      // 4d7e: ldc 0.12
      // 4d80: ldc_w -179.039
      // 4d83: ldc_w 42.061
      // 4d86: ldc_w 180.961
      // 4d89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d92: aastore
      // 4d93: dup
      // 4d94: bipush 4
      // 4d95: new net/minecraft/client/animation/Keyframe
      // 4d98: dup
      // 4d99: ldc 0.17
      // 4d9b: ldc_w -179.844
      // 4d9e: ldc_w 40.097
      // 4da1: ldc_w 180.156
      // 4da4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4da7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4daa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dad: aastore
      // 4dae: dup
      // 4daf: bipush 5
      // 4db0: new net/minecraft/client/animation/Keyframe
      // 4db3: dup
      // 4db4: ldc 0.21
      // 4db6: ldc_w -180.726
      // 4db9: ldc_w 40.148
      // 4dbc: ldc_w 179.274
      // 4dbf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dc2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dc5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dc8: aastore
      // 4dc9: dup
      // 4dca: bipush 6
      // 4dcc: new net/minecraft/client/animation/Keyframe
      // 4dcf: dup
      // 4dd0: ldc 0.25
      // 4dd2: ldc_w -181.681
      // 4dd5: ldc_w 43.451
      // 4dd8: ldc_w 178.319
      // 4ddb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4de1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4de4: aastore
      // 4de5: dup
      // 4de6: bipush 7
      // 4de8: new net/minecraft/client/animation/Keyframe
      // 4deb: dup
      // 4dec: ldc 0.29
      // 4dee: ldc_w -180.69
      // 4df1: ldc_w 41.705
      // 4df4: ldc_w 179.31
      // 4df7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dfa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dfd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e00: aastore
      // 4e01: dup
      // 4e02: bipush 8
      // 4e04: new net/minecraft/client/animation/Keyframe
      // 4e07: dup
      // 4e08: ldc 0.33
      // 4e0a: ldc_w -178.627
      // 4e0d: ldc_w 35.312
      // 4e10: ldc_w 181.373
      // 4e13: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e1c: aastore
      // 4e1d: dup
      // 4e1e: bipush 9
      // 4e20: new net/minecraft/client/animation/Keyframe
      // 4e23: dup
      // 4e24: ldc 0.38
      // 4e26: ldc_w -178.739
      // 4e29: ldc_w 36.883
      // 4e2c: ldc_w 181.261
      // 4e2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e38: aastore
      // 4e39: dup
      // 4e3a: bipush 10
      // 4e3c: new net/minecraft/client/animation/Keyframe
      // 4e3f: dup
      // 4e40: ldc 0.42
      // 4e42: ldc_w -180.241
      // 4e45: ldc_w 44.777
      // 4e48: ldc_w 179.759
      // 4e4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e54: aastore
      // 4e55: dup
      // 4e56: bipush 11
      // 4e58: new net/minecraft/client/animation/Keyframe
      // 4e5b: dup
      // 4e5c: ldc 0.46
      // 4e5e: ldc_w -180.889
      // 4e61: ldc_w 48.712
      // 4e64: ldc_w 179.111
      // 4e67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e70: aastore
      // 4e71: dup
      // 4e72: bipush 12
      // 4e74: new net/minecraft/client/animation/Keyframe
      // 4e77: dup
      // 4e78: ldc 0.5
      // 4e7a: ldc_w -180.812
      // 4e7d: ldc_w 50.291
      // 4e80: ldc_w 179.188
      // 4e83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e8c: aastore
      // 4e8d: dup
      // 4e8e: bipush 13
      // 4e90: new net/minecraft/client/animation/Keyframe
      // 4e93: dup
      // 4e94: ldc 0.54
      // 4e96: ldc_w -180.273
      // 4e99: ldc_w 51.361
      // 4e9c: ldc_w 179.727
      // 4e9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ea2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ea5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ea8: aastore
      // 4ea9: dup
      // 4eaa: bipush 14
      // 4eac: new net/minecraft/client/animation/Keyframe
      // 4eaf: dup
      // 4eb0: ldc 0.58
      // 4eb2: ldc_w -179.395
      // 4eb5: ldc_w 52.014
      // 4eb8: ldc_w 180.605
      // 4ebb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ebe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ec1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ec4: aastore
      // 4ec5: dup
      // 4ec6: bipush 15
      // 4ec8: new net/minecraft/client/animation/Keyframe
      // 4ecb: dup
      // 4ecc: ldc 0.62
      // 4ece: ldc_w -178.286
      // 4ed1: ldc_w 52.388
      // 4ed4: ldc_w 181.714
      // 4ed7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4eda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4edd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ee0: aastore
      // 4ee1: dup
      // 4ee2: bipush 16
      // 4ee4: new net/minecraft/client/animation/Keyframe
      // 4ee7: dup
      // 4ee8: ldc 0.67
      // 4eea: ldc_w -177.044
      // 4eed: ldc_w 52.63
      // 4ef0: ldc_w 182.956
      // 4ef3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ef6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ef9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4efc: aastore
      // 4efd: dup
      // 4efe: bipush 17
      // 4f00: new net/minecraft/client/animation/Keyframe
      // 4f03: dup
      // 4f04: ldc 0.75
      // 4f06: ldc_w -174.508
      // 4f09: ldc_w 53.137
      // 4f0c: ldc_w 185.492
      // 4f0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f18: aastore
      // 4f19: dup
      // 4f1a: bipush 18
      // 4f1c: new net/minecraft/client/animation/Keyframe
      // 4f1f: dup
      // 4f20: ldc 0.79
      // 4f22: ldc_w -173.383
      // 4f25: ldc_w 53.386
      // 4f28: ldc_w 186.617
      // 4f2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f34: aastore
      // 4f35: dup
      // 4f36: bipush 19
      // 4f38: new net/minecraft/client/animation/Keyframe
      // 4f3b: dup
      // 4f3c: ldc 0.83
      // 4f3e: ldc_w -172.824
      // 4f41: ldc_w 53.422
      // 4f44: ldc_w 187.176
      // 4f47: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f50: aastore
      // 4f51: dup
      // 4f52: bipush 20
      // 4f54: new net/minecraft/client/animation/Keyframe
      // 4f57: dup
      // 4f58: ldc 0.92
      // 4f5a: ldc_w -172.09
      // 4f5d: ldc_w 53.415
      // 4f60: ldc_w 187.91
      // 4f63: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f66: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f69: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f6c: aastore
      // 4f6d: dup
      // 4f6e: bipush 21
      // 4f70: new net/minecraft/client/animation/Keyframe
      // 4f73: dup
      // 4f74: ldc 0.96
      // 4f76: ldc_w -171.673
      // 4f79: ldc_w 53.143
      // 4f7c: ldc_w 188.327
      // 4f7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f88: aastore
      // 4f89: dup
      // 4f8a: bipush 22
      // 4f8c: new net/minecraft/client/animation/Keyframe
      // 4f8f: dup
      // 4f90: fconst_1
      // 4f91: ldc_w -172.52
      // 4f94: ldc_w 54.2
      // 4f97: ldc_w 187.48
      // 4f9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fa0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fa3: aastore
      // 4fa4: dup
      // 4fa5: bipush 23
      // 4fa7: new net/minecraft/client/animation/Keyframe
      // 4faa: dup
      // 4fab: ldc 1.04
      // 4fad: ldc_w -176.657
      // 4fb0: ldc_w 46.148
      // 4fb3: ldc_w 183.343
      // 4fb6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fb9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fbc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fbf: aastore
      // 4fc0: dup
      // 4fc1: bipush 24
      // 4fc3: new net/minecraft/client/animation/Keyframe
      // 4fc6: dup
      // 4fc7: ldc 1.08
      // 4fc9: ldc_w -178.187
      // 4fcc: ldc_w 34.917
      // 4fcf: ldc_w 181.813
      // 4fd2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fd5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fd8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fdb: aastore
      // 4fdc: dup
      // 4fdd: bipush 25
      // 4fdf: new net/minecraft/client/animation/Keyframe
      // 4fe2: dup
      // 4fe3: ldc 1.12
      // 4fe5: ldc_w -176.353
      // 4fe8: ldc_w 29.486
      // 4feb: ldc_w 183.647
      // 4fee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ff1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ff4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ff7: aastore
      // 4ff8: dup
      // 4ff9: bipush 26
      // 4ffb: new net/minecraft/client/animation/Keyframe
      // 4ffe: dup
      // 4fff: ldc 1.17
      // 5001: ldc_w -174.256
      // 5004: ldc_w 27.564
      // 5007: ldc_w 185.743
      // 500a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 500d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5010: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5013: aastore
      // 5014: dup
      // 5015: bipush 27
      // 5017: new net/minecraft/client/animation/Keyframe
      // 501a: dup
      // 501b: ldc 1.21
      // 501d: ldc_w -172.211
      // 5020: ldc_w 26.252
      // 5023: ldc_w 187.788
      // 5026: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5029: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 502c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 502f: aastore
      // 5030: dup
      // 5031: bipush 28
      // 5033: new net/minecraft/client/animation/Keyframe
      // 5036: dup
      // 5037: ldc 1.25
      // 5039: ldc_w -170.907
      // 503c: ldc_w 28.831
      // 503f: ldc_w 189.093
      // 5042: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5045: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5048: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 504b: aastore
      // 504c: dup
      // 504d: bipush 29
      // 504f: new net/minecraft/client/animation/Keyframe
      // 5052: dup
      // 5053: ldc 1.29
      // 5055: ldc_w -171.517
      // 5058: ldc_w 39.519
      // 505b: ldc_w 188.483
      // 505e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5061: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5064: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5067: aastore
      // 5068: dup
      // 5069: bipush 30
      // 506b: new net/minecraft/client/animation/Keyframe
      // 506e: dup
      // 506f: ldc 1.33
      // 5071: ldc_w -174.515
      // 5074: ldc_w 54.233
      // 5077: ldc_w 185.485
      // 507a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 507d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5080: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5083: aastore
      // 5084: dup
      // 5085: bipush 31
      // 5087: new net/minecraft/client/animation/Keyframe
      // 508a: dup
      // 508b: ldc 1.38
      // 508d: ldc_w -176.931
      // 5090: ldc_w 56.891
      // 5093: ldc_w 183.069
      // 5096: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5099: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 509c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 509f: aastore
      // 50a0: dup
      // 50a1: bipush 32
      // 50a3: new net/minecraft/client/animation/Keyframe
      // 50a6: dup
      // 50a7: ldc 1.42
      // 50a9: ldc_w -177.545
      // 50ac: ldc_w 48.415
      // 50af: ldc_w 182.455
      // 50b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50bb: aastore
      // 50bc: dup
      // 50bd: bipush 33
      // 50bf: new net/minecraft/client/animation/Keyframe
      // 50c2: dup
      // 50c3: ldc 1.46
      // 50c5: ldc_w -177.878
      // 50c8: ldc_w 42.034
      // 50cb: ldc_w 182.122
      // 50ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50d7: aastore
      // 50d8: dup
      // 50d9: bipush 34
      // 50db: new net/minecraft/client/animation/Keyframe
      // 50de: dup
      // 50df: ldc 1.5
      // 50e1: ldc_w -178.03
      // 50e4: ldc_w 40.283
      // 50e7: ldc_w 181.97
      // 50ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50f3: aastore
      // 50f4: dup
      // 50f5: bipush 35
      // 50f7: new net/minecraft/client/animation/Keyframe
      // 50fa: dup
      // 50fb: ldc 1.54
      // 50fd: ldc_w -178.5
      // 5100: ldc_w 43.684
      // 5103: ldc_w 181.5
      // 5106: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5109: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 510c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 510f: aastore
      // 5110: dup
      // 5111: bipush 36
      // 5113: new net/minecraft/client/animation/Keyframe
      // 5116: dup
      // 5117: ldc 1.58
      // 5119: ldc_w -179.323
      // 511c: ldc_w 50.511
      // 511f: ldc_w 180.677
      // 5122: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5125: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5128: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 512b: aastore
      // 512c: dup
      // 512d: bipush 37
      // 512f: new net/minecraft/client/animation/Keyframe
      // 5132: dup
      // 5133: ldc 1.62
      // 5135: ldc_w -180.357
      // 5138: ldc_w 59.101
      // 513b: ldc_w 179.643
      // 513e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5141: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5144: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5147: aastore
      // 5148: dup
      // 5149: bipush 38
      // 514b: new net/minecraft/client/animation/Keyframe
      // 514e: dup
      // 514f: ldc 1.67
      // 5151: ldc_w -181.46
      // 5154: ldc_w 68.564
      // 5157: ldc_w 178.54
      // 515a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 515d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5160: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5163: aastore
      // 5164: dup
      // 5165: bipush 39
      // 5167: new net/minecraft/client/animation/Keyframe
      // 516a: dup
      // 516b: ldc 1.71
      // 516d: ldc_w -182.442
      // 5170: ldc_w 77.716
      // 5173: ldc_w 177.558
      // 5176: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5179: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 517c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 517f: aastore
      // 5180: dup
      // 5181: bipush 40
      // 5183: new net/minecraft/client/animation/Keyframe
      // 5186: dup
      // 5187: ldc 1.75
      // 5189: ldc_w -183.127
      // 518c: ldc_w 85.531
      // 518f: ldc_w 176.873
      // 5192: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5195: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5198: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 519b: aastore
      // 519c: dup
      // 519d: bipush 41
      // 519f: new net/minecraft/client/animation/Keyframe
      // 51a2: dup
      // 51a3: ldc 1.79
      // 51a5: ldc_w -183.425
      // 51a8: ldc_w 91.498
      // 51ab: ldc_w 176.575
      // 51ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 51b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51b7: aastore
      // 51b8: dup
      // 51b9: bipush 42
      // 51bb: new net/minecraft/client/animation/Keyframe
      // 51be: dup
      // 51bf: ldc 1.83
      // 51c1: ldc_w -182.659
      // 51c4: ldc_w 91.333
      // 51c7: ldc_w 177.341
      // 51ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 51cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51d3: aastore
      // 51d4: dup
      // 51d5: bipush 43
      // 51d7: new net/minecraft/client/animation/Keyframe
      // 51da: dup
      // 51db: ldc 1.88
      // 51dd: ldc_w -180.889
      // 51e0: ldc_w 83.336
      // 51e3: ldc_w 179.111
      // 51e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 51e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51ef: aastore
      // 51f0: dup
      // 51f1: bipush 44
      // 51f3: new net/minecraft/client/animation/Keyframe
      // 51f6: dup
      // 51f7: ldc 1.92
      // 51f9: ldc_w -178.989
      // 51fc: ldc_w 70.672
      // 51ff: ldc_w 181.011
      // 5202: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5205: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5208: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 520b: aastore
      // 520c: dup
      // 520d: bipush 45
      // 520f: new net/minecraft/client/animation/Keyframe
      // 5212: dup
      // 5213: ldc 1.96
      // 5215: ldc_w -177.6
      // 5218: ldc_w 58.419
      // 521b: ldc_w 182.4
      // 521e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5221: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5224: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5227: aastore
      // 5228: dup
      // 5229: bipush 46
      // 522b: new net/minecraft/client/animation/Keyframe
      // 522e: dup
      // 522f: fconst_2
      // 5230: ldc_w -177.066
      // 5233: ldc_w 52.262
      // 5236: ldc_w 182.934
      // 5239: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 523c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 523f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5242: aastore
      // 5243: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5246: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5249: ldc_w "CTR_leg_left"
      // 524c: new net/minecraft/client/animation/AnimationChannel
      // 524f: dup
      // 5250: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5253: bipush 49
      // 5255: anewarray 27
      // 5258: dup
      // 5259: bipush 0
      // 525a: new net/minecraft/client/animation/Keyframe
      // 525d: dup
      // 525e: fconst_0
      // 525f: ldc_w 0.878
      // 5262: ldc_w -1.018
      // 5265: ldc_w -0.451
      // 5268: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 526b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 526e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5271: aastore
      // 5272: dup
      // 5273: bipush 1
      // 5274: new net/minecraft/client/animation/Keyframe
      // 5277: dup
      // 5278: ldc 0.04
      // 527a: ldc_w 0.726
      // 527d: ldc_w -0.58
      // 5280: ldc_w -0.722
      // 5283: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5286: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5289: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 528c: aastore
      // 528d: dup
      // 528e: bipush 2
      // 528f: new net/minecraft/client/animation/Keyframe
      // 5292: dup
      // 5293: ldc 0.08
      // 5295: ldc_w 0.514
      // 5298: ldc_w -0.173
      // 529b: ldc_w -1.05
      // 529e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52a7: aastore
      // 52a8: dup
      // 52a9: bipush 3
      // 52aa: new net/minecraft/client/animation/Keyframe
      // 52ad: dup
      // 52ae: ldc 0.12
      // 52b0: ldc_w 0.268
      // 52b3: ldc_w 0.214
      // 52b6: ldc_w -1.353
      // 52b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52c2: aastore
      // 52c3: dup
      // 52c4: bipush 4
      // 52c5: new net/minecraft/client/animation/Keyframe
      // 52c8: dup
      // 52c9: ldc 0.17
      // 52cb: ldc_w -0.023
      // 52ce: ldc 0.642
      // 52d0: ldc_w -1.511
      // 52d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52dc: aastore
      // 52dd: dup
      // 52de: bipush 5
      // 52df: new net/minecraft/client/animation/Keyframe
      // 52e2: dup
      // 52e3: ldc 0.21
      // 52e5: ldc_w -0.392
      // 52e8: ldc_w 1.172
      // 52eb: ldc_w -1.356
      // 52ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52f7: aastore
      // 52f8: dup
      // 52f9: bipush 6
      // 52fb: new net/minecraft/client/animation/Keyframe
      // 52fe: dup
      // 52ff: ldc 0.25
      // 5301: ldc_w -0.847
      // 5304: ldc_w 1.719
      // 5307: ldc_w -0.632
      // 530a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 530d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5310: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5313: aastore
      // 5314: dup
      // 5315: bipush 7
      // 5317: new net/minecraft/client/animation/Keyframe
      // 531a: dup
      // 531b: ldc 0.29
      // 531d: ldc_w -0.56
      // 5320: ldc_w 1.978
      // 5323: ldc_w -0.876
      // 5326: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5329: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 532c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 532f: aastore
      // 5330: dup
      // 5331: bipush 8
      // 5333: new net/minecraft/client/animation/Keyframe
      // 5336: dup
      // 5337: ldc 0.33
      // 5339: ldc_w 0.231
      // 533c: ldc_w 1.393
      // 533f: ldc_w -2.083
      // 5342: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5345: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5348: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 534b: aastore
      // 534c: dup
      // 534d: bipush 9
      // 534f: new net/minecraft/client/animation/Keyframe
      // 5352: dup
      // 5353: ldc 0.38
      // 5355: ldc_w 0.266
      // 5358: ldc 0.88
      // 535a: ldc_w -1.939
      // 535d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5360: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5363: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5366: aastore
      // 5367: dup
      // 5368: bipush 10
      // 536a: new net/minecraft/client/animation/Keyframe
      // 536d: dup
      // 536e: ldc 0.42
      // 5370: ldc_w -0.036
      // 5373: ldc_w 0.038
      // 5376: ldc_w -1.01
      // 5379: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 537c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 537f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5382: aastore
      // 5383: dup
      // 5384: bipush 11
      // 5386: new net/minecraft/client/animation/Keyframe
      // 5389: dup
      // 538a: ldc 0.46
      // 538c: ldc_w 0.146
      // 538f: ldc_w -1.632
      // 5392: ldc_w -1.138
      // 5395: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5398: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 539b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 539e: aastore
      // 539f: dup
      // 53a0: bipush 12
      // 53a2: new net/minecraft/client/animation/Keyframe
      // 53a5: dup
      // 53a6: ldc 0.5
      // 53a8: ldc_w 0.512
      // 53ab: ldc_w -3.547
      // 53ae: ldc_w -1.808
      // 53b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 53b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53ba: aastore
      // 53bb: dup
      // 53bc: bipush 13
      // 53be: new net/minecraft/client/animation/Keyframe
      // 53c1: dup
      // 53c2: ldc 0.54
      // 53c4: ldc_w 0.816
      // 53c7: ldc_w -5.59
      // 53ca: ldc_w -2.623
      // 53cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 53d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53d6: aastore
      // 53d7: dup
      // 53d8: bipush 14
      // 53da: new net/minecraft/client/animation/Keyframe
      // 53dd: dup
      // 53de: ldc 0.58
      // 53e0: ldc_w 0.979
      // 53e3: ldc_w -7.637
      // 53e6: ldc_w -3.471
      // 53e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 53ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53f2: aastore
      // 53f3: dup
      // 53f4: bipush 15
      // 53f6: new net/minecraft/client/animation/Keyframe
      // 53f9: dup
      // 53fa: ldc 0.62
      // 53fc: ldc_w 0.893
      // 53ff: ldc_w -8.949
      // 5402: ldc_w -3.951
      // 5405: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5408: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 540b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 540e: aastore
      // 540f: dup
      // 5410: bipush 16
      // 5412: new net/minecraft/client/animation/Keyframe
      // 5415: dup
      // 5416: ldc 0.67
      // 5418: ldc_w 0.682
      // 541b: ldc_w -9.668
      // 541e: ldc_w -4.124
      // 5421: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5424: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5427: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 542a: aastore
      // 542b: dup
      // 542c: bipush 17
      // 542e: new net/minecraft/client/animation/Keyframe
      // 5431: dup
      // 5432: ldc 0.71
      // 5434: ldc_w 0.477
      // 5437: ldc_w -10.369
      // 543a: ldc_w -4.293
      // 543d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5440: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5443: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5446: aastore
      // 5447: dup
      // 5448: bipush 18
      // 544a: new net/minecraft/client/animation/Keyframe
      // 544d: dup
      // 544e: ldc 0.75
      // 5450: ldc_w 0.297
      // 5453: ldc_w -10.986
      // 5456: ldc_w -4.441
      // 5459: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 545c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 545f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5462: aastore
      // 5463: dup
      // 5464: bipush 19
      // 5466: new net/minecraft/client/animation/Keyframe
      // 5469: dup
      // 546a: ldc 0.79
      // 546c: ldc_w 0.16
      // 546f: ldc_w -11.453
      // 5472: ldc_w -4.554
      // 5475: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5478: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 547b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 547e: aastore
      // 547f: dup
      // 5480: bipush 20
      // 5482: new net/minecraft/client/animation/Keyframe
      // 5485: dup
      // 5486: ldc 0.83
      // 5488: ldc_w 0.076
      // 548b: ldc_w -11.739
      // 548e: ldc_w -4.622
      // 5491: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5494: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5497: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 549a: aastore
      // 549b: dup
      // 549c: bipush 21
      // 549e: new net/minecraft/client/animation/Keyframe
      // 54a1: dup
      // 54a2: ldc 0.88
      // 54a4: ldc_w 0.033
      // 54a7: ldc_w -11.886
      // 54aa: ldc_w -4.658
      // 54ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 54b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54b6: aastore
      // 54b7: dup
      // 54b8: bipush 22
      // 54ba: new net/minecraft/client/animation/Keyframe
      // 54bd: dup
      // 54be: ldc 0.92
      // 54c0: ldc_w 0.055
      // 54c3: ldc_w -11.552
      // 54c6: ldc_w -4.467
      // 54c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 54cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54d2: aastore
      // 54d3: dup
      // 54d4: bipush 23
      // 54d6: new net/minecraft/client/animation/Keyframe
      // 54d9: dup
      // 54da: ldc 0.96
      // 54dc: ldc_w 0.22
      // 54df: ldc_w -10.151
      // 54e2: ldc_w -3.74
      // 54e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 54e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54ee: aastore
      // 54ef: dup
      // 54f0: bipush 24
      // 54f2: new net/minecraft/client/animation/Keyframe
      // 54f5: dup
      // 54f6: fconst_1
      // 54f7: ldc_w 0.524
      // 54fa: ldc_w -5.831
      // 54fd: ldc_w -1.35
      // 5500: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5503: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5506: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5509: aastore
      // 550a: dup
      // 550b: bipush 25
      // 550d: new net/minecraft/client/animation/Keyframe
      // 5510: dup
      // 5511: ldc 1.04
      // 5513: ldc_w -0.652
      // 5516: ldc_w -2.167
      // 5519: ldc_w -1.01
      // 551c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 551f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5522: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5525: aastore
      // 5526: dup
      // 5527: bipush 26
      // 5529: new net/minecraft/client/animation/Keyframe
      // 552c: dup
      // 552d: ldc 1.08
      // 552f: ldc_w -1.013
      // 5532: ldc_w -3.197
      // 5535: ldc_w -3.195
      // 5538: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 553b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 553e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5541: aastore
      // 5542: dup
      // 5543: bipush 27
      // 5545: new net/minecraft/client/animation/Keyframe
      // 5548: dup
      // 5549: ldc 1.12
      // 554b: ldc_w -0.224
      // 554e: ldc_w -6.426
      // 5551: ldc_w -4.209
      // 5554: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5557: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 555a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 555d: aastore
      // 555e: dup
      // 555f: bipush 28
      // 5561: new net/minecraft/client/animation/Keyframe
      // 5564: dup
      // 5565: ldc 1.17
      // 5567: ldc_w 0.169
      // 556a: ldc_w -9.376
      // 556d: ldc_w -4.453
      // 5570: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5573: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5576: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5579: aastore
      // 557a: dup
      // 557b: bipush 29
      // 557d: new net/minecraft/client/animation/Keyframe
      // 5580: dup
      // 5581: ldc 1.21
      // 5583: ldc_w 0.217
      // 5586: ldc_w -11.257
      // 5589: ldc_w -4.506
      // 558c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 558f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5592: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5595: aastore
      // 5596: dup
      // 5597: bipush 30
      // 5599: new net/minecraft/client/animation/Keyframe
      // 559c: dup
      // 559d: ldc 1.25
      // 559f: ldc_w 0.84
      // 55a2: ldc_w -8.457
      // 55a5: ldc_w -5.104
      // 55a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 55ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55b1: aastore
      // 55b2: dup
      // 55b3: bipush 31
      // 55b5: new net/minecraft/client/animation/Keyframe
      // 55b8: dup
      // 55b9: ldc 1.29
      // 55bb: ldc_w 2.296
      // 55be: ldc_w -0.271
      // 55c1: ldc_w -5.18
      // 55c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 55c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55cd: aastore
      // 55ce: dup
      // 55cf: bipush 32
      // 55d1: new net/minecraft/client/animation/Keyframe
      // 55d4: dup
      // 55d5: ldc 1.33
      // 55d7: ldc_w 2.607
      // 55da: ldc_w 4.927
      // 55dd: ldc_w -3.911
      // 55e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 55e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55e9: aastore
      // 55ea: dup
      // 55eb: bipush 33
      // 55ed: new net/minecraft/client/animation/Keyframe
      // 55f0: dup
      // 55f1: ldc 1.38
      // 55f3: ldc_w 2.355
      // 55f6: ldc_w 6.005
      // 55f9: ldc_w -4.345
      // 55fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 55ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5602: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5605: aastore
      // 5606: dup
      // 5607: bipush 34
      // 5609: new net/minecraft/client/animation/Keyframe
      // 560c: dup
      // 560d: ldc 1.42
      // 560f: ldc_w 2.14
      // 5612: ldc_w 6.48
      // 5615: ldc_w -5.386
      // 5618: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 561b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 561e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5621: aastore
      // 5622: dup
      // 5623: bipush 35
      // 5625: new net/minecraft/client/animation/Keyframe
      // 5628: dup
      // 5629: ldc 1.46
      // 562b: ldc_w 1.835
      // 562e: ldc_w 7.806
      // 5631: ldc_w -5.722
      // 5634: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5637: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 563a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 563d: aastore
      // 563e: dup
      // 563f: bipush 36
      // 5641: new net/minecraft/client/animation/Keyframe
      // 5644: dup
      // 5645: ldc 1.5
      // 5647: ldc_w 1.512
      // 564a: ldc_w 9.112
      // 564d: ldc_w -5.317
      // 5650: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5653: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5656: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5659: aastore
      // 565a: dup
      // 565b: bipush 37
      // 565d: new net/minecraft/client/animation/Keyframe
      // 5660: dup
      // 5661: ldc 1.54
      // 5663: ldc_w 1.185
      // 5666: ldc_w 8.824
      // 5669: ldc_w -4.192
      // 566c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 566f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5672: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5675: aastore
      // 5676: dup
      // 5677: bipush 38
      // 5679: new net/minecraft/client/animation/Keyframe
      // 567c: dup
      // 567d: ldc 1.58
      // 567f: ldc_w 0.698
      // 5682: ldc_w 8.199
      // 5685: ldc_w -2.268
      // 5688: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 568b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 568e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5691: aastore
      // 5692: dup
      // 5693: bipush 39
      // 5695: new net/minecraft/client/animation/Keyframe
      // 5698: dup
      // 5699: ldc 1.62
      // 569b: ldc_w 0.214
      // 569e: ldc_w 6.754
      // 56a1: ldc_w -0.166
      // 56a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 56a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56ad: aastore
      // 56ae: dup
      // 56af: bipush 40
      // 56b1: new net/minecraft/client/animation/Keyframe
      // 56b4: dup
      // 56b5: ldc 1.67
      // 56b7: ldc_w -0.115
      // 56ba: ldc_w 4.484
      // 56bd: ldc_w 1.571
      // 56c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 56c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56c9: aastore
      // 56ca: dup
      // 56cb: bipush 41
      // 56cd: new net/minecraft/client/animation/Keyframe
      // 56d0: dup
      // 56d1: ldc 1.71
      // 56d3: ldc_w -0.173
      // 56d6: ldc_w 1.754
      // 56d9: ldc_w 2.419
      // 56dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 56df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56e5: aastore
      // 56e6: dup
      // 56e7: bipush 42
      // 56e9: new net/minecraft/client/animation/Keyframe
      // 56ec: dup
      // 56ed: ldc 1.75
      // 56ef: ldc_w 0.036
      // 56f2: ldc_w -0.9
      // 56f5: ldc_w 2.199
      // 56f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 56fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5701: aastore
      // 5702: dup
      // 5703: bipush 43
      // 5705: new net/minecraft/client/animation/Keyframe
      // 5708: dup
      // 5709: ldc 1.79
      // 570b: ldc_w 0.41
      // 570e: ldc_w -3.108
      // 5711: ldc_w 1.116
      // 5714: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5717: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 571a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 571d: aastore
      // 571e: dup
      // 571f: bipush 44
      // 5721: new net/minecraft/client/animation/Keyframe
      // 5724: dup
      // 5725: ldc 1.83
      // 5727: ldc_w 0.574
      // 572a: ldc_w -3.911
      // 572d: ldc_w 0.733
      // 5730: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5733: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5736: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5739: aastore
      // 573a: dup
      // 573b: bipush 45
      // 573d: new net/minecraft/client/animation/Keyframe
      // 5740: dup
      // 5741: ldc 1.88
      // 5743: ldc_w 0.491
      // 5746: ldc_w -3.139
      // 5749: ldc 1.463
      // 574b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 574e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5751: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5754: aastore
      // 5755: dup
      // 5756: bipush 46
      // 5758: new net/minecraft/client/animation/Keyframe
      // 575b: dup
      // 575c: ldc 1.92
      // 575e: ldc_w 0.549
      // 5761: ldc_w -1.713
      // 5764: ldc_w 1.432
      // 5767: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 576a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 576d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5770: aastore
      // 5771: dup
      // 5772: bipush 47
      // 5774: new net/minecraft/client/animation/Keyframe
      // 5777: dup
      // 5778: ldc 1.96
      // 577a: ldc_w 0.759
      // 577d: ldc_w -0.995
      // 5780: ldc_w 0.335
      // 5783: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5786: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5789: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 578c: aastore
      // 578d: dup
      // 578e: bipush 48
      // 5790: new net/minecraft/client/animation/Keyframe
      // 5793: dup
      // 5794: fconst_2
      // 5795: ldc_w 0.878
      // 5798: ldc_w -1.018
      // 579b: ldc_w -0.451
      // 579e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 57a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57a7: aastore
      // 57a8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 57ab: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 57ae: ldc_w "CTR_leg_right"
      // 57b1: new net/minecraft/client/animation/AnimationChannel
      // 57b4: dup
      // 57b5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 57b8: bipush 47
      // 57ba: anewarray 27
      // 57bd: dup
      // 57be: bipush 0
      // 57bf: new net/minecraft/client/animation/Keyframe
      // 57c2: dup
      // 57c3: fconst_0
      // 57c4: ldc_w -2.789
      // 57c7: ldc_w 0.901
      // 57ca: ldc_w 2.789
      // 57cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57d6: aastore
      // 57d7: dup
      // 57d8: bipush 1
      // 57d9: new net/minecraft/client/animation/Keyframe
      // 57dc: dup
      // 57dd: ldc 0.04
      // 57df: ldc_w -2.357
      // 57e2: ldc_w 3.406
      // 57e5: ldc_w 2.357
      // 57e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57f1: aastore
      // 57f2: dup
      // 57f3: bipush 2
      // 57f4: new net/minecraft/client/animation/Keyframe
      // 57f7: dup
      // 57f8: ldc 0.08
      // 57fa: ldc_w -1.847
      // 57fd: ldc_w 4.471
      // 5800: ldc_w 1.847
      // 5803: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5806: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5809: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 580c: aastore
      // 580d: dup
      // 580e: bipush 3
      // 580f: new net/minecraft/client/animation/Keyframe
      // 5812: dup
      // 5813: ldc 0.12
      // 5815: ldc_w -1.304
      // 5818: ldc_w 4.236
      // 581b: ldc_w 1.304
      // 581e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5821: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5824: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5827: aastore
      // 5828: dup
      // 5829: bipush 4
      // 582a: new net/minecraft/client/animation/Keyframe
      // 582d: dup
      // 582e: ldc 0.17
      // 5830: ldc_w -0.714
      // 5833: ldc_w 2.734
      // 5836: ldc_w 0.714
      // 5839: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 583c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 583f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5842: aastore
      // 5843: dup
      // 5844: bipush 5
      // 5845: new net/minecraft/client/animation/Keyframe
      // 5848: dup
      // 5849: ldc 0.21
      // 584b: ldc_w -0.067
      // 584e: ldc_w -0.143
      // 5851: ldc_w 0.067
      // 5854: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5857: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 585a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 585d: aastore
      // 585e: dup
      // 585f: bipush 6
      // 5861: new net/minecraft/client/animation/Keyframe
      // 5864: dup
      // 5865: ldc 0.25
      // 5867: ldc_w 0.566
      // 586a: ldc_w -4.643
      // 586d: ldc_w -0.566
      // 5870: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5873: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5876: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5879: aastore
      // 587a: dup
      // 587b: bipush 7
      // 587d: new net/minecraft/client/animation/Keyframe
      // 5880: dup
      // 5881: ldc 0.29
      // 5883: ldc_w 1.064
      // 5886: ldc_w -11.374
      // 5889: ldc_w -1.064
      // 588c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 588f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5892: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5895: aastore
      // 5896: dup
      // 5897: bipush 8
      // 5899: new net/minecraft/client/animation/Keyframe
      // 589c: dup
      // 589d: ldc 0.33
      // 589f: ldc_w 1.9
      // 58a2: ldc_w -19.886
      // 58a5: ldc_w -1.9
      // 58a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58b1: aastore
      // 58b2: dup
      // 58b3: bipush 9
      // 58b5: new net/minecraft/client/animation/Keyframe
      // 58b8: dup
      // 58b9: ldc 0.38
      // 58bb: ldc_w 3.375
      // 58be: ldc_w -27.53
      // 58c1: ldc_w -3.375
      // 58c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58cd: aastore
      // 58ce: dup
      // 58cf: bipush 10
      // 58d1: new net/minecraft/client/animation/Keyframe
      // 58d4: dup
      // 58d5: ldc 0.42
      // 58d7: ldc_w 2.282
      // 58da: ldc_w -18.317
      // 58dd: ldc_w -2.282
      // 58e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58e9: aastore
      // 58ea: dup
      // 58eb: bipush 11
      // 58ed: new net/minecraft/client/animation/Keyframe
      // 58f0: dup
      // 58f1: ldc 0.46
      // 58f3: ldc_w 0.934
      // 58f6: ldc_w -6.175
      // 58f9: ldc_w -0.934
      // 58fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5902: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5905: aastore
      // 5906: dup
      // 5907: bipush 12
      // 5909: new net/minecraft/client/animation/Keyframe
      // 590c: dup
      // 590d: ldc 0.5
      // 590f: ldc_w 1.147
      // 5912: ldc_w -6.684
      // 5915: ldc_w -1.146
      // 5918: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 591b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 591e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5921: aastore
      // 5922: dup
      // 5923: bipush 13
      // 5925: new net/minecraft/client/animation/Keyframe
      // 5928: dup
      // 5929: ldc 0.54
      // 592b: ldc_w 0.838
      // 592e: ldc_w -6.905
      // 5931: ldc_w -0.838
      // 5934: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5937: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 593a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 593d: aastore
      // 593e: dup
      // 593f: bipush 14
      // 5941: new net/minecraft/client/animation/Keyframe
      // 5944: dup
      // 5945: ldc 0.58
      // 5947: ldc_w 0.121
      // 594a: ldc_w -6.926
      // 594d: ldc_w -0.121
      // 5950: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5953: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5956: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5959: aastore
      // 595a: dup
      // 595b: bipush 15
      // 595d: new net/minecraft/client/animation/Keyframe
      // 5960: dup
      // 5961: ldc 0.67
      // 5963: ldc_w -2.111
      // 5966: ldc_w -6.692
      // 5969: ldc_w 2.111
      // 596c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 596f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5972: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5975: aastore
      // 5976: dup
      // 5977: bipush 16
      // 5979: new net/minecraft/client/animation/Keyframe
      // 597c: dup
      // 597d: ldc 0.75
      // 597f: ldc_w -4.782
      // 5982: ldc_w -6.309
      // 5985: ldc_w 4.782
      // 5988: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 598b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 598e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5991: aastore
      // 5992: dup
      // 5993: bipush 17
      // 5995: new net/minecraft/client/animation/Keyframe
      // 5998: dup
      // 5999: ldc 0.79
      // 599b: ldc_w -6.077
      // 599e: ldc_w -5.849
      // 59a1: ldc_w 6.077
      // 59a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59ad: aastore
      // 59ae: dup
      // 59af: bipush 18
      // 59b1: new net/minecraft/client/animation/Keyframe
      // 59b4: dup
      // 59b5: ldc 0.83
      // 59b7: ldc_w -6.796
      // 59ba: ldc_w -5.229
      // 59bd: ldc_w 6.796
      // 59c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59c9: aastore
      // 59ca: dup
      // 59cb: bipush 19
      // 59cd: new net/minecraft/client/animation/Keyframe
      // 59d0: dup
      // 59d1: ldc 0.88
      // 59d3: ldc_w -7.282
      // 59d6: ldc_w -4.471
      // 59d9: ldc_w 7.282
      // 59dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59e5: aastore
      // 59e6: dup
      // 59e7: bipush 20
      // 59e9: new net/minecraft/client/animation/Keyframe
      // 59ec: dup
      // 59ed: ldc 0.92
      // 59ef: ldc_w -7.734
      // 59f2: ldc_w -3.244
      // 59f5: ldc_w 7.734
      // 59f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a01: aastore
      // 5a02: dup
      // 5a03: bipush 21
      // 5a05: new net/minecraft/client/animation/Keyframe
      // 5a08: dup
      // 5a09: ldc 0.96
      // 5a0b: ldc_w -8.226
      // 5a0e: ldc_w -0.621
      // 5a11: ldc_w 8.226
      // 5a14: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a1d: aastore
      // 5a1e: dup
      // 5a1f: bipush 22
      // 5a21: new net/minecraft/client/animation/Keyframe
      // 5a24: dup
      // 5a25: fconst_1
      // 5a26: ldc_w -7.405
      // 5a29: ldc_w 2.841
      // 5a2c: ldc_w 7.405
      // 5a2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a38: aastore
      // 5a39: dup
      // 5a3a: bipush 23
      // 5a3c: new net/minecraft/client/animation/Keyframe
      // 5a3f: dup
      // 5a40: ldc 1.04
      // 5a42: ldc_w -3.253
      // 5a45: ldc_w 5.205
      // 5a48: ldc_w 3.253
      // 5a4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a54: aastore
      // 5a55: dup
      // 5a56: bipush 24
      // 5a58: new net/minecraft/client/animation/Keyframe
      // 5a5b: dup
      // 5a5c: ldc 1.08
      // 5a5e: ldc_w -0.955
      // 5a61: ldc_w 6.897
      // 5a64: ldc_w 0.955
      // 5a67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a70: aastore
      // 5a71: dup
      // 5a72: bipush 25
      // 5a74: new net/minecraft/client/animation/Keyframe
      // 5a77: dup
      // 5a78: ldc 1.12
      // 5a7a: ldc_w -1.235
      // 5a7d: ldc_w 4.218
      // 5a80: ldc_w 1.235
      // 5a83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a8c: aastore
      // 5a8d: dup
      // 5a8e: bipush 26
      // 5a90: new net/minecraft/client/animation/Keyframe
      // 5a93: dup
      // 5a94: ldc 1.17
      // 5a96: ldc_w -1.253
      // 5a99: ldc_w -13.754
      // 5a9c: ldc_w 1.254
      // 5a9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5aa2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5aa5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aa8: aastore
      // 5aa9: dup
      // 5aaa: bipush 27
      // 5aac: new net/minecraft/client/animation/Keyframe
      // 5aaf: dup
      // 5ab0: ldc 1.21
      // 5ab2: ldc_w -0.959
      // 5ab5: ldc_w -31.229
      // 5ab8: ldc_w 0.959
      // 5abb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5abe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ac1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ac4: aastore
      // 5ac5: dup
      // 5ac6: bipush 28
      // 5ac8: new net/minecraft/client/animation/Keyframe
      // 5acb: dup
      // 5acc: ldc 1.25
      // 5ace: ldc_w -2.12
      // 5ad1: ldc_w -28.757
      // 5ad4: ldc_w 2.12
      // 5ad7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ada: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5add: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ae0: aastore
      // 5ae1: dup
      // 5ae2: bipush 29
      // 5ae4: new net/minecraft/client/animation/Keyframe
      // 5ae7: dup
      // 5ae8: ldc 1.29
      // 5aea: ldc_w -2.145
      // 5aed: ldc_w -25.573
      // 5af0: ldc_w 2.145
      // 5af3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5af6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5af9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5afc: aastore
      // 5afd: dup
      // 5afe: bipush 30
      // 5b00: new net/minecraft/client/animation/Keyframe
      // 5b03: dup
      // 5b04: ldc 1.33
      // 5b06: ldc_w -1.138
      // 5b09: ldc_w -20.323
      // 5b0c: ldc_w 1.138
      // 5b0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b18: aastore
      // 5b19: dup
      // 5b1a: bipush 31
      // 5b1c: new net/minecraft/client/animation/Keyframe
      // 5b1f: dup
      // 5b20: ldc 1.38
      // 5b22: ldc_w 0.081
      // 5b25: ldc_w -13.043
      // 5b28: ldc_w -0.08
      // 5b2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b34: aastore
      // 5b35: dup
      // 5b36: bipush 32
      // 5b38: new net/minecraft/client/animation/Keyframe
      // 5b3b: dup
      // 5b3c: ldc 1.42
      // 5b3e: ldc_w 0.956
      // 5b41: ldc_w -4.632
      // 5b44: ldc_w -0.955
      // 5b47: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b50: aastore
      // 5b51: dup
      // 5b52: bipush 33
      // 5b54: new net/minecraft/client/animation/Keyframe
      // 5b57: dup
      // 5b58: ldc 1.46
      // 5b5a: ldc_w 1.146
      // 5b5d: ldc_w 2.033
      // 5b60: ldc_w -1.146
      // 5b63: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b66: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b69: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b6c: aastore
      // 5b6d: dup
      // 5b6e: bipush 34
      // 5b70: new net/minecraft/client/animation/Keyframe
      // 5b73: dup
      // 5b74: ldc 1.5
      // 5b76: ldc_w 0.833
      // 5b79: ldc_w 3.581
      // 5b7c: ldc_w -0.834
      // 5b7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b88: aastore
      // 5b89: dup
      // 5b8a: bipush 35
      // 5b8c: new net/minecraft/client/animation/Keyframe
      // 5b8f: dup
      // 5b90: ldc 1.54
      // 5b92: ldc_w 0.797
      // 5b95: ldc_w -0.434
      // 5b98: ldc_w -0.797
      // 5b9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ba1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ba4: aastore
      // 5ba5: dup
      // 5ba6: bipush 36
      // 5ba8: new net/minecraft/client/animation/Keyframe
      // 5bab: dup
      // 5bac: ldc 1.58
      // 5bae: ldc_w 0.853
      // 5bb1: ldc_w -7.501
      // 5bb4: ldc_w -0.853
      // 5bb7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bbd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bc0: aastore
      // 5bc1: dup
      // 5bc2: bipush 37
      // 5bc4: new net/minecraft/client/animation/Keyframe
      // 5bc7: dup
      // 5bc8: ldc 1.62
      // 5bca: ldc_w 1.006
      // 5bcd: ldc_w -15.649
      // 5bd0: ldc_w -1.006
      // 5bd3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bd6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bd9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bdc: aastore
      // 5bdd: dup
      // 5bde: bipush 38
      // 5be0: new net/minecraft/client/animation/Keyframe
      // 5be3: dup
      // 5be4: ldc 1.67
      // 5be6: ldc_w -0.17
      // 5be9: ldc_w -14.12
      // 5bec: ldc 0.17
      // 5bee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bf1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bf4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bf7: aastore
      // 5bf8: dup
      // 5bf9: bipush 39
      // 5bfb: new net/minecraft/client/animation/Keyframe
      // 5bfe: dup
      // 5bff: ldc 1.71
      // 5c01: ldc_w -2.394
      // 5c04: ldc_w -0.054
      // 5c07: ldc_w 2.394
      // 5c0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c13: aastore
      // 5c14: dup
      // 5c15: bipush 40
      // 5c17: new net/minecraft/client/animation/Keyframe
      // 5c1a: dup
      // 5c1b: ldc 1.75
      // 5c1d: ldc_w -3.659
      // 5c20: ldc_w 12.478
      // 5c23: ldc_w 3.659
      // 5c26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c2f: aastore
      // 5c30: dup
      // 5c31: bipush 41
      // 5c33: new net/minecraft/client/animation/Keyframe
      // 5c36: dup
      // 5c37: ldc 1.79
      // 5c39: ldc_w -3.777
      // 5c3c: ldc_w 14.083
      // 5c3f: ldc_w 3.777
      // 5c42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c4b: aastore
      // 5c4c: dup
      // 5c4d: bipush 42
      // 5c4f: new net/minecraft/client/animation/Keyframe
      // 5c52: dup
      // 5c53: ldc 1.83
      // 5c55: ldc_w -3.446
      // 5c58: ldc_w 8.693
      // 5c5b: ldc_w 3.446
      // 5c5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c67: aastore
      // 5c68: dup
      // 5c69: bipush 43
      // 5c6b: new net/minecraft/client/animation/Keyframe
      // 5c6e: dup
      // 5c6f: ldc 1.88
      // 5c71: ldc_w -3.171
      // 5c74: ldc_w 4.389
      // 5c77: ldc_w 3.171
      // 5c7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c83: aastore
      // 5c84: dup
      // 5c85: bipush 44
      // 5c87: new net/minecraft/client/animation/Keyframe
      // 5c8a: dup
      // 5c8b: ldc 1.92
      // 5c8d: ldc_w -2.975
      // 5c90: ldc_w 1.531
      // 5c93: ldc_w 2.975
      // 5c96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c9f: aastore
      // 5ca0: dup
      // 5ca1: bipush 45
      // 5ca3: new net/minecraft/client/animation/Keyframe
      // 5ca6: dup
      // 5ca7: ldc 1.96
      // 5ca9: ldc_w -2.853
      // 5cac: ldc_w 0.327
      // 5caf: ldc_w 2.853
      // 5cb2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5cb5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cb8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cbb: aastore
      // 5cbc: dup
      // 5cbd: bipush 46
      // 5cbf: new net/minecraft/client/animation/Keyframe
      // 5cc2: dup
      // 5cc3: fconst_2
      // 5cc4: ldc_w -2.789
      // 5cc7: ldc_w 0.901
      // 5cca: ldc_w 2.789
      // 5ccd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5cd0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cd3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cd6: aastore
      // 5cd7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5cda: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5cdd: ldc_w "CTR_leg_right"
      // 5ce0: new net/minecraft/client/animation/AnimationChannel
      // 5ce3: dup
      // 5ce4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5ce7: bipush 46
      // 5ce9: anewarray 27
      // 5cec: dup
      // 5ced: bipush 0
      // 5cee: new net/minecraft/client/animation/Keyframe
      // 5cf1: dup
      // 5cf2: fconst_0
      // 5cf3: ldc_w 0.698
      // 5cf6: ldc_w 0.987
      // 5cf9: ldc_w 0.254
      // 5cfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5cff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d05: aastore
      // 5d06: dup
      // 5d07: bipush 1
      // 5d08: new net/minecraft/client/animation/Keyframe
      // 5d0b: dup
      // 5d0c: ldc 0.04
      // 5d0e: ldc 0.62
      // 5d10: ldc_w 1.154
      // 5d13: ldc_w 0.001
      // 5d16: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d1f: aastore
      // 5d20: dup
      // 5d21: bipush 2
      // 5d22: new net/minecraft/client/animation/Keyframe
      // 5d25: dup
      // 5d26: ldc 0.08
      // 5d28: ldc_w 0.516
      // 5d2b: ldc_w 1.258
      // 5d2e: ldc_w -0.102
      // 5d31: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d3a: aastore
      // 5d3b: dup
      // 5d3c: bipush 3
      // 5d3d: new net/minecraft/client/animation/Keyframe
      // 5d40: dup
      // 5d41: ldc 0.12
      // 5d43: ldc_w 0.404
      // 5d46: ldc_w 1.368
      // 5d49: ldc_w -0.048
      // 5d4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d55: aastore
      // 5d56: dup
      // 5d57: bipush 4
      // 5d58: new net/minecraft/client/animation/Keyframe
      // 5d5b: dup
      // 5d5c: ldc 0.17
      // 5d5e: ldc_w 0.282
      // 5d61: ldc 1.5
      // 5d63: ldc_w 0.173
      // 5d66: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d6f: aastore
      // 5d70: dup
      // 5d71: bipush 5
      // 5d72: new net/minecraft/client/animation/Keyframe
      // 5d75: dup
      // 5d76: ldc 0.21
      // 5d78: ldc_w 0.14
      // 5d7b: ldc 1.612
      // 5d7d: ldc_w 0.579
      // 5d80: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d83: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d86: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d89: aastore
      // 5d8a: dup
      // 5d8b: bipush 6
      // 5d8d: new net/minecraft/client/animation/Keyframe
      // 5d90: dup
      // 5d91: ldc 0.25
      // 5d93: ldc_w -0.012
      // 5d96: ldc_w 1.556
      // 5d99: ldc_w 1.166
      // 5d9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5da2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5da5: aastore
      // 5da6: dup
      // 5da7: bipush 7
      // 5da9: new net/minecraft/client/animation/Keyframe
      // 5dac: dup
      // 5dad: ldc 0.29
      // 5daf: ldc_w -0.146
      // 5db2: ldc_w 1.044
      // 5db5: ldc_w 1.86
      // 5db8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5dbb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dbe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dc1: aastore
      // 5dc2: dup
      // 5dc3: bipush 8
      // 5dc5: new net/minecraft/client/animation/Keyframe
      // 5dc8: dup
      // 5dc9: ldc 0.33
      // 5dcb: ldc_w -0.35
      // 5dce: ldc_w -0.204
      // 5dd1: ldc_w 2.217
      // 5dd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5dd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ddd: aastore
      // 5dde: dup
      // 5ddf: bipush 9
      // 5de1: new net/minecraft/client/animation/Keyframe
      // 5de4: dup
      // 5de5: ldc 0.38
      // 5de7: ldc_w -0.566
      // 5dea: ldc_w -1.955
      // 5ded: ldc_w 1.6
      // 5df0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5df3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5df6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5df9: aastore
      // 5dfa: dup
      // 5dfb: bipush 10
      // 5dfd: new net/minecraft/client/animation/Keyframe
      // 5e00: dup
      // 5e01: ldc 0.42
      // 5e03: ldc_w -0.416
      // 5e06: ldc_w -1.761
      // 5e09: ldc_w 0.807
      // 5e0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e15: aastore
      // 5e16: dup
      // 5e17: bipush 11
      // 5e19: new net/minecraft/client/animation/Keyframe
      // 5e1c: dup
      // 5e1d: ldc 0.46
      // 5e1f: ldc_w -0.26
      // 5e22: ldc_w -2.074
      // 5e25: ldc_w -0.333
      // 5e28: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e31: aastore
      // 5e32: dup
      // 5e33: bipush 12
      // 5e35: new net/minecraft/client/animation/Keyframe
      // 5e38: dup
      // 5e39: ldc 0.5
      // 5e3b: ldc_w -0.316
      // 5e3e: ldc_w -2.949
      // 5e41: ldc_w -0.722
      // 5e44: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e4d: aastore
      // 5e4e: dup
      // 5e4f: bipush 13
      // 5e51: new net/minecraft/client/animation/Keyframe
      // 5e54: dup
      // 5e55: ldc 0.54
      // 5e57: ldc_w -0.332
      // 5e5a: ldc_w -3.818
      // 5e5d: ldc_w -1.135
      // 5e60: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e63: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e66: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e69: aastore
      // 5e6a: dup
      // 5e6b: bipush 14
      // 5e6d: new net/minecraft/client/animation/Keyframe
      // 5e70: dup
      // 5e71: ldc 0.58
      // 5e73: ldc_w -0.356
      // 5e76: ldc_w -4.617
      // 5e79: ldc_w -1.525
      // 5e7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e85: aastore
      // 5e86: dup
      // 5e87: bipush 15
      // 5e89: new net/minecraft/client/animation/Keyframe
      // 5e8c: dup
      // 5e8d: ldc 0.62
      // 5e8f: ldc_w -0.396
      // 5e92: ldc_w -5.152
      // 5e95: ldc_w -1.787
      // 5e98: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ea1: aastore
      // 5ea2: dup
      // 5ea3: bipush 16
      // 5ea5: new net/minecraft/client/animation/Keyframe
      // 5ea8: dup
      // 5ea9: ldc 0.79
      // 5eab: ldc_w -0.396
      // 5eae: ldc_w -5.152
      // 5eb1: ldc_w -1.787
      // 5eb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5eb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5eba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ebd: aastore
      // 5ebe: dup
      // 5ebf: bipush 17
      // 5ec1: new net/minecraft/client/animation/Keyframe
      // 5ec4: dup
      // 5ec5: ldc 0.83
      // 5ec7: ldc_w -0.378
      // 5eca: ldc_w -5.099
      // 5ecd: ldc_w -1.761
      // 5ed0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ed3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ed6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ed9: aastore
      // 5eda: dup
      // 5edb: bipush 18
      // 5edd: new net/minecraft/client/animation/Keyframe
      // 5ee0: dup
      // 5ee1: ldc 0.88
      // 5ee3: ldc_w -0.22
      // 5ee6: ldc_w -4.66
      // 5ee9: ldc_w -1.562
      // 5eec: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5eef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ef2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ef5: aastore
      // 5ef6: dup
      // 5ef7: bipush 19
      // 5ef9: new net/minecraft/client/animation/Keyframe
      // 5efc: dup
      // 5efd: ldc 0.92
      // 5eff: ldc_w -0.017
      // 5f02: ldc_w -4.138
      // 5f05: ldc_w -1.349
      // 5f08: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f11: aastore
      // 5f12: dup
      // 5f13: bipush 20
      // 5f15: new net/minecraft/client/animation/Keyframe
      // 5f18: dup
      // 5f19: ldc 0.96
      // 5f1b: ldc_w 0.343
      // 5f1e: ldc_w -3.262
      // 5f21: ldc_w -1.073
      // 5f24: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f2d: aastore
      // 5f2e: dup
      // 5f2f: bipush 21
      // 5f31: new net/minecraft/client/animation/Keyframe
      // 5f34: dup
      // 5f35: fconst_1
      // 5f36: ldc_w 0.661
      // 5f39: ldc_w -2.162
      // 5f3c: ldc_w -0.876
      // 5f3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f48: aastore
      // 5f49: dup
      // 5f4a: bipush 22
      // 5f4c: new net/minecraft/client/animation/Keyframe
      // 5f4f: dup
      // 5f50: ldc 1.04
      // 5f52: ldc_w -0.347
      // 5f55: ldc_w -4.896
      // 5f58: ldc_w -1.722
      // 5f5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f64: aastore
      // 5f65: dup
      // 5f66: bipush 23
      // 5f68: new net/minecraft/client/animation/Keyframe
      // 5f6b: dup
      // 5f6c: ldc 1.08
      // 5f6e: ldc_w -0.396
      // 5f71: ldc_w -5.152
      // 5f74: ldc_w -1.787
      // 5f77: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f80: aastore
      // 5f81: dup
      // 5f82: bipush 24
      // 5f84: new net/minecraft/client/animation/Keyframe
      // 5f87: dup
      // 5f88: ldc 1.12
      // 5f8a: ldc_w -0.81
      // 5f8d: ldc_w -4.363
      // 5f90: ldc_w -4.43
      // 5f93: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f9c: aastore
      // 5f9d: dup
      // 5f9e: bipush 25
      // 5fa0: new net/minecraft/client/animation/Keyframe
      // 5fa3: dup
      // 5fa4: ldc 1.17
      // 5fa6: ldc_w -0.514
      // 5fa9: ldc_w 5.276
      // 5fac: ldc_w -4.032
      // 5faf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fb8: aastore
      // 5fb9: dup
      // 5fba: bipush 26
      // 5fbc: new net/minecraft/client/animation/Keyframe
      // 5fbf: dup
      // 5fc0: ldc 1.21
      // 5fc2: ldc_w -0.832
      // 5fc5: ldc_w 6.223
      // 5fc8: ldc -1.883
      // 5fca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fcd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fd0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fd3: aastore
      // 5fd4: dup
      // 5fd5: bipush 27
      // 5fd7: new net/minecraft/client/animation/Keyframe
      // 5fda: dup
      // 5fdb: ldc 1.25
      // 5fdd: ldc_w -0.447
      // 5fe0: ldc_w 6.076
      // 5fe3: ldc_w -2.735
      // 5fe6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fe9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fef: aastore
      // 5ff0: dup
      // 5ff1: bipush 28
      // 5ff3: new net/minecraft/client/animation/Keyframe
      // 5ff6: dup
      // 5ff7: ldc 1.29
      // 5ff9: ldc_w -0.474
      // 5ffc: ldc_w 5.894
      // 5fff: ldc_w -3.582
      // 6002: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6005: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6008: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 600b: aastore
      // 600c: dup
      // 600d: bipush 29
      // 600f: new net/minecraft/client/animation/Keyframe
      // 6012: dup
      // 6013: ldc 1.33
      // 6015: ldc_w -0.84
      // 6018: ldc_w 5.626
      // 601b: ldc_w -4.74
      // 601e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6021: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6024: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6027: aastore
      // 6028: dup
      // 6029: bipush 30
      // 602b: new net/minecraft/client/animation/Keyframe
      // 602e: dup
      // 602f: ldc 1.38
      // 6031: ldc_w -1.211
      // 6034: ldc_w 5.184
      // 6037: ldc_w -6.009
      // 603a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 603d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6040: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6043: aastore
      // 6044: dup
      // 6045: bipush 31
      // 6047: new net/minecraft/client/animation/Keyframe
      // 604a: dup
      // 604b: ldc 1.42
      // 604d: ldc_w -1.398
      // 6050: ldc_w 4.809
      // 6053: ldc_w -6.966
      // 6056: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6059: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 605c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 605f: aastore
      // 6060: dup
      // 6061: bipush 32
      // 6063: new net/minecraft/client/animation/Keyframe
      // 6066: dup
      // 6067: ldc 1.46
      // 6069: ldc_w -1.327
      // 606c: ldc_w 5.331
      // 606f: ldc_w -7.07
      // 6072: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6075: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6078: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 607b: aastore
      // 607c: dup
      // 607d: bipush 33
      // 607f: new net/minecraft/client/animation/Keyframe
      // 6082: dup
      // 6083: ldc 1.5
      // 6085: ldc_w -1.073
      // 6088: ldc_w 6.212
      // 608b: ldc_w -6.28
      // 608e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6091: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6094: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6097: aastore
      // 6098: dup
      // 6099: bipush 34
      // 609b: new net/minecraft/client/animation/Keyframe
      // 609e: dup
      // 609f: ldc 1.54
      // 60a1: ldc_w -0.937
      // 60a4: ldc_w 5.956
      // 60a7: ldc_w -4.807
      // 60aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 60ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60b3: aastore
      // 60b4: dup
      // 60b5: bipush 35
      // 60b7: new net/minecraft/client/animation/Keyframe
      // 60ba: dup
      // 60bb: ldc 1.58
      // 60bd: ldc_w -0.825
      // 60c0: ldc_w 5.611
      // 60c3: ldc_w -2.717
      // 60c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 60c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60cf: aastore
      // 60d0: dup
      // 60d1: bipush 36
      // 60d3: new net/minecraft/client/animation/Keyframe
      // 60d6: dup
      // 60d7: ldc 1.62
      // 60d9: ldc_w -0.759
      // 60dc: ldc_w 4.763
      // 60df: ldc_w -0.576
      // 60e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 60e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60eb: aastore
      // 60ec: dup
      // 60ed: bipush 37
      // 60ef: new net/minecraft/client/animation/Keyframe
      // 60f2: dup
      // 60f3: ldc 1.67
      // 60f5: ldc_w -0.218
      // 60f8: ldc_w 4.926
      // 60fb: ldc_w 0.164
      // 60fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6101: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6104: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6107: aastore
      // 6108: dup
      // 6109: bipush 38
      // 610b: new net/minecraft/client/animation/Keyframe
      // 610e: dup
      // 610f: ldc 1.71
      // 6111: ldc_w 0.663
      // 6114: ldc_w 5.223
      // 6117: ldc_w -1.609
      // 611a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 611d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6120: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6123: aastore
      // 6124: dup
      // 6125: bipush 39
      // 6127: new net/minecraft/client/animation/Keyframe
      // 612a: dup
      // 612b: ldc 1.75
      // 612d: ldc_w 0.776
      // 6130: ldc_w 3.098
      // 6133: ldc_w -3.308
      // 6136: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6139: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 613c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 613f: aastore
      // 6140: dup
      // 6141: bipush 40
      // 6143: new net/minecraft/client/animation/Keyframe
      // 6146: dup
      // 6147: ldc 1.79
      // 6149: ldc_w 0.665
      // 614c: ldc_w 1.73
      // 614f: ldc_w -2.967
      // 6152: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6155: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6158: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 615b: aastore
      // 615c: dup
      // 615d: bipush 41
      // 615f: new net/minecraft/client/animation/Keyframe
      // 6162: dup
      // 6163: ldc 1.83
      // 6165: ldc_w 0.739
      // 6168: ldc_w 1.816
      // 616b: ldc_w -1.72
      // 616e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6171: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6174: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6177: aastore
      // 6178: dup
      // 6179: bipush 42
      // 617b: new net/minecraft/client/animation/Keyframe
      // 617e: dup
      // 617f: ldc 1.88
      // 6181: ldc_w 0.746
      // 6184: ldc_w 1.604
      // 6187: ldc -0.727
      // 6189: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 618c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 618f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6192: aastore
      // 6193: dup
      // 6194: bipush 43
      // 6196: new net/minecraft/client/animation/Keyframe
      // 6199: dup
      // 619a: ldc 1.92
      // 619c: ldc_w 0.73
      // 619f: ldc_w 1.305
      // 61a2: ldc_w -0.063
      // 61a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 61a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61ae: aastore
      // 61af: dup
      // 61b0: bipush 44
      // 61b2: new net/minecraft/client/animation/Keyframe
      // 61b5: dup
      // 61b6: ldc 1.96
      // 61b8: ldc_w 0.714
      // 61bb: ldc 1.08
      // 61bd: ldc_w 0.265
      // 61c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 61c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61c9: aastore
      // 61ca: dup
      // 61cb: bipush 45
      // 61cd: new net/minecraft/client/animation/Keyframe
      // 61d0: dup
      // 61d1: fconst_2
      // 61d2: ldc_w 0.698
      // 61d5: ldc_w 0.987
      // 61d8: ldc_w 0.254
      // 61db: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 61de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61e4: aastore
      // 61e5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 61e8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 61eb: ldc_w "CTR_leg_right.002"
      // 61ee: new net/minecraft/client/animation/AnimationChannel
      // 61f1: dup
      // 61f2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 61f5: bipush 10
      // 61f7: anewarray 27
      // 61fa: dup
      // 61fb: bipush 0
      // 61fc: new net/minecraft/client/animation/Keyframe
      // 61ff: dup
      // 6200: ldc 0.38
      // 6202: ldc_w -2.707
      // 6205: fconst_0
      // 6206: ldc_w -2.155
      // 6209: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 620c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 620f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6212: aastore
      // 6213: dup
      // 6214: bipush 1
      // 6215: new net/minecraft/client/animation/Keyframe
      // 6218: dup
      // 6219: ldc 0.46
      // 621b: ldc_w -2.707
      // 621e: fconst_0
      // 621f: ldc_w 11.952
      // 6222: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6225: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6228: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 622b: aastore
      // 622c: dup
      // 622d: bipush 2
      // 622e: new net/minecraft/client/animation/Keyframe
      // 6231: dup
      // 6232: ldc 1.08
      // 6234: ldc_w -2.707
      // 6237: fconst_0
      // 6238: ldc_w 11.952
      // 623b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 623e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6241: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6244: aastore
      // 6245: dup
      // 6246: bipush 3
      // 6247: new net/minecraft/client/animation/Keyframe
      // 624a: dup
      // 624b: ldc 1.12
      // 624d: ldc_w -2.707
      // 6250: fconst_0
      // 6251: ldc_w 2.042
      // 6254: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6257: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 625a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 625d: aastore
      // 625e: dup
      // 625f: bipush 4
      // 6260: new net/minecraft/client/animation/Keyframe
      // 6263: dup
      // 6264: ldc 1.17
      // 6266: ldc_w -2.707
      // 6269: fconst_0
      // 626a: ldc_w -16.362
      // 626d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6270: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6273: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6276: aastore
      // 6277: dup
      // 6278: bipush 5
      // 6279: new net/minecraft/client/animation/Keyframe
      // 627c: dup
      // 627d: ldc 1.21
      // 627f: ldc_w -2.707
      // 6282: fconst_0
      // 6283: ldc_w -26.272
      // 6286: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6289: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 628c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 628f: aastore
      // 6290: dup
      // 6291: bipush 6
      // 6293: new net/minecraft/client/animation/Keyframe
      // 6296: dup
      // 6297: ldc 1.62
      // 6299: ldc_w -2.707
      // 629c: fconst_0
      // 629d: ldc_w -26.272
      // 62a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 62a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62a9: aastore
      // 62aa: dup
      // 62ab: bipush 7
      // 62ad: new net/minecraft/client/animation/Keyframe
      // 62b0: dup
      // 62b1: ldc 1.67
      // 62b3: ldc_w -2.707
      // 62b6: fconst_0
      // 62b7: ldc_w -22.504
      // 62ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 62bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62c3: aastore
      // 62c4: dup
      // 62c5: bipush 8
      // 62c7: new net/minecraft/client/animation/Keyframe
      // 62ca: dup
      // 62cb: ldc 1.75
      // 62cd: ldc_w -2.707
      // 62d0: fconst_0
      // 62d1: ldc_w -5.923
      // 62d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 62d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62dd: aastore
      // 62de: dup
      // 62df: bipush 9
      // 62e1: new net/minecraft/client/animation/Keyframe
      // 62e4: dup
      // 62e5: ldc 1.79
      // 62e7: ldc_w -2.707
      // 62ea: fconst_0
      // 62eb: ldc_w -2.155
      // 62ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 62f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62f7: aastore
      // 62f8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 62fb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 62fe: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 6301: putstatic net/unusual/block_factorys_bosses/client/animation/yeti/yetiHandPunch.HAND_PUNCH Lnet/minecraft/client/animation/AnimationDefinition;
      // 6304: return
   }
}
