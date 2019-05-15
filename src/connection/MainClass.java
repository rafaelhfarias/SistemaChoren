package connection;

public class MainClass {
public static void main(String[] args) {
	ConexaoMySQL.getConexaoMySQL();
	System.out.println(ConexaoMySQL.statusConection());
}
}
