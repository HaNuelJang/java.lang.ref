package simple.coding.reference;

public class SimpleGC {
	// 1. GC 수행 
	public void garbageCollect() throws InterruptedException {
		System.out.println("-- GC 호출");
		System.gc();
		System.out.println("-- GC 처리중..");
		Thread.sleep(3000);
		System.out.println("-- GC 완료");
	}

	// 2. 객체 유효성 검사
	public void isValid(Object Ref) {
		if (isRefAlive(Ref)) {
			System.out.println("결과: 유효한 객체 입니다.");
			return;
		}
		System.out.println("결과: 제거된 객체 입니다.");
	}

	// 2-1. 유효한 객체인지 판별
	private static Boolean isRefAlive(Object Ref) {
		return Ref == null ? Boolean.FALSE : Boolean.TRUE;
	}
}
