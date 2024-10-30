
public Class Cinema {

	private Lock l;
	private Condition c;
	private String film;
	private int viewers;
	private int L;  // Lugares

	Cinema(seats) {
		l = new ReentrantLock();
		c = l.newCondition();
		L = seats;
		film = NULL;
	}


	public void assiste(String filme) {
		l.lock();
		try {
			while (film != filme && viewers > 0) {
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
			if (! viewers) {
				c.signalAll();
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
