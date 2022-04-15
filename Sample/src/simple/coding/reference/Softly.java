package simple.coding.reference;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/* Soft Reference
 * Soft Reference는 Root Set의 참조 유무에 관여받지 않는다.
 * 단, Heap메모리가 가득차 GC가 수행될 때 GC대상이 된다.
 */
public class Softly {

	private static void useHeapMemory() {
		try {
			List<Softly> m = new ArrayList<Softly>(10000000);
			while (true) {
				m.add(new Softly());
			}
		} catch (OutOfMemoryError e) {
			System.out.println("ERR[" + e.getClass().getSimpleName() + "]: " + e.getMessage());
			System.out.println("-- GC 자동 처리");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("---Soft Reference Test---");

		// 간단한 GC와 Soft Reference 인스턴스를 세팅한다
		SimpleGC gc = new SimpleGC();
		MyReference strongRef = new MyReference();
		SoftReference<MyReference> softRef = new SoftReference<MyReference>(strongRef);
		Reference<MyReference> a = new SoftReference<MyReference>(strongRef);
		
		// sotfRef의 Root Set인 strongRef의 참조를 제거한다
		System.out.println("객체 Root Set 제거");
		strongRef = null;

		// GC 실행 및 검증
		gc.garbageCollect();
		gc.isValid(softRef.get());
		// softRef는 GC대상이 아니다.

		// Heap 메모리를 증가시켜 GC가 강제로 수행되도록 한다.
		useHeapMemory();

		gc.isValid(softRef.get());
		// softRef는 GC대상이다.

	}

}