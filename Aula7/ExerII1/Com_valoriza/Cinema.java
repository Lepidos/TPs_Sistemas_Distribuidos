
public Class Cinema {

	private Lock l;
	private Condition c1;
	private String film;
	private int viewers;
	private int L;  // Lugares

	private favorite Map<Sring,int>;  // String, Class x  // Class x int Condition
	public Cinema(seats) {
		l = new ReentrantLock();
		c = l.newCondition();
		L = seats;
		film = NULL;
		favorite = new Map<String,int>;
//		viewers = 0;
	}

	public Class Playing {
			int viewers;
			private Lock l2;
			private Condition c2;
			public Playing() {
				viewers = 0;
				l2 = new ReentrantLock();
				c2 = l.newCondition();
			}
	}
	public void assiste(String filme) {
		l.lock();
		try {
			while ((film != filme && viewers > 0) || ( film == filme && viewers >= L)) {
				c.await();
			}
			this.film = filme;
			viewers++;
		} finally {
			l.unlock()
		}

	}

	public void abandona(String filme) {
		l.lock();
		try {
			viewers--;
			array = favorite.get(filme) // this is pseudo code
			for viewer in array:
				c.signal();
		} finally {
			l.unlock();
		}

	}

	public String filmeEmExibição() {
		l.lock();
		try {
			return film;
		} finally {
			l.unlock();
		}
	}
