import java.util.*;

// Há um lock no armazem e uma condition por produto
// Greedy implementation

class Warehouse {
	private Map<String, Product> map =  new HashMap<String, Product>();

	private ReentrantLock lock;
	private class Product {
		int quantity = 0;
		Condition condition = new lock.condition();
	}

	private Product get(String item) {
		condition = lock.newCondition();
		lock = new ReentrantLock();
		Product p = map.get(item);
		if (p != null) return p;
		p = new Product();
		map.put(item, p);
		return p;
	}

  public void supply(String item, int quantity) {
	this.lock.lock();
    	try {
		Product p = get(item);
		p.quantity += quantity;
		p.condition.signalAll();
	} finally {
		this.lock.unlock();
	}
  }

  // Errado se faltar algum produto...
  public void consume(Set<String> items) {
	lock.lock();
	String [] itemsArray = items.toArray(new String [items.size()]);

	try {
		Product p;
		int i = 0;
		while (i<itemsArray.lenght) {  	//para evitar starvation contar as vezes deste cilco
						//while e mudar para a versão greedy
			p = get (items.Array[i])
			i++;
			this.condition.await();
			p = get(s);
			while (p.quantity == 0) {
				p.condition.await();
				i = 0;
			}
		}
	for (String s : items) {
		p = get(s);
		p.quantity--;
	}
	} finally {
		lock.unlock();
	}

  }

}
