package simple.coding.reference;

/* Strong Reference
 * Strong Reference�� Root Set�� ��ȿ�� ����(��ü ��������)�� �����ϸ� GC����� �ƴϴ�.
 * ��, ��ü ���������� null �Ǵ� �ٸ� ��ü�� �����ϰ� �Ǹ� Origin Object�� GC ����� �ȴ�.
 */
public class Strongly {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("---Strong Reference Test---");

		// ������ GC�� Strong Reference �ν��Ͻ��� �����Ѵ�
		SimpleGC gc = new SimpleGC();
		MyReference strongRef = new MyReference();

		// GC ���� �� ����
		gc.garbageCollect();
		gc.isValid(strongRef);
		// strongRef�� GC����� �ƴϴ�

		// strongRef�� Null�� �����Ѵ�
		System.out.println("��ü Root Set ����");
		strongRef = null;

		// GC ���� �� ����
		gc.garbageCollect();
		gc.isValid(strongRef);
		// strongRef�� GC����̴�.

	}

}