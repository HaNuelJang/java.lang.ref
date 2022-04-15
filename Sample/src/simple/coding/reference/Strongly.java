package simple.coding.reference;

/* Strong Reference
 * Strong Reference는 Root Set의 유효한 참조(객체 참조변수)가 존재하면 GC대상이 아니다.
 * 즉, 객체 참조변수가 null 또는 다른 객체를 참조하게 되면 Origin Object는 GC 대상이 된다.
 */
public class Strongly {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("---Strong Reference Test---");

		// 간단한 GC와 Strong Reference 인스턴스를 세팅한다
		SimpleGC gc = new SimpleGC();
		MyReference strongRef = new MyReference();

		// GC 실행 및 검증
		gc.garbageCollect();
		gc.isValid(strongRef);
		// strongRef는 GC대상이 아니다

		// strongRef를 Null로 세팅한다
		System.out.println("객체 Root Set 제거");
		strongRef = null;

		// GC 실행 및 검증
		gc.garbageCollect();
		gc.isValid(strongRef);
		// strongRef는 GC대상이다.

	}

}