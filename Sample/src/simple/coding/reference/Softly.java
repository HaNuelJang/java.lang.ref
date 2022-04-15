package simple.coding.reference;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/* Soft Reference
 * Soft Reference�� Root Set�� ���� ������ �������� �ʴ´�.
 * ��, Heap�޸𸮰� ������ GC�� ����� �� GC����� �ȴ�.
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
			System.out.println("-- GC �ڵ� ó��");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("---Soft Reference Test---");

		// ������ GC�� Soft Reference �ν��Ͻ��� �����Ѵ�
		SimpleGC gc = new SimpleGC();
		MyReference strongRef = new MyReference();
		SoftReference<MyReference> softRef = new SoftReference<MyReference>(strongRef);
		Reference<MyReference> a = new SoftReference<MyReference>(strongRef);
		
		// sotfRef�� Root Set�� strongRef�� ������ �����Ѵ�
		System.out.println("��ü Root Set ����");
		strongRef = null;

		// GC ���� �� ����
		gc.garbageCollect();
		gc.isValid(softRef.get());
		// softRef�� GC����� �ƴϴ�.

		// Heap �޸𸮸� �������� GC�� ������ ����ǵ��� �Ѵ�.
		useHeapMemory();

		gc.isValid(softRef.get());
		// softRef�� GC����̴�.

	}

}