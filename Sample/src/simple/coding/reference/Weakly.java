package simple.coding.reference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* Weak Reference
 * Weak Reference�� ���� GC���� ������ Sweep ����� �ȴ�.
 * ��, �������� Weak Reference�� �����ϴ� Weak Reference�� ��ü�� �������� �ʾƾ� �Ѵ�.
 */
public class Weakly {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("---Weak Reference Test---");

		// ������ GC�� Weak Reference �ν��Ͻ��� �����Ѵ�
		SimpleGC gc = new SimpleGC();
		MyReference strongRef = new MyReference();
		WeakReference<MyReference> weakRef = new WeakReference<MyReference>(strongRef);

		// GC ���� �� ����
		gc.garbageCollect();
		gc.isValid(weakRef.get());
		// weakRef�� GC����� �ƴϴ�.

		// weakRef�� Root Set�� strongRef�� ������ �����Ѵ�.
		System.out.println("��ü Root Set ����");
		strongRef = null;

		// GC ���� �� ����
		gc.garbageCollect();
		gc.isValid(weakRef.get());
		// weakRef�� GC����̴�.

		// ���: WeakReference�� Root Set�� ��ȿ�ϸ�, GC����� �ƴϴ�. Root Set�� ������ ���ŵǰ� �ٸ� ��ü����� ���谡
		// Weakly Reachable �ϸ�, GC����̴�.
		// + HashMap�� ���, put�� Key�� StrongReference�� �ȴ�. ���� Origin Reference ������ ������
		// ���ŵǴ��� HashMap���� StrongReference�� �����ϱ� ������ GC����� �ƴϴ�.
		// �̸� �ذ��ϱ����� key�� WeakReference�� ������ WeakHashMap�� ����Ѵ�.

	}

}