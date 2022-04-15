package simple.coding.reference;

public class SimpleGC {
	// 1. GC ���� 
	public void garbageCollect() throws InterruptedException {
		System.out.println("-- GC ȣ��");
		System.gc();
		System.out.println("-- GC ó����..");
		Thread.sleep(3000);
		System.out.println("-- GC �Ϸ�");
	}

	// 2. ��ü ��ȿ�� �˻�
	public void isValid(Object Ref) {
		if (isRefAlive(Ref)) {
			System.out.println("���: ��ȿ�� ��ü �Դϴ�.");
			return;
		}
		System.out.println("���: ���ŵ� ��ü �Դϴ�.");
	}

	// 2-1. ��ȿ�� ��ü���� �Ǻ�
	private static Boolean isRefAlive(Object Ref) {
		return Ref == null ? Boolean.FALSE : Boolean.TRUE;
	}
}
