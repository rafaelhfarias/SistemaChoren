package router;

import java.io.IOException;

import connection.BD;
import connection.ConexaoMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Data;

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
            try {
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
                            nextPage = "login.html";
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
                    case "Cadastrar Cartao":
                        nextPage = "cadastrocard.html";
                        break;
                    case "Registrar Transacao":
                        nextPage = "registrotrans.jsp";
                        break;
                    case "registrartrans":
                        String nomeT = request.getParameter("name");
                        String valor = request.getParameter("price");
                        String data = request.getParameter("date");
                        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                        java.sql.Date dataT = new java.sql.Date(fmt.parse(data).getTime());
                        String categoria = request.getParameter("category");
                        String cartao = request.getParameter("card");
                        String parcela = request.getParameter("parcela");
                        if (new Integer(cartao) == -1){
                            if ((BD.getSaldo(sessionId) < new Double(valor) && !categoria.equals("Receita")) || (new Integer(parcela) != 1))nextPage = "registrotrans.jsp";
                            else {
                                BD.insereTrans(sessionId, nomeT, new Double(valor), dataT, categoria, 1, 1);
                                nextPage = "mainPage.jsp";
                            }
                        }
                        else{
                            if (categoria.equals("Receita") || Data.beforeM(BD.getValidade(new Integer(cartao)), dataT, new Integer(parcela)) || BD.getLimiteDisp(new Integer(cartao)) < new Double(valor)){
                                nextPage = "registrotrans.jsp";
                                //System.out.println(Data.beforeM(BD.getValidade(new Integer(cartao)), dataT, new Integer(parcela)));
                                //System.out.println(cartao);
                                //System.out.println(BD.getLimiteDisp(new Integer(cartao)));
                            }
                            else{
                                double v_parcela = (new Double(valor))/(new Integer(parcela));
                                for (int i = 1; i <= new Integer(parcela); i++){
                                    BD.insereTrans(sessionId, nomeT, v_parcela, Data.addM(dataT, i-1), categoria, new Integer(cartao), i, new Integer(parcela));
                                    
                                }
                                nextPage = "mainPage.jsp";
                            }
                        }
                        
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
            } catch (ParseException ex) {
                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
