package router;

import java.io.IOException;

import connection.BD;
import connection.ConexaoMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Router
 */
@WebServlet("/Router")
public class Router extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static int sessionId;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Router() {
        super();
        // TODO Auto-generated constructor stub
	}

	public static int getId(){
        return sessionId;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/* String path = request.getParameter("path");
		String nextPage;
		switch (path){
		case "entrar":
			nextPage = "mainPage.jsp";
			break;
		default:
			nextPage = "login.html";
		} */
		String path = request.getParameter("path");
		String nextPage = "";
		switch (path){
		case "Conectar":
                    String login = request.getParameter("user");
                    String pass = request.getParameter("password");
                    if (BD.autentica(login,pass)){
                        sessionId = BD.getId(login);
                        nextPage = "mainPage.jsp";
                        break;
                    }
                    else 
			break;
                case "Cadastrar":
                        nextPage = "cadastro.html";
                        break;
                case "cadastrarUser":
                        String email = request.getParameter("email");
                        String senha = request.getParameter("password");
                        String nome = request.getParameter("name");
                        BD.insereUsuario(nome, email, senha);
                        nextPage = "index.html";
                        break;        
                case "Cadastrar Cartão":
                        nextPage = "cadastrocard.html";
                        break;
                case "Registrar Transacao":
                        nextPage = "registrotrans.jsp";
                        break;
                case "registrarcard":
                        String numero = request.getParameter("number");
                        String limite = request.getParameter("limit");
                        String mes = request.getParameter("month");
                        String ano = request.getParameter("year");
                        String fatura = request.getParameter("bill");
                        BD.insereCartao(numero, new Double(limite), new Integer(mes), new Integer(ano), new Integer(fatura));
                        nextPage = "mainPage.jsp";
                        break;
                default:
			nextPage = "index.html";
		}
		RequestDispatcher view = request.getRequestDispatcher(nextPage);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
