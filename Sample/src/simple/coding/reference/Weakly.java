package simple.coding.reference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* Weak Reference
 * Weak Reference는 다음 GC에서 무조건 Sweep 대상이 된다.
 * 단, 조건으로 Weak Reference를 참조하는 Weak Reference외 객체가 존재하지 않아야 한다.
 */
public class Weakly {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("---Weak Reference Test---");

		// 간단한 GC와 Weak Reference 인스턴스를 세팅한다
		SimpleGC gc = new SimpleGC();
		MyReference strongRef = new MyReference();
		WeakReference<MyReference> weakRef = new WeakReference<MyReference>(strongRef);

		// GC 실행 및 검증
		gc.garbageCollect();
		gc.isValid(weakRef.get());
		// weakRef는 GC대상이 아니다.

		// weakRef의 Root Set인 strongRef의 참조를 제거한다.
		System.out.println("객체 Root Set 제거");
		strongRef = null;

		// GC 실행 및 검증
		gc.garbageCollect();
		gc.isValid(weakRef.get());
		// weakRef는 GC대상이다.

		// 결론: WeakReference는 Root Set이 유효하면, GC대상이 아니다. Root Set의 참조가 제거되고 다른 객체들과의 관계가
		// Weakly Reachable 하면, GC대상이다.
		// + HashMap의 경우, put된 Key는 StrongReference가 된다. 따라서 Origin Reference 변수의 참조가
		// 제거되더라도 HashMap에서 StrongReference로 참조하기 떄문에 GC대상이 아니다.
		// 이를 해결하기위해 key를 WeakReference로 랩핑한 WeakHashMap을 사용한다.

	}

}