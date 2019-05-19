package router;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import connection.BD;
import connection.ConexaoMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Semelhante ao Router
 * 
 * @author Lincoln
 */
public class NewServlet extends HttpServlet {
    private static int sessionId;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public static int getId(){
        return sessionId;
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
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
		response.sendRedirect(nextPage);
		
            
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
