package br.com.caelum.almoco.threads;

public class Worker implements Runnable {
	
	private SyncConta conta;

	public Worker(SyncConta conta) {
		this.conta = conta;
	}

	@Override
	public void run() {
		conta.deposita(1.0);
	}

	
}
