package util;

public class TransacaoPorMesCategoria {
	private String mes;
	private double alimentacao;
	private double casa;
	private double comunicação;
	private double pessoais;
	private double educação;
	private double investimento;
	private double lazer;
	private double saúde;
	private double tarifas;
	private double transporte;
	private double outros;

	public TransacaoPorMesCategoria(String mes, double alimentacao, double casa, double comunicação, double pessoais,
			double educação, double investimento, double lazer, double saúde, double tarifas, double transporte,
			double outros) {
		this.mes = mes;
		this.alimentacao = alimentacao;
		this.casa = casa;
		this.comunicação = comunicação;
		this.pessoais = pessoais;
		this.educação = educação;
		this.investimento = investimento;
		this.lazer = lazer;
		this.saúde = saúde;
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

	public double getComunicação() {
		return comunicação;
	}

	public double getPessoais() {
		return pessoais;
	}

	public double getEducação() {
		return educação;
	}

	public double getInvestimento() {
		return investimento;
	}

	public double getLazer() {
		return lazer;
	}

	public double getSaúde() {
		return saúde;
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

	public void setComunicação(double comunicação) {
		this.comunicação = comunicação;
	}

	public void setPessoais(double pessoais) {
		this.pessoais = pessoais;
	}

	public void setEducação(double educação) {
		this.educação = educação;
	}

	public void setInvestimento(double investimento) {
		this.investimento = investimento;
	}

	public void setLazer(double lazer) {
		this.lazer = lazer;
	}

	public void setSaúde(double saúde) {
		this.saúde = saúde;
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
