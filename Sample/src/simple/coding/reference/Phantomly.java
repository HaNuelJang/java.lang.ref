package simple.coding.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;

/* Phantom Reference
 * Phantom Reference는 기존의 Reference와 사용목적이 다르다.
 * 해당 Reference는 선행 데이터를 올바르게 제거한 후, 다음 작업을 안전하게 조작하기 위함이다.
 * 
 * 
 * <GC동작방식>
 * 1) soft, waek reference의 mark 수행
 * 2) mark 대상 null처리
 * 3) finalize() 수행
 *      => *해당 처리도중 StrongReference가 생길 수 있는 문제 발생 / finalize는 한번만 실행되므로, StrongReference에 의해 영원히 GC에서 제거되지 않을 수 있음.
 * 4) finalize() 이후, memory에 reclaim 하기전, phantom reference 인스턴스는 메모리에서 제거후 queue에 적제
 * 5) GC의 Sweep 처리 
 * 
 * GC의 finalize()의 문제점을 개선하기 위해 해당 Reference가 만들어졌다.
 * 문제점 중 하나인 `GC에 의해 제거된 객체가 resurrect(Strong Reference를 갖도록 코딩되어 있는 경우)하는 경우`가 있다. 
 * 따라서 Phantom Reference 인스턴스는 finalize 상관없이 메모리에서 제거 후, Queue에 적재함으로 finalize()의 resurrection문제를 방지한다. 
 * 
 * <Phantom Reference 처리방식>
 * 1) Root Set 및 Phantom Reference 객체 생성
 * 2) Root Set 제거(참조무효화 및 GC수행: 물리적 제거)
 * 3) 물리적 제거가 일어난 Phantom Ref는 인스턴스 제거 후, ReferenceQueue로 enqueue
 * 4) 개발자는 ReferenceQueue를 확인하고 poll 또는 remove 호출
 * 
 * <특징>
 * 1) ReferenceQueue를 반드시 사용해야 한다.
 * 2) 물리적으로 제거 되면, Queue에 enqueue된다.
 * 3) Queue의 값은 수동으로 제거해야 한다.
 * 4) Phantom Reference는 get() 호출시, 항상 null을 리턴한다.
 */
public class Phantomly {
	public static HashMap<PhantomReference<MyReference>, String> taskMap = new HashMap<>();

	public static void main(String[] args) throws InterruptedException {
		System.out.println("---Phantom Reference Test---");

		// 간단한 GC와 Phantom Reference 인스턴스를 세팅한다.
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

		System.out.println("### phantom reference get() 확인");
		// GC 실행 및 검증
		gc.garbageCollect();
		gc.isValid(phantomRef.get());
		// phantomRef는 GC대상이다

		System.out.println("## phantom reference 참조제거 및 enqueue 확인");
		// phantomRef의 Root Set인 strongRef의 참조를 제거한다
		System.out.println("객체 Root Set 제거");
		strongRef = null;

		// GC 실행 및 검증
		gc.garbageCollect();
		taskMap.forEach((k, v) -> {
			System.out.println(v + " : " + k + "  isEnqueued: " + k.isEnqueued());
		});

		System.out.println("## phantom reference poll() 확인");
		Reference<?> targetRef = phantomQueue.poll();
		if (targetRef != null) {
			System.out.println("Queue에 GC대상이 존재, 제거합니다.");
			taskMap.remove(targetRef);
		}
		taskMap.forEach((k, v) -> {
			System.out.println(v + " : " + k + "  isEnqueued: " + k.isEnqueued());
		});

		System.out.println("## phantom reference 유효 참조인 경우 enqueue 여부 확인");
		gc.garbageCollect();
		taskMap.forEach((k, v) -> {
			System.out.println(v + " : " + k + "  isEnqueued: " + k.isEnqueued());
		});

	}

}