package simple.coding.reference;

/* TODO 
 * Java의 Reference.
 * 
 * Why? Java의 특징 중에서 일반적으로 알고있는 `메모리를 자동으로 관리`해 주는 GC가 있다.
 * 이 특징으로 인해 개발자는 메모리에 크게 신경 쓰지 않아도 된다. 하지만 GC는 생각보다 `스마트`하게 메모리를 관리해주지 않는다.
 * 예를 들면, 개발자가 Heap에 생성한 메모리를 한번 사용하고 더이상 쓰지 않을 A객체를 생성했다고 가정해보자.
 * 그리고 GC가 A객체에 대한 메모리를 해제 한다고 해보자. 
 * 
 * 언제 GC가 A객체의 메모리를 해제 할까?
 * GC는 무슨 근거로 A객체를 필요하지 않다고 판단할까?
 * GC가 객체를 해제하지 않는다면?
 * 
 * 질문들의 답을 하기 위해서는 GC의 원리와 GC의 Sweep 대상을 선별하는 방법에 대하여 알아야 한다.
 * 그리고 필요한 것이 java.lang.ref 패키지의 Reference 클래스 객체들 이다.
 * 즉, Reference객체를 통해 개발자가 간접적으로 개입하여 GC에게 Sweep대상에 대한 힌트를 줄 수 있다.
 * 이를 통해 GC가 효율적으로 '스마트'하게 메모리를 관리할 수 있게 된다. 
 * 
 * *NOTE
 * Reference의 접근성 수준에 따라 5가지로 분류한다. 그리고 분류 대상에 따라 GC의 수집(해제) 대상이 된다.
 * 1. Strongly reachable	// Sweep 제외
 * 2. Softly reachable		// JVM이 메모리가 부족할 때, Sweep 대상
 * 3. Weakly reachable		// WeakReference외 다른 객체가 참조하지 않는다면, Sweep 대상
 * 4. Phantomly reachable	// Sweep 대상
 * 5. UnReachable			// Sweep 대상
*/

class MyReference {
	public MyReference() {
		System.out.println("객체 생성");
	}
}