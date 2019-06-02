package util;

public class TransacaoPorMesCategoria {
	private String mes;
	private double alimentacao;
	private double casa;
	private double comunica��o;
	private double pessoais;
	private double educa��o;
	private double investimento;
	private double lazer;
	private double sa�de;
	private double tarifas;
	private double transporte;
	private double outros;

	public TransacaoPorMesCategoria(String mes, double alimentacao, double casa, double comunica��o, double pessoais,
			double educa��o, double investimento, double lazer, double sa�de, double tarifas, double transporte,
			double outros) {
		this.mes = mes;
		this.alimentacao = alimentacao;
		this.casa = casa;
		this.comunica��o = comunica��o;
		this.pessoais = pessoais;
		this.educa��o = educa��o;
		this.investimento = investimento;
		this.lazer = lazer;
		this.sa�de = sa�de;
		this.tarifas = tarifas;
		this.transporte = transporte;
		this.outros = outros;
	}
	public static TransacaoPorMesCategoria invalido() {
		return new TransacaoPorMesCategoria("",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1);
	}
	public String getMes() {
		return mes;
	}

	public double getAlimentacao() {
		return alimentacao;
	}

	public double getCasa() {
		return casa;
	}

	public double getComunica��o() {
		return comunica��o;
	}

	public double getPessoais() {
		return pessoais;
	}

	public double getEduca��o() {
		return educa��o;
	}

	public double getInvestimento() {
		return investimento;
	}

	public double getLazer() {
		return lazer;
	}

	public double getSa�de() {
		return sa�de;
	}

	public double getTarifas() {
		return tarifas;
	}

	public double getTransporte() {
		return transporte;
	}

	public double getOutros() {
		return outros;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public void setAlimentacao(double alimentacao) {
		this.alimentacao = alimentacao;
	}

	public void setCasa(double casa) {
		this.casa = casa;
	}

	public void setComunica��o(double comunica��o) {
		this.comunica��o = comunica��o;
	}

	public void setPessoais(double pessoais) {
		this.pessoais = pessoais;
	}

	public void setEduca��o(double educa��o) {
		this.educa��o = educa��o;
	}

	public void setInvestimento(double investimento) {
		this.investimento = investimento;
	}

	public void setLazer(double lazer) {
		this.lazer = lazer;
	}

	public void setSa�de(double sa�de) {
		this.sa�de = sa�de;
	}

	public void setTarifas(double tarifas) {
		this.tarifas = tarifas;
	}

	public void setTransporte(double transporte) {
		this.transporte = transporte;
	}

	public void setOutros(double outros) {
		this.outros = outros;
	}

}
