package br.com.caelum.almoco.threads;

public class SyncConta {

	private double saldo;
	
	synchronized public void deposita(double valor) {
		saldo += valor;
	}
	
	public double getSaldo() {
		return saldo;
	}
}
