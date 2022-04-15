package simple.coding.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;

/* Phantom Reference
 * Phantom Reference�� ������ Reference�� �������� �ٸ���.
 * �ش� Reference�� ���� �����͸� �ùٸ��� ������ ��, ���� �۾��� �����ϰ� �����ϱ� �����̴�.
 * 
 * 
 * <GC���۹��>
 * 1) soft, waek reference�� mark ����
 * 2) mark ��� nulló��
 * 3) finalize() ����
 *      => *�ش� ó������ StrongReference�� ���� �� �ִ� ���� �߻� / finalize�� �ѹ��� ����ǹǷ�, StrongReference�� ���� ������ GC���� ���ŵ��� ���� �� ����.
 * 4) finalize() ����, memory�� reclaim �ϱ���, phantom reference �ν��Ͻ��� �޸𸮿��� ������ queue�� ����
 * 5) GC�� Sweep ó�� 
 * 
 * GC�� finalize()�� �������� �����ϱ� ���� �ش� Reference�� ���������.
 * ������ �� �ϳ��� `GC�� ���� ���ŵ� ��ü�� resurrect(Strong Reference�� ������ �ڵ��Ǿ� �ִ� ���)�ϴ� ���`�� �ִ�. 
 * ���� Phantom Reference �ν��Ͻ��� finalize ������� �޸𸮿��� ���� ��, Queue�� ���������� finalize()�� resurrection������ �����Ѵ�. 
 * 
 * <Phantom Reference ó�����>
 * 1) Root Set �� Phantom Reference ��ü ����
 * 2) Root Set ����(������ȿȭ �� GC����: ������ ����)
 * 3) ������ ���Ű� �Ͼ Phantom Ref�� �ν��Ͻ� ���� ��, ReferenceQueue�� enqueue
 * 4) �����ڴ� ReferenceQueue�� Ȯ���ϰ� poll �Ǵ� remove ȣ��
 * 
 * <Ư¡>
 * 1) ReferenceQueue�� �ݵ�� ����ؾ� �Ѵ�.
 * 2) ���������� ���� �Ǹ�, Queue�� enqueue�ȴ�.
 * 3) Queue�� ���� �������� �����ؾ� �Ѵ�.
 * 4) Phantom Reference�� get() ȣ���, �׻� null�� �����Ѵ�.
 */
public class Phantomly {
	public static HashMap<PhantomReference<MyReference>, String> taskMap = new HashMap<>();

	public static void main(String[] args) throws InterruptedException {
		System.out.println("---Phantom Reference Test---");

		// ������ GC�� Phantom Reference �ν��Ͻ��� �����Ѵ�.
		SimpleGC gc = new SimpleGC();
		ReferenceQueue<MyReference> phantomQueue = new ReferenceQueue<>();
		MyReference strongRef = new MyReference();
		PhantomReference<MyReference> phantomRef = new PhantomReference<>(strongRef, phantomQueue);
		MyReference strongRef2 = new MyReference();
		PhantomReference<MyReference> phantomRef2 = new PhantomReference<>(strongRef2, phantomQueue);

		taskMap.put(phantomRef, "1st");
		taskMap.put(phantomRef2, "2nd");
		taskMap.forEach((k, v) -> {
			System.out.println(v + " : " + k + "  isEnqueued: " + k.isEnqueued());
		});

		System.out.println("### phantom reference get() Ȯ��");
		// GC ���� �� ����
		gc.garbageCollect();
		gc.isValid(phantomRef.get());
		// phantomRef�� GC����̴�

		System.out.println("## phantom reference �������� �� enqueue Ȯ��");
		// phantomRef�� Root Set�� strongRef�� ������ �����Ѵ�
		System.out.println("��ü Root Set ����");
		strongRef = null;

		// GC ���� �� ����
		gc.garbageCollect();
		taskMap.forEach((k, v) -> {
			System.out.println(v + " : " + k + "  isEnqueued: " + k.isEnqueued());
		});

		System.out.println("## phantom reference poll() Ȯ��");
		Reference<?> targetRef = phantomQueue.poll();
		if (targetRef != null) {
			System.out.println("Queue�� GC����� ����, �����մϴ�.");
			taskMap.remove(targetRef);
		}
		taskMap.forEach((k, v) -> {
			System.out.println(v + " : " + k + "  isEnqueued: " + k.isEnqueued());
		});

		System.out.println("## phantom reference ��ȿ ������ ��� enqueue ���� Ȯ��");
		gc.garbageCollect();
		taskMap.forEach((k, v) -> {
			System.out.println(v + " : " + k + "  isEnqueued: " + k.isEnqueued());
		});

	}

}