package priorityqueue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;

public class PriorityQueueTest {

	public static void main(String[] args) {
		PriorityQueue<GregorianCalendar> pq = new PriorityQueue<GregorianCalendar>();
		pq.add(new GregorianCalendar(1900, Calendar.DECEMBER, 9));
		pq.add(new GregorianCalendar(2001, Calendar.DECEMBER, 10));
		pq.add(new GregorianCalendar(1903, Calendar.DECEMBER, 3));
		pq.add(new GregorianCalendar(1910, Calendar.JULY, 22));
		
		System.out.println("遍历优先队列...");
		for (GregorianCalendar data : pq) {
			System.out.println(data.get(Calendar.YEAR));
		}
		System.out.println("删除元素...");
		while (!pq.isEmpty()) {
			System.out.println(pq.remove().get(Calendar.YEAR));
		}
	}

}
